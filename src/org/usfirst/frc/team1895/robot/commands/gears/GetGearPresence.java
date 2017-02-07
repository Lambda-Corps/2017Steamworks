package org.usfirst.frc.team1895.robot.commands.gears;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Changelog:
 * 2/4/2017 (Maddy Seputro)
 * 		Description: Detects if gear is in the slot or not using a short-range rangefinder. If the 
 * 		gear was detected to be present, return true. Otherwise return false.
 * 			- Desired arguments: none
 * 		To do still:
 * 			- Fill in execute method and other methods if needed
 * 	Added: requires statement
 */
public class GetGearPresence extends Command {

    public GetGearPresence() {
        requires(Robot.gearholder);
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
