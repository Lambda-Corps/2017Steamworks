package org.usfirst.frc.team1895.robot;

public class InformationOnEverything {
/* 
 * This class has no purpose but to provide basic information on the robot. 
 * Some conventions to keep in mind:
 * 	    FRONT = GEARSIDE
 * 	    BACK = FUELSIDE
 * 	    LEFT = left side of robot when gearside forward
 *      RIGHT = right side of robot when gearside is forward 
 *  
 * List of all Sensors: [cameras are still subject to movement however]
 *      - Drivetrain:
 *      	- 2 encoders (left_encoder, right_encoder)
 *      	- 1 gyro (gyro)
 *      	- 1 short-range rangefinder (middle_fr_short_rangefinder)
 *      	- 2 long-range rangefinders (left_fr_long_rangefinder, right_fr_long_rangefinder)
 *      	- 2 cameras (not declared yet)
 *      	- Solenoid for gearbox
 *      - GearHolder:
 *      	- 1 short-range rangefinder (inslot_short_rangefinder)
 *      	- Solenoid for gearbox
 *      - Shooter:
 *      	- 2 encoders (indexer_encoder, flywheel_encoder)
 *      	- Solenoid for gearbox
 *      - Winch:
 * 	    	- 1 encoder (winch_encoder)
 * 
 * List of all Buttons: [controls still TBD]
 * 	    - Using 2 Joysticks:
 *      	- X_AXIS: None
 *      	- Y_AXIS: Front and back straight driving
 *      	- Z_AXIS: None
 *      - Using a Gamepad:
 *      	- A: None
 *      	- B: None
 *      	- X: None
 *      	- Y: None
 *      	- LX_AXIS: None
 *      	- LY_AXIS: front and back straight driving
 *      	- RX_AXIS: left and right turning
 *      	- RY_AXIS: None
 *      	- FR_LEFT: None
 *      	- FR_RIGHT: None
 * Potential Buttons
 * Climbing
 * 	- alignToRope
 *  - CatchRope (winch enabled at full speed cause full speed is 1.1688 rev/ second)
 *  - Climb (winch pulls robot up rope and stops automatically when at the top)
 *       - measure voltage, when it exceeds a certain threshold stop motors
 *       - requires encoder
 *  - [JUST FOR TESTING] ClimbForTenRotations 
 *       - requires encoder
 *  - [JUST FOR TESTING] Unwind
 *  Drivetrain
 *  - ShiftGears
 *  - DriveWithCamera
 *
 * List of everything to be displayed to SmartDashboard (not necessarily doing so yet):
 * 	    - whether a gear is in the slot or not
 *      - distance encoders drove for any method that uses encoders and whether it has reached
 *      	its target distance or not (methods will be listed later but I'm tired right now haha)
 *      - heading of gyro as it turns and whether it has reached its target heading or not
 *      - Camera outputs
 * 
 * What the cameras will display and be used for:
 * 	    - DriveWithCamera and TurnWithCamera Commands
 *      - Back/Fuelside facing: 
 *      	- Aligning to rope
 *      	- Aligning to high goal
 *      - Front/Gearside facing:
 *        	- Aligning to Lift
 *      	- Aligning to Loading Station
 * TO BE DETERMINED: 
 *    - all long-range or 2 long-range and one short-range rangefinder for drivetrain class
 *    - desired arguments of DriveWithCamera Command, AlignToLift Command, AlignToRope Command, AlignToHighGoal Command, AlignToLoadingStation Command
 *    - speed of motor in CatchRope Command, Climb Command hardcode vs variable?
 * 
 */
}
