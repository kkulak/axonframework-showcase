package knbit.events.bc.announcement

import knbit.events.bc.AnnouncementBuilder
import spock.lang.Specification

/**
 * Created by novy on 24.04.15.
 */
class AnnouncementTest extends Specification {

    def "should not be able to create an announcement with content up to 140 characters length"() {

        given:
        def longestAcceptableContent = '-' * 140

        when:
        def objectUnderTest = AnnouncementBuilder
                .newAnnouncement()
                .content(longestAcceptableContent)
                .build();

        then:
        objectUnderTest.content() == longestAcceptableContent
    }

    def "should not be able to create an announcement with content length exceeding 140 characters"() {

        given:
        def tooLongContent = '-' * 141;

        when:
        AnnouncementBuilder
                .newAnnouncement()
                .content(tooLongContent)
                .build();

        then:
        thrown(IllegalArgumentException.class)
    }

    def "should be able to create an announcement with empty url"() {

        when:
        def objectUnderTest = AnnouncementBuilder
                .newAnnouncement()
                .imageUrl("")
                .build();

        then:
        !objectUnderTest.imageUrl().isPresent()

    }

    def "should be able to create an announcement with valid url"() {

        when:
        def objectUnderTest = AnnouncementBuilder
                .newAnnouncement()
                .imageUrl("http://valid.url.com")
                .build();

        then:
        objectUnderTest.imageUrl().isPresent()
    }

    def "should not be able to create an announcement given invalid url"() {

        when:
        AnnouncementBuilder
                .newAnnouncement()
                .imageUrl("htp//invalid")
                .build();

        then:
        thrown(IllegalArgumentException.class)
    }
}
