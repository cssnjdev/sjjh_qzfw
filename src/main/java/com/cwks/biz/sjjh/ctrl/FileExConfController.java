package com.cwks.biz.sjjh.ctrl;

import com.cwks.biz.sjjh.service.FileExConfService;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.log.LogWritter;
import com.cwks.common.core.systemConfig.BizErrorMsgContext;
import com.cwks.bizcore.comm.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * FileExConfController
 * <p>Title: FileExConfController.java</p>
 * <p>Description:数据交换前置服务根据文件交换配置接口 标准restful api</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: cssnj</p>
 *
 * @author cssnj
 * @version 1.0
 */
@RestController
@RequestMapping("/jhfw-file-conf-api")
public class FileExConfController {

    @Autowired
    private FileExConfService fileExConfService;

    /**
     * <p>url: /jhfw-file-conf-api/generate-metadata-table</p>
     * <p>Description:前置服务对外暴露的restful接口，用于处理文件交换配置自动更新表结构</p>
     *
     * @version 1.0
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/generate-metadata-table", method = {RequestMethod.POST, RequestMethod.GET})
    public ConcurrentHashMap jhdl_wbfw(HttpServletRequest request, @RequestBody String reqStr) throws Exception {
        LogWritter.sysDebug(this.getClass().getName()+".generate-metadata-table 方法开始");
        ConcurrentHashMap resMap = new ConcurrentHashMap();
        ConcurrentHashMap reqMap = new ConcurrentHashMap();
        if (reqStr == null) {
            return getResMap("-1", "", "请求报文为空！");
        } else {
            reqMap.put("REQUSET_MESSAGE", reqStr);
        }
        Map reqmap = JsonUtil.toMap(reqStr);
        if(reqmap == null){
            return getResMap("-1", "", "请求报文参数为空！");
        }
        if(reqmap.get("DS_CONF_ID") == null|| "".equals((String)reqmap.get("DS_CONF_ID"))){
            return getResMap("-1", "", "请求报文数据源参数[data_source_conf_id]为空！");
        }
        if(reqmap.get("DS_BIZ_ID") == null|| "".equals((String)reqmap.get("DS_BIZ_ID"))){
            return getResMap("-1", "", "请求报文数据源参数[DS_BIZ_ID]为空！");
        }
        if(reqmap.get("MB_ID") == null|| "".equals((String)reqmap.get("MB_ID"))){
            return getResMap("-1", "", "请求报文模板ID参数[MB_ID]为空！");
        }
        try {
            ResponseEvent resEvent = null;
            RequestEvent req = new RequestEvent();
            req.setReqCurMap(reqMap);
            resEvent = fileExConfService.generate_metadata_table(req);
            if (resEvent.getResCurMap() == null) {
                return getResMap("-1", "", "返回结果为空！");
            }
            resMap.put("code", String.valueOf(resEvent.getResCode()));
            resMap.put("message", resEvent.getResMsg());
            resMap.put("resdata", resEvent.getResStr());
        } catch (Exception e) {
            LogWritter.bizError(this.getClass().getName()+".generate-metadata-table 数据交换前置内部服务过程发生异常！error:",e);
            return getResMap("-1", "", e.getMessage());
        }
        LogWritter.sysDebug(this.getClass().getName()+".generate-metadata-table 方法结束");
        return resMap;
    }

    /**
     * <p>Description:包装异常情况的返回请求端的对象</p>
     *
     * @version 1.0
     */
    private ConcurrentHashMap getResMap(String code, String error_code, String msg) {
        ConcurrentHashMap resMap = new ConcurrentHashMap();
        String rtnMsg = "";
        Object obj = BizErrorMsgContext.singleton().getValueAsString(error_code);
        if (obj != null && !"".equals(obj)) {
            rtnMsg = (String) obj;
        }
        if (!"".equals(rtnMsg)) {
            msg = rtnMsg;
        }
        resMap.put("code", code);
        resMap.put("message", (msg==null)?"":msg);
        return resMap;
    }

}
