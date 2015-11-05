package knbit.events.bc.readmodel.kanbanboard.eventready

import com.mongodb.DBCollection
import knbit.events.bc.common.domain.valueobjects.Attendee
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails
import knbit.events.bc.eventready.domain.valueobjects.ReadyEvents
import knbit.events.bc.readmodel.EventDetailsWrapper
import knbit.events.bc.readmodel.TermWrapper
import knbit.events.bc.readmodel.kanbanboard.common.participantdetails.ParticipantDetailsRepository
import org.axonframework.eventhandling.annotation.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class ReadyEventEventHandler {
    def DBCollection readyEventCollection
    def ParticipantDetailsRepository participantsRepository

    @Autowired
    ReadyEventEventHandler(@Qualifier("readyevent") DBCollection readyEventCollection,
                           ParticipantDetailsRepository participantsRepository) {
        this.readyEventCollection = readyEventCollection
        this.participantsRepository = participantsRepository
    }

    @EventHandler
    def on(ReadyEvents.Created event) {
        def readyEventId = [readyEventId: event.readyEventId().value()]
        def correlationId = [correlationId: event.correlationId().value()]
        def eventDetails = EventDetailsWrapper.asMap(event.eventDetails().eventDetails())
        def term = termAndAttendeeDataFrom(event.eventDetails(), event.attendees())

        readyEventCollection.insert(readyEventId + correlationId + eventDetails + term)
    }

    private def termAndAttendeeDataFrom(EventReadyDetails details, Collection<Attendee> attendees) {
        def term = TermWrapper.asMap(details)
        def participants = [participants: participantsDetailsOf(attendees)]

        [term: term + participants]
    }

    private def participantsDetailsOf(Collection<Attendee> attendees) {
        attendees.collect { participantsRepository.detailsFor(it.memberId()) }
    }

}
