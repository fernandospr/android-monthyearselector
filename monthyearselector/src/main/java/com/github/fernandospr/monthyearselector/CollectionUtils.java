package com.github.fernandospr.monthyearselector;

import java.util.List;

class CollectionUtils {

    private CollectionUtils() {
        throw new AssertionError(getClass().toString() + " cannot be instantiated.");
    }

    static <T extends Comparable<T>> boolean containsAllBetween(List<T> list, T firstElem, T lastElem) {
        return containsAllGreaterThan(list, firstElem) && containsAllLessThan(list, lastElem);
    }

    static <T extends Comparable<T>> boolean containsAllGreaterThan(List<T> list, T elem) {
        for (T value : list) {
            if (value.compareTo(elem) <= 0) {
                return false;
            }
        }
        return true;
    }

    static <T extends Comparable<T>> boolean containsAllLessThan(List<T> list, T elem) {
        for (T value : list) {
            if (value.compareTo(elem) >= 0) {
                return false;
            }
        }
        return true;
    }
}
