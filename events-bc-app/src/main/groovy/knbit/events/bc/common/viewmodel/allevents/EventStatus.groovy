package knbit.events.bc.common.viewmodel.allevents

/**
 * Created by novy on 29.06.15.
 */
enum EventStatus {

    UNDER_SURVEYING('Under Surveying'),

    def String value

    EventStatus(value) {
        this.value = value
    }

}
