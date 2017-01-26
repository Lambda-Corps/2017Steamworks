package org.usfirst.frc.team1895.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This subsystem handles the robot's driving. It includes methods used for both tele-op driving and automated driving commands. Specifically the commands:
 * 	- DriveStraight
 * 	- DriveWithCameras
 * 	- SwitchGears
 * 	- TurnWithCameras
 *  - TurnWithGyro
 *  Sensors needed as a result would be: encoders (2), rangefinders, cameras, gyro, and a gear sensor (exact one TBD)
 */
public class Drivetrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

