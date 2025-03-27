package com.pomanager.economiccalender.Utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeFormatter {
    public static String[] timeZonNames = {"Line Islands: Kiritibati", "Rawaki Islands: Enderbury Kiribati", "Wellington, Fiji, Marshall Islands", "Norfolk Island", "Solomon Islands", "Lord Howe Island", "Caroline Islands, Papua New Guinea", "Darwin, Adelaide", "Japan, Korea, Indonesia (Sumatra, Java, West & Central Kalimantan)", "China, Indonesia (Bali, South & East Kalimantan), Malaysia, Singapore, Western Australia", "Cambodia, Christmas Island, Lao, Thailand,Vietnam", "Cocos Islands", "Bangladesh, Bhutan, Kazakhstan, Kyrgyzstan, Sri Lanka", "India", "Maldives, Pakistan, Tajikistan, Turkmenistan, Uzbekistan", "Kabul, Afghanistan", "Abu Dhabi, UAE, Muscat, Tblisi, Volgograd", "Tehran, Iran", "Moscow, Kuwait, Nairobi, Riyadh", "Athens, Helsinki, Istanbul, Jerusalem, Harare, Zimbabwe", "Paris, Berlin, Amsterdam, Brussels, Vienna, Madrid, Rome, Bern, Stockholm, Oslo", "London, Dublin, Edinburgh, Lisbon, Reykjavik, Casablanca", "Azores, Cape Verde Islands", "South Georgia & The South Sandwich Islands", "Brasilia, Buenos Aires, Georgetown", "Newfoundland", "Caracas, La Paz", "Bogota, Lima, New York", "Mexico City, Saskatchewan", "Alberta, British Columbia N.E., Denver, Edmonton, Phoenix, Salt Lake City, Santa Fe", "Los Angeles, San Diego, San Francisco", "Pitcairn Islands", "Alaska", "French Polynesia ", "Hawaii, Honolulu", "Midway Islands, American Samoa", "Eniwetok, Kwaialein"};

    public static String[] timeZonAdds = {"14", "13", "12", "11.5", "11", "10.5", "10", "9.5", "9", "8", "7", "6.5", "6", "5.5", "5", "4.5", "4", "3.5", "3", "2", "1", "0", "-1", "-2", "-3", "-3.5", "-4", "-5", "-6", "-7", "-8", "-8.5", "-9", "-9.5", "-10", "-11", "-12"};

    public static String[] timeZoneTimes = {"+14:00 GMT", "+13:00 GMT", "+12:00 GMT", "+11:30 GMT", "+11:00 GMT", "+10:30 GMT", "+10:00 GMT", "+9:30 GMT", "+9:00 GMT", "+8:00 GMT", "+7:00 GMT", "+6:30 GMT", "+6:00 GMT", "+5:30 GMT", "+5:00 GMT", "+4:30 GMT", "+4:00 GMT", "+3:30 GMT", "+3:00 GMT", "+2:00 GMT", "+1:00 GMT", "+0:00 GMT", "-1:00 GMT", "-2:00 GMT", "-3:00 GMT", "-3:30 GMT", "-4:00 GMT", "-5:00 GMT", "-6:00 GMT", "-7:00 GMT", "-8:00 GMT", "-8:30 GMT", "-9:00 GMT", "-9:30 GMT", "-10:00 GMT", "-11:00 GMT", "-12:00 GMT"};

    public static String getCurrentGMT() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date());
    }

    public static String getCurrentTimeString(Context context) {
        float timeStr = Float.parseFloat(PreferenceManager.getInstance(context).getSelectedTimezone());
        int hours = (int) timeStr;
        int minutes = (int) ((timeStr - hours) * 60);
        String timeZoneString = String.format("GMT%+03d:%02d", hours, minutes);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        sdf.setTimeZone(timeZone);

        return sdf.format(new Date());
    }

    public static String getDefaultTimeZone() {
        String format = new SimpleDateFormat("Z", Locale.US)
                .format(Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault()).getTime());
        if (format.charAt(1) == '0') {
            format = format.charAt(0) + format.substring(2);
        }
        if (format.charAt(0) == '+') {
            format = format.substring(1);
        }
        return format.substring(format.length() - 2).equals("00")
                ? format.substring(0, format.length() - 2)
                : (format.charAt(format.length() - 2) == '3' || format.charAt(format.length() - 2) == '5')
                ? format.substring(0, format.length() - 2).concat(".5")
                : "0";
    }

    public static String convertApiTimeByTimeZone(Context context, String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(dateString);

            double offset = Double.parseDouble(PreferenceManager.getInstance(context).getSelectedTimezone());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int hours = (int) offset;
            int minutes = (int) ((offset - hours) * 60);

            calendar.add(Calendar.HOUR_OF_DAY, hours);
            calendar.add(Calendar.MINUTE, minutes);

            Date updatedDate = calendar.getTime();

            SimpleDateFormat parse = new SimpleDateFormat("HH:mm");
            return parse.format(updatedDate);
        } catch (Exception ignored) {
        }

        return "";
    }

}
