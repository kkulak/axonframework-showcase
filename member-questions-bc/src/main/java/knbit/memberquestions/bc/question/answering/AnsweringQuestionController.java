package knbit.memberquestions.bc.question.answering;

import knbit.memberquestions.bc.question.answering.service.Email;
import knbit.memberquestions.bc.question.answering.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/events/members/questions/answers")
public class AnsweringQuestionController {
    private final MailService mailService;

    @Autowired
    public AnsweringQuestionController(MailService mailService) {
        this.mailService = mailService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void askQuestion(@RequestBody @Valid Answer answer) {
        mailService.send(
                Email.of(answer.getEmail(), answer.getSubject(), answer.getContent())
        );
    }

}
