package GUI;

import Find.ControllerFind;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewUserInterfaceGraphical extends JFrame implements ViewUserInterface {

    private JPanel panelMain;
    private JLabel labelFoundPowers;
    private JLabel labelGitHubLink;
    private JLabel labelTimeToRun;
    private JTextField textFieldTimeToRun;
    private JLabel labelThreadCount;
    private JTextField textFieldThreadCount;
    private JButton buttonStart;
    private JLabel labelTimeRemaining;
    private JLabel labelCountDown;

    private JList<String> listFoundPowers;
    private ConcurrentSkipListSet<Integer> foundPowersSet;
    private DefaultListModel<String> listFoundPowersModel;

    private ExecutorService controllerService;
    public static boolean isControllerRunning;

    public ViewUserInterfaceGraphical() {

        SwingUtilities.invokeLater(this::initUserInterface);
        SwingUtilities.invokeLater(this::initNonUserInterface);

        buttonStart.addActionListener(e -> {

            if (isControllerRunning) return;

            this.controllerService = Executors.newSingleThreadExecutor();

            double timeToRun;
            int threadCount;

            try {
                timeToRun = Double.parseDouble(textFieldTimeToRun.getText());
                if (timeToRun <= 0.0d) throw new NumberFormatException();
            } catch (NumberFormatException ignored) {
                showMessage("Runtime must be a number and greater than zero!", false);
                return;
            }

            try {
                threadCount = Integer.parseInt(textFieldThreadCount.getText());
                if (threadCount < 1) throw new NumberFormatException();
            } catch (NumberFormatException ignored) {
                showMessage("Thread Count must be a number and greater than zero!", false);
                return;
            }

            ControllerFind controllerFind = new ControllerFind(false, this, timeToRun, threadCount);

            this.controllerService.submit(controllerFind);
            this.controllerService.shutdown();

        });

    }

    @Override
    public void updateFoundPowersList(int foundPower) {

        SwingUtilities.invokeLater(() -> {

            if (foundPowersSet.contains(foundPower)) return;

            this.foundPowersSet.add(foundPower);

            this.listFoundPowersModel.clear();
            for (int x : foundPowersSet) this.listFoundPowersModel.addElement("2^" + x);

        });

    }

    @Override
    public void updateTimeRemaining(String timeRemaining) {

        SwingUtilities.invokeLater(() -> this.labelCountDown.setText(timeRemaining));

    }

    @Override
    public void showMessage(String messageText, boolean benchmarkResult) {

        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                this,
                messageText,
                benchmarkResult ? "Benchmark Result" : "Warning",
                benchmarkResult ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE));

    }

    private void initUserInterface() {

        this.labelFoundPowers.setText("Found Powers");
        this.labelGitHubLink.setText("https://github.com/YorkshireDev");

        DefaultListCellRenderer listFoundPowersRenderer = (DefaultListCellRenderer) listFoundPowers.getCellRenderer();
        listFoundPowersRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        this.listFoundPowersModel = new DefaultListModel<>();
        this.listFoundPowers.setModel(listFoundPowersModel);

        this.labelTimeToRun.setText("Run Time (Seconds)");
        this.textFieldTimeToRun.setText("60");

        this.labelThreadCount.setText("Thread Count");
        this.textFieldThreadCount.setText(String.valueOf(Runtime.getRuntime().availableProcessors()));

        this.buttonStart.setText("Start");
        this.labelTimeRemaining.setText("Time Remaining");
        this.labelCountDown.setText("N/A");

        this.setContentPane(panelMain);
        this.setTitle("Temporal");
        this.setPreferredSize(new Dimension(480, 240));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);

    }

    private void initNonUserInterface() {

        this.foundPowersSet = new ConcurrentSkipListSet<>();
        isControllerRunning = false;

    }

}
