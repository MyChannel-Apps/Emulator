package de.mca.ui.font;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class Glyphicons {
	private static String path		= "/de/mca/resources/fonts/glyphicons.ttf";
	private static float font_size	= 14f;
	private static int font_style	= Font.PLAIN;
	private static Font font;
	
	static {
		try (
			InputStream stream	= Glyphicons.class.getResourceAsStream(path)) {
			font				= Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch(IOException | FontFormatException exp) {
            exp.printStackTrace();
        }
	}
	
	public static Font get() {
		if(font == null) {
			return null;
		}

		return font.deriveFont(font_style, font_size);
	}
	
	public static Font get(int style, float size) {
		font_size	= size;
		font_style	= style;
		return get();
	}
	
	public static Font get(int style) {
		font_style	= style;
		return get();		
	}
	
	public static Font get(float size) {
		font_size	= size;
		return get();		
	}
}
