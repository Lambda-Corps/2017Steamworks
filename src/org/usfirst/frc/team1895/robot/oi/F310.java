package org.usfirst.frc.team1895.robot.oi;
import edu.wpi.first.wpilibj.Joystick;

// Custom Gamepad class
public class F310 extends Joystick {

	private static final int numAxes = 6;
	private static final int numButtons = 10;
	
	// Constants to represent the raw button number with a human readable word
	public static final int A = 0;
	public static final int B = 1;
	public static final int X = 2;
	public static final int Y = 3;
	public static final int LB = 4; //top button
	public static final int RB = 5; //top button
	public static final int START = 6;
	public static final int SELECT = 7;
	public static final int L_AXIS = 8;
	public static final int R_AXIS = 9;
	
	//Same as above for axis
	public static final int LX = 0;
	public static final int RX = 1;
	public static final int LY = 2;
	public static final int RY = 3;
	public static final int LT = 4;
	public static final int RT = 5;
	
	private double[] scalar = new double[numAxes];
	
	private double gain = 0.0;
	
	public F310(int port) {
		//needed to call super constructor in order for F310 to extend Joystick
		super(port, numAxes, numButtons);	
		for(double i : scalar) {
			i = 1.0;
		}
	}
	
	//call enum Buttons
	public boolean getButton(int buttons) {
		if(buttons < 0 || buttons >= numButtons) return false;
		return this.getRawButton(buttons);
	}
	
	//calls enum Axis
	public double getAxes(int axis) {
		if(axis < 0 || axis >= numAxes) return 0.0;
		double x = this.getRawAxis(axis);
		return gain * Math.pow(x, 3) + (1 - gain) * x;
	}
	
	//scales and axis by calling an axis and gives it a value
	public void setScalar(int axis, double value) {
		if(axis < 0 || axis >= numAxes) return;
		scalar[axis] = value;
	}
	
	public enum Buttons {
		A(0),
		B(1),
		X(2),
		Y(3),
		LB(4), //top button
		RB(5), //top button
		START(6),
		SELECT(7),
		L_AXIS(8),
		R_AXIS(9);
		
		private final int id;
		
		private Buttons(int id) {
			this.id = id;
		}
		
		public int getID() { 
			return id;
		}
	}
	
	public enum Axis {
		LX(0), 
		RX(1), 
		LY(2), 
		RY(3), 
		LZ(4),
		RZ(5);
		
		private final int id;
		
		private Axis(int id) {
			this.id = id;
		}
		
		public int getID() { 
			return id; 
		}
	}
}
