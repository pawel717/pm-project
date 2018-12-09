package com.pm.pmproject.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.ToOne;

@Entity(nameInDb = "attribute_progress",
        active = true,
        generateConstructors = true,
        generateGettersSetters = true)
public class AttributeProgress {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "value")
    private String value;

    private Long attributeId;

    @ToOne(joinProperty = "attributeId")
    private Attribute attribute;

    @Property(nameInDb = "progress_id")
    @NotNull
    private Long progressId;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 972456446)
private transient AttributeProgressDao myDao;

@Generated(hash = 735162862)
private transient Long attribute__resolvedKey;

@Generated(hash = 2034524360)
public AttributeProgress(Long id, String value, Long attributeId,
        @NotNull Long progressId) {
    this.id = id;
    this.value = value;
    this.attributeId = attributeId;
    this.progressId = progressId;
}

@Generated(hash = 509989464)
public AttributeProgress() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getValue() {
    return this.value;
}

public void setValue(String value) {
    this.value = value;
}

public Long getAttributeId() {
    return this.attributeId;
}

public void setAttributeId(Long attributeId) {
    this.attributeId = attributeId;
}

public Long getProgressId() {
    return this.progressId;
}

public void setProgressId(Long progressId) {
    this.progressId = progressId;
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 128553479)
public void delete() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.delete(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 1942392019)
public void refresh() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.refresh(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 713229351)
public void update() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.update(this);
}

/** To-one relationship, resolved on first access. */
@Generated(hash = 1866189090)
public Attribute getAttribute() {
    Long __key = this.attributeId;
    if (attribute__resolvedKey == null
            || !attribute__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        AttributeDao targetDao = daoSession.getAttributeDao();
        Attribute attributeNew = targetDao.load(__key);
        synchronized (this) {
            attribute = attributeNew;
            attribute__resolvedKey = __key;
        }
    }
    return attribute;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 744064319)
public void setAttribute(Attribute attribute) {
    synchronized (this) {
        this.attribute = attribute;
        attributeId = attribute == null ? null : attribute.getId();
        attribute__resolvedKey = attributeId;
    }
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 380150800)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getAttributeProgressDao() : null;
}
}
