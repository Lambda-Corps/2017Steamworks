package org.usfirst.frc.team1895.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DefaultDriveCommand;

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
 * Sensors needed as a result would be: encoders (2), rangefinders, cameras, gyro, and a gear sensor (exact one TBD)
 * Right now, MotorGroups are implemented and variables are renamed. The next step is to fill
 * the blank methods with functionality and use the sensors.
 */

public class Drivetrain extends Subsystem {

	// Instance variables. There should only be one instance of Drivetrain, but we are
	// assuming the programmer will not accidently create multiple instances
	
	// Create CANTalons here so we can access them in the future, if we need to
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
	private Encoder fuelside_encoder;
	private Encoder gearside_encoder;
	
	// Analog sensors
	private AnalogGyro  gyro;
	private AnalogInput rangefinder;
	
	// Instanciate all of the variables, and add the motors to their respective
	// MotorGroup.
	public Drivetrain() {
		
		fuelside_motor1 = new CANTalon(RobotMap.FS_MOTOR1_PORT);
		fuelside_motor2 = new CANTalon(RobotMap.FS_MOTOR2_PORT);
		fuelside_motor3 = new CANTalon(RobotMap.FS_MOTOR3_PORT);
		gearside_motor1 = new CANTalon(RobotMap.GS_MOTOR1_PORT);
		gearside_motor2 = new CANTalon(RobotMap.GS_MOTOR2_PORT);
		gearside_motor3 = new CANTalon(RobotMap.GS_MOTOR3_PORT);
		fuelside_motorgroup = new MotorGroup<CANTalon>(fuelside_motor1, fuelside_motor2, fuelside_motor3);
    	gearside_motorgroup = new MotorGroup<CANTalon>(gearside_motor1, gearside_motor2, gearside_motor3);
    	
    	fuelside_encoder = new Encoder(RobotMap.FS_GEARBOX_ENCODER_A_PORT, RobotMap.FS_GEARBOX_ENCODER_B_PORT);
    	gearside_encoder = new Encoder(RobotMap.GS_GEARBOX_ENCODER_A_PORT, RobotMap.GS_GEARBOX_ENCODER_B_PORT);
    	
    	rangefinder = new AnalogInput(RobotMap.RANGEFINDER_PORT);
    	gyro = new AnalogGyro(RobotMap.GYRO_PORT);
	}
	
	// A basic tank drive method. The two parameters are expected to be within the range -1.0 to 1.0
	// If not, they are limited to be within that range. The parameters will set their respective
	// side of robot to the given value.
	public void tankDrive(double fuelside, double gearside) {
		if(fuelside >  1.0) fuelside =  1.0;
		if(fuelside < -1.0) fuelside = -1.0;
		if(gearside >  1.0) gearside =  1.0;
		if(gearside < -1.0) gearside = -1.0;
		fuelside_motorgroup.set( fuelside);
		gearside_motorgroup.set(-gearside);
	}
	
	// A basic arcade drive method. The two parameters are expected to be within the range -1.0 to 1.0
	// If not, they are limited to be within that range. The transitional speed and yaw are combined
	// to be applied to the fuelside motor and gearside motor. Trans_speed (tansitional velocity) will
	// set the robots forward speed, and yaw (angular velocity) will set the robot turning. Having a
	//combination of the two will make the robot drive on an arc.
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
    	
    	fuelside_motorgroup.set(fs_speed);
    	gearside_motorgroup.set(gs_speed);
    }
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here. This gets run constantly
    	// as long as there are not other Commands that require this subsystem.
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

