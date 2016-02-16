package com.clouway.anqp.adapter.memory;

import com.clouway.anqp.VenueFinder;
import com.clouway.anqp.VenueGroup;
import com.clouway.anqp.VenueItem;
import com.clouway.anqp.VenueType;
import com.clouway.anqp.VenueTypeList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

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
}