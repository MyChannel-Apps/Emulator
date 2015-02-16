package de.mca.ui.panel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import de.mca.ui.Button;
import de.mca.ui.Panel;
import de.mca.ui.font.Glyphicons;
import de.mca.ui.window.Emulator;

@SuppressWarnings("serial")
public class NoApps extends Panel {
	private Emulator window;
	
	public NoApps(Emulator emulator) {
		this.window		= emulator;
		BoxLayout box	= new BoxLayout(this, BoxLayout.Y_AXIS);
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
		button_create.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				window.setOverlay(new AppCreator());
				e.consume();
			}
		});
		
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
