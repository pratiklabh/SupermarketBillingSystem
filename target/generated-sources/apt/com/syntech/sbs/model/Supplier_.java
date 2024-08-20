package com.syntech.sbs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Supplier.class)
public abstract class Supplier_ extends com.syntech.sbs.model.BaseIdEntity_ {

	public static volatile SingularAttribute<Supplier, String> address;
	public static volatile SingularAttribute<Supplier, String> phone;
	public static volatile SingularAttribute<Supplier, String> name;

	public static final String ADDRESS = "address";
	public static final String PHONE = "phone";
	public static final String NAME = "name";

}

