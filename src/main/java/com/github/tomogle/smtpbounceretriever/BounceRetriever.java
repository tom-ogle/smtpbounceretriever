package com.github.tomogle.smtpbounceretriever;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class BounceRetriever {

  protected BufferedReader getConnectionBufferedReader(final String requestUrl, final int timeOutMs) throws IOException {
    URL url = new URL(requestUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    connection.setRequestMethod("GET");
    connection.setReadTimeout(timeOutMs);

    connection.connect();

    return new BufferedReader(new InputStreamReader(connection.getInputStream()));
  }

}
