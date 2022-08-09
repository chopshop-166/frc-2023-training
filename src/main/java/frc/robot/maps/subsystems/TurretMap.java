package frc.robot.maps.subsystems;

import com.chopshop166.chopshoplib.motors.SmartMotorController;

public class TurretMap {
    private SmartMotorController motor;

    public TurretMap(SmartMotorController motor) {
        this.motor = motor;
    }

    public SmartMotorController getMotor() {
        return motor;
    }
}
