package it.nicolagiacchetta.dropwizard.bundles.micrometer.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MicrometerPrometheusConfiguration {

    @JsonProperty
    private boolean jvmMemoryMetricsEnabled = true;

    @JsonProperty
    private boolean jvmGcMetricsEnabled = true;

    @JsonProperty
    private boolean jvmThreadMetricsEnabled = true;

    public boolean isJvmMemoryMetricsEnabled() {
        return jvmMemoryMetricsEnabled;
    }

    public boolean isJvmGcMetricsEnabled() {
        return jvmGcMetricsEnabled;
    }

    public boolean isJvmThreadMetricsEnabled() {
        return jvmThreadMetricsEnabled;
    }
}
