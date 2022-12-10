package frc.robot.maps;
import frc.robot.maps.subsystems.TurretMap;

import frc.robot.maps.subsystems.VisionMap;


import com.chopshop166.chopshoplib.maps.RobotMapFor;

import frc.robot.maps.subsystems.ClawMap;
import frc.robot.maps.subsystems.ClimberMap;
import frc.robot.maps.subsystems.DriveMap;
import frc.robot.maps.subsystems.ShooterMap;

@RobotMapFor("Default")
public class RobotMap {
    // Maps
    private TurretMap turretMap = new TurretMap();

    private VisionMap visionMap = new VisionMap();

    private ShooterMap shooterMap = new ShooterMap();

    private ClimberMap climberMap = new ClimberMap();

    private ClawMap clawMap = new ClawMap();

    private DriveMap driveMap = new DriveMap();

    // Getters
    public TurretMap getTurretMap() {
        return turretMap;
    } 

    public VisionMap getVisionMap() {
        return visionMap;
    } 

    public ShooterMap getShooterMap() {
        return shooterMap;
    }

    public ClimberMap getClimberMap() {
        return climberMap;
    }

    public ClawMap getClawMap() {
        return clawMap;
    }

    public DriveMap getDriveMap() {
        return driveMap;
    }

}
