package Controllers;

import Models.Role;
import javafx.util.StringConverter;

public class MyEnumConverter extends StringConverter<Role>{

    @Override public String toString(Role enumConstant) {
        return enumConstant.name();
    }

    @Override public Role fromString(String string) {
        return Role.valueOf(string);
    }
}