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
 *      	- Y_AXIS: Front and back straight driving (tank)
 *      	- Z_AXIS: None
 *      - Using 2 Gamepads
 *      	BUTTONS FOR GAMEPAD 1
 *      	- START:
 *      	- BACK:
 *      	- A:
 *      	- B: 
 *      	- X: 
 *      	- Y: 
 *      	- RB: 
 *      	- LB: Manual override high gear
 *      	AXES FOR GAMEPAD 1
 *      	- LX_AXIS: left and right turning (arcade)
 *      	- LY_AXIS:
 *      	- LT:
 *      	- RX_AXIS:
 *      	- RY_AXIS: front and back straight driving (arcade)
 *      	- RT:
 *      	BUTTONS FOR GAMEPAD 2
 *      	- START:
 *      	- BACK:
 *      	- A: Gear Out
 *      	- B: Gear In
 *      	- X: 
 *      	- Y: 
 *      	- RB: switch cameras to the right
 *      	- LB: switch cameras to the left
 *      	AXES FOR GAMEPAD 2
 *      	- LX_AXIS: None
 *      	- LY_AXIS: front and back straight driving (arcade)
 *      	- LT: Climb Up
 *      	- RX_AXIS: left and right turning (arcade)
 *      	- RY_AXIS: None
 *      	- RT: None
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
 *      
 * List of things that were changed with Mr. Frederick:
 * 		- Line 182: pid controller for turning was changed to use the gyro instead of the ahrs
 * 		- Lines 194: added things for the lowrider encoder -- changing distance per puls
 * 		- Lines 297: changed gyro back to 
 * 		- Line 280: changed speed factor from 0.1 to 0.5
 * 		- Line 110: changed pGainDriv from 0.1 to 0.05
 * 		- Line 355-356: changed code to declare output value on 2 different lines
 * 		- Line 310: commented reset for navax board
 * 	
 * TO BE DETERMINED: 
 *    - all long-range or 2 long-range and one short-range rangefinder for drivetrain class
 *    - desired arguments of DriveWithCamera Command, AlignToLift Command, AlignToRope Command, AlignToHighGoal Command, AlignToLoadingStation Command
 *    - speed of motor in CatchRope Command, Climb Command hardcode vs variable?
 * 
 */
}
