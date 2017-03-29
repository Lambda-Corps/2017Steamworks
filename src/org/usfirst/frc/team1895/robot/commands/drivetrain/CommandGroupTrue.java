package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.commands.gears.DeployGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.RetractGearHolder;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CommandGroupTrue extends CommandGroup {

    public CommandGroupTrue() {
    	
//    	addSequential(new DriveStraightSetDistance(-10));
    	
    	
    	addSequential(new RetractGearHolder());
    	addSequential(new DriveStraightSetDistance(20));  //reverse
    	addSequential(new AlignToPeg());					//includes driving forward
    	addSequential(new DriveToObstacle(14, 0.5));
    	addSequential(new DeployGearHolder());
    	addSequential(new AutonomousGearCondition(new CommandGroupTrue(), new CommandGroupFalse()));
    	addSequential(new WaitCommandForAuto2());

    }
    
}