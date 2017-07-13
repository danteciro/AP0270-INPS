/**
* Resumen		
* Objeto		: ordenServicioConcluir.js
* Descripción		: ordenServicioConcluir
* Fecha de Creación	: 10/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                         |     Descripción
* =====================================================================================================================================================================
*    
*/

//common/OrdenServicio/OrdenServicioConcluir.js
var coOrSeOrSeConc = {
    armaConsultarFormOS : function() {
        if ($('#txtflagOrigenOSConc').val() == constant.flgOrigenExpediente.siged) {
            fill.clean("#slctIdEtapaOSConc,#slctIdTramiteOSConc");
            common.serie.cargarEtapa("#slctIdProcesoOSConc", "#slctIdEtapaOSConc");
            $('#slctIdEtapaOSConc').val($('#txtIdEtapaTrOSConc').val());
            $('#slctIdEtapaOSConc').trigger('change');
            $('#slctIdTramiteOSConc').val($('#txtIdTramiteOSConc').val());
        }
        // para datos de empresa supervisora
        if ($('#txtIdLocadorOSConc').val() != '' && $('#txtIdLocadorOSConc').val() != undefined) {
            $('#slctTipoEmprSupeOSConc').val($('#slctTipoEmprSupeOSConc').find('option[codigo="'+constant.empresaSupervisora.personaNatural+'"]').val())
            $('#slctTipoEmprSupeOSConc').trigger("change");
            $('#slctEmprSupeOSConc').val($('#txtIdLocadorOSConc').val());
        } else if ($('#txtIdSupervisoraEmpresaOSConc').val() != '' && $('#txtIdSupervisoraEmpresaOSConc').val() != undefined){
            $('#slctTipoEmprSupeOSConc').val($('#slctTipoEmprSupeOSConc').find('option[codigo="'+constant.empresaSupervisora.personaJuridica+'"]').val())
            $('#slctTipoEmprSupeOSConc').trigger("change");
            $('#slctEmprSupeOSConc').val($('#txtIdSupervisoraEmpresaOSConc').val());
        }
    },
    fxConcluir : {
        validaConcluirOS : function() {
            confirmer.open("¿Confirma que desea Concluir la Orden de Servicio?","coOrSeOrSeConc.fxConcluir.procesaConcluirOrdenServicio()");
        },
        procesaConcluirOrdenServicio : function() {
            loading.open();
            $.getJSON(baseURL + "pages/ordenServicio/concluir", {
                idExpediente : $('#txtIdExpedienteOSConc').val(),
                idOrdenServicio : $('#txtIdOrdenServicioOSConc').val(),
                idPersonalOri : $('#idPersonalSesion').val(),
                numeroExpediente : $('#txtNumeroExpedienteOSConc').val(),
                idArchivo : $('#slctOficioConc').val(), 
                fechaInicioPlazoDescargo : $('#fechaRecepcionConc').val()
            }).done(function(data) {
                loading.close();
                if (data.resultado == '0') {
                    $("#dialogOrdenServicio").dialog('close');
                    mensajeGrowl("success", constant.confirm.save, "");
                    // recargar grillas involucradas
                    espeNotiArchNotiArch.procesarGridArchivadoNotificado();
                    espeAsigAsig.procesarGridAsignacion();
                } else {
                    mensajeGrowl("error", data.mensaje, "");
                }
            }).fail(errorAjax);
        },
        cargarDocumentosOrdenServicio : function(idSlctDest) { 
            $.ajax({
                url : baseURL + "pages/archivo/findArchivoExpedienteOS",
                type : 'get',
                dataType : 'json',
                async : false,
                data : {
                    numeroExpediente : $('#txtNumeroExpedienteOSConc').val(),
                    idOrdenServicio : $('#txtIdOrdenServicioOSConc').val()
                },
                beforeSend : loading.open,
                success : function(data) {
                    loading.close();
                    var existe = false;
                    for (i = 0; i < data.ArchivoExpediente.length; i++) {
                        existe = false;
                        for (x = 0; x < data.MaestroPlazo.length; x++) {
                            if (data.ArchivoExpediente[i].idTipoDocumento.codigo == data.MaestroPlazo[x].codigo) {
                                existe = true;
                                break;
                            }
                        }
                        if (!existe) {
                            data.ArchivoExpediente.splice(i, 1);
                            i--;
                        }
                    }
                    fill.comboValorId(data.ArchivoExpediente,"idArchivo", "nombreArchivo", idSlctDest,"-1");
                    $("#divOficioFechaFinConc").dialog({
                        resizable : false,
                        draggable : true,
                        autoOpen : true,
                        height : "auto",
                        width : "auto",
                        modal : true,
                        dialogClass : 'dialog',
                        closeText : "Cerrar",
                        title:'FECHA RECEPCION USUARIO - MENSAJERIA',
                        close:function(){
                            $("#divOficioFechaFinConc").dialog("destroy");
                        }
                    });
                    $("#fechaRecepcionConc").val("");
                },
                error : function(jqXHR) {
                        errorAjax(jqXHR);
                }
            });
        } 
    },
    fillDataUnidadOperativa : function(data) {
        $('#txtRubroOSConc,#txtDireOperOSConc').val("");
        if (data != null && data.registro!=null) {
            $('#txtIdActividadConc').val(data.registro.idActividad);
            $('#txtRubroOSConc').val(data.registro.actividad);
            
            if(data.registro.direccionUnidadsupervisada.direccionCompleta!=null && data.registro.direccionUnidadsupervisada.direccionCompleta!=undefined){
                $('#txtDireOperOSConc').val(data.registro.direccionUnidadsupervisada.direccionCompleta);
                $('#txtIdDepartamentoOSConc').val(data.registro.direccionUnidadsupervisada.departamento.idDepartamento); 
                $('#txtIdProvinciaOSConc').val(data.registro.direccionUnidadsupervisada.provincia.idProvincia);
                $('#txtIdDistritoOSConc').val(data.registro.direccionUnidadsupervisada.distrito.idDistrito); 
            }
        }
    },
    inicializaObjetosEventos:function(){
        // Fecha
	$('#fechaRecepcionConc').datepicker();
        $("#fechaRecepcionConc").datepicker("option", "maxDate", new Date());
	$('#fechaRecepcionConc').change(function() {
            if ($('#fechaRecepcionConc').val() != '') {
                    var fecInicio = $(this).datepicker('getDate');
                    fecInicioFormat = new Date(fecInicio.getTime());
                    fecInicioFormat.setDate(fecInicioFormat.getDate());
                    $('#fechaRecepcionConc').val($('#fechaRecepcionConc').val());
            } else {
                    $('#fechaRecepcionConc').val('');
                    $("#fechaRecepcionConc").datepicker("option", "minDate",new Date(0, 0, 0, 0, 0, 0));
            }
        });

	// change UnidadOperativa
	$('#slctUnidSupeOSConc').change(function() {
            $('#txtIdActividadConc,#txtRubroOSConc,#txtDireOperOSConc,#txtIdDepartamentoOSConc,#txtIdProvinciaOSConc,#txtIdDistritoOSConc').val('');            
            if ($('#slctUnidSupeOSConc').val() != "") {
                common.empresaSupervisora.cargaDataUnidadOperativa($('#slctUnidSupeOSConc').val(),coOrSeOrSeConc.fillDataUnidadOperativa);
            }            
        });
	// change Serie (proceso,etapa,tramite)
	$('#slctIdProcesoOSConc').change(function() {
		fill.clean("#slctIdEtapaOSConc,#slctIdTramiteOSConc");
		common.serie.cargarEtapa("#slctIdProcesoOSConc", "#slctIdEtapaOSConc");
	});
	$('#slctIdEtapaOSConc').change(function() {
		fill.clean("#slctIdTramiteOSConc");
		common.serie.cargarTramite("#slctIdEtapaOSConc", "#slctIdTramiteOSConc");
	});
	// change empresa supervisora
	$('#slctTipoEmprSupeOSConc').change(function() {
            var idProceso = $("#slctIdProcesoOSConc").val();
            var idObligacionTipo = $("#cmbTipoSupervisionOSConc").val();
            var idRubro = $("#txtIdActividadConc").val();
            var idDepartamento = $("#txtIdDepartamentoOSConc").val();
            var idProvincia = $("#txtIdProvinciaOSConc").val();
            var idDistrito = $("#txtIdDistritoOSConc").val();
            fill.clean("#slctEmprSupeOSConc");
            if ($('#slctTipoEmprSupeOSConc').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
                    common.empresaSupervisora.cargarLocador('#slctEmprSupeOSConc', idObligacionTipo,idProceso,idRubro,idDepartamento,idProvincia, idDistrito);
            } else if ($('#slctTipoEmprSupeOSConc').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
                    common.empresaSupervisora.cargarSupervisoraEmpresa('#slctEmprSupeOSConc', idObligacionTipo,idProceso, idRubro, idDepartamento,idProvincia, idDistrito);
            }
        });
    	// concluir
	$('#btnConcluirOrdenServicioConc').click(function() {
		coOrSeOrSeConc.fxConcluir.cargarDocumentosOrdenServicio('#slctOficioConc');
	}); 
        $('#btnAceptarRecepcionConc').click(function() {
            if($('#frmConcluirOSConc').validateAllForm("#divMensajeValidaFrmConcluirOSConc")){
                $("#divOficioFechaFinConc").dialog("close");
                coOrSeOrSeConc.fxConcluir.validaConcluirOS();
            }   
        }); 
        
	$('#btnRegistroSupervisionConc').click(function() {
            common.abrirSupervision(
                    $('#tipoAccionOSConc').val() == constant.accionOS.atender ? constant.modoSupervision.registro : constant.modoSupervision.consulta,
                    $('#txtIdOrdenServicioOSConc').val(),
                    $('#rolSesion').val(),
                    $('#slctTipoEmprSupeOSConc option:selected').text(),
                    $('#slctEmprSupeOSConc option:selected').text(), 
                    constant.estado.activo,
                    $('#txtIdExpedienteOSConc').val(),
                    $('#txtIdObligacionTipoConc').val(),
                    $('#txtIdProcesoConc').val(),
                    $('#txtIdActividadConc').val(),
                    /* OSINE791 - RSIS2 - Inicio */
                    $('#txtIteracionOSConc').val()
                    /* OSINE791 - RSIS2 - Fin */
                );
        });
	
        $('#cmbTipoSupervisionOSConc').change(function() {
            if($("#cmbTipoSupervisionOSConc").find(':checked').attr('codigo')>0){
                $('#subTipoOSConc').css('display','inline-block');
                common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSConc').val(),'#cmbSubTipoSupervisionOSConc');
            }else{
                $('#subTipoOSConc').css('display','none');
                fill.clean('#cmbSubTipoSupervisionOSConc');
            }
        });
    }
};

$(function() {
    coOrSeOrSeConc.inicializaObjetosEventos();
    
    $('#slctUnidSupeOSConc').trigger('change');

    if ($('#tipoAccionOSConc').val() != constant.accionOS.generar) {
        common.procesarGridFilesExpediente($('#txtNumeroExpedienteOSConc').val(),
            $('#txtIdOrdenServicioOSConc').val(), "FilesOrdenServicioConc", 
            $('#tipoAccionOSConc').val());
    }
    
    // arma visualizacion de valores para casos "consultar" y diferentes a generar
    coOrSeOrSeConc.armaConsultarFormOS();
    // arma subtipo supervision, casos tipo consulta y editar
    if($('#txtIdObligacionSubTipoOSConc').val()!=''){
        common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSConc').val(),'#cmbSubTipoSupervisionOSConc');
        $('#subTipoOSConc').css('display','inline-block');
        $('#cmbSubTipoSupervisionOSConc').val($('#txtIdObligacionSubTipoOSConc').val());
    }
    boton.closeDialog();
});