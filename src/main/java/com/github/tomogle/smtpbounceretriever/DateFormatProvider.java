package com.github.tomogle.smtpbounceretriever;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Formats JodaTime DateTime objects for SMTP services.
 */
public class DateFormatProvider {

  public static String formatForSMTPcom(final DateTime dt) {
    DateTimeFormatter dtFormatter = DateTimeFormat.forPattern("HH:mm%20dd-MM-yyyy"); //JDK date/time  patterns
    return dtFormatter.print(dt);
  }

  public static String formatForJangoSMTP(final DateTime dt) {
    if (dt == null) {
      return "";
    }
    DateTimeFormatter dtFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    return dtFormatter.print(dt);
  }
}
