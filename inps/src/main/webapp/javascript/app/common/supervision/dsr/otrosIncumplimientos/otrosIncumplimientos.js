/**
* Resumen		
* Objeto		: otrosIncumplimientos.js
* Descripción		: JavaScript donde se maneja las acciones de la pestaña otrosIncumplimientos.
* Fecha de Creación	: 22/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  22/08/2016   |   Luis García Reyna          |     Crear la funcionalidad para registrar otros incumplimientos
*
*    
*/

//common/supervision/dsr/otrosIncumplimientos/otrosIncumplimientos.js

var coSuDsrOiOi={
    
    fxRegistraOI:{
        
        validaRegistroOI:function(){       
            
            if(($('#txtOtrosIncumplimientos').val() == '' && $('#datoTxtOtrosIncumplimientos').val().toUpperCase().trim() =='') || $('#datoTxtOtrosIncumplimientos').val().toUpperCase().trim() == $("#txtOtrosIncumplimientos").val().toUpperCase().trim()){
                coSupeDsrSupe.MostrarTab(4, 3);
            }else if($('#txtOtrosIncumplimientos').val().trim() != '' && $('#datoTxtOtrosIncumplimientos').val().toUpperCase().trim()==''){
                if($('#txtOtrosIncumplimientos').val().trim().length > 7){
                    confirmer.open("¿Confirma que desea registrar otros incumplimientos?","coSuDsrOiOi.fxRegistraOI.procesaRegistroOI()");           
                }else{
                    $('#txtOtrosIncumplimientos').addClass('error');
                    mensajeGrowl("warn", "Los otros incumplimientos de la supervisi&oacute;n deben tener una longitud mayor o igual a 8 caracteres, corregir", "");
                }
            }else if ($('#datoTxtOtrosIncumplimientos').val().toUpperCase().trim() != $("#txtOtrosIncumplimientos").val().toUpperCase().trim() || $('#txtOtrosIncumplimientos').val().trim() == ''){
                if($('#txtOtrosIncumplimientos').val().trim().length > 7){
                    confirmer.open("¿Confirma que desea actualizar otros incumplimientos?","coSuDsrOiOi.fxRegistraOI.procesaRegistroOI()");          
                }else{
                    if($("#txtOtrosIncumplimientos").val().toUpperCase().trim()!=''){
                        $('#txtOtrosIncumplimientos').addClass('error');
                        mensajeGrowl("warn", "Los otros incumplimientos de la supervisi&oacute;n deben tener una longitud mayor o igual a 8 caracteres, corregir", "");
                    }else{
                    confirmer.open("¿Confirma que desea actualizar otros incumplimientos?","coSuDsrOiOi.fxRegistraOI.procesaRegistroOI()");          
                    }
                }
            }
            
        },
        
        procesaRegistroOI : function (){
	$.ajax({
            url:baseURL + "pages/supervision/dsr/registroOtrosIncumplimientos",
            type:'post',
            async:false,
            data:{  
                idSupervision : $('#idSupervision').val(),
                idOrdenServicio : $('#idOrdenServicioRS').val(),
                otrosIncumplimientos :$('#txtOtrosIncumplimientos').val().trim().toUpperCase()
            },
            beforeSend:loading.open(),
            success:function(data){
                loading.close();              
                if(data.resultado=='0'){
                    coSupeDsrSupe.MostrarTab(4, 3);
                    if($('#txtOtrosIncumplimientos').val().trim() != '' && $('#datoTxtOtrosIncumplimientos').val().toUpperCase().trim()==''){
                        mensajeGrowl("success", constant.confirm.save, "");
                    }else if($('#datoTxtOtrosIncumplimientos').val().toUpperCase().trim() != $("#txtOtrosIncumplimientos").val().toUpperCase().trim() || $('#txtOtrosIncumplimientos').val().trim() == ''){
                        mensajeGrowl("success", constant.confirm.edit, "");
                    }
                }else{
                    mensajeGrowl("error", data.mensaje, "");
                }      
                $("#datoTxtOtrosIncumplimientos").val($('#txtOtrosIncumplimientos').val().toUpperCase());               
            },
            error:errorAjax
	});
        }
    },

    inicializaObjetosEventos:function(){
        $('#btnSiguiente').click(
            function(){coSuDsrOiOi.fxRegistraOI.validaRegistroOI();
            coSuDsrEmEm.cargarFlagInfracciones();
            coSuDsrEmEm.cargarFlagObstaculizados();
        });
        $('#btnAnteriorIn').click(function(){coSupeDsrSupe.MostrarTab(2, 3);});
        
    }   
    
};

$(function() {
    coSuDsrOiOi.inicializaObjetosEventos();
});