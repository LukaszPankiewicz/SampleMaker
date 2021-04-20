package buttons;

import gui.GUI;

import javax.imageio.IIOException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;


public class SaveListener implements ActionListener {


    private final List<JCheckBox> checkFieldList;

    public SaveListener(List<JCheckBox> checkFieldList) {
        this.checkFieldList = checkFieldList;
    }

    public List<JCheckBox> getCheckFieldList() {
        return checkFieldList;
    }

    public void actionPerformed(ActionEvent e) {

        boolean[] fieldCondition = new boolean[256];

        for (int i = 0; i < 256; i++) {
            JCheckBox field = getCheckFieldList().get(i);
            if (field.isSelected()) {
                fieldCondition[i] = true;
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("sample.ser"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(fieldCondition);
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}
