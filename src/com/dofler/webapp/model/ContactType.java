package com.dofler.webapp.model;

public enum ContactType {
    PHONE_NUMBER("Тел."),
    MESSENGER("Мессенджер") {
        @Override
        public String toHtml0(String value) {
            return toLink("skype:" + value, value);
        }
    },
    MAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return toLink("mailto:" + value, value);
        }
    },
    LINKEDIN("Профиль LinkedIn") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    GITHUB("Профиль GitHub") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    HOME_PAGE("Домашняя страница") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    protected String toHtml0(String value) {
        return value;
    }

    protected String toLink(String href) {
        return toLink(href, title);
    }

    protected static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}
