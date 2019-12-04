package panels;

import com.company.MainFrame;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.List;

public class ControlPanel extends JPanel {
    private final MainFrame frame;
    private final JLabel classNameLabel = new JLabel("Class name");
    private final JTextField classNameField = new JTextField(30);
    private final JLabel textLabel = new JLabel("Default text");
    private final JTextField textField = new JTextField(10);
    private final JButton createButton = new JButton("Add component");
    private final JButton saveButton = new JButton("Save current configuration");
    private final JButton loadButton = new JButton("Load another configuration");
    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        add(classNameLabel); add(classNameField); add(textLabel); add(textField); add(createButton); add(saveButton); add(loadButton);
        createButton.addActionListener(e -> {
            JComponent comp = createDynamicComponent(classNameField.getText());
            setComponentText(comp, textField.getText());
            frame.getDesignPanel().addAtRandomLocation(comp);
        });
        saveButton.addActionListener(e -> {

        });
        loadButton.addActionListener(e ->{

        });
    }
    private JComponent createDynamicComponent(String className) {
        if(className.equals("JButton"))
            return new JButton();
        if(className.equals("JPanel"))
            return new JPanel();
        if(className.equals("JLabel"))
            return new JLabel();
        if(className.equals("JTextField"))
            return new JTextField();
        return null;
    }
    private void setComponentText(JComponent comp, String text) {
        Method[] methodList = comp.getClass().getMethods();
        for (Method method:methodList){
            if (method.getName().equals("setText")){
                try {
                    method.invoke(comp,text);
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }
    }
}