/**
 * Created by Khomeni on 30/04/2017.
 */
var monthList = Ext.create('Ext.data.Store', {
    fields: ['serial', 'name'],
    data: [
        {"serial": "01", "name": "01. January"},
        {"serial": "02", "name": "02. February"},
        {"serial": "03", "name": "03. March"},
        {"serial": "04", "name": "04. April"},
        {"serial": "05", "name": "05. May"},
        {"serial": "06", "name": "06. June"},
        {"serial": "07", "name": "07. July"},
        {"serial": "08", "name": "08. August"},
        {"serial": "09", "name": "09. September"},
        {"serial": "10", "name": "10. October"},
        {"serial": "11", "name": "11. November"},
        {"serial": "12", "name": "12. December"}
    ]
});
var staffACStore = null;
function dateRangeAndACSelectionWindow(action, extraParam) {
    //postMe(action, 'courseId=' + 2+'&batchNo=' + 2);
    var form = Ext.create('Ext.form.Panel', {
        title: '',
        bodyPadding: 5,
        width: 500,
        //url: action,
        layout: 'anchor',
        defaults: {
            anchor: '100%'
        },
        items: [
            Ext.create('Ext.form.ComboBox', {
                width: 460,
                store: staffACStore,
                queryMode: 'local',
                fieldLabel: 'Account Name',
                displayField: 'name',
                valueField: 'acId',
                name: 'accountId',
                allowBlank: false
            }),
            {
                xtype: 'datefield',
                fieldLabel: 'Date From',
                value: '01/01/2017',// Ext.Date.format(new Date(), 'd/m/Y'),/
                name: 'from',
                format: 'd/m/Y',
                allowBlank: false
            }, {
                xtype: 'datefield',
                fieldLabel: 'Date To',
                value: Ext.Date.format(new Date(), 'd/m/Y'),
                name: 'to',
                allowBlank: false,
                format: 'd/m/Y'
            }],

        buttons: [{
            text: 'Reset',
            handler: function () {
                this.up('form').getForm().reset();
            }
        }, {
            text: 'View',
            formBind: true,
            disabled: true,
            handler: function () {
                var form = this.up('form').getForm();
                var accountId = form.findField('accountId').getValue();
                var from = toDate(form.findField('from').getValue(), 'DD/MM/YYYY');
                var to = toDate(form.findField('to').getValue(), 'DD/MM/YYYY');
                if (form.isValid()) {
                    postMe(action, 'accountId=' + accountId + '&from=' + from + '&to=' + to + extraParam);
                    //alert(action+'?from=' + from + '&to=' + to + extraParam);
                }
            }
        }]
    });
    Ext.create('Ext.window.Window', {
        title: 'Select Month and Type Year',
        items: [form]
    }).show();
}

function dateRangeSelectionWindow(action, extraParam) {
    //postMe(action, 'courseId=' + 2+'&batchNo=' + 2);
    var form = Ext.create('Ext.form.Panel', {
        title: '',
        bodyPadding: 5,
        width: 350,
        //url: action,
        layout: 'anchor',
        defaults: {
            anchor: '100%'
        },
        items: [
            {
                xtype: 'datefield',
                fieldLabel: 'Date From',
                value: Ext.Date.format(new Date(), 'd/m/Y'),
                name: 'from',
                format: 'd/m/Y',
                allowBlank: false
            }, {
                xtype: 'datefield',
                fieldLabel: 'Date To',
                value: Ext.Date.format(new Date(), 'd/m/Y'),
                name: 'to',
                allowBlank: false,
                format: 'd/m/Y'
            }],

        buttons: [{
            text: 'Reset',
            handler: function () {
                this.up('form').getForm().reset();
            }
        }, {
            text: 'View',
            formBind: true,
            disabled: true,
            handler: function () {
                var form = this.up('form').getForm();
                var from = toDate(form.findField('from').getValue(), 'DD/MM/YYYY');
                var to = toDate(form.findField('to').getValue(), 'DD/MM/YYYY');
                if (form.isValid()) {
                    postMe(action, 'from=' + from + '&to=' + to + extraParam);
                    //alert(action+'?from=' + from + '&to=' + to + extraParam);
                }
            }
        }]
    });
    Ext.create('Ext.window.Window', {
        title: 'Select Month and Type Year',
        items: [form]
    }).show();
}

function monthAndYearSelectionWindow(action, extraParam) {
    //postMe(action, 'courseId=' + 2+'&batchNo=' + 2);
    var form = Ext.create('Ext.form.Panel', {
        title: '',
        bodyPadding: 5,
        width: 350,
        //url: action,
        layout: 'anchor',
        defaults: {
            anchor: '100%'
        },
        items: [
            Ext.create('Ext.form.ComboBox', {
                width: 300,
                store: monthList,
                queryMode: 'local',
                fieldLabel: 'Month Name',
                displayField: 'name',
                valueField: 'serial',
                name: 'month',
                allowBlank: false,
                listeners: {
                    afterrender: function (combo) {
                        var record = monthList.getAt(gv_lastMonth - 1);
                        var serial = record.get('serial');
                        combo.setValue(serial);
                    }
                }
            })
            , {
                xtype: 'numberfield',
                fieldLabel: 'Year',
                value: 2017,
                name: 'year',
                minLength: 4,
                allowBlank: false
            }],

        buttons: [{
            text: 'Reset',
            handler: function () {
                this.up('form').getForm().reset();
            }
        }, {
            text: 'Proceed',
            formBind: true,
            disabled: true,
            handler: function () {
                var form = this.up('form').getForm();
                var year = form.findField('year').getValue();
                var month = form.findField('month').getValue();
                if (form.isValid()) {
                    postMe(action, 'year=' + year + '&month=' + month + extraParam);
                }
            }
        }]
    });
    Ext.create('Ext.window.Window', {
        title: 'Select Month and Type Year',
        items: [form]
    }).show();
}

function yearSelectionWindow(action) {
    //postMe(action, 'courseId=' + 2+'&batchNo=' + 2);
    var form = Ext.create('Ext.form.Panel', {
        title: '',
        bodyPadding: 5,
        width: 350,
        //url: action,
        layout: 'anchor',
        defaults: {
            anchor: '100%'
        },
        items: [
            {
                xtype: 'numberfield',
                fieldLabel: 'Year',
                value: 2017,
                name: 'year',
                minLength: 4,
                allowBlank: false
            }],

        buttons: [{
            text: 'Reset',
            handler: function () {
                this.up('form').getForm().reset();
            }
        }, {
            text: 'View',
            formBind: true,
            disabled: true,
            handler: function () {
                var form = this.up('form').getForm();
                var year = form.findField('year').getValue();
                if (form.isValid()) {
                    postMe(action, 'year=' + year);
                }
            }
        }]
    });
    Ext.create('Ext.window.Window', {
        title: 'Select Month and Type Year',
        items: [form]
    }).show();
}