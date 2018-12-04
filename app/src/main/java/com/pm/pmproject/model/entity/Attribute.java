package com.pm.pmproject.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "attribute",
        generateConstructors = true,
        generateGettersSetters = true)
public class Attribute {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "type")
    @NotNull
    private String type;

@Generated(hash = 225728241)
public Attribute(Long id, @NotNull String type) {
    this.id = id;
    this.type = type;
}

@Generated(hash = 959577406)
public Attribute() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getType() {
    return this.type;
}

public void setType(String type) {
    this.type = type;
}
}
