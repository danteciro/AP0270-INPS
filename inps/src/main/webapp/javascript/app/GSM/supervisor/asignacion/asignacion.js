/**
* Resumen		
* Objeto		: asignacion.js
* Descripción		: JavaScript donde se maneja las acciones de la pestaña supervisado del Supervisor para la GSM.
* Fecha de Creación	: 25/10/2016
* PR de Creación	: OSINE_SFS-1344
* Autor			: GMD.
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-1344 |  25/10/2016   |   Giancarlo Villanueva A.    |     Creación de la pestaña de asignaciones para el Supervisor de la GSM
*                      
*/

//gsm/supervisor/asignacion/asignacion.js
var supeAsigAsig={
    procesarGridAsignacion:function() {
        var nombres = ['idExpe','flagOrigen','idTramite','idEtapTram','idProcTram','idProceso','idObliTipo','idUnidSupe',
                       'ID UNIDAD SUPERVISADA','COD. UNIDAD SUPERVISADA','NOMBRE UNIDAD SUPERVISADA',
                       'ID TITULAR MINERO','COD. TITULAR MINERO','NOMBRE TITULAR MINERO',
                       'C&Oacute;DIGO OSINERGMIN',
                       'RAZ&Oacute;N SOCIAL','idEmprSupe','ruc','tipoDocumentoIdentidad','nroIdentificacion',
                       'idOrdeServ','FECHA OS', 'N&Uacute;MERO OS','IdTipoAsig','fechaCreacionOS','N&Uacute;MERO EXPEDIENTE','fechCreaSige','idFlujSige','FLUJO SIGED','ASUNTO SIGED',
                       'ESTRATO',
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
                       /* OSINE_SFS-1344 - Inicio */
                       {name: "unidadSupervisadaGSM.idDmUnidadSupervisada",hidden:true},//ID UNIDAD SUPERVISADA
                       {name: "unidadSupervisadaGSM.codigoUnidadSupervisada", width: 110, sortable: false, align: "left"},//CODIGO UNIDAD SUPERVISADA
                       {name: "unidadSupervisadaGSM.nombreUnidadSupervisada", width: 110, sortable: false, align: "left"},//NOMBRE UNIDAD SUPERVISADA
                       {name: "titularMinero.idTitularMinero",hidden:true},//ID TITULAR MINERO
                       {name: "titularMinero.codigoTitularMinero", width: 110, sortable: false, align: "left"},//CODIGO TITULAR MINERO
                       {name: "titularMinero.nombreTitularMinero", width: 110, sortable: false, align: "left"},//NOMBRE TITULAR MINERO
                       /* OSINE_SFS-1344 - Fin */
                       {name: "unidadSupervisada.codigoOsinergmin", width: 110, sortable: false, align: "left", hidden:true},
                       {name: "empresaSupervisada.razonSocial", width: 260, sortable: false, align: "left", hidden:true},
                       {name: "empresaSupervisada.idEmpresaSupervisada", hidden:true},
                       {name: "empresaSupervisada.ruc", hidden:true},
                       {name: "empresaSupervisada.tipoDocumentoIdentidad.descripcion", hidden:true},
                       {name: "empresaSupervisada.nroIdentificacion", hidden:true},
                       {name: "ordenServicio.idOrdenServicio", hidden:true},
                       {name: "ordenServicio.fechaCreacion", width: 80, sortable: false, align: "left",formatter:fecha},//FECHA OS
                       {name: "ordenServicio.numeroOrdenServicio", width: 130, sortable: false, align: "center"},//NUMERO OS
                       {name: "ordenServicio.idTipoAsignacion",hidden:true},
                       {name: "ordenServicio.fechaCreacionOS", hidden:true},
                       {name: "numeroExpediente", width: 100, sortable: false, align: "left"},//NUMERO EXPEDIENTE
                       {name: "fechaCreacionSiged", hidden:true,formatter:fecha_hora},
                       {name: "flujoSiged.idFlujoSiged", hidden:true},
                       {name: "flujoSiged.nombreFlujoSiged", width: 190, sortable: false, align: "left"},//FLUJO SIGED
                       {name: "asuntoSiged", width: 200, sortable: false, align: "left"},//ASUNTO SIGED
                       /* OSINE_SFS-1344 - Inicio */
                       {name: "unidadSupervisadaGSM.idEstrato.descripcion", width: 110, sortable: false, align: "left"},//ESTRATO
                       /* OSINE_SFS-1344 - Fin */
                       {name: "ordenServicio.locador.idLocador", hidden:true},
                       {name: "ordenServicio.locador.nombreCompletoArmado", hidden:true},
                       {name: "ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa", hidden:true},
                       {name: "ordenServicio.supervisoraEmpresa.razonSocial", hidden:true},
                       {name: "ordenServicio.motivoReasignacion",width: 30, sortable: false, align: "center",formatter:"mostrarObservacion"},//OBS.
                       {name: "ordenServicio.iteracion",width: 30, sortable: false, align: "center",hidden:true},
                       {name: "ordenServicio.flagConfirmaTipoAsignacion",width: 30, sortable: false, align: "center", hidden:true},
                       {name: "flagEvaluaTipoAsignacion",width: 30, sortable: false, align: "center", hidden:true},
                       {name: "ordenServicio.fechaHoraAnalogicaCreacionOS",width: 30, sortable: false, align: "center", hidden:true}                       
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
        	url: baseURL + "pages/expedienteGSM/findExpediente",
            datatype: "json",
            mtype : "POST",
            postData: {
                idPersonal:$('#idPersonalSesion').val(),
                nombreRol:$('#rolSesion').val(),
                identificadorEstado:constant.identificadorEstado.expAsignado,
                cadIdentificadorEstadoOrdenServicio:"'"+constant.identificadorEstado.osRegistro+"'",
                numeroExpediente:$('#txtNumeExpeBusqExpe').val(),
                numeroOrdenServicio:$('#txtNumeOSBusqExpe').val(),
                nombreTitularMinero:$('#txtNomTitMinOSBusqExpe').val(),
                codigoOsinergmin:$('#txtCodigoOsiBusqExpe').val(),
                razonSocial:$('#txtNomUnidSupOSBusqExpe').val()
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
            },
            loadComplete: function(data) {
                $('#contextMenuAsignacion').parent().remove();
                $('#divContextMenuAsignacion').html("<ul id='contextMenuAsignacion'>"
                        + "<li><a id='linkConsultarExpeAsig' data-icon='ui-icon-search' title='Consultar'>Atender Orden de Servicio</a></li>"
                        + "<li><a id='linkVerificarLevantamientoAsig' data-icon='ui-icon-check' title='Verificar Levantamiento'>Verificar Levantamiento</a></li>"
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
    /* OSINE_SFS-1344 - Inicio */
//    abrirOrdenesServicioSupervisor:function(){
//        $.ajax({
//            url:baseURL + "pages/expedienteGSM/abrirOrdenServicioSupervisor", 
//            type:'get',
//            async:false,
//            data:{
//                idPersonal:$('#idPersonalSesion').val()
//            },
//            beforeSend:loading.open,
//            success:function(data){
//                loading.close();
//                $("#dialogOrdenServicioSupervisor").html(data);
//                $("#dialogOrdenServicioSupervisor").dialog({
//                    resizable: false,
//                    draggable: true,
//                    autoOpen: true,
//                    height:"auto",
//                    width: "auto",
//                    modal: true,
//                    dialogClass: 'dialog',
//                    title: "ÓRDENES DE SERVICIO",
//                    closeText: "Cerrar"
//                });
//            },
//            error:errorAjax
//        });
//    } 
    /* OSINE_SFS-1344 - Fin */
};

//var supeAsigDevo={
//	/* OSINE_SFS-1344 - Inicio */
//    abrirOrdenServicioSupervisorDevolver:function(){
//        $.ajax({
//            url:baseURL + "pages/expedienteGSM/abrirOrdenServicioSupervisorDevolver", 
//            type:'get',
//            async:false,
//            data:{
//                idPersonal:$('#idPersonalSesion').val()
//            },
//            beforeSend:loading.open,
//            success:function(data){
//                loading.close();
//                $("#dialogOrdenServicioSupervisorDevolver").html(data);
//                $("#dialogOrdenServicioSupervisorDevolver").dialog({
//                    resizable: false,
//                    draggable: true,
//                    autoOpen: true,
//                    height:"auto",
//                    width: "auto",
//                    modal: true,
//                    dialogClass: 'dialog',
//                    title: "DEVOLVER ASIGNACIÓN",
//                    closeText: "Cerrar"
//                });
//            },
//            error:errorAjax
//        });
//    }
//	/* OSINE_SFS-1344 - Fin */
//};

/* OSINE_SFS-1344 - Inicio */
$(function() {
    supeAsigAsig.procesarGridAsignacion();
//    $('#btnAbrirOrdenesServicioSupervisor').click(supeAsigAsig.abrirOrdenesServicioSupervisor);   
//    $('#btnDevolverAsignaciones').click(supeAsigDevo.abrirOrdenServicioSupervisorDevolver);

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
/* OSINE_SFS-1344 - Fin */