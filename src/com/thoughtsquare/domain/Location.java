package com.thoughtsquare.domain;

import android.os.Parcel;
import android.os.Parcelable;
import com.thoughtsquare.utility.JSONObject;

import java.io.Serializable;

public class Location implements Parcelable, Serializable {
    private int id;
    private String title;
    private double latitude;
    private double longitude;
    private float radius;

    public Location(int id, String title, double latitude, double longitude, float radius) {
        this.id = id;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public Location(Parcel parcel) {
        this(parcel.readInt(), parcel.readString(), parcel.readDouble(), parcel.readDouble(), parcel.readFloat());
    }

    public Location(JSONObject jsonObject) {
        this(jsonObject.getInt("id"), jsonObject.getString("title"), jsonObject.getDouble("latitude"),
                jsonObject.getDouble("longitude"), 100000);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeFloat(radius);
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel parcel) {
            return new Location(parcel);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };


    public int describeContents() {
        return 0;
    }

    public boolean contains(android.location.Location otherLocation) {
        android.location.Location thisLocation = new android.location.Location("");
        thisLocation.setLatitude(latitude);
        thisLocation.setLongitude(longitude);
        return otherLocation.distanceTo(thisLocation) <= radius;
    }

}
