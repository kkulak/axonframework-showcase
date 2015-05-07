package knbit.events.bc.eventproposal.domain.sagas;

import knbit.events.bc.event.domain.valueobjects.commands.CreateEventCommand;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by novy on 07.05.15.
 */
class WithoutIdentifierMatcher {

    public static Matcher<Collection<GenericCommandMessage<CreateEventCommand>>> matchesExactlyOmittingId(Collection<CreateEventCommand> thoseCommands) {
        return new BaseMatcher<Collection<GenericCommandMessage<CreateEventCommand>>>() {
            @Override
            public boolean matches(Object item) {
                @SuppressWarnings("unchecked")
                Collection<GenericCommandMessage<CreateEventCommand>> theseCommands =
                        (Collection<GenericCommandMessage<CreateEventCommand>>) item;

                boolean bothHaveSameSize = theseCommands.size() == thoseCommands.size();
                return bothHaveSameSize && containSameCommandsOmittingId(theseCommands, thoseCommands);
            }

            private boolean containSameCommandsOmittingId(Collection<GenericCommandMessage<CreateEventCommand>> theseCommands,
                                                          Collection<CreateEventCommand> thoseCommands) {

                final Iterator<GenericCommandMessage<CreateEventCommand>> thisIterator = theseCommands.iterator();
                final Iterator<CreateEventCommand> thatIterator = thoseCommands.iterator();

                while (thisIterator.hasNext() && thatIterator.hasNext()) {
                    final CreateEventCommand thisCommand = thisIterator.next().getPayload();
                    final CreateEventCommand thatCommand = thatIterator.next();

                    if (!EqualsBuilder.reflectionEquals(thisCommand, thatCommand, "eventId")) {
                        return false;
                    }
                }

                return true;

            }

            @Override
            public void describeTo(Description description) {
            // todo: implement
            }
        };
    }

}
