package knbit.events.bc.eventproposal.domain.sagas;

import knbit.events.bc.event.domain.valueobjects.commands.CreateEventCommand;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by novy on 07.05.15.
 */
class WithoutIdentifierMatcher {

    public static Matcher<Collection<CreateEventCommand>> matchesExactlyOmittingId(Collection<CreateEventCommand> thoseCommands) {
        return new TypeSafeMatcher<Collection<CreateEventCommand>>() {
            @Override
            public boolean matchesSafely(Collection<CreateEventCommand> theseCommands) {
                boolean bothHaveSameSize = theseCommands.size() == thoseCommands.size();
                return bothHaveSameSize && containSameCommandsOmittingId(theseCommands, thoseCommands);
            }

            private boolean containSameCommandsOmittingId(Collection<CreateEventCommand> theseCommands,
                                                          Collection<CreateEventCommand> thoseCommands) {

                final Iterator<CreateEventCommand> thisIterator = theseCommands.iterator();
                final Iterator<CreateEventCommand> thatIterator = thoseCommands.iterator();

                while (thisIterator.hasNext() && thatIterator.hasNext()) {
                    final CreateEventCommand thisCommand = thisIterator.next();
                    final CreateEventCommand thatCommand = thatIterator.next();

                    if (!EqualsBuilder.reflectionEquals(thisCommand, thatCommand, "eventId")) {
                        return false;
                    }
                }

                return true;

            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

}
