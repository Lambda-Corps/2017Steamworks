package org.usfirst.frc.team1895.robot.commands.gears;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GetAverageVoltage extends Command {

    public GetAverageVoltage() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.gearholder);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// SmartDashboard.putNumber("AvgVoltage", Robot.gearholder.getAverageVoltage());
    	// SmartDashboard.putNumber("DistToObstacle", Robot.drivetrain.fineDistanceFinder());

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
