package br.com.neto.springmultipledatasources.pot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.internal.guava.Maps;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping(path = "api")
public class FirstController {

    private static final Logger LOG = LogManager.getLogger();

    private final FirstService firstService;

    public FirstController(FirstService firstService) {
        this.firstService = firstService;
    }

    @GetMapping
    public String hi() {
        return firstService.hi("Val");
    }

    @GetMapping("thread")
    public Map.Entry<String, Instant> threadPropagation() {
        LocaleContextHolder.setLocale(LocaleContextHolder.getLocale(), true);

        LOG.info("Calling firstService.asyncFunction() ...");
        firstService.asyncFunction();

        LOG.info("Returning ...");
        return Maps.immutableEntry("instant_now", Instant.now());
    }

}
