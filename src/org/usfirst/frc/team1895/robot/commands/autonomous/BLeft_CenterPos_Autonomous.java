package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.AlignToPeg;
import org.usfirst.frc.team1895.robot.commands.drivetrain.AutonomousGearCondition;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightSetDistance;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.drivetrain.GearGoneSequence;
import org.usfirst.frc.team1895.robot.commands.drivetrain.RetrySequence;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Changelog:
 * 2/7/2017 (Maddy Seputro)
 * 		Description: Autonomous plan should the robot be placed in the center position during competition.
 * 		Essentially, the robot drives up, puts a gear on the peg, backs away, rotates to the left, drives
 * 		around the airship, shifts into high gear, crosses the baseline and drives into the neutral zone.
 * 		NOTE: This autonomous plan does not shoot.  
 * 			List of High-Level Steps:
 * 			1. Drive forward [] feet using DriveStraightSeteDistance Command to the lift. 
 * 			2. Use AlignToHighGoal Command to accurately drive up to lift.
 * 			3. Use DeployGearHolder Command to send forth the gearholder and get the gear on the peg.
 * 			4. After it is confirmed to be attached, GetGearPresence Command should return false. 
 * 			5. Use RetractGearHolder method to pull the gearholder back.
 * 			6. Back away [] feet using DriveStraightSeteDistance Command. 
 * 			7. Turn left 30 degrees using TurnWithGyro Command.
 * 			8. Use DriveStraightSeteDistance Command to drive [] feet.
 * 			9. Turn right 30 degrees to the right using TurnWithGyro Command so that you're facing forward again.
 * 			10. Use ShiftGears Command to shift into high gear to allow for faster movement.
 * 			11. Use DriveStraightSeteDistance Command to drive [] feet to pass the baseline and head into 
 * 				the neutral zone.  
 * 		Field Pieces needed:
 * 			- 1 preloaded gear
 * 			- 10 preloaded fuel (to be used once tele-op starts)
 * 		Estimated Time needed to complete: [] SECONDS
 * 		To do still:
 * 			- TEST TEST TEST
 */
public class BLeft_CenterPos_Autonomous extends CommandGroup {
  
    public BLeft_CenterPos_Autonomous() {
 	
    	//UPDATED WITH RETRY CODE
    	
    	//addSequential(new DriveStraightWithoutPID(0.5, -20));
    	//addSequential(new TurnWithoutPID(45.0, 0.5));
    	//mock autonomous
    	addSequential(new DriveStraightSetDistance(20));
    	//addSequential(new DriveToObstacle(24, 0.2));		//should be 110 inches forward now
    	//addSequential(new AlignToPeg());
    	//addSequential(new WaitCommand(1.0));
    	//addSequential(new DriveStraightSetDistance(-7));
    	//should re-adjust if necessary
    	//addSequential(new DriveToObstacle(23, 0.15)); 		//should this be larger and then replace with driveStraight?
    	//addSequential(new DeployGearHolder());
    	//addSequential(new WaitUntilGearGone());
    	//addSequential(new WaitCommandForAuto());
    	//addSequential(new WaitCommand(1.0));
    	addSequential(new AlignToPeg());
    	addSequential(new PrintCommand("I'm Aligned!"));
    	//addSequential(new WaitCommand(1.0));
    	addSequential(new DriveToObstacle(20, 0.2));
    	addSequential(new WaitCommand(3.0));
    	addSequential(new AutonomousGearCondition(new RetrySequence(), new GearGoneSequence()));
//    	addSequential(new DriveStraightSetDistance(50));

     	/*addSequential(new DriveStraightSetDistance(30));
     	addSequential(new TurnWithGyro(-60));
     	addSequential(new DriveStraightSetDistance(-50));
     	addSequential(new TurnWithGyro(60));
     	addSequential(new DriveStraightSetDistance(-50));*/
 
    	
    }
}
