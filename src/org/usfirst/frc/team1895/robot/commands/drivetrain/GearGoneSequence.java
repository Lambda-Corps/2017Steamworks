package org.usfirst.frc.team1895.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class GearGoneSequence extends CommandGroup {

    public GearGoneSequence() {
    	
//    	addSequential(new DriveStraightSetDistance(10));
    	
    	addSequential(new WaitCommand(3.0));
    	addSequential(new PrintCommand("I'm done waiting in GearGoneSequence"));
    	
    }
    
}