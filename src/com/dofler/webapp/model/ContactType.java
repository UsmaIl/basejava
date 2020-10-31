package com.dofler.webapp.model;

public enum ContactType {
    NAME("Имя"),
    PHONE_NUMBER("Тел."),
    MESSENGER("Мессенджер"),
    MAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
