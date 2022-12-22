package esperVesper;


import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import testing.TestingController;

public class AverageSpeedOfSectionListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents,
                       EPStatement statement, EPRuntime runtime) {
        for (EventBean event:newEvents){
            double avg= (double) event.get("avg");
            TestingController.setExpectedSectionAverage(avg);
            System.out.println("-----Average Speed by section-----");
            System.out.println("Speed AVG(kmh): "+avg);
            System.out.println("----------------------------------");
        }
    }
}