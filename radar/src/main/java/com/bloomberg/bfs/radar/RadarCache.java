package com.bloomberg.bfs.radar;

import java.util.HashMap;
import java.util.Map;

import com.bloomberg.core.BBLogger;
import com.google.common.net.HostAndPort;

public class RadarCache {
  private BBLogger log = BBLogger.getLogger(getClass());

  private RadarSettings radarSettings;
  private EndpointCacheFactory endpointCacheFactory;

  private Map<EndpointIdentifier, EndpointCache> endpointCaches = new HashMap<EndpointIdentifier, EndpointCache>();

  public RadarSettings getRadarSettings(){
    return radarSettings;
  }
  public void setRadarSettings(RadarSettings radarSettings){
    this.radarSettings = radarSettings;
  }

  public EndpointCacheFactory getEndpointCacheFactory(){
    return endpointCacheFactory;
  }
  public void setEndpointCacheFactory(EndpointCacheFactory endpointCacheFactory){
    this.endpointCacheFactory = endpointCacheFactory;
  }

  public HostAndPort getQueryEndpoint(String tenant, String cloud, String collection) {
    return getEndpoint(tenant, cloud, collection, "query");
  }
  public HostAndPort getInjestEndpoint(String tenant, String cloud, String collection) {
    return getEndpoint(tenant, cloud, collection, "injest");
  }
  public HostAndPort getAdminEndpoint(String tenant, String cloud, String collection) {
    return getEndpoint(tenant, cloud, collection, "admin");
  }

  private HostAndPort getEndpoint(String tenant, String cloud, String collection, String endpointType){
    EndpointIdentifier endpointIdentifier = new EndpointIdentifier(tenant, cloud, collection, endpointType);

    if(!endpointCaches.containsKey(endpointIdentifier)){
      log.info("Creating new EndpointCache for %s", endpointIdentifier);

      endpointCaches.put(endpointIdentifier, endpointCacheFactory.createEndpointCache(radarSettings, endpointIdentifier));
    }

    return endpointCaches.get(endpointIdentifier).getEndpoint();
  }
}
