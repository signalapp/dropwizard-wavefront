package org.whispersystems.wavefront.metrics;


import com.codahale.metrics.Gauge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class NetworkGauge implements Gauge<Long> {

  protected SentAndReceived getSentReceived() throws IOException {
    File proc          = new File("/proc/net/dev");
    BufferedReader reader        = new BufferedReader(new FileReader(proc));
    String header        = reader.readLine();
    String header2       = reader.readLine();

    long           bytesSent     = 0;
    long           bytesReceived = 0;

    String interfaceStats;

      while ((interfaceStats = reader.readLine()) != null) {
        String[] stats = interfaceStats.split("\\s+");

        if (!stats[1].equals("lo:")) {
          bytesReceived += Long.parseLong(stats[2]);
          bytesSent     += Long.parseLong(stats[10]);
        }
      }

    return new SentAndReceived(bytesSent, bytesReceived);
  }

  static class SentAndReceived {
    final long sent;
    final long received;

    public SentAndReceived(long sent, long received) {
      this.sent     = sent;
      this.received = received;
    }
  }
}
