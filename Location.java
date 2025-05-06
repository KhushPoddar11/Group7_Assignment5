public class Location {
    private String name;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode(); // Case-insensitive
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            return name.equalsIgnoreCase(((Location) obj).name);
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
