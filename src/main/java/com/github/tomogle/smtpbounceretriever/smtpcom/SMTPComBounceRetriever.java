package com.github.tomogle.smtpbounceretriever.smtpcom;

import com.github.tomogle.smtpbounceretriever.BounceRetriever;
import com.github.tomogle.smtpbounceretriever.DateFormatProvider;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class SMTPComBounceRetriever extends BounceRetriever {

  private final String apiUrl;
  private final String apiKey;
  private final String senderLabel;
  private int timeoutMs;

  private final String statisticsUrl;

  public SMTPComBounceRetriever(final String apiUrl, final String apiKey, final String senderLabel, final int timeoutMs) {
    this.apiUrl = apiUrl;
    this.apiKey = apiKey;
    this.senderLabel = senderLabel;
    this.timeoutMs = timeoutMs;

    statisticsUrl = format("%s/account/senders/%s/statistics",apiUrl, senderLabel);
  }

  public List<String> getBounceAddresses(final long limit, final long offset, final DateTime startDateTime, final DateTime endDateTime)
      throws IOException {

    final String startDate = DateFormatProvider.formatForSMTPcom(startDateTime);
    final String endDate = DateFormatProvider.formatForSMTPcom(endDateTime);
    final String requestUrl = deliveryStatisticsRequestUrl(limit, offset, startDate, endDate);
    final BufferedReader connectionReader = getConnectionBufferedReader(requestUrl, timeoutMs);

    final JSONObject jsonObject = (JSONObject) JSONValue.parse(connectionReader);
    final JSONArray delivery = (JSONArray) jsonObject.get("delivery");

    List<String> bounceAddressList = new ArrayList<>();
    for(Object entryObj: delivery) {
      JSONObject entry = (JSONObject) entryObj;
      String bounceAddress = (String) entry.get("rcpt_to");
      bounceAddressList.add(bounceAddress);
    }
    return bounceAddressList;
  }

  public List<String> getComplaintAddresses(final long limit, final long offset, final DateTime startDateTime, final DateTime endDateTime)
      throws IOException {

    final String startDate = DateFormatProvider.formatForSMTPcom(startDateTime);
    final String endDate = DateFormatProvider.formatForSMTPcom(endDateTime);
    final String requestUrl = complaintsStatisticsRequestUrl(limit, offset, startDate, endDate);
    final BufferedReader connectionReader = getConnectionBufferedReader(requestUrl, timeoutMs);

    final JSONObject jsonObject = (JSONObject) JSONValue.parse(connectionReader);
    final JSONArray complaints = (JSONArray) jsonObject.get("complaints");

    List<String> complaintAddressList = new ArrayList<>();
    for(Object complaintObj : complaints) {
      JSONObject complaint = (JSONObject) complaintObj;
      String complaintAddress = (String) complaint.get("rcpt_to");
      complaintAddressList.add(complaintAddress);
    }
    return complaintAddressList;
  }

  public SMTPComStatSummary getStatSummary(final DateTime startDateTime, final DateTime endDateTime) throws IOException {
    final String startDate = DateFormatProvider.formatForSMTPcom(startDateTime);
    final String endDate = DateFormatProvider.formatForSMTPcom(endDateTime);
    final String requestUrl = statsSummaryUrl(startDate, endDate);
    final BufferedReader connectionReader = getConnectionBufferedReader(requestUrl, timeoutMs);

    final JSONObject jsonObject = (JSONObject) JSONValue.parse(connectionReader);
    final JSONObject summary = (JSONObject) jsonObject.get("summary");

    Long bouncedCount = (Long) summary.get("bounced_count");
    Long complaintsCount = (Long) summary.get("complaints_count");
    Long deliveredCount = (Long) summary.get("delivered_count");
    Long receivedCount = (Long) summary.get("received_count");

    return new SMTPComStatSummary(bouncedCount, complaintsCount, deliveredCount, receivedCount);
  }

  // Used for retrieving bounces
  private String deliveryStatisticsRequestUrl(final long limit, final long offset, final String startDate, final String endDate) {
    return format(
        "%s/delivery.json?api_key=%s&time_start=%s&time_end=%s&limit=%s&offset=%s",
        statisticsUrl, apiKey, startDate, endDate, limit, offset);
  }

  // Used for retrieving complaints
  private String complaintsStatisticsRequestUrl(final long limit, final long offset, final String startDate, final String endDate) {
    return format(
        "%s/complaints.json?api_key=%s&time_start=%s&time_end=%s&limit=%s&offset=%s",
        statisticsUrl, apiKey, startDate, endDate, limit, offset);
  }

  // Used for retrieving summary/aggregate stats
  private String statsSummaryUrl(final String startDate, final String endDate) {
    return format(
        "%s/summary.json?api_key=%s&time_start=%s&time_end=%s",
        statisticsUrl, apiKey, startDate, endDate);
  }

  public void setTimeoutMs(final int timeoutMs) {
    this.timeoutMs = timeoutMs;
  }
}
