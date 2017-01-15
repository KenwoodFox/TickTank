package ticktank.commands;

import edu.wpi.first.wpilibj.command.Command;
import ticktank.Direction;
import ticktank.TickTank;

public class RawTurn extends Command {

	private TickTank tank;

	public static final double turnSpeed = 0.5;
	private Direction direction;

	private double startHeading; // The angle we start at
	private double goalHeading; // The angle we're going to
	private double turnDegrees; // The total distance of the turn

	private double error; // The current distance we still have to go
	private double heading; // Our current heading

	public RawTurn(TickTank _tank, double _degrees, Direction _dir) {
		this.tank = _tank;
		this.turnDegrees = _degrees;
		this.direction = _dir;

		if (direction == Direction.LEFT || direction == Direction.CCW) {
			this.turnDegrees *= -1;
		}
	}

	@Override
	protected void initialize() {
		if (tank.navx == null) {
			end();
		}
		startHeading = tank.navx.getAngle();
		goalHeading = startHeading + turnDegrees;
		error = turnDegrees;
	}

	@Override
	protected void execute() {
		heading = tank.navx.getAngle();
		error = goalHeading - heading;

		if (direction == Direction.LEFT || direction == Direction.CCW) {
			tank.setLeftSpeed(-turnSpeed);
			tank.setRightSpeed(turnSpeed);
		} else if (direction == Direction.RIGHT || direction == Direction.CW) {
			tank.setLeftSpeed(turnSpeed);
			tank.setRightSpeed(-turnSpeed);
		}
	}

	@Override
	protected boolean isFinished() {
		return (Math.abs(error) < 10);
	}

	@Override
	protected void end() {
		tank.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
