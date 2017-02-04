package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DefaultDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This subsystem handles the robot's driving. It includes methods used for both tele-op driving and automated driving commands. Specifically the commands:
 * 	- DriveStraight
 * 	- DriveWithCameras
 * 	- SwitchGears
 * 	- TurnWithCameras
 *  - TurnWithGyro
 *  Sensors needed as a result would be: encoders (2), rangefinders, cameras, gyro, and a gear sensor (exact one TBD)
 */

/**
 * Changelog:
 * 
 * 1/27/2017: Added all six motors and a simple tankDrive method. All of this is done
 *            for the MVP, so expect 99% of what is here to change!! 
 *            (Ethan Passmore)
 *
 */

public class Drivetrain extends Subsystem {

	//Instance variables. There should only be one instance of Drivetrain, but we are
	//assuming the programmer will not accidently create multiple instances
	private CANTalon  leftMotor1;
	private CANTalon  leftMotor2;
	private CANTalon  leftMotor3;
	private CANTalon rightMotor1;
	private CANTalon rightMotor2;
	private CANTalon rightMotor3;
	
	public Drivetrain() {
		
		//instanciate the instance variables
		 leftMotor1 = new CANTalon(RobotMap.LEFT_MOTOR_1_PORT);
		 leftMotor2 = new CANTalon(RobotMap.LEFT_MOTOR_2_PORT);
		 leftMotor3 = new CANTalon(RobotMap.LEFT_MOTOR_3_PORT);
		rightMotor1 = new CANTalon(RobotMap.RIGHT_MOTOR_1_PORT);
		rightMotor2 = new CANTalon(RobotMap.RIGHT_MOTOR_2_PORT);
		rightMotor3 = new CANTalon(RobotMap.RIGHT_MOTOR_3_PORT);
		
		//TODO: Add the motors created above into MotorGroups, once MotorGroup is implemented.
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	
	//A basic tank drive method. The two parameters are expected to be within the range -1.0 to 1.0
	//If not, they are limited to be within that range (to be added after MVP) The parameters
	//will set their respective side of robot to the given value.
	public void tankDrive(double left, double right) {
		//TODO: Limit the parameters to be within range [-1.0, 1.0]
		//TODO: replace individual motor.set() calls to that side's respective MotorGoup .set() method
		 leftMotor1.set( left);
		 leftMotor2.set( left);
		 leftMotor3.set( left);
		rightMotor1.set(right);
		rightMotor2.set(right);
		rightMotor3.set(right);
		System.out.printf("1: %f\t2: %f\t3: %f\t\n"
				,rightMotor1.getOutputCurrent(), rightMotor2.getOutputCurrent(), rightMotor3.getOutputCurrent());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here. This gets run constantly
    	//as long as there are not other Commands that require this subsystem.
        setDefaultCommand(new DefaultDrive());
    }
    
    /**Thar be dragons when motors on the same gearbox are set differently, so a MotorGroup will handle setting multiple
     * motors to the same value as if it were one motor.*/
    @SuppressWarnings("unused")
	private class MotorGroup<T extends SpeedController> {
    	//TODO: Have Maddy finish implementing MotorGroup
    }
}

