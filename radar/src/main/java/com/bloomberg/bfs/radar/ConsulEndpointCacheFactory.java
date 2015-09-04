package com.bloomberg.bfs.radar;

public class ConsulEndpointCacheFactory implements EndpointCacheFactory {

  public EndpointCache createEndpointCache(RadarSettings radarSettings, EndpointIdentifier endpointIdentifier) {
    return new ConsulEndpointCache(radarSettings, endpointIdentifier);
  }

}
