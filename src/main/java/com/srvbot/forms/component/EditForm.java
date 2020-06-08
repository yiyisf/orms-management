package com.srvbot.forms.component;

import com.srvbot.forms.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;


@Tag("srvbot-form")
public class EditForm extends FormLayout {

    private TextField label = new TextField("标签");
    private TextField id = new TextField("ID");
    private ComboBox<CustomerFiled> type = new ComboBox<>("类型");
    private Select<Integer> weight = new Select<>();
    private final FormLayout formLayout;
    private Binder<ElementFiled> binder = new Binder<>(ElementFiled.class);

    private Component component;

    private ElementFiled elementFiled;

    public EditForm(MainView mainView) {
//        addClassName("form-content");
        this.elementFiled = new ElementFiled();
        label.setPlaceholder("请输入标签！");
        label.setRequired(true);
        label.setRequiredIndicatorVisible(true);
        id.setPlaceholder("请输入唯一的id!");
        id.setRequired(true);
        id.setRequiredIndicatorVisible(true);
        type.setPlaceholder("请选择类型!");
        type.setRequired(true);
        type.setItems(CustomerFiled.HOST, CustomerFiled.NUMBER, CustomerFiled.SELECT, CustomerFiled.TEXT, CustomerFiled.SUB_SYS_TEM, CustomerFiled.UM);
        type.setValue(CustomerFiled.TEXT);
        type.setRequiredIndicatorVisible(true);
        weight.setItems(1, 2, 3);
        weight.setLabel("比重：");
        weight.setRequiredIndicatorVisible(true);
//        weight.setValue(1);

        formLayout = new FormLayout();
        formLayout.add(label, id, type, weight);
        //test
//        formLayout.add(new RichTextEditor());
        NumberField numberField = new NumberField("数字:");
        numberField.setMin(0);
        numberField.setMax(100);
        numberField.setStep(2);
        numberField.setId("number");
        numberField.setHasControls(true);
        numberField.setPrefixComponent(new Icon(VaadinIcon.DOLLAR));
        formLayout.add(numberField);

        Button save = confirmBtn(mainView);
        Button button = cancelBtn();
        HorizontalLayout buttons = new HorizontalLayout(save, button);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        binder.forField(label)
                .withValidator(StringUtils::isNotEmpty, "请输入字段标签!")
                .bind(ElementFiled::getLabel, ElementFiled::setLabel);

        binder.forField(id)
                .withValidator(StringUtils::isNotEmpty, "请输入唯一id!")
                .bind(ElementFiled::getId, ElementFiled::setId);

        binder.forField(type)
                .withValidator(Objects::nonNull, "请选择组件类型!")
                .bind(ElementFiled::getType, ElementFiled::setType);

        binder.bind(weight, ElementFiled::getWeight, ElementFiled::setWeight);

        binder.setBean(this.elementFiled);

        formLayout.add(buttons, 2);
        add(formLayout);
    }

    private Button cancelBtn() {
        Button cancelBtn = new Button("取消");
        cancelBtn.addClickListener(e -> {
            getParent().ifPresent(p -> {
                if (p instanceof Dialog) {
                    ((Dialog) p).close();
                }
            });
        });

        return cancelBtn;
    }

    private Button confirmBtn(MainView mainView) {
        Button btn = new Button("确定");
        btn.addClickListener(e -> {
            if (binder.validate().isOk()) {
                System.out.println(this.elementFiled);
                getParent().ifPresent(p -> {
                    if (p instanceof Dialog) {
                        //init result component
                        initComponent(this.elementFiled);
                        mainView.initForm(this.component, this.elementFiled.getWeight());
                        ((Dialog) p).close();
                    }
                });
            } else {
                List<ValidationResult> errors = binder.validate().getValidationErrors();
                System.out.println(errors);
            }
            System.out.println(this.elementFiled);
        });
        return btn;
    }

    private void initComponent(ElementFiled elementFiled) {
        switch (elementFiled.getType()) {
            case TEXT:
                Text text = new Text(elementFiled.getLabel());
                System.out.println("element id is :" + elementFiled.getId());
//                text.setId(elementFiled.getId());
                this.component = text;
                break;
            case UM:
                TextField textField = new TextField(elementFiled.getLabel());
                textField.setId(elementFiled.getId());
                textField.setPlaceholder("");
                textField.setMaxLength(150);
                this.component = textField;
                break;
            case NUMBER:
                break;
            case HOST:
                break;
            case SELECT:
                break;
            case SUB_SYS_TEM:
                break;
        }

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

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
