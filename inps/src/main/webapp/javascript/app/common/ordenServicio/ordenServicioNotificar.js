/**
* Resumen		
* Objeto		: ordenServicioNotificar.js
* Descripción		: ordenServicioNotificar
* Fecha de Creación	: 09/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                         |     Descripción
* =====================================================================================================================================================================
*    
*/

//common/OrdenServicio/OrdenServicioNotificar.js
var coOrSeOrSeNot = {
    armaConsultarFormOS : function() {
        if ($('#txtflagOrigenOSNot').val() == constant.flgOrigenExpediente.siged) {
                fill.clean("#slctIdEtapaOSNot,#slctIdTramiteOSNot");
                    common.serie.cargarEtapa("#slctIdProcesoOSNot", "#slctIdEtapaOSNot");
                $('#slctIdEtapaOSNot').val($('#txtIdEtapaTrOSNot').val());
                $('#slctIdEtapaOSNot').trigger('change');
                $('#slctIdTramiteOSNot').val($('#txtIdTramiteOSNot').val());
        }
        // para datos de empresa supervisora
        if ($('#txtIdLocadorOSNot').val() != '' && $('#txtIdLocadorOSNot').val() != undefined) {
            $('#slctTipoEmprSupeOSNot').val($('#slctTipoEmprSupeOSNot').find('option[codigo="'+constant.empresaSupervisora.personaNatural+'"]').val())
            $('#slctTipoEmprSupeOSNot').trigger("change");
            $('#slctEmprSupeOSNot').val($('#txtIdLocadorOSNot').val());
        } else if ($('#txtIdSupervisoraEmpresaOSNot').val() != '' && $('#txtIdSupervisoraEmpresaOSNot').val() != undefined){
            $('#slctTipoEmprSupeOSNot').val($('#slctTipoEmprSupeOSNot').find('option[codigo="'+constant.empresaSupervisora.personaJuridica+'"]').val())
            $('#slctTipoEmprSupeOSNot').trigger("change");
            $('#slctEmprSupeOSNot').val($('#txtIdSupervisoraEmpresaOSNot').val());
        }
    },
    fxNotificar : {
        validaNotificarOS : function() {
            $.ajax({
                url:baseURL + "pages/bandeja/abrirEnviarMensajeria", 
                type:'get',
                async:false,
                data:{
                    idExpediente:$('#txtIdExpedienteOSNot').val(),
                    numeroExpediente:$('#txtNumeroExpedienteOSNot').val(),
                    'ordenServicio.idOrdenServicio':$('#txtIdOrdenServicioOSNot').val(),
                    flagNotificar:1
                },
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    $("#dialogEnviarMensajeria").html(data);
                    $("#dialogEnviarMensajeria").dialog({
                        resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height:"auto",
                        width: "auto",
                        modal: true,
                        dialogClass: 'dialog',
                        title: "ENVÍO DE MENSAJERIA",
                        close:function(){
                                            $("#dlgCliente").dialog("destroy");
                                    }
                    });
                    coOrSeOrSeEnMe.autoComplete();
                },
                error : errorAjax
            });
        },
        procesaNotificarOrdenServicio : function() {
            loading.open();
            $.getJSON(baseURL + "pages/ordenServicio/notificar", {
                    idExpediente : $('#txtIdExpedienteOSNot').val(),
                    idOrdenServicio : $('#txtIdOrdenServicioOSNot').val(),
                    idPersonalOri : $('#idPersonalSesion').val()
            }).done(function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                            $("#dialogOrdenServicio").dialog('close');
                            // recargar grillas involucradas
                            espeEvalEval.procesarGridEvaluacion();
                            espeNotiArchNotiArch.procesarGridArchivadoNotificado();
                    } else {
                            mensajeGrowl("error", data.mensaje, "");
                    }
            }).fail(errorAjax);
        }
    },
    fillDataUnidadOperativa : function(data) {
        $('#txtRubroOSNot,#txtDireOperOSNot').val("");
        /* OSINE_SFS-480 - RSIS 11 - Inicio */
        if (data != null && data.registro!=null) {
            $('#txtIdActividadNot').val(data.registro.idActividad);
            $('#txtRubroOSNot').val(data.registro.actividad);
            $('#txtIdUnidadSupervisadaOS').val(data.registro.idUnidadSupervisada);

            if(data.registro.direccionUnidadsupervisada.direccionCompleta!=null && data.registro.direccionUnidadsupervisada.direccionCompleta!=undefined){
                $('#txtDireOperOSNot').val(data.registro.direccionUnidadsupervisada.direccionCompleta);
                $('#txtIdDepartamentoOSNot').val(data.registro.direccionUnidadsupervisada.departamento.idDepartamento); 
                $('#txtIdProvinciaOSNot').val(data.registro.direccionUnidadsupervisada.provincia.idProvincia);
                $('#txtIdDistritoOSNot').val(data.registro.direccionUnidadsupervisada.distrito.idDistrito); 
            }
        }
        /* OSINE_SFS-480 - RSIS 11 - Fin */
    },	
    inicializaObjetosEventos:function(){
        // change UnidadOperativa
	$('#slctUnidSupeOSNot').change(function() {
            $('#txtIdActividadNot,#txtRubroOSNot,#txtDireOperOSNot,#txtIdDepartamentoOSNot,#txtIdProvinciaOSNot,#txtIdDistritoOSNot').val('');            
            if ($('#slctUnidSupeOSNot').val() != "") {
                common.empresaSupervisora.cargaDataUnidadOperativa($('#slctUnidSupeOSNot').val(),coOrSeOrSeNot.fillDataUnidadOperativa);
            }           
        });
	// change Serie (proceso,etapa,tramite)
	$('#slctIdProcesoOSNot').change(function() {
		fill.clean("#slctIdEtapaOSNot,#slctIdTramiteOSNot");
		common.serie.cargarEtapa("#slctIdProcesoOSNot", "#slctIdEtapaOSNot");
	});
	$('#slctIdEtapaOSNot').change(function() {
		fill.clean("#slctIdTramiteOSNot");
		common.serie.cargarTramite("#slctIdEtapaOSNot", "#slctIdTramiteOSNot");
	});
	// change empresa supervisora
	$('#slctTipoEmprSupeOSNot').change(function() {
            var idProceso = $("#slctIdProcesoOSNot").val();
            var idObligacionTipo = $("#cmbTipoSupervisionOSNot").val();
            var idRubro = $("#txtIdActividadNot").val();
            var idDepartamento = $("#txtIdDepartamentoOSNot").val();
            var idProvincia = $("#txtIdProvinciaOSNot").val();
            var idDistrito = $("#txtIdDistritoOSNot").val();
            fill.clean("#slctEmprSupeOSNot");
            if ($('#slctTipoEmprSupeOSNot').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
                    common.empresaSupervisora.cargarLocador('#slctEmprSupeOSNot', idObligacionTipo,idProceso,idRubro,idDepartamento,idProvincia, idDistrito);
            } else if ($('#slctTipoEmprSupeOSNot').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
                    common.empresaSupervisora.cargarSupervisoraEmpresa('#slctEmprSupeOSNot', idObligacionTipo,idProceso, idRubro, idDepartamento,idProvincia, idDistrito);
            }
        });
        // notificar
        $('#btnNotificarOrdenServicioNot').click(function() {
            coOrSeOrSeNot.fxNotificar.validaNotificarOS();
        });
        $('#btnRegistroSupervisionNot').click(function() {
            common.abrirSupervision(
                    $('#tipoAccionOSNot').val() == constant.accionOS.atender ? constant.modoSupervision.registro : constant.modoSupervision.consulta,
                    $('#txtIdOrdenServicioOSNot').val(),
                    $('#rolSesion').val(),
                    $('#slctTipoEmprSupeOSNot option:selected').text(),
                    $('#slctEmprSupeOSNot option:selected').text(), 
                    constant.estado.activo,
                    $('#txtIdExpedienteOSNot').val(),
                    $('#txtIdObligacionTipoNot').val(),
                    $('#txtIdProcesoNot').val(),
                    $('#txtIdActividadNot').val(),
                    /* OSINE791 - RSIS2 - Inicio */
                    $('#txtIteracionOSNot').val()
                    /* OSINE791 - RSIS2 - Fin */
                );
        });
	$('#cmbTipoSupervisionOSNot').change(function() {
            if($("#cmbTipoSupervisionOSNot").find(':checked').attr('codigo')>0){
                    $('#subTipoOSNot').css('display','inline-block');
                    common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSNot').val(),'#cmbSubTipoSupervisionOSNot');
            }else{
                    $('#subTipoOSNot').css('display','none');
                    fill.clean('#cmbSubTipoSupervisionOSNot');
            }
        });
    }
};

$(function() {
    coOrSeOrSeNot.inicializaObjetosEventos();
    
    $('#slctUnidSupeOSNot').trigger('change');

    // cargar files del expediente
    common.procesarGridFilesExpediente($('#txtNumeroExpedienteOSNot').val(),
        $('#txtIdOrdenServicioOSNot').val(), "FilesOrdenServicioNot", 
        $('#tipoAccionOSNot').val());
    
    // arma visualizacion de valores para casos "consultar" y diferentes a generar
    coOrSeOrSeNot.armaConsultarFormOS();
    // arma subtipo supervision, casos tipo consulta y editar
    if($('#txtIdObligacionSubTipoOSNot').val()!=''){
        common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSNot').val(),'#cmbSubTipoSupervisionOSNot');
        $('#subTipoOSNot').css('display','inline-block');
        $('#cmbSubTipoSupervisionOSNot').val($('#txtIdObligacionSubTipoOSNot').val());
    }
    boton.closeDialog();
});