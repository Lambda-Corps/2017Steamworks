package org.usfirst.frc.team1895.robot.ledstrip;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.SPI;

public class LEDStripDriver extends Thread {
	
	private static LEDStripDriver instance;
	
	private int totalLength;
	private List<LEDStrip> ledStrips;
	
	private SPI spi;
	
	
	/**
	 * Since this class should never be instanciated outside of the instance
	 * created by getInstance(), the constructor is private. All instance variables
	 * (besides instance) are instanciated here. When a LEDStrip is created, this
	 * thread will automatically start.
	 */
	private LEDStripDriver() {
		totalLength = 0;
		ledStrips  = new ArrayList<LEDStrip>();
		spi = new SPI(SPI.Port.kOnboardCS0);
		start();
	}
	
	/**
	 * This should only be called from wihin LEDStrip! When a LEDStrip
	 * is created in another thread, it will call this method in its
	 * constructor. The LEDStrip is added to a list so we can get data
	 * from it.
	 * 
	 * @param	strip
	 * @param	numberOfLEDs
	 */
	public synchronized void addStrip(LEDStrip strip, int numberOfLEDs) {
		ledStrips.add(strip);
		totalLength += numberOfLEDs * 3;
	}
	
	/**
	 * Get the instance of LEDStripDriver. If the instance does not exist yet,
	 * create the instance and return it.
	 * 
	 * @return	the instance of LEDStripDriver
	 */
	public static synchronized LEDStripDriver getInstance() {
		
		// If the instance does NOT exist, instance is instanciated then returned.
		// If it does, then return it.
		return instance == null ? instance = new LEDStripDriver() : instance;
	}
	
	/**
	 * Overriden method from Thread. When the thread is enables, this will write to the spi at
	 * around 33 Hz.
	 */
	@Override
	public void run() {
		while(true) {
			// :D
			//System.out.println("Hello from a thread!");
			
			 // Counter variable to track where in the packet we are
			int a = 0;
			
			// This will be written to the SPI bus. Since every LED represents
			// three bytes, the length of this is three times the length
			// of the strip we are using, plus one for the end byte.
			byte[] packet = new byte[totalLength];
			
			// Gather the data from each strip and put it in the packet array.
			for(int led = 0; led < ledStrips.size(); led++) {
				//System.out.println(led);
				byte[] data = ledStrips.get(led).update();
				for(int i = 0; i < data.length; i++, a++) {
					packet[a] = data[i];
				}
			}
			
			// Write to the spi port on the roboRIO the data for the LEDs.
			// If packet.length is incorrect, nothing bad happens.
			spi.write(packet, packet.length);
			
			// End byte, tells the LED strip we are now done writing to it
			spi.write(new byte[]{ 0x00}, 1);

			
			// Sleep for 30 milliseconds, which will make the thread update at 33.3 Hz
			try {
				Thread.sleep(30); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
