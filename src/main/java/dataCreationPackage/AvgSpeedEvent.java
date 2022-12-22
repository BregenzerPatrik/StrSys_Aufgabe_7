package dataCreationPackage;

public class AvgSpeedEvent {
    private double avgSpeed;

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public AvgSpeedEvent(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    @Override
    public String toString() {
        return "AvgSpeedEvent{" +
                "avgSpeed=" + avgSpeed +
                '}';
    }

}
