package com.pomanager.economiccalender.Utils;

import java.util.ArrayList;
import java.util.List;

public class CountriesFilter {
    public static ArrayList<String> allCountries = new ArrayList<>();

    static {
        allCountries.add("ae");
        allCountries.add("ar");
        allCountries.add("at");
        allCountries.add("au");
        allCountries.add("be");
        allCountries.add("bg");
        allCountries.add("bh");
        allCountries.add("br");
        allCountries.add("bw");
        allCountries.add("ca");
        allCountries.add("ch");
        allCountries.add("cl");
        allCountries.add("cn");
        allCountries.add("co");
        allCountries.add("cr");
        allCountries.add("cy");
        allCountries.add("cz");
        allCountries.add("de");
        allCountries.add("dk");
        allCountries.add("ec");
        allCountries.add("ee");
        allCountries.add("eg");
        allCountries.add("es");
        allCountries.add("eu");
        allCountries.add("fi");
        allCountries.add("fr");
        allCountries.add("gb");
        allCountries.add("gr");
        allCountries.add("hk");
        allCountries.add("hr");
        allCountries.add("hu");
        allCountries.add("id");
        allCountries.add("ie");
        allCountries.add("il");
        allCountries.add("in");
        allCountries.add("is");
        allCountries.add("it");
        allCountries.add("jo");
        allCountries.add("jp");
        allCountries.add("ke");
        allCountries.add("kr");
        allCountries.add("kw");
        allCountries.add("lb");
        allCountries.add("lk");
        allCountries.add("lt");
        allCountries.add("lu");
        allCountries.add("lv");
        allCountries.add("ma");
        allCountries.add("mt");
        allCountries.add("mu");
        allCountries.add("mw");
        allCountries.add("mx");
        allCountries.add("my");
        allCountries.add("na");
        allCountries.add("nl");
        allCountries.add("no");
        allCountries.add("nz");
        allCountries.add("om");
        allCountries.add("pe");
        allCountries.add("ph");
        allCountries.add("pk");
        allCountries.add("pl");
        allCountries.add("ps");
        allCountries.add("pt");
        allCountries.add("qa");
        allCountries.add("ro");
        allCountries.add("ru");
        allCountries.add("rw");
        allCountries.add("sa");
        allCountries.add("se");
        allCountries.add("sg");
        allCountries.add("si");
        allCountries.add("sk");
        allCountries.add("th");
        allCountries.add("tn");
        allCountries.add("tr");
        allCountries.add("tw");
        allCountries.add("tz");
        allCountries.add("ua");
        allCountries.add("us");
        allCountries.add("ve");
        allCountries.add("vn");
        allCountries.add("za");
        allCountries.add("zw");
    }

    public static String getDefaultSelectedCountry() {
        List<String> result = new ArrayList<>();
        for (String countryCode : allCountries) {
            result.add(String.valueOf(countryCode));
        }
        return String.join(",", result);
    }

    public static void addCountry(List<String> selectedCountries, String countryCode) {
        if (!countryCode.trim().isEmpty() && !selectedCountries.contains(countryCode)) {
            selectedCountries.add(countryCode);
        }
    }

    public static void removeCountry(List<String> selectedCountries, String countryCode) {
        if (!countryCode.trim().isEmpty()) {
            selectedCountries.remove(countryCode);
        }
    }

    public static boolean containsCountry(List<String> selectedCountries, String countryCode) {
        return selectedCountries.contains(countryCode);
    }
}
