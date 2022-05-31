import GUI.ViewUserInterfaceGraphical;
import GUI.ViewUserInterfaceHeadless;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws
            UnsupportedLookAndFeelException,
            ClassNotFoundException,
            InstantiationException,
            IllegalAccessException,
            InterruptedException {

        if (args.length == 0) {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.invokeLater(ViewUserInterfaceGraphical::new);

        } else {

            double timeToRun;
            int threadCount;

            switch (args.length) {

                case 1:

                    timeToRun = Double.parseDouble(args[0]);
                    threadCount = Runtime.getRuntime().availableProcessors();
                    break;

                case 2:
                default:

                    timeToRun = Double.parseDouble(args[0]);
                    threadCount = Integer.parseInt(args[1]);
                    break;

            }

            new ViewUserInterfaceHeadless(timeToRun, threadCount).run();
            System.exit(0);

        }

    }

}
