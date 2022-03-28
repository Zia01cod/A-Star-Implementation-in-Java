import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

public class AstarSearchAlgo {


    public static void main(String[] args) {

        //initialize the graph base on the Romania map
        Node start = new Node("Start", 550);
        Node n1 = new Node("ChkPt-1", 550);
        Node n2 = new Node("ChkPt-2", 450);
        Node n3 = new Node("ChkPt-3", 510);
        Node n4 = new Node("ChkPt-4", 325);
        Node n5 = new Node("ChkPt-5", 415);
        Node n6 = new Node("ChkPt-6", 235);
        Node n7 = new Node("ChkPt-7", 455);
        Node n8 = new Node("ChkPt-8", 400);
        Node n9 = new Node("ChkPt-9", 325);
        Node n10 = new Node("ChkPt-10", 240);
        Node n11 = new Node("ChkPt-11", 170);
        Node n12 = new Node("ChkPt-12", 205);
        Node n13 = new Node("Jezero", 0);

        //initializing the edges

        //Start
        start.adjacencies = new Edge[]{
                new Edge(n7, 77),
                new Edge(n9, 142),
                new Edge(n1, 120)
        };

        //ChkPt-1
        n1.adjacencies = new Edge[]{
                new Edge(start, 120),
                new Edge(n2, 113)
        };

        //ChkPt-2
        n2.adjacencies = new Edge[]{
                new Edge(n1, 113),
                new Edge(n3, 72)
        };


        //ChkPt-3
        n3.adjacencies = new Edge[]{
                new Edge(n2, 72),
                new Edge(n4, 77)
        };

        //ChkPt-4
        n4.adjacencies = new Edge[]{
                new Edge(n5, 122),
                new Edge(n3, 77)
        };


        //ChkPt-5
        n5.adjacencies = new Edge[]{
                new Edge(n4, 122),
                new Edge(n6, 126)
        };

        //ChkPt-6
        n6.adjacencies = new Edge[]{
                new Edge(n5, 126),
                new Edge(n11, 144),
                new Edge(n12, 148)
        };

        //ChkPt-7
        n7.adjacencies = new Edge[]{
                new Edge(start, 77),
                new Edge(n8, 71)
        };

        //ChkPt-8
        n8.adjacencies = new Edge[]{
                new Edge(n7, 71),
                new Edge(n9, 122)
        };

        //ChkPt-9
        n9.adjacencies = new Edge[]{
                new Edge(n8, 122),
                new Edge(start, 142),
                new Edge(n12, 82),
                new Edge(n10, 111)
        };

        //ChkPt-10
        n10.adjacencies = new Edge[]{
                new Edge(n9, 111),
                new Edge(n13, 213)
        };

        //ChkPt-11
        n11.adjacencies = new Edge[]{
                new Edge(n6, 140),
                new Edge(n13, 105),
                new Edge(n12, 99)
        };

        //ChkPt-12
        n12.adjacencies = new Edge[]{
                new Edge(n11, 99),
                new Edge(n6, 148),
                new Edge(n9, 82)
        };

        //Jezero
        n13.adjacencies = new Edge[]{
                new Edge(n10, 203),
                new Edge(n11, 105)
        };

        AstarSearch(start, n13);

        List<Node> path = printPath(n13);

        System.out.println("Path: " + path);


    }

    public static List<Node> printPath(Node target) {
        List<Node> path = new ArrayList<Node>();

        for (Node node = target; node != null; node = node.parent) {
            path.add(node);
        }

        Collections.reverse(path);

        return path;
    }

    public static void AstarSearch(Node source, Node goal) {

        Set<Node> explored = new HashSet<Node>();

        PriorityQueue<Node> queue = new PriorityQueue<Node>(20,
                new Comparator<Node>() {
                    //overriding the compare method
                    public int compare(Node i, Node j) {
                        if (i.f_scores > j.f_scores) {
                            return 1;
                        } else if (i.f_scores < j.f_scores) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }

                }
        );

        source.g_scores = 0;

        queue.add(source);

        boolean found = false;

        while ((!queue.isEmpty()) && (!found)) {

            Node current = queue.poll();

            explored.add(current);

            if (current.value.equals(goal.value)) {
                found = true;
            }

            for (Edge e : current.adjacencies) {
                Node child = e.target;
                double cost = e.cost;
                double temp_g_scores = current.g_scores + cost;
                double temp_f_scores = temp_g_scores + child.h_scores;


                if ((explored.contains(child)) &&
                        (temp_f_scores >= child.f_scores)) {
                    continue;
                } else if ((!queue.contains(child)) ||
                        (temp_f_scores < child.f_scores)) {

                    child.parent = current;
                    child.g_scores = temp_g_scores;
                    child.f_scores = temp_f_scores;

                    if (queue.contains(child)) {
                        queue.remove(child);
                    }

                    queue.add(child);

                }

            }

        }

    }

}

class Node {

    public final String value;
    public double g_scores;
    public final double h_scores;
    public double f_scores = 0;
    public Edge[] adjacencies;
    public Node parent;

    public Node(String val, double hVal) {
        value = val;
        h_scores = hVal;
    }

    public String toString() {
        return value;
    }

}

class Edge {
    public final double cost;
    public final Node target;

    public Edge(Node targetNode, double costVal) {
        target = targetNode;
        cost = costVal;
    }
}