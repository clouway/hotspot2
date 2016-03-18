package com.clouway.anqp.adapter.persistence;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.rules.ExternalResource;

public final class PersistentDatastoreRule extends ExternalResource {
  private MongodExecutable mongodExecutable;
  private MongodProcess mongodProcess;
  private MongoDatabase db;

  private final String localhost = "localhost";
  private final Integer port = 12345;

  @Override
  protected void before() throws Throwable {
    MongodStarter runtime = MongodStarter.getDefaultInstance();

    mongodExecutable = runtime.prepare(new MongodConfigBuilder()
            .version(Version.Main.PRODUCTION)
            .net(new Net(port, Network.localhostIsIPv6()))
            .build());

    mongodProcess = mongodExecutable.start();

    ServerAddress address = new ServerAddress(localhost, port);

    MongoClient mongo = new MongoClient(address, MongoClientOptions.builder().connectionsPerHost(2).build());
    db = mongo.getDatabase("anqp_test");
  }

  @Override
  protected void after() {
    mongodProcess.stop();
    mongodExecutable.stop();
  }

  public MongoDatabase db() {
    return db;
  }

  public String getConnectionURL() {
    return localhost + ":" + port;
  }

}