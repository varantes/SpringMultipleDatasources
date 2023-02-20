package br.com.neto.springmultipledatasources.pot;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@Order(2)
public class RequestResponseLoggingFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        configCorrelationId(req);

        LOG.info("Executing Request {} : {}", req.getMethod(), req.getRequestURI());

        long initTime = System.nanoTime();
        try {
            chain.doFilter(request, response);
        } finally {
            long finalTime = System.nanoTime();
            LOG.info("Finishing Request {} : {} after {} ms", req.getMethod(), req.getRequestURI(), (long) ((finalTime - initTime) / 1e6));
            ThreadContext.clearAll();
        }
    }

    private static void configCorrelationId(HttpServletRequest req) {
        String correlationId = Optional.ofNullable(req.getHeader("correlation-id")).orElse(UUID.randomUUID().toString());
        ThreadContext.put("correlation-id", correlationId);
    }


}