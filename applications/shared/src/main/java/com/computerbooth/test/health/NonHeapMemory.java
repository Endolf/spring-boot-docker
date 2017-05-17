package com.computerbooth.test.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

@Component
public class NonHeapMemory implements HealthIndicator {
    @Override
    public Health health() {
        MemoryUsage memBean = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
        //For now it's informational
        return Health.up()
                .withDetail("init", memBean.getInit())
                .withDetail("max", memBean.getMax())
                .withDetail("committed", memBean.getCommitted())
                .withDetail("used", memBean.getUsed())
                .build();
    }
}
