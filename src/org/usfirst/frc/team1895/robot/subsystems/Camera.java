package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

//import com.ni.vision.NIVision;
//import com.ni.vision.NIVision.Image;
//import com.ni.vision.VisionException;
//
//import edu.wpi.first.wpilibj.CameraServer;
//import edu.wpi.first.wpilibj.image.NIVisionException;
/**
 *
 */
public class Camera extends Subsystem {

//	private final int f_camera;
//	private final int b_camera;
    
	public Camera() {
    	//f_camera = NIVision.IMAQdxOpenCamera(RobotMap.f_camera, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

