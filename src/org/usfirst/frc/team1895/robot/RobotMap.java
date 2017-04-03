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
 * 2/4/2017:  Added motor ports for the shooter and winch subsystems, changed gyro port to 0, 
 * 			  added 3 rangefinders and renamed an existing one.
 * 			  (Maddy Seputro)
 *
 */

public class RobotMap {
	
	//Analog IO
		public static final int GYRO_PORT = 0;
		public static final int INSLOT_SHORT_RANGEFINDER_PORT = 2;
		public static final int MIDDLE_FR_SHORT_RANGEFINER_PORT = 1;
		public static final int LEFT_FR_LONG_RANGEFINDER_PORT = 3;
		public static final int RIGHT_FR_LONG_RANGEFINDER_PORT = 4;
	
	//CAN Bus
		public static final int LEFT_MOTOR1_PORT = 4;
		public static final int LEFT_MOTOR2_PORT = 5;
		public static final int LEFT_MOTOR3_PORT = 6;
		public static final int RIGHT_MOTOR1_PORT = 1;
		public static final int RIGHT_MOTOR2_PORT = 2;
		public static final int RIGHT_MOTOR3_PORT = 3;
		public static final int WINCH_MOTOR_PORT = 7;
		public static final int REGULATOR_MOTOR_PORT = 8;
		public static final int SHOOTER_MOTOR_PORT = 9;
		
	//Solenoids
		public static final int SHOOTER_SOLENOID_A_PORT = -1;
		public static final int SHOOTER_SOLENOID_B_PORT = -1;
		public static final int GEARHOLDER_SOLENOID_A_PORT = 3;
		public static final int GEARHOLDER_SOLENOID_B_PORT = 1;
		public static final int DRIVETRAIN_SOLENOID_A_PORT = 5;
		public static final int DRIVETRAIN_SOLENOID_B_PORT = 4;
		
	//Digital IO
		public static final int RIGHT_GEARBOX_ENCODER_A_PORT = 0;
		public static final int RIGHT_GEARBOX_ENCODER_B_PORT = 1;
		public static final int LEFT_GEARBOX_ENCODER_A_PORT = 3;
		public static final int LEFT_GEARBOX_ENCODER_B_PORT = 2;
		public static final int WINCH_ENCODER_A_PORT = 4;
		public static final int WINCH_ENCODER_B_PORT = 5;
	
	//Driver's Station IO
		public static final int  LEFT_JOYSTICK_PORT = 0;
		public static final int RIGHT_JOYSTICK_PORT = 1;
		public static final int GAMEPAD_PORT = 0;
		public static final int GAMEPAD2_PORT = 1;
		
	//Cameras
		public static final String f_camera = "f_camera";
		public static final String b_camera = "b_camera";
		public static final int num_cams = 2;
}
