package com.syntech.sbs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PurchaseDetails.class)
public abstract class PurchaseDetails_ extends com.syntech.sbs.model.BaseIdEntity_ {

	public static volatile SingularAttribute<PurchaseDetails, String> unit;
	public static volatile SingularAttribute<PurchaseDetails, Integer> quantity;
	public static volatile SingularAttribute<PurchaseDetails, Double> rate;
	public static volatile SingularAttribute<PurchaseDetails, Purchase> purchase;
	public static volatile SingularAttribute<PurchaseDetails, Long> discount;
	public static volatile SingularAttribute<PurchaseDetails, String> productName;

	public static final String UNIT = "unit";
	public static final String QUANTITY = "quantity";
	public static final String RATE = "rate";
	public static final String PURCHASE = "purchase";
	public static final String DISCOUNT = "discount";
	public static final String PRODUCT_NAME = "productName";

}

