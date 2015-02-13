package de.mca.ui.panel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import de.mca.ui.Panel;

@SuppressWarnings("serial")
public class AppContainer extends Panel {
	public AppContainer() {
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		box.maximumLayoutSize(this);

		setLayout(box);
		
		JLabel label_text = new JLabel("Platzhalter für die UI!");
		label_text.setForeground(Color.decode("0xDDDDDD"));
		label_text.setFont(new Font("Arial", Font.PLAIN, 30));
		label_text.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		add(new Panel());
		add(label_text);
		add(new Panel());
	}
}
