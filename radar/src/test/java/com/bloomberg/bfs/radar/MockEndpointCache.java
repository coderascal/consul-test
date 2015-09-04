package com.bloomberg.bfs.radar;

import java.io.IOException;
import java.util.LinkedList;

import com.google.common.net.HostAndPort;

public class MockEndpointCache implements EndpointCache {

  private LinkedList<HostAndPort> endpoints = new LinkedList<HostAndPort>();

  public MockEndpointCache(HostAndPort[] endpoints){
    for(HostAndPort endpoint : endpoints){
      this.endpoints.add(endpoint);
    }
  }

  public HostAndPort getEndpoint() {
    if(endpoints.isEmpty()){
      return null;
    }

    HostAndPort endpoint = endpoints.removeFirst();
    endpoints.addLast(endpoint);
    return endpoint;
  }

  public void close() throws IOException {
    // do nothing
  }

}
