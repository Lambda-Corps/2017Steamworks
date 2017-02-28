package org.usfirst.frc.team1895.robot.ledstrip;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDSubsystem extends Subsystem {

    // Initialize your subsystem here
	
	private Thread t1;
	private LEDStrip l;
	private int state = 0;
	
	
	public final Runnable
	disabledPeriodic = () -> {
		try {
			for(int i = 0; i < 256; i += 5) {
				l.fill(0, l.length, i, 0, 0);
				l.update();
				Thread.sleep(30);
			}
			for(int i = 255; i >= 0; i -= 5) {
				l.fill(0, l.length, i, 0, 0);
				l.update();
				Thread.sleep(30);
			}
			l.update();
			Thread.sleep(130);
		} catch(Exception e) {
			e.printStackTrace();
		}
	},
	
	blinky = () -> {
		try {
			for(int i = 0; i < l.length; i++) {
				if(Math.random() > 0.26) {
					l.paint(i, (int) (Math.random()*255) , 0, 0);
				} else {
					l.paint(i, 0, 0, 0);
				}
			}
			l.update();
			Thread.sleep(130);
		} catch(Exception e) {
			e.printStackTrace();
		}
	},
	
	blinkyIncrease = () -> {
		try {
			for(double k = 0.56; k >= 0.0; k-= 0.05) {
				for(int i = 0; i < l.length; i++) {
					if(Math.random() > k) {
						l.paint(i, (int) (Math.random()*96) + 192 , 0, 0);
					} else {
						l.paint(i, 0, 0, 0);
					}
				}
				l.update();
				Thread.sleep(115);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	},
			
	fadeIn = () -> {
		try {
			for(int i = 0; i < 256; i += 5) {
				l.fill(0, l.length, i, 0, 0);
				l.update();
				Thread.sleep(30);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	},
	
	fadeOut = () -> {
		try {
			for(int i = 255; i >= 0; i -= 5) {
				l.fill(0, l.length, i, 0, 0);
				l.update();
				Thread.sleep(30);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	},
			
	setRebecca = () -> {
		try {
			l.fill(0, 4, 255, 0, 0);
			l.fill(5, 8, 255, 0, 0);
			l.fill(9, 11, 255, 0, 0);
		} catch(Exception e) {
			e.printStackTrace();
		}
	},
			
	runRebecca = () -> {
		try {
			l.shift(1);
			Thread.sleep(50);
		} catch(Exception e) {
			e.printStackTrace();
		}
	};
	
    public LEDSubsystem() {
    	l = new LEDStrip(32);
		
		t1 = new Thread();
    }

    public void start(Runnable e) {
		if(!t1.isAlive()) {
			t1 = new Thread(e);
			t1.start();
		}
	}
    
	public void queue(Runnable e) {
		try {
			t1.join();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		start(e);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new LEDCommand());
    }
}
