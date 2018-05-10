package org.usfirst.frc.team1895.robot.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestDriveStraightSetDistance extends Command {
	
	double m_p, m_i, m_d, m_Distance;
	double pid_correction;
	boolean m_done;
	
    public TestDriveStraightSetDistance() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.drivetrain);
    	m_done = false;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_p = SmartDashboard.getNumber("P value: ", .1);
    	m_i = SmartDashboard.getNumber("I value: ", 0.0);
    	m_d = SmartDashboard.getNumber("D value: ", -.01);
    	m_Distance = SmartDashboard.getNumber("Test Drive Distance: ", 20.0);
    	pid_correction = SmartDashboard.getNumber("Test PID Correction", 0.1);
    	
    	Robot.drivetrain.makeNewPidDriving(m_p, m_i, m_d); //get p,i,d from smartdashboard
    	Robot.drivetrain.setPIDSetpoints(m_Distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_done = Robot.drivetrain.driveStraightWithPID(m_Distance);
    	SmartDashboard.putNumber("PID Drive LE: ", Robot.drivetrain.getLEncoderValues());
    	SmartDashboard.putNumber("PID Drive RE: ", Robot.drivetrain.getREncoderValues());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	m_Distance = 0.0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
