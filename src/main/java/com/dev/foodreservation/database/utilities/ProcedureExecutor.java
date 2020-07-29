package com.dev.foodreservation.database.utilities;

import java.util.ArrayList;
import java.util.List;

public class ProcedureExecutor {

    private String name;
    private List<FieldVariable> fieldVariables
            = new ArrayList<>();

    public ProcedureExecutor(String name){
         this.name = name;
    }

    public void addVariable(String key, Object value) {
        fieldVariables.add(new FieldVariable(key, String.valueOf(value)));
    }

    public String get(){
        String result = "EXEC " + this.name + " ";
        int handler = 0;
        for(FieldVariable variable : fieldVariables){
            if(handler++ > 0) result += ", ";
            result += variable.get();
        }
        return result;
    }
}
