package com.phyloa.timeline;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class PreferencesDialog implements ActionListener, MouseListener 
{
	private JFrame frame;
	private TimelinePreferences prefs;
	
	JDialog dialog;
	ColorDisplayer lineColor;
	ColorDisplayer textColor;
	ColorDisplayer fillColor;
	ColorDisplayer backgroundColor;
	ColorDisplayer selectColor;
	
	JButton submit;
	JButton cancel;

	public PreferencesDialog( JFrame frame, TimelinePreferences prefs )
	{
		this.frame = frame;
		this.prefs = prefs;
		
		dialog = new JDialog( frame, "Settings" );
		dialog.setPreferredSize( new Dimension( 200, 200 ) );
		dialog.setSize( 200, 200 );
		dialog.setResizable( false );
		
		//dialog.getContentPane().setLayout( new BoxLayout( dialog.getContentPane(), BoxLayout.PAGE_AXIS ) );
		dialog.getContentPane().setLayout( new MigLayout( "align center center, insets 10 10 10 10") );
		
		lineColor = new ColorDisplayer( prefs.lineColor, 50, 20 );
		lineColor.addMouseListener( this );
		JLabel lineColorLabel = new JLabel( "Line Color:" );
		lineColorLabel.setLabelFor( lineColor );
		dialog.getContentPane().add( lineColorLabel, "align right" );
		dialog.getContentPane().add( lineColor, "align left, wrap" );
		
		textColor = new ColorDisplayer( prefs.textColor, 50, 20 );
		textColor.addMouseListener( this );
		JLabel textColorLabel = new JLabel( "Text Color:" );
		textColorLabel.setLabelFor( textColor );
		dialog.getContentPane().add( textColorLabel, "align right" );
		dialog.getContentPane().add( textColor, "align left, wrap" );
		
		fillColor = new ColorDisplayer( prefs.fillColor, 50, 20 );
		fillColor.addMouseListener( this );
		JLabel fillColorLabel = new JLabel( "Fill Color:" );
		fillColorLabel.setLabelFor( fillColor );
		dialog.getContentPane().add( fillColorLabel, "align right" );
		dialog.getContentPane().add( fillColor, "align left, wrap" );
		
		backgroundColor = new ColorDisplayer( prefs.backgroundColor, 50, 20 );
		backgroundColor.addMouseListener( this );
		JLabel backgroundColorLabel = new JLabel( "Background Color:" );
		backgroundColorLabel.setLabelFor( backgroundColor );
		dialog.getContentPane().add( backgroundColorLabel, "align right" );
		dialog.getContentPane().add( backgroundColor, "align left, wrap" );
		
		selectColor = new ColorDisplayer( prefs.selectColor, 50, 20 );
		selectColor.addMouseListener( this );
		JLabel selectColorLabel = new JLabel( "Selection Color:" );
		selectColorLabel.setLabelFor( selectColor );
		dialog.getContentPane().add( selectColorLabel, "align right" );
		dialog.getContentPane().add( selectColor, "align left, wrap" );
		
		submit = new JButton( "Finish" );
		submit.addActionListener( this );
		submit.setActionCommand( "submit" );
		dialog.getContentPane().add( submit, "align center" );
		
		cancel = new JButton( "Cancel" );
		cancel.addActionListener( this );
		cancel.setActionCommand( "cancel" );
		dialog.getContentPane().add( cancel, "align center" );
		
		//dialog.pack();
	}
	
	public void showDialog()
	{
		lineColor.setColor( prefs.lineColor );
		textColor.setColor( prefs.textColor );
		fillColor.setColor( prefs.fillColor );
		backgroundColor.setColor( prefs.backgroundColor );
		selectColor.setColor( prefs.selectColor );
		
		dialog.setVisible( true );
	}

	@Override
	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource() == submit )
		{
			prefs.lineColor = lineColor.color;
			prefs.textColor = textColor.color;
			prefs.fillColor = fillColor.color;
			prefs.backgroundColor = backgroundColor.color;
			prefs.selectColor = selectColor.color;
		}
		dialog.setVisible( false );
		dialog.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseReleased( MouseEvent e ) 
	{
		if( e.getSource() == lineColor )
		{
			lineColor.color = JColorChooser.showDialog( dialog, "Line Color", lineColor.color );
			lineColor.repaint();
		}
		else if( e.getSource() == textColor )
		{
			textColor.color = JColorChooser.showDialog( dialog, "Text Color", textColor.color );
			textColor.repaint();
		}
		else if( e.getSource() == fillColor )
		{
			fillColor.color = JColorChooser.showDialog( dialog, "Fill Color", fillColor.color );
			fillColor.repaint();
		}
		else if( e.getSource() == backgroundColor )
		{
			backgroundColor.color = JColorChooser.showDialog( dialog, "Background Color", backgroundColor.color );
			backgroundColor.repaint();
		}
		else if( e.getSource() == selectColor )
		{
			selectColor.color = JColorChooser.showDialog( dialog, "Selection Color", selectColor.color );
			selectColor.repaint();
		}
		frame.repaint();
	}
}
