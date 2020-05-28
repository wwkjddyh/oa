package com.oa.platform.common;

/**
 * 状态码
 * @author Feng
 * @date 2018/06/21
 */
public class StatusCode {

    private StatusCode() {

    }

    /**
     * 继续：100 <br/>
     * 请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。
     */
    public static int GOON = 100;

    /**
     * 切换协议 <br/>
     * 请求者已要求服务器切换协议，服务器已确认并准备切换。
     */
    public static int SWITCH_PROTOCOL = 101;

    /**
     * 成功：200 <br/>
     * 服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
     */
    public static int SUCCESS = 200;

    /**
     * 创建：201 <br/>
     * 请求成功并且服务器创建了新的资源。
     */
    public static int CREATED = 201;

    /**
     * 接受：202 <br/>
     * 服务器已接受请求，但尚未处理。
     */
    public static int ACCEPTED = 202;

    /**
     * 未授权信息：203 <br/>
     * 服务器已成功处理了请求，但返回的信息可能来自另一来源。
     */
    public static int NON_AUTH_INFO = 203;

    /**
     * 无内容: 204 <br/>
     * 服务器成功处理了请求，但没有返回任何内容。
     */
    public static int NON_CONTENT = 204;

    /**
     * 重置内容：205 <br/>
     * 服务器成功处理了请求，但没有返回任何内容。
     */
    public static int RESET_CONTENT = 205;

    /**
     * 部分内容：206 <br/>
     * 服务器成功处理了部分 GET 请求。
     */
    public static int PARTIAL_CONTENT = 206;

    /**
     * 未修改：304 <br/>
     */
    public static int NOT_MODIFIED = 304;

    /**
     * 未使用：306 <br/>
     */
    public static int UNUSED = 306;

    /**
     * 请求错误：400 <br/>
     * 服务器不理解请求的语法。
     */
    public static int BAD_REQUEST = 400;

    /**
     * 未经授权：401 <br/>
     * 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
     */
    public static int UNAUTHORIZED = 401;

    /**
     * 付款需求：402 <br/>
     */
    public static int PAYMENT_REQUIRED = 402;

    /**
     * 禁止：403 <br/>
     * 服务器拒绝请求。
     */
    public static int FORBIDDEN = 403;

    /**
     * 未找到：404 <br/>
     * 服务器找不到请求的网页。
     */
    public static int NOT_FOUNED = 404;

    /**
     * 不允许的方法：405 <br/>
     * 禁用请求中指定的方法。
     */
    public static int METHOD_NOT_ALLOWED = 405;

    /**
     * 用户请求的格式不可得：406 <br/>
     * 无法使用请求的内容特性响应请求的网页。
     */
    public static int NOT_ACCEPTABLE = 406;

    /**
     * 需要代理验证：407 <br/>
     * 此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。
     */
    public static int PROXY_AUTHENTICATION_REQUIRED = 407;

    /**
     * 请求超时：408 <br/>
     * 服务器等候请求时发生超时。
     */
    public static int REQUEST_TIMEOUT = 408;

    /**
     * 冲突：409 <br/>
     * 服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。
     */
    public static int CONFLICT = 409;

    /**
     * 用户请求的资源被永久删除，且不会再得到：410 <br/>
     * 如果请求的资源已永久删除，服务器就会返回此响应。
     */
    public static int GONE = 410;

    /**
     * 请求参数异常：411 <br/>
     * 服务器不接受不含有效内容长度标头字段的请求。
     */
    public static int REQUEST_PARAM_ERROR = 411;

    /**
     * 前提条件失败：412 <br/>
     * 服务器未满足请求者在请求中设置的其中一个前提条件。
     */
    public static int PRECONDITION_FAILED = 412;

    /**
     * 请求实体太大：413 <br/>
     * 服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。
     */
    public static int REQUEST_ENTITY_TOO_LARGE = 413;

    /**
     * 请求URI太长：414 <br/>
     * 请求的 URI（通常为网址）过长，服务器无法处理。
     */
    public static int REQUEST_URI_TOO_LONG = 414;

    /**
     * 不支持的介质类型：415 <br/>
     * 请求的格式不受请求页面的支持。
     */
    public static int UNSUPPORTED_MEDIA_TYPE = 415;

    /**
     * 请求范围不符合要求：416 <br/>
     * 如果页面无法提供请求的范围，则服务器会返回此状态代码。
     */
    public static int REQUEST_RANGE_NON_COMPLIANT = 416;

    /**
     * 未满足期望：417 <br/>
     * 服务器未满足”期望”请求标头字段的要求。
     */
    public static int UNMET_EXPECTATION = 417;

    /**
     * 验证错误：422 <br/>
     * [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
     */
    public static int UNPROCESABLE_ENTITY = 422;

    /**
     * 内部服务器错误：500 <br/>
     * 服务器遇到错误，无法完成请求。
     */
    public static int INTERNAL_SERVER_ERROR = 500;

    /**
     * 未实施：501 <br/>
     * 服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码。
     */
    public static int NOT_IMPLEMENTED = 501;

    /**
     * 坏网关：502 <br/>
     * 服务器作为网关或代理，从上游服务器收到无效响应。
     */
    public static int BAD_GATEWAY = 502;

    /**
     * 服务不可用：503 <br/>
     * 服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。
     */
    public static int SERVICE_UNAVAILABLE = 503;

    /**
     * 网关超时:504 <br/>
     * 服务器作为网关或代理，但是没有及时从上游服务器收到请求。
     */
    public static int GATEWAY_TIMEOUT = 504;

    /**
     * 不支持HTTP版本：505 <br/>
     * 服务器不支持请求中所用的 HTTP 协议版本。
     */
    public static int HTTP_VERSION_NOT_SUPPORT = 505;

    /**
     * 验证码错误：554 <br/>
     */
    public static int VERIFY_CODE_ERROR = 554;

    /**
     * 请求参数重复：555 <br/>
     * 如提交的数据中，某个参数与服务器(或数据库)中数值重复
     */
    public static int REQUEST_PARAM_REPEAT = 555;

}
