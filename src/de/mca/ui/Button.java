package de.mca.ui;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import de.mca.interfaces.Callback;

@SuppressWarnings("serial")
public class Button extends JButton {
	public Button(Callback normal, Callback events, Callback callback) {
		Button instance = this;
		
		setFocusPainted(false);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setBorder(new EmptyBorder(0, 0, 0, 0));
	    setCursor(new Cursor(Cursor.HAND_CURSOR));
	    setMargin(new Insets(0, 0, 0, 0));
	    setLocation(new Point(-10, -10));
	    setRolloverEnabled(true);
	    
	    if(normal != null) {
	    	normal.run(instance);
	    }
	    
	    if(events != null) {
			events.run(instance);
		}
	    
	    addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(events != null) {
	    			events.run(instance);
	    		}
	    		
	    		if(callback != null) {
	    			callback.run(instance);
	    		}
	    	}
	    });
	}
	
	public Button(Callback normal, Callback events, Callback callback, Window event) {
		this(normal, events, callback);
		
		Button instance = this;
		
		event.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				if(events != null) {
	    			events.run(instance);
	    		}
			}
		});
	}

	public void updateIcons(String normal, String hover) {
	    setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(normal))));
	    setRolloverIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(hover))));
	}
}
