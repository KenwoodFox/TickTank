package ticktank.motionProfile;

import java.util.Scanner;

public class TestParams {

	public static void main(String[] args) {
		// Get user input
		Scanner scanner = new Scanner(System.in);
		System.out.print("Name: ");
		String name = scanner.next();
		System.out.print("Distance: ");
		double d = scanner.nextInt();
		System.out.print("Velocity: ");
		double v = scanner.nextInt();
		System.out.print("Acceleration: ");
		double a = scanner.nextInt();
		System.out.print("Jerk: ");
		double j = scanner.nextInt();
		scanner.close();

		ProfileParameters params = new ProfileParameters(d, v, a, j);
		//ProfileParameters params = AutoMap.params;
		Profile p = new Profile(params);
		String home = System.getProperty("user.home");
		p.store(home + "/Desktop/" + name);
	}
}
