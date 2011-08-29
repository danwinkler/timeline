package timeline;

import java.io.Serializable;
import java.util.ArrayList;

import com.phyloa.dlib.renderer.Renderer;

public interface Item extends Serializable, Comparable<Item>
{
	public boolean isVisible( TDate d, TDate lastDate );
	
	public String getName();
	public int getPriority();
	public ArrayList<String> getTags();
	public ArrayList<DoubleString> getData();
	public String getData( String string );
	public void setData( String a, String b );
	public void renderInfo( Renderer r );

	public TDate getCenter();

	public void setPriority( int priority );
}
