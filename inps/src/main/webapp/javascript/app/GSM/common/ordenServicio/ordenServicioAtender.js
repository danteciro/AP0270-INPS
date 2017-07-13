/**
* Resumen		
* Objeto			: ordenServicioAtender.js
* Descripción		: ordenServicioAtender
* Fecha de Creación	: 26/07/2016
* PR de Creación	: OSINE_SFS-1344
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-1344 |  26/10/2016   |   Giancarlo Villanueva A.    |     Adecuar funcionalidad INPS para la GSM
*/

//gsm/common/OrdenServicio/OrdenServicioAtender.js
var coOrSeOrSeAte = {
    armaConsultarFormOS : function() {
        if ($('#txtflagOrigenOSAte').val() == constant.flgOrigenExpediente.siged) {
                fill.clean("#slctIdEtapaOSAte,#slctIdTramiteOSAte");
                    common.serie.cargarEtapa("#slctIdProcesoOSAte", "#slctIdEtapaOSAte");
                $('#slctIdEtapaOSAte').val($('#txtIdEtapaTrOSAte').val());
                $('#slctIdEtapaOSAte').trigger('change');
                $('#slctIdTramiteOSAte').val($('#txtIdTramiteOSAte').val());
        }
    },
    fxAtender : {
		validaAtenderOS : function() {
			var idSupervision = '';
			var mensajeRespuesta = "";
			if ($('#txtFlagSupervisionAte').val() == constant.supervision.realiza) {
				idSupervision = coOrSeOrSeAte.fxAtender.validaExisteSupervision();
				if (idSupervision != null) {
					if (idSupervision != '') {
						datos = coOrSeOrSeAte.fxAtender.validaRegistroSupervision(idSupervision);                 
						if (datos != null) {
							mensajeRespuesta = datos.mensaje;
							if (datos.validoSupervision) {
								mensajeRespuesta = mensajeRespuesta == '' ? mensajeRespuesta
										: mensajeRespuesta + "<br>";
								confirmer
										.open(
												mensajeRespuesta
														+ "¿Confirma que desea cerrar la Orden de Servicio?",
												"coOrSeOrSeAte.fxAtender.procesaAtenderOrdenServicio()");
							} else {
								mensajeGrowl("warn", mensajeRespuesta);
							}
						}
					}
				}
			} else {
				confirmer.open(
						"¿Confirma que desea cerrar la Orden de Servicio?",
						"coOrSeOrSeAte.fxAtender.procesaAtenderOrdenServicio()");
			}
		},
		procesaAtenderOrdenServicio : function() {
			loading.open();
			$.getJSON(
					baseURL + "pages/ordenServicio/atender",
					{
						idExpediente : $('#txtIdExpedienteOSAte').val(),
						numeroExpediente : $('#txtNumeroExpedienteOSAte').val(),
						'ordenServicio.idOrdenServicio' : $(
								'#txtIdOrdenServicioOSAte').val(),
						idPersonalOri : $('#idPersonalSesion').val(),
						flagSupervision : $('#txtFlagSupervisionAte').val(),
						  /* OSINE791 RSIS42 - Inicio */
						nroIteracion : $('#txtIteracionOSAte').val(),
						  /* OSINE791 RSIS42 - Inicio */
						'empresaSupervisada.idEmpresaSupervisada' : $(
								'#txtIdEmpresaSupervisadaOSAte').val(),
						'flujoSiged.codigoSiged' : $('#slctIdFlujoSigedOSAte')
								.find(':checked').attr('codigoSiged'),
						asuntoSiged : $('#txtAsuntoSigedOSAte').val(),
                                                'obligacionTipo.idObligacionTipo':$('#txtIdObligacionTipoAte').val(),
                                                'unidadSupervisada.actividad.idActividad':$('#txtIdActividadAte').val(),
                                                'proceso.idProceso':$('#txtIdProcesoAte').val()
					}).done(function(data) {
				loading.close();
				if (data.resultado == '0') {
					$("#dialogOrdenServicio").dialog('close');
					mensajeGrowl("success", constant.confirm.save, "");
					// recargar grillas involucradas
					supeAsigAsig.procesarGridAsignacion();
				} else {
					mensajeGrowl("error", data.mensaje, "");
				}
			}).fail(errorAjax);
		},
		validaExisteSupervision : function() {
			var idSupervision = '';
			$.ajax({
				url : baseURL + "pages/supervision/findSupervision",
				type : 'post',
				async : false,
                                dataType:'json',
				data : {
					idOrdenServicio : $('#txtIdOrdenServicioOSAte').val()
				},
                                beforeSend:function(){loading.open();},
				success : function(data) {
					loading.close();
					if (data.resultado == '0') {
						if (data.supervision != null) {
							idSupervision = data.supervision.idSupervision;
						}
					} else {
						mensajeGrowl("error", data.mensaje, "");
						idSupervision = null;
					}
				},
                                error : function(jqXHR) {
                                        errorAjax(jqXHR);
                                }
			});
			return idSupervision;
		},
                validaRegistroSupervision : function(idSupervision) {
                	var datos = null;
			loading.open();
			$.ajax({
                            	url : baseURL + "pages/supervision/validarRegistroSupervision",
                            	type : 'post',
				async : false,
                                dataType:'json',
				data : {
                                    enPantalla : '0',
                                    idSupervision : idSupervision,
                                    'ordenServicioDTO.expediente.obligacionTipo.idObligacionTipo':$('#txtIdObligacionTipoAte').val(),
                                    'ordenServicioDTO.expediente.unidadSupervisada.actividad.idActividad':$('#txtIdActividadAte').val(),
                                    'ordenServicioDTO.expediente.proceso.idProceso':$('#txtIdProcesoAte').val()
				},
				success : function(data) {
					loading.close();
					if (data.resultado == '0') {
						datos = data;
					} else {
						mensajeGrowl("error", data.mensaje, "");
					}
				},
                                error : function(jqXHR) {
                                        errorAjax(jqXHR);
                                }
			});
			return datos;
		}
	},
	fillDataUnidadOperativa : function(data) {
            $('#txtRubroOSAte,#txtDireOperOSAte').val("");
            if (data != null && data.registro!=null) {
                $('#txtIdActividadAte').val(data.registro.idActividad);
                $('#txtRubroOSAte').val(data.registro.actividad);
                    
                if(data.registro.direccionUnidadsupervisada.direccionCompleta!=null && data.registro.direccionUnidadsupervisada.direccionCompleta!=undefined){
                    $('#txtDireOperOSAte').val(data.registro.direccionUnidadsupervisada.direccionCompleta);
                    $('#txtIdDepartamentoOSAte').val(data.registro.direccionUnidadsupervisada.departamento.idDepartamento); 
                    $('#txtIdProvinciaOSAte').val(data.registro.direccionUnidadsupervisada.provincia.idProvincia);
                    $('#txtIdDistritoOSAte').val(data.registro.direccionUnidadsupervisada.distrito.idDistrito); 
                }
            }
	},
    inicializaObjetosEventos:function(){
    /* OSINE_SFS-1344 - Inicio */	
    // change UnidadOperativa
	$('#slctUnidSupeOSAte').change(function() {
            $('#txtIdActividadAte,#txtRubroOSAte,#txtDireOperOSAte,#txtIdDepartamentoOSAte,#txtIdProvinciaOSAte,#txtIdDistritoOSAte').val('');            
            if ($('#slctUnidSupeOSAte').val() != "") {
                common.empresaSupervisora.cargaDataUnidadOperativa($('#slctUnidSupeOSAte').val(),coOrSeOrSeAte.fillDataUnidadOperativa);
            }
        });
	/* OSINE_SFS-1344 - Fin */	
	// change Serie (proceso,etapa,tramite)
	$('#slctIdProcesoOSAte').change(function() {
		fill.clean("#slctIdEtapaOSAte,#slctIdTramiteOSAte");
		common.serie.cargarEtapa("#slctIdProcesoOSAte", "#slctIdEtapaOSAte");
	});
	$('#slctIdEtapaOSAte').change(function() {
		fill.clean("#slctIdTramiteOSAte");
		common.serie.cargarTramite("#slctIdEtapaOSAte", "#slctIdTramiteOSAte");
	});
	// atender
	$('#btnAtenderOrdenServicioAte').click(function() {
		coOrSeOrSeAte.fxAtender.validaAtenderOS();
	});
        
	$('#btnSubirDocumentoAte').click(
			function() {
				common.abrirSubirDocumento($('#txtNumeroExpedienteOSAte').val(),
						$('#txtIdOrdenServicioOSAte').val(), $(
								'#txtIdEmpresaSupervisadaOSAte').val(), $(
								'#txtIdExpedienteOSAte').val(), $(
								'#txtAsuntoSigedOSAte').val(), $(
								'#slctIdFlujoSigedOSAte').find(':checked').attr(
								'codigoSiged'));
			});
	$('#btnRegistroSupervisionAte').click(function() {
            common.abrirSupervision(
                    $('#tipoAccionOSAte').val() == constant.accionOS.atender ? constant.modoSupervision.registro : constant.modoSupervision.consulta,
                    $('#txtIdOrdenServicioOSAte').val(),
                    $('#rolSesion').val(),
                    '',
                    '', 
                    constant.estado.activo,
                    $('#txtIdExpedienteOSAte').val(),
                    $('#txtIdObligacionTipoAte').val(),
                    $('#txtIdProcesoAte').val(),
                    $('#txtIdActividadAte').val(),
                    /* OSINE791 - RSIS2 - Inicio */
                    $('#txtIteracionOSAte').val()
                    /* OSINE791 - RSIS2 - Fin */
                );
        });
        $('#txtMotivoObserOSAte').alphanum(constant.valida.alphanum.comentario);
	$('#cmbTipoSupervisionOSAte').change(function() {
            if($("#cmbTipoSupervisionOSAte").find(':checked').attr('codigo')>0){
                    $('#subTipoOSAte').css('display','inline-block');
                    common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSAte').val(),'#cmbSubTipoSupervisionOSAte');
            }else{
                    $('#subTipoOSAte').css('display','none');
                    fill.clean('#cmbSubTipoSupervisionOSAte');
            }
        });
    }
};

$(function() {
    coOrSeOrSeAte.inicializaObjetosEventos();
    
    $('#slctUnidSupeOSAte').trigger('change');
    
    // cargar files del expediente
    /* OSINE_SFS-1344 - Inicio */	
    common.procesarGridFilesExpediente($('#txtNumeroExpedienteOSAte').val(),
        $('#txtIdOrdenServicioOSAte').val(), "FilesOrdenServicioAte", 
        $('#tipoAccionOSAte').val());
    /* OSINE_SFS-1344 - Fin */	
    // arma visualizacion de valores para casos "consultar" y diferentes a generar
    coOrSeOrSeAte.armaConsultarFormOS();
    // arma subtipo supervision, casos tipo consulta y editar
    if($('#txtIdObligacionSubTipoOSAte').val()!=''){
        common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSAte').val(),'#cmbSubTipoSupervisionOSAte');
        $('#subTipoOSAte').css('display','inline-block');
        $('#cmbSubTipoSupervisionOSAte').val($('#txtIdObligacionSubTipoOSAte').val());
    }
    boton.closeDialog();
});