<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <jsp:include page="../includes/head.jsp" />
        <jsp:include page="../includes/bootstrapImport.jsp" />
        <title>Page not found!</title>
    </head>
    <body>
        <div class="container vh-100 d-flex flex-column align-items-center justify-content-evenly">
            <img src="${pageContext.request.contextPath}/assets/media/images/stories/404.svg" class="h-75 user-select-none" alt="Page not found">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary" id="btnGoBack">
                <i class="lni lni-arrow-left"></i>
                <span>Go back</span>
            </a>
        </div>
    </body>
</html>
