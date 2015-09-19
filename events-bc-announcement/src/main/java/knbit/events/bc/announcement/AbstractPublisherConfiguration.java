package knbit.events.bc.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by novy on 19.09.15.
 */
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractPublisherConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String name;

    private boolean isDefault;

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null
                || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractPublisherConfiguration that = (AbstractPublisherConfiguration) obj;

        return this.id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return this.id != null ? id.hashCode() : super.hashCode();
    }
}
