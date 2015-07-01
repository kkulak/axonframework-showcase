package knbit.memberquestions.bc.question.asking;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 30.06.15.
 */

@Component
public class MemberQuestionDispatcher {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MemberQuestionDispatcher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void dispatchMemberQuestion(Question question) {
        rabbitTemplate.convertAndSend(question);
    }
}
