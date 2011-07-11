package com.phyloa.timeline;

import java.util.ArrayList;

import com.phyloa.dlib.renderer.Renderer;

public class RenderLines 
{
	ArrayList<String> lines = new ArrayList<String>();
	int separation;
	int x;
	int y;
	
	public RenderLines( int x, int y, int separation )
	{
		this.x = y;
		this.y = y;
		this.separation = separation;
	}
	
	public void add( String s )
	{
		lines.add( s );
	}
	
	public void render( Renderer r )
	{
		for( int i = 0; i < lines.size(); i++ )
		{
			r.text( lines.get( i ), x, y + separation * i );
		}
	}

	public void render( Renderer r, int maxLength )
	{
		for( int i = 0; i < Math.min( lines.size(), maxLength ); i++ )
		{
			r.text( lines.get( i ), x, y + separation * i );
		}
	}
}
