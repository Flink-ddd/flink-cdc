package com.panda.cdc.config;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @since 2019-11-25 15:09
 */
public class IpConfig extends ClassicConverter {

    private static String webIP;

    static {
        try {
            webIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            webIP = "exception";
        }
    }

    @Override
    public String convert(ILoggingEvent event) {
        return webIP;
    }
}
