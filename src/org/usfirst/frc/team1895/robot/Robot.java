package org.usfirst.frc.team1895.robot;

import org.usfirst.frc.team1895.robot.commands.autonomous.BLeft_CenterPos_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.BLeft_LeftPos_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.BLeft_RightPos_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.BRight_CenterPos_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.BRight_LeftPos_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.BRight_RightPos_Autonomous;
import org.usfirst.frc.team1895.robot.commands.autonomous.TestAutonomousRetry;
import org.usfirst.frc.team1895.robot.commands.drivetrain.AutonomousGearCondition;
import org.usfirst.frc.team1895.robot.commands.drivetrain.GearGoneSequence;
import org.usfirst.frc.team1895.robot.commands.drivetrain.RetrySequence;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnOnLEDRing;
import org.usfirst.frc.team1895.robot.commands.gears.DeployGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.RetractGearHolder;
import org.usfirst.frc.team1895.robot.ledstrip.LEDSubsystem;
import org.usfirst.frc.team1895.robot.subsystems.Drivetrain;
import org.usfirst.frc.team1895.robot.subsystems.FilteredCamera;
import org.usfirst.frc.team1895.robot.subsystems.GearHolder;
import org.usfirst.frc.team1895.robot.subsystems.Shooter;
import org.usfirst.frc.team1895.robot.subsystems.Winch;
import org.usfirst.frc.team1895.robot.testcommands.TestAlignToPeg;
import org.usfirst.frc.team1895.robot.testcommands.TestCameraCalibration;
import org.usfirst.frc.team1895.robot.testcommands.TestDriveStraightSetDistance;
import org.usfirst.frc.team1895.robot.testcommands.TestDriveToObstacle;
import org.usfirst.frc.team1895.robot.testcommands.TestEmptyCommand;
import org.usfirst.frc.team1895.robot.testcommands.TestTurnWithGyro;
import org.usfirst.frc.team1895.robot.testcommands.TestTurnWithoutPID;

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
		chooser.addObject("RED Left Position Autonomous", new BLeft_LeftPos_Autonomous());
		chooser.addObject("RED Center Position Autonomous", new BLeft_CenterPos_Autonomous());
		chooser.addObject("RED Right Position Autonomous", new BLeft_RightPos_Autonomous());
		chooser.addObject( "BLUE Left Position Autonomous", new BRight_LeftPos_Autonomous());
		chooser.addDefault("BLUE Center Position Autonomous", new BRight_CenterPos_Autonomous());
		chooser.addObject( "BLUE Right Position Autonomous", new BRight_RightPos_Autonomous());
		chooser.addObject("TestAutonomous Autonomous", new TestAutonomousRetry());
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
        gear_camera.stopVisionThread();
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
	    	SmartDashboard.putNumber("Turn D value: ", -.01);
	    	SmartDashboard.putNumber("Test Turn Angle: ", 90.0);
	    	SmartDashboard.putNumber("Test Turn NP Speed: ", .4);
	    	
			SmartDashboard.putData("Test PID Turn", new TestTurnWithGyro());
	        SmartDashboard.putData("Test Turn - NO PID", new TestTurnWithoutPID());
	        
			SmartDashboard.putData("Test Conditional Command", new AutonomousGearCondition(new RetrySequence(), new GearGoneSequence()));

	        
	        
	        // Distance Related Testing
	        SmartDashboard.putNumber("Distance P value: ", .1);
	    	SmartDashboard.putNumber("Distance I value: ", 0.0);
	    	SmartDashboard.putNumber("Distance D value: ", -.01);
	    	SmartDashboard.putNumber("Test Drive Distance: ", 20.0);
	    	SmartDashboard.putNumber("Test Drive Tank Speed: ", .4);
	    	SmartDashboard.putNumber("Test Drive Tank Scalar:", .94);
	    	

	    	
	        SmartDashboard.putData("Test Drive PID Distance", new TestDriveStraightSetDistance());
	        SmartDashboard.putData("Test Drive RangeFinder", new TestDriveToObstacle());
	        
	        //Sensor testing
	        SmartDashboard.putNumber("Gyro Angle: ", Robot.drivetrain.getAngle());
	        SmartDashboard.putNumber("Left Encoder: ", Robot.drivetrain.getLEncoderValues());
	        SmartDashboard.putNumber("Right Encoder: ", Robot.drivetrain.getREncoderValues());
	        SmartDashboard.putNumber("Rangefinder: ", Robot.drivetrain.fineDistanceFinder());
	        SmartDashboard.putNumber("Left Encoder: ", Robot.gear_camera.getAvgCenterX());


	        
	        
	        // Gear Holder Related Testing
	        SmartDashboard.putData("Deploy Gearholder", new DeployGearHolder()); 
	        SmartDashboard.putData("Retract Gearholder", new RetractGearHolder()); 
	        //Vision Related testing
			SmartDashboard.putNumber("Farthest Left Acceptable: ", 375);
			SmartDashboard.putNumber("Farthest Right Acceptable: ", 405);
	        // Camera Alignment Testing
	        // Add Relevant Dashboard values and Commands here
	        
	        SmartDashboard.putNumber("lowSpeed: ", 0.2);
			SmartDashboard.putNumber("highSpeed: ", 0.3);
			SmartDashboard.putNumber("neutralSpeed: ", 0.3);
	        
	        Robot.gear_camera.startVisionThread();
	        
	       
	        SmartDashboard.putData("Test AlignToPeg ", new TestAlignToPeg());
	        SmartDashboard.putData("TestCameraCalibration", new TestCameraCalibration());
	        SmartDashboard.putData("Test TurnLEDRingOn ", new TurnOnLEDRing());
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
	}
	
	public static void setRetryButton(boolean state) {
		retryButton.setPressed(state);
	}
}
