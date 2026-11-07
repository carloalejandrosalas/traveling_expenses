package main.java.forms.auth;

import main.java.AppContext;
import main.java.enums.UserInputTypeEnum;
import main.java.forms.common.Form;
import main.java.forms.common.FormField;

import java.util.ArrayList;

public class LoginForm extends Form {
    public final FormField<String> usernameField = new FormField<>("username",
            UserInputTypeEnum.STRING,
            "Username: ");

    public final FormField<String> passwordField = new FormField<>("password",
            UserInputTypeEnum.PASSWORD,
            "Password: ");

    public LoginForm(AppContext context) {
        var fields = new ArrayList<FormField<?>>();
        super(context, fields);

        fields.add(usernameField);
        fields.add(passwordField);
    }
}
