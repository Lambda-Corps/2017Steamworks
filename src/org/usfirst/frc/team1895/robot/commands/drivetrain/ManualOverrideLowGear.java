package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualOverrideLowGear extends Command {

	private final Drivetrain drivetrain = Robot.drivetrain;
	private boolean test;
	
    public ManualOverrideLowGear(boolean test) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
        
    	this.test = test;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if( test) Robot.drivetrain.manualOverride( true, false);
    	if(!test) Robot.drivetrain.manualOverride(false, false);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
