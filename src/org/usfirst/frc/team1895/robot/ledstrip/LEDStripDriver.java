package org.usfirst.frc.team1895.robot.ledstrip;
public class LEDStripDriver {
	
	private boolean isInstanciated = false;
	
	private LEDStrip strip;
	
	public void main(String[] args) {
		
		try {
			
			
			strip.gradient(  0,  strip.length/3, 255,   0,   0,   0, 255,   0);
			strip.gradient( strip.length/3, 2*strip.length/3,   0, 255,   0,   0,   0, 255);
			strip.gradient(2*strip.length/3, strip.length,   0,   0, 255, 255,   0,   0);
			while(true) {
				strip.shiftLayer(-1);
				strip.update();
				Thread.sleep(60);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void create() {
		if(!isInstanciated) {
			isInstanciated = true;
			strip = new LEDStrip(64);
		}
	}
	
	public LEDStrip.getInstance() {
		if()
	}
}
