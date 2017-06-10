package org.usfirst.frc.team1895.robot.commands.shooter;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetShooterSpeed extends InstantCommand {

	double shooterSpeed;
	
    public SetShooterSpeed(double speed) {
        super();
        shooterSpeed = speed;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.shooter);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.shooter.setShooterSpeed(shooterSpeed);
    }
}
