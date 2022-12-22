package esperVesper;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import dataCreationPackage.FilteredKMHSensorEvent;
import dataCreationPackage.RawSensorEvent;
import testing.TestingController;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class AverageSpeedBySensorListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents,
                       EPStatement statement, EPRuntime runtime) {
        System.out.println("----- New Average By Sensor Event ---------");
        for (EventBean event:newEvents){
            Map<String,Double> map=(HashMap<String,Double>) event.getUnderlying();
            if(map.get("avg") != null & map.get("id") != null) {
                int id = (int) Integer.parseInt(String.valueOf(map.get("id")).split("\\.")[0]);
                TestingController.setTrueSpeedValue( id,map.get("avg"));
                System.out.println("average speed(kmh): " + map.get("avg") + " of sensor: " + map.get("id"));
            }
        }
        System.out.println("-------------------------------------------");

    }
}
