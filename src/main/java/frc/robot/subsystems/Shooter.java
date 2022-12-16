package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;
import com.chopshop166.chopshoplib.motors.SmartMotorController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.maps.subsystems.ShooterMap;

public class Shooter extends SmartSubsystemBase {

    private final SmartMotorController shootMotor;
    private final SmartMotorController feedMotor;

    private final double feedSpeed = 0.5;
    private double setPoint = 0;

    public Shooter(ShooterMap map) {
        shootMotor = map.getShooterMotor();
        feedMotor = map.getFeedMotor();
        SmartDashboard.putNumber("Shoot Speed", 0);
    }

    public CommandBase speedUpTo(DoubleSupplier speed) {
        final double THRESHOLD = 0.1;
        return cmd("Speed Up").onInitialize(() -> {
            shootMotor.setSetpoint(speed.getAsDouble());
            setPoint = speed.getAsDouble();
        }).runsUntil(() -> Math.abs(speed.getAsDouble() - shootMotor.getEncoder().getRate()) < THRESHOLD);
    }

    public CommandBase feed() {
        return instant("Feed", () -> {
            feedMotor.set(feedSpeed);
        });
    }

    public CommandBase stopShooter() {
        return instant("Stop", () -> {
            shootMotor.setSetpoint(0);
            setPoint = 0;
            feedMotor.stopMotor();
        });
    }

    public CommandBase shoot(DoubleSupplier speed) {
        return sequence("Shoot", speedUpTo(speed), feed(), waitFor(0.1), stopShooter());
    }

    @Override
    public void reset() {
        // Nothing to reset here
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // Use this for any background processing
        SmartDashboard.putNumber("Shooter Error", setPoint - shootMotor.getEncoder().getRate());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    @Override
    public void safeState() {
        shootMotor.setSetpoint(0);
        feedMotor.stopMotor();
        setPoint = 0;
    }
}