package com.syntech.sbs.model;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ extends com.syntech.sbs.model.BaseIdEntity_ {

	public static volatile SingularAttribute<Product, String> unit;
	public static volatile SingularAttribute<Product, String> code;
	public static volatile SingularAttribute<Product, BigInteger> rate;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, String> description;
	public static volatile SingularAttribute<Product, BigInteger> discount;
	public static volatile SingularAttribute<Product, String> type;
	public static volatile SingularAttribute<Product, Stock> stock;

	public static final String UNIT = "unit";
	public static final String CODE = "code";
	public static final String RATE = "rate";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String DISCOUNT = "discount";
	public static final String TYPE = "type";
	public static final String STOCK = "stock";

}

