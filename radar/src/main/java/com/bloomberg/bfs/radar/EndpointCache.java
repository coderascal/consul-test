package com.bloomberg.bfs.radar;

import java.io.Closeable;
import java.io.IOException;

import com.google.common.net.HostAndPort;

public interface EndpointCache extends Closeable {

  public abstract HostAndPort getEndpoint();
  public abstract void close() throws IOException;

}