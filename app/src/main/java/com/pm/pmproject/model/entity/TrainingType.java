package com.pm.pmproject.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "training_type",
        active = true,
        generateConstructors = true,
        generateGettersSetters = true)
public class TrainingType {
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "name")
    @NotNull
    private String name;
}
