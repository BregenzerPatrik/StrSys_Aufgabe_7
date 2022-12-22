package esperVesper;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.EventSender;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.*;
import dataCreationPackage.FilteredKMHSensorEvent;
import dataCreationPackage.RawSensorEvent;
import dataCreationPackage.TrafficJamEvent;

import java.util.Objects;

public class EsperProcessor implements Runnable {
    private static EPRuntime runtime;
    private static EventSender rawSensorEventSender;
    private static EventSender filteredSensorEventSender;
    private static EventSender trafficJamEventSender;





    //EPL statements
    // remove negative values
    private static String q1 = "@name('getSensorsEvents') select * from RawSensorEvent(speed>=0);\n";
    private static String q2 = "@name('averageSpeedOfSection') select avg(speed) as avg from FilteredKMHSensorEvent#time_batch(5 sec);\n";
    private static String q3 = "@name('averageSpeedBySensor') select id as id, avg(speed) as avg from FilteredKMHSensorEvent#time_batch(5 sec) group by id;\n";
    private static String q4 = "@name('InsertAverageSpeedBySensorIntoAvgSpeedEvent') insert into AvgSpeedEvent select avg(speed) as avg " +
                                "from FilteredKMHSensorEvent#time_batch(5 sec);\n";
    private static String q5 = "@name('TrafficWarning') select * from AvgSpeedEvent#length_batch(2);\n";


    public static void addDatapoint(int sensorID, String singleSensorLine){
        if(!Objects.equals(singleSensorLine, "")){
            String[] measurements=singleSensorLine.split(",");
            for(String measurement : measurements){
                double speed= Double.parseDouble(measurement);
                addRawSensorEvent(new RawSensorEvent(sensorID,speed));
            }
        }
    }
    public static void addRawSensorEvent(RawSensorEvent sensorEvent){
        if(rawSensorEventSender !=null){
            rawSensorEventSender.sendEvent(sensorEvent);
        }

    }
    public static void addFilteredSensorEvent(FilteredKMHSensorEvent sensorEvent){
        if(filteredSensorEventSender !=null){
            filteredSensorEventSender.sendEvent(sensorEvent);
        }

    }
    public static void prepare(){
        //configure
        Configuration configuration = new Configuration();
        configuration.getCommon().addEventType(RawSensorEvent.class);
        configuration.getCommon().addEventType(FilteredKMHSensorEvent.class);
        configuration.getCommon().addEventType(TrafficJamEvent.class);

        //prepare compiler
        EPCompiler compiler = EPCompilerProvider.getCompiler();
        CompilerArguments args = new CompilerArguments(configuration);
        try {
            //fragen ob hier richtiger datentyp war
            EPCompiled epCompiled =  compiler.compile(q1 + q2+ q3 + q4 + q5, args);
            //deploy requests
            runtime = EPRuntimeProvider.getDefaultRuntime(configuration);
            runtime.initialize();
            EPDeployment deployment = runtime.getDeploymentService().deploy(epCompiled);

            //add sensors for events
            rawSensorEventSender =runtime.getEventService().getEventSender("RawSensorEvent");
            filteredSensorEventSender =runtime.getEventService().getEventSender("FilteredKMHSensorEvent");
            trafficJamEventSender= runtime.getEventService().getEventSender("TrafficJamEvent");



            //add listeners
            EPStatement statement = runtime.getDeploymentService()
                    .getStatement(deployment.getDeploymentId(),"getSensorsEvents");
            statement.addListener(new RawSensorEventListener());

            EPStatement statement2 = runtime.getDeploymentService()
                    .getStatement(deployment.getDeploymentId(),"averageSpeedOfSection");
            statement2.addListener(new AverageSpeedOfSectionListener());

            EPStatement statement3 = runtime.getDeploymentService()
                    .getStatement(deployment.getDeploymentId(),"averageSpeedBySensor");
            statement3.addListener(new AverageSpeedBySensorListener());

            EPStatement statement4 = runtime.getDeploymentService()
                    .getStatement(deployment.getDeploymentId(),"InsertAverageSpeedBySensorIntoAvgSpeedEvent");
            statement4.addListener((eventBeans, eventBeans1, epStatement, epRuntime) -> {
                //Do Nothing
            });

            EPStatement statement5 = runtime.getDeploymentService()
                    .getStatement(deployment.getDeploymentId(),"TrafficWarning");
            statement5.addListener(new AverageSpeedChangeListener());



        }
        catch(Exception e){System.out.println(e.getMessage());}

    }

    public static void addTrafficJamEvent(TrafficJamEvent trafficJamEvent) {
        if(trafficJamEventSender !=null){
            trafficJamEventSender.sendEvent(trafficJamEvent);
        }
    }

    @Override
    public void run() {
        prepare();

    }
}
