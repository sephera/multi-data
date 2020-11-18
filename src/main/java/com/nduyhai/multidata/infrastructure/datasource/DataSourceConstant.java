package com.nduyhai.multidata.infrastructure.datasource;

public final class DataSourceConstant {

  private DataSourceConstant() {
  }

  public static final class Second {

    private Second() {
    }

    public static final String PREFIX = "second";
    public static final String ENTITY_MANAGER = PREFIX + "EntityManager";
    public static final String TRANSACTION_MANAGER = PREFIX + "TransactionManager";
    public static final String DATASOURCE = PREFIX + "DataSource";
    public static final String PREFIX_DATASOURCE = PREFIX + ".datasource";
    public static final String JPA_PROPERTIES = PREFIX + "JPAProperties";
    public static final String DATASOURCE_PROPERTIES = PREFIX + "DSProperties";
    public static final String PREFIX_PROPERTIES = PREFIX + ".jpa";
    public static final String REPO_PACKAGE = "com.nduyhai.multidata.infrastructure.jpa.second";
    public static final String ENTITY_PACKAGE = "com.nduyhai.multidata.infrastructure.jpa.second";
    public static final String UNIT = PREFIX + "Unit";
  }

  public static final class First {

    private First() {
    }

    public static final String PREFIX = "first";
    public static final String ENTITY_MANAGER = PREFIX + "EntityManager";
    public static final String TRANSACTION_MANAGER = PREFIX + "TransactionManager";
    public static final String DATASOURCE = PREFIX + "DataSource";
    public static final String PREFIX_DATASOURCE = PREFIX + ".datasource";
    public static final String JPA_PROPERTIES = PREFIX + "JPAProperties";
    public static final String DATASOURCE_PROPERTIES = PREFIX + "DSProperties";
    public static final String PREFIX_PROPERTIES = PREFIX + ".jpa";
    public static final String REPO_PACKAGE = "com.nduyhai.multidata.infrastructure.jpa.first";
    public static final String ENTITY_PACKAGE = "com.nduyhai.multidata.infrastructure.jpa.first";
    public static final String UNIT = PREFIX + "Unit";

  }
}


