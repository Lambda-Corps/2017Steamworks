package org.usfirst.frc.team1895.robot.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Changelog: 2/21/2017 (George French) Description: Takes the horizontal offset
 * of the center of the robot to the peg and align to it autonomously
 */

public class TestAlignToPeg extends Command {
	public boolean stopVision;
	public double centerX;
	double lowS;
	double highS;
	double neutralS;
	
	public TestAlignToPeg() {
		requires(Robot.drivetrain);
		requires(Robot.gear_camera);
		
		stopVision = false;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.resetGyro();
		
		lowS = SmartDashboard.getNumber("lowSpeed: ", 0.2);
		highS = SmartDashboard.getNumber("highSpeed: ", 0.3);
		neutralS = SmartDashboard.getNumber("neutralSpeed: ", 0.3);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		centerX = Robot.gear_camera.getAvgCenterX();
				
		stopVision = Robot.drivetrain.testDriveToPeg(centerX, lowS, highS, neutralS);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return stopVision;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.resetGyro();
		
		SmartDashboard.putNumber("Final distance: ", Robot.drivetrain.fineDistanceFinder());
		SmartDashboard.putNumber("Final centerX: ", Robot.gear_camera.getAvgCenterX());
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}