package org.usfirst.frc.team1895.robot.commands.climbing;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.oi.F310;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 2/15/2017 (Maddy Seputro)
 * 		Description: Allows user to manually control the winch motor and climbing of the robot.
 * 		To do still:
 * 			- Test
 * 	Added: requires statement, execute 
 */
public class DefaultManuallyClimb extends Command {

    public DefaultManuallyClimb() {
        requires(Robot.winch);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//climb up
    	if(Robot.oi.gamepad2.getAxis(F310.LY) > 0.2) {
    		Robot.winch.manualClimbing(Robot.oi.gamepad2.getAxis(F310.LY)); //for gamepad2
    	} else if(Robot.oi.gamepad2.getAxis(F310.LY) < -0.2 && Robot.oi.gamepad2.getButton(F310.START)) {
    		//climb down 
    		Robot.winch.manualClimbing(-Robot.oi.gamepad2.getAxis(F310.LY)); //for gamepad2
    	} else {
    		//just don't move
    		Robot.winch.manualClimbing(0.0);
    	}
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
