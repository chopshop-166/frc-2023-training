package frc.robot.maps;

import com.chopshop166.chopshoplib.maps.RobotMapFor;
import com.chopshop166.chopshoplib.motors.PIDSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.maps.subsystems.TurretMap;

@RobotMapFor("Default")
public class RobotMap {
    public TurretMap getTurretMap() {
        return new TurretMap(new PIDSparkMax(12, MotorType.kBrushless));
    }
}
