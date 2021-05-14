import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SampleMaker implements MetaEventListener {

    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JPanel panel;
    JFrame frame;
    List<JCheckBox> checkFieldList;

    String[] instrumentsName = {"Bass drum", "Low Floor Tom", "Pedal Hi-hat", "Low Tom", "Ride Cymbal", "Chinese Cymbal",
            "Ride Bell", "Tambourine", "Splash Cymbal", "Cowbell", "Crash Cymbal 2", "Vibra Slap",
            "Ride Cymbal 2", "Low Bongo", "Mute High Conga", "Open Cuica"};
    int[] instruments = {36, 41, 44, 45, 51, 52, 53, 54, 55, 56, 57, 58, 59, 61 ,62, 79};


    public static void main(String[] args) {
        new SampleMaker().GUI();
    }
    public void GUI() {

        frame = new JFrame("Sample-Maker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel backgroundPanel = new JPanel(layout);
        backgroundPanel.setBackground(Color.LIGHT_GRAY);
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        checkFieldList = new ArrayList<JCheckBox>();
        Box buttonsField = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("Start");
        start.setForeground(Color.WHITE);
        start.setBackground(Color.darkGray);
        buttonsField.add(start);
        start.addActionListener(new StartListener());

        JButton stop = new JButton("Stop");
        stop.setForeground(Color.WHITE);
        stop.setBackground(Color.darkGray);
        buttonsField.add(stop);
        stop.addActionListener(new StopListener());

        JButton faster = new JButton("Tempo up");
        faster.setForeground(Color.WHITE);
        faster.setBackground(Color.darkGray);
        buttonsField.add(faster);
        faster.addActionListener(new FasterListener());

        JButton slower = new JButton("Tempo down");
        slower.setForeground(Color.WHITE);
        slower.setBackground(Color.darkGray);
        buttonsField.add(slower);
        slower.addActionListener(new SlowerListener());

        JButton save = new JButton("Save sample");
        save.setForeground(Color.WHITE);
        save.setBackground(Color.darkGray);
        buttonsField.add(save);
        save.addActionListener(new SaveTrack());

        JButton load = new JButton("Load sample");
        load.setForeground(Color.WHITE);
        load.setBackground(Color.darkGray);
        buttonsField.add(load);
        load.addActionListener(new LoadTrack());

        JButton exit = new JButton("Exit");
        exit.setForeground(Color.WHITE);
        exit.setBackground(Color.darkGray);
        buttonsField.add(exit);
        exit.addActionListener(new CloseListener());

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
        configurationMidi();

        frame.pack();
        frame.setVisible(true);
    }

    public void configurationMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeTrackAndPlay() {
        int[] trackList = null;
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
            trackList = new int[16];

            int key = instruments[i];

            for (int j = 0; j < 16; j++) {
                JCheckBox jc = (JCheckBox) checkFieldList.get(j + (16 * i));
                if (jc.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }
            makeTrack(trackList);
            track.add(createEvent(176, 1, 127, 0, 16));
        }
        track.add(createEvent(192, 9, 1, 0, 15));
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void meta(MetaMessage meta) {

    }

    public class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            makeTrackAndPlay();
        }
    }

    public class StopListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }
    }
    public class FasterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            float faster = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) ((float) faster * 1.03));
        }
    }
    public class SlowerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            float slower = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) ((float) slower * .97));
        }
    }
    public class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public void makeTrack(int[] list) {
        for (int i = 0; i < 16; i++) {
            int key = list[i];
            if (key != 0) {
                track.add(createEvent(144, 9, key, 100, i));
                track.add(createEvent(128, 9, key, 100, i + 1));
            }
        }
    }

    public static MidiEvent createEvent(int plc, int channel, int first, int second, int tact) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(plc, channel, first, second);
            event = new MidiEvent(a, tact);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    public class SaveTrack implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            boolean[] fieldCondition = new boolean[256];

            for (int i = 0; i < 256; i++) {
                JCheckBox field = (JCheckBox) checkFieldList.get(i);
                if (field.isSelected()) {
                    fieldCondition[i] = true;
                }
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File("save.ser"));
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(fieldCondition);
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }

    public class LoadTrack implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            boolean[] fieldCondition = null;
            try {
                FileInputStream fileInputStream = new FileInputStream(new File("save.ser"));
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
            sequencer.stop();
            makeTrackAndPlay();
        }
    }
}