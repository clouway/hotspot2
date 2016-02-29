package com.clouway.anqp.adapter.persistence;

import com.clouway.anqp.ID;
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

  @Test
  public void create() throws Exception {
    NewServiceProvider newProvider = new NewServiceProvider("name", "Description");

    Object id = repository.create(newProvider);
    ServiceProvider result = repository.findById(new ID(id)).get();

    ServiceProvider provider = new ServiceProvider(new ID(id), "name", "Description");

    assertThat(result, deepEquals(provider));
  }

  @Test(expected = ServiceProviderException.class)
  public void createProviderWithExistingName() throws Exception {
    NewServiceProvider newProvider1 = new NewServiceProvider("name", "Description");
    NewServiceProvider newProvider2 = new NewServiceProvider("name", "Description");

    repository.create(newProvider1);
    repository.create(newProvider2);
  }

  @Test
  public void findByUnknownId() throws Exception {
    Optional<ServiceProvider> result = repository.findById(new ID("ID"));

    assertFalse(result.isPresent());
  }

  @Test
  public void findAll() throws Exception {
    NewServiceProvider newProvider1 = new NewServiceProvider("service", "Description");
    NewServiceProvider newProvider2 = new NewServiceProvider("Roaming service", "Nice");

    Object id1 = repository.create(newProvider1);
    Object id2 = repository.create(newProvider2);

    List<ServiceProvider> result = repository.findAll();

    ServiceProvider provider1 = new ServiceProvider(new ID(id1), "service", "Description");
    ServiceProvider provider2 = new ServiceProvider(new ID(id2), "Roaming service", "Nice");

    List<ServiceProvider> providers = Lists.newArrayList(provider1, provider2);

    assertThat(result, deepEquals(providers));
  }

  @Test
  public void update() throws Exception {
    Object id = repository.create(new NewServiceProvider("name", "Description"));

    ServiceProvider newService = new ServiceProvider(new ID(id), "name", "New Description");

    repository.update(newService);
    ServiceProvider result = repository.findById(new ID(id)).get();

    assertThat(result, deepEquals(newService));
  }

  @Test
  public void updateProviderName() throws Exception {
    Object id = repository.create(new NewServiceProvider("name", "Description"));

    ServiceProvider newService = new ServiceProvider(new ID(id), "New Name", "New Description");

    repository.update(newService);
    ServiceProvider result = repository.findById(new ID(id)).get();

    assertThat(result, deepEquals(newService));
  }

  @Test(expected = ServiceProviderException.class)
  public void updateProviderWithReservedName() throws Exception {
    Object id1 = repository.create(new NewServiceProvider("name", "Description"));
    repository.create(new NewServiceProvider("Stamat", "Description"));

    ServiceProvider newService = new ServiceProvider(new ID(id1), "Stamat", "New Description");

    repository.update(newService);
  }

  @Test
  public void delete() throws Exception {
    Object id = repository.create(new NewServiceProvider("name1", "Description1"));

    repository.delete(new ID(id));
    Optional<ServiceProvider> result = repository.findById(new ID(id));

    assertFalse(result.isPresent());
  }

  @Test
  public void deleteByUnknownId() throws Exception {
    repository.create(new NewServiceProvider("name1", "Description1"));

    repository.delete(new ID("someID"));
    List<ServiceProvider> result = repository.findAll();

    assertThat(result.size(), is(1));
  }
}