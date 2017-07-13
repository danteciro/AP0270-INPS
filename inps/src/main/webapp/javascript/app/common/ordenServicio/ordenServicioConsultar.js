/**
* Resumen		
* Objeto		: ordenServicioConsultar.js
* Descripción		: ordenServicioConsultar
* Fecha de Creación	: 02/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: Julio Piro Gonzales
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                         |     Descripción
* =====================================================================================================================================================================
*    
*/

//common/OrdenServicio/OrdenServicioConsultar.js
var coOrSeOrSeCon = {
    armaConsultarFormOS : function() {
        if ($('#txtflagOrigenOSCon').val() == constant.flgOrigenExpediente.siged) {
            fill.clean("#slctIdEtapaOSCon,#slctIdTramiteOSCon");
                common.serie.cargarEtapa("#slctIdProcesoOSCon", "#slctIdEtapaOSCon");
            $('#slctIdEtapaOSCon').val($('#txtIdEtapaTrOSCon').val());
            $('#slctIdEtapaOSCon').trigger('change');
            $('#slctIdTramiteOSCon').val($('#txtIdTramiteOSCon').val());
        }
        // para datos de empresa supervisora
        if ($('#txtIdLocadorOSCon').val() != '' && $('#txtIdLocadorOSCon').val() != undefined) {
            $('#slctTipoEmprSupeOSCon').val($('#slctTipoEmprSupeOSCon').find('option[codigo="'+constant.empresaSupervisora.personaNatural+'"]').val())
            $('#slctTipoEmprSupeOSCon').trigger("change");
            $('#slctEmprSupeOSCon').val($('#txtIdLocadorOSCon').val());
        } else if ($('#txtIdSupervisoraEmpresaOSCon').val() != '' && $('#txtIdSupervisoraEmpresaOSCon').val() != undefined){
            $('#slctTipoEmprSupeOSCon').val($('#slctTipoEmprSupeOSCon').find('option[codigo="'+constant.empresaSupervisora.personaJuridica+'"]').val())
            $('#slctTipoEmprSupeOSCon').trigger("change");
            $('#slctEmprSupeOSCon').val($('#txtIdSupervisoraEmpresaOSCon').val());
        }
    },
    fillDataUnidadOperativa : function(data) {
        $('#txtRubroOSCon,#txtDireOperOSCon').val("");
        if (data != null && data.registro!=null) {
            $('#txtIdActividadCon').val(data.registro.idActividad);
            $('#txtRubroOSCon').val(data.registro.actividad);
            
            if(data.registro.direccionUnidadsupervisada.direccionCompleta!=null && data.registro.direccionUnidadsupervisada.direccionCompleta!=undefined){
                $('#txtDireOperOSCon').val(data.registro.direccionUnidadsupervisada.direccionCompleta);
                $('#txtIdDepartamentoOSCon').val(data.registro.direccionUnidadsupervisada.departamento.idDepartamento); 
                $('#txtIdProvinciaOSCon').val(data.registro.direccionUnidadsupervisada.provincia.idProvincia);
                $('#txtIdDistritoOSCon').val(data.registro.direccionUnidadsupervisada.distrito.idDistrito); 
            }           
        }
    },
    inicializaObjetosEventos:function(){
	// change UnidadOperativa
	$('#slctUnidSupeOSCon').change(function() {
            $('#txtIdActividadCon,#txtRubroOSCon,#txtDireOperOSCon,#txtIdDepartamentoOSCon,#txtIdProvinciaOSCon,#txtIdDistritoOSCon').val('');            
            if ($('#slctUnidSupeOSCon').val() != "") {
                common.empresaSupervisora.cargaDataUnidadOperativa($('#slctUnidSupeOSCon').val(),coOrSeOrSeCon.fillDataUnidadOperativa);
            }
        });
	// change Serie (proceso,etapa,tramite)
	$('#slctIdProcesoOSCon').change(function() {
		fill.clean("#slctIdEtapaOSCon,#slctIdTramiteOSCon");
		common.serie.cargarEtapa("#slctIdProcesoOSCon", "#slctIdEtapaOSCon");
	});
	$('#slctIdEtapaOSCon').change(function() {
		fill.clean("#slctIdTramiteOSCon");
		common.serie.cargarTramite("#slctIdEtapaOSCon", "#slctIdTramiteOSCon");
	});
	// change empresa supervisora
	$('#slctTipoEmprSupeOSCon').change(function() {
            var idProceso = $("#slctIdProcesoOSCon").val();
            var idObligacionTipo = $("#cmbTipoSupervisionOSCon").val();
            var idRubro = $("#txtIdActividadCon").val();
            var idDepartamento = $("#txtIdDepartamentoOSCon").val();
            var idProvincia = $("#txtIdProvinciaOSCon").val();
            var idDistrito = $("#txtIdDistritoOSCon").val();
            fill.clean("#slctEmprSupeOSCon");
            if ($('#slctTipoEmprSupeOSCon').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
                    common.empresaSupervisora.cargarLocador('#slctEmprSupeOSCon', idObligacionTipo,idProceso,idRubro,idDepartamento,idProvincia, idDistrito);
            } else if ($('#slctTipoEmprSupeOSCon').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
                    common.empresaSupervisora.cargarSupervisoraEmpresa('#slctEmprSupeOSCon', idObligacionTipo,idProceso, idRubro, idDepartamento,idProvincia, idDistrito);
            }
        });
        $('#btnRegistroSupervisionCon').click(function() {
            common.abrirSupervision(
                $('#tipoAccionOSCon').val() == constant.accionOS.atender ? constant.modoSupervision.registro : constant.modoSupervision.consulta,
                $('#txtIdOrdenServicioOSCon').val(),
                $('#rolSesion').val(),
                $('#slctTipoEmprSupeOSCon option:selected').text(),
                $('#slctEmprSupeOSCon option:selected').text(), 
                constant.estado.activo,
                $('#txtIdExpedienteOSCon').val(),
                $('#txtIdObligacionTipoCon').val(),
                $('#txtIdProcesoCon').val(),
                $('#txtIdActividadCon').val(),
                /* OSINE791 - RSIS2 - Inicio */
                $('#txtIteracionOSCon').val()
                /* OSINE791 - RSIS2 - Fin */
            );
        });
        $('#cmbTipoSupervisionOSCon').change(function() {
                if($("#cmbTipoSupervisionOSCon").find(':checked').attr('codigo')>0){
                        $('#subTipoOSCon').css('display','inline-block');
                        common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSCon').val(),'#cmbSubTipoSupervisionOSCon');
                }else{
                        $('#subTipoOSCon').css('display','none');
                        fill.clean('#cmbSubTipoSupervisionOSCon');
                }
        });
    }
};

$(function(){
    coOrSeOrSeCon.inicializaObjetosEventos();    
    $('#slctUnidSupeOSCon').trigger('change');
    // cargar files del expediente
    common.procesarGridFilesExpediente($('#txtNumeroExpedienteOSCon').val(),
            $('#txtIdOrdenServicioOSCon').val(), "FilesOrdenServicioCon", 
            $('#tipoAccionOSCon').val());
    // arma visualizacion de valores para casos "consultar" y diferentes a generar
    coOrSeOrSeCon.armaConsultarFormOS();
    // arma subtipo supervision, casos tipo consulta y editar
    if($('#txtIdObligacionSubTipoOSCon').val()!=''){
        common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSCon').val(),'#cmbSubTipoSupervisionOSCon');
        $('#subTipoOSCon').css('display','inline-block');
        $('#cmbSubTipoSupervisionOSCon').val($('#txtIdObligacionSubTipoOSCon').val());
    }
    boton.closeDialog();
});