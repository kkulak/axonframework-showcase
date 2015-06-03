package knbit.events.bc.eventproposal.web.forms;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Created by novy on 07.05.15.
 */

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProposalStateDto {

    public enum ProposalState {
        ACCEPTED, REJECTED
    }

    @NotNull
    private ProposalState state;
}
