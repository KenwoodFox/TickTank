package ticktank.commands;

import org.usfirst.frc.team236.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Stops the tank drive
 */
public class Stop extends Command {

	public Stop() {
		requires(Robot.tank);
	}

	@Override
	protected void execute() {
		Robot.tank.stop();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
