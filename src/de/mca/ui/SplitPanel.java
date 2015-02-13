package de.mca.ui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

@SuppressWarnings("serial")
public class Splitpanel extends JSplitPane {
	public Splitpanel() {
		super(JSplitPane.HORIZONTAL_SPLIT);
		setBorder(new EmptyBorder(10, 3, 10, 3));
		setContinuousLayout(true);
		setDividerSize(2);
		setOpaque(false);
		setUI(new BasicSplitPaneUI() {
			public BasicSplitPaneDivider createDefaultDivider() {
				return new BasicSplitPaneDivider(this) {
					public void setBorder(Border b) {
						super.setBorder(new EmptyBorder(0, 0, 0, 0));
					}
					
					@Override
					public void paint(Graphics g) {
						super.paint(g);
						
						Graphics2D g2	= (Graphics2D) g;
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.setColor(new Color(229, 229, 229, 100));
						g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
						g2.setColor(new Color(241, 241, 241, 255));
						g2.drawLine(getWidth() - 2, 1, getWidth() - 2, getHeight() - 1);
					}
				};
			}
       });
	}
}
