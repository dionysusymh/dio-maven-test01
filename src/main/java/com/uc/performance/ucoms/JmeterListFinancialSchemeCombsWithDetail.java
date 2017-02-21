package com.uc.performance.ucoms;

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
import com.uc.ucBase.oms.dto.FinSchemeCombQDTO;
import com.uc.ucBase.oms.dto.VehicleLockedDTO;
import com.zuche.framework.common.SpringApplicationContext;
import com.zuche.framework.remote.RemoteClient;
import com.zuche.framework.remote.RemoteClientFactory;
import com.zuche.framework.remote.RemoteType;

/**
 * @Description:按约定层次结构返回金融方案详情
 * 
 * @Version1.0 2016-8-22 by 杨明华 (mh.yang01@zuche.com)
 */

public class JmeterListFinancialSchemeCombsWithDetail extends AbstractJavaSamplerClient {

	public static final String REMOTE_SERVICE = "ucoms.financial.listFinancialSchemeCombsWithDetail";

	public static ApplicationContext springCtx = new ClassPathXmlApplicationContext("classpath*:frameworkContext.xml");

	private RemoteClient client;

	public void setupTest(JavaSamplerContext context) {

		SpringApplicationContext.initApplicationContext(springCtx);

		client = RemoteClientFactory.getInstance("http://omstest.maimaiche.com/ucoms", RemoteType.HESSIAN);
//		client = RemoteClientFactory.getInstance("http://oms.maimaiche.com/ucoms", RemoteType.HESSIAN);
		// System.out.println("client: " + client);
	}

	public Arguments getDefaultParameters() {

		Arguments args = new Arguments();
		// args.addArgument("deptId", "27575");
		return args;
	}

	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
		SampleResult sampleResult = new SampleResult();

		try {
			FinSchemeCombQDTO q = new FinSchemeCombQDTO();
			// 产品线 1 准新车 3 新车
			q.setChannelType(1);

			// 门店id
			q.setDeptId(124L);

			// 车型 (车300车型ID）
			q.setVehicleModelId(1L);

			// 登记日期 格式： "yyyy-MM-dd"
			q.setRegisterDate("2016-05-08");

			// 购置税是否贷款 0 否 1 是
			q.setPurchaseTaxIsLoaned(1);

			// 购置税
			q.setPurchaseTax(3000D);

			// 车辆销售价
			q.setVehiclePrice(300000D);

			// 抵押方式 0 默认全选 1 抵押 2 过户
			q.setProcessType(2);

			/**
			 * 贴息金额 贴息： 用户支付的总金额 = 首付 + 月供*月供期数 + 尾款 即 月供 * 月供期数 = （成交价 + 购置税）-
			 * 首付 - 尾款 但是在有钱任性的情况下，我们在某些情况下不想赚那么多钱 所以改变了还款金额 即： 贴息后月供 * 月供期数 =
			 * （成交价 + 购置税）- 首付 - 尾款 - 贴息金额
			 */
			q.setDiscountAmt(10000D);

			sampleResult.sampleStart();
			
			ResultObject<List<VehicleLockedDTO>> result = (ResultObject<List<VehicleLockedDTO>>) client
					.executeToObject(REMOTE_SERVICE, q);

			if (result.getStatus() == ResultConstant.STATUS_SUCCESS) {
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
		// args1.addArgument("deptId", "27575");

		JavaSamplerContext context = new JavaSamplerContext(args1);

		setupTest(context);
		runTest(context);
		teardownTest(context);
	}
}
