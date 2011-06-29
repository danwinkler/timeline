package timeline;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

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
		dialog.setPreferredSize( new Dimension( 520, 550 ) );
		dialog.setSize( 520, 450 );
		
		dialog.getContentPane().setLayout( new BoxLayout( dialog.getContentPane(), BoxLayout.PAGE_AXIS ) );
		
		stext = new JTextField( 50 );
		JLabel textLabel = new JLabel( "Name of Event:" );
		textLabel.setLabelFor( stext );
		JPanel textPanel = new JPanel();
		textPanel.add( textLabel, BorderLayout.WEST );
		textPanel.add( stext, BorderLayout.EAST );
		dialog.getContentPane().add( textPanel );
		
		JPanel datePanel = new JPanel( new GridLayout( 0, 2 ) );
		
		JPanel startDatePanel = new JPanel( new GridLayout( 0, 1 ) );
		
		syear = new JTextField( 9 );
		JLabel yearLabel = new JLabel( "Start Year:" );
		yearLabel.setLabelFor( syear );
		JPanel yearPanel = new JPanel();
		yearPanel.add( yearLabel );
		yearPanel.add( syear );
		startDatePanel.add( yearPanel );
		
		smonth = new JTextField( 9 );
		JLabel monthLabel = new JLabel( "Start Month:" );
		monthLabel.setLabelFor( smonth );
		JPanel monthPanel = new JPanel();
		monthPanel.add( monthLabel, BorderLayout.WEST );
		monthPanel.add( smonth, BorderLayout.EAST );
		startDatePanel.add( monthPanel );
		
		sday = new JTextField( 9 );
		JLabel dayLabel = new JLabel( "Start Day:" );
		dayLabel.setLabelFor( sday );
		JPanel dayPanel = new JPanel();
		dayPanel.add( dayLabel, BorderLayout.WEST );
		dayPanel.add( sday, BorderLayout.EAST );
		startDatePanel.add( dayPanel );
		
		shour = new JTextField( 9 );
		JLabel hourLabel = new JLabel( "Start Hour:" );
		hourLabel.setLabelFor( shour );
		JPanel hourPanel = new JPanel();
		hourPanel.add( hourLabel, BorderLayout.WEST );
		hourPanel.add( shour, BorderLayout.EAST );
		startDatePanel.add( hourPanel );
		
		sminute = new JTextField( 9 );
		JLabel minuteLabel = new JLabel( "Start Minute:" );
		minuteLabel.setLabelFor( sminute );
		JPanel minutePanel = new JPanel();
		minutePanel.add( minuteLabel, BorderLayout.WEST );
		minutePanel.add( sminute, BorderLayout.EAST );
		startDatePanel.add( minutePanel );
		
		ssecond = new JTextField( 9 );
		JLabel secondLabel = new JLabel( "Start Second:" );
		secondLabel.setLabelFor( ssecond );
		JPanel secondPanel = new JPanel();
		secondPanel.add( secondLabel, BorderLayout.WEST );
		secondPanel.add( ssecond, BorderLayout.EAST );
		startDatePanel.add( secondPanel );
		
		datePanel.add( startDatePanel );
		JPanel endDatePanel = new JPanel( new GridLayout( 0, 1 ) );
		
		fyear = new JTextField( 9 );
		JLabel fyearLabel = new JLabel( "End Year:" );
		fyearLabel.setLabelFor( syear );
		JPanel fyearPanel = new JPanel();
		fyearPanel.add( fyearLabel, BorderLayout.WEST );
		fyearPanel.add( fyear, BorderLayout.EAST );
		endDatePanel.add( fyearPanel );
		
		fmonth = new JTextField( 9 );
		JLabel fmonthLabel = new JLabel( "End Month:" );
		fmonthLabel.setLabelFor( fmonth );
		JPanel fmonthPanel = new JPanel();
		fmonthPanel.add( fmonthLabel, BorderLayout.WEST );
		fmonthPanel.add( fmonth, BorderLayout.EAST );
		endDatePanel.add( fmonthPanel );
		
		fday = new JTextField( 9 );
		JLabel fdayLabel = new JLabel( "End Day:" );
		fdayLabel.setLabelFor( fday );
		JPanel fdayPanel = new JPanel();
		fdayPanel.add( fdayLabel, BorderLayout.WEST );
		fdayPanel.add( fday, BorderLayout.EAST );
		endDatePanel.add( fdayPanel );
		
		fhour = new JTextField( 9 );
		JLabel fhourLabel = new JLabel( "End Hour:" );
		fhourLabel.setLabelFor( fhour );
		JPanel fhourPanel = new JPanel();
		fhourPanel.add( fhourLabel, BorderLayout.WEST );
		fhourPanel.add( fhour, BorderLayout.EAST );
		endDatePanel.add( fhourPanel );
		
		fminute = new JTextField( 9 );
		JLabel fminuteLabel = new JLabel( "End Minute:" );
		fminuteLabel.setLabelFor( fminute );
		JPanel fminutePanel = new JPanel();
		fminutePanel.add( fminuteLabel, BorderLayout.WEST );
		fminutePanel.add( fminute, BorderLayout.EAST );
		endDatePanel.add( fminutePanel );
		
		fsecond = new JTextField( 9 );
		JLabel fsecondLabel = new JLabel( "End Second:" );
		fsecondLabel.setLabelFor( fsecond );
		JPanel fsecondPanel = new JPanel();
		fsecondPanel.add( fsecondLabel, BorderLayout.WEST );
		fsecondPanel.add( fsecond, BorderLayout.EAST );
		endDatePanel.add( fsecondPanel );
		
		datePanel.add( endDatePanel );
		
		dialog.getContentPane().add( datePanel );
		
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
		
		tags = new JTextField( 50 );
		tags.setToolTipText( "Tags are comma separated." );
		JLabel tagsLabel = new JLabel( "Tags:" );
		tagsLabel.setLabelFor( tags );
		JPanel tagsPanel = new JPanel();
		tagsPanel.add( tagsLabel, BorderLayout.WEST );
		tagsPanel.add( tags, BorderLayout.EAST );
		dialog.getContentPane().add( tagsPanel );
		
		priority = new JSlider( 0, 100 );
		priority.setMajorTickSpacing( 10 );
		priority.setMinorTickSpacing( 5 );
		priority.setSnapToTicks( true );
		priority.setPaintLabels( true );
		priority.setPaintTicks( true );
		priority.setToolTipText( "100 is the most important, 0 is the least." );
		JLabel priorityLabel = new JLabel( "Priority:" );
		priorityLabel.setLabelFor( priority );
		JPanel priorityPanel = new JPanel();
		priorityPanel.add( priorityLabel, BorderLayout.WEST );
		priorityPanel.add( priority, BorderLayout.EAST );
		dialog.getContentPane().add( priorityPanel );
		
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
		//GOTO hack
		do
		{
			if( e.getActionCommand().equals( "submit" ) )
			{
				String etext = stext.getText().trim();
				if( etext.isEmpty() ) break; //GOTO
				String eyear = syear.getText().trim();
				if( eyear.isEmpty() ) break; //GOTO
				String emonth = smonth.getText().trim();
				String eday = sday.getText().trim();
				String ehour = shour.getText().trim();
				String eminute = sminute.getText().trim();
				String esecond = ssecond.getText().trim();
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
				
				String efyear = fyear.getText().trim();
				if( efyear.isEmpty() ) break; //GOTO
				String efmonth = fmonth.getText().trim();
				String efday = fday.getText().trim();
				String efhour = fhour.getText().trim();
				String efminute = fminute.getText().trim();
				String efsecond = fsecond.getText().trim();
				int ifyear;
				try { ifyear = Integer.parseInt( efyear ); } catch( NumberFormatException nfe ) { ifyear = 0; }
				int ifmonth;
				try { ifmonth = Integer.parseInt( efmonth ); } catch( NumberFormatException nfe ) { ifmonth = 1; }
				int ifday;
				try { ifday = Integer.parseInt( efday ); } catch( NumberFormatException nfe ) { ifday = 1; }
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
		} while( false );
		
		dialog.setVisible( false );
		dialog.dispose();
	}
}
