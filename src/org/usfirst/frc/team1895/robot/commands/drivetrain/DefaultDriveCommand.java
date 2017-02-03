package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This Command is where the user input is being used to set the robot's drivetrain motors.
 * As of right now, this Command is set up as the MVP, and is to be changed and added to.
 */

public class DefaultDriveCommand extends Command {
	public DefaultDriveCommand() {
		// Use requires() here to declare subsystem dependencies.
		// Since this Command only needs to access motors from the drivetrain,
		// we call requires to block other commands from using the same
		// subsystem, and trying to set motors to different values.
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Call the tankDrive() method of the drivetrain class, using the y-axis
		// value of the left joysick and the y-axis value of the right joystick
		// Joystick.getY() returns the Z axis, so use getRawAxis(1) (the y axis)
		// until the custom classes are implemented.
		//TODO: Implement Arcade Joystick or F310 get axis methods
		Robot.drivetrain.tankDrive(Robot.oi.leftArcadeJoystick.getRawAxis(1), Robot.oi.rightArcadeJoystick.getRawAxis(1));
	}

	// Make this return true when this Command no longer needs to run execute()
	// Since this is a default command, this Command (usually) never needs to end
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// Call the tankDrive method() to set all the motors to zero. If the robot is not meant to
		// be moving, most likely the program will stop the motors for us, but this should help prevent
		// any cases of something weird happening.
		Robot.drivetrain.tankDrive(0.0, 0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run. If this is the case, call the end() method
	// to peacefully close connections to the subsystem.
	@Override
	protected void interrupted() {
		end();
	}
}