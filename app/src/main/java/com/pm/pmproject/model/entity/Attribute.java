package com.pm.pmproject.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "attribute",
        generateConstructors = true,
        generateGettersSetters = true)
class Attribute {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "type")
    @NotNull
    private String type;
}
