package knbit.events.bc.interest.questionnaire.domain.aggregates;

/**
 * Created by novy on 26.05.15.
 */
class WithoutIdentifierMatcher {

/*    public static Matcher<Collection<GenericEventMessage<QuestionnaireCreatedEvent>>> matchesButQuestionId(Collection<QuestionnaireCreatedEvent> thoseEvents) {

        return new BaseMatcher<Collection<GenericEventMessage<QuestionnaireCreatedEvent>>>() {
            @Override
            public boolean matches(Object item) {
                @SuppressWarnings("unchecked")
                Collection<QuestionnaireCreatedEvent> theseEvents =
                        ((Collection<GenericEventMessage<QuestionnaireCreatedEvent>>) item)
                                .stream()
                                .map(GenericEventMessage::getPayload)
                                .collect(Collectors.toList());

                boolean bothHaveSameSize = theseEvents.size() == thoseEvents.size();
                return bothHaveSameSize && differsOnlyInQuestionIds(theseEvents, thoseEvents);
            }

            private boolean differsOnlyInQuestionIds(Collection<QuestionnaireCreatedEvent> theseEvents,
                                                     Collection<QuestionnaireCreatedEvent> thoseEvents) {

                final Iterator<QuestionnaireCreatedEvent> thisIterator = theseEvents.iterator();
                final Iterator<QuestionnaireCreatedEvent> thatIterator = thoseEvents.iterator();

                while (thisIterator.hasNext() && thatIterator.hasNext()) {
                    final QuestionnaireCreatedEvent thisEvent = thisIterator.next();
                    final QuestionnaireCreatedEvent thatEvent = thatIterator.next();

                    final boolean differInSomethingMoreThanQuestions = !EqualsBuilder.reflectionEquals(thisEvent, thatEvent, "questions");
                    final boolean questionsHaveDifferentSizes = thisEvent.questions().size() != thatEvent.questions().size();
                    final boolean questionsDifferMoreThanInIds = questionDifferMoreThanInIds(thisEvent.questions(), thatEvent.questions());

                    if (differInSomethingMoreThanQuestions || questionsHaveDifferentSizes || questionsDifferMoreThanInIds) {
                        return false;
                    }

                }

                return true;

            }

            private boolean questionDifferMoreThanInIds(List<IdentifiedQuestionData> theseQuestions, List<IdentifiedQuestionData> thoseQuestions) {
                final Iterator<IdentifiedQuestionData> thisIterator = theseQuestions.iterator();
                final Iterator<IdentifiedQuestionData> thatIterator = thoseQuestions.iterator();

                while (thisIterator.hasNext() && thatIterator.hasNext()) {
                    final IdentifiedQuestionData thisQuestionData = thisIterator.next();
                    final IdentifiedQuestionData thatQuestionData = thatIterator.next();

                    if (!EqualsBuilder.reflectionEquals(thisQuestionData, thatQuestionData, "questionId")) {
                        return true;
                    }
                }

                return false;

            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(thoseEvents);
            }
        };
    }*/

}
