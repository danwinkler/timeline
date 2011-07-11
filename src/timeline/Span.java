package timeline;

import java.util.ArrayList;

import com.phyloa.dlib.renderer.Renderer;
import com.phyloa.timeline.RenderLines;
import com.phyloa.timeline.Timeline;

public class Span implements Item
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public TDate start;
	public TDate end;
	public ArrayList<String> tags = new ArrayList<String>();
	public ArrayList<DoubleString> data = new ArrayList<DoubleString>();
	public int priority;
	
	public Span( String name, TDate start, TDate end )
	{
		this.name = name;
		this.start = start;
		this.end = end;
	}
	
	public boolean isVisible( TDate d, float zoom ) 
	{
		return d.monthDiff( start )+zoom/2 > 0 && d.monthDiff( end )-zoom/2 < 0;
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
		rl.add( "Started: " + start.toString() );
		rl.add( "Ended: " + end.toString() );
		
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
		if( description != null )
		{
			ArrayList<String> lines = Timeline.breakIntoLines( description, 40 );
			for( int i = 0; i < Math.min( lines.size(), 11 ); i++ )
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
		int year = start.getYear() + end.getYear();
		year /= 2;
		return new TDate( year, 0, 0 );
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
