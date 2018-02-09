<!DOCTYPE html>
<html lang="en">
<#assign ctx = rc.getContextPath()/>
<#import '/spring.ftl' as spring/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>MBL</title>
    <style>
        .x_content a span{
            font-size: 18px; font-weight: bolder;
            padding: 15px 15px 35px 15px;
        }
        .footer_fixed footer {
            position: fixed;
            left: 0px;
            bottom: 0px;
            width: 100%
        }
        @media (min-width: 768px) {
            .footer_fixed footer {
                margin-left: 0
            }
        }
        @media (min-width: 768px) {
            .footer_fixed .nav-sm footer {
                margin-left: 0
            }
        }
    </style>

    <!-- CSS Files -->
    <link href="../resources/assets/css/bootstrap.min.css" rel="stylesheet" />
    <script src="../resources/assets/js/core/jquery.3.2.1.min.js" type="text/javascript"></script>
    <script src="../resources/css/ca-sir.css" type="text/javascript"></script>

    <script src="../resources/assets/js/core/bootstrap.min.js" type="text/javascript"></script>
    <script src="../resources/js/khomeni-util.js" type="text/javascript"></script>

</head>


<body class="nav-md footer_fixed" style="background-color: #e1ffe4;">
<div class="container body">
    <div class="main_container">
        <!-- /page content -->

        <div class="row">
            <div class="col-md-6 col-md-offset-3 text-center" style="padding: 20px 0 0 0">
                <img src="../resources/images/logo/mbl_logo_1.png" height="73" width="500" alt=""/>
            </div>
        </div>

        <div class="row">
            <div class="login_wrapper">
                <div class="animate form login_form">
                    <section class="login_content">
                        <form action="${ctx}/auth/authenticateUser.mbl" method="post">
                            <h1>Login to &Copf;&oopf;&ropf;&eopf; &Mopf;&Iopf;&Sopf;</h1>

                            <div class="">
                            <@spring.formInput 'TokenBean.username' 'class="form-control input-sm" placeholder="CAAMP Username" required="required" data-validate-length="2,5"'/>
                            <@spring.showErrors '&' 'err-msg'/>
                            </div>

                            <div>
                            <@spring.formInput 'TokenBean.password' 'class="form-control input-sm" placeholder="CAAMP Password" required="required"'/>
                            <@spring.showErrors '&' 'err-msg'/>
                            </div>

                            <div>
                            <@spring.formInput 'TokenBean.brCode' 'class="form-control" placeholder="Branch Code" required="required"'/>
                            <@spring.showErrors '&' 'err-msg'/>
                            </div>

                            <div class="pull-right">
                                <input type="submit" value="Login" class="btn btn-success submit pointer"/>
                                <input type="reset" value="Reset" class="btn btn-warning"/>
                            </div>

                            <div class="clearfix"></div>

                            <div>
                            <@spring.formHiddenInput 'TokenBean.errors'/>
                            <@spring.showErrors '&' 'err-msg'/>
                            </div>

                            <div class="separator">
                                <p class="change_link">Not Registered with CAAMP?
                                    <a href="${caampRegisterLink!'#'}" class="to_register"> Register please </a>
                                </p>

                                <div class="clearfix"></div>
                                <br/>

                                <div>
                                <#--<h1><i class="fa fa-paw"></i> Welcome to MBL</h1>-->
                                    <p> &copy; 2018 All Rights Reserved. Mercantile Bank Limited</p>
                                </div>
                            </div>
                        </form>
                    </section>
                </div>
            </div>
        </div>


        <!-- footer content -->
        <footer style="background-color: #e1ffe4">
            <div class="row">

                <div class="col-md-12" style="background-color: #08e138; height: 30px; vertical-align: bottom">
                </div>
                <div class="col-md-12" style="background-color: #4853ee; height: 30px">

                </div>
            </div>

            <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
    </div>
</div>

<script src="../resources/assets/js/core/jquery.3.2.1.min.js" type="text/javascript"></script>

<script src="../resources/assets/js/core/popper.min.js" type="text/javascript"></script>
<script src="../resources/assets/js/core/bootstrap.min.js" type="text/javascript"></script>


</body>
</html>


