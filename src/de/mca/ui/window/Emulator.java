package de.mca.ui.window;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import de.mca.core.App;
import de.mca.ui.Panel;
import de.mca.ui.Splitpanel;
import de.mca.ui.Window;
import de.mca.ui.panel.AppContainer;
import de.mca.ui.panel.NoAppSelected;
import de.mca.ui.panel.NoApps;

@SuppressWarnings({"rawtypes", "serial", "unchecked"})
public class Emulator extends Window {
	private Splitpanel splitpanel;
	private Panel content;
	private JList projects;
	private DefaultListModel project_entries;
	private PropertyChangeSupport event 	= new PropertyChangeSupport(this);
	private Panel view_no_app_selected		= new NoAppSelected();
	private Panel view_no_apps				= new NoApps(this);
	private AppContainer view_app_container	= new AppContainer();
	private int selected					= -1;
	
	public Emulator() {
		super("MyChannel-Apps.de - Emulator");
		
		this.splitpanel			= new Splitpanel();
		this.content			= new Panel();
		this.project_entries	= new DefaultListModel();
		this.projects			= new JList(this.project_entries);
		this.projects.setPreferredSize(new Dimension(150, 0));
		
		JPopupMenu context		= new JPopupMenu();
		ActionListener action	= new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object entry	= project_entries.get(selected);
				
				switch(e.getActionCommand()) {
					case "Öffnen":
						event.firePropertyChange("selectApp", null, entry.toString());
					break;
					case "Im Explorer öffnen":
						event.firePropertyChange("exploreApp", null, entry.toString());
					break;
					case "Löschen":
						event.firePropertyChange("deleteApp", null, entry.toString());
					break;
				}
			}
		};
		
		JMenuItem item	= new JMenuItem("Öffnen");
		item.addActionListener(action);
		context.add(item);
		item			= new JMenuItem("Im Explorer öffnen");
		item.addActionListener(action);
		context.add(item);
		item			= new JMenuItem("Löschen");
		item.addActionListener(action);
		context.add(item);
		
		this.projects.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JList list		= (JList) e.getSource();
	            int row			= ((JList) list).locationToIndex(e.getPoint());
	            
	            if(row <= -1) {
	            	removeSelection();
	            	return;
	            }
	            
	            Object entry	= project_entries.get(row);
	            selected		= row;
	            
				if(SwingUtilities.isRightMouseButton(e)) {
		            list.setSelectedIndex(row);
		            context.show(list, list.getWidth(), 26 * row);
		        } else {
		        	event.firePropertyChange("selectApp", null, entry.toString());
		        }
			}
		});
		
		this.projects.setCellRenderer(new ListCellRenderer() {
			protected DefaultListCellRenderer defaultRenderer	= new DefaultListCellRenderer();
			private ImageIcon app_default						= new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/de/mca/resources/app.png")));
			private ImageIcon app_active						= new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/de/mca/resources/app_active.png")));
			
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				
		        renderer.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 10, 5, 10), new EmptyBorder(0, 0, 0, 0)));
		        renderer.setFont(new Font("Arial", Font.PLAIN, 13));
		        renderer.setIcon(app_default);
		        
				if(isSelected) {
					renderer.setIcon(app_active);
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
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		event.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		event.removePropertyChangeListener(listener);
	}
	
	public void addApp(App app) {
		this.project_entries.addElement(app); 
		initComplete();
		redraw();
	}
	
	public void removeApp(App app) {
		removeSelection();
		this.project_entries.removeElement(app); 
		initComplete();
		redraw();
	}
	
	public void selectApp(App app) {
		view_app_container.setAppReference(app);
		this.content.removeAll();
		
		if(app == null) {
			initComplete();
		} else {
			this.content.add(view_app_container);
			redraw();
		}
	}
	
	public void redraw() {
		if(this.project_entries.size() <= 0) {
			this.splitpanel.setDividerSize(0);
			this.splitpanel.setDividerLocation(0);
		} else {
			if(this.splitpanel.getDividerLocation() <= 2) {
				this.splitpanel.setDividerLocation(200);
			}
			
			this.splitpanel.setDividerSize(2);			
		}

		this.projects.revalidate();
		this.projects.repaint();
		this.content.revalidate();
		this.content.repaint();
		this.splitpanel.revalidate();
		this.splitpanel.repaint();
		revalidate();
		repaint();
	}
	
	public void initComplete() {
		this.content.removeAll();
		
		if(this.project_entries.size() <= 0) {
			this.content.add(view_no_apps);
			this.projects.setPreferredSize(new Dimension(0, 0));
		} else {
			this.projects.setPreferredSize(new Dimension(100, 0));
			this.content.add(view_no_app_selected);
		}

		redraw();
	}

	public void removeSelection() {
		this.projects.clearSelection();
		selectApp(null);
	}
	
	public void reloadApps(ArrayList<App> apps) {
		this.project_entries.removeAllElements();
		
		for(App app : apps) {
			addApp(app);
		}
		
		initComplete();
	}
}
