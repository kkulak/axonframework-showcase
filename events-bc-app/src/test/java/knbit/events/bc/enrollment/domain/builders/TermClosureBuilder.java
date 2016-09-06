package knbit.events.bc.enrollment.domain.builders;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import knbit.events.bc.enrollment.domain.valueobjects.TermClosure;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class TermClosureBuilder {
    TermId termId = TermId.of("term-id");
    Collection<Lecturer> lecturers = ImmutableList.of(Lecturer.of("John Doe", "john-doe"));
    ParticipantsLimit participantsLimit = ParticipantsLimit.of(10);

    public TermClosure build() {
        return TermClosure.of(termId, lecturers, participantsLimit);
    }

    public static TermClosure defaultTerm() {
        return TermClosureBuilder.instance().build();
    }

}
