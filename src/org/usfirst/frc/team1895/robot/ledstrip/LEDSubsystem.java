package org.usfirst.frc.team1895.robot.ledstrip;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDSubsystem extends Subsystem {

	public LEDStrip ledStrip;
	
	public LEDSubsystem() {
		ledStrip = new LEDStrip(2);
	}
	
	public void draw() {
		ledStrip.fill(0, ledStrip.length, 0, 0, 200);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new LEDCommand());
	}

}
