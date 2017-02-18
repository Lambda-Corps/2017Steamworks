package org.usfirst.frc.team1895.robot.commands.gears;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitUntilGearGoneOrTimeOut extends Command {
	boolean done;
	double counter;
	double seconds;
    public WaitUntilGearGoneOrTimeOut(double time) {
        // Use requires() here to declare subsystem dependencies
    	seconds = time;
    	counter = 0;
    	done = false;
        requires(Robot.gearholder);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
  		if (Robot.gearholder.getGearPresence() == false)  {
  			done = true;
  		}
  		if (counter >= (50* seconds)) {
  				done = true;
  		}
  		counter++;
}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
