package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/** Changelog: 
 * 2/4/2017: (Maddy Seputro)
 * 		Description: Uses the camera and 3 long-range rangefinders to drive toward the boiler at a given speed chosen by the user.
 * 		Will adjust as the robot goes using the values given by the rangefinders, and stop once a certain threshold has been reached 
 * 		and the two corner rangedfinders are within the accepted margin of error.
 * 			- Desired arguments: speed? 
 * 		To do still:
 * 			- Fill in execute method and other methods if needed
 * 	Added: requires statement, moved from Shooter Commands Package to the Drivetrain Commands Package because it requires 
 * sensors that would be used in drivetrain. Corresponding method also moved to drivetrain subsystem.
 *
 */
public class AlignToHighGoal extends Command {

    public AlignToHighGoal() {
        requires(Robot.drivetrain);
        //requires(Robot.fuel_camera);
    }

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
