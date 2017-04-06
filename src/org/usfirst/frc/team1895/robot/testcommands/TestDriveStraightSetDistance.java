package org.usfirst.frc.team1895.robot.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestDriveStraightSetDistance extends Command {
	
	double m_p, m_i, m_d, m_Distance;
	boolean m_done;
	
    public TestDriveStraightSetDistance() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.drivetrain);
    	m_done = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_p = SmartDashboard.getNumber("Distance P value: ", .025);
    	m_i = SmartDashboard.getNumber("Distance I value: ", 0.0);
    	m_d = SmartDashboard.getNumber("Distance D value: ", -.01);
    	m_Distance = SmartDashboard.getNumber("Test Drive Distance: ", 20.0);
    	
    	Robot.drivetrain.makeNewPidDriving(m_p, m_i, m_d); //get p,i,d from smartdashboard
    	Robot.drivetrain.setPIDSetpoints(m_Distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println(m_p + "   " + m_i + "     " + m_d);
    	m_done = Robot.drivetrain.driveStraightWithPID(m_Distance);
    	SmartDashboard.putNumber("PID Drive LE: ", Robot.drivetrain.getLEncoderValues());
    	SmartDashboard.putNumber("PID Drive RE: ", Robot.drivetrain.getREncoderValues());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("I FINISHED");
        return m_done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	m_Distance = 0.0;
    	m_done = true;
    	Robot.drivetrain.resetEncoders();
    	isFinished();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("interrupted cries");
    	end();
    }
}
