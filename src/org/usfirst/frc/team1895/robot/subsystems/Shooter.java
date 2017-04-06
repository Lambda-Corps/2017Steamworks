package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.shooter.DefaultShooter;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
	
    private final CANTalon regulator;
    private final CANTalon shooter;
    
    // This is a measured value, of the max encoder speed in native units per
    // second. This value is about 2273 RPM.
    private static final double encoderFullSpeed = 38800;
    
    public Shooter() {
    	regulator = new CANTalon(RobotMap.REGULATOR_MOTOR_PORT);
    	
    	// Creates the CANTalon, and sets it into speed control mode. Also,
    	// this sets the PID values for velocity control. Here, the F term
    	// is the most important, and MUST be tuned first!!! Calculate F
    	// by (100% * 1023) / encoderFullSpeed, then tune.
    	shooter = new CANTalon(RobotMap.SHOOTER_MOTOR_PORT);
    	shooter.setInverted(true);
    	shooter.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	shooter.changeControlMode(CANTalon.TalonControlMode.Speed);
    	shooter.setProfile(0);
    	shooter.setF(0.03747);
    	shooter.setP(0.0);
    	shooter.setI(0.0);
    	shooter.setD(0.0);
    }
    
    /**
     * Set the desired speed of the shooter. Since we are shooting forward,
     * the given argument must be 0.0 to 1.0 (which is the percentage of
     * the max speed).
     * */
    public void setShooterSpeed(double speed) {
    	if(speed < 0) speed = 0;
    	if(speed > 1) speed = 1; 
    	shooter.set(speed * encoderFullSpeed);
    }
    
    /**
     * Set the speed of the regulator. Ths does allow backdrive, for any case
     * where it is necassary. If this proves to be negatively affecting fuel
     * shooting, then this will change.
     * */
    public void setRegulator(double speed) {
    	if(speed < -1) speed = -1;
    	if(speed >  1) speed =  1;
    	regulator.set(speed);
    }
    
    /**
     * Set the default command to the DefaultShooter command, which deals with
     * setting the regulator speed and the shooter speed.
     * */
    public void initDefaultCommand() {
        setDefaultCommand(new DefaultShooter());
    }
}

