package com.bloomberg.bfs.radar;

import java.util.HashMap;
import java.util.Map;

import com.google.common.net.HostAndPort;

public class MockEndpointCacheFactory implements EndpointCacheFactory {

  private Map<EndpointIdentifier, HostAndPort[]> registeredEndpoints = new HashMap<EndpointIdentifier, HostAndPort[]>();

  public void registerEndpoints(EndpointIdentifier endpointIdentifier){
    registerEndpoints(endpointIdentifier, new HostAndPort[0]);
  }

  public void registerEndpoints(EndpointIdentifier endpointIdentifier, String ... endpoints){
    HostAndPort[] converted = new HostAndPort[endpoints.length];
    for(int idx = 0; idx < endpoints.length; ++idx){
      converted[idx] = HostAndPort.fromString(endpoints[idx]);
    }
    registerEndpoints(endpointIdentifier, converted);
  }

  public void registerEndpoints(EndpointIdentifier endpointIdentifier, HostAndPort ... endpoints){
    registeredEndpoints.put(endpointIdentifier, endpoints);
  }

  public EndpointCache createEndpointCache(RadarSettings radarSettings, EndpointIdentifier endpointIdentifier) {
    HostAndPort[] endpoints = registeredEndpoints.get(endpointIdentifier);
    if(null == endpoints){
      endpoints = new HostAndPort[0];
    }

    return new MockEndpointCache(endpoints);
  }

}
