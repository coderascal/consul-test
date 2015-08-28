package com.bloomberg.web.radar;

import java.util.Set;

public class Service {

  private String id;
  private String name;
  private Set<String> tags;
  
  private Node node;
  private String address;
  private int port;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public Service withId(String id){
    setId(id);
    return this;
  }
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Service withName(String name){
    setId(name);
    return this;
  }
  
  public Set<String> getTags() {
    return tags;
  }
  public void setTags(Set<String> tags) {
    this.tags = tags;
  }
  public Service withTags(Set<String> tags){
    setTags(tags);
    return this;
  }
  
  public Node getNode() {
    return node;
  }
  public void setNode(Node node) {
    this.node = node;
  }
  public Service withNode(Node node){
    setNode(node);
    return this;
  }
  
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public Service withAddress(String address){
    setAddress(address);
    return this;
  }
  
  public int getPort() {
    return port;
  }
  public void setPort(int port) {
    this.port = port;
  }
  public Service withPort(int port){
    setPort(port);
    return this;
  }
  
}
