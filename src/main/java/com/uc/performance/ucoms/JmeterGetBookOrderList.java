package com.uc.performance.ucoms;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.uc.performance.utils.RandomNum;
import com.uc.ucBase.common.dto.Result;
import com.uc.ucBase.oms.dto.BookOrderForWQueryDto;
import com.uc.ucBase.oms.dto.CustomBookOrderListDTO;
import com.zuche.framework.common.SpringApplicationContext;
import com.zuche.framework.remote.RemoteClient;
import com.zuche.framework.remote.RemoteClientFactory;
import com.zuche.framework.remote.RemoteType;

/**
 * @Description: 获取预订单列表
 * 
 * @Version1.0 2016-8-22 by 杨明华 (mh.yang01@zuche.com)
 */

public class JmeterGetBookOrderList extends AbstractJavaSamplerClient {

	public static final String REMOTE_SERVICE = "ucoms.bookOrderRemoteService.getBookOrderList";

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
		args.addArgument("mobile", "${mobile}");
		return args;
	}

	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
		SampleResult sampleResult = new SampleResult();

		try {
			sampleResult.sampleStart();

			BookOrderForWQueryDto quest = new BookOrderForWQueryDto();
			quest.setMobile(context.getParameter("mobile"));
			quest.setPageNum(0);
			//quest.setPageSize(RandomNum.getIntRandomNum(1, 10));

			Result<CustomBookOrderListDTO> result = (Result<CustomBookOrderListDTO>) client
					.executeToObject(REMOTE_SERVICE, quest);

//			 System.out.println(JSON.toJSONString(result));

			if (result.getCode() == Result.SUCCESS) {
				sampleResult.setSuccessful(true);
				sampleResult.setResponseCode(String.valueOf(result.getCode()));
				sampleResult.setResponseMessage(result.getMsg());
//				System.out.println(result.getCode());
//				System.out.println(result.getMsg());

			} else {
				sampleResult.setSuccessful(false);
				sampleResult.setResponseCode(String.valueOf(result.getCode()));
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
		args1.addArgument("mobile", "13611280030");

		JavaSamplerContext context = new JavaSamplerContext(args1);

		setupTest(context);
		runTest(context);
		teardownTest(context);
	}
}
