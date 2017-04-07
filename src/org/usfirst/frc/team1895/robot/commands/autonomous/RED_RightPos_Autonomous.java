package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.AlignToPeg;
import org.usfirst.frc.team1895.robot.commands.drivetrain.AutonomousGearCondition;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightSetDistance;
import org.usfirst.frc.team1895.robot.commands.drivetrain.GearGoneSequence;
import org.usfirst.frc.team1895.robot.commands.drivetrain.RetrySequence;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnOnLEDRing;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;
import org.usfirst.frc.team1895.robot.commands.gears.DeployGearHolder;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 *
 */
public class RED_RightPos_Autonomous extends CommandGroup {

    public RED_RightPos_Autonomous() {
    	//UPDATED WITH RETRY CODE
    	
    	//mock autonomous
    	//DRIVE UP
    	addSequential(new TurnOnLEDRing());
    	addSequential(new DriveStraightSetDistance(-83));
    	//TURN TOWARD TO AIRSHIP'S LIFT
    	addSequential(new TurnWithoutPID(-60, 0.4)); //this angle depends on where we are on the field
    	//DEPLOY GEARHOLDER [DRIVE UP A LITTLE IF NEEDED, OR ELSE USE DRIVETOOBSTACLE, DEPENDS ON DISTANCE
    	//addSequential(new DriveStraightSetDistance(-3)); //driving the hypotenuse
    	//ALIGN TO LIFT
    	addSequential(new AlignToPeg());
    	addSequential(new PrintCommand("I'm Aligned!"));
    	//addSequential(new WaitCommand(1.0));
    	addSequential(new DeployGearHolder());
    	addSequential(new PrintCommand("About to start retry"));
    	addSequential(new AutonomousGearCondition(new RetrySequence(), new GearGoneSequence()));
    	addSequential(new DriveStraightSetDistance(31));
    	//TURN
    	addSequential(new TurnWithoutPID(60.0, 0.4));
    	//DRIVE FORWARD INTO NEUTRAL ZONE
    	addSequential(new DriveStraightSetDistance(-50));	//drives into the neutral zone

    }
}
