package com.bloomberg.web.radar;

import org.junit.Assert;
import org.junit.Test;

import com.bloomberg.web.radar.RadarSettings;

public class RadarSettingsTest {
  
  @Test
  public void toStringTest(){
    RadarSettings settings = new RadarSettings();
    
    Assert.assertEquals("RadarSettings [discovererEndpoint=null]", settings.toString());
  }
  
}
