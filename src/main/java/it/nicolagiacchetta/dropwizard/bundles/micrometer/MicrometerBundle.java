package it.nicolagiacchetta.dropwizard.bundles.micrometer;


import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.jersey.DropwizardResourceConfig;
import io.dropwizard.jersey.setup.JerseyContainerHolder;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import it.nicolagiacchetta.dropwizard.bundles.micrometer.config.MicrometerBundleConfiguration;
import it.nicolagiacchetta.dropwizard.bundles.micrometer.endpoints.PrometheusMetricsResource;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.function.Function;

public class MicrometerBundle<T extends Configuration> implements ConfiguredBundle<T>{

    private Function<T, MicrometerBundleConfiguration> configurationSupplier;

    public MicrometerBundle(Function<T, MicrometerBundleConfiguration> configurationSupplier) {
        this.configurationSupplier = configurationSupplier;
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        registerAdminResource(environment, "Prometheus Metrics", "/metrics/*", new PrometheusMetricsResource(getMicrometerBundleConfiguration(configuration).getPrometheusConfiguration()));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

    private MicrometerBundleConfiguration getMicrometerBundleConfiguration(T configuration) {
        return configurationSupplier.apply(configuration);
    }

    private void registerAdminResource(Environment environment,
                                       String name,
                                       String urlPattern,
                                       Object resource) {
        DropwizardResourceConfig jerseyConfig = new DropwizardResourceConfig(environment.metrics());
        JerseyContainerHolder jerseyContainerHolder = new JerseyContainerHolder(new ServletContainer(jerseyConfig));
        JerseyEnvironment jerseyEnvironment = new JerseyEnvironment(jerseyContainerHolder, jerseyConfig);
        jerseyEnvironment.register(resource);
        environment.admin().addServlet(name, jerseyContainerHolder.getContainer()).addMapping(urlPattern);
    }
}
