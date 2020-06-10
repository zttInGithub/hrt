<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <style>
.table1 th{
	width:120px;
}
.table1 td{
	width:300px;
}
</style>
  <div style="height:125px;padding-top:10px;padding-left: 30px">
       <form method="post" enctype="multipart/form-data" id="frmBjjz10442" onsubmit="subbutton.disabled=1">
		<input type="hidden" name="file10442Name" id="file10442_Name"/>
		<br/>
           <table class="table1">
                <tr>
	   				<th>数量：</th>
	   				<td>
		    			<input type="text" name="MACHINENUM" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
   					</td>
   					<th>已出：</th>
	   				<td>
		    			<input type="text" name="IMPORTNUM" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'" readonly="readonly">
   					</td>
   				</tr>
   				<tr>
   					<th>发货数量：</th>
	   				<td>
		    			<input type="text" name="deliveNum" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true">
   					</td>
   					<th>收货联系人：</th>
	   				<td>
		    			<input type="text" name="deliverContacts" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true">
   					</td>
   				</tr>
                <tr>
   					<th>收货联系电话：</th>
	   				<td>
		    			<input type="text" name="deliverContactPhone" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true">
   					</td>
   					<th>收货联系邮箱：</th>
	   				<td>
		    			<input type="text" name="deliverContactMail" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'">
   					</td>
                </tr>
                <tr>
   					<th>收货地址：</th>
	   				<td>
		    			<input type="text" name="deliverReceiveaddr" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator',required:true">
   					</td>
   					<th>邮编：</th>
	   				<td>
		    			<input type="text" name="postCode" style="width: 150px;" class="easyui-validatebox" data-options="validType:'spaceValidator'">
   					</td>
                </tr>
                <input type="hidden" name="deliverOrderID" id="deliverOrderID_10442">
                <input type="hidden" name="deliverPurName" id="deliverPurName_10442" >
                <input type="hidden" name="poid" id="poid_10442" >
                <input type="hidden" name="pdid" id="pdid_10442" >
                <input type="hidden" name="unno" id="unno_10442" >
            </table>
     </form>
</div>