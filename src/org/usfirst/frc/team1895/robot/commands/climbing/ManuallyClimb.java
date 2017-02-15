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
public class ManuallyClimb extends Command {

    public ManuallyClimb() {
        requires(Robot.winch);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.oi.gamepad.getAxis(F310.LT) > Robot.oi.gamepad.getAxis(F310.RT) && Robot.oi.gamepad.getAxis(F310.LT) > 0.2) {
    		Robot.winch.manualClimbing(Robot.oi.gamepad.getAxis(F310.LT)); //for gamepad
    		System.out.println("here");
    	} else if(Robot.oi.gamepad.getAxis(F310.LT) < Robot.oi.gamepad.getAxis(F310.RT) && Robot.oi.gamepad.getAxis(F310.RT) > 0.2) {
    		Robot.winch.manualClimbing(-Robot.oi.gamepad.getAxis(F310.RT)); //for gamepad
    		System.out.println("or here");
    	} else {
    		Robot.winch.manualClimbing(0.0); //for gamepad
    		System.out.println("vQLKUYJMNG4 W");
    	}
    	//Robot.winch.manualClimbing(Robot.oi.rightArcadeJoystick.getRawAxis(2)); //for joystick
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
