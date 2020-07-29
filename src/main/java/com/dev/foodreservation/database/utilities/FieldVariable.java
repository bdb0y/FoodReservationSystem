package com.dev.foodreservation.database.utilities;

public class FieldVariable {

    public String fieldName,
                    value;

    public FieldVariable(String name, String value){
        this.fieldName = name;
        this.value = value;
    }

    public String get(){
        return "@"+this.fieldName + " = " +this.value;
    }
}
