package org.usfirst.frc.team1895.robot;

import org.usfirst.frc.team1895.robot.commands.autonomous.Position1_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.Position2_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.Position3_Autonomous;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightSetDistance;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.drivetrain.StopRobot;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1895.robot.commands.gears.DeployGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.GetAverageVoltage;
import org.usfirst.frc.team1895.robot.commands.gears.RetractGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.WaitUntilGearGoneOrTimeOut;
import org.usfirst.frc.team1895.robot.ledstrip.LEDSubsystem;
import org.usfirst.frc.team1895.robot.subsystems.Drivetrain;
import org.usfirst.frc.team1895.robot.subsystems.GearHolder;
import org.usfirst.frc.team1895.robot.subsystems.Shooter;
import org.usfirst.frc.team1895.robot.subsystems.Winch;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 *  List of all sensors used:
 *  
 *  List of what's displayed to SmartDashboard:
 *  
 *  
 */
public class Robot extends IterativeRobot {

	public static Drivetrain drivetrain;
	public static Shooter shooter;
	public static Winch winch;
	public static GearHolder gearholder;
	public static LEDSubsystem led;
	public static OI oi;
	//public static FilteredCamera gear_camera;
	//public static FilteredCamera fuel_camera;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		drivetrain = new Drivetrain();
		shooter = new Shooter();
		winch = new Winch();
		gearholder = new GearHolder();
		led = new LEDSubsystem();
		oi = new OI();
		//gear_camera = new FilteredCamera();
		//fuel_camera = new FilteredCamera();
		//choices for the user to pick autonomouses in smart dashboard
	
		
		chooser.addDefault("Position 3", new Position3_Autonomous());
		chooser.addObject("Position 2", new Position2_Autonomous());
		chooser.addObject("Position 1", new Position1_Autonomous());
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		drivetrain.setRobotTeleop(false);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		led.start(led.disabledPeriodic);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();
		/* String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
 
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
//		DriveToObstacle testCmd = new DriveToObstacle(24, 0.5);
		SmartDashboard.putData("Test data: voltage and distance", new GetAverageVoltage());
		//PID related commands
		SmartDashboard.putData("Test driving forward", new DriveStraightSetDistance(-50));
		SmartDashboard.putData("Test driving backward", new DriveStraightSetDistance(50));
		SmartDashboard.putData("Test turning clockwise", new TurnWithGyro(45));
		SmartDashboard.putData("Test turning counterclockwise", new TurnWithGyro(-45));
		SmartDashboard.putData("Test DriveToObstacle (rangefinder) ", new DriveToObstacle(15, 0.5));
		
		SmartDashboard.putData("Test data: voltage and distance", new StopRobot(2));
		SmartDashboard.putData("deploy", new DeployGearHolder());
		SmartDashboard.putData("retract", new RetractGearHolder());
		
		drivetrain.setRobotTeleop(true);
	}

	@Override
	public void testInit(){
		SmartDashboard.putData("Test data: voltage and distance", new GetAverageVoltage());
		//PID related commands
		SmartDashboard.putData("Test driving forward", new DriveStraightSetDistance(-50));
		SmartDashboard.putData("Test driving backward", new DriveStraightSetDistance(50));
		SmartDashboard.putData("Test turning clockwise", new TurnWithGyro(90));
		SmartDashboard.putData("Test turning counterclockwise", new TurnWithGyro(-90));
		SmartDashboard.putData("Test DriveToObstacle (rangefinder) ", new DriveToObstacle(15, 0.5));
		
		SmartDashboard.putData("Test data: voltage and distance", new StopRobot(2));
	
	}
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
