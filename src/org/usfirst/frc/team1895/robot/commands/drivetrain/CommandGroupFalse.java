package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CommandGroupFalse extends CommandGroup {

    public CommandGroupFalse() {
    	
    	System.out.println("False");
//    	addSequential(new DriveStraightSetDistance(10));
    	
    	addSequential(new WaitCommandForAuto1());
    	
    }
    
}