package com.pm.pmproject.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.util.Date;
import java.util.List;

@Entity(nameInDb = "Training",
        active = true,
        generateConstructors = true,
        generateGettersSetters = true)
public class Training {

    @Id(autoincrement = true)
    private Long Id;

    @Property(nameInDb = "duration")
    @NotNull
    private Long duration;

    @Property(nameInDb = "date")
    @NotNull
    private Date date;

    private Long trainingTypeId;

    @ToOne(joinProperty = "trainingTypeId")
    private TrainingType type;

    @ToMany(referencedJoinProperty = "trainingId")
    private List<AttributeTraining> attributes;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 811827863)
private transient TrainingDao myDao;

@Generated(hash = 167441525)
public Training(Long Id, @NotNull Long duration, @NotNull Date date,
        Long trainingTypeId) {
    this.Id = Id;
    this.duration = duration;
    this.date = date;
    this.trainingTypeId = trainingTypeId;
}

@Generated(hash = 1863741921)
public Training() {
}

public Long getId() {
    return this.Id;
}

public void setId(Long Id) {
    this.Id = Id;
}

public Long getTrainingTypeId() {
    return this.trainingTypeId;
}

public void setTrainingTypeId(Long trainingTypeId) {
    this.trainingTypeId = trainingTypeId;
}

@Generated(hash = 506996655)
private transient Long type__resolvedKey;

/** To-one relationship, resolved on first access. */
@Generated(hash = 1989106162)
public TrainingType getType() {
    Long __key = this.trainingTypeId;
    if (type__resolvedKey == null || !type__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        TrainingTypeDao targetDao = daoSession.getTrainingTypeDao();
        TrainingType typeNew = targetDao.load(__key);
        synchronized (this) {
            type = typeNew;
            type__resolvedKey = __key;
        }
    }
    return type;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1505912720)
public void setType(TrainingType type) {
    synchronized (this) {
        this.type = type;
        trainingTypeId = type == null ? null : type.getId();
        type__resolvedKey = trainingTypeId;
    }
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

public Long getDuration() {
    return this.duration;
}

public void setDuration(Long duration) {
    this.duration = duration;
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
@Generated(hash = 1896765297)
public List<AttributeTraining> getAttributes() {
    if (attributes == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        AttributeTrainingDao targetDao = daoSession.getAttributeTrainingDao();
        List<AttributeTraining> attributesNew = targetDao
                ._queryTraining_Attributes(Id);
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

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1249407740)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getTrainingDao() : null;
}
}
