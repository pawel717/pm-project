package com.pm.pmproject.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "Training",
        active = true,
        generateConstructors = true,
        generateGettersSetters = true)
public class Training {

    @Id(autoincrement = true)
    private Long Id;

    @Property(nameInDb = "duration")
    private Long duration;

    private Long trainingTypeId;

    @ToOne(joinProperty = "trainingTypeId")
    private TrainingType type;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 811827863)
private transient TrainingDao myDao;

@Generated(hash = 618422868)
public Training(Long Id, Long duration, Long trainingTypeId) {
    this.Id = Id;
    this.duration = duration;
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

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1249407740)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getTrainingDao() : null;
}
}
