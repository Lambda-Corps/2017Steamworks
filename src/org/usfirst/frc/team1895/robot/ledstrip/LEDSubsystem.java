package org.usfirst.frc.team1895.robot.ledstrip;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDSubsystem extends Subsystem {

	SPI spi = new SPI(SPI.Port.kOnboardCS0);
	
	public void draw() {
		byte[] test = new byte[]{ (byte) 255, (byte) 255,(byte) 255,(byte) 255,(byte) 255,(byte) 255,(byte) 255,(byte) 255,(byte) 255,(byte) 255,(byte) 255,(byte) 255,(byte) 255,(byte) 255,(byte) 255,0x00};
		System.out.println("akwejvbrh");
		spi.write(test, test.length);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new LEDCommand());
	}

}
