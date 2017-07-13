/**
 * Resumen		
 * Objeto		: terminarSupervision.js
 * Descripción		: JavaScript donde se maneja las acciones de la pestaña terminar Supervision.
 * Fecha de Creación	: 11/10/2016
 * PR de Creación	: OSINE_SFS-791
 * Autor			: GMD
 * =====================================================================================================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |     Descripción
 * =====================================================================================================================================================================
 * OSINE791–RSIS40   | 11/10/2016 | Zosimo Chaupis Santur | Crear la funcionalidad para generar los resultados de una supervisión de orden de levantamiento DSR-CRITICIDAD la cual tenga todas sus obligaciones incumplidas subsanadas. 
 * OSINE_SFS-791  |  12/10/2016   |   Luis García Reyna          | 	Terminar Supervision - Listar Infracciones No Subsanadas
 *    
 */


var coSuDsrTerminarSup = {
    /* OSINE_SFS-791 - RSIS 40 - Inicio */
    inicializaObjetosEventosTerminoSupervision: function () {
        $('#btnGenerarResultadosTerminarSup').click(function () {
            if ($('#idDepartamentoObliDsrSup').val() == '' || $('#idProvinciaObliDsrSup').val() == '' || $('#idDistritoObliDsrSup').val() == '') {
                mensajeGrowl("error", "No se puede Generar Resultados.", "Unidad Supervisada no tiene datos de Ubigeo.");
                return false;
            } else {
                coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.generarResultadoInicial();
            }
        });
        $('#btnAnteriorInfrac').click(function () {
            coSupeDsrSupe.MostrarTab(2, 3);
        });
        $("#divGenerarResultadosDsrTerminarSupervision").dialog({
            resizable: false,
            draggable: true,
            autoOpen: false,
            height: "auto",
            width: "auto",
            modal: true,
            dialogClass: 'dialog',
            closeText: "Cerrar"
        });
    },
        
    fxGenerarResultadoTerminarSupervision: {
        generarResultadoInicial: function() {      
            var nroInfracciones = $("#flagInfraccionesTermSup").val();
            if (nroInfracciones > 0) {                
                if ($('#frmTerminarSupervision').validateAllForm("#divMensajeValidaFrmTerminarSup")) {
                    coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.generarResultadosTerminarInicio();
                }
            } else {
                coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.generarResultadosTerminarInicio();
            }
            
        },       
        
        generarResultadosTerminarInicio: function () {
            var idEstado = $("#EstadoResultadoSupervision").val();
            var modo = $("#modoSupervision").val();
            if (idEstado == '' && modo == constant.modoSupervision.registro) {
                confirmer.open("¿Confirma que desea generar los resultados de la Supervisión?", "coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.generarResultadosTerminarSupervision()");
            } else {
                coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.generarResultadosTerminarSupervision();
            }
        },
        cargarFlagInfraccionesTerminarSup: function () {
            $('#flagInfraccionesTermSup').val("");
            //var idsupanerior = $('#idSupervisionAnt').val();
            var idsupervision = $("#idSupervision").val();
            $.ajax({
                url: baseURL + "pages/detalleSupervision/findDetalleSupervision",
                type: 'post',
                async: false,
                data: {
                    idSupervision: idsupervision,
                    codigoResultadoSupervision: constant.codigoResultadoSupervision.subsanadoNo
                },
                success: function (data) {
                    if (data != null && data != undefined) {
                        if (data.length > 0) {
                            $('#flagInfraccionesTermSup').val(constant.estado.activo);
                        } else {
                            $('#flagInfraccionesTermSup').val(constant.estado.inactivo);
                        }
                    }
                    /* OSINE_SFS-791 - RSIS 39 - Inicio */
                    coSuDsrTerminarSup.evaluaFlagInfraccionesTerminarSup();
                    /* OSINE_SFS-791 - RSIS 39 - Fin */
                },
                error: errorAjax
            });
        },
        ComprobarResultadoTerminarSupervisionDsr: function () {
            var idEstado = $("#EstadoResultadoSupervision").val();
            var modo = $("#modoSupervision").val();
            if (idEstado != '' && modo == constant.modoSupervision.registro) {
                $("#btnGenerarResultadosTerminarSup").val('Consultar Resultados');
                coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.BloquearDatosTerminarSupervision();
                coSupeDsrSupe.BloquearDatosIniciales();
                coSupeDsrSupe.BloquearAtencionVisita();
                $("#radioDsrOblSubsanadoNo").attr("disabled","disable");
                $("#radioDsrOblSubsanadoSi").attr("disabled","disable");
                //coSupeDsrSupe.BloquearObligacionDetalle();
                //coSupeDsrSupe.BloquearObligacionDetalleComentarioSubsanacion();
                //coSupeDsrSupe.BloquearGrillaCheckBoxProductosSuspender();
                //coSupeDsrSupe.BloquearObligacionDetalleComentarioSubsanacion();
            }
        },
        BloquearDatosTerminarSupervision: function () {
            $("#cartaVisitaTerSup").attr('disabled', 'disabled');
        },
        generarResultadosTerminarSupervision: function () {
            var nrocarta;
            if ($("#cartaVisitaTerSup").val() == '' || $("#cartaVisitaTerSup").val() != null || $("#cartaVisitaTerSup").val() != undefined) {
                nrocarta = $("#cartaVisitaTerSup").val();
            } else {
                nrocarta = '';
            }
            loading.open();
            $.ajax({
                url: baseURL + "pages/terminarSupervision/generarResultadosTerminarSupervision",
                type: 'post',
                async: false,
                data: {
                    idSupervision: $('#idSupervision').val(),
                    flagInfracciones: $('#flagInfraccionesTermSup').val(),
                    nroCarta: nrocarta,
                    direccionOperativa: $('#DireccionOperativaObliDsrSup').val(),
                    idDepartamento: $('#idDepartamentoObliDsrSup').val(),
                    idProvincia: $('#idProvinciaObliDsrSup').val(),
                    idDistrito: $('#idDistritoObliDsrSup').val(),
                },
                success: function (data) {
                    loading.close();
                    if (data.resultado == '0') {
                        if (data.registroDocumentoSupervision != null) {
                            var idSupervision = data.registroDocumentoSupervision.supervision.idSupervision;
                            //var idTipoDocumento = data.registroDocumentoSupervision.idTipoDocumento.idMaestroColumna;
                            coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.grillaGenerarResultadosDSRTerminarSup(idSupervision);
                            $('#divGenerarResultadosDsrTerminarSupervision').dialog('open');
                            if ($("#fechaTerminoTerSup").val() =='' || $("#fechaTerminoTerSup").val() != null ){     
                                if(data.supervision.fechaFin != null){
                                    $("#fechaTerminoTerSup").val(data.supervision.fechaFin);
                                }                                
                            }
                            if ($("#horaTerminoTerSup").val() =='' || $("#horaTerminoTerSup").val() != null ){     
                                if(data.supervision.horaFin != null){
                                    $("#horaTerminoTerSup").val(data.supervision.horaFin);
                                }                                
                            }         
                            if ($("#EstadoResultadoSupervision").val() =='' || $("#EstadoResultadoSupervision").val() != null ){  
                                if(data.supervision.resultadoSupervisionDTO.idResultadosupervision != null){
                                    $("#EstadoResultadoSupervision").val(data.supervision.resultadoSupervisionDTO.idResultadosupervision);
                                }
                                //alert("codigo es : |"+data.supervision.resultadoSupervisionDTO.codigo);
                                //if(data.supervision.resultadoSupervisionDTO.codigo != null){
                                    //$("#EstadoResultadoSupervision").attr("codigo",""+data.supervision.resultadoSupervisionDTO.codigo);
                                //}
                            }
                        coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.ComprobarResultadoTerminarSupervisionDsr();
                        
                        }
                    } else {
                    	/*if(data.errorNroCarta!=null && data.errorNroCarta == constant.estado.activo)
                    		mensajeGrowl("error", data.mensaje, "");
                    	else */
                    		mensajeGrowl("error", "Ocurrió un error al Generar Resultados, intente en otro momento.", "");
                    }
                },
                error: errorAjax
            });

        },
        
        grillaGenerarResultadosDSRTerminarSup:function(idSupervision){
            var nombres = ['RESULTADO', 'DESCARGAR'];
            var columnas = [
               {name: "descripcionDocumento", width: 210, sortable: false, align: "center"},
               {name: "idDocumentoAdjunto", width: 200, sortable: false, align: "center", formatter:  coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.descargarGenerarResultadosIMGDSRTerminarSup}
            ];
           $("#gridContenedorGenerarResultadosDsrTerminarSupervision").html("");
           var grid = $("<table>", {
               "id": "gridGenerarResultadosDsr"
           });
           var pager = $("<div>", {
               "id": "paginacionGenerarResultados"
           });
           $("#gridContenedorGenerarResultadosDsrTerminarSupervision").append(grid).append(pager);

           grid.jqGrid({
               url: baseURL + "pages/terminarSupervision/generarResultadosDsrTerminarSupervision",
               datatype: "json",
               postData: {
            	   idSupervision: idSupervision,
            	  //idTipoDocumento: idTipoDocumento
               },
               hidegrid: false,
               rowNum: constant.rowNumPrinc,
               pager: "#paginacionGenerarResultados",
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
               },
               loadError: function(jqXHR) {
                   errorAjax(jqXHR);
               }
           });
        },
        descargarGenerarResultadosIMGDSRTerminarSup: function(idDocumentoAdjunto){
            var editar = "";
            if (idDocumentoAdjunto != undefined && idDocumentoAdjunto != '') {
                editar = '<a class="link" href="' + baseURL + 'pages/archivo/descargaResultadoGeneradoDsr?idDocumentoAdjunto=' + idDocumentoAdjunto + '" target="_blank" >' +
                        '<img class="vam" width="17" height="18" src="' + baseURL + 'images/descarga.png">' +
                        '</a>';
            }
            return editar;
        },
        
    },
    /* OSINE_SFS-791 - RSIS 40 - Fin */
    
    /* OSINE_SFS-791 - RSIS 39 - Inicio */
    
    procesarGridInfraccionesNoSubsanadas:function(){
        if(idSupervision!=null && idSupervision!=''){
            
            var nombres = ['IDDETALLESUPERVISION','IDDETALLESUPERVISIONANT','IDINFRACCION','IDRESULTADODETSUPERVISION','PRIORIDAD', 'DESCRIPCIÓN DE LA INFRACCIÓN', 'COMENTARIO','countEscIncumplidoAnt'];
            var columnas = [

                {name: "idDetalleSupervision",index:'id',hidden:true},
                {name: "idDetalleSupervisionAnt",index:'idDetAnt',hidden:true},
                {name: "infraccion.idInfraccion",index:'idInfraccion',hidden:true},
                {name: "resultadoSupervision.idResultadosupervision",index:'idResultadosupervision',hidden:true},
                {name: "prioridad", width: 60, sortable: false, align: "center"},
                {name: "infraccion.descripcionInfraccion", width: 500, sortable: false, align: "left"},
                {name: "comentario", width: 80, sortable: false, align: "center", formatter: coSuDsrTerminarSup.comentarioInfraccionesNoSubsanadasFormatter},
                {name: "countEscIncumplidoAnt", width: 10, sortable: false, hidden:true, align: "center"}
            ];

            $("#gridContenedorInfraccionesNoSubsanadas").html("");
            var grid = $("<table>", {
            "id": "gridInfraccionesNoSubsanadas"
            });
            var pager = $("<div>", {
            "id": "paginacionInfraccionesNoSubsanadas"
            });
            $("#gridContenedorInfraccionesNoSubsanadas").append(grid).append(pager);

            grid.jqGrid({
                url: baseURL + "pages/supervision/dsr/listarDetalleSupervision",
                datatype: "json",
                postData: {
                    idSupervision: $('#idSupervision').val(),
                    codigoResultadoSupervision: constant.codigoResultadoSupervision.subsanadoNo
                },
                hidegrid: false,
                rowNum: constant.rowNumPrinc,
                pager: "#paginacionInfraccionesNoSubsanadas",
                emptyrecords: "No se encontraron resultados",
                loadtext: "Cargando",
                colNames: nombres,
                colModel: columnas,
                height: "auto",
                viewrecords: true,
                caption: "Descripción de la Infracción",
                jsonReader: {root: "filas",page: "pagina",total: "total",records: "registros",repeatitems: false,id: "idSupervision"},
                subGrid: true,
                onSelectRow: function(rowid, status) {
                    jQuery("#gridInfraccionesNoSubsanadas").resetSelection();
                },
                afterInsertRow: function(rowid, aData, rowelem) {
                    var rowData = grid.getRowData(rowid);
                    if (rowData["countEscIncumplidoAnt"] < 1) {
                        $('tr#' + rowid, grid)
                                .children("td.sgcollapsed")
                                .html("")
                                .removeClass('ui-sgcollapsed sgcollapsed');
                    }
                },

                subGridOptions: {"plusicon": "ui-icon-circle-plus","minusicon": "ui-icon-circle-minus","openicon": "ui-icon-arrowreturn-1-e","reloadOnExpand": false,"selectOnExpand": false},
                subGridRowExpanded: function(subgrid_id, row_id) { 
        
                    var dataDetalleSupervision = grid.getRowData(row_id);
                    var subgrid_table_id, pager_id;  

                    subgrid_table_id = subgrid_id + "_t";
                    pager_id = "p_" + subgrid_table_id;
                    $("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
                    var nombres = ['IDESCENARIOINCUMPLIDO','IDDETALLESUPERVISION','IDESCENARIOINCUMPLIMIENTO','IDMAESCEINCUMAESTRO','ESCENARIO INCUMPLIMIENTO','COMENTARIO'];
                    var columnas = [ 
                        {name: "idEscenarioIncumplido",index:'idEido',hidden:true},
                        {name: "idDetalleSupervision",index:'idDs',hidden:true},
                        {name: "escenarioIncumplimientoDTO.idEsceIncumplimiento",index:'idEito',hidden:true},
                        {name: "escenarioIncumplimientoDTO.idEsceIncuMaestro.idMaestroColumna",index:'idEim',hidden:true},
                        {name: "escenarioIncumplimientoDTO.idEsceIncuMaestro.descripcion", width: 200, sortable: false, align: "left"}, 
                        {name: "comentario", width: 80, sortable: false, align: "center", formatter: coSuDsrTerminarSup.comentarioEitoInfNoSubsanadasFormatter}
                    ];

                    jQuery("#" + subgrid_table_id).jqGrid({

                        url : baseURL + "pages/supervision/dsr/findEscenarioIncumplido",		
                        datatype: "json",
                        postData: {
                            idDetalleSupervision:dataDetalleSupervision.idDetalleSupervisionAnt
                        },
                        hidegrid: false,
                        rowNum: constant.rowNumPrinc,
                        pager: pager_id,
                        emptyrecords: "No se encontraron resultados",
                        loadtext: "Cargando",
                        colNames: nombres,
                        colModel: columnas,
                        height: "auto",
                        viewrecords: true,
                        caption: "Escenario Incumplimiento",
                        autowidth: true,
                        jsonReader: {root: "filas", page: "pagina", total: "total", records: "registros",id:"idEscenarioIncumplido"},
                        onSelectRow: function(rowid, status) {
                            jQuery("#" + subgrid_table_id).resetSelection();
                        }
                    });             
                }  
            });
        }
    },
    
    comentarioInfraccionesNoSubsanadasFormatter: function(cellvalue, options, rowdata) {
        var comentarioInfraccion = rowdata["countEscIncumplidoAnt"];
        var icon='ui-icon-search',html = '';
        if (comentarioInfraccion < 1 ){       
            html='<div class="ilb cp btnGrid" onclick="coSuDsrTerminarSup.abrirPopUpComIncumInfNoSubsanadas(\'\','+rowdata.idDetalleSupervisionAnt+','+rowdata.infraccion.idInfraccion+',\''+constant.modoSupervision.consulta+'\');">';
            html+='<span class="ui-icon '+icon+'"></span></div>'
        }
        return html;
    },
    
    comentarioEitoInfNoSubsanadasFormatter: function(cellvalue, options, rowdata) {
        var icon='ui-icon-search',html = '';
        html='<div class="ilb cp btnGrid" onclick="coSuDsrTerminarSup.abrirPopUpComIncumInfNoSubsanadas('+rowdata.escenarioIncumplimientoDTO.idEsceIncumplimiento+','+rowdata.idDetalleSupervision+',\'\',\''+constant.modoSupervision.consulta+'\');">';
        html+='<span class="ui-icon '+icon+'"></span></div>'
        return html;
    },
    
    abrirPopUpComIncumInfNoSubsanadas: function(idEsceIncumplimiento,idDetalleSupervision,idInfraccion,modoSupervision) {
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
                    }
                });
//    			}
            },
            error: errorAjax
        });
    },
    
    evaluaFlagInfraccionesTerminarSup:function(){
        $('#seccMsjInfracSubsanadas').css('display','none');
//        $('#divDatosFinalesTerminarSupervision').css('display','none');
                
        if($('#flagInfraccionesTermSup').val()==constant.estado.inactivo && $('#modoSupervision').val()==constant.modoSupervision.registro){
            $('#seccMsjInfracSubsanadas').css('display','');
            $('#seccCartaVisitaINS').css('display','none');
            $('#seccGridInfNoSubsan').css('display','none');      
        };
        
        if($('#flagInfraccionesTermSup').val()==constant.estado.activo && $('#modoSupervision').val()==constant.modoSupervision.registro){
            $('#seccGridInfNoSubsan').css('display','');
            coSuDsrTerminarSup.procesarGridInfraccionesNoSubsanadas();
            $('#seccCartaVisitaINS').css('display','');
        }
        
    }
    //* OSINE_SFS-791 - RSIS 39 - Fin */
    
};


$(function () {
    /* OSINE_SFS-791 - RSIS 40 - Inicio */
    $('#fechaTerminoTerSup').datepicker();
    $('#horaTerminoTerSup').timepicker({
        showPeriod: true,
        showLeadingZero: true,
        hourText: 'Hora',
        minuteText: 'Minuto',
    });

    coSuDsrTerminarSup.inicializaObjetosEventosTerminoSupervision();
    coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.cargarFlagInfraccionesTerminarSup();
    coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.ComprobarResultadoTerminarSupervisionDsr();
    boton.closeDialog();
    /* OSINE_SFS-791 - RSIS 40 - Fin */
    /* OSINE_SFS-791 - RSIS 39 - Inicio */
//    coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.cargarFlagInfraccionesTerminarSup();
    /* OSINE_SFS-791 - RSIS 39 - Fin */
    
});
