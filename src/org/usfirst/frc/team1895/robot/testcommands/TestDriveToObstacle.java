package org.usfirst.frc.team1895.robot.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestDriveToObstacle extends Command {

	double m_speed, m_goalDistance, m_scalar;
	boolean m_done;
	
    public TestDriveToObstacle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	m_done = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_speed = SmartDashboard.getNumber("Test Drive Tank Speed: ", .4);
    	m_goalDistance = SmartDashboard.getNumber("Test Drive Distance: ", 13);
    	m_scalar = SmartDashboard.getNumber("Test Drive Tank Scalar:", .94);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_done = Robot.drivetrain.testdriveRangeFinderDistance(m_goalDistance, m_speed, m_scalar);
    	SmartDashboard.putNumber("RangeFinder Distance: ", Robot.drivetrain.fineDistanceFinder());
    	SmartDashboard.putNumber("PID Drive LE: ", Robot.drivetrain.getLEncoderValues());
    	SmartDashboard.putNumber("PID Drive RE: ", Robot.drivetrain.getREncoderValues());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
