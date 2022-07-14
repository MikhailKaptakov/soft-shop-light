package ru.soft1.soft_shop_light.model;

import lombok.Data;
import org.hibernate.Hibernate;
import java.util.Objects;

@Data
public class AbstractEntity implements HasId {

    public static final int START_SEQ = 1000;

    protected Long id;

    protected AbstractEntity() {
    }

    protected AbstractEntity(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        AbstractEntity that = (AbstractEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : Objects.hash(id);
    }


}
