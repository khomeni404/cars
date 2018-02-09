<div class="sidebar-wrapper">
    <div class="logo text-center" style="font-size: 12px; font-weight: bold">
            BRANCH - ${(Session.session_user.brCode)!}
            <br/> <span style="color: black">${(Session.session_user.brName)!}</span>
             <br/> ${(Session.session_user.employeeId)!} - ${(Session.session_user.nickName)!}
    </div>
    <ul class="nav">
        <li id="menu-item-dashboard">
            <a class="nav-link" href="${ctx}/home/dashboard.mbl">
                <i class="nc-icon nc-chart-pie-35"></i>
                <p>Dashboard</p>
            </a>
        </li>
        <li id="menu-item-view-ir">
            <a class="nav-link" href="${ctx}/ir/viewInquiry.mbl?id=3">
                <i class="nc-icon nc-circle-09"></i>
                <p>View</p>
            </a>
        </li>
        <li id="menu-item-new-ir">
            <a class="nav-link" href="${ctx}/ir/createInquiry.mbl">
                <i class="nc-icon nc-circle-09"></i>
                <p>New IR</p>
            </a>
        </li>
        <li>
            <a class="nav-link" href="#">
                <i class="nc-icon nc-notes"></i>
                <p>Table List</p>
            </a>
        </li>
        <li>
            <a class="nav-link" href="#">
                <i class="nc-icon nc-paper-2"></i>
                <p>Typography</p>
            </a>
        </li>
        <li>
            <a class="nav-link" href="#">
                <i class="nc-icon nc-atom"></i>
                <p>Icons</p>
            </a>
        </li>
        <li>
            <a class="nav-link" href="#">
                <i class="nc-icon nc-pin-3"></i>
                <p>Maps</p>
            </a>
        </li>
        <li>
            <a class="nav-link" href="#">
                <i class="nc-icon nc-bell-55"></i>
                <p>Notifications</p>
            </a>
        </li>
        <li class="nav-item active active-pro">
            <a class="nav-link active" href="${ctx}/home/logout.mbl">
                <i class="nc-icon nc-alien-33"></i>
                <p>Logout</p>
            </a>
        </li>
    </ul>
</div>