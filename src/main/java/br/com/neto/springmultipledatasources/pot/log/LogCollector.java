package br.com.neto.springmultipledatasources.pot.log;

import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("UnusedReturnValue")
public class LogCollector {


    private static final String MESSAGE_KEY = "message";
    private static final String EMPTY_MESSAGE_VALUE = "EMPTY_MESSAGE";
    private static final String ELAPSED_TIME_KEY = "elapsedTimeMillis";
    private static final String TOTAL_KEY = "total";
    private final StopWatch stopwatch;
    private Map<String, Object> elasedTimeMap;
    private final Map<String, Object> logMap = new LinkedHashMap<>();

    public static LogCollector build() {
        return new LogCollector();
    }

    public LogCollector addMessage(String msg) {

        if (StringUtils.hasText(msg)) {
            logMap.put(MESSAGE_KEY, msg);
        } else {
            logMap.put(MESSAGE_KEY, EMPTY_MESSAGE_VALUE);
        }

        return this;
    }

    public LogCollector add(String key, Object value) {
        logMap.put(key, value);
        return this;
    }

    public LogCollector stopwatchStart(String stopwatchMarker) {

        if (elasedTimeMap == null) {
            elasedTimeMap = new LinkedHashMap<>();
            logMap.put(ELAPSED_TIME_KEY, elasedTimeMap);
        }

        stopwatch.start(stopwatchMarker);

        return this;
    }

    public LogCollector stopwatchMark(String stopwatchMarker) {
        stopwatch.stop();

        elasedTimeMap.put(stopwatch.getLastTaskName(), stopwatch.getLastTaskTimeMillis());

        stopwatch.start(stopwatchMarker);

        return this;
    }

    public LogCollector stopwatchStop(boolean includeTotalElapsedTime) {

        stopwatch.stop();
        elasedTimeMap.put(stopwatch.getLastTaskName(), stopwatch.getLastTaskTimeMillis());

        if (includeTotalElapsedTime) {
            elasedTimeMap.put(TOTAL_KEY, stopwatch.getTotalTimeMillis());
        }

        return this;
    }

    public Map<String, Object> collect() {
        return Collections.unmodifiableMap(logMap);
    }

    private LogCollector() {
        stopwatch = new StopWatch();
    }
}
