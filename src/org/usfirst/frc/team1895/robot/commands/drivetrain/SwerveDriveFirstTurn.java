package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Changelog:
 * (Zach)
 * 		Description: 
 * 			- Desired arguments: speed, distance
 * 		To do still:
 * 			- Fill in execute method and other methods if needed
 * 			- Add arcade drive and gamepad use
 */
public class SwerveDriveFirstTurn extends Command {
	boolean done;
	double distancetoPeg;
	double angletoPeg;
    public SwerveDriveFirstTurn(double distancetoLift,double angletoLift) {
    	distancetoPeg=distancetoLift;
    	angletoPeg=angletoLift;
    	done = false;
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = Robot.drivetrain.swerveIntoPeg1(distancetoPeg, angletoPeg);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
