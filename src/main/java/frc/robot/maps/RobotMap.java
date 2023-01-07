package frc.robot.maps;

import com.chopshop166.chopshoplib.drive.SwerveDriveMap;
import com.chopshop166.chopshoplib.maps.RobotMapFor;
// $Imports$

import frc.robot.maps.subsystems.ShooterMap;

@RobotMapFor("Default")
public class RobotMap {
    // $Maps$

    // $Getters$

    public SwerveDriveMap getDriveMap() {
        return new SwerveDriveMap();
    }

    public ShooterMap getShooterMap() {
        return new ShooterMap();
    }
}
