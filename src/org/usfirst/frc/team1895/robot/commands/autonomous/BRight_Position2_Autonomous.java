package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightSetDistance;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.gears.DeployGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.RetractGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.WaitUntilGearGone;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BRight_Position2_Autonomous extends CommandGroup {

    public BRight_Position2_Autonomous() {
        
    	
    	addSequential(new DriveToObstacle(14, .4));
//    	addSequential(new DriveStraightSetDistance(-54));
    	//addSequential(new DriveToObstacle(24, 0.6));		//should be 110 inches forward now
    	//addSequential(new AlignToPeg());
    	//should re-adjust if necessary
//    	addSequential(new DriveToObstacle(14, 0.15)); 		//should this be larger and then replace with driveStraight?
//    	addSequential(new DeployGearHolder());
//    	addSequential(new WaitUntilGearGoneOrTimeOut(4));
//    	 
//    	addSequential(new RetractGearHolder());
     	/*addSequential(new DriveStraightSetDistance(30));
     	addSequential(new TurnWithGyro(60));
     	addSequential(new DriveStraightSetDistance(-50));
     	addSequential(new TurnWithGyro(-60));
     	addSequential(new DriveStraightSetDistance(-50));*/
    }
}
