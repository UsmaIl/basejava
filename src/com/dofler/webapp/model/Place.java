package com.dofler.webapp.model;

import com.dofler.webapp.util.DateUtil;
import com.dofler.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Place implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate endDate;
    private String title;

    private String description;

    public Place() {
    }

    public Place(LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public Place(int startYear, int startMonth, String title, String description) {
        this(DateUtil.of(startYear, Month.of(startMonth)), DateUtil.NOW, title, description);
    }

    public Place(int startYear, int startMonth, int endYear, int endMonth, String title, String description) {
        this(DateUtil.of(startYear, Month.of(startMonth)), DateUtil.of(endYear, Month.of(endMonth)), title, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(startDate, place.startDate) &&
                Objects.equals(endDate, place.endDate) &&
                Objects.equals(title, place.title) &&
                Objects.equals(description, place.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        return "Place{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
