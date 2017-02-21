package com.uc.ucoms;

import java.util.Date;
import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.uc.ucBase.common.resultUtil.ResultConstant;
import com.uc.ucBase.common.resultUtil.ResultObject;
import com.uc.ucBase.oms.dto.VehicleLockedDTO;
import com.zuche.framework.common.SpringApplicationContext;
import com.zuche.framework.remote.RemoteClient;
import com.zuche.framework.remote.RemoteClientFactory;
import com.zuche.framework.remote.RemoteType;

/**
 * @Description:获取门店对应的车辆已预订数量列表
 * 
 * @Version1.0 2016-8-22 by 杨明华
 * (mh.yang01@zuche.com)
 */

public class JmeterGetDeptVehicleLockedInfoByDeptId extends AbstractJavaSamplerClient {

	public static final String REMOTE_SERVICE = "ucoms.order.getDeptVehicleLockedInfoByDeptId";

	public static ApplicationContext springCtx = new ClassPathXmlApplicationContext("classpath*:frameworkContext.xml");

	private RemoteClient client;

	public void setupTest(JavaSamplerContext context) {

		SpringApplicationContext.initApplicationContext(springCtx);

		client = RemoteClientFactory.getInstance("http://omstest.maimaiche.com/ucoms", RemoteType.HESSIAN);
		// client = RemoteClientFactory.getInstance("http://oms.maimaiche.com/ucoms",
		// RemoteType.HESSIAN);
		// System.out.println("client: " + client);
	}

	public Arguments getDefaultParameters() {

		Arguments args = new Arguments();
		args.addArgument("deptId", "${deptId}");
		return args;
	}

	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
		SampleResult sampleResult = new SampleResult();

		try {
			sampleResult.sampleStart();

			Long deptId = context.getLongParameter("deptId");

			ResultObject<List<VehicleLockedDTO>> result = (ResultObject<List<VehicleLockedDTO>>) client
					.executeToObject(REMOTE_SERVICE, deptId);

			// System.out.println(JSON.toString(result));

			if (result.getStatus() == ResultConstant.STATUS_SUCCESS) {
				sampleResult.setSuccessful(true);
				sampleResult.setResponseCode(String.valueOf(result.getMsg()));
				sampleResult.setResponseMessage(result.getMsg());
//				System.out.println(result.getStatus());
//				System.out.println(result.getMsg());

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
		springCtx = null;
		
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
		args1.addArgument("deptId", "124");

		JavaSamplerContext context = new JavaSamplerContext(args1);

		setupTest(context);
		runTest(context);
		teardownTest(context);
	}

}
