package com.srvbot.forms.component.formComponent;

import com.srvbot.forms.MainView;
import com.srvbot.forms.component.ElementFiled;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

@Tag("formField")
public class FormDiv extends FlexLayout {

    private ElementFiled elementFiled;

    private Component component;

    public FormDiv(ElementFiled elementFiled, boolean isEdit) {
        this.elementFiled = elementFiled;
        initComponent(elementFiled, isEdit);
        getElement().getThemeList().add("");
        Button editBtn = new Button();
        Button closeBtn = new Button();
        editBtn.setIcon(VaadinIcon.EDIT.create());
        editBtn.getElement().getStyle().set("float", "right");
        editBtn.addClickListener(event -> {
            getParent().flatMap(Component::getParent).ifPresent(m -> {
                if (m instanceof MainView) {
                    MainView m1 = (MainView) m;
                    m1.showEdit(this.elementFiled);
                }
            });
        });

        closeBtn.setIcon(VaadinIcon.CLOSE_CIRCLE.create());
        closeBtn.getElement().getStyle().set("float", "right");
        closeBtn.addClickListener(event -> {
            getParent().ifPresent(p -> {
                if (p instanceof FormLayout) {
                    FormLayout p1 = (FormLayout) p;
                    p1.remove(this);
                }
            });
        });
        if (isEdit) {
            add(component, editBtn, closeBtn);
        } else {
            add(component);
        }
        editBtn.getThemeNames().add("hide");

        expand(component);
    }

    private void initComponent(ElementFiled elementFiled, boolean isEdit) {
        switch (elementFiled.getType()) {
            case TEXT:
                this.component = new FormText(elementFiled.getLabel());
                System.out.println("element id is :" + elementFiled.getId());
                break;
            case UM:
                TextField textField = new TextField(elementFiled.getLabel());
                textField.setId(elementFiled.getId());
                textField.setPlaceholder("");
                textField.setMaxLength(150);
                this.component = textField;
                break;
            case NUMBER:
                NumberField num = new NumberField();
                num.setLabel(elementFiled.getLabel());
                this.component = num;
                break;
            case HOST:
                ComboBox<String> hostComBox = new ComboBox<>();
                hostComBox.setLabel(elementFiled.getLabel());
                hostComBox.setItems("host1", "host2", "host3");
                this.component = hostComBox;
                break;
            case SELECT:
                Select<String> select = new Select<>();
                select.setLabel(elementFiled.getLabel());
                this.component = select;
                break;
            case SUB_SYS_TEM:
                ComboBox<String> subSysComBox = new ComboBox<>();
                subSysComBox.setLabel(elementFiled.getLabel());
                subSysComBox.setItems("sys1", "sys2", "sys3");
                this.component = subSysComBox;
                break;
            case RADIO:
                RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();
                radioButtonGroup.setLabel(elementFiled.getLabel());
                radioButtonGroup.setItems("r1", "r2", "r3");
                radioButtonGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

                this.component = radioButtonGroup;
                break;
            case CHECK_BOX:
                CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
                checkboxGroup.setLabel(elementFiled.getLabel());
                checkboxGroup.setItems("c1", "c2", "c3");
                this.component = checkboxGroup;
                break;
        }

        this.component.setId(elementFiled.getId());

    }

    public ElementFiled getElementFiled() {
        return elementFiled;
    }

    public void setElementFiled(ElementFiled elementFiled) {
        this.elementFiled = elementFiled;
    }
}
