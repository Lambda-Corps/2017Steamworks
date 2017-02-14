package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Changelog:
 * 2/4/2017 (Maddy Seputro)
 * 		Description: Uses left and right motorgroup encoders to drive a given distance at a given speed. Once the distance has been reached, stop. 
 * 		The methods used will be PID-controlled.
 * 			- Desired arguments: speed, distance
 * 		To do still:
 * 			- Fill in execute method and other methods if needed
 * 	Added: requires statement
 * 
 */
public class DriveStraightSetDistance extends Command {

	double goalDistance = 0.0;
	boolean done = false;
    public DriveStraightSetDistance(double givenDistance) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        goalDistance = givenDistance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.setPIDSetpoints(goalDistance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = Robot.drivetrain.driveStraightWithPID(goalDistance);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
