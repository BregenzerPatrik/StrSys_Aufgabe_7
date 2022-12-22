package dataCreationPackage;

public class FilteredKMHSensorEvent extends BaseSensorEvent {
    public FilteredKMHSensorEvent(int id, double speed) {
        super(id, speed);
    }
    @Override
    public String toString() {
        return "FilteredSensorEvent{" +
                "id=" + id +
                ", speed=" + speed +
                '}';
    }
}
