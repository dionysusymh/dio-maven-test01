package com.uc.ucoms;

import java.util.HashMap;
import java.util.Map;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.uc.ucBase.common.dto.Result;
import com.zuche.framework.common.SpringApplicationContext;
import com.zuche.framework.remote.RemoteClient;
import com.zuche.framework.remote.RemoteClientFactory;
import com.zuche.framework.remote.RemoteType;

/**
 * @Description:设置当前价格无效
 * 
 * @Version1.0 2016-8-22 by 杨明华 (mh.yang01@zuche.com)
 */

public class JmeterSetNewPriceEffect extends AbstractJavaSamplerClient {

	public static final String REMOTE_SERVICE = "ucoms.salePriceRemoteService.setNewPriceEffect";

	public static ApplicationContext springCtx = new ClassPathXmlApplicationContext("classpath*:frameworkContext.xml");

	private RemoteClient client;

	public void setupTest(JavaSamplerContext context) {

		SpringApplicationContext.initApplicationContext(springCtx);

		client = RemoteClientFactory.getInstance("http://omstest.maimaiche.com/ucoms", RemoteType.HESSIAN);
		// System.out.println("client: " + client);
	}

	public Arguments getDefaultParameters() {

		Arguments args = new Arguments();
		args.addArgument("priceId", "${priceId}");
		args.addArgument("deptId","${deptId}");
		args.addArgument("vehicleModelId","${vehicleModelId}");
		return args;
	}

	public SampleResult runTest(JavaSamplerContext context) {

		SampleResult sampleResult = new SampleResult();

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("priceId", context.getIntParameter("priceId"));
			map.put("deptId", context.getIntParameter("deptId"));
			map.put("vehicleModelId", context.getIntParameter("vehicleModelId"));
			
			sampleResult.sampleStart();

			Result<Boolean> result = (Result<Boolean>) client.executeToObject(REMOTE_SERVICE, map);

			if (result.getCode() == 1) {
				sampleResult.setSuccessful(true);
				sampleResult.setResponseCode(String.valueOf(result.getMsg()));
				sampleResult.setResponseMessage(result.getMsg());
//				 System.out.println(result.getCode());
//				 System.out.println(result.getMsg());
			} else {
				sampleResult.setSuccessful(false);
				sampleResult.setResponseCode(String.valueOf(result.getMsg()));
				sampleResult.setResponseMessage(result.getMsg());
//				System.out.println(result.getCode());
//				System.out.println(result.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			sampleResult.setSuccessful(false);
		} finally {
			sampleResult.sampleEnd();
		}

		return sampleResult;
	}

	public void teardownTest(JavaSamplerContext context) {
		client = null;
	}

	@Test
	public void test() {

		Arguments args1 = new Arguments();
		args1.addArgument("priceId", "273");
		args1.addArgument("deptId", "121");
		args1.addArgument("vehicleModelId", "10049");

		JavaSamplerContext context = new JavaSamplerContext(args1);

		setupTest(context);
		runTest(context);
		teardownTest(context);
	}
}
