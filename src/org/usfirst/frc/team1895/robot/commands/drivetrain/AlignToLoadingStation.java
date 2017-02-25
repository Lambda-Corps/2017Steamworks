package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
<<<<<<< HEAD:src/org/usfirst/frc/team1895/robot/commands/drivetrain/AlignToLoadingStation.java
 * Changelog:
 * 2/4/2017 (Maddy Seputro)
 * 		Description: Use camera to detect the loading station, then drive up to it using the cameras. The middle front rangefinder 
 * 		can be used to help the robot determine whether it has reached its destination or not. Will be PID-controlled.
 * 			- Desired arguments: speed? (or do we want it to be hardcoded)
 * 		To do still:
 * 			- Fill in execute method and other methods if needed
 * 	Added: requires statement
=======
 * Changelog: 2/4/2017 (Maddy Seputro) Description: Uses the camera and gyro to
 * turn a given heading chosen by the user at a given speed chosen by the user.
 * Will finish once the heading has been reached. - Desired arguments: speed,
 * heading To do still: - Fill in execute method and other methods if needed
 * Added: requires statement
>>>>>>> george's latest code:src/org/usfirst/frc/team1895/robot/commands/drivetrain/TurnWithCamera.java
 */
public class AlignToLoadingStation extends Command {

<<<<<<< HEAD:src/org/usfirst/frc/team1895/robot/commands/drivetrain/AlignToLoadingStation.java
    public AlignToLoadingStation() {
        requires(Robot.drivetrain);
    }
=======
	public TurnWithCamera() {
	}
>>>>>>> george's latest code:src/org/usfirst/frc/team1895/robot/commands/drivetrain/TurnWithCamera.java

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
