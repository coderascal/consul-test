package com.bloomberg.web.radar;

public class Node {

  private String name;
  private String address;
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Node withName(String name){
    setName(name);
    return this;
  }
  
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public Node withAddress(String address){
    setAddress(address);
    return this;
  }
  
  @Override
  public String toString() {
    return String.format("Node [name=%s, address=%s]", name, address);
  }
  
}
