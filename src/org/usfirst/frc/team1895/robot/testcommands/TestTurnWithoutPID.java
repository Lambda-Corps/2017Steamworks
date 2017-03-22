package org.usfirst.frc.team1895.robot.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This test iterates through the testing of turning to a desired angle
 * using the gyro (NAVX).  
 */
public class TestTurnWithoutPID extends Command {

	double m_GoalAngle, m_speed;
	boolean m_done;
	
    public TestTurnWithoutPID() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	m_done = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_speed = SmartDashboard.getNumber("Test Turn NP Speed: ", 0.35);
    	m_GoalAngle = SmartDashboard.getNumber("Test Turn Angle: ", 90.0);
    	
    	// Reset the Gyro state
    	Robot.drivetrain.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Test Turn Gyro Angle: ", Robot.drivetrain.getGyroAngle());
    	m_done = Robot.drivetrain.turnWithGyroNP(m_GoalAngle, m_speed);
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
