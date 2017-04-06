package org.usfirst.frc.team1895.robot.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestVision extends Command {

	int minHue;
	int maxHue;
	int minSat;
	int maxSat;
	int minVal;
	int maxVal;
	
    public TestVision() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gear_camera);
    	
    	SmartDashboard.putNumber("max hue", 130);
		SmartDashboard.putNumber("max sat", 255);
		SmartDashboard.putNumber("max val", 255);
		SmartDashboard.putNumber("min hue", 30);
		SmartDashboard.putNumber("min sat", 0);
		SmartDashboard.putNumber("min val", 230);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	minHue = (int) SmartDashboard.getNumber("min hue", 30);
		minSat = (int) SmartDashboard.getNumber("min sat", 0);
		minVal = (int) SmartDashboard.getNumber("min val", 230);
		maxHue = (int) SmartDashboard.getNumber("max hue", 130);
		maxSat = (int) SmartDashboard.getNumber("max sat", 255);
		maxVal = (int) SmartDashboard.getNumber("max val", 255);
		
		Robot.gear_camera.setThresholdVals(minHue, maxHue, minSat, maxSat, minVal, maxVal);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
