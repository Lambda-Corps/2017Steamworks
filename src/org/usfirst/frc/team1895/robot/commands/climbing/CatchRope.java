package org.usfirst.frc.team1895.robot.commands.climbing;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Changelog:
 * 2/4/2017 (Maddy Seputro)
 * 		Description: Move the winch slowly so that the rope has a change to be caught in the winch's spindle. Once 
 * 		the rope has been caught, the current level will exceed a certain threshold and we will know it has reached 
 * 		the top and the motors will stop.
 * 			- Desired arguments: speed? (or do we want it to be hardcoded)
 * 		To do still:
 * 			- Fill in execute method and other methods if needed
 * 	Added: requires statement, renamed Command from MoveWinch to CatchRope
 */
public class CatchRope extends Command {

    public CatchRope() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.winch);
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
