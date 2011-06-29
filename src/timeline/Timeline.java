package timeline;

import java.awt.RenderingHints;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import com.phyloa.dlib.renderer.Graphics2DIRenderer;
import com.phyloa.dlib.renderer.Renderer;
import com.phyloa.dlib.util.KeyHandler;

public class Timeline 
{
	ArrayList<Item> items = new ArrayList<Item>();
	
	ArrayList<ItemRenderer> visible = new ArrayList<ItemRenderer>();
	
	Renderer r;
	
	KeyHandler k;
	
	TDate centerDate = new TDate();
	float zoom = 12;
	
	Item selected = null;
	Item hover = null;
	
	File file = null;
	
	int priority = 0;
	ArrayList<String> tags = new ArrayList<String>();
	
	public Timeline( Renderer r )
	{
		this.r = r;
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
			zoomIn( .01f );
		}
		if( k.down )
		{
			zoomOut( .01f );
		}
	}
	
	public void zoomIn( float amt )
	{
		zoom -= zoom * amt;
		updateVisibleItems();
	}
	
	public void zoomOut( float amt )
	{
		zoom += zoom * amt;
		updateVisibleItems();
	}
	
	public void scrollRight( float amt )
	{
		centerDate = centerDate.addDays( (int)(zoom * amt) + 1 );
		updateVisibleItems();
	}
	public void scrollLeft( float amt )
	{
		centerDate = centerDate.addDays( -((int)(zoom * amt) + 1) );
		updateVisibleItems();
	}
	
	public void render()
	{
		((Graphics2DIRenderer)r).g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		int width = r.getWidth();
		int height = r.getHeight();
		r.color( 255, 255, 255 );
		r.fillRect( 0, 0, width, height );
		
		int tempPriority = priority;
		while( tempPriority < 100 )
		{
			for( int i = 0; i < visible.size(); i++ )
			{
				ItemRenderer e = visible.get( i );
				if( !e.getPlaced() )
				{
					e.place( this );
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
		Collections.sort( visible );
		for( int i = 0; i < visible.size(); i++ )
		{
			ItemRenderer e = visible.get( i );
			e.render( r, selected, hover );
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
		r.color( 0, 0, 0 );
		//DRAW a base line
		r.line( 0, height - 100, width, height - 100 );
		//FIND distance between years in pixels
		float yearSeparation = width / (zoom/12.f);
		//CHOOSE year interval... LOL AT MY LOGIC
		int interval = yearSeparation < 20 ? yearSeparation < 10 ? yearSeparation < 5 ? yearSeparation < 2.5f ? yearSeparation < .5 ? yearSeparation < .2f ? yearSeparation < .1f ? yearSeparation < .05f ? yearSeparation < .02f ? yearSeparation < .01f ? 5000 : 2000 : 1000 : 500 : 200 : 100 : 25 : 10 : 5 : 2 : 1;
		//CHOOSE first year to draw
		int firstYear = (int) (centerDate.getYear() - Math.ceil((zoom/24.f)));
		//LOCK firstYear to interval
		while( firstYear % interval != 0 ) 
		{
			firstYear--;
		}
		//FIND pixel location of first year
		float firstDrawX = getDrawX( new TDate( firstYear, 1, 1 ) );
		//FIND last year to draw
		int lastYear = (int) (centerDate.getYear() + Math.ceil((zoom/24.f)));
		for( int i = firstYear; i <= lastYear; i+= interval )
		{
			r.pushMatrix();
			r.translate( firstDrawX + yearSeparation*(i-firstYear), height-100 );
			r.rotate( (float)Math.PI/2 );
			r.line( 0, 0, -20, 0 );
			r.text( i + "", 3, 4 );
			r.popMatrix();
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
		float dist = d.monthDiff( centerDate );
		dist += zoom/2;
		dist = dist / zoom;
		return (int) (dist * width);
	}
	
	public int getYearFromMouse( int x )
	{
		int width = r.getWidth();
		int diff = x - width;
		return 0;
	}

	public void setZoomYears( int i ) 
	{
		zoom = i*12;
	}
	
	public void updateVisibleItems()
	{
		visible.clear();
		for( int i = 0; i < items.size(); i++ )
		{
			Item item = items.get( i );
			if( item.isVisible( centerDate, zoom ) && item.getPriority() >= priority && isTagged( item ) )
			{
				visible.add( createRenderer( item ) );
			}
		}
	}

	public void add( Item item ) 
	{
		items.add( item );
		if( item.isVisible( centerDate, zoom ) )
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
		zoom = 25*12;
		centerDate = new TDate();
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
				text += "<date>" + e.date.toString() + "</date>\n";
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
				text += "<start>" + e.start.toString() + "</start>\n";
				text += "<end>" + e.end.toString() + "</end>\n";
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
}
