//common/unidadOperativa/unidadOperativa.js
var coUnOpUnOp={
    validaAsignarUnidadOperativa:function(){
        if($('#frmAsignarUO').validateAllForm("")){
            confirmer.open("¿Confirma que desea Asignar Unidad Operativa al Expediente?","coUnOpUnOp.procesaAsignarUnidadOperativa()");
        }
    },
    procesaAsignarUnidadOperativa:function(){
        $.ajax({
            url:baseURL + "pages/expediente/asignarUnidadSupervisada",
            type:'get',
            async:false,
            data:$('#frmAsignarUO').serialize(),
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                if(data.resultado=="0"){
                    $('#dialogUnidadOperativa').dialog('close');
                    respReceRece.procesarGridExpeResponsable();
                }else{
                    mensajeGrowl('error', data.mensaje, '');
                }                
            },
            error:errorAjax
        });
    },
    cargaDataUnidadOperativa:function(fill){
        $.ajax({
            url:baseURL + "pages/unidadSupervisada/cargaDataUnidadOperativa",
            type:'get',
            async:false,
            data:{
                cadIdUnidadSupervisada:$('#slctUnidSupe').val()
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                fill(data);
            },
            error:errorAjax
        });
    },
    fillDataUnidadOperativa:function(data){
        $('#txtRubroUO,#txtDireOperUO').val("");
        if(data!=null){
            $('#txtRubroUO').val(data.registro.actividad);
            $('#txtDireOperUO').val(data.registro.direccion);
        }
    }
};

$(function() {
    boton.closeDialog();
    $('#btnAsignarUnidadOperativa').click(function(){coUnOpUnOp.validaAsignarUnidadOperativa();});
    $('#btnCrearUnidOper').click(function(){common.abrirMantUnidadOperativa("new",$('#txtRucUO').val().replace('RUC - ',''),$('#txtRazonSocialUO').val(),$('#idEmpresaSupervisada').val());});
    
    $('#slctUnidSupe').change(function(){
        $('#txtRubroUO,#txtDireOperUO').val("");
        if($('#slctUnidSupe').val()!=""){
            coUnOpUnOp.cargaDataUnidadOperativa(coUnOpUnOp.fillDataUnidadOperativa);
        }    
    });
    $('#slctUnidSupe').trigger('change');
});