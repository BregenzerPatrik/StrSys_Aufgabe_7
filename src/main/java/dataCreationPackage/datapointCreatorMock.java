package dataCreationPackage;

import esperVesper.EsperProcessor;
import testing.TestingController;

public class datapointCreatorMock implements Runnable{

    @Override
    public void run() {

            try {
                // values of multiple sensors are created, negative values must be ignored,
                //---------Window---Nr----1---------------------------------------------
                TestingController.resetTest();
                EsperProcessor.addDatapoint(0,"21.00,25.0,80.0");
                Thread.sleep(200);//-4800ms
                EsperProcessor.addDatapoint(0,"24.50");
                Thread.sleep(800);//-4000ms
                EsperProcessor.addDatapoint(3,"1.0,4.1");
                Thread.sleep(500);//-3500ms
                EsperProcessor.addDatapoint(0,"-1.00,24.0");
                TestingController.setExpectedTrafficJam(false);
                TestingController.setExpectedSpeedValue(0,125.64);
                TestingController.setExpectedSpeedValue(3,((1+4.1)/2)*3.6);
                TestingController.setTrueSectionAverage((21+25+80+24.5+1+4.1+24)/7*3.6);
                Thread.sleep(3500); //+0 ms
                TestingController.executeTest();
                Thread.sleep(100);
                /*
                Ergebnisse 1st Window:
                Sensor 0 Durchschnitt:
                    korrekt: 34,9 m/s bzw. 125,64 km/h
                    fälschlicherweise -1 nicht rausgefiltert: 28,9166 m/s bzw. 104,1 km/h
                    -1 rausgefiltert aber mitgezählt: 29,0833 m/s bzw. 104,7 km/h
                Sensor 3 Durchschnitt:
                    korrekt: 2,55 m/s bzw. 9,18 km/h

                Gesamt-Durchschnitt:
                    korrekt: 25,65714 m/s bzw. 92,36571 km/h
                 */
                //---------Window---Nr----2---------------------------------------------
                //test if traffic jam is found
                EsperProcessor.addDatapoint(0,"5.75,10.5,15.25");
                Thread.sleep(450);//-4450ms
                EsperProcessor.addDatapoint(1,"");
                Thread.sleep(800);//-3650ms
                EsperProcessor.addDatapoint(0,"1.123,5.813,4.321");
                Thread.sleep(700);//-2950ms
                EsperProcessor.addDatapoint(2,"0.5,0.25,0.2");
                Thread.sleep(450);//-2500ms
                EsperProcessor.addDatapoint(1,"47.11,13.37,69.420,69.69,-42.1337");
                Thread.sleep(2500);//+0ms
                TestingController.setExpectedTrafficJam(false);
                TestingController.setExpectedSpeedValue(0,25.6542);
                TestingController.setExpectedSpeedValue(1,179.631);
                TestingController.setExpectedSpeedValue(2,1.14);
                TestingController.setTrueSectionAverage(67.37455);
                TestingController.executeTest();
                Thread.sleep(100);//+100ms

                /*
                Ergebnisse 2nd Window:
                Sensor 0 Durchschnitt:
                    korrekt: 7,12616 m/s bzw. 25,6542 km/h
                Sensor 1 Durchschnitt:
                    korrekt: 49,8975 m/s bzw. 179,631 km/h
                    fälschlicherweise -42.1337 nicht rausgefiltert: 31,49126 m/s bzw. 113,368536 km/h
                    -42.1337 rausgefiltert aber mitgezählt: 39,918 m/s bzw. 143,7048 km/h
                Sensor 2 Durchschnitt:
                    korrekt: 0,31666 m/s bzw. 1,14 km/h

                Gesamt-Durchschnitt:
                    korrekt: 18,715 m/s bzw. 67,37455 km/h
                 */
                //---------Window---Nr----3---------------------------------------------
                //test if traffic jam is found
                EsperProcessor.addDatapoint(0,"0.1,0.2,0.001");
                Thread.sleep(450);//-4450ms
                EsperProcessor.addDatapoint(1,"0.4");
                Thread.sleep(800);//-3650ms
                EsperProcessor.addDatapoint(0,"0.6,0.2,0.1337");
                Thread.sleep(700);//-2950ms
                EsperProcessor.addDatapoint(2,"0.5,0.1,0.2");
                Thread.sleep(450);//-2500ms
                EsperProcessor.addDatapoint(1,"0.42,0.4711,0.69,0.54321,-0.2222");
                Thread.sleep(2500);//+100ms
                TestingController.setExpectedSpeedValue(0,0.74082);
                TestingController.setExpectedSpeedValue(1,1.8175032);
                TestingController.setExpectedSpeedValue(2,0.96);
                TestingController.setTrueSectionAverage(1.1723);
                TestingController.setExpectedTrafficJam(true);
                TestingController.executeTest();
                Thread.sleep(100);//+100ms

                /*
                Ergebnisse 3rd Window:
                Sensor 0 Durchschnitt:
                    korrekt: 0,2057833 m/s bzw. 0,74082 km/h
                Sensor 1 Durchschnitt:
                    korrekt: 0,504862 m/s bzw. 1,8175032 km/h
                    fälschlicherweise -0.2222 nicht rausgefiltert: 0,383685 m/s bzw. 1,381266 km/h
                    -0.2222 rausgefiltert aber mitgezählt: 0,420718333333 m/s bzw. 1,514586 km/h
                Sensor 2 Durchschnitt:
                    korrekt: 0,2666 m/s bzw. 0,96 km/h

                Gesamt-Durchschnitt:
                    korrekt: 0,3256 m/s bzw. 1,1723 km/h
                 */



                Thread.sleep(7000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

    }
}
