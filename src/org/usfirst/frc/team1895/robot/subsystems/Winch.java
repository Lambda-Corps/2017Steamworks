package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.climbing.DefaultWinch;

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
 */
public class Winch extends Subsystem {

	private CANTalon winch_motor;
	private Encoder winch_encoder;
	
    public Winch() {
    	winch_motor = new CANTalon(RobotMap.WINCH_MOTOR_PORT);
    	winch_encoder = new Encoder(RobotMap.WINCH_ENCODER_A_PORT, RobotMap.WINCH_ENCODER_B_PORT);
    }
    
    // For: Climb and DefaultWinch Commands
    // Sensors: encoder?
    // Description: For the Climb Command, it will read a current as it climbs, and once the current level 
    // exceeds a certain threshold we will know it has reached the top and the motors will stop. For the
    // DefaultWinch Command, we will just set the motor to zero so it doesn't run unless other commands are called
    public boolean climb() {
    	return false;
    }
    
    // For: CatchRope Command
    // Sensors: encoder?
    // Description: Turn on the winch_motor and set it to a slow speed to make it easier to catch the rope. When the rope
    // is caught, the voltage the motor draws will increase as resistance will increase. Once it exceeds a certain threshold 
    // it will return true, indicating the method to stop as the rope has been caught. Otherwise return false. 
    public boolean catchRope() {
    	return false;
    }

    public void initDefaultCommand() {
    	// sets the motor to zero so it doesn't run unless other commands are called
        setDefaultCommand(new DefaultWinch());
    }
}

