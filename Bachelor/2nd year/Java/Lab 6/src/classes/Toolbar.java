package classes;

import javax.swing.*;
import java.awt.*;

public class Toolbar extends JToolBar {
    JColorChooser colorChooser;
    public Toolbar(DrawingFrame drawFrame){
        colorChooser=new JColorChooser();
        drawFrame.add(this, BorderLayout.PAGE_START);
    }
}
