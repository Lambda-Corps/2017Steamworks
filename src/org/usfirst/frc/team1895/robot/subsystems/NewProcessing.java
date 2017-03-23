package org.usfirst.frc.team1895.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NewProcessing {

	VideoCapture camera;
	Mat blurredImage = new Mat();
	Mat mask = new Mat();
	Mat hsvImage = new Mat();
	Mat frame = new Mat();
	Mat morphOut = new Mat();
	
	public NewProcessing() {
		camera = new VideoCapture(0);
		camera.open(0);
		
		if(!camera.isOpened()) {
			System.out.println("No camera");
		}
		
	}
	
 	public Rect getRect() {
		
 		Rect r = new Rect();
 		
		double minHue = (int) SmartDashboard.getNumber("minHue: ", 0);
		double maxHue = (int) SmartDashboard.getNumber("maxHue: ", 255);
		double minSat = (int) SmartDashboard.getNumber("minSat: ", 0);
		double maxSat = (int) SmartDashboard.getNumber("maxSat: ", 255);
		double minVal = (int) SmartDashboard.getNumber("minVal: ", 0);
		double maxVal = (int) SmartDashboard.getNumber("maxVal: ", 255); 
		
		SmartDashboard.getNumber("minHue: ", minHue);
		SmartDashboard.getNumber("maxHue: ", maxHue);
		SmartDashboard.getNumber("minSat: ", minSat);
		SmartDashboard.getNumber("maxSat: ", maxSat);
		SmartDashboard.getNumber("minVal: ", minVal);
		SmartDashboard.getNumber("maxVal: ", maxVal);
		
		camera.read(frame);
		Imgproc.cvtColor(frame, hsvImage, Imgproc.COLOR_BGR2HSV);
		
		Scalar minValues = new Scalar(minHue, minSat, minVal);
		Scalar maxValues = new Scalar(maxHue, maxSat, maxVal);
		
		Core.inRange(hsvImage, minValues, maxValues, mask);
		
		Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));
		Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));
		Imgproc.erode(mask, morphOut, erodeElement);
		Imgproc.dilate(morphOut, mask, dilateElement);
		
		ArrayList<MatOfPoint> contours = new ArrayList<>();
		
		Mat hierarchy = new Mat();
		
		Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
		
		//This is what outlines the contours
		if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
			for (int idx = 0; idx >= (int) hierarchy.get(0, idx)[0];) {
				Imgproc.drawContours(mask, contours, idx, new Scalar(250, 0, 0));
			}
		}
		
		try {
			r = Imgproc.boundingRect(contours.get(0));
			System.out.println("Leftmost edge: " + r.x);
			return r;
		} catch(Exception e) {}
		
		//Default
		r.set(new double[]{0.0,0.0});

		return r;
	}
 	
	
}