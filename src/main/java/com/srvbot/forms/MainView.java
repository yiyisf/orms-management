package com.srvbot.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.util.Locale;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     */
    public MainView() {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.START);
        add(initHeader());
        add(initForm(initTextfield(), initTextfield(), initTextfield(), initTextfield()));
    }

    private Component initForm(Component... components) {
        FormLayout formLayout = new FormLayout();

        for (Component component : components) {
            formLayout.addFormItem(component, "xxx");
        }

        DatePicker datePicker = new DatePicker();
        datePicker.setLocale(Locale.CHINESE);
        datePicker.setClearButtonVisible(true);
        formLayout.addFormItem(datePicker, "日期");

        return formLayout;
    }

    private TextField initTextfield() {
        return new TextField();
    }

    private Component initHeader() {
        addClassName("form-header");
        H1 formHeader = new H1("Form Header");
        formHeader.getElement().getThemeList().add("dark");
        return formHeader;
    }
}
