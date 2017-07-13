/**
 * Resumen		
 * Objeto			: detalleDrsLevantamiento.js
 * Descripción		: detalleDrsLevantamiento
 * Fecha de Creación: 07/10/2016
 * PR de Creación	: OSINE_SFS-791
 * Autor			: GMD
 * =====================================================================================================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |    Descripción
 * =====================================================================================================================================================================
 * OSINE_SFS-791     07/10/2016       Mario Dioses Fernandez          Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
 *
 */

var coSupeDsrDetalleLev = {
		
	inicio: function(){
		coSupeDsrDetalleLev.procesarGridOblDtlEscenarioIncumpLev();
		coSupeDsrDetalleLev.procesarGridOblDtlMdioProbLev();
		coSupeDsrDetalleLev.procesarGridOblDtlMdioProbDetalleLev();
		$('#btnAbrirComInctoSinEscenarioLev').click(function(){
			coSupeDsrDetalleLev.abrirPopUpComentarioIncumplimientoLev("",$('#idOblDsrDetIdDetalleSupervisionLev').val(),$('#idInfraccionOblDetSupLev').val(),constant.modoSupervision.consulta);
        });
	},
	
	procesarGridOblDtlMdioProbLev: function() {
		//grid Medios Probatorios Detalle Supervision
		if($('#idOblDsrDetIdDetalleSupervisionLev').val()==''){
    		$('#idOblDsrDetIdDetalleSupervisionLev').val(-1);
    	}
        var nombres = ['idDocumentoAdjunto', 'DESCRIPCI&Oacute;N', 'ARCHIVO', 'DESCARGAR'];
        var columnas = [
            {name: "idDocumentoAdjunto", hidden: true},
            {name: "descripcionDocumento", hidden: false, width: 310, sortable: false, align: "left"},
            {name: "nombreArchivo", hidden: false, width: 300, sortable: false, align: "left"},
            {name: "idDocumentoAdjunto", width: 80, sortable: false, align: "center", formatter: 'descargarMedioProbatorioDsr'}
        ];
        $("#gridContenedorArchivosMPDsrLev").html("");
        var grid = $("<table>", {
            "id": "gridArchivosMedioProbatorioDsrLev"
        });
        var pager = $("<div>", {
            "id": "paginacionArchivosMedioProbatorioDsrLev"
        });

        $("#gridContenedorArchivosMPDsrLev").append(grid).append(pager);
        grid.jqGrid({
            url: baseURL + "pages/archivo/findPghDocumentoAdjunto",
            datatype: "json",
            postData: {
                idDetalleSupervision: $('#idOblDsrDetIdDetalleSupervisionLev').val()
            },
            hidegrid: false,
            rowNum: constant.rowNum,
            pager: "#paginacionArchivosMedioProbatorioDsrLev",
            emptyrecords: "No se encontraron resultados",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: "auto",
            width: "auto",
            viewrecords: true,
            caption: "",
            jsonReader: {
                root: "filas",
                page: "pagina",
                total: "total",
                records: "registros",
                repeatitems: false,
                id: "idDocumentoAdjunto"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            onRightClickRow: function(rowid) {},
            loadComplete: function(data) {             
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    
    procesarGridOblDtlMdioProbDetalleLev: function() {
		//grid Medios Probatorios Detalle Levantamiento
    	if($('#idOblDsrDetIdDetalleLevantamiento').val()==''){
    		$('#idOblDsrDetIdDetalleLevantamiento').val(-1);
    	}
        var nombres = ['idDocumentoAdjunto', 'DESCRIPCI&Oacute;N', 'ARCHIVO', 'DESCARGAR'];
        var columnas = [
            {name: "idDocumentoAdjunto", hidden: true},
            {name: "descripcionDocumento", hidden: false, width: 270, sortable: false, align: "left"},
            {name: "nombreArchivo", hidden: false, width: 290, sortable: false, align: "left"},
            {name: "idDocumentoAdjunto", width: 80, sortable: false, align: "center", formatter: 'descargarMedioProbatorioDsr'}
        ];
        $("#gridContenedorArchivosMPDsrDetalleLev").html("");
        var grid = $("<table>", {
            "id": "gridContenedorArchivosMPDsrDetalleLev"
        });
        var pager = $("<div>", {
            "id": "paginacionArchivosMedioProbatorioDsrDetalleLev"
        });

        $("#gridContenedorArchivosMPDsrDetalleLev").append(grid).append(pager);
        grid.jqGrid({
            url: baseURL + "pages/archivo/findPghDocumentoAdjunto",
            datatype: "json",
            postData: {
            	idDetalleLevantamiento: $('#idOblDsrDetIdDetalleLevantamiento').val()
            },
            hidegrid: false,
            rowNum: constant.rowNum,
            pager: "#paginacionArchivosMedioProbatorioDsrDetalleLev",
            emptyrecords: "No se encontraron resultados",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: "auto",
            width: "auto",
            viewrecords: true,
            caption: "",
            jsonReader: {
                root: "filas",
                page: "pagina",
                total: "total",
                records: "registros",
                repeatitems: false,
                id: "idDocumentoAdjunto"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            onRightClickRow: function(rowid) {},
            loadComplete: function(data) {             
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
	
	procesarGridOblDtlEscenarioIncumpLev: function() {		
        var nombres = ['idEsceIncumplimiento','ESCENARIO','','COMENTARIO'];
        var columnas = [
            {name: "idEsceIncumplimiento", width: 10, hidden: true, sortable: false},
            {name: "idEsceIncuMaestro.descripcion", width: 610, hidden: false, sortable: false},
            {name: "flagIncumplidoEnDetSup", hidden: true},
            {name: "comentario", width: 80, hidden: false, sortable: false, align: "center", formatter: "escenarioIncumplimientoObs"}
        ];
        $("#gridContenedorObligacionDtlEscenarioLev").html("");
        var grid = $("<table>", {
            "id": "gridObligacionDtlEscenarioLev"
        });
        $("#gridContenedorObligacionDtlEscenarioLev").append(grid);

        grid.jqGrid({
            url: baseURL + "pages/supervision/dsr/findEscenarioIncumplimiento",
            datatype: "json",
            mtype: "POST",
            postData: {
                idInfraccion: $("#idInfraccionOblDetSupLev").val(),
                idDetalleSupervision: $('#idOblDsrDetIdDetalleSupervisionLev').val(),
                flagBuscaIncumplido:constant.estado.activo
            },
            hidegrid: false,
            emptyrecords: "No se encontraron resultados",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: 55,
            viewrecords: true,
            caption: "",
            jsonReader: {
                root: "filas",
                page: "pagina",
                total: "total",
                records: "registros",
                repeatitems: false,
                id: "idEsceIncumplimiento"
            },
            onSelectRow: function(rowid, status) {},
            loadComplete: function(data) {
            	if (data.registros == 0) {
                    $("#divEscIncumplimientoLev").css('display', 'none');
                    $("#divSinEscIncumplimientoLev").css('display', '');
                } else {
                	$("#divEscIncumplimientoLev").css('display', '');
                    $("#divSinEscIncumplimientoLev").css('display', 'none');
                }
            	
            	
            	var lista = jQuery("#gridObligacionDtlEscenarioLev").getDataIDs();
                for(i=0;i<lista.length;i++){
                    rowData=jQuery("#gridObligacionDtlEscenarioLev").getRowData(lista[i]); 
                    if (rowData.flagIncumplidoEnDetSup=='' || rowData.flagIncumplidoEnDetSup !=1 || rowData.flagIncumplidoEnDetSup==null) {
                        $('#gridObligacionDtlEscenarioLev').jqGrid('delRowData',lista[i]);
                    }
                }                
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    abrirPopUpComentarioIncumplimientoLev: function(idEsceIncumplimiento,idDetalleSupervision,idInfraccion,modoSupervision) {
        $.ajax({
            url: baseURL + "pages/comentarioIncumplimiento/abrirComentarioIncumplimiento",
            type: 'post',
            async: false,
            data: {
                idEsceIncumplimiento:idEsceIncumplimiento,
                idDetalleSupervision:idDetalleSupervision,
                idInfraccion:idInfraccion,
                codigoOsinergmin:$('#codOsinergminLev').val(),
                modoSupervision:modoSupervision
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                $("#dialogComentarioIncumplimientoLev").html(data);
                $("#dialogComentarioIncumplimientoLev").dialog({
                    resizable: false,
                    draggable: true,
                    height: "auto",
                    width: "auto",
                    modal: true,
                    title: "COMENTARIO",
                    closeText: "Cerrar",
                    position: 'top+50',
                    close:function(){
                    	$("#dialogComentarioIncumplimientoLev").dialog("destroy"); 
                    }
                });
            },
            error: errorAjax
        });
    }
}
jQuery.extend($.fn.fmatter, { 
	escenarioIncumplimientoObs: function (cellvalue, options, rowdata) {
	    var html='';	    
	    html='<div class="ilb cp btnGrid" style="width:20px;margin:0 auto;" onclick="coSupeDsrDetalleLev.abrirPopUpComentarioIncumplimientoLev(' + rowdata['idEsceIncumplimiento'] + ','+$('#idOblDsrDetIdDetalleSupervisionLev').val()+',' + rowdata['idInfraccion'] + ',\''+constant.modoSupervision.consulta+'\');">';
	    html+='<center><span class="ui-icon ui-icon-search"></span></center></div>';
	    return html;
	},
	descargarMedioProbatorioDsr: function(id, cellvalue, options, rowdata) {
        var html = "<a href='" + baseURL + "pages/archivo/descargaPghArchivoBD?idDocumentoAdjunto=" + id + "' target='_blank'>";
        html += "<img src=\"" + baseURL + "/../images/stickers.png\" width='17' height='18' style=\"cursor: pointer;\" alt=\"Descargar Medio Probatorio\"/>";
        html += "</a>";
        return html;
    }
});
$(function() {
	coSupeDsrDetalleLev.inicio();
    boton.closeDialog();    
});