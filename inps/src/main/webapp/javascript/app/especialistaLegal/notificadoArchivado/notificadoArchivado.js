/**
* Resumen		
* Objeto		: notificadoArchivado.js
* Descripción		: JavaScript donde se maneja las acciones de la pestaña de notificado/ Archivado del Especalista.
* Fecha de Creación	: 25/03/2016  
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  11/05/2016   |   Hernan Torres Saenz        |     Agregar criterios al filtro de búsqueda en la sección asignaciones,evaluación y notificación/archivado 
* OSINE_SFS-480  |  25/05/2016   |   Mario Dioses Fernandez     |     Construir formulario de envio a Mensajeria, consumiendo WS
* OSINE_SFS-480  |  27/05/2016   |   Luis García Reyna          |     Funcionalidad de Datos de Mensajeria: Datos de Envio, Datos del Cargo
* OSINE_791      |  25/08/2016   |   Cristopher Paucar Torre    |     Adecuar la bandeja Notificado para el flujo DSR.       
*/

//especialista/notificadoArchivado/notificadoArchivado.js
var espeNotiArchNotiArch={
    procesarGridArchivadoNotificado:function() {
        var nombres = ['idExpe','flagOrigen','idTramite','idEtapTram','idProcTram','idProceso','idObliTipo','idUnidSupe','C&Oacute;DIGO OSINERGMIN',
                        'RAZ&Oacute;N SOCIAL','ruc',
                       'idOrdeServ','FECHA OS', 'N&Uacute;MERO OS','TIPO ASIGNACI&Oacute;N','N&Uacute;MERO EXPEDIENTE','fechCreaSige','idFlujSige','FLUJO SIGED','ASUNTO SIGED',
                       'idLoca','nombCompArmaLo','idSupeEmpr','razoSociSE','ESTADO NOTIFICACI&Oacute;N','IDENTIFICADOR','flagTramiteFinalizado','FINALIZADO','Iter.',
                   'nroOrdenesServicio','','codigoFlujoSupervInps'];
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
            {name: "unidadSupervisada.nombreUnidad", width: 220, sortable: false, align: "left"},
            {name: "unidadSupervisada.ruc", hidden:true},
//            {name: "empresaSupervisada.razonSocial", width: 220, sortable: false, align: "left"},
//            {name: "empresaSupervisada.idEmpresaSupervisada", hidden:true},
//            {name: "empresaSupervisada.ruc", hidden:true},
//            {name: "empresaSupervisada.tipoDocumentoIdentidad.descripcion", hidden:true},
//            {name: "empresaSupervisada.nroIdentificacion", hidden:true},
            {name: "ordenServicio.idOrdenServicio", width: 10, hidden:true},
            {name: "ordenServicio.fechaCreacion", width: 65, sortable: false, align: "left",formatter:fecha},
            {name: "ordenServicio.numeroOrdenServicio", width: 120, sortable: false, align: "center"},
            {name: "ordenServicio.idTipoAsignacion",hidden:true},
            {name: "numeroExpediente", width: 80, sortable: false, align: "left"},
            {name: "fechaCreacionSiged", hidden:true,formatter:fecha_hora},
            {name: "flujoSiged.idFlujoSiged", hidden:true},
            {name: "flujoSiged.nombreFlujoSiged", width: 175, sortable: false, align: "left"},
            {name: "asuntoSiged", width: 220, sortable: false, align: "left"},
            {name: "ordenServicio.locador.idLocador", hidden:true},
            {name: "ordenServicio.locador.nombreCompletoArmado", hidden:true},
            {name: "ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa", hidden:true},
            {name: "ordenServicio.supervisoraEmpresa.razonSocial", hidden:true},
            {name: "ordenServicio.estadoProceso.nombreEstado",width: 80, sortable: false, align: "center" },
            {name: "ordenServicio.estadoProceso.identificadorEstado",width: 85, sortable: false, align: "left",hidden: true },
            {name: "flagTramiteFinalizado", hidden:true},
            {name: "finalizado", width: 60, sortable: false, align: "center",formatter:"finalizadoNotifEspe"},
            {name: "ordenServicio.iteracion",width: 30, sortable: false, align: "center"},
            {name: "nroOrdenesServicio",hidden:true},
            {name: "obligacionSubTipo.idObligacionSubTipo",hidden:true},
            /* OSINE791 - RSIS28 - Inicio */
            {name: "codigoFlujoSupervInps",hidden:true},
            /* OSINE791 - RSIS28 - Fin */
        ];
        $("#gridContenedorNotificadoArchivado").html("");
        var grid = $("<table>", {
            "id": "gridNotificadoArchivado"
        });
        var pager = $("<div>", {
            "id": "paginacionNotificadoArchivado"
        });
        $("#gridContenedorNotificadoArchivado").append(grid).append(pager);

        grid.jqGrid({
        	url: baseURL + "pages/expediente/findExpediente",
            datatype: "json",
            mtype : "POST",
            postData: {
                idPersonal:$('#idPersonalSesion').val(),
                identificadorEstado:constant.identificadorEstado.expAsignado,
                cadIdentificadorEstadoOrdenServicio:"'"+constant.identificadorEstado.osOficiado+"','"+constant.identificadorEstado.osConcluido+"'",
                numeroExpediente:$('#txtNumeExpeBusqExpe').val(),
                numeroOrdenServicio:$('#txtNumeOSBusqExpe').val(),
                razonSocial:$('#txtRazoSociBusqExpe').val(),
                codigoOsinergmin:$('#txtCodigoOsiBusqExpe').val(),
                tipoEmpresaSupervisora:$('#slctTipoEmprSupeBusqExpe').val()!=null?$('#slctTipoEmprSupeBusqExpe').find(':checked').attr('codigo'):null,
                identificadorEmpresaSupervisora:$('#slctEmprSupeOSBusqExpe').val()==null||$('#slctEmprSupeOSBusqExpe').val().trim()==''?-1:$('#slctEmprSupeOSBusqExpe').val().trim()==''?-1:$('#slctEmprSupeOSBusqExpe').val(),
                identificadorOpcion:constant.identificadorBandeja.espNotificadoArchivado
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
            pager: "#paginacionNotificadoArchivado",
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
                //id: "idExpediente"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            onRightClickRow: function(rowid) {
                /* OSINE_SFS-480 - RSIS 03 - Inicio */
                $('#linkConcluirOS,#linkConfirmarDescargoOS,#linkEnviarMensajeriaOS').css('display','none'); 
                /* OSINE_SFS-480 - RSIS 03 - Fin */
                $('#contextMenuNotificadoArchivado').parent().css('opacity','1');
                
                var row=$('#gridNotificadoArchivado').getRowData(rowid);
                /* OSINE791 RSIS28 - Inicio */
                fxLinkGrilla.habilitarNotificado(row['ordenServicio.estadoProceso.identificadorEstado'],row['flagTramiteFinalizado'],
                    row['nroOrdenesServicio'],row['codigoFlujoSupervInps']);              
                /*
                if(row['ordenServicio.estadoProceso.identificadorEstado']==constant.identificadorEstado.osOficiado){
                    $('#linkConcluirOS').css('display','');
                    $('#linkEnviarMensajeriaOS').css('display','');
                }else if(row['ordenServicio.estadoProceso.identificadorEstado']==constant.identificadorEstado.osConcluido 
                        && row['flagTramiteFinalizado']==constant.estado.inactivo && row['nroOrdenesServicio'] < 2 ){
                    $('#linkConfirmarDescargoOS').css('display','');
                }else{
                    $('#contextMenuNotificadoArchivado').parent().css('opacity','0');
                }*/
                
                 //Mostrar/Ocultar
                fxLinkGrilla.mostrarOcultarMenu('contextMenuNotificadoArchivado');
                /* OSINE791 RSIS28 - Fin */
                //$('#linkConcluirOS').attr('onClick', 'common.abrirOrdenServicio("'+constant.accionOS.concluir+'","' + rowid + '","gridNotificadoArchivado")');
                $('#linkConcluirOS').attr('onClick', 'common.ordenServicio.abrirConcluir("' + rowid + '","gridNotificadoArchivado")');
                $('#linkConfirmarDescargoOS').attr('onClick', 'common.ordenServicio.abrirConfirmarDescargo("' + rowid + '","gridNotificadoArchivado")');
//                $('#linkConfirmarDescargoOS').attr('onClick', 'common.abrirOrdenServicio("'+constant.accionOS.confirmardescargo+'","' + rowid + '","gridNotificadoArchivado")');
                /* OSINE_SFS-480 - RSIS 03 - Inicio */
                $('#linkEnviarMensajeriaOS').attr('onClick', 'common.abrirEnviarMensajeria("' + rowid + '","gridNotificadoArchivado","' + 0 + '")'); 
                /* OSINE_SFS-480 - RSIS 03 - Fin */
            },
            loadComplete: function(data) {
                $('#contextMenuNotificadoArchivado').parent().remove();
                $('#divContextMenuNotificadoArchivado').html("<ul id='contextMenuNotificadoArchivado'>"
                        + "<li> <a id='linkConcluirOS' data-icon='ui-icon-mail-closed'>Concluir</a></li>"
                        + "<li> <a id='linkConfirmarDescargoOS' data-icon='ui-icon-mail-closed'>Confirmar Descargo</a></li>"
                        /* OSINE_SFS-480 - RSIS 03 - Inicio */
                        + "<li> <a id='linkEnviarMensajeriaOS' data-icon='ui-icon-mail-closed'>Enviar Mensajer&iacute;a</a></li>" 
                        /* OSINE_SFS-480 - RSIS 03 - Fin */
                        + "</ul>");
                $('#contextMenuNotificadoArchivado').puicontextmenu({
                    target: $('#gridNotificadoArchivado')
                });
                $('#contextMenuNotificadoArchivado').parent().css('width','182px');
                cambioColorTrGrid($('#gridNotificadoArchivado'));
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
//        grid.jqGrid('navGrid','#paginacionNotificadoArchivado',{edit:false,add:false,del:false,search:false,view:false,refresh:false});
//        grid.jqGrid('navButtonAdd','#paginacionNotificadoArchivado',{caption:"",buttonicon:"ui-icon-search",title:"Buscar Expediente",
//            onClickButton:function(){
//                common.abrirBusquedaExpediente(espeNotiArchNotiArch.procesarGridArchivadoNotificado,constant.bandeja.espNotificadoArchivado);
//            }
//        });
    }
};

jQuery.extend($.fn.fmatter, {
    finalizadoNotifEspe: function(cellvalue, options, rowdata) {
        return rowdata.flagTramiteFinalizado=='1'?'SI':'NO';
    }
});

$(function() {
    espeNotiArchNotiArch.procesarGridArchivadoNotificado();
    /* OSINE_SFS-480 - RSIS 06 - Inicio */
    $('#btnConsultarMensajeria').click(function(){common.abrirConsultaMensajeriaExpediente();});
    /* OSINE_SFS-480 - RSIS 06 - Fin */
});