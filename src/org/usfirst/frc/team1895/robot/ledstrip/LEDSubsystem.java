package org.usfirst.frc.team1895.robot.ledstrip;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDSubsystem extends Subsystem {

	public LEDStrip ledStrip;
	public AnalogGyro gyro;
	
	public LEDSubsystem() {
		ledStrip = new LEDStrip(4);
		gyro = new AnalogGyro(0);
	}
	
	public void draw() {
		
		double angle = gyro.getAngle();
		
		if(angle < 0.0) {
			angle *= -1;
		}
		
		if(angle >= 0 && angle <  90) {
			ledStrip.fill(0, ledStrip.length, 255,   0,   0);
		} else if(angle >=  90 && angle < 180) {
			ledStrip.fill(0, ledStrip.length,   0, 255,   0);
		} else if(angle >= 180 && angle < 270) {
			ledStrip.fill(0, ledStrip.length,   0,   0, 255);
		} else if(angle >= 270 && angle < 360) {
			ledStrip.fill(0, ledStrip.length, 255, 255, 255);
		}
		
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new LEDCommand());
	}

}
