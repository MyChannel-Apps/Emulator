package de.mca.ui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.ComponentUI;

public class PanelResizer extends JFrame
{
    private JPanel mainPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane();

    // inner class DragBar: bar to drag&drop for panel resize
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

             setBackground(Color.red);
             setOpaque(true);
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

            scrollPane.revalidate();
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

    //inner class ResizablePanel
    class ResizablePanel extends JPanel
    {
        public ResizablePanel()
        {
            setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
            setMinimumSize(new java.awt.Dimension(300, 80));
            setPreferredSize(new java.awt.Dimension(300, 130));
            setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            setLayout(new BorderLayout());
            JLabel myLabel = new JLabel("Test label");
            myLabel.setHorizontalAlignment(SwingConstants.CENTER);

            add(myLabel, BorderLayout.CENTER);
            add(new DragBar(this), BorderLayout.SOUTH);
        }
    }// end inner class ResizablePanel

    public PanelResizer()
    {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(100, 200));

        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        mainPanel.add(new ResizablePanel());
        mainPanel.add(new ResizablePanel());
        mainPanel.add(new ResizablePanel());

        scrollPane.setViewportView(mainPanel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        pack();
    }

    public static void main(String args[]) {
        new PanelResizer().setVisible(true);
    }
}
