package com.bloomberg.web.radar;

import java.util.stream.Stream;

import org.junit.Test;

public class RadarFrameworkTest {
  
  @Test
  public void toStringTest(){
    RadarFramework framework = new RadarFramework(new RadarSettings());
    
    framework.withTag("foo", "bar");
    framework.withoutTag("baz");
    
    Stream.Builder<Service> s;
    
//    framework.getStream().
  }
  
}
