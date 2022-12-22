package testing;

public class TestingController {
    static int numOfSensors = 7;
    static Double[] trueSpeeds = new Double[numOfSensors];
    static Double[] expectedSpeeds = new Double[numOfSensors];

    static Double expectedSectionAverage = null;
    static Double trueSectionAverage = null;

    static boolean trueTrafficJam = false;
    static boolean expectedTrafficJam = false;

    public static boolean isTrueTrafficJam() {
        return trueTrafficJam;
    }

    public static void setTrueTrafficJam(boolean trueTrafficJam) {
        trueTrafficJam = trueTrafficJam;
    }

    public static boolean isExpectedTrafficJam() {
        return expectedTrafficJam;
    }

    public static void setExpectedTrafficJam(boolean expectedTrafficJam) {
        expectedTrafficJam = expectedTrafficJam;
    }



    public static void initializeExpectedSectionAverage() {
        expectedSectionAverage = null;
    }

    public static void initializetrueSectionAverage() {
        trueSectionAverage = null;
    }

    public static void setExpectedSectionAverage(double speed) {
        expectedSectionAverage = speed;
    }

    public static void setTrueSectionAverage(double speed) {
        trueSectionAverage = speed;
    }

    public static void setTrueSpeedValue(int sensorNumber, double sensorValue) {
        trueSpeeds[sensorNumber] = sensorValue;
    }

    public static void setExpectedSpeedValue(int sensorNumber, double sensorValue) {
        expectedSpeeds[sensorNumber] = sensorValue;
    }

    public static void initiateExpectedSpeeds() {
        expectedSpeeds = new Double[numOfSensors];
        for (int i = 0; i < numOfSensors; ++i) {
            expectedSpeeds[i] = null;
        }
    }

    public static void initiateTrueSpeeds() {
        trueSpeeds = new Double[numOfSensors];
        for (int i = 0; i < numOfSensors; ++i) {
            trueSpeeds[i] = null;
        }
    }

    public static void resetTest() {
        initiateExpectedSpeeds();
        initiateTrueSpeeds();
        initializeExpectedSectionAverage();
        initializetrueSectionAverage();
        setTrueTrafficJam(false);
        setExpectedTrafficJam(false);
    }

    public static void executeTest() {
        //test if expedted speed for sensors is
        for (int i = 0; i < numOfSensors; ++i) {
            if(expectedSpeeds[i]!= trueSpeeds[i]) {
                if (0.01 < Math.abs(expectedSpeeds[i] - trueSpeeds[i])) {
                    System.out.println("ERROR in average sensor speed for sensor: " + i + " expected: " + expectedSpeeds[i] + " got: " + trueSpeeds[i]);
                }
            }
        }
        //test if total average speed of section is correct
        if (0.01 < Math.abs(expectedSectionAverage -trueSectionAverage)) {
            System.out.println("ERROR IN SECTION AVERAGE expected: " + trueSectionAverage + " got: " + expectedSectionAverage);
        }

        //test if traffic jam has been detected
        if (isTrueTrafficJam()!= isExpectedTrafficJam()) {
            System.out.println("ERROR IN TRAFFIC JAM DETECTION expected: " + isTrueTrafficJam() + " got: " + isExpectedTrafficJam());
        }
        resetTest();

    }

}
