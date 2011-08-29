package com.phyloa.timeline;

import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import timeline.DoubleString;
import timeline.Event;
import timeline.Item;
import timeline.Span;
import timeline.TDate;

import com.phyloa.dlib.renderer.Graphics2DIRenderer;
import com.phyloa.dlib.renderer.Renderer;
import com.phyloa.dlib.util.KeyHandler;

public class Timeline 
{
	public static final int maxZoom = 10;
	
	ArrayList<Item> items = new ArrayList<Item>();
	
	ArrayList<ItemRenderer> visible = new ArrayList<ItemRenderer>();
	
	Renderer r;
	
	KeyHandler k;
	
	TDate firstDate = new TDate();
	TDate lastDate = new TDate();
	
	Item selected = null;
	Item hover = null;
	
	File file = null;
	
	int priority = 0;
	ArrayList<String> tags = new ArrayList<String>();
	
	TimelinePreferences tp = new TimelinePreferences();
	
	String notes = "";
	
	public Timeline( Renderer r )
	{
		this.r = r;
		this.clear();
	}
	
	public void setKeyHandler( KeyHandler k )
	{
		this.k = k;
	}
	
	public void update()
	{
		if( k.right )
		{
			scrollRight( 1 );
		}
		if( k.left )
		{
			scrollLeft( 1 );
		}
		if( k.up )
		{
			zoomIn( .1f );
		}
		if( k.down )
		{
			zoomOut( .1f );
		}
	}
	
	public void zoomIn( float amt )
	{
		float diff = lastDate.monthDiff( firstDate );
		firstDate = firstDate.addDays( (int)(diff * amt) );
		lastDate = lastDate.addDays( -(int)(diff * amt) );
		updateVisibleItems();
	}
	
	public void zoomOut( float amt )
	{
		float diff = lastDate.monthDiff( firstDate );
		firstDate = firstDate.addDays( -(int)(diff * amt) );
		lastDate = lastDate.addDays( (int)(diff * amt) );
		updateVisibleItems();
	}
	
	public void scrollRight( float amt )
	{
		float diff = lastDate.monthDiff( firstDate );
		firstDate = firstDate.addHours( (int)(diff * amt) );
		lastDate = lastDate.addHours( (int)(diff * amt) );
		updateVisibleItems();
	}
	
	public void scrollLeft( float amt )
	{
		float diff = lastDate.monthDiff( firstDate );
		firstDate = firstDate.addHours( -((int)(diff * amt)) );
		lastDate = lastDate.addHours( -(int)(diff * amt) );
		updateVisibleItems();
	}
	
	public void render()
	{
		((Graphics2DIRenderer)r).g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		int width = r.getWidth();
		int height = r.getHeight();
		r.color( tp.backgroundColor );
		r.fillRect( 0, 0, width, height );
		
		int tempPriority = priority;
		while( tempPriority < 100 )
		{
			for( int i = 0; i < visible.size(); i++ )
			{
				ItemRenderer e = visible.get( i );
				if( !e.getPlaced() )
				{
					e.place( this, tp );
				}
			}
			
			if( belowHeight( height - 120 ) )
			{
				tempPriority += 5;
				for( int i = 0; i < visible.size(); i++ )
				{
					if( visible.get( i ).item.getPriority() < tempPriority )
					{
						visible.remove( i );
						i--;
					}
				}
			}
			else
			{
				break;
			}
		}
		
		//Sort to draw from bottom to top so lines go over other items
		Collections.sort( visible );
		
		for( int i = 0; i < visible.size(); i++ )
		{
			ItemRenderer e = visible.get( i );
			e.render( r, selected, hover, tp );
		}
		drawDateLine( r, width, height );
		
		if( selected != null )
		{
			r.color( 255, 255, 255 );
			r.fillRect( width - 300, height - 200, 280, 180 );
			r.color( 0, 0, 0 );
			r.drawRect( width - 300, height - 200, 280, 180 );
			
			r.pushMatrix();
			r.translate( width - 300 + 10, height - 200 );
			selected.renderInfo( r );
			r.popMatrix();
			
		}
	}
	
	private boolean belowHeight( int height )
	{
		for( int i = 0; i < visible.size(); i++ )
		{
			ItemRenderer r = visible.get( i );
			if( r.getY() + r.getHeight() > height )
			{
				return true;
			}
		}
		return false;
	}
	
	private void drawDateLine( Renderer r, int width, int height ) 
	{
		r.color( tp.lineColor );
		//DRAW a base line
		((Graphics2DIRenderer)r).g.setStroke( new BasicStroke( tp.lineThickness ) );
		r.line( 0, height - 100, width, height - 100 );
		
		float pixelsBetweenYears = r.getWidth() / (lastDate.monthDiff( firstDate ) / 12.f );
		int interval = 1; 
		while( pixelsBetweenYears < 15 )
		{
			interval *= 10;
			pixelsBetweenYears *= 10;
		}
		//CHOOSE first year to draw
		int firstYear = firstDate.getYear();
		//LOCK firstYear to interval
		while( firstYear % interval != 0 ) 
		{
			firstYear--;
		}
		//FIND pixel location of first year
		float firstDrawX = getDrawX( new TDate( firstYear, 1, 1 ) );
		//FIND last year to draw
		int lastYear = lastDate.getYear();
		for( int i = firstYear; i <= lastYear; i+= interval )
		{
			r.pushMatrix();
			r.translate( getDrawX( new TDate( i, 1, 1 ) ), height-100 );
			r.rotate( (float)Math.PI/2 );
			r.color( tp.lineColor );
			r.line( 0, 0, -20, 0 );
			r.color( tp.textColor );
			r.text( i + "", 3, 4 );
			r.popMatrix();
			if( pixelsBetweenYears > 180 )
			{
				for( int m = 1; m <= 12; m++ )
				{
					r.pushMatrix();
					r.translate( getDrawX( new TDate( i, m, 1 ) ), height-100 );
					r.rotate( (float)Math.PI/2 );
					r.color( tp.lineColor );
					r.line( 0, 0, -20, 0 );
					r.color( tp.textColor );
					r.text( TDate.getNameOfMonth( m ) + "", m == 1 ? 40 : 3, 4 );
					r.popMatrix();
					
					if( (pixelsBetweenYears / 12) > 18*TDate.getDaysInMonth( m, i ) )
					{
						for( int d = 0; d <= TDate.getDaysInMonth( m, i ); d++ )
						{
							r.pushMatrix();
							r.translate( getDrawX( new TDate( i, m, d ) ), height-100 );
							r.rotate( (float)Math.PI/2 );
							r.color( tp.lineColor );
							r.line( 0, 0, -20, 0 );
							r.color( tp.textColor );
							r.text( d + "", 3, 4 );
							r.popMatrix();
						}
					}
				}
			}
		}
	}
	
	public boolean isTagged( Item it )
	{
		if( tags.size() == 0 )
			return true;
		ArrayList<String> itags = it.getTags();
		for( int i = 0; i < itags.size(); i++ )
		{
			for( int j = 0; j < tags.size(); j++ )
			{
				if( itags.get( i ).toLowerCase().equals( tags.get( j ).toLowerCase() ) )
				{
					return true;
				}
			}
		}
		return false;
	}

	public int getDrawX( TDate d )
	{
		int width = r.getWidth();
		float dist = d.monthDiff( firstDate );
		float diff = lastDate.monthDiff( firstDate );
		dist = dist / diff;
		return (int) (dist * width);
	}
	
	public int getYearFromMouse( int x )
	{
		//int width = r.getWidth();
		return 0;
	}
	
	public void updateVisibleItems()
	{
		visible.clear();
		for( int i = 0; i < items.size(); i++ )
		{
			Item item = items.get( i );
			if( item.isVisible( firstDate, lastDate ) && item.getPriority() >= priority && isTagged( item ) )
			{
				visible.add( createRenderer( item ) );
			}
		}
	}

	public void add( Item item ) 
	{
		items.add( item );
		if( item.isVisible( firstDate, lastDate ) )
		{
			visible.add( createRenderer( item ) );
		}
	}
	
	public ItemRenderer createRenderer( Item item )
	{
		if( item instanceof Event )
		{
			return new EventRenderer( (Event)item );
		}
		else if( item instanceof Span )
		{
			return new SpanRenderer( (Span)item );
		}
		return null;
	}

	public void clear() 
	{
		items.clear();
		visible.clear();
		firstDate = new TDate( 1990, 1, 1 );
		lastDate = new TDate( 2010, 1, 1 );
		tp = new TimelinePreferences();
	}

	public Item getItem( int x, int y ) 
	{
		for( int i = 0; i < visible.size(); i++ )
		{
			ItemRenderer it = visible.get( i );
			if( it.getX() < x && it.getX()+it.getWidth() > x && it.getY() < y && it.getY()+it.getHeight() > y )
			{
				return it.item;
			}
		}
		return null;
	}
	
	public static ArrayList<String> breakIntoLines( String s, int charLimit )
	{
		ArrayList<String> lines = new ArrayList<String>();
		char[] chars = s.toCharArray();
        boolean endOfString = false;
        int start = 0;
        int end = start;
        while(start < chars.length-1) {
            int charCount = 0;
            int lastSpace = 0;
            while( charCount < charLimit ) {
                if(chars[charCount+start] == ' ') {
                    lastSpace = charCount;
                }
                charCount++;
                if(charCount+start == s.length()) {
                    endOfString = true;
                    break;
                }
            } 
            end = endOfString ? s.length()
                              : (lastSpace > 0) ? lastSpace+start : charCount+start;
            lines.add( s.substring(start, end ) );
            start = end+1;
        }
        
        return lines;
	}
	
	public String toXml()
	{
		String text = "<?xml version=\"1.0\"?>\n";
		text += "<timeline>\n";
		Collections.sort( items );
		for( int i = 0; i < items.size(); i++ )
		{
			Item item = items.get( i );
			if( item instanceof Event )
			{
				text += "<item type=\"Event\">\n";
				Event e = (Event)item;
				text += "<name>" + e.name + "</name>\n";
				text += "<date>" + e.date.toStringSlashed() + "</date>\n";
				String loc = e.getData( "loc" );
				String link = e.getData( "link" );
				String desc = e.getData( "desc" );
				if( loc != null && !loc.equals( "" ) ) text += "<location>" + loc + "</location>\n";
				if( link != null && !link.equals( "" ) ) text += "<link>" + link + "</link>\n";
				if( desc != null && !desc.equals( "" ) ) text += "<description>" + desc + "</description>\n";
				
				text += "</item>\n";
			}
			else if( item instanceof Span )
			{
				text += "<item type=\"Span\">\n";
				Span e = (Span)item;
				text += "<name>" + e.name + "</name>\n";
				text += "<start>" + e.start.toStringSlashed() + "</start>\n";
				text += "<end>" + e.end.toStringSlashed() + "</end>\n";
				String loc = e.getData( "loc" );
				String link = e.getData( "link" );
				String desc = e.getData( "desc" );
				if( loc != null && !loc.equals( "" ) ) text += "<location>" + loc + "</location>\n";
				if( link != null && !link.equals( "" ) ) text += "<link>" + link + "</link>\n";
				if( desc != null && !desc.equals( "" ) ) text += "<description>" + desc + "</description>\n";
				
				text += "</item>\n";
			}
		}
		
		text += "</timeline>\n";
		return text;
	}
	
	public String toText()
	{
		String text = "";
		Collections.sort( items );
		for( int i = 0; i < items.size(); i++ )
		{
			Item item = items.get( i );
			if( item instanceof Event )
			{
				Event e = (Event)item;
				text += e.name + "\n";
				text += e.date.toString() + "\n";
				String loc = e.getData( "loc" );
				String link = e.getData( "link" );
				String desc = e.getData( "desc" );
				if( loc != null && !loc.equals( "" ) ) text += loc + "\n";
				if( link != null && !link.equals( "" ) ) text += link + "\n";
				if( desc != null && !desc.equals( "" ) ) text += desc + "\n";
				text += "\n\n";
			}
			else if( item instanceof Span )
			{
				Span e = (Span)item;
				text += e.name + "\n";
				text += e.start.toString() + "\n";
				text += e.end.toString() + "\n";
				String loc = e.getData( "loc" );
				String link = e.getData( "link" );
				String desc = e.getData( "desc" );
				if( loc != null && !loc.equals( "" ) ) text += loc + "\n";
				if( link != null && !link.equals( "" ) ) text += link + "\n";
				if( desc != null && !desc.equals( "" ) ) text += desc + "\n";
				text += "\n\n";
			}
		}
		
		return text;
	}

	public void setCenter( TDate center )
	{
		float diff = lastDate.monthDiff( firstDate );
		diff /= 2;
		firstDate = center.addMonths( (int) -diff );
		lastDate = center.addMonths( (int) diff );
	}
	
	public Item getItemByName( String name )
	{
		for( int i = 0; i < items.size(); i++ )
		{
			if( items.get( i ).getName().equalsIgnoreCase( name ) )
			{
				return items.get( i );
			}
		}
		return null;
	}
	
	public void updateNotesFromTimeline()
	{
		StringBuilder sb = new StringBuilder();
		String[] oldLines = notes.split( "\n" );
		Item current = null;
		ArrayList<Item> handled = new ArrayList<Item>();
		for( int i = 0; i < oldLines.length; i++ )
		{
			//Not current adding to an item
			if( current == null )
			{
				String trimmedLine = oldLines[i].trim();
				//Found an item
				if( trimmedLine.startsWith( "*" ) )
				{
					int indexOfStar = trimmedLine.indexOf( '*' );
					String name = trimmedLine.substring( indexOfStar+1, trimmedLine.length() ).trim();
					//Find name of item
					current = getItemByName( name );
					//If Item doesn't exist, Ignore
					if( current == null )
					{
						sb.append( oldLines[i] + "\n" );
						while( true )
						{
							i++;
							sb.append( oldLines[i] + "\n" );
							if( oldLines[i].trim().startsWith( "*" ) )
							{
								break;
							}
						}
					}
					else
					{
						handled.add( current );
						sb.append( itemToNotes( current ) );
						//Get rid of old stuff
						while( true )
						{
							i++;
							if( oldLines[i].trim().startsWith( "*" ) )
							{
								break;
							}
						}
						current = null;
					}
				}
				else
				{
					sb.append( oldLines[i] + "\n" );
				}
			}
		}
		
		for( int i = 0; i < items.size(); i++ )
		{
			if( !handled.contains( items.get( i ) ) )
			{
				sb.append( itemToNotes( items.get( i ) ) );
				sb.append( "\n" );
			}
		}
		
		notes = sb.toString();
	}
	
	public void updateTimelineFromNotes()
	{
		items.clear();
		String[] newNotes = notes.split( "\n" );
		for( int i = 0; i < newNotes.length; i++ )
		{
			String trimmedLine = newNotes[i].trim();
			if( trimmedLine.startsWith( "*" ) )
			{
				boolean failed = false;
				int indexOfStar = trimmedLine.indexOf( '*' );
				String name = trimmedLine.substring( indexOfStar+1, trimmedLine.length() ).trim();
				Item current = getItemByName( name );
				if( current == null )
				{
					//New Item, find if span or event
					i++;
					Item newI = null;
					String date = newNotes[i].trim();
					if( date.contains( "-" ) )
					{
						String[] dates = date.split( "-" );
						//Span
						try
						{
						newI = new Span( name, new TDate( dates[0].trim() ), new TDate( dates[1].trim() ) );
						} catch( Exception ex )
						{
							failed = true;
						}
					}
					else
					{
						//Event
						try
						{
						newI = new Event( name, new TDate( date ) );
						} catch( Exception ex )
						{
							failed = true;
						}
					}
					if( !failed )
					{
						while( true )
						{
							i++;
							String line = newNotes[i].trim();
							if( line.startsWith( "Location:" ) )
							{
								newI.setData( "loc", line.split( ":", 2 )[1].trim() );
							} else if( line.startsWith( "Priority:" ) )
							{
								try {
								newI.setPriority( Integer.parseInt( line.split( ":", 2 )[1].trim() ) );
								} catch( Exception ex ) {}
							} else if( line.startsWith( "Tags:" ) )
							{
								String[] tags = line.split( ":", 2 )[1].trim().split( "," );
								newI.getTags().clear();
								for( int j = 0; j < tags.length; j++ )
								{
									newI.getTags().add( tags[j] );
								}
							} else if( line.startsWith( "Link:" ) )
							{
								newI.setData( "link", line.split( ":", 2 )[1].trim() );
							} else if( line.startsWith( "*" ) )
							{
								break;
							} else
							{
								String desc = line;
								i++;
								while( true )
								{
									if( newNotes[i].trim().startsWith( "*" ) )
									{
										break;
									}
									desc += newNotes[i] + "\n";
									i++;
								}
								newI.setData( "desc", desc );
								break;
							}
						}
						items.add( newI );
					}
					else
					{
						//Fail, get to next *
						while( true )
						{
							i++;
							if( newNotes[i].trim().startsWith( "*" ) )
							{
								break;
							}
						}
					}
					
				}
				else
				{
					//Old item, update item from notes
				}
			}
		}
	}
	
	public String itemToNotes( Item current )
	{
		StringBuilder sb = new StringBuilder();
		sb.append( "* " + current.getName() + "\n" ); //Name
		if( current instanceof Span )
		{
			Span cSpan = (Span)current;
			sb.append( cSpan.start.toString() + " - " + cSpan.end.toString() + "\n" );
		}
		else
		{
			sb.append( current.getCenter().toString() + "\n" );
		}
		if( current.getPriority() != 0 )
		{
			sb.append( "Priority: " + current.getPriority() + "\n" );
		}
		if( current.getTags().size() > 0 && !current.getTags().get( 0 ).trim().equals( "" ) )
		{
			sb.append( "Tags: " );
			for( int j = 0; j < current.getTags().size(); j++ )
			{
				sb.append( current.getTags().get( j ) );
				if( j < current.getTags().size()-1 )
				{
					sb.append( ", " );
				}
			}
			sb.append( "\n" );
		}
		ArrayList<DoubleString> data = current.getData();
		for( DoubleString ds : data )
		{
			if( ds.a.equals( "link" ) )
			{
				if( !ds.b.trim().equals( "" ) )
					sb.append( "Link: " + ds.b + "\n" );
			}
			else if( ds.a.equals( "loc" ) )
			{
				if( !ds.b.trim().equals( "" ) )
					sb.append( "Location: " + ds.b + "\n" );
			}
			else if( ds.a.equals( "desc" ) )
			{
				if( !ds.b.trim().equals( "" ) )
					sb.append( ds.b + "\n" );
			}
		}
		sb.append( "*\n" );
		return sb.toString();
	}
}
