# Micrometer Dropwizard Bundle
[![Build Status](https://travis-ci.org/nicolagiacchetta/micrometer-dropwizard-bundle.svg?branch=master)](https://travis-ci.org/nicolagiacchetta/micrometer-dropwizard-bundle)

A [Dropwizard](https://www.dropwizard.io/) bundle for [Micrometer](https://micrometer.io/). 


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
    jvmMemoryMetricsEnabled: true
    jvmGcMetricsEnabled: true
    jvmThreadMetricsEnabled: false
```
None of the parameter is mandatory: a default value is always provided. For instance for the `path` field the default value is `/micrometer/prometheus/`.


