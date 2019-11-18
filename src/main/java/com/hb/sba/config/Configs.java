package com.hb.sba.config;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.config.MeterFilterReply;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author xiaodong
 * @title
 * @date 2019/11/15 16:28
 * @desc
 */
//@Component
public class Configs {

    /**
     * move to client side
     * @return
     */
    //@Bean
    public MeterFilter meterFilter() {
        return new MeterFilter() {
            @Override
            public MeterFilterReply accept(Meter.Id id) {
                if (id.getName().startsWith("tomcat.")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("jvm.")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("process.")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("system.")) {
                    return MeterFilterReply.DENY;
                }
                return MeterFilterReply.NEUTRAL;
            }
        };
    }
}
