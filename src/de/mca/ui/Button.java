package de.mca.ui;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import de.mca.interfaces.Callback;
import de.mca.ui.font.Awesome;

@SuppressWarnings("serial")
public class Button extends JButton {
	private Color color 	= Color.NONE;
	private String icon 	= "";
	public enum Color {
		NONE,
		GREEN
	};
	
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

	public Button(String text) {
		super(text);
		setBorder(new EmptyBorder(8, 20, 8, 30));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public Button(String text, Color color, String icon) {
		this(text);
		
		this.color	= color;
		this.icon	= icon;
	}

	 @Override
	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 
		 if(this.color == Color.NONE) {
			 return;
		 }
		 
		 Graphics2D g2d = (Graphics2D) g;
		 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 
		 if(this.color == Color.GREEN) {
			 if(getModel().isRollover()) {
				 g2d.setColor(java.awt.Color.decode("0x86C030"));				 
			 } else {
				 g2d.setColor(java.awt.Color.decode("0x93CE3B"));
			 }
		 }
		 
		 g2d.fillRect(0, 0, getWidth(), getHeight());
		 g2d.setColor(java.awt.Color.WHITE);
		 
		 FontRenderContext render	= new FontRenderContext(null, false, false);
		 Font font 					= Awesome.get(16);
		 Rectangle2D text_r			= getFont().getStringBounds(getText(), render);
		 Rectangle2D text_i			= font.getStringBounds(getText(), render);
		 
		 float text_i_x				= (float) (getWidth() - text_i.getWidth()) / 2;
		 float text_i_y				= (float) (getHeight() - font.getSize()) / 2;
		 float text_r_x				= (float) (getWidth() - text_r.getWidth()) / 2;
		 float text_r_y				= (float) (getHeight() - getFont().getSize()) / 2;
		 int correct				= 10;
		 
		 if(!this.icon.isEmpty()) {
			 text_r_x += 10;
			 g2d.setFont(font);
			 g2d.drawString(this.icon, text_i_x - correct, (float) font.getSize() + text_i_y);
		 }
		 
		 g2d.setFont(new Font("Arial", Font.PLAIN, 15));
		 g2d.drawString(getText(), text_r_x - correct, (float) getFont().getSize() + text_r_y);
	}
	 
	public void updateIcons(String normal, String hover) {
	    setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(normal))));
	    setRolloverIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(hover))));
	}
}
