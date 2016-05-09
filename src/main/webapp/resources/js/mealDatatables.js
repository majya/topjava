/**
 * Created by Mayia on 08.05.2016.
 */
var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, function (data){
        updateTableByData(data);
    });
    return false;
}

$(function () {
    datatableApi = $('#datatable').DataTable(
        {
            "ajax": {
                "url": ajaxUrl,
                "dataSrc": ""
            },
            
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type == 'display') {
                            var dateObject = new Date(date);
                            return '<span>' + dateObject.toISOString().substring(0, 10) + '</span>';
                        }
                        return date;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                    $(row).addClass(data.exceed ? 'exceeded' : 'normal');

            }
        });

    $('#filter').submit(function () {
        updateTable();
        return false;
    });
    makeEditable();
});
