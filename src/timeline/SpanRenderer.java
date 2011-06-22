package timeline;

import com.phyloa.dlib.renderer.Renderer;

public class SpanRenderer extends ItemRenderer
{
	int x = -100;
	int y = -100;
	int width, height;
	
	boolean placed = false;
	
	Span e;
	
	public SpanRenderer( Span e )
	{
		this.e = e;
		item = e;
	}
	
	public void place( Timeline line )
	{
		y = 20;
		width = line.getDrawX( e.end ) - line.getDrawX( e.start );  
		x = line.getDrawX( e.start );
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
	
	public void render( Renderer r, Item select, Item hover )
	{
		boolean selected = select == e;
		boolean hovered = hover == e;
		r.color( selected ? 255 : 0, 0, 0 );
		r.line( x, y, x, y+height );
		r.line( x+width, y, x+width, y+height );
		r.line( x, y+(height/2), x+width, y+(height/2) );
		r.text( e.name, x+(width/2) - strLen( e.name )/2, y+(height/2)-1 );
		
		if( hovered )
		{
			r.color( 128, 128, 128, 128 );
			r.line( x, y+height, x, r.getHeight()-100 );
			r.line( x+width, y+height, x+width, r.getHeight()-100 );
		}
	}
	
	private int strLen( String s )
	{
		return s.length() * 7;
	}
	
	public int getHeight()
	{	
		return height;
	}
	
	public boolean getPlaced()
	{	
		return placed;
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
}
