/**
 * Author: ng-hoangnam
 * Date: 31/01/2023
 * Description:
 */

package com.viespa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static String validate(String data, String test)  {
        Matcher matcher = Pattern.compile(test).matcher(data);
        if (matcher.find()) return data;
        return "0";
    }
}
