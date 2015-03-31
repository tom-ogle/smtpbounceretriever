package com.github.tomogle.smtpbounceretriever.jangosmtp;

import com.github.tomogle.smtpbounceretriever.BounceRetriever;
import com.github.tomogle.smtpbounceretriever.DateFormatProvider;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class JangoSMTPBounceRetriever extends BounceRetriever {

  private final String userName;
  private final String password;
  private int timeoutMs;

  public JangoSMTPBounceRetriever(final String userName, final String password, final int timeoutMs) {
    this.userName = userName;
    this.password = password;
    this.timeoutMs = timeoutMs;
  }

  /**
   * Gets all bounces since the given time.
   * @param sinceDateTime If this parameter is null then all bounces will be retrieved
   * @return The List of all bounces
   * @throws IOException Thrown if there is an IO error.
   */
  public List<String> getBounceAddresses(final DateTime sinceDateTime) throws IOException {
    final String sinceTime = DateFormatProvider.formatForJangoSMTP(sinceDateTime);
    final String requestUrl = format("https://api.jangomail.com/api.asmx/GetBounceListAll?Username=%s&Password=%s&Since=%s",
        userName, password, sinceTime);
    List<String> bounces = new ArrayList<>();

    final BufferedReader connectionReader = getConnectionBufferedReader(requestUrl, timeoutMs);
    String line;
    while((line = connectionReader.readLine()) != null) {
      if (line.equals("SUCCESS")) {
        break;
      }
    }
    while((line = connectionReader.readLine()) != null) {
      bounces.add(line);
    }
    return bounces;
  }

  public void setTimeoutMs(final int timeoutMs) {
    this.timeoutMs = timeoutMs;
  }
}
