package timeline;

import java.io.Serializable;
import java.util.ArrayList;

public interface Item extends Serializable
{
	public boolean isVisible( TDate d, float zoom );
	
	public int getPriority();
	public ArrayList<String> getTags();
}
