package org.nami.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ThreadFactory;

/**
 * NamiThreadFactory
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class NamiThreadFactory {

    private String name;
    private Boolean daemon;

    public NamiThreadFactory(String name, Boolean daemon) {
        this.name = name;
        this.daemon = daemon;
    }

    public ThreadFactory create() {
        return new ThreadFactoryBuilder().setNameFormat(this.name + "-%d").setDaemon(this.daemon).build();
    }
}
