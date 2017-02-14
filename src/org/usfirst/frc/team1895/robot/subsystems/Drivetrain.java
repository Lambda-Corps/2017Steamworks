package org.usfirst.frc.team1895.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DefaultDriveCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Changelog:
 * 		(Maddy Seputro)
 *  	Description: Subsystem for This subsystem handles the robot's driving. It includes methods used for both tele-op
 *  	driving and automated driving commands, and will be used for commands in the Drivetrain Commands Package
 * 			Will need:
 * 			- 6 motors (three each side)
 * 			- 2 encoders, one for left and one for right motorgroups (so 4 digital IO ports)
 * 			- 2 solenoids
 * 			- cameras for Vision Tracking
 * 			- gyro for turning (so one analog port)
 * 			- rangefinder for aligning to lift or boiler (so one analog port)
 * 			- gear sensor for determining whether a gear is present in the slot or not (exact sensor TBD)
 * 		To do still:
 * 			- Finish methods 
 *  		- Add comments when you're done writing and as you write
 * 			- Add cameras if you want them
 * 			- Test
 * Added: Moved alignToHighGoal method from shooter subsystem to drivetrain. Also moved the alignToRope method from the winch subsystem to drivetrain. 
 * Standardized comments, added blank methods, created necessary sensors for the class excluding the cameras. 
 * TO CONSIDER: Method (and corresponding Command + button) for changing turning sensitivity?
 * 
 * 2/11/2017: Inverted the right motorgroup in tankdrive method.
 * 
 * 2/14/2017: Hand-Merged Meredith's PID code into Drivetrain subsystem. Also added MyPIDOutput dummy class.
 * Made a decision to 
 */

public class Drivetrain extends Subsystem {

	// Instance variables. There should only be one instance of Drivetrain, but we are
	// assuming the programmer will not accidently create multiple instances
	
	// Create CANTalons here so we can access them in the future, if we need to
	private CANTalon left_motor1;
	private CANTalon left_motor2;
	private CANTalon left_motor3;
	private CANTalon right_motor1;
	private CANTalon right_motor2;
	private CANTalon right_motor3;
	
	// Motorgroups
	private MotorGroup<CANTalon> left_motorgroup;
	private MotorGroup<CANTalon> right_motorgroup;
	
	// Solenoids
	private DoubleSolenoid left_solenoid;
	private DoubleSolenoid right_solenoid;
	
	// Digital sensors
	private Encoder left_encoder;
	private Encoder right_encoder;
	
	// Analog sensors
	private AnalogGyro  gyro;
	private AnalogInput middle_fr_short_rangefinder;
	// if the plan on using three rangefinders to align to boiler is confirmed
	private AnalogInput left_fr_long_rangefinder;
	private AnalogInput right_fr_long_rangefinder;
	
	// PID 
	private MyPIDOutput myPIDOutputDriving;
	private MyPIDOutput myPIDOutputTurning;
	private PIDController pidControllerDriving;
	private PIDController pidControllerTurning;
	final double pGain = .25, iGain = 1, dGain = 1;
	boolean done = false;
	/* raise P constant until controller oscillates. If oscillation too much, lower constant a bit
	 * raise D constant to damp oscillation, causing it to converge. D also slows controller's approach to setpoint so will need to tweak balance of P and D
	 * if P + D are tuned and it oscillates + converges, but not to correct setpoint, increase I 
	 * = steady-state error - positive, nonzero integral constant will cause controller to correct for it
	 */

	
	// cameras (to be added later)
	
	// Instantiate all of the variables, and add the motors to their respective MotorGroup.
	public Drivetrain() {
		
		// CANTalons
		left_motor1 = new CANTalon(RobotMap.LEFT_MOTOR1_PORT);
		left_motor2 = new CANTalon(RobotMap.LEFT_MOTOR2_PORT);
		left_motor3 = new CANTalon(RobotMap.LEFT_MOTOR3_PORT);
		right_motor1 = new CANTalon(RobotMap.RIGHT_MOTOR1_PORT);
		right_motor2 = new CANTalon(RobotMap.RIGHT_MOTOR2_PORT);
		right_motor3 = new CANTalon(RobotMap.RIGHT_MOTOR3_PORT);
		left_motorgroup = new MotorGroup<CANTalon>(left_motor1, left_motor2, left_motor3);
    	right_motorgroup = new MotorGroup<CANTalon>(right_motor1, right_motor2, right_motor3);
    	
    	// Digital IO
    	left_encoder = new Encoder(RobotMap.LEFT_GEARBOX_ENCODER_A_PORT, RobotMap.LEFT_GEARBOX_ENCODER_B_PORT, false, EncodingType.k4X);
    	right_encoder = new Encoder(RobotMap.RIGHT_GEARBOX_ENCODER_A_PORT, RobotMap.RIGHT_GEARBOX_ENCODER_B_PORT, false, EncodingType.k4X);
    	
    	// Analog IO
    	gyro = new AnalogGyro(RobotMap.GYRO_PORT);
    	middle_fr_short_rangefinder = new AnalogInput(RobotMap.MIDDLE_FR_SHORT_RANGEFINER_PORT);
    	// if the plan on using three rangefinders to align to boiler is confirmed
    	left_fr_long_rangefinder = new AnalogInput(RobotMap.LEFT_FR_LONG_RANGEFINDER_PORT);
    	right_fr_long_rangefinder = new AnalogInput(RobotMap.RIGHT_FR_LONG_RANGEFINDER_PORT);
    
    	// PID-related things
    	gyro.calibrate();
        myPIDOutputDriving = new MyPIDOutput();
        myPIDOutputTurning = new MyPIDOutput();
        pidControllerDriving = new PIDController(pGain, iGain, dGain, left_encoder, myPIDOutputDriving);   // Input are P, I, D, Input , output
		pidControllerTurning = new PIDController(pGain, iGain, dGain, gyro, myPIDOutputTurning);
		
    	// Solenoids
    	left_solenoid = new DoubleSolenoid(RobotMap.L_DRIVETRAIN_SOLENOID_A_PORT, RobotMap.L_DRIVETRAIN_SOLENOID_B_PORT);
    	right_solenoid = new DoubleSolenoid(RobotMap.R_DRIVETRAIN_SOLENOID_A_PORT, RobotMap.R_DRIVETRAIN_SOLENOID_B_PORT);
    	
    	// SmartDashboard things
    	SmartDashboard.putData("PID Controller for Driving", pidControllerDriving);
		SmartDashboard.putData("PID Controller for Turning", pidControllerTurning);
		SmartDashboard.putNumber("PID Output Driving: ", myPIDOutputDriving.get());
	
	}
	
//==FOR TELE-OP DRIVING=======================================================================================
	// For: DefaultDrive Command
    // Sensors: None
    // Description: A basic tank drive method. The two parameters are expected to be within the range -1.0 to 1.0
    // If not, they are limited to be within that range. The parameters will set their respective
	// side of robot to the given value.
	public void tankDrive(double left, double right) {
		if(left >  1.0) left =  1.0;
		if(left < -1.0) left = -1.0;
		if(right >  1.0) right =  1.0;
		if(right < -1.0) right = -1.0;
		left_motorgroup.set( left);
		right_motorgroup.set(right);
	}
	
	// For: DefaultDrive Command
    // Sensors: None
    // Description: A basic arcade drive method. The two parameters are expected to be within the range -1.0 to 1.0
	// If not, they are limited to be within that range. The transitional speed and yaw are combined
	// to be applied to the left motor and right motor. Trans_speed (transitional velocity) will
	// set the robots forward speed, and yaw (angular velocity) will set the robot turning. Having a
	// combination of the two will make the robot drive on an arc.
	public void arcadeDrive(double trans_speed, double yaw) {
		// If yaw is at full, and transitional is at 0, then we want motors to go different speeds.
		// Since motors physically are turned around, then setting both motors to the same speed
		// will have this effect. If the transitional is at full and yaw at 0, then motors need to
		// go the same direction, so one is a minus to cancel the effect of mirrored motors.
    	double fs_speed = yaw - trans_speed;
    	double gs_speed = yaw + trans_speed;
    	
    	// This determines the variable with the greatest magnitude. If the magnitude
    	// is greater than 1.0, than divide each variable by the largest so that
    	// the largest is 1.0 (or -1.0), and that all other variables are
    	// less than that.
    	double max_speed = Math.max(Math.abs(fs_speed), Math.abs(gs_speed)); 
    	if(Math.abs(max_speed) > 1.0) {
    		fs_speed /= max_speed;
    		gs_speed /= max_speed;
    	}
    	
    	left_motorgroup.set(fs_speed);
    	right_motorgroup.set(gs_speed);
    	
    	// Prints encoder distances, used for testing purposes
    	System.out.println(left_encoder.getDistance());
    	System.out.println(right_encoder.getDistance());
    }

//==FOR PID DRIVING========================================================================================
	
	public void setPIDSetpoints(double setpoint) {
		pidControllerDriving.setSetpoint(setpoint);
		pidControllerDriving.enable();
		left_encoder.reset();
		right_encoder.reset();
	}

	public boolean driveStraightWithPID(double desiredMoveDistance) {
		double speedfactor = 0.001;   // This is the "P" factor to scale the error between encoders values to the motor drive bias
		double maxErrorValue = 0.1;   // Limits the control the error has on driving	
		double error = speedfactor*(left_encoder.get() - right_encoder.get()); 
		if (error >= maxErrorValue) error = 0.1;
		if (error <= -maxErrorValue) error = -0.1;
		
		
		pidControllerDriving.setAbsoluteTolerance(100);
		
		arcadeDrive(error, (0.15 * myPIDOutputDriving.get()));
		
		System.out.println("LeftEncoder: " + left_encoder.getDistance() + " RightEncoder: " + right_encoder.getDistance() + " error: "+ error);
		
		done = pidControllerDriving.onTarget();
		
		if (done){
			pidControllerDriving.disable();
			System.out.println("done is true======================");
		
		}
		return done;
	}	
	
	public double getGyroAngle(){
        return gyro.getAngle();
    }
	
    public void resetGyro(double angle){
        gyro.reset();
        pidControllerTurning.setSetpoint(angle);
		pidControllerTurning.enable();
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage
        return gyro.getAngle();
    }
    
    public void turnWithPID(double desiredTurnAngle) {
		System.out.println(String.format("output 1: %5.1f    output 2: %5.1f", myPIDOutputTurning.get(), myPIDOutputTurning.get()));
		arcadeDrive(myPIDOutputTurning.get(), myPIDOutputTurning.get());
		System.out.println(String.format("Left Encoder: %5.1f    Right Encoder: %5.1f    SetPoint:  %5.1f", 
				left_encoder.getDistance(), right_encoder.getDistance(), pidControllerTurning.getSetpoint()));
		System.out.println("AngleTurned:     %5.1f" + gyro.getAngle());
	}

	
//==FOR AUTONOMOUS AND CAMERA DRIVING, AND GEAR SHIFTING===================================================
	
	// For: DriveStraight Command
    // Sensors: left_encoder, right_encoder 
    // Description: will use PID to drive a certain distance with a 0 degree heading, using encoders to 
	// measure how far we have moved from the initial position 
	public boolean driveStraightSetDistance() {
		return false;
	}
	
	// For: TurnWithGyro Command 
	// Sensors: gyro
	// Description: Will use PID to control the gyro to turn to a certain heading
	public boolean turnWithGyro() {
		return false;
	}
	
	// For: TurnWithCamera Command
	// Sensors: Camera, gyro
	// Description: Uses camera to determine robot's placement relative to its surroundings. It will then 
	// turn and adjust robot to stay on target path. Heading is measured in Degrees, RIGHT = POSITIVE
	public boolean turnWithCamera() {
		return false;
	}
	
	// For: DriveWithCamera Command 
	// Sensors: Camera
	// Description: Uses camera to drive the robot along target path and adjust the robot if necessary. Will 
	// utilize the ultrasonic middle_fr_short_rangefinder to determine how far from target position we are and then 
	// call the motors to turn and adjust as needed if the robot drifts. 
	public void driveWithCamera() {
	
	}
	
    // For: AlignToHighGoal Command
    // Sensors: 3 long-range rangefinders
    // Description: Use the 3 rangefinders to get the distance the front two corners and the front of the robot are from 
	// the boiler. If the left and right corners are the same distance (within a certain margin of error) and exceed a certain
	// distance, and if the middle rangefinder exceeds a certain threshold distance, then the robot knows it had reached its 
	// final position.
    public boolean alignToHighGoal() {
    	return false;
    }
    
    // For: AlignToRope Command
    // Sensors: Camera, PID-controlled
    // Description: Will use the camera to determine where rope is relative to the robot. Robot will then 
    // adjust itself to move toward the rope.
    public boolean alignToRope() {
    	return false;
    }
    
	// For: ShiftGears Command
	// Sensors: None
	// Description: Should shift from low to high gear or vice versa to allow the driver to drive with more torque
	// and less speed in low gear, and less torque and more speed in high gear
	public void shiftGears() {
	}
	
//==DEFAULT COMMAND AND MOTOR GROUPS CLASS=================================================================
    public void initDefaultCommand() {
        // Allows for tele-op driving in arcade or tank drive
        setDefaultCommand(new DefaultDriveCommand());
    }
    
    // "Thar be dragons when motors on the same gearbox are set differently" (Scott 2017), so 
    // a MotorGroup will handle setting multiple motors to the same value as if it were one motor.
    private class MotorGroup<T extends SpeedController> {
    	
    	// Create a list of type T, which of type SpeedController. All of the
    	// motors assigned to this instance of MotorGroup will be held in
    	// this list.
    	private List<T> list = new ArrayList<T>();
    	
    	// Add a variable number of SpeedControllers, since the gearbox can either have two or
    	// three motors attached. For every element in parameter list, add it to list.
    	private MotorGroup(T...t) {
    		for(T i : t) {
    			list.add(i);
    		}
    	}
    	
    	// This method calls the set() method for all SpeedControllers in list. This is better to
    	// call because it is impossible to set motors to different values. Other than this, it
    	// acts like a normal set method of SpeedController.
    	private void set(double v) {
			for(T i : list) {
				i.set(v);
			}
		}
    }
}

