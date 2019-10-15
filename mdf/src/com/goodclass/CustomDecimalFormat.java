package com.goodclass;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CustomDecimalFormat {

    public static String format(double value,
                                String currencySymbol,
                                RoundingMode mode,
                                char decimalSymbol,
                                char groupingSymbol,
                                int groupingSize,
                                int minFractionDigits,
                                int maxFactionDigits) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator(decimalSymbol);
        otherSymbols.setGroupingSeparator(groupingSymbol);

        DecimalFormat df = new DecimalFormat("#" + currencySymbol, otherSymbols);
        df.setRoundingMode(mode);
        df.setMinimumFractionDigits(minFractionDigits);
        df.setMaximumFractionDigits(maxFactionDigits);
        df.setGroupingSize(groupingSize);
        if (groupingSize > 0) {
            df.setGroupingUsed(true);
        }
        return df.format(value);
    }

    public static void main(String[] args) {
        double length = 10000400.12390334d;

        String text = CustomDecimalFormat.format(length,
                " $",
                RoundingMode.HALF_UP,
                ',',
                '.',
                3,
                1,
                3);

        System.out.println(text);
    }

}

