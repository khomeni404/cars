<#import "../template/mbl_template.ftl" as layout>
<@layout.mbl_layout "${(title)!'--'}", 'new-ir'>
    <#import "/spring.ftl" as spring/>
    <#assign ctx = rc.getContextPath()/>
    <#include '../template/constant.ftl'/>
    <#assign IU = Utility>

<div class="col-md-12">
    <div class="card">
        <div class="card-header">
            <h4 class="card-title">New IR</h4>
        </div>
        <div class="card-body">
            <form action="${ctx}/ir/saveInquiry.mbl" method="POST">

                <div class="row">
                    <div class="col-md-3 pr-1">
                        <div class="form-group">
                            <label>Customer ID <span class="se-mandatory">*</span></label>
                            <@spring.formInput 'Inquiry.custId' 'class="form-control"'/>
                            <@spring.showErrors " & ", "ftl-err-msg" />
                        </div>
                    </div>
                    <div class="col-md-6 pl-1">
                        <div class="form-group">
                            <label>Core Customer Name <span class="se-mandatory">*</span></label>
                            <@spring.formInput 'Inquiry.custName' 'class="form-control"'/>
                            <@spring.showErrors " & ", "ftl-err-msg" />
                        </div>
                    </div>
                    <div class="col-md-3 pl-1">
                        <div class="form-group">
                            <label>Credit Limit <span class="se-mandatory">*</span></label>
                            <@spring.formInput 'Inquiry.sanctionAmt' 'class="form-control"'/>
                            <@spring.showErrors " & ", "ftl-err-msg" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 pr-1">
                        <div class="form-group">
                            <label>Credit Mode <span class="se-mandatory">*</span></label>
                            <@spring.formSingleSelect 'Inquiry.crMode' IU.getOptions('--Select a Mode--', CR_MODES)  'class="form-control pointer"'/>
                            <@spring.showErrors " & ", "ftl-err-msg" />
                        </div>
                    </div>
                    <div class="col-md-6 pl-1">
                        <div class="form-group">
                            <label>Type of Inquiry <span class="se-mandatory">*</span></label>
                            <@spring.formSingleSelect 'Inquiry.inqType' IU.getOptions('--Select a Type--', INQ_TYPES)  'class="form-control pointer"'/>
                            <@spring.showErrors " & ", "ftl-err-msg" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 pr-1">
                        <div class="form-group">
                            <label>No of Installment</label>
                            <@spring.formInput 'Inquiry.emi' 'class="form-control"'/>
                            <@spring.showErrors " & ", "ftl-err-msg" />
                        </div>
                    </div>
                    <div class="col-md-6 pl-1">
                        <div class="form-group">
                            <label>Periodicity of Payment</label>
                            <@spring.formSingleSelect 'Inquiry.pop' IU.getOptions('--Select a POP--', POPS)  'class="form-control pointer"'/>
                            <@spring.showErrors " & ", "ftl-err-msg" />
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-6 pr-1">
                        <div class="form-group">
                            <label>Type of Financing <span class="se-mandatory">*</span></label>
                            <@spring.formInput 'Inquiry.financingType' 'class="form-control"'/>
                            <@spring.showErrors " & ", "ftl-err-msg" />

                        </div>
                    </div>
                    <div class="col-md-6 pl-1">
                        <div class="form-group">
                            <label>Inquiry For <span class="se-mandatory">*</span></label>
                            <@spring.formSingleSelect 'Inquiry.inqFor' IU.getOptions('--Select a Option--', CUSTOMER_TYPE)  'class="form-control pointer"'/>
                            <@spring.showErrors " & ", "ftl-err-msg" />
                        </div>
                    </div>
                </div>


                <button type="submit" class="btn btn-info btn-fill pull-right pointer">Save Inquiry</button>
                <div class="clearfix"></div>
            </form>
        </div>
    </div>
</div>

</@layout.mbl_layout>

<script>
    /*$(function () {
        $("input.form-control:select").each(function() {
            $( this ).css("background-color", "yellow");
        });
    });*/
</script>
