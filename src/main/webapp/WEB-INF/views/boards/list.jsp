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
    <title>글 목록</title>
    <style type="text/css">
        td {
            padding-top:15px;
            padding-bottom:15px;
            padding-right:20px;
        }
        .pagination {
            display: inline-block;
        }
        .pagination a {
            color: black;
            float: left;
            padding: 8px 16px;
            text-decoration: none;
        }
        .pagination a.active {
            background-color: #2d2d2d;
            color: white;
        }
        .pagination a:hover:not(.active) {background-color: #f3f3f3;}
        .title{
            width: 82%;
        }
        .date {
            text-align: center;
            width: 18%;
        }
        #boards a {
            color: inherit;
            text-decoration: none;
        }
        #boards a:hover {
            color: inherit;
            text-decoration: none;
            cursor:pointer;
        }
        .top a {
            color: inherit;
            text-decoration: none;
        }
        .top a:hover {
            color: inherit;
            text-decoration: none;
            cursor:pointer;
        }
    </style>
</head>
<body>
    <div align="center">
        <div align="center" style="width: 42%">
            <div class="top" style="padding: 50px;">
                <a href="/boards/writeform" style="font-size: 35px; font-weight: bold">[글쓰기]</a>
            </div>
            <%--테이블 시작--%>
            <table id="boards" style="width:100%">
                <c:if test="${boards != null}">
                    <c:forEach items="${boards}" var="b">
                        <tr>
                            <td class="title">
                                <c:forEach begin="1" end="${b.indent}" step="1">
                                    <span>
                                        <svg id="i-chevron-right" viewBox="0 0 32 32" width="28" height="32" fill="none" stroke="currentcolor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2">
                                            <path d="M12 30 L24 16 12 2" />
                                        </svg>
                                    </span>
                                </c:forEach>
                                <a href="/boards/${b.id}">${b.title}</a>
                                <a href="/boards/${b.id}#comments" class="cmt-cnt" style="padding-left: 3px; font-size: 22px; font-weight: bold; color: #f01600;">
                                    (<u>${b.commentCnt}</u>)
                                </a>
                            </td>
                            <td class="date">${b.updatedAt}</td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
            <%--테이블 끝--%>
            <%--검색 div 시작--%>
            <div style="padding-bottom: 20px; padding-top: 50px; width: 50%">
                <form id="search" method="get" action="/boards">
                    <div class="input-group">
                        <select class="custom-select" name="type">
                            <!--<option value="writer">작성자</option>-->
                            <option value="title">제목</option>
                            <option value="content">내용</option>
                            <option value="title+content">제목+내용</option>
                        </select>
                        <input type="text" name="str" size="15" style="font-size: 20px; padding-bottom: 3px; padding-top: 3px; padding-left: 2px    ">
                        <a href="javascript:document.getElementById('search').submit();" style="padding: 7px">
                            <svg id="i-search" viewBox="0 0 32 32" width="32" height="32" fill="none" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="2">
                                <circle cx="14" cy="14" r="12"/>
                                <path d="M23 23 L30 30"/>
                            </svg>
                        </a>
                    </div>
                </form>
            </div>
            <%--검색 div 끝--%>
            <%--페이징 div 시작--%>
            <div class="pagination" style="padding-top: 10px; padding-bottom: 80px">
                <a href="#">&laquo;</a>
                <a href="#">1</a>
                <a class="active" href="#">2</a>
                <a href="#">3</a>
                <a href="#">4</a>
                <a href="#">5</a>
                <a href="#">6</a>
                <a href="#">7</a>
                <a href="#">&raquo;</a>
            </div>
            <%--페이징 div 끝--%>
        </div>
    </div>
</body>
</html>
