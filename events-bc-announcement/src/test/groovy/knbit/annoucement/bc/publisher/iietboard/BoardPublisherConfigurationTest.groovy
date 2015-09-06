package knbit.annoucement.bc.publisher.iietboard

import spock.lang.Specification

/**
 * Created by novy on 05.04.15.
 */
class BoardPublisherConfigurationTest extends Specification {

    def "should construct proper post url given board url with trailing slash"() {
        given:
        def objectUnderTest = BoardPublisherConfigurationBuilder
                .newBoardPublisherConfiguration()
                .boardUrl("https://forum.iiet.pl/")
                .boardId("57")
                .build();

        expect:
        objectUnderTest.postOnBoardUrl() == "https://forum.iiet.pl/posting.php?mode=post&f=57"

    }

    def "should construct proper post url given board url without trailing slash"() {
        given:
        def objectUnderTest = BoardPublisherConfigurationBuilder
                .newBoardPublisherConfiguration()
                .boardUrl("https://forum.iiet.pl")
                .boardId("57")
                .build();

        expect:
        objectUnderTest.postOnBoardUrl() == "https://forum.iiet.pl/posting.php?mode=post&f=57"

    }
}
