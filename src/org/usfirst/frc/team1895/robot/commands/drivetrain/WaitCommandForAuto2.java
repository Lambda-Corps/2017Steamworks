package org.usfirst.frc.team1895.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitCommandForAuto2 extends Command {
	boolean done;
	double counter;
	double counterforgear;
	double gearNotTakenTimeout;
	double time;

    public WaitCommandForAuto2() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	counter = 0.0;
    	counterforgear = 0.0;
    	done = false;
    	time = 2;  //seconds
    	gearNotTakenTimeout = 50 * time; // time before gear deployment is retried, 20 ms per count increment
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (counter >= gearNotTakenTimeout) {    // Gear has been taken, wait 2 seconds before retrying, repeat until gear taken 
				counter = 0;
			}
			counter++;		// Only incremented if Gear is present
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
