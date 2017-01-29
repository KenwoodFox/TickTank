package ticktank.commands;

import org.usfirst.frc.team236.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import ticktock.MultiTicker;
import ticktank.DriveSide;
import ticktank.TickTank;
import ticktank.motionProfile.DriveParameters;
import ticktank.motionProfile.Profile;
import ticktank.motionProfile.ProfileFollower;

/**
 *
 */
public class FollowProfile extends Command {

	Profile leftProfile, rightProfile;
	ProfileFollower leftFollower, rightFollower;
	DriveSide leftSide, rightSide;
	boolean isInverted;
	TickTank tank;
	ticktock.MultiTicker ticker;

	public FollowProfile(TickTank _tank, Profile bothSides, boolean isInverted) {
		this(_tank, bothSides, bothSides, isInverted);
	}

	public FollowProfile(TickTank _tank, Profile _left, Profile _right, boolean _isInverted) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		tank = _tank;

		requires(tank);

		this.leftProfile = _left;
		this.rightProfile = _right;

		this.leftSide = tank.left;
		this.rightSide = tank.right;

		this.isInverted = _isInverted;

		if (leftProfile == null || rightProfile == null) {
			System.out.println("Null profile(s)");
		} else {
			DriveParameters leftParams = new DriveParameters(RobotMap.Drive.kV_left, RobotMap.Drive.kA, RobotMap.Drive.kP);
			DriveParameters rightParams = new DriveParameters(RobotMap.Drive.kV_right, RobotMap.Drive.kA, RobotMap.Drive.kP);
			
			leftFollower = new ProfileFollower(leftProfile, leftSide, leftSide, leftParams, isInverted);
			rightFollower = new ProfileFollower(rightProfile, rightSide, rightSide, rightParams, isInverted);
		}
		ticker = new MultiTicker(1 / 100.0);
		ticker.addLoopable(leftFollower);
		ticker.addLoopable(rightFollower);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Starting FollowProfile Command");
		tank.zeroEncoders();
		leftFollower.restart();
		rightFollower.restart();

		ticker.start();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return leftFollower.isFinished() && rightFollower.isFinished();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		ticker.stop();
		tank.stop();
		System.out.println("Ending FollowProfile Command");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
