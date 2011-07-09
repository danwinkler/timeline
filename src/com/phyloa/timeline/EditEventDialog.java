package com.phyloa.timeline;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
import timeline.Event;
import timeline.TDate;

public class EditEventDialog implements ActionListener
{
	Event edit;
	String name;
	TDate date = new TDate();
	JDialog dialog;
	JTextField text;
	JTextField year;
	JTextField month;
	JTextField day;
	JTextField hour;
	JTextField minute;
	JTextField second;
	JTextField link;
	JTextField location;
	JTextArea description;
	JSlider priority;
	JTextField tags;
	JButton submit;
	JButton cancel;
	
	TimelineRun callback;
	
	public EditEventDialog( JFrame frame, TimelineRun callback )
	{
		this.callback = callback;
		
		dialog = new JDialog( frame, "Edit Event" );
		dialog.setPreferredSize( new Dimension( 350, 550 ) );
		dialog.setSize( 350, 550 );
		dialog.setResizable( false );
		
		dialog.getContentPane().setLayout( new MigLayout( "align center center") );
		
		text = new JTextField();
		JLabel textLabel = new JLabel( "Name of Event:" );
		textLabel.setLabelFor( text );
		dialog.getContentPane().add( textLabel, "align right" );
		dialog.getContentPane().add( text, "align left, wrap 10px, growx" );
		
		year = new JTextField();
		JLabel yearLabel = new JLabel( "Year:" );
		yearLabel.setLabelFor( year );
		dialog.getContentPane().add( yearLabel, "align right" );
		dialog.getContentPane().add( year, "align left, wrap, growx" );
		
		month = new JTextField();
		JLabel monthLabel = new JLabel( "Month:" );
		monthLabel.setLabelFor( month );
		dialog.getContentPane().add( monthLabel, "align right" );
		dialog.getContentPane().add( month, "align left, wrap, growx" );
		
		day = new JTextField();
		JLabel dayLabel = new JLabel( "Day:" );
		dayLabel.setLabelFor( day );
		dialog.getContentPane().add( dayLabel, "align right" );
		dialog.getContentPane().add( day, "align left, wrap, growx" );
		
		hour = new JTextField();
		JLabel hourLabel = new JLabel( "Hour:" );
		hourLabel.setLabelFor( hour );
		dialog.getContentPane().add( hourLabel, "align right" );
		dialog.getContentPane().add( hour, "align left, wrap, growx" );
		
		minute = new JTextField();
		JLabel minuteLabel = new JLabel( "Minute:" );
		minuteLabel.setLabelFor( minute );
		dialog.getContentPane().add( minuteLabel, "align right" );
		dialog.getContentPane().add( minute, "align left, wrap, growx" );
		
		second = new JTextField();
		JLabel secondLabel = new JLabel( "Second:" );
		secondLabel.setLabelFor( second );
		dialog.getContentPane().add( secondLabel, "align right" );
		dialog.getContentPane().add( second, "align left, wrap 10px, growx" );
		
		link = new JTextField();
		JLabel linkLabel = new JLabel( "Link:" );
		linkLabel.setLabelFor( link );
		dialog.getContentPane().add( linkLabel, "align right" );
		dialog.getContentPane().add( link, "align left, wrap, growx" );
		
		location = new JTextField();
		JLabel locationLabel = new JLabel( "Location:" );
		locationLabel.setLabelFor( location );
		dialog.getContentPane().add( locationLabel, "align right" );
		dialog.getContentPane().add( location, "align left, wrap, growx" );
		
		tags = new JTextField();
		tags.setToolTipText( "Tags are comma separated." );
		JLabel tagsLabel = new JLabel( "Tags:" );
		tagsLabel.setLabelFor( tags );
		dialog.getContentPane().add( tagsLabel, "align right" );
		dialog.getContentPane().add( tags, "align left, wrap 15px, growx" );
		
		priority = new JSlider( 0, 100 );
		priority.setMajorTickSpacing( 10 );
		priority.setMinorTickSpacing( 5 );
		priority.setSnapToTicks( true );
		priority.setPaintLabels( true );
		priority.setPaintTicks( true );
		priority.setToolTipText( "100 is the most important, 0 is the least." );
		JLabel priorityLabel = new JLabel( "Priority:" );
		priorityLabel.setLabelFor( priority );
		dialog.getContentPane().add( priorityLabel, "span, wrap, align center" );
		dialog.getContentPane().add( priority, "span, wrap 15px, align center" );
		
		description = new JTextArea( 20, 5 );
		description.setLineWrap( true );
		description.setWrapStyleWord( true );
		JScrollPane descScroll = new JScrollPane( description );
		descScroll.setMaximumSize( new Dimension( 400, 150 ) );
		descScroll.setBounds( 0, 0, 400, 100 );
		descScroll.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		descScroll.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
		JLabel descLabel = new JLabel( "Description:" );
		descLabel.setLabelFor( descScroll );
		dialog.getContentPane().add( descLabel, "span, wrap, align center" );
		dialog.getContentPane().add( descScroll, "span, wrap, align center, growx, wmin 300" );
		
		submit = new JButton( "Submit" );
		submit.addActionListener( this );
		submit.setActionCommand( "submit" );
		dialog.getContentPane().add( submit, "align right" );
		
		cancel = new JButton( "Cancel" );
		cancel.addActionListener( this );
		cancel.setActionCommand( "cancel" );
		dialog.getContentPane().add( cancel, "align left" );
	}
	
	public void showEventDialog( Event e, Integer yeari )
	{
		edit = e;
		if( edit != null )
		{
			name = edit.name;
			date = edit.date;
			text.setText( name );
			year.setText( date.getYear() + "" );
			month.setText( date.getMonth() + "" );
			day.setText( date.getDay() + "" );
			hour.setText( date.getHour() + "" );
			minute.setText( date.getMinute() + "" );
			second.setText( date.getSecond() + "" );
			link.setText( edit.getData( "link" ) );
			location.setText( edit.getData( "loc" ) );
			description.setText( edit.getData( "desc" ) );
			priority.setValue( edit.priority );
			String tagText = "";
			for( int i = 0; i < edit.tags.size(); i++ )
			{
				tagText += edit.tags.get( i );
				
				if( i < edit.tags.size() - 1 )
				{
					tagText += ",";
				}
			}
			tags.setText( tagText );
		}
		else if( yeari != null )
		{
			year.setText( yeari.toString() );
		}
		
		dialog.setVisible( true );
	}

	public void actionPerformed( ActionEvent e ) 
	{
		if( e.getActionCommand().equals( "submit" ) )
		{
			String etext = text.getText().trim();
			if( etext.isEmpty() ) 
			{
				JOptionPane.showMessageDialog( dialog, "Please enter a name for the event.", "Event Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			String eyear = year.getText().trim();
			if( eyear.isEmpty() ) 
			{
				JOptionPane.showMessageDialog( dialog, "Please enter a year for the event.", "Event Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			String emonth = month.getText().trim();
			String eday = day.getText().trim();
			String ehour = hour.getText().trim();
			String eminute = minute.getText().trim();
			String esecond = second.getText().trim();
			int iyear;
			try { iyear = Integer.parseInt( eyear ); } catch( NumberFormatException nfe ) { iyear = 0; }
			int imonth;
			try { imonth = Integer.parseInt( emonth ); } catch( NumberFormatException nfe ) { imonth = 0; }
			int iday;
			try { iday = Integer.parseInt( eday ); } catch( NumberFormatException nfe ) { iday = 0; }
			int ihour;
			try { ihour = Integer.parseInt( ehour ); } catch( NumberFormatException nfe ) { ihour = 0; }
			int iminute;
			try { iminute = Integer.parseInt( eminute ); } catch( NumberFormatException nfe ) { iminute = 0; }
			int isecond;
			try { isecond = Integer.parseInt( esecond ); } catch( NumberFormatException nfe ) { isecond = 0; }
			
			TDate date = new TDate( iyear, imonth, iday, ihour, iminute, isecond );
			String[] sTags = tags.getText().split(",");
			ArrayList<String> aTags = new ArrayList<String>();
			for( int i = 0; i < sTags.length; i++ )
			{
				aTags.add( sTags[i].trim() );
			}
			if( edit == null )
			{
				Event event = new Event( etext, date );
				event.setData( "link", link.getText().trim() );
				event.setData( "loc", location.getText().trim() );
				event.setData( "desc", description.getText().trim() );
				event.priority = priority.getValue();
				event.tags = aTags;
				callback.dialogCompleted( event, true );
			}
			else
			{
				edit.name = etext;
				edit.date = date;
				edit.setData( "link", link.getText().trim() );
				edit.setData( "loc", location.getText().trim() );
				edit.setData( "desc", description.getText().trim() );
				edit.priority = priority.getValue();
				edit.tags = aTags;
				callback.dialogCompleted( edit, false );
			}
		}
		
		dialog.setVisible( false );
		dialog.dispose();
	}
}
