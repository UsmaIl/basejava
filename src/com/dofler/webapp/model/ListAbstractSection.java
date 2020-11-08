package com.dofler.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListAbstractSection extends AbstractSection {
    private final List<String> details;

    public ListAbstractSection(List<String> details) {
        this.details = details;
    }

    public List<String> getListSection() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListAbstractSection that = (ListAbstractSection) o;
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
