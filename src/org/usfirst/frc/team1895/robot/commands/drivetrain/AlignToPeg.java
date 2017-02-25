<<<<<<< HEAD
//package org.usfirst.frc.team1895.robot.commands.drivetrain;
//
//import org.usfirst.frc.team1895.robot.Robot;
//
//import edu.wpi.first.wpilibj.command.Command;
//
///**
// * Changelog: 2/21/2017 (George French) 
// * Description: Uses the camera to align
// * with the gear peg
// */
//public class AlignToPeg extends Command {
//	public boolean stopVision;
//
//    public AlignToPeg() {
//    	requires(Robot.drivetrain);
//    	requires(Robot.gear_camera);
//    	
//    	Robot.gear_camera.startVisionThread();
//    	stopVision = false;
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    	// This is the offset from the camera to the center of the
//    	// target. The preferred of the center of the target position is 80.
//    	double offset = Robot.gear_camera.getCenterX();
//    			
//    	if (offset < 70) {
//    		// Left Turn!
//    		Robot.drivetrain.arcadeDrive(.0, -0.4);
//    	}
//    	else if (offset > 90) {
//    		// Right Turn!
//    		Robot.drivetrain.arcadeDrive(.0, 0.4);
//
//    	}
//    	else {
//    		// Centered!
//    		Robot.drivetrain.arcadeDrive(0, 0);
//    		stopVision = true;
//    	}
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return false;
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    }
//}
=======
package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Changelog: 2/21/2017 (George French) 
 * Description: Uses the camera to align
 * with the gear peg
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
    	// target. The preferred of the center of the target position is 80.
    	double offset = Robot.gear_camera.getCenterX();
    			
    	if (offset < 70) {
    		// Left Turn!
    		Robot.drivetrain.arcadeDrive(.0, -0.4);
    	}
    	else if (offset > 90) {
    		// Right Turn!
    		Robot.drivetrain.arcadeDrive(.0, 0.4);

    	}
    	else {
    		// Centered!
    		Robot.drivetrain.arcadeDrive(0, 0);
    		stopVision = true;
    	}
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
>>>>>>> george's latest code
