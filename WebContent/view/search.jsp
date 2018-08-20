<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>搜索</title>
    <script src="static/js/jquery-1.12.3.min.js"></script>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/css/jquery.bootgrid.min.css">
    <script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="static/js/jquery.bootgrid.min.js"></script>
    <!--引入layui样式  -->
    <link rel="stylesheet" href="static/css/layui.css">
    <script src="static/layui.js"></script>
    <!-- 引入自定义样式 -->
    <link rel="stylesheet" href="static/css/index.css">
</head>
<body>
<div class="container" style="margin-top: 50px">
    <div class="row">
	    <form class="layui-form" action="">
	     <div class="col-md-10">
		  <div class="layui-form-item"> 
		    <div class="layui-input-block">
		      <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
		    </div>
		    </div>
		</div>
		 <div class="col-md-2">
		   <div class="layui-form-item">
			    <button class="layui-btn" lay-submit="" lay-filter="searchForm">提交</button>
			</div>
		  </div>
		  
		</form>
    </div>
    <div class="row">
       <div class="col-md-12" id="grid">
            <!--  <table id="grid-data" class="table table-condensed table-hover table-striped">
	            <thead>
	            	<tr >
		            	<th data-column-id="file">file</th>		            
						<th data-column-id="fileName" data-formatter="fileName">fileName</th>
		                <th data-column-id="size">size</th>
		                <th data-column-id="commands" data-formatter="commands" >content</th>
		            </tr>
		        </thead>
	        </table>-->
	        <div style="padding: 10px; background-color: #F2F2F2;"id="file_body"></div>	
	    	<div id="filePage"></div>
        </div> 
    </div>
   
</div>
<script>
    $(document).ready(function(){
        $("#grid-data").bootgrid({
            navigation:2,
            url:"getdoclist.do",
            ajax:true,
            formatters: {
                "commands": function(column, row)
                {
                console.log(row);
                   return row.content.substr(0,1000);
                   //return "<a href=\""+row.url+"\>"+row.url+"</a>"
                },
                "fileName":function(column, row){
                	var url="http://localhost:8080/mysolr/plugins/pdfjs/web/viewer.html?file=/temp/"+row.hlName;
                	return "<a target='_blank' href="+url+">"+row.fileName+"</a>";
                	
                }
            }
        });
        

        
       /*  $("#search").click(function(){
           /* if($("#info").val()=="")
                //alert("请输入搜索词");
           else{
                $("#grid-data").bootgrid("search", $("#info").val());
           } 
           searchInfo = $("#info").val();
			//console.log("ss:"+searchInfo);

        }); */
        
        
        });
    layui.use(['laypage', 'layer','form'], function(){
   	  var laypage = layui.laypage,layer = layui.layer;
   	  
   	  var searchInfo = null;
   	  
   	$.getJSON("getdoclist.do", 
       	{
    	  searchPhrase:searchInfo
      	},
      	function(data) {
      		 console.log("aa:"+searchInfo)
          for(var i = 0; i < data.rows.length; i++) {
            var obj = data.rows[i];
             
            var div_header = $('<div class="layui-card-header"></div>');
            var a = $('<a href=' + obj.fileName + ' target="_blank">' + obj.fileName + '</a>');
            var div_content = $('<div class="layui-card-content">' + obj.content + '</div>');
			var div_card = $('<div class="layui-card"></div>');
			div_header.append(a);
			div_card.append(div_header,div_content);
		              $('#file_body').append(div_card)
          }

          var nums = 5; //每页出现的数量
          var pages = Math.ceil(data.total/nums); //得到总页数
		  //console.log(pages)
          var thisDate = function(datas) {
             var str = '';
             for(var i = 0; i < datas.rows.length; i++) {
                  //console.log(data.rows[i]);
                  str += '<div class="layui-card"><div class="layui-card-header"><a href="http://localhost:8080/mysolr/plugins/pdfjs/web/viewer.html?file=/temp/' + datas.rows[i].hlName + '" target="_blank">' + datas.rows[i].fileName + '</a></div>'+
                  '<div class="layui-card-content">' +  datas.rows[i].content + '</div></div>';
              }
              return str; 
          }; 
          //完整功能
       	  laypage.render({
       	    elem: 'filePage'
       	    ,count: data.total
       	    ,limit:5
       	    ,layout: ['count', 'prev', 'page', 'next', 'skip']
       	    ,jump: function(obj,first){	
       	    	if (!first) {
	    			$.ajax({
	                    url:'getdoclist.do',
	                    data:{
	                        current:obj.curr,
	                        rowCount:obj.limit,
	                       
	                    },
	                    type:'post',
	                    success:function(data){
	                    	document.getElementById('file_body').innerHTML = thisDate(data);
	                    },
	                    error:function(){
	                        console.log('网络错误，通信失败');
	                    }
	                })              	 	
	   	    	}
       	    	
       	    }
           });            
        }); 
        var form = layui.form;
        form.on('submit(searchForm)', function(data){
        	var searchInfo = data.field.title;
            layer.msg(searchInfo);
        	
        	return false;
        });
    });
</script>
</body>
</body>
</html>