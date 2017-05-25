package org.usfirst.frc.team1895.robot.commands.shooter;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.oi.F310;
import org.usfirst.frc.team1895.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will set the shooter speed and the regulator speed.
 */
public class DefaultShooter extends Command {

	// Cast the variables from outside the class, since it
	// is easier and looks nicer then typing it out each time.
	private Shooter shooter = Robot.shooter;
	private F310 driverTwo = Robot.oi.gamepad2;
	
    public DefaultShooter() {
        requires(shooter);
    }

    /**
     * Set the shooter and regulator to the desired speed from the
     * second driver's controller.
     * */
    @Override
    protected void execute() {
    	shooter.setRegulator(driverTwo.getAxis(F310.RY));
    	shooter.setShooterSpeed(1.0);
    }

    /**
     * Since we never want this command to end, return false.
     * */
    @Override
    protected boolean isFinished() {
        return false;
    }
}
