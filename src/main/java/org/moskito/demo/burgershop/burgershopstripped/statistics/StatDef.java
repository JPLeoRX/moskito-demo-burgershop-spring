package org.moskito.demo.burgershop.burgershopstripped.statistics;

import java.util.ArrayList;
import java.util.List;

public enum StatDef {
    NUMBER("Number"),
    VOLUME("Volume");

    private String statName;

    private StatDef(final String statName) {
        this.statName = statName;
    }

    public String getStatName() {
        return statName;
    }

    public static List<String> getStatNames() {
        List<String> ret = new ArrayList<String>(StatDef.values().length);
        for (StatDef value : StatDef.values()) {
            ret.add(value.getStatName());
        }
        return ret;
    }

    public static StatDef getValueByName(String statName) {
        for (StatDef value : StatDef.values()) {
            if (value.getStatName().equals(statName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No such value with name: " + statName);
    }
}