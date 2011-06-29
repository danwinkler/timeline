package timeline;

import java.util.ArrayList;

import com.phyloa.dlib.renderer.Renderer;

import timeline.TDate;

public class Event implements Item
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	TDate date = new TDate();
	ArrayList<String> tags = new ArrayList<String>();
	ArrayList<DoubleString> data = new ArrayList<DoubleString>();
	int priority;
	
	public Event( String name )
	{
		this.name = name;
	}
	
	public Event( String name, TDate date )
	{
		this( name );
		this.date = date;
	}
	
	public Event( String name, int year, int month, int day )
	{
		this( name, new TDate( year, month, day ) );
	}
	
	public boolean isVisible( TDate d, float zoom ) 
	{
		return Math.abs( date.monthDiff( d ) ) < zoom/2;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public ArrayList<String> getTags()
	{
		return tags;
	}
	
	public void renderInfo( Renderer r )
	{
		r.color( 0, 0, 0 );
		RenderLines rl = new RenderLines( 0, 20, 14 );
		rl.add( name );
		rl.add( date.toString() );
		String link = getData( "link" );
		String loc = getData( "loc" );
		String description = getData( "desc" );
		if( link != null && !link.equals( "" ) )
		{
			rl.add( "Link: " + link );	
		}
		if( loc != null && !loc.equals( "" ) )
		{
			rl.add( "Location: " + loc );	
		}
		rl.add( "Priority: " + priority );
		if( tags.size() > 0 )
		{
			String tagsText = "";
			for( int i = 0; i < tags.size(); i++ )
			{
				tagsText += tags.get( i );
				if( i < tags.size() - 1 )
				{
					tagsText += ", ";
				}
			}
			rl.add( "Tags: " + tagsText );
		}
		if( description != null && !description.equals( "" ) )
		{
			ArrayList<String> lines = Timeline.breakIntoLines( description, 40 );
			for( int i = 0; i < lines.size(); i++ )
			{
				rl.add( lines.get( i ) );
			}
		}
		
		rl.render( r );
	}


	public ArrayList<DoubleString> getData() 
	{
		return data;
	}

	public String getData( String string ) 
	{
		for( int i = 0; i < data.size(); i++ )
		{
			DoubleString ds = data.get( i );
			if( ds.a.equals( string ) )
			{
				return ds.b;
			}
		}
		return null;
	}
	
	public void setData( String a, String b )
	{
		for( int i = 0; i < data.size(); i++ )
		{
			DoubleString ds = data.get( i );
			if( ds.a.equals( a ) )
			{
				ds.b = b;
				return;
			}
		}
		data.add( new DoubleString( a, b ) );
	}

	public TDate getCenter() 
	{
		return date;
	}

	public int compareTo( Item i ) 
	{
		if( getCenter().isBefore( i.getCenter() ) )
		{
			return 1;
		}
		return -1;
	}
}
