package frc.robot;

public class Robot extends CommandRobot {

    
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

    public CommandBase safeStateSubsystems(final SmartSubsystem... subsystems) {
        return parallel("Reset Subsystems",
                Stream.of(subsystems).map(SmartSubsystem::safeStateCmd).toArray(CommandBase[]::new));
    }
}