package com.uc.ucoms;

import java.util.Date;
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
 * @Description:查询已定价的新车品牌
 * 
 * @Version1.0 2016-8-22 by 杨明华
 * (mh.yang01@zuche.com)
 */

public class JmeterGetNewPriceByList extends AbstractJavaSamplerClient {

	public static final String REMOTE_SERVICE = "ucoms.standardVehiclePrice.getNewPriceByList";

	public static ApplicationContext springCtx = new ClassPathXmlApplicationContext("classpath*:frameworkContext.xml");

	// private ApplicationContext applicationContext;

	private RemoteClient client;

	public void setupTest(JavaSamplerContext context) {

		SpringApplicationContext.initApplicationContext(springCtx);

		// client =
		// RemoteClientFactory.getInstance("http://admintest.maimaiche.com/ucadmin",
		// RemoteType.HESSIAN);
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

	public SampleResult runTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
		SampleResult sampleResult = new SampleResult();

		try {
			sampleResult.sampleStart();

			// Object ob =
			// RemoteClientFactory.getInstance().executeToObject(REMOTE_SERVICE);

			Result<List<VehicleDetailInfo>> result = (Result<List<VehicleDetailInfo>>) client
					.executeToObject(REMOTE_SERVICE);

			// SaleDTO result = (SaleDTO)
			// client.executeToObject("ucoms.sale.getSaleBySaleNO","SO010220160817009");

			// System.out.println(JSON.toString(result));

			// System.out.println("Call OK");
			if (result.getCode() == 1) {
				sampleResult.setSuccessful(true);
				sampleResult.setResponseCode(String.valueOf(result.getMsg()));
				sampleResult.setResponseMessage(result.getMsg());
				// System.out.println(result.getCode());
				// System.out.println(result.getMsg());

			} else {
				sampleResult.setSuccessful(false);
				sampleResult.setResponseCode(String.valueOf(result.getMsg()));
				sampleResult.setResponseMessage(result.getMsg());
				// System.out.println(result.getCode());
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
		try {
			System.out.println("current time: " + new Date());
			Thread.sleep(30);
			System.out.println("current time: " + new Date());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
