package org.ifrs.model;

public class GeolocationModel {
  public AddressModel address;

  public String getCity() {
    return address.city;
  }

  public String getTown() { 
    return address.town;
  }

  public String getVillage() {
    return address.village;
  }
}
