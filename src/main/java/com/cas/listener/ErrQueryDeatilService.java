package com.cas.listener;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ErrQueryDeatilService {

    private static final String url = "https://tsm.cmpay.com/tsmui_new_pro/html/coreBusinessTransactionRecord/index?_orderBy=beginTime_desc";
    private static final String detailUrl = "https://tsm.cmpay.com/tsmui_new_pro/html/query/index/tsmCoreSync/CoreSe_mobileNo_seid?_orderBy=bindDate_desc";

    private static final String cookie = "sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221886bc1d0d97c1-0c017992db8c898-1d525634-2007040-1886bc1d0daead%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg4NmJjMWQwZDk3YzEtMGMwMTc5OTJkYjhjODk4LTFkNTI1NjM0LTIwMDcwNDAtMTg4NmJjMWQwZGFlYWQifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%22%2C%22value%22%3A%22%22%7D%2C%22%24device_id%22%3A%221886bc1d0d97c1-0c017992db8c898-1d525634-2007040-1886bc1d0daead%22%7D; route=ef0af51fa840b8ed0ae311ff2c8cf995; JSESSIONID=1FFFE948ADC717D5A93F02D5FA4C8EA6";

    public MobileObj getMobileDetail(String mobileNo) {
        Map<String, Object> body = new HashMap<>();
        body.put("mobileNoOrigin", mobileNo);
        body.put("btn_serverInfoCode", "浙中");
        body.put("serverInfoCode", "zhezhong");
        body.put("iccid", "");
        body.put("sn", "");
        body.put("_pageSize", "10");
        body.put("_pageNo", "1");
        body.put("_orderBy", "");

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Length", "171");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Cookie", cookie);
        headers.put("Host", "tsm.cmpay.com");
        headers.put("Origin", "https://tsm.cmpay.com");
        headers.put("Referer", "https://tsm.cmpay.com/tsmui_new_pro/ent/businessTransactionRecord/businessTransactionRecordList.html");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

        String result = "";
        try {
            result = HttpRequest.post(detailUrl)
                    .headerMap(headers, true)//头信息，多个头信息多次调用此方法即可
                    .form(body)//表单内容
                    .timeout(10000)//超时，毫秒
                    .execute().body();
        } catch (Exception e) {
            System.out.println("异常，跳过");
            return null;
        }

        JSONArray jsonArrays = (JSONArray) JSONObject.parseObject(result).get("result");

        List<MobileObj> list = jsonArrays.toJavaList(MobileObj.class);
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            for (MobileObj obj: list) {
                if ("已激活".equals(obj.getStatus_description())) {
                    return obj;
                }
            }
            return null;
        }

    }

    public DsopObj getErrInfo(String mobileNo) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> body = new HashMap<>();
        body.put("mobileNo", mobileNo);
        body.put("btn_serverInfoCode", "西片区");
        body.put("serverInfoCode", "xiqu");
        body.put("btn_month", "202307");
        body.put("month", "202307");
        body.put("_pageSize", "10");
        body.put("_pageNo", "1");
        body.put("_orderBy", "");
        body.put("seid", "");
        body.put("transactionId", "");

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Length", "171");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Cookie", cookie);
        headers.put("Host", "tsm.cmpay.com");
        headers.put("Origin", "https://tsm.cmpay.com");
        headers.put("Referer", "https://tsm.cmpay.com/tsmui_new_pro/ent/businessTransactionRecord/businessTransactionRecordList.html");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        String result = "";
        try {
            result = HttpRequest.post(url)
                    .headerMap(headers, true)//头信息，多个头信息多次调用此方法即可
                    .form(body)//表单内容
                    .timeout(100)//超时，毫秒
                    .execute().body();
        } catch (Exception e) {
            System.out.println("异常，跳过");
            return null;
        }


        JSONArray jsonArrays = (JSONArray) JSONObject.parseObject(result).get("result");

        List<DsopObj> list = jsonArrays.toJavaList(DsopObj.class);

        System.out.println(list);

        for (DsopObj obj: list) {
//            if ("订购产品".equals(obj.getTransactionType())) {
//                return obj;
//            }
//            if ("产品个人化".equals(obj.getTransactionType())) {
//                return obj;
//            }
            if (obj.getSeid() != null && (!"".equals(obj.getSeid()))) {
                return obj;
            }
        }
        return null;
    }

    public static class MobileObj {
        private String bindCommType;
        private String status_description;
        private String availableRam;
        private String serverInfoCode;
        private String status_name;
        private String activeDeviceType_description;
        private String userNo;
        private String needSync;
        private String fixedAcRuleVersion;
        private String mobileNo;
        private String imsi;
        private String unknownRom;
        private String mac;
        private String seBatch_id;
        private String availableRom;
        private String iccid;
        private String bindDate;
        private String activeDeviceType_name;
        private String seid;
        private String imei;
        private String unknownRam;
        private String sn;
        private String id;
        private String androidId;

        public String getBindCommType() {
            return bindCommType;
        }

        public void setBindCommType(String bindCommType) {
            this.bindCommType = bindCommType;
        }

        public String getStatus_description() {
            return status_description;
        }

        public void setStatus_description(String status_description) {
            this.status_description = status_description;
        }

        public String getAvailableRam() {
            return availableRam;
        }

        public void setAvailableRam(String availableRam) {
            this.availableRam = availableRam;
        }

        public String getServerInfoCode() {
            return serverInfoCode;
        }

        public void setServerInfoCode(String serverInfoCode) {
            this.serverInfoCode = serverInfoCode;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }

        public String getActiveDeviceType_description() {
            return activeDeviceType_description;
        }

        public void setActiveDeviceType_description(String activeDeviceType_description) {
            this.activeDeviceType_description = activeDeviceType_description;
        }

        public String getUserNo() {
            return userNo;
        }

        public void setUserNo(String userNo) {
            this.userNo = userNo;
        }

        public String getNeedSync() {
            return needSync;
        }

        public void setNeedSync(String needSync) {
            this.needSync = needSync;
        }

        public String getFixedAcRuleVersion() {
            return fixedAcRuleVersion;
        }

        public void setFixedAcRuleVersion(String fixedAcRuleVersion) {
            this.fixedAcRuleVersion = fixedAcRuleVersion;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getImsi() {
            return imsi;
        }

        public void setImsi(String imsi) {
            this.imsi = imsi;
        }

        public String getUnknownRom() {
            return unknownRom;
        }

        public void setUnknownRom(String unknownRom) {
            this.unknownRom = unknownRom;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getSeBatch_id() {
            return seBatch_id;
        }

        public void setSeBatch_id(String seBatch_id) {
            this.seBatch_id = seBatch_id;
        }

        public String getAvailableRom() {
            return availableRom;
        }

        public void setAvailableRom(String availableRom) {
            this.availableRom = availableRom;
        }

        public String getIccid() {
            return iccid;
        }

        public void setIccid(String iccid) {
            this.iccid = iccid;
        }

        public String getBindDate() {
            return bindDate;
        }

        public void setBindDate(String bindDate) {
            this.bindDate = bindDate;
        }

        public String getActiveDeviceType_name() {
            return activeDeviceType_name;
        }

        public void setActiveDeviceType_name(String activeDeviceType_name) {
            this.activeDeviceType_name = activeDeviceType_name;
        }

        public String getSeid() {
            return seid;
        }

        public void setSeid(String seid) {
            this.seid = seid;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getUnknownRam() {
            return unknownRam;
        }

        public void setUnknownRam(String unknownRam) {
            this.unknownRam = unknownRam;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAndroidId() {
            return androidId;
        }

        public void setAndroidId(String androidId) {
            this.androidId = androidId;
        }
    }


    public static class DsopObj {

        private String serverInfoCode;
        private String instanceName;
        private String resultCode;
        private String mobileNo;
        private String transactionId;
        private String transactionType;
        private String executionTime;
        private String scpType;
        private String seid;
        private String endTime;
        private String beginTime;
        private String resultDescription;
        private String id;
        private String communicateType;

        public String getServerInfoCode() {
            return serverInfoCode;
        }

        public void setServerInfoCode(String serverInfoCode) {
            this.serverInfoCode = serverInfoCode;
        }

        public String getInstanceName() {
            return instanceName;
        }

        public void setInstanceName(String instanceName) {
            this.instanceName = instanceName;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getExecutionTime() {
            return executionTime;
        }

        public void setExecutionTime(String executionTime) {
            this.executionTime = executionTime;
        }

        public String getScpType() {
            return scpType;
        }

        public void setScpType(String scpType) {
            this.scpType = scpType;
        }

        public String getSeid() {
            return seid;
        }

        public void setSeid(String seid) {
            this.seid = seid;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getResultDescription() {
            return resultDescription;
        }

        public void setResultDescription(String resultDescription) {
            this.resultDescription = resultDescription;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCommunicateType() {
            return communicateType;
        }

        public void setCommunicateType(String communicateType) {
            this.communicateType = communicateType;
        }
    }

}
