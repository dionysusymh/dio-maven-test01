package com.uc.performance.ucoms;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.uc.performance.utils.DateUtil;
import com.uc.ucBase.common.dto.Result;
import com.uc.ucBase.oms.dto.BookOrderForWDTO;
import com.uc.ucBase.oms.dto.FinSchemeDetailDTO;
import com.zuche.framework.common.SpringApplicationContext;
import com.zuche.framework.remote.RemoteClient;
import com.zuche.framework.remote.RemoteClientFactory;
import com.zuche.framework.remote.RemoteType;


/**
 * @Description: 获取预订单列表
 * 
 * @Version1.0 2016-8-22 by 杨明华 (mh.yang01@zuche.com)
 */

public class JmeterSaveBookOrder extends AbstractJavaSamplerClient {

	public static final String REMOTE_SERVICE = "ucoms.bookOrderRemoteService.saveBookOrder";

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

			BookOrderForWDTO quest = new BookOrderForWDTO();
			FinSchemeDetailDTO finquest = new FinSchemeDetailDTO();
//			quest.setMemberId(2224089L);
//			quest.setMemberMainId(165714L);
//			quest.setMobile("13122822035");
//			quest.setRegionId(233L);
//			quest.setProductLine(3);
//			quest.setDeptId(124L);
//			quest.setModelId(10231L);
//			quest.setBuyCarDeptId(124L);
//			quest.setSubmitOrigin(501);
//			quest.setDeptPublishId(3671L);
//			quest.setSubscribeDate(DateUtil.getCurrentDateAndTime());
//			quest.setBookBuyCarDate(DateUtil.getCurrentDateAndTime());
//			quest.setSellPrice("362000");
//			quest.setFirstRate(100);
//			quest.setStrage(0);
//			quest.setReceiveDeposit(0.01);
//			quest.setPurchaseTaxIsLoaned(0);
//			quest.setPayStatus(0);
			
			quest.setBookBuyCarDate(DateUtil.getCurrentDateAndTime());
			quest.setBrandName("大众");
			quest.setBuyCarDeptId(119L);
			quest.setDeptId(134L);
			quest.setDeptPublishId(3766L);
			quest.setFirstRate(20);
			quest.setHalfYear("2");
			quest.setMemberId(2313613L);
			quest.setMemberMainId(165714L);
			quest.setMemberName("test");
			quest.setMobile("13122822035");
			quest.setModelId(2196L);
			quest.setPayStatus(0);			
			quest.setProductLine(1);
			quest.setPromotionId(114L);
			quest.setPromotionMoney("1000.00");
			quest.setPurchaseTax(0.00);
			quest.setPurchaseTaxIsLoaned(0);
			quest.setReceiveDeposit(0.01);
			quest.setRegionId(116L);
			quest.setSellPrice("150000");
			quest.setServiceNature("1");
			quest.setStrage(36);
			quest.setSubmitOrigin(504);			
			quest.setSubscribeDate(DateUtil.getCurrentDateAndTime());
						
			finquest.setAnnualRate(1.00);
			finquest.setBrokerageAmt(1000.00);
			finquest.setDepositAmt(3576.00);
			finquest.setDiscountAmt(0.00);
			finquest.setDownPayment(29800.00);
			finquest.setDownPaymentRatio(20);
			finquest.setFinalPayments(0.00);
			finquest.setGpsAmt(100.00);
			finquest.setGpsInstallAmt(230.00);
			finquest.setLoanAmt(119200.00);
			finquest.setManagementAmt(1000.00);
			finquest.setMonthRate(8.33E-4);
			finquest.setMonthRepayAmt(3410.44);
			finquest.setProcessType(2);
			finquest.setProductId(111L);
			finquest.setProductName("神州买卖车网站准新车0905无尾款");
			finquest.setPurchaseTaxIsLoaned(0);
			finquest.setRepayTotalAmount(122775.84);
			finquest.setSchemeId(148L);
			finquest.setSchemeName("方案一--复制");
			finquest.setTerm(36);
			finquest.setTermId(36L);
			
			quest.setFinSchemeDetailDTO(finquest);

//			System.out.println(JSON.toJSONString(quest));
			
			Result<String> result = (Result<String>) client
					.executeToObject(REMOTE_SERVICE, quest);

//			System.out.println(JSON.toJSONString(result));

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
		args1.addArgument("mobile", "15910445715");

		JavaSamplerContext context = new JavaSamplerContext(args1);

		setupTest(context);
		runTest(context);
		teardownTest(context);
	}
}
