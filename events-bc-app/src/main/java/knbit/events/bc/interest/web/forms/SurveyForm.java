package knbit.events.bc.interest.web.forms;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyForm {

    private EndOfSurveyingPolicyForm endOfSurveyingPolicy;
    private NotificationPolicyForm notificationPolicy;
    private QuestionnaireForm questionnaire;

}
