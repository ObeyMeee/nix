package ua.com.andromeda.module2.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class BaseEntity {
    protected String id;

    protected BaseEntity() {
        id = UUID.randomUUID().toString();
    }
}
