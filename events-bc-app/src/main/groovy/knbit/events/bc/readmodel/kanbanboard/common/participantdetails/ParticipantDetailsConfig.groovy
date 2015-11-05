package knbit.events.bc.readmodel.kanbanboard.common.participantdetails

import groovy.util.logging.Slf4j
import groovyx.net.http.RESTClient
import knbit.events.bc.common.Profiles
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

/**
 * Created by novy on 05.11.15.
 */

@Slf4j
@Configuration
class ParticipantDetailsConfig {

    @Value("\${members.endpoint}")
    def String membersEndpoint

    @Bean
    @Profile(Profiles.DEV)
    public ParticipantDetailsRepository mockParticipantDetailsRepository() {
        log.debug("creating mock members-bc client")
        return new MockParticipantDetailsRepository()
    }

    @Bean
    @Profile(Profiles.PROD)
    public RESTClient restClient() {
        return new RESTClient(membersEndpoint)
    }

    @Bean
    @Profile(Profiles.PROD)
    public ParticipantDetailsRepository membersBCParticipantDetailsRepository(RESTClient client) {
        log.debug("creating members-bc client with url ${membersEndpoint}")
        return new MembersBCParticipantDetailsRepository(client)
    }
}
