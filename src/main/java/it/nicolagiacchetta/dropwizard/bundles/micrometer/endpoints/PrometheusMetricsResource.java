package it.nicolagiacchetta.dropwizard.bundles.micrometer.endpoints;

import io.micrometer.prometheus.PrometheusMeterRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("")
public class PrometheusMetricsResource {

    private PrometheusMeterRegistry prometheusMeterRegistry;

    public PrometheusMetricsResource(PrometheusMeterRegistry prometheusMeterRegistry) {
        this.prometheusMeterRegistry = prometheusMeterRegistry;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMetrics() {
        String response = prometheusMeterRegistry.scrape();
        return Response.status(200).entity(response).build();
    }

    public PrometheusMeterRegistry getPrometheusMeterRegistry() {
        return prometheusMeterRegistry;
    }

    public void setPrometheusMeterRegistry(PrometheusMeterRegistry prometheusMeterRegistry) {
        this.prometheusMeterRegistry = prometheusMeterRegistry;
    }
}

