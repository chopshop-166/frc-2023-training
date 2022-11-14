package frc.robot;

import java.util.stream.Stream;

import com.chopshop166.chopshoplib.commands.CommandRobot;
import com.chopshop166.chopshoplib.commands.SmartSubsystem;
import com.chopshop166.chopshoplib.controls.ButtonXboxController;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.maps.OffAxisMap;
import frc.robot.maps.RobotMap;
import frc.robot.subsystems.Drive;

public class Robot extends CommandRobot {

    private RobotMap map = new OffAxisMap();
    private Drive drive = new Drive(map.getDriveMap());
    private final ButtonXboxController driveController = new ButtonXboxController(0);

    @Override
    public void teleopInit() {

    }

    @Override
    public void robotInit() {
        super.robotInit();
    }

    @Override
    public void configureButtonBindings() {
        driveController.a().whenPressed(drive.resetGyro());
        driveController.x().whenPressed(instant("", () -> {
            drive.setDriverMode(true);
        }));

        driveController.y().whenPressed(instant("", () -> {
            drive.setDriverMode(false);
        }));
    }

    @Override
    public void populateDashboard() {

    }

    @Override
    public void setDefaultCommands() {
        drive.setDefaultCommand(drive.fieldCentricDrive(driveController::getLeftX, driveController::getLeftY,
                driveController::getRightX));
    }

    public CommandBase safeStateSubsystems(final SmartSubsystem... subsystems) {
        return parallel("Reset Subsystems",
                Stream.of(subsystems).map(SmartSubsystem::safeStateCmd).toArray(CommandBase[]::new));
    }
}