package com.computerbooth.test.actuator.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

@Component
public class JvmInfo implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        RuntimeMXBean jvmBean = ManagementFactory.getRuntimeMXBean();

        Map details = new HashMap<String,String>();
        details.put("spec version", jvmBean.getManagementSpecVersion());
        details.put("name", jvmBean.getName());
        details.put("spec name", jvmBean.getSpecName());
        details.put("spec vendor", jvmBean.getSpecVendor());
        details.put("spec version", jvmBean.getSpecVersion());
        details.put("vm name", jvmBean.getVmName());
        details.put("vm vendor", jvmBean.getVmVendor());
        details.put("vm version", jvmBean.getVmVersion());

        builder
            .withDetail("jvm", details);
    }
}
