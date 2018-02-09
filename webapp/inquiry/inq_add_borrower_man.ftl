<#import "../template/mbl_template.ftl" as layout>
<@layout.mbl_layout "${(title)!'--'}", 'new-ir'>
    <#import "/spring.ftl" as spring/>
    <#assign ctx = rc.getContextPath()/>
    <#include '../template/constant.ftl'/>
    <#assign IU = Utility>
<style>
    /* .se-input-lab-r {
         width: 20%
     }

     .se-input {
         width: 30%
     }
 */
    .se-input input {
        width: 100%
    }
    .se-input select {
        width: 100%; height: 25px; cursor: pointer; font-family:"Courier New", Courier, monospace; font-weight: bold;
    }

    table tr td {
        border: 1px solid #9dd2b8;
    }
</style>
<form action="${ctx}/ir/createInquiry.mbl" method="POST">
    <div class="col-md-12">
        <div class="card">
            <div class="card-body table-full-width table-responsive">
                <div class="row" style="padding: 0 10px 0 10px">

                    <div class="col-md-12">
                        <table id="table-header" class="" style="table-layout: fixed; width: 100%">
                            <tr>
                                <td class="se-input-lab-r">Customer ID</td>
                                <td class="se-input">
                                    <@spring.formInput 'SubjectData.custId'/>
                                    <@spring.showErrors " & ", "err-msg" />
                                </td>
                                <td class="se-input-lab-r">Credit Limit</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            <#--<td width="3"></td>-->

                            </tr>
                            <tr>
                                <td class="se-input-lab-r">Repayment Mode</td>
                                <td class="se-input">
                                    <select name="" id="">
                                        <option value="">Non-Installment</option>
                                        <option value="">Installment</option>
                                        <option value="">Card</option>
                                    </select>
                                </td>


                                <td class="se-input-lab-r">Type of Inquiry</td>
                                <td class="se-input">
                                    <select name="" id="">
                                        <option value="2">New/Requested</option>
                                        <option value="1">Living</option>
                                        <option value="0">Others</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-input-lab-r">No of Installment</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            <#--<td width="3"></td>-->
                                <td class="se-input-lab-r">Periodicity of Payment</td>
                                <td class="se-input">
                                    <select name="installIntervalValue" id="">
                                        <option value="0">Fortnightly</option>
                                        <option value="1">Monthly</option>
                                        <option value="2">Bi-Monthly</option>
                                        <option value="3">Quarterly</option>
                                        <option value="6">Half Yearly</option>
                                        <option value="12">Yearly</option>
                                        <option value="-1">Others</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                        <hr/>
                    </div>

                    <div class="col-md-12">
                        <table id="table-2" class="" style="table-layout: fixed; width: 100%">
                            <tr>
                                <td class="se-input-lab-r" width="3%">1.</td>
                                <td class="se-input-lab-l" width="32%">Branch Name</td>
                                <td class="se-input" width="65%">
                                    <input type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-input-lab-r">4.</td>
                                <td class="se-input-lab-l">Borrower Subject Code</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-input-lab-r">5.</td>
                                <td class="se-input-lab-l">Type of Financing <span class="se-mandatory">*</span></td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-input-lab-r">6.</td>
                                <td class="se-input-lab-l">Full Name of the Borrower with Title</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-input-lab-r">7.</td>
                                <td class="se-input-lab-l">Legal Form</td>
                                <td class="se-input">
                                    <select name="">
                                        <option value=""></option>
                                        <option value="Proprietor">Proprietor</option>
                                        <option value="Private Ltd. Co.">Private Ltd. Co.</option>

                                    </select>
                                </td>
                            </tr>

                            <tr>
                                <td class="se-input-lab-r" rowspan="4" style="vertical-align: top">9.</td>
                                <td class="se-input-lab-l">Permanent Address (Borrower)</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-input-lab-l">Present Address (Borrower)</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-input-lab-l">Business Address (Borrower)</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-input-lab-l">Factory Address (Borrower)</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            </tr>


                            <tr>
                                <td class="se-input-lab-r">10.</td>
                                <td class="se-input-lab-l">Sector Code</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-input-lab-r"></td>
                                <td class="se-input-lab-l">Registration No</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-input-lab-r"></td>
                                <td class="se-input-lab-l">Registration Date</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="se-input-lab-r"></td>
                                <td class="se-input-lab-l">Phone/Cell No.</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            </tr>

                        </table>
                    </div>

                    <#--<div class="col-md-12">
                        <table id="table-2" class="" style="table-layout: fixed; width: 100%">
                            <tr>
                                <td class="se-input-lab-r">9.</td>
                                <td class="se-input-lab-l">Full Name of the Borrower with Title</td>
                                <td class="se-input">
                                    <input type="text"/>
                                </td>
                            </tr>
                        </table>
                    </div>-->

                </div>

            </div>
        </div>
    </div>
</form>
</@layout.mbl_layout>
