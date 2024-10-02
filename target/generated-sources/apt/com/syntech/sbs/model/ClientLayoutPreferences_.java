package com.syntech.sbs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ClientLayoutPreferences.class)
public abstract class ClientLayoutPreferences_ extends com.syntech.sbs.model.BaseIdEntity_ {

	public static volatile SingularAttribute<ClientLayoutPreferences, String> clientName;
	public static volatile SingularAttribute<ClientLayoutPreferences, String> companyNameAlign;
	public static volatile SingularAttribute<ClientLayoutPreferences, String> addressAlign;
	public static volatile SingularAttribute<ClientLayoutPreferences, String> dateAlign;
	public static volatile SingularAttribute<ClientLayoutPreferences, String> grandTotalAlign;
	public static volatile SingularAttribute<ClientLayoutPreferences, String> customerNameAlign;
	public static volatile SingularAttribute<ClientLayoutPreferences, String> salesInvoiceAlign;
	public static volatile SingularAttribute<ClientLayoutPreferences, String> tableAlign;

	public static final String CLIENT_NAME = "clientName";
	public static final String COMPANY_NAME_ALIGN = "companyNameAlign";
	public static final String ADDRESS_ALIGN = "addressAlign";
	public static final String DATE_ALIGN = "dateAlign";
	public static final String GRAND_TOTAL_ALIGN = "grandTotalAlign";
	public static final String CUSTOMER_NAME_ALIGN = "customerNameAlign";
	public static final String SALES_INVOICE_ALIGN = "salesInvoiceAlign";
	public static final String TABLE_ALIGN = "tableAlign";

}

