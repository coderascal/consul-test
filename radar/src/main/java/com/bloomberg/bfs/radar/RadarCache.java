package com.bloomberg.bfs.radar;

import java.util.HashMap;
import java.util.Map;

import com.bloomberg.core.BBLogger;
import com.google.common.net.HostAndPort;

public class RadarCache {
  private BBLogger log = BBLogger.getLogger(getClass());

  private RadarSettings radarSettings;
  private Map<EndpointIdentifier, EndpointCache> endpointCaches = new HashMap<EndpointIdentifier, EndpointCache>();

  public RadarSettings getRadarSettings(){
    return radarSettings;
  }
  public void setRadarSettings(RadarSettings radarSettings){
    this.radarSettings = radarSettings;
  }
  public RadarCache withRadarSettings(RadarSettings radarSettings){
    setRadarSettings(radarSettings);
    return this;
  }

  public HostAndPort getQueryEndpoint(String tenant, String cloud, String collection) {
    EndpointIdentifier endpointIdentifier = new EndpointIdentifier(tenant, cloud, collection, "query");

    if(!endpointCaches.containsKey(endpointIdentifier)){
      log.info("Creating new EndpointCache for %s", endpointIdentifier);

      EndpointCache cache = new EndpointCache(endpointIdentifier, radarSettings);
      cache.start();
      endpointCaches.put(endpointIdentifier, cache);
    }

    return endpointCaches.get(endpointIdentifier).getEndpoint();
  }
}
