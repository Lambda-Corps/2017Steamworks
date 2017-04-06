package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.AlignToPeg;
import org.usfirst.frc.team1895.robot.commands.drivetrain.AutonomousGearCondition;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightSetDistance;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.drivetrain.GearGoneSequence;
import org.usfirst.frc.team1895.robot.commands.drivetrain.RetrySequence;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1895.robot.commands.gears.DeployGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.RetractGearHolder;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 * ON THE LEFT
 */
public class BLeft_LeftPos_Autonomous extends CommandGroup {

    public BLeft_LeftPos_Autonomous() {
    	
    	//UPDATED WITH RETRY CODE
    	
    	addSequential(new DriveStraightSetDistance(-83)); //117
    	//TURN TOWARD TO AIRSHIP'S LIFT
    	addSequential(new TurnWithGyro(60)); //this angle depends on where we are on the field
    	//addSequential(new DriveStraightSetDistance(-3)); //driving the hypotenuse
    	//ALIGN TO LIFT
    	addSequential(new AlignToPeg());
    	addSequential(new PrintCommand("I'm Aligned!"));
    	//addSequential(new WaitCommand(1.0));
    	addSequential(new DeployGearHolder());
    	addSequential(new PrintCommand("About to start retry"));
    	addSequential(new AutonomousGearCondition(new RetrySequence(), new GearGoneSequence()));
    	addSequential(new DriveStraightSetDistance(31));
    	//TURN SO FACING FORWARD AGAIN
    	addSequential(new TurnWithGyro(-60.0));
    	//DRIVE FORWARD INTO NEUTRAL ZONE
    	addSequential(new DriveStraightSetDistance(-60));	
    	addSequential(new TurnWithGyro(60.0));		//Mr.Brey wants us to drive toward the boiler but stay in NZ
    	addSequential(new DriveStraightSetDistance(-50));
    }
}
