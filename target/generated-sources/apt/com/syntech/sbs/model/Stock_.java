package com.syntech.sbs.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Stock.class)
public abstract class Stock_ extends com.syntech.sbs.model.BaseIdEntity_ {

	public static volatile SingularAttribute<Stock, LocalDateTime> date;
	public static volatile SingularAttribute<Stock, Product> product;
	public static volatile SingularAttribute<Stock, Integer> quantity;
	public static volatile SingularAttribute<Stock, BigInteger> rate;

	public static final String DATE = "date";
	public static final String PRODUCT = "product";
	public static final String QUANTITY = "quantity";
	public static final String RATE = "rate";

}

