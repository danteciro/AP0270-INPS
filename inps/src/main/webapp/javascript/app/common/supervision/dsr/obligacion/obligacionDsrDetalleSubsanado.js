/* 
* Resumen		
 * Objeto		: obligacionDsrDetalleSubsanado.js
 * Descripción		: supervision
 * Fecha de Creación	: 06/10/2016
 * PR de Creación	: OSINE_SFS-791
 * Autor			: GMD
 */
//common/supervision/dsr/obligacion/obligacionDsrDetalleSubsanado.js

var coSupeDsrOblOblDetSub = {
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
                        //coSupeDsrOblOblDetSub.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()), $("#oblDsrTotalPrioridad").val());
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
        if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
            icon='ui-icon-search';
        }else
        {
             icon='ui-icon-search';
        }
            
        if($('#modoSupervision').val()==constant.modoSupervision.registro){
            html='<div class="ilb cp btnGrid" onclick="coSupeDsrOblOblDetSub.abrirPopUpComentarioIncumplimiento(' + rowdata['idEsceIncumplimiento'] + ','+$('#oblDsrDetIdDetalleSupervision').val()+',' + rowdata['idInfraccion'] + ',\''+constant.modoSupervision.consulta+'\');">';
        }else{
            html='<div class="ilb cp btnGrid" onclick="coSupeDsrOblOblDetSub.abrirPopUpComentarioIncumplimiento(' + rowdata['idEsceIncumplimiento'] + ','+$('#oblDsrDetIdDetalleSupervision').val()+',' + rowdata['idInfraccion'] + ',\''+constant.modoSupervision.consulta+'\');">';
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
        html='<div class="ilb cp btnGrid" onclick="coSupeDsrOblOblDetSub.abrirPopUpEjecucionMedidaIncumplimiento('+rowdata['idEsceIncumplimiento']+');">';
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
            {name: "comentario", width: anchoComentario, hidden: ocultarComentario, sortable: false, align: "center", formatter: coSupeDsrOblOblDetSub.escenarioIncumplimientoObsFormatter},
            /* OSINE_SFS-791 - RSIS 24 - Inicio */ 
            {name: "ejmedida", width: anchoEjecucionMedida, hidden: ocultarEjecucionMedida, sortable: false, align: "center", formatter: coSupeDsrOblOblDetSub.escenarioIncumplimientoObsFormatterEjeMedida}
            /* OSINE_SFS-791 - RSIS 24 - Inicio */ 
        ];
        $("#gridContenedorObligacionDtlEscenarioSub").html("");
        var grid = $("<table>", {
            "id": "gridObligacionDtlEscenario"
        });
        $("#gridContenedorObligacionDtlEscenarioSub").append(grid);

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
               
                /* OSINE791 - RSIS23 - Fin */
                /* OSINE_SFS-791 - RSIS 24 - Inicio */ 
                if($('#modoSupervision').val()==constant.modoSupervision.registro){
                    var lista = jQuery("#gridObligacionDtlEscenario").getDataIDs();
                    for(i=0;i<lista.length;i++){
                        rowData=jQuery("#gridObligacionDtlEscenario").getRowData(lista[i]);
                        rowData.flagIncumplidoEnDetSup   
                        if (rowData.flagIncumplidoEnDetSup=='' || rowData.flagIncumplidoEnDetSup !=1 || rowData.flagIncumplidoEnDetSup==null) {
                            $('#gridObligacionDtlEscenario').jqGrid('delRowData',lista[i]);
                        }
                    }
                }else
                    {
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
        $("#gridContenedorObligacionDtlPrdtosSub").html("");
        var grid = $("<table>", {
            "id": "gridObligacionDtlPrdtos"
        });
        $("#gridContenedorObligacionDtlPrdtosSub").append(grid);

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
            height: 55,
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
                    
                          if(rowIds.length > 0)
                              {
                        $(".containerEscenearioProductoSuspendeer").show();
                              }
                        /* OSINE791 - RSIS23 - Inicio */
                        if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
                            $("#gridObligacionDtlPrdtos").jqGrid('hideCol', ["suspender"]);
                        }else
                            {
                             $("#gridObligacionDtlPrdtos").jqGrid('hideCol', ["suspender"]);  
                            }
                        /* OSINE791 - RSIS23 - Fin */
                   // }
                }
                $.each(rowIds, function(i, row) {  
                    var rowData = grid.getRowData(row);
                    if (rowData.idProductoSuspender == 0) {
                        check1 = '<div class="ilb"><input style="margin-left: 10px;" id="radioProductoScop' + rowData.idProducto + '" onclick="coSupeDsrOblOblDetSub.guardarProductoSuspender(' + rowData.idProducto + ')" type="checkbox" name="radioProductoScop' + rowData.idProducto + '" value="' + rowData.idProductoSuspender + '"  >' +
                                '<label  for="radioProductoScop' + rowData.idProducto + '" class="checkbox"></label></div>';
                    } else {
                        check1 = '<div class="ilb"><input style="margin-left: 10px;" id="radioProductoScop' + rowData.idProducto + '" onclick="coSupeDsrOblOblDetSub.guardarProductoSuspender(' + rowData.idProducto + ')" type="checkbox" name="radioProductoScop' + rowData.idProducto + '" value="' + rowData.idProductoSuspender + '"  checked>' +
                                '<label  for="radioProductoScop' + rowData.idProducto + '" class="checkbox"></label></div>';
                    }
                    grid.jqGrid('setCell', row, 'suspender', check1);
                    /* OSINE791 - RSIS23 - Inicio */
                    if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
                        $("#gridObligacionDtlPrdtos tr").map(function () {
                            if ($(this).find('input').val() == 0) {
                                $(this).css('display','none');
                            }
                        });
                    }
                    else{
                        $("#gridObligacionDtlPrdtos tr").map(function () {
                            if ($(this).find('input').val() == 0) {
                                $(this).css('display','none');
                            }
                        });
                    }
                    /* OSINE791 - RSIS23 - Fin */
                });
                
                /* OSINE791 - RSIS23 - mdiosesf -Inicio */
                if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
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
                else{
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
                    coSupeDsrOblOblDetSub.procesarGridOblDtlPdtoScop();
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
        $("#gridContenedorArchivosMPDsrAnt").html("");
        var grid = $("<table>", {
            "id": "gridArchivosMedioProbatorioDsrAnt"
        });
        var pager = $("<div>", {
            "id": "paginacionArchivosMedioProbatorioDsr"
        });

        $("#gridContenedorArchivosMPDsrAnt").append(grid).append(pager);
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
             
            },
            loadComplete: function(data) {
//                               
             },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    eliminarMedioProbatorioConfDsr: function(idDocumentoAdjunto) {
        confirmer.open('¿Confirma que desea eliminar el archivo?', 'coSupeDsrOblOblDetSub.suprimirMedioProbatorio("' + idDocumentoAdjunto + '")');
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
                coSupeDsrOblOblDetSub.procesarGridOblDtlMdioProbSubsanado();
            }
            else if (data.resultado == '1') {
                mensajeGrowl('error', data.mensaje);
            }
        });
    },
    /* OSINE791 - RSIS8 - Fin */
    getDetalleSupervision: function(idDetalleSupervision,flagAnterior) {
        $('#idInfraccionOblDetSup').val("");
        $("#ObliDsrComentarioSubsanacion").val("");
      
        
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
                    
                    // Detalle Supervision Anterior
                    if(flagAnterior==constant.estado.activo){
                        
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
    //                   
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
                             
                            if (data.detaSupe.resultadoSupervision.codigo != -1) {
    //                       
                            $('.oblDsrRadiosIncumplimiento input[value=' + data.detaSupe.resultadoSupervision.idResultadosupervision + ']').attr('checked', true);


                            var resultadoAnterior = $("#idResultadoObligacionAntOblDetSup").attr("codigo");

                            if (data.detaSupe.resultadoSupervision.codigo == constant.codigoResultadoSupervision.cumple && resultadoAnterior == constant.codigoResultadoSupervision.incumple) {

                                    $("#divradioDsrOblDelIncumplimientoSi").hide();
                                    $("#divradioDsrOblDelIncumplimientoPorVerificar").hide();
                                    $("#divradioDsrOblDelIncumplimientoNoAplica").hide();
                                    $("#divradioDsrOblDelIncumplimientoObstaculizado").hide();
                                    $("#divComentarioSubsanacion").hide();  
                                    $("#ObliDsrComentarioSubsanacion").val((data.detaSupe.comentario != null) ? data.detaSupe.comentario.toUpperCase() : "");

                            }
    //                   
                        }
                                $("#containerComentarioObstaculizado").hide();
                             if($('#modoSupervision').val() == constant.modoSupervision.registro && data.detaSupe.countComentarioDetSupervision>0){
                            $('#spnAbrirComInctoSinEscenarioSub').removeClass('ui-icon-comment').addClass('ui-icon-search');
                          }
                          
                        coSupeDsrOblOblDetSub.procesarGridOblDtlEscenarioIncump();
                        coSupeDsrOblOblDetSub.procesarGridOblDtlPdtoScop();
                       
                        coSupeDsrOblOblDetSub.procesarGridOblDtlMdioProb();

                        coSupeDsrOblOblDetSub.muestraMedioProbatorioDiv();
                        
                     
                        

                    }else{ // Detalle Supervision Actual
                        $(".obligacionDetalleContainer .prioridad").html(data.detaSupe.prioridad);
                        $(".obligacionDetalleContainer .totalPrioridad").html(data.detaSupe.totalPrioridad);
                        $("#oblDsrPrioridad").val(data.detaSupe.prioridad);
                        $("#oblDsrTotalPrioridad").val(data.detaSupe.totalPrioridad);
                        
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
                        
                        $('.oblDsrRadiosSubsanar input').attr('checked', false);
                        
                       $('.oblDsrRadiosSubsanar input[value=' + data.detaSupe.resultadoSupervision.idResultadosupervision + ']').attr('checked', true);
                       $("#idResultadoObligacionDetSupActual").val(data.detaSupe.resultadoSupervision.idResultadosupervision);
                       $("#idResultadoObligacionDetSupActual").attr("codigo",data.detaSupe.resultadoSupervision.codigo);
                    
                        
                        coSupeDsrOblOblDetSub.procesarGridOblDtlMdioProbSubsanado();
                        
                          
                         /* Detalle Levantamiento*/
                    if(data.detaSupelevantamiento != null && (data.detaSupelevantamiento.descripcion != undefined || data.detaSupelevantamiento.descripcion != '' || data.detaSupelevantamiento.descripcion != null))
                    {
                        $("#txtdescripcionLevantamiento").val(data.detaSupelevantamiento.descripcion);
                    }else
                        {
                        $("#txtdescripcionLevantamiento").val("");
                        }
                    
                     if(data.detaSupelevantamiento != null && (data.detaSupelevantamiento.idDetalleLevantamiento != undefined || data.detaSupelevantamiento.idDetalleLevantamiento != '' || data.detaSupelevantamiento.idDetalleLevantamiento != null))
                    {
                         $("#idOblDsrDetIdDetalleLevantamiento").val(data.detaSupelevantamiento.idDetalleLevantamiento);
                    }else
                        {
                          $("#idOblDsrDetIdDetalleLevantamiento").val("");   
                        }

                    coSupeDsrOblOblDetSub.procesarGridOblDtlMdioProbDetalleLev();
                    /* Detalle Levantamiento*/
                        
                        
                        
                    }
                   
                    
                    
                   
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
                        $("#oblDsrDetIdDetalleSupervisionSub").val(data.obligacion.idDetalleSupervision);
                        $("#oblDsrDetIdDetalleSupervision").val(data.obligacion.idDetalleSupervision);
                        $("#oblDsrDetIdDetalleSupervisionAntSub").val(data.obligacion.idDetalleSupervisionAnt);
                        coSupeDsrOblOblDetSub.getDetalleSupervision(data.obligacion.idDetalleSupervision,0);
                        coSupeDsrOblOblDetSub.getDetalleSupervision(data.obligacion.idDetalleSupervisionAnt,1);
                        
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

            $('#medioProbatorioDsr').css('display', '');
//        
    },
    /* OSINE791 - RSIS8 - Fin */
    initFormDetalle: function(idDetalle) {
   

            $("#radioDsrOblSubsanadoSi").click(function() {
           if($("#radioDsrOblSubsanadoSi").attr('codigo')!=$('#idResultadoObligacionDetSupActual').attr('codigo')){

               coSupeDsrOblOblDetSub.registrarDsrObligacion();
                
            }
            });
            
            $("#radioDsrOblSubsanadoNo").click(function() {
           if($("#radioDsrOblSubsanadoNo").attr('codigo')!=$('#idResultadoObligacionDetSupActual').attr('codigo')){
      
               coSupeDsrOblOblDetSub.registrarDsrObligacion();
               
            }
            });
        
        $("#btnAbrirEditarComentarioSubsanacion").click(function() {    
            coSupeDsrOblOblDetSub.abrirRegistroComentarioSubsanacion(constant.estado.activo);
            if($('#modoSupervision').val() == constant.modoSupervision.registro){
              $("#btnGuardarComentarioSubsanacion").css('display','none');
              $("#AreaComentarioSubsanacion").attr('disabled', 'disabled');    
             }
        });
        $("#btnBackListadoObligacion").click(function() {
            if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
                $("div.oblDsrDivListado").css('display', '');
                $("div.oblDsrDivDetalle").css('display', 'none');
            } else if ($('#modoSupervision').val() == constant.modoSupervision.consulta) {
                //$("div.ejeMedOblLst").css('display', '');
                //$("div.ejeMedOblDivDetalle").css('display', 'none');
                $("div.oblDsrDivListado").css('display', '');
                $("div.oblDsrDivDetalle").css('display', 'none');
            }
            if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
                coSupeDsrSupe.VerificarObstaculizados();
                coSupeDsrOblOblLst.procesarGridOblLst();
            }
        });
        $("#btnAnteriorDetalleObligacion").click(function() {
            /* OSINE791 – RSIS23 - Inicio */
            if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
              //  coSupeDsrSupe.VerificarObstaculizados();
                $("#ObliDsrComentarioSubsanacion").val("");
                $("#idInfraccionOblDetSup").val("");
            }
            /* OSINE791 – RSIS23 - Fin */
            coSupeDsrOblOblDetSub.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()) - 1, $("#oblDsrTotalPrioridad").val());
        });
        if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
               $("#btnSubsanar").css('display', 'none');
        };
    
        $("#btnSiguienteDetalleObligacion").click(function() {
//            
                
            //valida radios
                    var validaRadios = coSupeDsrOblOblDetSub.validarRadiosObligatoriosSP();
                        if (validaRadios == '') {
                            coSupeDsrOblOblDetSub.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()) + 1, $("#oblDsrTotalPrioridad").val());
                        } else {
                            mensajeGrowl('warn', validaRadios);
                        }
//            
        });
        /*OSINE791 - RSIS9 Inicio*/
        $('#btnAbrirComInctoSinEscenario').click(function(){
           
            if($('#modoSupervision').val()==constant.modoSupervision.registro){
                coSupeDsrOblOblDetSub.abrirPopUpComentarioIncumplimiento("",$('#oblDsrDetIdDetalleSupervision').val(),$('#idInfraccionOblDetSup').val(),constant.modoSupervision.consulta);
            }else{
                coSupeDsrOblOblDetSub.abrirPopUpComentarioIncumplimiento("",$('#oblDsrDetIdDetalleSupervision').val(),$('#idInfraccionOblDetSup').val(),constant.modoSupervision.consulta);
            }
        });
        /*OSINE791 - RSIS9 Fin*/
        $('#btnSubsanar').click(function(){
            $('#radioDsrOblDelIncumplimientoNo').attr('checked',true);
            var radioCodigoSeleccionado = $(".oblDsrRadiosIncumplimiento input:radio[name='radioDsrOblDelIncumplimiento']:checked").attr("codigo");
            var codEstaAntecedente = $("#idResultadoObligacionDetSupActual").attr("codigo");     
            if (codEstaAntecedente == constant.codigoResultadoSupervision.incumple && radioCodigoSeleccionado == constant.codigoResultadoSupervision.cumple) {
                $("#divradioDsrOblDelIncumplimientoSi").hide();
                coSupeDsrOblOblDetSub.abrirRegistroComentarioSubsanacion();
                $("#divComentarioSubsanacion").show();
            }            
        });
        
        // OSINE791 - RSIS38 - Inicio -->
        
        
        
       //  OSINE791 - RSIS38 - Fin -->
       $("#divConsultarMediosProbDsr").dialog({
            resizable: false,
            draggable: true,
            autoOpen: false,
            height: "auto",
            width: "auto",
            modal: true,
            dialogClass: 'dialog',
            closeText: "Cerrar",
            close: function(){
                //$("#divConsultarMediosProbDsr").dialog("destroy");
            }
        });
        
         $("#divConsultarMediosProbLevDsr").dialog({
            resizable: false,
            draggable: true,
            autoOpen: false,
            height: "auto",
            width: "auto",
            modal: true,
            dialogClass: 'dialog',
            closeText: "Cerrar",
            close: function(){
                //$("#divConsultarMediosProbLevDsr").dialog("destroy");
            }
        });
        /* OSINE791 - RSIS17 - Fin */
    
     
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
        if (!($("#radioDsrOblSubsanadoSi").is(':checked')) && !($("#radioDsrOblSubsanadoNo").is(':checked'))) {
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
        var radio = $(".oblDsrRadiosSubsanar input:radio[name='radioDsrSubsana']:checked").val();
        var radioCodigo = $(".oblDsrRadiosSubsanar input:radio[name='radioDsrSubsana']:checked").attr("codigo");
        var comentarioObstaculizado = "";
//      
        //  OSINE791 - RSIS13 - Fin -->
        var parameters = "_=p";
        parameters += "&idDetalleSupervision=" + $('#oblDsrDetIdDetalleSupervisionSub').val();
        parameters += "&estadoIncumplimiento=" + $(".oblDsrRadiosSubsanar input:radio[name='radioDsrSubsana']:checked").val();
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
                    coSupeDsrOblOblDetSub.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()), $("#oblDsrTotalPrioridad").val());
//                      
                } else {
                    mensajeGrowl("error", data.mensaje);
                }
            },
            error: errorAjax
        });
    },
    mostrarDetalleObligacion: function(idDetalleSupervision,idDetalleSupervisionAnt) {
        if ($('#modoSupervision').val() == constant.modoSupervision.registro) {
            $("div.oblDsrDivListado").css('display', 'none');
            $("div.oblDsrDivDetalle").css('display', '');
        } else if ($('#modoSupervision').val() == constant.modoSupervision.consulta) {
           
            $("div.oblDsrDivListado").css('display', 'none');
            $("div.oblDsrDivDetalle").css('display', '');
        }
        
        $("#oblDsrDetIdDetalleSupervisionSub").val(idDetalleSupervision);
        $("#oblDsrDetIdDetalleSupervision").val(idDetalleSupervision);
        $("#oblDsrDetIdDetalleSupervisionAntSub").val(idDetalleSupervisionAnt);
        coSupeDsrOblOblDetSub.getDetalleSupervision(idDetalleSupervision,0);
        coSupeDsrOblOblDetSub.getDetalleSupervision(idDetalleSupervisionAnt,1);
        
        
        
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
                        coSupeDsrOblOblDetSub.getObligacionPorPrioridad($("#idSupervision").val(), parseInt($("#oblDsrPrioridad").val()), $("#oblDsrTotalPrioridad").val());
                    }
                });
            },
            error: errorAjax
        });
        /*<!--  OSINE791 - RSIS8 - Fin -->*/
    },
    
    //RSIS38 -- Guardar nuevo Archivo de subsanacion
    procesarGridOblDtlMdioProbSubsanado: function() {
        var nombres = ['idDocumentoAdjunto', 'DESCRIPCIÓN', 'ARCHIVO', 'DESCARGAR'];
        var columnas = [
            {name: "idDocumentoAdjunto", hidden: true},
            {name: "descripcionDocumento", hidden: false, width: 200, sortable: false, align: "center"},
            {name: "nombreArchivo", hidden: false, width: 200, sortable: false, align: "center"},
            {name: "idDocumentoAdjunto", width: 60, sortable: false, align: "center", formatter: 'descargarMedioProbatorioDsr'}
        ];
        $("#gridContenedorArchivosMPDsrSub").html("");
        var grid = $("<table>", {
            "id": "gridArchivosMedioProbatorioDsrSub"
        });
        var pager = $("<div>", {
            "id": "paginacionArchivosMedioProbatorioDsrSub"
        });

        $("#gridContenedorArchivosMPDsrSub").append(grid).append(pager);
        grid.jqGrid({
            url: baseURL + "pages/archivo/findPghDocumentoAdjunto",
            datatype: "json",
            postData: {
                idDetalleSupervision: $('#oblDsrDetIdDetalleSupervisionSub').val()
            },
            hidegrid: false,
            rowNum: constant.rowNum,
            pager: "#paginacionArchivosMedioProbatorioDsrSub",
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
                $('#linkEliminarArchivosMPDsrSub').attr('onClick', 'coSupeDsrOblOblDetSub.eliminarMedioProbatorioConfDsr(' + rowid + ')');//PENDIENTE
            
            },
            loadComplete: function(data) {
         if($('#modoSupervision').val()==constant.modoSupervision.registro){
                $('#contextMenuArchivosMPDsr2').parent().remove();
                $('#divContextArchivosMPDsrSub').html("<ul id='contextMenuArchivosMPDsr2'>"
                        + "<li> <a id='linkEliminarArchivosMPDsrSub' data-icon='ui-icon-trash' title='Eliminar'>Eliminar</a></li>"
                        + "</ul>");
                $('#contextMenuArchivosMPDsr2').puicontextmenu({
                    target: $('#gridArchivosMedioProbatorioDsrSub')
                });
                $('#contextMenuArchivosMPDsr2').parent().css('width', '65px');   
               }
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
            {name: "descripcionDocumento", hidden: false, width: 200, sortable: false, align: "left"},
            {name: "nombreArchivo", hidden: false, width: 200, sortable: false, align: "left"},
            {name: "idDocumentoAdjunto", width: 60, sortable: false, align: "center", formatter: 'descargarMedioProbatorioDsr'}
        ];
        $("#gridContenedorArchivosMPDsrDetalleLevSub").html("");
        var grid = $("<table>", {
            "id": "gridContenedorArchivosMPDsrDetalleLevSub"
        });
        var pager = $("<div>", {
            "id": "paginacionArchivosMedioProbatorioDsrDetalleLev"
        });

        $("#gridContenedorArchivosMPDsrDetalleLevSub").append(grid).append(pager);
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
    }
    
}

/* OSINE791 - RSIS8 - Inicio */
jQuery.extend($.fn.fmatter, {
    eliminarMedioProbatorio: function(cellvalue, options, rowdata) {
        return editar = "<img src=\"" + baseURL + "/../images/delete_16.png\" style=\"cursor: pointer;\" alt=\"Eliminar Adjunto\" onclick=\"coSupeDsrOblOblDetSub.eliminarMedioProbatorioConfDsr('" + rowdata.idDocumentoAdjunto + "');\" />";
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
    coSupeDsrOblOblDetSub.initFormDetalle();
    coSupeDsrOblOblDetSub.procesarGridOblDtlPdtoScop();
    
    
    $('#btnAbrirRegistrarMedioProbSub').click(function() {
        common.abrirRegistroMedioProbatorios($('#oblDsrDetIdDetalleSupervisionSub').val());
    });
    
});
