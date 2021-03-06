package ticktank.commands;

import edu.wpi.first.wpilibj.command.Command;
import ticktank.TickTank;

/**
 * Shift into low gear.
 *
 * @author samcf
 */
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
	protected boolean isFinished() {
		return true;
	}
}
