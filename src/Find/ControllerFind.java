package Find;

import GUI.ViewUserInterface;

import java.util.Queue;
import java.util.concurrent.*;

public class ControllerFind implements Runnable {

    private static final long TIME_DECREMENT = 500L; // milliseconds

    private final ViewUserInterface viewUserInterface;

    private final double timeToRun;
    private final int threadCount;

    private final ExecutorService modelService;
    static volatile boolean stopSignal;
    static CountDownLatch goLatch;
    static CountDownLatch stopLatch;

    static Queue<Integer> foundPowersQueue;
    static Queue<Integer> amountPowersProcessedQueue;
    static Queue<Integer> largestPowerProcessedQueue;

    public ControllerFind(ViewUserInterface viewUserInterface, double timeToRun, int threadCount) {

        this.viewUserInterface = viewUserInterface;

        this.timeToRun = timeToRun * 1000.0d;
        this.threadCount = threadCount;

        this.modelService = Executors.newFixedThreadPool(threadCount);
        stopSignal = false;
        goLatch = new CountDownLatch(1);
        stopLatch = new CountDownLatch(threadCount);

        foundPowersQueue = new ConcurrentLinkedQueue<>();
        amountPowersProcessedQueue = new ConcurrentLinkedQueue<>();
        largestPowerProcessedQueue = new ConcurrentLinkedQueue<>();

    }

    @Override
    public void run() {

        ViewUserInterface.isControllerRunning = true;

        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        long timeRemaining = (long) timeToRun;

        for (int i = 0; i < threadCount; i++) this.modelService.submit(new ModelFind(i, threadCount));

        goLatch.countDown();

        while (timeRemaining > 0) {

            try {

                viewUserInterface.updateTimeRemaining(String.valueOf(timeRemaining / 1000L));

                foundPowersQueue.forEach(viewUserInterface::updateFoundPowersList);

                Thread.sleep(TIME_DECREMENT);
                timeRemaining -= TIME_DECREMENT;

            } catch (InterruptedException e) { throw new RuntimeException(e); }

        }

        String benchmarkResult;

        try {

            viewUserInterface.updateTimeRemaining("Stopping...");

            stopSignal = true;
            stopLatch.await();

            int powersProcessedSum = 0;
            int largestPowerProcessed = 0;

            for (int powerProcessedTotal : amountPowersProcessedQueue) powersProcessedSum += powerProcessedTotal;
            for (int maxPowerProcessed : largestPowerProcessedQueue) if (maxPowerProcessed > largestPowerProcessed) largestPowerProcessed = maxPowerProcessed;
            int powerProcessedPerSecond = (int) (((double) powersProcessedSum / timeToRun) * 1000);

            benchmarkResult =
                    "Total Processed: " + powersProcessedSum
                            + System.lineSeparator() + System.lineSeparator()
                            + "Total Processed Per Second: " + powerProcessedPerSecond + " /s"
                            + System.lineSeparator() + System.lineSeparator()
                            + "Largest Power Processed: 2^" + largestPowerProcessed;

        } catch (InterruptedException e) { throw new RuntimeException(e);}

        viewUserInterface.updateTimeRemaining("N/A");

        viewUserInterface.showMessage(benchmarkResult, true);

        ViewUserInterface.isControllerRunning = false;

    }

}
