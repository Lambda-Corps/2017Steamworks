package org.usfirst.frc.team1895.robot.commands.gears;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Changelog:
 * 2/4/2017 (Maddy Seputro)
 * 		Description: Use camera to detect the lift, then drive up to it using the cameras. The middle front rangefinder 
 * 		can be used to help the robot determine whether it has reached its destination or not. Will be PID-controlled.
 * 			- Desired arguments: speed? (or do we want it to be hardcoded)
 * 		To do still:
 * 			- Fill in execute method and other methods if needed
 * 	Added: requires statement
 */
public class AlignToLift extends Command {

    public AlignToLift() {
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
