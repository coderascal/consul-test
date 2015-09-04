package com.bloomberg.bfs.radar;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import com.bloomberg.core.BBLogger;
import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.cache.ConsulCache.Listener;
import com.orbitz.consul.cache.ServiceHealthCache;
import com.orbitz.consul.model.health.ServiceHealth;

public class ConsulEndpointCache implements Closeable, EndpointCache {
  private BBLogger log = BBLogger.getLogger(getClass());

  private EndpointIdentifier endpointIdentifier;
  private RadarSettings radarSettings;

  private ServiceHealthCache cache;
  private Listener<HostAndPort, ServiceHealth> updateListener;

  private LinkedList<HostAndPort> endpoints;

  public ConsulEndpointCache(RadarSettings radarSettings, final EndpointIdentifier endpointIdentifier){
    this.radarSettings = radarSettings;
    this.endpointIdentifier = endpointIdentifier;

    // Create the update listener
    updateListener = new Listener<HostAndPort, ServiceHealth>() {
      public void notify(Map<HostAndPort, ServiceHealth> newValues) {
	StringBuilder messageBuilder = new StringBuilder();

	LinkedList<HostAndPort> endpoints = new LinkedList<HostAndPort>();
	for(HostAndPort endpoint : newValues.keySet()){
	  endpoints.add(endpoint);
	  messageBuilder.append(String.format("%s:%s, ", endpoint.getHostText(), endpoint.getPort()));
	}

	log.info(String.format("Received updates for %s - new endpoints are %s", endpointIdentifier, messageBuilder.toString()));
	setEndpoints(endpoints);
      }
    };

    log.debug("Starting endpoint cache for %s", endpointIdentifier);
    for(HostAndPort discoveryEndpoint : radarSettings.getDiscoveryEndpoints()){
      try{
	log.debug("Getting endpoints for %s from discovery endpoint %s", endpointIdentifier, discoveryEndpoint);

	// Get current state
	HealthClient healthClient = Consul.newClient(discoveryEndpoint.getHostText(), discoveryEndpoint.getPort()).healthClient();
	endpoints = new LinkedList<HostAndPort>();
	for(ServiceHealth result : healthClient.getHealthyServiceInstances(endpointIdentifier.toString()).getResponse()){
	  endpoints.add(HostAndPort.fromParts(result.getNode().getAddress(), result.getService().getPort()));
	}
	setEndpoints(endpoints);

	// Setup watcher
	cache = ServiceHealthCache.newCache(healthClient, endpointIdentifier.toString());
	cache.addListener(updateListener);
	cache.start();

	log.info("Loaded endpoint cache for %s from discovery at %s", endpointIdentifier, discoveryEndpoint);

	// exit early as we already found all we'll find from any of the discovery endpoints
	return;
      }
      catch(Throwable t){
	continue; // try next
      }
    }

    // If we get here then we failed to load
    throw new RadarException(String.format("Failed to load endpoint cache for %s", endpointIdentifier));
  }

  public synchronized HostAndPort getEndpoint(){
    if(endpoints.isEmpty()){
      return  null;
    }

    HostAndPort endpoint = endpoints.removeFirst();
    endpoints.addLast(endpoint);
    return endpoint;
  }

  private synchronized void setEndpoints(LinkedList<HostAndPort> endpoints){
    this.endpoints = endpoints;
  }

  public void close() throws IOException {
    cache.removeListener(updateListener);
  }
}
