package cn.leisore._20201115;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;

public class TestConconsulKV {

	public static void main(String[] args) {

		Consul client = Consul.builder().withUrl("http://localhost:8500").build();

		KeyValueClient kvClient = client.keyValueClient();

		Map<String, Object> pp = Maps.newHashMap();
		pp.put("topics", Lists.newArrayList("t1","t2"));
		
		kvClient.putValue("pp1", JSON.toJSONString(pp));
	}

}
