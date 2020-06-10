<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	
</script>
<form id="sysAdmin_30000_queryForm" method="post" enctype="multipart/form-data" >
	<fieldset>
		<legend>贷款信息</legend>
		<table class="table">
		   	<tr>
		   		<th>代理商名称：</th>
		   		<td><input type="text" readonly="readonly" name="AGENTNAME" style="width:200px;"/></td>
		   	
		   		<th>协议编号：</th>
	   			<td><input type="text" readonly="readonly" name="PROTOCOLNO" style="width:200px;"/></td>
		   	</tr>
		   	<tr>
			   	<th>贷款方式：</th>
	   			<td><select name="CRETYPE" style="width:205px;">
	   				<option value='0'>现金</option>
	   				<option value='1'>设备</option>
	   			</select></td>
	   			
	   			<th>贷款利率：</th>
	   			<td>
	   				<input type="text" readonly="readonly" name="CRERATE" style="width:200px;" /></td>
	   		</tr>
	   		<tr>
	   			<th>贷款金额：</th>
	   			<td>
	   				<input type="text" readonly="readonly" name="CREAMT" style="width:200px;" />
	   			</td>
		   	
		   		<th>未还金额：</th>
	   			<td><input type="text" readonly="readonly" name=SURPLUSAMT style="width:200px;"/></td>
	   		</tr>
		   	<tr>	
		   		<th>贷款日期：</th>
	   			<td>
	   				<input type="text" readonly="readonly" name="CREDATE" style="width:200px;" />
	   			</td>
	   			
	   			<th>到期时间：</th>
	   			<td>
	   				<input type="text" readonly="readonly" name="REPAYTIME" style="width:200px;" />
	   			</td>
	   		</tr>
		   	<tr>
	   			<th>贷款期限：</th>
	   			<td ><input type="text" readonly="readonly" name="CRETIMELIMIT" style="width:200px;"/></td>
		   		<th>审批标志：</th>
		   		<td><select name="APPROVEFLAG" style="width:205px;">
		   			<option value='0'>待审批</option>
		   			<option value='1'>已审批</option>
		   			<option value='2'>已拒绝</option>
		   		</select></td>
		   	
		   	</tr>
		   	<tr>
		   		<th>申请人：</th>
	   			<td><input type="text" readonly="readonly" name="PROPOSER" style="width:200px;" /></td>
		   	
		   		<th>申请人电话：</th>
		   		<td>
		   			<input type="text" readonly="readonly" name="PROPHONE" style="width:200px;"/>
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>是否放款：</th>
	   			<td><select name="LOANFLAG" style="width:205px;" >
	   				<option value='0'>未放款</option>
	   				<option value='1'>已放款</option>
	   			</select></td>
		   		<th>利息合计：</th>
	   			<td><input type="text" readonly="readonly" name="TOTALACCRUAL" style="width:200px;" /></td>
		   	</tr>
		   	<tr>
		   		<th>pos机单号：</th>
	   			<td><input type="text" readonly="readonly" name="POSINVOICENO" style="width:200px;" /></td>
	   			
		   		<th>汇款单号：</th>
	   			<td><input type="text" readonly="readonly" name="PAYNO" style="width:200px;" /></td>
	   		</tr>
		   	<tr>
		   		<th>放款时间：</th>
	   			<td><input type="text" readonly="readonly" name="LOANDATE" style="width:200px;" /></td>
	   		
		   		<th>是否逾期：</th>
	   			<td><select name="OVERDUEFLAG" style="width:205px;" >
	   				<option value='0'>正常</option>
	   				<option value='1'>逾期</option>
	   			</select></td>
	   		</tr>
		   	<tr>
		   		<th>下一还款日：</th>
		   		<td ><input type="text" readonly="readonly" name="NEXTREPAYDAY" style="width:200px;" /></td>
				<th>拒绝原因：</th>
	   			<td><input type="text" readonly="readonly" name="REFUSALREASON" style="width:200px;" /></td>
			</tr>
			<tr>
		   		<th>协议是否超期：</th>
		   		<td><select name="ISVALID" style="width:205px;" >
		   			<option value='0'>有效</option>
	   				<option value='1'>超期</option>
		   		</select></td>
		   		
		   		<th>合同起始时间：</th>
		   		<td><input type="text" readonly="readonly" name="contractPeriod" style="width:200px;" /></td>
			</tr>
			<tr>
				<th>受理描述</th>
				<td>
					<textarea rows="3" cols="50" style="resize:none;" name="processContext" readonly="readonly"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
