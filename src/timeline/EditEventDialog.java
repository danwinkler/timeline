package timeline;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

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
	JButton submit;
	JButton cancel;
	
	TimelineRun callback;
	
	public EditEventDialog( JFrame frame, TimelineRun callback )
	{
		this.callback = callback;
		
		dialog = new JDialog( frame, "Edit Event" );
		dialog.setPreferredSize( new Dimension( 520, 550 ) );
		dialog.setSize( 520, 400 );
		
		dialog.getContentPane().setLayout( new BoxLayout( dialog.getContentPane(), BoxLayout.PAGE_AXIS ) );
		
		text = new JTextField( 50 );
		JLabel textLabel = new JLabel( "Name of Event:" );
		textLabel.setLabelFor( text );
		JPanel textPanel = new JPanel();
		textPanel.add( textLabel, BorderLayout.WEST );
		textPanel.add( text, BorderLayout.EAST );
		dialog.getContentPane().add( textPanel );
		
		year = new JTextField( 9 );
		JLabel yearLabel = new JLabel( "Year:" );
		yearLabel.setLabelFor( year );
		JPanel yearPanel = new JPanel();
		yearPanel.add( yearLabel, BorderLayout.WEST );
		yearPanel.add( year, BorderLayout.EAST );
		dialog.getContentPane().add( yearPanel );
		
		month = new JTextField( 9 );
		JLabel monthLabel = new JLabel( "Month:" );
		monthLabel.setLabelFor( month );
		JPanel monthPanel = new JPanel();
		monthPanel.add( monthLabel, BorderLayout.WEST );
		monthPanel.add( month, BorderLayout.EAST );
		dialog.getContentPane().add( monthPanel );
		
		day = new JTextField( 9 );
		JLabel dayLabel = new JLabel( "Day:" );
		dayLabel.setLabelFor( day );
		JPanel dayPanel = new JPanel();
		dayPanel.add( dayLabel, BorderLayout.WEST );
		dayPanel.add( day, BorderLayout.EAST );
		dialog.getContentPane().add( dayPanel );
		
		hour = new JTextField( 9 );
		JLabel hourLabel = new JLabel( "Hour:" );
		hourLabel.setLabelFor( hour );
		JPanel hourPanel = new JPanel();
		hourPanel.add( hourLabel, BorderLayout.WEST );
		hourPanel.add( hour, BorderLayout.EAST );
		dialog.getContentPane().add( hourPanel );
		
		minute = new JTextField( 9 );
		JLabel minuteLabel = new JLabel( "Minute:" );
		minuteLabel.setLabelFor( minute );
		JPanel minutePanel = new JPanel();
		minutePanel.add( minuteLabel, BorderLayout.WEST );
		minutePanel.add( minute, BorderLayout.EAST );
		dialog.getContentPane().add( minutePanel );
		
		second = new JTextField( 9 );
		JLabel secondLabel = new JLabel( "Second:" );
		secondLabel.setLabelFor( second );
		JPanel secondPanel = new JPanel();
		secondPanel.add( secondLabel, BorderLayout.WEST );
		secondPanel.add( second, BorderLayout.EAST );
		dialog.getContentPane().add( secondPanel );
		
		link = new JTextField( 50 );
		JLabel linkLabel = new JLabel( "Link:" );
		linkLabel.setLabelFor( link );
		JPanel linkPanel = new JPanel();
		linkPanel.add( linkLabel, BorderLayout.WEST );
		linkPanel.add( link, BorderLayout.EAST );
		dialog.getContentPane().add( linkPanel );
		
		location = new JTextField( 50 );
		JLabel locationLabel = new JLabel( "Location:" );
		locationLabel.setLabelFor( location );
		JPanel locationPanel = new JPanel();
		locationPanel.add( locationLabel, BorderLayout.WEST );
		locationPanel.add( location, BorderLayout.EAST );
		dialog.getContentPane().add( locationPanel );
		
		description = new JTextArea( 20, 5 );
		description.setLineWrap( true );
		description.setWrapStyleWord( true );
		JScrollPane descScroll = new JScrollPane( description );
		descScroll.setBounds( 0, 0, 400, 100 );
		descScroll.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		JLabel descLabel = new JLabel( "Description:" );
		descLabel.setLabelFor( descScroll );
		JPanel descPanel = new JPanel();
		descPanel.setLayout( new BoxLayout( descPanel, BoxLayout.LINE_AXIS ) );
		descPanel.add( descLabel );
		descPanel.add( descScroll );
		dialog.getContentPane().add( descPanel );
		
		JPanel buttonPanel = new JPanel();
		submit = new JButton( "Submit" );
		submit.addActionListener( this );
		submit.setActionCommand( "submit" );
		buttonPanel.add( submit );
		
		cancel = new JButton( "Cancel" );
		cancel.addActionListener( this );
		cancel.setActionCommand( "cancel" );
		dialog.getContentPane().add( cancel );
		buttonPanel.add( cancel );
		dialog.getContentPane().add( buttonPanel );
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
			if( etext.isEmpty() ) return;
			String eyear = year.getText().trim();
			if( eyear.isEmpty() ) return;
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
			if( edit == null )
			{
				Event event = new Event( etext, date );
				event.setData( "link", link.getText().trim() );
				event.setData( "loc", location.getText().trim() );
				event.setData( "desc", description.getText().trim() );
				callback.dialogCompleted( event, true );
			}
			else
			{
				edit.name = etext;
				edit.date = date;
				edit.setData( "link", link.getText().trim() );
				edit.setData( "loc", location.getText().trim() );
				edit.setData( "desc", description.getText().trim() );
				callback.dialogCompleted( edit, false );
			}
		}
		
		dialog.setVisible( false );
		dialog.dispose();
	}
}
