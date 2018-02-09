<script src="../resources/assets/js/core/jquery.3.2.1.min.js" type="text/javascript"></script>
<#--ExtJS Plugin-->
<script src="../resources/ExtJS-4.0.0/ext-all.js"></script>

<script src="../resources/assets/js/core/popper.min.js" type="text/javascript"></script>
<script src="../resources/assets/js/core/bootstrap.min.js" type="text/javascript"></script>


<!--  Plugin for Switches, full documentation here: http://www.jque.re/plugins/version3/bootstrap.switch/ -->
<#--<script src="../resources/assets/js/plugins/bootstrap-switch.js"></script>-->
<!--  Google Maps Plugin    -->
<#--<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>-->
<!--  Chartist Plugin  -->
<#--<script src="../resources/assets/js/plugins/chartist.min.js"></script>-->
<!--  Notifications Plugin    -->
<script src="../resources/assets/js/plugins/bootstrap-notify.js"></script>
<!-- Control Center for Light Bootstrap Dashboard: scripts for the example pages etc -->
<script src="../resources/assets/js/light-bootstrap-dashboard.js?v=2.0.1" type="text/javascript"></script>
<!-- Light Bootstrap Dashboard DEMO methods, don't include it in your project! -->
<#--<script src="../resources/assets/js/demo.js"></script>-->

<#--My Scripts-->
<script src="../resources/js/khomeni-util.js"></script>
<#--<script src="../resources/js/khomeni-ext-windows.js"></script>-->

<script type="text/javascript">
    $(document).ready(function() {
        // Javascript method's body can be found in assets/js/demos.js
        //demo.initDashboardPageCharts();

        //demo.showNotification();

        /**
         * Activating menu and sub-menu
         * */
        var menu = '${menu}';
        $('#menu-item-' + menu).attr('class', 'active');


    });
</script>