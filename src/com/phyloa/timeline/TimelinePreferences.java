package com.phyloa.timeline;

import java.awt.Color;
import java.io.Serializable;

public class TimelinePreferences implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color lineColor = Color.black;
	Color backgroundColor = Color.white;
	Color fillColor = Color.white;
	Color textColor = Color.black;
	Color selectColor = Color.red;
	
	float lineThickness = 2;
	boolean roundedCorners = true;
	float cornerRadius = 10;
	
	int boxMargins = 5;

	public TimelinePreferences()
	{
		
	}
}
