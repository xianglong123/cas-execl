package com.cas.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.cas.utils.CustomConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @description: 同步数字身份开通数据到江苏
 * @author: xianglong
 * @create: 2023-08-24 11:10
 **/
@Service
public class DicpUserOpenServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(DicpUserOpenServiceImpl.class);

    /**
     * 数据文件分隔符
     */
    private static final String DATA_DIVIDE = "|";

    public void syncData() {
        LocalDate today = LocalDate.now().minusDays(1);
        String fileName = "/Users/xianglong/IdeaProjects/cas-execl/2222.txt";
        dataProcess(this::uploadLocal, this::uploadRemote, this::writeLocal, this::queryList, fileName, today);
    }


    private List<User> queryList(LocalDate s, LocalDate e) {
        List<User> list = new ArrayList<>();
        list.add(new User("xianglong", "12"));
        list.add(new User("lilei", "23"));
        list.add(new User("liufang", "34"));
        return list;
    }

    /**
     * 数据处理
     * 参数1：控制上传本地逻辑
     * 参数2：控制上传远端逻辑
     * 参数3：本地数据集合处理逻辑抽取
     * 参数4：集合的数据来源
     * 参数5：文件存储地址
     * 参数6：查询数据时间
     * 
     * 时间可以自行修改 "today" 的取值
     */
    private <T> void dataProcess(CustomConsumer<List<T>, String, BiConsumer<OutputStreamWriter, List<T>>> biConsumer,
                                 Consumer<String> consumer,
                                 BiConsumer<OutputStreamWriter, List<T>> bic,
                                 BiFunction<LocalDate, LocalDate, List<T>> biFunction,
                                 String fileName, LocalDate today) {
        biConsumer.accept(biFunction.apply(today, today), fileName, bic);
        consumer.accept(fileName);
    }

    /**
     * 具体的方法
     * @param osw
     * @param dos
     */
    private void writeLocal(OutputStreamWriter osw, List<User> dos) {
        try (BufferedWriter bw = new BufferedWriter(osw)) {
            if (ObjectUtil.isNotNull(dos)) {
                for (User bo : dos) {
                    bw.write(listProcess(bo.getName(), bo.getAge()));
                    bw.newLine();
                    bw.flush();
                }
            }
        } catch (Exception e) {
            log.info("导出明细数据到文件失败", e);
        }
    }

    /**
     * 数据集合 单数据 处理
     */
    private String listProcess(String... val) {
        StringBuilder sb = new StringBuilder();
        for (String va : val) {
            sb.append(StrUtil.blankToDefault(va, "")).append(DATA_DIVIDE);
        }
        return sb.toString();
    }

    /**
     * 文件本地生成
     */
    private <T> void uploadLocal(List<T> dos, String fileName, BiConsumer<OutputStreamWriter, List<T>> biConsumer) {
        try (OutputStreamWriter osw = new OutputStreamWriter(Files.newOutputStream(Paths.get(fileName)), StandardCharsets.UTF_8)) {
            biConsumer.accept(osw, dos);
        }catch (Exception e){
            log.info("导出明细数据到文件失败，文件名：{}",fileName);
            log.info(e.getMessage());
        }
    }

    /**
     * 文件上传远端
     */
    private void uploadRemote(String fileName) {
        // todo
        log.info("上传苏证通平台");
    }

    class User {

        public User(String name, String age) {
            this.name = name;
            this.age = age;
        }

        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }


}
