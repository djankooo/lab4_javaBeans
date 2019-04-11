import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.Customizer;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AlarmBeanCustomizer extends Panel implements Customizer, KeyListener {

    private AlarmBean target;
    private TextField snozeField;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void setObject(Object obj) {
        target = (AlarmBean) obj;
        Label t1 = new Label("Snooze time :");
        add(t1);
        snozeField = new TextField(String.valueOf(target.getTimeOfSnooze()), 20);
        add(snozeField);
        snozeField.addKeyListener(this);
    }

    public Dimension getPreferredSize() {
        return new Dimension(225, 50);
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        Object source = e.getSource();
        if (source == snozeField) {
            String txt = snozeField.getText();
            try {
                target.setTimeOfSnooze((new Integer(txt)).intValue());
            } catch (NumberFormatException ex) {
                //salaryField.setText(String.valueOf(target.getSalary()));
            }
            support.firePropertyChange("", null, null);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        support.removePropertyChangeListener(l);
    }
}
