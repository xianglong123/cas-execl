package com.cas.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.cas.po.SimpleReadData;
import com.alibaba.excel.read.listener.ReadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/12/7 10:39 上午
 * @desc
 */
public class ReadDataListener implements ReadListener<SimpleReadData> {
    private static final Logger log = LoggerFactory.getLogger(ReadDataListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    private List<SimpleReadData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 这个每一条数据解析都会来调用
     * @param data
     * @param context
     */
    @Override
    public void invoke(SimpleReadData data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 清理List
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了，都回来调用
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
        saveData();
    }

    /**
     * 持久化
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        // ... DIY 持久化操作
        log.info("存储数据库成功！");
    }
}
