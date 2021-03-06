package com.phyloa.timeline;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.miginfocom.swing.MigLayout;
import timeline.DoubleString;
import timeline.Event;
import timeline.Item;
import timeline.Span;
import timeline.TDate;
import timeline.TimelineData;

import com.phyloa.dlib.renderer.Graphics2DIRenderer;
import com.phyloa.dlib.util.DFile;
import com.phyloa.dlib.util.DGraphics;

public class TimelineRun implements KeyListener, ComponentListener,
		ActionListener, MouseListener, MouseWheelListener, MouseMotionListener,
		ChangeListener
{
	Timeline timeline;
	Graphics2DIRenderer r;
	
	public JFrame container;
	public JMenuBar menubar;
	public ImagePanel ip;
	public RightClickMenu rcmenu;
	public JSlider priority;
	public JTextField tags;
	public JTextArea notes;
	public JTabbedPane tabbedPane;
	
	int lastX;
	
	boolean showNotes = false;
	
	public TimelineRun()
	{
		try
		{
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		}
		catch( ClassNotFoundException e )
		{
			e.printStackTrace();
		}
		catch( InstantiationException e )
		{
			e.printStackTrace();
		}
		catch( IllegalAccessException e )
		{
			e.printStackTrace();
		}
		catch( UnsupportedLookAndFeelException e )
		{
			e.printStackTrace();
		}
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		r = new Graphics2DIRenderer( screenSize.width, screenSize.height );
		
		container = new JFrame( "Timeline" );
		container.setPreferredSize( new Dimension( 800, 600 ) );
		container.setSize( 800, 600 );
		container.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		container.addKeyListener( this );
		container.addMouseWheelListener( this );
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener( this );
		container.getContentPane().add( tabbedPane );
		
		JPanel timelinePane = new JPanel( new BorderLayout() );
		JPanel notesPane = new JPanel( new BorderLayout() );
		
		tabbedPane.addTab( "Timeline", timelinePane );
		tabbedPane.addTab( "Notes", notesPane );
		
		menubar = new JMenuBar();
		container.setJMenuBar( menubar );
		
		JMenu fileMenu = new JMenu( "File" );
		fileMenu.setMnemonic( 'F' );
		menubar.add( fileMenu );
		
		JMenuItem newMenu = new JMenuItem( "New Timeline", 'N' );
		newMenu.setAccelerator( KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK ) );
		newMenu.setActionCommand( "new" );
		newMenu.addActionListener( this );
		fileMenu.add( newMenu );
		
		JMenuItem openMenu = new JMenuItem( "Open", 'O' );
		openMenu.setAccelerator( KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK ) );
		openMenu.setActionCommand( "open" );
		openMenu.addActionListener( this );
		fileMenu.add( openMenu );
		
		JMenuItem saveMenu = new JMenuItem( "Save", 'S' );
		saveMenu.setAccelerator( KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK ) );
		saveMenu.setActionCommand( "save" );
		saveMenu.addActionListener( this );
		fileMenu.add( saveMenu );
		
		JMenuItem saveAsMenu = new JMenuItem( "Save As...", 'A' );
		saveAsMenu.setActionCommand( "saveas" );
		saveAsMenu.addActionListener( this );
		saveAsMenu.addActionListener( this );
		fileMenu.add( saveAsMenu );
		
		fileMenu.addSeparator();
		
		JMenuItem exportxmlMenu = new JMenuItem( "Export as XML" );
		exportxmlMenu.setActionCommand( "exportxml" );
		exportxmlMenu.addActionListener( this );
		exportxmlMenu.addActionListener( this );
		fileMenu.add( exportxmlMenu );
		
		JMenuItem exporttextMenu = new JMenuItem( "Export as Text" );
		exporttextMenu.setActionCommand( "exporttext" );
		exporttextMenu.addActionListener( this );
		exporttextMenu.addActionListener( this );
		fileMenu.add( exporttextMenu );
		
		fileMenu.addSeparator();
		
		JMenuItem importxmlMenu = new JMenuItem( "Import XML" );
		importxmlMenu.setActionCommand( "importxml" );
		importxmlMenu.addActionListener( this );
		importxmlMenu.addActionListener( this );
		fileMenu.add( importxmlMenu );
		
		JMenu timelineMenu = new JMenu( "Timeline" );
		timelineMenu.setMnemonic( 'T' );
		menubar.add( timelineMenu );
		
		JMenuItem addEventMenu = new JMenuItem( "Add Event", 'E' );
		addEventMenu.setAccelerator( KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_E, java.awt.Event.CTRL_MASK ) );
		addEventMenu.setActionCommand( "addevent" );
		addEventMenu.addActionListener( this );
		timelineMenu.add( addEventMenu );
		
		JMenuItem addSpanMenu = new JMenuItem( "Add Span", 'D' );
		addSpanMenu.setAccelerator( KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_D, java.awt.Event.CTRL_MASK ) );
		addSpanMenu.setActionCommand( "addspan" );
		addSpanMenu.addActionListener( this );
		timelineMenu.add( addSpanMenu );
		
		JMenuItem editItemMenu = new JMenuItem( "Modify Item", 'M' );
		editItemMenu.setAccelerator( KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_Q, java.awt.Event.CTRL_MASK ) );
		editItemMenu.setActionCommand( "edititem" );
		editItemMenu.addActionListener( this );
		timelineMenu.add( editItemMenu );
		
		timelineMenu.addSeparator();
		
		JMenuItem settingsMenu = new JMenuItem( "Settings", 'S' );
		settingsMenu.setActionCommand( "settings" );
		settingsMenu.addActionListener( this );
		timelineMenu.add( settingsMenu );
		
		JToolBar toolbar = new JToolBar( "Timeline Control" );
		toolbar.setFloatable( false );
		toolbar.setFocusable( false );
		toolbar.setLayout( new MigLayout( "align center center" ) );
		timelinePane.add( toolbar, BorderLayout.SOUTH );

		priority = new JSlider( 0, 100, 0 );
		priority.setMajorTickSpacing( 10 );
		priority.setMinorTickSpacing( 5 );
		priority.setSnapToTicks( true );
		priority.setPaintLabels( true );
		priority.setPaintTicks( true );
		priority.setToolTipText( "100 is the most important, 0 is the least." );
		priority.addChangeListener( this );
		JLabel priorityLabel = new JLabel( "Priority Threshold:" );
		priorityLabel.setLabelFor( priority );
		JPanel priorityPanel = new JPanel();
		priorityPanel.add( priorityLabel, BorderLayout.WEST );
		priorityPanel.add( priority, BorderLayout.EAST );
		toolbar.add( priorityPanel );
		
		tags = new JTextField( 30 );
		tags.setActionCommand( "tags" );
		tags.addKeyListener( this );
		tags.setToolTipText( "Tags are comma separated." );
		JLabel tagsLabel = new JLabel( "Tags to Show:" );
		tagsLabel.setLabelFor( tags );
		JPanel tagsPanel = new JPanel();
		tagsPanel.setMaximumSize( new Dimension( 250, 50 ) );
		tagsPanel.setPreferredSize( new Dimension( 250, 50 ) );
		tagsPanel.add( tagsLabel );
		tagsPanel.add( tags );
		toolbar.add( tagsPanel );
		
		ip = new ImagePanel( r.getImage() );
		timelinePane.add( ip, BorderLayout.CENTER );
		
		//Notes
		notes = new JTextArea();
		JScrollPane notesScroll = new JScrollPane( notes );
		notesPane.add( notesScroll, BorderLayout.CENTER );
		//End Notes
		
		container.pack();
		container.setVisible( true );
		
		timeline = new Timeline( r );
		
		changeTimelineSize( ip.getWidth(), ip.getHeight() );
		ip.addComponentListener( this );
		ip.addMouseListener( this );
		ip.addMouseMotionListener( this );
		container.repaint();
		container.requestFocus();
		
		rcmenu = new RightClickMenu( ip, this );
	}
	
	public void timelineChanged()
	{
		timeline.updateVisibleItems();
		timeline.render();
		
		container.repaint();
	}
	
	public void changeTimelineSize( int x, int y )
	{
		r.im = DGraphics.createBufferedImage( x, y );
		timelineChanged();
	}
	
	public static void main( String[] args )
	{
		@SuppressWarnings( "unused" )
		TimelineRun tr = new TimelineRun();
	}
	
	public void keyPressed( KeyEvent e )
	{
		if( timeline != null && e.getSource() != tags )
		{
			switch( e.getKeyCode() )
			{
			case KeyEvent.VK_UP:
				timeline.zoomIn( 2f );
				timelineChanged();
				break;
			case KeyEvent.VK_DOWN:
				timeline.zoomOut( 2f );
				timelineChanged();
				break;
			case KeyEvent.VK_LEFT:
				timeline.scrollLeft( 10 );
				timelineChanged();
				break;
			case KeyEvent.VK_RIGHT:
				timeline.scrollRight( 10 );
				timelineChanged();
				break;
			}
		}
	}
	
	public void keyReleased( KeyEvent e )
	{
		if( e.getSource() == tags )
		{
			ArrayList<String> tagsA = new ArrayList<String>();
			String[] tagsS = tags.getText().split( "," );
			for( int i = 0; i < tagsS.length; i++ )
			{
				String text = tagsS[i].trim();
				if( text.length() > 0 )
					tagsA.add( text );
			}
			timeline.tags = tagsA;
			timelineChanged();
		}
	}
	
	public void keyTyped( KeyEvent e )
	{
		
	}
	
	public class ImagePanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		private BufferedImage image;
		
		public ImagePanel( Image image2 )
		{
			this.image = (BufferedImage)image2;
		}
		
		@Override
		public void paintComponent( Graphics g )
		{
			g.drawImage( image, 0, 0, null );
		}
	}
	
	public void componentHidden( ComponentEvent arg0 )
	{
		changeTimelineSize( ip.getWidth(), ip.getHeight() );
	}
	
	public void componentMoved( ComponentEvent arg0 )
	{
		changeTimelineSize( ip.getWidth(), ip.getHeight() );
	}
	
	public void componentResized( ComponentEvent arg0 )
	{
		changeTimelineSize( ip.getWidth(), ip.getHeight() );
	}
	
	public void componentShown( ComponentEvent arg0 )
	{
		changeTimelineSize( ip.getWidth(), ip.getHeight() );
	}
	
	public void actionPerformed( ActionEvent e )
	{
		if( e.getActionCommand().equals( "fastback" ) )
		{
			timeline.scrollLeft( 1f );
			timelineChanged();
		}
		else if( e.getActionCommand().equals( "back" ) )
		{
			timeline.scrollLeft( .5f );
			timelineChanged();
		}
		else if( e.getActionCommand().equals( "forward" ) )
		{
			timeline.scrollRight( .5f );
			timelineChanged();
		}
		else if( e.getActionCommand().equals( "fastforward" ) )
		{
			timeline.scrollRight( 1f );
			timelineChanged();
		}
		else if( e.getActionCommand().equals( "new" ) )
		{
			timeline.clear();
			timelineChanged();
		}
		else if( e.getActionCommand().equals( "open" ) )
		{
			final JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter( new FileNameExtensionFilter(
					"Timeline Files", "timeline" ) );
			int returnVal = fc.showOpenDialog( container );
			if( returnVal == JFileChooser.APPROVE_OPTION )
			{
				File f = fc.getSelectedFile();
				Object o = null;
				try
				{
					o = DFile.loadObject( f.getAbsolutePath() );
				}
				catch( IOException e1 )
				{
					e1.printStackTrace();
				}
				catch( ClassNotFoundException e1 )
				{
					e1.printStackTrace();
				}
				if( o instanceof TimelineData )
				{
					TimelineData dat = (TimelineData)o;
					timeline.firstDate = dat.centerDate;
					timeline.lastDate = dat.lastDate;
					timeline.items = dat.items;
					timeline.notes = dat.notes;
					timeline.file = f;
					timeline.tp = dat.tp == null ? new TimelinePreferences()
							: dat.tp;
					timelineChanged();
				}
			}
		}
		else if( e.getActionCommand().equals( "save" ) )
		{
			TimelineData dat = new TimelineData();
			dat.centerDate = timeline.firstDate;
			dat.lastDate = timeline.lastDate;
			dat.items = timeline.items;
			dat.tp = timeline.tp;
			dat.notes = timeline.notes;
			if( timeline.file == null )
			{
				final JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter( new FileNameExtensionFilter(
						"Timeline Files", "timeline" ) );
				int returnVal = fc.showSaveDialog( container );
				if( returnVal == JFileChooser.APPROVE_OPTION )
				{
					File f = fc.getSelectedFile();
					try
					{
						String filename = f.getAbsolutePath();
						if( !filename.endsWith( ".timeline" ) )
						{
							filename = filename + ".timeline";
						}
						DFile.saveObject( filename, dat );
						timeline.file = f;
					}
					catch( IOException e1 )
					{
						e1.printStackTrace();
					}
				}
			}
			else
			{
				try
				{
					DFile.saveObject( timeline.file.getAbsolutePath(), dat );
				}
				catch( IOException e1 )
				{
					e1.printStackTrace();
				}
			}
		}
		else if( e.getActionCommand().equals( "saveas" ) )
		{
			final JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter( new FileNameExtensionFilter(
					"Timeline Files", "timeline" ) );
			int returnVal = fc.showSaveDialog( container );
			if( returnVal == JFileChooser.APPROVE_OPTION )
			{
				File f = fc.getSelectedFile();
				TimelineData dat = new TimelineData();
				dat.centerDate = timeline.firstDate;
				dat.lastDate = timeline.lastDate;
				dat.items = timeline.items;
				dat.tp = timeline.tp;
				dat.notes = timeline.notes;
				try
				{
					String filename = f.getAbsolutePath();
					if( !filename.endsWith( ".timeline" ) )
					{
						filename = filename + ".timeline";
					}
					DFile.saveObject( filename, dat );
					timeline.file = f;
				}
				catch( IOException e1 )
				{
					e1.printStackTrace();
				}
			}
		}
		else if( e.getActionCommand().equals( "addevent" ) )
		{
			addEvent( null );
		}
		else if( e.getActionCommand().equals( "addspan" ) )
		{
			addSpan( null );
		}
		else if( e.getActionCommand().equals( "edititem" ) )
		{
			if( timeline.selected != null )
			{
				editItem( timeline.selected );
			}
		}
		else if( e.getActionCommand().equals( "settings" ) )
		{
			SettingsDialog pd = new SettingsDialog( container,
					timeline.tp );
			pd.showDialog();
		}
		else if( e.getActionCommand().equals( "exporttext" ) )
		{
			final JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter( new FileNameExtensionFilter(
					"Text Files", "txt" ) );
			int returnVal = fc.showSaveDialog( container );
			if( returnVal == JFileChooser.APPROVE_OPTION )
			{
				File f = fc.getSelectedFile();
				try
				{
					String filename = f.getAbsolutePath();
					if( !filename.endsWith( ".txt" ) )
					{
						filename = filename + ".txt";
					}
					DFile.saveText( filename, timeline.toText() );
				}
				catch( FileNotFoundException e1 )
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else if( e.getActionCommand().equals( "exportxml" ) )
		{
			final JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter( new FileNameExtensionFilter(
					"XML Files", "xml" ) );
			int returnVal = fc.showSaveDialog( container );
			if( returnVal == JFileChooser.APPROVE_OPTION )
			{
				File f = fc.getSelectedFile();
				try
				{
					String filename = f.getAbsolutePath();
					if( !filename.endsWith( ".xml" ) )
					{
						filename = filename + ".xml";
					}
					DFile.saveText( filename, timeline.toXml() );
				}
				catch( FileNotFoundException e1 )
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else if( e.getActionCommand().equals( "importxml" ) )
		{
			final JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter( new FileNameExtensionFilter( "XML Files", "xml" ) );
			int returnVal = fc.showOpenDialog( container );
			if( returnVal == JFileChooser.APPROVE_OPTION )
			{
				try
				{
					File f = fc.getSelectedFile();
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse( f );
					doc.getDocumentElement().normalize();
					
					NodeList nList = doc.getElementsByTagName( "item" );
					
					for( int temp = 0; temp < nList.getLength(); temp++ )
					{
						
						Node nNode = nList.item( temp );
						if( nNode.getNodeType() == Node.ELEMENT_NODE )
						{
							Element item = (Element)nNode;
							String type = item.getAttribute( "type" );
							if( type.equalsIgnoreCase( "span" ) )
							{
								try
								{
									String start = getTagValue( "start", item );
									String end = getTagValue( "end", item );
									String name = getTagValue( "name", item );
									TDate startD = new TDate( start );
									TDate endD = new TDate( end );
									Span s = new Span( name, startD, endD );
									
									try
									{
									String priority = getTagValue( "priority", item );
									if( priority != null )
										s.priority = Integer.parseInt( priority );
									} catch( Exception ex ) {}
									
									try
									{
									String tags = getTagValue( "tags", item );
									if( tags != null )
									{
										String[] tagA = tags.split( "," );
										for( int i = 0; i < tagA.length; i++ )
										{
											s.tags.add( tagA[i] );
										}
									}
									} catch( Exception ex ) {}
									
									try
									{
									String link = getTagValue( "link", item );
									if( link != null )
										s.data.add( new DoubleString( "link", link ) );
									} catch( Exception ex ) {}
									
									try
									{
									String description = getTagValue( "description", item );
									if( description != null )
										s.data.add( new DoubleString( "desc", description ) );
									} catch( Exception ex ) {}
									
									try
									{
									String location = getTagValue( "location", item );
									if( location != null )
										s.data.add( new DoubleString( "loc", location ) );
									} catch( Exception ex ) {}
									
									timeline.add( s );
								}
								catch( Exception ex )
								{
									continue;
								}
							}
							else
							{
								try
								{
									String date = getTagValue( "date", item );
									String name = getTagValue( "name", item );
									TDate dateD = new TDate( date );
									Event s = new Event( name, dateD );
									
									try
									{
									String priority = getTagValue( "priority", item );
									if( priority != null )
										s.priority = Integer.parseInt( priority );
									} catch( Exception ex ) {}
									
									try
									{
									String tags = getTagValue( "tags", item );
									if( tags != null )
									{
										String[] tagA = tags.split( "," );
										for( int i = 0; i < tagA.length; i++ )
										{
											s.tags.add( tagA[i] );
										}
									}
									} catch( Exception ex ) {}
									
									try
									{
									String link = getTagValue( "link", item );
									if( link != null )
										s.data.add( new DoubleString( "link", link ) );
									} catch( Exception ex ) {}
									
									try
									{
									String description = getTagValue( "description", item );
									if( description != null )
										s.data.add( new DoubleString( "desc", description ) );
									} catch( Exception ex ) {}
									
									try
									{
									String location = getTagValue( "location", item );
									if( location != null )
										s.data.add( new DoubleString( "loc", location ) );
									} catch( Exception ex ) {}
									
									timeline.add( s );
								}
								catch( Exception ex )
								{
									continue;
								}
							}
							
						}
					}
				}
				catch( Exception exception )
				{
					
				}
			}
		}
		container.requestFocus();
	}
	
	private static String getTagValue( String sTag, Element eElement )
	{
		NodeList nlList = eElement.getElementsByTagName( sTag ).item( 0 ).getChildNodes();
		Node nValue = (Node)nlList.item( 0 );
		
		return nValue.getNodeValue();
	}
	
	public void editItem( Item i )
	{
		if( i instanceof Event )
		{
			EditEventDialog ed = new EditEventDialog( container, this );
			ed.showEventDialog( (Event)i, null );
		}
		else if( i instanceof Span )
		{
			EditSpanDialog es = new EditSpanDialog( container, this );
			es.showSpanDialog( (Span)i, null );
		}
	}
	
	public void addSpan( Integer year )
	{
		EditSpanDialog es = new EditSpanDialog( container, this );
		es.showSpanDialog( null, year );
	}
	
	public void addEvent( Integer year )
	{
		EditEventDialog ed = new EditEventDialog( container, this );
		ed.showEventDialog( null, year );
	}
	
	public void dialogCompleted( Item i, boolean added )
	{
		if( i != null )
		{
			if( added )
			{
				timeline.add( i );
				timeline.setCenter( i.getCenter() );
			}
			timelineChanged();
		}
	}
	
	public void mouseClicked( MouseEvent e )
	{
		if( e.getButton() == MouseEvent.BUTTON1 )
		{
			timeline.selected = timeline.getItem( e.getX(), e.getY() );
			timelineChanged();
		}
		else if( e.getButton() == MouseEvent.BUTTON3 )
		{
			
		}
		container.requestFocus();
	}
	
	public void mouseEntered( MouseEvent e )
	{
		
	}
	
	public void mouseExited( MouseEvent e )
	{
		
	}
	
	public void mousePressed( MouseEvent e )
	{
		if( e.isPopupTrigger() )
		{
			rcmenu.show( e.getX(), e.getY(), timeline.getItem( e.getX(), e
					.getY() ) );
		}
	}
	
	public void mouseReleased( MouseEvent e )
	{
		if( e.isPopupTrigger() )
		{
			rcmenu.show( e.getX(), e.getY(), timeline.getItem( e.getX(), e
					.getY() ) );
		}
	}
	
	public void mouseWheelMoved( MouseWheelEvent e )
	{
		float rot = e.getWheelRotation();
		if( rot < 0 )
		{
			timeline.zoomIn( -e.getWheelRotation() * .8f );
		}
		else
		{
			timeline.zoomOut( e.getWheelRotation() * .8f );
		}
		
		timelineChanged();
	}
	
	public void mouseDragged( MouseEvent e )
	{
		if( e.getButton() == MouseEvent.BUTTON1 || true )
		{
			float diff = (lastX - e.getX()) * .7f;
			if( diff > 0 )
			{
				timeline.scrollLeft( -diff );
			}
			else
			{
				timeline.scrollRight( diff );
			}
			timelineChanged();
			
			lastX = e.getX();
		}
	}
	
	public void mouseMoved( MouseEvent e )
	{
		timeline.hover = timeline.getItem( e.getX(), e.getY() );
		timelineChanged();
		
		lastX = e.getX();
	}
	
	public void stateChanged( ChangeEvent e )
	{
		if( e.getSource() == priority )
		{
			timeline.priority = priority.getValue();
			timelineChanged();
		}
		else if( e.getSource() == tabbedPane )
		{
			if( tabbedPane.getSelectedIndex() == 0 )
			{
				if( timeline != null )
				{
					timeline.notes = notes.getText();
					timeline.updateTimelineFromNotes();
				}
			}
			else if( tabbedPane.getSelectedIndex() == 1 )
			{
				timeline.updateNotesFromTimeline();
				notes.setText( timeline.notes );
			}
		}
	}
	
	public void deleteItem( Item item )
	{
		timeline.items.remove( item );
		timelineChanged();
	}
}
