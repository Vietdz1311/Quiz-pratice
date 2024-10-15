<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/head.jsp"></jsp:include>
<jsp:include page="../../common/header.jsp"></jsp:include>
<jsp:include page="../../common/menu.jsp"></jsp:include>
    <div class="container mt-4 ttr-wrapper">
        <h1 class="mb-4">Question List</h1>
        <a href="QuestionBankController?action=new" class="btn btn-primary mb-2">Add New Question</a>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Course</th>
                    <th>Question</th>
                    <th>Question Type</th>
                    <th>Difficulty</th>
                    <th>Marks</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="question" items="${listQuestion}">
                <tr>
                    <td>${question.questionID}</td>
                    <td>${question.course.courseName}</td>
                    <td>${question.questionText}</td>
                    <td>${question.questionType}</td>
                    <td>${question.difficulty}</td>
                    <td>${question.marks}</td>
                    <td>
                        <a href="QuestionBankController?action=edit&id=${question.questionID}" class="btn btn-warning btn-sm">Edit</a>
                        <a href="QuestionBankController?action=delete&id=${question.questionID}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this item?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>
