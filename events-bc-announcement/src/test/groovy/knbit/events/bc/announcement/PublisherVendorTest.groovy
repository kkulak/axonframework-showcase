package knbit.events.bc.announcement

import spock.lang.Specification

/**
 * Created by novy on 12.04.15.
 */
class PublisherVendorTest extends Specification {

    def "deserializing string-serialized values should work"() {

        given:
        def stringSerializedValues = PublisherVendor.stringValues()

        when:
        Collection<PublisherVendor> deserializedPublishers = PublisherVendor.fromStringValues(stringSerializedValues)

        then:
        deserializedPublishers == Arrays.asList(PublisherVendor.values())

    }
}
