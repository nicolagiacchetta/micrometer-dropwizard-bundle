package it.nicolagiacchetta.dropwizard.bundles.micrometer.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MicrometerBundleConfiguration {

    @JsonCreator
    public MicrometerBundleConfiguration() {
        this.prometheusConfiguration = new MicrometerPrometheusConfiguration();
    }

    public MicrometerPrometheusConfiguration getPrometheusConfiguration() {
        return prometheusConfiguration;
    }

    @JsonProperty("prometheus")
    private MicrometerPrometheusConfiguration prometheusConfiguration;
}
