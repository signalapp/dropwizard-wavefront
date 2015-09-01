# dropwizard-wavefront

A Dropwizard Metrics reporter for [wavefront](https://www.wavefront.com).

## Installing

Add the maven dependency to your project:

`````
<dependency>
  <groupId>org.whispersystems</groupId>
  <artifactId>dropwizard-wavefront</artifactId>
  <version>{latest_version_here}</version>
</dependency>
`````

Then you just need to tell Dropwizard where to find the reporter.  From your module base dir:
 
`````
$ mkdir -p /resources/META-INF/services/
$ echo "org.whispersystems.wavefront.WavefrontMetricsReporterFactory" > /resources/META-INF/services/io.dropwizard.metrics.ReporterFactory
`````

## Configuring

Add the following to your configuration yaml file:

`````
metrics:
  reporters:
    - type: wavefront
      token: {your api token}
      hostname: "metrics.wavefront.com"
      frequency: 60 seconds
`````

Done!  Your metrics should now report to wavefront every 60 seconds.

## Additional Gauges

There are system gauges for network, cpu, file descriptors, and memory available as well.

To use them:

`````
environment.metrics().register(name(CpuUsageGauge.class, "cpu"), new CpuUsageGauge());
environment.metrics().register(name(FreeMemoryGauge.class, "free_memory"), new FreeMemoryGauge());
environment.metrics().register(name(NetworkSentGauge.class, "bytes_sent"), new NetworkSentGauge());
environment.metrics().register(name(NetworkReceivedGauge.class, "bytes_received"), new NetworkReceivedGauge());
environment.metrics().register(name(FileDescriptorGauge.class, "fd_count"), new FileDescriptorGauge());
`````