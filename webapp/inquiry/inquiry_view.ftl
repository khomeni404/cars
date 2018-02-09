<#import "../template/mbl_template.ftl" as layout>
<@layout.mbl_layout "${(title)!'--'}", 'view-ir'>
    <#import "/spring.ftl" as spring/>
    <#assign ctx = rc.getContextPath()/>
    <#include '../template/constant.ftl'/>
    <#assign IU = Utility>
    <#--<#assign inqStatus = Inquiry.status>-->
    <#assign inqStatus = 13>
    <#assign costStatus = 1> <#-- Inquiry.costStatus>-->
    <#assign isCibOperator = cibOperator>
<style>
    #inquiry-table tr td {
        padding: 1px 0 1px 5px;
        border: 1px double #9dd2b9
    }

    #data-table tr td {
        padding: 1px 0 1px 5px;
        border: 1px double #9dd2b9
    }

    #data-table tr .se-label-r {
        width: 20%;
    }

    #data-table tr .se-value-l {
        width: 30%;
    }
</style>


<div class="col-md-12">
    <div class="card strpied-tabled-with-hover">
        <div class="card-header ">
            <span class=""><b>${(Inquiry.inqNo)!'--'} : ${(Inquiry.custName)!'--'}</b></span>
            <span class="pull-right card-category">
                <b>(${Inquiry.inqFor?has_content?then(IU.getProp(CUSTOMER_TYPE, Inquiry.inqFor?string)!'--','--')})</b>
            </span>
        </div>
        <div class="card-body table-full-width table-responsive">
            <div class="row" style="padding: 0 10px 0 10px">
                <div class="col-md-12">
                    <table id="inquiry-table" class="" width="100%">
                        <tbody>
                        <tr>
                            <td class="se-label-r" width="20%">Master Customer Name</td>
                            <td class="se-value-l " width="30%">${(Inquiry.custName)!'--'}</td>
                            <td class="se-label-r" width="20%">Request Date</td>
                            <td class="se-value-l "
                                width="100%">${(Inquiry.requestDate?string('dd/MM/yyyy'))!'--'}</td>
                        </tr>
                        <tr>
                            <td class="se-label-r">Master Customer ID</td>
                            <td class="se-value-l ">${(Inquiry.custId)!'--'}</td>
                            <td class="se-label-r">Branch Code</td>
                            <td class="se-value-l " width="100%">${(Inquiry.brCode)!'--'}</td>
                        </tr>
                        <tr>
                            <td class="se-label-r">Inquiry Type</td>
                            <td class="se-value-l ">
                            ${Inquiry.inqType?has_content?then(IU.getInqType(Inquiry.inqType?string)!'--','--')}
                            </td>
                            <td class="se-label-r">Type of Financing</td>
                            <td class="se-value-l " width="100%">${(Inquiry.financingType)!'--'}</td>
                        </tr>
                        <tr>
                            <td class="se-label-r">Credit Mode</td>
                            <td class="se-value-l ">
                            ${Inquiry.crMode?has_content?then(IU.getCrMode(Inquiry.crMode?string)!'--','--')}
                            </td>
                            <td class="se-label-r">No of Installment</td>
                            <td class="se-value-l " width="100%">
                            ${(Inquiry.emi)!'N / A'}
                            &nbsp;(${Inquiry.pop?has_content?then(IU.getPOP(Inquiry.pop?string)!'--','--')})
                            </td>
                        </tr>
                        <tr>
                            <td class="se-label-r">Inquiry Status</td>
                            <td class="se-value-l ">
                            ${(IU.getInquiryStatus(inqStatus))!'---'} <br/>
                                <span style="font-size: 8px">${(IU.getInquiryStatusDesc(inqStatus))!'---'}</span>
                            </td>
                            <td class="se-label-r">Cost Status</td>
                            <td class="se-value-l " width="100%">
                                <#if Inquiry.costStatus = 1>
                                    Posted <br/>
                                    <span style="font-size: 8px">BDT. ${(Inquiry.getTotalCost())!}/-</span>
                                <#else >
                                    Not Posted ${(Inquiry.getTotalCost())!}
                                </#if>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="col-md-12" style="/*display: none;*/">
    <div class="card strpied-tabled-with-hover">
        <div class="card-body table-full-width table-responsive">
            <#list subjectDataList as sd>
                <#assign recordType = sd.recordType>
                <div class="col-md-12">
                    <div class="form-row data-header" style="cursor: pointer; background-repeat: green; height: 20px"
                         title="Show Details"
                         onclick="$('#subject-data-col-'+${(sd.custId)!'--'}).toggle(); $('#subject-data-col-preli-'+${(sd.custId)!'--'}).toggle()">
                        <div class="col-md-12" style="color: #0A0B0C">
                        ${sd?index+1}. Limited Company : Director<#-- <%=CustType.get(custTypeCode).toString() + " : "
                    + (groupSerial == 0 ? "Core Customer" : (relationText))%>-->
                        </div>

                    </div>
                </div>

                <div class="col-md-12" id="subject-data-col-preli-${(sd.custId)!'--'}">
                    <div class="form-row" style="padding: 0 5px 0 5px">
                        <div class="col-md-1 se-label-r">Cust ID</div>
                        <div class="col-md-2 se-value-l copy-me">
                        ${(sd.custId)!'--'}
                        </div>

                        <div class="col-md-1 se-label-r">Name</div>
                        <div class="col-md-8 se-value-l">
                            <span class="copy-me">${(sd.name)!'--'}</span>
                        </div>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="row" id="subject-data-col-${(sd.custId)!'--'}"
                         style="display: none; padding: 0 15px 0 32px">
                        <table id="data-table" class="" width="100%">
                            <tbody>
                            <tr>
                                <td class="se-label-r">Customer ID</td>
                                <td class="se-value-l copy-me">${(sd.custId)!'--'}</td>
                                <td class="se-label-r">Relation</td>
                                <td class="se-value-l copy-me">${(sd.sectorCode)!'--'}</td>
                            </tr>
                            <tr>
                                <td class="se-label-r">BR Code</td>
                                <td class="se-value-l copy-me">${(sd.brCode)!'--'}</td>
                                <td class="se-label-r">Sector Code</td>
                                <td class="se-value-l copy-me">${(sd.sectorCode)!'--'}</td>
                            </tr>
                            <tr>
                                <td class="se-label-r">Customer Title</td>
                                <td class="se-value-l copy-me">${(sd.title)!'--'}</td>
                                <td class="se-label-r">Customer Name</td>
                                <td class="se-value-l copy-me">${(sd.name)!'--'}</td>
                            </tr>

                            <#--For Individuals-->
                                <#if recordType='P'>
                                <tr>
                                    <td class="se-label-r">Father Title</td>
                                    <td class="se-value-l copy-me">${(sd.fatherTitle)!'--'}</td>
                                    <td class="se-label-r">Father Name</td>
                                    <td class="se-value-l copy-me">${(sd.fatherName)!'--'}</td>
                                </tr>

                                <tr>
                                    <td class="se-label-r">Mother Title</td>
                                    <td class="se-value-l copy-me">${(sd.motherTitle)!'--'}</td>
                                    <td class="se-label-r">Mother Name</td>
                                    <td class="se-value-l copy-me">${(sd.motherName)!'--'}</td>
                                </tr>

                                <tr>
                                    <td class="se-label-r">Spouse Title</td>
                                    <td class="se-value-l copy-me">${(sd.spouseTitle)!'--'}</td>
                                    <td class="se-label-r">Spouse Name</td>
                                    <td class="se-value-l copy-me">${(sd.spouseName)!'--'}</td>
                                </tr>

                                <tr>
                                    <td class="se-label-r">TIN</td>
                                    <td class="se-value-l copy-me">${(sd.tin)!'--'}</td>
                                    <td class="se-label-r">NID</td>
                                    <td class="se-value-l copy-me">${(sd.nid)!'--'}</td>
                                </tr>
                                </#if>


                            <#--For Company-->
                                <#if recordType != 'P'>
                                <tr>
                                    <td class="se-label-r">TIN</td>
                                    <td class="se-value-l copy-me">${(sd.tin)!'--'}</td>
                                    <td class="se-label-r">Legal Form</td>
                                    <td class="se-value-l copy-me">${(sd.legalForm)!'--'}</td>
                                </tr>
                                <tr>
                                    <td class="se-label-r">Reg. Date</td>
                                    <td class="se-value-l copy-me">${(sd.regDate)!'--'}</td>
                                    <td class="se-label-r">Reg. No.</td>
                                    <td class="se-value-l copy-me">${(sd.regNo)!'--'}</td>
                                </tr>
                                </#if>

                            <#--Individuals-->
                                <#if recordType = 'P'>
                                <tr>
                                    <td class="se-label-r">Sex</td>
                                    <td class="se-value-l copy-me">${sd.getSexText()!'--'}</td>
                                    <td class="se-label-r">Date of Birth</td>
                                    <td class="se-value-l copy-me">${(sd.dob)!'--'}</td>
                                </tr>
                                <tr>
                                    <td class="se-label-r">Birth Place (District)</td>
                                    <td class="se-value-l copy-me">${(sd.birthPlace)!'--'}</td>
                                    <td class="se-label-r">Birth Country</td>
                                    <td class="se-value-l copy-me">${(sd.birthCountry)!'--'}</td>
                                </tr>
                                </#if>

                            <!--Permanent Address>-->
                            <tr>
                                <td colspan="4">
                                    <div class="form-row" style="padding: 0 5px 0 0">
                                        <div class="col-md-12 se-value-l"><b style="color: #c05300">Permanent
                                            Address</b>
                                        <span class="copy-me pull-right">
                                        ${(sd.permanentAddress)!'--'}
                                        </span>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-label-r">District</td>
                                <td class="se-value-l copy-me">${(sd.permanentDist)!'--'}</td>
                                <td class="se-label-r">Country Code</td>
                                <td class="se-value-l copy-me">${(sd.pecCode)!'--'}</td>
                            </tr>
                            <tr>
                                <td class="se-label-r">Post Code</td>
                                <td class="se-value-l copy-me">${(sd.permanentPC)!'--'}</td>
                                <td class="se-label-r">Country</td>
                                <td class="se-value-l copy-me">${(sd.permanentCountry)!'--'}</td>
                            </tr>

                            <!--Present Address-->
                            <tr>
                                <td colspan="4">
                                    <div class="form-row" style="padding: 0 5px 0 0">
                                        <div class="col-md-12 se-value-l"><b style="color: #c05300">Present Address</b>

                                        <span class="copy-me pull-right">
                                        ${(sd.presentAddress)!'--'}
                                        </span>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-label-r">District</td>
                                <td class="se-value-l copy-me">${(sd.presentDist)!'--'}</td>
                                <td class="se-label-r">Country Code</td>
                                <td class="se-value-l copy-me">${(sd.prcCode)!'--'}</td>
                            </tr>
                            <tr>
                                <td class="se-label-r">Post Code</td>
                                <td class="se-value-l copy-me">${(sd.presentPC)!'--'}</td>
                                <td class="se-label-r">Country</td>
                                <td class="se-value-l copy-me">${(sd.presentCountry)!'--'}</td>
                            </tr>

                            <!--Company : Business Address-->
                                <#if recordType != 'P'>
                                <tr>
                                    <td colspan="4">
                                        <div class="form-row" style="padding: 0 5px 0 0">
                                            <div class="col-md-12 se-value-l"><b style="color: #c05300">Business
                                                Address</b>

                                            <span class="copy-me pull-right">
                                            ${(sd.businessAddress)!'--'}
                                            </span>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="se-label-r">District</td>
                                    <td class="se-value-l copy-me">${(sd.businessDist)!'--'}</td>
                                    <td class="se-label-r">Country Code</td>
                                    <td class="se-value-l copy-me">${(sd.bzcCode)!'--'}</td>
                                </tr>
                                <tr>
                                    <td class="se-label-r">Post Code</td>
                                    <td class="se-value-l copy-me">${(sd.businessPC)!'--'}</td>
                                    <td class="se-label-r">Country</td>
                                    <td class="se-value-l copy-me">${(sd.businessCountry)!'--'}</td>
                                </tr>

                                </#if>
                            <!--Individuals : Identification Document-->
                                <#if recordType = 'P'>
                                <tr>
                                    <td colspan="4">
                                        <div class="form-row" style="padding: 0 5px 0 0">
                                            <div class="col-md-12 se-value-l"><b style="color: #c05300">Identification
                                                Document & Phone No.</b></div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="se-label-r">ID Type</td>
                                    <td class="se-value-l copy-me">${(sd.idType)!'--'}</td>
                                    <td class="se-label-r">ID Ref. No</td>
                                    <td class="se-value-l copy-me">${(sd.idRefNo)!'--'}</td>
                                </tr>

                                <tr>
                                    <td class="se-label-r">ID Issue Date</td>
                                    <td class="se-value-l copy-me">${(sd.idIssueDate)!'--'}</td>
                                    <td class="se-label-r">ID Issue Country</td>
                                    <td class="se-value-l copy-me">${(sd.idIssueCountry)!'--'}</td>
                                </tr>
                                </#if>
                            <#--Common-->
                            <tr>
                                <td class="se-label-r">Phone No</td>
                                <td class="se-value-l copy-me">${(sd.phoneNo)!'--'}</td>
                                <td class="se-label-r">Cell No</td>
                                <td class="se-value-l copy-me">${(sd.cellNo)!'--'}</td>
                            </tr>

                            </tbody>
                        </table>

                    </div>
                </div>
                <br/>
            </#list>
        </div>
    </div>
</div>

<div class="col-md-12">
    <#if INQ_COST_STATUS_POSTED = costStatus>
        DEBITED
    <#else >
        not DEBITED
    </#if>
    <#if inqStatus = INQ_STATUS_PROCESSING>
        PROCESSING
    <#else >
        not PROCESSING
    </#if>
    <#if isCibOperator>
        CibOperator
    <#else >
        not CibOperator
    </#if>

    || ${(IU.getInquiryStatus(inqStatus))!'---'}
</div>

    <#if inqStatus != INQ_STATUS_CLOSED>
    <div class="col-md-12">
        <div class="card strpied-tabled-with-hover">
            <div class="card-body table-full-width table-responsive">
                <div class="col-md-12 text-right">

                    <#if isCibOperator && inqStatus = INQ_STATUS_PROCESSING && costStatus = INQ_COST_STATUS_POSTED>
                        <input type="button" class="btn btn-sm btn-warning  pointer" value="Attach CIB Rpt."
                               onclick="attachCIBReport()">
                    <#elseif (inqStatus = INQ_STATUS_CREATED || inqStatus = INQ_STATUS_COMPLAINED) && !isCibOperator>
                        <input type="button" class="btn btn-sm btn-info  pointer"
                               value="Attach Undertaking"
                               onclick="attachUndertaking()">

                    </#if>

                    <#if inqStatus = INQ_STATUS_CREATED>
                        <input type="button" id="approve-and-forward" class="btn btn-sm btn-info  pointer op-btn"
                               value="Approved & Forward">
                    </#if>
                    <#if isCibOperator && inqStatus = INQ_STATUS_FORWARDED>
                        <input type="button" id="process-request" class="btn btn-sm btn-success  pointer op-btn"
                               value="Start Processing">
                    </#if>
                    <#if isCibOperator && (inqStatus = INQ_STATUS_PROCESSING || inqStatus = INQ_STATUS_FORWARDED) && costStatus = INQ_COST_STATUS_PENDING>
                        <#if inqStatus = INQ_STATUS_FORWARDED>
                            <td><input type="button" onclick="chargeCreate();" style=""
                                       class="btn btn-sm btn-warning  pointer op-btn"
                                       value="Balance"></td>
                        <#else >
                            <td><input type="button" onclick="chargeCreate();" style=""
                                       class="btn btn-sm btn-warning  pointer op-btn"
                                       value="Make Charge"></td>
                        </#if>

                    <#else >
                        <input type="button" id="charge-create" class="btn btn-sm btn-warning pointer op-btn"
                               value="Test Charge(X)">

                    </#if>


                    <#if isCibOperator && inqStatus = INQ_STATUS_PROCESSING && costStatus = INQ_COST_STATUS_POSTED>
                    <#--<input type="button" style="" class="btn-warning pointer op-btn"
                                value="Upload CIB Report."
                                onclick="uploadDocument('<%=CIBDictionary.CIB_UPLOAD_KEY_CIB_RPT%>')">-->
                    </#if>
                    <#if inqStatus = INQ_STATUS_CREATED || ((inqStatus = INQ_STATUS_PROCESSING || inqStatus = INQ_STATUS_FORWARDED)) && isCibOperator>
                        <input type="button" onclick="makeComplain();" style=""
                               class="btn btn-sm btn-success pointer op-btn"
                               value="Complain & Objection">
                    </#if>

                    <#if inqStatus = INQ_STATUS_COMPLAINED>
                        <input type="button" style="" id="btn-complain-resolved"
                               class="btn btn-sm btn-success pointer op-btn"
                               value="Objection Resolved">
                    </#if>
                    <#if inqStatus = INQ_STATUS_REPORTED>
                        <input type="button" style="" id="close-request" class="btn btn-sm btn-info pointer op-btn"
                               value="Close IR">
                    <#elseif costStatus != INQ_COST_STATUS_POSTED && inqStatus != INQ_STATUS_REJECTED>

                        <input type="button" style="" id="reject-request" class="btn btn-sm btn-info pointer op-btn"
                               onclick="rejectIR();"
                               value="Reject IR">
                    </#if>

                    <input type="button" style="" class="btn btn-sm btn-warning pointer op-btn"
                           value="Back"
                           onclick="back()">
                </div>
            </div>
        </div>
    </div>
    </#if>


<style>
    .more-info-link:hover {
        font-weight: bold;
        font-style: normal;
    }

    a {
        color: blue
    }

    a:hover {
        color: black;
        font-weight: bold
    }

    .data-header {
        text-shadow: 1px 1px 1px rgba(2, 2, 1, 0.5);
        font-size: 11px;
        /*height: 30px;*/
        color: #ffffff;
        padding-right: 10px;
        margin: 0;
        background-color: #d1d1d1;
        text-transform: uppercase;
        font-weight: bold;
        /*line-height: 20px;*/
    }
</style>


<div class="row" style="/*display: none*/">
    <div class="col-md-12">
        <div class="block ibbl-block">
            <div class="content controls">
                <table class="table table-bordered table-striped" border="1">
                    <thead class="ibbl-thead-l">
                    <tr class="tr-head">
                        <th colspan="6"><b>Operation History</b></th>
                    </tr>
                    </thead>
                    <thead class="ibbl-thead-l">
                    <tr class="td-color2" style="font-weight: bold; font-size: 10px">
                        <th width="10%">Date & Time</th>
                        <th width="10%">On Status</th>
                        <th>History Note</th>
                        <th width="10%">Operator</th>
                    </tr>
                    </thead>
                    <#list historyList as history>
                        <tr class="">
                            <td>
                            <#--<%=sdf.format(history.getRecordDate())%>-->
                                <br>
                            <#--<%=stf.format(history.getRecordDate())%>-->
                            </td>
                            <td>
                            <#--<%="On " + InquiryStatus.get(history.getInqStatus()).STATUS%>-->
                            </td>
                            <td>
                            <#--<%=history.getHistoryNote()%>-->
                            </td>
                            <td>
                            <#--<%=history.getOperator()%>-->
                            </td>
                        </tr>
                    </#list>

                </table>
            </div>
        </div>
    </div>
</div>


<style>
    #data-block-table tr td {
        vertical-align: top
    }
</style>


</@layout.mbl_layout>

<script type="text/javascript">
    var isIE = false || !!document.documentMode;
    $(function () {
        $('.Body').css({
            'vertical-align': 'top',
            'padding-top': '5px'
        });
        $('.tr-opc').hide();
        $('#complain-note').hide();
        var repayInst = $('.repay-inst');
        repayInst.hide();
        var repayMode = '<%=repayMode%>';
        if (repayMode == 1001) {
            repayInst.show();
        }
    });

    function showOpcData(counter) {
        $('.tr-opc-' + counter).toggle();
    }

    $('#approve-and-forward').click(function () {
        var msg = 'Are you sure to FORWARD this IR to CIB ?';
        var url = '${ctx}/genInquiryAction.do?action=update&inqOid=<%=inqOid%>&updateKey=<%=InquiryStatus.FORWARDED.STATUS%>';
        Ext.Msg.confirm('Attention !', msg, function (btn, text) {
            if (btn == 'yes') {
                Ext.MessageBox.wait('Please wait until response.', 'Forwarding...');
                window.location = url;
            }
        });
    });

    $('#process-request').click(function () {
        var url = '${ctx}/genInquiryAction.do?action=update&inqOid=<%=inqOid%>&updateKey=<%=InquiryStatus.PROCESSING.STATUS%>';
        var msg = 'Do you want to start <b>PROCESSING</b> of this \'IR\' ?<br/>N.B. This \'IR\' will go to concern \'CIB Operator\'s queue for being processed.';

        Ext.Msg.confirm('Attention !', msg, function (btn, text) {
            if (btn == 'yes') {
                Ext.MessageBox.wait('Please wait until response.', 'Sending to Queue...');
                window.location = url;
            }
        });

    });

    function rejectIR() {
        var msg = 'Do you want to <b style="color: #ba4a00">REJECT</b> this \'IR\' ?<br/>N.B. This \'IR\' will never seen again.';

        Ext.Msg.confirm('Attention !', msg, function (btn, text) {
            if (btn == 'yes') {
                Ext.MessageBox.wait('Please wait until response.', 'Sending to Queue...');
                postMe('${ctx}/genInquiryAction.do', 'action=<%=CibInquiryAction.ACTION_METHOD_REJECT%>&oid=<%=inqOid%>')
            }
        });

    }

    $('#close-request').click(function () {
        var msg = 'Are you sure to CLOSE this Inquiry Story ?';
        var url = '${ctx}/genInquiryAction.do';
        Ext.MessageBox.confirm('Attention please !', msg, callbackFunction);
        function callbackFunction(btn) {
            if (btn == 'yes') {
                postMe(url, 'action=<%=CibInquiryAction.ACTION_METHOD_UPDATE%>&inqOid=<%=inqOid%> & updateKey=<%=InquiryStatus.CLOSED.STATUS%>');
            }
        }
    });

    function chargeCreate() {
        window.location = '${ctx}/genInquiryAction.do?action=<%=CibInquiryAction.ACTION_METHOD_BALANCE%>&inqOid=<%=inqOid%>';
    }


    /**
     * MAY BE NOT IN USE
     */
    $('#add-doc').click(function () {
        window.location = '${ctx}/addReportDoc.do?inqOid=<%=inqOid%>';
    });


    function attachCIBReport() {
        var msg = 'CIB charge yet not been realized.';
        var chargeDebited = ''; // <%= costStatus == CIBDictionary.CIB_COST_STATUS_DEBITED % >;
        if (chargeDebited) {
            postMe('${ctx}/addReportDoc.do', 'action=<%=CibInquiryAction.ACTION_METHOD_ADD%>&inqOid=<%=inqOid%>&docName=<%=CIBDictionary.CIB_DOC_NAME_CIB_REPORT%>');
        } else {
            Ext.Msg.alert('Attention!', msg);
        }
    }

    function attachUndertaking() {
        postMe('${ctx}/addReportDoc.do', 'action=<%=CibInquiryAction.ACTION_METHOD_ADD%>&inqOid=<%=inqOid%>&docName=<%=CIBDictionary.CIB_DOC_NAME_UNDERTAKING%>');
    }


    function makeComplain() {
        Ext.create('Ext.window.Window', {
            title: 'Complain Window',
            width: 400,
            layout: 'fit',
            items: {
                xtype: 'form',
                border: false,
                items: [
                    {
                        xtype: 'textarea',
                        anchor: '100%',
                        name: 'complainNote',
                        allowBlank: false,
                        fieldLabel: 'Complain Note'
                    }
                ], buttons: [{
                    text: 'Send Complain',
                    formBind: true,
                    disabled: true,
                    iconCls: 'check',
                    handler: function () {
                        var form = this.up('form').getForm();
                        var complainNote = form.findField("complainNote").getValue();
                        if (form.isValid()) {
                            window.location = '${ctx}/genInquiryAction.do?action=update&inqOid=<%=inqOid%>&updateKey=<%=InquiryStatus.COMPLAINED.STATUS%>&compNote=' + complainNote;
                        }
                    }
                }]
            }
        }).show();
    }


    $('#btn-complain-resolved').click(function () {
        var msg = 'Are you sure that Complain has been resolved ?<br/>You could better review \'complain\' again.';
        var url = '${ctx}/genInquiryAction.do?action=update&inqOid=<%=inqOid%>&updateKey=<%=CIBDictionary.CIB_UPDATE_KEY_RESOLVE%>';
        Ext.Msg.confirm('Attention !', msg, function (btn, text) {
            if (btn == 'yes') {
                //Ext.MessageBox.wait('Please wait until response.', 'Sending to Queue...');
                window.location = url;
            }
        });
    });

    function showCustDataFromEIBS(custID) {
        if (custID == '') {
            Ext.Msg('Sorry !', 'Select a customer ID first.');
            return false;
        } else {
            Ext.MessageBox.wait('Pulling data from eIBS....', 'Please wait.');
            showCustomerDataFromEIBS('<%=EibsDataAction.VIEW%>', custID);
            Ext.MessageBox.hide();
        }
    }

    function back() {
        window.location = '${ctx}/genInquiryAction.do?action=search';
    }

</script>
<style>
    .panel-container-cust-info {
        color: red;
    }

    #operations-tr td {
        text-align: center;
        vertical-align: middle;
        border: 0;
        max-width: 152px
    }

    .op-btn {
        max-width: 150px
    }
</style>