package org.usfirst.frc.team1895.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DefaultDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
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

	// Instance variables. There should only be one instance of Drivetrain, but we are
	// assuming the programmer will not accidently create multiple instances
	
	// Create CANTalons here so we can access them in the future, if wee need to
	private CANTalon fuelside_motor1;
	private CANTalon fuelside_motor2;
	private CANTalon fuelside_motor3;
	private CANTalon gearside_motor1;
	private CANTalon gearside_motor2;
	private CANTalon gearside_motor3;
	
	// Motorgroups
	private MotorGroup<CANTalon> fuelside_motorgroup;
	private MotorGroup<CANTalon> gearside_motorgroup;
	
	// Digital sensors
	private Encoder left_gearbox_encoder;
	private Encoder right_gearbox_encoder;
	
	// Analog sensors
	private AnalogGyro  gyro;
	private AnalogInput rangefinder;
	
	// Instanciate all of the variables, and add the motors to their respective
	// MotorGroup.
	public Drivetrain() {
		
		fuelside_motor1 = new CANTalon(RobotMap.LEFT_MOTOR_1_PORT);
		fuelside_motor2 = new CANTalon(RobotMap.LEFT_MOTOR_2_PORT);
		fuelside_motor3 = new CANTalon(RobotMap.LEFT_MOTOR_3_PORT);
		gearside_motor1 = new CANTalon(RobotMap.RIGHT_MOTOR_1_PORT);
		gearside_motor2 = new CANTalon(RobotMap.RIGHT_MOTOR_2_PORT);
		gearside_motor3 = new CANTalon(RobotMap.RIGHT_MOTOR_3_PORT);
		
		fuelside_motorgroup = new MotorGroup<CANTalon>(fuelside_motor1, fuelside_motor2, fuelside_motor3);
    	gearside_motorgroup = new MotorGroup<CANTalon>(gearside_motor1, gearside_motor2, gearside_motor3);
    	
    	fuelside_encoder = new Encoder(RobotMap.FS_GEARBOX_ENCODER_A_PORT, RobotMap.FS_GEARBOX_ENCODER_B_PORT);
    	gearside_encoder = new Encoder(RobotMap.GS_GEARBOX_ENCODER_A_PORT, RobotMap.GS_GEARBOX_ENCODER_B_PORT);
    	
    	rangefinder = new AnalogInput(RobotMap.RANGEFINDER_PORT);
    	gyro = new AnalogGyro(RobotMap.GYRO_PORT);
		
		//TODO: Add the motors created above into MotorGroups, once MotorGroup is implemented.
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	
	// A basic tank drive method. The two parameters are expected to be within the range -1.0 to 1.0
	// If not, they are limited to be within that range. The parameters
	// will set their respective side of robot to the given value.
	public void tankDrive(double fuelside, double gearside) {
		if(fuelside >  1.0) fuelside =  1.0;
		if(fuelside < -1.0) fuelside = -1.0;
		if(gearside >  1.0) gearside =  1.0;
		if(gearside < -1.0) gearside = -1.0;
		fuelside_motorgroup.set(fuelside);
		gearside_motorgroup.set(gearside);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here. This gets run constantly
    	//as long as there are not other Commands that require this subsystem.
        setDefaultCommand(new DefaultDrive());
    }
    
    /**Thar be dragons when motors on the same gearbox are set differently, so a MotorGroup will handle setting multiple
     * motors to the same value as if it were one motor.*/
    private class MotorGroup<T extends SpeedController> {
    	//list of type T
    	private List<T> list = new ArrayList<T>();
    	
    	//constructor
    	private MotorGroup(T...t) {	//so you can add as many motors of type T as needed per gearbox
    		for(T i : t) {			//for the number of motors, to every motor
    			list.add(i);		//add a motor to the arraylist
    		}
    	}
    	
    	private void set(double v) { //sets velocity of each motorgroup
			for(T i : list) {
				i.set(v);			 //ensures they all go the same speed
			}
		}
    }
}

