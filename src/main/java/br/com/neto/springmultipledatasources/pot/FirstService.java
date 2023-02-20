package br.com.neto.springmultipledatasources.pot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
        } catch (InterruptedException ignore) {
        }

        long finalTime = System.nanoTime();
        LOG.info("Returning [elapsed time: {} ms]", (long) ((finalTime - initTime) / 1e6));
    }
}
