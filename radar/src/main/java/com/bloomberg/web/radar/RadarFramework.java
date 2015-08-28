package com.bloomberg.web.radar;


public class RadarFramework {

  private final String QUERY_SERVICE_SUFFIX = "query";
  private final String INJEST_SERVICE_SUFFIX = "injest";
  
  public String getCloudQueryServiceName(String cloudName){
    return getCloudServiceName(cloudName, QUERY_SERVICE_SUFFIX);
  }
  
  public String getCloudInjestServiceName(String cloudName){
    return getCloudServiceName(cloudName, INJEST_SERVICE_SUFFIX);
  }
  
  private String getCloudServiceName(String cloudName, String suffix){
    return String.format("%s-%s", cloudName, suffix);
  }
}
