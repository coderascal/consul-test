package com.bloomberg.web.radar;

import java.net.URL;
import java.util.List;

public class RadarSettings {

  private String discoveryEndpoint;
  private int discoveryPort;
  
  public String getDiscoveryEndpoint() {
    return discoveryEndpoint;
  }
  public void setDiscoveryEndpoint(String discoveryEndpoint) {
    this.discoveryEndpoint = discoveryEndpoint;
  }
  public RadarSettings withDiscoveryEndpoint(String discoveryEndpoint){
    this.discoveryEndpoint = discoveryEndpoint;
    return this;
  }
  
  public int getDiscoveryPort() {
    return discoveryPort;
  }
  public void setDiscoveryPort(int discoveryPort) {
    this.discoveryPort = discoveryPort;
  }
  public RadarSettings withDiscoveryPort(int discoveryPort){
    this.discoveryPort = discoveryPort;
    return this;
  }
  
  @Override
  public String toString() {
    return String.format("RadarSettings [discoveryEndpoint=%s, discoveryPort=%s]", discoveryEndpoint, discoveryPort);
  }
  

}
