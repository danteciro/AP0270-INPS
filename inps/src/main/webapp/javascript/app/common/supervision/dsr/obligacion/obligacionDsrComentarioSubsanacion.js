/**
 * Resumen		
 * Objeto		: obligacionDsrComentarioSubsanacion.js
 * Descripción		: supervision
 * Fecha de Creación	: 01/09/2016
 * PR de Creación	: OSINE_SFS-791
 * Autor			: GMD
 * =====================================================================================================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |     Descripción
 * =====================================================================================================================================================================
 *OSINE791–RSIS14 |01/09/2016     | Zosimo Chaupis Santur        | Considerar la funcionalidad para subsanar una obligación marcada como "INCUMPLIDA" de una supervisión  de orden de supervisión DSR-CRITICIDAD
 ***/

//common/supervision/dsr/obligacion/obligacionDsrComentarioSubsanacion.js


//  OSINE791 - RSIS14 - Inicio -->
var coSupeDsrOblComentSubsa = {
    initForm: function() {

        $("#btnCerrarComentarioSubsanacion").click(function() {
            $("#dialogComentarioSubsanacion").dialog("close");
        });
        $("#btnGuardarComentarioSubsanacion").click(function() {
            var mensajeValida = '';
            if ($('#containerComentarioSubsanacion').validateAllForm('#divMensajeValidaFrmSup')) {
                mensajeValida = coSupeDsrOblComentSubsa.validarComentarioSubsanacionSP();
                if (mensajeValida == '') {
                    if($('#flagEditComenSubsa').val()==constant.estado.activo){
                        confirmer.open('¿Confirma que desea registrar el comentario para la subsanaci&oacute;n del incumplimiento?', 'coSupeDsrOblComentSubsa.editarDsrComentarioSubsanacion()');
                    }else{
                        confirmer.open('¿Confirma que desea registrar el comentario para la subsanaci&oacute;n del incumplimiento?', 'coSupeDsrOblComentSubsa.RegistrarDsrComentarioSubsanacion()');
                    }                    
                } else {
                    mensajeGrowl('warn', mensajeValida);
                }
            }
            
        });
    },
    validarComentarioSubsanacionSP: function() {
        var mensajeValidacio = "";
        if ($('#AreaComentarioSubsanacion').val().trim() != '' && $('#AreaComentarioSubsanacion').val().trim().length < 8) {
            $('#AreaComentarioSubsanacion').addClass('error');
            mensajeValidacio = "El comentario para la subsanaci&oacute;n del incumplimiento debe tener una longitud mayor o igual a 8 caracteres, corregir<br>";
            return mensajeValidacio;
        }
        return mensajeValidacio;
    },
    CargarDataComentarioSubsanacion: function() {
        var comentario = $("#ObliDsrComentarioSubsanacion").val().toUpperCase();
        $("#AreaComentarioSubsanacion").val(comentario);
    },
    RegistrarDsrComentarioSubsanacion: function() {
        //  OSINE791 - RSIS14 - Fin -->
        loading.open();
        var idActResultadoSupervision = $("#idResultadoObligacionDetSupActual").val();
        var comentario = $("#AreaComentarioSubsanacion").val().toUpperCase();
        var parameters = "_=p";
        parameters += "&idDetalleSupervision=" + $('#oblDsrDetIdDetalleSupervision').val();
        parameters += "&estadoIncumplimiento=" + $(".oblDsrRadiosIncumplimiento input:radio[name='radioDsrOblDelIncumplimiento']:checked").val();
        parameters += "&comentarioObstaculizado=" + comentario;
        parameters += "&idResultadoSupervisionAct=" + idActResultadoSupervision;

        $.ajax({
            url: baseURL + "pages/supervision/dsr/guardarDsrObligacion",
            type: 'post',
            async: false,
            data: parameters,
            success: function(data) {
                loading.close();
                if (data.resultado == '0') {
                    mensajeGrowl("success", constant.confirm.save);
                    $("#ObliDsrComentarioSubsanacion").val(comentario);
                    $("#AreaComentarioSubsanacion").val("");
                    $("#dialogComentarioSubsanacion").dialog("close");
                } else {
                    mensajeGrowl("error", data.mensaje);
                }
            },
            error: errorAjax
        });
    },
    editarDsrComentarioSubsanacion:function(){
        var parameters = "_=p";
        parameters += "&idDetalleSupervision=" + $('#oblDsrDetIdDetalleSupervision').val();
        parameters += "&comentarioObstaculizado=" + $("#AreaComentarioSubsanacion").val().toUpperCase();

        $.ajax({
            url: baseURL + "pages/supervision/dsr/editarDsrComentarioSubsanacion",
            type: 'post',
            async: false,
            data: parameters,
            beforeSend:loading.open,
            success: function(data) {
                loading.close();
                if (data.resultado == '0') {
                    mensajeGrowl("success", constant.confirm.save);
                    $("#dialogComentarioSubsanacion").dialog("close");
                } else {
                    mensajeGrowl("error", data.mensaje);
                }
            },
            error: errorAjax
        });
    }
}
$(function() {
    coSupeDsrOblComentSubsa.initForm();
    coSupeDsrOblComentSubsa.CargarDataComentarioSubsanacion();
    coSupeDsrSupe.ComprobarResultadoSupervisionDsr();
    boton.closeDialog();
});
//  OSINE791 - RSIS14 - Fin -->