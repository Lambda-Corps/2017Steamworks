package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Changelog:
 * 2/4/2017 (Maddy Seputro)
 * 		Description: Uses left and right motorgroup encoders to drive a given distance at a given speed. Once the distance has been reached, stop. 
 * 		The methods used will be PID-controlled.
 * 			- Desired arguments: speed, distance
 * 		To do still:
 * 			- Fill in execute method and other methods if needed
 * 	Added: requires statement
 * 
 */
public class DriveStraightSetDistance extends Command {

	double goalDistance = 0.0;
	boolean done = false;
    public DriveStraightSetDistance(double givenDistance) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain); 
        goalDistance = givenDistance;
//        SmartDashboard.putNumber("P value: ", .1);
//    	SmartDashboard.putNumber("I value: ", 0.0);
//    	SmartDashboard.putNumber("D value: ", -.01);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double p, i , d;
//    	p = SmartDashboard.getNumber("P value: ", .1);
//    	i = SmartDashboard.getNumber("I value: ", 0.0);
//    	d = SmartDashboard.getNumber("D value: ", -.01);
//    	Robot.drivetrain.makeNewPidDriving(p, i, d); //get p,i,d from smartdashboard
    	Robot.drivetrain.setPIDSetpoints(goalDistance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = Robot.drivetrain.driveStraightWithPID(goalDistance);
    	if (done){
    		Robot.drivetrain.tankDrive(0, 0);
    	}
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
