/**
* Resumen		
* Objeto		: asignacion.js
* Descripción		: JavaScript donde se maneja las acciones de la pestaña supervisado del Supervisor.
* Fecha de Creación	: 25/03/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: GMD.
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  11/05/2016   |   Hernan Torres Saenz        |     Agregar criterios al filtro de búsqueda en la sección asignaciones,evaluación y notificación/archivado 
* OSINE_SFS-480  |  17/05/2016   |   Luis García Reyna          |     Crear la interfaz "devolver asignaciones" de acuerdo a especificaciones.
* OSINE_SFS-791  |  06/10/2016   |   Mario Dioses Fernandez     |     Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
*                      
*/

//supervisor/asignacion/asignacion.js
var supeAsigAsig={
    procesarGridAsignacion:function() {
        var nombres = ['idExpe','flagOrigen','idTramite','idEtapTram','idProcTram','idProceso','idObliTipo','idUnidSupe','C&Oacute;DIGO OSINERGMIN',
                       'RAZ&Oacute;N SOCIAL','ruc',
                       'idOrdeServ','FECHA OS', 'N&Uacute;MERO OS','IdTipoAsig','fechaCreacionOS','N&Uacute;MERO EXPEDIENTE','fechCreaSige','idFlujSige','FLUJO SIGED','ASUNTO SIGED',
                       'idLoca','nombCompArmaLo','idSupeEmpr','razoSociSE','Obs','Iter.', 
                       'flagConfirmaTipoAsignacion', 'flagEvaluaTipoAsignacion', 'fechaHoraAnalogicaCreacionOS'];
                   var columnas = [
                       {name: "idExpediente", hidden:true},
                       {name: "flagOrigen", hidden:true},
                       {name: "tramite.idTramite", hidden:true},
                       {name: "tramite.idEtapa", hidden:true},
                       {name: "tramite.idProceso", hidden:true},
                       {name: "proceso.idProceso", hidden:true},
                       {name: "obligacionTipo.idObligacionTipo", hidden:true},
                       {name: "unidadSupervisada.idUnidadSupervisada", hidden:true},
                       {name: "unidadSupervisada.codigoOsinergmin", width: 110, sortable: false, align: "left"},
                       {name: "unidadSupervisada.nombreUnidad", width: 260, sortable: false, align: "left"},
                       {name: "unidadSupervisada.ruc", hidden:true},
//                       {name: "empresaSupervisada.razonSocial", width: 260, sortable: false, align: "left"},
//                       {name: "empresaSupervisada.idEmpresaSupervisada", hidden:true},
//                       {name: "empresaSupervisada.ruc", hidden:true},
//                       {name: "empresaSupervisada.tipoDocumentoIdentidad.descripcion", hidden:true},
//                       {name: "empresaSupervisada.nroIdentificacion", hidden:true},
                       {name: "ordenServicio.idOrdenServicio", hidden:true},
                       {name: "ordenServicio.fechaCreacion", width: 80, sortable: false, align: "left",formatter:fecha},
                       {name: "ordenServicio.numeroOrdenServicio", width: 130, sortable: false, align: "center"},
                       {name: "ordenServicio.idTipoAsignacion",hidden:true},
                       {name: "ordenServicio.fechaCreacionOS", hidden:true},
                       {name: "numeroExpediente", width: 100, sortable: false, align: "left"},
                       {name: "fechaCreacionSiged", hidden:true,formatter:fecha_hora},
                       {name: "flujoSiged.idFlujoSiged", hidden:true},
                       {name: "flujoSiged.nombreFlujoSiged", width: 190, sortable: false, align: "left"},
                       {name: "asuntoSiged", width: 200, sortable: false, align: "left"},
                       {name: "ordenServicio.locador.idLocador", hidden:true},
                       {name: "ordenServicio.locador.nombreCompletoArmado", hidden:true},
                       {name: "ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa", hidden:true},
                       {name: "ordenServicio.supervisoraEmpresa.razonSocial", hidden:true},
                       {name: "ordenServicio.motivoReasignacion",width: 30, sortable: false, align: "center",formatter:"mostrarObservacion"},
                       {name: "ordenServicio.iteracion",width: 30, sortable: false, align: "center"},
                       /* OSINE_SFS-791 - RSIS 33 - Inicio */
                       {name: "ordenServicio.flagConfirmaTipoAsignacion",width: 30, sortable: false, align: "center", hidden:true},
                       {name: "flagEvaluaTipoAsignacion",width: 30, sortable: false, align: "center", hidden:true},
                       {name: "ordenServicio.fechaHoraAnalogicaCreacionOS",width: 30, sortable: false, align: "center", hidden:true}                       
                       /* OSINE_SFS-791 - RSIS 33 - Fin */
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
                nombreRol:$('#rolSesion').val(),
                identificadorEstado:constant.identificadorEstado.expAsignado,
                cadIdentificadorEstadoOrdenServicio:"'"+constant.identificadorEstado.osRegistro+"'",
                numeroExpediente:$('#txtNumeExpeBusqExpe').val(),
                numeroOrdenServicio:$('#txtNumeOSBusqExpe').val(),
                razonSocial:$('#txtRazoSociBusqExpe').val(),
                codigoOsinergmin:$('#txtCodigoOsiBusqExpe').val()
                /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
                , numeroRegistroHidrocarburo:$('#txtNumeroRH').val()==null||$('#txtNumeroRH').val().trim()==''?'':$('#txtNumeroRH').val()
                , idDepartamento:$('#slctDepartamento').val()==null||$('#slctDepartamento').val().trim()==''?'':$('#slctDepartamento').val()
                , idProvincia:$('#slctProvincia').val()==null||$('#slctProvincia').val().trim()==''?'':$('#slctProvincia').val()
                , idDistrito:$('#slctDistrito').val()==null||$('#slctDistrito').val().trim()==''?'':$('#slctDistrito').val()
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
            	/* OSINE_SFS-791 - RSIS 33 - Inicio */
            	var row = $('#gridAsignacion').jqGrid("getRowData",rowid);
            	//Opcion Atender Orden Servicio.
            	if(row!=null && row["flagEvaluaTipoAsignacion"]=='' || row["flagEvaluaTipoAsignacion"]==null){
            		$('#linkConsultarExpeAsig').attr('onClick', 'common.ordenServicio.abrirAtender("' + rowid + '","gridAsignacion")');
            		$('#linkConsultarExpeAsig').css("display", "inline");
                	$('#linkVerificarLevantamientoAsig').css("display", "none");
                	$('#contextMenuAsignacion').parent().css('opacity', '1');
            	}                
                //Opcion Orden Levantamiento DSR-CRITICIDAD. ==> iteracion > iteracion.primera && flagEvaluaTipoAsignacion = estado.activo       
            	else if(row!=null && row["flagEvaluaTipoAsignacion"]==constant.estado.activo && row["ordenServicio.iteracion"] > constant.iteracion.primera &&
                		(row["ordenServicio.flagConfirmaTipoAsignacion"]==null || row["ordenServicio.flagConfirmaTipoAsignacion"]=="")){
            		$('#linkVerificarLevantamientoAsig').attr('onClick', 'common.ordenServicio.abrirVerificarLevantamiento("' + rowid + '","gridAsignacion")');
                	$('#linkConsultarExpeAsig').css("display", "none");
                	$('#linkVerificarLevantamientoAsig').css("display", "inline");
                	$('#contextMenuAsignacion').parent().css('opacity', '1');
                //Opcion Atender Orden Servicio. ==> iteracion = iteracion.primera && flagEvaluaTipoAsignacion = estado.activo
                } else if(row!=null && (row["flagEvaluaTipoAsignacion"]==constant.estado.activo && row["ordenServicio.iteracion"] > constant.iteracion.primera &&
                		(row["ordenServicio.flagConfirmaTipoAsignacion"]==constant.estado.activo)) || 
                		(row["flagEvaluaTipoAsignacion"]==constant.estado.activo && row["ordenServicio.iteracion"] == constant.iteracion.primera &&
                		(row["ordenServicio.flagConfirmaTipoAsignacion"]==null || row["ordenServicio.flagConfirmaTipoAsignacion"]==""))){
                	$('#linkConsultarExpeAsig').attr('onClick', 'common.ordenServicio.abrirAtender("' + rowid + '","gridAsignacion")');
            		$('#linkConsultarExpeAsig').css("display", "inline");
                	$('#linkVerificarLevantamientoAsig').css("display", "none");
                	$('#contextMenuAsignacion').parent().css('opacity', '1');
                } else {
                	$('#contextMenuAsignacion').parent().css('opacity', '0');
                }
                /* OSINE_SFS-791 - RSIS 33 - Fin */ 
            },
            loadComplete: function(data) {
                $('#contextMenuAsignacion').parent().remove();
                $('#divContextMenuAsignacion').html("<ul id='contextMenuAsignacion'>"
                        + "<li><a id='linkConsultarExpeAsig' data-icon='ui-icon-search' title='Consultar'>Atender Orden de Servicio</a></li>"
                        /* OSINE_SFS-791 - RSIS 33 - Inicio */
                        + "<li><a id='linkVerificarLevantamientoAsig' data-icon='ui-icon-check' title='Verificar Levantamiento'>Verificar Levantamiento</a></li>"
                        /* OSINE_SFS-791 - RSIS 33 - Fin */
                        + "</ul>");
                $('#contextMenuAsignacion').puicontextmenu({
                    target: $('#gridAsignacion')
                });
                $('#contextMenuAsignacion').parent().css('width','182px');
                //cambio grid fila inps
                cambioColorTrGrid($('#gridAsignacion'));
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    abrirOrdenesServicioSupervisor:function(){
        $.ajax({
            url:baseURL + "pages/bandeja/abrirOrdenServicioSupervisor", 
            type:'get',
            async:false,
            data:{
                idPersonal:$('#idPersonalSesion').val()
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogOrdenServicioSupervisor").html(data);
                $("#dialogOrdenServicioSupervisor").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: "ÓRDENES DE SERVICIO",
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });
    }    
};

/* OSINE_SFS-480 - RSIS 38 - Inicio */
var supeAsigDevo={
    abrirOrdenServicioSupervisorDevolver:function(){
        $.ajax({
            url:baseURL + "pages/bandeja/abrirOrdenServicioSupervisorDevolver", 
            type:'get',
            async:false,
            data:{
                idPersonal:$('#idPersonalSesion').val()
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogOrdenServicioSupervisorDevolver").html(data);
                $("#dialogOrdenServicioSupervisorDevolver").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: "DEVOLVER ASIGNACIÓN",
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });
    }           
};
/* OSINE_SFS-480 - RSIS 38 - Fin */

$(function() {
    supeAsigAsig.procesarGridAsignacion();
    $('#btnAbrirOrdenesServicioSupervisor').click(supeAsigAsig.abrirOrdenesServicioSupervisor);   
    /* OSINE_SFS-480 - RSIS 38 - Inicio */
    $('#btnDevolverAsignaciones').click(supeAsigDevo.abrirOrdenServicioSupervisorDevolver);
    /* OSINE_SFS-480 - RSIS 38 - Fin */
});
jQuery.extend($.fn.fmatter, {
	mostrarObservacion: function(cellvalue, options, rowdata) {
        var motivoReasignacion=rowdata.ordenServicio.motivoReasignacion;
        var html = '';
        if (motivoReasignacion != null && motivoReasignacion != '' && motivoReasignacion!=undefined){       
            html = '<div class="ilb cp" title="Observación" onclick="confirmer.open(\''+motivoReasignacion+'\',\'\',{textAceptar:\'\',textCancelar:\'Cerrar\',title:\'Observación\'});"><img class="vam" width="17" height="18" src="'+baseURL+'images/cuaderno.png"></div>';
        }
        return html;
    }
});