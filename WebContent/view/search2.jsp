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
</head>
<body>
<div class="container" style="margin-top: 50px">
    <div class="row">
        <!--<div class="col-md-6 col-md-offset-3">
            <img src="./css/pic.jpg" />
        </div>-->
        <div class="col-md-10">
            <input type="text" class="form-control" id="info" placeholder="请输入搜索内容">
        </div>
        <div class="col-md-2">
            <button id="search" type="button" class="btn btn-primary">搜索</button>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12" id="grid">
            <table id="grid-data" class="table table-condensed table-hover table-striped">
	            <thead>
	            	<tr >
<!-- 		            	<th data-column-id="file">file</th>		            
 -->						<th data-column-id="fileName" data-formatter="fileName">fileName</th>
		                <th data-column-id="size">size</th>
		                <th data-column-id="commands" data-formatter="commands" >content</th>
		            </tr>
		        </thead>
	        </table>
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
        $("#search").click(function(){
            //if($("#info").val()=="")
                //alert("请输入搜索词");
           // else
            //{
                $("#grid-data").bootgrid("search", $("#info").val());
            //}


        });
    });
</script>
</body>
</body>
</html>