package com.srvbot.forms.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;


@Tag("messages")
public class MessageList extends Div {

    public MessageList() {
        addClassName("messageList");
    }

//    public void addMessage(String from, String msg) {
//        Div line = new Div(new Text(from + ":" + msg));
//        add(line);
//        line.getElement().callJsFunction("scrollIntoView");
//    }

    @Override
    public void add(Component... components) {
        super.add(components);
        components[components.length-1]
                .getElement()
                .callJsFunction("scrollIntoView");

    }
}
