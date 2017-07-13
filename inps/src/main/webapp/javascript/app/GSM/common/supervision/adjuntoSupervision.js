/**
* Resumen
* Objeto            : adjuntoSupervision.js
* Descripción       : JavaScript donde se maneja las acciones de la pestaña adjuntoSupervision.
* Fecha de Creación : 06/10/2016
* PR de Creación    : OSINE_SFS-791
* Autor             : GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  06/10/2016   |   Luis García Reyna          |     Registrar Supervisión No Iniciada
*/

//common/supervision/adjuntoSupervision.js
//variables globales
var modoDA;
var coSupeAdjuSupe={
    
    inicializaObjetosEventos: function () {
        $("#fileDASuper").change(function(){      
            $("#file_nameDASuper").val(quitaSlashDir($("#fileDASuper").val())); 
        });
        $('#btnAgregarAdjSuper').click(function() {
            coSupeAdjuSupe.validarAgregarDocumentoAdjunto();
        });
        //Formularios
        $("#formDocumentoAdjuntoSuper").ajaxForm({
            beforeSubmit: function () {
                loading.open();
                console.log("beforeSubmit");
            },
            dataType: 'json',
            resetForm: true,
            success: function(data) {
                loading.close();
                $("#dialogAgregarAdjunto").dialog('close');
                if (data != null && data.error != null) {
                    coSupeAdjuSupe.validaCargaArchivo(data);}
                },
            error: errorAjax
        });           
    },
    
    validarAgregarDocumentoAdjunto:function(){
        $('#txtDescripcionDASuper,#file_nameDASuper').removeClass('error');
        var mensajeValidacion = '';

        if($('#formDocumentoAdjuntoSuper').validateAllForm('#divMensajeFrmDocumentoAdjuntoSuper')){
            if(mensajeValidacion==''){
                mensajeValidacion = coSupeAdjuSupe.validarArchivoPermitido($('#fileDASuper').val());
                if(mensajeValidacion==''){
                    mensajeValidacion = coSupeAdjuSupe.validarDocumentoAdjuntoGrilla($('#txtDescripcionDASuper').val(),$('#fileDASuper').val());  
                    if(mensajeValidacion==''){confirmer.open('¿Confirma que desea agregar el archivo?', 'coSupeAdjuSupe.registrarDocumentoAdjunto()');}
                }
            }
            if(mensajeValidacion!=''){mensajeGrowl('warn', mensajeValidacion);}
        }
    },
        
    validarArchivoPermitido:function(nombreArchivo){
        var mensajeValidacion = "";
        var extensionPermitida=false;
        var extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf("."),nombreArchivo.length).toUpperCase();
        loading.open();
        var url = baseURL + "pages/supervision/findTiposArchivo";
        $.ajax({
            url: url,
            type: 'post',
            async: false,
            data: {},
            success: function(data) {
                loading.close();
                if(data.resultado=='0'){
                    if(data.listaTipoArchivo!=undefined && data.listaTipoArchivo.length>0){
                        for(var i=0;i<data.listaTipoArchivo.length;i++){
                            var archivo = data.listaTipoArchivo[i];
                            if(archivo.codigo.toUpperCase()==extensionArchivo){extensionPermitida=true;}
                        }
                        if(!extensionPermitida){mensajeValidacion = "El formato de archivo indicado no esta permitido cargar en el sistema, corregir <br>";}
                    }
                }else{
                    mensajeGrowl('error', data.mensaje);
                    return null;
                }
            },
        error: errorAjax
        });
        return mensajeValidacion;
    },
        
    validarDocumentoAdjuntoGrilla:function(descripcion,nombreArchivo){
        var mensajeValidacion = "";
        loading.open();
        var url = baseURL + "pages/archivo/listaPghDocumentoAdjunto";
        $.ajax({
            url: url,
            type: 'post',
            async: false,
            data: {
                idSupervision : $('#idSupervisionDA').val(),
                nombreDocumento : nombreArchivo.toUpperCase()
            },
            success: function(data) {
                loading.close();
                if(data.resultado=='0'){
                    if(data.listaDocumentoAdjunto!=undefined && data.listaDocumentoAdjunto.length>0){
                        mensajeValidacion = "No se puede agregar el mismo archivo a la lista, corregir <br>";
                    }
                }else{
                    mensajeGrowl('error', data.mensaje);
                    return null;
                }
            },
            error: errorAjax
        });
        return mensajeValidacion;
    },

    registrarDocumentoAdjunto:function (){
        $("#formDocumentoAdjuntoSuper").submit();
    },
    validaCargaArchivo:function(data) {
        if (data.error){
            mensajeGrowl("warn", "Advertencia:", data.mensaje);}
        else{
            mensajeGrowl("success", constant.confirm.save);
            $( "[id^='gridAdjuntarArchivosSupervision']" ).trigger('reloadGrid');
        }
    }
        
}

$(function() {
    boton.closeDialog();
    coSupeAdjuSupe.inicializaObjetosEventos();
});