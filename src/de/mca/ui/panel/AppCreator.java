package de.mca.ui.panel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BoxLayout;
import de.mca.ui.Button;
import de.mca.ui.Panel;

@SuppressWarnings("serial")
public class AppCreator extends Panel {
	public AppCreator() {
		setLayout(new BoxLayout(this, 1));
		add(new Button("Test", Button.Color.GREEN, ""));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setColor(new Color(255, 0, 0, 200));
		g2.fillRect(3, 30, getWidth() - 6, getHeight() - 6);
	}
}
