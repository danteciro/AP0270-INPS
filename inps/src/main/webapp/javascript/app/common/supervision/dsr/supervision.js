/**
 * Resumen		
 * Objeto		: supervision.js
 * Descripción		: JavaScript donde se maneja las acciones de la pestaña supervisado del Supervisor para Devolver Asignaciones.
 * Fecha de Creación	: 
 * PR de Creación	: OSINE_SFS-480
 * Autor			: GMD
 * =====================================================================================================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |     Descripción
 * =====================================================================================================================================================================
 *OSINE791–RSIS05 | 17/08/2016    | Zosimo Chaupis Santur        | Crear la funcionalidad para registrar los datos iniciales de una supervisión de orden de supervisión DSR-CRITICIDAD de la casuística SUPERVISIÓN REALIZADA
* OSINE791–RSIS12 | 26/08/2016    | Zosimo Chaupis Santur        | Crear la funcionalidad para registrar una obligación como "POR VERIFICAR" en el registro de supervisión  de una orden de supervisión DSR-CRITICIDAD
* OSINE791-RSIS15 | 16/09/2016    |	Alexander Vilca Narvaez 	 | Validar si existen infracciones pendientes de finalizar atención  
*    
 */

//common/supervision/dsr/supervision.js
var coSupeDsrSupe = {
    //  OSINE791 - RSIS05 - Inicio -->
    OcultarTabs: function() {
        $("#tabRecepcionVisitaVer").hide();
        $("#tabObligacionesVer").hide();
        $("#tabOtrosIncumplientosVer").hide();
        $("#tabEjecucionMedidaVer").hide();
        $("#tabterminarSupervisionVer").hide();
    },
    VerTabs: function() {
        $("#tabRecepcionVisitaVer").show();
        $("#tabObligacionesVer").show();
        $("#tabOtrosIncumplientosVer").show();
        $("#tabEjecucionMedidaVer").show();
    },
    VerTabsIteracion: function() {
        $("#tabRecepcionVisitaVer").show();
       $("#tabObligacionesVer").show();
        $("#tabterminarSupervisionVer").show();
    },
    OcultarDivTabs: function() {
        // $("#tabRecepcionVisita").hide();
        // $("#tabObligaciones").hide();
        // $("#tabOtrosIncumplientos").hide();
        // $("#tabEjecucionMedida").hide();
    },
    VerDivTabs: function() {
        //$("#tabRecepcionVisita").show();
        // $("#tabObligaciones").show();
        // $("#tabOtrosIncumplientos").show();
        // $("#tabEjecucionMedida").show();
    },
    MostrarTab: function(idTabNuevo, idTabAnterior) {
        $('#tabsSupervision').tabs("enable", idTabNuevo);
        $('#tabsSupervision').tabs({active: idTabNuevo});
        $('#tabsSupervision').tabs("disable", idTabAnterior);
    },
    BloquearDatosIniciales: function() {
        $("#fechaInicioSP").attr('disabled', 'disabled');
        $("#horaInicioSP").attr('disabled', 'disabled');
        $("#radioSiSupervision").attr('disabled', 'disabled');
        $("#radioNoSupervision").attr('disabled', 'disabled');
        $("#radioPorVerificar").attr('disabled', 'disabled');    
    },
    BloquearAtencionVisita: function() {
        $("#chkIdentificaRS").attr('disabled', 'disabled');
        $('input[name=chkIdentifica]:checked').attr('checked',true);
        $("#slctTipoDocumentoRS").attr('disabled', 'disabled');
        $("#txtNumeroDocumentoRS").attr('disabled', 'disabled');
        $("#txtApellidoPaternoRS").attr('disabled', 'disabled');
        $("#txtApellidoMaternoRS").attr('disabled', 'disabled');
        $("#txtNombresRS").attr('disabled', 'disabled');
        $("#slctCargoAtiendeRS").attr('disabled', 'disabled');
        $("#txtCorreoElectronicoRS").attr('disabled', 'disabled');
        $("#txtObservacionPersonaAtiendeRS").attr('disabled', 'disabled');
    },
    BloquearObligacionDetalle: function() {
        $("#radioDsrOblDelIncumplimientoSi").attr('disabled', 'disabled');
        $("#btnSubsanar").css('display', 'none');
        $("#radioDsrOblDelIncumplimientoNo").attr('disabled', 'disabled');
        $("#radioDsrOblDelIncumplimientoPorVerificar").attr('disabled', 'disabled');
        $("#radioDsrOblDelIncumplimientoNoAplica").attr('disabled', 'disabled');
        $("#radioDsrOblDelIncumplimientoObstaculizado").attr('disabled', 'disabled');
        $("#DivcontainerEscenearioIncumplimiento").find('input,select,textarea').attr('disabled','disabled')
        $("#DivcontainerSinEscenario").find('input,select,textarea,checkbox').attr('disabled','disabled')
        $("#containerComentarioObstaculizado").find('input,select,textarea').attr('disabled','disabled')
        $("#txtComentarioEscenarioIncumplido").attr('disabled', 'disabled');
        $("#formRegSupervisionDsrOblDetIncumpCmt").find('input,select,textarea').attr('disabled','disabled')
        $("#DivcomentariosContainer").find('input,select,textarea').attr('disabled','disabled')
        //$("#btnAbrirRegistrarMedioProb").css('display','none');
        
        
    },
    BloquearObligacionDetalleComentarioSubsanacion: function() {
        $("#AreaComentarioSubsanacion").attr('disabled', 'disabled');
        $("#btnGuardarComentarioSubsanacion").css('display','none');
    },
    BloquearGrillaCheckBoxProductosSuspender : function(){
     $(".containerEscenearioProductoSuspendeer").find("input").attr("disabled","disabled");
    },
    BloquearOtrosIncumplimientos: function() {
        $("#txtOtrosIncumplimientos").attr('disabled', 'disabled');
    },
    BloquearEjecucionMedida: function() {
        $("#radioSiEjecMed").attr('disabled', 'disabled');
        $("#radioNoEjecMed").attr('disabled', 'disabled');
        $('input[name=radioEjecutaMedida]:checked').attr('checked',true);
        //$("#fechaTerminoRS").attr('disabled', 'disabled');
        //$("#horaTerminoRS").attr('disabled', 'disabled');
        $("#cartaVisita").attr('disabled', 'disabled');
        $("#txtComentario").attr('disabled', 'disabled');
        $("#txtComentarioEjecucionMedida").attr('disabled', 'disabled');
        $("#btnGuadarEmComentario").css('display','none');
        $("#btnGenerarResultados").val('Consultar Resultados');
    },
    //  OSINE791 - RSIS05 - Fin -->
    //  OSINE791 - RSIS12 - Inicio -->
     VerificarObstaculizados: function() {

        var idSupervision = $("#idSupervision").val();
        var mensajeValidacion = "";
        var url = baseURL + "pages/supervision/dsr/VerificarObstaculizados";
        $.ajax({
            url: url,
            type: 'post',
            async: false,
            data: {
                idSupervision: idSupervision
            },
            success: function(data) {
//            	loading.close();
                if (data.respuesta == 'ok') {
                    //mostrando escenarios
                } else {
                    return null;
                }
            },
            error: errorAjax
        });
    },
    
    
    ComprobarResultadoSupervisionDsr: function() {
        var idEstado = $("#EstadoResultadoSupervision").val();
        var modo = $("#modoSupervision").val();
        if(idEstado != '' && modo == constant.modoSupervision.registro){
            coSupeDsrSupe.BloquearDatosIniciales();
            coSupeDsrSupe.BloquearAtencionVisita();
            coSupeDsrSupe.BloquearObligacionDetalle();
            coSupeDsrSupe.BloquearObligacionDetalleComentarioSubsanacion();
            coSupeDsrSupe.BloquearOtrosIncumplimientos();
            coSupeDsrSupe.BloquearEjecucionMedida();
            coSupeDsrSupe.BloquearGrillaCheckBoxProductosSuspender();
            coSupeDsrSupe.BloquearObligacionDetalleComentarioSubsanacion();
            $("#radioDsrOblSubsanadoNo").attr("disabled","disable");
            $("#radioDsrOblSubsanadoSi").attr("disabled","disable");
            $("#cartaVisitaTerSup").attr("disabled","disable");
            
        }
    },
       ObtenerDireccionOperativaSupervisionDsr: function() {
        var direccion = $("#txtDireOperOSAte").val();
        var idDepa = $('#txtIdDepartamentoOSAte').val();
        var idProvi = $('#txtIdProvinciaOSAte').val();
        var idDistri = $('#txtIdDistritoOSAte').val();
        $('#DireccionOperativaObliDsrSup').val(direccion);
        $('#idDepartamentoObliDsrSup').val(idDepa);
        $('#idProvinciaObliDsrSup').val(idProvi);
        $('#idDistritoObliDsrSup').val(idDistri);           
    },
    ObtenerDireccionOperativaSupervisionDsrConsulta: function() {
        var direccion = $("#txtDireOperOSCon").val();
        var idDepa = $('#txtIdDepartamentoOSCon').val();
        var idProvi = $('#txtIdProvinciaOSCon').val();
        var idDistri = $('#txtIdDistritoOSCon').val();
        $('#DireccionOperativaObliDsrSup').val(direccion);
        $('#idDepartamentoObliDsrSup').val(idDepa);
        $('#idProvinciaObliDsrSup').val(idProvi);
        $('#idDistritoObliDsrSup').val(idDistri);
        
        if($('#DireccionOperativaObliDsrSup').val()=='' && $('#idDepartamentoObliDsrSup').val()=='' && 
        		$('#idProvinciaObliDsrSup').val()=='' && $('#idProvinciaObliDsrSup').val()=='' && 
        		$('#idDistritoObliDsrSup').val()==''){
        	var direccion = $("#txtDireOperOSApr").val();
            var idDepa = $('#txtIdDepartamentoOSApr').val();
            var idProvi = $('#txtIdProvinciaOSApr').val();
            var idDistri = $('#txtIdDistritoOSApr').val();
            $('#DireccionOperativaObliDsrSup').val(direccion);
            $('#idDepartamentoObliDsrSup').val(idDepa);
            $('#idProvinciaObliDsrSup').val(idProvi);
            $('#idDistritoObliDsrSup').val(idDistri);
        }     
    },    
    
    /* OSINE791 - RSIS15 - Inicio */
    validarCumplimiento: function(idSupervision,codigoResultadoSupervision){
    	var result="";
        var url = baseURL + "pages/detalleSupervision/findDetalleSupervision";
        $.ajax({
            url: url,
            type: 'post',
            async: false,
            data: {
                idSupervision: idSupervision,
                codigoResultadoSupervision: codigoResultadoSupervision
            },
            success: function(data) {
            	 result = data; 
            },
            error: errorAjax
        });
        return result;
    },
    
    validaComentariosRegistrados: function(){
        $.ajax({
            url: baseURL + "pages/supervision/dsr/validarExisteComentario",
            type:'get',
            async:false,
            data:{
                idSupervision: $('#idSupervision').val()
            } ,
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                if (data.existenComentarioRegistrados=='No'){
                     mensajeGrowl("warn","Aún existen infracciones pendientes de registrar comentario, verificar\n\<br>");
                } else{
                    if(coSupeDsrSupe.validaComentariosObstaculuzadosRegistrados()=='1'){
                        coSupeDsrSupe.MostrarTab(3, 2);
                    }         
                }
            },
            error:errorAjax
        });
        	
    }, 
    VerificarCumplimientos: function() {
        var idSupervision = $("#idSupervision").val();	
        var mensajeValidacion="Aún existen infracciones pendientes de finalizar atención, verificar\n\<br>"
        listaNoDefinidos = coSupeDsrSupe.validarCumplimiento(idSupervision,constant.codigoResultadoSupervision.noDefinido);
        if (listaNoDefinidos.length>0) {
        	 mensajeGrowl("warn",mensajeValidacion);
	} else {
            listaNoAplica = coSupeDsrSupe.validarCumplimiento(idSupervision,constant.codigoResultadoSupervision.porVerificar);
            if (listaNoAplica.length>0) {
                    mensajeGrowl("warn", mensajeValidacion)
            } else {	                    
                coSupeDsrSupe.validaComentariosRegistrados();
            }
        }
    },//aca fu
    VerificarCumplimientosSub: function() {
        var idSupervision = $("#idSupervision").val();	
        var mensajeValidacion="Aún existen infracciones pendientes de finalizar atención, verificar\n\<br>"
        listaNoDefinidos = coSupeDsrSupe.validarCumplimiento(idSupervision,constant.codigoResultadoSupervision.noDefinido);
        if (listaNoDefinidos.length>0) {
        	 mensajeGrowl("warn",mensajeValidacion);
	} else {
            	                    
                coSupeDsrSupe.MostrarTab(3, 2);
                coSuDsrTerminarSup.fxGenerarResultadoTerminarSupervision.cargarFlagInfraccionesTerminarSup();

        }
    }
    ,
            
     validaComentariosObstaculuzadosRegistrados: function(){
        var variable="";
        $.ajax({
        	url: baseURL + "pages/supervision/dsr/validarExisteComentarioObligacion",
                type:'get',
                async:false,
                data:{
                    idSupervision: $('#idSupervision').val()
                },
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    variable=data.resultado;
                    if (data.resultado=='0'){
                         mensajeGrowl("warn","Aún existen obligaciones pendientes de registrar comentario, verificar\n\<br>");
                    }            
                },
        	error:errorAjax
        });
        return variable;
    }
    /* OSINE791 - RSIS15 - Fin */
}

$(function() {
//  OSINE791 - RSIS05 - Inicio -->
    boton.closeDialog();
    $('#tabsSupervision').tabs();
    //  OSINE791 - RSIS05 - Fin -->
    coSupeDsrSupe.ComprobarResultadoSupervisionDsr();
    coSupeDsrSupe.ObtenerDireccionOperativaSupervisionDsr();
    var modosup = $("#modoSupervision").val();
    if(modosup == constant.modoSupervision.consulta){
        coSupeDsrSupe.ObtenerDireccionOperativaSupervisionDsrConsulta();
    }
});

