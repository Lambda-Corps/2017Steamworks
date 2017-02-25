package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
<<<<<<< HEAD:src/org/usfirst/frc/team1895/robot/commands/drivetrain/SwitchGears.java
 * Changelog:
 * 2/4/2017 (Maddy Seputro)
 * 		Description: Switches between low and high gears at the push of a button. Low gear allows for more torque and less speed.
 * 		High gear allows for less torque but more speed.
 * 			- Desired arguments: none
 * 		To do still:
 * 			- Fill in execute method and other methods if needed
 * 	Added: requires statement
=======
 * Changelog: 2/4/2017 (Maddy Seputro) Description: Uses the camera to drive
 * toward a specific target. If the robot drifts from the target the robot will
 * adjust itself. - Desired arguments: TBD To do still: - Fill in execute method
 * and other methods if needed Added: requires statement
>>>>>>> george's latest code:src/org/usfirst/frc/team1895/robot/commands/drivetrain/DriveWithCamera.java
 */
public class SwitchGears extends Command {

<<<<<<< HEAD:src/org/usfirst/frc/team1895/robot/commands/drivetrain/SwitchGears.java
    public SwitchGears() {
        requires(Robot.drivetrain);
    }
=======
	public DriveWithCamera() {
		
	}
>>>>>>> george's latest code:src/org/usfirst/frc/team1895/robot/commands/drivetrain/DriveWithCamera.java

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
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
