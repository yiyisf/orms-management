package com.srvbot.forms.component;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Header extends VerticalLayout {
    public Header(String text) {
        addClassName("form-header");
        getElement().getThemeList().add("dark");
        add(new H1(text));
    }
}
