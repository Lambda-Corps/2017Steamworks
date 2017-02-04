package org.usfirst.frc.team1895.robot;

import edu.wpi.first.wpilibj.Joystick;

/** 
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

/**
 * Changelog:
 * 
 * 1/27/2017: Created The left and right joysticks to be used for a tank drive setup
 *            (Ethan Passmore)
 *
 */

public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	// Create the joysticks here. Until the specialized classes are implemented, 
	// use the Joystick class from wpilib.
	public Joystick  leftArcadeJoystick;
	public Joystick rightArcadeJoystick;
	
	public OI() {
		// The constructor wants the port the joystick is connected to in the
		// driver's station
		//TODO: replace Joystick with ArcadeJoystick or F310 once implemented
		 leftArcadeJoystick = new Joystick(RobotMap.LEFT_JOYSTICK_PORT);
		rightArcadeJoystick = new Joystick(RobotMap.RIGHT_JOYSTICK_PORT);
	}
}
