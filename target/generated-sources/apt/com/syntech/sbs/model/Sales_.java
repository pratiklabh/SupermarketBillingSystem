package com.syntech.sbs.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sales.class)
public abstract class Sales_ extends com.syntech.sbs.model.BaseIdEntity_ {

	public static volatile SingularAttribute<Sales, LocalDateTime> date;
	public static volatile SingularAttribute<Sales, Long> total;
	public static volatile SingularAttribute<Sales, String> paymentMode;
	public static volatile SingularAttribute<Sales, User> customer;
	public static volatile ListAttribute<Sales, SalesDetails> salesDetails;

	public static final String DATE = "date";
	public static final String TOTAL = "total";
	public static final String PAYMENT_MODE = "paymentMode";
	public static final String CUSTOMER = "customer";
	public static final String SALES_DETAILS = "salesDetails";

}

