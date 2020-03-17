package com.soict.hoangviet.supportinglecturer.utils;

public class Define {
    private Define() {
    }

    public static final String PREF_FILE_NAME = "oab_pref";
    public static final String PREF_DEVICE = "device_pref";
    public static final String PREF_LANGUAGE = "language_pref";
    public static final String REALM_NAME = "oab_database";

    public static final long DEFAULT_TIMEOUT = 5L;
    public static final long CLICK_TIME_INTERVAL = 1200L;

    public static class Api {

        public static final String CONTENT_TYPE = "Content-Type: application/json";

        public static class Url {
            public static final String YOUTUBE_URL = "https://www.googleapis.com/youtube/v3/search";
            public static final String FACEBOOK_URL = "https://graph.facebook.com/v6.0/me/live_videos";
        }

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
            public static final String SECRET = "Secret";


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

    public static class IntentKey {
        public static final String KEY_INTENT_USER_PERMISSION = "key_intent_user_permission";
        public static final String LECTURE = "lecture";
        public static final String EXTRA_DATA = "data";
        public static final String FROM_LOGIN = "isFromLogin";
        public static final String EXTRA_RESULT_CODE = "result_code";
        public static final String EXTRA_VIDEO_URL = "video_url";
        public static final String EXTRA_PREVIEW_TYPE = "preview_type";
        public static final String ACCOUNT = "account";

    }

//    public static final class file {
//        public static final String KEY_INTENT_PATH_DIR = "selected_folder";
//        public static final String STORAGE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
//
//    }

    public static final class KeyPreference {
        public static final String IS_LOGINED = "isSignIn";
        public static final String IS_LIVESTREAMED = "isLiveStreamed";
        public static final String USER_ID = "userId";
        public static final String LOGIN_FROM_FACEBOOK = "isFacebookSignIn";
        public static final String RTMP_FACEBOOK = "rtmpFacebook";
        public static final String LOGIN_FROM_GOOGLE = "isGoogleSignIn";
        public static final String ACCOUNT_NAME = "accountName";
        public static final String YOUTUBE_NAME = "youtubeName";
        public static final String BROADCAST_ID = "broadcastId";
        public static final String RTMP_GOOGLE = "rtmpGoogle";
        public static final String SETTING_ZOOM = "settingZoom";
        public static final String LANGUAGE = "language";

        //Record
        public static final String RECORD_AUDIO = "key_record_audio";
        public static final String VIDEO_RESOLUTION = "key_video_resolution";
        public static final String VIDEO_FPS = "key_video_fps";
        public static final String VIDEO_BITRATE = "key_video_bitrate";
        public static final String SHOW_NOTIFICATION = "key_show_notification";
        public static final String VIDEO_ENCODER = "key_video_encoder";
        public static final String AUDIO_SOURCE = "key_audio_source";
        public static final String OUTPUT_FORMAT = "key_output_format";
    }

    public static final class RequestCode {

        public static final int REQUEST_ACCOUNT_PICKER = 200;
        public static final int REQUEST_LOGIN = 201;
        public static final int REQUEST_LOGIN_GOOGLE = 202;
        public static final int REQUEST_RECOVERY_ACCOUNT = 203;
        public static final int RC_SIGN_IN = 9001;

    }

    public static class Languages {
        public static final String VIETNAM = "vi";
        public static final String ENGLAND = "en";
    }

    public static final class Action {
        //Action
        public static final int ACTION_START_RECORDING = 1;
        public static final int ACTION_STOP_RECORDING = 2;
        public static final int ACTION_PAUSE_RECORDING = 3;
        public static final int ACTION_RESUME_RECORDING = 4;
    }


}
