package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.*;
import com.clouway.anqp.EAP.Method;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.LinkedHashMap;

import static com.clouway.anqp.Auth.Info.CREDENTIAL_TYPE;
import static com.clouway.anqp.Auth.Info.EXPANDED_EAP_METHOD;
import static com.clouway.anqp.Auth.Info.EXPANDED_INNER_EAP_METHOD;
import static com.clouway.anqp.Auth.Info.INNER_AUTHENTICATION_EAP_METHOD_TYPE;
import static com.clouway.anqp.Auth.Info.NON_EAP_INNER_AUTHENTICATION_TYPE;
import static com.clouway.anqp.Auth.Info.TUNNELED_EAP_METHOD_CREDENTIAL_TYPE;
import static com.clouway.anqp.Auth.Type.*;

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
  public IPv4AvailabilityCatalog getV4Catalog() {
    // The catalog contains only currently supported types
    return new InMemoryIPv4AvailabilityCatalog(
            IPv4.Availability.NOT_AVAILABLE,
            IPv4.Availability.PUBLIC,
            IPv4.Availability.PORT_RESTRICTED,
            IPv4.Availability.SINGLE_NAT_PRIVATE,
            IPv4.Availability.UNKNOWN
    );
  }

  @Provides
  @Singleton
  public IPv6AvailabilityCatalog getV6Catalog() {
    // The catalog contains only currently supported types
    return new InMemoryIPv6AvailabilityCatalog(
            IPv6.Availability.NOT_AVAILABLE,
            IPv6.Availability.AVAILABLE,
            IPv6.Availability.UNKNOWN
    );
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

  @Provides
  @Singleton
  public EapAuthCatalog getEapAuthenticationCatalog() {
    return new InMemoryEapAuthCatalog(
            new AuthEntry(EXPANDED_EAP_METHOD, EXPANDED_EAP_METHOD_SUBFIELD),
            new AuthEntry(NON_EAP_INNER_AUTHENTICATION_TYPE, RESERVED_0, PAP_1, CHAP_2, MSCHAP_3, MSCHAPV2_4),
            new AuthEntry(INNER_AUTHENTICATION_EAP_METHOD_TYPE, EAP_TLS, EAP_SIM, EAP_TTLS, EAP_AKA),
            new AuthEntry(EXPANDED_INNER_EAP_METHOD, EXPANDED_EAP_METHOD_SUBFIELD),
            new AuthEntry(CREDENTIAL_TYPE, SIM_1, USIM_2, NFC_SECURE_ELEMENT_3, HARDWARE_TOKEN_4, SOFTOKEN_5, CERTIFICATE_6, USERNAME_PASSWORD_7, NONE_8, RESERVED_9, VENDOR_SPECIFIC_10),
            new AuthEntry(TUNNELED_EAP_METHOD_CREDENTIAL_TYPE, SIM_1, USIM_2, NFC_SECURE_ELEMENT_3, HARDWARE_TOKEN_4, SOFTOKEN_5, CERTIFICATE_6, USERNAME_PASSWORD_7, RESERVED_8, ANONYMOUS_9, VENDOR_SPECIFIC_10));
  }
}