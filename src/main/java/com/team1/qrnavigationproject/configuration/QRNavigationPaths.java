package com.team1.qrnavigationproject.configuration;

public interface QRNavigationPaths {

    public static final String WELCOME_PAGE = "/qrnavigation/welcome";

    public static final String LANDING_PAGE = "/home";

    public static final String CONTENT_PAGE = "/content";

    public static final String SIGN_UP_PAGE = "/qrnavigation/signup";

    public static final String LOGIN_PAGE = "/qrnavigation/login";

    public static final String AUTH_SIGN_UP = "/qrnavigation/auth/signup";

    public static final String AUTH_LOGIN = "/qrnavigation/auth/login";

    public static final String ADMIN_MAIN_PAGE = "/admin/main";

    public static final String ERROR_PAGE = "/error";

    public static final String LOGOUT =  "/logout";
    public static String [] PERMITTED_PATHS = {
            WELCOME_PAGE,
            LANDING_PAGE,
            CONTENT_PAGE,
            SIGN_UP_PAGE,
            LOGIN_PAGE,
            AUTH_LOGIN,
            AUTH_SIGN_UP,
            ADMIN_MAIN_PAGE,
            ERROR_PAGE
    };
}
