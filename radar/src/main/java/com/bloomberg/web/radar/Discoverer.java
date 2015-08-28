package com.bloomberg.web.radar;

import java.net.URL;
import java.util.Collection;
import java.util.Collection;

public interface Discoverer {
  
  public URL getEndpoint(String applicationName);
  public URL getEndpoint(String applicationName, Collection<String> tags);
  public URL getEndpoint(String applicationName, Collection<String> tags, Collection<String> dataCenters);
  
  public Collection<URL> getEndpoints(String applicationName);
  public Collection<URL> getEndpoints(String applicationName, Collection<String> tags);
  public Collection<URL> getEndpoints(String applicationName, Collection<String> tags, Collection<String> dataCenters);
  
  
  // withTags(....).withDataCenter(....)
  
}
