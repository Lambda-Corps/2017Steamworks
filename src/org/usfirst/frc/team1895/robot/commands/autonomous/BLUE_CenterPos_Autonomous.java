package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.AlignToPeg;
import org.usfirst.frc.team1895.robot.commands.drivetrain.AutonomousGearCondition;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightSetDistance;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.drivetrain.GearGoneSequence;
import org.usfirst.frc.team1895.robot.commands.drivetrain.RetrySequence;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnOnLEDRing;
import org.usfirst.frc.team1895.robot.commands.gears.DeployGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.RetractGearHolder;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 *
 */
public class BLUE_CenterPos_Autonomous extends CommandGroup {

    public BLUE_CenterPos_Autonomous() {
        
    	//UPDATED WITH RETRY CODE
    	addSequential(new TurnOnLEDRing());
    	addSequential(new DriveStraightSetDistance(-34));
    	addSequential(new PrintCommand("Done driving straight with PID"));
    	//addSequential(new WaitCommand(1.0));
    	addSequential(new AlignToPeg());
    	addSequential(new PrintCommand("I'm Aligned!"));
    	//addSequential(new WaitCommand(1.0));
    	addSequential(new DeployGearHolder());
    	addSequential(new PrintCommand("About to start retry"));
    	addSequential(new AutonomousGearCondition(new RetrySequence(), new GearGoneSequence()));
    	addSequential(new PrintCommand("done with retry"));
//    	addSequential(new DriveStraightSetDistance(50));
    	
    	
//    	addSequential(new DriveToObstacle(14, .4));
//    	addSequential(new DriveStraightSetDistance(-54));
    	//addSequential(new DriveToObstacle(24, 0.6));		//should be 110 inches forward now
    	//addSequential(new AlignToPeg());
    	//should re-adjust if necessary
//    	addSequential(new DriveToObstacle(14, 0.15)); 		//should this be larger and then replace with driveStraight?
//    	addSequential(new DeployGearHolder());
//    	addSequential(new WaitUntilGearGoneOrTimeOut(4));
    }
}
