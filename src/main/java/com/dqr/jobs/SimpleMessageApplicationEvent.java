package com.dqr.jobs;

import org.springframework.context.ApplicationEvent;

/**
 * Simple Message Application Event.
 * <p>
 * Created by dqromney on 3/2/17.
 */
public class SimpleMessageApplicationEvent extends ApplicationEvent {
    private String message;

    public SimpleMessageApplicationEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    /* (non-Javadoc)
     * @see java.util.EventObject#toString()
     */
    public String toString() {
        return "message=[" + message + "], " + super.toString();
    }
}
