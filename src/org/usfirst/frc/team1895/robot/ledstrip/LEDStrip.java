package org.usfirst.frc.team1895.robot.ledstrip;
import java.util.ArrayList;

public class LEDStrip {

	// The main variable that holds the length of the strip. No
	// different than length variable, but more easily readable.
	private final int numberOfLEDs;
	
	// This is the variable that OTHER classes will access, so it
	// is public. This variable is no different than numberOfLEDs,
	// otherwise something is fatally wrong.
	public final int length;
	
	// When dealing with layers, we need to know the number of layers
	// and what layer we are currently modifying, hence the variable names
	private int current_layer = 0;
	private int numberOfLayers = 1;
	
	// The array that actaully holds the information for all of the LEDs
	// on the strip. As for the individual colors, the LED instance at any
	// index will deal with that.
	private LED[] strip;
	
	//NOT WORKING YET
	private ArrayList<LED[]> save_data = new ArrayList<LED[]>();
	
	
	// The LED strip does NOT work by feeding it 0x00-0xff directly. Instead, it
	// needs to me mapped to a value it actually can display. We do this by making
	// an array called GAMMA, which simply maps a color value (0-255) as an index,
	// and the value of that index is what the LED strip will like :D
	// Code was not written by anyone on the team, so the exact logic is unkown to me
	private static final int GAMMA_LENGTH = 256;
	private static final short[] GAMMA = new short[GAMMA_LENGTH];
	static {
        for (int i = 0; i < GAMMA_LENGTH; i++) {
			int j = (int) (Math.pow(((float) i) / 255.0, 2.5) * 127.0 + 0.5);
			GAMMA[i] = (byte) (0x80 | j);
        }
    }
	
	/**
	 * LEDStrip can handle normal color setting along with transparancy and layers.
	 * Transparancy is only effective on layers other than the first layer (layer 0).
	 * Layers will overwrite colors on lower layers unless given a transparancy, in
	 * which case it will blend the colors. :D
	 */
	public LEDStrip(int numberOfLEDs) {
		this.numberOfLEDs = numberOfLEDs;
		length = numberOfLEDs;
		strip = new LED[numberOfLEDs];
		for(int i = 0; i < numberOfLEDs; i++) {
			strip[i] = new LED();
		}
		LEDStripDriver.getInstance().addStrip(this, numberOfLEDs);
		
	}
	/**
	 * Sets the color of an individual LED with the colors given in the arguments.
	 * This is the overloaded method that does not deal with transparency.
	 */
	public void paint(int id, int red, int green, int blue) {
		paint(id, red, green, blue, 255);
	}
	/**
	 * Sets the color of an individual LED with the colors and transparency given 
	 * in the arguments.
	 */
	public void paint(int id, int red, int green, int blue, int alpha) {
		strip[id].set(red, green, blue, alpha);
	}

	/**
	 * Fills the LEDs from start to end with a solid color
	 */
	public void fill(int start, int end, int red, int green, int blue) {
		fill(start, end, red, green, blue, 255);
	}

	/**
	 * Fills the LEDs from start to end with a solid color
	 * */
	public void fill(int start, int end, int red, int green, int blue, int alpha) {
		for(int i = start; i < end; i++) {
			if(i < 0 || i >= numberOfLEDs) continue;
			paint(i, red, green, blue, alpha);
		}
	}

	/**
	 * Sets the LEDs between start and end to a gradient
	 */
	public void gradient(int start, int end, int red1, int green1, int blue1, int red2, int green2, int blue2) {
		gradient(start, end, red1, green1, blue1, 255, red2, green2, blue2, 255);
	}

	/**
	 * Sets the LEDs between start and end to a gradient
	 */
	public void gradient(int start, int end, int red1, int green1, int blue1, int alpha1, int red2, int green2, int blue2, int alpha2) {
		int length = end - start;
		for(int i = start; i < end; i++) {
			if(i < 0 || i >= numberOfLEDs) continue;
			int red   = (int) ((red2   - red1  )/length * (i-start) * 1.0 + red1  );
			int green = (int) ((green2 - green1)/length * (i-start) * 1.0 + green1);
			int blue  = (int) ((blue2  - blue1 )/length * (i-start) * 1.0 + blue1 );
			int alpha = (int) ((alpha2 - alpha1)/length * (i-start) * 1.0 + alpha1);
			paint(i, red, green, blue, alpha);
		}
	}

	/**
	 * Shifts all layers a certain amount of LEDs up or  down the strip
	 */
	public void shift(int number) {
		number %= numberOfLEDs;
		LED[] tmp = new LED[Math.abs(number)];
		if(number > 0) {
			for(int i = 0; i < tmp.length; i++) tmp[i] = strip[strip.length - i - 1];
			for(int i = numberOfLEDs - 1; i >= number; i--) strip[i] = strip[i - number];
			for(int i = 0; i < tmp.length; i++) strip[i] = tmp[tmp.length - i - 1];
		} else if(number < 0) {
			for(int i = 0; i < tmp.length; i++) tmp[i] = strip[i];
			for(int i = 0; i < numberOfLEDs + number; i++) strip[i] = strip[i - number];
			for(int i = 0; i < tmp.length; i++) strip[strip.length - i - 1] = tmp[tmp.length - i - 1];
		}
	}
	
	/**
	 * Shifts the current layer a certain amount of LEDs up or  down the strip
	 */
	public void shiftLayer(int number) {
		number %= numberOfLEDs;
		short[][] tmp = new short[Math.abs(number)][4];
		if(number > 0) {
			for(int i = 0; i < tmp.length; i++) tmp[i] = strip[strip.length - i - 1].getLayer();
			for(int i = numberOfLEDs - 1; i >= number; i--) strip[i].setLayer(strip[i - number].getLayer());
			for(int i = 0; i < tmp.length; i++) strip[i].setLayer(tmp[tmp.length - i - 1]);
		} else if(number < 0) {
			for(int i = 0; i < tmp.length; i++) tmp[i] = strip[i].getLayer();
			for(int i = 0; i < numberOfLEDs + number; i++) strip[i].setLayer(strip[i - number].getLayer());
			for(int i = 0; i < tmp.length; i++) strip[strip.length - i - 1].setLayer(tmp[tmp.length - i - 1]);
		}
	}
	
	/**
	 * Not working yet, do not call!!!
	 * 
	 * @deprecated
	 */
	public void save() {
		LED[] tmp = new LED[numberOfLEDs];
		for(int i = 0; i < numberOfLEDs; i++) tmp[i] = new LED();
		for(int i = 0; i < numberOfLEDs; i++) {
			tmp[i].set((int)strip[i].getRed(), (int)strip[i].getGreen(), (int)strip[i].getBlue(), 255);
		}
		save_data.add(tmp);
		System.out.println(java.util.Arrays.toString(strip));
	}
	
	/**
	 * Not working yet, do not call!!!
	 * 
	 * @deprecated
	 */
	
	public void load(int id) {
		if(id < 0 || id >= save_data.size()) return;
		strip = save_data.get(id).clone();
		
	}

	/**
	 * Add a new layer, and change the current layer to the new layer
	 */
	public void addLayer() {
		numberOfLayers++;
		for(int i = 0; i < numberOfLEDs; i++) {
			strip[i].addLayer();
		}
		current_layer = numberOfLayers-1;
	}

	/**
	 * Not yet implemented, this does absolutely nothing.
	 */
	public void removeLayer(int layer) {
		//TODO: add stuff
	}

	/**
	 * Switch the current layer
	 */
	public void changeLayer(int layer) {
		if(layer < 0 || layer >= numberOfLayers) return;
		current_layer = layer;
	}

	/**
	 * Returns the main LED[]
	 */
	public LED[] get() {
		return strip;
	}

	/**
	 * Returns a specific LED from the main LED[]
	 */
	public LED get(int id) {
		return strip[id];
	}

	/**
	 * This method is only useful for the LEDStripDriver. This return the
	 * data for the SPI.
	 * 
	 * @return new byte[]
	 */
	public byte[] update() {
		byte packet[] = new byte[numberOfLEDs * 3];
		for(int i = 0; i < numberOfLEDs; i++) {
			packet[(i * 3)    ] = (byte) strip[i].getGreen();
			packet[(i * 3) + 1] = (byte) strip[i].getRed();
			packet[(i * 3) + 2] = (byte) strip[i].getBlue();
		}
		return packet;
	}

	/**
	 * Ovverides Object.toString()
	 */
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < numberOfLEDs; i++) {
			int red   = strip[i].getRed()   < 0 ? 256 + strip[i].getRed()  : strip[i].getRed();
			int green = strip[i].getGreen() < 0 ? 256 + strip[i].getGreen(): strip[i].getGreen();
			int blue  = strip[i].getBlue()  < 0 ? 256 + strip[i].getBlue() : strip[i].getBlue();
			s += String.format("%d: %3d, %3d, %3d\n", i, red, green, blue);
		}
		return s;
	}

	/**
	 * LED backend that handles holding the color and setting new colors, layers, and transparancy.
	 */
	private class LED {
		
		private ArrayList<short[]> layer = new ArrayList<short[]>();
		private short red   = 0;
		private short green = 0;
		private short blue  = 0;
		
		private LED() {
			addLayer();
		}
		
		private void addLayer() {
			layer.add(new short[]{ 0, 0, 0, 0});
		}
		
		private void set(int red, int green, int blue, int alpha) {
			if((red < 0 || red > 255) || (green < 0 || green > 255) || (blue < 0 || blue > 255)) return;
			
			if(alpha < 0) alpha = 0;
			if(alpha > 255) alpha = 255;
			
			layer.get(current_layer)[0] = (short) (red   * alpha / 255.0 + layer.get(current_layer)[0] * (255 - alpha) / 255.0);
			layer.get(current_layer)[1] = (short) (green * alpha / 255.0 + layer.get(current_layer)[1] * (255 - alpha) / 255.0);
			layer.get(current_layer)[2] = (short) (blue  * alpha / 255.0 + layer.get(current_layer)[2] * (255 - alpha) / 255.0);
			layer.get(current_layer)[3] = (short)  alpha;
			
			updateLED();
		}
		
		private void updateLED() {
			if(layer.get(current_layer)[0] > 255) layer.get(current_layer)[0] = (short) 255;
			if(layer.get(current_layer)[1] > 255) layer.get(current_layer)[1] = (short) 255;
			if(layer.get(current_layer)[2] > 255) layer.get(current_layer)[2] = (short) 255;
			
			red   = layer.get(0)[0];
			green = layer.get(0)[1];
			blue  = layer.get(0)[2];
			
			for(int i = 1; i < numberOfLayers; i++) {
				red   = (short) (layer.get(i)[0] * layer.get(i)[3] / 255.0 + red   * (255 - layer.get(i)[3]) / 255.0);
				green = (short) (layer.get(i)[1] * layer.get(i)[3] / 255.0 + green * (255 - layer.get(i)[3]) / 255.0);
				blue  = (short) (layer.get(i)[2] * layer.get(i)[3] / 255.0 + blue  * (255 - layer.get(i)[3]) / 255.0);
			}
			
			if(red   > 255) red   = (short) 255;
			if(green > 255) green = (short) 255;
			if(blue  > 255) blue  = (short) 255;
			
			if(red   < 0) red   = (short) 0;
			if(green < 0) green = (short) 0;
			if(blue  < 0) blue  = (short) 0;
		}
		
		private short[] getLayer() {
			short[] tmp = new short[4];
			for(int i = 0; i < tmp.length; i++) {
				tmp[i] = layer.get(current_layer)[i];
			}
			return tmp;
		}
		
		private void setLayer(short[] a) {
			layer.remove(current_layer);
			layer.add(current_layer, a);
			updateLED();
		}
		
		private short getRed()   { return GAMMA[red  ];}
		private short getGreen() { return GAMMA[green];}
		private short getBlue()  { return GAMMA[blue ];}
	}
}