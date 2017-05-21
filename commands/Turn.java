package ticktank.commands;

import edu.wpi.first.wpilibj.command.Command;
import pid.PID;
import ticktock.Ticker;
import ticktank.Direction;
import ticktank.TickTank;

/**
 * Use the PID controller to turn to a given angle offset using the tank's
 * default turn PID parameters.
 *
 * @author samcf
 */
public class Turn extends Command {

	private static final double DEFAULT_MARGIN = 1;

	private TickTank tank;

	private double setPoint; // The heading we are going to
	private double degrees;
	private double error; // The current distance we still have to go
	private double margin;

	private PID controller;
	private Ticker ticker;

	public Turn(TickTank _tank, double _degrees, Direction _dir, double _margin) {
		if (_dir == Direction.LEFT || _dir == Direction.CCW) {
			degrees = _degrees * -1;
		} else {
			degrees = _degrees;
		}

		this.margin = _margin;

		this.tank = _tank;
		controller = new PID(tank, tank, tank.turnParams);
		ticker = new Ticker(controller, tank.turnParams.interval);
		requires(tank);
	}

	public Turn(TickTank _tank, double _degrees, Direction _dir) {
		this(_tank, _degrees, _dir, DEFAULT_MARGIN);
	}

	@Override
	protected void initialize() {
		if (tank.navx == null) {
			System.out.println("No gyro in use");
			end();
		}
		this.setPoint = tank.navx.getAngle() + degrees;
		controller.setSetpoint(setPoint);

		System.out.println("Turning");
		ticker.start();
	}

	@Override
	protected void execute() {
		error = setPoint - tank.navx.getAngle();
		System.out.println(error);
	}

	@Override
	protected boolean isFinished() {
		return (Math.abs(error) < margin) && (tank.navx.getRate() < 0.25);
	}

	@Override
	protected void end() {
		tank.stop();
		ticker.stop();
		System.out.println("Not turning");
	}

	@Override
	protected void interrupted() {
		end();
	}
}
