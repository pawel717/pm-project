package com.pm.pmproject.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

@Entity(nameInDb = "Training",
        active = true,
        generateConstructors = true,
        generateGettersSetters = true)
public class Training {

    @Id(autoincrement = true)
    private Long Id;

    private Long trainingTypeId;

    @ToOne(joinProperty = "trainingTypeId")
    private TrainingType type;
}
