package dataCreationPackage;

public class BaseSensorEvent {
    public BaseSensorEvent(int id, double speed) {
        this.id = id;
        this.speed = speed;
    }
    protected int id;
    protected double speed;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "BaseSensorEvent{" +
                "id=" + id +
                ", speed=" + speed +
                '}';
    }

}
