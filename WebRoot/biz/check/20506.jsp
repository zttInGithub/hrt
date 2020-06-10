<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	//初始化datagrid
	
	$('#shRiskType').hide();
		$('#check_20506_datagrid').datagrid({
			//url:'${ctx}/sysAdmin/paymentRisk_queryConfluence',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : false,//分页
			nowrap : true,
			striped:true,
			ctrlSelect:true,
			checkOnSelect:true,
			 pageSize : 9999,
			//pageList : [ 10, 15 ], 
			sortName: 'profit',
			sortOrder: 'desc',
			columns : [[{
				title :'字段',
				field :'Element',
				width : 100
			},{
				title :'内容',
				field :'Info',
				width : 100
			},{
				title :'出现有效次数',
				field :'ValidNum',
				width : 100
			},{
				title :'涉及机构数量',
				field :'InvOrgNum',
				width : 100
			}]]
		});

	
	//风险信息总汇查询
	function check_20506_Report() {
	  var RiskType6= $('#RiskType6').val();
	  var KeyWord6= $('#KeyWord6').val();
	  var CusProperty=  $('#CusProperty').val();
	    var Infos6=  $('#Infos6').val();
	  if(RiskType6==''||KeyWord6==''||CusProperty==''||Infos6==''){
	 		 $.messager.alert('提示', "客户属性,风险类型,关键字,查询条件信息不可为空！");
	 		 return;
	  }
		$.ajax({
			type:'post',
			url:'${ctx}/sysAdmin/paymentRisk_queryConfluence',
			//async : false,
			data:$('#check_20506_searchForm').serialize(),
			dataType : 'json',
			success : function(data) {
			if (data.success) {
				$.messager.alert('提示', data.msg);
			}else{
				 $("#check_20506_datagrid").datagrid("loadData", data.rows);  //动态取数据
				}
			},
			error : function() {
				alert('error');
			}
		});
	}
	
	
	function  selectMenu(a){
	 $('#RiskType6').empty();
	  $('#KeyWord6').empty();
		 if(a=="02"){
		$('#check_20506_searchForm').append("<input type=\"hidden\"  name=\"TrnxCode\" value=\"QR0002\"/>");
			$('#RiskType6').append("<option value=\"\"></option><option value=\"01\">虚假申请</option><option value=\"02\">套现、套积分</option><option value=\"03\">违法经营</option><option value=\"04\">销赃</option><option value=\"05\">买卖或租借银行账户</option> <option value=\"06\">侧录点</option><option value=\"07\">伪卡集中使用点</option><option value=\"08\">泄露账户及交易信息</option><option value=\"09\">恶意倒闭</option><option value=\"10\">恶意分单</option><option value=\"11\">移机</option><option value=\"12\">高风险商户</option><option value=\"13\">商户合谋欺诈</option> <option value=\"14\">破产或停业商户</option><option value=\"15\">强迫签单</option><option value=\"17\">频繁变更服务机构</option><option value=\"18\">关联商户涉险</option><option value=\"19\">买卖银行卡信息</option><option value=\"99\">其他</option>");
			$('#KeyWord6').append("<option value=\"\"></option><option value=\"01\">商户名称</option><option value=\"02\">组织机构代码</option><option value=\"03\">营业执照编码</option><option value=\"04\">服务器IP</option><option value=\"05\">手机</option><option value=\"06\">营业执照注册名称</option><option value=\"07\">法人身份证号码</option><option value=\"08\">银行账号</option><option value=\"09\">网址</option><option value=\"10\">ICP编号</option>");
		}else if(a=="01"){
		$('#check_20506_searchForm').append("<input type=\"hidden\"  name=\"TrnxCode\" value=\"QR0001\"/>");
			$('#RiskType6').append("<option value=\"\"></option><option value=\"01\">虚假申请（受害人信息）</option><option value=\"02\">虚假申请（嫌疑人信息）</option><option value=\"03\">伪卡（受害人信息）</option><option value=\"04\">失窃卡（受害人信息）</option><option value=\"05\">未达卡（受害人信息）</option> <option value=\"06\">银行卡转移（受害人信息）</option><option value=\"07\">盗用银行卡（嫌疑人信息）</option><option value=\"08\">银行卡网络欺诈（受害人信息）</option><option value=\"09\">银行卡网络欺诈（嫌疑人信息）</option><option value=\"10\">虚拟账户被盗（受害人信息）</option><option value=\"11\">盗用虚拟账户（嫌疑人信息）</option><option value=\"12\">套现、套积分（嫌疑人信息）</option><option value=\"13\">协助转移赃款</option> <option value=\"14\">买卖或租借银行账户</option><option value=\"15\">专案风险信息</option><option value=\"17\">买卖银行卡信息</option><option value=\"99\">其他</option>");
			$('#KeyWord6').append("<option value=\"\"></option><option value=\"01\">手机</option><option value=\"02\">Imei</option><option value=\"03\">MAC</option><option value=\"04\">银行卡号</option><option value=\"05\">证件号码</option><option value=\"06\">收款银行卡号</option><option value=\"07\">邮箱</option><option value=\"08\">IP</option>");
		}else{
		}	
	} 
	
	
		//清除表单内容
	function check_20506_cleanFun() {
		 $('#RiskType6').empty();
		  $('#KeyWord6').empty();
		  $('#CusProperty').empty();
		$('#check_20506_searchForm input').val('');
	}
</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:16px;">
		<form id="check_20506_searchForm" method="post">
		<!-- <input type="hidden" id="check" name="check" /> -->
			<table class="tableForm" >
				<tr>
					<th>客户属性<font color="red">&nbsp;*</font></th>
					<td> 
						<select id="CusProperty"  onchange="javascript:selectMenu(this.value);" name="CusProperty"  style="width:130px;">
							 <option value=""></option> 
							<option value="01">个人</option> 
							<option value="02">商户</option>
						 </select>	
					</td>
					 <th>风险类型<font color="red">&nbsp;*</font></th>
					<td>
					   <select id="RiskType6"  name="RiskType" data-options="panelHeight:'auto',editable:false" style="width:130px;" >
						</select>	
					</td>	
						
					<th>关键字<font color="red">&nbsp;*</font></th>
					<td>
						<select id="KeyWord6" name="KeyWord" data-options="panelHeight:'auto',editable:false" style="width:140px;">
						
						 </select>
					</td>
					<th>查询条件信息<font color="red">&nbsp;*</font></th>
					<td><input id="Infos6" name="Info" class="easyui-validatebox" data-options="required:true"  style="width: 250px;" /></td>

				</tr>
				<tr>
					<th>风险事件时间</th>
					<td><input id="StartTime" name="StartTime" class="easyui-datebox"style="width: 80px;"/>
					<a>&nbsp;-&nbsp;&nbsp;</a><input id="EndTime" name="EndTime" class="easyui-datebox"style="width: 80px;"/>
					</td>
					<td></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20506_Report();">查询</a> 
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20506_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="check_20506_datagrid"></table>
    </div> 
</div>