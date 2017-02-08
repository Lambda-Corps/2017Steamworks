package org.usfirst.frc.team1895.robot.ledstrip;
public class LEDStripDriver {
	
	public static final boolean isHardware = false;
	
	public static void main(String[] args) {
		
		try {
			LEDStrip l = new LEDStrip(64, isHardware);
			
			l.gradient(  0,  l.length/3, 255,   0,   0,   0, 255,   0);
			l.gradient( l.length/3, 2*l.length/3,   0, 255,   0,   0,   0, 255);
			l.gradient(2*l.length/3, l.length,   0,   0, 255, 255,   0,   0);
			while(true) {
				l.shiftLayer(-1);
				l.update();
				Thread.sleep(60);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
