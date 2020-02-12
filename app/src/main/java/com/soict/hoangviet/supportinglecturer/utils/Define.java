package com.soict.hoangviet.supportinglecturer.utils;

public class Define {
    private Define() {
    }

    public static final String PREF_FILE_NAME = "oab_pref";
    public static final String PREF_DEVICE = "device_pref";
    public static final String PREF_LANGUAGE = "language_pref";
    public static final String REALM_NAME = "oab_database";

    public static final long DEFAULT_TIMEOUT = 5L;
    public static final long CLICK_TIME_INTERVAL = 300L;

    public static class Api {

        public static final String CONTENT_TYPE = "Content-Type: application/json";
        public static final String LOGIN_URL = "app_api/v1/auth/user";


        public static class BaseResponse {
            public static final String SUCCESS = "success";
            public static final String STATUS = "status";
            public static final String MESSAGE = "msg";
            public static final String DATA = "data";
            public static final String PAGE = "page";
            public static final String CURRENT_PAGE = "current_page";
            public static final String TOTAL_PAGE = "total_page";
            public static final String ERROR = "error";
            public static final String ERROR_CODE = "error_code";
            public static final String ERROR_MESSAGE = "error_message";
            public static final int DEFAULT_INDEX = 1;
            public static final int DEFAULT_LIMIT = 10;
        }

        public static class Error {
            // Declare variable name by function description
            public static final String NO_NETWORK_CONNECTION = "NO_NETWORK_CONNECTION";
            public static final String TIME_OUT = "TIME_OUT";
            public static final String UNKNOWN = "UNKNOWN";
        }

        public static class Key {
            public static final String AUTHORIZATION = "Authorization";
            public static final String LOGIN_NAME = "login_name";
            public static final String PASSWORD = "password";
            public static final String TOKEN = "token";
            public static final String DEVICE_TOKEN_ID = "device_token_id";
            public static final String IS_LOGIN = "is_login";
            public static final String LOGIN_RESPONSE = "login_response";
            public static final String LANGUAGE = "language";
        }

        public static class Http {
            public static final int OK = 200;
            public static final int NO_CONTENT = 204;
            public static final int BAD_REQUEST = 400;
            public static final int RESPONSE_CODE_ACCESS_TOKEN_EXPIRED = 403;
            public static final int NOT_FOUND = 404;
        }

        public static class Query {
            public static final String ID = "id";
            public static final String DEVICE_ID = "device_id";
            public static final String CATEGORY = "category_id";
            public static final String PAGE = "page";
            public static final String LIMIT = "limit";
            public static final String KEYWORD_SEARCH = "s";
            public static final String BEARER = "Bearer ";
            public static final String AUTHORIZATION = "Authorization";

        }

        public static class Method {
            public static final String PUT = "PUT";
            public static final String DELETE = "DELETE";
        }
    }

    public static class Bus {
        public static final String ACCESS_TOKEN_EXPIRED = "ACCESS_TOKEN_EXPIRED";
    }

    public class ResponseStatus {
        public static final int LOADING = 1;
        public static final int SUCCESS = 2;
        public static final int ERROR = 0;
    }

    public class Database {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "app_database";

        public class User {
            public static final String TABLE_NAME = "dtb_user";
            public static final String ID = "id";
            public static final String LOG_IN = "login";
            public static final String AVATAR_URL = "avatar_url";
            public static final String NAME = "name";
            public static final String COMPANY = "company";
            public static final String EMAIL = "email";
            public static final String LOCATION = "location";
        }

        public class Repo {
            public static final String TABLE_NAME = "dtb_repo";
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String FULL_NAME = "full_name";
            public static final String DESCRIPTION = "description";
            public static final String CONTRIBUTORS_URL = "contributors_url";
        }
    }

    public static class Intent {
        public static final String REPO_OWNER = "repo_owner";
        public static final String REPO_NAME = "repo_name";
    }
}
