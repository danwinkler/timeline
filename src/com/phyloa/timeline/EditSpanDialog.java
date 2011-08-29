package com.phyloa.timeline;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

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
import timeline.Span;
import timeline.TDate;

public class EditSpanDialog implements ActionListener
{
	Span edit;
	String name;
	TDate start = new TDate();
	TDate end = new TDate();
	JDialog dialog;
	JTextField stext;
	JTextField syear;
	JTextField smonth;
	JTextField sday;
	JTextField shour;
	JTextField sminute;
	JTextField ssecond;
	JTextField fyear;
	JTextField fmonth;
	JTextField fday;
	JTextField fhour;
	JTextField fminute;
	JTextField fsecond;
	JTextField link;
	JTextField location;
	JTextArea description;
	JSlider priority;
	JTextField tags;
	JButton submit;
	JButton cancel;
	
	TimelineRun callback;
	
	public EditSpanDialog( JFrame frame, TimelineRun callback )
	{
		this.callback = callback;
		
		dialog = new JDialog( frame, "Edit Span" );
		dialog.setPreferredSize( new Dimension( 400, 550 ) );
		dialog.setSize( 400, 450 );
		
		dialog.getContentPane().setLayout( new MigLayout( "align center center" ) );
		
		stext = new JTextField();
		JLabel textLabel = new JLabel( "Name of Event:" );
		textLabel.setLabelFor( stext );
		dialog.getContentPane().add( textLabel, "align right" );
		dialog.getContentPane().add( stext, "align left, span 3, wrap 10px, growx" );
		
		syear = new JTextField( 9 );
		JLabel yearLabel = new JLabel( "Start Year:" );
		yearLabel.setLabelFor( syear );
		dialog.getContentPane().add( yearLabel, "align right" );
		dialog.getContentPane().add( syear, "align left" );
		
		fyear = new JTextField( 9 );
		JLabel fyearLabel = new JLabel( "End Year:" );
		fyearLabel.setLabelFor( syear );
		dialog.getContentPane().add( fyearLabel, "align right" );
		dialog.getContentPane().add( fyear, "align left, wrap" );
		
		smonth = new JTextField( 9 );
		JLabel monthLabel = new JLabel( "Start Month:" );
		monthLabel.setLabelFor( smonth );
		dialog.getContentPane().add( monthLabel, "align right" );
		dialog.getContentPane().add( smonth, "align left" );

		fmonth = new JTextField( 9 );
		JLabel fmonthLabel = new JLabel( "End Month:" );
		fmonthLabel.setLabelFor( fmonth );
		dialog.getContentPane().add( fmonthLabel, "align right" );
		dialog.getContentPane().add( fmonth, "align left, wrap" );
		
		sday = new JTextField( 9 );
		JLabel dayLabel = new JLabel( "Start Day:" );
		dayLabel.setLabelFor( sday );
		dialog.getContentPane().add( dayLabel, "align right" );
		dialog.getContentPane().add( sday, "align left" );

		fday = new JTextField( 9 );
		JLabel fdayLabel = new JLabel( "End Day:" );
		fdayLabel.setLabelFor( fday );
		dialog.getContentPane().add( fdayLabel, "align right" );
		dialog.getContentPane().add( fday, "align left, wrap" );
		
		shour = new JTextField( 9 );
		JLabel hourLabel = new JLabel( "Start Hour:" );
		hourLabel.setLabelFor( shour );
		dialog.getContentPane().add( hourLabel, "align right" );
		dialog.getContentPane().add( shour, "align left" );

		fhour = new JTextField( 9 );
		JLabel fhourLabel = new JLabel( "End Hour:" );
		fhourLabel.setLabelFor( fhour );
		dialog.getContentPane().add( fhourLabel, "align right" );
		dialog.getContentPane().add( fhour, "align left, wrap" );
		
		sminute = new JTextField( 9 );
		JLabel minuteLabel = new JLabel( "Start Minute:" );
		minuteLabel.setLabelFor( sminute );
		dialog.getContentPane().add( minuteLabel, "align right" );
		dialog.getContentPane().add( sminute, "align left" );

		fminute = new JTextField( 9 );
		JLabel fminuteLabel = new JLabel( "End Minute:" );
		fminuteLabel.setLabelFor( fminute );
		dialog.getContentPane().add( fminuteLabel, "align right" );
		dialog.getContentPane().add( fminute, "align left, wrap" );
		
		ssecond = new JTextField( 9 );
		JLabel secondLabel = new JLabel( "Start Second:" );
		secondLabel.setLabelFor( ssecond );
		dialog.getContentPane().add( secondLabel, "align right" );
		dialog.getContentPane().add( ssecond, "align left" );

		fsecond = new JTextField( 9 );
		JLabel fsecondLabel = new JLabel( "End Second:" );
		fsecondLabel.setLabelFor( fsecond );
		dialog.getContentPane().add( fsecondLabel, "align right" );
		dialog.getContentPane().add( fsecond, "align left, wrap 10px" );
		
		link = new JTextField();
		JLabel linkLabel = new JLabel( "Link:" );
		linkLabel.setLabelFor( link );
		dialog.getContentPane().add( linkLabel, "align right" );
		dialog.getContentPane().add( link, "align left, wrap, span 3, growx" );
		
		location = new JTextField();
		JLabel locationLabel = new JLabel( "Location:" );
		locationLabel.setLabelFor( location );
		dialog.getContentPane().add( locationLabel, "align right" );
		dialog.getContentPane().add( location, "align left, wrap, span 3, growx" );
		
		tags = new JTextField();
		tags.setToolTipText( "Tags are comma separated." );
		JLabel tagsLabel = new JLabel( "Tags:" );
		tagsLabel.setLabelFor( tags );
		dialog.getContentPane().add( tagsLabel, "align right" );
		dialog.getContentPane().add( tags, "align left, wrap 15px, span 3, growx" );
		
		priority = new JSlider( 0, 100 );
		priority.setMajorTickSpacing( 10 );
		priority.setMinorTickSpacing( 5 );
		priority.setSnapToTicks( true );
		priority.setPaintLabels( true );
		priority.setPaintTicks( true );
		priority.setToolTipText( "100 is the most important, 0 is the least." );
		JLabel priorityLabel = new JLabel( "Priority:" );
		priorityLabel.setLabelFor( priority );
		dialog.getContentPane().add( priorityLabel, "align center, wrap, span" );
		dialog.getContentPane().add( priority, "align center, wrap 15px, span" );
		
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
		dialog.getContentPane().add( descLabel, "span 4, wrap, align center" );
		dialog.getContentPane().add( descScroll, "span 4, wrap, align center, growx" );
		
		submit = new JButton( "Submit" );
		submit.addActionListener( this );
		submit.setActionCommand( "submit" );
		dialog.getContentPane().add( submit, "span 2, align right" );
		
		cancel = new JButton( "Cancel" );
		cancel.addActionListener( this );
		cancel.setActionCommand( "cancel" );
		dialog.getContentPane().add( cancel, "span 2, align left" );
		
		dialog.getContentPane().setFocusCycleRoot( true );
		Vector<Component> co = new Vector<Component>();
		co.add( stext );
		co.add( syear );
		co.add( fyear );
		co.add( smonth );
		co.add( fmonth );
		co.add( sday );
		co.add( fday );
		co.add( shour );
		co.add( fhour );
		co.add( sminute );
		co.add( fminute );
		co.add( ssecond );
		co.add( fsecond );
		co.add( link );
		co.add( location );
		co.add( tags );
		co.add( priority );
		co.add( description );
		co.add( submit );
		co.add( cancel );
		MyOwnFocusTraversalPolicy tp = new MyOwnFocusTraversalPolicy( co );
		dialog.getContentPane().setFocusTraversalPolicy( tp );
		
		dialog.pack();
	}
	
	public void showSpanDialog( Span e, Integer year )
	{
		edit = e;
		if( edit != null )
		{
			name = edit.name;
			stext.setText( name );
			start = edit.start;
			syear.setText( start.getYear() + "" );
			smonth.setText( start.getMonth() + "" );
			sday.setText( start.getDay() + "" );
			shour.setText( start.getHour() + "" );
			sminute.setText( start.getMinute() + "" );
			ssecond.setText( start.getSecond() + "" );
			end = edit.end;
			fyear.setText( end.getYear() + "" );
			fmonth.setText( end.getMonth() + "" );
			fday.setText( end.getDay() + "" );
			fhour.setText( end.getHour() + "" );
			fminute.setText( end.getMinute() + "" );
			fsecond.setText( end.getSecond() + "" );
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
		else if( year != null )
		{
			syear.setText( year.toString() );
		}
		
		dialog.setVisible( true );
	}

	public void actionPerformed( ActionEvent e ) 
	{
		if( e.getActionCommand().equals( "submit" ) )
		{
			String etext = stext.getText().trim();
			if( etext.isEmpty() ) 
			{
				JOptionPane.showMessageDialog( dialog, "Please enter a name for the Span.", "Span Error", JOptionPane.ERROR_MESSAGE );
				return; 
			}
			String eyear = syear.getText().trim();
			if( eyear.isEmpty() ) 
			{
				JOptionPane.showMessageDialog( dialog, "Please enter a star year for the Span.", "Span Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			String emonth = smonth.getText().trim();
			String eday = sday.getText().trim();
			String ehour = shour.getText().trim();
			String eminute = sminute.getText().trim();
			String esecond = ssecond.getText().trim();
			int iyear;
			try { iyear = Integer.parseInt( eyear ); } catch( NumberFormatException nfe ) { iyear = 0; }
			int imonth;
			try { imonth = Integer.parseInt( emonth ); } catch( NumberFormatException nfe ) { imonth = TDate.getMonthByName( emonth ); }
			int iday;
			try { iday = Integer.parseInt( eday ); } catch( NumberFormatException nfe ) { iday = 0; }
			int ihour;
			try { ihour = Integer.parseInt( ehour ); } catch( NumberFormatException nfe ) { ihour = 0; }
			int iminute;
			try { iminute = Integer.parseInt( eminute ); } catch( NumberFormatException nfe ) { iminute = 0; }
			int isecond;
			try { isecond = Integer.parseInt( esecond ); } catch( NumberFormatException nfe ) { isecond = 0; }
			
			String efyear = fyear.getText().trim();
			if( efyear.isEmpty() )
			{
				JOptionPane.showMessageDialog( dialog, "Please enter a end year for the Span.", "Span Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			String efmonth = fmonth.getText().trim();
			String efday = fday.getText().trim();
			String efhour = fhour.getText().trim();
			String efminute = fminute.getText().trim();
			String efsecond = fsecond.getText().trim();
			int ifyear;
			try { ifyear = Integer.parseInt( efyear ); } catch( NumberFormatException nfe ) { ifyear = 0; }
			int ifmonth;
			try { ifmonth = Integer.parseInt( efmonth ); } catch( NumberFormatException nfe ) { ifmonth = TDate.getMonthByName( efmonth ); }
			int ifday;
			try { ifday = Integer.parseInt( efday ); } catch( NumberFormatException nfe ) { ifday = 0; }
			int ifhour;
			try { ifhour = Integer.parseInt( efhour ); } catch( NumberFormatException nfe ) { ifhour = 0; }
			int ifminute;
			try { ifminute = Integer.parseInt( efminute ); } catch( NumberFormatException nfe ) { ifminute = 0; }
			int ifsecond;
			try { ifsecond = Integer.parseInt( efsecond ); } catch( NumberFormatException nfe ) { ifsecond = 0; }
			
			TDate startDate = new TDate( iyear, imonth, iday, ihour, iminute, isecond );
			TDate endDate = new TDate( ifyear, ifmonth, ifday, ifhour, ifminute, ifsecond );
			
			String[] sTags = tags.getText().split(",");
			ArrayList<String> aTags = new ArrayList<String>();
			for( int i = 0; i < sTags.length; i++ )
			{
				aTags.add( sTags[i].trim() );
			}
			
			if( edit == null )
			{
				Span span = new Span( etext, startDate, endDate );
				span.setData( "link", link.getText().trim() );
				span.setData( "loc", location.getText().trim() );
				span.setData( "desc", description.getText().trim() );
				span.priority = priority.getValue();
				span.tags = aTags;
				callback.dialogCompleted( span, true );
			}
			else
			{
				edit.start = startDate;
				edit.end = endDate;
				edit.name = etext;
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
