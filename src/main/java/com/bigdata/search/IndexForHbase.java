package com.bigdata.search;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by licheng on 1/17/17.
 */
public class IndexForHbase {
    private static Admin admin ;
    private static Configuration conf ;
    private static Connection conn ;

    public IndexForHbase() {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","192.168.1.110,192.168.1.111,192.168.1.112");

        try {
            conn = ConnectionFactory.createConnection(conf);
            admin = conn.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







}
