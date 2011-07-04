package com.phyloa.timeline;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ColorDisplayer extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	public Color color;
	
	public ColorDisplayer( Color color )
	{
		super();
		this.color = color;
	}
	
	public ColorDisplayer( Color color, int width, int height )
	{
		super();
		this.color = color;
		this.setSize( width, height );
		this.setPreferredSize( new Dimension( width, height ) );
	}
	
	public void setColor( Color color )
	{
		this.color = color;
	}
	
	@Override
	public void paintComponent( Graphics g ) 
    {
        g.setColor( color );
        g.fillRect( 0, 0, getWidth(), getHeight() ); 
    }
}
