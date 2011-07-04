package com.phyloa.timeline;

import timeline.Event;
import timeline.Span;
import timeline.TDate;

import com.phyloa.dlib.renderer.Graphics2DRenderer;

public class TimelineTest extends Graphics2DRenderer
{
	Timeline t;
	
	@Override
	public void initialize() 
	{
		size( 800, 600 );
		
		t = new Timeline( this );
		t.setKeyHandler( k );
		
		t.setZoomYears( 25 );
		
		t.add( new Event( "Dan's Birthday", 1990, 04, 12 ) );
		t.add( new Event( "Zoe's Birthday", 1990, 03, 31 ) );
		t.add( new Event( "World Trade Center", 2001, 9, 11 ) );
		t.add( new Event( "John's Birthday", 1995, 3, 22 ) );
		t.add( new Event( "Josh's Birthday", 1992, 9, 22 ) );
		
		t.add( new Event( "New Millennium's Eve", 1999, 12, 31 ) );
		t.add( new Event( "Columbia Disaster", 2003, 2, 1 ) );
		t.add( new Event( "2004 Tsunami", 2004, 12, 26 ) );
		
		t.add( new Span( "George H. Bush's Presidency", new TDate( 2001, 1, 20 ), new TDate( 2009, 1, 20 ) ) );
		t.add( new Span( "Bill Clinton's Presidency", new TDate( 1993, 1, 20 ), new TDate( 2001, 1, 20 ) ) );
	}

	@Override
	public void update() 
	{
		t.update();
		t.render();
	}
	
	public static void main( String[] args )
	{
		TimelineTest t = new TimelineTest();
		t.begin();
	}

}
