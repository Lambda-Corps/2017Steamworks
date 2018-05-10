package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.gears.DefaultGearInOrOut;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Changelog:
 * 2/4/2017 (Maddy Seputro)
 *  	Description: Subsystem for the gear slot. It will be used for some commands in the Gears Commands Package
 * 			Will need:
 * 			- pneumatic cylinder 
 * 			- infrared rangefinder for sensing gear presence (so one analog port)
 * 		To do still:
 * 			- Finish methods alignToRope, climb, and moveWinch
 *  		- Add comments when you're done writing and as you write
 * 			- Add cameras if you want them
 * 			- Test
 * Added: getGearPresence method, default command, inslot_short_rangefinder
 */
public class GearHolder extends Subsystem {

	//short-range infrared rangefinder for detecting if gear is present in the slot or not
	private AnalogInput inslot_short_rangefinder;
	private final DoubleSolenoid gearholder_solenoid;
	public int placeHolder = 0;
    public int placeHolder2 = 1;
    int listLength = 0;
    double[] lastFive = new double[listLength];
    double M;//slope
	private final Compressor compressor;
	private final double GEARISINVOLTAGE = 1.15;
	boolean gearHOut;
	
    public GearHolder() {
    	inslot_short_rangefinder = new AnalogInput(RobotMap.INSLOT_SHORT_RANGEFINDER_PORT);
    	//SmartDashboard.putData("Gear Range Finder", inslot_short_rangefinder);
    	compressor = new Compressor();
    	gearholder_solenoid = new DoubleSolenoid(RobotMap.GEARHOLDER_SOLENOID_A_PORT, RobotMap.GEARHOLDER_SOLENOID_B_PORT);
    	gearHOut = false;
    }
    
    public double getVolatage(){
    	return inslot_short_rangefinder.getAverageVoltage();
    }
    
    public void moveGearHolder(){
    	if(isGearPresent() == true) {
    		System.out.println("Gear is in the slot");
    		gearholder_solenoid.set(Value.kForward);
    	}
    	else {
    		System.out.println("");
    	}
	}
    
    public boolean extendGear(){
    	if(gearHOut != true) {
    		gearholder_solenoid.set(DoubleSolenoid.Value.kForward);	
    		gearHOut = true;
    	}
    	return gearHOut;
	}
    
    public boolean retractGear() {
    	gearholder_solenoid.set(DoubleSolenoid.Value.kReverse);
    	gearHOut = false;
    	return gearHOut;
    }
    
    public boolean getGearState() {
    	return gearHOut;
    }
	

	public double roundedDistanceFinder(AnalogInput variablerangeFinder){
		double distance = 0.0; // distance(cm)
		double outputValue = variablerangeFinder.getVoltage(); //gets sensor voltage
		if (outputValue>1.6){
			M = -.175;
			distance = 10; //System.out.println("10 cm");
		}
		if (outputValue>1.3 && outputValue<=1.6){
			M = -.075;
			distance = 10;//System.out.println("10 cm");
		}
		if (outputValue>0.9 && outputValue<=1.3){
			M = -.04;
			distance = 20;//System.out.println("20 cm");
		}
		if (outputValue>0.7 && outputValue<=0.9){
			M = -0.02;
			distance = 30;//System.out.println("30 cm");
		}
		if (outputValue>0.6 && outputValue<=0.7){
			M = -0.01;
			distance = 40;//System.out.println("40 cm");
		}
		if (outputValue>0.5 && outputValue<=0.6){
			M =-0.01;
			distance = 50; //System.out.println("50cm");
		}
		if (outputValue>0.4 && outputValue<=0.5){
			M=-0.01;
			distance = 60;//System.out.println("60cm");
		}
		return distance;
    }
    
    // For: GetGearPresence Command
    // Sensors: Short-range infrared rangefinder (1)
    // Description: Detects if gear is in the slot or not using a short-range rangefinder. If the 
    // gear was detected to be present, return true. Otherwise return false.
	public boolean isGearPresent() {
		boolean bReturn = false;
		double distance = inslot_short_rangefinder.getVoltage();
		//System.out.println("-------------------------voltaeg of rangefinder: " + distance);
		if (distance > GEARISINVOLTAGE){ //Gear is captured
				bReturn = true; //insert code once gear was captured	
		}
		return bReturn;// anything else return false
    }
	
	public double getAverageVoltage() {
		return inslot_short_rangefinder.getAverageVoltage();
	}
	

    public void initDefaultCommand() {
        // To keep the gear holder retracted when not in use. 
        setDefaultCommand(new DefaultGearInOrOut());
    }
}

