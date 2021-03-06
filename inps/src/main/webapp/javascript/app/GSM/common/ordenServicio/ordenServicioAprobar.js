/**
* Resumen		
* Objeto		: ordenServicioAprobar.js
* Descripción		: ordenServicioAprobar
* Fecha de Creación	: 09/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                         |     Descripción
* =====================================================================================================================================================================
*    
*/

//common/OrdenServicio/OrdenServicioAprobar.js
var coOrSeOrSeApr = {
    armaConsultarFormOS : function() {
            if ($('#txtflagOrigenOSApr').val() == constant.flgOrigenExpediente.siged) {
                    fill.clean("#slctIdEtapaOSApr,#slctIdTramiteOSApr");
            		common.serie.cargarEtapa("#slctIdProcesoOSApr", "#slctIdEtapaOSApr");
                    $('#slctIdEtapaOSApr').val($('#txtIdEtapaTrOSApr').val());
                    $('#slctIdEtapaOSApr').trigger('change');
                    $('#slctIdTramiteOSApr').val($('#txtIdTramiteOSApr').val());
            }
            // para datos de empresa supervisora
            if ($('#txtIdLocadorOSApr').val() != '' && $('#txtIdLocadorOSApr').val() != undefined) {
                $('#slctTipoEmprSupeOSApr').val($('#slctTipoEmprSupeOSApr').find('option[codigo="'+constant.empresaSupervisora.personaNatural+'"]').val())
                $('#slctTipoEmprSupeOSApr').trigger("change");
                $('#slctEmprSupeOSApr').val($('#txtIdLocadorOSApr').val());
            } else if ($('#txtIdSupervisoraEmpresaOSApr').val() != '' && $('#txtIdSupervisoraEmpresaOSApr').val() != undefined){
                $('#Apr').val($('#slctTipoEmprSupeOSApr').find('option[codigo="'+constant.empresaSupervisora.personaJuridica+'"]').val())
                $('#slctTipoEmprSupeOSApr').trigger("change");
                $('#slctEmprSupeOSApr').val($('#txtIdSupervisoraEmpresaOSApr').val());
            }
    },
    fxAprobar : {
        validaAprobarOS : function() {
                confirmer.open("¿Confirma que desea Aprobar la Orden de Servicio?",
                                "coOrSeOrSeApr.fxAprobar.procesaAprobarOrdenServicio()");
        },
        procesaAprobarOrdenServicio : function() {
            loading.open();
            $.getJSON(baseURL + "pages/ordenServicio/aprobar", {
                    'expediente.idExpediente' : $('#txtIdExpedienteOSApr').val(),
                    idOrdenServicio : $('#txtIdOrdenServicioOSApr').val(),
                    idPersonalOri : $('#idPersonalSesion').val(),
                    'expediente.asuntoSiged' : $('#txtAsuntoSigedOSApr').val(),
                    'expediente.numeroExpediente' : $('#txtNumeroExpedienteOSApr').val(),
                    'expediente.obligacionTipo.idObligacionTipo':$('#txtIdObligacionTipoApr').val(),
                    'expediente.unidadSupervisada.actividad.idActividad':$('#txtIdActividadApr').val(),
                    'expediente.proceso.idProceso':$('#txtIdProcesoApr').val()
                    
            }).done(function(data) {
                loading.close();
                if (data.resultado == '0') {
                        $("#dialogOrdenServicio").dialog('close');
                        mensajeGrowl("success", constant.confirm.save, "");
                        // recargar grillas involucradas
                        espeEvalEval.procesarGridEvaluacion();
                        espeNotiArchNotiArch.procesarGridArchivadoNotificado();
                } else {
                        mensajeGrowl("error", data.mensaje, "");
                }
            }).fail(errorAjax);
        }
    },
    fxObservarAprobar : {
        observarOS : function() {
                $('#divFormOSApr').hide("blind");
                $('#btnObservarOSAprobarApr,#btnsAccPrincApr').hide();
                $('#formObservaApr,#btnConfirmaObservarOSAprobarApr').show();
        },
        validaObservarOS : function() {
            if ($('#frmObservarOSApr').validateAllForm("#divMensajeValidaObservaApr")) {
                confirmer.open("¿Confirma que desea Observar/Devolver la Orden de Servicio?","coOrSeOrSeApr.fxObservarAprobar.procesaObservarOrdenServicio()");
            }
        },
        procesaObservarOrdenServicio : function() {
            $.ajax({
                url : baseURL + "pages/ordenServicio/observarAprobar",
                type : 'post',
                async : false,
                dataType : 'json',
                data : {
                    'expediente.idExpediente' : $('#txtIdExpedienteOSApr').val(),
                    idOrdenServicio : $('#txtIdOrdenServicioOSApr').val(),
                    idPersonalOri : $('#idPersonalSesion').val(),
                    motivoReasignacion : $('#txtMotivoObserOSApr').val(),
                    // htorress - RSIS 18 - Inicio
                    'expediente.asuntoSiged' : $('#txtAsuntoSigedOSApr').val(),
                    'expediente.numeroExpediente' : $('#txtNumeroExpedienteOSApr').val(),
                    // htorress - RSIS 18 - Fin
                    'expediente.obligacionTipo.idObligacionTipo':$('#txtIdObligacionTipoApr').val(),
                    'expediente.unidadSupervisada.actividad.idActividad':$('#txtIdActividadApr').val(),
                    'expediente.proceso.idProceso':$('#txtIdProcesoApr').val()
                },
                beforeSend : loading.open,
                success : function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                            $("#dialogOrdenServicio").dialog('close');
                            mensajeGrowl("success", constant.confirm.save, "");
                            // recargar grillas involucradas
                            espeEvalEval.procesarGridEvaluacion();
                    } else {
                            mensajeGrowl("error", data.mensaje, "");
                    }
                },
                error : function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
        }
    },    
    fillDataUnidadOperativa : function(data) {
        $('#txtRubroOSApr,#txtDireOperOSApr').val("");
        if (data != null && data.registro!=null) {
            $('#txtIdActividadApr').val(data.registro.idActividad);
            $('#txtRubroOSApr').val(data.registro.actividad);

            if(data.registro.direccionUnidadsupervisada.direccionCompleta!=null && data.registro.direccionUnidadsupervisada.direccionCompleta!=undefined){
                $('#txtDireOperOSApr').val(data.registro.direccionUnidadsupervisada.direccionCompleta);
                $('#txtIdDepartamentoOSApr').val(data.registro.direccionUnidadsupervisada.departamento.idDepartamento); 
                $('#txtIdProvinciaOSApr').val(data.registro.direccionUnidadsupervisada.provincia.idProvincia);
                $('#txtIdDistritoOSApr').val(data.registro.direccionUnidadsupervisada.distrito.idDistrito); 
            }
        }
    },
    procBuscarTitularMineroOS: function(nombreGrid) {
  
            var nombres = ['idDmUnidadSupervisada','idTitularMinero', 
                           'C&Oacute;D. TITULAR MINERO', 'NOMBRE TITULAR MINERO', 
                           'idEstrato.idMaestroColumna','ESTRATO',
                           'C&Oacute;D. UNIDAD SUPERVISADA', 'NOMBRE UNIDAD SUPERVISADA', 
                           'idTipoMinado.idMaestroColumna', 'TIPO MINADO', 
                           'idActividad.idActividad', 'RUBRO', 'SELECCIONAR'];
            var columnas = [
                {name: "idDmUnidadSupervisada", hidden: true},
                {name: "idTitularMinero.idTitularMinero", hidden: true},
                
                {name: "idTitularMinero.codigoTitularMinero", width: 70, align: "center",hidden: false},
                {name: "idTitularMinero.nombreTitularMinero", width: 250,hidden: false, align: "center"},
                
                {name: "idEstrato.idMaestroColumna", hidden: true},
                {name: "idEstrato.descripcion", align: "center", width: 95, hidden: false},
                
                {name: "codigoUnidadSupervisada", width: 80, align: "center"},
                {name: "nombreUnidadSupervisada", width: 200, align: "center"},
                
                {name: "idTipoMinado.idMaestroColumna", hidden: true},
                {name: "idTipoMinado.descripcion", width: 90, align: "center"},
                
                {name: "idActividad.idActividad", hidden: true},
                {name: "idActividad.nombre", width: 80, align: "center", hidden: true},
                
                {name: "", width: 70, sortable: false, align: "center",  formatter: radioSelecUnidadSupervisada}
            ];
            $("#gridContenedor"+nombreGrid).html("");
            var grid = $("<table>", {
            	"id": "grid"+nombreGrid
            });
            var pager = $("<div>", {
            	"id": "paginacion"+nombreGrid
            });
            $("#gridContenedor"+nombreGrid).append(grid).append(pager);

            grid.jqGrid({
                url: baseURL + "pages/ordenServicioGSM/cargaUnidadSupervisadaTitularMinero",
                datatype: "json",
                mtype: "POST",
                postData: {
                	codigoTitularMinero:$('#txtCodigoTitularMinero').val()
                },
                hidegrid: false,
                rowNum: constant.rowNumPrinc,
                pager: "#paginacion"+nombreGrid,
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
                    id: "idDmUnidadSupervisada"
                },
                onSelectRow: function(rowid, status) {
                    grid.resetSelection();
                },
                onRightClickRow: function(rowid) {
                	/*
                	var row=$('#grid'+nombreGrid).getRowData(rowid); 
                    $('#contextMenu'+nombreGrid).parent().css('opacity', '1');
                    $('#linkQuitar').css('display', '')
                    $('#linkQuitar').attr('onClick', 'coOrSeOrSeMa.fxGenerar.validarQuitarUO("' + rowid + '","gridUnidadesOperativas")');
                    */
                },
                loadComplete: function(data) {
                	if(data.resultado!=0){
                		mensajeGrowl(
                                "error",
                                "No se encuentra el C&oacute;digo del Titular Minero ingresado.",
                                "");
                	}
                	/*
                    //agregar check seleccionar todos
                    var html = '<input id="chkSeleAllVisibles" type="checkbox" name="" value="" onclick="coOrSeOrSeMa.selecAllOrdenServicioVisibles(this);"><label for="chkSeleAllVisibles" class="checkbox"></label>';
                    $('#gridUnidadesOperativas_check').html(html);
                    $('#gridUnidadesOperativas_check').css('height', '20px');
                    $('#gridUnidadesOperativas_check').removeClass('ui-jqgrid-sortable');

                    //para marcar los ya seleccionados, casos de navegar entre paginas de grilla
                    $('#unidOperativaSeleccionadaMAS').find('div').map(function() {
                        $('#idUnidSup' + $(this).attr('id').replace('idUnidSupSele', '')).attr('checked', true);
                    });
                    // Para la opción de Quitar UO de la lista de Sesión.
                    $('#contextMenuUnidadOperativa').parent().remove();
                    $('#divContextMenuUnidadOperativaMAS').html("<ul id='contextMenuUnidadOperativa'>"
                            + "<li> <a id='linkQuitar' data-icon='ui-icon-trash' title='Quitar'>Quitar</a></li>"
                            + "</ul>");
                    $('#contextMenuUnidadOperativa').puicontextmenu({
                        target: $('#grid'+nombreGrid)
                    });
                    */
                	
                },
                loadError: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
        
    },
    inicializaObjetosEventos:function(){
        // change UnidadOperativa
	$('#slctUnidSupeOSApr').change(function() {
            $('#txtIdActividadApr,#txtRubroOSApr,#txtDireOperOSApr,#txtIdDepartamentoOSApr,#txtIdProvinciaOSApr,#txtIdDistritoOSApr').val('');            
            if ($('#slctUnidSupeOSApr').val() != "") {
                common.empresaSupervisora.cargaDataUnidadOperativa($('#slctUnidSupeOSApr').val(),coOrSeOrSeApr.fillDataUnidadOperativa);
            }            
        });
	// change Serie (proceso,etapa,tramite)
	$('#slctIdProcesoOSApr').change(function() {
		fill.clean("#slctIdEtapaOSApr,#slctIdTramiteOSApr");
		common.serie.cargarEtapa("#slctIdProcesoOSApr", "#slctIdEtapaOSApr");
	});
	$('#slctIdEtapaOSApr').change(function() {
		fill.clean("#slctIdTramiteOSApr");
		common.serie.cargarTramite("#slctIdEtapaOSApr", "#slctIdTramiteOSApr");
	});
	// change empresa supervisora
	$('#slctTipoEmprSupeOSApr').change(function() {
            var idProceso = $("#slctIdProcesoOSApr").val();
            var idObligacionTipo = $("#cmbTipoSupervisionOSApr").val();
            var idRubro = $("#txtIdActividadApr").val();
            var idDepartamento = $("#txtIdDepartamentoOSApr").val();
            var idProvincia = $("#txtIdProvinciaOSApr").val();
            var idDistrito = $("#txtIdDistritoOSApr").val();
            fill.clean("#slctEmprSupeOSApr");
            if ($('#slctTipoEmprSupeOSApr').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
                    common.empresaSupervisora.cargarLocador('#slctEmprSupeOSApr', idObligacionTipo,idProceso,idRubro,idDepartamento,idProvincia, idDistrito);
            } else if ($('#slctTipoEmprSupeOSApr').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
                    common.empresaSupervisora.cargarSupervisoraEmpresa('#slctEmprSupeOSApr', idObligacionTipo,idProceso, idRubro, idDepartamento,idProvincia, idDistrito);
            }
        });
    	// aprobar
	$('#btnAprobarOrdenServicioApr').click(function() {
            
		coOrSeOrSeApr.fxAprobar.validaAprobarOS();
	});
	// observarJefe
	$('#btnObservarOSAprobarApr').click(function() {
		coOrSeOrSeApr.fxObservarAprobar.observarOS();
	});
	$('#btnConfirmaObservarOSAprobarApr').click(function() {
		coOrSeOrSeApr.fxObservarAprobar.validaObservarOS();
	});
	$('#btnRegistroSupervisionApr').click(function() {
            common.abrirSupervision(
                    $('#tipoAccionOSApr').val() == constant.accionOS.atender ? constant.modoSupervision.registro : constant.modoSupervision.consulta,
                    $('#txtIdOrdenServicioOSApr').val(),
                    $('#rolSesion').val(),
                    $('#slctTipoEmprSupeOSApr option:selected').text(),
                    $('#slctEmprSupeOSApr option:selected').text(), 
                    constant.estado.activo,
                    $('#txtIdExpedienteOSApr').val(),
                    $('#txtIdObligacionTipoApr').val(),
                    $('#txtIdProcesoApr').val(),
                    $('#txtIdActividadApr').val(),
                    /* OSINE791 - RSIS2 - Inicio */
                    $('#txtIteracionOSApr').val()
                    /* OSINE791 - RSIS2 - Fin */
                );
        });
        $('#txtMotivoObserOSApr').alphanum(constant.valida.alphanum.comentario);
	$('#cmbTipoSupervisionOSApr').change(function() {
            if($("#cmbTipoSupervisionOSApr").find(':checked').attr('codigo')>0){
                $('#subTipoOSApr').css('display','inline-block');
                common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSApr').val(),'#cmbSubTipoSupervisionOSApr');
            }else{
                $('#subTipoOSApr').css('display','none');
                fill.clean('#cmbSubTipoSupervisionOSApr');
            }
        });
    }
    
};

$(function() {
    coOrSeOrSeApr.inicializaObjetosEventos();
    
    coOrSeOrSeApr.fxAprobar.procBuscarTitularMineroOS('UnidadOperativaTitMinMAS');
    $('#slctUnidSupeOSApr').trigger('change');

    // cargar files del expediente
    if ($('#tipoAccionOSApr').val() != constant.accionOS.generar) {
        common.procesarGridFilesExpediente($('#txtNumeroExpedienteOSApr').val(),
        $('#txtIdOrdenServicioOSApr').val(), "FilesOrdenServicioApr", 
        $('#tipoAccionOSApr').val());
    }
    
    // arma visualizacion de valores para casos "consultar" y diferentes a generar
    coOrSeOrSeApr.armaConsultarFormOS();
    // arma subtipo supervision, casos tipo consulta y editar
    if($('#txtIdObligacionSubTipoOSApr').val()!=''){
        common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSApr').val(),'#cmbSubTipoSupervisionOSApr');
        $('#subTipoOSApr').css('display','inline-block');
        $('#cmbSubTipoSupervisionOSApr').val($('#txtIdObligacionSubTipoOSApr').val());
    }
    boton.closeDialog();
});