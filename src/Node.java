import java.util.*;

public class Node implements Comparable<Node> {
	private String name;
	private Integer cost;
	private ArrayList<Node> children = new ArrayList<Node>();

	public Node(String _name, Integer _cost) {
		name = _name;
		cost = _cost;
	}

	public void addChild(Node child) {
		this.children.add(child);
	}

	public String getName() {
		return name;
	}

	public Integer getCost() {
		return cost;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	@Override
	public int compareTo(Node other) {
		return Integer.compare(this.cost, other.cost);
	}

}
