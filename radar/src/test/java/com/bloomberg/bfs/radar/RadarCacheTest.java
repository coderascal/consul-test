package com.bloomberg.bfs.radar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;

public class RadarCacheTest{

  private RadarSettings radarSettings;

  @Before
  public void beforeTest(){
    radarSettings = new RadarSettings()
    .withDiscoveryEndpoints(Lists.newArrayList(
	HostAndPort.fromParts("172.20.20.11", 8500),
	HostAndPort.fromParts("172.20.20.12", 8500),
	HostAndPort.fromParts("172.20.20.13", 8500),
	HostAndPort.fromParts("172.20.20.14", 8500),
	HostAndPort.fromParts("172.20.20.15", 8500)
	));
  }

  @Test
  public void getExistingServiceTest() throws InterruptedException{
    RadarCache radar = new RadarCache().withRadarSettings(radarSettings);
    HostAndPort first = radar.getQueryEndpoint("tenant", "cloud", "collection");

    HostAndPort second = radar.getQueryEndpoint("isys", "drqs", "drq1");

    Assert.assertNotNull(first);
    Assert.assertNotNull(second);
    Assert.assertNotEquals(first.toString(), second.toString());

    while(true){
      //      BBLogger.getLogger(getClass()).info("I am looping");
      Thread.sleep(5000);
    }
  }
}

//package com.bloomberg.bfs.radar;
//
//import org.junit.Test;
//
//public class DiscovererTest {
//
//  @Test
//  public void doitTest(){
//    Discoverer d = new Discoverer();
//    d.doit();
//  }
//
//  @Test
//  public void subscribeTest() throws Exception{
//    Discoverer d = new Discoverer();
//    d.subscribe();
//
//    System.in.read();
//  }
//}
