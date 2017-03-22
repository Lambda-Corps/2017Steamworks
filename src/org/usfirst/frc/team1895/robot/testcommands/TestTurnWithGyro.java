package org.usfirst.frc.team1895.robot.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class tests the iterations of turning with a PID Controller.
 */
public class TestTurnWithGyro extends Command {

	double m_GoalAngle, m_p, m_i, m_d;
	boolean m_done;
	
    public TestTurnWithGyro() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	m_done = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_p = SmartDashboard.getNumber("Turn P value: ", .025);
    	m_i = SmartDashboard.getNumber("Turn I value: ", 0.0);
    	m_d = SmartDashboard.getNumber("Turn D value: ", -.005);
    	m_GoalAngle = SmartDashboard.getNumber("Test Turn Angle: ", 90.0);
    	
    	// Reset the PID object in the drivetrain.
    	Robot.drivetrain.makeNewPidTurning(m_p, m_i, m_d); //get p,i,d from smartdashboard
    	
    	// Reset the Gyro state and enable the PID controller
    	Robot.drivetrain.resetGyro();
    	Robot.drivetrain.setUpPIDTurning(m_GoalAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Test Turn Gyro Angle: ", Robot.drivetrain.getGyroAngle());
    	m_done = Robot.drivetrain.turnWithPID(m_GoalAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	m_GoalAngle = 0.0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
