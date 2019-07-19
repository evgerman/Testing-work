package ru.cbr.edu.search;

import java.util.Arrays;

public enum SearchOperation {
    EQUALITY("="), GREATER_THAN("=>"), LESS_THAN("<="), CONTAINS("%");

    private final String name;

    SearchOperation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SearchOperation[] getDateSearchOps() {
        return new SearchOperation[]{ EQUALITY, GREATER_THAN, LESS_THAN };
    }

    public static SearchOperation getByName(String name) {
        for (SearchOperation op : values()) {
            if (op.name.equals(name)) {
                return op;
            }
        }
        return null;
    }
}