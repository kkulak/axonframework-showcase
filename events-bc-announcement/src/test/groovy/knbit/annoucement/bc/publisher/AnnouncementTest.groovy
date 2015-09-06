package knbit.annoucement.bc.publisher

import knbit.annoucement.bc.AnnouncementBuilder
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

    def "should return empty optional when asked about image name and there is no url present"() {

        given:
        def objectUnderTest = AnnouncementBuilder
                .newAnnouncement()
                .imageUrl("")
                .build()

        expect:
        !objectUnderTest.imageName().isPresent()

    }

    def "if url contains an extension, it should take a prefix and append this extension"() {

        given:
        def objectUnderTest = AnnouncementBuilder
                .newAnnouncement()
                .imageUrl("http://valid.url.com/file.jpg")
                .build()

        when:
        def expectedImageName = Announcement.IMAGE_NAME_PREFIX + ".jpg"

        then:
        objectUnderTest.imageName().get() == expectedImageName
    }

    def "it should compose default prefix and suffix given non image extension"() {

        given:
        def objectUnderTest = AnnouncementBuilder
                .newAnnouncement()
                .imageUrl("http://valid.url.com/file.php")
                .build()

        expect:
        objectUnderTest.imageName().get() ==
                Announcement.IMAGE_NAME_PREFIX + "." + Announcement.IMAGE_NAME_SUFFIX

    }

    def "it should compose default prefix and suffix given non image extension with random string afterwards"() {

        given:
        def objectUnderTest = AnnouncementBuilder
                .newAnnouncement()
                .imageUrl("http://valid.url.com/file.php?param=value")
                .build()

        expect:
        objectUnderTest.imageName().get() ==
                Announcement.IMAGE_NAME_PREFIX + "." + Announcement.IMAGE_NAME_SUFFIX

    }

    def "it should compose default prefix and suffix given no extension"() {

        given:
        def objectUnderTest = AnnouncementBuilder
                .newAnnouncement()
                .imageUrl("http://valid.url.com/file")
                .build()

        expect:
        objectUnderTest.imageName().get() ==
                Announcement.IMAGE_NAME_PREFIX + "." + Announcement.IMAGE_NAME_SUFFIX

    }
}
