package com.srvbot.forms;

import com.srvbot.forms.component.EditForm;
import com.srvbot.forms.component.ElementFiled;
import com.srvbot.forms.component.formComponent.FormDiv;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
@PWA(name = "Form Management",
        shortName = "表单管理",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {
    private final FormLayout formLayout;

    private final EditForm editForm;

    private final Dialog dialog;

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     */
    public MainView() {

//        getElement().getThemeList().add(Lumo.DARK);

        this.editForm = new EditForm(this);
        this.dialog = new Dialog();
//        this.dialog.setWidth("50%");

        setSizeFull();

        setDefaultHorizontalComponentAlignment(Alignment.START);
        add(addBtn());
        formLayout = initForm();
        formLayout.getElement().getStyle()
                .set("border", "1px solid grey")
                .set("padding", "1em");

//        formLayout.setMinHeight("30em");

        add(formLayout);
        expand(formLayout);
//        formLayout.add(new FormDiv(component), 1);
//        formLayout.add(new FormDiv(component), 3);
//        add(initForm(initTextfield("XX", "t1"), initTextfield("YY", "t2"), initTextfield("ZZ", "t3"), initTextfield("AAA", "t4")));
    }

    private Button addBtn() {
        Button addBtn = new Button("New");
        addBtn.setIcon(new Icon(VaadinIcon.PLUS));
        addBtn.addClickListener(event -> addElement());
        return addBtn;
    }

    private void addElement() {
        this.editForm.clearField();;
        this.dialog.add(this.editForm);
        this.dialog.open();
    }

    public void showEdit(ElementFiled elementFiled) {
        this.editForm.setField(elementFiled);
        this.dialog.add(this.editForm);
        this.dialog.open();
    }

    private FormLayout initForm() {
        FormLayout form = new FormLayout();
        List<FormLayout.ResponsiveStep> list = new ArrayList<>();
        list.add(new FormLayout.ResponsiveStep("25em", 1));
        list.add(new FormLayout.ResponsiveStep("32em", 2));
        list.add(new FormLayout.ResponsiveStep("45em", 3));
        list.add(new FormLayout.ResponsiveStep("60em", 4));
        form.setResponsiveSteps(list);
        form.getElement().getThemeList().add("padding ");
        return form;
    }

    public void initForm(Component component, int colspan) {
//        FormLayout formLayout = new FormLayout();
//        List<FormLayout.ResponsiveStep> list = new ArrayList<>();
//        list.add(new FormLayout.ResponsiveStep("25em", 1));
//        list.add(new FormLayout.ResponsiveStep("32em", 2));
//        list.add(new FormLayout.ResponsiveStep("45em", 3));
//        list.add(new FormLayout.ResponsiveStep("60em", 4));
//        formLayout.setResponsiveSteps(list);
//        formLayout.getElement().getThemeList().add("padding ");
        if (Objects.nonNull(component)) {
            formLayout.add(component, colspan);
        }
//        DatePicker datePicker = new DatePicker();
//        datePicker.setLocale(Locale.CHINESE);
//        datePicker.setClearButtonVisible(true);
//        datePicker.setLabel("日期");
//        datePicker.setRequired(true);
//        datePicker.setId("date1");
//        datePicker.setRequiredIndicatorVisible(true);
//
//        formLayout.add(datePicker, 2);
//        formLayout.add(new VerticalLayout(), 2);
//        //
//        ComboBox<String> comboBox = new ComboBox<>("combox");
//        comboBox.setItems("xx", "yy", "zz");
//        comboBox.setRequired(true);
//        comboBox.setRequiredIndicatorVisible(true);
//        comboBox.setId("combox");
//        formLayout.add(comboBox);
//
//        comboBox.addValueChangeListener(event -> {
//            System.out.println("value changed");
//            UI.getCurrent().getPage()
//                    .executeJs("console.log ('combox select value is :' + $0)", comboBox.getValue());
//        });

    }

    private TextField initTextfield(String label, String id) {
        TextField field = new TextField();
        field.setId(id);
        field.setLabel(label);
        field.setRequired(true);
        field.setRequiredIndicatorVisible(true);
        return field;
    }

    private Component initHeader() {
//        addClassName("form-header");
        H1 formHeader = new H1("Form Header");
        formHeader.getElement().getThemeList().add("dark");
//        formHeader.getElement()

        return formHeader;
    }
}
