package com.srvbot.forms.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class EditForm extends FormLayout {

    private TextField label = new TextField("标签");
    private TextField id = new TextField("ID");
    private ComboBox<CustomerFiled> type = new ComboBox<>("类型");
    private Select<Integer> weight = new Select<>();
    private final FormLayout formLayout;
    private Binder<ElementFiled> binder = new Binder<>(ElementFiled.class);

    private ElementFiled elementFiled;

    public EditForm() {
        this.elementFiled = new ElementFiled();
        label.setPlaceholder("请输入标签！");
        label.setRequired(true);
        label.setRequiredIndicatorVisible(true);
        id.setPlaceholder("请输入唯一的id!");
        id.setRequired(true);
        id.setRequiredIndicatorVisible(true);
        type.setPlaceholder("请选择类型!");
        type.setRequired(true);
        type.setRequiredIndicatorVisible(true);
        weight.setItems(1,2,3);
        weight.setLabel("比重：");
        weight.setRequiredIndicatorVisible(true);
//        weight.setValue(1);

        formLayout = new FormLayout();
        formLayout.add(label, id, type, weight);
//        formLayout.add(initTextfield("Label", "label"), initTextfield("提示语", "placeholder"), initTextfield("是否必输项","requeried"));
//        formLayout.add();
        Button save = confirmBtn();
        Button button = cancelBtn();
        HorizontalLayout buttons = new HorizontalLayout(save, button);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        binder.bind(label, ElementFiled::getLabel, ElementFiled::setLabel);

        binder.bind(id, ElementFiled::getId, ElementFiled::setId);

        binder.bind(type, ElementFiled::getType, ElementFiled::setType);

        binder.bind(weight, ElementFiled::getWeight, ElementFiled::setWeight);

        binder.setBean(this.elementFiled);
//        formLayout.add(save);
//        formLayout.add(button);

        formLayout.add(buttons);
        add(formLayout);
    }

    private Button cancelBtn() {
        Button cancelBtn = new Button("取消");
//        cancelBtn.addClickListener( e -> this.close());

        return cancelBtn;
    }

    private Button confirmBtn() {
        Button btn = new Button("确定");
        btn.addClickListener( e -> {
            if (binder.validate().isOk()) {
                System.out.println("validate OK!");
            } else {
                List<ValidationResult> errors = binder.validate().getValidationErrors();
                System.out.println(errors);
            }
            System.out.println(this.elementFiled);
        });
        return btn;
    }

    private TextField initTextfield(String label, String id) {
        TextField field = new TextField();
        field.setId(id);
        field.setLabel(label);
        field.setRequired(true);
        field.setRequiredIndicatorVisible(true);
        return field;
    }

    public FormLayout getFormLayout() {
        return formLayout;
    }

    public ElementFiled getElementFiled() {
        return elementFiled;
    }

    public void setElementFiled(ElementFiled elementFiled) {
        this.elementFiled = elementFiled;
    }
}
