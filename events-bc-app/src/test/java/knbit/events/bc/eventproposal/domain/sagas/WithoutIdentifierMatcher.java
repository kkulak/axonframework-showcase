package knbit.events.bc.eventproposal.domain.sagas;

import knbit.events.bc.backlogevent.domain.valueobjects.commands.CreateBacklogEventCommand;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Created by novy on 07.05.15.
 */
class WithoutIdentifierMatcher {

    public static Matcher<Collection<GenericCommandMessage<CreateBacklogEventCommand>>> matchesExactlyOmittingId(Collection<CreateBacklogEventCommand> thoseCommands) {
        return new BaseMatcher<Collection<GenericCommandMessage<CreateBacklogEventCommand>>>() {
            @Override
            public boolean matches(Object item) {
                @SuppressWarnings("unchecked")
                Collection<CreateBacklogEventCommand> theseCommands =
                        ((Collection<GenericCommandMessage<CreateBacklogEventCommand>>) item)
                                .stream()
                                .map(GenericCommandMessage::getPayload)
                                .collect(Collectors.toList());

                boolean bothHaveSameSize = theseCommands.size() == thoseCommands.size();
                return bothHaveSameSize && containSameCommandsOmittingId(theseCommands, thoseCommands);
            }

            private boolean containSameCommandsOmittingId(Collection<CreateBacklogEventCommand> theseCommands,
                                                          Collection<CreateBacklogEventCommand> thoseCommands) {

                final Iterator<CreateBacklogEventCommand> thisIterator = theseCommands.iterator();
                final Iterator<CreateBacklogEventCommand> thatIterator = thoseCommands.iterator();

                while (thisIterator.hasNext() && thatIterator.hasNext()) {
                    final CreateBacklogEventCommand thisCommand = thisIterator.next();
                    final CreateBacklogEventCommand thatCommand = thatIterator.next();

                    if (!EqualsBuilder.reflectionEquals(thisCommand, thatCommand, "eventId")) {
                        return false;
                    }
                }

                return true;

            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(thoseCommands);
            }
        };
    }

}
