package timeline;

import java.util.ArrayList;

public class Span implements Item 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	TDate start;
	TDate end;
	ArrayList<String> tags = new ArrayList<String>();
	int priority;
	
	public Span( String name, TDate start, TDate end )
	{
		this.name = name;
		this.start = start;
		this.end = end;
	}
	
	public boolean isVisible( TDate d, float zoom ) 
	{
		return d.monthDiff( start )+zoom/2 > 0 && d.monthDiff( end )-zoom/2 < 0;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public ArrayList<String> getTags()
	{
		return tags;
	}
}
