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

import edu.wpi.first.wpilibj.command.Subsystem;

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
		camera.read(frame);
		Imgproc.cvtColor(frame, hsvImage, Imgproc.COLOR_BGR2HSV);
		
		Scalar minValues = new Scalar(47, 216, 166);
		Scalar maxValues = new Scalar(115, 255, 255);
		
		Core.inRange(hsvImage, minValues, maxValues, mask);
		
		Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));
		Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));
		Imgproc.erode(mask, morphOut, erodeElement);
		Imgproc.dilate(morphOut, mask, dilateElement);
		
		ArrayList<MatOfPoint> contours = new ArrayList<>();
		
		Mat hierarchy = new Mat();
		
		Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
		
		try {
			Rect r = Imgproc.boundingRect(contours.get(0));
			System.out.println("Leftmost edge: " + r.x);
			return r;
		} catch(Exception e) {}
		
		return null;
	}
	
}