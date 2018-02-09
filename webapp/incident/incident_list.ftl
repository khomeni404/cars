<#import "../taim_template/taims_layout.ftl" as layout>
<@layout.taims_layout  '${(title)!}' >
    <#assign ctx = rc.getContextPath()/>
<div class="row">
    <div class="col-xs-12">
        <table id="simple-table" class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th class="center">
                    <#--<label class="pos-rel">
                        <input type="checkbox" class="ace" />
                        <span class="lbl"></span>
                    </label>-->
                </th>
                <th>Ref No.</th>
                <th class="hidden-480">Lost Amt</th>
                <th class="hidden-480">Incident Date</th>
                <th class="hidden-480">Reporting Date</th>

                <th></th>
                <th>
                <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                Update
            </th>
            </tr>
            </thead>

            <tbody>
            <#list list as incident>
            <tr>
                <td class="center">
                    <label class="pos-rel">
                        <input type="checkbox" class="ace" />
                        <span class="lbl"></span>
                    </label>
                </td>

                <td>${(incident.refNo)!}</td>
                <td>${(incident.lossAmt)!}</td>
                <td>${(incident.incidentDate)!}</td>
                <td>${(incident.reportingDate)!}</td>

                <td class="hidden-480">
                    <span class="label label-sm label-warning">Expiring</span>
                </td>

                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button class="btn btn-xs btn-success" onclick="window.location = '${ctx}/incident/viewIncident.ibbl?id=${(incident.id)!}'">
                            <i class="ace-icon fa fa-eye bigger-120"></i>  View
                        </button>

                        <button class="btn btn-xs btn-info" onclick="window.location = '${ctx}/incident/editIncident.ibbl?id=${incident.id}'">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>

                        <button class="btn btn-xs btn-danger">
                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </button>

                        <button class="btn btn-xs btn-warning" onclick="window.location = '${ctx}/incident/createIncidentReview.ibbl?incidentId=${incident.id!}'">
                            <i class="ace-icon fa fa-flag bigger-120"></i>
                        </button>
                    </div>

                    <div class="hidden-md hidden-lg">
                        <div class="inline pos-rel">
                            <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                <li>
                                    <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																			<span class="blue">
																				<i class="ace-icon fa fa-search-plus bigger-120"></i>
																			</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="${ctx}/incident/editIncident.ibbl?id=${incident.id}" class="tooltip-success" data-rel="tooltip" title="Edit">
																			<span class="green">
																				<i class="ace-icon fa fa-pencil-square-o bigger-120">D</i>
																			</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																			<span class="red">
																				<i class="ace-icon fa fa-trash-o bigger-120"></i>
																			</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </td>
            </tr>
            </#list>

            </tbody>
        </table>
    </div><!-- /.span -->
</div>


</@layout.taims_layout>