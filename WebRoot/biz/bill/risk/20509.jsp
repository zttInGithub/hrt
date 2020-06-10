<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	$(function() {
		$('#check_20509_datagrid').datagrid({
			url:'${ctx}/sysAdmin/specialMerchant_querySpecialPersonal',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,//分页
			nowrap : true,
			striped:true,
			ctrlSelect:true,
			//singleSelect:true,
			checkOnSelect:true,
			 pageSize : 9999,
			pageList : [ 10, 15 ], 
			idField : 'Id',
			sortName : 'Id',
			sortOrder: 'desc',
			columns : [[{
				title :'id',
				field :'ID',
				width : 100,
				hidden :true
			},{
				title :'商户类型',
				field :'CUSTYPE',
				width : 100,
				sortable :true,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "自然人";
					 }else if(value=='02'){
					 	return "企业商户";
					 }else if(value=='03'){
					 	return "个体工商户";
					 }
					}
				},{
				title :'商户属性',
				field :'CUSNATURE',
				width : 100,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "实体特约商户";
					 }else if(value=='02'){
					 	return "网络特约商户";
					 }else if(value=='03'){
					 	return "实体兼网络特约商户";
					 }
					}
			},{
				title :'商户简称',
				field :'CUSNAME',
				width : 100
			},{
				title :'商户名称',
				field :'REGNAME',
				width : 100
			},{
				title :'商户英文名称',
				field :'CUSNAMEEN',
				width : 100
			},{
				title :'法证件类型',
				field :'DOCTYPE',
				width : 100,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "身份证";
					 }else if(value=='02'){
					 	return "军官证";
					 }else if(value=='03'){
					 	return "护照";
					 }else if(value=='04'){
					 	return "户口簿";
					 }else if(value=='05'){
					 	return "士兵证";
					 }else if(value=='06'){
					 	return "港澳来往内地通行证";
					 }else if(value=='07'){
					 	return "台湾同胞来往内地通行证";
					 }else if(value=='08'){
					 	return "临时身份证";
					 }else if(value=='09'){
					 	return "外国人居留证";
					 }else if(value=='10'){
					 	return "警官证";
					 }else if(value=='99'){
					 	return "其他";
					 }
					}
			},{
				title :'法人证件号码',
				field :'DOCCODE',
				width : 100
			},{
				title :'商户编码',
				field :'CUSCODE',
				width : 100
			},{
				title :'商户行业类别',
				field :'INDUTYPE',
				width : 100
			},{
				title :'银行账号',
				field :'BANKNO',
				width : 100
			},{
				title :'开户行',
				field :'OPENBANK',
				width : 100
			},{
				title :'商户注册地区',
				field :'REGADDRPROV',
				width : 100
			},{
				title :'商户注册地址',
				field :'REGADDRDETAIL',
				width : 100
			},{
				title :'商户经营地区',
				field :'ADDRPROV',
				width : 100
			},{
				title :'商户经营地址',
				field :'ADDRDETAIL',
				width : 100
			},{
				title :'网址',
				field :'URL',
				width : 100
			},{
				title :'服务器IP',
				field :'SERVERIP',
				width : 100
			},{
				title :'ICP编号',
				field :'ICP',
				width : 100
			},{
				title :'商户联系人',
				field :'CONTNAME',
				width : 100
			},{
				title :'商户联系电话',
				field :'CONTPHONE',
				width : 100
			},{
				title :'商户E-mail',
				field :'CUSEMAIL',
				width : 100
			},{
				title :'商户经营地区',
				field :'OCCURAREA',
				width : 100
			},{
				title :'清算网络',
				field :'NETWORKTYPE',
				width : 100,
				formatter:function(value,row,index){
				if(value=="01"){
					return "中国银联";
				}else if(value=="02"){
					return "网络支付清算平台";
				}else if(value=="02"){
					return "网络支付清算平台";
				}else if(value=="03"){
					return "清算总中心";
				}else if(value=="04"){
					return "农信银";
				}else if(value=="05"){
					return "城商行";
				}else if(value=="06"){
					return "同城结算中心";
				}else if(value=="99"){
					return "其他";
				}
				}
			},{
				title :'商户状态',
				field :'STATUS',
				width : 100,
				formatter :function(value,row,index){
					if(value=="01"){
						return "启用";
					}else if(value == "02"){
						return "关闭（暂停）";
					}else if(value == "03"){
						return "注销";
					}
				}
			},{
				title :'服务起始时间',
				field :'STARTTIME',
				width : 100
			},{
				title :'服务终止时间',
				field :'ENDTIME',
				width : 100
			},{
				title :'合规风险状况',
				field :'RISHSTATUS',
				width : 100,
				formatter : function(value,row,index){
					if(value=="01"){
						return "合规";
					}else if(value == "02"){
						return "风险";
					}
				}
			},{
				title :'开通业务种类',
				field :'OPENTYPE',
				width : 100,
				formatter : function(value,row,index){
					if(value=="01"){
						return "POS";
					}else if(value == "02"){
						return "条码";
					}
				}
			},{
				title :'计费类型',
				field :'CHAGETYPE',
				width : 100,
				formatter : function(value,row,index){
					if(value=="01"){
						return "标准";
					}else if(value == "02"){
						return "优惠";
					}else if(value == "03"){
						return "减免";
					}
				}
			},{
				title :'支持账户类型',
				field :'ACCOUNTTYPE',
				width : 100,
				formatter : function(value,row,index){
					if(value=="01"){
						return "借记卡";
					}else if(value == "02"){
						return "贷记卡";
					}else if(value == "03"){
						return "支付账户";
					}
				}
			},{
				title :'拓展方式',
				field :'EXPANDTYPE',
				width : 100,
				formatter : function(value,row,index){
					if(value=="01"){
						return "自主拓展";
					}else if(value == "02"){
						return "外包服务机构推荐";
					}
				}
			},{
				title :'外包服务机构名称',
				field :'OUTSERVICENAME',
				width : 100
			},{
				title :'外包服务机构法人证件类型',
				field :'OUTSERVICECARDTYPE',
				width : 100,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "营业执照编码";
					 }else if(value=='02'){
					 	return "统一社会信息代码";
					 }else if(value=='03'){
					 	return "组织机构代码证";
					 }else if(value=='04'){
					 	return "经营许可证";
					 }else if(value=='05'){
					 	return "税务登记证";
					 }else if(value=='99'){
					 	return "其他";
					 }
				}
			},{
				title :'外包服务机构法人证件号码',
				field :'OUTSERVICECARDCODE',
				width : 100
			},{
				title :'外包服务机构法定代表人证件类型',
				field :'OUTSERVICELEGCARDTYPE',
				width : 100,
				formatter : function(value,row,index) {
					if (value=='01'){
					   return "营业执照编码";
					 }else if(value=='02'){
					 	return "统一社会信息代码";
					 }else if(value=='03'){
					 	return "组织机构代码证";
					 }else if(value=='04'){
					 	return "经营许可证";
					 }else if(value=='05'){
					 	return "税务登记证";
					 }else if(value=='99'){
					 	return "其他";
					 }
				}	 
			},{
				title :'外包服务机构法定代表人证件号码',
				field :'OUTSERVICELEGCARDCODE',
				width : 100
			},{
				title :'上报日期',
				field :'REPDATE',
				width : 100
			},{
				title :'上传人',
				field :'REPPERSON',
				width : 100
			}]],
			toolbar:[{
					text:'图标说明：'
				},{
					id:'btn_edit',
					text:'上传',
					iconCls:'icon-edit',
					handler:function(){
						check_20509_upload();
					}
				}]
		});
	});
	/* //表单jiaoyan
	function check_20509_jiaoyan() {
			var RiskType = $('#RiskType8').datebox('getValue');
			//var OrigSender = $('#OrigSender7').val();
			var CusName = $('#CusName8').val();
			var OrgCode = $('#OrgCode8').val();
			var BusLicCode = $('#BusLicCode8').val();
			//alert(RiskType);alert(MobileNo);alert(BankNo);alert(Mac);alert(Imei);
			if ( '' == RiskType && BusLicCode == '' && CusName == '' && OrgCode == '') {
				$.messager.alert('提示','风险类型、客户名称、组织机构代码、营业执照编码 不能全部为空！ ');
			} else {
				return true;
			}
		}
	 */
	//商户风险信息变更查询
	function check_20509_Report() {
		$('#check_20509_datagrid').datagrid('unselectAll');
		//if(check_20509_jiaoyan()){
			$.ajax({
				type:'post',
				url:'${ctx}/sysAdmin/specialMerchant_querySpecialPersonal',
				//async : false,
				data:$('#check_20509_searchForm').serialize(),
				dataType : 'json',
				success : function(data) {
				if (data.success) {
					$.messager.alert('提示', data.msg);
				}else{
					 $("#check_20509_datagrid").datagrid("loadData", data.rows);  //动态取数据
					}
				},
				error : function() {
					alert('error');
				}
			});
		//}
	}

//上传窗口
	function check_20509_upload() {
	  var rows = $('#check_20509_datagrid').datagrid('getSelections');
      var row = rows[0];	
		$('<div id="check_20509_upload"/>').dialog({
			title: '上传',
			width: 900,   
		    height: 600, 
		    closed: false,
		    href: '${ctx}/biz/bill/risk/20509Upload.jsp',  
		    modal: true,
		    onLoad: function() {
		    	row.Occurarea = stringToList(row.Occurarea);   	
		    	$('#check_20509_uploadForm').form('load', row);
			},
			 buttons:[{ 
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						var dia = $('#check_20509_upload');
						$('#check_20509_uploadForm').form('submit', {
							url:'${ctx}/sysAdmin/specialMerchant_reportSpecialPersonal',
						    success:function(data){   
								var res = $.parseJSON(data);
								if (res.sessionExpire) {
									window.location.href = getProjectLocation();
								} else {
									if(res.success) {
										dia.dialog('destroy');
										$('#check_20509_datagrid').datagrid('unselectAll');
										//$('#check_20509_datagrid').datagrid('reload');
										//$.messager.alert("提示", res.msg,"info"); 
										$.messager.show({
										title : '提示',
										msg : res.msg
									}); 
									} else {
										$('#check_20509_datagrid').datagrid('unselectAll');
										$.messager.alert('提示', res.msg); 
										
									}  
								}
						    }   
						});
					}
				},{
					text:'取消',
					handler:function(){
						$('#check_20509_upload').dialog('destroy');
						//$('#check_20509_datagrid').datagrid('unselectAll');
					}
				}],
				onClose : function() {
					$(this).dialog('destroy');
					//$('#check_20509_datagrid').datagrid('unselectAll');
				} 
		});
	}
	 				//清除表单内容
	function check_20509_cleanFun() {
		$('#check_20509_searchForm input').val('');
	} 
</script>

<div class="easyui-layout" data-options="fit:true, border:false">

	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:16px;">
		<form id="check_20509_searchForm" method="post">
		<!-- <input type="hidden" id="check" name="check" /> -->
			<table class="tableForm" >
				<tr>
					<th>商户名称</th>
					<td><input  name="RegName" style="width: 120px;" /></td>	
					<th>商户编码</th>
					<td><input  name="CusCode" style="width: 120px;" /></td>
					 <th>商户联系电话</th>
					<td><input  name="ContPhone" style="width: 120px;" /></td>
					<th>上报时间</th>
					<td><input  name="REPDATE" class="easyui-datebox" style="width: 83px;" /> <a>&nbsp;-&nbsp;</a>
						<input  name="REPDATE1" class="easyui-datebox" style="width: 83px;" />
					</td>
					<th></th>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="check_20509_Report();">查询</a> 
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="check_20509_cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
        <table id="check_20509_datagrid"></table>
    </div> 
</div>