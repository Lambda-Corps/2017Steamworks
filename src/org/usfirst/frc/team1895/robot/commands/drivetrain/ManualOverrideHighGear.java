package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualOverrideHighGear extends Command {

	private boolean test;
	
    public ManualOverrideHighGear(boolean test) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    	this.test = test;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(test) Robot.drivetrain.manualOverride(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(!test) Robot.drivetrain.manualOverride(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
