package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.oi.F310;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FineTuningLowGear extends Command {

    public FineTuningLowGear() {
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.manualOverride( true, false); //ensures it's in low gear
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Y axis at 70%, X at 65%
    	//Robot.drivetrain.arcadeDrive((0.70)*Robot.oi.gamepad.getAxis(F310.RY), (0.65)*Robot.oi.gamepad.getAxis(F310.LX));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.manualOverride(false, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
