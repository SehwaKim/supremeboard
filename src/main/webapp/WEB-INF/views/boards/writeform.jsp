<%--
  Created by IntelliJ IDEA.
  User: sehwa
  Date: 18. 11. 6
  Time: 오후 4:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%-- 공통헤더 시작 --%>
    <%@ include file="/WEB-INF/views/common/header.jsp"%>
    <%-- 공통헤더 끝 --%>
    <title>글 쓰기</title>
    <style type="text/css">
        a {
            color: inherit;
            text-decoration: none;
        }
        a:hover {
            color: inherit;
            text-decoration: none;
            cursor:pointer;
        }
    </style>
</head>
<body>
    <div align="center">
        <div align="center" style="width: 42%;">
            <form id="write-form" method="post" action="/boards">
                <div class="title-area" align="left" style="padding-top: 30px; padding-bottom: 30px">
                    <p style="font-size: 22px">제목</p>
                    <input type="text" name="title" size="67%"/>
                </div>
                <div class="content-area" align="left">
                    <p style="font-size: 22px">내용</p>
                    <textarea name="content" cols="70" rows="15"></textarea>
                </div>
                <input type="hidden" name="categoryId" value="1">
                <input type="hidden" name="userId" value="1">
                <input type="hidden" name="writer" value="아무개">
                <div style="padding-top: 30px; font-size: 30px; font-weight: bold;">
                    <a href="javascript:document.getElementById('write-form').submit();">[글쓰기]</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
