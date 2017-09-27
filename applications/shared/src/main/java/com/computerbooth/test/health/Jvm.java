package com.computerbooth.test.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;

@Component
public class Jvm implements HealthIndicator {
    @Override
    public Health health() {
        RuntimeMXBean jvmBean = ManagementFactory.getRuntimeMXBean();
        //For now it's informational
        return Health.up()
                .withDetail("spec version", jvmBean.getManagementSpecVersion())
                .withDetail("name", jvmBean.getName())
                .withDetail("spec name", jvmBean.getSpecName())
                .withDetail("spec vendor", jvmBean.getSpecVendor())
                .withDetail("spec version", jvmBean.getSpecVersion())
                .withDetail("uptime", jvmBean.getUptime())
                .withDetail("vm name", jvmBean.getVmName())
                .withDetail("vm vendor", jvmBean.getVmVendor())
                .withDetail("vm version", jvmBean.getVmVersion())
                .build();
    }
}
