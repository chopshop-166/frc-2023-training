package frc.robot.maps.subsystems;

import com.chopshop166.chopshoplib.motors.SmartMotorController;

public class ShooterMap {
    private SmartMotorController shooterMotor;
    private SmartMotorController feedMotor;

    public ShooterMap(SmartMotorController shooterMotor, SmartMotorController feedMotor) {
        this.shooterMotor = shooterMotor;
        this.feedMotor = feedMotor;
    }

    public ShooterMap() {
        this(new SmartMotorController(), new SmartMotorController());
    }

    public SmartMotorController getShooterMotor() {
        return shooterMotor;
    }

    public SmartMotorController getFeedMotor() {
        return feedMotor;
    }

}
