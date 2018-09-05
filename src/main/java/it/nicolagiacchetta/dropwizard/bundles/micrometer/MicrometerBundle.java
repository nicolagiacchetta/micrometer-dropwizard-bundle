package it.nicolagiacchetta.dropwizard.bundles.micrometer;


import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.jersey.DropwizardResourceConfig;
import io.dropwizard.jersey.setup.JerseyContainerHolder;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import it.nicolagiacchetta.dropwizard.bundles.micrometer.config.MicrometerBundleConfiguration;
import it.nicolagiacchetta.dropwizard.bundles.micrometer.config.MicrometerPrometheusConfiguration;
import it.nicolagiacchetta.dropwizard.bundles.micrometer.endpoints.PrometheusMetricsResource;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import static it.nicolagiacchetta.dropwizard.bundles.micrometer.utils.DropwizardUtils.adaptFormatUrlPattern;
import static it.nicolagiacchetta.dropwizard.bundles.micrometer.utils.StringUtils.isNullOrEmpty;

public class MicrometerBundle<T extends Configuration> implements ConfiguredBundle<T>{

    private final static Logger LOGGER = LoggerFactory.getLogger(MicrometerBundle.class);

    private Function<T, MicrometerBundleConfiguration> configurationSupplier;

    public MicrometerBundle(Function<T, MicrometerBundleConfiguration> configurationSupplier) {
        this.configurationSupplier = configurationSupplier;
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        MicrometerBundleConfiguration bundleConfiguration = getMicrometerBundleConfiguration(configuration);
        registerPrometheusResource(bundleConfiguration, environment);
    }

    private void registerPrometheusResource(MicrometerBundleConfiguration configuration, Environment environment) {
        LOGGER.debug("Registering Prometheus resource to environment...");
        MicrometerPrometheusConfiguration prometheusConf = configuration.getPrometheusConfiguration();
        String name = isNullOrEmpty(prometheusConf.getName()) ? "Prometheus Metrics" : prometheusConf.getName();
        String path = isNullOrEmpty(prometheusConf.getPath()) ? "/micrometer/prometheus/*" : adaptFormatUrlPattern(prometheusConf.getPath());
        LOGGER.debug("...attempting to register Prometheus resource to path={}...", path);
        registerAdminResource(environment, name, path, new PrometheusMetricsResource(prometheusConf));
        LOGGER.debug("...Prometheus resource registered to environment.");
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
        DropwizardResourceConfig jerseyConfig = new DropwizardResourceConfig();
        JerseyContainerHolder jerseyContainerHolder = new JerseyContainerHolder(new ServletContainer(jerseyConfig));
        JerseyEnvironment jerseyEnvironment = new JerseyEnvironment(jerseyContainerHolder, jerseyConfig);
        jerseyEnvironment.register(resource);
        environment.admin().addServlet(name, jerseyContainerHolder.getContainer()).addMapping(urlPattern);
    }
}
