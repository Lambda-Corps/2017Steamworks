package org.usfirst.frc.team1895.robot.commands.climbing;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Changelog:
 * 2/4/2017 (Maddy Seputro)
 * 		Description: Will read a current as it climbs, and once the current level 
 * 		exceeds a certain threshold we will know it has reached the top and the motors will stop.
 * 			- Desired arguments: speed?
 * 		To do still:
 * 			- Fill in execute method and other methods if needed
 * 	Added: requires statement
 * This command handles the motorized control of the climbing motor. 
 */
public class Climb extends Command {
	public Climb() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.winch);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
