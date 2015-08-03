package com.clouway.anqp;

/**
 * AnqpPlatformBootstrap is a bootstrap class which is representing the entry point of the ANQP platform.
 */
public class AnqpPlatformBootstrap {

  public static void main(String[] args) {
    System.out.println("ANPQ Platform was started successfully.");

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.out.println("ANQP Platform was terminated successfully.");
      }
    });

    while (true) {
      try {
        Thread.currentThread().sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
