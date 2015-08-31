package org.whispersystems.wavefront.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NetworkReceivedGauge extends NetworkGauge {

  private final Logger logger = LoggerFactory.getLogger(NetworkSentGauge.class);

  private long lastTimestamp;
  private long lastReceived;

  @Override
  public Long getValue() {
    try {
      long            timestamp       = System.currentTimeMillis();
      SentAndReceived sentAndReceived = getSentReceived();
      long            result          = 0;

      if (lastTimestamp != 0) {
        result       = sentAndReceived.received - lastReceived;
        lastReceived = sentAndReceived.received;
      }

      lastTimestamp = timestamp;
      return result;
    } catch (IOException e) {
      logger.warn("NetworkReceivedGauge", e);
      return -1L;
    }
  }



}
