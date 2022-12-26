package frc.robot.maps;

import com.chopshop166.chopshoplib.maps.DifferentialDriveMap;
import com.chopshop166.chopshoplib.maps.RobotMapFor;

import frc.robot.maps.subsystems.ClawMap;

@RobotMapFor("Default")
public class RobotMap {

    public DifferentialDriveMap getDriveMap(){
        return new DifferentialDriveMap();
    }

    public ClawMap getClawMap() {
        return new ClawMap();
    }
}
