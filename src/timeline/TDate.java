package timeline;

import java.io.Serializable;
import java.util.Date;

public class TDate implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8213223945996132255L;
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	
	public TDate( int year, int month, int day )
	{
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public TDate( int year, int month, int day, int hour, int minute, int second )
	{
		this( year, month, day );
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	
	public TDate()
	{
		year = 2000;
	}
	
	public float monthDiff( TDate d )
	{
		return ((year-d.year)*12) + (month-d.month) + ((day-d.day)*(1.f/30.f));
	}
	
	public String toString()
	{
		return month + "/" + day + "/" + year;
	}
	
	public int getYear()
	{
		return year;
	}

	public TDate addMonths( int i )
	{
		int nm = month + i;
		int ny = year;
		while( nm > 12 )
		{
			nm -= 12;
			ny++;
		}
		while( nm < 1 )
		{
			nm += 12;
			ny--;
		}
		return new TDate( ny, nm, day, hour, minute, second );
	}
	
	public TDate addDays( int i )
	{
		int nd = day + i;
		int nm = month;
		int ny = year;
		while( nd > getDaysInMonth( nm, ny ) )
		{
			nd -= getDaysInMonth( nm, ny );
			nm++;
			while( nm > 12 )
			{
				nm -= 12;
				ny++;
			}
		}
		while( nd < 1 )
		{
			nd += getDaysInMonth( nm, ny );
			nm--;
			while( nm < 1 )
			{
				nm += 12;
				ny--;
			}
		}
		return new TDate( ny, nm, nd, hour, minute, second );
	}
	
	public int getDaysInMonth( int month, int year )
	{
		switch( month )
		{
		case 1: return 31;
		case 2: return isLeapYear( year ) ? 29 : 28;
		case 3: return 31;
		case 4: return 30;
		case 5: return 31;
		case 6: return 30;
		case 7: return 31;
		case 8: return 31;
		case 9: return 30;
		case 10: return 31;
		case 11: return 30;
		case 12: return 31;
		default: return 30;
		}
	}
	
	public boolean isLeapYear( int year )
	{
		if( ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0) )
			return true;
		else
			return false;

	}
}
