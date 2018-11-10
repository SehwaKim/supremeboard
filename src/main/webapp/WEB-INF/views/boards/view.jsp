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
    <title>글 보기</title>
    <style type="text/css">
        #content-area th {
            padding-top: 15px;
            padding-bottom: 15px;
        }
        .tr-head td {
            padding-top: 15px;
            padding-bottom: 15px;
            border-bottom: 2px solid #d7d7d7;
        }
        .tr-body td {
            padding-top: 40px;
            padding-bottom: 15px;
        }
        #comment-area td {
            padding-top: 15px;
            padding-bottom: 15px;
        }
        .tr-edge {
            border-bottom: 2px solid #d7d7d7;
        }
        a {
            color: inherit;
            font-size: 32px;
            font-weight: bold;
            text-decoration: none;
        }
        a:hover {
            color: inherit;
            text-decoration: none;
            cursor:pointer;
        }
    </style>
    <script>
        function writeComment() {
            var JSONObject= {
                'content': $('#comment-content').val(),
                'boardId': ${board.id},
                'commenter': 'sehwa',
                'userId': 1
            };
            var jsonData = JSON.stringify( JSONObject );
            $.ajax({
                url : '/api/comments',
                method : 'post',
                data : jsonData,
                dataType: "text",
                contentType: "application/json; charset=UTF-8",
                success : function (data) {
                    var commentArray = JSON.parse(data);
                    var newList = "";
                    for (var i in commentArray) {
                        newList +=
                            "<tr>" +
                            "<td colspan=\"2\">" + commentArray[i].content + "</td>" +
                            "</tr>" +
                            "<tr class=\"tr-edge\">" +
                            "<td style=\"padding-left: 20px; font-size: 17px\">" + commentArray[i].commenter + "</td>" +
                            "<td style=\"font-size: 17px\">" + commentArray[i].updatedAt + "</td>" +
                            "</tr>";
                    }
                    document.getElementById("comment-area").innerHTML = newList;
                }
            });
        }
    </script>
</head>
<body>
    <div align="center">
        <div align="center" style="width: 42%;">
            <div style="padding-top: 50px; padding-bottom: 120px">
                <table id="content-area" width="100%">
                    <tr align="center">
                        <th colspan="2" style="color: white; background-color: black">
                            ${board.title} (${board.commentCnt})
                        </th>
                    </tr>
                    <tr class="tr-head">
                        <td style="padding-left: 20px">${board.writer}</td>
                        <td>${board.updatedAt}</td>
                    </tr>
                    <tr class="tr-body">
                        <td colspan="2">${board.content}</td>
                    </tr>
                </table>
            </div>
            <%--댓글창 시작--%>
            <div id="comment-input-area" align="left">
                <textarea id="comment-content" name="content" cols="55" rows="5"></textarea>
                <a href="javascript:writeComment();" style="padding-left: 15px;">[답글등록]</a>
            </div>
            <div id="comments" style="padding-top: 80px; padding-bottom: 80px">
                <table id="comment-area" width="100%">
                <c:if test="${comments != null}">
                    <c:forEach items="${comments}" var="c">
                        <tr>
                            <td colspan="2">${c.content}</td>
                        </tr>
                        <tr class="tr-edge">
                            <td style="padding-left: 20px; font-size: 17px">${c.commenter}</td>
                            <td style="font-size: 17px">${c.updatedAt}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </table>
            </div>
            <div style="padding-bottom: 100px">
                <a href="/boards">[목록으로]</a>
            </div>
            <%--댓글창 끝--%>
        </div>
    </div>
</body>
</html>