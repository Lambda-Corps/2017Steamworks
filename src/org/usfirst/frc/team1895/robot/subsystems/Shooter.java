package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.shooter.DefaultShooter;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/** ChangeLog: 
 * 	2/4/2017 (Maddy Seputro)
 * 		Description: Subsystem for the shooter and the intake system, which will be
 * 		used in the Shooter Command package. Flywheel should always be on, so the indexer
 * 		motor will determine whether balls are shot or not.
 *			Will need:
 *			- 2 encoders (so four digital IO ports)
 *			- motor
 *			- 1 solenoid
 *			- camera (but it's in drivetrain)
 *		To do still:
 *			- Finish methods alignToHighGoal, moveIntake, shoot
 *			- Add comments when you're done writing and as you write
 *			- Add cameras if you want them
 *			- Test 
 */
public class Shooter extends Subsystem {

	private CANTalon indexer_motor;		//smaller wheel
	private CANTalon flywheel_motor; 	//larger wheel -- speed will be PID controlled
	private Encoder indexer_encoder;
	private Encoder flywheel_encoder;
	private DoubleSolenoid shooter_solenoid;
	
    public Shooter() {
    	indexer_motor = new CANTalon(RobotMap.INDEXER_MOTOR_PORT);
    	flywheel_motor = new CANTalon(RobotMap.FLYWHEEL_MOTOR_PORT);
    	indexer_encoder = new Encoder(RobotMap.INDEXER_ENCODER_A_PORT, RobotMap.INDEXER_ENCODER_B_PORT);
    	flywheel_encoder = new Encoder(RobotMap.FLYWHEEL_ENCODER_A_PORT, RobotMap.FLYWHEEL_ENCODER_B_PORT);
    	shooter_solenoid = new DoubleSolenoid(RobotMap.SHOOTER_SOLENOID_A_PORT, RobotMap.SHOOTER_SOLENOID_B_PORT);
    }
    
    // For: Climb and DefaultWinch Commands 
    // Sensors: indexer_encoder
    // Description: will move the indexer to allow fuel to be shot one at a time. Encoders could be used to 
    // control how much the indexer motor moves when shooting or count revolutions needed to shoot one fuel
    public void moveIntake() {
    	//Show maddy some cool shtuffs //yayyyyyy
    }
    
    // For: DefaultShooter and Shoot Commands
    // Sensors: indexer_encoder (maybe)
    // Description: for the DefaultShooter Command, set flywheel_motor on but indexer_motor off so fuel isn't shot
    // As for the Shoot Command, set indexer_motor and flywheel_motor to desired speed. It will be called when the
    // robot presses the button for shooting (which is still TBD)
    public void shoot() {
    	
    }
    
    
    public void initDefaultCommand() {
    	// set flywheel_motor on but indexer_motor off so fuel isn't shot
        setDefaultCommand(new DefaultShooter());
    }
}

