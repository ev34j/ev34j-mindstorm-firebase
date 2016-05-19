package com.ev34j.firebase;

public class RobotAction {

  private final long timeStamp = System.currentTimeMillis();

  private String action;

  public RobotAction() { }

  public RobotAction(final String action) {
    this.action = action;
  }

  public long getTimeStamp() { return this.timeStamp; }

  public String getAction() { return this.action; }
}
