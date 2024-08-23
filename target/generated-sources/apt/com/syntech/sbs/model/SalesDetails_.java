package com.syntech.sbs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SalesDetails.class)
public abstract class SalesDetails_ extends com.syntech.sbs.model.BaseIdEntity_ {

	public static volatile SingularAttribute<SalesDetails, Product> product;
	public static volatile SingularAttribute<SalesDetails, String> unit;
	public static volatile SingularAttribute<SalesDetails, Integer> quantity;
	public static volatile SingularAttribute<SalesDetails, Double> rate;
	public static volatile SingularAttribute<SalesDetails, Long> discount;
	public static volatile SingularAttribute<SalesDetails, Sales> sales;

	public static final String PRODUCT = "product";
	public static final String UNIT = "unit";
	public static final String QUANTITY = "quantity";
	public static final String RATE = "rate";
	public static final String DISCOUNT = "discount";
	public static final String SALES = "sales";

}

