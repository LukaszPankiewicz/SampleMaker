package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    JPanel panel;
    JFrame frame;
    List<JCheckBox> checkFieldList;

    String[] instrumentsName = {"Bass drum", "Low Floor Tom", "Pedal Hi-hat", "Low Tom", "Ride Cymbal", "Chinese Cymbal",
            "Ride Bell", "Tambourine", "Splash Cymbal", "Cowbell", "Crash Cymbal 2", "Vibra Slap",
            "Ride Cymbal 2", "Low Bongo", "Mute High Conga", "Open Cuica"};
    int[] instruments = {36, 41, 44, 45, 51, 52, 53, 54, 55, 56, 57, 58, 59, 61 ,62, 79};

    public GUI() {

    frame = new JFrame("Sample-Maker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    BorderLayout layout = new BorderLayout();
    JPanel backgroundPanel = new JPanel(layout);
        backgroundPanel.setBackground(Color.LIGHT_GRAY);
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    Box buttonsField = new Box(BoxLayout.Y_AXIS);
    checkFieldList = new ArrayList<JCheckBox>();

    JButton start = new JButton("Start");
        start.setForeground(Color.WHITE);
        start.setBackground(Color.darkGray);
        buttonsField.add(start);

    JButton stop = new JButton("Stop");
        stop.setForeground(Color.WHITE);
        stop.setBackground(Color.darkGray);
        buttonsField.add(stop);

    JButton faster = new JButton("Tempo up");
        faster.setForeground(Color.WHITE);
        faster.setBackground(Color.darkGray);
        buttonsField.add(faster);

    JButton slower = new JButton("Tempo down");
        slower.setForeground(Color.WHITE);
        slower.setBackground(Color.darkGray);
        buttonsField.add(slower);

    JButton save = new JButton("Save sample");
        save.setForeground(Color.WHITE);
        save.setBackground(Color.darkGray);
        buttonsField.add(save);

    JButton load = new JButton("Load sample");
        load.setForeground(Color.WHITE);
        load.setBackground(Color.darkGray);
        buttonsField.add(load);

    JButton random = new JButton("Play random sample");
        random.setForeground(Color.WHITE);
        random.setBackground(Color.darkGray);
        buttonsField.add(random);

    JButton exit = new JButton("Exit");
        exit.setForeground(Color.WHITE);
        exit.setBackground(Color.darkGray);
        buttonsField.add(exit);

    Box namesField = new Box(BoxLayout.Y_AXIS);
        namesField.setFont(new Font("New Courier", Font.BOLD,12));
        for (int i = 0; i < 16; i++) {
        namesField.add(new Label(instrumentsName[i]));
    }

        backgroundPanel.add(BorderLayout.EAST, buttonsField);
        backgroundPanel.add(BorderLayout.WEST, namesField);
        frame.getContentPane().add(backgroundPanel);

    GridLayout checkFieldGrid = new GridLayout(16,16);

    panel = new JPanel(checkFieldGrid);
        backgroundPanel.add(BorderLayout.CENTER, panel);

        for (int i = 0; i < 256; i++) {
        JCheckBox jCheckBox = new JCheckBox();
        jCheckBox.setBackground(Color.lightGray);
        jCheckBox.setSelected(false);
        checkFieldList.add(jCheckBox);
        panel.add(jCheckBox);
    }

        frame.pack();
        frame.setVisible(true);
    }
}
