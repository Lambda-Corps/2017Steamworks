package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.gears.RetractGearHolder;

import edu.wpi.first.wpilibj.AnalogInput;
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
	
    public GearHolder() {
    	inslot_short_rangefinder = new AnalogInput(RobotMap.INSLOT_SHORT_RANGEFINDER_PORT);
    }
    
    // For: GetGearPresence Command
    // Sensors: Short-range infrared rangefinder (1)
    // Description: Detects if gear is in the slot or not using a short-range rangefinder. If the 
    // gear was detected to be present, return true. Otherwise return false.
    public boolean getGearPresence() {
    	return false;
    }

    public void initDefaultCommand() {
        // To keep the gear holder retracted when not in use. 
        setDefaultCommand(new RetractGearHolder());
    }
}

