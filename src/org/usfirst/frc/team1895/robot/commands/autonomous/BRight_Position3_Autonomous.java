package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightSetDistance;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1895.robot.commands.gears.DeployGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.RetractGearHolder;
import org.usfirst.frc.team1895.robot.commands.gears.WaitUntilGearGone;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * ON THE LEFT
 */
public class BRight_Position3_Autonomous extends CommandGroup {

    public BRight_Position3_Autonomous() {
    	
    	//UPDATED WITH RETRY CODE
    	
    	addSequential(new DriveStraightSetDistance(-83)); //117
    	//TURN TOWARD TO AIRSHIP'S LIFT
    	addSequential(new TurnWithGyro(60)); //this angle depends on where we are on the field
    	//DEPLOY GEARHOLDER [DRIVE UP A LITTLE IF NEEDED, OR ELSE USE DRIVETOOBSTACLE, DEPENDS ON DISTANCE
    	addSequential(new DeployGearHolder());
    	//31
    	//addSequential(new DriveStraightSetDistance(-3)); //driving the hypotenuse
    	//ALIGN TO LIFT
    	addSequential(new DriveToObstacle(16, 0.15)); 	//
    	//addSequential(new AlignToPeg());
    	//WAIT TILL GEAR IS GONE
    	addSequential(new WaitUntilGearGone());
    	
    	//RETRACT GEAR HOLDER AND DRIVE BACK
    	addSequential(new RetractGearHolder());
    	addSequential(new DriveStraightSetDistance(31));
    	//TURN SO FACING FORWARD AGAIN
    	addSequential(new TurnWithGyro(-60.0));
    	//DRIVE FORWARD INTO NEUTRAL ZONE
    	addSequential(new DriveStraightSetDistance(-40));	
    	addSequential(new TurnWithGyro(60.0));		//Mr.Brey wants us to drive toward the boiler but stay in NZ
    	addSequential(new DriveStraightSetDistance(-50));
    }
}
