<%-- 
    Document   : edit
    Created on : Jun 12, 2024, 1:03:17 AM
    Author     : HP
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
       <%@ include file="../component/headerManager.jsp" %>
        <div class="container mt-4 ttr-wrapper">
            <h2 class="mb-4">Update Admin</h2>
            <form action="managerController?action=edit" method="post">
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" class="form-control" id="name" name="name" value="${currentAdmin.name}" required>
                    <input type="hidden" name="idAdmin" value="${currentAdmin.id}" required>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" class="form-control" id="email" name="email" value="${currentAdmin.email}" required>
                </div>
                <div class="form-group">
                    <label for="phone">Phone:</label>
                    <input type="text" class="form-control" id="phone" name="phone" value="${currentAdmin.phone}" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" class="form-control" id="password" name="password">
                    <input type="hidden" value="${currentAdmin.password}" name="oldPassword"/>
                </div>
                <div class="form-group">
                    <label for="status">Status:</label>
                    <select class="form-control" id="status" name="status">
                        <option value="1" ${currentAdmin.status == 1 ? "selected" : ""}>Active</option>
                        <option value="0" ${currentAdmin.status == 0 ? "selected" : ""}>Inactive</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Update Admin</button>
            </form>
        </div>
    </body>
</html>
