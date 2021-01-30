package org.nami.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IpUtils
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class IpUtils {

    public static String getLocalIpAddress() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address.getHostAddress();
    }
}
