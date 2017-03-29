package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class RetryDeployGearHolder1 extends Command {
	double counter;
	boolean done;
	int attempt;
	
	boolean retryGearDeployment;

    public RetryDeployGearHolder1() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	counter = 0.0;
    	done = false;
    	attempt = 0;
    	
    	retryGearDeployment = false;
//        Robot.retryButton.whenPressed(new DeployGearHolderDummyCommandGroup());

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	attempt ++;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	new RetryGearDeploymentCommandGroup();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.retryButton.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
