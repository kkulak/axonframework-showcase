package knbit.events.bc.common.infrastructure.config.axon;

import org.axonframework.saga.GenericSagaFactory;
import org.axonframework.saga.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class SpringManagedSagaFactory extends GenericSagaFactory {
    private final AutowireCapableBeanFactory beanFactory;

    @Autowired
    public SpringManagedSagaFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public <T extends Saga> T createSaga(Class<T> sagaType) {
        final T saga = super.createSaga(sagaType);
        beanFactory.autowireBean(saga);
        return saga;
    }

}
