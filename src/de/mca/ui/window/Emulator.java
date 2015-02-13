package de.mca.ui.window;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import de.mca.core.App;
import de.mca.ui.Panel;
import de.mca.ui.Splitpanel;
import de.mca.ui.Window;
import de.mca.ui.font.Awesome;

@SuppressWarnings({"rawtypes", "serial", "unchecked"})
public class Emulator extends Window {
	private Splitpanel splitpanel;
	private Panel content;
	private JList projects;
	private DefaultListModel project_entries;
	
	public Emulator() {
		super("MyChannel-Apps.de - Emulator");
		
		this.splitpanel			= new Splitpanel();
		this.content			= new Panel();
		this.project_entries	= new DefaultListModel();
		this.projects			= new JList(this.project_entries);
		this.projects.setPreferredSize(new Dimension(150, 0));
		
		this.projects.setCellRenderer(new ListCellRenderer() {
			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
			
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				
		        renderer.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 10, 5, 10), new EmptyBorder(0, 0, 0, 0)));
		        renderer.setFont(new Font("Arial", Font.PLAIN, 13));
		        renderer.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/de/mca/resources/app.png"))));
		        
				if(isSelected) {
					renderer.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/de/mca/resources/app_active.png"))));
				    renderer.setForeground(Color.WHITE);
					renderer.setBackground(Color.decode("0x3998D6"));
				}
				
				return renderer;
			}
		});
		
		this.splitpanel.setLeftComponent(this.projects);
		this.splitpanel.setRightComponent(this.content);
        
        add(this.splitpanel);
	}
	
	public void addProject(App app) {
		this.project_entries.addElement(app); 
		this.projects.repaint();
	}
}
