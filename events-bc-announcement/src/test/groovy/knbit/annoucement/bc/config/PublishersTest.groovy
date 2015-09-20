package knbit.annoucement.bc.config

import spock.lang.Specification

/**
 * Created by novy on 12.04.15.
 */
class PublishersTest extends Specification {

    def "deserializing string-serialized values should work"() {

        given:
        def stringSerializedValues = Publishers.stringValues()

        when:
        Collection<Publishers> deserializedPublishers = Publishers.fromStringValues(stringSerializedValues)

        then:
        deserializedPublishers == Arrays.asList(Publishers.values())

    }
}
