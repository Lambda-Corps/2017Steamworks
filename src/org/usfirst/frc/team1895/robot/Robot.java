package org.usfirst.frc.team1895.robot;

import org.usfirst.frc.team1895.robot.commands.autonomous.BRight_LeftPos_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.BLeft_CenterPos_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.BRight_RightPos_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.BLeft_RightPos_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.BRight_CenterPos_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.BLeft_LeftPos_Autonomous;
import org.usfirst.frc.team1895.robot.ledstrip.LEDSubsystem;
import org.usfirst.frc.team1895.robot.subsystems.Drivetrain;
import org.usfirst.frc.team1895.robot.subsystems.FilteredCamera;
import org.usfirst.frc.team1895.robot.subsystems.GearHolder;
import org.usfirst.frc.team1895.robot.subsystems.Shooter;
import org.usfirst.frc.team1895.robot.subsystems.Winch;
import org.usfirst.frc.team1895.robot.testcommands.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.buttons.InternalButton;
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
	
	int drive_encoder_counter;
	public static FilteredCamera gear_camera;
	
	public static InternalButton retryButton;

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
		
		drive_encoder_counter = 0;
		gear_camera = new FilteredCamera();
		//fuel_camera = new FilteredCamera();
		
		retryButton = new InternalButton();
		
		//choices for the user to pick autonomouses in smart dashboard
		chooser.addObject("LEFT BOILER Position 3", new BRight_RightPos_Autonomous());
		chooser.addObject("LEFT BOILER Position 2", new BLeft_CenterPos_Autonomous());
		chooser.addObject("LEFT BOILER Position 1", new BRight_LeftPos_Autonomous());
		chooser.addObject("RIGHT BOILER Position 3", new BLeft_LeftPos_Autonomous());
		chooser.addDefault("RIGHT BOILER Position 2", new BRight_CenterPos_Autonomous());
		chooser.addObject("RIGHT BOILER Position 1", new BLeft_RightPos_Autonomous());
		chooser.addObject("Test Commands", new TestEmptyCommand());
		
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
		//autonomousCommand = new BLeft_Position2_Autonomous();
		/* String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		// schedule the autonomous command (example)
		// If we are in test mode, we are using the dashboard to rapidly iterate 
		// command testing, such as PID tuning, turning, etc.
		//
		// These commands are in autonomous Init because they are run only during
		// the autonomous sequence.  We need to ensure we're not shifting, or doing
		// anything other than simulating the test environment.
		if(!autonomousCommand.getName().contains("Test")){
			if (autonomousCommand != null) {
				autonomousCommand.start();
			}
		}
		else{
			// We are in test mode, add all the relevant pieces to the smart 
			// dashboard for testing
			// Testing Turning
			SmartDashboard.putNumber("Turn P value: ", .025);
	    	SmartDashboard.putNumber("Turn I value: ", 0.0);
	    	SmartDashboard.putNumber("Turn D value: ", -.005);
	    	SmartDashboard.putNumber("Test Turn Angle: ", 90.0);
	    	SmartDashboard.putNumber("Test Turn NP Speed: ", .4);
	    	
			SmartDashboard.putData("Test PID Turn", new TestTurnWithGyro());
	        SmartDashboard.putData("Test Turn - NO PID", new TestTurnWithoutPID());
	        
	        
	        // Distance Related Testing
	        SmartDashboard.putNumber("Distance P value: ", .1);
	    	SmartDashboard.putNumber("Distance I value: ", 0.0);
	    	SmartDashboard.putNumber("Distance D value: ", -.01);
	    	SmartDashboard.putNumber("Test Drive Distance: ", 20.0);
	    	SmartDashboard.putNumber("Test Drive Tank Speed: ", .4);
	    	SmartDashboard.putNumber("Test Drive Tank Scalar:", .94);
	    	
	        SmartDashboard.putData("Test Drive PID Distance", new TestDriveStraightSetDistance());
	        SmartDashboard.putData("Test Drive RangeFinder", new TestDriveToObstacle());
	        
	        // Gear Holder Related Testing
	        //SmartDashboard.putData("deploy", new DeployGearHolder()); 
	        //SmartDashboard.putData("Align to Peg ", new AlignToPeg());*/
	        
	        // Camera Alignment Testing
	        // Add Relevant Dashboard values and Commands here
	        
	        // Shooter Testing
	        // Add relevant Dashboard values and Commands here
		}
			
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
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null)
            autonomousCommand.cancel();
        
        drivetrain.resetEncoders();
        drivetrain.setRobotTeleop(true);
	}
 
	@Override
	public void testInit(){
		/*SmartDashboard.putData("Test data: voltage and distance", new GetAverageVoltage());
		//PID related commands
		SmartDashboard.putData("Test driving forward", new DriveStraightSetDistance(-50));
		SmartDashboard.putData("Test driving backward", new DriveStraightSetDistance(50));
		SmartDashboard.putData("Test turning clockwise", new TurnWithGyro(90));
		SmartDashboard.putData("Test turning counterclockwise", new TurnWithGyro(-90));
		SmartDashboard.putData("Test DriveToObstacle (rangefinder) ", new DriveToObstacle(15, 0.5));
		
		SmartDashboard.putData("Test data: voltage and distance", new StopRobot(2));*/
	}
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
//		SmartDashboard.putNumber("Motor current left: ", drivetrain.lMCurrent());
//		SmartDashboard.putNumber("motor current right: ", drivetrain.rMCurrent());
//		SmartDashboard.putNumber("LeftEncoder: ", drivetrain.getLEncoderValues());
//		SmartDashboard.putNumber("RightEncoder: ", drivetrain.getREncoderValues());
//		SmartDashboard.putNumber("Gyro Value: ", drivetrain.getAngle());
//		SmartDashboard.putNumber("AHRS turning value", Robot.drivetrain.getAngleAHRS());
		
		drive_encoder_counter++;
    	//so that the counter will print the current and encoder values only 5 times a second
    	if(drive_encoder_counter == 10) {
    		//drivetrain.printTelemetry();
    		drive_encoder_counter = 0;
    	}
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
		//Robot.gear_camera.stopVisionThread();
	}
	
	public static void setRetryButton(boolean state) {
		retryButton.setPressed(state);
	}
}
