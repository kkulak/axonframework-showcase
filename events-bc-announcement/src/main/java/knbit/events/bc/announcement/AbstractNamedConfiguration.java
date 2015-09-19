package knbit.events.bc.announcement;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by novy on 19.09.15.
 */
@MappedSuperclass
@Getter
public abstract class AbstractNamedConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null
                || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractNamedConfiguration that = (AbstractNamedConfiguration) obj;

        return this.id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return this.id != null ? id.hashCode() : super.hashCode();
    }
}
