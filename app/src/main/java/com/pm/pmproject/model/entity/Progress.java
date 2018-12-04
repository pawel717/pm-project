package com.pm.pmproject.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.Date;
import java.util.List;

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
    private List<Attribute> attributes;
}
