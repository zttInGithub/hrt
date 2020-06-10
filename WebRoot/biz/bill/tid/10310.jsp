<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 库存信息 -->
<script type="text/javascript">
	$(function(){
		$('#machineModel_10310').combogrid({
			url : '${ctx}/sysAdmin/purchaseDetail_listMachineModel.action',
			idField:'MACHINEMODEL',
			textField:'MACHINEMODEL',
			mode:'remote',
			fitColumns:true,
			columns:[[ 
				{field:'MACHINEMODEL',title:'机型名称',width:150},
			]]
		});
		
		$('#rebateType_10310').combogrid({
			url : '${ctx}/sysAdmin/purchaseDetail_listPurConfigure.action?type=3',
			idField:'VALUEINTEGER',
			textField:'VALUEINTEGER',
			value:-1,
			mode:'remote',
			fitColumns:true,
			columns:[[
				{field:'NAME',title:'返利名称',width:150},
				{field:'VALUEINTEGER',title:'类型',width:150}
			]]
		});
	});
	//初始化datagrid
	$(function() {
	
		$("#unNO").combobox({
			url : '${ctx}/sysAdmin/terminalInfo_getUnitGodes.action',
			valueField : 'UNNO',
			textField : 'UN_NAME',
			onSelect:function(rec){
				$("#unNO").val(rec.UNNO);
				$('#sysAdmin_terminalUse_datagrid').datagrid('load', serializeObject($('#sysAdmin_terminalUse_searchForm')));
			}
		});
		$('#sysAdmin_terminalUse_datagrid').datagrid({
			url :'${ctx}/sysAdmin/terminalInfo_findUse.action',
			fit : true,
			fitColumns : true,
			border : false,
			nowrap : true,
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			/* ctrlSelect:true,
			checkOnSelect:true, */
			sortName: 'termID',
			sortOrder: 'asc',
			idField : 'btID',
			columns : [[{
				title :'唯一编号',
				field :'btID',
				width : 100,
				checkbox:true
			},{
				title :'机构编号',
				field :'unNO',
				width : 70,
				sortable:true
			},{
				title :'机构名称',
				field :'unitName',
				width : 80,
				sortable:true
			},{
				title :'终端编号',
				field :'termID',
				width : 80,
				sortable:true
			},{
				title :'SN号',
				field :'sn',
				width : 80,
				sortable:true
			},{
				title :'入库单号',
				field :'batchID',
				width : 110
			},{
				title :'出库单号',
				field :'terOrderID',
				width : 110
			},{
				title :'费率',
				field :'rate',
				width : 70,
				sortable:true
			},{
				title :'秒到手续费',
				field :'secondRate',
				width : 70,
				sortable:true
			},{
				title :'扫码费率',
				field :'scanRate',
				width : 70,
				sortable:true
			},{
				title :'活动类型',
				field :'rebateType',
				width : 75,
				sortable:true,
				formatter:function(value,row,index){
					if (value==null||value==''||value=='0'){
						return '无';
					}else if(value=='1'){
						return '循环送';
					}else if(value=='2'){
						return '激活返利';
					}else if(value=='3'){
						return '分期返利';
					}else if(value=='4'){
						return '购机返利';
					}else if(value=='5'){
						return '活动5';
					}else if(value=='6'){
						return '活动6';
					}else if(value=='7'){
						return '活动7';
					}else if(value=='8'){
						return '活动8';
					}else if(value=='9'){
						return '买断9';
					}else if(value=='10'){
						return '活动10';
					}else if(value=='11'){
						return '活动11';
					}else if(value=='12'){
						return '活动12';
					}else if(value=='13'){
						return '活动13';
					}else if(value==14){
						return '活动14';
					}else if(value==15){
						return '活动15';
					}else if(value==16){
						return '活动16';
					}else if(value==17){
						return '活动17';
					}else if(value==18){
						return '活动18';
					}else{
					   return "活动"+value;
					}
				}
			},{
				title :'押金金额',
				field :'depositAmt',
				width : 70,
				sortable:true,
				formatter:function(value,row,index){
					if(row.rebateType=='14'||row.rebateType=='15'){
						return value+1;//14,15返回120,但是数据库存的是119
					}else{
						return value;
					}
				}
			},{
				title :'机型',
				field :'machineModel',
				width : 70
			},{
				title :'库位',
				field :'storage',
				width : 70
			},{
				title :'蓝牙类型',
				field :'snType',
				width : 70,
				sortable:true,
				formatter:function(value,row,index){
					if(value=='1'){
						return '小蓝牙';
					}else if(value=='2'){
						return '大蓝牙';
					}
				}
			},{
				title :'入网时间/合成状态',
				field :'keyConfirmDate',
				width : 150,
				sortable:true
			},{
				title :'出库时间',
				field :'outDate',
				width : 150,
				sortable:true
			}/* ,{
				title :'密钥明文',
				field :'keyContext',
				width : 100,
				sortable:true
			} ,{
				title :'合成情况',
				field :'keyConfirmDate',
				width : 100,
				sortable:true,
				formatter:function(value,row,index){
					if(value==null){
						return '未合成';
					}else{
						return '已合成';
					}
				}
			}*/,{
				title :'分配情况',
				field :'allotConfirmDate',
				width : 65,
				sortable:true,
				formatter:function(value,row,index){
					if(value==null){
						return '未分配';
					}else{
						return '已分配';
					}
				}
			},{
				title :'使用情况',
				field :'usedConfirmDate',
				width : 60,
				sortable:true,
				formatter:function(value,row,index){
					if(value==null){
						return '未使用';
					}else{
						return '已使用';
					}
				}
			}
			/**,{
				title :'操作',
				field :'operation',
				width : 100,
				align : 'center',
				formatter : function(value,row,index) {
					if(row.usedConfirmDate == null&&row.sn !=null){
						return '<img src="${ctx}/images/start.png" title="修改费率" style="cursor:pointer;" onclick="sysAdmin_terminalinfo_queryFun('+index+')"/>&nbsp;&nbsp';
					}else{
						return '';
					}
				}
			}**/
			]]		
		});
	});
	
	//表单查询
	function sysAdmin_10310_searchFun() {
		$('#sysAdmin_terminalUse_datagrid').datagrid('load', serializeObject($('#sysAdmin_terminalUse_searchForm'))); 
	}

	//清除表单内容
	function sysAdmin_10310_cleanFun() {
		$('#sysAdmin_terminalUse_searchForm input').val('');
	}
	
</script>



<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:130px; overflow: hidden; padding-top:10px;">
		<form id="sysAdmin_terminalUse_searchForm" style="padding-left:2%">
			<table class="tableForm" >
				<tr>
					<th >自营机构号：</th>
					<td colspan="3"><!-- <input id="unNO" name="unNO" class="easyui-combobox" style="width: 150px;"/> -->
						<input name="unNO" style="width: 110px;"/>
						&nbsp;&nbsp;&nbsp;&nbsp;<b>归属机构号：</b><input name="unNO1" style="width: 110px;"/>
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终端编号：</th>
					<td  colspan="3">
						<input name="termIDStart" />&nbsp;至&nbsp;
						<input name="termIDEnd" />
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用情况：</th>
					<td >
						<select name="status">
							<option selected="selected" value="">全部</option>
							<option value="2">已使用</option>
							<option value="0">未使用</option>
						</select>
					</td>
				</tr>
				<tr>
					<th >入库单号：</th>
					<td colspan="3">
						<input name="batchID" style="width: 110px;"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>出库单号：</b>
						<input name="terOrderID" style="width: 110px;"/>
					</td>
					<th>SN号：</th>
					<td colspan="3">
						<input name="snStart" style="margin-left: -2px;" />&nbsp;至&nbsp;
						<input name="snEnd" />
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终端类型：</th>
					<td >
						<select name="isM35">
							<option selected="selected" value="">全部</option>
							<option value="1">-手刷 -</option>
							<option value="0">-传统 -</option>
							<option value="2">-智能 -</option>
						</select>
					</td>
					<td colspan="2" style="text-align: center;">&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_10310_searchFun();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_10310_cleanFun();">清空</a>	
					</td>
				</tr>
				<tr>
					<th>入网时间：</th>
					<td><input name="keyConfirmDate" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;</td>
					<td colspan="2"><input name="keyConfirmDateEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机型：</th>
					<td>
						<select id="machineModel_10310" name="machineModel"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 130px;"></select>
					</td>
					<th>蓝牙：</th>
					<td>
						<select name="snType" style="width:60px">
							<option selected="selected" value="">全部</option>
							<option value="1">小蓝牙</option>
							<option value="2">大蓝牙</option>
						</select>
					</td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库位：</th>
					<td>
						<select name="storage" style="width:60px">
							<option selected="selected" value="">全部</option>
							<option value="HRT">HRT</option>
							<option value="HYB">HYB</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>出库时间：</th>
					<td><input name="outDate" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/>&nbsp;至&nbsp;</td>
					<td colspan="2"><input name="outDateEnd" class="easyui-datebox" data-options="editable:false" style="width: 150px;"/></td>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;返利类型：</th>
					<td>
						<select id="rebateType_10310" name="rebateType"
						class="easyui-combogrid"
						data-options="editable:false" style="width: 130px;"></select>
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
       <table id="sysAdmin_terminalUse_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


