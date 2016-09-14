package com.redhat;

public class TimeBomb {
    private final long timeout;

    public TimeBomb(long timeout) {
        this.timeout = timeout;
    }

    public long getTimeout() {
        return timeout;
    }

}
