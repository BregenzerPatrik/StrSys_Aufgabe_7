package esperVesper;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;

import java.util.HashMap;
import java.util.Map;

public class TrafficJamEventListener implements UpdateListener {

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents,
                       EPStatement statement, EPRuntime runtime) {
        System.out.println("TRAFFIC JAM DETECTED");
    }

}
