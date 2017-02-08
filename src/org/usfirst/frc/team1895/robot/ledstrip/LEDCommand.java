package org.usfirst.frc.team1895.robot.ledstrip;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LEDCommand extends Command {
	
	public LEDCommand() {
		requires(Robot.led);
	}
	
	@Override
	protected void initialize() {
		
	}
	
	@Override
	protected void execute() {
		
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void interrupted() {
		
	}
	
	@Override
	protected void end() {
		
	}
}
