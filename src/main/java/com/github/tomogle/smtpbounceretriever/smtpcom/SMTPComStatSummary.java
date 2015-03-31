package com.github.tomogle.smtpbounceretriever.smtpcom;

public final class SMTPComStatSummary {

  private final long bouncedCount;
  private final long complaintsCount;
  private final long deliveredCount;
  private final long receivedCount;

  public SMTPComStatSummary(final long bouncedCount, final long complaintsCount, final long deliveredCount, final long receivedCount) {
    this.bouncedCount = bouncedCount;
    this.complaintsCount = complaintsCount;
    this.deliveredCount = deliveredCount;
    this.receivedCount = receivedCount;
  }

  public long getBouncedCount() {
    return bouncedCount;
  }

  public long getComplaintsCount() {
    return complaintsCount;
  }

  public long getDeliveredCount() {
    return deliveredCount;
  }

  public long getReceivedCount() {
    return receivedCount;
  }

  @Override public boolean equals(final Object o) {
    if(this == o)
      return true;
    if(o == null || getClass() != o.getClass())
      return false;

    SMTPComStatSummary that = (SMTPComStatSummary) o;

    if(bouncedCount != that.bouncedCount)
      return false;
    if(complaintsCount != that.complaintsCount)
      return false;
    if(deliveredCount != that.deliveredCount)
      return false;
    return receivedCount == that.receivedCount;

  }

  @Override public int hashCode() {
    int result = (int) (bouncedCount ^ (bouncedCount >>> 32));
    result = 31 * result + (int) (complaintsCount ^ (complaintsCount >>> 32));
    result = 31 * result + (int) (deliveredCount ^ (deliveredCount >>> 32));
    result = 31 * result + (int) (receivedCount ^ (receivedCount >>> 32));
    return result;
  }

  @Override public String toString() {
    return "SMTPComStats: { " +
        "bouncedCount = " + bouncedCount +
        ", complaintsCount = " + complaintsCount +
        ", deliveredCount = " + deliveredCount +
        ", receivedCount = " + receivedCount +
        " }";
  }
}
