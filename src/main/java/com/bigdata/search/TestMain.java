package com.bigdata.search;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;

/**
 * Created by licheng on 3/29/18.
 */
public class TestMain {
    public static void main(String args[]) {
        Client client = Esutil.getClient();
        IndicesExistsRequest indicesExistsRequest = new IndicesExistsRequest("yuqing1");
        AdminClient adminClient = client.admin();
        IndicesAdminClient indicesAdminClient = adminClient.indices();
        ActionFuture<IndicesExistsResponse> actionFuture = indicesAdminClient.exists(indicesExistsRequest);
        IndicesExistsResponse indicesExistsResponse = actionFuture.actionGet();

        boolean currentIndexIsExist = indicesExistsResponse.isExists();
        System.out.println(currentIndexIsExist);
        if (!currentIndexIsExist) {
            CreateIndexRequestBuilder createIndexRequestBuilder = indicesAdminClient.prepareCreate("yuqing1");
            createIndexRequestBuilder.get();
        }
        currentIndexIsExist = indicesExistsResponse.isExists();
        System.out.println(currentIndexIsExist);
    }
}
