package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import classes.NodeShape;
import java.util.List;

public class Canvas extends JPanel {
    BufferedImage image;
    double radius;
    List<NodeShape> nodeList;

    public Canvas(){
        nodeList=new ArrayList<>();
        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
    }
    public void addMouseClick(){
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                drawNode(e.getX(), e.getY());
            }
        });
    }

    @Override
    public void paintComponent(Graphics graphics){

    }

    public void drawNode(int x, int y) {
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Random rand = new Random();
        graphics.setColor(new Color(rand.nextInt(0xFFFFFF)));
        NodeShape node=new NodeShape(x,y,radius);
        graphics.fill(node);
        nodeList.add(node);
    }
}
