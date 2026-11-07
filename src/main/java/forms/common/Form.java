package main.java.forms.common;

import main.java.AppContext;

import java.util.ArrayList;


public class Form {
    private final ArrayList<FormField<?>> fields;
    private final AppContext context;

    public Form(AppContext context, ArrayList<FormField<?>> fields) {
        this.fields = fields;
        this.context = context;
    }

    public ArrayList<FormField<?>> getFields() {
        return fields;
    }

    public void appendField(FormField<?> field) {
        this.fields.add(field);
    }

    public void removeField(FormField<?> field) {
        this.fields.remove(field);
    }

    public void renderForm () {
        for (FormField<?> field : this.fields) {
            field.prompt(this.context.getScanner(), this);
        }
    }

    public FormField<?> getFieldByName(String name) {
        for (FormField<?> field : this.fields) {
            if (field.getName().equals(name)) {
                return field;
            }
        }
        return null;
    }

}
