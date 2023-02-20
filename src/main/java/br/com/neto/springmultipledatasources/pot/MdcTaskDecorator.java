package br.com.neto.springmultipledatasources.pot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MdcTaskDecorator implements TaskDecorator {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> contextMap = ThreadContext.getContext();
        LOG.info("Saving tenant information for async thread...");
        return () -> {
            try {
                ThreadContext.putAll(contextMap);
                LOG.info("Restoring tenant information for async thread...");
                runnable.run();
            } finally {
                ThreadContext.clearAll();
            }
        };
    }
}
