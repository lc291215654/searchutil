package com.bigdata.search;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Esutil {

    public static Client client = null;

    /**
     * 获取客户端
     *
     * @return
     */
    public static Client getClient() {
        if (client != null) {
            return client;
        }
        Settings settings = Settings.builder().put("cluster.name", "mycluster").build();
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("huadonbd1"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("huadonbd2"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }


    public static String addIndex(String index, String type, Doc Doc) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", Doc.getId());
        hashMap.put("title", Doc.getTitle());
        hashMap.put("describe", Doc.getDescribe());
        hashMap.put("author", Doc.getAuthor());

        IndexResponse response = getClient().prepareIndex(index, type).setSource(hashMap).execute().actionGet();
//        IndexResponse response2 = getClient().prepareIndex(index, type).setSource(hashMap).get() ;
//        System.out.println(response2.getIndex());
//        System.out.println(response2.getType());
//        System.out.println(response2.getId()) ;
        return response.getId();
    }

    /**
     * https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index.html
     *
     * @param key   搜索关键字
     * @param index 索引库（数据库）
     * @param type  类型（表名）
     * @param start 从搜索结果第start开始
     * @param row   页的大小
     * @return
     */
    public static Map<String, Object> search(String key, String index, String type, int start, int row) {
        //创建查询索引,要查询的索引库为index
        SearchRequestBuilder builder = getClient().prepareSearch(index);
        builder.setTypes(type);
        builder.setFrom(start);
        builder.setSize(row);
        //设置高亮后缀

        //设置查询类型：1.SearchType.DFS_QUERY_THEN_FETCH 精确查询； 2.SearchType.SCAN 扫描查询,无序
        builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        if (StringUtils.isNotBlank(key)) {
            // 设置查询关键词
            builder.setQuery(QueryBuilders.multiMatchQuery(key, "title", "describe"));
        }

        //设置是否按查询匹配度排序
        builder.setExplain(true);
        //设置高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        builder.highlighter(highlightBuilder);

        //执行搜索,返回搜索响应信息
        SearchResponse searchResponse = builder.get();

        SearchHits searchHits = searchResponse.getHits();
        //总命中数
        long total = searchHits.getTotalHits();
        Map<String, Object> map = new HashMap<String, Object>();
        SearchHit[] hits = searchHits.getHits();
        map.put("count", total);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            //highlightFields.size=0??
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();

            //title高亮
            HighlightField titleField = highlightFields.get("title");
            Map<String, Object> source = hit.getSource();
            if (titleField != null) {
                Text[] fragments = titleField.fragments();
                String name = "";
                for (Text text : fragments) {
                    name += text;
                }
                source.put("title", name);
            }
            //describe高亮
            HighlightField describeField = highlightFields.get("describe");

            if (describeField != null) {
                Text[] fragments = describeField.fragments();
                String describe = "";
                for (Text text : fragments) {
                    describe += text;
                }
                source.put("describe", describe);
            }
            list.add(source);
        }
        map.put("dataList", list);
        return map;
    }


    static class MyTimerTask extends TimerTask {
        public void run() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            System.out.println("当前的系统时间为：" + simpleDateFormat.format(new Date()));

            AdminClient adminClient = getClient().admin();
            String indexname = "hdcom-" + (Calendar.getInstance().get(Calendar.YEAR)) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1);
            IndicesExistsRequest indicesExistsRequest = new IndicesExistsRequest(indexname);
            boolean currentIndexIsExist = adminClient.indices().exists(indicesExistsRequest).actionGet().isExists();
            if (currentIndexIsExist) {
                System.out.println("当前使用的索引库： " + indexname + " 存在");
            } else {
                System.out.println("当前使用的索引库： " + indexname + " 不存在……");
                System.out.println("正在建立索引库： " + indexname);
                adminClient.indices().prepareCreate(indexname)
                        .setSettings(Settings.builder()
                                .put("index.number_of_shards", 3)
                                .put("index.number_of_replicas", 2)
                        )
                        .get();
            }

            String indexname2 = "hdcom-" + (Calendar.getInstance().get(Calendar.YEAR)) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 2);
            IndicesExistsRequest indicesExistsRequest2 = new IndicesExistsRequest(indexname2);
            boolean currentIndexIsExist2 = adminClient.indices().exists(indicesExistsRequest2).actionGet().isExists();
            if (currentIndexIsExist2) {
                System.out.println("即将使用的索引库： " + indexname2 + " 存在");
            } else {
                System.out.println("即将使用的索引库： " + indexname2 + " 不存在……");
                System.out.println("正在建立索引库： " + indexname2);
                adminClient.indices().prepareCreate(indexname2)
                        .setSettings(Settings.builder()
                                .put("index.number_of_shards", 3)
                                .put("index.number_of_replicas", 2)
                        )
                        .get();
                GetIndexRequest getIndexRequest = new GetIndexRequest();
                adminClient.indices().prepareGetIndex().setIndices("test").addTypes("test").get();
            }

            System.out.println("-------------------------------休息10秒-----------------------------------");
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
//        Map<String, Object> search = Esutil.search("中国", "hdcom", "doc", 0, 20);
//        List<Map<String, Object>> list = (List<Map<String, Object>>) search.get("dataList");
//        System.out.println(list);
//        Doc doc1 = new Doc();
//        doc1.setAuthor("中华网");
//        doc1.setId(291215);
//        Esutil.addIndex("hdcom","doc",doc1) ;

//        GetResponse response = Esutil.getClient().prepareGet("hdcom", "doc", "AVmRKtAD61eiAPPLlvLf")
//                .get();
//        Map<String,Object> re = response.getSource() ;
//        System.out.println(re);

//        AdminClient adminClient = getClient().admin();
//        String indexname = "hdcom-" + (Calendar.getInstance().get(Calendar.YEAR)) + "-" +(Calendar.getInstance().get(Calendar.MONTH)+1) ;
//        System.out.println(indexname) ;
//        IndicesExistsRequest indicesExistsRequest = new IndicesExistsRequest(indexname) ;
//        System.out.println(adminClient.indices().exists(indicesExistsRequest).actionGet().isExists() );
//
//        ClusterAdminClient clusterAdminClient = adminClient.cluster() ;
//        IndicesAdminClient indicesAdminClient = adminClient.indices() ;


        //定时检查
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(), 1000, 10000);
        AdminClient adminClient = getClient().admin();

//        adminClient.indices().prepareCreate("test")
//                .setSettings(Settings.builder()
//                        .put("index.number_of_shards", 3)
//                        .put("index.number_of_replicas", 2)
//                )
//                .get();
        // GetIndexRequest getIndexRequest = new GetIndexRequest() ;
        //adminClient.indices().prepareGetIndex().setIndices("test").addTypes("test").get() ;
        // adminClient.indices().prepareGetIndex().setIndices("test").addTypes("test1").get() ;
//        XContentBuilder builder = jsonBuilder()
//                .startObject()
//                .field("user", "kimchy")
//                .field("postDate", new Date())
//                .field("message", "trying out Elasticsearch")
//                .endObject() ;
//        String json = builder.string();
//        //GetIndexResponse response = adminClient.indices().prepareGetIndex().setIndices("test").setTypes("test1").get() ;
//        IndexResponse response = client.prepareIndex("test", "test1", "2").setSource(json).get();
//        System.out.println(response.toString());


        System.out.println("aaa");
    }
}
