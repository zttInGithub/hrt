package com.hrt.biz.bill.action;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayUserCertifyOpenCertifyRequest;
import com.alipay.api.request.AlipayUserCertifyOpenInitializeRequest;
import com.alipay.api.request.AlipayUserCertifyOpenQueryRequest;
import com.alipay.api.response.AlipayUserCertifyOpenCertifyResponse;
import com.alipay.api.response.AlipayUserCertifyOpenInitializeResponse;
import com.alipay.api.response.AlipayUserCertifyOpenQueryResponse;
import com.hrt.biz.util.CommonTools;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.hrt.biz.bill.entity.model.MerchantAuthenticityModel;
import com.hrt.biz.bill.entity.pagebean.MerchantAuthenticityBean;
import com.hrt.biz.bill.service.IMerchantAuthenticityService;
import com.hrt.biz.util.gateway.MD5Util;
import com.hrt.biz.util.unionpay.HrtRSAUtil;
import com.hrt.biz.util.unionpay.SHAEncUtil;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.JsonBeanForSign;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.phone.service.IPhoneWechatPublicAccService;
import com.hrt.redis.RedisUtil;
import com.hrt.util.JxlOutExcelUtil;
import com.hrt.util.ParamPropUtils;
import com.hrt.util.SimpleXmlUtil;
import com.opensymphony.xwork2.ModelDriven;

import redis.clients.jedis.ShardedJedis;

/**
 * 描述: 商户实名认证 作者: xxx 创建日期: 2016-02-18
 */
public class MerchantAuthenticityAction extends BaseAction implements ModelDriven<MerchantAuthenticityBean> {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(MerchantAuthenticityAction.class);

	private MerchantAuthenticityBean matb = new MerchantAuthenticityBean();
	private IMerchantAuthenticityService merchantAuthenticityService;
	private IPhoneWechatPublicAccService phoneWechatPublicAccService;

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
	public MerchantAuthenticityBean getModel() {
		return matb;
	}

	/**
	 * 查询商户认证信息（未/已认证）
	 */
	public void listMerchantAuthenticity() {
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			matb.setAuthType("MER");
			dgb = merchantAuthenticityService.queryMerchantAuthenticity(matb, ((UserBean) userSession));
		} catch (Exception e) {
			log.error("分页查询商户认证信息异常：" + e);
		}
		super.writeJson(dgb);
	}

	/**
	 * 查询交易认证
	 */
	public void listTxnAuthenticity() {
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			matb.setAuthType("TXN");
			dgb = merchantAuthenticityService.queryTxnAuthenticity(matb, ((UserBean) userSession));
		} catch (Exception e) {
			log.error("分页查询交易认证信息异常：" + e);
		}
		super.writeJson(dgb);
	}

	/**
	 * 人工修改交易认证信息(通过/退回)
	 */
	public void editTxnAuthenticity() {
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			matb.setAuthType("TXN");
			matb.setCdate(new Date());
			// matb.setApproveNote(new
			// String(this.getRequest().getParameter("approveNote").getBytes("GB2312")));
			boolean editSuccess = false;
			String flag = super.getRequest().getParameter("flag");
			if (flag == "go" || "go".endsWith(flag)) {
				matb.setStatus("00");
				editSuccess = merchantAuthenticityService.updateTxnAuthenticityGo(matb, (UserBean) userSession);
				if (editSuccess) {
					json.setSuccess(true);
					json.setMsg("人工认证成功");
				} else {
					json.setSuccess(false);
					json.setMsg("人工认证失败");
				}
			} else if (flag == "back" || "back".endsWith(flag)) {
				matb.setStatus("01");
				editSuccess = merchantAuthenticityService.updateTxnAuthenticityBack(matb, (UserBean) userSession);
				if (editSuccess) {
					json.setSuccess(true);
					json.setMsg("退回成功");
				} else {
					json.setSuccess(false);
					json.setMsg("退回失败");
				}
			} else {
				throw new Exception("传参错误");
			}
		} catch (Exception e) {
			log.error("修改交易认证信息异常：" + e);
			json.setSuccess(false);
			json.setMsg("操作异常");
		}
		super.writeJson(json);
	}

	/**
	 * 查看明细(查看 认证失败后 上传的照片)
	 */
	public void serachTxnAuthInfoDetailed() {

		MerchantAuthenticityBean mb = merchantAuthenticityService.queryTxnAuthenticityById(matb.getBmatid());
		super.writeJson(mb);
	}

	/**
	 * 商户重新认证信息
	 */
	public void editMerchantAuthenticity() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		// session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				DataGridBean dgb = new DataGridBean();
				dgb = merchantAuthenticityService.queryMerchantAuthenticity(matb, ((UserBean) userSession));
				if (dgb.getRows().size() >= 1) {
					matb = (MerchantAuthenticityBean) dgb.getRows().get(0);
				}
				// 认证
				Map<String, String> result = merchantAuthenticityService.addMerchantAuthInfo(matb);
				String msg = result.get("msg");
				String status = result.get("status");
				merchantAuthenticityService.sendResultToHyb(result, matb);
				if ("1".endsWith(status)) {
					json.setSuccess(false);
					json.setMsg("商户认证失败," + msg);
				} else if ("3".endsWith(status)) {
					json.setSuccess(false);
					json.setMsg("商户交易异常," + msg);
				} else if ("2".endsWith(status)) {
					json.setSuccess(true);
					json.setMsg("商户认证成功," + msg);
				}
				merchantAuthenticityService.updatemMerchantAuthenticity(matb, ((UserBean) userSession));
			} catch (Exception e) {
				log.error("商户认证异常" + e);
				json.setMsg("商户认证失败");
			}
		}
		super.writeJson(json);
	}

	/**
	 * 解密商户认证信息
	 */
	public void decMerchantAuthInfo() {
		JsonBean json = new JsonBean();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"),
				super.getRequest().getParameter("timeStamp"));
		if (!b) {
			json.setSuccess(false);
			json.setMsg("数据有误！");
		} else {
			String username = matb.getUsername();
			String bankAccName = matb.getBankAccName();
			String accnoExpdate = matb.getAccnoExpdate();
			String cardName = matb.getCardName();

			String sign = super.getRequest().getParameter("sign");
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("username", username);
			map1.put("bankAccName", bankAccName);
			map1.put("cardName", cardName);
			String signEnd = "&key=dseesa325errtcyraswert749errtdyt";
			String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1) + signEnd));
			if (md5.equals(sign)) {
				String legalNum = matb.getLegalNum();
				String bankAccNo = matb.getBankAccNo();
				String decrypt = HrtRSAUtil.decryptWithBase64(legalNum);
				String decrypt2 = HrtRSAUtil.decryptWithBase64(bankAccNo);
				String decrypt3 = HrtRSAUtil.decryptWithBase64(accnoExpdate);
				if (decrypt != null && !"".equals(decrypt) && decrypt2 != null && !"".equals(decrypt2)
						&& decrypt3 != null && !"".equals(decrypt3)) {
					matb.setLegalNum(decrypt);
					matb.setBankAccNo(decrypt2);
					matb.setAccnoExpdate(decrypt3);
					matb.setAuthType("MER");
					if (matb.getMid() != null && !"".equals(matb.getMid()) && matb.getBankAccNo() != null
							&& !"".equals(matb.getBankAccNo()) && matb.getBankAccName() != null
							&& !"".equals(matb.getBankAccName()) && matb.getLegalNum() != null
							&& !"".equals(matb.getLegalNum())) {
						log.info("认证接口返回信息（商户）：mid:" + matb.getMid() + ";no:" + matb.getBankAccNo() + ";name:"
								+ matb.getBankAccName() + ";lum:" + matb.getLegalNum());
						// 验证银行卡是否符合规则
						Integer ruleCount = merchantAuthenticityService.queryCardRule(matb);
						if (ruleCount > 0) {
							// 过滤掉相同：卡号，身份证号，入账人名称 已经失败的认证。
							Integer noCount = merchantAuthenticityService.queryHasFailMerCount(matb);
							if (noCount < 1) {
								// 之前认证成功
								Integer yesCount = merchantAuthenticityService.queryMerAuthYes(matb);
								if (yesCount < 1) {
									// 查询实名验证次数
									Integer count = merchantAuthenticityService.queryMerchantAuthCount(matb);
									if (count < 3) {
										try {
											matb.setLegalNum(matb.getLegalNum().toUpperCase());
											Map<String, String> map = merchantAuthenticityService
													.addMerchantAuthInfo(matb);
											merchantAuthenticityService.sendResultToHyb(map, matb);
											if ("2".equals(map.get("status"))) {
												json.setSuccess(true);
											} else {
												json.setSuccess(false);
											}
											json.setMsg(map.get("msg"));
										} catch (Exception e) {
											log.error(e);
											json.setSuccess(false);
											json.setMsg("请求认证异常！");
										}
									} else {
										json.setSuccess(false);
										json.setMsg("三次认证已使用完毕，不可再次认证！");
									}
								} else {
									matb.setCdate(new Date());
									matb.setRespcd("2000");
									matb.setStatus("00");
									matb.setRespinfo("认证成功");
									try {
										merchantAuthenticityService.saveMerchantAuthInfo(matb);
									} catch (Exception e) {
										json.setMsg("认证失败(异常)!");
										log.error(e);
									}
									Map<String, String> result = new HashMap<String, String>();
									result.put("userName", matb.getUsername());
									result.put("mid", matb.getMid());
									result.put("msg", "认证成功");
									result.put("status", "2");
									merchantAuthenticityService.sendResultToHyb(result, matb);
									json.setSuccess(true);
									json.setMsg("该户已经认证成功，不需再次认证！");
								}
							} else {
								// 添加认证信息(商户可能多次保单，产生多个mid，导致之后认证被滤掉,查不到信息)
								try {
									matb.setAuthType("MER");
									// 如果两次失败mid相同，不需要存/mid不相同，则存
									String oldMid = merchantAuthenticityService.queryHasFailMerMid(matb);
									if (!matb.getMid().equals(oldMid)) {
										matb.setCdate(new Date());
										matb.setRespcd("2001");
										matb.setStatus("00");
										matb.setRespinfo("认证失败！信息商户认证已失败");
										Map<String, String> map = merchantAuthenticityService
												.saveMerchantAuthInfo(matb);
										map.put("msg", "认证失败！");
										map.put("status", "1");
										merchantAuthenticityService.sendResultToHyb(map, matb);
									}
									json.setMsg("认证失败！请联系人工认证");
								} catch (Exception e) {
									json.setMsg("认证失败(异常)!");
									log.error(e);
								}
								json.setSuccess(false);
							}
						} else {
							json.setSuccess(false);
							json.setMsg("请使用贷记卡进行认证！");
						}
					} else {
						json.setSuccess(false);
						json.setMsg("参数有空值，请核对参数！");
					}
				} else {
					json.setSuccess(false);
					json.setMsg("参数有空值，请核对参数！");
					super.writeJson(json);
				}
			} else {
				json.setSuccess(false);
				json.setMsg("数据有误！");
				super.writeJson(json);
			}
		}
		JsonBeanForSign json1 = new JsonBeanForSign();
		String data = JSON.toJSONStringWithDateFormat(json, "yyyy-MM-dd HH:mm:ss");
		String signs1 = SHAEncUtil.getSHA256Str(data);
		json1.setSign(signs1);
		json1.setData(data);
		super.writeJson(json1);
	}

	public void addMerchantAuthInfoDataV2() {
		// @author:lxg-20191114 敏感信息加密处理
		try {
			String data = getRequest().getParameter("data");
			String aesEn = getRequest().getParameter("aesEn");
			String sck = CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck, data);
			matb = JSONObject.parseObject(data0, MerchantAuthenticityBean.class);
			matb.setSck(sck);
			matb.setEnc(true);
			log.error("addMerchantAuthInfoDataV2解密后的请求参数:" + JSON.toJSONString(matb));
			addMerchantAuthInfo();
		} catch (Exception e) {
			log.error("addMerchantAuthInfoDataV2解密请求出错:" + e.getMessage());
		}
	}

	public void addMerchantAuthInfo() {
		JsonBean json = new JsonBean();
		matb.setAuthType("MER");
		if (matb.getMid() != null && !"".equals(matb.getMid()) && matb.getBankAccNo() != null
				&& !"".equals(matb.getBankAccNo()) && matb.getBankAccName() != null && !"".equals(matb.getBankAccName())
				&& matb.getLegalNum() != null && !"".equals(matb.getLegalNum())) {
			log.info("认证接口返回信息（商户）：mid:" + matb.getMid() + ";no:" + matb.getBankAccNo() + ";name:"
					+ matb.getBankAccName() + ";lum:" + matb.getLegalNum());
			// 实名认证限制商户
//			boolean flag = merchantAuthenticityService.isTrue(matb);
			boolean flag = true;
			// 查询实名验证次数
			Integer count = merchantAuthenticityService.queryMerchantAuthCount(matb);
			if (flag) {
				// 验证银行卡是否符合规则
				Integer ruleCount = merchantAuthenticityService.queryCardRule(matb);
				if (ruleCount > 0) {
					// 过滤掉相同：卡号，身份证号，入账人名称 已经失败的认证。
					Integer noCount = merchantAuthenticityService.queryHasFailMerCount(matb);
					if (noCount < 1) {
						// 之前认证成功
						Integer yesCount = merchantAuthenticityService.queryMerAuthYes(matb);
						if (yesCount < 1) {
							// 查询实名验证次数
//							Integer count=merchantAuthenticityService.queryMerchantAuthCount(matb);
							if (count < 3) {
								try {
									matb.setLegalNum(matb.getLegalNum().toUpperCase());
									Map<String, String> map = merchantAuthenticityService.addMerchantAuthInfo(matb);
									merchantAuthenticityService.sendResultToHyb(map, matb);
									if ("2".equals(map.get("status"))) {
										json.setSuccess(true);
									} else {
										json.setSuccess(false);
									}
									json.setMsg(map.get("msg"));
								} catch (Exception e) {
									log.error(e);
									json.setSuccess(false);
									json.setMsg("请求认证异常！");
								}
							} else {
								json.setSuccess(false);
								json.setMsg("三次认证已使用完毕，不可再次认证！");
							}
						} else {
							matb.setCdate(new Date());
							matb.setRespcd("2000");
							matb.setStatus("00");
							matb.setRespinfo("认证成功");
							try {
								merchantAuthenticityService.saveMerchantAuthInfo(matb);
							} catch (Exception e) {
								json.setMsg("认证失败(异常)!");
								log.error(e);
							}
							Map<String, String> result = new HashMap<String, String>();
							result.put("userName", matb.getUsername());
							result.put("mid", matb.getMid());
							result.put("msg", "认证成功");
							result.put("status", "2");
							merchantAuthenticityService.sendResultToHyb(result, matb);
							json.setSuccess(true);
							json.setMsg("该户已经认证成功，不需再次认证！");
						}
					} else {
						// 添加认证信息(商户可能多次保单，产生多个mid，导致之后认证被滤掉,查不到信息)
						try {
							matb.setAuthType("MER");
							// 如果两次失败mid相同，不需要存/mid不相同，则存
							String oldMid = merchantAuthenticityService.queryHasFailMerMid(matb);
							if (!matb.getMid().equals(oldMid)) {
								matb.setCdate(new Date());
								matb.setRespcd("2001");
								matb.setStatus("00");
								matb.setRespinfo("认证失败！信息商户认证已失败");
								Map<String, String> map = merchantAuthenticityService.saveMerchantAuthInfo(matb);
								map.put("msg", "认证失败！");
								map.put("status", "1");
								merchantAuthenticityService.sendResultToHyb(map, matb);
							}
							// 产品吕世杰2018-06-14
							json.setMsg("验证信息不一致！");
						} catch (Exception e) {
							json.setMsg("认证失败(异常)!");
							log.error(e);
						}
						json.setSuccess(false);
					}
				} else {
					json.setSuccess(false);
					json.setMsg("请使用贷记卡进行认证！");
				}
			} else {
				// 限制实名认证商户认证
				try {
					if (count < 3) {
						matb.setAuthType("MER");
						matb.setCdate(new Date());
						matb.setRespcd("2001");
						matb.setStatus("00");
						matb.setRespinfo("信息认证失败！");
						Map<String, String> map = merchantAuthenticityService.saveMerchantAuthInfo(matb);
						map.put("msg", "认证失败！");
						map.put("status", "1");
						merchantAuthenticityService.sendResultToHyb(map, matb);
						json.setMsg("信息认证失败！");
					} else {
						json.setMsg("三次认证已使用完毕，不可再次认证！");
					}
				} catch (Exception e) {
					json.setMsg("认证失败(异常)!");
					log.error(e);
				}
				json.setSuccess(false);
			}
		} else {
			json.setSuccess(false);
			json.setMsg("参数有空值，请核对参数！");
		}
//		super.writeJson(json);
		// @author:lxg-20191114 敏感信息加密处理
		super.writeJson(CommonTools.jsonBeanToString(json, matb.isEnc(), matb.getSck()));
	}

	public void addMerchantAuthInfoForECP() {
		JsonBean json = new JsonBean();
		try {
			String ids[] = super.getRequest().getParameter("bmids").split(",");
			String wayName = super.getRequest().getParameter("wayName");
			for (String id : ids) {
				Integer bmtid = Integer.valueOf(id);
				MerchantAuthenticityBean mb = merchantAuthenticityService.queryMerAuthenticityById(bmtid);
				mb.setWayName(wayName);
				mb.setAuthType("MER");
				mb.setAuthUpload(null);
				mb.setAuthUploadFile(null);
				Map<String, String> map = merchantAuthenticityService.addMerchantAuthInfo(mb);
				String status = map.get("status");
				// @author:lxg-20190626 重新发送,认证成功的数据,将原来的自动审批添加审批信息
				if ("2".equals(status)) {
					merchantAuthenticityService.updatemAutoMerchantApproveNote(bmtid);
				}
				merchantAuthenticityService.sendResultToHyb(map, mb);
			}
			json.setSuccess(true);
			json.setMsg("重发请求认证成功");
		} catch (Exception e) {
			log.error(e);
			json.setSuccess(false);
			json.setMsg("重发请求认证异常！");
		}
		super.writeJson(json);
	}

	public void addTxnAuthInfoDataV2() {
		// @author:lxg-20191114 敏感信息加密处理
		try {
			String data = getRequest().getParameter("data");
			String aesEn = getRequest().getParameter("aesEn");
			String sck = CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck, data);
			matb = JSONObject.parseObject(data0, MerchantAuthenticityBean.class);
			matb.setSck(sck);
			matb.setEnc(true);
			log.error("addTxnAuthInfoDataV2解密后的请求参数:" + JSON.toJSONString(matb));
			addTxnAuthInfo();
		} catch (Exception e) {
			log.error("addTxnAuthInfoDataV2解密请求出错:" + e.getMessage());
		}
	}

	/**
	 * 交易认证
	 */
	public void addTxnAuthInfo() {
		JsonBean json = new JsonBean();
		matb.setAuthType("TXN");
		// try {
		// merchantAuthenticityService.addTxnAuthInfo(matb);
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		if (matb.getMid() != null && !"".equals(matb.getMid()) && matb.getBankAccNo() != null
				&& !"".equals(matb.getBankAccNo()) && matb.getBankAccName() != null && !"".equals(matb.getBankAccName())
				&& matb.getLegalNum() != null && !"".equals(matb.getLegalNum())) {
			log.info("认证接口返回信息（交易）：mid:" + matb.getMid() + ";no:" + matb.getBankAccNo() + ";name:"
					+ matb.getBankAccName() + ";lum:" + matb.getLegalNum());
			// 验证银行卡是否符合规则
			Integer ruleCount = merchantAuthenticityService.queryCardRule(matb);
			if (ruleCount > 0) {
				// 查询如果认证通过，直接返回成功
				Integer flag1 = merchantAuthenticityService.queryTxnAuthCountYes(matb);
				if (flag1 < 1) {
					// 查询当天此商户交易验证失败次数
					Integer count = merchantAuthenticityService.queryTxnAuthCount(matb);
					if (count < 3) {
						// 查询交易认证：如果当天同商户同卡同身份证同人认证失败，返回失败
						Integer flag2 = merchantAuthenticityService.queryTxnAuthCountNoToDay(matb);
						if (flag2 < 1) {
							// 查询商户&商户认证：同卡同身份证同人认证失败
							Integer flag3 = merchantAuthenticityService.queryHasFailMerCount(matb);
							if (flag3 < 1) {
								// 查询卡认证成功过,说明之前成功信息和此次信息不一致
								MerchantAuthenticityBean mBank = new MerchantAuthenticityBean();
								mBank.setBankAccNo(matb.getBankAccNo());
								Integer flag4 = merchantAuthenticityService.queryTxnAuthCountYes(mBank);
								if (flag4 < 1) {
									try {
										matb.setLegalNum(matb.getLegalNum().toUpperCase());
										Map<String, String> map = merchantAuthenticityService.addTxnAuthInfo(matb);
										if ("2".equals(map.get("status"))) {
											json.setSuccess(true);
											json.setMsg(map.get("msg"));
										} else {
											json.setSuccess(false);
											json.setMsg(map.get("msg"));
										}
									} catch (Exception e) {
										log.error(e);
										json.setSuccess(false);
										json.setMsg("请求认证异常！");
									}
								} else {
									json.setMsg("认证失败！");
									json.setSuccess(false);
								}
							} else {
								try {// 添加交易失败记录
									matb.setCdate(new Date());
									matb.setRespcd("2001");
									matb.setStatus("00");
									matb.setRespinfo("认证失败！此信息认证已失败");
									merchantAuthenticityService.saveMerchantAuthInfo(matb);
									json.setMsg("认证失败！");
									json.setSuccess(false);
								} catch (Exception e) {
									json.setMsg("认证失败(异常)!");
									log.error(e);
								}
							}
						} else {
							json.setSuccess(false);
							json.setMsg("认证失败！");
						}
					} else {
						json.setSuccess(false);
						json.setMsg("商户存在风险，禁止交易！");
					}
				} else {
					// 记录卡片认证通过在不同商户下的信息
					try {
						matb.setAuthType("TXN");
						Integer count = merchantAuthenticityService.queryTxnAuthCountYesWithMid(matb);
						if (count == 0) {
							matb.setCdate(new Date());
							matb.setRespcd("2000");
							matb.setStatus("00");
							matb.setRespinfo("认证成功！");
							Map<String, String> map = merchantAuthenticityService.saveMerchantAuthInfo(matb);
						} else {
							// @author:lxg-20190517 查询出认证成功的数据推送给综合
							MerchantAuthenticityModel ok = merchantAuthenticityService
									.queryMerchantAuthenticitySuccess(matb);
							merchantAuthenticityService.pushADMreceiveRepayBD(ok, null);
						}
					} catch (Exception e) {
						log.error("交易认证异常2" + e);
					}
					json.setSuccess(true);
					json.setMsg("认证成功！");
				}
			} else if (ruleCount == -1) {// 交易认证,白名单商户 卡bin不存在
				json.setSuccess(false);
				json.setMsg("无效卡！");
			} else {
				// APP判断msg="请使用贷记卡！"，不在执行上传图片
				json.setSuccess(false);
				json.setMsg("请使用贷记卡！");
			}
		} else {
			json.setSuccess(false);
			json.setMsg("参数有空值，请核对参数！");
		}
//		super.writeJson(json);
		// @author:lxg-20191114 敏感信息加密处理
		super.writeJson(CommonTools.jsonBeanToString(json, matb.isEnc(), matb.getSck()));
	}

	/**
	 * 交易认证(对外)
	 */
	public void hrtAddTxnAuthInfo() {
		JsonBean result = new JsonBean();
		matb.setAuthType("HRT");

		// merchantAuthenticityService.querySignAuth(matb);

		if (matb.getMid() != null && !"".equals(matb.getMid()) && matb.getUnno() != null && !"".equals(matb.getUnno())
				&& matb.getBankAccNo() != null && !"".equals(matb.getBankAccNo()) && matb.getBankAccName() != null
				&& !"".equals(matb.getBankAccName()) && matb.getLegalNum() != null && !"".equals(matb.getLegalNum())
				&& matb.getSign() != null && !"".equals(matb.getSign()) && matb.getSessionId() != null
				&& !"".equals(matb.getSessionId())) {
			// sign验证
			log.info("第三接口返回信息（对外）：mid:" + matb.getMid() + ";unno:" + matb.getUnno() + ";no:" + matb.getBankAccNo()
					+ ";name:" + matb.getBankAccName() + ";lum:" + matb.getLegalNum() + ";sign:" + matb.getSign()
					+ ";phoneNo:" + matb.getPhoneNo());
			Integer signAuth = merchantAuthenticityService.querySignAuth(matb);
			if (signAuth > 0) {
				try {
//					matb.setLegalNum(matb.getLegalNum().toUpperCase());
					Map<String, String> map = merchantAuthenticityService.addTxnAuthInfoForYBF(matb);
					if ("2".equals(map.get("status"))) {
						result.setSuccess(true);
						result.setMsg(map.get("msg"));
						result.setObj(map.get("sessionId"));
					} else {
						result.setSuccess(false);
						result.setMsg(map.get("msg"));
						result.setObj(map.get("sessionId"));
					}
				} catch (Exception e) {
					log.error(e);
					result.setSuccess(false);
					result.setMsg("认证失败！");
					result.setObj(matb.getSessionId());
				}
			} else {
				result.setSuccess(false);
				result.setMsg("网络传输异常");
				result.setObj(matb.getSessionId());
			}
		} else {
			result.setSuccess(false);
			result.setMsg("参数有空值，请核对参数！");
			result.setObj(matb.getSessionId());
			log.info("第三接口返回信息（有空值）：mid:" + matb.getMid() + "   unno:" + matb.getUnno() + "   no:" + matb.getBankAccNo()
					+ "   name:" + matb.getBankAccName() + "  lun:" + matb.getLegalNum() + "  sign:" + matb.getSign());
		}
		result.setObj(matb.getSessionId());
		super.writeJson(result);
	}

	/**
	 * 交易认证,查询在此商户下此卡是否认证成功过？
	 */
	public void queryAuthInfoByBankAccNo() {
		JsonBean json = new JsonBean();
		matb.setAuthType("TXN");
		if (matb.getBankAccNo() != null && !"".equals(matb.getBankAccNo()) && matb.getMid() != null
				&& !"".equals(matb.getMid())) {
			// 通过卡号查询是否认证过
			Integer ifSuccess = merchantAuthenticityService.queryTxnAuthCountYesWithMid(matb);
			if (ifSuccess > 0) {
				json.setMsg("此卡认证成功过!");
				json.setSuccess(true);
			} else {
				json.setMsg("此卡没有认证成功过！");
				json.setSuccess(false);
			}
		} else {
			json.setSuccess(false);
			json.setMsg("参数有空值，请核对参数！");
		}
		super.writeJson(json);
	}

	/**
	 * 商户认证,查询在商户是否存在待审的记录？
	 */
	public void queryAuthInfoByMid() {
		JsonBean json = new JsonBean();
		if (matb.getMid() != null && !"".equals(matb.getMid())) {
			Integer ifSuccess = merchantAuthenticityService.queryAuthInfoByMid(matb);
			if (ifSuccess > 0) {
				json.setMsg("认证中!");
				json.setSuccess(true);
			} else if (ifSuccess < 0) {
				json.setMsg("已通过!");
				json.setObj(1);
				json.setSuccess(false);
			} else {
				json.setMsg("待申请!");
				json.setSuccess(false);
			}
		} else {
			json.setSuccess(false);
			json.setMsg("参数有空值，请核对参数！");
		}
		super.writeJson(json);
	}

	/**
	 * 加密商户认证,查询在商户是否存在待审的记录？
	 */
	public void decQueryAuthInfoByMid() {
		JsonBean json = new JsonBean();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"),
				super.getRequest().getParameter("timeStamp"));
		if (!b) {
			json.setSuccess(false);
			json.setMsg("数据有误！");
		} else {
			String mid = super.getRequest().getParameter("mid");
			String sign = super.getRequest().getParameter("sign");
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("mid", mid);
			String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map1) + "&key=dseesa325errtcyraswert749errtdyt"));
			if (md5.equals(sign)) {
				if (matb.getMid() != null && !"".equals(matb.getMid())) {
					Integer ifSuccess = merchantAuthenticityService.queryAuthInfoByMid(matb);
					if (ifSuccess > 0) {
						json.setMsg("认证中!");
						json.setSuccess(true);
					} else {
						json.setMsg("待申请!");
						json.setSuccess(false);
					}
				} else {
					json.setSuccess(false);
					json.setMsg("参数有空值，请核对参数！");
				}
			} else {
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

	public void uploadPicWhenTxnAuthFalseDataV2() {
		// @author:lxg-20191114 敏感信息加密处理
		try {
			File file = matb.getAuthUploadFile();
			String data = getRequest().getParameter("data");
			String aesEn = getRequest().getParameter("aesEn");
			String sck = CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck, data);
			matb = JSONObject.parseObject(data0, MerchantAuthenticityBean.class);
			matb.setSck(sck);
			matb.setAuthUploadFile(file);
			matb.setEnc(true);
			log.error("uploadPicWhenTxnAuthFalseDataV2解密后的请求参数:" + JSON.toJSONString(matb));
			uploadPicWhenTxnAuthFalse();
		} catch (Exception e) {
			log.error("uploadPicWhenTxnAuthFalseDataV2解密请求出错:" + e.getMessage());
		}
	}

	/**
	 * 交易认证失败，上传照片(当天多次失败，更新照片)
	 */
	public void uploadPicWhenTxnAuthFalse() {
		JsonBean json = new JsonBean();
		if (!"MER".equals(matb.getAuthType())) {
			matb.setAuthType("TXN");
		}
		boolean weChatRequest=matb.isWeChatProg() && StringUtils.isNotEmpty(matb.getUploadFileBaseInfo());
		if (matb.getMid() != null && !"".equals(matb.getMid()) && matb.getBankAccNo() != null
				&& !"".equals(matb.getBankAccNo()) && matb.getBankAccName() != null && !"".equals(matb.getBankAccName())
				&& matb.getLegalNum() != null && !"".equals(matb.getLegalNum()) &&
				(matb.getAuthUploadFile() != null || weChatRequest)) {
			try {
				Integer result = merchantAuthenticityService.updateTxnAuthWithPic(matb);
				if (result > 0) {
					json.setSuccess(true);
					json.setMsg("上传成功");
				} else {
					json.setSuccess(false);
					json.setMsg("上传失败");
				}
			} catch (Exception e) {
				log.error(e);
				json.setSuccess(false);
				json.setMsg("上传异常！");
			}
		} else {
			json.setSuccess(false);
			json.setMsg("参数有空值，请核对参数！");
		}
//		super.writeJson(json);
		// @author:lxg-20191114 敏感信息加密处理
		super.writeJson(CommonTools.jsonBeanToString(json, matb.isEnc(), matb.getSck()));
	}

	/**
	 * 加密交易认证失败，上传照片(当天多次失败，更新照片)
	 */
	public void decUploadPicWhenTxnAuthFalse() {
		JsonBean json = new JsonBean();
		boolean b = phoneWechatPublicAccService.addReplayAttack(super.getRequest().getParameter("uuid"),
				super.getRequest().getParameter("timeStamp"));
		if (!b) {
			json.setSuccess(false);
			json.setMsg("数据有误！");
		} else {
			String sign = super.getRequest().getParameter("sign");
			Map<String, String> map = new HashMap<String, String>();
			map.put("legalNum", matb.getLegalNum());
			map.put("bankAccNo", matb.getBankAccNo());
			map.put("authType", matb.getAuthType());
			map.put("mid", matb.getMid());
			map.put("bankAccName", matb.getBankAccName());
			map.put("username", matb.getUsername());
			String md5 = MD5Util.MD5((SimpleXmlUtil.getSignBlock(map) + "&key=dseesa325errtcyraswert749errtdyt"));
			if (md5.equals(sign)) {
				if (!"MER".equals(matb.getAuthType())) {
					matb.setAuthType("TXN");
				}
				matb.setBankAccNo(HrtRSAUtil.decryptWithBase64(matb.getBankAccNo()));
				matb.setLegalNum(HrtRSAUtil.decryptWithBase64(matb.getLegalNum()));
				if (matb.getMid() != null && !"".equals(matb.getMid()) && matb.getBankAccNo() != null
						&& !"".equals(matb.getBankAccNo()) && matb.getBankAccName() != null
						&& !"".equals(matb.getBankAccName()) && matb.getLegalNum() != null
						&& !"".equals(matb.getLegalNum()) && matb.getAuthUpload() != null) {
					try {
						// 图片解密
						matb.setAuthUploadFile(HrtRSAUtil.decryptStringToFile(matb.getAuthUpload().substring(10)));
						if (matb.getAuthUploadFile() != null) {
							Integer result = merchantAuthenticityService.updateTxnAuthWithPic(matb);
							if (result > 0) {
								json.setSuccess(true);
								json.setMsg("上传成功");
							} else {
								json.setSuccess(false);
								json.setMsg("上传失败");
							}
						} else {
							json.setSuccess(false);
							json.setMsg("数据有误！");
						}
					} catch (Exception e) {
						log.error(e);
						json.setSuccess(false);
						json.setMsg("上传异常！");
					}
				} else {
					json.setSuccess(false);
					json.setMsg("参数有空值，请核对参数！");
				}
			} else {
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

	public void queryTxnAuthInfoByMidDataV2() {
		// @author:lxg-20191114 敏感信息加密处理
		try {
			String data = getRequest().getParameter("data");
			String aesEn = getRequest().getParameter("aesEn");
			String sck = CommonTools.getSck(aesEn);
			String data0 = CommonTools.parseAesEnAndData(sck, data);
			matb = JSONObject.parseObject(data0, MerchantAuthenticityBean.class);
			matb.setSck(sck);
			matb.setEnc(true);
			log.error("queryTxnAuthInfoByMidDataV2解密后的请求参数:" + JSON.toJSONString(matb));
			queryTxnAuthInfoByMid();
		} catch (Exception e) {
			log.error("queryTxnAuthInfoByMidDataV2解密请求出错:" + e.getMessage());
		}
	}

	/**
	 * 查询商户下所有交易认证记录
	 */
	public void queryTxnAuthInfoByMid() {
		JsonBean json = new JsonBean();
		if (matb.getMid() != null && !"".equals(matb.getMid())) {
			List<MerchantAuthenticityModel> authInfoList = merchantAuthenticityService
					.queryTxnAuthInfoByMid(matb.getMid());
			if (authInfoList.size() > 0) {
				json.setObj(authInfoList);
				json.setSuccess(true);
				json.setMsg("查询成功");
			} else {
				json.setSuccess(false);
				json.setMsg("暂无记录");
			}
		} else {
			json.setSuccess(false);
			json.setMsg("查询失败");
		}
//		super.writeJson(json);
		// @author:lxg-20191114 敏感信息加密处理
		super.writeJson(CommonTools.jsonBeanToString(json, matb.isEnc(), matb.getSck()));
	}

	/**
	 * 人工修改商户认证信息(通过/退回)
	 */
	public void editMerAuthenticity() {
		JsonBean json = new JsonBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			matb.setAuthType("MER");
			matb.setCdate(new Date());
//			matb.setApproveNote(new String(super.getRequest().getParameter("approveNote").getBytes("iso8859-1"),"UTF8"));
			boolean editSuccess = false;
			String flag = super.getRequest().getParameter("flag");
			if (flag == "go" || "go".endsWith(flag)) {
				matb.setStatus("00");
				editSuccess = merchantAuthenticityService.updateTxnAuthenticityGo(matb, (UserBean) userSession);
				if (editSuccess) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("sessionId", "");
					map.put("msg", "认证成功");
					map.put("status", "2");
					merchantAuthenticityService.sendResultToHyb(map, matb);
					json.setSuccess(true);
					json.setMsg("人工认证成功");
				} else {
					json.setSuccess(false);
					json.setMsg("人工认证失败");
				}
			} else if (flag == "back" || "back".endsWith(flag)) {
				matb.setStatus("01");
				editSuccess = merchantAuthenticityService.updateTxnAuthenticityBack(matb, (UserBean) userSession);
				if (editSuccess) {
					json.setSuccess(true);
					json.setMsg("退回成功");
				} else {
					json.setSuccess(false);
					json.setMsg("退回失败");
				}
			} else {
				throw new Exception("传参错误");
			}
		} catch (Exception e) {
			log.error("修改商户认证信息异常：" + e);
			json.setSuccess(false);
			json.setMsg("操作异常");
		}
		super.writeJson(json);
	}

	/**
	 * 查看明细(查看 认证失败后 上传的照片)
	 */
	public void serachMerAuthInfoDetailed() {

		MerchantAuthenticityBean mb = merchantAuthenticityService.queryMerAuthenticityById(matb.getBmatid());
		super.writeJson(mb);
	}

	/**
	 * 查询通道商认证资料
	 */
	public void listHrtAuthenticity() {
		DataGridBean dgb = new DataGridBean();
		try {
			Object userSession = super.getRequest().getSession().getAttribute("user");
			matb.setAuthType("HRT");
			dgb = merchantAuthenticityService.queryHrtAuthenticity(matb, ((UserBean) userSession));
		} catch (Exception e) {
			log.error("分页查询查询通道商认证资料异常：" + e);
		}
		super.writeJson(dgb);
	}

	public void exportHrtAuthenticity() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		matb.setAuthType("HRT");
		// session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				List<Map<String, Object>> list = merchantAuthenticityService.exportHrtAuthenticity(matb,
						((UserBean) userSession));

				List<String[]> excelList = new ArrayList<String[]>();
				String title[] = { "机构号", "身份证", "持卡人名称", "卡号", "通道", "认证状态", "认证返回信息", "认证时间" };
				excelList.add(title);
				for (int i = 0; i < list.size(); i++) {
					Map map = list.get(i);
					Object status = map.get("STATUS");
					if (status == null) {
						status = "未认证";
					} else if ("00".equals(status.toString())) {
						status = "已认证";
					} else if ("01".equals(status.toString())) {
						status = "认证中";
					}
					String[] rowContents = { map.get("UNNO") == null ? "" : map.get("UNNO").toString(),
							map.get("LEGALNUM") == null ? "" : map.get("LEGALNUM").toString(),
							map.get("BANKACCNAME") == null ? "" : map.get("BANKACCNAME").toString(),
							map.get("BANKACCNO") == null ? "" : map.get("BANKACCNO").toString(),
							map.get("CARDNAME") == null ? "" : map.get("CARDNAME").toString(), status.toString(),
							map.get("RESPINFO") == null ? "" : map.get("RESPINFO").toString(),
							map.get("CDATE") == null ? "" : map.get("CDATE").toString(), };
					excelList.add(rowContents);
				}
				String excelName = "人工实名认证资料.csv";
				JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
				json.setSuccess(true);
				json.setMsg("导出人工实名认证资料成功");
				excelList = null;
				list = null;
			} catch (Exception e) {
				log.error("导出人工实名认证资料异常：" + e);
				json.setMsg("导出人工实名认证资料失败");
			}
		}
	}

	/**
	 * 支付宝人脸认证调用
	 */
	public void addMerchantAuthInfoUseZFB() {
		JsonBean json = new JsonBean();
		//测试临时添加20200527
		json.setMsg("您今日的认证次数过多，请明日再试！");
		json.setSuccess(true);
		json.setStatus("1997");
		super.writeJson(json);
		return;
		
		
		
		
//		matb.setAuthType("MER");
//		// 新版支付宝人脸验证，先判断agentid是否有值，有值且判断agentid是否在redis中配置，配置走人脸验证 。
//		if (matb.getAgentId() != null && !"".equals(matb.getAgentId())) {
//			// 有配置该产品需要人脸认证
//			if (matb.getMid() != null && !"".equals(matb.getMid()) 
//					&& matb.getBankAccName() != null && !"".equals(matb.getBankAccName()) 
//					&& matb.getLegalNum() != null && !"".equals(matb.getLegalNum()) 
//					&& matb.getReturnUrl() != null && !"".equals(matb.getReturnUrl())) {
//				log.info(matb.getMid()+","+matb.getBankAccName()+","+matb.getReturnUrl()+","+matb.getLegalNum()+","+matb.getAgentId());
//				// 判断此商户是否存在待认证记录
//				Integer count = merchantAuthenticityService.queryMerchantAuthIsReadyForZfb(matb);
//				if (count > 0) {
//					List<Map<String, String>> orderList = merchantAuthenticityService.queryCertifyIdForMatbForOrderNo(matb);
//					if(orderList !=null && orderList.size()>0) {
//						json.setCountTxnAmount(orderList.get(0).get("CARDHOLDERNAME")); 
//						json.setMsg("您正在验证中，请在支付宝app内完成验证，如已验证完成，请点击查询按钮。");
//						json.setStatus("1999");
//						json.setSuccess(true);
//					}else {
//						json.setMsg("待认证订单号查询异常");
//						json.setStatus("1998");
//						json.setSuccess(true);
//					}
//					super.writeJson(json);
//					return;
//				}
//				//判断此商户是否有成功认证记录
//				Integer isSuccessCount = merchantAuthenticityService.queryMerchantAuthIsSuccessForZfb(matb);
//				if (isSuccessCount > 0) {
//					json.setMsg("该商户已存在成功认证状态！");
//					json.setStatus("2000");
//					json.setSuccess(true);
//					super.writeJson(json);
//					return;
//				}
//				
//				
//				// 判断此商户当天是否已认证失败3次
//				// 查询实名验证次数，每人每天认证失败次数不能超过三次
//				Integer count1 = merchantAuthenticityService.queryMerchantAuthCountForZfb(matb);
//				if (count1 < 3) {
//					AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
//							ParamPropUtils.props.getProperty("APP_ID"),
//							ParamPropUtils.props.getProperty("privateKey"), "json", "GBK",
//							ParamPropUtils.props.getProperty("publicKey"), "RSA2");
//					AlipayUserCertifyOpenInitializeRequest request = new AlipayUserCertifyOpenInitializeRequest();
//					JSONObject jsonObject = new JSONObject();
//					String outer_order_no = UUID.randomUUID().toString().replaceAll("-", "");
//					jsonObject.put("outer_order_no", outer_order_no);
//					jsonObject.put("biz_code", "FACE");//SMART_FACE
//					JSONObject identity_param = new JSONObject();
//					identity_param.put("identity_type", "CERT_INFO");
//					identity_param.put("cert_type", "IDENTITY_CARD");
//					identity_param.put("cert_name", matb.getBankAccName());
//					identity_param.put("cert_no", matb.getLegalNum());
//					jsonObject.put("identity_param", identity_param);
//					JSONObject merchant_config = new JSONObject();
//					String return_url = matb.getReturnUrl() + "?outer_order_no=" + outer_order_no;
//					merchant_config.put("return_url", return_url);
//					jsonObject.put("merchant_config", merchant_config);
//					request.setBizContent(jsonObject.toJSONString());
//					AlipayUserCertifyOpenInitializeResponse response = null;
//					String certify_id = "";
//
//					try {
//						response = alipayClient.execute(request);
//					} catch (AlipayApiException e) {
//						log.error("[支付宝身份验证初始化请求调用]-失败:" + e.getErrMsg());
//						json.setStatus("1998");
//					}
//					if (response.isSuccess()) {
//						certify_id = response.getCertifyId();
//						log.error("[支付宝初始化身份验证成功]-身份验证id:" + certify_id);
//
//					} else {
//						String error = response.getSubMsg();
//						log.error("[支付宝初始化身份验证失败]-失败原因" + error);
//					}
//					String certifyUrl = "";
//					if (StringUtils.isNotEmpty(certify_id)) {
//						log.info("[支付宝身份确认]-请求支付宝sdk确认身份");
//						certifyUrl = certify(alipayClient, certify_id);
//						String encodeUrl = "alipays://platformapi/startapp?appId=20000067&url="
//								+ URLEncoder.encode(certifyUrl);
//						json.setReturnUrl(encodeUrl);
//						json.setStatus("1996");
//						json.setCountTxnAmount(outer_order_no);
//						json.setSuccess(true);
//						// TODO 初始化认证记录
//						matb.setSysseqnb(certify_id);
//						matb.setCardholderName(outer_order_no);
//						log.info("[支付宝初始化验证插入]"+outer_order_no);
//						merchantAuthenticityService.saveMerchantAuthInfoZFB(matb);
//					} else {
//						json.setSuccess(true);
//						json.setStatus("1998");
//						json.setMsg("无订单！");
//					}
//				} else {
//					json.setMsg("您今日的认证次数过多，请明日再试！");
//					json.setSuccess(true);
//					json.setStatus("1997");
//				}
//			} else {
//				json.setSuccess(true);
//				json.setMsg("参数有空值，请核对参数！");
//			}
//		}
//		super.writeJson(json);
//		return;
	}

	/**
	 * 支付宝人脸认证查询，认证记录变更（数据插入状态修改）
	 */
	public void updateMerchantAuthInfoUseZFB() {
		matb.setAuthType("MER");
		JsonBean json = new JsonBean();
		String certify_id = null;
		log.info("[支付宝身份验证]-更新身份验证结果");
		if (matb.getMid() == null || "".equals(matb.getMid()) 
				|| matb.getBankAccName() == null || "".equals(matb.getBankAccName()) 
				|| matb.getLegalNum() == null || "".equals(matb.getLegalNum())
				|| matb.getUsername() == null || "".equals(matb.getUsername())) {
			json.setMsg("参数空值不全，请核对参数！");
			super.writeJson(json);
			return;
		}
		if (matb.getAuthUpload() == null || "".equals(matb.getAuthUpload())) {
			// 用三要素查(基本不会走这个，都是有订单号的。)
			List<Map<String, String>> list2 = merchantAuthenticityService.queryCertifyIdForMatb(matb);
			if (list2.isEmpty() || list2.get(0) == null) {
				json.setMsg("此商户待认证信息不存在");
				json.setStatus("1998");
				json.setSuccess(true);
				super.writeJson(json);
				return;
			} else {
				String status = list2.get(0).get("RESPCD");
				certify_id = list2.get(0).get("SYSSEQNB");
				json.setMsg(status);
				if ("2000".equals(status)) {
					if (certify_id.isEmpty()) {
						json.setMsg("certify_id为空");
						json.setStatus("1998");
						json.setSuccess(true);
					}else {
						json.setStatus("2000");
						json.setSuccess(true);
					}
					super.writeJson(json);
					return;
				}
			}
		} else {

			// 根据outer_order_no查询对应的certify_id
			List<Map<String, String>> list = merchantAuthenticityService.queryCertifyIdForOrderNo(matb);

			if (list.isEmpty() || list.get(0) == null) {
				json.setMsg("无此订单");
				json.setStatus("1998");
				json.setSuccess(true);
				super.writeJson(json);
				return;
			} else {
				String status = list.get(0).get("RESPCD");
				certify_id = list.get(0).get("SYSSEQNB");
				json.setMsg(status);
				json.setStatus(status);
				if ("2000".equals(status)) {
					if (certify_id.isEmpty()) {
						json.setMsg("certify_id为空");
						json.setStatus("1998");
						json.setSuccess(true);
					}else {
						json.setStatus("2000");
						json.setSuccess(true);
					}
					super.writeJson(json);
					return;
				} 
			}
		}
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
				ParamPropUtils.props.getProperty("APP_ID"), ParamPropUtils.props.getProperty("privateKey"), "json",
				"GBK", ParamPropUtils.props.getProperty("publicKey"), "RSA2");
		AlipayUserCertifyOpenQueryRequest request = new AlipayUserCertifyOpenQueryRequest();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("certify_id", certify_id);
		request.setBizContent(jsonObject.toJSONString());
		AlipayUserCertifyOpenQueryResponse response = null;
		try {
			response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			log.error("[支付宝身份验证]-查询身份验证结果请求失败：{}", e);
		}
		Map<String, Object> query_response = (Map<String, Object>) JSONObject.parseObject(response.getBody(), Map.class)
				.get("alipay_user_certify_open_query_response");
		String passed = (String) query_response.get("passed");
		String sub_msg = (String) query_response.get("sub_msg");
		if (response.isSuccess()) {
			log.info("[支付宝身份验证]-成功");
			if (passed != null) {
				// TODO 更新验证信息
				String status1 = merchantAuthenticityService.updateAuthenticityForZFB(matb, passed, "1");
				//json.setMsg(passed);
				json.setStatus(status1);
				json.setSuccess(true);
				log.error("[支付宝身份验证]-成功：{}" + passed);
			}
		} else {
			if (sub_msg != null) {
				// TODO 更新验证信息
				String status2 = merchantAuthenticityService.updateAuthenticityForZFB(matb, sub_msg, "2");
				//json.setMsg(sub_msg);
				json.setStatus(status2);
				json.setSuccess(true);
				log.error("[支付宝身份验证]-失败：{}" + sub_msg);
			}
		}
		super.writeJson(json);
	}

	/**
	 * certify
	 * @param alipayClient
	 * @param certify_id
	 * @return
	 */
	private String certify(AlipayClient alipayClient, String certify_id) {
		AlipayUserCertifyOpenCertifyRequest request = new AlipayUserCertifyOpenCertifyRequest();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("certify_id", certify_id);
		request.setBizContent(jsonObject.toJSONString());
		AlipayUserCertifyOpenCertifyResponse response = null;
		try {
			response = alipayClient.pageExecute(request, "GET");
		} catch (AlipayApiException e) {
			log.error("[支付宝身份确认调用失败]-失败:" + e.getErrMsg());
		}
		String certifyUrl = "";
		if (response.isSuccess()) {
			certifyUrl = response.getBody();
			log.error("[支付宝身份确认]-成功，返回url:" + certifyUrl);

		} else {
			String error = response.getSubMsg();
			log.error("[支付宝身份确认失败]-失败原因:" + error);
		}
		return certifyUrl;
	}
}
