package org.usfirst.frc.team1895.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnOffLEDRing extends Command {

	final Relay ledRing;
	
    public TurnOffLEDRing() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	ledRing = new Relay(0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	ledRing.set(Relay.Value.kForward);
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
