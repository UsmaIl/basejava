package com.dofler.webapp.model;

import java.util.List;
import java.util.Objects;

public class Institution extends AbstractSection {
    private final Link homePage;
    private final List<Place> places;

    public Institution(Link homePage, List<Place> places) {
        this.homePage = homePage;
        this.places = places;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Place> getPlaces() {
        return places;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institution that = (Institution) o;
        return Objects.equals(homePage, that.homePage) && Objects.equals(places, that.places);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, places);
    }

    @Override
    public String toString() {
        return "Institution(" +
                "homePage=" + homePage +
                ", places=" + places +
                ')';
    }
}
