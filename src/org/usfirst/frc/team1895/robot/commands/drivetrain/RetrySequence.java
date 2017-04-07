package org.usfirst.frc.team1895.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class RetrySequence extends CommandGroup {

    public RetrySequence() {
    	
//    	addSequential(new DriveStraightSetDistance(-10));
    	
    	//addSequential(new RetractGearHolder());
    	addSequential(new DriveStraightSetDistance(-20));  //reverse
    	//addSequential(new WaitCommand(1.0));
    	addSequential(new AlignToPeg());					//includes driving forward
    	//addSequential(new DriveStraightSetDistance(10));
    	//addSequential(new WaitCommandForAuto());
    	addSequential(new PrintCommand("about to wait"));
    	addSequential(new WaitCommand(3.0));
    	//addSequential(new AutonomousGearCondition(new RetrySequence(), new GearGoneSequence()));

    }
    
}