import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class TestCaseLoader {
    private static int testCaseCounter = 1;
    private static PrintWriter logger;

    public static void runAllTests() {
        try {
            logger = new PrintWriter(new FileWriter("test_log.txt"));
            logger.println("===== Automated Test Case Log =====\n");
            System.out.println("Running Automated Test Cases...");
            runTest1(); runTest2(); runTest3(); runTest4();
            runTest5(); runTest6(); runTest7(); runTest8();
            runTest9(); runTest10(); runTest11(); runTest12();
            logger.close();
            System.out.println("✅ Log written to test_log.txt");
        } catch (IOException e) {
            System.out.println("⚠️ Failed to write log file: " + e.getMessage());
        }
    }

    private static void runTest(String desc, Graph graph, Location start, Location end, boolean expectPath) {
        StringBuilder output = new StringBuilder();
        output.append("\n🧪 Test Case ").append(testCaseCounter++).append(": ").append(desc).append("\n");

        Dijkstra.findShortestPath(graph, start);
        int dist = Dijkstra.distances.getOrDefault(end, Integer.MAX_VALUE);

        if (dist == Integer.MAX_VALUE && expectPath) {
            output.append("❌ No path found (Expected a valid path).\n");
        } else if (!expectPath && dist != Integer.MAX_VALUE) {
            output.append("❌ Unexpected path found.\n");
        } else if (!expectPath) {
            output.append("✅ Correctly reported no path.\n");
        } else {
            List<Location> path = Dijkstra.getShortestPath(end);
            output.append("✅ Path: ");
            for (int i = 0; i < path.size(); i++) {
                output.append(path.get(i));
                if (i < path.size() - 1) output.append(" → ");
            }
            output.append("\nTotal Distance: ").append(dist).append("\n");
        }

        System.out.print(output);
        if (logger != null) logger.print(output);
    }

    private static Graph baseGraph(Location... locations) {
        Graph g = new Graph();
        for (Location loc : locations) g.addLocation(loc);
        return g;
    }

    // === Test Definitions ===

    private static void runTest1() {
        Location A = new Location("A"), B = new Location("B");
        Graph g = baseGraph(A, B);
        g.addPath(A, B, 5);
        runTest("Simple direct path A → B", g, A, B, true);
    }

    private static void runTest2() {
        Location A = new Location("A"), B = new Location("B"), C = new Location("C");
        Graph g = baseGraph(A, B, C);
        g.addPath(A, B, 3); g.addPath(B, C, 4);
        runTest("Two-step path A → B → C", g, A, C, true);
    }

    private static void runTest3() {
        Location A = new Location("A"), B = new Location("B"), C = new Location("C");
        Graph g = baseGraph(A, B, C);
        g.addPath(A, B, 3);
        runTest("No path between A → C", g, A, C, false);
    }

    private static void runTest4() {
        Location A = new Location("A"), B = new Location("B"), C = new Location("C"), D = new Location("D");
        Graph g = baseGraph(A, B, C, D);
        g.addPath(A, B, 2); g.addPath(B, D, 2);
        g.addPath(A, C, 2); g.addPath(C, D, 2);
        runTest("Multiple shortest paths A → D", g, A, D, true);
    }

    private static void runTest5() {
        Location A = new Location("A"), B = new Location("B"), C = new Location("C");
        Graph g = baseGraph(A, B, C);
        g.addPath(A, B, 1); g.addPath(B, C, 1); g.addPath(C, A, 1);
        runTest("Circular path A → C", g, A, C, true);
    }

    private static void runTest6() {
        Location[] locs = new Location[6];
        for (int i = 0; i < 6; i++) locs[i] = new Location("L" + i);
        Graph g = baseGraph(locs);
        for (int i = 0; i < 5; i++) g.addPath(locs[i], locs[i + 1], 1);
        runTest("Long chain L0 → L5", g, locs[0], locs[5], true);
    }

    private static void runTest7() {
        Location A = new Location("A"), B = new Location("B"), C = new Location("C");
        Graph g = baseGraph(A, B, C);
        g.addPath(A, B, 10); g.addPath(A, C, 1); g.addPath(C, B, 1);
        runTest("High-cost direct vs low-cost 2-step path", g, A, B, true);
    }

    private static void runTest8() {
        Location A = new Location("A"), B = new Location("B"), C = new Location("C"), D = new Location("D"), E = new Location("E");
        Graph g = baseGraph(A, B, C, D, E);
        g.addPath(A, B, 1); g.addPath(B, C, 1); g.addPath(D, E, 1);
        runTest("Disconnected clusters A → E", g, A, E, false);
    }

    private static void runTest9() {
        Location X = new Location("X"), A = new Location("A"), B = new Location("B"), C = new Location("C");
        Graph g = baseGraph(X, A, B, C);
        g.addPath(X, A, 1); g.addPath(X, B, 2); g.addPath(X, C, 3);
        runTest("Star topology A → C", g, A, C, true);
    }

    private static void runTest10() {
        Location A = new Location("A"), B = new Location("B"), C = new Location("C");
        Graph g = baseGraph(A, B, C);
        g.addPath(A, B, 10); g.addPath(A, C, 1); g.addPath(C, B, 2);
        runTest("Shortcut vs direct path", g, A, B, true);
    }

    private static void runTest11() {
        Location A = new Location("A");
        Graph g = baseGraph(A);
        runTest("Same start and end (A → A)", g, A, A, true);
    }

    private static void runTest12() {
        int size = 15;
        Location[] locs = new Location[size];
        for (int i = 0; i < size; i++) locs[i] = new Location("N" + i);
        Graph g = baseGraph(locs);
        for (int i = 0; i < size - 1; i++) g.addPath(locs[i], locs[i + 1], 1);
        runTest("Large linear graph N0 → N14", g, locs[0], locs[size - 1], true);
    }
}
