package org.usfirst.frc.team1895.robot.ledstrip;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LEDCommand extends Command {

	private final LEDSubsystem led = Robot.led;
	
    public LEDCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(led);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//led.queue(led.setRebecca);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	led.start(led.blinky);
    	//led.start(led.runRebecca);
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
