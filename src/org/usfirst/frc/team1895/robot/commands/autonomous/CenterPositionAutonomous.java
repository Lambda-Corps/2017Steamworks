package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightSetDistance;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.drivetrain.StopRobot;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1895.robot.commands.gears.DeployGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.RetractGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.WaitUntilGearGoneOrTimeOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

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
 * 		Commands needed: 
 * 			- DriveStraightSeteDistance, AlignToHighGoal, DeployGearHolder, GetGearPresence, RetractGearHolder,
 * 				TurnWithGyro, ShiftGears
 * 		Field Pieces needed:
 * 			- 1 preloaded gear
 * 			- 10 preloaded fuel (to be used once tele-op starts)
 * 		Estimated Time needed to complete: [] SECONDS
 * 		To do still:
 * 			- Finish the other commands so we can add them in
 * 			- TEST TEST TEST
 */
public class CenterPositionAutonomous extends CommandGroup {

    public CenterPositionAutonomous() {
 
    	//addSequential(new DriveStraightSetDistance(-15));
    	
    	//addSequential(new DriveToObstacle(24, 0.6));
    	
    	//mock autonomous
    	////DEPLOY GEAR AND DRIVE UP
    	addParallel(new DeployGearHolder());
    	addSequential(new DriveStraightSetDistance(-50)); //GEAR EXTENDS 4.75"
    	addSequential(new StopRobot(2));
    	//ALIGN TO LIFT
    	//where george will be
    	addSequential(new DriveToObstacle(12, 0.5));//drive close enough to use George
    	addSequential(new WaitUntilGearGoneOrTimeOut(10)); 
    	
    //SECOND POSSIBILITY: GIVE GEAR AND IF IT DOESN'T WORK TRY TO ALIGN AGAIN
    	//IF GEAR IS STILL THERE, TRY TO ALIGN AGAIN
//    	if(Robot.gearholder.isGearPresent()) {
//    		addSequential(new DriveStraightSetDistance(40));
//    		//add george code
//    		addSequential(new StopRobot(10));
//    		addSequential(new DriveToObstacle(20, 0.5));
//    		addSequential(new WaitUntilGearGoneOrTimeOut(10));
//    	}
    //FIRST POSSIBILITY: GIVE GEAR THEN DRIVE INTO NEUTRAL ZONE NOW	
    	//RETRACT GEAR HOLDER AND DRIVE BACK
    	//addParallel(new RetractGearHolder());
    	//addSequential(new DriveStraightSetDistance(40));
    	//TURN
    	/*addSequential(new TurnWithGyro(-60.0));
    	//DRIVE FORWARD
    	addSequential(new DriveStraightSetDistance(-80)); //is this disance correct or will we still hit the airship
    	//TURN SO FACINNG FORWARD AGAIN
    	addSequential(new TurnWithGyro(60.0));
    	//DRIVE FORWARD
    	addSequential(new DriveStraightSetDistance(-80));*/
    	
//    	//practice autonomous
//    	addSequential(new DriveStraightSetDistance(-50));
//    	addSequential(new DriveToObstacle(24, 0.6));
//    	addSequential(new DriveStraightSetDistance(74));
    	
    
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
