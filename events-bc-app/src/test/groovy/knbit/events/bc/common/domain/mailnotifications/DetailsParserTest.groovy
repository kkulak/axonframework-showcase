package knbit.events.bc.common.domain.mailnotifications

import knbit.events.bc.choosingterm.domain.valuobjects.EventDuration
import knbit.events.bc.choosingterm.domain.valuobjects.Location
import knbit.events.bc.common.domain.enums.EventType
import knbit.events.bc.common.domain.valueobjects.*
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantsLimit
import knbit.events.bc.eventready.domain.valueobjects.EventReadyDetails
import org.joda.time.DateTime
import org.joda.time.Duration
import spock.lang.Specification

/**
 * Created by novy on 13.12.15.
 */
class DetailsParserTest extends Specification {

    def "should not include section nor image url if not present"() {
        when:
        def details = EventDetails.of(
                Name.of("name"),
                Description.of("desc"),
                EventType.LECTURE,
                null,
                null
        )

        then:
        DetailsParser.parse(details) == [
                eventName       : "name",
                eventDescription: "desc",
                eventType       : "LECTURE"
        ]
    }

    def "otherwise should include both section and url"() {
        when:
        def details = EventDetails.of(
                Name.of("name"),
                Description.of("desc"),
                EventType.LECTURE,
                URL.of("http://example.com/image.png"),
                Section.of("dummyId", "sectionName")
        )

        then:
        DetailsParser.parse(details) == [
                eventName       : "name",
                eventDescription: "desc",
                eventType       : "LECTURE",
                imageUrl        : "http://example.com/image.png",
                section         : "sectionName"
        ]
    }

    def "given ready event details, should also include start time, location and lecturers"() {
        when:
        def eventDetails = EventDetails.of(
                Name.of("name"),
                Description.of("desc"),
                EventType.LECTURE,
                null,
                null
        )
        def start = DateTime.now()
        def details = EventReadyDetails.of(
                eventDetails,
                EventDuration.of(start, Duration.standardHours(1)),
                ParticipantsLimit.of(666),
                Location.of("3.27A"),
                [Lecturer.of("Kamil Kulak", "id1"), Lecturer.of("Slawomir Nowak", "id2")]
        )

        then:
        DetailsParser.parse(details) == [
                eventName       : "name",
                eventDescription: "desc",
                eventType       : "LECTURE",
                start           : start.toString(),
                location        : "3.27A",
                lecturers       : "Kamil Kulak, Slawomir Nowak"
        ]

    }
}
