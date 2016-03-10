package com.clouway.anqp;

import com.clouway.anqp.Auth.Info;
import com.clouway.anqp.Auth.Type;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;

/**
 */
public class AuthEntry {
  private final Info info;
  private final Set<Type> types;
  private final Set<String> stringTypes;

  public AuthEntry(Info info, Type... types) {
    List<String> temp = Lists.newArrayList();

    for (Type type : types) {
      temp.add(type.name());
    }

    this.info = info;
    this.types = ImmutableSet.copyOf(types);
    this.stringTypes = ImmutableSet.copyOf(temp);
  }

  public Info getInfo() {
    return info;
  }

  public List<Type> getTypes() {
    return Lists.newArrayList(types);
  }

  public Boolean containsType(String type) {
    return stringTypes.contains(type);
  }
}
