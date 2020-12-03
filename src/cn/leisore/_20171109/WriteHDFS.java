
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class WriteHDFS {

	public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        
        conf.set("dfs.nameservices","webgateCluster");
        conf.set("dfs.ha.namenodes.webgateCluster","bes51,bes52");
        conf.set("dfs.namenode.rpc-address.webgateCluster.bes51","192.168.1.51:9000");
        conf.set("dfs.namenode.rpc-address.webgateCluster.bes52","192.168.1.52:9000");
        conf.set("fs.defaultFS","hdfs://192.168.1.51:9000");
        
        FileSystem fileSystem = FileSystem.get( conf);
        System.out.println(fileSystem.getName());
        
        Path srcPath = new Path("log4j2.xml");  
        Path dstPath = new Path("/user/tests/test2/" + srcPath.getName());  
        fileSystem.copyFromLocalFile(false, srcPath, dstPath);
        fileSystem.close(); 
        System.out.println("OK");
	}

}
