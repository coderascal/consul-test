package com.bloomberg.bfs.radar;

public class RadarException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public RadarException(String message) {
    super(message);
  }

  public RadarException(String message, Throwable t) {
    super(message,t);
  }

}
