package it.nicolagiacchetta.dropwizard.bundles.micrometer.endpoints;

import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import it.nicolagiacchetta.dropwizard.bundles.micrometer.config.MicrometerPrometheusConfiguration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("prometheus")
public class PrometheusMetricsResource {

    private PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    public PrometheusMetricsResource(MicrometerPrometheusConfiguration configuration) {
        if(configuration.isJvmMemoryMetricsEnabled()) {
            new JvmMemoryMetrics().bindTo(prometheusRegistry);
        }

        if(configuration.isJvmGcMetricsEnabled()) {
            new JvmGcMetrics().bindTo(prometheusRegistry);
        }

        if(configuration.isJvmThreadMetricsEnabled()) {
            new JvmThreadMetrics().bindTo(prometheusRegistry);
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMetrics() {
        String response = prometheusRegistry.scrape();
        return Response.status(200).entity(response).build();
    }
}

