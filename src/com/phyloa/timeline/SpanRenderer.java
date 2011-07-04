package com.phyloa.timeline;

import java.awt.FontMetrics;

import timeline.Item;
import timeline.Span;

import com.phyloa.dlib.renderer.Graphics2DIRenderer;
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
	
	@Override
	public void place( Timeline line, TimelinePreferences tp )
	{
		y = 20;
		width = line.getDrawX( e.end ) - line.getDrawX( e.start ); 
		x = line.getDrawX( e.start );
		height = 26;
		
		width *= ((float)e.priority / 100.f) + 1;
		height *= ((float)e.priority / 100.f) + 1;
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
	
	@Override
	public void render( Renderer r, Item select, Item hover, TimelinePreferences tp )
	{
		boolean selected = select == e;
		boolean hovered = hover == e;
		r.color( selected ? tp.selectColor : tp.lineColor );
		r.line( x, y, x, y+height );
		r.line( x+width, y, x+width, y+height );
		r.line( x, y+(height/2), x+width, y+(height/2) );
		r.color( selected ? tp.selectColor : tp.textColor );
		r.text( e.name, x+(width/2) - strLen( e.name, r )/2, y+(height/2)-1-tp.lineThickness );
		r.color( (e.priority / 100.f) * 255, 0, ((100-e.priority) / 100.f) * 255 );
		r.fillOval( x + (width / 2) - 4, y + (height / 2) + 2, 8, 8 );
		
		if( hovered )
		{
			r.color( 128, 128, 128, 128 );
			r.line( x, y+height, x, r.getHeight()-100 );
			r.line( x+width, y+height, x+width, r.getHeight()-100 );
		}
	}
	
	private int strLen( String s, Renderer r )
	{
		FontMetrics fm = ((Graphics2DIRenderer)r).g.getFontMetrics();
		return fm.stringWidth( s );
	}
	
	@Override
	public int getHeight()
	{	
		return height;
	}
	
	@Override
	public boolean getPlaced()
	{	
		return placed;
	}
	
	@Override
	public int getWidth()
	{	
		return width;
	}
	
	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}
}
