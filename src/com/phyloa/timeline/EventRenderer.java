package com.phyloa.timeline;

import java.awt.BasicStroke;
import java.awt.FontMetrics;

import timeline.Event;
import timeline.Item;

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
	
	@Override
	public void render( Renderer r, Item selected, Item hover, TimelinePreferences tp ) 
	{
		((Graphics2DIRenderer)r).g.setStroke( new BasicStroke( tp.lineThickness ) );
		boolean select = selected == e;
		boolean hovered = hover == e;
		r.pushMatrix();
			r.translate( x, y );
			r.color( tp.fillColor );
			if( tp.roundedCorners ) 
				r.fillRoundedRect( 0, 0, width, height, tp.cornerRadius, tp.cornerRadius ); 
			else 
				r.fillRect( 0, 0, width, height );
			r.color( (e.priority / 100.f) * 255, 0, ((100-e.priority) / 100.f) * 255 );
			r.fillOval( width - 5 - tp.boxMargins - tp.lineThickness, height - 5 - tp.boxMargins - tp.lineThickness, 8, 8 );
			r.color( select ? tp.selectColor : tp.textColor );
			r.pushMatrix();
				r.translate( tp.boxMargins, tp.boxMargins );
				r.text( e.name, 2, 11 );
				r.text( e.date.toString(), 2, 23 );
			r.popMatrix();
			r.color( select ? tp.selectColor : tp.lineColor );
			if( tp.roundedCorners ) 
				r.drawRoundedRect( 0, 0, width, height, tp.cornerRadius, tp.cornerRadius ); 
			else 
				r.drawRect( 0, 0, width, height );
		r.popMatrix();
		if( hovered )
		{
			r.color( 128, 128, 128, 128 );
			r.line( x+(width/2), y+height, x+(width/2), r.getHeight() - 100 );
		}
	}
	
	@Override
	public void place( Timeline line, TimelinePreferences tp )
	{
		y = 20;
		width = makeWidth( line, line.r.getWidth() ) + (tp.boxMargins * 2);
		x = line.getDrawX( e.date );
		x -= width/2;
		height = 26 + (tp.boxMargins * 2);
		
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
	
	@Override
	public int getHeight() 
	{
		return height;
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
	
	@Override
	public boolean getPlaced()
	{
		return placed;
	}
}
