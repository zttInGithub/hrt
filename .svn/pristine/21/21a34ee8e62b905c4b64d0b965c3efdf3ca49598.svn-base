 /**
* 分页
* url：要提交到的地址
* params：提交到后台的参数
* pageID：总页数
* callFunc：回调函数，处理从获取的数据
*/
var AjaxPager ={
	$ : function(url,params,callFunc,pageID){
		var obj = {};
		
		obj.url=url;
		obj.params=params;
		obj.pageID=pageID;
		obj.getRowHtml=callFunc;
		obj.page=1;
		obj.pageCount=1;
		obj.FirstQuery="Y";
		obj.loading=false;
		obj.getPagerTip=function(){
			return "<div id='"+obj.pageID+".tip' style='text-align:center; height:16'>上拉加载更多</div>";
		}
		obj.runAs=function(){
			obj.params.page=obj.page;
			obj.params.FirstQuery=obj.FirstQuery;
			$.ajax({
				type : "POST",
				url : obj.url,
				contentType: "application/json; charset=utf-8",
				dataType : "json",
				data :  JSON.stringify(obj.params),
				success : function(data) {
					//是否分页
					var isPage=typeof(obj.pageID)!="undefined";
					var tip=document.getElementById(obj.pageID+".tip");
					if(tip){
						tip.parentNode.removeChild(tip);
					}
					//从回调方法中返回html
					var rows=null;
					//回调函数是否存在
					var isRowHtmlFunc=typeof(obj.getRowHtml)!="undefined";
					if(isRowHtmlFunc){
						rows=obj.getRowHtml(data);
					}
					
					obj.loading=false;//数据加载完
					//第一次加载数据
					if(obj.FirstQuery=="Y"){	
						obj.pageCount=data.pageCount;
						//分页处理
						if(isPage){
							//0为object，目前为document/window，1为其它元素
							var scrolType=typeof(obj.pageID)=="object"?0:1;
							var scrolObj=scrolType==0?obj.pageID:"#"+obj.pageID;
							if(typeof(obj.pageID)=="object"){
								scrolObj=obj.pageID
							}
							$(scrolObj).unbind("scroll").bind("scroll", function(e){  
							    var scrollHeight = 0;//滚动容器的高度
							    var height=0;//滚动的高度
							    if(scrolType==0){
							    	height=$(window).height();
							    	scrollHeight=$(document).height()-$(window).scrollTop()-3;
							    }else{
							    	scrollHeight=this.scrollHeight;  
							    	height=$(this).scrollTop() + $(this).height()+2;
							    }
							    
							    console.log(scrollHeight+"  -  "+height+"  "+obj.page+"  "+obj.pageCount);
							    if ((scrollHeight <= height) && //滚动到底部
							    		!obj.loading) {         //数据不在加载中
							    	if(obj.page>=obj.pageCount){
							    		//数据加载完提示
							    	}else{
							    		obj.loading=true;//数据加载中
//							    		console.log(scrollHeight+"  -  "+height+"  "+obj.page+"  "+obj.pageCount);
//							    		$("#"+obj.pageID+".tip").html("加载中...");
							    		obj.page=obj.page+1;//分页控件的起始页从1开始
										obj.runAs();
							    	}
							    }  
							});  
						}
					}
					//有分页功能，不是最后一页，加入滚动提示
					if((obj.page!=obj.pageCount) && obj.pageCount>1 && isPage){
						//rows.append(obj.getPagerTip());
						if(isRowHtmlFunc){
							if(typeof(rows)=="undefined" || rows==null){
								alert("请在回调函数["+obj.getRowHtml.name+"]中返回分页数据");
							}else{
								var item=rows.children().last();
								item.after(obj.getPagerTip());
							}
						}
					}
					obj.FirstQuery="N";
				}
			});
		}

		return obj;
	}
	
}
