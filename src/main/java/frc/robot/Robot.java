package frc.robot;

import com.chopshop166.chopshoplib.Autonomous;
import com.chopshop166.chopshoplib.commands.CommandRobot;
import com.chopshop166.chopshoplib.controls.ButtonXboxController;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.maps.OffAxisMap;
import frc.robot.maps.RobotMap;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;

public class Robot extends CommandRobot {

    private Auto auto = new Auto();
    private RobotMap map = new OffAxisMap();
    private final ButtonXboxController driveController = new ButtonXboxController(0);

    // $Subsystems$
    private Drive drive = new Drive(map.getDriveMap());
    private Shooter shooter = new Shooter(map.getShooterMap());

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
        driveController.a().whenPressed(drive.resetGyro());
        driveController.b().whenPressed(shooter.shoot(() -> SmartDashboard.getNumber("Shoot Speed", 0)));
        driveController.x().whenPressed(drive.driveTo(
                new Pose2d(2, -1, Rotation2d.fromDegrees(180))));
    }

    @Override
    public void populateDashboard() {

    }

    @Override
    public void setDefaultCommands() {
        drive.setDefaultCommand(drive.fieldCentricDrive(driveController::getLeftX, driveController::getLeftY,
                driveController::getRightX));

    }
}