/* 
    Document   : asignacion
    Created on : 25/03/2016, 09:32:18 AM
    Author     : htorress
*/

//supervisorRegional/recepcion/recepcion.js
var espeDeriDeri={
    procesarGridDerivado:function() {
        
        var nombres = ['idExpe','flagOrigen','','C&Oacute;DIGO OSINERGMIN','RAZ&Oacute;N SOCIAL','ruc',
            'N&Uacute;MERO EXPEDIENTE', 'FECHA CREACI&Oacute;N SIGED','','FLUJO SIGED',
            'ASUNTO SIGED','FECHA DERIVACI&Oacute;N','idTramite','idEtapa','TramIdProceso','ProIdProceso','idObligacionTipo','idLocador','idSupervisoraEmpresa','iteracion','iteracionExpediente'];
        var columnas = [
            {name: "idExpediente" ,hidden:true},
            {name: "flagOrigen", hidden:true},
            {name: "unidadSupervisada.idUnidadSupervisada" , hidden:true},
            {name: "unidadSupervisada.codigoOsinergmin", width: 95, sortable: false, align: "left"},
            {name: "unidadSupervisada.nombreUnidad", width: 265, sortable: false, align: "left"},
            {name: "unidadSupervisada.ruc", hidden:true},
//            {name: "empresaSupervisada.razonSocial", width: 265, sortable: false, align: "left"},
//            {name: "empresaSupervisada.idEmpresaSupervisada", hidden:true},
//            {name: "empresaSupervisada.ruc", hidden:true},
//            {name: "empresaSupervisada.tipoDocumentoIdentidad.descripcion" , hidden:true},
//            {name: "empresaSupervisada.nroIdentificacion", hidden:true},
            {name: "numeroExpediente", width: 90, sortable: false, align: "left"},
            {name: "fechaCreacionSiged" , width: 105, sortable: false, align: "left",formatter:fecha_hora},
            {name: "flujoSiged.idFlujoSiged" , hidden:true},
            {name: "flujoSiged.nombreFlujoSiged" , width: 195, sortable: false, align: "left"},
            {name: "asuntoSiged", width: 280, sortable: false, align: "left"},
            {name: "fechaDerivacion" , width: 105, sortable: false, align: "left",formatter:fecha_hora},            
            {name: "tramite.idTramite", hidden:true},
            {name: "tramite.idEtapa", hidden:true},
            {name: "tramite.idProceso" , hidden:true},
            {name: "proceso.idProceso", hidden:true},
            {name: "obligacionTipo.idObligacionTipo", hidden:true},
            {name: "ordenServicio.locador.idLocador" , hidden:true},
            {name: "ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa", hidden:true},
            {name: "ordenServicio.iteracion" ,hidden:true},
            {name: "iteracionExpediente" ,hidden:true}
        ];
        $("#gridContenedorDerivado").html("");
        var grid = $("<table>", {
            "id": "gridDerivado"
        });
        var pager = $("<div>", {
            "id": "paginacionDerivado"
        });
        $("#gridContenedorDerivado").append(grid).append(pager);

        grid.jqGrid({
            url: baseURL + "pages/expediente/findExpediente",
            datatype: "json",
            mtype : "POST",
            postData: {
                idPersonal:$('#idPersonalSesion').val(),
                identificadorEstado:constant.identificadorEstado.expDerivado,
                cadIdentificadorEstadoOrdenServicio:"'"+constant.identificadorEstado.osConcluido+"','"+constant.identificadorEstado.osRegistro+"'",
                numeroExpediente:$('#txtNumeExpeBusqExpe').val(),
                numeroOrdenServicio:$('#txtNumeOSBusqExpe').val(),
                razonSocial:$('#txtRazoSociBusqExpe').val(),
                codigoOsinergmin:$('#txtCodigoOsiBusqExpe').val(),
                identificadorOpcion:constant.identificadorBandeja.espEvaluacion,
                cadEstado:"'"+constant.estado.inactivo+"','"+constant.estado.activo+"'"
            },
            hidegrid: false,
            rowNum: constant.rowNumPrinc,
            pager: "#paginacionDerivado",
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
                $('#linkAsignarOS').attr('onClick', 'common.abrirOrdenServicio("'+constant.accionOS.asignar+'","'+rowid+'","gridDerivado")');
                $('#linkReasignarExpe').attr('onClick', 'common.abrirReasignarExpe("'+rowid+'","gridDerivado")');
            },
            loadComplete: function(data) {
                $('#contextMenuDerivado').parent().remove();
                $('#divContextMenuDerivado').html("<ul id='contextMenuDerivado'>"
                        + "<li> <a id='linkAsignarOS' data-icon='ui-icon-person'>Asignar Orden de Servicio</a></li>"
                        + "<li> <a id='linkReasignarExpe' data-icon='ui-icon-check'>Reasignar Especialista</a></li>"
                        + "</ul>");
                $('#contextMenuDerivado').puicontextmenu({
                    target: $('#gridDerivado')
                });
                $('#contextMenuDerivado').parent().css('width','182px');
                cambioColorTrGrid($('#gridDerivado'));
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        }); 
//        grid.jqGrid('navGrid','#paginacionDerivado',{edit:false,add:false,del:false,search:false,view:false,refresh:false});
//        grid.jqGrid('navButtonAdd','#paginacionDerivado',{caption:"",buttonicon:"ui-icon-search",title:"Buscar Expediente",
//            onClickButton:function(){
//                common.abrirBusquedaExpediente(espeDeriDeri.procesarGridDerivado,constant.bandeja.espDerivados);
//            }
//        });
    }
	
};

//inicializacion
$(function() {
    espeDeriDeri.procesarGridDerivado();
});




