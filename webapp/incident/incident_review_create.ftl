<#import "../taim_template/taims_layout.ftl" as layout>
<@layout.taims_layout  "${(title)!}" >
    <#import "/spring.ftl" as spring/>
    <#assign ctx = rc.getContextPath()/>
    <#assign inc = IncidentReview.incident/>

<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <h4 class="header green clearfix text-right">
            <b>Review Note for ${inc.refNo!}</b>
        </h4>

        <form action="${ctx}/incident/saveIncidentReview.ibbl" method="POST" class="form-horizontal" role="form">
           <input type="hidden" name="incidentId" value="${inc.id!}"/>

            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> Reference No </label>

                <div class="col-sm-9">
                    <input readonly value="${inc.refNo!}" type="text" id="form-field-1" placeholder=""
                           class="col-xs-10 col-sm-5"/>
                </div>
            </div>

            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> Reviewer </label>

                <div class="col-sm-9">
                    <input readonly  value="${userName!}" type="text" id="form-field-1" placeholder=""
                           class="col-xs-10 col-sm-5"/>
                </div>
            </div>

            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="signedAs"> Reviewed as</label>

                <div class="col-sm-9">
                    <@spring.formSingleSelect "IncidentReview.signedAs", heads!"",  "class='chosen-select form-control' data-placeholder='Select a Signed As Option'"/>
                    <@spring.showErrors " & ", "err-msg" />
                </div>
            </div>

            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="reviewNote">Review Note</label>

                <div class="col-sm-9">
                    <@spring.formTextarea  "IncidentReview.reviewNote" "class='autosize-transition form-control'" />
                    <@spring.showErrors " & ", "err-msg" />
                </div>
            </div>


            <div class="space-4"></div>


            <div class="col-lg-12 text-right">
                <button class="btn btn-white btn-info btn-bold"
                        onclick="window.location = '${ctx}/incident/createIncidentReview.ibbl?incidentId=${inc.id}'">
                    <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
                    Save Review
                </button>

            </div>


        </form>
    </div>
</div>
<div class="hr hr-dotted hr-16"></div>


</@layout.taims_layout>

<style type="text/css">
    .control-label {
        font-weight: bold;
        color: #425d07;
    }
</style>