<#import "/spring.ftl" as spring/>
<#assign form = JspTaglibs["http://www.springframework.org/tags/form"] />


<#assign brErr><@form.errors path="Token.brCode" /></#assign>
<#if brErr?? && brErr != ''>

</#if>

${Inquiry.inqFor?has_content?then(IU.getProp(CUS Inquiry.inqFor?string)!'--','--')}


<#--Form Widget Template-->
<div class="row">
    <div class="col-lg-12 col-sm-6">
        <div class="widget-box widget-color-green">
            <div class="widget-header">
                <h5 class="widget-title bigger lighter"><b>Form Title</b></h5>
            </div>

            <div class="widget-body">
                <form id="data-form" action="${ctx}/app/saveFamilyData.se" method="POST"
                      class="form-horizontal"
                      role="form">
                <@spring.formHiddenInput "FamilyInfoBean.id"/>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="widget-main">
                                <div class="col-lg-4 col-xs-12">
                                    <div class="form-group">
                                        <label class="col-lg-12 col-sm-12 col-xs-12 control-label no-padding-right"
                                               for="motherState">Mother State<span class="se-mandatory">*</span>
                                        </label>

                                        <div class="col-lg-12 col-sm-12 col-xs-12">
                                        <@spring.formSingleSelect  "FamilyInfoBean.motherState" IU.getOptions('Select State', PARENT_STATES) "class='form-control'" />
                                        <@spring.showErrors " & ", "err-msg" />
                                        </div>
                                    </div>
                                </div>

                            <#--2nd Column-->
                                <div class="col-lg-4 col-xs-12"></div>

                            <#--3rd Column-->
                                <div class="col-lg-4 col-xs-12"></div>
                            </div>
                        </div>
                    </div>

                    <div class="widget-toolbox padding-8 clearfix">
                        <div class="btn-group pull-left">
                            <button type="button"
                                    onclick="window.location = '${ctx}/app/viewApplication.se'"
                                    class="btn btn-md btn-danger btn-white btn-round btn-bold">
                                <i class="ace-icon fa fa-backward bigger-120 blue"></i>
                                Back
                            </button>
                        </div>
                        <div class="btn-group pull-right">
                            <button type="button" onclick="window.location = '${ctx}/admin/dashboard.se'"
                                    class="btn btn-md btn-danger btn-white btn-round">
                                <i class="ace-icon fa fa-home bigger-120 blue"></i>
                                Home
                            </button>
                            <button type="submit"
                                    class="btn btn-md btn-danger btn-white btn-round">
                                <i class="ace-icon fa fa-floppy-o bigger-125 green"></i>
                                Save
                            </button>
                            <button type="button"
                                    class="btn btn-md btn-danger btn-white btn-round">
                                <i class="ace-icon fa fa-pencil bigger-125 green"></i>
                                Edit
                            </button>
                            <button type="reset"
                                    class="btn btn-md btn-success btn-white btn-round">
                                <i class="ace-icon fa fa-refresh bigger-125 red2"></i>
                                Reset
                            </button>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>
