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

	public AlignToPeg() {
		requires(Robot.drivetrain);
		requires(Robot.gear_camera);

		Robot.gear_camera.startVisionThread();
		stopVision = false;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// This is the offset from the camera to the center of the
		// target.
		double offset = Robot.gear_camera.getOffset();
		
		// Allow a 2 inch tolerance both ways
		if (offset < -2) {
			Robot.drivetrain.tankDrive(0.3, 0.2);
		} 
		else if (offset > 2) {
			Robot.drivetrain.tankDrive(0.2, 0.3);
		} 
		else {
			Robot.drivetrain.tankDrive(0.0, 0.0);
			System.out.println("-----Aligned!!!-----");
			stopVision = true;
		}
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
