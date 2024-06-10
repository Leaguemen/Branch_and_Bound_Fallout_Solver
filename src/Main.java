import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static Integer bfSumStep = 0;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Masukkan path relative from test : ");
		String input = scanner.nextLine();
		String filePath = "../test/" + input;
		ArrayList<String> possibleSolutions = Utility.parseText(filePath);
		String result = BranchBound.getSolution(possibleSolutions);
		System.out.println(result);
		System.out.println("Branch and Bound win rate : " + BranchBound.win + "/" +
				1000);
		System.out.println("Average steps taken : " + BranchBound.sumSteps / 1000 + "\n");
		System.out.println("Brute Force win rate : " + bruteForce(possibleSolutions)
				+ "/" + 1000);
		System.out.println("Average steps taken : " + bfSumStep / 1000);
		scanner.close();

	}

	public static int bruteForce(ArrayList<String> sols) {
		int wins = 0;
		for (int k = 0; k < 1000; k++) {
			int steps = 0;
			String jawaban = BranchBound.pickRandomString(sols);
			for (int i = 0; i < sols.size(); i++) {
				steps++;
				if (sols.get(i).equals(jawaban)) {
					break;
				}
			}
			bfSumStep += steps;
			if (steps <= 4) {
				wins++;
			}
			steps = 0;
		}
		return wins;
	}
}
