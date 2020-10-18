package classes;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    JButton saveButton;
    JButton resetButton;
    JButton loadButton;
    public ControlPanel(DrawingFrame drawFrame){
        saveButton=new JButton("SAVE");
        resetButton=new JButton("RESET");
        loadButton=new JButton("LOAD");
        drawFrame.add(this, BorderLayout.PAGE_END);
    }
}
