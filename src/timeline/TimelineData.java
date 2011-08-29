package timeline;

import java.io.Serializable;
import java.util.ArrayList;

import com.phyloa.timeline.TimelinePreferences;


public class TimelineData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TDate centerDate = new TDate();
	public TDate lastDate = new TDate();
	public float zoom = 12; //DEPRECATED
	public ArrayList<Item> items = new ArrayList<Item>();
	public TimelinePreferences tp;
	public String notes;
}
