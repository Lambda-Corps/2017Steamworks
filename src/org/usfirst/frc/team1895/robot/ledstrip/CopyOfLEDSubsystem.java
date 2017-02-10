package org.usfirst.frc.team1895.robot.ledstrip;

import edu.wpi.first.wpilibj.command.Subsystem;

public class CopyOfLEDSubsystem extends Subsystem {

	public LEDStrip ledStrip;
	
	public CopyOfLEDSubsystem() {
		ledStrip = new LEDStrip(2);
	}
	
	public void draw() {
		ledStrip.fill(0, ledStrip.length, 200, 0, 0);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new CopyOfLEDCommand());
	}

}
