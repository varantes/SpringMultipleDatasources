package br.com.neto.springmultipledatasources;

import br.com.neto.springmultipledatasources.pot.LogCollector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

class SpringStopwatchTest {

    private static final Logger LOG = LogManager.getLogger();
    private final LogCollector logCollector = LogCollector.build();

    @Test
    void stopwatchTest() {

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
            Assertions.fail(e.getMessage());
        } finally {
            LOG.info("Returning [total elapsed time: {}}", stopwatch.getTotalTimeMillis());
        }
    }

    @Test
    void logCollectorTest() {
        logCollector.addMessage("Testing LogCollector");

        logCollector.stopwatchStart("task1");
        try {
            Thread.sleep(1000);

            logCollector.stopwatchMark("task2");
            Thread.sleep(2000);

            throw new RuntimeException("Apenas para teste");

        } catch (InterruptedException e) {
            Assertions.fail(e.getMessage());
        } catch (RuntimeException e) {
            logCollector.add("exception", "%s: %s".formatted(e.toString(), e.getMessage()));
        } finally {
            logCollector.stopwatchStop(true);
            LOG.info(toJson(logCollector.collect()));
        }
    }

    private String toJson(Object obj) {

        if (obj == null) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        }

        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOG.error("Error marshalling to JSON. Returning toString...");
            return obj.toString();
        }
    }
}
