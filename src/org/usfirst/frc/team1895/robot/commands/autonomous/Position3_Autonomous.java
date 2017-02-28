package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.AlignToPeg;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightSetDistance;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1895.robot.commands.gears.DeployGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.RetractGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.WaitUntilGearGoneOrTimeOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Changelog:
 * 2/7/2017 (Maddy Seputro)
 * 		Description: Autonomous plan should the robot be placed on the left side the field during competition.
 * 		Essentially, this autonomous plan will drive forward, turn and drive up to the lift, put a gear on the 
 * 		peg, back up, turn back to facing forward, shift gears to drive faster, then drive into the neutral zone.
 * 		NOTE: This autonomous plan does not shoot.
 * 			List of Steps:
 * 			1. Drive forward [] feet using DriveStraightSeteDistance Command.
 * 			2. Turn right [] degrees to the right using TurnWithGyro Command.
 * 			3. Drive forward [] feet using DriveStraightSeteDistance Command to the lift. 
 * 			4. Use DriveToObstacle Command to accurately drive up to lift.
 * 			5. Use DeployGearHolder Command to send forth the gearholder and get the gear on the peg.
 * 			6. After it is confirmed to be attached, GetGearPresence Command should return false. 
 * 			7. Use RetractGearHolder method to pull the gearholder back.
 * 			8. Back away [] feet using DriveStraightSeteDistance Command. 
 * 			9. Turn left [] degrees to the right using TurnWithGyro Command so that you're facing forward again.
 * 			10. Use ShiftGears Command to shift into high gear to allow for faster movement.
 * 			11. Use DriveStraightSeteDistance Command to drive [] feet to pass the baseline and head into 
 * 				the neutral zone. 
 * 		Commands needed: 
 * 			- DriveStraightSeteDistance, TurnWithGyro, AlignToHighGoal, DeployGearHolder, GetGearPresence, 
 * 				RetractGearHolder, ShiftGears
 * 		Field Pieces needed:
 * 			- 1 preloaded gear
 * 			- 10 preloaded fuel (to be used once tele-op starts)
 * 		Estimated Time needed to complete: [] SECONDS
 * 		To do still:
 * 			- Finish the other commands so we can add them in
 * 			- #4 How do we know it is attached??
 * 			- TEST TEST TEST
 */
public class Position3_Autonomous extends CommandGroup {

    public Position3_Autonomous() {
    	
    	//mock autonomous
    	//DRIVE UP
    	addSequential(new DriveStraightSetDistance(-110));
    	//TURN TOWARD TO AIRSHIP'S LIFT
    	addSequential(new TurnWithGyro(30)); //this angle depends on where we are on the field
    	//DEPLOY GEARHOLDER [DRIVE UP A LITTLE IF NEEDED, OR ELSE USE DRIVETOOBSTACLE, DEPENDS ON DISTANCE
    	addParallel(new DeployGearHolder());
    	addSequential(new DriveStraightSetDistance(-11.245)); //driving the hypotenuse
    	//ALIGN TO LIFT
    	addSequential(new DriveToObstacle(12, 0.6)); 	//23.245/35.245
    	addSequential(new AlignToPeg());
    	addSequential(new DriveToObstacle(4, 0.6));		//31.245
    	addSequential(new DriveStraightSetDistance(3));
    	//WAIT TILL GEAR IS GONE
    	addSequential(new WaitUntilGearGoneOrTimeOut(4));
    	
    	//RETRACT GEAR HOLDER AND DRIVE BACK
    	addParallel(new RetractGearHolder());
    	addSequential(new DriveStraightSetDistance(35.245));
    	//TURN
    	addSequential(new TurnWithGyro(-30.0));
    	//DRIVE FORWARD INTO NEUTRAL ZONE
    	addSequential(new DriveStraightSetDistance(-50));	//drives into the neutral zone

    }
}
