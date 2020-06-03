package com.srvbot.forms.component;

public class ElementFiled {
    private String label;
    private String id;
    private CustomerFiled type;
    private Integer weight;

    public ElementFiled() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerFiled getType() {
        return type;
    }

    public void setType(CustomerFiled type) {
        this.type = type;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ElementFiled{" +
                "label='" + label + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", weight=" + weight +
                '}';
    }
}
