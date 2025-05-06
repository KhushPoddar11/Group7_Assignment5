import java.util.*;

public class Graph {
    private Map<Location, List<Path>> adjacencyList = new HashMap<>();

    public void addLocation(Location loc) {
        adjacencyList.putIfAbsent(loc, new ArrayList<>());
    }

    public void addPath(Location from, Location to, int distance) {
        adjacencyList.get(from).add(new Path(to, distance));
        adjacencyList.get(to).add(new Path(from, distance)); // Undirected
    }

    public Set<Location> getLocations() {
        return adjacencyList.keySet();
    }

    public List<Path> getPaths(Location loc) {
        return adjacencyList.getOrDefault(loc, new ArrayList<>());
    }

    public boolean hasLocation(Location loc) {
        return adjacencyList.containsKey(loc);
    }
}
