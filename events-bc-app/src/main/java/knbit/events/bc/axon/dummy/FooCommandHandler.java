package knbit.events.bc.axon.dummy;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by novy on 05.05.15.
 */

@Component
public class FooCommandHandler {

    private final Repository<FooAggregate> repository;

    @Autowired
    public FooCommandHandler(@Qualifier("fooAggregateRepository") Repository<FooAggregate> repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void on(CreateFooCommand command) {
        repository.add(
                new FooAggregate(
                        command.getFooId(), command.getName()
                )
        );

    }
}
