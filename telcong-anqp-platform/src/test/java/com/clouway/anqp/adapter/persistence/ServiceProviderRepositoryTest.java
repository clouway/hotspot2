package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.*;
import com.clouway.anqp.DomainNameList;
import com.clouway.anqp.ID;
import com.clouway.anqp.NAI;
import com.clouway.anqp.Network3GPP;
import com.clouway.anqp.NewServiceProvider;
import com.clouway.anqp.ServiceProvider;
import com.clouway.anqp.ServiceProviderException;
import com.clouway.anqp.api.datastore.DatastoreCleaner;
import com.clouway.anqp.api.datastore.DatastoreRule;
import com.clouway.anqp.api.datastore.FakeDatastore;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.clouway.anqp.NewServiceProviderBuilder.newServiceProvider;
import static com.clouway.anqp.ServiceProviderBuilder.newProvider;
import static com.clouway.anqp.util.matchers.EqualityMatchers.deepEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 */
public class ServiceProviderRepositoryTest {
  @ClassRule
  public static DatastoreRule datastoreRule = new DatastoreRule();

  @Rule
  public DatastoreCleaner datastoreCleaner = new DatastoreCleaner(datastoreRule.db());

  private FakeDatastore datastore = new FakeDatastore(datastoreRule.db());

  private PersistentServiceProviderRepository repository = new PersistentServiceProviderRepository(datastore);

  @Test(expected = ServiceProviderException.class)
  public void createProviderWithExistingName() throws Exception {
    NewServiceProvider provider1 = newServiceProvider().name("name").build();
    NewServiceProvider provider2 = newServiceProvider().name("name").build();

    repository.create(provider1);
    repository.create(provider2);
  }

  @Test
  public void findByUnknownId() throws Exception {
    Optional<ServiceProvider> result = repository.findById(new ID("ID"));

    assertFalse(result.isPresent());
  }

  @Test
  public void findAll() throws Exception {
    DomainNameList names = new DomainNameList(Lists.newArrayList("dName"));
    List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "123", "445"));
    List<RoamingConsortium> list =  Lists.newArrayList(new RoamingConsortium("name", "0xFFFFFF"));
    List<NAI> naiList = Lists.newArrayList(new NAI("N/A"));

    NewServiceProvider newProvider1 = new NewServiceProvider("name1", "descr1", networks, names, list, naiList);
    NewServiceProvider newProvider2 = new NewServiceProvider("name2", "descr2", networks, names, list, naiList);

    Object id1 = repository.create(newProvider1);
    Object id2 = repository.create(newProvider2);

    List<ServiceProvider> result = repository.findAll();

    ServiceProvider provider1 = new ServiceProvider(new ID(id1), "name1", "descr1", networks, names, list, naiList);
    ServiceProvider provider2 = new ServiceProvider(new ID(id2), "name2", "descr2", networks, names, list, naiList);

    List<ServiceProvider> providers = Lists.newArrayList(provider1, provider2);

    assertThat(result, deepEquals(providers));
  }

  @Test
  public void update() throws Exception {
    DomainNameList names = new DomainNameList(Lists.newArrayList("dName"));
    List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "123", "445"));
    List<RoamingConsortium> list =  Lists.newArrayList(new RoamingConsortium("name", "0xAAFFAA"));
    List<NAI> naiList = Lists.newArrayList(new NAI("N/A"));
   
    Object id = repository.create(new NewServiceProvider("name", "descr", networks, names, list, naiList));

    List<Network3GPP> newNetworks = Lists.newArrayList(new Network3GPP("newName", "321", "555"));
    DomainNameList newNames = new DomainNameList(Lists.newArrayList("newDomainName"));
    List<RoamingConsortium> newList =  Lists.newArrayList(new RoamingConsortium("newName", "0xAAAAAA"));
    List<NAI> newNAIList = Lists.newArrayList(new NAI("EAP-SIM"));
   
    ServiceProvider newService = new ServiceProvider(new ID(id), "name", "newDescr", newNetworks, newNames, newList, newNAIList);

    repository.update(newService);
    ServiceProvider result = repository.findById(new ID(id)).get();

    assertThat(result, deepEquals(newService));
  }

  @Test
  public void updateProviderName() throws Exception {
    DomainNameList names = new DomainNameList(Lists.newArrayList("dName"));
    List<Network3GPP> networks = Lists.newArrayList(new Network3GPP("name", "123", "445"));
    List<RoamingConsortium> list =  Lists.newArrayList(new RoamingConsortium("name", "0xAAFFAA"));
    List<NAI> naiList = Lists.newArrayList(new NAI("EAP-SIM"));

    Object id = repository.create(new NewServiceProvider("name", "descr", networks, names, list, naiList));

    ServiceProvider newService = new ServiceProvider(new ID(id), "newName", "descr", networks, names, list, naiList);

    repository.update(newService);
    ServiceProvider result = repository.findById(new ID(id)).get();

    assertThat(result, deepEquals(newService));
  }

  @Test(expected = ServiceProviderException.class)
  public void updateProviderWithReservedName() throws Exception {
    NewServiceProvider newProvider1 = newServiceProvider().name("name1").build();
    NewServiceProvider newProvider2 = newServiceProvider().name("name2").build();

    Object id = repository.create(newProvider1);
    repository.create(newProvider2);

    ServiceProvider provider = newProvider().id(new ID(id)).name("name2").build();

    repository.update(provider);
  }

  @Test
  public void delete() throws Exception {
    NewServiceProvider provider = newServiceProvider().build();
    Object id = repository.create(provider);

    repository.delete(new ID(id));
    Optional<ServiceProvider> result = repository.findById(new ID(id));

    assertFalse(result.isPresent());
  }

  @Test
  public void deleteByUnknownId() throws Exception {
    NewServiceProvider provider = newServiceProvider().build();
    repository.create(provider);

    repository.delete(new ID("someID"));
    List<ServiceProvider> result = repository.findAll();

    assertThat(result.size(), is(1));
  }
}