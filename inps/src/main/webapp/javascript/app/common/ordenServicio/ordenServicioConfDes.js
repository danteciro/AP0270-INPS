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

//common/OrdenServicio/OrdenServicioConfDes.js
var coOrSeOrSeConfDes = {
    armaConsultarFormOS : function() {
        if ($('#txtflagOrigenOSConfDes').val() == constant.flgOrigenExpediente.siged) {
            fill.clean("#slctIdEtapaOSConfDes,#slctIdTramiteOSConfDes");
            common.serie.cargarEtapa("#slctIdProcesoOSConfDes", "#slctIdEtapaOSConfDes");
            $('#slctIdEtapaOSConfDes').val($('#txtIdEtapaTrOSConfDes').val());
            $('#slctIdEtapaOSConfDes').trigger('change');
            $('#slctIdTramiteOSConfDes').val($('#txtIdTramiteOSConfDes').val());
        }
        // para datos de empresa supervisora
        if ($('#txtIdLocadorOSConfDes').val() != '' && $('#txtIdLocadorOSConfDes').val() != undefined) {
            $('#slctTipoEmprSupeOSConfDes').val($('#slctTipoEmprSupeOSConfDes').find('option[codigo="'+constant.empresaSupervisora.personaNatural+'"]').val())
            $('#slctTipoEmprSupeOSConfDes').trigger("change");
            $('#slctEmprSupeOSConfDes').val($('#txtIdLocadorOSConfDes').val());
        } else if ($('#txtIdSupervisoraEmpresaOSConfDes').val() != '' && $('#txtIdSupervisoraEmpresaOSConfDes').val() != undefined){
            $('#slctTipoEmprSupeOSConfDes').val($('#slctTipoEmprSupeOSConfDes').find('option[codigo="'+constant.empresaSupervisora.personaJuridica+'"]').val())
            $('#slctTipoEmprSupeOSConfDes').trigger("change");
            $('#slctEmprSupeOSConfDes').val($('#txtIdSupervisoraEmpresaOSConfDes').val());
        }
    },
    fxConfirmarDescargo: {
        validaConfirmarDescargoOS: function() {
            confirmer
                    .open(
                    "¿Confirma que se realizaron los Descargos respectivos?",
                    "coOrSeOrSeConfDes.fxConfirmarDescargo.procesaConfirmarDescargoOrdenServicio()");
        },
        procesaConfirmarDescargoOrdenServicio: function() {
            loading.open();
            $.getJSON(baseURL + "pages/ordenServicio/confirmarDescargo", {
                idExpediente: $('#txtIdExpedienteOSConfDes').val(),
                idOrdenServicio: $('#txtIdOrdenServicioOSConfDes').val(),
                idPersonalOri: $('#idPersonalSesion').val(),
                numeroExpediente: $('#txtNumeroExpedienteOSConfDes').val()
            }).done(function(data) {
                loading.close();
                if (data.resultado == '0') {
                    $("#dialogOrdenServicio").dialog('close');
                    mensajeGrowl("success", constant.confirm.save, "");
                    // recargar grillas involucradas
                    espeNotiArchNotiArch.procesarGridArchivadoNotificado();
                    espeDeriDeri.procesarGridDerivado();
                } else {
                    mensajeGrowl("error", data.mensaje, "");
                }
            }).fail(errorAjax);
        }
    },
    fillDataUnidadOperativa : function(data) {
        $('#txtRubroOSConfDes,#txtDireOperOSConfDes').val("");
        if (data != null && data.registro!=null) {
            $('#txtIdActividadConfDes').val(data.registro.idActividad);
            $('#txtRubroOSConfDes').val(data.registro.actividad);
            
            if(data.registro.direccionUnidadsupervisada.direccionCompleta!=null && data.registro.direccionUnidadsupervisada.direccionCompleta!=undefined){
                $('#txtDireOperOSConfDes').val(data.registro.direccionUnidadsupervisada.direccionCompleta);
                $('#txtIdDepartamentoOSConfDes').val(data.registro.direccionUnidadsupervisada.departamento.idDepartamento); 
                $('#txtIdProvinciaOSConfDes').val(data.registro.direccionUnidadsupervisada.provincia.idProvincia);
                $('#txtIdDistritoOSConfDes').val(data.registro.direccionUnidadsupervisada.distrito.idDistrito); 
            }
        }
    },
    inicializaObjetosEventos:function(){
        // Fecha
	$('#fechaRecepcionConfDes').datepicker();
        $("#fechaRecepcionConfDes").datepicker("option", "maxDate", new Date());
	$('#fechaRecepcionConfDes').change(function() {
            if ($('#fechaRecepcionConfDes').val() != '') {
                    var fecInicio = $(this).datepicker('getDate');
                    fecInicioFormat = new Date(fecInicio.getTime());
                    fecInicioFormat.setDate(fecInicioFormat.getDate());
                    $('#fechaRecepcionConfDes').val($('#fechaRecepcionConfDes').val());
            } else {
                    $('#fechaRecepcionConfDes').val('');
                    $("#fechaRecepcionConfDes").datepicker("option", "minDate",new Date(0, 0, 0, 0, 0, 0));
            }
        });

	// change UnidadOperativa
	$('#slctUnidSupeOSConfDes').change(function() {
            $('#txtIdActividadConfDes,#txtRubroOSConfDes,#txtDireOperOSConfDes,#txtIdDepartamentoOSConfDes,#txtIdProvinciaOSConfDes,#txtIdDistritoOSConfDes').val('');            
            if ($('#slctUnidSupeOSConfDes').val() != "") {
                common.empresaSupervisora.cargaDataUnidadOperativa($('#slctUnidSupeOSConfDes').val(),coOrSeOrSeConfDes.fillDataUnidadOperativa);
            }            
        });
	// change Serie (proceso,etapa,tramite)
	$('#slctIdProcesoOSConfDes').change(function() {
		fill.clean("#slctIdEtapaOSConfDes,#slctIdTramiteOSConfDes");
		common.serie.cargarEtapa("#slctIdProcesoOSConfDes", "#slctIdEtapaOSConfDes");
	});
	$('#slctIdEtapaOSConfDes').change(function() {
		fill.clean("#slctIdTramiteOSConfDes");
		common.serie.cargarTramite("#slctIdEtapaOSConfDes", "#slctIdTramiteOSConfDes");
	});
	// change empresa supervisora
	$('#slctTipoEmprSupeOSConfDes').change(function() {
            var idProceso = $("#slctIdProcesoOSConfDes").val();
            var idObligacionTipo = $("#cmbTipoSupervisionOSConfDes").val();
            var idRubro = $("#txtIdActividadConfDes").val();
            var idDepartamento = $("#txtIdDepartamentoOSConfDes").val();
            var idProvincia = $("#txtIdProvinciaOSConfDes").val();
            var idDistrito = $("#txtIdDistritoOSConfDes").val();
            fill.clean("#slctEmprSupeOSConfDes");
            if ($('#slctTipoEmprSupeOSConfDes').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
                    common.empresaSupervisora.cargarLocador('#slctEmprSupeOSConfDes', idObligacionTipo,idProceso,idRubro,idDepartamento,idProvincia, idDistrito);
            } else if ($('#slctTipoEmprSupeOSConfDes').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
                    common.empresaSupervisora.cargarSupervisoraEmpresa('#slctEmprSupeOSConfDes', idObligacionTipo,idProceso, idRubro, idDepartamento,idProvincia, idDistrito);
            }
        });
    	// confirmardescargo
        $('#btnConfirmarDescargoOSConfDes').click(function() {
            coOrSeOrSeConfDes.fxConfirmarDescargo.validaConfirmarDescargoOS();
        });
        
	$('#btnRegistroSupervisionConfDes').click(function() {
            common.abrirSupervision(
                    $('#tipoAccionOSConfDes').val() == constant.accionOS.atender ? constant.modoSupervision.registro : constant.modoSupervision.consulta,
                    $('#txtIdOrdenServicioOSConfDes').val(),
                    $('#rolSesion').val(),
                    $('#slctTipoEmprSupeOSConfDes option:selected').text(),
                    $('#slctEmprSupeOSConfDes option:selected').text(), 
                    constant.estado.activo,
                    $('#txtIdExpedienteOSConfDes').val(),
                    $('#txtIdObligacionTipoConfDes').val(),
                    $('#txtIdProcesoConfDes').val(),
                    $('#txtIdActividadConfDes').val(),
                    $('#txtIteracionOSConfDes').val()
                );
        });
	
        $('#cmbTipoSupervisionOSConfDes').change(function() {
            if($("#cmbTipoSupervisionOSConfDes").find(':checked').attr('codigo')>0){
                $('#subTipoOSConfDes').css('display','inline-block');
                common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSConfDes').val(),'#cmbSubTipoSupervisionOSConfDes');
            }else{
                $('#subTipoOSConfDes').css('display','none');
                fill.clean('#cmbSubTipoSupervisionOSConfDes');
            }
        });
    }
};

$(function() {
    coOrSeOrSeConfDes.inicializaObjetosEventos();
    
    $('#slctUnidSupeOSConfDes').trigger('change');

    common.procesarGridFilesExpediente($('#txtNumeroExpedienteOSConfDes').val(),
        $('#txtIdOrdenServicioOSConfDes').val(), "FilesOrdenServicioConfDes", 
        $('#tipoAccionOSConfDes').val());
    
    // arma visualizacion de valores para casos "consultar" y diferentes a generar
    coOrSeOrSeConfDes.armaConsultarFormOS();
    // arma subtipo supervision, casos tipo consulta y editar
    if($('#txtIdObligacionSubTipoOSConfDes').val()!=''){
        common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSConfDes').val(),'#cmbSubTipoSupervisionOSConfDes');
        $('#subTipoOSConfDes').css('display','inline-block');
        $('#cmbSubTipoSupervisionOSConfDes').val($('#txtIdObligacionSubTipoOSConfDes').val());
    }
    boton.closeDialog();
});