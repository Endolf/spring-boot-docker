package com.computerbooth.test.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

@Component
public class OperatingSystem implements HealthIndicator {
    @Override
    public Health health() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        //Just for monitoring
        return Health.up()
                .withDetail("arch", osBean.getArch())
                .withDetail("name", osBean.getName())
                .withDetail("version", osBean.getVersion())
                .withDetail("avail-processors", osBean.getAvailableProcessors())
                .withDetail("system-load-average", osBean.getSystemLoadAverage())
                .build();
    }
}
