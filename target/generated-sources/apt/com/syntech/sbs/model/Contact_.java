package com.syntech.sbs.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contact.class)
public abstract class Contact_ extends com.syntech.sbs.model.BaseIdEntity_ {

	public static volatile SingularAttribute<Contact, String> subject;
	public static volatile SingularAttribute<Contact, String> name;
	public static volatile SingularAttribute<Contact, String> message;
	public static volatile SingularAttribute<Contact, String> email;

	public static final String SUBJECT = "subject";
	public static final String NAME = "name";
	public static final String MESSAGE = "message";
	public static final String EMAIL = "email";

}

