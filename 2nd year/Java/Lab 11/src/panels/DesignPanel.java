package panels;

import com.company.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class DesignPanel extends JPanel implements Serializable {
    public static final int W = 800, H = 600;
    private final MainFrame frame;
    public DesignPanel(MainFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(W, H));
        setLayout(null);
    }
    public void addAtRandomLocation(JComponent comp) {
        Random random=new Random();
        int x = random.nextInt(W);
        int y = random.nextInt(H);
        int w = comp.getPreferredSize().width;
        int h = comp.getPreferredSize().height;
        comp.setBounds(x, y, w, h);
        comp.setToolTipText(comp.getClass().getName());
        this.add(comp);
        frame.repaint();
    }
}
