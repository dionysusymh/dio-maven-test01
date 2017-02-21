package com.uc.performance.ucoms;

import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.junit.Test;
import org.mortbay.util.ajax.JSON;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.uc.ucBase.common.dto.Result;
import com.uc.ucBase.po.VehicleDetailInfo;
import com.zuche.framework.common.SpringApplicationContext;
import com.zuche.framework.remote.RemoteClient;
import com.zuche.framework.remote.RemoteClientFactory;
import com.zuche.framework.remote.RemoteType;

/**
 * @Description:准新车当前价格返回所有品牌名字 英文名
 * 
 * @Version1.0 2016-8-22 by 杨明华
 * (mh.yang01@zuche.com)
 */

public class JmeterGetPriceByList extends AbstractJavaSamplerClient {

	public static final String REMOTE_SERVICE = "ucoms.standardVehiclePrice.getPriceByList";

	public static ApplicationContext springCtx = new ClassPathXmlApplicationContext("classpath*:frameworkContext.xml");

	private RemoteClient client;

	public void setupTest(JavaSamplerContext context) {

		SpringApplicationContext.initApplicationContext(springCtx);

		client = RemoteClientFactory.getInstance("http://omstest.maimaiche.com/ucoms", RemoteType.HESSIAN);
		// client =
		// RemoteClientFactory.getInstance("http://oms.maimaiche.com/ucoms",
		// RemoteType.HESSIAN);
		// System.out.println("client: " + client);
	}

	public Arguments getDefaultParameters() {

		Arguments args = new Arguments();
		// args.addArgument("modelID", "27575");
		return args;
	}

	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
		SampleResult sampleResult = new SampleResult();

		try {
			sampleResult.sampleStart();

			Result<List<VehicleDetailInfo>> result = (Result<List<VehicleDetailInfo>>) client
					.executeToObject(REMOTE_SERVICE);

			// System.out.println(JSON.toString(result));

			if (result.getCode() == 1) {
				sampleResult.setSuccessful(true);
				sampleResult.setResponseCode(String.valueOf(result.getMsg()));
				sampleResult.setResponseMessage(result.getMsg());
				// System.out.println(result.getStatus());
				// System.out.println(result.getMsg());

			} else {
				sampleResult.setSuccessful(false);
				sampleResult.setResponseCode(String.valueOf(result.getMsg()));
				sampleResult.setResponseMessage(result.getMsg());
				// System.out.println(result.getStatus());
				// System.out.println(result.getMsg());
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
		// args1.addArgument("modelID", "27575");

		JavaSamplerContext context = new JavaSamplerContext(args1);

		setupTest(context);
		runTest(context);
		teardownTest(context);
	}
}
