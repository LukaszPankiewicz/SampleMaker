package buttons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class LoadListener implements ActionListener {

    private final List<JCheckBox> checkFieldList;

    public LoadListener(List<JCheckBox> checkFieldList) {
        this.checkFieldList = checkFieldList;
    }

    public List<JCheckBox> getCheckFieldList() {
        return checkFieldList;
    }

    public void actionPerformed(ActionEvent e) {

        boolean[] fieldCondition = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("sample.ser"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            fieldCondition = (boolean[]) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        for (int i = 0; i < 256; i++) {
            JCheckBox field = (JCheckBox) checkFieldList.get(i);
            if (fieldCondition[i]) {
                field.setSelected(true);
            } else {
                field.setSelected(false);
            }
        }
    }
}
