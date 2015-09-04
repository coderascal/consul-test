package com.bloomberg.bfs.radar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bloomberg.core.BBLogger;

public class RadarCacheTest{

  private BBLogger log = BBLogger.getLogger(getClass());

  private MockEndpointCacheFactory endpointCacheFactory;

  @Before
  public void beforeTest(){
    endpointCacheFactory = new MockEndpointCacheFactory();
    endpointCacheFactory.registerEndpoints(new EndpointIdentifier("tenant1", "cloud1", "collection1", "query"), "host1:10001", "host2:10001", "host2:10002", "host3:10001");
    endpointCacheFactory.registerEndpoints(new EndpointIdentifier("tenant1", "cloud1", "collection1", "injest"), "host1:20001", "host2:20001", "host2:20002");
    endpointCacheFactory.registerEndpoints(new EndpointIdentifier("tenant1", "cloud1", "collection2", "query"), "host1:30001");
    endpointCacheFactory.registerEndpoints(new EndpointIdentifier("tenant2", "cloud1", "collection1", "query"), "host1:40001", "host1:40002", "host1:40003", "host2:40001");
    endpointCacheFactory.registerEndpoints(new EndpointIdentifier("tenant1", "cloud1", "collection1", "admin"));
  }

  @Test
  public void multipleEndpointRoundRobinTest(){
    RadarCache cache = new RadarCache();
    cache.setEndpointCacheFactory(endpointCacheFactory);
    cache.setRadarSettings(new RadarSettings());

    Assert.assertEquals("host1:10001", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host2:10001", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host2:10002", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host3:10001", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host1:10001", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host2:10001", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host2:10002", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host3:10001", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host1:10001", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host2:10001", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host2:10002", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host3:10001", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());

  }

  @Test
  public void getAllTest(){
    RadarCache cache = new RadarCache();
    cache.setEndpointCacheFactory(endpointCacheFactory);
    cache.setRadarSettings(new RadarSettings());

    Assert.assertEquals("host1:10001", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host1:20001", cache.getInjestEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host1:30001", cache.getQueryEndpoint("tenant1", "cloud1", "collection2").toString());
    Assert.assertEquals("host1:40001", cache.getQueryEndpoint("tenant2", "cloud1", "collection1").toString());

    Assert.assertEquals("host2:10001", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host2:20001", cache.getInjestEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host1:30001", cache.getQueryEndpoint("tenant1", "cloud1", "collection2").toString());
    Assert.assertEquals("host1:40002", cache.getQueryEndpoint("tenant2", "cloud1", "collection1").toString());

    Assert.assertEquals("host2:10002", cache.getQueryEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host2:20002", cache.getInjestEndpoint("tenant1", "cloud1", "collection1").toString());
    Assert.assertEquals("host1:30001", cache.getQueryEndpoint("tenant1", "cloud1", "collection2").toString());
    Assert.assertEquals("host1:40003", cache.getQueryEndpoint("tenant2", "cloud1", "collection1").toString());
  }

  @Test
  public void singleEndpointRoundRobinTest(){
    RadarCache cache = new RadarCache();
    cache.setEndpointCacheFactory(endpointCacheFactory);
    cache.setRadarSettings(new RadarSettings());

    Assert.assertEquals("host1:30001", cache.getQueryEndpoint("tenant1", "cloud1", "collection2").toString());
    Assert.assertEquals("host1:30001", cache.getQueryEndpoint("tenant1", "cloud1", "collection2").toString());
    Assert.assertEquals("host1:30001", cache.getQueryEndpoint("tenant1", "cloud1", "collection2").toString());
    Assert.assertEquals("host1:30001", cache.getQueryEndpoint("tenant1", "cloud1", "collection2").toString());
    Assert.assertEquals("host1:30001", cache.getQueryEndpoint("tenant1", "cloud1", "collection2").toString());
  }

  @Test
  public void noEndpointTest(){
    RadarCache cache = new RadarCache();
    cache.setEndpointCacheFactory(endpointCacheFactory);
    cache.setRadarSettings(new RadarSettings());

    Assert.assertNull(cache.getAdminEndpoint("tenant1", "cloud1", "collection1"));
  }

  @Test
  public void nonExistantTest(){
    RadarCache cache = new RadarCache();
    cache.setEndpointCacheFactory(endpointCacheFactory);
    cache.setRadarSettings(new RadarSettings());

    Assert.assertNull(cache.getAdminEndpoint("non", "existant", "collection1"));
  }

}