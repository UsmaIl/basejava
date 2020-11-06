package com.dofler.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListInstitution extends Section {
    private final List<Institution> institutions;

    public ListInstitution(List<Institution> institutions) {
        this.institutions = institutions;
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
