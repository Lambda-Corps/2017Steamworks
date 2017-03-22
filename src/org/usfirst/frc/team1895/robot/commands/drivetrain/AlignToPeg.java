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
	double offset;
	double angle;
	double distance;
	double hypotenuse;
	double counter;
	public AlignToPeg() {
		requires(Robot.drivetrain);
		requires(Robot.gear_camera);

		Robot.gear_camera.startVisionThread();
		stopVision = false;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.resetGyro();
		
		// This is the offset from the camera to the edge of the peg
		offset = Robot.gear_camera.getOffset() + 10.25;
		distance = Robot.drivetrain.fineDistanceFinder();
		angle = Math.toDegrees(Math.atan((offset / 24)));
		hypotenuse = Math.hypot(offset, 24);
		counter = 0.0;
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (counter>300){
			SmartDashboard.putNumber("Gyro angle: ", Robot.drivetrain.getGyroAngle());
			if ((angle-38)>=-.1||(angle-38)<=.1){
				SmartDashboard.putString("Is alignToPeg Done", "Done");
				Robot.drivetrain.tankDrive(0, 0);
				stopVision = true;
			}
			if(Robot.drivetrain.getGyroAngle() <= (angle-38)) {//the 38 is the angle when it is perfectly aligned
				
				Robot.drivetrain.tankDrive(0.3, -0.3);
			}
			if(Robot.drivetrain.getGyroAngle() > (angle-38)){//38 =  atan(10.25(horizontal of tapes) / 13(distance))
				Robot.drivetrain.tankDrive(-0.3, 0.3);
			}
			else {
				Robot.drivetrain.tankDrive(0.0, 0.0);
				stopVision = true;
			}
		}
		else{
			offset = Robot.gear_camera.getOffset() + 10.25;
			SmartDashboard.putString("Is alignToPeg Done", "False");
			angle = Math.toDegrees(Math.atan((offset / 13)));//13 is the distance
			SmartDashboard.putNumber("Angle inside AlignToPeg ", angle);
			counter++;
		}
		
		// Hopefully drive far enough to reach peg
		//Robot.drivetrain.driveStraightWithPID(hypotenuse-5); 

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return stopVision;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.resetGyro();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}