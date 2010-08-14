package teamdj.thoughtsquare.domain;

public class Location {
    private int id;
    private String title;
    private float latitude;
    private float longitude;

    public Location(int id, String title, float latitude, float longitude) {
        this.id = id;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
