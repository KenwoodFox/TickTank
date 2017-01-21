package ticktank.commands;

import edu.wpi.first.wpilibj.command.Command;
import ticktank.TickTank;

public class ShiftDown extends Command {

	TickTank tank;

	public ShiftDown(TickTank _tank) {
		this.tank = _tank;
	}

	@Override
	protected void initialize() {
		tank.shiftDown();
	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {

	}
}
