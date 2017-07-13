/**
 * Resumen		
 * Objeto		: obligacionDsrDetalle.js
 * Descripción		: supervision
 * Fecha de Creación	: 17/08/2016
 * PR de Creación	: OSINE_SFS-791
 * Autor			: GMD
 * =====================================================================================================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |     Descripción
 * =====================================================================================================================================================================
 *OSINE791–RSIS10 |26/08/2016     | Zosimo Chaupis Santur        | Crear la funcionalidad para registrar una obligación como "CUMPLIDA" en el registro de supervisión  de una orden de supervisión DSR-CRITICIDAD
 *OSINE791–RSIS12 |26/08/2016     | Zosimo Chaupis Santur        | Crear la funcionalidad para registrar una obligación como "POR VERIFICAR" en el registro de supervisión  de una orden de supervisión DSR-CRITICIDAD
 *OSINE791–RSIS13 |31/08/2016     | Zosimo Chaupis Santur        | Crear la funcionalidad para registrar información de una obligación "OBSTACULIZADA" en el registro de supervisión  de una orden de supervisión DSR-CRITICIDAD
 *OSINE791-RSIS8  |01/09/2016     | Cristopher Paucar Torre      | Registrar Medio Probatorio 
 *OSINE791–RSIS14 |01/09/2016     | Zosimo Chaupis Santur        | Considerar la funcionalidad para subsanar una obligación marcada como "INCUMPLIDA" de una supervisión  de orden de supervisión DSR-CRITICIDAD
 *OSINE791-RSIS23 |02/09/2016     | Jose Herrera Pajuelo         | Crear la funcionalidad para consultar la obligación de una supervisión realizada cuando se debe aprobar una orden de supervisión DSR-CRITICIDAD
 *OSINE791-RSIS25 |08/09/2016     |	Alexander Vilca Narvaez 	 | Crear la funcionalidad para consultar el comentario de ejecución de medida ingresado para las infracciones identificadas DSR-CRITICIDAD
 ***/

//common/supervision/dsr/obligacion/obligacionDsrDetalle.js
var coSupeDsrOblOblDet = {
    abrirPopUpComentarioIncumplimiento: function(idEsceIncumplimiento,idDetalleSupervision,idInfraccion,modoSupervision) {
        $.ajax({
            url: baseURL + "pages/comentarioIncumplimiento/abrirComentarioIncumplimiento",
            type: 'post',
            async: false,
            data: {
                idEsceIncumplimiento:idEsceIncumplimiento,
                idDetalleSupervision:idDetalleSupervision,
                idInfraccion:idInfraccion,
                codigoOsinergmin:$('#txtCodOsinergSup').val(),
                modoSupervision:modoSupervision
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                $("#dialogComentarioIncumplimiento").html(data);
                $("#dialogComentarioIncumplimiento").dialog({
                    resizable: false,
                    draggable: true,
                    height: "auto",
                    width: "auto",
                    modal: true,
                    title: "COMENTARIO",
                    closeText: "Cerrar",
                    position: 'top+50',
                    close:function(){
                        coSupeDsrOblOblDet.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()), $("#oblDsrTotalPrioridad").val());
                    }
                });
//    			}
            },
            error: errorAjax
        });
    },
    
    /* OSINE791 - RSIS23 - Inicio */
    //Solo modo consulta
    abrirPopUpEjecucionMedidaIncumplimiento: function(idEsceIncumplimiento) {
        $.ajax({
            url: baseURL + "pages/supervision/dsr/abrirEscenarioIncumplimientoEjecucionMedida",
            type: 'post',
            async: false,
            data: {
                /* OSINE791 - RSIS25 - Inicio */
            	idDetalleSupervision:$('#oblDsrDetIdDetalleSupervision').val(), 
            	idEsceIncumplimiento:idEsceIncumplimiento,
            	/* OSINE791 - RSIS25 - Fin */
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                $("#obligacionDsrDialogEjecucionMedidaIncumplimiento").html(data);
                $("#obligacionDsrDialogEjecucionMedidaIncumplimiento").dialog({
                    resizable: false,
                    draggable: true,
                    position: 'center',
                    autoOpen: true,
                    height: "auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: "Ejecución Medida",
                    closeText: "Cerrar",
                    close:function(){
                    	$("#obligacionDsrDialogEjecucionMedidaIncumplimiento").dialog('destroy');
                    }
                });
            },
            error: errorAjax
        });
    },
    /* OSINE791 - RSIS23 - Fin */
    
    escenarioIncumplimientoObsFormatter: function (cellvalue, options, rowdata) {
        /* OSINE791 - RSIS23 - Inicio */
        var icon='ui-icon-comment',html='';
        if ($('#modoSupervision').val() == constant.modoSupervision.consulta) {
            icon='ui-icon-search';
        }else if(rowdata.flagIncumplidoEnDetSup==constant.estado.activo){
            icon='ui-icon-pencil';
        }
        if($('#modoSupervision').val()==constant.modoSupervision.registro){
            html='<div class="ilb cp btnGrid" onclick="coSupeDsrOblOblDet.abrirPopUpComentarioIncumplimiento(' + rowdata['idEsceIncumplimiento'] + ','+$('#oblDsrDetIdDetalleSupervision').val()+',' + rowdata['idInfraccion'] + ',\''+constant.modoSupervision.registro+'\');">';
        }else if($('#modoSupervision').val()==constant.modoSupervision.consulta){
            html='<div class="ilb cp btnGrid" onclick="coSupeDsrOblOblDet.abrirPopUpComentarioIncumplimiento(' + rowdata['idEsceIncumplimiento'] + ','+$('#oblDsrDetIdDetalleSupervision').val()+',' + rowdata['idInfraccion'] + ',\''+constant.modoSupervision.consulta+'\');">';
        }
        html+='<span class="ui-icon '+icon+'"></span></div>'
        /* OSINE791 - RSIS23 - Fin */
        return html;
        
        var id=rowdata.idComentarioIncumplimiento,html = '';
        if (id != null && id != '' && id != undefined){       
            html='<div class="ilb cp"><span class="ui-icon ui-icon-pencil"></span></div>'            
        }
        return html;
        
    },
    /* OSINE_SFS-791 - RSIS 24 - Inicio */ 
    escenarioIncumplimientoObsFormatterEjeMedida: function(cellvalue, options, rowdata) {
        var icon='ui-icon-search',html = '';
        html='<div class="ilb cp btnGrid" onclick="coSupeDsrOblOblDet.abrirPopUpEjecucionMedidaIncumplimiento('+rowdata['idEsceIncumplimiento']+');">';
        html+='<span class="ui-icon '+icon+'"></span></div>'
        return html;
    },
    /* OSINE_SFS-791 - RSIS 24 - Inicio */ 
    procesarGridOblDtlEscenarioIncump: function() {
        var ocultarComentario = false;
        /* OSINE_SFS-791 - RSIS 24 - Inicio */ 
        var ocultarEjecucionMedida = '';
        var flagEjecucionMedida = $('#flagEjecucionMedida').val();
        if(flagEjecucionMedida==constant.estado.activo && $('#modoSupervision').val()==constant.modoSupervision.consulta){
            ocultarEjecucionMedida=false;
        }else if(flagEjecucionMedida==constant.estado.inactivo || flagEjecucionMedida=="" || flagEjecucionMedida==null){
            ocultarEjecucionMedida=true;
        }
        /* OSINE_SFS-791 - RSIS 24 - Fin */ 
        
        var anchoDescripcion = 450;
        var anchoComentario = 90;
        var anchoEjecucionMedida = 90;
        var incumpliminentoSi = $('#radioDsrOblDelIncumplimientoSi').is(":checked");
        if (!incumpliminentoSi) {
            anchoDescripcion += anchoComentario += anchoEjecucionMedida;
            ocultarComentario = true;
        }
        var nombres = ['idEsceIncumplimiento','ESCENARIO','','COMENTARIO','EJMEDIDA'];
        var columnas = [
            {name: "idEsceIncumplimiento", width: anchoDescripcion, hidden: true, sortable: false},
            {name: "idEsceIncuMaestro.descripcion", width: anchoDescripcion, hidden: false, sortable: false},
            {name: "flagIncumplidoEnDetSup", hidden: true},
            {name: "comentario", width: anchoComentario, hidden: ocultarComentario, sortable: false, align: "center", formatter: coSupeDsrOblOblDet.escenarioIncumplimientoObsFormatter},
            /* OSINE_SFS-791 - RSIS 24 - Inicio */ 
            {name: "ejmedida", width: anchoEjecucionMedida, hidden: ocultarEjecucionMedida, sortable: false, align: "center", formatter: coSupeDsrOblOblDet.escenarioIncumplimientoObsFormatterEjeMedida}
            /* OSINE_SFS-791 - RSIS 24 - Inicio */ 
        ];
        $("#gridContenedorObligacionDtlEscenario").html("");
        var grid = $("<table>", {
            "id": "gridObligacionDtlEscenario"
        });
        $("#gridContenedorObligacionDtlEscenario").append(grid);

        grid.jqGrid({
            url: baseURL + "pages/supervision/dsr/findEscenarioIncumplimiento",
            datatype: "json",
            mtype: "POST",
            postData: {
                idInfraccion: $("#idInfraccionOblDetSup").val(),
                idDetalleSupervision: $('#oblDsrDetIdDetalleSupervision').val(),
                flagBuscaIncumplido:constant.estado.activo
            },
            hidegrid: false,
            emptyrecords: "No se encontraron resultados",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: 85,
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
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            loadComplete: function(data) {
                if (data.registros == 0) {
                    $(".containerEscenearioIncumplimiento").css('display', 'none');
                    $(".containerSinEscenario").css('display', 'none');
                    if($('#radioDsrOblDelIncumplimientoSi').is(":checked")){
                        $(".containerSinEscenario").css('display', '');
                    }
                } else {
                    $(".containerEscenearioIncumplimiento").css('display', '');
                    $(".containerSinEscenario").css('display', 'none');
                }
                /* OSINE791 - RSIS23 - Inicio */
                if ($('#modoSupervision').val() == constant.modoSupervision.consulta) {
                    if ($('#radioDsrOblDelIncumplimientoObstaculizado').is(":checked") || $('#radioDsrOblDelIncumplimientoNoAplica').is(":checked")) {
                        $(".containerEscenearioIncumplimiento").css('display','none');
                        $(".containerSinEscenario").css('display','none');
                    }
                }
                /* OSINE791 - RSIS23 - Fin */
                /* OSINE_SFS-791 - RSIS 24 - Inicio */ 
                if($('#modoSupervision').val()==constant.modoSupervision.consulta){
                    var lista = jQuery("#gridObligacionDtlEscenario").getDataIDs();
                    for(i=0;i<lista.length;i++){
                        rowData=jQuery("#gridObligacionDtlEscenario").getRowData(lista[i]);
                        rowData.flagIncumplidoEnDetSup   
                        if (rowData.flagIncumplidoEnDetSup=='' || rowData.flagIncumplidoEnDetSup !=1 || rowData.flagIncumplidoEnDetSup==null) {
                            $('#gridObligacionDtlEscenario').jqGrid('delRowData',lista[i]);
                        }
                    }
                }
                /* OSINE_SFS-791 - RSIS 24 - Inicio */ 
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    pdtoSuspenderFormatter: function(cellvalue, options, rowdata) {
        check = '<input id="radioProductoScop" type="radio" name="radioProductoScop" value="1">' +
                '<label for="radioProductoScop" class="radio"></label>';
        return check;
    },
    procesarGridOblDtlPdtoScop: function() {
        var nombres = ['SUSPENDER', 'PRODUCTO', '', ''];
        var columnas = [
            {name: "suspender", align: 'center', width: 90, hidden: false, sortable: false},
            {name: "producto", width: 450, hidden: false, sortable: false},
            {name: "idProducto", width: 450, hidden: true, sortable: false},
            {name: "idProductoSuspender", width: 450, hidden: true, sortable: false}
        ];
        $("#gridContenedorObligacionDtlPrdtos").html("");
        var grid = $("<table>", {
            "id": "gridObligacionDtlPrdtos"
        });
        $("#gridContenedorObligacionDtlPrdtos").append(grid);

        grid.jqGrid({
            url: baseURL + "pages/supervision/dsr/findObligacionPrdtos",
            datatype: "json",
            mtype: "POST",
            postData: {
                idDetalleSupervision: $("#oblDsrDetIdDetalleSupervision").val()
            },
            hidegrid: false,
            emptyrecords: "No se encontraron resultados",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: 85,
            viewrecords: true,
            caption: "",
            jsonReader: {
                root: "filas",
                page: "pagina",
                total: "total",
                records: "registros",
                repeatitems: false,
                id: "letra"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            loadComplete: function(data) {
            	var rowIds = grid.jqGrid('getDataIDs');
                if (rowIds.length > 0) {
                    $("#oblProductoSuspender").val("1");
                    var incumpliminentoSi = $('#radioDsrOblDelIncumplimientoSi').is(":checked");
                    if (incumpliminentoSi) {
                        $(".containerEscenearioProductoSuspendeer").show();
                        /* OSINE791 - RSIS23 - Inicio */
                        if ($('#modoSupervision').val() == constant.modoSupervision.consulta) {
                            $("#gridObligacionDtlPrdtos").jqGrid('hideCol', ["suspender"]);
                        }
                        /* OSINE791 - RSIS23 - Fin */
                    }
                }
                $.each(rowIds, function(i, row) {  
                    var rowData = grid.getRowData(row);
                    if (rowData.idProductoSuspender == 0) {
                        check1 = '<div class="ilb"><input style="margin-left: 10px;" id="radioProductoScop' + rowData.idProducto + '" onclick="coSupeDsrOblOblDet.guardarProductoSuspender(' + rowData.idProducto + ')" type="checkbox" name="radioProductoScop' + rowData.idProducto + '" value="' + rowData.idProductoSuspender + '"  >' +
                                '<label  for="radioProductoScop' + rowData.idProducto + '" class="checkbox"></label></div>';
                    } else {
                        check1 = '<div class="ilb"><input style="margin-left: 10px;" id="radioProductoScop' + rowData.idProducto + '" onclick="coSupeDsrOblOblDet.guardarProductoSuspender(' + rowData.idProducto + ')" type="checkbox" name="radioProductoScop' + rowData.idProducto + '" value="' + rowData.idProductoSuspender + '"  checked>' +
                                '<label  for="radioProductoScop' + rowData.idProducto + '" class="checkbox"></label></div>';
                    }
                    grid.jqGrid('setCell', row, 'suspender', check1);
                    /* OSINE791 - RSIS23 - Inicio */
                    if ($('#modoSupervision').val() == constant.modoSupervision.consulta) {
                        $("#gridObligacionDtlPrdtos tr").map(function () {
                            if ($(this).find('input').val() == 0) {
                                $(this).css('display','none');
                            }
                        });
                    }
                    /* OSINE791 - RSIS23 - Fin */
                });
                
                /* OSINE791 - RSIS23 - mdiosesf -Inicio */
                if ($('#modoSupervision').val() == constant.modoSupervision.consulta) {
	                if($('#gridObligacionDtlPrdtos tr:visible').length==1){
	                	var myrow = {
	                			'suspender':'', 
	                            'producto':'No se seleccionaron productos a suspender', 
	                            'idProducto':'',
	                            'idProductoSuspender':''
	                    };
	                	$("#gridObligacionDtlPrdtos").addRowData($.jgrid.randId(), myrow,"first");
	                } 
                }
                /* OSINE791 - RSIS23 - mdiosesf -Fin */ 	
                               
                coSupeDsrSupe.ComprobarResultadoSupervisionDsr();
                
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    guardarProductoSuspender: function(idProducto) {
        var indicador = 0;
        if ($('#radioProductoScop' + idProducto).is(":checked")) {
            indicador = 1;
        }
        loading.open();
        var parameters = "_=p";
        parameters += "&idDetalleSupervision=" + $('#oblDsrDetIdDetalleSupervision').val();
        parameters += "&idProducto=" + idProducto;
        parameters += "&indicador=" + indicador;
        parameters += "&idProductoSuspender=" + $('#radioProductoScop' + idProducto).val();
        $.ajax({
            url: baseURL + "pages/supervision/dsr/guardarDsrProductoSuspender",
            type: 'post',
            async: false,
            data: parameters,
            success: function(data) {
                loading.close();
                if (data.resultado == '0') {
                    mensajeGrowl("success", data.mensaje);
                    coSupeDsrOblOblDet.procesarGridOblDtlPdtoScop();
                } else {
                    mensajeGrowl("error", data.mensaje);
                }
            },
            error: errorAjax
        });
    },
    /* OSINE791 - RSIS8 - Inicio */
    procesarGridOblDtlMdioProb: function() {
        var nombres = ['idDocumentoAdjunto', 'DESCRIPCI&Oacute;N', 'ARCHIVO', 'DESCARGAR'];
        var columnas = [
            {name: "idDocumentoAdjunto", hidden: true},
            {name: "descripcionDocumento", hidden: false, width: 200, sortable: false, align: "center"},
            {name: "nombreArchivo", hidden: false, width: 200, sortable: false, align: "center"},
            {name: "idDocumentoAdjunto", width: 60, sortable: false, align: "center", formatter: 'descargarMedioProbatorioDsr'}
        ];
        $("#gridContenedorArchivosMPDsr").html("");
        var grid = $("<table>", {
            "id": "gridArchivosMedioProbatorioDsr"
        });
        var pager = $("<div>", {
            "id": "paginacionArchivosMedioProbatorioDsr"
        });

        $("#gridContenedorArchivosMPDsr").append(grid).append(pager);
        grid.jqGrid({
            url: baseURL + "pages/archivo/findPghDocumentoAdjunto",
            datatype: "json",
            postData: {
                idDetalleSupervision: $('#oblDsrDetIdDetalleSupervision').val()
            },
            hidegrid: false,
            rowNum: constant.rowNum,
            pager: "#paginacionArchivosMedioProbatorioDsr",
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
            onRightClickRow: function(rowid) {
                $('#linkEliminarArchivosMPDsr').attr('onClick', 'coSupeDsrOblOblDet.eliminarMedioProbatorioConfDsr(' + rowid + ')');//PENDIENTE
            },
            loadComplete: function(data) {
                $('#contextMenuArchivosMPDsr').parent().remove();
                $('#divContextArchivosMPDsr').html("<ul id='contextMenuArchivosMPDsr'>"
                        + "<li> <a id='linkEliminarArchivosMPDsr' data-icon='ui-icon-trash' title='Eliminar'>Eliminar</a></li>"
                        + "</ul>");
                $('#contextMenuArchivosMPDsr').puicontextmenu({
                    target: $('#gridArchivosMedioProbatorioDsr')
                });
                $('#contextMenuArchivosMPDsr').parent().css('width', '65px');                
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    eliminarMedioProbatorioConfDsr: function(idDocumentoAdjunto) {
        confirmer.open('¿Confirma que desea eliminar el archivo?', 'coSupeDsrOblOblDet.suprimirMedioProbatorio("' + idDocumentoAdjunto + '")');
    },
    suprimirMedioProbatorio: function(idDocumentoAdjunto) {
        loading.open();
        var url = baseURL + "pages/archivo/eliminarPghDocumentoAdjunto";
        $.post(url, {
            idDocumentoAdjunto: idDocumentoAdjunto
        }, function(data) {
            loading.close();
            if (data.resultado == '0') {
                mensajeGrowl("success", constant.confirm.remove);
                coSupeDsrOblOblDet.procesarGridOblDtlMdioProb();
            }
            else if (data.resultado == '1') {
                mensajeGrowl('error', data.mensaje);
            }
        });
    },
    /* OSINE791 - RSIS8 - Fin */
    getDetalleSupervision: function() {
        $('#idInfraccionOblDetSup').val("");
        $("#ObliDsrComentarioSubsanacion").val("");
        var idDetalleSupervision = $("#oblDsrDetIdDetalleSupervision").val();
        var url = baseURL + "pages/supervision/dsr/getDetalleSupervision";
        $.ajax({
            url: url,
            type: 'post',
            async: false,
            data: {
                idDetalleSupervision: idDetalleSupervision
            },
            success: function(data) {
                if (data.resultado == '0') {
                    $(".obligacionDetalleContainer .containerEscenearioProductoSuspendeer").hide();
                    
                    
                    if (data.detaSupe.resultadoSupervisionAnt.idResultadosupervision == null || data.detaSupe.resultadoSupervisionAnt.idResultadosupervision == undefined) {
                      $("#idResultadoObligacionAntOblDetSup").val("-1");
                      $("#idResultadoObligacionAntOblDetSup").attr("codigo",constant.codigoResultadoSupervision.noDefinido);
                    }else{
                      $("#idResultadoObligacionAntOblDetSup").val(data.detaSupe.resultadoSupervisionAnt.idResultadosupervision);
                      $("#idResultadoObligacionAntOblDetSup").attr("codigo",data.detaSupe.resultadoSupervisionAnt.codigo);
                    }
                      var tempo = $("#idResultadoObligacionAntOblDetSup").val();
                      var anttempo =  $("#idResultadoObligacionAntOblDetSup").attr("codigo");
                    
                      $("#FlagInicialResultadoObligacionOblDetSup").val(tempo);
                      $("#FlagInicialResultadoObligacionOblDetSup").attr("codigo",anttempo);
                    
                    $("#oblDsrDetIdDetalleSupervision").val(data.detaSupe.idDetalleSupervision);
                    $(".obligacionDetalleContainer .prioridad").html(data.detaSupe.prioridad);
                    $(".obligacionDetalleContainer .totalPrioridad").html(data.detaSupe.totalPrioridad);
                    $("#oblDsrPrioridad").val(data.detaSupe.prioridad);
                    $("#oblDsrTotalPrioridad").val(data.detaSupe.totalPrioridad);

                    if (data.detaSupe.infraccion != null && data.detaSupe.infraccion != undefined) {
                        $('#idInfraccionOblDetSup').val(data.detaSupe.infraccion.idInfraccion);
                        $(".obligacionDetalleContainer .descripcionInfraccion").html(data.detaSupe.infraccion.descripcionInfraccion);
                        if(data.detaSupe.infraccion.documentoAdjunto!=null && data.detaSupe.infraccion.documentoAdjunto!=undefined &&
                            data.detaSupe.infraccion.documentoAdjunto.nombreArchivo && data.detaSupe.infraccion.documentoAdjunto.rutaAlfresco){
                            $("#descargaAdjuntoInfraccion").css('display','').attr('href',baseURL + 'pages/documentoAdjunto/descargaArchivoAlfresco?aplicacionSpace=OBLIGACIONES&nombreArchivo='+data.detaSupe.infraccion.documentoAdjunto.nombreArchivo+'&rutaAlfresco='+data.detaSupe.infraccion.documentoAdjunto.rutaAlfresco);
                        }else{
                            $("#descargaAdjuntoInfraccion").css('display','none').attr('href','#');
                        }
                    }

                    if (data.detaSupe.prioridad == 1) {
                        $("#btnAnteriorDetalleObligacion").hide();
                    } else {
                        $("#btnAnteriorDetalleObligacion").show();
                    }
                    if (data.detaSupe.prioridad == data.detaSupe.totalPrioridad) {
                        $("#btnSiguienteDetalleObligacion").hide();
                    } else {
                        $("#btnSiguienteDetalleObligacion").show();
                    }

                    //limpiar checkeds
                    $('.oblDsrRadiosIncumplimiento input').attr('checked', false);
                    //  OSINE791 - RSIS12 - Inicio -->
                    $("#divradioDsrOblDelIncumplimientoObstaculizado").hide();
                    $("#containerComentarioObstaculizado").hide();
                    $("#divComentarioSubsanacion").hide();
                    
                    $("#idResultadoObligacionDetSupActual").val(data.detaSupe.resultadoSupervision.idResultadosupervision);
                    $("#idResultadoObligacionDetSupActual").attr("codigo",data.detaSupe.resultadoSupervision.codigo);
                    
                    
                    if (data.detaSupe.resultadoSupervision.codigo != -1) {
                        if (data.detaSupe.resultadoSupervision.codigo == constant.codigoResultadoSupervision.obstaculizado) {
                            $("#divradioDsrOblDelIncumplimientoObstaculizado").show();
                            $("#containerComentarioObstaculizado").show();
                            if (data.detaSupe.comentario != null) {
                                $("#txtComentarioObstaculizado").val(data.detaSupe.comentario.toUpperCase());
                            } else {
                                $("#txtComentarioObstaculizado").val("");
                            }
                            $("#divradioDsrOblDelIncumplimientoSi").hide();
                            $("#divradioDsrOblDelIncumplimientoNo").hide();
                            $("#divradioDsrOblDelIncumplimientoPorVerificar").hide();
                            $("#divradioDsrOblDelIncumplimientoNoAplica").hide();
                        } else {
                            $("#divradioDsrOblDelIncumplimientoObstaculizado").hide();
                            $("#containerComentarioObstaculizado").hide();
                            $("#txtComentarioObstaculizado").val("");
                            $("#divradioDsrOblDelIncumplimientoSi").show();
                            $("#divradioDsrOblDelIncumplimientoNo").show();
                            $("#divradioDsrOblDelIncumplimientoPorVerificar").show();
                            $("#divradioDsrOblDelIncumplimientoNoAplica").show();
                        }
                        $('.oblDsrRadiosIncumplimiento input[value=' + data.detaSupe.resultadoSupervision.idResultadosupervision + ']').attr('checked', true);
                        //  OSINE791 - RSIS14 - Inicio -->
                        
                        var resultadoAnterior = $("#idResultadoObligacionAntOblDetSup").attr("codigo");
                        //var FlagInicialResultado = $("#FlagInicialResultadoObligacionOblDetSup").attr("codigo");
                        if (data.detaSupe.resultadoSupervision.codigo == constant.codigoResultadoSupervision.cumple && resultadoAnterior == constant.codigoResultadoSupervision.incumple) {
                                
                                $("#divradioDsrOblDelIncumplimientoSi").hide();
                                $("#divradioDsrOblDelIncumplimientoPorVerificar").hide();
                                $("#divradioDsrOblDelIncumplimientoNoAplica").hide();
                                $("#divradioDsrOblDelIncumplimientoObstaculizado").hide();
                                $("#divComentarioSubsanacion").show();  
                                $("#ObliDsrComentarioSubsanacion").val((data.detaSupe.comentario != null) ? data.detaSupe.comentario.toUpperCase() : "");
                           
                        }
                        if (data.detaSupe.resultadoSupervision.codigo == constant.codigoResultadoSupervision.incumple) {
                            $('#divBtnSubsanar').css('display','');
//                            $("#divradioDsrOblDelIncumplimientoPorVerificar").hide();
//                            $("#divradioDsrOblDelIncumplimientoNoAplica").hide();
//                            $("#divradioDsrOblDelIncumplimientoObstaculizado").hide();
//                            $("#divradioDsrOblDelIncumplimientoNo").show();
//                            $("#divradioDsrOblDelIncumplimientoSi").show();                            
                        }else{
                            $('#divBtnSubsanar').css('display','none');
                        }
                         
                        //  OSINE791 - RSIS14 - Fin -->
                    }
                    //  OSINE791 - RSIS12 - Fin -->
                    /*OSINE791 - RSIS9 - Inicio*/
                    $('#countComeDetSup').val((data.detaSupe!=null && data.detaSupe.countComentarioDetSupervision!= null)?data.detaSupe.countComentarioDetSupervision:"");
                    if($('#modoSupervision').val() == constant.modoSupervision.registro && data.detaSupe.countComentarioDetSupervision>0){                    	       
                        $('#spnAbrirComInctoSinEscenario').removeClass('ui-icon-comment').addClass('ui-icon-pencil');
                    }else if($('#modoSupervision').val() == constant.modoSupervision.registro){
                        $('#spnAbrirComInctoSinEscenario').removeClass('ui-icon-pencil').addClass('ui-icon-comment');
                    }else if($('#modoSupervision').val() == constant.modoSupervision.consulta && data.detaSupe.countComentarioDetSupervision>0){
                        $('#spnAbrirComInctoSinEscenario').removeClass('ui-icon-comment').addClass('ui-icon-search');
                    }
                    /*OSINE791 - RSIS9 - Fin*/
//                    $("#idResultadoObligacionAntOblDetSup").val(data.detaSupe.resultadoSupervision.idResultadosupervision);
//                    $("#idResultadoObligacionAntOblDetSup").attr("codigo",data.detaSupe.resultadoSupervision.codigo);

                    coSupeDsrOblOblDet.procesarGridOblDtlEscenarioIncump();
                    /*OSINE791 - mdiosesf - Inicio*/
                    if(data.detaSupe.flagMostrarProducto == '1') {
                    	coSupeDsrOblOblDet.procesarGridOblDtlPdtoScop();                    	
                    }                  
                    /*OSINE791 - mdiosesf - Fin*/
                    /* OSINE791 - RSIS8 - Inicio */
                    coSupeDsrOblOblDet.procesarGridOblDtlMdioProb();
                    coSupeDsrOblOblDet.muestraMedioProbatorioDiv();
                    /* OSINE791 - RSIS8 - Fin */
                } else {
                    mensajeGrowl('error', data.mensaje);
                    return null;
                }
            },
            error: errorAjax
        });
    },
    getObligacionPorPrioridad: function(idSupervision, nextPrioridad, totalPrioridad) {
        if (nextPrioridad > totalPrioridad) {
            //do nothing
        } else if (nextPrioridad < 1) {
            //do nothing
        } else {
            var mensajeValidacion = "";
//			loading.open();
            var url = baseURL + "pages/supervision/dsr/findObligacionByPrioridad";
            $.ajax({
                url: url,
                type: 'post',
                async: false,
                data: {
                    idSupervision: idSupervision,
                    prioridad: nextPrioridad
                },
                success: function(data) {
//	            	loading.close();
                    if (data.resultado == '0') {
                        $("#oblDsrDetIdDetalleSupervision").val(data.obligacion.idDetalleSupervision);
                        coSupeDsrOblOblDet.getDetalleSupervision();
                    } else {
                        mensajeGrowl('error', data.mensaje);
                        return null;
                    }
                },
                error: errorAjax
            });
        }
    },
    /* OSINE791 - RSIS8 - Inicio */
    muestraMedioProbatorioDiv: function() {
        if ($('#radioDsrOblDelIncumplimientoSi').attr('checked') || $('#radioDsrOblDelIncumplimientoObstaculizado').attr('checked')) {
            $('#medioProbatorioDsr').css('display', '');
        } else {
            $('#medioProbatorioDsr').css('display', 'none');
        }
    },
    /* OSINE791 - RSIS8 - Fin */
    initFormDetalle: function(idDetalle) {
        $("#radioDsrOblDelIncumplimientoSi").click(function() {
            if($("#radioDsrOblDelIncumplimientoSi").attr('codigo')!=$('#idResultadoObligacionDetSupActual').attr('codigo')){
//                $(".obligacionDetalleContainer").show();
//                $(".containerEscenearioProductoSuspendeer").hide()
                coSupeDsrOblOblDet.registrarDsrObligacion();
                /* OSINE791 - RSIS8 - Inicio */
//                coSupeDsrOblOblDet.muestraMedioProbatorioDiv();
                /* OSINE791 - RSIS8 - Fin */
            }
        });
        $("#radioDsrOblDelIncumplimientoPorVerificar").click(function() {
            if($("#radioDsrOblDelIncumplimientoPorVerificar").attr('codigo')!=$('#idResultadoObligacionDetSupActual').attr('codigo')){
//                $(".obligacionDetalleContainer .containerEscenearioProductoSuspendeer").hide();
//                coSupeDsrOblOblDet.muestraMedioProbatorioDiv();
//                coSupeDsrOblOblDet.procesarGridOblDtlEscenarioIncump();
                coSupeDsrOblOblDet.registrarDsrObligacion();
            }
        });
        $("#radioDsrOblDelIncumplimientoNoAplica").click(function() {
            if($("#radioDsrOblDelIncumplimientoNoAplica").attr('codigo')!=$('#idResultadoObligacionDetSupActual').attr('codigo')){
//                $(".obligacionDetalleContainer .containerEscenearioProductoSuspendeer").hide();
//                coSupeDsrOblOblDet.muestraMedioProbatorioDiv();
//                coSupeDsrOblOblDet.procesarGridOblDtlEscenarioIncump();
                coSupeDsrOblOblDet.registrarDsrObligacion();
            }
        });

        $("#radioDsrOblDelIncumplimientoNo").click(function() {
            if($("#radioDsrOblDelIncumplimientoNo").attr('codigo')!=$('#idResultadoObligacionDetSupActual').attr('codigo')){
                //coSupeDsrOblOblDet.registrarDsrObligacion();
//                var radioCodigoSeleccionado = $(".oblDsrRadiosIncumplimiento input:radio[name='radioDsrOblDelIncumplimiento']:checked").attr("codigo");
//                var codEstaAntecedente = $("#idResultadoObligacionAntOblDetSup").attr("codigo");     
//                if (codEstaAntecedente == constant.codigoResultadoSupervision.incumple && radioCodigoSeleccionado == constant.codigoResultadoSupervision.cumple) {
//                    $("#divradioDsrOblDelIncumplimientoSi").hide();
//                    coSupeDsrOblOblDet.abrirRegistroComentarioSubsanacion();
//                    $("#divComentarioSubsanacion").show();
//                }else{
                    coSupeDsrOblOblDet.registrarDsrObligacion();
//                    $("#divComentarioSubsanacion").hide();
//                }         
//                coSupeDsrOblOblDet.procesarGridOblDtlEscenarioIncump();
//                coSupeDsrOblOblDet.muestraMedioProbatorioDiv();
//                $(".containerEscenearioProductoSuspendeer").hide();
            }
        });
        
        $("#btnAbrirEditarComentarioSubsanacion").click(function() {    
            coSupeDsrOblOblDet.abrirRegistroComentarioSubsanacion(constant.estado.activo);
            if($('#modoSupervision').val() == constant.modoSupervision.consulta){
              $("#btnGuardarComentarioSubsanacion").css('display','none');
              $("#AreaComentarioSubsanacion").attr('disabled', 'disabled');    
             }
        });
        $("#btnBackListadoObligacion").click(function() {
            if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
                $("div.oblDsrDivListado").css('display', '');
                $("div.oblDsrDivDetalle").css('display', 'none');
            } else if ($('#modoSupervision').val() == constant.modoSupervision.consulta) {
                $("div.ejeMedOblLst").css('display', '');
                $("div.ejeMedOblDivDetalle").css('display', 'none');
            }
            if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
                coSupeDsrSupe.VerificarObstaculizados();
                coSupeDsrOblOblLst.procesarGridOblLst();
            }
        });
        $("#btnAnteriorDetalleObligacion").click(function() {
            /* OSINE791 – RSIS23 - Inicio */
            if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
                coSupeDsrSupe.VerificarObstaculizados();
                $("#ObliDsrComentarioSubsanacion").val("");
                $("#idInfraccionOblDetSup").val("");
            }
            /* OSINE791 – RSIS23 - Fin */
            coSupeDsrOblOblDet.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()) - 1, $("#oblDsrTotalPrioridad").val());
        });
        if ($('#modoSupervision').val() == constant.modoSupervision.consulta) {
               $("#btnSubsanar").css('display', 'none');
        };
    
        $("#btnSiguienteDetalleObligacion").click(function() {
            var idResultadoSup = $("#EstadoResultadoSupervision").val();
            if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
                coSupeDsrSupe.VerificarObstaculizados();
                if (idResultadoSup == null || idResultadoSup == '') {
                    var mensaje = '';
                    if (($("#radioDsrOblDelIncumplimientoSi").is(':checked'))) {
                        mensaje = coSupeDsrOblOblDet.validaComentarioRegistrado();
                    }
                    if (mensaje == '') {
                        if (!($("#radioDsrOblDelIncumplimientoObstaculizado").is(':checked'))) {
                            /* OSINE791 – RSIS10 - Inicio */
                            var validaRadios = coSupeDsrOblOblDet.validarRadiosObligatoriosSP();
                            if (validaRadios == '') {
                                coSupeDsrOblOblDet.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()) + 1, $("#oblDsrTotalPrioridad").val());
                            } else {
                                mensajeGrowl('warn', validaRadios);
                            }
                        } else {
                            var mensajeValida = '';
                            if (coSupeDsrOblOblDet.validarFormularioComentarioObstaculizadoSP()) {
                                mensajeValida = coSupeDsrOblOblDet.validarComentarioObstaculizadoSP();
                                if (mensajeValida == '') {
                                    coSupeDsrOblOblDet.registrarDsrObligacion();
                                    coSupeDsrOblOblDet.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()) + 1, $("#oblDsrTotalPrioridad").val());
                                } else {
                                    mensajeGrowl('warn', mensajeValida);
                                }
                            }
                        }
                    } else {
                        mensajeGrowl('warn', mensaje);
                    }
                }else{
                    coSupeDsrOblOblDet.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()) + 1, $("#oblDsrTotalPrioridad").val());
                }
            	
            }else {
                coSupeDsrOblOblDet.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()) + 1, $("#oblDsrTotalPrioridad").val());
        
            }
        });
        /*OSINE791 - RSIS9 Inicio*/
        $('#btnAbrirComInctoSinEscenario').click(function(){
            if($('#modoSupervision').val()==constant.modoSupervision.registro){
                coSupeDsrOblOblDet.abrirPopUpComentarioIncumplimiento("",$('#oblDsrDetIdDetalleSupervision').val(),$('#idInfraccionOblDetSup').val(),constant.modoSupervision.registro);
            }else if($('#modoSupervision').val()==constant.modoSupervision.consulta){
                coSupeDsrOblOblDet.abrirPopUpComentarioIncumplimiento("",$('#oblDsrDetIdDetalleSupervision').val(),$('#idInfraccionOblDetSup').val(),constant.modoSupervision.consulta);
            }
        });
        /*OSINE791 - RSIS9 Fin*/
        $('#btnSubsanar').click(function(){
            $('#radioDsrOblDelIncumplimientoNo').attr('checked',true);
            var radioCodigoSeleccionado = $(".oblDsrRadiosIncumplimiento input:radio[name='radioDsrOblDelIncumplimiento']:checked").attr("codigo");
            var codEstaAntecedente = $("#idResultadoObligacionDetSupActual").attr("codigo");     
            if (codEstaAntecedente == constant.codigoResultadoSupervision.incumple && radioCodigoSeleccionado == constant.codigoResultadoSupervision.cumple) {
                $("#divradioDsrOblDelIncumplimientoSi").hide();
                coSupeDsrOblOblDet.abrirRegistroComentarioSubsanacion();
                $("#divComentarioSubsanacion").show();
            }            
        });
    },
    //  OSINE791 - RSIS13 - Inicio -->
    validarFormularioComentarioObstaculizadoSP: function() {
        var result = $('#containerComentarioObstaculizado').validateAllForm('#divMensajeValidaFrmSup');
        return result;
    },
    validarComentarioObstaculizadoSP: function() {
        var mensajeValidacio = "";
        if ($('#txtComentarioObstaculizado').val().trim() != '' && $('#txtComentarioObstaculizado').val().trim().length < 8) {
            $('#txtComentarioObstaculizado').addClass('error');
            mensajeValidacio = "El comentario de obstaculizaci&oacute;n de la supervisi&oacute;n debe tener una longitud mayor o igual a 8 caracteres, corregir<br>";
            return mensajeValidacio;
        }
        return mensajeValidacio;
    },
    validarRadiosObligatoriosSP: function() {
        var mensajeValidacio = "";
        if (!($("#radioDsrOblDelIncumplimientoSi").is(':checked')) && !($("#radioDsrOblDelIncumplimientoNo").is(':checked')) && !($("#radioDsrOblDelIncumplimientoPorVerificar").is(':checked')) && !($("#radioDsrOblDelIncumplimientoNoAplica").is(':checked'))) {
            mensajeValidacio = "Se debe completar los campos obligatorios, corregir<br>";
            return mensajeValidacio;
        }
        return mensajeValidacio;
    },
    
    //  OSINE791 - RSIS15 - Inicio -->
    validaComentarioRegistrado: function() {
        var datafromgrid = $('#gridObligacionDtlEscenario').jqGrid('getRowData');
        var mensajeValidacion="No se ha registrado ningún comentario para esta infracción, verificar.";
        if(datafromgrid.length>0){ // verifica a nivel de escenario incumplido
	        $.each(datafromgrid,function(k,v){
	          if (v.flagIncumplidoEnDetSup==constant.comentario.registrado){
	        	  mensajeValidacion = "";
	        	  return false;
	            }
	        });
        }else {
            var CantcomentarioInfraccion = $('#countComeDetSup').val();
            if (CantcomentarioInfraccion>0){
	        	  mensajeValidacion = "";
	        	  return false;
            } 
        }	
        return mensajeValidacion;
    },
    //  OSINE791 - RSIS15 - Fin -->
    
    //  OSINE791 - RSIS13 - Fin -->
    registrarDsrObligacion: function() {
        loading.open();
        //  OSINE791 - RSIS13 - Inicio -->
        var idActResultadoSupervision = $("#idResultadoObligacionDetSupActual").val();
        var radio = $(".oblDsrRadiosIncumplimiento input:radio[name='radioDsrOblDelIncumplimiento']:checked").val();
        var radioCodigo = $(".oblDsrRadiosIncumplimiento input:radio[name='radioDsrOblDelIncumplimiento']:checked").attr("codigo");
        var comentarioObstaculizado = "";
        if (radioCodigo == constant.codigoResultadoSupervision.obstaculizado) {//6 es el id de Estado Obstaculizado
            comentarioObstaculizado = $("#txtComentarioObstaculizado").val().toUpperCase();
        }else if(radioCodigo == constant.codigoResultadoSupervision.cumple && $("#idResultadoObligacionDetSupActual").attr('codigo') == constant.codigoResultadoSupervision.incumple){
            idActResultadoSupervision=-1;
        } else {
            comentarioObstaculizado = "";
        }
        //  OSINE791 - RSIS13 - Fin -->
        var parameters = "_=p";
        parameters += "&idDetalleSupervision=" + $('#oblDsrDetIdDetalleSupervision').val();
        parameters += "&estadoIncumplimiento=" + $(".oblDsrRadiosIncumplimiento input:radio[name='radioDsrOblDelIncumplimiento']:checked").val();
        parameters += "&comentarioObstaculizado=" + comentarioObstaculizado;
        parameters += "&idResultadoSupervisionAct=" + idActResultadoSupervision;
        
        $.ajax({
            url: baseURL + "pages/supervision/dsr/guardarDsrObligacion",
            type: 'post',
            async: false,
            data: parameters,
            success: function(data) {
                loading.close();
                if (data.resultado == '0') {                    
                    mensajeGrowl("success", constant.confirm.save);
                    coSupeDsrOblOblDet.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()), $("#oblDsrTotalPrioridad").val());
//                    $("#idResultadoObligacionDetSupActual").attr("codigo",radioCodigo);
//                    $("#idResultadoObligacionDetSupActual").val(radio);
//                    
//                    if (comentarioObstaculizado != "") {
//                        idSupervision = $("#idSupervision").val();
//                        prioridad = $("#oblDsrPrioridad").val();
//                        totalPrioridad = $("#oblDsrTotalPrioridad").val();
//                        var valor = $("#idResultadoObligacionDetSupActual").attr("codigo");
//                        if (valor != constant.codigoResultadoSupervision.incumple) {
//                            coSupeDsrOblOblDet.getObligacionPorPrioridad(idSupervision, parseInt(prioridad) + 1, totalPrioridad);
//                        }
//                    } else if (radioCodigo == constant.codigoResultadoSupervision.incumple) {
//                        coSupeDsrOblOblDet.procesarGridOblDtlEscenarioIncump();
//                        coSupeDsrOblOblDet.procesarGridOblDtlPdtoScop();
//                    }
//                        
//                    var flagResultadoAntSup = $("#FlagInicialResultadoObligacionOblDetSup").attr("codigo");
//                    if (radioCodigo == constant.codigoResultadoSupervision.cumple && flagResultadoAntSup == constant.codigoResultadoSupervision.incumple) {
//                        $("#divComentarioSubsanacion").show();
//                    } else {
//                        $("#divComentarioSubsanacion").hide();
//                        $("#ObliDsrComentarioSubsanacion").val("");
//                    }                    
                } else {
                    mensajeGrowl("error", data.mensaje);
                }
            },
            error: errorAjax
        });
    },
    mostrarDetalleObligacion: function(idDetalleSupervision) {
        if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
            $("div.oblDsrDivListado").css('display', 'none');
            $("div.oblDsrDivDetalle").css('display', '');
        } else if ($('#modoSupervision').val() == constant.modoSupervision.consulta) {
            $("div.ejeMedOblLst").css('display', 'none');
            $("div.ejeMedOblDivDetalle").css('display', '');
        }
        $("#oblDsrDetIdDetalleSupervision").val(idDetalleSupervision)
        coSupeDsrOblOblDet.getDetalleSupervision();
    },
    abrirRegistroComentarioSubsanacion: function(flagEditar) {
        if(flagEditar==undefined || flagEditar==''){
            flagEditar='0';
        }
        var idDetalleSupervision = $('#oblDsrDetIdDetalleSupervision').val();
        var title = "SUBSANACION DE INCUMPLIMIENTO";
        $.ajax({
            url: baseURL + "pages/supervision/dsr/abrirRegistroComentarioSubsanacion",
            type: 'get',
            async: false,
            data: {
                idDetalleSupervision: idDetalleSupervision,
                flagEditar:flagEditar
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                $("#dialogComentarioSubsanacion").html('');
                $("#dialogComentarioSubsanacion").html(data);
                $("#dialogComentarioSubsanacion").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height: "auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar",
                    close:function(){
                    	$("#dialogComentarioSubsanacion").dialog('destroy');
                        coSupeDsrOblOblDet.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()), $("#oblDsrTotalPrioridad").val());
                    }
                });
            },
            error: errorAjax
        });
        /*<!--  OSINE791 - RSIS8 - Fin -->*/
    }
}

/* OSINE791 - RSIS8 - Inicio */
jQuery.extend($.fn.fmatter, {
    eliminarMedioProbatorio: function(cellvalue, options, rowdata) {
        return editar = "<img src=\"" + baseURL + "/../images/delete_16.png\" style=\"cursor: pointer;\" alt=\"Eliminar Adjunto\" onclick=\"coSupeDsrOblOblDet.eliminarMedioProbatorioConfDsr('" + rowdata.idDocumentoAdjunto + "');\" />";
    },
    descargarMedioProbatorioDsr: function(id, cellvalue, options, rowdata) {
        var html = "<a href='" + baseURL + "pages/archivo/descargaPghArchivoBD?idDocumentoAdjunto=" + id + "' target='_blank'>";
        html += "<img src=\"" + baseURL + "/../images/stickers.png\" width='17' height='18' style=\"cursor: pointer;\" alt=\"Descargar Medio Probatorio\"/>";
        html += "</a>";
        return html;
    }
});
/*  OSINE791 - RSIS8 - Fin */

$(function() {
    boton.closeDialog();
    coSupeDsrOblOblDet.initFormDetalle();
    //coSupeDsrOblOblDet.procesarGridOblDtlEscenarioIncump();
    coSupeDsrOblOblDet.procesarGridOblDtlPdtoScop();
    /* OSINE791 - RSIS8 - Inicio */
    $('#btnAbrirRegistrarMedioProb').click(function() {
        common.abrirRegistroMedioProbatorios($('#oblDsrDetIdDetalleSupervision').val());
    });
    /* OSINE791 - RSIS8 -Fin */
    //$(".containerEscenearioProductoSuspendeer").find("input").attr("disabled","disabled");
});
