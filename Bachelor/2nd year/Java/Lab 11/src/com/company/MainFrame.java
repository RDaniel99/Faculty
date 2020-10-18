package com.company;

import panels.ControlPanel;
import panels.DesignPanel;

import javax.swing.*;

import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MainFrame extends JFrame {
    private ControlPanel controlPanel;
    private DesignPanel designPanel;
    public MainFrame() {
        super("Swing Designer");
        init();
    }
    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        controlPanel = new ControlPanel(this);
        designPanel = new DesignPanel(this);
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(designPanel, BorderLayout.CENTER);
        pack();
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    public DesignPanel getDesignPanel() {
        return designPanel;
    }

    public void setDesignPanel(DesignPanel designPanel) {
        this.designPanel = designPanel;
    }
}
