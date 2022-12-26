package frc.robot.maps.subsystems;

import com.chopshop166.chopshoplib.digital.DigitalInputSource;
import com.chopshop166.chopshoplib.pneumatics.IDSolenoid;
import com.chopshop166.chopshoplib.pneumatics.MockDSolenoid;

public class ClawMap {
    private IDSolenoid solenoid;
    private DigitalInputSource sensor;

    public ClawMap(final IDSolenoid solenoid, final DigitalInputSource sensor) {
        this.solenoid = solenoid;
        this.sensor = sensor;
    }

    public ClawMap(){
        this(new MockDSolenoid(), () -> false);
    }

    public IDSolenoid getSolenoid(){
        return this.solenoid;
    }

    public DigitalInputSource getSensor() {
        return this.sensor;
    }
}

