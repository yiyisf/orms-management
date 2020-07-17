package com.srvbot.forms.component;

import com.srvbot.forms.MainView;
import com.srvbot.forms.component.formComponent.FormDiv;
import com.srvbot.forms.component.formComponent.FormText;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
        id.addValueChangeListener(event -> {

        });
        type.setPlaceholder("请选择类型!");
        type.setRequired(true);
//        type.setItems(CustomerFiled.HOST, CustomerFiled.NUMBER, CustomerFiled.SELECT, CustomerFiled.TEXT, CustomerFiled.SUB_SYS_TEM, CustomerFiled.UM);
        type.setItems(CustomerFiled.values());

        type.setValue(CustomerFiled.TEXT);
        type.setRequiredIndicatorVisible(true);
        weight.setItems(1, 2, 3, 4);
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
                System.out.println(this.binder.getBean());
                getParent().ifPresent(p -> {
                    if (p instanceof Dialog) {
                        System.out.println("form json is:" + this.binder.getBean());
                        //init result component
                        this.component = new FormDiv(this.binder.getBean(), true);
//                        initComponent(this.binder.getBean(), true);
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

//    private void initComponent(ElementFiled elementFiled, boolean isEdit) {
//        switch (elementFiled.getType()) {
//            case TEXT:
//                FormText text = new FormText(binder.getBean().getLabel());
//                System.out.println("element id is :" + elementFiled.getId());
//                this.component = new FormDiv(text, isEdit);
//                break;
//            case UM:
//                TextField textField = new TextField(binder.getBean().getLabel());
//                textField.setId(elementFiled.getId());
//                textField.setPlaceholder("");
//                textField.setMaxLength(150);
//                this.component = new FormDiv(textField, isEdit);
//                break;
//            case NUMBER:
//                NumberField num = new NumberField();
//                num.setLabel(binder.getBean().getLabel());
//                this.component = new FormDiv(num, isEdit);
//                break;
//            case HOST:
//                ComboBox<String> hostComBox = new ComboBox<>();
//                hostComBox.setLabel(binder.getBean().getLabel());
//                hostComBox.setItems("host1", "host2", "host3");
//                this.component = new FormDiv(hostComBox, isEdit);
//                break;
//            case SELECT:
//                Select<String> select = new Select<>();
//                select.setLabel(binder.getBean().getLabel());
//                this.component = new FormDiv(select, isEdit);
//                break;
//            case SUB_SYS_TEM:
//                ComboBox<String> subSysComBox = new ComboBox<>();
//                subSysComBox.setLabel(binder.getBean().getLabel());
//                subSysComBox.setItems("sys1", "sys2", "sys3");
//                this.component = new FormDiv(subSysComBox, isEdit);
//                break;
//        }
//
//    }

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

    public void clearField() {
        this.binder.setBean(new ElementFiled());
    }

    public void setField(ElementFiled elementFiled) {
        this.binder.setBean(elementFiled);
    }
}
