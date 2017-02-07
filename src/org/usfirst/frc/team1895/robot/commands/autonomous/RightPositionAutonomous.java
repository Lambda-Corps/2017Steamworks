package org.usfirst.frc.team1895.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Changelog:
 * 2/7/2017 (Maddy Seputro)
 * 		Description: Autonomous plan should the robot be placed on the right side the field during competition.
 * 		Essentially, this autonomous plan will drive forward, turn and drive up to the lift, put a gear on the 
 * 		peg, back up, turn back to facing forward, shift gears to drive faster, then drive into the neutral zone.
 * 		NOTE: This autonomous plan does not shoot.
 * 			List of Steps:
 * 			1. Drive forward [] feet using DriveStraightSeteDistance Command.
 * 			2. Turn left [] degrees to the right using TurnWithGyro Command.
 * 			3. Drive forward [] feet using DriveStraightSeteDistance Command to the lift. 
 * 			4. Use AlignToHighGoal Command to accurately drive up to lift.
 * 			5. Use DeployGearHolder Command to send forth the gearholder and get the gear on the peg.
 * 			6. After it is confirmed to be attached, GetGearPresence Command should return false. 
 * 			7. Use RetractGearHolder method to pull the gearholder back.
 * 			8. Back away [] feet using DriveStraightSeteDistance Command. 
 * 			9. Turn right [] degrees to the right using TurnWithGyro Command so that you're facing forward again.
 * 			10. Use ShiftGears Command to shift into high gear to allow for faster movement.
 * 			11. Use DriveStraightSeteDistance Command to drive [] feet to pass the baseline and head into 
 * 				the neutral zone. 
 * 		Field Pieces needed:
 * 			- 1 preloaded gear
 * 			- 10 preloaded fuel (to be used once tele-op starts)
 * 		Estimated Time needed to complete: [] SECONDS
 * 		To do still:
 * 			- Finish the other commands so we can add them in
 * 			- #4 How do we know it is attached??
 * 			- TEST TEST TEST
 */
public class RightPositionAutonomous extends CommandGroup {

    public RightPositionAutonomous() {
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
