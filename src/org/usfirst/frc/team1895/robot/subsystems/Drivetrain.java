package org.usfirst.frc.team1895.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DefaultDriveCommand;
import org.usfirst.frc.team1895.robot.oi.F310;
import org.usfirst.frc.team1895.robot.subsystems.MyPIDOutput;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Changelog: (Maddy Seputro) Description: Subsystem for This subsystem handles
 * the robot's driving. It includes methods used for both tele-op driving and
 * automated driving commands, and will be used for commands in the Drivetrain
 * Commands Package Will need: - 6 motors (three each side) - 2 encoders, one
 * for left and one for right motorgroups (so 4 digital IO ports) - 2 solenoids
 * - cameras for Vision Tracking - gyro for turning (so one analog port) -
 * rangefinder for aligning to lift or boiler (so one analog port) - gear sensor
 * for determining whether a gear is present in the slot or not (exact sensor
 * TBD) To do still: - Finish methods - Add comments when you're done writing
 * and as you write - Add cameras if you want them - Test Added: Moved
 * alignToHighGoal method from shooter subsystem to drivetrain. Also moved the
 * alignToRope method from the winch subsystem to drivetrain. Standardized
 * comments, added blank methods, created necessary sensors for the class
 * excluding the cameras. TO CONSIDER: Method (and corresponding Command +
 * button) for changing turning sensitivity?
 * 
 * 2/11/2017: Inverted the right motorgroup in tankdrive method.
 * 
 * 2/14/2017: Hand-Merged Meredith's PID code into Drivetrain subsystem. Also
 * added MyPIDOutput dummy class.
 * 
 * 2/15/2017: Deleted AlignToRope Command and method from this subsystem.
 * 
 * 2/18/2017: Hand-merged Meredith and Zach's code for driving and turning with
 * PIDs and aligning to peg, excludinng their HangGear methods and commands.
 * Also fixed a few errors in their methods after testing. Also added code for
 * setting ramp rate of motors, but it is currently commented out. The solenoids
 * are also commented out in every subsystem. Lastly, code to read the NAVX
 * board was added. More finetuning needs to be done for the PIDs and ramp-rate
 * in the future.
 * 
 */

public class Drivetrain extends Subsystem {

	// Instance variables. There should only be one instance of Drivetrain, but
	// we are
	// assuming the programmer will not accidently create multiple instances

	// Create CANTalons here so we can access them in the future, if we need to
	private CANTalon left_motor1;
	private CANTalon left_motor2;
	// private CANTalon left_motor3;
	private CANTalon right_motor1;
	private CANTalon right_motor2;
	// private CANTalon right_motor3;

	private static final double TALON_RAMP_RATE = 48.0;

	// The two motors mounted as a mirror to one another do not output the
	// exact same force. This value will modify the the dominant side to
	// help the robot drive straight
	private static final double TANK_DRIVE_SCALAR = .94;

	// Motorgroups
	private MotorGroup<CANTalon> left_motorgroup;
	private MotorGroup<CANTalon> right_motorgroup;

	// Solenoids
	// private DoubleSolenoid left_solenoid;
	// private DoubleSolenoid right_solenoid;

	// Digital sensors
	private Encoder left_encoder;
	private Encoder right_encoder;

	// Analog sensors
	AHRS ahrs;
	private AnalogGyro gyro;
	private AnalogInput middle_fr_short_rangefinder;
	// if the plan on using three rangefinders to align to boiler is confirmed
	private AnalogInput left_fr_long_rangefinder;
	private AnalogInput right_fr_long_rangefinder;

	// PID
	private MyPIDOutput myPIDOutputDriving;
	private MyPIDOutput myPIDOutputTurning;
	private PIDController pidControllerDriving;
	private PIDController pidControllerTurning;

	// final double pGainDriv = .00075, iGainDriv = 0, dGainDriv = -.0015;
	//final double pGainDriv = .1, iGainDriv = 0, dGainDriv = -.01; //FOR LOWRIDER
	final double pGainDriv = .1, iGainDriv = 0, dGainDriv = -.01; //FOR REAL ROBOT
	final double pGainTurn = .025, iGainTurn = 0, dGainTurn = -.005; // d
																		// smaller
																		// =
																		// positive
	boolean pid_done = false;
	int printCounter = 0;
	/*
	 * raise P constant until controller oscillates. If oscillation too much,
	 * lower constant a bit raise D constant to damp oscillation, causing it to
	 * converge. D also slows controller's approach to setpoint so will need to
	 * tweak balance of P and D if P + D are tuned and it oscillates +
	 * converges, but not to correct setpoint, increase I = steady-state error -
	 * positive, nonzero integral constant will cause controller to correct for
	 * it
	 */

	/*
	 * For the swerve into peg comman we need this varaibles ratio-difference in
	 * speeds of wheels to make a gradual turn angletoPeg-angle calculated bythe
	 * camera convereted to radians setangleforgyro-setpoint for gyro
	 * bFirstcall_to_Swerve-flag to see if it is the first time through
	 */
	double angletoPeg2;
	double setangleforgyro;
	double ratio;
	boolean bFirstcall_to_Swerve;

	// cameras (to be added later)
	boolean hitThreshold = false;

	// Gear Shifting
	private DoubleSolenoid transmissionSolenoid;
	private boolean manualOverride = false;
	private int transmission_state = 0;
	private boolean inHigh = false;
	private int transmissionTimer = 0;

	private boolean teleopEnabled = false;

	// for encoder driving
	private boolean firsttimeusingEncoder;
	double desiredDrivingAngle = 0;

	// Instantiate all of the variables, and add the motors to their respective
	// MotorGroup.
	public Drivetrain() {

		// CANTalons
		left_motor1 = new CANTalon(RobotMap.LEFT_MOTOR1_PORT);
		left_motor2 = new CANTalon(RobotMap.LEFT_MOTOR2_PORT);
		// left_motor3 = new CANTalon(RobotMap.LEFT_MOTOR3_PORT);
		right_motor1 = new CANTalon(RobotMap.RIGHT_MOTOR1_PORT);
		right_motor2 = new CANTalon(RobotMap.RIGHT_MOTOR2_PORT);
		// right_motor3 = new CANTalon(RobotMap.RIGHT_MOTOR3_PORT);
		// left_motorgroup = new MotorGroup<CANTalon>(left_motor1, left_motor2,
		// left_motor3);
		// right_motorgroup = new MotorGroup<CANTalon>(right_motor1,
		// right_motor2, right_motor3);
		left_motorgroup = new MotorGroup<CANTalon>(left_motor1, left_motor2);
		right_motorgroup = new MotorGroup<CANTalon>(right_motor1, right_motor2);

		// left_motor1.setVoltageRampRate(TALON_RAMP_RATE);
		// left_motor2.setVoltageRampRate(TALON_RAMP_RATE);
		// left_motor3.setVoltageRampRate(TALON_RAMP_RATE);
		// right_motor1.setVoltageRampRate(TALON_RAMP_RATE);
		// right_motor2.setVoltageRampRate(TALON_RAMP_RATE);
		// right_motor3.setVoltageRampRate(TALON_RAMP_RATE);

		// Digital IO
		left_encoder = new Encoder(RobotMap.LEFT_GEARBOX_ENCODER_A_PORT, RobotMap.LEFT_GEARBOX_ENCODER_B_PORT, true,
				EncodingType.k4X);
		right_encoder = new Encoder(RobotMap.RIGHT_GEARBOX_ENCODER_A_PORT, RobotMap.RIGHT_GEARBOX_ENCODER_B_PORT, true,
				EncodingType.k4X);

		// Analog IO
		gyro = new AnalogGyro(RobotMap.GYRO_PORT);
		 try {
		 /* Communicate w/navX-MXP via the MXP SPI Bus. */
		 /* Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or
		 SerialPort.Port.kUSB */
		 /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/
		 for details. */
		 ahrs = new AHRS(SPI.Port.kMXP);
		 } catch (RuntimeException ex ) {
		 DriverStation.reportError("Error instantiating navX-MXP: " +
		 ex.getMessage(), true);
		 }
		middle_fr_short_rangefinder = new AnalogInput(RobotMap.MIDDLE_FR_SHORT_RANGEFINER_PORT);
		// if the plan on using three rangefinders to align to boiler is
		// confirmed
		left_fr_long_rangefinder = new AnalogInput(RobotMap.LEFT_FR_LONG_RANGEFINDER_PORT);
		right_fr_long_rangefinder = new AnalogInput(RobotMap.RIGHT_FR_LONG_RANGEFINDER_PORT);

		// PID-related things
		gyro.calibrate();
		myPIDOutputDriving = new MyPIDOutput();
		myPIDOutputTurning = new MyPIDOutput();
		pidControllerDriving = new PIDController(pGainDriv, iGainTurn, dGainDriv, left_encoder, myPIDOutputDriving); // Input
																												// output
		pidControllerTurning = new PIDController(pGainTurn, iGainTurn, dGainTurn, gyro, myPIDOutputTurning);
		// pidControllerTurning = new PIDController(pGainTurn, iGainTurn,
		// dGainTurn, ahrs, myPIDOutputTurning);

		// Solenoids
		// left_solenoid = new
		// DoubleSolenoid(RobotMap.L_DRIVETRAIN_SOLENOID_A_PORT,
		// RobotMap.L_DRIVETRAIN_SOLENOID_B_PORT);
		// right_solenoid = new
		// DoubleSolenoid(RobotMap.R_DRIVETRAIN_SOLENOID_A_PORT,
		// RobotMap.R_DRIVETRAIN_SOLENOID_B_PORT);

		left_encoder.setDistancePerPulse(0.0225);
		right_encoder.setDistancePerPulse(0.0225);

		// For Swerving
		angletoPeg2 = 0.0;
		setangleforgyro = 0.0;
		ratio = 0.0;
		bFirstcall_to_Swerve = true;

		transmissionSolenoid = new DoubleSolenoid(RobotMap.DRIVETRAIN_SOLENOID_A_PORT,
				RobotMap.DRIVETRAIN_SOLENOID_B_PORT);
		transmissionSolenoid.set(DoubleSolenoid.Value.kReverse);

		LiveWindow.addActuator("Drive Train", "Left Encoder", left_encoder);
		LiveWindow.addActuator("Drive Train", "Right Encoder", right_encoder);
		LiveWindow.addActuator("Drive TrainL", "Left First Motor", left_motor1);
		LiveWindow.addActuator("Drive TrainR", "Right First Motor", right_motor1);
		LiveWindow.addActuator("Drive TrainL", "Left Center Motor", left_motor2);
		LiveWindow.addActuator("Drive TrainR", "Right Center Motor", right_motor2);

		// LiveWindow.addActuator("Drive TrainL", "Left Third Motor",
		// left_motor3);
		// LiveWindow.addActuator("Drive TrainR", "Right Third Motor",
		// right_motor3);

		// for encoder driving
		firsttimeusingEncoder = true;

	}

	public double getVoltage() {
		return middle_fr_short_rangefinder.getAverageVoltage();
	}

	// ==FOR TELE-OP
	// DRIVING=======================================================================================
	// For: DefaultDrive Command
	// Sensors: None
	// Description: A basic tank drive method. The two parameters are expected
	// to be within the range -1.0 to 1.0
	// If not, they are limited to be within that range. The parameters will set
	// their respective
	// side of robot to the given value.
	public void tankDrive(double left, double right) {
		if (left > 1.0)
			left = 1.0;
		if (left < -1.0)
			left = -1.0;
		if (right > 1.0)
			right = 1.0;
		if (right < -1.0)
			right = -1.0;
		left_motorgroup.set(left);
		right_motorgroup.set(-right);

		// Check to see if gear shifting is necessary. if it is, then shift
		// shiftGears();
	}

	// For: DefaultDrive Command
	// Sensors: None
	// Description: A basic arcade drive method. The two parameters are expected
	// to be within the range -1.0 to 1.0
	// If not, they are limited to be within that range. The transitional speed
	// and yaw are combined
	// to be applied to the left motor and right motor. Trans_speed
	// (transitional velocity) will
	// set the robot's forward speed, and yaw (angular velocity) will set the
	// robot turning. Having a
	// combination of the two will make the robot drive on an arc.
	public void arcadeDrive(double trans_speed, double yaw) {
		// Currently, when trying to turn, the left and right turning functions
		// are backward, so I'm
		// going to invert them.
		yaw *= -1.0;
		trans_speed *= -1.0;
		// If yaw is at full, and transitional is at 0, then we want motors to
		// go different speeds.
		// Since motors physically are turned around, then setting both motors
		// to the same speed
		// will have this effect. If the transitional is at full and yaw at 0,
		// then motors need to
		// go the same direction, so one is a minus to cancel the effect of
		// mirrored motors.
		double left_speed = yaw - trans_speed;
		double right_speed = yaw + trans_speed;

		// This determines the variable with the greatest magnitude. If the
		// magnitude
		// is greater than 1.0, than divide each variable by the largest so that
		// the largest is 1.0 (or -1.0), and that all other variables are
		// less than that.
		double max_speed = Math.max(Math.abs(left_speed), Math.abs(right_speed));
		if (Math.abs(max_speed) > 1.0) {
			left_speed /= max_speed;
			right_speed /= max_speed;
		}
		left_motorgroup.set(left_speed);
		right_motorgroup.set(right_speed);
		// left_motor2.set(left_speed);
		// right_motor1.set(right_speed);

		// Check to see if gear shifting is necessary. if it is, then shift
		//shiftGears();
	}

	// ==FOR PID
	// DRIVING========================================================================================

	// to reset encoders and set the PID setpoints
	public void setPIDSetpoints(double setpoint) {
		left_encoder.reset();
		right_encoder.reset();
		pidControllerDriving.setSetpoint(setpoint);
		pidControllerDriving.enable();
	}

	public boolean driveStraightWithPID(double desiredMoveDistance) {
		double speedfactor = 0.1; // This is the "P" factor to scale the error
									// between encoders values to the motor
									// drive bias
		double maxErrorValue = 0.005; // Limits the control the error has on
									// driving
		double error = speedfactor * (left_encoder.getDistance() - right_encoder.getDistance());
		if (error >= maxErrorValue)
			error = maxErrorValue;
		if (error <= -maxErrorValue)
			error = -maxErrorValue;

		pidControllerDriving.setAbsoluteTolerance(1);
		if(++printCounter % 10 == 0){
		}

		double pidOutput = myPIDOutputDriving.get();
//		System.out.println("pidOutput " + pidOutput);
		if(Double.isNaN(pidOutput)){
		}
		else{
				arcadeDrive(-0.8*(pidOutput), error);
		}

		pid_done = pidControllerDriving.onTarget();

		if (pid_done) {
			pidControllerDriving.disable();
			printCounter = 0;
		}
		return pid_done;
	}

	public double getGyroAngle() {
		return ahrs.getAngle();
	}

	public void resetGyro() {
		//gyro.reset();
		ahrs.zeroYaw();
	}
 
	public void setUpPIDTurning(double angle) {
		pidControllerTurning.setSetpoint(angle);
		pidControllerTurning.enable();
	}

	protected double returnPIDInput() {
		// Return your input value for the PID loop
		// e.g. a sensor, like a potentiometer:
		// yourPot.getAverageVoltage() / kYourMaxVoltage
		// return gyro.getAngle();
		return ahrs.getAngle();
	}

	public boolean turnWithPID(double desiredTurnAngle) {

		pidControllerTurning.setAbsoluteTolerance(2.0);

		// basicArcadeDrive uses x, y inputs so it should be 0 for y and
		// whatever the PIDcontroller calculates as x
		double pidOuput = myPIDOutputTurning.get();

		if (Double.isNaN(pidOuput)){
		}
		else{
			arcadeDrive(0.0, pidOuput);
		}

		printCounter++;

		// Every tenth iteration, print to the log
		if (printCounter % 10 == 0)  {		
		}

		pid_done = pidControllerTurning.onTarget();

		if (pid_done) {
			pidControllerTurning.disable();
			printCounter = 0;
		}

		return pid_done;
	}

	// ==FOR AUTONOMOUS AND CAMERA DRIVING, AND GEAR
	// SHIFTING===================================================

	// for finding the distance from the middle_fr_short_rangefinder to the
	// airship
	public double fineDistanceFinder() {
		double outputValue = middle_fr_short_rangefinder.getAverageVoltage();
		if (outputValue > 2.4 || outputValue < 0.4) { // code currently only
														// accurate from 0.4-2.4
														// volts
			return 25;
			// TODO: Add code to handle that -1 so the robot can act accordingly
		}
		double voltage = Math.pow(outputValue, -1.16);
		double coefficient = 10.298;
		double d = voltage * coefficient;
		return d;
	}

	public double distancetoMove(double distancefromPeg, double angletoPeg) {
		double distancetoMove = distancefromPeg * Math.tan(angletoPeg);
		return distancetoMove;
	}

	public double getAngleforgyro(double distancefromPeg, double angletoPeg) {
		double diameterwheeltowheel = 30.0;
		double distancetoMove = distancetoMove(distancefromPeg, angletoPeg);
		double setangleforgyro = Math.acos(distancetoMove / diameterwheeltowheel);
		return setangleforgyro;
	}

	public double getRatio(double distancefromPeg, double angletoPeg) {// distance
																		// in
																		// inches
		double goalDistance = 10;// make this into a class variable
		double distancetoMove = distancetoMove(distancefromPeg, angletoPeg);
		double setangleforgyro = getAngleforgyro(distancefromPeg, angletoPeg);
		double distanceforturn = (distancefromPeg - goalDistance) / 2;
		double distanceoneturnmakes = (distancetoMove * Math.tan(setangleforgyro));
		double ratio = (distanceforturn / distanceoneturnmakes);
		return ratio;
	}

	public boolean swerveIntoPeg1(double distancefromPeg, double angletoPeg) {
		if (bFirstcall_to_Swerve) {
			angletoPeg2 = Math.toRadians(angletoPeg);
			setangleforgyro = getAngleforgyro(distancefromPeg, angletoPeg2);
			ratio = getRatio(distancefromPeg, angletoPeg2);
			resetGyro();
			bFirstcall_to_Swerve = false;
		}
		// does gyro equal the angle if it does reverse
		// System.out.println("setangleforgyro: " + setangleforgyro + "\t getangle: " + ahrs.getAngle());
		double left_speed = 0.0;
		double right_speed = 0.0;
		if (angletoPeg > 0) {
			left_speed = (1 / (ratio));
			right_speed = (1 / (ratio - 1));

			if (ahrs.getAngle() > setangleforgyro) {
				resetGyro();
				tankDrive(0, 0);
				return true;
			}
		} else if (angletoPeg < 0) {
			left_speed = (1 / (ratio - 1));
			right_speed = (1 / (ratio));

			if (ahrs.getAngle() < setangleforgyro) {
				resetGyro();
				tankDrive(0, 0);
				return true;
			}
		}

//		System.out.println("leftspeed: " + left_speed + "rightspeed: " + right_speed);
		tankDrive(left_speed / 4, right_speed / 4);
		return false;
	}

	public boolean swervetoPeg2(double distancefromPeg, double angletoPeg) {
		double angletoPeg2 = Math.toRadians(angletoPeg);
		double setangleforgyro = getAngleforgyro(distancefromPeg, angletoPeg2);
		double ratio = getRatio(distancefromPeg, angletoPeg2);
		if (angletoPeg > 0) {
			left_motorgroup.set(1 / (ratio - 1));
			right_motorgroup.set(1 / (ratio));
			if (gyro.getAngle() < (-setangleforgyro)) {
				gyro.reset();
				gyro.calibrate();
				return true;
			}
		}
		if (angletoPeg < 0) {
			left_motorgroup.set(1 / (ratio));
			right_motorgroup.set(1 / (ratio - 1));
			if (gyro.getAngle() > (-setangleforgyro)) {
				gyro.reset();
				gyro.calibrate();
				return true;
			}
		}
		return false;
	}

	// For: AlignToHighGoal Command
	// Sensors: 3 long-range rangefinders
	// Description: Use the 3 rangefinders to get the distance the front two
	// corners and the front of the robot are from
	// the boiler. If the left and right corners are the same distance (within a
	// certain margin of error) and exceed a certain
	// distance, and if the middle rangefinder exceeds a certain threshold
	// distance, then the robot knows it had reached its
	// final position.
	public boolean alignToHighGoal() {
		return false;
	}

	public boolean driveRangeFinderDistance(double goaldistance, double speed) {
		double left_speed = speed * TANK_DRIVE_SCALAR;
		double right_speed = speed;
		if (fineDistanceFinder() <= (goaldistance)) {// if the robot crossed the
														// goal distance +
														// buffer then the code
														// will stop
			tankDrive(0, 0);
			return true;
		} else {// if it hasn't crossed it will run at a determined speed
			tankDrive(left_speed, right_speed);
			return false;
		}
	}

	public boolean testdriveRangeFinderDistance(double goaldistance, double speed, double scalar) {
		double left_speed = speed * scalar;
		double right_speed = speed;
		if (fineDistanceFinder() <= (goaldistance)) {// if the robot crossed the
														// goal distance +
														// buffer then the code
														// will stop
			tankDrive(0, 0);
			return true;
		} else {// if it hasn't crossed it will run at a determined speed
			tankDrive(left_speed, right_speed);
			return false;
		}
	}

	public boolean driveToPeg(double heading, double highSpeed, double lowSpeed, double neutralSpeed) {
		double distanceToGo = fineDistanceFinder();
		
		if (distanceToGo < 14) {
			tankDrive(0.0, 0.0);
			return true;
		} else {
			if (heading < 380) {
				tankDrive(lowSpeed, highSpeed);
				SmartDashboard.putString("Turning left with: ", "lowSpeed: " + lowSpeed + " highSpeed: " + highSpeed);
			} else if (heading >= 430) {
				tankDrive(highSpeed, lowSpeed);
				SmartDashboard.putString("Turning right with: ", "lowSpeed: " + lowSpeed + " highSpeed: " + highSpeed);
			} else {
				tankDrive(neutralSpeed, neutralSpeed);
			}
			return false;
		}
	}

	public boolean testDriveToPeg(double heading, double desiredHeading, double lowSpeed, double highSpeed, double neutralSpeed) {
		double distanceToGo = fineDistanceFinder();
		
		if (distanceToGo < 16) {
			tankDrive(0.0, 0.0);
			return true;
		} else {
			if (heading < desiredHeading - 30) {
				tankDrive(lowSpeed, highSpeed);
				//SmartDashboard.putString("Turning left with: ", "lowSpeed: " + lowSpeed + " highSpeed: " + highSpeed);
			} else if (heading >= desiredHeading + 20) {
				tankDrive(highSpeed, lowSpeed);
				//SmartDashboard.putString("Turning right with: ", "lowSpeed: " + lowSpeed + " highSpeed: " + highSpeed);
			} else {
				tankDrive(neutralSpeed, neutralSpeed);
			}
			//SmartDashboard.putNumber("desiredHeading read: ", desiredHeading);
			return false;
		}

	}
	
    
	// Sensors: Encoders
	/**
	 * This method should check to see if the left or right encoder read a value
	 * o greater than 4 ft/s. If so, shift nto high gear. This will avoid the
	 * large acceleration time required for high gear. if less than 3.7 ft/s,
	 * shift back into low gear.
	 */
	/*public void shiftGears() {
		// Disabling shifting for the rest of competiton
		// If Teleop is not enabled, skip. Autonomos doesn't work with shifting
		if (!teleopEnabled)
			return;

		// If manual control is enabled, then shift accordingly and skip the
		// rest
		if (manualOverride) {
			shiftHighGear(inHigh);
			return;
		}

		// find the fastest encoder
		double max = Math.max(Math.abs(left_encoder.getRate()), Math.abs(right_encoder.getRate()));

		// This takes care of the logic of when we should shift
		switch (transmission_state) {

		case 0:

			// If we are over a certain speed, shift into high gear. If not,
			// stay low
			// inches per second
			if (max > 60.0) {
				shiftHighGear(true);
				transmission_state = 1;
			} else {
				shiftHighGear(false);
			}

			break;

		case 1:

			// If we are over an even higher speed, we are definitely in high gear
			if (max > 70.0) {
				transmission_state = 2;
				transmissionTimer = 0;
				break;
			}

			// If timer runs out and we are slow, switch back into low gear as a
			// failsafe
			if (transmissionTimer > 10 && max < 20) {
				transmission_state = 0;
				shiftHighGear(false);
				break;
			}
			transmissionTimer++;

			break;
		case 2: // In high gear, if need to switch to low
			if (max < 12.0) {
				// changed the original 42.0 in/sec to 12.0
				shiftHighGear(false);
				transmission_state = 0;
			}
			break;
		}
	} */

	/**
	 * 
	 * @param intoHigh
	 */
	public void shiftHighGear(boolean intoHigh) {
		if (intoHigh) {
			transmissionSolenoid.set(DoubleSolenoid.Value.kForward);
		} else {
			transmissionSolenoid.set(DoubleSolenoid.Value.kReverse);
		}
	}

	/**
	 * This method is used to enable or disable manual control. If the first
	 * param is false, the second param has no effect.
	 * 
	 * @param manualOverride
	 *            - enable or disable manual control
	 * @param gearState
	 *            - true: high gear, false: low gear
	 */
	public void manualOverride(boolean manualOverride, boolean gearState) { // low
																			// ==
																			// false,
																			// high
																			// ==
																			// true
		this.manualOverride = manualOverride;
		inHigh = gearState;
	}

	/**
	 * 
	 * 
	 * @param brake
	 *            - whether to set to brake (true) mode or coast (false)
	 */
	public void setBrake(boolean brake) {
		left_motorgroup.enableBrake(brake);
		right_motorgroup.enableBrake(brake);
	}

	public void setRobotTeleop(boolean teleopEnabled) {
		this.teleopEnabled = teleopEnabled;
	}

	// ==DEFAULT COMMAND AND MOTOR GROUPS
	// CLASS=================================================================
	public void initDefaultCommand() {
		// Allows for tele-op driving in arcade or tank drive
		setDefaultCommand(new DefaultDriveCommand());
	}

	// ==METHODS FOR ACCESSING VALUES AND TESTING
	// THINGS========================================================
	public void makeNewPidDriving(double p, double i, double d) {
		myPIDOutputDriving = new MyPIDOutput();
		pidControllerDriving = new PIDController(p, i, d, left_encoder, myPIDOutputDriving);
	}

	public void resetPIDOutput() {
		myPIDOutputDriving.reset();
	}
	public void makeNewPidTurning(double p, double i, double d) {
		myPIDOutputTurning = new MyPIDOutput();
		pidControllerTurning = new PIDController(p, i, d, ahrs, myPIDOutputTurning);
	}

	public void resetEncoders() {
		left_encoder.reset();
		right_encoder.reset();
	}

	public double getLEncoderValues() {
		return left_encoder.getDistance();
	}

	public double getREncoderValues() {
		return right_encoder.getDistance();
	}

	// public double lMCurrent() {
	// return left_motor1.getOutputCurrent();
	// }
	//
	// public double rMCurrent() {
	// return right_motor1.getOutputCurrent();
	// }

	public double getAngle() {
		return ahrs.getAngle();
	}

	 public void printTelemetry() {
		 System.out.println("Left encoder: " + left_encoder.getDistance());
		 System.out.println("Right encoderL " + right_encoder.getDistance());
		
		 System.out.println("\nLM1_cur: " + left_motor1.getOutputCurrent());
		 System.out.println("LM2_cur: " + left_motor2.getOutputCurrent());
		
		 System.out.println("RM1_cur: " + right_motor1.getOutputCurrent());
		 System.out.println("RM2_cur: " + right_motor2.getOutputCurrent());
		
		 System.out.println("Joy Y: " + Robot.oi.gamepad.getAxis(F310.RY));
		 System.out.println("Joy X" + Robot.oi.gamepad.getAxis(F310.LX));
	 }

	public boolean driveWithEncoders(double v, double distancetoTravel) {
		// sets initial return value as false
		boolean returnValue = false;

		double l_encoderDistance = left_encoder.getDistance();
		double r_encoderDistance = right_encoder.getDistance();

		if (distancetoTravel > l_encoderDistance && distancetoTravel > r_encoderDistance) {
			tankDrive(v, v);
		} else {
			tankDrive(0.0, 0.0);
			returnValue = true;
		}

		// return boolean
		return returnValue;

	}

	public boolean turnWithGyroNP(double turnAngle, double s) {
		double currentAngle = ahrs.getAngle();
		double desiredAngle = turnAngle;
		double tolerance = 2;
		double difference = 0;
		// turning right
		if (turnAngle >= 0 && currentAngle < turnAngle) {
			difference = turnAngle - currentAngle;
			tankDrive(-s, s);
		}
		// turning left
		else {
			difference = -turnAngle - -currentAngle;
			tankDrive(s, -s);
		}
		
		if (difference <= tolerance) {
			tankDrive(0.0, 0.0);
			return true;
		}
		return false;
	}

	// "Thar be dragons when motors on the same gearbox are set differently"
	// (Scott 2017), so
	// a MotorGroup will handle setting multiple motors to the same value as if
	// it were one motor.
	private class MotorGroup<T extends SpeedController> {

		// Create a list of type T, which of type SpeedController. All of the
		// motors assigned to this instance of MotorGroup will be held in
		// this list.
		private List<T> list = new ArrayList<T>();

		// Add a variable number of SpeedControllers, since the gearbox can
		// either have two or
		// three motors attached. For every element in parameter list, add it to
		// list.
		private MotorGroup(T... t) {
			for (T i : t) {
				list.add(i);
			}
		}

		// This method calls the set() method for all SpeedControllers in list.
		// This is better to
		// call because it is impossible to set motors to different values.
		// Other than this, it
		// acts like a normal set method of SpeedController.
		private void set(double v) {
			for (T i : list) {
				i.set(v);
			}
		}

		private void enableBrake(boolean toBrake) {
			for (T i : list) {
				((CANTalon) i).enableBrakeMode(toBrake);
			}
		}
	}
}
