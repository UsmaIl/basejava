package com.dofler.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListInstitution extends AbstractSection {
    private List<Institution> institutions;

    public ListInstitution(List<Institution> institutions) {
        Objects.requireNonNull(institutions, "institutions must not be null");
        this.institutions = institutions;
    }

    public ListInstitution(Institution... institutions) {
        this(Arrays.asList(institutions));
    }

    public ListInstitution() {
    }

    public List<Institution> getListInstitution() {
        return institutions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListInstitution that = (ListInstitution) o;
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
