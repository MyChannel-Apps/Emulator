package de.mca.ui;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.mca.interfaces.Callback;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private int border = 5;
	private JPanel content;
	private Window instance;
	private JPanel body;
	
	public Window(String string) {
		super(string);
		
		body		= new JPanel();
		instance	= this;
		
		// Content Panel
		content = new JPanel() {
			public JPanel init() {
				setLayout(new BorderLayout());
				setOpaque(false);
				return this;
			}
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				Graphics2D g2d = (Graphics2D) g.create();
				
				// Draw Shadow
				g2d.setColor(Color.BLACK);
				for(int i = 0; i < border; i++) {
					g2d.setComposite(AlphaComposite.SrcOver.derive(0.1f));
					g2d.fillRect(i, i, getWidth() - (2 * i), getHeight() - (2 * i));
				}
				
				// Draw Background
				g2d.setComposite(AlphaComposite.SrcOver.derive(1f));
				g2d.setColor(Color.WHITE);
				g2d.fillRect(border - 2, border - 2, getWidth() - (2 * border - 4), getHeight() - (2 * border - 4));
				g2d.dispose();
			}
		}.init();
		
		// Topbar
		content.add(BorderLayout.NORTH, new JPanel() {
			private Point initialClick;
			
			public JPanel init() {
				setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
				setBorder(new EmptyBorder(3, 0, 0, 3));
			    setOpaque(false);
			    
				// Button: Minimize
				add(new Button(new Callback() {
					@Override
					public void run(Button b) {
						b.updateIcons("/de/mca/resources/window_minimize_normal.png", "/de/mca/resources/window_minimize_hover.png");
					}
				}, null, new Callback() {
					@Override
					public void run(Button b) {
						instance.setExtendedState(JFrame.ICONIFIED);
						instance.setLocationRelativeTo(null);
					}
				}));
				
				// Button: Maximize / Restore
				add(new Button(new Callback() {
					@Override
					public void run(Button b) {
						b.updateIcons("/de/mca/resources/window_maximize_normal.png", "/de/mca/resources/window_maximize_hover.png");
					}
				}, new Callback() {
					@Override
					public void run(Button b) {
						if((instance.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
							b.updateIcons("/de/mca/resources/window_restore_normal.png", "/de/mca/resources/window_restore_hover.png");
						} else {
							b.updateIcons("/de/mca/resources/window_maximize_normal.png", "/de/mca/resources/window_maximize_hover.png");
						}
					}
				}, new Callback() {
					@Override
					public void run(Button b) {
						instance.setExtendedState((instance.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH ? JFrame.NORMAL : JFrame.MAXIMIZED_BOTH);
						instance.setLocationRelativeTo(null);
					}
				}, instance));
				
				// Button: Close
				add(new Button(new Callback() {
					@Override
					public void run(Button b) {
						b.updateIcons("/de/mca/resources/window_close_normal.png", "/de/mca/resources/window_close_hover.png");
					}
				}, null, new Callback() {
					@Override
					public void run(Button b) {
						instance.close();
					}
				}));
				
				setOpaque(false);
				
				addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						if(e.getClickCount() == 2) {
							instance.setExtendedState((instance.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH ? JFrame.NORMAL : JFrame.MAXIMIZED_BOTH);
							instance.setLocationRelativeTo(null);
						} else {
							if((instance.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
								return;
							}
							
							initialClick = e.getPoint();
							
							getComponentAt(initialClick);
						}
					}
				});
				
				addMouseMotionListener(new MouseMotionAdapter() {
					public void mouseDragged(MouseEvent e) {
						if(initialClick == null || (instance.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
							return;
						}
						
						int thisX	= instance.getLocation().x;
						int thisY	= instance.getLocation().y;
						int xMoved	= (thisX + e.getX()) - (thisX + initialClick.x);
						int yMoved	= (thisY + e.getY()) - (thisY + initialClick.y);
						int X		= thisX + xMoved;
						int Y		= thisY + yMoved;
						
						instance.setLocation(X, Y);
					}
				});
				
				return this;
			}
		}.init());
		
		// Body
		body.setOpaque(false);
		body.setBackground(Color.YELLOW);
		body.setLayout(new BorderLayout());
		
		content.setOpaque(false);
		content.add(BorderLayout.CENTER, body);
		
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent windowEvent) {
		    	instance.close();
		    }
		});
		
		// Defaults
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		add(content);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		setSize(800, 600);
		getRootPane().setOpaque(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/de/mca/resources/logo.png")));
	}
	
	public Window() {
		this("");
	}

	public void open() {
		setVisible(true);
	}
	
	public void close() {
		setVisible(false);
	}
	
	public void setOverlay(JComponent c) {
		if(getGlassPane().isVisible()) {
			return;
		}
		
		setGlassPane(c);
		
		for(MouseListener a : content.getMouseListeners()) {
			c.addMouseListener(a);
		}
		
		c.setVisible(true);
	}
	
	@Override
	public Component add(Component c) {
		if(content.equals(c)) {
			super.add(c);
		} else {
			if(c instanceof JComponent) {
				((JComponent) c).setOpaque(false);
			}
			
			body.add(c);
		}
		
		return c;
	}
}