package org.usfirst.frc.team1895.robot.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestAlignToPeg extends Command {

	public boolean stopVision;
	double offset;
	double angle;
	double distance;
	double hypotenuse;
	double counter;
	
	public TestAlignToPeg() {
		requires(Robot.drivetrain);
		requires(Robot.gear_camera);

		stopVision = false;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.gear_camera.startVisionThread();
		
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
			SmartDashboard.putNumber("Gyro angle for vision: ", Robot.drivetrain.getGyroAngle());
			if ((38-angle)>=-2||(38-angle)<=2){
				SmartDashboard.putString("Is alignToPeg Done", "Done");
				Robot.drivetrain.arcadeDrive(0, 0);
				stopVision = true;
			}
			if(Robot.drivetrain.getGyroAngle() <= (38-angle)) {//the 38 is the angle when it is perfectly aligned
				
				Robot.drivetrain.arcadeDrive(0, 0.3);
			}
			if(Robot.drivetrain.getGyroAngle() > (38-angle)){//38 =  atan(10.25(horizontal of tapes) / 13(distance))
				Robot.drivetrain.arcadeDrive(0, -0.3);
			}
			else {
				Robot.drivetrain.arcadeDrive(0.0, 0.0);
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
