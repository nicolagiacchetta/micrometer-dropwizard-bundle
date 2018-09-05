package it.nicolagiacchetta.dropwizard.bundles.micrometer.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MicrometerPrometheusConfiguration {

    @JsonProperty
    private String name = "";

    @JsonProperty
    private String path = "";

    @JsonProperty
    private boolean jvmMemoryMetricsEnabled = true;

    @JsonProperty
    private boolean jvmGcMetricsEnabled = true;

    @JsonProperty
    private boolean jvmThreadMetricsEnabled = true;

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

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
