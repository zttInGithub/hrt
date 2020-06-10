<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	//初始化datagrid
	$(function() {
		$('#sysAdmin_merchantList4803_datagrid').datagrid({
			url :'${ctx}/sysAdmin/merchant_listMerchantInfoY3.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			nowrap : true,
			ctrlSelect:true,
			checkOnSelect:true,
			pageSize : 10,
			pageList : [ 10, 15 ],
			sortName: 'joinConfirmDate',
			sortOrder: 'desc',
			idField : 'bmid',
			columns : [[{
				field : 'bmid',
				checkbox:true
			},{
				title : '机构名称',
				field : 'unitName',
				width : 120
			},{
				title :'商户编号',
				field :'mid',
				width : 130,
				sortable :true
			},{
				title :'商户注册名称',
				field :'rname',
				width : 100,
				sortable :true
			},{
				title :'商户审批时间',
				field :'approveDate',
				width : 120,
				sortable :true
			},{
			    title :'受理状态',
				field :'approveStatusName',
				width : 80
			}
			]],
			toolbar:[]
		});
	});
	
	function sysAdmin_merchantterminalinfo_exportFun() {
		var txnDay = $('#createDateStart_104801').datebox('getValue');
    	var txnDay1= $('#createDateEnd_104801').datebox('getValue');
        var start=new Date(txnDay.replace("-", "/").replace("-", "/"));  
        var end=new Date(txnDay1.replace("-", "/").replace("-", "/")); 
        if(txnDay!=null&&txnDay1!=null&&((end-start)/(24*60*60*1000))>=31){
	 		$.messager.alert('提示', "开始日期 和 截止日期 之差不能超过31天！");
	    }
	    else{
			$('#sysAdmin_104803_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_exportMer.action'
			});
	    }
	}
	function sysAdmin_merchantterminalinfoSelected_exportFun() {
		var checkedItems = $('#sysAdmin_merchantList4803_datagrid').datagrid('getChecked');
		var names = [];
		$.each(checkedItems, function(index, item){
			names.push(item.bmid);
		});               
		var bmids=names.join(",");
		if(bmids==null||bmids==""){
			$.messager.alert('提示',"请勾选操作列");
			return;
		}
		$("#bmids_480").val(bmids);
		$('#sysAdmin_104803_searchForm').form('submit',{
			    		url:'${ctx}/sysAdmin/merchant_exportMerSelected.action'
			    	});
	}
	
	//查看明细
	function sysAdmin_merchantList4801_queryFun(index,bmid){
		$('<div id="sysAdmin_merchantList4801_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看商户明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    modal: true,
		    href: '${ctx}/biz/bill/merchant/10484.jsp?bmid='+bmid,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_merchantList4803_datagrid').datagrid('getRows');
				var row = rows[index];
				row.bankAccNo=row.bankAccNo.replace(/[\s]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
				console.log(row.secondRate)
				if(row.secondRate!=""&&row.secondRate!=null){
					var first = row.secondRate.substring(0,1);
					if(first=="\.")row.secondRate="0"+row.secondRate;
				}
		    	$('#sysAdmin_merchantList_queryForm').form('load', row);
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantList4801_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_merchantList4803_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//查看明细
	function sysAdmin_merchantList_queryFun(index,bmid){
		$('<div id="sysAdmin_merchantList_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">查看商户明细</span>',
			width: 900,
		    height:550, 
		    closed: false,
		    modal: true,
		    href: '${ctx}/biz/bill/merchant/10484_1.jsp?bmid='+bmid,
		    onLoad:function() {
		    	var rows = $('#sysAdmin_merchantList_datagrid').datagrid('getRows');
				var row = rows[index];
				row.bankAccNo=row.bankAccNo.replace(/[\s]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
				console.log(row.secondRate)
				if(row.secondRate!=""&&row.secondRate!=null){
					var first = row.secondRate.substring(0,1);
					if(first=="\.")row.secondRate="0"+row.secondRate;
				}
                if(row.contactPhone!=""&&row.contactPhone!=null && row.contactPhone.length==11){
                    row.contactPhone=row.contactPhone.substring(0,3)+'****'+row.contactPhone.substring(row.contactPhone.length-4,row.contactPhone.length);
                }
		    	$('#sysAdmin_merchantList_queryForm').form('load', row);
			},
			buttons:[{
				text:'关闭',
				iconCls:'icon-ok',
				handler:function() {
					$('#sysAdmin_merchantList_queryDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
				$('#sysAdmin_merchantList_datagrid').datagrid('unselectAll');
			}
		});
	}
	
	//表单查询
	function sysAdmin_104803_searchFun80() {
		$('#sysAdmin_merchantList4803_datagrid').datagrid('load', serializeObject($('#sysAdmin_104803_searchForm')));
	}

	//清除表单内容
	function sysAdmin_104803_cleanFun80() {
		$('#sysAdmin_104803_searchForm input').val('');
	}
	
	//查看终端
	function sysAdmin_104801_queryFun(mid){
		$('<div id="sysAdmin_104801_run"/>').dialog({
			title: '查看终端',
			width: 850,   
		    height: 400, 
		    closed: false,
		    href:'${ctx}/biz/bill/merchant/10487.jsp?mid='+mid,  
		    modal: true,
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	function sysAdmin_erweima_queryImage(mid) { 
		var qrurl="";
		//var timestamp= (new Date()).valueOf();
		//var img='${ctx}/sysAdmin/checkReceiptsOpreation_queryQrCodeImageShow.action?mid='+mid+'&timestamp='+timestamp;
		var url = '${ctx}/sysAdmin/merchant_queryMerchanWithQRUrl.action?mid='+mid;
		$.ajax({
			type: 'GET',
			//async: false, //是否异步
			url: url,
			dataType: 'json',
			success:function(data) {
				qrurl=data.msg;
				showBigImg(qrurl);
			}
		});
	}
	//查看二维码
	function showBigImg(qrurl){
		url=qrurl.replace("&", "");
		//$('#sysAdmin_10492_run').qrcode("http://www.helloweba.com"); 
		//$('<div id="sysAdmin_10492_run"><img src="sysAdmin/rand_ImageShow.action?upload='+rowData.qrUpload+'" alt="加载失败" style="width:380px;height:380px"></div>').dialog({
		$('<div id="showBigImg" />').dialog({
			title: '查看二维码',
			width: 500,   
		    height: 400, 
		    closed: false,
		    href:'${ctx}/biz/bill/merchant/104801QR.jsp?url='+url,  
		    modal: true,
			onClose : function() {
				$(this).dialog('destroy');
			}  
		});
	}
	/* function showBigImg(img){
		var imgDialog = "<div id='sysAdmin_imgDialog3' style='padding:10px;'><img id='img' style='width:480px;height:550px'></div>";
		$('#sysAdmin_merchantList4803_datagrid').after(imgDialog); 
		$("#img").attr("src",img);
		$('#sysAdmin_imgDialog3').dialog({
				title: '<span style="color:#157FCC;">查看</span>',
				width: 600,   
			    height: 600,
			    resizable:true,
		    	maximizable:true, 
			    modal:true,
			    buttons:[{
					text:'顺时针',
					iconCls:'',
					handler:function() {
						rotateRight();
					}
				},{
					text:'逆时针',
					iconCls:'',
					handler:function() {
						rotateLeft();
					}
				}],
				onClose:function() {
					$('#sysAdmin_imgDialog3').remove();
					rotation=0;
					rotate=0;
				}
			});
	} */

</script>


<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'north',border:false" style="height:80px; overflow: hidden; padding-top:20px;">
		<form id="sysAdmin_104803_searchForm" style="padding-left:3%" method="post">
		<input type="hidden" id="bmids_480" name="bmids" />
			<table class="tableForm" >
				<tr>
					<th>商户编号</th>
					<td><input name="mid" style="width:120px;" /></td>
				    
					<td colspan="4" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 
						onclick="sysAdmin_104803_searchFun80();">查询</a> &nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" 
						onclick="sysAdmin_104803_cleanFun80();">清空</a>	
					</td>
				</tr>
			</table>
		</form>
	</div>  
	
    <div data-options="region:'center', border:false" style="overflow: hidden;">  
      <table id="sysAdmin_merchantList4803_datagrid" style="overflow: hidden;"></table>
    </div> 
</div>


