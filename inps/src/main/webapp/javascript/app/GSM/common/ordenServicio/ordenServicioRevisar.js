/**
* Resumen		
* Objeto		: ordenServicioRevisar.js
* Descripción		: ordenServicioRevisar
* Fecha de Creación	: 05/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                         |     Descripción
* =====================================================================================================================================================================
*    
*/

//common/OrdenServicio/OrdenServicioRevisar.js
var coOrSeOrSeRev = {
    armaConsultarFormOS : function() {
            if ($('#txtflagOrigenOSRev').val() == constant.flgOrigenExpediente.siged) {
                    fill.clean("#slctIdEtapaOSRev,#slctIdTramiteOSRev");
            		common.serie.cargarEtapa("#slctIdProcesoOSRev", "#slctIdEtapaOSRev");
                    $('#slctIdEtapaOSRev').val($('#txtIdEtapaTrOSRev').val());
                    $('#slctIdEtapaOSRev').trigger('change');
                    $('#slctIdTramiteOSRev').val($('#txtIdTramiteOSRev').val());
            }
            // para datos de empresa supervisora
            if ($('#txtIdLocadorOSRev').val() != '' && $('#txtIdLocadorOSRev').val() != undefined) {
                $('#slctTipoEmprSupeOSRev').val($('#slctTipoEmprSupeOSRev').find('option[codigo="'+constant.empresaSupervisora.personaNatural+'"]').val())
                $('#slctTipoEmprSupeOSRev').trigger("change");
                $('#slctEmprSupeOSRev').val($('#txtIdLocadorOSRev').val());
            } else if ($('#txtIdSupervisoraEmpresaOSRev').val() != '' && $('#txtIdSupervisoraEmpresaOSRev').val() != undefined){
                $('#slctTipoEmprSupeOSRev').val($('#slctTipoEmprSupeOSRev').find('option[codigo="'+constant.empresaSupervisora.personaJuridica+'"]').val())
                $('#slctTipoEmprSupeOSRev').trigger("change");
                $('#slctEmprSupeOSRev').val($('#txtIdSupervisoraEmpresaOSRev').val());
            }
    },fxRevisar : {
        validaRevisarOS : function() {
                confirmer.open("¿Desea dar conformidad a la Orden de Servicio?","coOrSeOrSeRev.fxRevisar.procesaRevisarOrdenServicio()");
        },
        procesaRevisarOrdenServicio : function() {
                loading.open();
                $.getJSON(baseURL + "pages/ordenServicio/revisar", {
                        idExpediente : $('#txtIdExpedienteOSRev').val(),
                        idOrdenServicio : $('#txtIdOrdenServicioOSRev').val(),
                        idPersonalOri : $('#idPersonalSesion').val(),
                        numeroExpediente : $('#txtNumeroExpedienteOSRev').val(),
                        asuntoSiged : $('#txtAsuntoSigedOSRev').val()
                }).done(function(data) {
                        loading.close();
                        if (data.resultado == '0') {
                                $("#dialogOrdenServicio").dialog('close');
                                mensajeGrowl("success", constant.confirm.save, "");
                                // recargar grillas involucradas
                                espeEvalEval.procesarGridEvaluacion();
                        } else {
                                mensajeGrowl("error", data.mensaje, "");
                        }
                }).fail(errorAjax);
        }
    },
    fxObservar : {
            observarOS : function() {
                    $('#divFormOSRev').hide("blind");
                    $('#btnObservarOSRev,#btnsAccPrincRev').hide();
                    $('#formObservaRev,#btnConfirmaObservarOSRev').show();
            },
            validaObservarOS : function() {
                if ($('#frmObservarOSRev').validateAllForm("#divMensajeValidaObservaRev")) {
                    confirmer.open("¿Confirma que desea Observar/Devolver la Orden de Servicio?","coOrSeOrSeRev.fxObservar.procesaObservarOrdenServicio()");
                }
            },
            procesaObservarOrdenServicio : function() {
                $.ajax({
                        url : baseURL + "pages/ordenServicio/observar",
                        type : 'post',
                        async : false,
                        dataType : 'json',
                        data : {
                                idExpediente : $('#txtIdExpedienteOSRev').val(),
                                idOrdenServicio : $('#txtIdOrdenServicioOSRev').val(),
                                idPersonalOri : $('#idPersonalSesion').val(),
                                motivoReasignacion : $('#txtMotivoObserOSRev').val(),
                                asuntoSiged : $('#txtAsuntoSigedOSRev').val(),
                                numeroExpediente : $('#txtNumeroExpedienteOSRev').val()
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
            $('#txtRubroOSRev,#txtDireOperOSRev').val("");
            if (data != null && data.registro!=null) {
                $('#txtIdActividadRev').val(data.registro.idActividad);
                $('#txtRubroOSRev').val(data.registro.actividad);
                    
                if(data.registro.direccionUnidadsupervisada.direccionCompleta!=null && data.registro.direccionUnidadsupervisada.direccionCompleta!=undefined){
                    $('#txtDireOperOSRev').val(data.registro.direccionUnidadsupervisada.direccionCompleta);
                    $('#txtIdDepartamentoOSRev').val(data.registro.direccionUnidadsupervisada.departamento.idDepartamento); 
                    $('#txtIdProvinciaOSRev').val(data.registro.direccionUnidadsupervisada.provincia.idProvincia);
                    $('#txtIdDistritoOSRev').val(data.registro.direccionUnidadsupervisada.distrito.idDistrito); 
                }                
            }
	},
    inicializaObjetosEventos:function(){
        // change UnidadOperativa
	$('#slctUnidSupeOSRev').change(function() {
            $('#txtIdActividadRev,#txtRubroOSRev,#txtDireOperOSRev,#txtIdDepartamentoOSRev,#txtIdProvinciaOSRev,#txtIdDistritoOSRev').val('');            
            if ($('#slctUnidSupeOSRev').val() != "") {
                common.empresaSupervisora.cargaDataUnidadOperativa($('#slctUnidSupeOSRev').val(),coOrSeOrSeRev.fillDataUnidadOperativa);
            }            
        });
	// change Serie (proceso,etapa,tramite)
	$('#slctIdProcesoOSRev').change(function() {
		fill.clean("#slctIdEtapaOSRev,#slctIdTramiteOSRev");
		common.serie.cargarEtapa("#slctIdProcesoOSRev", "#slctIdEtapaOSRev");
	});
	$('#slctIdEtapaOSRev').change(function() {
		fill.clean("#slctIdTramiteOSRev");
		common.serie.cargarTramite("#slctIdEtapaOSRev", "#slctIdTramiteOSRev");
	});
	// change empresa supervisora
	$('#slctTipoEmprSupeOSRev').change(function() {
            var idProceso = $("#slctIdProcesoOSRev").val();
            var idObligacionTipo = $("#cmbTipoSupervisionOSRev").val();
            var idRubro = $("#txtIdActividadRev").val();
            var idDepartamento = $("#txtIdDepartamentoOSRev").val();
            var idProvincia = $("#txtIdProvinciaOSRev").val();
            var idDistrito = $("#txtIdDistritoOSRev").val();
            fill.clean("#slctEmprSupeOSRev");
            if ($('#slctTipoEmprSupeOSRev').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
                    common.empresaSupervisora.cargarLocador('#slctEmprSupeOSRev', idObligacionTipo,idProceso,idRubro,idDepartamento,idProvincia, idDistrito);
            } else if ($('#slctTipoEmprSupeOSRev').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
                    common.empresaSupervisora.cargarSupervisoraEmpresa('#slctEmprSupeOSRev', idObligacionTipo,idProceso, idRubro, idDepartamento,idProvincia, idDistrito);
            }
        });
    	// revisar
	$('#btnRevisarOrdenServicioRev').click(function() {
		coOrSeOrSeRev.fxRevisar.validaRevisarOS();
	});
	// observar
	$('#btnObservarOSRev').click(function() {
		coOrSeOrSeRev.fxObservar.observarOS();
	});
	$('#btnConfirmaObservarOSRev').click(function() {
		coOrSeOrSeRev.fxObservar.validaObservarOS();
	});
	$('#btnRegistroSupervisionRev').click(function() {
            common.abrirSupervision(
                    $('#tipoAccionOSRev').val() == constant.accionOS.atender ? constant.modoSupervision.registro : constant.modoSupervision.consulta,
                    $('#txtIdOrdenServicioOSRev').val(),
                    $('#rolSesion').val(),
                    $('#slctTipoEmprSupeOSRev option:selected').text(),
                    $('#slctEmprSupeOSRev option:selected').text(), 
                    constant.estado.activo,
                    $('#txtIdExpedienteOSRev').val(),
                    $('#txtIdObligacionTipoRev').val(),
                    $('#txtIdProcesoRev').val(),
                    $('#txtIdActividadRev').val(),
                    /* OSINE791 - RSIS2 - Inicio */
                    $('#txtIteracionOSRev').val()
                    /* OSINE791 - RSIS2 - Fin */
                );
        });
	$('#cmbTipoSupervisionOSRev').change(function() {
            if($("#cmbTipoSupervisionOSRev").find(':checked').attr('codigo')>0){
                    $('#subTipoOSRev').css('display','inline-block');
                    common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSRev').val(),'#cmbSubTipoSupervisionOSRev');
            }else{
                    $('#subTipoOSRev').css('display','none');
                    fill.clean('#cmbSubTipoSupervisionOSRev');
            }
        });
    }
};

$(function() {
    coOrSeOrSeRev.inicializaObjetosEventos();
    
    $('#slctUnidSupeOSRev').trigger('change');

    // cargar files del expediente
    if ($('#tipoAccionOSRev').val() != constant.accionOS.generar) {
        common.procesarGridFilesExpediente($('#txtNumeroExpedienteOSRev').val(),
            $('#txtIdOrdenServicioOSRev').val(), "FilesOrdenServicioRev", 
            $('#tipoAccionOSRev').val());
    }
    
    // arma visualizacion de valores para casos "consultar" y diferentes a generar
    coOrSeOrSeRev.armaConsultarFormOS();
    // arma subtipo supervision, casos tipo consulta y editar
    if($('#txtIdObligacionSubTipoOSRev').val()!=''){
        common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSRev').val(),'#cmbSubTipoSupervisionOSRev');
        $('#subTipoOSRev').css('display','inline-block');
        $('#cmbSubTipoSupervisionOSRev').val($('#txtIdObligacionSubTipoOSRev').val());
    }
    boton.closeDialog();
});