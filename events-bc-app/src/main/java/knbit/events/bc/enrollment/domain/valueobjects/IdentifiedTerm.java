package knbit.events.bc.enrollment.domain.valueobjects;

import knbit.events.bc.choosingterm.domain.valuobjects.Term;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

/**
 * Created by novy on 03.10.15.
 */

@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor(staticName = "of")
public class IdentifiedTerm {

    @Getter
    private final TermId termId;
    @Delegate
    private final Term term;
}
