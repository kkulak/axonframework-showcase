package knbit.events.bc.enrollment.domain.builders;

import com.google.common.collect.ImmutableList;
import knbit.events.bc.choosingterm.domain.builders.TermBuilder;
import knbit.events.bc.choosingterm.domain.valuobjects.EnrollmentIdentifiedTerm;
import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import knbit.events.bc.choosingterm.domain.valuobjects.TermId;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;

@Accessors(fluent = true)
@Setter
@NoArgsConstructor(staticName = "instance")
public class EnrollmentIdentifiedTermBuilder {
    private TermId termId = TermId.of("term-id");
    private Term term = TermBuilder.defaultTerm();
    private Collection<Lecturer> lecturers = ImmutableList.of(Lecturer.of("John Doe", "john-doe"));
    private ParticipantsLimit participantsLimit = ParticipantsLimit.of(10);

    public EnrollmentIdentifiedTerm build() {
        return EnrollmentIdentifiedTerm.of(termId, term, lecturers, participantsLimit);
    }

    public static EnrollmentIdentifiedTerm defaultTerm() {
        return EnrollmentIdentifiedTermBuilder.instance().build();
    }

}
