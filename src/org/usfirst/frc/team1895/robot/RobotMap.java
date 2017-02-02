package org.usfirst.frc.team1895.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

/*
 * Changelog:
 * 
 * 1/27/2017: Created ports for the six motors on the drivetrain. All of the ports are set to zero until the
 *            CANTalon ids are set.
 *            Added ports for the two arcade joysticks.
 *            (Ethan Passmore)
 * 1/28/2017: Changed port numbers to what is set up on the electronics board.
 *
 */

public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static final int leftMotor = 1;
	// public static final int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static final int rangefinderPort = 1;
	// public static final int rangefinderModule = 1;
	
	//Analog IO
		public static final int RANGEFINDER_PORT = 1;
		public static final int GYRO_PORT = 2;
	
	//CAN Bus
		public static final int FS_MOTOR1_PORT = 4;
		public static final int FS_MOTOR2_PORT = 5;
		public static final int FS_MOTOR3_PORT = 6;
		public static final int GS_MOTOR1_PORT = 1;
		public static final int GS_MOTOR2_PORT = 2;
		public static final int GS_MOTOR3_PORT = 3;
	
	//Digital IO
		public static final int FS_GEARBOX_ENCODER_A_PORT = 1;
		public static final int FS_GEARBOX_ENCODER_B_PORT = 2;
		public static final int GS_GEARBOX_ENCODER_A_PORT = 3;
		public static final int GS_GEARBOX_ENCODER_B_PORT = 4;
		public static final int WINCH_ENCODER_A_PORT = 5;
		public static final int WINCH_ENCODER_B_PORT = 6;
		public static final int SHOOTER_ENCODER_A_PORT = 7;
		public static final int SHOOTER_ENCODER_B_PORT = 8;
	
	//Driver's Station IO
		public static final int  LEFT_JOYSTICK_PORT = 0;
		public static final int RIGHT_JOYSTICK_PORT = 1;
}
