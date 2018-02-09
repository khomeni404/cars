<#import "../taim_template/taims_layout.ftl" as layout>
<@layout.taims_layout   "${(title)!}" >
    <#assign ctx = rc.getContextPath()/>
    <#assign inc = incident/>
    <#assign initiator = inc.initiator/>

<div class="row">
    <div class="col-xs-6">
        <div class="profile-user-info profile-user-info-striped">
            <div class="profile-info-row">
                <div class="profile-info-name"> Ref. No.</div>

                <div class="profile-info-value">
                    <span class="editable" id="username">${inc.refNo!}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name"> Type</div>

                <div class="profile-info-value">
                    <span class="editable" id="country">${TU.getIncidentType(inc.type?string)!}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name"> Incident Date</div>

                <div class="profile-info-value">
                    <span class="editable" id="age">${inc.incidentDate?string('dd/MM/yyyy')!}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name"> Reporting Date</div>

                <div class="profile-info-value">
                    <span class="editable" id="signup">${inc.reportingDate?string('dd/MM/yyyy')!}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name"> Duration</div>

                <div class="profile-info-value">
                    <span class="editable" id="login">${inc.startedFrom!}  -  ${inc.stoppedAt!}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name"> Details</div>

                <div class="profile-info-value">
                    <span class="editable" id="about">${inc.details!}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name"> Control</div>

                <div class="profile-info-value">
                    <span class="editable" id="about">
                        <#if inc.hasControllingApparatus>
                            Exists
                        <#else >
                            Did not exists
                        </#if>
                    </span>
                </div>
            </div>
            <#if inc.hasControllingApparatus>
                <div class="profile-info-row">
                    <div class="profile-info-name"> Failure Reasons</div>

                    <div class="profile-info-value">
                        <span class="editable" id="about">${inc.failureReasons!}</span>
                    </div>
                </div>
            </#if>

            <div class="profile-info-row">
                <div class="profile-info-name"> Preventative Controls</div>

                <div class="profile-info-value">
                    <span class="editable" id="about">${inc.preventativeControls!}</span>
                </div>
            </div>
        </div>
    </div>


    <div class="col-xs-6">
        <div class="profile-user-info profile-user-info-striped">
            <div class="profile-info-row">
                <div class="profile-info-name"> Initiator Name</div>

                <div class="profile-info-value">
                    <span class="editable" id="username">${initiator.name!}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name"> Employee ID</div>

                <div class="profile-info-value">
                <#--<i class="fa fa-map-marker light-orange bigger-110"></i>-->
                    <span class="editable" id="country">${initiator.employeeID!}</span>
                <#--<span class="editable" id="city">Amsterdam</span>-->
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name"> Division/Dept.</div>

                <div class="profile-info-value">
                    <span class="editable" id="age">${'---'}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name"> CASM UserID</div>

                <div class="profile-info-value">
                    <span class="editable" id="signup">${initiator.casmUserid!}</span>
                </div>
            </div>




        </div>
    </div>
</div>

<div class="hr hr-dotted hr-16"></div>

<div class="row text-right">
    <div class="col-lg-12">
        <button class="btn btn-white btn-info btn-bold" onclick="window.location = '${ctx}/incident/createIncidentReview.ibbl?incidentId=${inc.id}'">
            <i class="ace-icon fa fa-flag-checkered bigger-120 blue"></i>
            Create Review
        </button>
        <button class="btn btn-white btn-info btn-bold" onclick="window.location = '${ctx}/incident/editIncident.ibbl?id=${inc.id}'">
            <i class="ace-icon fa fa-pencil-square-o bigger-120 red2"></i>
            Edit
        </button>

    </div>

    <#if reviewList?has_content>
    <div class="col-lg-12">
        <div class="space"></div>

        <div>
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th class="center"></th>
                    <th class="center">Reviewer</th>
                    <th class="center">Review Date</th>
                    <th class="center">Signed As</th>
                    <th class="center">Review Note</th>
                </tr>
                </thead>

                <tbody>
                <#list reviewList as review>
                <tr>
                    <td class="center">${review.id!}</td>

                    <td class="text-left">
                        <a href="#">${(review.reviewBy.name)!}</a>
                    </td>
                    <td class="center">${(review.reviewDate?string('dd/MM/yyyy'))!}</td>
                    <td class="center">${review.signedAs!}</td>
                    <td class="center">${review.reviewNote!}</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
    </#if>
</div>

</@layout.taims_layout>

<script>

</script>