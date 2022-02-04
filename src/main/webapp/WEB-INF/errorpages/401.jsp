<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<jsp:include page="../includes/head.jsp" />
	<jsp:include page="../includes/bootstrapImport.jsp" />
	<title>Unauthorized!</title>
</head>
<body>
<div class="container vh-100 d-flex flex-column align-items-center justify-content-evenly">
	<img src="${pageContext.request.contextPath}/assets/media/images/stories/401.svg" class="h-75 user-select-none" alt="Unauthorized access">
	<a href="${pageContext.request.contextPath}/" class="btn btn-primary" id="btnGoBack">
		<i class="lni lni-arrow-left"></i>
		<span>Go back</span>
	</a>
</div>
</body>
</html>