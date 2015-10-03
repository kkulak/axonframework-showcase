package knbit.events.bc.enrollment.domain;

import knbit.events.bc.enrollment.domain.aggregates.EventUnderEnrollment;
import knbit.events.bc.enrollment.domain.valueobjects.Lecturer;
import knbit.events.bc.enrollment.domain.valueobjects.ParticipantLimit;
import knbit.events.bc.enrollment.domain.valueobjects.commands.EventUnderEnrollmentCommands;
import knbit.events.bc.enrollment.domain.valueobjects.commands.TermModifyingCommands;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 02.10.15.
 */
@Component
public class EventUnderEnrollmentCommandHandler {

    private final Repository<EventUnderEnrollment> repository;

    @Autowired
    public EventUnderEnrollmentCommandHandler(
            @Qualifier("eventUnderEnrollmentRepository") Repository<EventUnderEnrollment> repository) {

        this.repository = repository;
    }

    @CommandHandler
    public void handle(EventUnderEnrollmentCommands.Create command) {
        final EventUnderEnrollment eventUnderEnrollment = new EventUnderEnrollment(
                command.eventId(), command.eventDetails(), command.terms()
        );

        repository.add(eventUnderEnrollment);
    }

    @CommandHandler
    public void handle(TermModifyingCommands.AssignLecturer command) {
        final EventUnderEnrollment eventUnderEnrollment = repository.load(command.eventId());

        eventUnderEnrollment.assignLecturer(
                command.termId(), Lecturer.of(command.firstName(), command.lastName())
        );
    }

    @CommandHandler
    public void handle(TermModifyingCommands.SetParticipantLimit command) {
        final EventUnderEnrollment eventUnderEnrollment = repository.load(command.eventId());

        eventUnderEnrollment.limitParticipants(
                command.termId(), ParticipantLimit.of(command.participantLimit())
        );
    }
}
