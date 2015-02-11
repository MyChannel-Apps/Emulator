package de.mca.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SplitComponent extends JPanel {
	private boolean last_component = false;
	private Resizer pan;
	
	public SplitComponent() {
		pan = new Resizer(this);
		setLayout(new BorderLayout());
		add(BorderLayout.EAST, pan);
	}
	
	public void lastComponent(boolean state) {
		this.last_component = state;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(!last_component) {
			Graphics2D g2 = (Graphics2D) g;
			int center = 4;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.decode("0xE5E5E5"));
			g2.drawLine(getWidth() - 1 - center, 0, getWidth() - 1 - center, getHeight());
			g2.setColor(Color.decode("0xF1F1F1"));
			g2.drawLine(getWidth() - 2 - center, 1, getWidth() - 2 - center, getHeight() - 1);
		} else {
			pan.hide();
		}
	}
	
	class Resizer extends JPanel implements MouseListener, MouseMotionListener {
		private Point pointPressed;
	    private Point pointReleased;
	    private SplitComponent component;
	    
	    public Resizer(SplitComponent component) {
	    	this.component = component;
			setOpaque(false);
	    	addMouseListener(this);
	    	addMouseMotionListener(this);
		}

	    private void updateCursor(boolean on) {
	    	if(on) {
				setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
	    	} else {
	    		setCursor(null);
	    	}
	    }
	    
	    @Override
	    public void mousePressed(MouseEvent e) {
	    	pointPressed = e.getPoint();
	    	updateCursor(true);
	    }
	    
	    @Override
	    public void mouseDragged(MouseEvent e) {
	    	pointPressed = e.getPoint();
	    	mouseReleased(e);
	    }
	    
	    @Override
	    public void mouseReleased(MouseEvent e) {
	    	pointReleased		= e.getPoint();
	    	int deltaX			= pointReleased.x; //- pointPressed.x;
	    	int deltaY			= pointReleased.y - pointPressed.y;
	    	Dimension size		= component.getSize();
	    	
	    	if(deltaX > 0) {
	    		size.width		-= deltaX;
	    	} else {
	    		size.width		+= deltaX;
	    	}
	    	
	    	if(size.width < 0) {
	    		return;
	    	}

	    	component.setPreferredSize(new Dimension(size.width, size.height));
	    	component.revalidate();
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
	    	updateCursor(true);
	    }

		@Override
		public void mouseMoved(MouseEvent arg0) {
			/* Do Nothing */
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			/* Do Nothing */
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			/* Do Nothing */
		}
	}
}
