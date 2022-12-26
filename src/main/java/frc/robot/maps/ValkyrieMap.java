package frc.robot.maps;

import com.chopshop166.chopshoplib.digital.CSDigitalInput;
import com.chopshop166.chopshoplib.digital.DigitalInputSource;
import com.chopshop166.chopshoplib.maps.DifferentialDriveMap;
import com.chopshop166.chopshoplib.maps.RobotMapFor;
import com.chopshop166.chopshoplib.motors.CSSparkMax;
import com.chopshop166.chopshoplib.pneumatics.IDSolenoid;
import com.chopshop166.chopshoplib.pneumatics.RevDSolenoid;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.maps.subsystems.ClawMap;

@RobotMapFor("ValkyrieMap")
public class ValkyrieMap extends RobotMap{
    public DifferentialDriveMap getDriveMap(){
        return new DifferentialDriveMap(new CSSparkMax(2, MotorType.kBrushless), new CSSparkMax(3, MotorType.kBrushless), 1);
    }

    public ClawMap getClawMap() {
        IDSolenoid solenoid = new RevDSolenoid(1,2);
        DigitalInputSource sensor = new CSDigitalInput(3);
        return new ClawMap(solenoid, sensor);
    }
}
