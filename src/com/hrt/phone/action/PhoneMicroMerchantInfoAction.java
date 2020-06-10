package com.hrt.phone.action;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.entity.model.MerchantAuthenticityModel;
import com.hrt.biz.bill.entity.pagebean.MerchantAuthenticityBean;
import com.hrt.biz.bill.service.IMerchantAuthenticityService;
import com.hrt.biz.util.CommonTools;
import com.hrt.biz.util.unionpay.AESUtil;
import com.hrt.frame.constant.Constant;
import com.hrt.frame.constant.PhoneProdConstant;
import com.hrt.util.ParamPropUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.ShardedJedis;

import com.alibaba.fastjson.JSON;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.pagebean.AgentSalesBean;
import com.hrt.biz.bill.entity.pagebean.AggPayTerminfoBean;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.biz.bill.entity.pagebean.MerchantTerminalInfoBean;
import com.hrt.biz.bill.service.IAgentSalesService;
import com.hrt.biz.bill.service.IAggPayTerminfoService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.bill.service.IMerchantTerminalInfoService;
import com.hrt.biz.util.gateway.MD5Util;
import com.hrt.biz.util.unionpay.HrtRSAUtil;
import com.hrt.biz.util.unionpay.SHAEncUtil;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.JsonBeanForSign;
import com.hrt.phone.service.IPhoneMicroMerchantInfoService;
import com.hrt.phone.service.IPhoneWechatPublicAccService;
import com.hrt.redis.RedisUtil;
import com.hrt.util.SimpleXmlUtil;
import com.opensymphony.xwork2.ModelDriven;

public class PhoneMicroMerchantInfoAction extends BaseAction implements ModelDriven<MerchantInfoBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log =LogFactory.getLog(PhoneMicroMerchantInfoAction.class);
//	private static String signEnd = "&key=dseesa325errtcyraswert749errtdyt";
//	private static String signEnd = "&key=12345678";
	private MerchantInfoBean merchantInfoBean =new MerchantInfoBean();
	private IPhoneMicroMerchantInfoService phoneMicroMerchantInfoService;
	private IMerchantInfoService merchantInfoService;
	private IAgentSalesService agentSalesService;
	private IMerchantTerminalInfoService merchantTerminalInfoService;
	private IAggPayTerminfoService aggPayTerminfoService;
	private IPhoneWechatPublicAccService phoneWechatPublicAccService;
	private IMerchantAuthenticityService merchantAuthenticityService;

	public IMerchantAuthenticityService getMerchantAuthenticityService() {
		return merchantAuthenticityService;
	}

	public void setMerchantAuthenticityService(IMerchantAuthenticityService merchantAuthenticityService) {
		this.merchantAuthenticityService = merchantAuthenticityService;
	}

	public IPhoneWechatPublicAccService getPhoneWechatPublicAccService() {
		return phoneWechatPublicAccService;
	}

	public void setPhoneWechatPublicAccService(IPhoneWechatPublicAccService phoneWechatPublicAccService) {
		this.phoneWechatPublicAccService = phoneWechatPublicAccService;
	}

	@Override
	public MerchantInfoBean getModel() {
		return merchantInfoBean;
	}
	
	public MerchantInfoBean getMerchantInfoBean() {
		return merchantInfoBean;
	}

	public void setMerchantInfoBean(MerchantInfoBean merchantInfoBean) {
		this.merchantInfoBean = merchantInfoBean;
	}

	public IAggPayTerminfoService getAggPayTerminfoService() {
		return aggPayTerminfoService;
	}

	public void setAggPayTerminfoService(
			IAggPayTerminfoService aggPayTerminfoService) {
		this.aggPayTerminfoService = aggPayTerminfoService;
	}

	public IMerchantTerminalInfoService getMerchantTerminalInfoService() {
		return merchantTerminalInfoService;
	}

	public void setMerchantTerminalInfoService(
			IMerchantTerminalInfoService merchantTerminalInfoService) {
		this.merchantTerminalInfoService = merchantTerminalInfoService;
	}

	public IAgentSalesService getAgentSalesService() {
		return agentSalesService;
	}

	public void setAgentSalesService(IAgentSalesService agentSalesService) {
		this.agentSalesService = agentSalesService;
	}

	public IPhoneMicroMerchantInfoService getPhoneMicroMerchantInfoService() {
		return phoneMicroMerchantInfoService;
	}
	
	public void setPhoneMicroMerchantInfoService(
			IPhoneMicroMerchantInfoService phoneMicroMerchantInfoService) {
		this.phoneMicroMerchantInfoService = phoneMicroMerchantInfoService;
	}
	
	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}
	
	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	/**
	 * 解密和融通手机端自助报单信息
	 */
	public void decMicroMerchantInfo(){
		JsonBean json = new JsonBean();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"), super.getRequest().getParameter("timeStamp"));
		if(!b) {
			json.setSuccess(false);
			json.setMsg("查询失败!");
		}else {
			String legalNum = merchantInfoBean.getLegalNum();
			String bankAccNo = merchantInfoBean.getBankAccNo();
//			String contactPhone = merchantInfoBean.getContactPhone();
//			String hybPhone = merchantInfoBean.getHybPhone();
			String bankBranch = merchantInfoBean.getBankBranch();
			String payBankId = merchantInfoBean.getPayBankId();
			String raddr = merchantInfoBean.getRaddr();
			String agentId = merchantInfoBean.getAgentId();
			String bno = merchantInfoBean.getBno();
			String bankAccName = merchantInfoBean.getBankAccName();
			String settMethod = merchantInfoBean.getSettMethod();
	
			String sign = super.getRequest().getParameter("sign");
			Map<String,String> map1 = new HashMap<String, String>();
			map1.put("legalNum", legalNum);
			map1.put("bankAccNo", bankAccNo);
			map1.put("contactPhone", "");
			map1.put("hybPhone", "");
			map1.put("bankBranch", bankBranch);
			map1.put("payBankId", payBankId);
			map1.put("raddr", raddr);
			map1.put("agentId", agentId);
			map1.put("bno", bno);
			map1.put("bankAccName", bankAccName);
			map1.put("settMethod", settMethod);
			String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1)+ParamPropUtils.props.getProperty("signEnd")));
			if(md5.equals(sign)){
				String decrypt= HrtRSAUtil.decryptWithBase64(merchantInfoBean.getLegalNum());
				String decrypt2= HrtRSAUtil.decryptWithBase64(merchantInfoBean.getBankAccNo());
				//图片解密
				merchantInfoBean.setMaterialUpLoadFile(HrtRSAUtil.decryptStringToFile(merchantInfoBean.getMaterialUpLoad().substring(10)));
				merchantInfoBean.setMaterialUpLoad4File(HrtRSAUtil.decryptStringToFile(merchantInfoBean.getMaterialUpLoad4().substring(10)));
				merchantInfoBean.setMaterialUpLoad2File(HrtRSAUtil.decryptStringToFile(merchantInfoBean.getMaterialUpLoad2().substring(10)));
				merchantInfoBean.setBupLoadFile(HrtRSAUtil.decryptStringToFile(merchantInfoBean.getBupLoad().substring(10)));
				merchantInfoBean.setRegistryUpLoadFile(HrtRSAUtil.decryptStringToFile(merchantInfoBean.getRegistryUpLoad().substring(10)));
				merchantInfoBean.setLegalUploadFile(HrtRSAUtil.decryptStringToFile(merchantInfoBean.getLegalUploadFileName().substring(10)));
//				boolean f1 = HrtRSAUtil.decryptStringToFile(HrtRSAUtil.decryptWithBase64(merchantInfoBean.getMaterialUpLoad()), merchantInfoBean.getMaterialUpLoadFile());
//				boolean f2 = HrtRSAUtil.decryptStringToFile(HrtRSAUtil.decryptWithBase64(merchantInfoBean.getMaterialUpLoad4()), merchantInfoBean.getMaterialUpLoad4File());
//				boolean f3 = HrtRSAUtil.decryptStringToFile(HrtRSAUtil.decryptWithBase64(merchantInfoBean.getMaterialUpLoad2()), merchantInfoBean.getMaterialUpLoad2File());
//				boolean f4 = HrtRSAUtil.decryptStringToFile(HrtRSAUtil.decryptWithBase64(merchantInfoBean.getBupLoad()), merchantInfoBean.getBupLoadFile());
//				boolean f5 = HrtRSAUtil.decryptStringToFile(HrtRSAUtil.decryptWithBase64(merchantInfoBean.getRegistryUpLoad()), merchantInfoBean.getRegistryUpLoadFile());
//				boolean f6 = HrtRSAUtil.decryptStringToFile(HrtRSAUtil.decryptWithBase64(merchantInfoBean.getLegalUploadFileName()), merchantInfoBean.getLegalUploadFile());
				if(merchantInfoBean.getMaterialUpLoadFile()!=null&&merchantInfoBean.getMaterialUpLoad4File()!=null
						&&merchantInfoBean.getMaterialUpLoad2File()!=null&&merchantInfoBean.getBupLoadFile()!=null
						&&merchantInfoBean.getRegistryUpLoadFile()!=null&&merchantInfoBean.getLegalUploadFile()!=null) {
					//解密后是否为空
					if(decrypt!=null&&!"".equals(decrypt)&&decrypt2!=null&&!"".equals(decrypt2)){
						merchantInfoBean.setLegalNum(decrypt);
						merchantInfoBean.setBankAccNo(decrypt2);
						Integer flag = 1;
						boolean flag2=true;
						Integer tidCount=merchantInfoService.queryTidCountByLegalNum(merchantInfoBean.getLegalNum(), 0, "");
						if(tidCount<100){
							++tidCount;
							merchantInfoBean.setBankArea("同一个身份证号下共申请Mpos("+tidCount+")台！");
							//判断手刷商户编号总数量是否已满！
							boolean flag1=merchantInfoService.queryMicroMerchantCount();
							if(flag1){
								boolean flag3=phoneMicroMerchantInfoService.queryIsHotMerch(merchantInfoBean);
								if(flag3){
									if(merchantInfoBean.getSn()!=null&&!"".equals(merchantInfoBean.getSn())){
										flag=phoneMicroMerchantInfoService.findHrtSnIsUsing(merchantInfoBean.getSn(),null);
									}
									if(flag==1){
										if(merchantInfoBean.getSn()!=null&&!"".equals(merchantInfoBean.getSn())&&!"null".equals(merchantInfoBean.getSn())){
											flag2=phoneMicroMerchantInfoService.findHrtSnInfo(merchantInfoBean.getSn());
										}
										if(flag2){
												List<Map<String, Object>> list;
												try {
													list = phoneMicroMerchantInfoService.saveHrtMerchantInfo(merchantInfoBean);
													if(list!=null){
														phoneMicroMerchantInfoService.hrtMerchantToHYB((MerchantInfoModel)list.get(0).get("merchantInfoModel"),merchantInfoBean);
													}
													if(list!=null&&merchantInfoBean.getSn()!=null&&!"".equals(merchantInfoBean.getSn())){
														phoneMicroMerchantInfoService.saveHrtMerToADMDB(merchantInfoBean,list);
													}
													json.setObj(list);
													json.setSuccess(true);
													json.setMsg("注册成功"); 
												} catch (Exception e) {
													json.setSuccess(false);
													json.setMsg("注册失败");
													e.printStackTrace();
												}
										}else{
											json.setMsg("该SN号不存在(此版本禁止使用押金设备)！");
											json.setSuccess(false);
										}
									}else if(flag==2){
										json.setMsg("该Pos:SN号已绑定成功,请去刷卡收款！");
										json.setSuccess(false);
									}else{
										json.setMsg("该Pos:SN号已经被占用,请联系销售！");
										json.setSuccess(false);
									}
								}else{
									json.setMsg("该户在黑名单商户中，请核查！");
									json.setSuccess(false);
								}
							}else{
								json.setSuccess(false);
								json.setMsg("商户编号生成错误，请稍后重试！");
							}
						}else{
							json.setSuccess(false);
							json.setMsg("同一个身份证号申请Mpos超限！");
						}
					}else{
						json.setSuccess(false);
						json.setMsg("参数有空值，请核对参数！");
					}
				}else{
					json.setSuccess(false);
					json.setMsg("数据有误！");
				}
			}else {
				json.setSuccess(false);
				json.setMsg("数据有误！");
			}
		}
		JsonBeanForSign json1 = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json1.setSign(signs1);
		json1.setData(data);
		super.writeJson(json1);
	}

	public void addMicroMerchantInfoDataV2(){
		try {
			Map<String,File> fileMap=new HashMap<String,File>();
			fileMap.put("legalUploadFile",merchantInfoBean.getLegalUploadFile());
			fileMap.put("materialUpLoadFile",merchantInfoBean.getMaterialUpLoadFile());
			fileMap.put("materialUpLoad2File",merchantInfoBean.getMaterialUpLoad2File());
			fileMap.put("materialUpLoad1File",merchantInfoBean.getMaterialUpLoad1File());
			String data=getRequest().getParameter("data");
			String aesEn=getRequest().getParameter("aesEn");
			String sck=CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck,data);
			merchantInfoBean=JSONObject.parseObject(data0,MerchantInfoBean.class);
			merchantInfoBean.setSck(sck);
			merchantInfoBean.setEnc(true);
			merchantInfoBean.setLegalUploadFile(fileMap.get("legalUploadFile"));
			merchantInfoBean.setMaterialUpLoadFile(fileMap.get("materialUpLoadFile"));
			merchantInfoBean.setMaterialUpLoad1File(fileMap.get("materialUpLoad1File"));
			merchantInfoBean.setMaterialUpLoad2File(fileMap.get("materialUpLoad2File"));
			log.error("addMicroMerchantInfoDataV2解密后的请求参数:"+JSON.toJSONString(merchantInfoBean));
            addMicroMerchantInfo();
		} catch (Exception e) {
			log.error("addMicroMerchantInfoDataV2解密请求出错:"+e.getMessage());
		}
	}

	/**
	 * 和融通手机端自助报单
	 */
	public void addMicroMerchantInfo(){
		log.error("addMicroMerchantInfo报单请求参数:"+JSONObject.toJSONString(merchantInfoBean));
		JsonBean json = new JsonBean();
		if(StringUtils.isNotEmpty(merchantInfoBean.getAccExpdate())
				&& StringUtils.isEmpty(merchantInfoBean.getLegalExpdate())){
			merchantInfoBean.setLegalExpdate(merchantInfoBean.getAccExpdate());
		}

		Integer flag = 1;
		boolean flag2=true;
		//  @author:xuegangliu:20190326 身份证号下存在三个机构号的数据返回信息:您已使用我司其他产品，请勿重复注册。)
		//  	992107  易宝付
		//		982125  银收宝
		//		982058  亿米付
		boolean isExist = merchantInfoService.queryIsExistInHrtUnno(merchantInfoBean);
		if(isExist){
			json.setSuccess(false);
			json.setMsg("您已使用我司其他产品，请勿重复注册。");
		}else{

			boolean c = merchantInfoService.queryIsRegist(merchantInfoBean);//同一身份证注册身份证不能超过5次
			if (c) {
				boolean b = merchantInfoService.queryAge(merchantInfoBean);//年龄限制
				if (b) {
					Integer tidCount = null;
					if (merchantInfoBean.getAgentId() != null && PhoneProdConstant.PHONE_SYT.equals(merchantInfoBean.getAgentId())) {
						tidCount = merchantInfoService.queryTidCountByLegalNum2(merchantInfoBean, 0);
					} else {
						tidCount = merchantInfoService.queryTidCountByLegalNum(merchantInfoBean.getLegalNum(), 0, "");
					}
					if (tidCount < 10) {
						++tidCount;
						merchantInfoBean.setBankArea("同一个身份证号下共申请Mpos(" + tidCount + ")台！");
						//判断手刷商户编号总数量是否已满！
						boolean flag1 = merchantInfoService.queryMicroMerchantCount();
						if (flag1) {
							boolean flag3 = phoneMicroMerchantInfoService.queryIsHotMerch(merchantInfoBean);
							if (flag3) {
								if (merchantInfoBean.getLegalUploadFile() != null && merchantInfoBean.getMaterialUpLoadFile() != null && merchantInfoBean.getMaterialUpLoad1File() != null && merchantInfoBean.getMaterialUpLoad2File() != null) {
									if (merchantInfoBean.getSn() != null && !"".equals(merchantInfoBean.getSn())) {
										flag = phoneMicroMerchantInfoService.findHrtSnIsUsing(merchantInfoBean.getSn(), null);
									}
									if (flag == 1) {
										if (merchantInfoBean.getSn() != null && !"".equals(merchantInfoBean.getSn()) && !"null".equals(merchantInfoBean.getSn())) {
											flag2 = phoneMicroMerchantInfoService.findHrtSnInfo(merchantInfoBean.getSn());
										}
										if (flag2) {
											List<Map<String, Object>> list;
											try {
												list = phoneMicroMerchantInfoService.saveHrtMerchantInfo(merchantInfoBean);
												if (list != null) {
													phoneMicroMerchantInfoService.hrtMerchantToHYB((MerchantInfoModel) list.get(0).get("merchantInfoModel"), merchantInfoBean);
												}
												if (list != null && merchantInfoBean.getSn() != null && !"".equals(merchantInfoBean.getSn())) {
													phoneMicroMerchantInfoService.saveHrtMerToADMDB(merchantInfoBean, list);
												}
												json.setObj(list);
												json.setSuccess(true);
												json.setMsg("注册成功");
											} catch (Exception e) {
												json.setSuccess(false);
												json.setMsg("注册失败");
												e.printStackTrace();
											}
										} else {
											json.setMsg("该SN号不存在(此版本禁止使用押金设备)！");
											json.setSuccess(false);
										}
									} else if (flag == 2) {
										json.setMsg("该Pos:SN号已绑定成功,请去刷卡收款！");
										json.setSuccess(false);
									} else {
										json.setMsg("该Pos:SN号已经被占用,请联系销售！");
										json.setSuccess(false);
									}
								} else {
									json.setMsg("上传图片数量不完整！");
									json.setSuccess(false);
								}
							} else {
								json.setMsg("该户在黑名单商户中，请核查！");
								json.setSuccess(false);
							}
						} else {
							json.setSuccess(false);
							json.setMsg("商户编号生成错误，请稍后重试！");
						}
					} else {
						json.setSuccess(false);
						json.setMsg("同一个身份证号申请Mpos超限！");
					}
				} else {
					json.setSuccess(false);
					json.setMsg("商户入网年龄要求18-60岁！");
				}
			} else {
				json.setSuccess(false);
				json.setMsg("您已注册过会员宝，不允许重复注册！");
			}
		}
		super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
	}

    public void getSyncMerchantInfoByPhoneDataV2(){
        try {
            String data=getRequest().getParameter("data");
            String aesEn=getRequest().getParameter("aesEn");
            String sck=CommonTools.getSck(aesEn);
            String data0 = CommonTools.parseAesEnAndData(sck,data);
            merchantInfoBean=JSONObject.parseObject(data0,MerchantInfoBean.class);
            merchantInfoBean.setSck(sck);
            merchantInfoBean.setEnc(true);
            log.error("getSyncMerchantInfoByPhoneDataV2解密后的请求参数:"+JSON.toJSONString(merchantInfoBean));
            getSyncMerchantInfoByPhone();
        } catch (Exception e) {
            log.error("getSyncMerchantInfoByPhoneDataV2解密请求出错:"+e.getMessage());
        }
    }

	/**
	 * 获取同步的商户信息
	 * @param isOpen 是否获取
	 * @param type 1:所用产品用户信息都可获取 2:只限制秒到商户信息
	 * @return
	 */
    private Map getMerInfoAndAuthInfo(boolean isOpen,int type){
		Map result=null;
		if(isOpen) {
			String remarks = merchantInfoBean.getRemarks();
			log.error("商户同步信息获取,请求参数:"+remarks);
			List<MerchantInfoModel> merList = merchantInfoService.getMerchantInfoByRemarks(remarks,type);
			if(merList.size()>0) {
				for (MerchantInfoModel info : merList) {
					if (info != null && info.getMid() != null) {
						MerchantAuthenticityModel merchantAuthenticityModel=null;
						boolean isHrt=StringUtils.isNotEmpty(info.getBno()) && Constant.HRT_PORD_AGENTID.contains("."+info.getBno()+".");
						if(isHrt) {
							merchantAuthenticityModel = merchantAuthenticityService.getSuccessInfoMerTypeByMid(info.getMid(),info.getLegalPerson(), info.getLegalNum());
						}else{
						}
						if ((isHrt && merchantAuthenticityModel != null && merchantAuthenticityModel.getBankAccNo() != null)
								|| (!isHrt && merchantAuthenticityModel==null)) {
							result = new HashMap();
							result.put("bmid", info.getBmid());
							result.put("bankAccName", info.getBankAccName());
							result.put("bankAccNo", info.getBankAccNo());
							result.put("legalNum", info.getLegalNum());
							result.put("legalPerson", info.getLegalPerson());
							result.put("agentId", info.getBno());
							result.put("productName", info.getRemarks());
							result.put("bmatid", merchantAuthenticityModel==null?0:merchantAuthenticityModel.getBmatid());
							result.put("bankAccNo1", merchantAuthenticityModel==null?"":merchantAuthenticityModel.getBankAccNo());
							break;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 获取秒到同步信息
	 */
	public void getMdSyncMerchantInfoByPhone(){
		JsonBean json = new JsonBean();
		boolean isOpen=false;
		boolean broken = false;
		ShardedJedis shardedJedis = null;
		//JedisSource rource = new JedisSource();
		//Jedis jedis = rource.getJedis();
		try {
			shardedJedis = RedisUtil.getShardedJedis();
			log.info("是否开启秒到手机号同步商户信息,读取[isSyncMd]结果为" + shardedJedis.hget("HrtAppConfig", "isSyncMd"));
			String isSyncMd = shardedJedis.hget("HrtAppConfig", "isSyncMd");
			if("1".equals(isSyncMd)){
				isOpen=true;
			}
		} catch (Exception e) {
			log.info("是否开启秒到手机号同步商户信息,读取[isSyncMd]异常:"+e.getMessage());
			broken = true;
		} finally {
			//JedisSource.returnResource(jedis);
			if(shardedJedis != null) {
				RedisUtil.delShardedJedis(broken, shardedJedis);
			}
		}
		Map result =getMerInfoAndAuthInfo(isOpen,1);
		if(result!=null){
			json.setSuccess(true);
			json.setObj(result);
			json.setMsg("查询同步信息成功");
		}else{
			json.setSuccess(false);
			json.setMsg("未查询到同步信息");
		}
		super.writeJson(json);
    }

	/**
	 * 获取plus同步信息
	 */
	public void getSyncMerchantInfoByPhone(){
		JsonBean json = new JsonBean();
		boolean isOpen=false;
		boolean broken = false;
		ShardedJedis shardedJedis = null;
		//JedisSource rource = new JedisSource();
		//Jedis jedis = rource.getJedis();
		try {
			shardedJedis = RedisUtil.getShardedJedis();
			log.info("是否开启plus手机号同步商户信息,读取[isSyncPlus]结果为" + shardedJedis.hget("HrtAppConfig", "isSyncPlus"));
			String isSyncPlus = shardedJedis.hget("HrtAppConfig", "isSyncPlus");
			if("1".equals(isSyncPlus)){
				isOpen=true;
			}
		} catch (Exception e) {
			log.info("是否开启plus手机号同步商户信息,读取[isSyncPlus]异常:"+e.getMessage());
			broken = true;
		} finally {
			//JedisSource.returnResource(jedis);
			if(shardedJedis != null) {
				RedisUtil.delShardedJedis(broken, shardedJedis);
			}
		}
		Map result =getMerInfoAndAuthInfo(isOpen,1);
		if(result!=null){
			json.setSuccess(true);
			json.setObj(result);
			json.setMsg("查询同步信息成功");
		}else{
			json.setSuccess(false);
			json.setMsg("未查询到同步信息");
		}
        super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
	}

	public void syncMerchantInfoByPhoneToNewPlusDataV2(){
		try {
			String data=getRequest().getParameter("data");
			String aesEn=getRequest().getParameter("aesEn");
			String sck=CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck,data);
			merchantInfoBean=JSONObject.parseObject(data0,MerchantInfoBean.class);
			merchantInfoBean.setSck(sck);
			merchantInfoBean.setEnc(true);
			log.error("syncMerchantInfoByPhoneToNewPlusDataV2解密后的请求参数:"+JSON.toJSONString(merchantInfoBean));
			syncMerchantInfoByPhoneToNewPlus();
		} catch (Exception e) {
			log.error("syncMerchantInfoByPhoneToNewPlusDataV2解密请求出错:"+e.getMessage());
		}
	}

	/**
	 * 同步已有商户信息到新plus商户
	 */
	public void syncMerchantInfoByPhoneToNewPlus(){
		JsonBean json = new JsonBean();
		log.error("同步商户信息传入参数merchantInfoBean:"+JSONObject.toJSONString(merchantInfoBean));
		try {
			MerchantInfoModel t = merchantInfoService.saveSyncMerchantInfoByPhoneToNewPlus(merchantInfoBean,merchantInfoBean.getAgentId());
			if(t!=null && !"ERROR".equals(t.getRemarks())) {
				phoneMicroMerchantInfoService.hrtMerchantToHYB(t, merchantInfoBean);
			}else if(t!=null && "ERROR".equals(t.getRemarks())){
                json.setSuccess(false);
                json.setMsg("该手机号已经存在该产品商户");
				super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
                return;
            }else{
				json.setSuccess(false);
				json.setMsg("不存在同步信息");
				super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
				return;
			}
			Map<String, String> result= new HashMap<String, String>();
			if(t!=null && (merchantInfoBean.getBmaid()==0)){
				// 认证信息为0的为银收宝名称
				MerchantAuthenticityBean mb = new MerchantAuthenticityBean();
				mb.setUsername(merchantInfoBean.getHybPhone());
				mb.setMid(t.getMid());
				result.put("userName",merchantInfoBean.getHybPhone());
				result.put("mid",t.getMid());
				result.put("msg","认证成功");
				result.put("status","2");
				merchantAuthenticityService.sendResultToHyb(result,mb);
				json.setSuccess(true);
				json.setMsg("同步信息成功!");
			}else {
				MerchantAuthenticityBean merchantAuthenticityBean = merchantAuthenticityService.queryMerAuthenticityById(merchantInfoBean.getBmaid());
				if (merchantAuthenticityBean != null && merchantAuthenticityBean.getBmatid() != null) {
					merchantAuthenticityBean.setBmatid(null);
					merchantAuthenticityBean.setMid(t.getMid());
					String sysseqnb = UUID.randomUUID().toString().replaceAll("-", "");
					merchantAuthenticityBean.setSysseqnb(sysseqnb);
					merchantAuthenticityBean.setCdate(new Date());
					merchantAuthenticityBean.setCardName(null);
					merchantAuthenticityBean.setAuthUpload(null);
					merchantAuthenticityBean.setUsername(merchantInfoBean.getHybPhone());
					// 保存信息并推送综合
					Map ok = merchantAuthenticityService.saveMerchantAuthInfo(merchantAuthenticityBean);
					result.put("userName", merchantAuthenticityBean.getUsername());
					result.put("mid", merchantAuthenticityBean.getMid());
					result.put("msg", "认证成功");
					result.put("status", "2");
					merchantAuthenticityService.sendResultToHyb(result, merchantAuthenticityBean);
					if (ok.size() > 0) {
						json.setSuccess(true);
						json.setMsg("同步信息成功!");
					} else {
						json.setSuccess(false);
						json.setMsg("同步信息失败!");
					}
				}
			}
		}catch (Exception e){
			json.setSuccess(false);
			json.setMsg("同步信息失败");
			log.error("商户报单同步异常:"+e.getMessage());
		}
		super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
	}

	public void getSpeedMdSyncMerchantInfoByPhoneDataV2(){
		try {
			String data=getRequest().getParameter("data");
			String aesEn=getRequest().getParameter("aesEn");
			String sck= CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck,data);
			merchantInfoBean= JSONObject.parseObject(data0,MerchantInfoBean.class);
			merchantInfoBean.setSck(sck);
			merchantInfoBean.setEnc(true);
			log.error("getSpeedMdSyncMerchantInfoByPhoneDataV2解密后的请求参数:"+JSON.toJSONString(merchantInfoBean));
			getSpeedMdSyncMerchantInfoByPhone();
		} catch (Exception e) {
			log.error("getSpeedMdSyncMerchantInfoByPhoneDataV2解密请求出错:"+e.getMessage());
		}
	}

	/**
	 * 获取极速秒到版同步商户信息
	 */
	public void getSpeedMdSyncMerchantInfoByPhone(){
		JsonBean json = new JsonBean();
		boolean isOpen=false;
		boolean broken = false;
		ShardedJedis shardedJedis = null;
		//JedisSource rource = new JedisSource();
		//Jedis jedis = rource.getJedis();
		try {
			shardedJedis = RedisUtil.getShardedJedis();
			log.info("是否开启极速秒到版手机号同步商户信息,读取[isSyncSpeedMd]结果为" + shardedJedis.hget("HrtAppConfig", "isSyncSpeedMd"));
			String isSyncPlus = shardedJedis.hget("HrtAppConfig", "isSyncSpeedMd");
			if("1".equals(isSyncPlus)){
				isOpen=true;
			}
		} catch (Exception e) {
			log.info("是否开启极速秒到版手机号同步商户信息,读取[isSyncSpeedMd]异常:"+e.getMessage());
			broken = true;
		} finally {
			//JedisSource.returnResource(jedis);
			if(shardedJedis != null) {
				RedisUtil.delShardedJedis(broken, shardedJedis);
			}
		}
		Map result =getMerInfoAndAuthInfo(isOpen,2);
		if(result!=null){
			json.setSuccess(true);
			json.setObj(result);
			json.setMsg("查询同步信息成功");
		}else{
			json.setSuccess(false);
			json.setMsg("未查询到同步信息");
		}
		super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
	}

    public void syncMerchantInfoByPhoneToSpeedMdDataV2(){
        try {
            String data=getRequest().getParameter("data");
            String aesEn=getRequest().getParameter("aesEn");
            String sck= CommonTools.getSck(aesEn);
            String data0 = CommonTools.parseAesEnAndData(sck,data);
            merchantInfoBean= JSONObject.parseObject(data0,MerchantInfoBean.class);
            merchantInfoBean.setSck(sck);
            merchantInfoBean.setEnc(true);
            log.error("syncMerchantInfoByPhoneToSpeedMdDataV2解密后的请求参数:"+JSON.toJSONString(merchantInfoBean));
            syncMerchantInfoByPhoneToSpeedMd();
        } catch (Exception e) {
            log.error("syncMerchantInfoByPhoneToSpeedMdDataV2解密请求出错:"+e.getMessage());
        }
    }

	/**
	 * 极速秒到版同步已有商户信息
	 */
	public void syncMerchantInfoByPhoneToSpeedMd(){
		JsonBean json = new JsonBean();
		log.error("极速秒到版同步商户信息传入参数merchantInfoBean:"+JSONObject.toJSONString(merchantInfoBean));
		try {
			MerchantInfoModel t = merchantInfoService.saveSyncMerchantInfoByPhoneToNewPlus(merchantInfoBean,merchantInfoBean.getAgentId());
			if(t!=null && !"ERROR".equals(t.getRemarks())) {
				phoneMicroMerchantInfoService.hrtMerchantToHYB(t, merchantInfoBean);
			}else if(t!=null && "ERROR".equals(t.getRemarks())){
				json.setSuccess(false);
				json.setMsg("该手机号已经存在该产品商户");
                super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
				return;
			}else{
				json.setSuccess(false);
				json.setMsg("不存在同步信息");
                super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
				return;
			}
			Map<String, String> result= new HashMap<String, String>();
			if(t!=null && (merchantInfoBean.getBmaid()==0)){
				// 认证信息为0的为银收宝名称
				MerchantAuthenticityBean mb = new MerchantAuthenticityBean();
				mb.setUsername(merchantInfoBean.getHybPhone());
				mb.setMid(t.getMid());
				result.put("userName",merchantInfoBean.getHybPhone());
				result.put("mid",t.getMid());
				result.put("msg","认证成功");
				result.put("status","2");
				merchantAuthenticityService.sendResultToHyb(result,mb);
				json.setSuccess(true);
				json.setMsg("同步信息成功!");
			}else {
				MerchantAuthenticityBean merchantAuthenticityBean = merchantAuthenticityService.queryMerAuthenticityById(merchantInfoBean.getBmaid());
				if (merchantAuthenticityBean != null && merchantAuthenticityBean.getBmatid() != null) {
					merchantAuthenticityBean.setBmatid(null);
					merchantAuthenticityBean.setMid(t.getMid());
					String sysseqnb = UUID.randomUUID().toString().replaceAll("-", "");
					merchantAuthenticityBean.setSysseqnb(sysseqnb);
					merchantAuthenticityBean.setCdate(new Date());
					merchantAuthenticityBean.setCardName(null);
					merchantAuthenticityBean.setAuthUpload(null);
					merchantAuthenticityBean.setUsername(merchantInfoBean.getHybPhone());
					// 保存信息并推送综合
					Map ok = merchantAuthenticityService.saveMerchantAuthInfo(merchantAuthenticityBean);
					result.put("userName", merchantAuthenticityBean.getUsername());
					result.put("mid", merchantAuthenticityBean.getMid());
					result.put("msg", "认证成功");
					result.put("status", "2");
					merchantAuthenticityService.sendResultToHyb(result, merchantAuthenticityBean);
					if (ok.size() > 0) {
						json.setSuccess(true);
						json.setMsg("同步信息成功!");
					} else {
						json.setSuccess(false);
						json.setMsg("同步信息失败!");
					}
				}
			}
		}catch (Exception e){
			json.setSuccess(false);
			json.setMsg("同步信息失败");
			log.error("极速秒到版报单同步已有手机号异常:"+e.getMessage());
		}
        super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
	}
	
	/**
	 * Plus商户入网报单(加密处理)
	 */
	public void addPlusMicroMerchantInfoDataV2() {
		try {
			Map<String,File> fileMap=new HashMap<String,File>();
			fileMap.put("legalUploadFile",merchantInfoBean.getLegalUploadFile());
			fileMap.put("materialUpLoadFile",merchantInfoBean.getMaterialUpLoadFile());
			fileMap.put("materialUpLoad2File",merchantInfoBean.getMaterialUpLoad2File());
			fileMap.put("materialUpLoad1File",merchantInfoBean.getMaterialUpLoad1File());
			String data=getRequest().getParameter("data");
			String aesEn=getRequest().getParameter("aesEn");
			String sck=CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck,data);
			merchantInfoBean=JSONObject.parseObject(data0,MerchantInfoBean.class);
			merchantInfoBean.setSck(sck);
			merchantInfoBean.setEnc(true);
			merchantInfoBean.setLegalUploadFile(fileMap.get("legalUploadFile"));
			merchantInfoBean.setMaterialUpLoadFile(fileMap.get("materialUpLoadFile"));
			merchantInfoBean.setMaterialUpLoad1File(fileMap.get("materialUpLoad1File"));
			merchantInfoBean.setMaterialUpLoad2File(fileMap.get("materialUpLoad2File"));
			log.error("addPlusMicroMerchantInfoDataV2解密后的请求参数:"+JSON.toJSONString(merchantInfoBean));
			addPlusMicroMerchantInfo();
		} catch (Exception e) {
			log.error("addPlusMicroMerchantInfoDataV2解密请求出错:"+e.getMessage());
		}
	}

	/**
	 * Plus商户入网报单
	 */
	public void addPlusMicroMerchantInfo(){
		log.error("addPlusMicroMerchantInfo报单请求参数:"+JSONObject.toJSONString(merchantInfoBean));
		JsonBean json = new JsonBean();
		if(StringUtils.isNotEmpty(merchantInfoBean.getAccExpdate())
				&& StringUtils.isEmpty(merchantInfoBean.getLegalExpdate())){
			merchantInfoBean.setLegalExpdate(merchantInfoBean.getAccExpdate());
		}
		Integer flag = 1;
		boolean flag2=true;
		//  @author:xuegangliu:20190326 身份证号下存在三个机构号的数据返回信息:您已使用我司其他产品，请勿重复注册。)
		//  	992107  易宝付
		//		982125  银收宝
		//		982058  亿米付
//		boolean isExist = merchantInfoService.queryIsExistInHrtUnno(merchantInfoBean);
		boolean isExist = false;
		String hasProd = merchantInfoService.isExistPhoneAndAgentId(merchantInfoBean.getHybPhone(),merchantInfoBean.getAgentId());
		if(StringUtils.isNotEmpty(hasProd)){
			json.setSuccess(false);
			json.setMsg(hasProd);
			super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
			return;
		}
		if(isExist){
			json.setSuccess(false);
			json.setMsg("您已使用我司其他产品，请勿重复注册。");
		}else{
//			boolean c = merchantInfoService.queryIsRegist(merchantInfoBean);//同一身份证注册身份证不能超过5次
			boolean c = true;
			if (c) {
				boolean b = merchantInfoService.queryAge(merchantInfoBean);//年龄限制
				if (b) {
					Integer tidCount = null;
					if(merchantInfoBean.getAgentId() != null && PhoneProdConstant.PHONE_PLUS.equals(merchantInfoBean.getAgentId())){
						tidCount = merchantInfoService.queryPlusTidCountByLegalNum(merchantInfoBean,0);
					}else if(merchantInfoBean.getAgentId() != null && PhoneProdConstant.PHONE_PRO.equals(merchantInfoBean.getAgentId())){
                        tidCount = merchantInfoService.queryPROTidCountByLegalNum(merchantInfoBean,0);
                    }else{
                        json.setSuccess(false);
                        json.setMsg("产品类型不符!");
						super.writeJson(json);
						return;
                    }

					if (tidCount < 10) {
						++tidCount;
						merchantInfoBean.setBankArea("同一个身份证号下共申请Mpos(" + tidCount + ")台！");
						//判断手刷商户编号总数量是否已满！
						boolean flag1 = merchantInfoService.queryMicroMerchantCount();
						boolean isPlus = true;
						if (isPlus || flag1) {
							boolean flag3 = phoneMicroMerchantInfoService.queryIsHotMerch(merchantInfoBean);
							if (flag3) {
							    if(merchantInfoBean.isWeChatProg()){
							        if(StringUtils.isNotEmpty(merchantInfoBean.getMaterialUpLoad4())
                                        && StringUtils.isNotEmpty(merchantInfoBean.getMaterialUpLoad5())
                                        && StringUtils.isNotEmpty(merchantInfoBean.getMaterialUpLoad6())
                                        && StringUtils.isNotEmpty(merchantInfoBean.getMaterialUpLoad7())){
                                    }else{
                                        json.setMsg("上传图片数量不完整！");
                                        json.setSuccess(false);
                                    }
                                }

								if (merchantInfoBean.isWeChatProg()
                                        || (merchantInfoBean.getLegalUploadFile() != null && merchantInfoBean.getMaterialUpLoadFile() != null && merchantInfoBean.getMaterialUpLoad1File() != null && merchantInfoBean.getMaterialUpLoad2File() != null)) {
									if (merchantInfoBean.getSn() != null && !"".equals(merchantInfoBean.getSn())) {
										flag = phoneMicroMerchantInfoService.findHrtSnIsUsing(merchantInfoBean.getSn(), null);
									}
									if (flag == 1) {
										if (merchantInfoBean.getSn() != null && !"".equals(merchantInfoBean.getSn()) && !"null".equals(merchantInfoBean.getSn())) {
											flag2 = phoneMicroMerchantInfoService.findHrtSnInfo(merchantInfoBean.getSn());
										}
										if (flag2) {
											List<Map<String, Object>> list;
											try {
												list = phoneMicroMerchantInfoService.saveHrtMerchantInfo(merchantInfoBean);
												if (list != null) {
													phoneMicroMerchantInfoService.hrtMerchantToHYB((MerchantInfoModel) list.get(0).get("merchantInfoModel"), merchantInfoBean);
												}
												if (list != null && merchantInfoBean.getSn() != null && !"".equals(merchantInfoBean.getSn())) {
													phoneMicroMerchantInfoService.saveHrtMerToADMDB(merchantInfoBean, list);
												}
												json.setObj(list);
												json.setSuccess(true);
												json.setMsg("注册成功");
											} catch (Exception e) {
												json.setSuccess(false);
												json.setMsg("注册失败");
												e.printStackTrace();
											}
										} else {
											json.setMsg("该SN号不存在(此版本禁止使用押金设备)！");
											json.setSuccess(false);
										}
									} else if (flag == 2) {
										json.setMsg("该Pos:SN号已绑定成功,请去刷卡收款！");
										json.setSuccess(false);
									} else {
										json.setMsg("该Pos:SN号已经被占用,请联系销售！");
										json.setSuccess(false);
									}
								} else {
									json.setMsg("上传图片数量不完整！");
									json.setSuccess(false);
								}
							} else {
								json.setMsg("该户在黑名单商户中，请核查！");
								json.setSuccess(false);
							}
						} else {
							json.setSuccess(false);
							json.setMsg("商户编号生成错误，请稍后重试！");
						}
					} else {
						json.setSuccess(false);
						json.setMsg("同一个身份证号申请Mpos超限！");
					}
				} else {
					json.setSuccess(false);
					json.setMsg("商户入网年龄要求18-60岁！");
				}
			} else {
				json.setSuccess(false);
				json.setMsg("您已注册过会员宝，不允许重复注册！");
			}
		}
		super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
		//super.writeJson(json);
	}

	public void addMerchantTermianalInfoByPhoneDataV2(){
		// @author:lxg-20191114 敏感信息加密处理
		try {
			String data=getRequest().getParameter("data");
			String aesEn=getRequest().getParameter("aesEn");
			String sck=CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck,data);
			merchantInfoBean=JSONObject.parseObject(data0,MerchantInfoBean.class);
			merchantInfoBean.setSck(sck);
			merchantInfoBean.setEnc(true);
			log.error("addMerchantTermianalInfoByPhoneDataV2解密后的请求参数:"+JSON.toJSONString(merchantInfoBean));
            addMerchantTermianalInfoByPhone();
		} catch (Exception e) {
			log.error("addMerchantTermianalInfoByPhoneDataV2解密请求出错:"+e.getMessage());
		}
	}

	/**
	 * 和融通手机端增机申请
	 */
	public void addMerchantTermianalInfoByPhone(){
		JsonBean json = new JsonBean();
		Integer flag2 = 5;
		boolean isChangeUnno = true;
		if(!phoneMicroMerchantInfoService.queryIsHotMerch(merchantInfoBean)){
			json.setMsg("该户在黑名单商户中，请核查！");
			json.setSuccess(false);
//			super.writeJson(json);
			// @author:lxg-20191114 敏感信息加密处理
			super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
			return ;
		}
		
		//限制e11002\421003下小蓝牙设备禁止使用(20200529)
		String isSpecialUnno = phoneMicroMerchantInfoService.queryIsSpecialUnno(merchantInfoBean);
		if(isSpecialUnno!=null) {
			json.setMsg(isSpecialUnno);
			json.setSuccess(false);
			super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
			return ;
		}
		
		//绑定设备判定规则
		String errMsg = phoneMicroMerchantInfoService.queryAgainSaveTer(merchantInfoBean);
		if(StringUtils.isNotEmpty(errMsg)){
            json.setMsg(errMsg);
            json.setSuccess(false);
//            super.writeJson(json);
			// @author:lxg-20191114 敏感信息加密处理
			super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
            return ;
        }
		Integer tidCount=null;
		if(merchantInfoBean.getAgentId()!=null&&PhoneProdConstant.PHONE_SYT.equals(merchantInfoBean.getAgentId())) {
			tidCount = merchantInfoService.queryTidCountByLegalNum2(merchantInfoBean, 1);
		}else {
			tidCount=merchantInfoService.queryTidCountByLegalNum("", 1,merchantInfoBean.getMid());
		}
		if(tidCount<10){
			//判断SN是优享，APP是否可以使用优享设备
			boolean isNew = phoneMicroMerchantInfoService.findHrtAppIsNew(merchantInfoBean);
			if(isNew){
				//根据SN判断该pose是否已经被绑定  1未使用；2已绑定；3被其他使用
				Integer flag=phoneMicroMerchantInfoService.findHrtSnIsUsing(merchantInfoBean.getSn(),merchantInfoBean.getMid());
				if(flag==1){
					//查询微商的unno
					MerchantInfoModel infoModel= phoneMicroMerchantInfoService.queryMicroMerchantInfo(merchantInfoBean.getMid());
					//如果设备机构号是字母开头，商户机构号是数字开头且不为000000，修改设备机构号为数字开头
					isChangeUnno = phoneMicroMerchantInfoService.updateUnnoForTer(merchantInfoBean.getSn(),infoModel.getUnno(),true);
					//判断SN是否存在
					flag2=phoneMicroMerchantInfoService.findHrtSnInfoT(merchantInfoBean.getSn(),infoModel.getUnno());
					if(flag2==4){
						boolean flag3=phoneMicroMerchantInfoService.findMidInfo(merchantInfoBean.getMid());
						if(flag3){
							List<Map<String,String>> list = phoneMicroMerchantInfoService.saveMerchantTermialInfoByPhone(merchantInfoBean);
							if(list==null){
								json.setSuccess(false);
								json.setMsg("设备与商户费率信息不一致,请联系销售！"); 
							}else{
								if(list.get(0).containsKey("msg")){
									json.setSuccess(false);
									json.setMsg(list.get(0).get("msg")); 
								}else{
									phoneMicroMerchantInfoService.merchantTermialInfotoHyb(merchantInfoBean,list);
									json.setObj(list);
									json.setSuccess(true);
									// 10000 秒到,10006PLUS,10002联刷,10005收银台,
									boolean isPlusOrMD = merchantInfoBean.getAgentId()!=null
											&& ".10000.10006.10007.10009.".contains("."+merchantInfoBean.getAgentId()+".");
									if(list.get(0).containsKey("deposit")){
										if("1".equals(list.get(0).get("yxsb"))){
											if(isPlusOrMD){
												json.setMsg("设备绑定成功，请在“我的设备”中设备列表查看。\n请在“会员宝收款器”微信公众号内绑定您注册的账号，以便享受实时交易提醒、历史交易查询等更多服务。");
											}else {
												json.setMsg("设备绑定成功，请在“我的设备”中设备列表查看。");
											}
										}else{
											if(isPlusOrMD){
												json.setMsg("设备绑定成功，请在“我的设备”中设备列表查看。\n请在“会员宝收款器”微信公众号内绑定您注册的账号，以便享受实时交易提醒、历史交易查询等更多服务。");
											}else {
												json.setMsg("设备绑定成功，请在“我的设备”中设备列表查看。");
											}
										}
									}else{
										if(isPlusOrMD){
											json.setMsg("增机申请成功，请您 10分钟后 刷卡或扫码收款！\n请在“会员宝收款器”微信公众号内绑定您注册的账号，以便享受实时交易提醒、历史交易查询等更多服务。");
										}else {
											json.setMsg("增机申请成功，请您 10分钟后 刷卡或扫码收款！");
										}
									}
								}
							}
						}else{
							json.setMsg("MID不存在,请联系销售！");
							json.setSuccess(false);
						}
					}else if(flag2==5){
						json.setMsg("该POS:SN号与商户归属不一致,请联系销售！");
						json.setSuccess(false);
					}else{
						json.setMsg("该POS:SN号不存在,请联系销售！");
						json.setSuccess(false);
					}
					//修改过设备的机构号，判断设备是否被使用，未被使用则修改设备机构号为字母开头
					if(!isChangeUnno) {
						phoneMicroMerchantInfoService.updateUnnoForTer(merchantInfoBean.getSn(),infoModel.getUnno(),false);
					}
				}else if(flag==2){
					json.setMsg("该POS:SN号已绑定成功！");
					json.setSuccess(false);
				}else{
					json.setMsg("该POS:SN号已被使用,请联系销售！");
					json.setSuccess(false);
				}
			}else{
				json.setMsg("使用优享设备，请将APP更新到最新版本");
				json.setSuccess(false);
			}
		}else{
			json.setSuccess(false);
			json.setMsg("同一个身份证号申请Mpos超限！");
		}
//		super.writeJson(json);
		// @author:lxg-20191114 敏感信息加密处理
		super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
	}

	/**
	 * 聚合商户app自助报单
	 */
	public void addAggPayMerchantInfo(){
		JsonBean json = new JsonBean();
		log.info("立码富商户自助报单请求:phone="+merchantInfoBean.getHybPhone()+";ranme="+merchantInfoBean.getRname()+";remarks="+merchantInfoBean.getRemarks());
		Integer qrtidCount=merchantInfoService.queryQRidCountByLegalNum(merchantInfoBean.getLegalNum(), 0, "");
		//商户类型  4企业；5商户；6个人
		if(qrtidCount<5||!"6".equals(merchantInfoBean.getAreaType())){
			if(qrtidCount>=5){
				++qrtidCount;
				merchantInfoBean.setBankArea("同一个身份证号下申请聚合商户("+qrtidCount+")！");
				merchantInfoBean.setProcessContext("同一个身份证号下申请聚合商户("+qrtidCount+")！");
			}
			boolean flag3=phoneMicroMerchantInfoService.queryIsHotMerch(merchantInfoBean);
			if(flag3){
				//限制多次报单
				boolean flag4=phoneMicroMerchantInfoService.queryManyTimes(merchantInfoBean);
				if(flag4){
//					Integer flag = 0;
//					if(merchantInfoBean.getQrtid()!=null&&!"".equals(merchantInfoBean.getQrtid())){
//						flag = phoneMicroMerchantInfoService.findHrtQridIsUsing(merchantInfoBean.getQrtid(),null);
//					}
//					//5不存在；0未使用；4停用；6占用
//					if(flag==0){
						List<Map<String, Object>> list;
						try {
							list = phoneMicroMerchantInfoService.saveAggPayMerchantInfo(merchantInfoBean);
							if(list!=null){
								phoneMicroMerchantInfoService.hrtAggPayMerchantToHYB((MerchantInfoModel)list.get(0).get("merchantInfoModel"),merchantInfoBean);
//								if(merchantInfoBean.getQrtid()!=null&&!"".equals(merchantInfoBean.getQrtid())){
//									phoneMicroMerchantInfoService.saveAggMerToADMDB(merchantInfoBean,list);
//								}
								json.setObj(list);
								json.setSuccess(true);
								json.setMsg("注册成功"); 
							}else{
								json.setSuccess(false);
								json.setMsg("注册失败");
							}
						} catch (Exception e) {
							log.info("聚合商户app自助报单异常："+e);
							json.setSuccess(false);
							json.setMsg("注册失败!");
						}
//					}else if(flag==5){
//						json.setMsg("该终端号("+merchantInfoBean.getQrtid()+")不存在!");
//						json.setSuccess(false);
//					}else if(flag==4){
//						json.setMsg("该终端号("+merchantInfoBean.getQrtid()+")已被停用!");
//						json.setSuccess(false);
//					}else{
//						json.setMsg("该终端号("+merchantInfoBean.getQrtid()+")已被占用!");
//						json.setSuccess(false);
//					}
				}else{
					json.setMsg("用户操作频繁，请稍后再试！");
					json.setSuccess(false);
				}
			}else{
				json.setMsg("该户在黑名单商户中，请核查！");
				json.setSuccess(false);
			}
		}else{
			json.setSuccess(false);
			json.setMsg("申请商户超限！");
		}
		log.info("立码富商户自助报单返回:phone="+merchantInfoBean.getHybPhone()+";ranme="+merchantInfoBean.getRname()+";msg="+json.getMsg());
		super.writeJson(json);
	}
	
	/**
	 * 聚合商户增机申请
	 */
	public void addAggPayMerchantTermianalInfo(){
		JsonBean json = new JsonBean();
		if(!phoneMicroMerchantInfoService.queryIsHotMerch(merchantInfoBean)){
			json.setMsg("该户在黑名单商户中，请核查！");
			json.setSuccess(false);
			super.writeJson(json);
			return ;
		}
//		Integer qrtidCount=merchantInfoService.queryQRidCountByLegalNum("", 1, merchantInfoBean.getMid());
//		if(qrtidCount<5){
			Integer flag=phoneMicroMerchantInfoService.updateHrtQridIsUsing(merchantInfoBean.getQrtid(),merchantInfoBean.getMid(),merchantInfoBean.getQrPwd());
			//5不存在；0未使用；4停用；1待审核；2审核通过；3退回；6占用;7机构归属不一致 ;8 非法链接
			if(flag==0){
				boolean flag3=phoneMicroMerchantInfoService.findMidInfo(merchantInfoBean.getMid());
				if(flag3){
					List<Map<String,String>> list = phoneMicroMerchantInfoService.saveAggPayMerchantTermialInfo(merchantInfoBean);
					if(list==null){
						json.setSuccess(true);
						json.setMsg("增机申请成功，请您10分钟后扫码收款!"); 
					}else{
						if(list.get(0).containsKey("msg")){
							json.setSuccess(false);
							json.setMsg(list.get(0).get("msg")); 
						}
					}
				}else{
					json.setMsg("商户不存在,请联系销售!");
					json.setSuccess(false);
				}
			}else if(flag==1){
				json.setMsg("该终端号("+merchantInfoBean.getQrtid()+")正在审核中!请稍等");
				json.setSuccess(false);
			}else if(flag==2){
				json.setMsg("该终端号("+merchantInfoBean.getQrtid()+")已绑定成功!");
				json.setSuccess(false);
			}else if(flag==3){
				json.setMsg("非接终端不存在/已绑定!");
				json.setSuccess(false);
			}else if(flag==6){
				json.setMsg("该终端号("+merchantInfoBean.getQrtid()+")已被占用!");
				json.setSuccess(false);
			}else if(flag==5){
				json.setMsg("该终端号("+merchantInfoBean.getQrtid()+")不存在!");
				json.setSuccess(false);
			}else if(flag==4){
				json.setMsg("该终端号("+merchantInfoBean.getQrtid()+")已被停用!");
				json.setSuccess(false);
			}else if(flag==7){
				json.setMsg("该终端与商户归属不一致,请联系销售!");
				json.setSuccess(false);
			}else if(flag==8){
				json.setMsg("非法二维码!");
				json.setSuccess(false);
			}else{
				json.setMsg("该终端号("+merchantInfoBean.getQrtid()+")已被退回!");
				json.setSuccess(false);
			}
//		}else{
//			json.setSuccess(false);
//			json.setMsg("申请二维码超限！");
//		}
		super.writeJson(json);
	}
	
	/**
	 * 聚合商户伪增机申请(只走快捷)
	 */
	public void addAggPayMerchantKJInfo(){
		JsonBean json = new JsonBean();
		boolean flag3=phoneMicroMerchantInfoService.findMidInfo(merchantInfoBean.getMid());
		if(flag3){
			List<Map<String,String>> list = phoneMicroMerchantInfoService.saveAggPayMerchantKJInfo(merchantInfoBean);
			if(list==null){
				json.setSuccess(true);
				json.setMsg("申请成功!"); 
			}else{
				if(list.get(0).containsKey("msg")){
					json.setSuccess(false);
					json.setMsg(list.get(0).get("msg")); 
				}
			}
		}else{
			json.setMsg("商户不存在!");
			json.setSuccess(false);
		}
		log.info("立码富商户快捷开通返回:mid="+merchantInfoBean.getMid()+";msg="+json.getMsg());
		super.writeJson(json);
	}
	
	/**
	 * 查询销售
	 */
	public void searchAgList(){
		JsonBean json = new JsonBean();
		DataGridBean dgd = new DataGridBean();
		try {
			String saleName = super.getRequest().getParameter("saleName");
			AgentSalesBean agentSales= new AgentSalesBean();
			agentSales.setSaleName(saleName);
			agentSales.setPage(1);
			agentSales.setRows(10);
			dgd = agentSalesService.queryAgentSales(agentSales, "110000");
			json.setObj(dgd.getRows());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("app报单查询销售：" + e);
		}
    	super.writeJson(json);
	}
	
	/**
	 * 查询商户审批状态
	 */
	public void queryMicroMerchantStatus(){
		JsonBean json = new JsonBean();
		if(merchantInfoBean.getMid()!=null && !"".equals(merchantInfoBean.getMid())){
			boolean flag3=phoneMicroMerchantInfoService.findMidInfo(merchantInfoBean.getMid());
			if(flag3){
				List<Map<String,Object>> list =phoneMicroMerchantInfoService.findMidStatusInfo(merchantInfoBean.getMid());
				json.setObj(list);
				json.setMsg("查询成功!");
				json.setSuccess(true);
			}else{
				json.setMsg("MID不存在！");
				json.setSuccess(false);
			}
		}else{
			json.setMsg("上传MID参数为空！");
			json.setSuccess(false);
	    }

	    super.writeJson(json);
	}
	
	/**
	 * 查询商户审批状态(解密)
	 */
	public void decQueryMicroMerchantStatus(){
		JsonBean json = new JsonBean();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"), super.getRequest().getParameter("timeStamp"));
		if(!b) {
			json.setSuccess(false);
			json.setMsg("数据有误！");
		}else {
			if(merchantInfoBean.getMid()!=null && !"".equals(merchantInfoBean.getMid())){
				Map<String,String> map1 = new HashMap<String, String>();
				String mid=super.getRequest().getParameter("mid");
				String sign = super.getRequest().getParameter("sign");
				map1.put("mid", mid);
				String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1)+ParamPropUtils.props.getProperty("signEnd")));
				if(md5.equals(sign)){
					boolean flag3=phoneMicroMerchantInfoService.findMidInfo(merchantInfoBean.getMid());
					if(flag3){
						List<Map<String,Object>> list =phoneMicroMerchantInfoService.findMidStatusInfo(merchantInfoBean.getMid());
						json.setObj(list);
						json.setMsg("查询成功!");
						json.setSuccess(true);
					}else{
						json.setMsg("MID不存在！");
						json.setSuccess(false);
					}
				}else{
					json.setSuccess(false);
					json.setMsg("数据有误！");
				}
			}else{
				json.setMsg("上传MID参数为空！");
				json.setSuccess(false);
			}
		}
		JsonBeanForSign json1 = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json1.setSign(signs1);
		json1.setData(data);
		super.writeJson(json1);
	}
	
	/**
	 * 根据Mid查看明细(获取二维码路径)
	 */
	public void serachMerchantInfoMid(){
		JsonBean json = new JsonBean();
		String url =null;
		try {
			DataGridBean dgb = merchantInfoService.queryMerchantInfoMid(merchantInfoBean.getMid());
			if(dgb!=null&&dgb.getRows().size()>0){
				MerchantInfoBean merchantInfoBean=(MerchantInfoBean) dgb.getRows().get(0);
				url=merchantInfoBean.getQrUrl();
				if(url!=null&&!"".equals(url)){
					json.setSuccess(true);
					// @author:lxg-20190716 将sign换为 mid+时间戳 aes加密
					long date=new Date().getTime();
					String sign = merchantInfoBean.getMid()+date;
					String aesBase64Sign = AESUtil.base64AndAesEncode(sign);
                    String signValue=URLEncoder.encode(aesBase64Sign,"utf-8");
					String qrUrl =url.substring(0,url.lastIndexOf("=")+1)+signValue;
					json.setObj(qrUrl+"&timeStamp="+date);
					json.setMsg("查询成功");
				}else{
					json.setObj(merchantInfoBean.getMid());
					json.setMsg("未注册二维码");
					json.setSuccess(false);
				}
			}else{
				json.setMsg("未注册二维码");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("手机查询商户二维码信息异常：mid = "+merchantInfoBean.getMid() + e);
			json.setSuccess(false);
			json.setMsg("未注册二维码");
		}
		super.writeJson(json);
	}

    /**
     * 根据Mid查看银收宝明细(获取二维码路径)
     */
	public void serachYSBMerchantInfoMid(){
		JsonBean json = new JsonBean();
		String url =null;
		try {
		    log.info("银收宝商户查询二维码传入参数:"+merchantInfoBean.getMid());
			DataGridBean dgb = merchantInfoService.queryMerchantInfoMid(merchantInfoBean.getMid());
			if(dgb!=null&&dgb.getRows().size()>0){
				MerchantInfoBean merchantInfoBean=(MerchantInfoBean) dgb.getRows().get(0);

				// @author:xuegangliu-20190408 二维码拼接  &sign=NAXOSOXMGI  qrpayment
				// http://xpay.hrtpayment.com/xpay/qrpayment?mid=212134336255941&sign=EIALIHPZUX
				// @author:lxg-20190716 将sign换为 mid+时间戳 aes加密
				long date = new Date().getTime();
				String sign = merchantInfoBean.getMid()+date;
				String aesBase64Sign = AESUtil.base64AndAesEncode(sign);
				String signValue=URLEncoder.encode(aesBase64Sign,"utf-8");
				StringBuffer sb = new StringBuffer(ParamPropUtils.props.get("xpayPath")+"/qrpayment?mid=");
				sb.append(merchantInfoBean.getMid()).append("&sign=").append(signValue);
				merchantInfoBean.setQrUrl(sb.toString());
				url=merchantInfoBean.getQrUrl();
				if(url!=null&&!"".equals(url)){
					json.setSuccess(true);
					json.setObj(url+"&timeStamp="+date);
					json.setMsg("查询成功");
				}else{
					json.setObj(merchantInfoBean.getMid());
					json.setMsg("未注册二维码");
					json.setSuccess(false);
				}
			}else{
				json.setMsg("未注册二维码");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("手机查询商户二维码信息异常：mid = "+merchantInfoBean.getMid() + e);
			json.setSuccess(false);
			json.setMsg("未注册二维码");
		}
        log.info("银收宝商户查询二维码返回参数:"+JSON.toJSONString(json));
		super.writeJson(json);
	}
	
	/**
	 * 根据Mid查看是否绑定终端
	 */
	public void serachMerTerByMid(){
		JsonBean json = new JsonBean();
		try {
			MerchantTerminalInfoBean mt = new MerchantTerminalInfoBean();
			String mid = super.getRequest().getParameter("mid");
			mt.setMid(mid);
			DataGridBean dgb = merchantTerminalInfoService.queryMerchantTerminalInfoBmid(mt);
			if(dgb!=null&&dgb.getRows().size()>0){
//				MerchantInfoBean merchantInfoBean=(MerchantInfoBean) dgb.getRows().get(0);
				json.setObj(dgb.getRows());
				json.setSuccess(true);
				json.setMsg("查询成功");
			}else{
				json.setMsg("未绑定终端");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("手机查询商户是否绑定终端：mid = "+merchantInfoBean.getMid() + e);
			json.setSuccess(false);
			json.setMsg("未绑定终端");
		}
		super.writeJson(json);
	}

	public void serachMerTerByMidNewDataV2(){
		// @author:lxg-20191114 敏感信息加密处理
		try {
			String data=getRequest().getParameter("data");
			String aesEn=getRequest().getParameter("aesEn");
			log.debug("serachMerTerByMidNewDataV2 data:"+data+",aes:"+aesEn);
			String sck= CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck,data);
			merchantInfoBean= JSONObject.parseObject(data0,MerchantInfoBean.class);
			merchantInfoBean.setSck(sck);
			merchantInfoBean.setEnc(true);
			log.error("serachMerTerByMidNewDataV2解密后的请求参数:"+JSON.toJSONString(merchantInfoBean));
			serachMerTerByMidNew();
		} catch (Exception e) {
			log.error("serachMerTerByMidNewDataV2解密请求出错:"+e.getMessage());
		}
	}

	/**
	 * 根据Mid查看是否绑定终端（新）
	 */
	public void serachMerTerByMidNew(){
		log.error("手机查询商户是否绑定终端请求参数:"+JSON.toJSONString(merchantInfoBean));
		JsonBean json = new JsonBean();
		try {
			MerchantTerminalInfoBean mt = new MerchantTerminalInfoBean();
			String mid = super.getRequest().getParameter("mid");
			mt.setMid(mid);
			if(merchantInfoBean.isEnc() && merchantInfoBean.getMid()!=null){
				mt.setMid(merchantInfoBean.getMid());
			}
			List<Map<String,Object>> list = merchantTerminalInfoService.queryMerchantTerminalInfoBmidPhone(mt);
			if(list!=null&&list.size()>0){
                Integer isAct = merchantTerminalInfoService.queryMerchantIsAct(mt);
                json.setNumberUnits(String.valueOf(isAct));
				json.setObj(list);
				json.setSuccess(true);
				json.setMsg("查询成功");
			}else{
				json.setMsg("未绑定终端");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("手机查询商户是否绑定终端：mid = "+merchantInfoBean.getMid() + e);
			json.setSuccess(false);
			json.setMsg("未绑定终端");
		}
//		super.writeJson(json);
		// @author:lxg-20191114 敏感信息加密处理
		super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
	}

	/**
	 * 查询终端是否激活
	 */
	public void serachTerminalIsActByTidAndMid(){
		JsonBean json = new JsonBean();
		try {
			String mid = super.getRequest().getParameter("mid");
			String tid = super.getRequest().getParameter("tid");
			if(StringUtils.isEmpty(mid) || StringUtils.isEmpty(tid)){
				json.setSuccess(false);
				json.setMsg("终端号与商户号不能为空");
			}else{
				MerchantTerminalInfoBean mt = new MerchantTerminalInfoBean();
				mt.setMid(mid);
				mt.setTid(tid);
				Map map = merchantTerminalInfoService.serachTerminalIsActByTid(mt);
				json.setObj(map);
				json.setSuccess(true);
				json.setMsg("查询成功");
			}
		} catch (Exception e) {
			log.error("手机查询查询终端是否激活：mid = "+merchantInfoBean.getMid() + e);
			json.setSuccess(false);
			json.setMsg("未绑定终端");
		}
		super.writeJson(json);
	}

	/**
	 * 根据Mid查看是否绑定聚合终端
	 */
	public void serachAggPayMerTerByMidNew(){
		JsonBean json = new JsonBean();
		try {
			AggPayTerminfoBean mt = new AggPayTerminfoBean();
			String mid = super.getRequest().getParameter("mid");
			mt.setMid(mid);
			List<Map<String,String>> list = aggPayTerminfoService.queryAggPayTerminalInfoBmidPhone(mt);
			if(list!=null&&list.size()>0){
				json.setObj(list);
				json.setSuccess(true);
				json.setMsg("查询成功");
			}else{
				json.setMsg("未绑定聚合终端");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("手机查询商户是否绑定聚合终端：mid = "+merchantInfoBean.getMid() + e);
			json.setSuccess(false);
			json.setMsg("未绑定聚合终端");
		}
		super.writeJson(json);
	}
	
	/**
	 * 根据传入sn查询是否押金设备
	 * */
	public void serachMerchantinfoDepositBySn() {
		JsonBean json = new JsonBean();
		Map map = phoneMicroMerchantInfoService.serachMerchantinfoDepositBySn(merchantInfoBean.getSn());
		Long flag = Long.parseLong(map.get("depositAmt")+"");
		String numberUnits=map.get("numberUnits").toString();
		log.info("根据传入sn查询是否押金设备：sn="+merchantInfoBean.getSn()+";flag="+flag);
		if (flag!=null&&flag>0) {
			json.setSuccess(true);
			json.setObj(flag);
			json.setMsg("此SN:"+merchantInfoBean.getSn()+"为押金设备");
		}else{
			json.setMsg("此SN:"+merchantInfoBean.getSn()+"非押金设备");
			json.setSuccess(false);
			json.setObj(0);
		}
		json.setNumberUnits(numberUnits);
		super.writeJson(json);
	}
	
	/**
	 * 根据传入商户编号&tid 更新押金位
	 * */
	public void updateMerchantinfoDeposit() {
		JsonBean json = new JsonBean();
		log.info("APP押金商户更新押金标识：mid="+merchantInfoBean.getMid()+";tid="+merchantInfoBean.getTid()+";");
		Integer flag = phoneMicroMerchantInfoService.updateDeposit(merchantInfoBean.getMid(),merchantInfoBean.getTid());
		log.info("APP押金商户更新押金标识：mid="+merchantInfoBean.getMid()+";tid="+merchantInfoBean.getTid()+";flag="+flag);
		if (flag>0) {
			json.setSuccess(true);
			json.setMsg("更新成功！");
		}else{
			json.setMsg("更新失败！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	
	/**
	 * 加密查询微商户信息
	 */
	public void encQueryMicroMerchantInfo(){
		try {
			JsonBean json = new JsonBean();
			boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"), super.getRequest().getParameter("timeStamp"));
			if(!b) {
				json.setSuccess(false);
				json.setMsg("数据有误！");
			}else {
				String mid = merchantInfoBean.getMid();
				mid= HrtRSAUtil.decryptWithBase64(mid);
				if(mid!=null&&!"".equals(mid)){
					MerchantInfoModel m= phoneMicroMerchantInfoService.queryMicroMerchantInfo(mid);
					if(m!=null){
						List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
						Map<String,Object> map = new HashMap<String, Object>();
						//加密身份证和卡号
						String bankAccNo = m.getBankAccNo();
						String legalNum = m.getLegalNum();
						String a = "";
						for(int i =0;i<bankAccNo.length()-10;i++) {
							a += "*";
						}
						map.put("rname",m.getRname());
						map.put("bankAccName", m.getBankAccName());
						map.put("bankAccNo", bankAccNo.substring(0, 6)+a+bankAccNo.substring(bankAccNo.length()-4));
						map.put("legalNum", legalNum.substring(0, 6)+"********"+legalNum.substring(legalNum.length()-4));
						map.put("legalNum1", HrtRSAUtil.encryptWithBase64(legalNum));
						map.put("contactPhone", "");
						map.put("raddr", m.getRaddr());
						map.put("bno", m.getBno());
						map.put("mid", m.getMid());
						map.put("accType", m.getAccType());
						map.put("payBankId", m.getPayBankId());
						map.put("legalPerson", m.getLegalPerson());
						map.put("contactPerson", m.getContactPerson());
						map.put("bankBranch", m.getBankBranch().substring(0, m.getBankBranch().indexOf("行")+1));
						list.add(map);
						DataGridBean dgb = new DataGridBean();
						dgb.setRows(list);
						dgb.setTotal(1);
						json.setSuccess(true);
						json.setMsg("查询成功！");
						json.setObj(dgb);
					}else{
						json.setMsg("MID不存在！");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("数据有误！");
					json.setSuccess(false);
				}
			}
			JsonBeanForSign json1 = new JsonBeanForSign();
			String data = JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss");
			String signs1 = SHAEncUtil.getSHA256Str(data);
			json1.setSign(signs1);
			json1.setData(data);
			super.writeJson(json1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密请求信息查询微商户信息
	 */
	public void queryMicroMerchantInfoDataV2(){
		// @author:lxg-20191114 敏感信息加密处理
		try {
			String data=getRequest().getParameter("data");
			String aesEn=getRequest().getParameter("aesEn");
			String sck=CommonTools.getSck(aesEn);
			log.debug("queryMicroMerchantInfoDataV2 data:"+data+",aesEn:"+aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck,data);
			merchantInfoBean=JSONObject.parseObject(data0,MerchantInfoBean.class);
			merchantInfoBean.setSck(sck);
			merchantInfoBean.setEnc(true);
			log.error("queryMicroMerchantInfoDataV2解密后的请求参数:"+JSON.toJSONString(merchantInfoBean));
            queryMicroMerchantInfo();
		} catch (Exception e) {
			log.error("queryMicroMerchantInfoDataV2解密请求出错:"+e.getMessage());
		}
	}

	/**
	 * 查询微商户信息
	 */
	public void queryMicroMerchantInfo(){
		JsonBean json = new JsonBean();
		// @author:lxg-20190509 请求添加apiVersion参数,新版本mid加密,旧版本apiVersion无值
		String mid=null;
		if(StringUtils.isNotEmpty(merchantInfoBean.getApiVersion())){
			String apiVersion=merchantInfoBean.getApiVersion();
			if(apiVersion.equals("v2") || "v2"==apiVersion) {
				if(merchantInfoBean.isEnc()){
					mid = merchantInfoBean.getMid();
				}else{
					mid = HrtRSAUtil.decryptWithBase64(merchantInfoBean.getMid());
				}
			}else{
				json.setMsg("请更换v2版本进行查询！");
				json.setSuccess(false);
				// @author:lxg-20191114 敏感信息加密处理
				super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
				return;
			}
		}else {
			mid=merchantInfoBean.getMid();
		}
		MerchantInfoModel m= phoneMicroMerchantInfoService.queryMicroMerchantInfo(mid);
		if(m!=null){
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String, Object>();
//			if("".equals(m.getDeposit())||m.getDeposit()==null){
//				m.setDeposit(0d);
//			}
//			if("".equals(m.getDepositFlag())||m.getDepositFlag()==null){
//				m.setDepositFlag(0);
//			}
			map.put("rname",m.getRname());
			map.put("bankAccName", m.getBankAccName());
			map.put("bankAccNo", m.getBankAccNo());
			map.put("legalNum", m.getLegalNum());
			map.put("contactPhone", "");
			map.put("raddr", m.getRaddr());
			map.put("bno", m.getBno());
			map.put("mid", m.getMid());
			map.put("accType", m.getAccType());
			map.put("payBankId", m.getPayBankId());
			map.put("legalPerson", m.getLegalPerson());
			map.put("contactPerson", m.getContactPerson());
			map.put("areaType", m.getAreaType());
//			map.put("depositAmt", m.getDeposit().intValue());
//			map.put("depositFlag", m.getDepositFlag());
			map.put("bankBranch", m.getBankBranch().substring(0, m.getBankBranch().indexOf("行")+1));
			list.add(map);
			DataGridBean dgb = new DataGridBean();
			dgb.setRows(list);
			dgb.setTotal(1);
			json.setSuccess(true);
			json.setMsg("查询成功！");
			json.setObj(dgb);
		}else{
			json.setMsg("MID不存在！");
			json.setSuccess(false);
		}
		super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
	}
	
	/**
	 * 解密更新微商户商户信息
	 */
	public void decUpdateMicroMerchantInfo(){
		JsonBean json = new JsonBean();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"), super.getRequest().getParameter("timeStamp"));
		if(!b) {
			json.setSuccess(false);
			json.setMsg("数据有误！");
		}else {
			String legalNum = merchantInfoBean.getLegalNum();
			String bankAccNo = merchantInfoBean.getBankAccNo();
//			String contactPhone = merchantInfoBean.getContactPhone();
//			String hybPhone = merchantInfoBean.getHybPhone();
			String bankBranch = merchantInfoBean.getBankBranch();
			String payBankId = merchantInfoBean.getPayBankId();
			String raddr = merchantInfoBean.getRaddr();
			String agentId = merchantInfoBean.getAgentId();
			String bno = merchantInfoBean.getBno();
			String bankAccName = merchantInfoBean.getBankAccName();
			String settMethod = merchantInfoBean.getSettMethod();
			String mid = merchantInfoBean.getMid();
	
			String sign = super.getRequest().getParameter("sign");
			Map<String,String> map1 = new HashMap<String, String>();
			map1.put("legalNum", legalNum);
			map1.put("bankAccNo", bankAccNo);
			map1.put("contactPhone", "");
			map1.put("hybPhone", "");
			map1.put("bankBranch", bankBranch);
			map1.put("payBankId", payBankId);
			map1.put("raddr", raddr);
			map1.put("agentId", agentId);
			map1.put("bno", bno);
			map1.put("bankAccName", bankAccName);
			map1.put("settMethod", settMethod);
			map1.put("mid", mid);
			String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1)+ParamPropUtils.props.getProperty("signEnd")));
			if(md5.equals(sign)){
				String decrypt= HrtRSAUtil.decryptWithBase64(legalNum);
				String decrypt2= HrtRSAUtil.decryptWithBase64(bankAccNo);
				//图片解密
				merchantInfoBean.setMaterialUpLoadFile(HrtRSAUtil.decryptStringToFile(merchantInfoBean.getMaterialUpLoad().substring(10)));
				merchantInfoBean.setMaterialUpLoad4File(HrtRSAUtil.decryptStringToFile(merchantInfoBean.getMaterialUpLoad4().substring(10)));
				merchantInfoBean.setMaterialUpLoad2File(HrtRSAUtil.decryptStringToFile(merchantInfoBean.getMaterialUpLoad2().substring(10)));
				merchantInfoBean.setBupLoadFile(HrtRSAUtil.decryptStringToFile(merchantInfoBean.getBupLoad().substring(10)));
				merchantInfoBean.setRegistryUpLoadFile(HrtRSAUtil.decryptStringToFile(merchantInfoBean.getRegistryUpLoad().substring(10)));
				merchantInfoBean.setLegalUploadFile(HrtRSAUtil.decryptStringToFile(merchantInfoBean.getLegalUploadFileName().substring(10)));
				
				merchantInfoBean.setLegalNum(decrypt);
				merchantInfoBean.setBankAccNo(decrypt2);
				MerchantInfoModel m= phoneMicroMerchantInfoService.queryMicroMerchantInfo(merchantInfoBean.getMid());
				if(m!=null){
					if(!"Y".equals(m.getApproveStatus()) && !"C".equals(m.getApproveStatus())){
						try {
							phoneMicroMerchantInfoService.updateMicroMerchantInfoZWK(m,merchantInfoBean);
							json.setMsg("更新成功");
							json.setSuccess(true);
						} catch (Exception e) {
							log.error("更新信息异常", e);
							json.setMsg("更新商户信息异常！");
							json.setSuccess(false);
						}
					}else{
						json.setMsg("此户处于审核中或审批完成，为不可修改状态");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("MID不存在！");
					json.setSuccess(false);
				}
			}else {
				json.setSuccess(false);
				json.setMsg("数据有误！");
			}
		}
		JsonBeanForSign json1 = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json1.setSign(signs1);
		json1.setData(data);
		super.writeJson(json1);
	}

	public void updateMicroMerchantInfoDataV2(){
		// @author:lxg-20191114 敏感信息加密处理
		try {
			String data=getRequest().getParameter("data");
			String aesEn=getRequest().getParameter("aesEn");
			String sck=CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck,data);
			merchantInfoBean=JSONObject.parseObject(data0,MerchantInfoBean.class);
			merchantInfoBean.setSck(sck);
			merchantInfoBean.setEnc(true);
			log.error("updateMicroMerchantInfoDataV2解密后的请求参数:"+JSON.toJSONString(merchantInfoBean));
            updateMicroMerchantInfo();
		} catch (Exception e) {
			log.error("updateMicroMerchantInfoDataV2解密请求出错:"+e.getMessage());
		}
	}
	
	/**
	 * 更新微商户商户信息
	 */
	public void updateMicroMerchantInfo(){
		JsonBean json = new JsonBean();
//		if(merchantInfoBean.getLegalUploadFile() !=null && merchantInfoBean.getMaterialUpLoadFile() !=null 
//				&& merchantInfoBean.getMaterialUpLoad1File() !=null && merchantInfoBean.getMaterialUpLoad2File() !=null &&  merchantInfoBean.getMaterialUpLoad3File() !=null){
		boolean b = merchantInfoService.queryAge(merchantInfoBean);//年龄限制
		if(b){
			MerchantInfoModel m= phoneMicroMerchantInfoService.queryMicroMerchantInfo(merchantInfoBean.getMid());
			if(m!=null){
				if(!"Y".equals(m.getApproveStatus()) && !"C".equals(m.getApproveStatus())){
					try {
						phoneMicroMerchantInfoService.updateMicroMerchantInfoZWK(m,merchantInfoBean);
						json.setMsg("更新成功");
						json.setSuccess(true);
					} catch (Exception e) {
						log.error("更新信息异常", e);
						json.setMsg("更新商户信息异常！");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("此户处于审核中或审批完成，为不可修改状态");
					json.setSuccess(false);
				}
			}else{
				json.setMsg("MID不存在！");
				json.setSuccess(false);
			}
//		}else{
//			json.setMsg("上传图片数量不完整！");
//			json.setSuccess(false);
//		}
		}else{
			json.setSuccess(false);
			json.setMsg("商户入网年龄要求18-60岁！");
		}
		log.info("更新微商户商户信息：mid="+merchantInfoBean.getMid()+";返回="+json.getMsg());
//		super.writeJson(json);
		// @author:lxg-20191114 敏感信息加密处理
		super.writeJson(CommonTools.jsonBeanToString(json,merchantInfoBean.isEnc(),merchantInfoBean.getSck()));
	}
	
	/**
	 * 立马富补充材料-更新商户商户信息
	 */
	public void updateLMFMerchantInfo(){
		JsonBean json = new JsonBean();
		MerchantInfoModel m= phoneMicroMerchantInfoService.queryMicroMerchantInfo(merchantInfoBean.getMid());
		if(m!=null){
			if(!"Z".equals(m.getApproveStatus())){
				try {
					phoneMicroMerchantInfoService.updateMicroMerchantInfoZWK(m,merchantInfoBean);
					json.setMsg("更新成功");
					json.setSuccess(true);
				} catch (Exception e) {
					log.error("更新信息异常", e);
					json.setMsg("更新商户信息异常！");
					json.setSuccess(false);
				}
			}else{
				json.setMsg("此户不可更新材料");
				json.setSuccess(false);
			}
		}else{
			json.setMsg("MID不存在！");
			json.setSuccess(false);
		}
		log.info("立马富补充材料：mid="+merchantInfoBean.getMid()+";返回="+json.getMsg());
		super.writeJson(json);
	}
	
	/**
	 * 会员宝查询商户-mid&hybPhone
	 */
	public void queryMerchByMid(){
		JsonBean json = new JsonBean();
		try {
			MerchantInfoModel infoModel= phoneMicroMerchantInfoService.queryMicroMerchantInfo(merchantInfoBean.getMid());
			json.setSuccess(false);
			if(infoModel==null){
				json.setMsg("商户号不存在");
			}else if(infoModel.getHybPhone()==null||"".equals(infoModel.getHybPhone())){
				json.setMsg("手机号不存在");
			}else if (infoModel.getHybPhone().equals(merchantInfoBean.getHybPhone())){
				json.setMsg("商户与手机号匹配");
				json.setSuccess(true);
			}else{
				json.setMsg("手机号不匹配");
			}
			log.info("会员宝查询商户：入参mid="+merchantInfoBean.getMid()+";入参hybPhone="+merchantInfoBean.getHybPhone()+";返回="+json.getMsg());
		} catch (Exception e) {
			log.error("会员宝查询商户mid&hybPhone异常：" + e);
			json.setMsg("查询失败!");
		}
		super.writeJson(json);
	}
	
	/**
	 *1、app添加信用卡接口 mid，银行卡号（加密）、银行预留手机号（加密） 参数：mid,accno,phone
	 */
	public void addQKPayCard(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		String accno=super.getRequest().getParameter("accno");
		String phone=super.getRequest().getParameter("phone");
		try {
			Map<String ,Object>  map = phoneMicroMerchantInfoService.addQKPayCard(mid,accno,phone);
			json.setSuccess(true);
			json.setObj(map);
			json.setMsg("添加信用卡成功！");
		} catch (Exception e) {
			log.error("添加信用卡异常：" + e);
			json.setSuccess(false);
			json.setMsg("添加信用卡失败！");
		}
		super.writeJson(json);
	}
	
	/**
	 * 2、快捷卡管理 上送条件mid， 返回条件 前六后四卡号、主键、银行名称  参数：mid,page,size
	 */
	public void queryQKPayCard(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		String page=super.getRequest().getParameter("page");
		String size=super.getRequest().getParameter("size");
		try {
			Map<String ,Object>  map = phoneMicroMerchantInfoService.queryQKPayCard(mid,page,size);
			json.setSuccess(true);
			json.setObj(map);
			json.setMsg("查询快捷卡管理成功！");
		} catch (Exception e) {
			log.error("查询快捷卡管理异常：" + e);
			json.setSuccess(false);
			json.setMsg("查询快捷卡管理失败！");
		}
		super.writeJson(json);
	}
	
	/**
	 * 3、解除绑定  上送条件mid，主键 参数：mid,qpcid
	 */
	public void unBQKPayCard(){
		JsonBean json = new JsonBean();
		String mid=super.getRequest().getParameter("mid");
		String qpcid=super.getRequest().getParameter("qpcid");
		try {
			Map<String ,Object>  map = phoneMicroMerchantInfoService.unBQKPayCard(mid,qpcid);
			json.setSuccess(true);
			json.setObj(map);
			json.setMsg("解除绑定成功！");
		} catch (Exception e) {
			log.error("解除绑定异常：" + e);
			json.setSuccess(false);
			json.setMsg("解除绑定失败！");
		}
		super.writeJson(json);
	}
	
	/**
	 * 4、判断选择卡是否支持积分通道？ 上送条件卡号前六后四 参数：accno
	 */
	public void isIntegral(){
		JsonBean json = new JsonBean();
		String accno=super.getRequest().getParameter("accno");
		String type=super.getRequest().getParameter("type");
		try {
			Map<String ,Object>  map = phoneMicroMerchantInfoService.isIntegral(accno,type);
			json.setSuccess(true);
			json.setObj(map);
			json.setMsg("查询是否支持积分成功！");
		} catch (Exception e) {
			log.error("查询是否支持积分异常：" + e);
			json.setSuccess(false);
			json.setMsg("查询是否支持积分失败！");
		}
		super.writeJson(json);
	}
	
	/**
	 * 德古拉手机端自助报单
	 */
	public void addMicroMerchantInfoDGL(){
		JsonBean json = new JsonBean();
		//判断手刷商户编号总数量是否已满！
		boolean flag1=merchantInfoService.queryMicroMerchantCount();
		if(flag1){
			List<Map<String, Object>> list;
			try {
				list = phoneMicroMerchantInfoService.saveHrtMerchantInfoDGL(merchantInfoBean);
				if(list!=null){
					phoneMicroMerchantInfoService.hrtMerchantToDGL((MerchantInfoModel)list.get(0).get("merchantInfoModel"),merchantInfoBean);
					json.setObj(list);
					json.setSuccess(true);
					json.setMsg("注册成功"); 
				}else{
					json.setSuccess(false);
					json.setMsg("注册失败");
				}
			} catch (Exception e) {
				json.setSuccess(false);
				json.setMsg("注册失败");
				e.printStackTrace();
			}
		}else{
			json.setSuccess(false);
			json.setMsg("商户编号生成错误，请稍后重试！");
		}
		super.writeJson(json);
	}
	
	/**
	 * 手机号查询德古拉商户信息
	 */
	public void queryMerchantInfoDGL(){
		JsonBean json = new JsonBean();
		if(merchantInfoBean.getHybPhone()==null||"".equals(merchantInfoBean.getHybPhone())){
			json.setMsg("手机号不能为空！");
			json.setSuccess(false);
			return ;
		}
		MerchantInfoModel m= phoneMicroMerchantInfoService.queryMerchantInfoDGL(merchantInfoBean.getHybPhone());
		if(m!=null){
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("rname",m.getRname());
			map.put("bankAccName", m.getBankAccName());
			map.put("bankAccNo", m.getBankAccNo());
			map.put("legalNum", m.getLegalNum());
			map.put("contactPhone", "");
			map.put("mid", m.getMid());
			map.put("accType", m.getAccType());
			map.put("payBankId", m.getPayBankId());
			map.put("legalPerson", m.getLegalPerson());
			map.put("contactPerson", m.getContactPerson());
			map.put("approveStatus", m.getApproveStatus());
			map.put("bankBranch", m.getBankBranch());
			map.put("joinConfirmDate", m.getJoinConfirmDate());
//			map.put("bankBranch", m.getBankBranch().substring(0, m.getBankBranch().indexOf("行")+1));
			list.add(map);
			json.setObj(list);
			json.setMsg("手机号已报单！");
			json.setSuccess(true);
		}else{
			json.setMsg("手机号未报单！");
			json.setSuccess(false);
		}
		log.info("手机号查询德古拉商户信息：hybPhone="+merchantInfoBean.getHybPhone()+";返回="+json.getMsg());
		super.writeJson(json);
	}
	
	/**
	 * 德古拉更新商户商户信息
	 */
	public void updateLMFMerchantInfoDGL(){
		JsonBean json = new JsonBean();
		MerchantInfoModel m= phoneMicroMerchantInfoService.queryMicroMerchantInfo(merchantInfoBean.getMid());
		if(m!=null){
			if("Z".equals(m.getApproveStatus())){
				try {
					phoneMicroMerchantInfoService.updateMicroMerchantInfoDGL(m,merchantInfoBean);
					json.setMsg("更新成功");
					json.setSuccess(true);
				} catch (Exception e) {
					log.error("更新信息异常", e);
					json.setMsg("更新商户信息异常！");
					json.setSuccess(false);
				}
			}else{
				json.setMsg("此户不可更新材料");
				json.setSuccess(false);
			}
		}else{
			json.setMsg("MID不存在！");
			json.setSuccess(false);
		}
		log.info("德古拉更新材料：mid="+merchantInfoBean.getMid()+";返回="+json.getMsg());
		super.writeJson(json);
	}
	
	/**
	 * 德古拉手机端增机申请
	 */
	public void addMerchantTermianalInfoDGL(){
		JsonBean json = new JsonBean();
		boolean flag3=phoneMicroMerchantInfoService.findMidInfo(merchantInfoBean.getMid());
		if(flag3){
			List<Map<String,String>> list = phoneMicroMerchantInfoService.saveMerchantTermialInfoDGL(merchantInfoBean);
			if(list==null){
				json.setSuccess(false);
				json.setMsg("增机申请失败！"); 
			}else{
				if(list.get(0).containsKey("msg")){
					json.setSuccess(false);
					json.setMsg(list.get(0).get("msg")); 
				}else{
					json.setSuccess(true);
					json.setMsg("增机申请成功！"); 
				}
			}
		}else{
			json.setMsg("MID不存在,请联系销售！");
			json.setSuccess(false);
		}
		super.writeJson(json);
	}
	/**
	 * 连刷返回行业类别
	 */
	public void listAgentMccid(){
		JsonBean json = new JsonBean();
		List<Object> list = phoneMicroMerchantInfoService.listAgentMccid();
		if(list==null){
			json.setSuccess(false);
			json.setMsg("查询行业类别失败！"); 
		}else{
			json.setSuccess(true);
			json.setMsg("查询行业类别成功！"); 
			json.setList(list); 
		}
		super.writeJson(json);
	}
	
	/**
	 * 大额扫码设定默认设备
	 */
	public void updateSelectSn(){
		JsonBean jsonBean = new JsonBean();
		if (merchantInfoBean.getMid() == null ||"".equals(merchantInfoBean.getMid())||merchantInfoBean.getSn() == null ||"".equals(merchantInfoBean.getSn())) {
			jsonBean.setMsg("商户号或SN不能为空");
			jsonBean.setSuccess(false);
		}else {
			try{
				jsonBean = phoneMicroMerchantInfoService.updateSelectSn(merchantInfoBean);
			} catch (Exception e) {
				jsonBean.setSuccess(false);
				jsonBean.setMsg("设定默认设备失败!");
				log.info("设定默认设备失败"+e);
			}
		}
		super.writeJson(jsonBean);
	}

	/**
	 * sn交易量查询
	 */
	public void querySnSumsamt(){
		JsonBean jsonBean = new JsonBean();
		if (merchantInfoBean.getMid() == null ||"".equals(merchantInfoBean.getMid())||merchantInfoBean.getSn() == null ||"".equals(merchantInfoBean.getSn())) {
			jsonBean.setMsg("商户号或SN不能为空");
			jsonBean.setSuccess(false);
		}else {
			log.info("sn交易量查询请求参数mid="+merchantInfoBean.getMid()+",sn="+merchantInfoBean.getSn());
			try{
				jsonBean = phoneMicroMerchantInfoService.querySnSumsamt(merchantInfoBean);
			} catch (Exception e) {
				jsonBean.setSuccess(false);
				jsonBean.setMsg("查询sn交易量失败!");
				log.info("查询sn交易量失败"+e);
			}
		}
		log.info("sn交易量查询返回参数"+ JSONObject.toJSONString(jsonBean));
		super.writeJson(jsonBean);
	}
	
	/**
	 * 查询激活返现记录
	 */
	public void findReturnRecord(){
		JsonBean jsonBean = new JsonBean();
		if (merchantInfoBean.getMid() == null ||"".equals(merchantInfoBean.getMid())||merchantInfoBean.getSn() == null ||"".equals(merchantInfoBean.getSn())
			||merchantInfoBean.getPage() == null ||"".equals(merchantInfoBean.getPage())||merchantInfoBean.getRows() == null ||"".equals(merchantInfoBean.getRows())) {
			jsonBean.setMsg("参数缺失");
			jsonBean.setSuccess(false);
		}else {
			try{
				jsonBean = phoneMicroMerchantInfoService.findReturnRecord(merchantInfoBean);
			} catch (Exception e) {
				jsonBean.setSuccess(false);
				jsonBean.setMsg("设定默认设备失败!");
				log.info("设定默认设备失败"+e);
			}
		}
		super.writeJson(jsonBean);
	}

	/**
	 * 交易限额
	 */
	public void limitForTerms(){
		JsonBean jsonBean = new JsonBean();
		if(StringUtils.isEmpty(merchantInfoBean.getAgentId()) || null==merchantInfoBean.getAuthType()){
			jsonBean.setMsg("参数缺失");
			jsonBean.setSuccess(false);
		}else{
			jsonBean.setObj(1);
			jsonBean.setSuccess(true);
//			try {
//				String tipInfo = phoneMicroMerchantInfoService.limitForTerms(merchantInfoBean.getAuthType(), merchantInfoBean.getAgentId());
//				if (StringUtils.isNotEmpty(tipInfo)) {
//					jsonBean.setObj(0);
//					jsonBean.setMsg(tipInfo);
//					jsonBean.setSuccess(true);
//				} else {
//					jsonBean.setObj(1);
//					jsonBean.setMsg(tipInfo);
//					jsonBean.setSuccess(true);
//				}
//			}catch (Exception e){
//                jsonBean.setObj(1);
//				jsonBean.setSuccess(true);
////				jsonBean.setMsg("查询交易限额失败!");
//				log.info("查询资金池交易限额失败异常"+e);
//			}
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 花呗分期
	 */
	public void huaBeiParam(){
		JsonBean jsonBean = new JsonBean();
		if(StringUtils.isEmpty(super.getRequest().getParameter("sign")) 
				||StringUtils.isEmpty(super.getRequest().getParameter("mid")) ){
			jsonBean.setMsg("参数缺失");
			jsonBean.setSuccess(false);
		}else{
			Map<String,Object> map = new HashMap<String, Object>();
			try {
				String sign = super.getRequest().getParameter("sign");
				String mid = super.getRequest().getParameter("mid");
				String md5 = MD5Util.MD5(("mid="+mid+ParamPropUtils.props.getProperty("signEnd")));
				log.info("上传签名："+sign+",本地签名："+md5);
				if(md5.equals(sign)){
					map = phoneMicroMerchantInfoService.huaBeiParam();
					jsonBean.setObj(map);
					jsonBean.setSuccess(true);
					jsonBean.setMsg("请求成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("签名失败");
				}
			}catch (Exception e){
				map.put("isOff", 0);
				jsonBean.setSuccess(true);
				jsonBean.setObj(map);
				jsonBean.setMsg("获取花呗参数异常");
				log.info("花呗分期请求接口异常"+e);
			}
		}
		super.writeJson(jsonBean);
	}
	/**
	 * 花呗分期-MD\SYT
	 */
	public void huaBeiParamInfo(){
		JsonBean jsonBean = new JsonBean();
		if(StringUtils.isEmpty(super.getRequest().getParameter("sign")) 
				||StringUtils.isEmpty(super.getRequest().getParameter("mid"))
				||StringUtils.isEmpty(super.getRequest().getParameter("productType"))){
			jsonBean.setMsg("参数缺失");
			jsonBean.setSuccess(false);
		}else{
			Map<String,Object> map = new HashMap<String, Object>();
			try {
				String sign = super.getRequest().getParameter("sign");
				String mid = super.getRequest().getParameter("mid");
				String productType = super.getRequest().getParameter("productType");
				String md5 = MD5Util.MD5(("mid="+mid+ParamPropUtils.props.getProperty("signEnd")));
				log.info("上传签名："+sign+",本地签名："+md5);
				if(md5.equals(sign)){
					map = phoneMicroMerchantInfoService.huaBeiParamInfo(productType);
					if(map.isEmpty()) {
						jsonBean.setSuccess(false);
						jsonBean.setMsg("签名失败,产品类型不正确");
						super.writeJson(jsonBean);
					}
					jsonBean.setObj(map);
					jsonBean.setSuccess(true);
					jsonBean.setMsg("请求成功");
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("签名失败");
				}
			}catch (Exception e){
				map.put("isOff", 0);
				jsonBean.setSuccess(true);
				jsonBean.setObj(map);
				jsonBean.setMsg("获取花呗参数异常");
				log.info("花呗分期请求接口异常"+e);
			}
		}
		super.writeJson(jsonBean);
	}

	/**
	 * sn收签日期添加
	 */
	public void modifyTermAcceptDate(){
		JsonBean jsonBean = new JsonBean();
		try {
			if(merchantInfoBean.getApiVersion()!=null
					&& merchantInfoBean.getSn()!=null && merchantInfoBean.getApproveDate()!=null){
				log.error(String.format("sn收签日期添加请求参数:apiVersion=%s,sn=%s,approveDate=%s",
						merchantInfoBean.getApiVersion(),merchantInfoBean.getSn(),merchantInfoBean.getApproveDate().toString()));
				String md5=MD5Util.MD5(Constant.SN_PUSH_ACCEPTDATE_KEY+merchantInfoBean.getSn());
				if(md5!=null && md5.equals(merchantInfoBean.getApiVersion())){
					String errMsg = phoneMicroMerchantInfoService.updateTermAcceptDate(merchantInfoBean.getSn(),merchantInfoBean.getApproveDate());
					if(StringUtils.isNotEmpty(errMsg)){
						jsonBean.setSuccess(false);
						jsonBean.setMsg(errMsg);
					}else{
						jsonBean.setSuccess(true);
						jsonBean.setMsg("处理成功");
					}
				}else{
					jsonBean.setSuccess(false);
					jsonBean.setMsg("参数信息key不正确");
				}
			}else{
				jsonBean.setSuccess(false);
				jsonBean.setMsg("缺少参数信息");
			}
		}catch (Exception e){
			log.error("修改终端接收日期异常:"+e.getMessage());
			jsonBean.setSuccess(false);
			jsonBean.setMsg("处理异常");
		}
		log.error("sn收签日期添加返回参数:"+JSONObject.toJSONString(jsonBean));
		super.writeJson(jsonBean);
	}
}
