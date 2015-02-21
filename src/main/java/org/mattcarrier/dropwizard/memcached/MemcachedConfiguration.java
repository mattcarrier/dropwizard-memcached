package org.mattcarrier.dropwizard.memcached;

import static com.google.common.base.Preconditions.checkNotNull;
import io.dropwizard.util.Duration;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MemcachedConfiguration {
    @NotNull
    private String host = "localhost";

    @NotNull
    private Integer port = 11211;

    @NotNull
    private Map<String, Duration> expiryTimes = new HashMap<>();

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param port
     *            the port to set
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * @return the expiryTimes
     */
    public Map<String, Duration> getExpiryTimes() {
        return expiryTimes;
    }

    /**
     * @param expiryTimes
     *            the expiryTimes to set
     */
    public void setExpiryTimes(Map<String, Duration> expiryTimes) {
        this.expiryTimes = expiryTimes;
    }

    @JsonIgnore
    public int getExpireTime(final String key) {
        checkNotNull(key, "key is required");

        if (null == expiryTimes.get(key)) {
            return 0;
        }

        final Duration d = expiryTimes.get(key);
        if (Duration.days(30).toNanoseconds() < d.toNanoseconds()) {
            throw new RuntimeException(
                    "dropwizard-memcached doesn't support expiry times longer than 30 days");
        }

        return (int) d.toSeconds();
    }
}