package org.usfirst.frc.team1895.robot.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutonomousShoot extends CommandGroup {

    public AutonomousShoot() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order
    	
    	addSequential(new SetShooterSpeed(.78));
    	addSequential(new WaitCommand(1));
    	addSequential(new SetRegulatorSpeed(-.9));  //1
    	addSequential(new WaitCommand(.2));
    	addSequential(new SetRegulatorSpeed(0));
    	addSequential(new WaitCommand(.85));
    	addSequential(new SetRegulatorSpeed(-.6));  //2
    	addSequential(new WaitCommand(.2));
    	addSequential(new SetRegulatorSpeed(0));
    	addSequential(new WaitCommand(.85));
    	addSequential(new SetRegulatorSpeed(-.6));  //3
    	addSequential(new WaitCommand(.2));
    	addSequential(new SetRegulatorSpeed(0));
    	addSequential(new WaitCommand(.85));
    	addSequential(new SetRegulatorSpeed(-.6));  //4
    	addSequential(new WaitCommand(.2));
    	addSequential(new SetRegulatorSpeed(0));
    	addSequential(new WaitCommand(.85));
    	addSequential(new SetRegulatorSpeed(-.6));  //5
    	addSequential(new WaitCommand(.2));
    	addSequential(new SetRegulatorSpeed(0));
    	addSequential(new WaitCommand(.85));
    	addSequential(new SetRegulatorSpeed(-.6));  //1
    	addSequential(new WaitCommand(.2));
    	addSequential(new SetRegulatorSpeed(0));
    	addSequential(new WaitCommand(.85));
    	addSequential(new SetRegulatorSpeed(-.6));  //2
    	addSequential(new WaitCommand(.2));
    	addSequential(new SetRegulatorSpeed(0));
    	addSequential(new WaitCommand(.85));
    	addSequential(new SetRegulatorSpeed(-.6));  //3
    	addSequential(new WaitCommand(.2));
    	addSequential(new SetRegulatorSpeed(0));
    	addSequential(new WaitCommand(.85));
    	addSequential(new SetRegulatorSpeed(-.6));  //4
    	addSequential(new WaitCommand(.2));
    	addSequential(new SetRegulatorSpeed(0));
    	addSequential(new WaitCommand(.85));
    	addSequential(new SetRegulatorSpeed(-.6));  //5
    	addSequential(new WaitCommand(.2));
    	addSequential(new SetRegulatorSpeed(0));
    	addSequential(new WaitCommand(.85));


    	addSequential(new SetShooterSpeed(0)); //stop
    	addSequential(new SetRegulatorSpeed(0));

    	
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
