package knbit.events.bc.choosingterm.domain.valuobjects

import spock.lang.Specification

import java.time.Duration
import java.time.LocalDateTime

/**
 * Created by novy on 19.08.15.
 */
class OverlappingTest extends Specification {

    def "given another term with overlapping date and same location, should return true"() {
        when:
        def firstTermDuration = EventDuration.of(
                LocalDateTime.of(2015, 1, 1, 18, 30), Duration.ofMinutes(90)
        )
        def firstTerm = Term.of(
                firstTermDuration, Capacity.of(120), Location.of("3.21C")
        )

        def secondTermDuration = EventDuration.of(
                LocalDateTime.of(2015, 1, 1, 19, 0), Duration.ofMinutes(90)
        )
        def secondTerm = Term.of(
                secondTermDuration, Capacity.of(120), Location.of("3.21C")
        )

        then:
        firstTerm.overlaps(secondTerm)
    }

    def "given another term with overlapping date but different location, should return false"() {
        when:
        def firstTermDuration = EventDuration.of(
                LocalDateTime.of(2015, 1, 1, 18, 30), Duration.ofMinutes(90)
        )
        def firstTerm = Term.of(
                firstTermDuration, Capacity.of(120), Location.of("3.21C")
        )

        def secondTermDuration = EventDuration.of(
                LocalDateTime.of(2015, 1, 1, 19, 0), Duration.ofMinutes(90)
        )
        def secondTerm = Term.of(
                secondTermDuration, Capacity.of(120), Location.of("3.28C")
        )

        then:
        !firstTerm.overlaps(secondTerm)
    }

    def "given another term with same location but not overlapping date, should return false"() {
        when:
        def firstTermDuration = EventDuration.of(
                LocalDateTime.of(2015, 1, 1, 18, 30), Duration.ofMinutes(90)
        )
        def firstTerm = Term.of(
                firstTermDuration, Capacity.of(120), Location.of("3.21C")
        )

        def secondTermDuration = EventDuration.of(
                LocalDateTime.of(2015, 1, 1, 20, 0), Duration.ofMinutes(90)
        )
        def secondTerm = Term.of(
                secondTermDuration, Capacity.of(120), Location.of("3.21C")
        )

        then:
        !firstTerm.overlaps(secondTerm)
    }
}
