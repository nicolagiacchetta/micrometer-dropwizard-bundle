# Micrometer Dropwizard Bundle
[![Build Status](https://travis-ci.org/nicolagiacchetta/micrometer-dropwizard-bundle.svg?branch=master)](https://travis-ci.org/nicolagiacchetta/micrometer-dropwizard-bundle)

A basic [Dropwizard](https://www.dropwizard.io/) bundle for [Micrometer](https://micrometer.io/). 

## About this Repo
The main purpose of this repo is to provide an example of how I have plugged Micrometer to a Dropwizard application. Currently the bundle only supports the capability to expose some JVM metrics of the service in the [Prometheus](https://prometheus.io/) format.  

## Getting started
### Building from Source
To generate and publish in the project directory the jar of the library, run the `jar` and `publish` gradle tasks.


```
./gradlew clean jar publish
```

A `build.sh` script is also provided.


### Add the MicrometerBundle to your application
Add the `MicrometerBundle` to your application environment as shown here below:

```Java
public class YourApplication extends Application<YourApplicationConfiguration> {
    ...
    
    public void initialize(Bootstrap<YourApplicationConfiguration> bootstrap) {
        MicrometerBundle<ReportsApplicationConfiguration> micrometerBundle = new MicrometerBundle<>(YourApplicationConfiguration::getMicrometerBundleConfiguration);
        bootstrap.addBundle(micrometerBundle);
    }
}
```

Update the application's configuration class in order to have access to the parameters to configure the `MicrometerBundle`.

```Java
public class YourApplicationConfiguration extends Configuration {

    ...

    @Valid
    @JsonProperty("micrometerBundle")
    private MicrometerBundleConfiguration micrometerBundleConfiguration = new MicrometerBundleConfiguration();

    public MicrometerBundleConfiguration getMicrometerBundleConfiguration() {
        return this.micrometerBundleConfiguration;
    }
}
```

In the following snippet an example of configuration block for the Prometheus resource to be added to the yml configuration file of your application.
```Yml
micrometerBundle:
  prometheus:
    path: metrics/prometheus
    name: Metrics in Prometheus format
    prefix: some-prefix
    processorMetricsEnabled: true
    jvmMemoryMetricsEnabled: true
    jvmGcMetricsEnabled: true
    jvmThreadMetricsEnabled: false
    classLoaderMetricsEnabled: false
```
None of the parameter is mandatory: a default value is always provided. For instance for the `path` field of the `prometheus` node the default value is `/micrometer/prometheus/`. The default value of all the boolean flags is `true`.

To provide full access to the `PrometheusMeterRegistry`, the `MicrometerBundle` exposes a setter and a getter method. A `PrometheusMeterRegistryBuilder` is also provided in order to facilitate the construction of custom `io.micrometer.prometheus.PrometheusMeterRegistry`. 