package com.bloomberg.bfs.radar;

public interface EndpointCacheFactory {

  public abstract EndpointCache createEndpointCache(RadarSettings radarSettings, EndpointIdentifier endpointIdentifier);

}
