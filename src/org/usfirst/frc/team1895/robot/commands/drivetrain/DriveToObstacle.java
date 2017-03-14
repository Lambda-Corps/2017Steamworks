package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToObstacle extends Command {

	double goalDistance;
	boolean done;
	double speed;
    public DriveToObstacle(double distancetoObstacle, double speed2) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        //goalDistance = distancetoObstacle;
//        this.speed = speed;
        done = false;
        SmartDashboard.putNumber("Speed in DriveToObstacle: ", .1);
        SmartDashboard.putNumber("goalDistance ", distancetoObstacle);
    	SmartDashboard.putNumber("speed", speed);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	speed = SmartDashboard.getNumber("Speed in DriveToObstacle: ", .1);
    	//goalDistance = SmartDashboard.getNumber("goalDistance", goalDistance);
    	speed = SmartDashboard.getNumber("speed", speed);
    	goalDistance = SmartDashboard.getNumber("goalDistance ", goalDistance);
    
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = Robot.drivetrain.driveRangeFinderDistance(goalDistance, speed);//ten is distance in inches and .5 is speed
    	SmartDashboard.putNumber("LeftEncoder: ", Robot.drivetrain.getLEncoderValues());
		SmartDashboard.putNumber("RightEncoder: ", Robot.drivetrain.getREncoderValues());
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
