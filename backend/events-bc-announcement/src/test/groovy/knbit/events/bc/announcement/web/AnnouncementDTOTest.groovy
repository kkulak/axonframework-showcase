package knbit.events.bc.announcement.web

import com.google.common.collect.ImmutableList
import knbit.events.bc.announcement.config.Publishers
import spock.lang.Specification

/**
 * Created by novy on 12.04.15.
 */
class AnnouncementDTOTest extends Specification {

    def "should throw an exception trying to set not allowed publisher"() {

        when:
        new AnnouncementDTO(
                ImmutableList.of("facebook", "some not allowed publisher", "twitter"),
                "title", "content"
        )

        then:
        thrown(IllegalArgumentException.class)
    }

    def "should set publishers otherwise"() {

        given:
        def objectUnderTest = new AnnouncementDTO(
                Publishers.stringValues(), "title", "content"
        )

        expect:
        objectUnderTest.getPublishers() == Publishers.stringValues()

    }
}
