package buttons;

import gui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class SaveListener extends GUI implements ActionListener {

    GUI gui;
    public SaveListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public List<JCheckBox> getCheckFieldList() {
        return super.getCheckFieldList();
    }
    

    public void actionPerformed(ActionEvent e) {

        boolean[] fieldCondition = new boolean[256];

        for (int i = 0; i < 256; i++) {
            JCheckBox field = (JCheckBox) checkFieldList.get(i);
            if (field.isSelected()) {
                fieldCondition[i] = true;
            }
        }
    }
}
