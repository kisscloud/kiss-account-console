package com.kiss.accountconsole.regex;

public class Regex {
    public static final String SINGLE_PERMISSION = ".+";
    public static final String PERMISSION_ARRAY = "^\\[(.*)\\]$";
    public static final String NOT_SINGLE_PERMISSION = "^!(.+)";
    public static final String NOT_PERMISSION_ARRAY = "^!\\[(.*)\\]$";
    public static final String ONLY_CHARACTER = ".+";
    public static final String CHARACTER_POINT = ".+\\..+";
    public static final String CHARACTER_POINT_BRACKET = ".+\\.\\[\\]\\..+";
}
