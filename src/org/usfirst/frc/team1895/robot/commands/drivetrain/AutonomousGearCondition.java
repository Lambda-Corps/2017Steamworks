package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

/**
 *
 */
public class AutonomousGearCondition extends ConditionalCommand {
	int var;
	
    public AutonomousGearCondition(CommandGroup onTrue, CommandGroup onFalse){
    	super(onTrue, onFalse);
    	var = 0;
    }
    
    protected boolean condition() {
    	
//    	if (Robot.gearholder.isGearPresent())
//    		return true;
//    	else
//    		return false;
    	
    	if (var==1)
    		return true;
    	else
    		return false;
    }
}
