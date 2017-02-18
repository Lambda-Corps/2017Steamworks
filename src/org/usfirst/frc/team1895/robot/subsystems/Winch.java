package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.climbing.DefaultManuallyClimb;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Changelog: 
 * 2/4/2017 (Maddy Seputro)
 * 		Description: Subsystem for the Winch, which will be used in the Climbing
 * 		Command package.
 * 			Will need:
 * 			- motor
 * 			- encoder (so two digital IO ports)
 * 			- cameras?
 * 		To do still:
 * 			- Finish methods climb, and moveWinch
 *  		- Add comments when you're done writing and as you write
 * 			- Add cameras if you want them
 * 			- Test
 * Added: Moved alignToRope command from here to the drivetrain subsystem. Standardized comments, added default command, motors, and encoders. 
 * Renamed moveWinch method to catchRope method.
 * 2/8/2017 (Maddy Seputro) 
 * Added: climb() and catchRope() methods coded but not tested yet. Both 75% complete. Threshold values still TBD.
 * 2/15/2017 
 * Added method manualClimbing() to manually moving the winch. Changed default command to DefaultManuallyClimb. Deleted original DefaultWinch Command. 
 */
public class Winch extends Subsystem {

	private CANTalon winch_motor;
	private Encoder winch_encoder;
	private int cl_counter;
	
    public Winch() {
    	winch_motor = new CANTalon(RobotMap.WINCH_MOTOR_PORT);
    	winch_encoder = new Encoder(RobotMap.WINCH_ENCODER_A_PORT, RobotMap.WINCH_ENCODER_B_PORT);
    	cl_counter = 0;
    }
//==MANUAL MOVEMENT=================================================================================================================
    
    // For: ManuallyClimb Command
    // Sensors: None
    // Description: Takes a given velocity and sets the motor to that velocity after ensuring it does not 
    // exceed 1.0 or -1.0. 
    public void manualClimbing(double velocity) {
    	if(velocity > 1.0) velocity = 1.0;
    	if(velocity < 0.0) velocity = 0.0;
    	winch_motor.set(velocity); 
    }
    
//==AUTONOMOUS/AUTOMATED MOVEMENT====================================================================================================
    
    // For: Climb and DefaultWinch Commands
    // Sensors: encoder
    // Description: For the Climb Command, it will read a current as it climbs, and once the current level 
    // exceeds a certain threshold we will know it has reached the top and the motors will stop. For the
    // DefaultWinch Command, we will just set the motor to zero so it doesn't run unless other commands are called
    public boolean climb(double speed) {
    	winch_encoder.reset();
    	double current = winch_motor.getOutputCurrent();
    	double distance = winch_encoder.getDistance();
    	final double touchpad_reached = 0.5; 
    	//0.5 needs to be changed to the current drawn when the robot first touches the touchpad
    	
    	//if the motor hasn't reached the touchpad yet, continue at max speed and increment counter
    	winch_motor.set(speed);
    	cl_counter++;
    	//so that the counter will print the current and encoder values only 5 times a second
    	if(cl_counter == 10) {
    		System.out.println("Current: " + current);
    		System.out.println("Encoder distance: " + distance);
    		cl_counter = 0;
    	}
    	//winch motors are set to zero once the touchpad has been reached
    	if(current > touchpad_reached) {
    		winch_motor.set(0.0);
    		return true;
    	}
    	return false;
    }
    
    // For: CatchRope Command
    // Sensors: encoder
    // Description: Turn on the winch_motor and set it to a slow speed to make it easier to catch the rope. When the rope
    // is caught, the voltage the motor draws will increase as resistance will increase. Once it exceeds a certain threshold 
    // it will return true, indicating the method to stop as the rope has been caught. Otherwise return false. 
    public boolean catchRope(double speed) {
    	winch_encoder.reset();
    	double current = winch_motor.getOutputCurrent();
    	double distance = winch_encoder.getDistance();
    	final double ropeCaught = 0.5; //0.5 must be changed to threshold where rope has been caught
    	int counter = 0;
    	int second = 50;
    	
    	winch_motor.set(speed);
    	cl_counter++;
    	//for printing values, will print the current and encoder values only 5 times a second
    	if(cl_counter == 10) {
    		System.out.println("Current: " + current);
    		System.out.println("Encoder distance: " + distance);
    		cl_counter = 0;
    	}
    	//winch motors are set to zero once the rope has been caught
    	if(current > ropeCaught && counter > second) {
    		winch_motor.set(0.0);
    		return true;
    	}
    	else if(counter <= second ) {
    		counter++;
    		return false;
    	}
    	return false;
    }

    public void initDefaultCommand() {
    	// sets the motor to zero so it doesn't run unless other commands are called
        setDefaultCommand(new DefaultManuallyClimb());
    }
}

