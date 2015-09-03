package com.bloomberg.bfs.radar;

public class EndpointIdentifier {

  private String tenant;
  private String cloud;
  private String collection;
  private String endpointType;

  public EndpointIdentifier(String tenant, String cloud, String collection, String endpointType){
    setTenant(tenant);
    setCloud(cloud);
    setCollection(collection);
    setEndpointType(endpointType);
  }

  public String getTenant() {
    return tenant;
  }
  public void setTenant(String tenant) {
    this.tenant = tenant;
  }
  public EndpointIdentifier withTenant(String tenant){
    setTenant(tenant);
    return this;
  }

  public String getCloud() {
    return cloud;
  }
  public void setCloud(String cloud) {
    this.cloud = cloud;
  }
  public EndpointIdentifier withCloud(String cloud){
    setCloud(cloud);
    return this;
  }

  public String getCollection() {
    return collection;
  }
  public void setCollection(String collection) {
    this.collection = collection;
  }
  public EndpointIdentifier withCollection(String collection){
    setCollection(collection);
    return this;
  }

  public String getEndpointType() {
    return endpointType;
  }
  public void setEndpointType(String endpointType) {
    this.endpointType = endpointType;
  }
  public EndpointIdentifier withEndpointType(String endpointType){
    setEndpointType(endpointType);
    return this;
  }

  @Override
  public String toString(){
    return String.format("%s-%s-%s-%s", tenant, cloud, collection, endpointType);
  }

  @Override
  public int hashCode(){
    return toString().hashCode();
  }

  /* (non-Javadoc)
   * @see com.bloomberg.bfs.radar.EndpointIdentifier#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj){
    if(obj instanceof EndpointIdentifier){
      EndpointIdentifier other = (EndpointIdentifier)obj;
      return collection.equals(other.collection) && cloud.equals(other.cloud) && tenant.equals(other.tenant) && endpointType.equals(other.endpointType);
    }

    return false;
  }
}
