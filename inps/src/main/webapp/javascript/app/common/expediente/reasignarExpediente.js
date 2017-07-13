//common/expediente/reasignarExpediente.js
var coExReEx={
    validaGuardarReasExpe:function(){
        if($('#frmReasignarExpediente').validateAllForm("#divMensajeValidaRE")){
            confirmer.open("Confirma que desea reasignar el Expediente : " + $('#numeroExpediente').val(),"coExReEx.procesaReasignarExpediente()");
        }
    },
    procesaReasignarExpediente:function(){
        $.ajax({
            url:baseURL + "pages/expediente/derivar",
            type:'get',
            async:false,
            data:{
                idPersonalOri:$('#idPersonalSesion').val(),
                idPersonalDest:$('#slctIdPersonalDestRE').val(),
                cadIdExpedientes:$('#idExpedienteRE').val(),
                motivoReasignacion:$('#txtMotivoReasignacionRE').val()
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                if(data.resultado=="0"){
                    mensajeGrowl('success', 'Se reasignó satisfactoriamente el Expediente', '');
                    espeDeriDeri.procesarGridDerivado();
                    $("#dialogReasignarExpediente").dialog('close')
                }else{
                    mensajeGrowl('error', data.mensaje, '');
                }
            },
            error:errorAjax
        });
    }
};

$(function() {
    boton.closeDialog();
    $('#btnGuardarReasignarExpediente').click(function(){coExReEx.validaGuardarReasExpe();});
    //quitar el especialista logueado
    $('#slctIdPersonalDestRE').find('option').map(function(){
        if($(this).val()!=undefined && $(this).val()==$('#idPersonalSesion').val()){$(this).remove();}
    });
});