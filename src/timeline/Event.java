package timeline;

import java.util.ArrayList;

import timeline.TDate;

public class Event implements Item
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	TDate date = new TDate();
	ArrayList<String> tags = new ArrayList<String>();
	int priority;
	
	public Event( String name )
	{
		this.name = name;
	}
	
	public Event( String name, TDate date )
	{
		this( name );
		this.date = date;
	}
	
	public Event( String name, int year, int month, int day )
	{
		this( name, new TDate( year, month, day ) );
	}
	
	public boolean isVisible( TDate d, float zoom ) 
	{
		return Math.abs( date.monthDiff( d ) ) < zoom/2;
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
