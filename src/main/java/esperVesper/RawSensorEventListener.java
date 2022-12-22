package esperVesper;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import dataCreationPackage.FilteredKMHSensorEvent;
import dataCreationPackage.RawSensorEvent;

public class RawSensorEventListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents,
                       EPStatement statement, EPRuntime runtime) {
        for (EventBean event:newEvents){
            RawSensorEvent rawSensorEvent= (RawSensorEvent) event.getUnderlying();
            FilteredKMHSensorEvent localEvent=  new FilteredKMHSensorEvent(  rawSensorEvent.getId(),
                                                                        rawSensorEvent.getSpeed()*3.6);
            EsperProcessor.addFilteredSensorEvent(localEvent);
            System.out.println("raw Event "+rawSensorEvent);
        }
    }
}
