package com.phyloa.timeline;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import timeline.Item;

public class RightClickMenu implements ActionListener
{
	JPopupMenu popup;
	Component parent;
	TimelineRun callback;
	int x;
	int y;
	Item item;
	
	public RightClickMenu( Component parent, TimelineRun callback )
	{
		this.parent = parent;
		this.callback = callback;
	}
	
	public void show( int x, int y, Item i )
	{
		this.x = x;
		this.y = y;
		this.item = i;
		popup = new JPopupMenu();
		if( i != null )
		{
			JMenuItem edit = new JMenuItem( "Edit" );
			edit.setActionCommand( "edit" );
			edit.addActionListener( this );
			popup.add( edit );
			
			if( i.getData( "link" ) != null && !i.getData( "link" ).equals( "" ) )
			{
				JMenuItem link = new JMenuItem( "Visit Link" );
				link.setActionCommand( "link" );
				link.addActionListener( this );
				popup.add( link );
			}
			if( i.getData( "loc" ) != null && !i.getData( "loc" ).equals( "" ) )
			{
				JMenuItem loc = new JMenuItem( "View on Map" );
				loc.setActionCommand( "loc" );
				loc.addActionListener( this );
				popup.add( loc );
			}
		}
		
		JMenuItem adde = new JMenuItem( "Add Event" );
		adde.setActionCommand( "addevent" );
		adde.addActionListener( this );
		popup.add( adde );
		
		JMenuItem adds = new JMenuItem( "Add Span" );
		adds.setActionCommand( "addspan" );
		adds.addActionListener( this );
		popup.add( adds );
		
		popup.show( parent, x, y );
	}

	public void actionPerformed( ActionEvent e )
	{
		if( e.getActionCommand().equals( "edit" ) )
		{
			callback.editItem( item );
		}
		else if( e.getActionCommand().equals( "link" ) )
		{
			try {
				Desktop.getDesktop().browse( new URI( item.getData( "link" ) ) );
			} catch (IOException e1) {
			} catch (URISyntaxException e1) {
			}
		}
		else if( e.getActionCommand().equals( "loc" ) )
		{
			try {
				Desktop.getDesktop().browse( new URI( "http://maps.google.com/maps?q=" + item.getData( "loc" ).replaceAll( "\\s", "+" ) ) );
			} catch (IOException e1) {
			} catch (URISyntaxException e1) {
			}
		}
		else if( e.getActionCommand().equals( "addevent" ) )
		{
			callback.addEvent( null );
		} 
		else if( e.getActionCommand().equals( "addspan" ) )
		{
			callback.addSpan( null );
		}
	}
}
