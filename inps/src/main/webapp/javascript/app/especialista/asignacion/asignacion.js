/**
* Resumen		
* Objeto		: asignacion.js
* Descripción		: JavaScript donde se maneja las acciones de la pestaña de asignaciones del Especialista.
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  11/05/2016   |   Hernan Torres Saenz        |     Agregar criterios al filtro de búsqueda en la sección asignaciones,evaluación  y notificación/archivado 
* OSINE_SFS-480  |  19/05/2016   |   Hernán Torres Saenz        |     Adecuar  interfaz para la nueva forma de generación de órdenes de servicio (masivo).
* OSINE_SFS-480  |  20/05/2016   |   Hernán Torres Saenz        |     Crear la opción "Anular orden de servicio" en la pestaña asigaciones  de la bandeja del especialista el cual direccionará a la interfaz "Anular orden de servicio"
* OSINE_SFS-480  |  20/05/2016   |   Hernán Torres Saenz        |     Crear la interfaz "Anular orden de servicio" de acuerdo a especificaciones.
* OSINE_SFS-480  |  25/05/2016   |   Mario Dioses Fernandez     |     Crear la opción "Editar" en pestaña asignaciones de la bandeja del especialista el cual direccionará a la interfaz "Anular orden de servicio"
*
*        
*/

//especialista/asignacion/asignacion.js
var espeAsigAsig={
    procesarGridAsignacion:function() {
        var nombres = ['idExpe','flagOrigen','idTramite','idEtapTram','idProcTram','idProceso','idObliTipo','idUnidSupe','C&Oacute;DIGO OSINERGMIN',
            'RAZ&Oacute;N SOCIAL','ruc',
            'idOrdeServ','FECHA OS', 'N&Uacute;MERO OS','idTipoAsig','iteracion','N&Uacute;MERO EXPEDIENTE','fechCreaSige','idFlujSige','FLUJO SIGED','ASUNTO SIGED',
            /* OSINE_SFS-480 - RSIS 42 - Inicio */          
            'idLoca','nombCompArmaLo','idSupeEmpr','razoSociSE','EMPRESA SUPERVISORA','idPeticion', 'codigoPeticion', 'idMotivo','comentarioDevolucion','','','comentarios'];
            /* OSINE_SFS-480 - RSIS 42 - Fin */
        var columnas = [
            {name: "idExpediente", hidden:true},
            {name: "flagOrigen", hidden:true},
            {name: "tramite.idTramite", hidden:true},
            {name: "tramite.idEtapa", hidden:true},
            {name: "tramite.idProceso", hidden:true},
            {name: "proceso.idProceso", hidden:true},
            {name: "obligacionTipo.idObligacionTipo", hidden:true},
            {name: "unidadSupervisada.idUnidadSupervisada", hidden:true},
            {name: "unidadSupervisada.codigoOsinergmin", width: 70, sortable: false, align: "left"},
            {name: "unidadSupervisada.nombreUnidad", width: 245, sortable: false, align: "left"},
            {name: "unidadSupervisada.ruc", hidden:true},
//JPIRO EMPSUP            {name: "empresaSupervisada.razonSocial", width: 245, sortable: false, align: "left"},
//            {name: "empresaSupervisada.idEmpresaSupervisada", hidden:true},
//            {name: "empresaSupervisada.ruc", hidden:true},
//            {name: "empresaSupervisada.tipoDocumentoIdentidad.descripcion", hidden:true},
//            {name: "empresaSupervisada.nroIdentificacion", hidden:true},
            {name: "ordenServicio.idOrdenServicio", width: 10, hidden:true},
            {name: "ordenServicio.fechaCreacion", width: 60, sortable: false, align: "left",formatter:fecha},
            {name: "ordenServicio.numeroOrdenServicio", width: 100, sortable: false, align: "left"},
            {name: "ordenServicio.idTipoAsignacion",hidden:true},
            {name: "ordenServicio.iteracion", hidden:true},
            {name: "numeroExpediente", width: 80, sortable: false, align: "left"},
            {name: "fechaCreacionSiged", hidden:true,formatter:fecha_hora},
            {name: "flujoSiged.idFlujoSiged", hidden:true},
            {name: "flujoSiged.nombreFlujoSiged", width: 195, sortable: false, align: "left"},
            {name: "asuntoSiged", width: 240, sortable: false, align: "left"},
            
            {name: "ordenServicio.locador.idLocador", hidden:true},
            {name: "ordenServicio.locador.nombreCompletoArmado", hidden:true},
            {name: "ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa", hidden:true},
            {name: "ordenServicio.supervisoraEmpresa.razonSocial", hidden:true},
            {name: "empresaSupervisora", width: 140, sortable: false, align: "left",formatter:"empresaSupervisoraAsigEspe"},
            /* OSINE_SFS-480 - RSIS 42 - Inicio */
            {name: "ordenServicio.idPeticion.idMaestroColumna", hidden:true},
            {name: "ordenServicio.idPeticion.codigo", hidden:true},
            {name: "ordenServicio.idMotivo", hidden:true},
            {name: "ordenServicio.comentarioDevolucion", hidden:true},
            {name: "flagMuestral", hidden:true},
            {name: "obligacionSubTipo.idObligacionSubTipo", hidden:true},
            {name: "comentarios", width:140,hidden: true}
            /* OSINE_SFS-480 - RSIS 42 - Fin */
        ];
        $("#gridContenedorAsignacion").html("");
        var grid = $("<table>", {
            "id": "gridAsignacion"
        });
        var pager = $("<div>", {
            "id": "paginacionAsignacion"
        });
        $("#gridContenedorAsignacion").append(grid).append(pager);

        grid.jqGrid({
            url: baseURL + "pages/expediente/findExpediente",
            datatype: "json",
            mtype : "POST",
            postData: {
                idPersonal:$('#idPersonalSesion').val(),
                identificadorEstado:constant.identificadorEstado.expAsignado,
                cadIdentificadorEstadoOrdenServicio:"'"+constant.identificadorEstado.osRegistro+"','"+constant.identificadorEstado.osSupervisado+"','"+constant.identificadorEstado.osRevisado+"','"+constant.identificadorEstado.osAprobado+"','"+constant.identificadorEstado.osOficiado+"'",
                numeroExpediente:$('#txtNumeExpeBusqExpe').val(),
                numeroOrdenServicio:$('#txtNumeOSBusqExpe').val(),
                razonSocial:$('#txtRazoSociBusqExpe').val(),
                codigoOsinergmin:$('#txtCodigoOsiBusqExpe').val(),
                tipoEmpresaSupervisora:$('#slctTipoEmprSupeBusqExpe').val()!=null?$('#slctTipoEmprSupeBusqExpe').find(':checked').attr('codigo'):null,
                identificadorEmpresaSupervisora:$('#slctEmprSupeOSBusqExpe').val()==null||$('#slctEmprSupeOSBusqExpe').val().trim()==''?-1:$('#slctEmprSupeOSBusqExpe').val().trim()==''?-1:$('#slctEmprSupeOSBusqExpe').val(),
                identificadorOpcion:constant.identificadorBandeja.espEvaluacion
                /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
                , numeroRegistroHidrocarburo:$('#txtNumeroRH').val()==null||$('#txtNumeroRH').val().trim()==''?'':$('#txtNumeroRH').val()
                , idDepartamento:$('#slctDepartamento').val()==null||$('#slctDepartamento').val().trim()==''?'':$('#slctDepartamento').val()
                , idProvincia:$('#slctProvincia').val()==null||$('#slctProvincia').val().trim()==''?'':$('#slctProvincia').val()
                , idDistrito:$('#slctDistrito').val()==null||$('#slctDistrito').val().trim()==''?'':$('#slctDistrito').val()
                , idPeticion:$('#slctPeticion').val()==null||$('#slctPeticion').val().trim()==''?'':$('#slctPeticion').val()
                /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
            },
            hidegrid: false,
            rowNum: constant.rowNumPrinc,
            pager: "#paginacionAsignacion",
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
//                $('#linkConsultarExpeAsig').attr('onClick', 'common.abrirOrdenServicio("'+constant.accionOS.consultar+'","' + rowid + '","gridAsignacion")');
                $('#linkConsultarExpeAsig').attr('onClick', 'common.ordenServicio.abrirConsultar("' + rowid + '","gridAsignacion")');
                $('#linkVerTrazabilidadOrdeServEsAs').attr('onClick', 'common.abrirTrazabilidadOrdeServ("'+rowid+'","gridAsignacion")');
                /* OSINE_SFS-480 - RSIS 41 - Inicio */
                var row=$('#'+"gridAsignacion").jqGrid("getRowData",rowid); 
                if(row['ordenServicio.idPeticion.codigo']!=constant.codTipoPeticion.anular){
                	$('#linkAnularExpeAsig').css('display','none');
                }else{
                	$('#linkAnularExpeAsig').css('display','');
                } 
                /* OSINE_SFS-480 - RSIS 41 - Fin */
                /* OSINE_SFS-480 - RSIS 44 - Inicio */
                if(row['ordenServicio.idPeticion.codigo']!=constant.codTipoPeticion.editar){ 
                	$('#linkEditarExpeAsig').css('display','none');
                }else{
                	$('#linkEditarExpeAsig').css('display','');
                } 
                /* OSINE_SFS-480 - RSIS 44 - Fin */
                /* OSINE_SFS-480 - RSIS 41 - Inicio */
                $('#linkAnularExpeAsig').attr('onClick', 'common.abrirOrdenServicio("'+constant.accionOS.anular+'","' + rowid + '","gridAsignacion")'); 
                /* OSINE_SFS-480 - RSIS 41 - Fin */
                /* OSINE_SFS-480 - RSIS 44 - Inicio */
                $('#linkEditarExpeAsig').attr('onClick', 'common.abrirOrdenServicio("'+constant.accionOS.editar+'","' + rowid + '","gridAsignacion")'); 
                /* OSINE_SFS-480 - RSIS 44 - Fin */
            },
            loadComplete: function(data) {
                $('#contextMenuAsignacion').parent().remove();
                $('#divContextMenuAsignacion').html("<ul id='contextMenuAsignacion'>"
                        + "<li> <a id='linkConsultarExpeAsig' data-icon='ui-icon-search' title='Consultar'>Consultar</a></li>"
                        + "<li> <a id='linkVerTrazabilidadOrdeServEsAs' data-icon='ui-icon-transferthick-e-w'>Ver Trazabilidad Orden Servicio</a></li>"
                        /* OSINE_SFS-480 - RSIS 41 - Inicio */
                        + "<li> <a id='linkAnularExpeAsig' data-icon='ui-icon-trash' title='Anular Orden De Servicio'>Anular Orden De Servicio</a></li>"
                        /* OSINE_SFS-480 - RSIS 41 - Fin */
                        /* OSINE_SFS-480 - RSIS 44 - Inicio */
                        + "<li><a id='linkEditarExpeAsig' data-icon='ui-icon-pencil' title='Editar Orden De Servicio'>Editar Orden De Servicio</a></li>" 
                        /* OSINE_SFS-480 - RSIS 44 - Fin */
                        + "</ul>");
                $('#contextMenuAsignacion').puicontextmenu({
                    target: $('#gridAsignacion')
                });
                $('#contextMenuAsignacion').parent().css('width','190px');
                //cambio grid fila inps
                cambioColorTrGrid($('#gridAsignacion'));
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    }  
};

jQuery.extend($.fn.fmatter, {
    empresaSupervisoraAsigEspe: function(cellvalue, options, rowdata) {
        var loca=$.trim(rowdata.ordenServicio.locador.nombreCompletoArmado);
        var supeEmpr=$.trim(rowdata.ordenServicio.supervisoraEmpresa.razonSocial);
        var supeEmprCons=$.trim(rowdata.ordenServicio.supervisoraEmpresa.nombreConsorcio);
        var html = '';
        if (loca != null && loca != '' && loca != undefined){       
            html=loca;
        }else if(supeEmpr != null && supeEmpr != '' && supeEmpr != undefined){
            html=supeEmpr;
        }else if(supeEmprCons != null && supeEmprCons != '' && supeEmprCons != undefined){
            html=supeEmprCons;
        }
        return html;
    }
});

$(function() {
    espeAsigAsig.procesarGridAsignacion();
    /* OSINE_SFS-480 - RSIS 27 - Inicio */
    //$('#btnAbrirNuevoExpediente').click(function(){common.abrirOrdenServicio(constant.accionOS.generar,"","",constant.flgOrigenExpediente.inps);});
    $('#btnAbrirNuevoExpediente').click(function(){common.abrirOrdenServicioMasivo(constant.accionOS.generar,"","",constant.flgOrigenExpediente.inps);});
    /* OSINE_SFS-480 - RSIS 27 - Fin */
});