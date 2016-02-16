package com.clouway.anqp.util;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;

/**
 */
public abstract class EqualityTest<T> {

  private Class<T> targetClazz;

  @SuppressWarnings("unchecked")
  public EqualityTest() {
    this.targetClazz = (Class<T>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];
  }

  @Test
  public void checkEquality() {
    EqualsVerifier.forClass(targetClazz).allFieldsShouldBeUsed().verify();
  }

}
