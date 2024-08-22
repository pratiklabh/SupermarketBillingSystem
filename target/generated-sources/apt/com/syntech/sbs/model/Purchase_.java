package com.syntech.sbs.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Purchase.class)
public abstract class Purchase_ extends com.syntech.sbs.model.BaseIdEntity_ {

	public static volatile SingularAttribute<Purchase, LocalDateTime> date;
	public static volatile SingularAttribute<Purchase, Long> total;
	public static volatile SingularAttribute<Purchase, Supplier> supplier;
	public static volatile SingularAttribute<Purchase, Long> discount;
	public static volatile ListAttribute<Purchase, PurchaseDetails> purchaseDetails;

	public static final String DATE = "date";
	public static final String TOTAL = "total";
	public static final String SUPPLIER = "supplier";
	public static final String DISCOUNT = "discount";
	public static final String PURCHASE_DETAILS = "purchaseDetails";

}

