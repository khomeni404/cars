<#import "../taim_template/taims_layout.ftl" as layout>
<@layout.taims_layout  "${(title)!}" >
    <#import "/spring.ftl" as spring/>
    <#assign ctx = rc.getContextPath()/>
    <#assign Incident = Incident/>

<div class="row">
    <form id="incident-form" action="${ctx}/incident/updateIncident.ibbl" method="POST" class="form-horizontal"
          role="form">
        <@spring.formHiddenInput  "Incident.id"/>
        <div class="col-xs-6">
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="refNo"> Reference No </label>

                <div class="col-sm-9">
                    <@spring.formInput  "Incident.refNo" "class=' form-control se-num'" />
                    <@spring.showErrors " & ", "err-msg" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="incident-type"> Incident Type</label>

                <div class="col-sm-9">
                    <@spring.formSingleSelect "Incident.type", TU.getIncidentTypeMap(),  "class='chosen-select form-control' id='incident-type' data-placeholder='Choose a State...'"/>
                    <@spring.showErrors " & ", "err-msg" />
                </div>
            </div>


            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="user-name"> Initiator Name </label>

                <div class="col-sm-9">
                    <input type="text" readonly="" value="${userName!}" id="user-name" placeholder="Text Field"
                           class="form-control"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="incident-date"> Incident Date </label>

                <div class="col-sm-9">
                    <div class="input-group"><span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i> </span>
                        <@spring.formInput  "Incident.incidentDate" "class='date-picker form-control'  data-date-format='dd/mm/yyyy' readonly" />
                    </div>
                    <@spring.showErrors " & ", "err-msg" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="id-date-picker-1"> Reporting Date</label>

                <div class="col-sm-9">
                    <div class="input-group"><span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i> </span>
                        <@spring.formInput  "Incident.reportingDate" "class='date-picker form-control'  data-date-format='dd/mm/yyyy' readonly" />
                    </div>
                    <@spring.showErrors " & ", "err-msg" />

                </div>
            </div>

            <div class="space-4"></div>


            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="loss-amt"> Appox. Loss Amt. </label>

                <div class="col-sm-9">
                    <@spring.formInput  "Incident.lossAmt" "class='form-control se-amt' value='125400' " />
                    <@spring.showErrors " & ", "err-msg" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="incident-started-from">Duration </label>

                <div class="col-sm-4">
                    <@spring.formInput  "Incident.startedFrom" "class='form-control' readonly" />
                    <@spring.showErrors " & ", "err-msg" />
                </div>
                <label class="col-sm-1 control-label text-center" for="incident-stopped-at">to</label>

                <div class="col-sm-4">
                    <@spring.formInput  "Incident.stoppedAt" "class='form-control' readonly" />
                    <@spring.showErrors " & ", "err-msg" />
                </div>
            </div>

            <div class="space-4"></div>


            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-12">
                    <button class="btn btn-info" type="submit">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        Update
                    </button>
                </div>
            </div>


        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <label class="col-sm-12 control-label" for="incident-details">Details of Incident(with Effect of to
                    Business)</label>
            </div>

            <div class="form-group">
                <div class="col-sm-12">
                    <@spring.formTextarea  "Incident.details" "class='autosize-transition form-control'" />
                    <@spring.showErrors " & ", "err-msg" />
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-9">
                    <div class="inline">
                        <div class="checkbox">
                            <label class="">
                                <@spring.formCheckbox  "Incident.hasControllingApparatus" "class='ace ace-checkbox-2'" />
                                <span class="lbl"> <b>Preventative Control Was Existed</b></span>
                            </label>
                        </div>

                    </div>
                    <@spring.showErrors " & ", "err-msg" />
                </div>
            </div>

            <div class="form-group control-already-exists"
                 style="display: <#if Incident.hasControllingApparatus?string = 'false'>none</#if>">
                <label class="col-sm-12 control-label" for="failure-reasons">Failure Reasons</label>
            </div>

            <div class="form-group control-already-exists"
                 <#if Incident.hasControllingApparatus?string = 'false'>style="display: none" </#if>>
                <div class="col-sm-12">
                    <@spring.formTextarea  "Incident.failureReasons" "class='autosize-transition form-control'" />
                    <@spring.showErrors " & ", "err-msg" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-12 control-label" for="preventative-controls">Preventative Controls Implemented for
                    Future Re-occurrence</label>
            </div>

            <div class="form-group">
                <div class="col-sm-12">
                    <@spring.formTextarea  "Incident.preventativeControls" "class='autosize-transition form-control'" />
                    <@spring.showErrors " & ", "err-msg" />
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <#list Incident.reviewList as ir>
                <div class="row">
                    <div class="col-lg-1">
                        <input type="text" name="reviewList[${ir?index}].id" value="${ir.id!}">
                    </div>
                    <div class="col-lg-4">
                        <input type="text" name="reviewList[${ir?index}].reviewNote" value="${ir.reviewNote!}">
                    </div>
                    <div class="col-lg-2">
                        <input type="text" name="reviewList[${ir?index}].reviewDate" value="${ir.reviewDate!}">
                    </div>
                    <div class="col-lg-2">
                        <input type="text" name="reviewList[${ir?index}].signedAs" value="${ir.signedAs!}">
                    </div>
                </div>
            </#list>
        </div>
    </form>
</div>

</@layout.taims_layout>

<script type="text/javascript">
    $('#startedFrom').datetimepicker().next().on(ace.click_event, function () {
        $(this).prev().focus();
    });


    $('#stoppedAt').datetimepicker().next().on(ace.click_event, function () {
        $(this).prev().focus();
    });

    $('#hasControllingApparatus').on(ace.click_event, function () {
        $('.control-already-exists').toggle();
        $('#failureReasons').val('');
    });
</script>
