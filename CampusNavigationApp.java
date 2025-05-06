import java.util.*;

public class CampusNavigationApp {
    private static Graph graph = new Graph();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadSampleData();
        int choice;
        do {
            System.out.println("\n===== Campus Navigation System =====");
            System.out.println("1. Add a location (building)");
            System.out.println("2. Add a path between locations");
            System.out.println("3. Display all locations and paths");
            System.out.println("4. Find the shortest route");
            System.out.println("5. Exit");
            System.out.println("6. Run Automated Test Cases");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addLocation();
                case 2 -> addPath();
                case 3 -> displayGraph();
                case 4 -> findRoute();
                case 5 -> System.out.println("Goodbye!");
                case 6 -> TestCaseLoader.runAllTests();
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }

    private static void addLocation() {
        System.out.print("Enter location name: ");
        String name = scanner.nextLine();
        Location loc = new Location(name);
        graph.addLocation(loc);
        System.out.println("Location added.");
    }

    private static void addPath() {
        System.out.print("From: ");
        Location from = new Location(scanner.nextLine());
        System.out.print("To: ");
        Location to = new Location(scanner.nextLine());
        if (!graph.hasLocation(from) || !graph.hasLocation(to)) {
            System.out.println("Both locations must exist.");
            return;
        }
        System.out.print("Distance: ");
        int dist = Integer.parseInt(scanner.nextLine());
        graph.addPath(from, to, dist);
        System.out.println("Path added.");
    }

    private static void displayGraph() {
        System.out.println("Locations:");
        for (Location loc : graph.getLocations()) {
            System.out.println("- " + loc);
        }
        System.out.println("Paths:");
        for (Location loc : graph.getLocations()) {
            for (Path path : graph.getPaths(loc)) {
                System.out.println(loc + " ↔ " + path.getDestination() + " (" + path.getDistance() + ")");
            }
        }
    }

    private static void findRoute() {
        System.out.print("Start location: ");
        Location start = new Location(scanner.nextLine());
        System.out.print("End location: ");
        Location end = new Location(scanner.nextLine());
        if (!graph.hasLocation(start) || !graph.hasLocation(end)) {
            System.out.println("Both locations must exist.");
            return;
        }
        if (start.equals(end)) {
            System.out.println("You're already at the destination!");
            return;
        }
        Dijkstra.findShortestPath(graph, start);
        if (Dijkstra.distances.get(end) == Integer.MAX_VALUE) {
            System.out.println("No path found.");
        } else {
            List<Location> path = Dijkstra.getShortestPath(end);
            System.out.println("🚩 Shortest Path Found!");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));
                if (i < path.size() - 1) System.out.print(" → ");
            }
            System.out.println("\nTotal Distance: " + Dijkstra.distances.get(end));
        }
    }

    private static void loadSampleData() {
        Location lib = new Location("Library");
        Location cafe = new Location("Cafeteria");
        Location hall = new Location("Lecture Hall");
        Location lab = new Location("Computer Lab");
        Location admin = new Location("Admin Block");

        graph.addLocation(lib);
        graph.addLocation(cafe);
        graph.addLocation(hall);
        graph.addLocation(lab);
        graph.addLocation(admin);

        graph.addPath(lib, cafe, 4);
        graph.addPath(cafe, hall, 6);
        graph.addPath(lib, lab, 3);
        graph.addPath(lab, admin, 7);
        graph.addPath(hall, admin, 5);
    }
}
