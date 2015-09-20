package knbit.memberquestions.bc.question.asking;

import knbit.events.bc.common.config.AMQPConstants;
import knbit.events.bc.common.dispatcher.MessageDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by novy on 30.06.15.
 */

@RestController
@RequestMapping(value = "/events/members/questions")
public class AskingQuestionController {
    private final MessageDispatcher dispatcher;
    private static final String NOTIFICATION_TYPE = "MEMBER_ASKED_QUESTION";

    @Autowired
    public AskingQuestionController(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void askQuestion(@RequestBody @Valid Question question) {
        dispatcher.dispatch(question, AMQPConstants.NOTIFICATION_QUEUE, NOTIFICATION_TYPE);
    }

}
