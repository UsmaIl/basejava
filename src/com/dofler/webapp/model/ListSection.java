package com.dofler.webapp.model;


import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    public static final ListSection EMPTY = new ListSection(Collections.emptyList());
    private List<String> details;

    public ListSection() {
    }

    public ListSection(List<String> details) {
        this.details = details;
    }

    public List<String> getListSection() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(details);
    }

    @Override
    public String toString() {
        return details.toString();
    }
}
