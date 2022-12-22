package esperVesper;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import dataCreationPackage.TrafficJamEvent;
import testing.TestingController;

import java.util.Map;

public class AverageSpeedChangeListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents,
                       EPStatement statement, EPRuntime runtime) {
        if(newEvents.length<2)
            return;
        double oldSpeed= (double)((Map) newEvents[0].getUnderlying()).get("avg");
        double newSpeed= (double)((Map) newEvents[1].getUnderlying()).get("avg");


        if(oldSpeed/newSpeed>2){
            System.out.println("------TRAFFIC JAM DETECTED------");
            System.out.println("oldspeed: "+oldSpeed);
            System.out.println("newSpeed: "+newSpeed);
            TestingController.setExpectedTrafficJam(true);
            EsperProcessor.addTrafficJamEvent(new TrafficJamEvent());
            System.out.println("--------------------------------");
        }
    }
}