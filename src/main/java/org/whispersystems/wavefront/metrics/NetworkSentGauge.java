package org.whispersystems.wavefront.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NetworkSentGauge extends NetworkGauge {

  private final Logger logger = LoggerFactory.getLogger(NetworkSentGauge.class);

  private long lastTimestamp;
  private long lastSent;

  @Override
  public Long getValue() {
    try {
      long            timestamp       = System.currentTimeMillis();
      SentAndReceived sentAndReceived = getSentReceived();
      long            result          = 0;

      if (lastTimestamp != 0) {
        result        = sentAndReceived.sent - lastSent;
        lastSent      = sentAndReceived.sent;
      }

      lastTimestamp = timestamp;
      return result;
    } catch (IOException e) {
      logger.warn("NetworkSentGauge", e);
      return -1L;
    }
  }
}
