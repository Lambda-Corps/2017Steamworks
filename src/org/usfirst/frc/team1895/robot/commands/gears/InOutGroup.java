package org.usfirst.frc.team1895.robot.commands.gears;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class InOutGroup extends CommandGroup {
	public InOutGroup() {
		addSequential(new DeployGearHolder());
		addSequential(new RetractGearHolder());
	}
}
