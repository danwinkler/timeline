package timeline;

import com.phyloa.dlib.renderer.Renderer;

public interface ItemRenderer 
{
	public int getWidth();
	public int getX();
	public int getHeight();
	public int getY();
	
	public boolean getPlaced();
	
	public void place( Timeline line );
	public void render( Renderer r );
}
