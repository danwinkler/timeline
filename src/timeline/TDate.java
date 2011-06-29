package timeline;

import java.io.Serializable;

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
	
	@Override
	public String toString()
	{
		String str = String.valueOf( year );
		if( month != 0 )
		{
			if( day == 0 )
			{
				str = getNameOfMonth( month ) + " " + str;
			}
			else
			{
				str = getNameOfMonth( month ) + " " + day + ", " + str;
			}
		}
		return str;
	}
	
	public int getYear()
	{
		return year;
	}

	public int getMonth() 
	{
		return month;
	}

	public int getDay() 
	{
		return day;
	}

	public int getHour() 
	{
		return hour;
	}

	public int getMinute() 
	{
		return minute;
	}

	public int getSecond() 
	{
		return second;
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
	
	public static int getDaysInMonth( int month, int year )
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
	
	public static String getNameOfMonth( int month )
	{
		switch( month )
		{
		case 1: return "January";
		case 2: return "February";
		case 3: return "March";
		case 4: return "April";
		case 5: return "May";
		case 6: return "June";
		case 7: return "July";
		case 8: return "August";
		case 9: return "September";
		case 10: return "October";
		case 11: return "November";
		case 12: return "December";
		default: return "";
		}
	}
	
	public static boolean isLeapYear( int year )
	{
		if( ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0) )
			return true;
		else
			return false;

	}
	
	public boolean isBefore( TDate td )
	{
		if( year < td.getYear() )
		{
			return true;
		}
		else if( year == td.getYear() )
		{
			if( month < td.getMonth() )
			{
				return true;
			}
			else if( month == td.getMonth() )
			{
				if( day < td.getDay() )
				{
					return true;
				}
				else if( day == td.getDay() )
				{
					if( hour < td.getHour() )
					{
						return true;
					}
					else if( hour == td.getHour() )
					{
						if( minute < td.getMinute() )
						{
							return true;
						}
						else if( minute == td.getMinute() )
						{
							if( second < td.getSecond() )
							{
								return true;
							}
							else if( second == td.getSecond() )
							{
								return false;
							}
						}
					}
				}
			}
		}
		return false;
	}
}
