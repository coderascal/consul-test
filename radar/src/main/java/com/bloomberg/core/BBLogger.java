package com.bloomberg.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class BBLogger {

  Logger log = null;
  final Marker fatal = MarkerFactory.getMarker("FATAL");

  public enum Level {
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR,
    FATAL
  }

  private BBLogger(Logger log){
    this.log = log;
  }

  public static BBLogger getLogger(Class<?> clazz){
    return new BBLogger(LoggerFactory.getLogger(clazz));
  }

  public void trace(String message, Object...args){
    log.trace(String.format(message, args));
  }

  public void debug(String message, Object...args){
    log.debug(String.format(message, args));
  }

  public void info(String message, Object...args){
    log.info(String.format(message, args));
  }

  public void info(Throwable t, String message, Object...args){
    log.info(String.format(message, args), t);
  }

  public void warn(String message, Object...args){
    log.warn(String.format(message, args));
  }

  public void warn(Throwable t, String message, Object...args){
    log.warn(String.format(message, args), t);
  }

  public void error(String message, Object...args){
    log.error(String.format(message, args));
  }

  public void error(Throwable t, String message, Object...args){
    log.error(String.format(message, args), t);
  }

  public void fatal(String message, Object...args){
    log.error(fatal, String.format(message, args));
  }

  public void fatal(Throwable t, String message, Object...args){
    log.error(fatal, String.format(message, args), t);
  }

}
