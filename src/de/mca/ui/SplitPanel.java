package de.mca.ui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.ComponentUI;

@SuppressWarnings("serial")
public class SplitPanel extends JPanel {
	private ArrayList<Component> components;
	private GridLayout grid;
	private Alignment alignment;
	
	public enum Alignment {
		Horizontal,
		Vertical
	};
	
	public SplitPanel() {
		alignment	= Alignment.Horizontal;
		grid		= new GridLayout();
		components	= new ArrayList<Component>();
		
		setOpaque(true);
		setLayout(grid);
	}
	
	public SplitPanel(Alignment a) {
		this();
		alignment	= a;
	}
	
	@Override
	public Component add(Component c) {
		components.add(c);
		super.add(c);
		
		((JComponent) c).setOpaque(false);
		
		if(alignment == Alignment.Horizontal) {
			grid.setColumns(components.size());
			grid.setRows(0);
		} else if(alignment == Alignment.Vertical) {
			grid.setColumns(0);
			grid.setRows(components.size());
		}
		
		return c;
	}
	
	
	
	
	
	
	
	class DragBar extends JComponent
    implements MouseListener,
               MouseMotionListener
{
    private Point pointPressed;
    private Point pointReleased;
    private JPanel panel;

    public DragBar(JPanel panel)
    {
         this.panel = panel;

         setBackground(Color.GREEN);
        // setOpaque(true);
         setUI(new ComponentUI() {});
         setPreferredSize(new Dimension(5,5));

         addMouseListener(this);
         addMouseMotionListener(this);
    }

    private void updateCursor(boolean on)
    {
        if (on) {
            setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
        } else {
            setCursor(null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
         pointPressed = e.getLocationOnScreen();
         updateCursor(true);
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mouseReleased(e);
        pointPressed = e.getLocationOnScreen();
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        pointReleased = e.getLocationOnScreen();

        int deltaY = pointReleased.y - pointPressed.y;

        Point panelLocation = panel.getLocation();
        Dimension size = panel.getSize();
        size.height += deltaY;

        panel.setBounds(panelLocation.x, panelLocation.y, size.width, size.height);
        panel.setPreferredSize(new Dimension(size.width, size.height));

        //scrollPane.revalidate();
        pointPressed = null;
        pointReleased = null;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        updateCursor(true);
    }

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mouseMoved(MouseEvent e){}
}//end: inner class DragBar

}
