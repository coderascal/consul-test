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

public class EndpointCache implements Closeable {
  private BBLogger log = BBLogger.getLogger(getClass());

  private EndpointIdentifier endpointIdentifier;
  private RadarSettings radarSettings;

  private ServiceHealthCache cache;
  private Listener<HostAndPort, ServiceHealth> updateListener;

  private LinkedList<HostAndPort> endpoints;

  public EndpointCache(EndpointIdentifier endpointIdentifier, RadarSettings radarSettings){
    this.endpointIdentifier = endpointIdentifier;
    this.radarSettings = radarSettings;

    updateListener = new Listener<HostAndPort, ServiceHealth>() {
      public void notify(Map<HostAndPort, ServiceHealth> newValues) {
	StringBuilder builder = new StringBuilder();

	builder.append("I have been notified of: ");

	LinkedList<HostAndPort> endpoints = new LinkedList<HostAndPort>();
	for(HostAndPort endpoint : newValues.keySet()){
	  endpoints.add(endpoint);

	  builder.append(String.format("%s:%s, ", endpoint.getHostText(), endpoint.getPort()));
	}

	log.info(builder.toString());
	setEndpoints(endpoints);
      }
    };
  }

  public void start() {
    log.info("Loading endpoint cache for %s", endpointIdentifier);

    for(HostAndPort discoveryEndpoint : radarSettings.getDiscoveryEndpoints()){
      try{
	log.trace("Getting endpoints for %s from discovery endpoint %s", endpointIdentifier, discoveryEndpoint);

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
