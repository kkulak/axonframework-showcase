package knbit.events.bc.choosingterm.web;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by novy on 20.08.15.
 */

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
class TermsDTO {

    @NotNull
    private Collection<TermDTO> terms = Lists.newLinkedList();
    @NotNull
    private Collection<TermProposalDTO> termProposals = Lists.newLinkedList();

    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    static class TermDTO {

        @NotNull
        private DateTime date;
        @NotNull
        private Integer duration;
        @NotNull
        private Integer capacity;
        @NotBlank
        private String location;

    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    static class TermProposalDTO {

        @NotNull
        private DateTime date;
        @NotNull
        private Integer duration;
        @NotNull
        private Integer capacity;
    }
}
