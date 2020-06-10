<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 
    Document   : 通用查询
    Created on : 2014年4月9日19:53:28
    Author     : lichao
    InitUrl    : http://127.0.0.1:8080/HrtApp/frame/sysadmin/query/query.jsp?LISTPAGECODE=920011
          参数名	   : LISTPAGECODE
--%>
<%
 String code=request.getParameter("LISTPAGECODE");
 %>
<script type="text/javascript">
	function findInfo(url) {
		$('<div id="sysAdmin_queryDialog"/>').dialog({
			title: '<span style="color:#157FCC;">操作</span>',
			width: 440,   
		    height: 300,
		    closed: false,
		    //href:'${ctx}/biz/bill/pos/10211.jsp',
		    href: '${ctx}'+url,  
		    modal: true/* ,
		    buttons:[{
		    	text:'确认',
		    	iconCls:'icon-ok',
		    	handler:function() {
		    		$('#sysAdmin_machineInfo_addForm').form('submit',{
			    		url:'${ctx}/sysAdmin/machineInfo_add.action',
		    			success:function(data) {
			    			var result = $.parseJSON(data);
			    			if (result.sessionExpire) {
			    				window.location.href = getProjectLocation();
				    		} else {
				    			if (result.success) {
				    				$('#sysAdmin_machineInfo_datagrid').datagrid('reload');
					    			$('#sysAdmin_machineInfo_addDialog').dialog('destroy');
					    			$.messager.show({
										title : '提示',
										msg : result.msg
									});
					    		} else {
					    			$('#sysAdmin_machineInfo_addDialog').dialog('destroy');
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
					$('#sysAdmin_machineInfo_addDialog').dialog('destroy');
				}
			}],
			onClose:function() {
				$(this).dialog('destroy');
			} */
		});
	}


	$(function(){
		var code='<%=code%>';
		$.ajax({
				cache: true,
                type: "POST",
                url:'${ctx}/query/query_init.action',
                data:{"LISTPAGECODE":code},
                async: false,
                error: function(request) {
                    alert("Connection error");
                },
                success: function(data) {
                	var json=eval("("+data+")");
                    getExportHTML(json);
                    getDisHTML(json);
                    getPageHTML(json);
                    getDataHTML(json);
                    getTitleHTML(json);
                    getColumnHTML(json);
                }
		});
	});

	function dosubmit(){
		$.ajax({
				cache: true,
                type: "POST",
                url:'${ctx}/query/query_search.action',
                dataType:"JSON",
                data:$("#myform").serialize(),
                async: false,
                error: function(request) {
                    alert("Connection error");
                },
                success: function(data) {
                	//var json=eval("("+data+")");
                	var json=data;
                    getExportHTML(json);
                    getDisHTML(json);
                    getPageHTML(json);
                    getDataHTML(json);
                    getTitleHTML(json);
                    getColumnHTML(json);
                }
		});
	}
	//导出form表单
	function getExportHTML(json){
		var html="";
		var ele=$("#export");
		html+='<input type="hidden" name="DISCOLUMNSS" id="DISCOLUMNSS" value="';
		if(json.DISCOLUMNS==null||json.DISCOLUMNS==""){
			html+='">';
		}else{
			html+=json.DISCOLUMNS+'">';
		}
		html+='<input type="hidden" name="LISTPAGECODEE" id="LISTPAGECODEE" value="'+json.LISTPAGECODE+'" >';
		html+='<input type="hidden" name="FLAG" id="FLAG" >';
		html+='<input type="hidden" name="PAGE1" id="PAGE1" value="'+json.PAGE+'" >';
		$.each(json.COLUMNS,function(idx,item){
			if(item.searchable==true){
				html+='<input type="hidden"  name="'+item.name+'S" value="';
				if(json[item.name]==null||json[item.name]==""){
					html+='"   />';
				}else{
					html+=json[item.name]+'"   />';
				}
			}
		});
		ele.empty();
		ele.html(html);
	}
	//选择显示列
	function getDisHTML(json){
		var html="";
		var ele=$("#columntable");
		$.each(json.ALLCOLUMNS,function(idx,item){
			if(idx%2==0){
				html+='<tr>';
			}
			html+='<td><div class="datagrid-cell" style="width: 100px; text-align: left;">';
			html+='<input type="checkbox" value="'+item.name+'" ><span>'+item.title+'</span>';
			html+='<span class="datagrid-sort-icon">&nbsp;</span></div></td>';
			if(idx%2==1){
				html+='</tr>';
			}
		});
		ele.empty();
		ele.html(html);
	}
	//分页组件
	function getPageHTML(json){
		var html="";
		var ele=$("#pagination");
		html+='<table  border="0" cellspacing="0" cellpadding="0"><tbody><tr>';
		if(json.PAGENUM<=1){
			html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain l-btn-disabled">';
			html+='<span class="l-btn-left"><span class="l-btn-text">';
			html+='<span class="l-btn-empty pagination-first">&nbsp;</span></span></span></a></td>';
			html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain l-btn-disabled"><span class="l-btn-left">';
			html+='<span class="l-btn-text"><span class="l-btn-empty pagination-prev">&nbsp;</span>';
			html+='</span></span></a></td>';
			html+='<td><div class="pagination-btn-separator"></div></td>';
			html+='<td><span style="padding-left:6px;">第</span></td>';
			html+='<td><input id="PAGE" name="PAGE" type="text" size="2" value="'+json.PAGE+'" class="pagination-num"></td>';
			html+='<td><span style="padding-right:6px;">页，共'+json.PAGENUM +'页</span></td>';
			html+='<td><div class="pagination-btn-separator"></div></td>';
			html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain l-btn-disabled" id="" onclick=getPage("after")>';
			html+='<span class="l-btn-left"><span class="l-btn-text"><span class="l-btn-empty pagination-next">&nbsp;</span>';
			html+='</span></span></a></td>';
			html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain l-btn-disabled" id="" onclick=getPage("last")>';
			html+='<span class="l-btn-left"><span class="l-btn-text"><span class="l-btn-empty pagination-last">&nbsp;</span>';
			html+='</span></span></a></td>';
		}else{
			if(json.PAGE==1){
				html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain l-btn-disabled">';
				html+='<span class="l-btn-left"><span class="l-btn-text"><span class="l-btn-empty pagination-first">&nbsp;</span>';
				html+='</span></span></a></td>';
				html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain l-btn-disabled"><span class="l-btn-left">';
				html+='<span class="l-btn-text"><span class="l-btn-empty pagination-prev">&nbsp;</span>';
				html+='</span></span></a></td>';
				html+='<td><div class="pagination-btn-separator"></div></td>';
				html+='<td><span style="padding-left:6px;">第</span></td>';
				html+='<td><input id="PAGE" name="PAGE" type="text" size="2" value="'+json.PAGE +'" class="pagination-num"></td>';
				html+='<td><span style="padding-right:6px;">页，共'+json.PAGENUM+'页</span></td>';
				html+='<td><div class="pagination-btn-separator"></div></td>';
				html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain" id="" onclick=getPage("after")>';
				html+='<span class="l-btn-left"><span class="l-btn-text"><span class="l-btn-empty pagination-next">&nbsp;</span>';
				html+='</span></span></a></td>';
				html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain" id="" onclick=getPage("last")>';
				html+='<span class="l-btn-left"><span class="l-btn-text"><span class="l-btn-empty pagination-last">&nbsp;</span>';
				html+='</span></span></a></td>';
			}else if(json.PAGE==json.PAGENUM){
				html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain" id="" onclick=getPage("first")>';
				html+='<span class="l-btn-left"><span class="l-btn-text"><span class="l-btn-empty pagination-first">&nbsp;</span>';
				html+='</span></span></a></td>';
				html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain" id="" onclick=getPage("fornt")>';
				html+='<span class="l-btn-left"><span class="l-btn-text">';
				html+='<span class="l-btn-empty pagination-prev">&nbsp;</span></span></span></a></td>';
				html+='<td><div class="pagination-btn-separator"></div></td>';
				html+='<td><span style="padding-left:6px;">第</span></td>';
				html+='<td><input id="PAGE" name="PAGE" type="text" size="2" value="'+json.PAGE +'" class="pagination-num"></td>';
				html+='<td><span style="padding-right:6px;">页，共'+json.PAGENUM +'页</span></td>';
				html+='<td><div class="pagination-btn-separator"></div></td>';
				html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain l-btn-disabled">';
				html+='<span class="l-btn-left"><span class="l-btn-text">';
				html+='<span class="l-btn-empty pagination-next">&nbsp;</span>';
				html+='</span></span></a></td>';
				html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain l-btn-disabled"><span class="l-btn-left">';
				html+='<span class="l-btn-text"><span class="l-btn-empty pagination-last">&nbsp;</span>';
				html+='</span></span></a></td>';
			}else{
				html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain" id="" onclick=getPage("first")>';
				html+='<span class="l-btn-left"><span class="l-btn-text"><span class="l-btn-empty pagination-first">&nbsp;</span>';
				html+='</span></span></a></td>';
				html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain" id="" onclick=getPage("fornt")>';
				html+='<span class="l-btn-left"><span class="l-btn-text">';
				html+='<span class="l-btn-empty pagination-prev">&nbsp;</span>';
				html+='</span></span></a></td>';
				html+='<td><div class="pagination-btn-separator"></div></td>';
				html+='<td><span style="padding-left:6px;">第</span></td>';
				html+='<td><input id="PAGE" name="PAGE" type="text" size="2" value="'+json.PAGE +'" class="pagination-num"></td>';
				html+='<td><span style="padding-right:6px;">页，共'+json.PAGENUM+'页</span></td>';
				html+='<td><div class="pagination-btn-separator"></div></td>';
				html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain" id="" onclick=getPage("after")>';
				html+='<span class="l-btn-left"><span class="l-btn-text">';
				html+='<span class="l-btn-empty pagination-next">&nbsp;</span>';
				html+='</span></span></a></td>';
				html+='<td><a href="javascript:void(0)" class="l-btn l-btn-plain" id="" onclick=getPage("last")>';
				html+='<span class="l-btn-left"><span class="l-btn-text">';
				html+='<span class="l-btn-empty pagination-last">&nbsp;</span>';
				html+='</span></span></a></td>';
			}
		}
		ele.empty();
		ele.html(html);
	}
	//数据查询列
	function getDataHTML(json){
		var html="";
		var ele=$("#datatable");
		html+='<tbody><tr id="search" class="datagrid-row" style="display:'+json.ISSEARCH+';">';
		$.each(json.COLUMNS,function(idx,item){
			if(item.searchable==true ){
				html+='<td><div class="datagrid-cell" style="text-align:left;height:auto;width:'+item.width+'px;">'; 
				html+='<input type="text" id="'+item.name +'" style="width: '+item.width +'px;"  name="'+item.name +'" value="';
				if(json[item.name]==null||json[item.name]==""){
					html+='" onblur="searchData()" />';
				}else{
					html+=json[item.name]+'" onblur="searchData()" />';
				}
				html+='	</div></td>';
			}else{
				html+='<td><div class="datagrid-cell" style="text-align:left;height:auto;width:'+item.width+'px;">&nbsp;</div></td>';
			}
		});
		$.each(json.OPERATIONS,function(idx,item){
			html+='<td class=""><div class="datagrid-cell" style="text-align:left;height:auto;width:'+item.width+'px;">&nbsp;</div></td>';
		});
		html+='</tr>';
		$.each(json.LIST,function(idex,items){
			html+='<tr class="datagrid-row">';
			$.each(json.COLUMNS,function(idx,item){
				html+='<td field="'+item.name +'"><div class="datagrid-cell"';
				if(items[item.name]==null||items[item.name]==""){
					html+='style="text-align:left;height:auto;width: '+item.width+'px;">&nbsp;</div></td>';
				}else{
					html+='style="text-align:left;height:auto;width: '+item.width+'px;">'+items[item.name] +'</div></td>';
				}
				
			});
			$.each(json.OPERATIONS,function(idx,item){
				html+='<td field=""><div class="datagrid-cell"';
				html+='style="text-align:center;height:auto;width: '+item.width+'px;">';
				html+='<button onclick="findInfo(&quot;'+item.action+'&quot;)">'+item.content +'</button></div></td>';
			});
			html+='</tr>';
		});
		html+='</tbody>';
		ele.empty();
		ele.html(html);
	}
	//查询form表单
	function getTitleHTML(json){
		var html="";
		var ele=$("#title");
		html+='<input type="hidden" name="PAGENUM" id="PAGENUM" value="'+json.PAGENUM +'">';
		html+='<input type="hidden" name="LISTPAGECODE" id="LISTPAGECODE" value="'+json.LISTPAGECODE +'">';
		html+='<input type="hidden" name="PAGESIZE" id="PAGESIZE" value="'+json.PAGESIZE +'">';
		html+='<input type="hidden" name="TOTAL" id="TOTAL" value="'+json.TOTAL +'">';
		html+='<input type="hidden" name="ISSEARCH" id="ISSEARCH" value="'+json.ISSEARCH +'">';
		html+='<input type="hidden" name="DISCOLUMNS" id="DISCOLUMNS" value="';
		if(json.DISCOLUMNS==null||json.DISCOLUMNS==""){
			html+='">';
		}else{
			html+=json.DISCOLUMNS+'">';
		}
		html+='<input type="hidden" name="SORT" id="SORT" value="'+json.SORT +'">';
		html+='<input type="hidden" name="ORDER" id="ORDER" value="';
		if(json.ORDER==null||json.ORDER==""){
			html+='">';
		}else{
			html+=json.ORDER +'">';
		}
		ele.empty();
		ele.html(html);
	}
	//动态列
	function getColumnHTML(json){
		var html="";
		var ele=$("#columnid");
		$.each(json.COLUMNS,function(idx,item){
			html+='<td field="'+item.name +'" class="">';
			html+='<div class="datagrid-cell" style="width: '+item.width +'px; text-align: left;" onclick=sort("'+item.name +'")>';
			html+='<span>'+item.title +'</span><span class="datagrid-sort-icon">&nbsp;</span></div></td>';
		});
		$.each(json.OPERATIONS,function(idx,item){
			html+='<td class=""><div class="datagrid-cell" style="width: '+item.width +'px; text-align: left;">';
			html+='<span>'+item.title +'</span><span class="datagrid-sort-icon">&nbsp;</span></div></td>';
		});
		ele.empty();
		ele.html(html);
	}
	function getPage(type){
		var page=$("#PAGE").val();
		var pageNum=$("#PAGENUM").val();
		if("fornt"==type){
			$("#PAGE").val(Number(page)-1);
		}else if("after"==type){
			$("#PAGE").val(Number(page)+1);
		}else if("last"==type){
			$("#PAGE").val(pageNum);
		}else{
			$("#PAGE").val(1);
		}
		//$("#myform").submit();
		dosubmit();
	}
	
	function sort(col){
		$("#ORDER").val(col);	
		dosubmit();
	}
	function chageSearch(){
		$("#search").toggle();
	}
	function searchData(){
		$("#ISSEARCH").val(true);
		$("#PAGE").val(1);
		dosubmit();
	}
	
	function disMenu(){
		$("#expmenu").toggle();
	}
	
	function chengeRow(){
		$("#w").window('open');
	}
	
	function exportExcel(type){
		disMenu();
		$("#FLAG").val(type);
	    $("#exportform").submit();
	}
	function procReturn(){
		var r=$(":checkbox");
		var returnData="";  
   		for(var i=0;i<r.length;i++){
         if(r[i].checked){
         	returnData+=r[i].value+",";
         }
      	}      
      	$("#DISCOLUMNS").val(returnData);
		$('#w').window('close');
		dosubmit();
	}
</script>
<!-- <div class="easyui-layout" data-options="fit:true, border:false"> -->
<div>
<form action="${ctx}/query/query_exportExl.action" id="exportform">
<div id="export" style="display: none;">

<%-- <input type="hidden" name="DISCOLUMNS" id="DISCOLUMNS" value="${DISCOLUMNS }">
<input type="hidden" name="LISTPAGECODE" id="LISTPAGECODE" value="${LISTPAGECODE }">
<input type="hidden" name="FLAG" id="FLAG" value="${FLAG }">
<input type="hidden" name="PAGE1" id="PAGE1" value="${PAGE }">
<c:forEach items="${COLUMNS}" var="column">
	<c:if test="${column.searchable==true }">
	<input type="hidden"  name="${column.name }" value="${ requestScope[column.name] }"  />
	</c:if>
</c:forEach> --%>

</div>
</form>
<form  id="myform" onsubmit="return false">
	<div id="title" ><%-- ${TITLE}
	<input type="hidden" name="PAGENUM" id="PAGENUM" value="${PAGENUM }">
	<input type="hidden" name="LISTPAGECODE" id="LISTPAGECODE" value="${LISTPAGECODE }">
	<input type="hidden" name="PAGESIZE" id="PAGESIZE" value="${PAGESIZE }">
	<input type="hidden" name="TOTAL" id="TOTAL" value="${TOTAL }">
	<input type="hidden" name="ISSEARCH" id="ISSEARCH" value="${ISSEARCH }">
	<input type="hidden" name="DISCOLUMNS" id="DISCOLUMNS" value="${DISCOLUMNS }">
	<input type="hidden" name="SORT" id="SORT" value="${SORT }">
	<input type="hidden" name="ORDER" id="ORDER" value="${ORDER }"> --%>
	</div>
	<div class="datagrid-wrap panel-body panel-body-noborder" title="" style="width: 100%; bottom:0;">
		<div class="datagrid-toolbar">
			<a href="javascript:void(0)" style="float: left;" class="l-btn l-btn-plain"  onclick="chageSearch()" >
			<span class="l-btn-left">
			<span class="l-btn-text">
			<span class="l-btn-empty icon-query-search">&nbsp;  </span>
			</span>
			</span>
			</a> 
			<div class="datagrid-btn-separator"></div>
			<a href="javascript:void(0)" style="float: left;" class="l-btn l-btn-plain" id="exportId" onclick="disMenu()">
			<span class="easyui-splitbutton">
			<span class="l-btn-left" style="margin-top: -4px;">
			<span class="l-btn-text">
			<span class="l-btn-empty icon-query-export">&nbsp; </span>
			</span>
			</span>
			</span>
			</a> 
			<div class="datagrid-btn-separator"></div>
			<a href="javascript:void(0)" style="float: left;" class="l-btn l-btn-plain" id="" onclick="chengeRow()">
			<span class="l-btn-left">
			<span class="l-btn-text">
			<span class="l-btn-empty icon-query-filter">&nbsp;  </span>
			</span>
			</span>
			</a>
		</div>
<div class="datagrid-view" style="width: 100%; height: 359px;overflow:x-scroll;">
	<div class="datagrid-view1" style="width: 0px;">
	</div>
	
		<div class="datagrid-view2" style="width: 100%; left: 0px;">
			<div class="datagrid-header" style="width: 100%; height: 24px;">
			<div class="datagrid-header-inner" style="display: block; ">
				<table border="0" cellspacing="0" cellpadding="0" class="datagrid-htable" style="height: 25px;">
					<tbody>
					
					
								<tr id="columnid" class="datagrid-header-row">
								<%-- <c:forEach items="${COLUMNS}" var="column">
									<td field="${column.name }" class=""><div class="datagrid-cell" style="width: ${column.width }px; text-align: left;" onclick="sort('${column.name }')">
											<span>${column.title }</span><span class="datagrid-sort-icon">&nbsp;</span>
										</div>
									</td>
								</c:forEach>
								<c:forEach items="${OPERATIONS}" var="operation">
									<td class=""><div class="datagrid-cell"
											style="width: ${operation.width }px; text-align: left;">
											<span>${operation.title }</span><span class="datagrid-sort-icon">&nbsp;</span>
										</div>
									</td>
								</c:forEach> --%>
								</tr>
								
								
							</tbody>
				</table>
				</div>
			</div>
			<div class="datagrid-body" style="width: 100%; height: 334px;">
					<table id="datatable" border="0" cellspacing="0" cellpadding="0" class="datagrid-btable" style="table-layout: auto;">
					</table>
				</div>
			</div>
		</div>
		
		<!-- 分页组件 -->
		<div style="position:absolute; bottom:0; width:100%;">
			<div id="pagination" class="datagrid-pager pagination" style="position:absolute; bottom:0; width:100%;">
			<div class="pagination-info">显示${(PAGE-1)*PAGESIZE+1 }到${PAGE*PAGESIZE }条,共${TOTAL }条记录</div>
			<div style="clear:both;"></div>
		</div>
		</div>
	</div>
</form>
<div id="w" class="easyui-window" data-options="title:'筛选列',inline:true,closed:true," style="width:250px;height:330px;padding:10px">
<div>
<p class="standardTitle"><B>请选择您要显示的列</B></p>
<table id="columntable">

</table>
<input type="button" name="ok" value="确定" onclick="procReturn();"  />
<input type="button" name="cancel" value="取消" onclick="$('#w').window('close')"/>

</div>
</div>


<div style="width: 150px; display:none ; left: 35px; top: 80px;" id="expmenu" class="menu-top menu">
        <div onclick="exportExcel('exccuu');" name="" href="" class="menu-item" style="height: 20px;"><div class="menu-text">导出当前数据为excel</div></div>
        <div onclick="exportExcel('excall');" icon="icon-redo" name="" href="" class="menu-item" style="height: 20px;"><div class="menu-text">导出全部数据为excel</div></div>
        <div class="menu-sep">&nbsp;</div>
        <div onclick="exportExcel('excnull');" name="" href="" class="menu-item" style="height: 20px;"><div class="menu-text">导出无数据excel</div></div>
 </div>
</div>

