package com.dofler.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Institution extends AbstractSection {
    private Link homePage;
    private List<Place> places;

    public static final Institution EMPTY = new Institution("", "", Place.EMPTY);

    public Institution(String name, String url, Place ... places) {
        this(new Link(name, url), Arrays.asList(places));
    }


    public Institution(Link homePage, List<Place> places) {
        this.homePage = homePage;
        this.places = places;
    }

    public Institution() {
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
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
