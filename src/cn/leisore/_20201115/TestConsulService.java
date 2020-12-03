package cn.leisore._20201115;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import com.orbitz.consul.model.health.ServiceHealth;

public class TestConsulService {

	public static void main(String[] args) throws Exception {
		Consul client = Consul.builder().withUrl("http:localhost:8500").build();

		AgentClient agentClient = client.agentClient();
		
		String serviceId = UUID.randomUUID().toString();
		Registration service = ImmutableRegistration.builder()
		        .id(serviceId)
		        .name("pproxies")
		        .port(8080)
		       .check(Registration.RegCheck.ttl(3L)) // registers with a TTL of 3 seconds
		        .tags(Collections.singletonList("tag1"))
		        .meta(Collections.singletonMap("version", "1.0"))
		        .build();

		agentClient.register(service);
		try {
			agentClient.pass(serviceId, "cais");
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}
		System.out.println("ehere");
		Thread.sleep(5000);
	
		HealthClient healthClient = client.healthClient();

		// Discover only "passing" nodes
		List<ServiceHealth> nodes= healthClient.getAllServiceInstances("mqs/service/pproxies").getResponse();
		//List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances("myService").getResponse();
		
		ConsulResponse<List<ServiceHealth>> healthyServiceInstances = healthClient.getHealthyServiceInstances("pproxies");
		healthyServiceInstances.getResponse().forEach( s -> {
			s.getService().get
		});
		nodes.forEach( s -> {
			String id = s.getService().getId();
			String status = s.getChecks().get(0).getStatus();
			//s.getChecks().forEach(c -);
			System.out.println(id + ":" +s.getChecks().size());
			
		});
		
		nodes.forEach(System.out::println);
		
		System.out.println("fff");
		Thread.sleep(100000);
	}

}
