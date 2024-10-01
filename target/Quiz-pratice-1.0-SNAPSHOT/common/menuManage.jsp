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
                    <a href="managerController" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-settings"></i></span>
                        <span class="ttr-label">Admin</span>
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
