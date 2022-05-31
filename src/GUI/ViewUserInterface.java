package GUI;

import javax.swing.*;

public abstract class ViewUserInterface extends JFrame {

    public static boolean isControllerRunning;

    public abstract void updateFoundPowersList(int foundPower);
    public abstract void updateTimeRemaining(String timeRemaining);
    public abstract void showMessage(String messageText, boolean benchmarkResult);

}
