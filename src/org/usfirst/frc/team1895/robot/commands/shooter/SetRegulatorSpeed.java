package org.usfirst.frc.team1895.robot.commands.shooter;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetRegulatorSpeed extends InstantCommand {

	double regSpeed;
	
    public SetRegulatorSpeed(double speed) {
        super();
        
        regSpeed = speed;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.shooter);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.shooter.setRegulator(regSpeed);
    }
}
