package main.java.forms.projects;

import main.java.AppContext;
import main.java.dtos.projects.Project;
import main.java.forms.common.FormField;
import main.java.enums.UserInputTypeEnum;
import main.java.forms.common.Form;
import main.java.forms.common.Rule;
import main.java.utils.FormUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class UpsertProjectForm extends Form {
    public final FormField<String> nameField;
    public final FormField<String> descriptionField;
    public final FormField<BigDecimal> budgetField;
    public final FormField<LocalDate> startDateField;
    public final FormField<LocalDate> endDateField;
    public final FormField<String> statusField;

    public UpsertProjectForm(AppContext context, Project project) {
        ArrayList<FormField<?>> fields = new ArrayList<>();
        super(context, fields);

        var isUpdate = project != null;

         this.nameField = new FormField<>("name", UserInputTypeEnum.STRING,
                "Project " +
                        "Name: ", new Rule[]{
                FormUtils.requiredRule
        }, isUpdate ? project.name() : null);

        this.descriptionField = new FormField<>("description",
                UserInputTypeEnum.STRING, "Project " +
                "Description: ", new Rule[]{
                FormUtils.requiredRule
        }, isUpdate ? project.description() : null);

        this.budgetField = new FormField<>("budget", UserInputTypeEnum.CURRENCY, "Project Budget: ", new Rule[]{
                        FormUtils.requiredRule
                }, isUpdate ? project.budget() : null);

        this.startDateField = new FormField<>("startDate",
                UserInputTypeEnum.DATE,
                "Project Start Date (YYYY-MM-DD): ", new Rule[]{
                FormUtils.requiredRule
        }, isUpdate ? project.startDate() : null);

        this.endDateField = new FormField<>("endDate",
                UserInputTypeEnum.DATE,
                "Project End Date (YYYY-MM-DD): ", new Rule[]{
                FormUtils.requiredRule
        }, isUpdate ? project.endDate() : null);

        this.statusField = new FormField<>("status", UserInputTypeEnum.STRING,
                "Project Status (e.g., Planned, In Progress, Completed): ", new Rule[]{
                FormUtils.requiredRule
        }, isUpdate ? project.status().toString() : null);


        // Append the fields to the form.
        fields.add(nameField);
        fields.add(descriptionField);
        fields.add(budgetField);
        fields.add(startDateField);
        fields.add(endDateField);

        if (project == null) {
            // Skips the status since there is a screen flow for this already.
            fields.add(statusField);
        }
    }
}
