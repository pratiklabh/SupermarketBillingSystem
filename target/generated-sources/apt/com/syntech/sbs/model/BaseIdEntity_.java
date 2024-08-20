package com.syntech.sbs.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseIdEntity.class)
public abstract class BaseIdEntity_ {

	public static volatile SingularAttribute<BaseIdEntity, LocalDateTime> createdAt;
	public static volatile SingularAttribute<BaseIdEntity, Long> id;
	public static volatile SingularAttribute<BaseIdEntity, LocalDateTime> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String ID = "id";
	public static final String UPDATED_AT = "updatedAt";

}

