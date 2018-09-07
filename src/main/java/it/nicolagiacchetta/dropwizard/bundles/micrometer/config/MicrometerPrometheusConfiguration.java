package it.nicolagiacchetta.dropwizard.bundles.micrometer.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MicrometerPrometheusConfiguration {

    @JsonProperty
    private String name;

    @JsonProperty
    private String path;

    @JsonProperty
    private String prefix;

    @JsonProperty
    private boolean processorMetricsEnabled = true;

    @JsonProperty
    private boolean jvmMemoryMetricsEnabled = true;

    @JsonProperty
    private boolean jvmGcMetricsEnabled = true;

    @JsonProperty
    private boolean jvmThreadMetricsEnabled = true;

    @JsonProperty
    private boolean classLoaderMetricsEnabled = true;

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isProcessorMetricsEnabled() {
        return processorMetricsEnabled;
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

    public boolean isClassLoaderMetricsEnabled() {
        return classLoaderMetricsEnabled;
    }

}
