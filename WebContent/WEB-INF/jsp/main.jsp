<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ page import ="model.User,model.Mutter,java.util.List" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%-- <%
//セッションスコープに保存されたユーザー情報を取得
User loginUser = (User) session.getAttribute("loginUser");
//アプリケーションスコープに保存されたつぶやきリストを取得
//→DB連携に伴いリクエストスコープから取得へ変更
@SuppressWarnings("unchecked")
List<Mutter> mutterList = (List<Mutter>) request.getAttribute("mutterList");
//リクエストスコープに保存されたエラーメッセージを取得
String errorMsg =(String) request.getAttribute("errorMsg");
%> --%>
<html>
<head>
<meta charset="UTF-8">
<title>笹焼</title>
</head>
<body>
<h1>笹焼サイトようこそ</h1>
<p>
<%--
<%= loginUser.getName() %>
 --%>
<c:out value ="${loginUser.name}"/>さん、ログイン中
<a href = "/Logout">ログアウト</a>
</p>

<p><a href="/Main">更新</a></p>
<!-- Main.javaへpostで送信 -->
<form action="/Main" method="post" accept-charset="UTF-8">
<!-- ささやきを入力 -->
<input type="text" name="text">
<input type="submit" value="ささやく">
<input type='hidden' name='charset' value='utf8' />
</form>
<%--

<% if(errorMsg !=null){ %>
<p><%= errorMsg %></p>
<% } %>
<!--例外発生ポイント↓ -->
<% for(Mutter mutter : mutterList) { %>
	<p><%=mutter.getUserName() %>:<%= mutter.getText() %></p>
<% }%>

--%>
<c:if test= "${not empty errorMsg }">
	<p>${errorMsg }</p>
</c:if>
<c:forEach var ="mutter" items="${mutterList}">
	<p><c:out value="${mutter.userName}"/> :<c:out value=" ${mutter.text} "/></p>
</c:forEach>

</body>
</html>