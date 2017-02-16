package org.usfirst.frc.team1895.robot.ledstrip;

import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDSubsystem extends Subsystem {

	private LEDStrip ledStrip;
	
	private int count = 0;
	
	public LEDSubsystem() {
		ledStrip = new LEDStrip(24);
		ledStrip.gradient(                        - 1,     ledStrip.length / 3, 255,   0,   0,   0, 255,   0);
		ledStrip.gradient(    ledStrip.length / 3 - 1, 2 * ledStrip.length / 3,   0, 255,   0,   0,   0, 255);
		ledStrip.gradient(2 * ledStrip.length / 3 - 1,     ledStrip.length    ,   0,   0, 255, 255,   0,   0);
	}
	
	public void draw() {
		if(count % 5 == 0) {
			ledStrip.shift(1);
		}
		count++;
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new LEDCommand());
	}

}
