package com.example.demo;

import io.micrometer.core.instrument.Clock;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class LoggingObservationListener implements ObservationHandler<Observation.Context> {
    private final Clock clock;

    public LoggingObservationListener(Clock clock) {
        this.clock = clock;
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true; // 支持所有上下文
    }

    @Override
    public void onStart(Observation.Context context) {
        context.put("startTime", clock.monotonicTime());
        System.out.println("Observation started: " + context.getName());
    }

    @Override
    public void onStop(Observation.Context context) {
        long startTime = context.getOrDefault("startTime", 0L);
        long duration = clock.monotonicTime() - startTime;
        System.out.println("Observation stopped: " + context.getName() + ", Duration: " + duration + " ns");
    }
}
