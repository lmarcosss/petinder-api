package org.ifrs.client;

import javax.ws.rs.Produces;
import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.ifrs.model.GeolocationModel;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@RegisterRestClient(configKey="geolocation-api")
public interface GeolocationClient {

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public GeolocationModel getGeolocation(
      @QueryParam String key,
      @QueryParam Double lat,
      @QueryParam Double lon,
      @QueryParam("accept-language") String acceptLanguage,
      @QueryParam int zoom,
      @QueryParam String format
    );
}
