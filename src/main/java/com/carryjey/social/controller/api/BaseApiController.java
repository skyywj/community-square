package com.carryjey.social.controller.api;

import com.carryjey.social.controller.front.BaseController;
import com.carryjey.social.util.Result;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public class BaseApiController extends BaseController {

    protected Result success() {
        return success(null);
    }

    protected Result success(Object detail) {
        Result result = new Result();
        result.setCode(200);
        result.setDescription("SUCCESS");
        result.setDetail(detail);
        return result;
    }

    protected Result error(String description) {
        Result result = new Result();
        result.setCode(201);
        result.setDescription(description);
        return result;
    }
}
