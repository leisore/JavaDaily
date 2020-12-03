package cn.leisore._20171109;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class WriteHBase {

	public static void main(String[] args) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		Configuration configuration = HBaseConfiguration.create();
		
		HBaseAdmin admin = new HBaseAdmin(configuration);
		HTableDescriptor desc = new HTableDescriptor("test");
		desc.addFamily(new HColumnDescriptor("data"));
		admin.createTable(desc);
		System.out.println("OK");
	}

}
