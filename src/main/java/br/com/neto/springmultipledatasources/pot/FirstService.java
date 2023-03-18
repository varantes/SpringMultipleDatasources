package br.com.neto.springmultipledatasources.pot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FirstService {

    private static final Logger LOG = LogManager.getLogger();

    @Async
    public void asyncFunction() {

        LOG.info("Executing...");
        long initTime = System.nanoTime();

        try {
            LOG.info("Simulate 2s execution");
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            LOG.warn("Thread interrupted", e);
        }

        long finalTime = System.nanoTime();
        LOG.info("Returning [elapsed time: {} ms]", (long) ((finalTime - initTime) / 1e6));
    }

    public String hi(String name) {
        return "Hello %s. Now is %s".formatted(name, LocalDateTime.now());
    }
}
