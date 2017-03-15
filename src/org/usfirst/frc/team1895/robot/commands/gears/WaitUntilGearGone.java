package org.usfirst.frc.team1895.robot.commands.gears;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.commands.drivetrain.RetryDeployGearHolder1;
import org.usfirst.frc.team1895.robot.commands.drivetrain.RetryGearDeploymentCommandGroup;

import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class WaitUntilGearGone extends Command {
	boolean done;
	double counter;
	double counterforgear;
	double gearNotTakenTimeout;
	double time;
	
	InternalButton retryButton;
	boolean retryGearDeployment;
	
    public WaitUntilGearGone() {
        // Use requires() here to declare subsystem dependencies
    	counter = 0.0;
    	counterforgear = 0.0;
    	done = false;
    	time = 2;  //seconds
    	gearNotTakenTimeout = 50 * time; // time before gear deployment is retried, 20 ms per count increment
        requires(Robot.gearholder);
        retryGearDeployment = false;
        retryButton = new InternalButton();
        retryButton.whenPressed(new RetryGearDeploymentCommandGroup());
        SmartDashboard.putData("button", retryButton);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("running: WUGGOTO--------------------------");
    	System.out.println("I_Counter: " + counter);
    	System.out.println("I_Counter for gear: " + counterforgear);
    	System.out.println("I_done state: " + done);
    	System.out.println("I_gearNotTakenTimeout: " + gearNotTakenTimeout);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
  		if (Robot.gearholder.isGearPresent() == false)  {  // Gear has been taken(?)
  			counter = 0;
  			if (counterforgear >= gearNotTakenTimeout){ // wait 2 seconds
  				done = true;
  			}
  			counterforgear++;      		// Counter for delay after taken
  		} 
  		else {
  			if (retryButton.get()==false) {
  				if (counter >= gearNotTakenTimeout) {    // Gear has been NOT taken, wait 2 seconds before retrying, repeat until gear taken 
  					retryGearDeployment = true;
  					counter = 0;
  				}
  				counter++;		// Only incremented if Gear is present
  			}
  				if (retryGearDeployment) {        //retry gear deployment after a certain time
  					retryButton.setPressed(true);
  					retryGearDeployment = false;
  				}
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
