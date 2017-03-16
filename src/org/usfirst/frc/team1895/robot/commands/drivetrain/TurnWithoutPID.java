package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnWithoutPID extends Command {

	private boolean finished;
	double angle = 0.0;
	double speed = 0.0;
	
    public TurnWithoutPID(double angle, double speed) {
    	requires(Robot.drivetrain);
    	finished = false;
    	this.angle = angle;
    	this.speed = speed;
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	finished = Robot.drivetrain.turnWithGyroNP(angle, speed);
    	SmartDashboard.putNumber("Our Current Angle:", Robot.drivetrain.getAngle());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
