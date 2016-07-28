package com.thoughtworks.ketsu.web.jersey;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.thoughtworks.ketsu.infrastructure.records.Models;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;
import java.util.Properties;

import static org.jvnet.hk2.guice.bridge.api.GuiceBridge.getGuiceBridge;

public class Api extends ResourceConfig {
    @Inject
    public Api(ServiceLocator locator) throws Exception {
        Properties properties = new Properties();
        String dbname = System.getenv().getOrDefault("MONGODB_DATABASE", "mongodb_store");
        String host = System.getenv().getOrDefault("MONGODB_HOST", "localhost");
        String port = System.getenv().getOrDefault("MONGODB_PORT", "27017");
        String username = System.getenv().getOrDefault("MONGODB_USER", "admin");
        String password = System.getenv().getOrDefault("MONGODB_PASS", "mypass");
        String connectURL = String.format(
                "mongodb://%s:%s@%s/%s",
                username,
                password,
                host,
                dbname
        );
//        String redistHost = System.getenv().getOrDefault("REDIS_HOST", "127.0.0.1");
//        String redisPort = System.getenv().getOrDefault("REDIS_PORT", "6379");
//        final String redisURL = String.format("%s:%s", redistHost, redisPort);

        properties.setProperty("db.url", connectURL);

        bridge(locator, Guice.createInjector(new Models("development", properties), new AbstractModule() {
            @Override
            protected void configure() {
                bind(ServiceLocator.class).toInstance(locator);
            }
        }));

        property(org.glassfish.jersey.server.ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        packages("com.thoughtworks.ketsu.web");
        register(RoutesFeature.class);
        register(LoggingFilter.class);
        register(CORSResponseFilter.class);
//        register(OpenSessionInViewRequestFilter.class);
//        register(OpenSessionInViewResponseFilter.class);
    }

    private void bridge(ServiceLocator serviceLocator, Injector injector) {
        getGuiceBridge().initializeGuiceBridge(serviceLocator);
        serviceLocator.getService(GuiceIntoHK2Bridge.class).bridgeGuiceInjector(injector);
    }

}
