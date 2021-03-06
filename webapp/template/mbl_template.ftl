<#macro mbl_layout title menu>
    <#assign ctx = rc.getContextPath()/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <link rel="apple-touch-icon" sizes="76x76" href="../resources/assets/img/apple-icon.png">
    <link rel="icon" type="image/png" href="../resources/assets/img/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>MBL</title>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />

    <!--     Fonts and icons     -->
    <link href="../resources/css/google-font-api.css" rel="stylesheet" />
    <link rel="stylesheet" href="../resources/css/font-awesome.min.css" />

    <#--ExtJS Plugin-->
    <link href="../resources/ExtJS-4.0.0/resources/css/ext-all.css" rel="stylesheet" />

    <!-- CSS Files -->
    <link href="../resources/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link href="../resources/assets/css/light-bootstrap-dashboard.css?v=2.0.1" rel="stylesheet" />




    <!-- CSS Just for demo purpose, don't include it in your project -->
    <#--<link href="../resources/assets/css/demo.css" rel="stylesheet" />-->
    <link href="../resources/css/ca-sir.css" rel="stylesheet" />
</head>

<body>
<div class="wrapper">
    <div class="sidebar" data-image="../resources/assets/img/sidebar-5.jpg">
        <!--
    Tip 1: You can change the color of the sidebar using: data-color="purple | blue | green | orange | red"

    Tip 2: you can also add an image using data-image tag
-->
        <#include 'menu.ftl'/>
    </div>
    <div class="main-panel">
        <!-- Navbar -->
        <#include 'nav.ftl'/>
        <!-- End Navbar -->

        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <#nested >
                </div>
            </div>
        </div>

        <#include 'footer.ftl'/>
    </div>
</div>
<!--   -->
<!-- <div class="fixed-plugin">
<div class="dropdown show-dropdown">
    <a href="#" data-toggle="dropdown">
        <i class="fa fa-cog fa-2x"> </i>
    </a>

    <ul class="dropdown-menu">
        <li class="header-title"> Sidebar Style</li>
        <li class="adjustments-line">
            <a href="javascript:void(0)" class="switch-trigger">
                <p>Background Image</p>
                <label class="switch">
                    <input type="checkbox" data-toggle="switch" checked="" data-on-color="primary" data-off-color="primary"><span class="toggle"></span>
                </label>
                <div class="clearfix"></div>
            </a>
        </li>
        <li class="adjustments-line">
            <a href="javascript:void(0)" class="switch-trigger background-color">
                <p>Filters</p>
                <div class="pull-right">
                    <span class="badge filter badge-black" data-color="black"></span>
                    <span class="badge filter badge-azure" data-color="azure"></span>
                    <span class="badge filter badge-green" data-color="green"></span>
                    <span class="badge filter badge-orange" data-color="orange"></span>
                    <span class="badge filter badge-red" data-color="red"></span>
                    <span class="badge filter badge-purple active" data-color="purple"></span>
                </div>
                <div class="clearfix"></div>
            </a>
        </li>
        <li class="header-title">Sidebar Images</li>

        <li class="active">
            <a class="img-holder switch-trigger" href="javascript:void(0)">
                <img src="../resources/assets/img/sidebar-1.jpg" alt="" />
            </a>
        </li>
        <li>
            <a class="img-holder switch-trigger" href="javascript:void(0)">
                <img src="../resources/assets/img/sidebar-3.jpg" alt="" />
            </a>
        </li>
        <li>
            <a class="img-holder switch-trigger" href="javascript:void(0)">
                <img src="..//assets/img/sidebar-4.jpg" alt="" />
            </a>
        </li>
        <li>
            <a class="img-holder switch-trigger" href="javascript:void(0)">
                <img src="../resources/assets/img/sidebar-5.jpg" alt="" />
            </a>
        </li>

        <li class="button-container">
            <div class="">
                <a href="http://www.creative-tim.com/product/light-bootstrap-dashboard" target="_blank" class="btn btn-info btn-block btn-fill">Download, it's free!</a>
            </div>
        </li>

        <li class="header-title pro-title text-center">Want more components?</li>

        <li class="button-container">
            <div class="">
                <a href="http://www.creative-tim.com/product/light-bootstrap-dashboard-pro" target="_blank" class="btn btn-warning btn-block btn-fill">Get The PRO Version!</a>
            </div>
        </li>

        <li class="header-title" id="sharrreTitle">Thank you for sharing!</li>

        <li class="button-container">
            <button id="twitter" class="btn btn-social btn-outline btn-twitter btn-round sharrre"><i class="fa fa-twitter"></i> · 256</button>
            <button id="facebook" class="btn btn-social btn-outline btn-facebook btn-round sharrre"><i class="fa fa-facebook-square"></i> · 426</button>
        </li>
    </ul>
</div>
</div>
-->
</body>

<!--   Core JS Files   -->
    <#include 'javascript.ftl'/>

</html>
</#macro>