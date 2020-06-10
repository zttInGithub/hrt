package com.hrt.biz.bill.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.model.MerchantTaskDetail1Model;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail2Model;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail3Model;
import com.hrt.biz.bill.service.IMerchantTaskOperateService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class MerchantTaskOperateAction extends BaseAction{

	/**
	 * 商户工单取消
	 * @author xxx 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(AgentSalesAction.class);
	private String unno ;
	private String approveStatus;
	private List<Object> list;
	private IMerchantTaskOperateService merchantTaskOperateService;
	private Integer bmtkid;
	private MerchantTaskDetail1Model merchantTaskDetail1;
	private MerchantTaskDetail2Model merchantTaskDetail2;
	private MerchantTaskDetail3Model merchantTaskDetail3;
	private String mid;
	private String startDay;
	private String endDay;
	//当前页
	private Integer page;
	//显示行数
	private Integer rows;
	//批量取消工单bmtkid集合
	private String ids;
	//工单类型
	private String mtype;

	public String getMtype() {
		return mtype;
	}
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	public IMerchantTaskOperateService getMerchantTaskOperateService() {
		return merchantTaskOperateService;
	}
	public void setMerchantTaskOperateService(IMerchantTaskOperateService merchantTaskOperateService) {
		this.merchantTaskOperateService = merchantTaskOperateService;
	}
	public MerchantTaskDetail1Model getMerchantTaskDetail1() {
		return merchantTaskDetail1;
	}
	public void setMerchantTaskDetail1(MerchantTaskDetail1Model merchantTaskDetail1) {
		this.merchantTaskDetail1 = merchantTaskDetail1;
	}
	public MerchantTaskDetail2Model getMerchantTaskDetail2() {
		return merchantTaskDetail2;
	}
	public void setMerchantTaskDetail2(MerchantTaskDetail2Model merchantTaskDetail2) {
		this.merchantTaskDetail2 = merchantTaskDetail2;
	}
	public MerchantTaskDetail3Model getMerchantTaskDetail3() {
		return merchantTaskDetail3;
	}
	public void setMerchantTaskDetail3(MerchantTaskDetail3Model merchantTaskDetail3) {
		this.merchantTaskDetail3 = merchantTaskDetail3;
	}
	public Integer getBmtkid() {
		return bmtkid;
	}
	public void setBmtkid(Integer bmtkid) {
		this.bmtkid = bmtkid;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	
	 
	
	
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	
	
	/**
	 * 根据商户编号查询商户未审核工单
	 */
	public void serchMerchantTaskData(){
		DataGridBean dgb =null;
		Object  user=super.getRequest().getSession().getAttribute("user");
		if(user!=null){
			this.unno=((UserBean)user).getUnNo();
			try {
				System.out.println(startDay);
				dgb=merchantTaskOperateService.queryMerchantTaskData((UserBean)user, approveStatus,page,rows,mid,startDay,endDay);
			} catch (Exception e) {
				log.error("待审核工单查询异常：" + e);
			}
		}
		super.writeJson(dgb);  
		} 
	/**
	 * 根据商户编号查询商户未审核工单
	 */
	public void serchMerchantTaskDatabaodan(){
		DataGridBean dgb =null;
		UserBean  user=(UserBean)super.getRequest().getSession().getAttribute("user");
		if(user!=null){
			this.unno=((UserBean)user).getUnNo();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(mid==null||"".equals(mid))mid="864";
				if(startDay==null||"".equals(startDay)){
					startDay="2018-01-01";
					startDay= sdf.format(new Date());
					endDay= sdf.format(new Date());
				}
//				else if(!startDay.startsWith("2018")){
//					startDay="2018-01-01";
//				}
				if(user.getLoginName().startsWith("baodan0")||user.getLoginName().startsWith("11100099"))user.setUnNo("110000");
				dgb=merchantTaskOperateService.queryMerchantTaskData(user, approveStatus,page,rows,mid,startDay,endDay);
			} catch (Exception e) {
				log.error("待审核工单查询异常：" + e);
			}
		}
		super.writeJson(dgb);  
		} 
	
	/**
	 * 根据商户编号查询风控未审核工单
	 */
	public void queryMerchantRiskTaskData(){
		DataGridBean dgb =null;
		Object  user=super.getRequest().getSession().getAttribute("user");
		this.unno=((UserBean)user).getUnNo();
		try {
			dgb=merchantTaskOperateService.queryMerchantRiskTaskData((UserBean)user, approveStatus,page,rows,mid,startDay,endDay,mtype);
		} catch (Exception e) {
			log.error("待审核工单查询异常：" + e);
		}
		super.writeJson(dgb);  
		} 
	
	/**
	 * 根据商户编号查询风控未审核工单
	 */
	public void queryMerchantRiskTaskDatabaodan(){
		DataGridBean dgb =null;
		UserBean  user=(UserBean)super.getRequest().getSession().getAttribute("user");
		this.unno=((UserBean)user).getUnNo();
		try {
			if(startDay==null||"".equals(startDay)){
				startDay="2018-01-01";
			}else if(!startDay.startsWith("2018")){
				startDay="2018-01-01";
			}
			mtype = "0";
			user.setUnNo("110000");
			dgb=merchantTaskOperateService.queryMerchantRiskTaskData(user, approveStatus,page,rows,mid,startDay,endDay,mtype);
		} catch (Exception e) {
			log.error("待审核工单查询异常：" + e);
		}
		super.writeJson(dgb);  
		} 
	
	
	/**
	 * 根据审核工单ID查询商户提交的商户基本信息工单
	 */
	public void serachMerahctTaskDetail1(){
		super.writeJson(merchantTaskOperateService.queryMerchantTaskDetail1(bmtkid)); 
	} 
	
	/**
	 * 根据审核工单ID查询商户提交的商户银行信息工单
	 */
	public void serachMerahctTaskDetail2(){
		super.writeJson(merchantTaskOperateService.queryMerchantTaskDetail2(bmtkid));
	}
	
	/**
	 * 根据审核工单ID查询商户提交的商户费率信息工单
	 */
	public void serachMerahctTaskDetail3(){
		super.writeJson(merchantTaskOperateService.queryMerchantTaskDetail3(bmtkid));
	}
	
	/**
	 * 根据审核工单ID查询代理提交的风控工单
	 */
	public void serachMerahctTaskDetail4(){
		super.writeJson(merchantTaskOperateService.queryMerchantTaskDetail4(bmtkid));
	}
	
	/**
	 * 根据审核工单ID查询代理提交的转T+0工单
	 */
	public void serachMerahctTaskDetail5(){
		super.writeJson(merchantTaskOperateService.queryMerchantTaskDetail5(bmtkid,mid));
	}
	
	/**
	 * 取消商户提交的工单
	 */
	public void deleteMerchantTaskDetail(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			if(bmtkid!=null){
				try {
					boolean ff=merchantTaskOperateService.deleteMerchantTaskDetail(bmtkid);
					if(ff){
						json.setSuccess(true);
						json.setMsg("工单取消成功");
					}else{
						json.setSuccess(true);
						json.setMsg("工单已处理过，禁止重复处理！");
					}
					 
				} catch (Exception e) {
					log.error("工单取消异常：" + e);
					json.setMsg("工单取消失败");
					} 
				}
			}
			super.writeJson(json);
		}
	/**
	 * 批量取消商户提交的工单
	 */
//	public void deleteMoneyMerchantTaskDetail(){
//		JsonBean json = new JsonBean();
//		Object userSession = super.getRequest().getSession().getAttribute("user");
//		if (userSession == null) {
//			json.setSessionExpire(true);
//		} else {
//			if(ids !=null && ids !=""){
//				try {
//					merchantTaskOperateService.deleteMoneyMerchantTaskDetail(ids);
//					json.setSuccess(true);
//					json.setMsg("工单批量取消成功"); 
//				} catch (Exception e) {
//					log.error("工单批量取消异常：" + e);
//					json.setMsg("工单批量取消失败");
//					}
//				} 
//			}
//			super.writeJson(json);
//		}
	
public void ImageShow() throws IOException{
		
	    
		//String img = "D:\\u01\\hrtwork\\upload\\111000\\111000.486001094000604.20140509.1.jpg";
	    String img =super.getRequest().getParameter("upload");
	  //  img="D:/"+img;
        BufferedInputStream bis = null;  
        OutputStream os = null;  
        FileInputStream fileInputStream = new FileInputStream(new File(img));  
        HttpServletResponse response= super.getResponse();
        bis = new BufferedInputStream(fileInputStream);  
                byte[] buffer = new byte[512];  
                response.setCharacterEncoding("UTF-8");  
                        //不同类型的文件对应不同的MIME类型  
                response.setContentType("image/*");  
                        //文件以流的方式发送到客户端浏览器  
                //response.setHeader("Content-Disposition","attachment; filename=img.jpg");  
                //response.setHeader("Content-Disposition", "inline; filename=img.jpg");  
  
                response.setContentLength(bis.available());  
                  
                os = response.getOutputStream();  
                int n;  
                while ((n = bis.read(buffer)) != -1) {  
                  os.write(buffer, 0, n);  
                }  
                bis.close();  
                os.flush();  
                os.close();  
		
	}
	}
	

