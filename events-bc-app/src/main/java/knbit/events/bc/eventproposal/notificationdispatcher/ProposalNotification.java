package knbit.events.bc.eventproposal.notificationdispatcher;

import lombok.*;

/**
 * Created by novy on 09.05.15.
 */

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProposalNotification {

    private String eventProposalId;
    private String name;
    private String description;
}
