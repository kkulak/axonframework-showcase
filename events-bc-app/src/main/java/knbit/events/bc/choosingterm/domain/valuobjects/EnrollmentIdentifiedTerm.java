package knbit.events.bc.choosingterm.domain.valuobjects;

import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

import java.util.Collection;

/**
 * Created by novy on 03.10.15.
 */

@Accessors(fluent = true)
@Value(staticConstructor = "of")
public class EnrollmentIdentifiedTerm {

    TermId termId;
    @Delegate Term term;
    Collection<Lecturer> lecturers;
    ParticipantsLimit participantsLimit;

}
