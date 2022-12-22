package dataCreationPackage;

public class RawSensorEvent extends BaseSensorEvent {
    public RawSensorEvent(int id, double speed) {
        super(id, speed);
    }

    @Override
    public String toString() {
        return "RawSensorEvent{" +
                "id=" + id +
                ", speed=" + speed +
                '}';
    }
}
