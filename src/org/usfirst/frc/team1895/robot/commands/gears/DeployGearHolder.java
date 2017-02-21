package org.usfirst.frc.team1895.robot.commands.gears;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Changelog:
 * 2/4/2017 (Maddy Seputro)
 * 		Description: Moves the gear holder from its retracted position to its deployed position using pneumatics.
 * 			- Desired arguments: speed? (or do we want it to be hardcoded)
 * 		To do still:
 * 			- Fill in execute method and other methods if needed
 * 	Added: requires statement
 */
public class DeployGearHolder extends Command {

    public DeployGearHolder() {
    	requires(Robot.gearholder);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gearholder.extendGear();
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
