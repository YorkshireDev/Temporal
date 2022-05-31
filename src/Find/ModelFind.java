package Find;

import java.math.BigInteger;

class ModelFind implements Runnable {

    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger TWO = BigInteger.TWO;
    private static final BigInteger TEN = BigInteger.TEN;

    private final int xStart;
    private final int xOffset;

    ModelFind(int xStart, int xOffset) {

        this.xStart = xStart;
        this.xOffset = xOffset;

    }

    @Override
    public void run() {

        try { ControllerFind.goLatch.await(); }
        catch (InterruptedException e) { throw new RuntimeException(e); }

        int x = xStart;
        int amountProcessed = 0;

        BigInteger currentNumber;
        BigInteger[] currentNumberDivMod;

        while (! ControllerFind.stopSignal) {

            currentNumber = TWO.pow(x);

            while (currentNumber.compareTo(ZERO) > 0) {

                currentNumberDivMod = currentNumber.divideAndRemainder(TEN);

                if (! currentNumberDivMod[1].mod(TWO).equals(ZERO)) break;

                currentNumber = currentNumberDivMod[0];

            }

            if (currentNumber.equals(ZERO)) ControllerFind.foundPowersQueue.add(x);

            x += xOffset;
            amountProcessed++;

        }

        ControllerFind.amountPowersProcessedQueue.add(amountProcessed - 1);
        ControllerFind.largestPowerProcessedQueue.add(x - xOffset);

        ControllerFind.stopLatch.countDown();

    }

}
