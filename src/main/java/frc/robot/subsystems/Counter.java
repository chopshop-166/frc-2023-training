package frc.robot.subsystems;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Counter extends SmartSubsystemBase {

    private int data;

    public CommandBase increment() {
        return instant("Increment", () -> {
            data++;
        });
    }

    public CommandBase increment(int value) {
        return instant("Increment " + value, () -> {
            data += value;
        });
    }

    public CommandBase decrement() {
        return instant("Decrement", () -> {
            data--;
        });
    }

    public CommandBase decrement(int value) {
        return instant("Decrement " + value, () -> {
            data -= value;
        });
    }

    public CommandBase print() {
        return instant("Print", () -> {
            System.out.println(data);
        });
    }

    @Override
    public void reset() {
        // Nothing to reset here
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // Use this for any background processing
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    @Override
    public void safeState() {

    }
}