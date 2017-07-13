// responsable/recepcion/recepcion.js
var respReceRece={
    procesarGridExpeResponsable:function() {
        var nombres = ['idExpe','flagOrigen','','C&Oacute;DIGO OSINERGMIN','RAZ&Oacute;N SOCIAL','idEmpresaSupervisada','ruc','tipoDocumentoIdentidad','nroIdentificacion','N&Uacute;MERO EXPEDIENTE', 'FECHA CREACI&Oacute;N SIGED','','FLUJO SIGED','ASUNTO SIGED',''];
        var columnas = [
            {name: "idExpediente", hidden:true},
            {name: "flagOrigen", hidden:true},
            {name: "unidadSupervisada.idUnidadSupervisada", hidden:true},
            {name: "unidadSupervisada.codigoOsinergmin", width: 85, sortable: false, align: "left"},
            {name: "empresaSupervisada.razonSocial", width: 305, sortable: false, align: "left"},
            {name: "empresaSupervisada.idEmpresaSupervisada", hidden:true},
            {name: "empresaSupervisada.ruc", hidden:true},
            {name: "empresaSupervisada.tipoDocumentoIdentidad.descripcion", hidden:true},
            {name: "empresaSupervisada.nroIdentificacion", hidden:true},
            {name: "numeroExpediente", width: 100, sortable: false, align: "left"},
            {name: "fechaCreacionSiged", width: 120, sortable: false, align: "left",formatter:fecha_hora},
            {name: "flujoSiged.idFlujoSiged", hidden:true},
            {name: "flujoSiged.nombreFlujoSiged", width: 195, sortable: false, align: "left"},
            {name: "asuntoSiged", width: 300, sortable: false, align: "left"},
            {name: 'check',width:21, sortable: false, align:"center",formatter: "checkboxSelecExpeResponsable"}
        ];
        $("#gridContenedorExpeResponsable").html("");
        var grid = $("<table>", {
            "id": "gridExpeResponsable"
        });
        var pager = $("<div>", {
            "id": "paginacionExpeResponsable"
        });
        $("#gridContenedorExpeResponsable").append(grid).append(pager);

        grid.jqGrid({
            url: baseURL + "pages/expediente/findExpediente",
            datatype: "json",
            mtype : "POST",
            postData: {
                idPersonal:$('#idPersonalSesion').val(),
                identificadorEstado:constant.identificadorEstado.expRegistro,
                numeroExpediente:$('#txtNumeExpeBusqExpe').val(),
                numeroOrdenServicio:$('#txtNumeOSBusqExpe').val(),
                razonSocial:$('#txtRazoSociBusqExpe').val(),
                codigoOsinergmin:$('#txtCodigoOsiBusqExpe').val()
            },
            hidegrid: false,
            rowNum: constant.rowNum,
            pager: "#paginacionExpeResponsable",
            emptyrecords: "No se encontraron resultados",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: "auto",
            viewrecords: true,
            caption: "",
            jsonReader: {
                root: "filas",
                page: "pagina",
                total: "total",
                records: "registros",
                repeatitems: false,
                id: "idExpediente"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            onRightClickRow: function(rowid) {
                var row=$('#gridExpeResponsable').jqGrid("getRowData",rowid);
                $('#linkDocumentosExpe').attr('onClick', 'common.abrirDocumentoExpediente("'+rowid+'","gridExpeResponsable")');
                $('#linkUnidadOperativa').attr('onClick', 'common.abrirUnidadOperativa("'+rowid+'","gridExpeResponsable")');
            },
            loadComplete: function(data) {
                //agregar check seleccionar todos
                var html='<input id="chkSeleAllVisibles" type="checkbox" name="" value="" onclick="respReceRece.selecAllExpedienteVisibles(this);"><label for="chkSeleAllVisibles" class="checkbox"></label>';
                $('#jqgh_gridExpeResponsable_check').html(html);
                $('#jqgh_gridExpeResponsable_check').css('height','20px');
                $('#jqgh_gridExpeResponsable_check').removeClass('ui-jqgrid-sortable');
                //contextMenu
                $('#contextMenuExpeResponsable').parent().remove();
                $('#divContextMenuExpeResponsable').html("<ul id='contextMenuExpeResponsable'>"
                        + "<li> <a id='linkDocumentosExpe' data-icon='ui-icon-document'>Ver Documentos</a></li>"
                        + "<li> <a id='linkUnidadOperativa' data-icon='ui-icon-home'>Asignar Unidad Operativa</a></li>"
                        + "</ul>");
                $('#contextMenuExpeResponsable').puicontextmenu({
                    target: $('#gridExpeResponsable')
                });
                $('#contextMenuExpeResponsable').parent().css('width','182px');
                /////
                //para marcar los ya seleccionados, casos de navegar entre paginas de grilla
                $('#expeSeleccionados').find('div.filaForm').map(function(){
                    $('#idExpe'+$(this).attr('id').replace('idExpeSele','')).attr('checked',true);
                });
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    selecAllExpedienteVisibles:function(obj){
        if($(obj).attr('checked')){
            $('#gridExpeResponsable').find('input').attr('checked',true);
        }else{
            $('#gridExpeResponsable').find('input').attr('checked',false);
        }
        $('#gridExpeResponsable').find('input').map(function(){
            respReceRece.selecExpediente(this);
        });
    },
    selecExpediente:function(obj){
        if($(obj).attr('checked')){
            if($('#idExpeSele'+$(obj).attr('id').replace('idExpe','')).length=='0'){
                var html='<div class="filaForm" id="idExpeSele'+$(obj).attr('id').replace('idExpe','')+'">'+
                    '<div style="margin:0px 0px 0px 5px;">'+$(obj).attr('numeroExpediente')+'</div>'+
                    '</div>';
                $('#expeSeleccionados').append(html);
            }
        }else{
            $('#idExpeSele'+$(obj).attr('id').replace('idExpe','')).remove();
        }
    },
    validaDerivarExpedientes:function(){
        if($('#expeSeleccionados .filaForm').length<1){
                mensajeGrowl('warn', "Seleccione expediente(s) a derivar", "");
        }else if($('#derivarExpedidiente').validateAllForm("#divMensValidaDerivarExpedidiente")){
            confirmer.open("¿Confirma que desea derivar los expedientes seleccionados?","respReceRece.procesaDerivarExpedientes()");
        }
    },
    procesaDerivarExpedientes:function(){
        $.ajax({
            url:baseURL + "pages/expediente/derivar",
            type:'get',
            async:false,
            data:{
                idPersonalOri:$('#idPersonalSesion').val(),
                idPersonalDest:$('#idDestinatarioDerivar').val(),
                cadIdExpedientes:$('#expeSeleccionados').find('div.[id^="idExpe"]').map(function(){return $(this).attr('id').replace('idExpeSele','')}).get().join(",")
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                if(data.resultado=="0"){
                    mensajeGrowl('success', 'Se derivaron satisfactoriamente los expedientes seleccionados', '');
                    respReceRece.procesarGridExpeResponsable();
                    $('#expeSeleccionados').html('');
                    $('#idDestinatarioDerivar').val('');
                }else{
                    mensajeGrowl('error', data.mensaje, '');
                }
            },
            error:errorAjax
        });
    },
    abrirExpeDeriResponsable:function(){
        $.ajax({
            url:baseURL + "pages/bandeja/abrirExpeDeriResponsable",
            type:'get',
            async:false,
            data:{
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogExpeDeriResponsable").html(data);
                $("#dialogExpeDeriResponsable").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: "EXPEDIENTES DERIVADOS",
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });
    }
};

jQuery.extend($.fn.fmatter, {
    checkboxSelecExpeResponsable: function(cellvalue, options, rowdata) {
        var id=rowdata.idExpediente;
        var numeroExpe=rowdata.numeroExpediente;
        var html = '';
        if (id != null && id != '' && id != undefined){       
            html='<input id="idExpe'+id+'" numeroExpediente="'+numeroExpe+'" type="checkbox" name="" value="" onclick="respReceRece.selecExpediente(this)">'+
                '<label for="idExpe'+id+'" class="checkbox"></label>';
        }
        return html;
    }
});
//inicializacion
$(function() {
    respReceRece.procesarGridExpeResponsable();
    $('#btnDerivarExpedientes').click(respReceRece.validaDerivarExpedientes);
    $('#btnVerDerivadosResponsable').click(respReceRece.abrirExpeDeriResponsable);
    $('#derivarExpedidiente').validarForm();
});