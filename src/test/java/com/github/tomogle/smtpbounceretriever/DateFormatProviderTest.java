package com.github.tomogle.smtpbounceretriever;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateFormatProviderTest {
  
  @Test
  public void formatForSMTPcomShouldReturnExpectedPattern() throws Exception {
    testFormatDateTimeForSMTPcom(new DateTime(2014, 11, 22, 0, 0), "00:00%2022-11-2014");
  }

  @Test
  public void formatForSMTPcomShouldReturnExpectedPattern2() throws Exception {
    testFormatDateTimeForSMTPcom(new DateTime(2014, 11, 22, 0, 25), "00:25%2022-11-2014");
  }

  @Test
  public void formatForSMTPcomShouldReturnExpectedPattern3() throws Exception {
    testFormatDateTimeForSMTPcom(new DateTime(2014, 11, 22, 5, 0), "05:00%2022-11-2014");
  }

  @Test
  public void formatForSMTPcomShouldReturnExpectedPattern4() throws Exception {
    testFormatDateTimeForSMTPcom(new DateTime(2012, 11, 22, 5, 59), "05:59%2022-11-2012");
  }

  @Test
  public void formatForSMTPcomShouldReturnExpectedPattern5() throws Exception {
    testFormatDateTimeForSMTPcom(new DateTime(2014, 8, 22, 12, 0), "12:00%2022-08-2014");
  }

  @Test
  public void formatForSMTPcomShouldReturnExpectedPattern6() throws Exception {
    testFormatDateTimeForSMTPcom(new DateTime(2080, 1, 22, 23, 1), "23:01%2022-01-2080");
  }

  @Test
  public void formatForSMTPcomShouldReturnExpectedPattern7() throws Exception {
    testFormatDateTimeForSMTPcom(new DateTime(2014, 11, 1, 23, 59, 32, 100), "23:59%2001-11-2014");
  }

  private static void testFormatDateTimeForSMTPcom(final DateTime timeToTest, final String expectedResult) {
    final String actualDateResult = DateFormatProvider.formatForSMTPcom(timeToTest);
    assertEquals(expectedResult, actualDateResult);
  }

  @Test
  public void formatForJangoSMTPShouldReturnExpectedPattern() throws Exception {
    testFormatDateTimeForJangoSMTP(new DateTime(2014, 11, 22, 0, 0), "2014-11-22");
  }

  @Test
  public void formatForJangoSMTPShouldReturnExpectedPattern2() throws Exception {
    testFormatDateTimeForJangoSMTP(new DateTime(2014, 11, 22, 0, 25), "2014-11-22");
  }

  @Test
  public void formatForJangoSMTPShouldReturnExpectedPattern3() throws Exception {
    testFormatDateTimeForJangoSMTP(new DateTime(2014, 11, 22, 5, 0), "2014-11-22");
  }

  @Test
  public void formatForJangoSMTPShouldReturnExpectedPattern4() throws Exception {
    testFormatDateTimeForJangoSMTP(new DateTime(2012, 11, 22, 5, 59), "2012-11-22");
  }

  @Test
  public void formatForJangoSMTPShouldReturnExpectedPattern5() throws Exception {
    testFormatDateTimeForJangoSMTP(new DateTime(2014, 8, 22, 12, 0), "2014-08-22");
  }

  @Test
  public void formatForJangoSMTPShouldReturnExpectedPattern6() throws Exception {
    testFormatDateTimeForJangoSMTP(new DateTime(2080, 1, 22, 23, 1), "2080-01-22");
  }

  @Test
  public void formatForJangoSMTPShouldReturnExpectedPattern7() throws Exception {
    testFormatDateTimeForJangoSMTP(new DateTime(2014, 11, 1, 23, 59, 32, 100), "2014-11-01");
  }

  @Test
  public void formatForJangoSMTPShouldReturnEmptyStringForNullDateTime() throws Exception {
    testFormatDateTimeForJangoSMTP(null, "");
  }

  private static void testFormatDateTimeForJangoSMTP(final DateTime timeToTest, final String expectedResult) {
    final String actualDateResult = DateFormatProvider.formatForJangoSMTP(timeToTest);
    assertEquals(expectedResult, actualDateResult);
  }
}
