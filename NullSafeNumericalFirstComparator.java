import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Компаратор, сравнивающий строки по заданым правилам в task1.
 * Приоритет: null > "" > все остальные строки.
 * Разбивает строку на числа и не-числа, пытаясь вначале сравнивать численно.
 * Если после разбивания строки получились такого вида:
 * String[] a = ["123", "asd", "1"] и String[] b = ["123, "asd"]
 * то приоритет отдается кратчайшей строке (т. е. b больше a)
 */
class NullSafeNumericalFirstComparator implements Comparator<String> {
    /**
     * Splits digits and non-digits characters into tokens.
     * Example: "123asd,.654.8" splits into ["123", "asd,.", "654", ".", "8"]
     *
     * @param str given string that would be split
     * @return Tokens
     */
    private static Iterable<String> Tokenize(String str) {
        List<String> output = new ArrayList<String>();
        Matcher match = Pattern.compile("\\d+|\\D+").matcher(str);
        while (match.find()) {
            output.add(match.group());
        }
        return output;
    }

    /**
     * Compares given string with following rules:
     * <ul>
     * <li>null = null</li>
     * <li>null > "all strings"</li>
     * </ul>
     *
     * @param o1 first string to be compared
     * @param o2 second string to be compared
     * @return 1, 0, -1 if first argument is less, equal, greater than second argument
     */
    private static int nullComparator(final String o1, final String o2) {
        if (o1 == null) {
            return o2 == null ? 0 : -1;
        }
        //o1 is not null
        if (o2 == null) {
            return 1;
        }
        // o1 and o2 are not null
        throw new IllegalArgumentException("At least one argument must be null");
    }

    @Override
    public int compare(final String o1, final String o2) {
        if (o1 == null || o2 == null)
            return nullComparator(o1, o2);

        //o1 and o2 are not null
        if (o1.equals("") || o2.equals(""))
            return emptyStringComparator(o1, o2);

        //o1 and o2 are not nulls and are not empty strings
        return tokenizeAndTryCompareNumericalFirst(o1, o2);
    }

    /**
     * Compares given string with following rules:
     * <ul>
     * <li>"" = ""</li>
     * <li>"" > "all strings"</li>
     * </ul>
     *
     * @param o1 first string to be compared
     * @param o2 second string to be compared
     * @return 1, 0, -1 if first argument is less, equal, greater than second argument
     */
    private int emptyStringComparator(@NotNull final String o1, @NotNull final String o2) {
        if (o1.equals("")) {
            return o2.equals("") ? 0 : -1;
        }
        //o1 is not ""
        if (o2.equals("")) {
            return 1;
        }
        throw new IllegalArgumentException("At least one argument must be empty string");
    }

    private int tokenizeAndTryCompareNumericalFirst(@NotNull final String o1, @NotNull final String o2) {
        Iterator<String> it1 = Tokenize(o1).iterator();
        Iterator<String> it2 = Tokenize(o2).iterator();

        while (it1.hasNext() && it2.hasNext()) {
            String token1 = it1.next();
            String token2 = it2.next();

            Integer comparedIntegers = tryCompareAsIntegers(token1, token2);
            if (comparedIntegers != null && comparedIntegers != 0) {
                return comparedIntegers;
            }

            int comparedTokens = token1.compareTo(token2);
            if (comparedTokens != 0) {
                return comparedTokens;
            }
        }

        return Integer.compare(o1.length(), o2.length());
    }

    /**
     * Try numerical compare of given strings
     * @param token1 first Integer to be compared in string format
     * @param token2 second Integer to be compared in string format
     * @return if strings are parsed: -1, 0, 1. Otherwise null
     */
    private Integer tryCompareAsIntegers(String token1, String token2) {
        Integer int1 = tryParse(token1);
        Integer int2 = tryParse(token2);
        if (int1 != null && int2 != null) {
            return int1.compareTo(int2);
        } else {
            return null;
        }
    }

    /**
     * Treat given text as Integer
     * @param text Integer in string format
     * @return parsed Integer otherwise null
     */
    private static Integer tryParse(String text) {
        try {
            return new Integer(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
