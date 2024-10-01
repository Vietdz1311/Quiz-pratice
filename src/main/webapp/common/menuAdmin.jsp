<%-- 
    Document   : menu
    Created on : Sep 17, 2024, 9:47:15 PM
    Author     : HP
--%>

<div class="ttr-sidebar">
    <div class="ttr-sidebar-wrapper content-scroll">
        <div class="ttr-sidebar-logo">
            <a href="AdminController?action=edit&adminID=${sessionScope.adminLogin.id}">
                <p>Hello, ${sessionScope.adminLogin.name}</p>
            </a>
            <div class="ttr-sidebar-toggle-button" onclick="changePage()">
                <i class="ti-power-off"></i>
            </div>
        </div>
        <nav class="ttr-sidebar-navi">
            <ul>
                <li>
                    <a href="ListStudentsController" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-user"></i></span>
                        <span class="ttr-label">Students</span>
                    </a>
                </li>
                <li>
                    <a href="ListTeachersController" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-user"></i></span>
                        <span class="ttr-label">Teachers</span>
                    </a>
                </li>
                <li>
                    <a href="ListCourseController" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-book"></i></span>
                        <span class="ttr-label">Courses</span>
                    </a>
                </li>
                <li>
                    <a href="ListSemestersController" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-calendar"></i></span>
                        <span class="ttr-label">Semester</span>
                    </a>
                </li>
                <li>
                    <a href="ListClassController" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-book"></i></span>
                        <span class="ttr-label">Classes</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>
<script>
    function changePage() {
        if (confirm("Are you sure to logout?")) {
            window.location.href = "AdminLogoutController";
        }
    }
</script>
