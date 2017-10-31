package com.computerbooth.test.actuator.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

@Component
public class OperatingSystemInfo implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        Map details = new HashMap<String,String>();
        details.put("arch", osBean.getArch());
        details.put("name", osBean.getName());
        details.put("version", osBean.getVersion());
        details.put("avail-processors", osBean.getAvailableProcessors());

        builder
            .withDetail("operating-system", details);
    }
}
