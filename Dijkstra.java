import java.util.*;

public class Dijkstra {
    public static Map<Location, Integer> distances = new HashMap<>();
    public static Map<Location, Location> previous = new HashMap<>();

    public static void findShortestPath(Graph graph, Location start) {
        distances.clear();
        previous.clear();

        PriorityQueue<Location> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        for (Location loc : graph.getLocations()) {
            distances.put(loc, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Location current = queue.poll();
            for (Path path : graph.getPaths(current)) {
                Location neighbor = path.getDestination();
                int newDist = distances.get(current) + path.getDistance();
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
    }

    public static List<Location> getShortestPath(Location target) {
        List<Location> path = new ArrayList<>();
        for (Location at = target; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
