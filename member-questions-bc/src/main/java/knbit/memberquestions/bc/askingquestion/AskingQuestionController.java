package knbit.memberquestions.bc.askingquestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by novy on 30.06.15.
 */

@RestController
@RequestMapping(value = "/events/members")
public class AskingQuestionController {

    private final MemberQuestionDispatcher dispatcher;

    @Autowired
    public AskingQuestionController(MemberQuestionDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void askQuestion(@RequestBody @Valid Question question) {
        dispatcher.dispatchMemberQuestion(question);
    }

}
