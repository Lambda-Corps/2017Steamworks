package org.usfirst.frc.team1895.robot.commands.gears;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DefaultGearInOrOut extends Command {

	private int counter;
    public DefaultGearInOrOut() {
    	requires(Robot.gearholder);
    	counter = 0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(++counter % 25 == 0) {
    		//on 25th iteration, print. Only prints twice a second
    		SmartDashboard.putBoolean("Is gear in the slot", Robot.gearholder.isGearPresent());
    		SmartDashboard.putBoolean("Is the gearholder out?", Robot.gearholder.getGearState());
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
