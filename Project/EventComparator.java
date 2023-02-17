package cs2030.simulator;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
 
    @Override
    public int compare(Event e1, Event e2) {
        if (e1.getEventTime() == e2.getEventTime()) {
            return e1.getCustomerID() - e2.getCustomerID();
        }
        return Double.compare(e1.getEventTime(),e2.getEventTime());
    }
}

