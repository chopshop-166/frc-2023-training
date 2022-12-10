package frc.robot;

import com.chopshop166.chopshoplib.Autonomous;
import com.chopshop166.chopshoplib.commands.CommandRobot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.maps.RobotMap;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

public class Robot extends CommandRobot {

    private Auto auto = new Auto();
    private RobotMap map = new RobotMap();

    // Subsystems
    Turret turret = new Turret(map.getTurretMap());

    Vision vision = new Vision(map.getVisionMap());

    Shooter shooter = new Shooter(map.getShooterMap());

    Climber climber = new Climber(map.getClimberMap());

    Claw claw = new Claw(map.getClawMap());

    Drive drive = new Drive(map.getDriveMap());

    @Autonomous(defaultAuto = true)
    public CommandBase exampleAuto = auto.exampleAuto();

    @Override
    public void teleopInit() {

    }

    @Override
    public void robotInit() {
        super.robotInit();
    }

    @Override
    public void configureButtonBindings() {

    }

    @Override
    public void populateDashboard() {

    }

    @Override
    public void setDefaultCommands() {

    }
}