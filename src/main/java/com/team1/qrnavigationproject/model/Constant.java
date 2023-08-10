package com.team1.qrnavigationproject.model;

public class Constant {

    public final static String IMAGE_URL_REGEX = "(file:/)?(/[^\\s]+(\\.(?i)(jpg|png|gif|bmp))|https?://.*\\.(?i)(jpg|png|gif|bmp))";
    public final static String WEBSITE_URL_REGEX = "^(?i)(?:(?:https?|ftp)://)?(?:(?:(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,})|(?:\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}))(?::\\d+)?(?:/[^\\s]*)?$";;
    public final static String DATE_TIME_REGEX = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
    public final static String POSTCODE_REGEX = "^[A-Z]{1,2}[\\dR][\\dA-Z]? \\d[A-Z]{2}$";
    public static final String USERNAME_REGEX = "^[a-zA-Z\\d_!@#$%^&*()-]{3,30}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,}$";

    public static final String PHONE_REGEX = "^\\+\\d{1,3}\\s?\\d{1,14}$";

    public static final String NAME_REGEX = "^[a-zA-ZÀ-ÖØ-öø-ÿ-' ]+$";




}
