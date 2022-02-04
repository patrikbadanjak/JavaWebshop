<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<jsp:include page="../includes/head.jsp" />
	<jsp:include page="../includes/bootstrapImport.jsp" />
	<title>An error occurred!</title>
</head>
<body>
<div class="container vh-100 d-flex flex-column align-items-center justify-content-evenly">
	<h2>An unknown error occured.</h2>
	<a href="${pageContext.request.contextPath}/" class="btn btn-primary" id="btnGoBack">
		<i class="lni lni-arrow-left"></i>
		<span>Go back</span>
	</a>
</div>
</body>
</html>