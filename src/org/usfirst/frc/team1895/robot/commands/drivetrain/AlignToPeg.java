package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Changelog: 2/21/2017 (George French) Description: Takes the horizontal offset
 * of the center of the robot to the peg and align to it autonomously
 */

public class AlignToPeg extends Command {
	public boolean stopVision;
	double centerX;
	
	public AlignToPeg() {
		requires(Robot.drivetrain);
		requires(Robot.gear_camera);

		stopVision = false;
	}

	// Called just before this Command runs the first time
	// Since it is in the initialize, it will only be called once instead of it being in the constructor,
	// where every time the command is instantiated you'd start the vision thread
	protected void initialize() {		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		centerX = Robot.gear_camera.getAvgCenterX();
		stopVision = Robot.drivetrain.testDriveToPeg(centerX, 345, 0.3, 0.2, 0.3);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return stopVision;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}