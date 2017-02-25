package org.usfirst.frc.team1895.robot.subsystems;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
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

	// The GRIP pipeline class, code was generated by the GRIP tool
	private LiftTracker gearPipeline;
	private Thread visionThread;

	private double pos = 0.0;

	static double[] centerX;
	static double lengthBetweenTargets;
	static double angleToTarget;
	static double centerAverage;

	public static final double WIDTH_BETWEEN_TARGET = 8;
	// Not sure if distance constant will work for us
	public static final double DISTANCE_CONSTANT = 5738;

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

		gearPipeline = new LiftTracker();

		// Get the Axis camera from CameraServer

		UsbCamera liftPegCamera = CameraServer.getInstance().startAutomaticCapture();
		// Set the resolution
		// camera.setResolution(640, 480);
		// camera.setExposureManual(61);
		// camera.setBrightness(45);

		CvSink cvSink = CameraServer.getInstance().getVideo(); // capture mats
																// from camera
		CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle Stream", 640, 480); // send
																									// stream
																									// to
																									// CameraServer
		
		
		
		Mat mat = new Mat(); // define mat in order to reuse it
		
		
		visionThread = new Thread(() -> {

			while (!Thread.interrupted()) { // this should only be false when
											// shutting down
				// System.out.println(pos);
				if (cvSink.grabFrame(mat) == 0) { // fill mat with image from
													// camera TODO exception
													// handling (there is an
													// error if it returns 0)
					outputStream.notifyError(cvSink.getError()); // send an
																	// error
																	// instead
																	// of the
																	// mat
					SmartDashboard.putString("Vision State", "Couldn't grab frame");
					continue; // skip to the next iteration of the thread
			} 


				gearPipeline.process(mat); // process the mat (this does not
											// change the mat, and has an
											// internal output to pipeline

				System.out.println(gearPipeline.filterContoursOutput().size());

				// Calculate centerX
				if (gearPipeline.filterContoursOutput().size() >= 2) {
					// Draw an imaginary rectangle to find the center x and y
					// values
					Rect r1 = Imgproc.boundingRect(gearPipeline.filterContoursOutput().get(0));
					Rect r2 = Imgproc.boundingRect(gearPipeline.filterContoursOutput().get(1));
					centerX = new double[] { r1.x + (r1.width / 2), r2.x + (r2.width / 2) };

					lengthBetweenTargets = Math.abs(centerX[0] - centerX[1]);

					centerAverage = (centerX[0] + centerX[1]) / 2;
					// Calculate the angle to the target
					// angleToTarget =
					// Math.toDegrees(Math.atan((centerAverage-319.5) / 554.3));

					System.out.println("centerX:  " + centerAverage);

					// Put a rectangle on the image based off of the imaginary
					// one
					// Imgproc.rectangle(mat, new Point((r1.x-50), (r1.y-50)),
					// new Point((r1.x+50), (r1.y+50)), new Scalar(255, 255,
					// 255), 5);
					// Imgproc.rectangle(mat, new Point((r2.x-50), (r2.y-50)),
					// new Point((r2.x+50), (r2.x+50)), new Scalar(255, 255,
					// 255), 5);
				} else {
					SmartDashboard.putString("Vision State", "FAILED TO FIND TARGET");
				}
				outputStream.putFrame(mat); // Give stream (and CameraServer) a
											// new frame

				mat.release();
			}

		});
		visionThread.setDaemon(true);

	}

	public void startVisionThread() {
		visionThread.start();
	}

	public void stopVisionThread() {
		visionThread.suspend();
	}

	public void resumeVisionThread() {
		visionThread.resume();
	}

	private int detectedContours() {
		return gearPipeline.filterContoursOutput().size();
	}

	public double angleToTarget() {
		return angleToTarget;
	}

	public double getCenterX() {
		return centerAverage;
	}
}