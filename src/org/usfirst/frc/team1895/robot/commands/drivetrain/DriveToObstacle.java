package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToObstacle extends Command {

	double goalDistance;
	boolean done;
	double speed;
    public DriveToObstacle(double distancetoObstacle, double speed) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        goalDistance = distancetoObstacle;
        this.speed = speed;
        done = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = Robot.drivetrain.driveRangeFinderDistance(goalDistance, speed);//ten is distance in inches and .5 is speed
    	
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