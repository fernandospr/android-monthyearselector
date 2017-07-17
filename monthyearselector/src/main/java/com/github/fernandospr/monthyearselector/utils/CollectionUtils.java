package com.github.fernandospr.monthyearselector.utils;

import java.util.List;

public class CollectionUtils {

    private CollectionUtils() {
        throw new AssertionError(getClass().toString() + " cannot be instantiated.");
    }

    public static <T extends Comparable<T>> boolean containsAllBetween(List<T> list, T firstElem, T lastElem) {
        return containsAllGreaterThan(list, firstElem) && containsAllLessThan(list, lastElem);
    }

    public static <T extends Comparable<T>> boolean containsAllGreaterThan(List<T> list, T elem) {
        for (T value : list) {
            if (value.compareTo(elem) <= 0) {
                return false;
            }
        }
        return true;
    }

    public static <T extends Comparable<T>> boolean containsAllLessThan(List<T> list, T elem) {
        for (T value : list) {
            if (value.compareTo(elem) >= 0) {
                return false;
            }
        }
        return true;
    }
}
