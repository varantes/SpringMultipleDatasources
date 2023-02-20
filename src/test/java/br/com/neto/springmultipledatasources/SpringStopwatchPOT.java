package br.com.neto.springmultipledatasources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.internal.guava.Stopwatch;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

public class SpringStopwatchPOT {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void test() {
        // StopWatch is a simple stop watch, allowing for timing of a number of tasks, exposing total running time and running time for each
        // named task.
        StopWatch stopwatch = new StopWatch("SpringStopwatchPOT Threads");
        stopwatch.start("SpringStopwatchPOT-1");
        performTask1();
        stopwatch.stop();
        stopwatch.start("SpringStopwatchPOT-2");
        performTask2();
        stopwatch.stop();
        LOG.info("CrunchifyThreads took total: " + stopwatch.getTotalTimeSeconds() + " seconds");
        // prettyPrint() return a string with a table describing all tasks performed. For custom reporting, call getTaskInfo() and use the
        // task info directly.
        LOG.info("\n1. prettyPrint Result: " + stopwatch.prettyPrint());
        // Return a short description of the total running time.
        LOG.info("2. Short Summary: " + stopwatch.shortSummary());
        // Return the number of tasks timed.
        LOG.info("3. Total Task Count: " + stopwatch.getTaskCount());
        // Return the name of this task.
        LOG.info("4. Last Task Name: " + stopwatch.getLastTaskInfo().getTaskName());
    }

    private void performTask1() {
        Runnable myRunnable = () -> LOG.info("performTask1 executing this only line");
        new Thread(myRunnable).start();
    }

    private void performTask2() {
        LOG.info("performTask2 running...\n");
        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep(2000);
                LOG.info("Running Loop # " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOG.info("performTask2 finished");
    }

    @Test
    public void b() {

        StopWatch stopwatch = new StopWatch("an-id");

        try {

            stopwatch.start("task 1");
            Thread.sleep(1000);
            stopwatch.stop();

            LOG.info("After sleeping for 1s... [elapsed {}]", stopwatch.getLastTaskTimeMillis());

            stopwatch.start("task 2");
            Thread.sleep(2000);
            stopwatch.stop();

            LOG.info("After sleeping for 2s... [elapsed {}]", stopwatch.getLastTaskTimeMillis());



        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            LOG.info("Returning [total elapsed time: {}}", stopwatch.getTotalTimeMillis());
        }
    }
}
