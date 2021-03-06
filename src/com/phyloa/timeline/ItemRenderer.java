package com.phyloa.timeline;

import timeline.Item;

import com.phyloa.dlib.renderer.Renderer;

public abstract class ItemRenderer implements Comparable<ItemRenderer>
{
	public abstract int getWidth();
	public abstract int getX();
	public abstract int getHeight();
	public abstract int getY();
	
	public abstract boolean getPlaced();
	
	public abstract void place( Timeline line, TimelinePreferences tp );
	public abstract void render( Renderer r, Item selected, Item hover, TimelinePreferences tp );
	
	public Item item;
	
	public int compareTo( ItemRenderer i ) 
	{
		return i.getY() < this.getY() ? -1 : 1;
	}
}
