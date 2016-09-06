package knbit.events.bc.interest.domain.enums;

import knbit.events.bc.common.domain.exceptions.DomainException;
import knbit.events.bc.common.domain.valueobjects.EventId;
import knbit.events.bc.interest.domain.exceptions.*;

/**
 * Created by novy on 30.05.15.
 */
public enum InterestAwareEventState {


    CREATED {
        @Override
        public DomainException correspondingIncorrectStateException(EventId eventId) {
            return new SurveyingInterestNotYetStartedException(eventId);
        }
    },

    IN_PROGRESS {
        @Override
        public DomainException correspondingIncorrectStateException(EventId eventId) {
            return new SurveyingInterestAlreadyInProgressException(eventId);
        }
    },

    ENDED {
        @Override
        public DomainException correspondingIncorrectStateException(EventId eventId) {
            return new SurveyingInterestAlreadyEndedException(eventId);
        }
    },

    TRANSITED {
        @Override
        public DomainException correspondingIncorrectStateException(EventId eventId) {
            return new InterestAwareEventAlreadyTransitedException(eventId);
        }
    },

    CANCELLED {
        @Override
        public DomainException correspondingIncorrectStateException(EventId eventId) {
            return new InterestAwareEventAlreadyCancelledException(eventId);
        }
    };

    public abstract DomainException correspondingIncorrectStateException(EventId eventId);
}
