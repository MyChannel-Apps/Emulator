package de.mca.ui.panel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import de.mca.ui.Button;
import de.mca.ui.Panel;
import de.mca.ui.font.Glyphicons;

@SuppressWarnings("serial")
public class NoApps extends Panel {
	public NoApps() {
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		box.maximumLayoutSize(this);

		setLayout(box);
		
		JLabel label_icon = new JLabel("\ue243");
		label_icon.setForeground(Color.decode("0xDDDDDD"));
		label_icon.setFont(Glyphicons.get(100f));
		label_icon.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel label_text = new JLabel("Du hast derzeit noch keine Apps erstellt!");
		label_text.setForeground(Color.decode("0xDDDDDD"));
		label_text.setFont(new Font("Arial", Font.PLAIN, 30));
		label_text.setAlignmentX(Component.CENTER_ALIGNMENT);

		Button button_create = new Button("Neue App erstellen", Button.Color.GREEN, "\uf067");
		button_create.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		add(new Panel());
		add(label_icon);
		add(new JLabel(" "));
		add(label_text);
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(button_create);
		add(new Panel());
	}
}
