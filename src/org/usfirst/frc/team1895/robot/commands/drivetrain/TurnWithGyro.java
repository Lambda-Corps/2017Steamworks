package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Changelog:
 * 2/4/2017 (Maddy Seputro)
 * 		Description: Uses the gyro to turn a given heading chosen by the user at a given speed chosen by the user. Will finish once the 
 * 		heading has been reached.  
 * 			- Desired arguments: speed, heading
 * 		To do still:
 * 			- Fill in execute method and other methods if needed
 * 	Added: requires statement
 *
 */
public class TurnWithGyro extends Command {
	double goalAngle = 0.0;
	boolean done = false;
    public TurnWithGyro(double givenAngle) {
    	requires(Robot.drivetrain);
        goalAngle = givenAngle;
    }
 
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetGyro();
    	Robot.drivetrain.setUpPIDTurning(goalAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = 	Robot.drivetrain.turnWithPID(goalAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	goalAngle = 0.0;
    	done = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
