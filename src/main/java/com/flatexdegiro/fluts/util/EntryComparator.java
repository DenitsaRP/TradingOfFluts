package com.flatexdegiro.fluts.util;

import java.util.Comparator;
import java.util.Map;

public class EntryComparator implements Comparator<Map.Entry<Integer, Integer>> {
    @Override
    public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
        int valueComparison = o2.getValue().compareTo(o1.getValue());

        if (valueComparison != 0) {
            return valueComparison;
        }

        return o1.getKey().compareTo(o2.getKey());
    }
}
