package org.mattcarrier.dropwizard.memcached;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

public class MemcachedBundle<T extends Configuration> implements ConfiguredBundle<T> {
    private MemcachedClient client;

    public MemcachedConfiguration getMemcachedConfiguration(final T configuration) {
        return new MemcachedConfiguration();
    }

    public MemcachedClient getClient() {
        return client;
    }

    @Override
    public void run(final T configuration, final Environment environment) throws Exception {
        try {
            final MemcachedConfiguration config = getMemcachedConfiguration(configuration);
            client = new MemcachedClient(new InetSocketAddress(config.getHost(), config.getPort()));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }
}
