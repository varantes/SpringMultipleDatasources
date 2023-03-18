package br.com.neto.springmultipledatasources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class MultithreadTest {

    private static final Logger LOG = LogManager.getLogger();

    @SuppressWarnings("unused")
    void aTest() {
        // Create a list of elements to process
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("Element " + i);
        }

        // Define the number of threads to use
        int numThreads = 3;

        // Divide the list into sublists and process each sublist in parallel using streams
        int chunkSize = 8;
        AtomicInteger index = new AtomicInteger(0);
        List<List<String>> sublists = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            int startIndex = index.getAndAdd(chunkSize);
            int endIndex = Math.min(startIndex + chunkSize, list.size());
            sublists.add(list.subList(startIndex, endIndex));
        }
        sublists.parallelStream().forEach(sublist -> {
            sublist.forEach(element -> {
                // Process each element in the sublist
                // Do something with the element
                LOG.info("Thread " + Thread.currentThread().threadId() + " processed element " + element);
            });
        });

        // Print a message indicating that all processing is complete
        LOG.info("All processing is complete.");

        Assertions.assertTrue(true, "Aeaeaeaeaeae!!!");
    }

    @Test
    void bTest() {
        // Create a list of elements to process
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Element " + i);
        }

        // Define the number of threads to use
        int numThreads = 3;

        try (ForkJoinPool myPool = new ForkJoinPool(numThreads)) {

            myPool.submit(() -> list.parallelStream().forEach(element -> {
                sleepBetweenOneAndTwoSeconds(element);
                LOG.info("Thread " + Thread.currentThread().threadId() + " processed element " + element);
            }));

            // Shut down the executor and wait for all tasks to complete
            myPool.shutdown();
            while (!myPool.isTerminated()) {
                Thread.yield();
            }
        }

        // Print a message indicating that all processing is complete
        LOG.info("All processing is complete.");
    }
    
    @Test
    void testExceptions() {
        final List<String> list = new ArrayList<>() {{
            for (int i = 0; i < 20; i++) {
                add("Element " + i);
            }
        }};

        // Define the number of threads to use
        int numThreads = 3;

        try (ForkJoinPool myPool = new ForkJoinPool(numThreads)) {

            ForkJoinTask<?> submit = myPool.submit(() -> list.parallelStream().forEach(element -> {

                if (element.contains("1") || element.contains("2")) {
                    throw new RuntimeException("Exceção no " + element);
                }

                sleepBetweenOneAndTwoSeconds(element);

                LOG.info("Thread " + Thread.currentThread().threadId() + " processed element " + element);

            }));

            submit.get(1, TimeUnit.HOURS);

            Assertions.fail("Should have thrown a RuntimeException");

            myPool.shutdown();

            while (!myPool.isTerminated()) {
                Thread.yield();
            }

        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            LOG.error(e.getMessage(), e);
        }

        // Print a message indicating that all processing is complete
        LOG.info("All processing is complete.");
    }

    private static void sleepBetweenOneAndTwoSeconds(String element) {
        try {
            int randomNumber = new Random().nextInt(1000) + 1000;
            LOG.info("{} - Sleeping for {} ms", element, randomNumber);
            Thread.sleep(randomNumber);
        } catch (InterruptedException ignore) {
        }
    }

}

