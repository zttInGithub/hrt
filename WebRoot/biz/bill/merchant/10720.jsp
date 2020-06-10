<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	//初始化datagrid
	$(function() {
		$('#sysAdmin_merBankCard_datagrid').datagrid({
			url :'${ctx}/phone/phoneMerchantWallet_queryMerchantBankCardListPC.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			remoteSort:true,
			idField : 'MBCID',
			columns : [[{
				title : '商户ID',
				field : 'MID',
				width : 100
			},{
				title : '银行卡号',
				field : 'BANKACCNO',
				width : 100
			},{
				title : '入账人名称',
				field : 'BANKACCNAME',
				width : 100
			},{
				title : '支付系统行号',
				field : 'PAYBANKID',
				width : 100
			},{
				title :'创建时间',
				field :'CREATEDATE',
				width : 100
			},{
				title :'类型',
				field :'MERCARDTYPE',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.MERCARDTYPE==0){
						return '默认';
					}else{
						return '添加';
						}
					
				}
			},{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.MERCARDTYPE==1){
						return '<img src="${ctx}/images/frame_pencil.png" title="修改银行卡信息" style="cursor:pointer;" onclick="sysAdmin_merBankCard_editFun('+index+')"/>&nbsp;&nbsp';
						}

				}
			}]]
		});
	});
	
	function sysAdmin_merBankCard_editFun(index,isForeign,merchantType,bmid){
		$('<div id="sysAdmin_merBankCard_editDialog"/>').dialog({
			title: '<span style="color:#157FCC;">修改银行卡信息</span>',
			width: 380,
		    height:240, 
		    closed: false,
		    href: '${ctx}/biz/bill/merchant/10721.jsp',
		    modal: true,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_merBankCard_datagrid').datagrid('getRows');
				var row = rows[index];
		    	$('#sysAdmin_merBankCard_editForm').form('load', row);
			},
			buttons:[{
				text:'确认',
				iconCls:'icon-ok',
				handler:function() {
					var inputs = document.getElementById("sysAdmin_merBankCard_editForm").getElementsByTagName("input");
		    		for(var i=0;i<inputs.length;i++){
		    	    	if(inputs[i].type=="file"){continue;}
		    			inputs[i].value = $.trim(inputs[i].value);
		    		}
		    		
					$('#sysAdmin_merBankCard_editForm').form('submit', {
						url:'${ctx}/phone/phoneMerchantWallet_updateMerchantBankCard.action',
						success:function(data) {
							var result = $.parseJSON(data);
							if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_merBankCard_datagrid').datagrid('reload');
					    			$('#sysAdmin_merBankCard_editDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$.messager.alert('提示', result.msg);
						    	}
					    	}
						}
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#sysAdmin_merBankCard_editDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			}
		});
	}

	//表单查询
	function sysAdmin_merBankCard_searchFun() {
		$('#sysAdmin_merBankCard_datagrid').datagrid('load', serializeObject($('#sysAdmin_merBankCard_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_merBankCard_cleanFun10() {
		$('#sysAdmin_merBankCard_searchForm input').val('');
	}
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:60px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_merBankCard_searchForm" style="padding-left:10%">
			<table class="tableForm" >
				<tr>
					<td style="width:150px;"></td>
					<th>商户编号</th>
					<td><input name="mid" style="width: 216px;" /></td>
					<td colspan="5" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_merBankCard_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_merBankCard_cleanFun10();">清空</a>	
					</td>
				</tr> 
			</table>
		</form>
	</div>  
 
 	<div data-options="region:'center', border:false" style="overflow: hidden;">  
		<table id="sysAdmin_merBankCard_datagrid" style="overflow: hidden;"></table> 
	</div>
</div>

