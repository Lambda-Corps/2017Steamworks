package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveUntilDistanceWithRangefinder extends Command {

	double goalDistance;
	boolean done = false;
    public DriveUntilDistanceWithRangefinder() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        double howfar = Robot.drivetrain.fineDistanceFinder();
        
        goalDistance = (howfar+6);
        System.out.println(howfar+6);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.setPIDSetpoints(goalDistance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = Robot.drivetrain.driveStraightWithPID(goalDistance);
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