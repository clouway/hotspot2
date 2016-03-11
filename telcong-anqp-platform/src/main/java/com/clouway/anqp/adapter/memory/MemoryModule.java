package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.*;
import com.clouway.anqp.EAP.Method;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.LinkedHashMap;

import static com.clouway.anqp.IpType.NOT_AVAILABLE;
import static com.clouway.anqp.IpType.PORT_RESTRICTED;
import static com.clouway.anqp.IpType.PUBLIC;
import static com.clouway.anqp.IpType.SINGLE_NAT_PRIVATE;
import static com.clouway.anqp.IpType.UNKNOWN;

/**
 * MemoryModule is a guice module which is used for the In-Memory configuration of the temporary or caching implementations.
 */
public class MemoryModule extends AbstractModule {
  @Override
  protected void configure() {
  }

  @Provides
  @Singleton
  public VenueFinder getVenueProfile() {
    return new InMemoryVenueFinder(
            new VenueItem(new VenueGroup("assembly"), new VenueTypeList(
                    new VenueType("arena"),
                    new VenueType("stadium"),
                    new VenueType("passenger-terminal"),
                    new VenueType("amphitheater"),
                    new VenueType("amusement-park"),
                    new VenueType("place-of-worship"),
                    new VenueType("convention-center"),
                    new VenueType("library"),
                    new VenueType("museum"),
                    new VenueType("restaurant"),
                    new VenueType("theater"),
                    new VenueType("bar"),
                    new VenueType("coffee-shop"),
                    new VenueType("zoo-or-aquarium"),
                    new VenueType("emergency-cord-center"),
                    new VenueType("unspecified"))),
            new VenueItem(new VenueGroup("business"), new VenueTypeList(
                    new VenueType("doctor"),
                    new VenueType("bank"),
                    new VenueType("fire-station"),
                    new VenueType("police-station"),
                    new VenueType("post-office"),
                    new VenueType("professional-office"),
                    new VenueType("research-and-dev-facility"),
                    new VenueType("attorney-office"),
                    new VenueType("unspecified"))),
            new VenueItem(new VenueGroup("educational"), new VenueTypeList(
                    new VenueType("school-primary"),
                    new VenueType("school-secondary"),
                    new VenueType("univ-or-college"),
                    new VenueType("unspecified"))),
            new VenueItem(new VenueGroup("factory-and-industrial"), new VenueTypeList(
                    new VenueType("factory"),
                    new VenueType("unspecified"))),
            new VenueItem(new VenueGroup("institutional"), new VenueTypeList(
                    new VenueType("hospital"),
                    new VenueType("long-term-care"),
                    new VenueType("alc-drug-rehab"),
                    new VenueType("group-home"),
                    new VenueType("prison-or-jail"),
                    new VenueType("unspecified"))),
            new VenueItem(new VenueGroup("mercantile"), new VenueTypeList(
                    new VenueType("retail-store"),
                    new VenueType("grocery-market"),
                    new VenueType("auto-service-station"),
                    new VenueType("shopping-mall"),
                    new VenueType("gas-station"),
                    new VenueType("unspecified"))),
            new VenueItem(new VenueGroup("outdoor"), new VenueTypeList(
                    new VenueType("muni-mesh-network"),
                    new VenueType("city-park"),
                    new VenueType("rest-area"),
                    new VenueType("traffic-control"),
                    new VenueType("bus-stop"),
                    new VenueType("kisok"),
                    new VenueType("unspecified"))),
            new VenueItem(new VenueGroup("residential"), new VenueTypeList(
                    new VenueType(" private-residence"),
                    new VenueType("hotel"),
                    new VenueType("dormitory"),
                    new VenueType("boarding-house"),
                    new VenueType("unspecified"))),
            new VenueItem(new VenueGroup("storage"), new VenueTypeList(
                    new VenueType("unspecified")
            )),
            new VenueItem(new VenueGroup("utility-and-misc"), new VenueTypeList(
                    new VenueType("unspecified")
            )),
            new VenueItem(new VenueGroup("vehicular"), new VenueTypeList(
                    new VenueType("unspecified"),
                    new VenueType("automobile-or-truck"),
                    new VenueType("airplane"),
                    new VenueType("bus"),
                    new VenueType("ferry"),
                    new VenueType("ship"),
                    new VenueType("train"),
                    new VenueType("motor-bike")
            )));
  }

  @Provides
  @Singleton
  public IpTypeCatalog getIpTypeCatalog() {
    // The catalog contains only currently supported types
    return new InMemoryIpTypeCatalog(new LinkedHashMap<String, Integer>() {{
      put(NOT_AVAILABLE.name(), 0);
      put(PUBLIC.name(), 1);
      put(PORT_RESTRICTED.name(), 2);
      put(SINGLE_NAT_PRIVATE.name(), 3);
      put(UNKNOWN.name(), 7);
    }});
  }

  @Provides
  @Singleton
  public AuthenticationTypeFinder getAuthenticationTypeFinder() {
    return new InMemoryAuthenticationTypeFinder(
            new AuthenticationType(0, "Acceptance of terms and conditions"),
            new AuthenticationType(1, "On-line enrollment supported"),
            new AuthenticationType(2, "http/https redirection"),
            new AuthenticationType(3, "DNS redirection"));
  }

  @Singleton
  @Provides
  public CapabilityCatalog getCapabilityCatalog() {
    return new InMemoryCapabilityCatalog(new LinkedHashMap<Integer, Capability>() {{
      put(256, new Capability(256, "ANQP Query List"));
      put(257, new Capability(257, "ANQP Capability list"));
      put(258, new Capability(258, "Venue Name information"));
      put(259, new Capability(259, "Emergency Call Number information"));
      put(260, new Capability(260, "Network Authentication Type information"));
      put(261, new Capability(261, "Roaming Consortium list"));
      put(262, new Capability(262, "IP Address Type Availability information"));
      put(263, new Capability(263, "NAI Realm list"));
      put(264, new Capability(264, "3GPP Cellular Network information"));
      put(265, new Capability(265, "AP Geospatial Location"));
      put(266, new Capability(266, "AP Civic Location"));
      put(267, new Capability(267, "AP Location Public Identifier URI"));
      put(268, new Capability(268, "Domain Name list"));
    }});
  }

  @Provides
  @Singleton
  public EncodingCatalog getEncodingCatalog() {
    return new InMemoryEncodingCatalog(Encoding.values());
  }

  @Provides
  @Singleton
  public EapMethodCatalog getEapMethodCatalog() {
    return new InMemoryEapMethodCatalog(Method.values());
  }

  @Provides
  @Singleton
  public EmergencyAlertRepository getEmergencyAlertCatalog() {
    return new InMemoryEmergencyAlertRepository(new CapMessage(
            new ID(1),
            new CapAlert(Lists.newArrayList(new CapInfo(
                    Lists.newArrayList(new CapResource("error_resource1")),
                    Lists.newArrayList(new CapArea("error_area1"))
            ))))
    );
  }
}