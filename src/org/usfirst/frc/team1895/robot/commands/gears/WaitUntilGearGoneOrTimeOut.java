package org.usfirst.frc.team1895.robot.commands.gears;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitUntilGearGoneOrTimeOut extends Command {
	boolean done;
	double counter;
	double counterforgear;
	double gearNotTakenTimeout;
	
    public WaitUntilGearGoneOrTimeOut(double time) { // time in seconds for timeout
        // Use requires() here to declare subsystem dependencies
    	counter = 0.0;
    	counterforgear = 0.0;
    	done = false;
    	gearNotTakenTimeout = 50 * time; // 20 ms per count increment
        requires(Robot.gearholder);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("I_Counter: " + counter);
    	System.out.println("I_Counter for gear: " + counterforgear);
    	System.out.println("I_done state: " + done);
    	System.out.println("I_gearNotTakenTimeout: " + gearNotTakenTimeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
  		if (Robot.gearholder.isGearPresent() == false)  {  // Gear has NOT been taken  
  			counter = 0;
  			if (counterforgear >= 100){ //2 seconds
  				done = true;	
  			}
  			counterforgear++;      // Counter for delay after taken
  		} 
  		else {
  			if (counter >= gearNotTakenTimeout) {    // Gear has been NOT taken, wait before moving
  				done = true;
  			}
  			counter++;		// Only incremented if Gear is present
  		}
}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	counter = 0.0;
    	counterforgear = 0.0;
    	done = false;
//    	System.out.println("E_Counter: " + counter);
//    	System.out.println("E_Counter for gear: " + counterforgear);
//    	System.out.println("E_done state: " + done);
//    	System.out.println("E_gearNotTakenTimeout: " + gearNotTakenTimeout);
    }

    //If done equals true then our is finished will be true and it will then do end()
    protected void interrupted() {
    	counter = 0.0;
    	counterforgear = 0.0;
    	done = true;
    }
}
