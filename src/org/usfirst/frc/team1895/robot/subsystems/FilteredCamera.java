package org.usfirst.frc.team1895.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
//import org.usfirst.frc.team1897.robot.commands.ExampleCommand;

//import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * This class will process the the images coming in from the camera, send the 
 * images through the GRIP filter pipeline and finally send the processed
 * images to an MJPEGSTREAM in order to view the output on the smart
 * dashboard.
 */

public class FilteredCamera extends Subsystem {

	public void initDefaultCommand() {
		// Set the default command for the subsystem here.
		// setDefaultCommand(new ExampleCommand());
	}

	Thread visionThread;

	Mat mask;
	Mat hsvImage;
	Mat frame;
	Mat morphOutput;
	
	int minHue =   0;
	int minSat =   0;
	int minVal =   0;
	int maxHue = 255;
	int maxSat = 255;
	int maxVal = 255;
	
	static double centerX;
	static double lengthBetweenTargets;
	static double angleToTarget;
	static double horizontalOffset;
	static double measuredWidth;
	static double pixelsPerInch;
	double exposure;
	public static final double WIDTH_BETWEEN_TARGET = 8;
	public static final double WIDTH_OF_TAPE = 2; // INCHES
	
	public FilteredCamera() {		
		startGearVisionThread();
	}

	// This method is the true "constructor" of the camera. All we really want
	// is to have the robot
	// spawn an individual thread to do the vision processing. The Pipeline was
	// all setup through
	// GRIP.
	//
	// Grab the filtered image from the grip pipeline, overlay a rectangle onto
	// the Mat object and
	// write the image to the output stream that should be available for display
	// on the smart dashboard.
	private void startGearVisionThread() {
		UsbCamera liftPegCamera = CameraServer.getInstance().startAutomaticCapture();
		
		exposure = SmartDashboard.getNumber("Exposure: ");
		SmartDashboard.putNumber("Read Exposure: ", exposure);
		
		liftPegCamera.setExposureManual(150);

		visionThread = setUpVisionThread();
		visionThread.setDaemon(true);

	}

	public void startVisionThread() {
		visionThread.start();
	}
	
	private Thread setUpVisionThread() {
		
		CvSink cvSink = CameraServer.getInstance().getVideo(); // capture mats
																// from camera
		Mat mat = new Mat(); // define mat in order to reuse it
		CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle Stream", 640, 480); // send
																									// stream
																									// to
																									// CameraServer
		//instantiates and returns Thread object
		return new Thread(() -> {

			while (!Thread.interrupted()) { // this should only be false when
											// shutting down
				if (cvSink.grabFrame(mat) == 0) { // fill mat with image from
													// camera TODO exception
													// handling (there is an
													// error if it returns 0)
					outputStream.notifyError(cvSink.getError());    // send an
																	// error
																	// instead
																	// of the
																	// mat
					SmartDashboard.putString("Vision State", "Couldn't grab frame");
					continue; // skip to the next iteration of the thread
				}
				
				
				////////////////////////////////////////////////////////////////////////////////
				Imgproc.cvtColor(frame, hsvImage, Imgproc.COLOR_BGR2HSV);
				
				//Scalar minValues = new Scalar(SmartDashboard.getNumber("min hue",   0), SmartDashboard.getNumber("min sat",   0), SmartDashboard.getNumber("min val",   0));
				//Scalar maxValues = new Scalar(SmartDashboard.getNumber("max hue", 255), SmartDashboard.getNumber("max sat", 255), SmartDashboard.getNumber("max val", 255));
				
				minHue = (int) SmartDashboard.getNumber("min hue",   0);
				minSat = (int) SmartDashboard.getNumber("min sat",   0);
				minVal = (int) SmartDashboard.getNumber("min val",   0);
				maxHue = (int) SmartDashboard.getNumber("max hue", 255);
				maxSat = (int) SmartDashboard.getNumber("max sat", 255);
				maxVal = (int) SmartDashboard.getNumber("max val", 255);
				
				
				Scalar minValues = new Scalar(minHue, minSat, minVal);
				Scalar maxValues = new Scalar(maxHue, maxSat, maxVal);
				
				//Ethan's gets
				SmartDashboard.putNumber("max hue", maxHue);
				SmartDashboard.putNumber("max sat", maxSat);
				SmartDashboard.putNumber("max val", maxVal);
				SmartDashboard.putNumber("min hue", minHue);
				SmartDashboard.putNumber("min sat", minSat);
				SmartDashboard.putNumber("min val", minVal);
				
				//Scalar minValues = new Scalar(minHue, minSat, minVal);
				//Scalar maxValues = new Scalar(maxHue, maxSat, maxVal);
				Core.inRange(hsvImage, minValues, maxValues, mask);
				
				Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8,8));
				Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8,8));
				Imgproc.erode(mask, morphOutput, erodeElement);
				Imgproc.dilate(morphOutput, mask, dilateElement);
				
				ArrayList<MatOfPoint> contours = new ArrayList<>();
				Mat hierarchy = new Mat();
				Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
				
				if (hierarchy.size().height > 0 && hierarchy.size().width > 0){
					for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
						Imgproc.drawContours(mask, contours, idx, new Scalar(250, 0, 0));
					}
				}  
				
				centerX = 0.0;
				try {
					Rect r1 = Imgproc.boundingRect(contours.get(0));
					centerX += (r1.x + r1.width/2);
				} catch(Exception e) {}
				
				try {
					Rect r2 = Imgproc.boundingRect(contours.get(1));
					centerX += (r2.x + r2.width/2);
				} catch(Exception e) {}
				
				//////////////////////////////////////////////
				centerX /= 2;
				
				outputStream.putFrame(mask);
			}
		});
	}

	public void stopVisionThread() {
		visionThread.suspend();
	}
	
	public void stopThread() {
		visionThread.stop();
	}

	public void resumeVisionThread() {
		visionThread.resume();
	}

	public double angleToTarget() {
		return angleToTarget;
	}

	public double getAvgCenterX() {
		return centerX;
	}

	public double getOffset() {
		return horizontalOffset;
	}
}