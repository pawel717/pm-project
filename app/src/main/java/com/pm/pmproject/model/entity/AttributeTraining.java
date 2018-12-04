package com.pm.pmproject.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "attribute_training",
        active = true,
        generateConstructors = true,
        generateGettersSetters = true)
public class AttributeTraining {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "value")
    private String value;

    @Property(nameInDb = "attribute_id")
    @NotNull
    private Long attributeId;

    @Property(nameInDb = "training_id")
    @NotNull
    private Long trainingId;
}
