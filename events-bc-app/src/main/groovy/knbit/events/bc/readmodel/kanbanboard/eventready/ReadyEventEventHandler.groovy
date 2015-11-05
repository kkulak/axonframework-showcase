package knbit.events.bc.readmodel.kanbanboard.eventready

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents
import knbit.events.bc.readmodel.EventDetailsWrapper
import knbit.events.bc.readmodel.TermWrapper
import knbit.events.bc.readmodel.kanbanboard.enrollment.handlers.enrollment.ParticipantDetailsRepository
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class ReadyEventEventHandler {
    def DBCollection readyEventCollection
    def ParticipantDetailsRepository participantRepository

    @Autowired
    ReadyEventEventHandler(@Qualifier("readyevent") DBCollection readyEventCollection,
                           ParticipantDetailsRepository participantsRepository) {
        this.readyEventCollection = readyEventCollection
        this.participantRepository = participantRepository
    }

    @EventHandler
    def on(ReadyEvents.Created event) {
        def readyEventId = [readyEventId: event.readyEventId().value()]
        def correlationId = [corellationId: event.correlationId().value()]
        def eventDetails = EventDetailsWrapper.asMap(event.eventDetails().eventDetails())
        def term = termAndAttendeeDataFrom(event.eventDetails(), event.attendees())

        readyEventCollection.insert(readyEventId + correlationId + eventDetails + term)
    }

    private static def termAndAttendeeDataFrom(EventReadyDetails details, Collection<Attendee> attendees) {
        def term = TermWrapper.asMap(details)
        def participants = [participants: [participantsDetailsOf(attendees)]]

        [term: [ term + participants ]]
    }

    private static def participantsDetailsOf(Collection<Attendee> attendees) {
        attendees.collect { participantRepository.detailsFor(it.memberId()) }
    }

}
