import java.util.*;

public class BranchBound {
	private static ArrayList<Node> pq = new ArrayList<>();
	private static Node simpulExpan = new Node("Default", 100);
	public static Integer steps = 0;
	public static Integer sumSteps = 0;
	public static Integer win = 0;
	private static HashMap<String, Integer> picked = new HashMap<String, Integer>();

	public static ArrayList<Node> makeChildren(Node parent, int matches, ArrayList<String> possibleSolutions) {
		ArrayList<Node> retVal = new ArrayList<Node>();
		Integer match = 0;
		for (int i = 0; i < possibleSolutions.size(); i++) {
			String currentword = possibleSolutions.get(i);
			for (int j = 0; j < possibleSolutions.get(0).length(); j++) {
				if (currentword.charAt(j) == parent.getName().charAt(j)) {
					match++;
				}
			}
			// System.out.println("match : " + match);
			if (match == matches) {
				retVal.add(new Node(currentword, currentword.length() - matches));
			}
			match = 0;
		}
		return retVal;
	}

	public static int getKemiripan(String a, String b) {
		int kemiripan = 0;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) == b.charAt(i)) {
				kemiripan++;
			}
		}
		return kemiripan;
	}

	public static String pickRandomString(ArrayList<String> list) {
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException("The list cannot be null or empty");
		}
		Random random = new Random();
		int randomIndex = random.nextInt(list.size());
		return list.get(randomIndex);
	}

	public static Node getMinNode() {
		Node min = pq.get(0);
		int idx = 0;
		for (int i = 0; i < pq.size(); i++) {
			if (pq.get(i).compareTo(min) < 0) {
				min = pq.get(i);
				idx = i;
			}
		}
		pq.remove(idx);
		return min;
	}

	public static String getSolution(ArrayList<String> possibleSolutions) {
		pq.clear();
		// setup
		int kemiripan;
		String result = null;
		boolean found = false;
		int mode = 1;
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Start in solver mode / experiment mode (1/0) : ");
			mode = scanner.nextInt();
			scanner.nextLine(); // Consume newline character
		} catch (Exception e) {
			System.out.println("Invalid input. Defaulting to solver mode.");
			mode = 1; // default mode if there's an input error
		}
		if (mode == 1) {
			try (Scanner s = new Scanner(System.in)) {
				while (!found) {
					if (simpulExpan.getCost() == 0) {
						result = simpulExpan.getName();
						found = true;
					} else {
						// make children
						if (simpulExpan.getCost() == 100) {
							for (int i = 0; i < possibleSolutions.size(); i++) {
								simpulExpan.addChild(new Node(possibleSolutions.get(i),
										possibleSolutions.get(0).length()));
							}
						} else {
							System.out.println("Enter ammount of matches: ");
							if (scanner.hasNextInt()) {
								kemiripan = scanner.nextInt();
								scanner.nextLine();
								if (kemiripan > 0) {
									ArrayList<Node> tempChildren = makeChildren(simpulExpan, kemiripan,
											possibleSolutions);
									System.out.println(tempChildren.size());
									for (Node child : tempChildren) {
										simpulExpan.addChild(new Node(child.getName(), child.getCost()));
									}
								}
							} else {
								System.out.println("Invalid input. Please enter an integer.");
								scanner.next();
							}
						}
						System.out.println("size : " + simpulExpan.getChildren().size());
						;
						for (int i = 0; i < simpulExpan.getChildren().size(); i++) {
							if (picked.get(simpulExpan.getChildren().get(i).getName()) == null
									|| simpulExpan.getName().length() - simpulExpan.getChildren().get(i)
											.getCost() == simpulExpan.getName().length()) {
								pq.add(simpulExpan.getChildren().get(i));
							}
						}
						for (Node node : pq) {
							System.out.println(node.getName() + " : " + node.getCost());
						}
						simpulExpan = getMinNode();
						picked.put(simpulExpan.getName(), 1);
						System.out.println(simpulExpan.getCost());
						System.out.println("Now checking : " + simpulExpan.getName());
					}
				}
			}
		} else {
			for (int k = 0; k < 1000; k++) {

				String jawaban = pickRandomString(possibleSolutions);
				System.out.println(jawaban);
				while (!found) {
					if (simpulExpan.getCost() == 0) {
						result = simpulExpan.getName();
						found = true;
					} else {
						// make children
						if (simpulExpan.getCost() == 100) {
							for (int i = 0; i < possibleSolutions.size(); i++) {
								simpulExpan.addChild(new Node(possibleSolutions.get(i),
										possibleSolutions.get(0).length()));
							}
						} else {
							kemiripan = getKemiripan(jawaban, simpulExpan.getName());
							// System.out.println("Kemiripan : " + kemiripan);
							if (kemiripan > 0) {
								ArrayList<Node> tempChildren = makeChildren(simpulExpan, kemiripan, possibleSolutions);
								if (kemiripan == jawaban.length()) {
									result = simpulExpan.getName();
									break;
								}
								for (Node child : tempChildren) {
									simpulExpan.addChild(new Node(child.getName(), child.getCost()));
								}
							}
						}
						for (int i = 0; i < simpulExpan.getChildren().size(); i++) {
							if (picked.get(simpulExpan.getChildren().get(i).getName()) == null
									|| simpulExpan.getName().length() - simpulExpan.getChildren().get(i)
											.getCost() == simpulExpan.getName().length()) {
								pq.add(simpulExpan.getChildren().get(i));
							}
						}
						// for (Node nodes : pq) {
						// System.out.println(nodes.getName() + " : " + nodes.getCost());
						// }
						// System.out.println("");
						simpulExpan = getMinNode();
						picked.put(simpulExpan.getName(), 1);
						steps++;
						// System.out.println("Node yang dipilih : " + simpulExpan.getName());
					}
				}
				System.out.println("Step count : " + steps);
				sumSteps += steps;
				if (steps <= 4) {
					win++;
				}
				steps = 0;
				found = false;
				pq.clear();
				simpulExpan = new Node("Default", 100);
				picked.clear();
			}

		}
		scanner.close();
		return result;
	}
}
