package com.srvbot.forms.layout;

import com.srvbot.forms.component.MessageList;
import com.srvbot.forms.domain.ChatMessage;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.Objects;

@Route(value = "chat")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@Push
public class ChatLayout extends VerticalLayout {

    private final UnicastProcessor<ChatMessage> publisher;
    private final Flux<ChatMessage> messages;

    private String userName;
    private MessageList messageList;

    public ChatLayout(UnicastProcessor<ChatMessage> publisher, Flux<ChatMessage> messages) {
        this.publisher = publisher;
        this.messages = messages;
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getElement().getThemeList().add("dark");
        addClassName("messageList");

        H1 chat = new H1("Chat For xx");
        chat.getElement().getThemeList().add("dark");
        add(chat);
        //
        askUserName();
    }

    private void askUserName() {
        HorizontalLayout layout = new HorizontalLayout();
        TextField nameInput = new TextField();
        nameInput.setRequired(true);
        nameInput.setRequiredIndicatorVisible(true);
        Button start = new Button("开始");
        start.addClickListener(event -> {
            String name = nameInput.getValue();
            if (StringUtils.isNotEmpty(name)) {
                userName = name;
            } else {
                nameInput.setErrorMessage("请输入用户名!");
                nameInput.setInvalid(true);
                return;
            }
            remove(layout);
            showChat();
        });

        start.addClickShortcut(Key.ENTER);

        layout.add(nameInput, start);

        add(layout);
    }

    private void showChat() {
        if (Objects.isNull(this.messageList)) {
            this.messageList = new MessageList();
        }

        TextField message = new TextField();

        Button send = new Button("发送", VaadinIcon.ENTER.create(), event -> {
            String text = message.getValue();
            if (StringUtils.isNotEmpty(text)) {
                message.clear();
                publisher.onNext(new ChatMessage(userName, text));
//                messageList.addMessage(userName, text);
            } else {
                message.setInvalid(true);
            }
            message.focus();
        });
        message.focus();

        send.addClickShortcut(Key.ENTER);
        send.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        HorizontalLayout layout = new HorizontalLayout(message, send);
        layout.setAlignItems(Alignment.START);
        layout.expand(message);
        layout.setWidth("50%");
        add(messageList, layout);
        expand(messageList);
        messages.subscribe(chatMessage -> {
            getUI().ifPresent(ui -> {
                ui.access(() -> messageList.add(new Div(new Text(chatMessage.getFrom() + ":" + chatMessage.getMessage()))));
            });
        });
    }
}
