package com.juniorsfredo.xtreme_management_api.domain.models.enums;

import com.juniorsfredo.xtreme_management_api.domain.exceptions.InvalidMonthValueException;

public enum PlanTypes {
    MONTHLY(1),
    QUARTERLY(4),
    ANNUAL(12);

    private final int month;

    PlanTypes(int month) {
        if (month != 1 && month != 4 && month != 12) {
            throw new InvalidMonthValueException("Invalid month value: " + month);
        }
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public static PlanTypes fromMonth(int month) {
        for (PlanTypes type : values()) {
            if (type.getMonth() == month) {
                return type;
            }
        }
        throw new InvalidMonthValueException("No plan type with month value: " + month);
    }
}
