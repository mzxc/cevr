/*
 * 文 件 名:  ResultBuild.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-8-30
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.cevr.component.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果构建
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-30]
 * @see #ResultBuild
 * @since 1.0
 */
public abstract class ResultBuild {
    public static Map<String, Object> init(final boolean ifSuccess, final String msg, final Object data) {
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", ifSuccess);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
