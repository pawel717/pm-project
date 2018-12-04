package com.pm.pmproject.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "progress",
        active = true,
        generateConstructors = true,
        generateGettersSetters = true)
public class Progress {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "date")
    @NotNull
    private Date date;

    @ToMany
    @JoinEntity(
            entity = AttributeProgress.class,
            sourceProperty = "progressId",
            targetProperty = "attributeId"
    )
    private List<AttributeProgress> attributes;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1634104625)
private transient ProgressDao myDao;

@Generated(hash = 694441117)
public Progress(Long id, @NotNull Date date) {
    this.id = id;
    this.date = date;
}

@Generated(hash = 2086691479)
public Progress() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public Date getDate() {
    return this.date;
}

public void setDate(Date date) {
    this.date = date;
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 479348143)
public List<AttributeProgress> getAttributes() {
    if (attributes == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        AttributeProgressDao targetDao = daoSession.getAttributeProgressDao();
        List<AttributeProgress> attributesNew = targetDao._queryProgress_Attributes(id);
        synchronized (this) {
            if (attributes == null) {
                attributes = attributesNew;
            }
        }
    }
    return attributes;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 1697487056)
public synchronized void resetAttributes() {
    attributes = null;
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

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1075568466)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getProgressDao() : null;
}
}
