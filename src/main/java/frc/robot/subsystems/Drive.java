package frc.robot.subsystems;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;

import frc.robot.maps.subsystems.DriveMap;

public class Drive extends SmartSubsystemBase {

    private DriveMap map;

    public Drive(DriveMap map) {
        this.map = map;
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