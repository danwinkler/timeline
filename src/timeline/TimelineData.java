package timeline;

import java.io.Serializable;
import java.util.ArrayList;

public class TimelineData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	TDate centerDate = new TDate();
	float zoom = 12;
	ArrayList<Item> items = new ArrayList<Item>();
}
