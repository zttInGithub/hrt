<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_20112_datagrid').datagrid({
			url :'${ctx}/sysAdmin/checkWechatTxnDetail_queryWechantTxnDetailInfo.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ,50,100],
			sortName: 'pwid',
			sortOrder: 'desc',
			idField : 'pwid',
			columns : [[{
				field : 'pwid',
				checkbox:true
			},{
				title : '机构号',
				field : 'UNNO',
				width : 50
			},{
				title : '商户编号',
				field : 'MID',
				width : 100
			},{
				title : '商户名称',
				field : 'RNAME',
				width : 70
			},{
				title :'终端号',
				field :'DETAIL',
				width : 50
			},{
				title :'原订单号',
				field :'OLDORDERID',
				width : 50
			},{
				title : '商户订单号',
				field : 'MER_ORDERID',
				width : 195
			},{
				title : '银行端订单号',
				field : 'BK_ORDERID',
				width : 165
			},{
				title :'商户类型',
				field :'ISM35',
				width : 50,
				 formatter : function(value,row,index) {
						// 支付渠道(10：微信；11：支付宝)
						if (value=='6'){
						   return "聚合";
						}else{
							return "线下";
						}
					} 
			},{
				title :'渠道',
				field :'FIINFO1',
				width : 40
				/* formatter : function(value,row,index) {
						// 支付渠道(10：微信；11：支付宝)
						if (value=='12'||value=='16'){
						   return "支付宝";
						}else if(value=='18'){
							return "立码付";
						}else{
							return "微信";
						}
					} */
			},{
				title :'交易类型',
				field :'TXNTYPE',
				width : 50,
				formatter : function(value,row,index) {
						//（0：消费；1：退款）
						if (value=='0'){
						   return "消费";
						}else if(value=='1'){
							return "退款";
						}
					}
			},{
				title :'交易金额',
				field :'TXNAMT',
				width : 50
			},{
				title :'手续费',
				field :'MDA',
				width : 35
			},{
				title :'状态',
				field :'STATUS',
				width : 50,
				formatter : function(value,row,index) {
						// 状态 0：未支付；1：支付成功；2：支付失败；4：交易关闭
						if (value=='0'){
						   return "未支付";
						}else if(value=='1'){
							return "支付成功";
						}else if(value=='2'){
							return "支付失败";
						}else if(value=='3'){
							return "交易关闭";
						}
					}
			},{
				title :'交易日期',
				field :'CDATE',
				width : 90,
				sortable :true
			}
			//,{
			//	title :'操作',
			//	field :'operation',
			//	width : 40,
			//	align : 'center',
			//	formatter : function(value,row,index) {
			//		//消费成功
			//		if(row.txnType=='0'&&row.status=='1'){
			//			return '<img src="${ctx}/images/frame_pencil.png" title="退款" style="cursor:pointer;" onclick="sysAdmin_20112_editFun('+index+')"/>&nbsp;&nbsp';
			//		}
			//	}
			//}
			]],
			toolbar:[{
					id:'btn_adds',
					text:'资料导出所有',
					iconCls:'icon-query-export',
					handler:function(){
						wechantTxnDetail_export();
						}
			}]  
		});
	});
	
	//资料导出
	function wechantTxnDetail_export() {
		$('#sysAdmin_20112_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/checkWechatTxnDetail_wechantTxnExport.action'
			    	});
	}
	
	//退款
	function sysAdmin_20112_editFun(index) {

		//获取操作对象
		var rows = $('#sysAdmin_20112_datagrid').datagrid('getRows');
		var row = rows[index];
		
		$('<div id="sysAdmin_20112_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;align:center">退款信息</span>',
			width: 800,
		    height:510,
		    closed: false,
		    href: '${ctx}/biz/check/20113.jsp?pwid='+row.pwid,
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_20112_datagrid').datagrid('getRows');
				var row = rows[index];
		    	row.menuIds = stringToList(row.pwid);    
		    	$('#sysAdmin_20113_editForm').form('load', row);
			},
			buttons:[{
				text:'确认退款',
				iconCls:'icon-ok',
				handler:function() {
					var inputs = document.getElementById("sysAdmin_20113_editForm").getElementsByTagName("input");

		    		for(var i=0;i<inputs.length;i++){
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
					$('#sysAdmin_20113_editForm').form('submit', {
						url:'${ctx}/sysAdmin/checkWechatTxnDetail_wechantTxnRefund.action?',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_20112_datagrid').datagrid('unselectAll');
				    				$('#sysAdmin_20112_datagrid').datagrid('reload');
					    			$('#sysAdmin_20112_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_20112_editDialog').dialog('destroy');
					    			$('#sysAdmin_20112_datagrid').datagrid('unselectAll');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
						    	}
					    	}
						}
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_20112_editDialog').dialog('destroy');
					$('#sysAdmin_20112_datagrid').datagrid('unselectAll');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_20112_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//表单查询
	function sysAdmin_merchantAuthenticity_searchFun20112() {
		var txnDay = $('#createDateStart_20112').datebox('getValue');
    	var txnDay1= $('#createDateEnd_20112').datebox('getValue');
        var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
        var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
        if((txnDay!=""&&txnDay1=="")||(txnDay1!=""&&txnDay=="")){
        	$.messager.alert('提示', "请选择开始日期 和 截止日期 ");
        	return;
        }
        if(txnDay!=null&&txnDay1!=null&&((end-start)/(24*60*60*1000))>=31){
	 		$.messager.alert('提示', "开始日期 和 截止日期 之差不能超过31天！");
	    }
	    else{
			$('#sysAdmin_20112_datagrid').datagrid('load', serializeObject($('#sysAdmin_20112_searchForm'))); 
		}
	}

	//清除表单内容
	function sysAdmin_merchantAuthenticity_cleanFun20112() {
		$('#sysAdmin_20112_searchForm input').val('');
		$('#sysAdmin_20112_searchForm select').val('全部');
	}
	//初始化datagrid
	$(function() {
			$('#unno20112').combogrid({
			url : '${ctx}/sysAdmin/CheckUnitProfitMicro_getProfitUnitGodesForSales.action',
			//url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes.action',
			idField:'UNNO',
			textField:'UN_NAME',
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'UN_NAME',title:'机构名称',width:150}			
			]]
		});
	});
</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:100px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_20112_searchForm" style="padding-left:2%" method="post">
		<input type="hidden" id="bmatids" name="bmatids" />
			<table class="tableForm" >
				<tr>
					<th>商户订单号</th>
					<td><input name="mer_orderId" style="width: 100px;" /></td>
					<td width="10px"></td>
					<th>机构</th>
					<td><select name="unno" id="unno20112" class="easyui-combogrid" style="width:100px;"></select></td>
					<td width="10px"></td>
					<th>商户编号</th>
					<td><input name="mid" style="width: 215px;" /></td>
					 <th>商户类型</th>
					<td>
						<select name="isM35" style="width: 100px;">
							<option value="" selected="selected">全部</option>
							<option value="6">聚合</option>
							<option value="0">线下</option>
						</select>
					</td>
					
				</tr>
				<tr>
				
					
				</tr>
				<tr>
					<th>支付渠道</th>
					<td>
						<select name="fiinfo2" style="width: 100px;">
							<option value="" selected="selected">全部</option>
							<option value="WX">微信</option>
							<option value="ZFB">支付宝</option>
							<option value="LMF">立码付</option>
							<option value="JD">京东</option>
							<option value="QQ">QQ</option>
							<option value="BDQB">百度钱包</option>
						</select>
					</td>
					<td width="10px"></td>
					<th>交易类型</th>
					<td>
						<select name="txnType" style="width: 120px;">
							<option value="" selected="selected">全部</option>
							<option value="0">消费</option>
							<option value="1">退款</option>
						</select>
					</td>
					<td width="20px"></td>
					<th>交易日期起</th>
					<td><input name="cdateStart" id="createDateStart_20112" class="easyui-datebox" style="width: 100px;"  />
						&nbsp;止 <input name="cdateEnd" id="createDateEnd_20112" class="easyui-datebox" style="width: 100px;"  />
					</td>
					<td width="60px"></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_searchFun20112();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merchantAuthenticity_cleanFun20112();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_20112_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>

