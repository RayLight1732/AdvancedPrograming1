package com.jp.daichi.ex7calcurator.operator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class Operators {
    public static final Add ADD = new Add();
    public static final Subtract SUBTRACT = new Subtract();
    public static final Multiply MULTIPLY = new Multiply();
    public static final Divide DIVIDE = new Divide();
    public static final Pow POW = new Pow();
    public static final Mod MOD = new Mod();

    private static final Set<Operator> operators = getFields();
    private static Set<Operator> getFields() {
        Set<Operator> result = new HashSet<>();
        Field[] fields = Operators.class.getDeclaredFields();
        for (Field field:fields) {
            if (Modifier.isStatic(field.getModifiers()) && Operator.class.isAssignableFrom(field.getType())) {
                try {
                    result.add((Operator) field.get(null));
                } catch (IllegalAccessException ignore) {}
            }
        }
        return result;
    }

    public static Operator fromChar(char c) {
        for (Operator operator:operators) {
            if (operator.getChar()!= Operator.NULL && operator.getChar() == c) {
                return operator;
            }
        }
        return null;
    }
}
