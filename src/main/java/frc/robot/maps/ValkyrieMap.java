package frc.robot.maps;

import com.chopshop166.chopshoplib.digital.CSDigitalInput;
import com.chopshop166.chopshoplib.digital.DigitalInputSource;
import com.chopshop166.chopshoplib.maps.RobotMapFor;
import com.chopshop166.chopshoplib.pneumatics.IDSolenoid;
import com.chopshop166.chopshoplib.pneumatics.RevDSolenoid;

import frc.robot.maps.subsystems.ClawMap;

@RobotMapFor("Valkyrie")
public class ValkyrieMap extends RobotMap {

    @Override
    public ClawMap getClawMap() {
        IDSolenoid solenoid = new RevDSolenoid(1, 2);
        DigitalInputSource sensor = new CSDigitalInput(3);
        return new ClawMap(solenoid, sensor);
    }
}
