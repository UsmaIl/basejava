package com.dofler.webapp.model;

import java.util.List;
import java.util.Objects;

public class Section<T extends Contentable> {
    private final List<T> institutions;

    public Section(List<T> institutions) {
        this.institutions = institutions;
    }

    public List<T> getInstitution() {
        return institutions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section<?> that = (Section<?>) o;
        return Objects.equals(institutions, that.institutions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(institutions);
    }

    @Override
    public String toString() {
        return institutions.toString();
    }
}
