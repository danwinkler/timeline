package timeline;

import java.util.ArrayList;

import com.phyloa.dlib.renderer.KeyHandler;
import com.phyloa.dlib.renderer.Renderer;

public class Timeline 
{
	ArrayList<Item> items = new ArrayList<Item>();
	
	ArrayList<ItemRenderer> visible = new ArrayList<ItemRenderer>();
	
	Renderer r;
	
	KeyHandler k;
	
	TDate centerDate = new TDate();
	float zoom = 12;
	
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
			centerDate = centerDate.addDays( (int)(zoom * .1f) + 1 );
			updateVisibleItems();
		}
		if( k.left )
		{
			centerDate = centerDate.addDays( -((int)(zoom * .1f) + 1) );
			updateVisibleItems();
		}
		if( k.up )
		{
			zoom -= zoom * .01f;
			updateVisibleItems();
		}
		if( k.down )
		{
			zoom += zoom * .01f;
			updateVisibleItems();
		}
	}
	
	public void render()
	{
		int width = r.getWidth();
		int height = r.getHeight();
		r.color( 255, 255, 255 );
		r.fillRect( 0, 0, width, height );
		for( int i = 0; i < visible.size(); i++ )
		{
			ItemRenderer e = visible.get( i );
			if( !e.getPlaced() )
			{
				e.place( this );
			}
				e.render( r );
		}
		
		drawDateLine( r, width, height );
	}
	
	private void drawDateLine( Renderer r, int width, int height ) 
	{
		r.line( 0, height - 100, width, height - 100 );
		float yearSeparation = width / (zoom/12.f);
		int firstYear = (int) (centerDate.getYear() - Math.ceil((zoom/24.f)));
		float firstDrawX = getDrawX( new TDate( firstYear, 1, 1 ) );
		int lastYear = (int) (centerDate.getYear() + Math.ceil((zoom/24.f)));
		for( int i = firstYear; i <= lastYear; i++ )
		{
			r.pushMatrix();
			r.translate( firstDrawX + yearSeparation*(i-firstYear), height-100 );
			r.rotate( (float)Math.PI/2 );
			r.line( 0, 0, -20, 0 );
			r.text( i + "", 3, 4 );
			r.popMatrix();
		}
	}

	int getDrawX( TDate d )
	{
		int width = r.getWidth();
		float dist = d.monthDiff( centerDate );
		dist += zoom/2;
		dist = dist / zoom;
		return (int) (dist * width);
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
			if( item.isVisible( centerDate, zoom ) )
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
}
