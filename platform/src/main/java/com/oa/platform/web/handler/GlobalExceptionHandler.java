package com.oa.platform.web.handler;

import com.oa.platform.common.StatusCode;
import com.oa.platform.exception.PlatformException;
import com.oa.platform.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * RestController 增强器(主要处理全局异常)
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        log.info("binder.getFieldDefaultPrefix {}" , binder.getFieldDefaultPrefix());
        log.info("binder.getFieldMarkerPrefix {}", binder.getFieldMarkerPrefix());

    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        //model.addAttribute("ProjectName", "InsectControl");
        //model.addAttribute("ProjectBuilder", "Feng Jianbo");
    }

    /**
     * 全局异常捕捉处理
     * @param throwable 异常
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, Object> errorException(Throwable throwable) {
        log.error("API 异常抛出：{} ", throwable);
        String throwMessage = throwable.getMessage();
        if (throwable instanceof PlatformException) {
            throwMessage = ((PlatformException)throwable).getMessage();
        }
        return StringUtil.getResultVo(StatusCode.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", throwMessage);

    }
}
