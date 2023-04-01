package com.example.eventorestapi.specifications;

public class Filter {
    private String propertyName;
    private Object value;
    private FilterOperator operator;

    public Filter(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
        this.operator = FilterOperator.EQUALS;
    }

    public Filter(String propertyName, Object value, FilterOperator operator) {
        this.propertyName = propertyName;
        this.value = value;
        this.operator = operator;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getValue() {
        return value;
    }

    public FilterOperator getOperator() {
        return operator;
    }
}