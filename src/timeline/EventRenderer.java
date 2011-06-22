package timeline;

import java.awt.FontMetrics;

import com.phyloa.dlib.renderer.Graphics2DIRenderer;
import com.phyloa.dlib.renderer.Renderer;

public class EventRenderer extends ItemRenderer
{
	int x = -100;
	int y = -100;
	int width = 0;
	int height = 0;
	
	Event e;
	
	boolean placed;
	
	public EventRenderer( Event e )
	{
		this.e = e;
		item = e;
	}
	
	public void render( Renderer r, Item selected, Item hover ) 
	{
		boolean select = selected == e;
		boolean hovered = hover == e;
		r.pushMatrix();
			r.translate( x, y );
			r.color( 255, 255, 255 );
			r.fillRect( 0, 0, width, height );
			r.color( select ? 255 : 0, 0, 0 );
			r.text( e.name, 2, 11 );
			r.text( e.date.toString(), 2, 23 );
			r.drawRect( 0, 0, width, height );
		r.popMatrix();
		r.line( x, y, x, r.getHeight() - 100 );
	}
	
	public void place( Timeline line )
	{
		y = 20;
		width = makeWidth( line, line.r.getWidth() );
		x = line.getDrawX( e.date );
		height = 26;
		while( true )
		{
			boolean found = true;
			for( int i = 0; i < line.visible.size(); i++ )
			{
				ItemRenderer er = line.visible.get( i );
				if( er != this )
				{
					if( er.getX() < x + width && er.getX() + er.getWidth() > x )
					{
						if( er.getY() < y + height && er.getY() + er.getHeight() > y )
						{
							found = false;
							y += 28;
							break;
						}
					}
				}
			}
			if( found )
				break;
		}
		placed = true;
	}
	
	private int makeWidth( Timeline line, int dx )
	{
		Graphics2DIRenderer g2dr = ((Graphics2DIRenderer)line.r);
		FontMetrics fm = g2dr.g.getFontMetrics();
		return Math.max( fm.stringWidth( e.name ) + 5, fm.stringWidth( e.date.toString() ) + 5 );	
	}
	
	private int strLen( String s )
	{
		return s.length() * 7;
	}
	
	public int getHeight() 
	{
		return height;
	}

	public int getWidth() 
	{
		return width;
	}

	public int getX() 
	{
		return x;
	}
	
	public int getY() 
	{
		return y;
	}
	
	public boolean getPlaced()
	{
		return placed;
	}
}
