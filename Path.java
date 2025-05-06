public class Path {
    private Location destination;
    private int distance;

    public Path(Location destination, int distance) {
        this.destination = destination;
        this.distance = distance;
    }

    public Location getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return destination + " (" + distance + ")";
    }
}
