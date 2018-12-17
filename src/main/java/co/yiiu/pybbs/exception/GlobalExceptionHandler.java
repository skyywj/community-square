package co.yiiu.pybbs.exception;

import co.yiiu.pybbs.util.BaseModel;
import co.yiiu.pybbs.util.JsonUtil;
import co.yiiu.pybbs.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  private BaseModel baseModel;

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return HttpStatus.valueOf(statusCode);
  }

  /**
   * 错误页面统一处理
   *
   * @param request
   * @param e
   * @return
   * @throws Exception
   */
  @ExceptionHandler(value = Exception.class)
  public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
    e.printStackTrace();
    String accept = request.getHeader("Accept");
    if (accept.contains("text/html")) {
      ModelAndView mav = new ModelAndView();
      mav.addObject("exception", e);
      mav.addObject("errorCode", getStatus(request));
      mav.setViewName("front/error");
      return mav;
    } else /*if (accept.contains("application/json"))*/ {
      Result result = new Result();
      result.setCode(201);
      result.setDescription(e.getMessage());
      response.setContentType("application/json;charset=utf-8");
      response.getWriter().write(JsonUtil.objectToJson(result));
    }
    return null;
  }

  /**
   * 接口错误统一处理
   *
   * @param e
   * @return
   * @throws Exception
   */
  @ExceptionHandler(value = ApiException.class)
  @ResponseBody
  public Result jsonErrorHandler(ApiException e) throws Exception {
    Result result = new Result();
    result.setCode(e.getCode());
    result.setDescription(e.getMessage());
    return result;
  }
}
