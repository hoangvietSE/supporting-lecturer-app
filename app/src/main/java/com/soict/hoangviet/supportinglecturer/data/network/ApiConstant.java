package com.soict.hoangviet.supportinglecturer.data.network;

public class ApiConstant {
    public static final String PDF_TO_PNG = "pdf/to/png";
    public static final String PPT_TO_PNG = "ppt/to/png";
    public static final String PPTX_TO_PNG = "pptx/to/png";

    public class HttpStatusCode {
        public static final int UNKNOWN = -1;
        public static final int OK = 200;
        public static final int CREATED = 201;
        public static final int NONE = 204;
        public static final int BAD_REQUEST = 400;
        public static final int UNAUTHORIZED = 401;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int PRECONDITION_FAILED = 412;
        public static final int UNPROCESSABLE = 422;
        public static final int CART_CHECK_OUT = 473;
    }

    public class HttpMessage {
        public static final String ERROR_TRY_AGAIN  = "Có lỗi xảy ra. Vui lòng thử lại";
        public static final String ERROR_NETWORK = "Vui lòng kết nối internet";
        public static final String TIME_OUT  = "Có lỗi xảy ra. Vui lòng thử lại";
    }
}
