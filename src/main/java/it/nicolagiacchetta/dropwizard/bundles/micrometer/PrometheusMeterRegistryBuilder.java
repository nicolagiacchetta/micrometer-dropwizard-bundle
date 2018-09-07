package it.nicolagiacchetta.dropwizard.bundles.micrometer;

import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.lang.Nullable;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import it.nicolagiacchetta.dropwizard.bundles.micrometer.config.MicrometerPrometheusConfiguration;

import java.time.Duration;

public class PrometheusMeterRegistryBuilder {

    public PrometheusMeterRegistryBuilder(MicrometerPrometheusConfiguration configuration) {
        if(configuration == null)
            return;

        this.processorMetricsEnabled = configuration.isProcessorMetricsEnabled();
        this.jvmGcMetricsEnabled = configuration.isJvmGcMetricsEnabled();
        this.jvmThreadMetricsEnabled = configuration.isJvmThreadMetricsEnabled();
        this.jvmMemoryMetricsEnabled = configuration.isJvmMemoryMetricsEnabled();
        this.classLoaderMetricsEnabled = configuration.isClassLoaderMetricsEnabled();
    }

    private String prefix;
    private Duration step;
    private boolean jvmMemoryMetricsEnabled = false;
    private boolean jvmGcMetricsEnabled = false;
    private boolean jvmThreadMetricsEnabled = false;
    private boolean classLoaderMetricsEnabled = false;
    private boolean processorMetricsEnabled = false;

    public PrometheusMeterRegistryBuilder withPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public PrometheusMeterRegistryBuilder withStep(Duration step) {
        this.step = step;
        return this;
    }

    public PrometheusMeterRegistryBuilder withProcessorMetricsEnabled(boolean processorMetricsEnabled) {
        this.processorMetricsEnabled = processorMetricsEnabled;
        return this;
    }

    public PrometheusMeterRegistryBuilder withClassLoaderMetricsEnabled(boolean classLoaderMetricsEnabled) {
        this.classLoaderMetricsEnabled = classLoaderMetricsEnabled;
        return this;
    }

    public PrometheusMeterRegistryBuilder withJvmMemoryMetricsEnabled(boolean jvmMemoryMetricsEnabled) {
        this.jvmMemoryMetricsEnabled = jvmMemoryMetricsEnabled;
        return this;
    }

    public PrometheusMeterRegistryBuilder withJvmGcMetricsEnabled(boolean jvmGcMetricsEnabled) {
        this.jvmGcMetricsEnabled = jvmGcMetricsEnabled;
        return this;
    }

    public PrometheusMeterRegistryBuilder withJvmThreadMetricsEnabled(boolean jvmThreadMetricsEnabled) {
        this.jvmThreadMetricsEnabled = jvmThreadMetricsEnabled;
        return this;
    }

    public PrometheusMeterRegistry build() {
        PrometheusMeterRegistry prometheusMeterRegistry = new PrometheusMeterRegistry(new PrometheusConfig() {
            @Override
            public Duration step() {
                return step == null ? Duration.ofSeconds(60) : step;
            }

            @Override
            @Nullable
            public String get(String k) {
                return null;
            }

            @Override
            public String prefix() {
                return prefix == null ? PrometheusConfig.super.prefix() : prefix;
            }
        });

        if(this.processorMetricsEnabled) {
            new ProcessorMetrics().bindTo(prometheusMeterRegistry);
        }

        if(this.jvmMemoryMetricsEnabled) {
            new JvmMemoryMetrics().bindTo(prometheusMeterRegistry);
        }

        if(this.jvmGcMetricsEnabled) {
            new JvmGcMetrics().bindTo(prometheusMeterRegistry);
        }

        if(this.jvmThreadMetricsEnabled) {
            new JvmThreadMetrics().bindTo(prometheusMeterRegistry);
        }

        if(this.classLoaderMetricsEnabled) {
            new ClassLoaderMetrics().bindTo(prometheusMeterRegistry);
        }

        return prometheusMeterRegistry;
    }

}
