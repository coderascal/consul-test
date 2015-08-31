package com.bloomberg.bfs.radar;

import java.util.List;

import com.bloomberg.core.BBLogger;
import com.google.common.net.HostAndPort;

public class RadarSettings {
  private BBLogger log = BBLogger.getLogger(getClass());

  private List<HostAndPort> discoveryEndpoints;

  public List<HostAndPort> getDiscoveryEndpoints(){
    return discoveryEndpoints;
  }
  public void setDiscoveryEndpoints(List<HostAndPort> discoveryEndpoints){
    this.discoveryEndpoints = discoveryEndpoints;
    log.trace("discoveryEndpoints set to %s", discoveryEndpoints);
  }
  public RadarSettings withDiscoveryEndpoints(List<HostAndPort> discoveryEndpoints){
    setDiscoveryEndpoints(discoveryEndpoints);
    return this;
  }

}
