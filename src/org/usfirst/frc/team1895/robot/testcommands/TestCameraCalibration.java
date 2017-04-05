package org.usfirst.frc.team1895.robot.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestCameraCalibration extends Command {

    public TestCameraCalibration() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gear_camera);
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("CenterX value", Robot.gear_camera.getAvgCenterX());
    	SmartDashboard.putNumber("Distance Away", Robot.drivetrain.fineDistanceFinder());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//Returning false to continuously try the command. This command will be stopped by disabling.
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
