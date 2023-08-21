package com.team1.qrnavigationproject.configuration;

public interface QRNavigationPaths {

    String WELCOME_PAGE = "/qrnavigation/welcome";

    String LANDING_PAGE = "/home";

    String CONTENT_PAGE = "/content";

    String SIGN_UP_PAGE = "/qrnavigation/signup";

    String LOGIN_PAGE = "/qrnavigation/login";

    String AUTH_SIGN_UP = "/qrnavigation/auth/signup";

    String AUTH_LOGIN = "/qrnavigation/auth/login";

    String ADMIN_MAIN_PAGE = "/admin/main";

    String ADMIN_SPACES_PAGE = "/admin/places";

    String ADMIN_ORG_REG_PAGE = "/admin/organization/register";

    String ADMIN_ORG_UPDATE_PAGE = "/admin/organization/update";

    String ERROR_PAGE = "/error";

    String LOGIN = "/login";

    String LOGIN_ERROR = "/login-error";

    String LOGOUT = "/logout";


    String[] PERMITTED_PATHS = {
            WELCOME_PAGE,
            LANDING_PAGE,
            CONTENT_PAGE,
            LOGIN,
            LOGIN_ERROR,
            SIGN_UP_PAGE,
            " /qrnavigation/signup",
//            LOGIN_PAGE,
            ERROR_PAGE,
            AUTH_SIGN_UP,
            AUTH_LOGIN
    };
    String ADMIN_ORG_REG_PROCESS = "/admin/organization/register/process";
    String ADMIN_ORG_UPDATE_PROCESS = "/admin/organization/update/process";
    String ADMIN_SPACES_CREATE = "/admin/spaces/create";
    String ADMIN_SPACES_CREATE_PROCESS = "/admin/spaces/create/process";
}
