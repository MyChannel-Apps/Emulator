package de.mca.ui;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SplitPanel extends Box {
	Box container;
    Dimension minSize = new Dimension(400, 300);
    
	public SplitPanel(int axis){
        super(axis);

		setOpaque(true);
		setBorder(new EmptyBorder(10, 10, 10, 10));
    }
	
	@Override
	public Component add(Component new_c) {
		 Box newContainer;
         if(((BoxLayout) getLayout()).getAxis() == BoxLayout.X_AXIS){
             newContainer = Box.createVerticalBox();
         } else{
             newContainer = Box.createHorizontalBox();
         }

         for(Component c : getComponents()){
        	 
             remove(c);

			if(c instanceof Box) {
				for(Component a : ((Box) c).getComponents()){
					if(a instanceof SplitComponent) {
						((SplitComponent) a).lastComponent(false);
					}
				}
			}
             newContainer.add(c);
         }

         ((SplitComponent) new_c).lastComponent(true);
         newContainer.add(new_c);
         add(newContainer, 0);
         setAlignmentX(Box.LEFT_ALIGNMENT);
         revalidate();
		
		return new_c;
	}

    @Override
    public Dimension getPreferredSize() {
        Dimension result = super.getPreferredSize();
        result.width = result.width > minSize.width ? result.width : minSize.width;
        result.height = result.height > minSize.height ? result.height : minSize.height;
        return result;
    }
}
