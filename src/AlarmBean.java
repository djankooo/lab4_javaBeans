
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmBean extends javax.swing.JFrame implements ActionListener {

    private Color backgroundColor;

    private JLabel currentTimeJLabel;
    private JLabel currentTimeDataJLabel;

    private JLabel wakingTimeJLabel;
    private JLabel wakingTimeDataJLabel;

    private JLabel wakeUpLabel;

    private Date wakingHourDate = null;

    private Date now = new Date();

    private String currentDatePattern = getAmericanPattern();

    private int wakingMinute;
    private int wakingHour;
    private int timeOfSnooze = 2;

    private SimpleDateFormat sdf;

    private JButton setWakingTimeButton;
    private JButton SnoozeButton;

    private boolean once = true;

    private Clip clip;

    public AlarmBean() {
        clockLabel();
        initComponents();
    }

    public int getWakingHour() {
        return wakingHour;
    }

    public void setWakingHour(int wakingHour) {
        this.wakingHour = wakingHour;
    }

    public int getWakingMinute() {
        return wakingMinute;
    }

    public void setWakingMinute(int wakingMinute) {
        this.wakingMinute = wakingMinute;
    }

    public String getCurrentDatePattern() {
        return currentDatePattern;
    }

    public void setCurrentPattern(String currentPattern) {
        this.currentDatePattern = currentPattern;
    }

    public String getEuropeanPattern() {
        return "kk:mm";
    }

    public String getAmericanPattern() {
        return "hh:mm a";
    }

    public int getTimeOfSnooze() {
        return timeOfSnooze;
    }

    public void setTimeOfSnooze(int timeOfSnooze) {
        this.timeOfSnooze = timeOfSnooze;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    private void initComponents() {

        wakeUpLabel = new JLabel("wakeUp : ");
        wakeUpLabel.setFont(new Font("sans-serif", Font.PLAIN, 40));

        currentTimeJLabel = new JLabel("Aktualny czas : ");
        currentTimeJLabel.setFont(new Font("sans-serif", Font.PLAIN, 40));

        currentTimeDataJLabel = new JLabel();
        currentTimeDataJLabel.setFont(new Font("sans-serif", Font.PLAIN, 40));

        wakingTimeJLabel = new JLabel("Czas budzenia : ");
        wakingTimeJLabel.setFont(new Font("sans-serif", Font.PLAIN, 40));

        wakingTimeDataJLabel = new JLabel();
        wakingTimeDataJLabel.setFont(new Font("sans-serif", Font.PLAIN, 40));

        setWakingTimeButton = new JButton("Set waking time!");
        SnoozeButton = new JButton("Snooze!");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(backgroundColor);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        currentTimeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        getContentPane().add(currentTimeJLabel, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        getContentPane().add(currentTimeDataJLabel, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        wakingTimeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        getContentPane().add(wakingTimeJLabel, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        getContentPane().add(wakingTimeDataJLabel, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        getContentPane().add(setWakingTimeButton, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        getContentPane().add(SnoozeButton, gridBagConstraints);



        setWakingTimeButton.addActionListener(e -> {
            JTextField hoursJTextField = new JTextField("12");
            hoursJTextField.setPreferredSize(new Dimension(60,20));
            JTextField minutesJTextField = new JTextField("30");
            minutesJTextField.setPreferredSize(new Dimension(60,20));

            JPanel myPanel = new JPanel();
            myPanel.add(hoursJTextField);
            myPanel.add(minutesJTextField);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Set waking hours", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                setWakingHour(Integer.parseInt(hoursJTextField.getText()));
                setWakingMinute(Integer.parseInt(minutesJTextField.getText()));

                wakingHourDate = new Date(now.getYear(),now.getMonth(),now.getDay(),wakingHour, wakingMinute, now.getSeconds());
                wakingTimeDataJLabel.setText(sdf.format(wakingHourDate));
            }
        });

        SnoozeButton.addActionListener(e -> {
            clip.stop();
            setWakingMinute(wakingMinute+timeOfSnooze);
            wakingHourDate = new Date(now.getYear(),now.getMonth(),now.getDay(),wakingHour, wakingMinute, now.getSeconds());
            wakingTimeDataJLabel.setText(sdf.format(wakingHourDate));
            once = true;
        });

        setSize(800, 600);
    }

    public void playSound(String soundName) {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
            clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.loop(10);
        }
        catch(Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace( );
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AlarmBean().setVisible(true));
    }

    public void clockLabel() {

        sdf = new SimpleDateFormat(currentDatePattern);
        setFont(new Font("sans-serif", Font.PLAIN, 40));

        Timer t = new Timer(1000, this);
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Date d = new Date();
        currentTimeDataJLabel.setText(sdf.format(d));

        if(wakingHourDate!=null && once) {
            if (d.getHours() == wakingHourDate.getHours() && d.getMinutes() == wakingHourDate.getMinutes()) {
                playSound("buzz.wav");
                once = false;
            }
        }
    }
}
