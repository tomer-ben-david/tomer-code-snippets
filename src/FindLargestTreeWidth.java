import java.util.HashMap;
import java.util.Map;

// Given a tree, what is it's largest width, the tree does not need to be complete.
public class FindLargestTreeWidth {

    public static class Node {
        public Node left;
        public Node right;
    }

    public static class ExampleData {
        Node a = new Node();

        public ExampleData() {
            Node aRight = new Node();
            Node aLeft = new Node();

            a.right = aRight;
            a.left = aLeft;

            Node bRight = new Node();

            aRight.right = bRight;
        }
    }

    Map<Integer, Integer> levelToWidth = new HashMap<>();

    public void fillMap(Node root, int level) {
        if (root == null) {
            return;
        } else {
            Integer curLevel = levelToWidth.get(level);
            if (curLevel == null) {
                levelToWidth.put(level, 1);
            } else {
                levelToWidth.put(level, curLevel + 1);
            }
            fillMap(root.right, level + 1);
            fillMap(root.left, level + 1);
        }
    }

    public int findMaxInMap() {
        int max = 0;
        for (Map.Entry<Integer, Integer> entry: levelToWidth.entrySet()) {
            max = Math.max(max, entry.getValue());
        }
        return max;
    }

    public static void main(String[] args) throws Exception {
        FindLargestTreeWidth f = new FindLargestTreeWidth();
        f.fillMap(new ExampleData().a, 0);
        System.out.println(f.findMaxInMap());
    }
}
