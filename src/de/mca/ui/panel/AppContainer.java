package de.mca.ui.panel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import de.mca.core.App;
import de.mca.ui.Panel;

@SuppressWarnings("serial")
public class AppContainer extends Panel {
	private App instance = null;
	private JLabel label_text;
	
	public AppContainer() {
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		box.maximumLayoutSize(this);

		setLayout(box);
		
		label_text = new JLabel("Platzhalter: " + (instance == null ? "UNKNOWN" : instance.getConfig("appName")));
		label_text.setForeground(Color.decode("0xDDDDDD"));
		label_text.setFont(new Font("Arial", Font.PLAIN, 30));
		label_text.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		add(new Panel());
		add(label_text);
		add(new Panel());
	}

	public void setAppReference(App app) {
		this.instance = app;
		label_text.setText("Platzhalter: " + (instance == null ? "UNKNOWN" : instance.getConfig("appName")));
	}
}
