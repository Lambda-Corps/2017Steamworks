package org.usfirst.frc.team1895.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitCommandForAuto extends Command {
	boolean done;
	double counter;
	double counterforgear;
	double gearNotTakenTimeout;
	double time;

    public WaitCommandForAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	counterforgear = 0.0;
    	done = false;
    	time = 3;  //seconds
    	gearNotTakenTimeout = 50 * time; // time before geargonesequence returns completed and robot goes to next instruction, 20 ms per count increment
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("I'm in wait command for auto -- 3 seconds");
    	System.out.println(counterforgear);
			if (counterforgear >= gearNotTakenTimeout){ // wait 3 seconds
				done = true;
			}
			counterforgear++;      		// Counter for delay after taken
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	counterforgear = 0.0;
    	done = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
