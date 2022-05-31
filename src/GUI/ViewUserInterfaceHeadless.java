package GUI;

import Find.ControllerFind;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewUserInterfaceHeadless implements ViewUserInterface {

    private final double timeToRun;
    private final int threadCount;

    private final ConcurrentSkipListSet<Integer> foundPowersSet;
    private final List<String> foundPowersList;

    private String timeRemaining;

    private ExecutorService controllerService;
    public static boolean isControllerRunning;

    public ViewUserInterfaceHeadless(double timeToRun, int threadCount) {

        this.timeToRun = timeToRun;
        this.threadCount = threadCount;

        this.controllerService = Executors.newSingleThreadExecutor();
        isControllerRunning = false;

        this.foundPowersSet = new ConcurrentSkipListSet<>();
        this.foundPowersList = new ArrayList<>();

        this.timeRemaining = "";

    }

    @Override
    public void updateFoundPowersList(int foundPower) {

        if (foundPowersSet.contains(foundPower)) return;

        this.foundPowersSet.add(foundPower);

        this.foundPowersList.clear();
        for (int x : foundPowersSet) this.foundPowersList.add("2^" + x);

    }

    @Override
    public void updateTimeRemaining(String timeRemaining) {

        this.timeRemaining = timeRemaining;

    }

    @Override
    public void showMessage(String messageText, boolean benchmarkResult) {

        if (! benchmarkResult) return;

        System.out.println();
        System.out.println(messageText);
        System.out.println();

    }

    public void run() throws InterruptedException {

        ControllerFind controllerFind = new ControllerFind(true, this, timeToRun, threadCount);

        this.controllerService.submit(controllerFind);
        this.controllerService.shutdown();

        while (isControllerRunning) {

            System.out.println("Found Powers:");
            System.out.println();
            foundPowersList.forEach(System.out::println);
            System.out.println();
            System.out.println("Time Remaining: " + timeRemaining);
            System.out.println();

            Thread.sleep(1000L);

        }

        this.controllerService = null;

    }

}
