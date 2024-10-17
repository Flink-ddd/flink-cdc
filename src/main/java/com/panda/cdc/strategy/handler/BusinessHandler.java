//package com.rmpl.cdc.strategy.handler;
//
//import akka.japi.tuple.Tuple3;
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import com.rmpl.cdc.dao.entity.MSearchIndexConfig;
//import com.rmpl.cdc.db.OperatorTypeEnum;
//import com.rmpl.cdc.mq.ChangeDataCommonVO;
//import com.rmpl.cdc.strategy.entity.BaseBO;
//import com.rmpl.cdc.utils.BeanUtils;
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.flink.api.common.functions.RuntimeContext;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.functions.sink.SinkFunction;
//import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
//import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
//import org.apache.flink.streaming.connectors.elasticsearch2.ElasticsearchSink;
//import org.apache.flink.table.api.EnvironmentSettings;
//import org.apache.flink.table.api.Table;
//import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.client.Requests;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.springframework.jdbc.support.JdbcUtils;
//import org.springframework.stereotype.Service;
//
//import java.lang.reflect.Field;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//import java.net.UnknownHostException;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.apache.flink.table.api.Expressions.$;
//
///**
// * @author muxh
// * @create 2023-04-20 14:01
// **/
//@Service
//@Log4j2
//public class BusinessHandler implements SinkFunction<Tuple3<Object, String, String>> {
//    private Connection connection;
//    private PreparedStatement preparedStatement;
//    public static final String SYSTEM_KEY_PREFIX = "search:search_center_key_";
//    /**
//     * 后期可以读取搜索库所在的MySQL驱动信息
//     */
//    String username = "root";
//    String password = "625211234";
//    String drivername = "com.mysql.jdbc.Driver";
//    String dburl = "jdbc:mysql://127.0.0.1:3306/search_center";
//
//    @Override
//    public void invoke(Tuple3<Object, String, String> value) throws Exception {
//        log.info("进入业务核心方法，当前请求参数为{},{},{}", value.t1(), value.t2(), value.t3());
//        ChangeDataCommonVO changeDataCommonVO = BeanUtils.convertBean(value.t1(), ChangeDataCommonVO.class);
//        //1:先查询searchConfig表数据是否存在
//        String sql = "select * from m_search_index_config where involve_database = ?";
//        List<MSearchIndexConfig> searchIndexConfigList = getQuery(sql, changeDataCommonVO);
//        if (CollectionUtils.isNotEmpty(searchIndexConfigList)) {
//            searchIndexConfigList.stream().forEach(mSearchIndexConfig -> {
//                //当前数据库变更为新增
//                //2:如果当前是数据新增的操作，根据数据唯一标识字段查询到Redis中的索引模版（索引模版在搜索中心的初始化接口中生成），进行数据赋值，然后在ES中新增数据。
//                if (OperatorTypeEnum.INSERT.getOperatorType().equals(value.t2())) {
//                    this.handleInsert(mSearchIndexConfig, value.t3());
//                }
//                //当前数据库变更为修改
//                //3:如果是数据修改的操作，根据查询到的index，type，数据库唯一标识字段（ES中数据ID）修改ES索引下的信息
//                if (OperatorTypeEnum.UPDATE.getOperatorType().equals(value.t2())) {
//                    this.handleUpdate(mSearchIndexConfig, value.t3());
//                }
//            });
//        }
//        SinkFunction.super.invoke(value);
//    }
//
//    private void handleInsert(MSearchIndexConfig mSearchIndexConfig, String t3) {
//        log.info("进入新增ES数据的流程");
//        //ES中单条数据的唯一ID
//        String id = this.getQueryEsId(mSearchIndexConfig, t3);
//        /** 疑问点
//         *  如果当前数据库变更的表没有存executeIdentMapper这个字段的话，那么是不是可以通过CDC捕捉到数据变更后，通过flink SQL查询别的表得到这个字段？
//         *  但是会相当麻烦，因为索引种类很多，监控的数据库表很多，这块规则不好规划。
//         *  就比如售后订单列表中，用售后单号作为了ES单条json数据的唯一ID，但是售后列表中所监控的相关数据表中，有可能没有return_no这个字段。但是为了规范起见，减少解析数据的操作，建议当前需要走ES搜索的接口，监控的相关数据表中，都要有这个唯一ID的字段。
//         */
//        //查询redis数据模版（在flink中写连接redis的代码，可以从nacos配置文件中直接读取。）
//        //解析变更后的String数据和数据模版，然后把变更后的String字段数据赋值在数据模版中。
//        //调用写入es的方法
//        writeES(mSearchIndexConfig, t3, id);
//    }
//
//    private void handleUpdate(MSearchIndexConfig mSearchIndexConfig, String t3) {
//        log.info("进入修改ES数据的流程");
//        //ES中单条数据的唯一ID
//        String id = this.getQueryEsId(mSearchIndexConfig, t3);
//        writeES(mSearchIndexConfig, t3, id);
//    }
//
//    private String getQueryEsId(MSearchIndexConfig mSearchIndexConfig, String t3) {
//        String id = "";
//        //当前单条数据解析，获取单条数据的唯一标识字段值。
//        Map maps = (Map) JSONObject.parse(t3);
//        for (Object key : maps.keySet()) {
//            if (key.equals(mSearchIndexConfig.getExecuteIdentMapper())) {
//                id = String.valueOf(maps.get(key));
//                break;
//            }
//        }
//        return id;
//    }
//
//    private void writeES(MSearchIndexConfig mSearchIndexConfig, String t3, String id) {
//        // 设置ES属性信息
//        Map<String, String> config = new HashMap<>();
//        config.put("cluster_name", "127.0.0.1:9200");
//        // 该配置表示批量写入ES时的记录条数
//        config.put("bulk_flush_max_action", "100");
//        // 设置ES集群节点列表
//        List<InetSocketAddress> transportAddresses = new ArrayList<>();
//        try {
//            transportAddresses.add(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 9200));
//            // 创建ElasticsearchSinkFunction，需覆写process方法
//            ElasticsearchSinkFunction<String> elasticsearchSinkFunction = new ElasticsearchSinkFunction<String>() {
//                public IndexRequest updateIndexRequest(String s) {
//                    return Requests.indexRequest()
//                            .index(mSearchIndexConfig.getIndexCode())
//                            .type(mSearchIndexConfig.getIndexType())
//                            .id(id)
//                            .routing(id)
//                            .source(XContentType.JSON, t3);
//                }
//
//                @Override
//                public void process(String s, RuntimeContext ctx, RequestIndexer indexer) {
//                    indexer.add(updateIndexRequest(s));
//                }
//            };
//            // 根据conf、addresses、sinkFunction构建ElasticsearchSink实例
//            ElasticsearchSink<String> elasticsearchSink = new ElasticsearchSink<String>(config, transportAddresses, elasticsearchSinkFunction);
//            log.info("操作ES索引信息成功", elasticsearchSink.hashCode());
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private List<MSearchIndexConfig> getQuery(String sql, ChangeDataCommonVO changeDataCommonVO) {
//        List<MSearchIndexConfig> mSearchIndexConfigList = new ArrayList<>();
//        ResultSet resultSet = null;
//        try {
//            connection = DriverManager.getConnection(dburl, username, password);
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, changeDataCommonVO.getDataBase());
//            preparedStatement.setString(2, changeDataCommonVO.getTable());
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                MSearchIndexConfig searchIndexConfig = new MSearchIndexConfig();
//                searchIndexConfig.setId(resultSet.getLong("id"));
//                searchIndexConfig.setIndexCode(resultSet.getString("index_code"));
//                searchIndexConfig.setIndexType(resultSet.getString("index_type"));
//                searchIndexConfig.setRedisKey(resultSet.getString("redis_key"));
//                searchIndexConfig.setExecuteIdent(resultSet.getString("execute_ident"));
//                searchIndexConfig.setInvolveDatabase(resultSet.getString("involve_database"));
//                searchIndexConfig.setInvolveTable(resultSet.getString("involve_table"));
//                mSearchIndexConfigList.add(searchIndexConfig);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            JdbcUtils.closeResultSet(resultSet);
//        }
//        return mSearchIndexConfigList;
//    }
//
//}
