/**
* Resumen          
* Objeto           : general.js
* Descripción             : JavaScript donde se maneja las acciones generales del Inps.
* Fecha de Creación       :  
 * PR de Creación   : OSINE_SFS-480
* Autor                   : GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |   19/05/2016  |    Hernán Torres Saenz       |     Adecuar  interfaz para la nueva forma de generación de órdenes de servicio (masivo).
* OSINE_SFS-480  |   20/05/2016  |    Hernán Torres Saenz       |     Crear la opción "Anular orden de servicio" en la pestaña asigaciones  de la bandeja del especialista el cual direccionará a la interfaz "Anular orden de servicio"
* OSINE_791      |   25/08/2016  |    Cristopher Paucar Torre   |     Adecuar la bandeja Notificado para el flujo DSR.       
 *                |   26/08/2016  |    Gullet Alvites Pisco      |     Adecuar la bandeja Evaluacion para el flujo DSR.
* OSINE_SFS-1063 |   27/09/2016  |    Victoria Ubaldo Gamarra   |     Nuevas constantes para bandeja rechazo carga
* OSINE_SFS-791  |  06/10/2016   |   Luis García Reyna          |     Registrar Supervisión No Iniciada
* OSINE_SFS-791  |   07/10/2016  |    Mario Dioses Fernandez    |     Crear la funcionalidad "verificar levantamientos" que permita verificar el tipo de asignación para la orden de levantamiento DSR-CRITICIDAD.
* OSINE_791-RSIS34| 10/10/2016   |   Cristopher Paucar Torre    |     Editar Orden de Servicio "Cambiar tipo de Asignación".
*/

var baseURL;
var id;
var caracteres = "abcdefghijklmnopqrstuvwxyzñÑABCDEFGHIJKLMNOPQRSTUVWXYZáéíóú ";
var numeros = "0123456789";
var numeros_caracteres = numeros + caracteres;
var moneda = numeros + ".";

/* OSINE_SFS-1063 - Inicio */
var constantBandeja = {
             rowNum: 5,
             rowNumPrinc: 20
};
/* OSINE_SFS-1063 - Fin */

var constant = {
    rowNum: 5,
    rowNumPrinc: 10,
    /* OSINE791 - RSIS8 - Inicio */
    codigoResultadoSupervision: {
        'noDefinido': 0,
        'cumple': 1,
        'incumple': 2,
        'noAplica': 3,
        'porVerificar': 4,
        'obstaculizado': 5,
        'supervisionInicialSi': 10,
        'supervisionInicialNo': 11,
        'supervisionInicialPorVerificar': 12,
        'supervisionInicialObstaculizada': 13,
        /*OSINE_SFS-791 - RSIS 03 - Inicio */
        'supervisionNo': 15,
        'subsanadoSi': 16,
        'subsanadoNo': 17
        /*OSINE_SFS-791 - RSIS 03 - Fin */
    },
    /* OSINE791 - RSIS8 - Fin */
    /* OSINE791 - RSIS4 - Inicio */
    codigoFlagSupervisionInicial: {
        'SupervisionObstaculizado': 'SUPERVISIONOBSTACULIZADO',
        'SupervisionNo': 'SUPERVISIONNO'
    },
    /* OSINE791 - RSIS4 - Fin */
    /* OSINE791 RSIS28 - Inicio */
    flujoLink: {
        'dsrCri':'DSR_CRI'
    },
    /* OSINE791 RSIS28 - Fin */    
    confirm: {
        'save': "Se registró satisfactoriamente.",
        'edit': "Se actualizó el registro satisfactoriamente.",
        'remove': "Se eliminó el registro satisfactoriamente.",
        'anular': "Se anuló el registro satisfactoriamente."
    },
    identificadorEstado: {
        'expRegistro': 'EXP_REGISTRO',
        'expDerivado': 'EXP_DERIVADO',
        'expAsignado': 'EXP_ASIGNADO',
        'expConcluido': 'EXP_CONCLUIDO',
        'osRegistro': 'OS_REGISTRO',
        'osSupervisado': 'OS_SUPERVISADO',
        'osRevisado': 'OS_REVISADO',
        'osAprobado': 'OS_APROBADO',
        'osOficiado': 'OS_OFICIADO',
        'osConcluido': 'OS_CONCLUIDO'
    },
    identificadorBandeja: {
        'espDerivados': 'IDENTIFICADOR_DERIVADO',
        'espAsignaciones': 'IDENTIFICADOR_ASIGNADO',
        'espEvaluacion': 'IDENTIFICADOR_EVALUADO',
        'espNotificadoArchivado': 'IDENTIFICADOR_NOTIFICADO',
        'supAsignaciones': 'IDENTIFICADOR_SUPERVISADO',
        'resRecepcion': 'IDENTIFICADOR_RECEPCIONADO',
        'espLegal' : 'IDENTIFICADOR_ESP_LEGAL'
    },
    empresaSupervisora: {
        'personaNatural': '1',
        'personaJuridica': '2'
    },
    accionOS: {
        'atender': 'atender',
        'revisar': 'revisar',
        'aprobar': 'aprobar',
        'notificar': 'notificar',
        'concluir': 'concluir',
        'asignar': 'asignar',
        'generar': 'generar',
        'consultar': 'consultar',
        'documentos': 'documentos',
        /* OSINE_SFS-480 - RSIS 41 - Inicio */
        'anular': 'anular',
        'editar': 'editar',
        /* OSINE_SFS-480 - RSIS 41 - Fin */
        'confirmardescargo': 'confirmardescargo'
    },
    // OSINE_SFS-480 - mdiosesf - RSIS 41 - Inicio
    codTipoPeticion: {
        'anular': 'MOTIVO_ANULAR',
        'editar': 'MOTIVO_EDITAR'
    },
    /* OSINE_SFS-480 - RSIS 06 - Inicio */
    estadoMensajeria: {
        'notificado': 'Notificado',
        'devuelto': 'Devuelto',
        'robado': 'Robado/Extraviado'
    },
    /* OSINE_SFS-480 - RSIS 06 - Fin */
    codMotivoPeticionEditar: {
        'cambiar_empresa_supervisora': 'M1',
        /* OSINE_SFS-791 - RSIS 34 - Inicio */
        'cambiar_tipo_asignacion': 'M3'
        /* OSINE_SFS-791 - RSIS 34 - Fin */
    },
    // OSINE_SFS-480 - mdiosesf - RSIS 41 - Fin
    flgOrigenExpediente: {
        'siged': '0',
        'inps': '1'
    },
    estadoOrigen: {
        archivoSiged: "FINAL",
        archivoInps: "TEMPORAL"
    },
    estado: {
        activo: "1",
        inactivo: "0"
    },
    iteracion: {
        'primera': '1',
        'segunda': '2'
    },
    tipoCumple: {
        'cumple': '1',
        'incumple': '0'
    },
    tipoSancion: {
        'sancion': '0',
        'nosancion': '1'
    },
    modoSupervision: {
        'consulta': 'consulta',
        'registro': 'registro'
    },
    supervision: {
        'realiza': '1'
    },
    motivoSupervision: {
        'especifica': '1',
        'noEspecifica': '0'
    },
    rol: {
        'jefeUnidad': 'JEFE DE UNIDAD',
        'jefeRegional': 'JEFE REGIONAL',
        /* OSINE791 - RSIS 21 - Inicio */
        'jefe':'JEFE',
        'especialista': 'ESPECIALISTA',
        'supervisorRegional': 'SUPERVISOR REGIONAL'
        /* OSINE791 - RSIS 21 - Fin */
    },
    confirmMensajeria: {
        'save': "Se envió mensajería satisfactoriamente.",
        'duplicado': "No se puedo agregar, ya existe destinatario seleccionado.",
        'selDocumento': "No se puedo enviar, seleccione documento para continuar.",
        'selClientes': "No se puedo enviar, seleccione destinarios para continuar."
    },
    comentario:{
       'registrado':"1"
    },
    valida: {
        alphanum: {
            descrip: {
                allowNumeric: true, allowLatin: true, allowUpper: true, allowLower: true, allowCaseless: true, allowOtherCharSets: true, allowSpace: true,
                allow: '!@#$%^&*()+=[]\\\;,/{}|":<>?~`.- _'
            },
            numeric: {
                allowMinus: false, allowThouSep: false,
                allow: '.'
            },
            alphaNum: {
                allowNumeric: true, allowLatin: true, allowUpper: true, allowLower: true, allowCaseless: true, allowOtherCharSets: false, allowSpace: true,
                allow: 'Ññóáéíóú'
            },
            alpha: {
                allowNumeric: false, allowLatin: true, allowUpper: true, allowLower: false, allowCaseless: true, allowOtherCharSets: false, allowSpace: true,
                allow: 'Ñ'
            },
            telefono: {
                allowNumeric: true, allowLatin: false, allowOtherCharSets: false,
                allow: '-/#'
            },
            numerico: {
                allowNumeric: true, allowLatin: false, allowOtherCharSets: false,
                allow: '.'
            },
            baseLegal: {
                allowNumeric: true, allowLatin: false, allowUpper: true, allowLower: true, allowCaseless: true, allowOtherCharSets: false, allowSpace: false,
                allow: 'BL-bl'
            }
            // htorress - RSIS 18 - Inicio 
            , comentario: {
                allowNumeric: true, allowLatin: true,
                allow: '*,-./ #()+&'
            }
            // htorress - RSIS 18 - Fin 
            /* OSINE_SFS-480 - RSIS 27 - Inicio */
            , codigoOsinergmin: {
                allowLatin: false,
                allowOtherCharSets: false,
                allowSpace: false,
                allow: ',1234567890'
            }
            /* OSINE_SFS-480 - RSIS 27 - Fin */
            /* OSINE_SFS-1063 - RSIS 05 - Inicio */
            , soloNumeros: {
                allowNumeric: true, allowLatin: false, allowOtherCharSets: false,
                allowSpace: false
            }
            ,decimalReles:{ // 6 enteros y 04 decimales
            allowNumeric               : true,
            maxPreDecimalPlaces : 6,
            allowDecSep         : true,
            maxDecimalPlaces    : 4,
            allowThouSep        : false,
            allowMinus          : false,
            
            },
            horasMinutos: {
                allowNumeric: true, allowLatin: false, allowOtherCharSets: false,
                allowSpace: false, allow: ':'
            }
            /* OSINE_SFS-1063 - RSIS 05 - Fin */
            /* OSINE_SFS-1344 - RSIS 03 - Inicio */
            , codigoTitularMinero: {
                allowLatin: false,
                allowOtherCharSets: false,
                allowSpace: false,
                allow: '1234567890'
            }
            /* OSINE_SFS-1344 - RSIS 03 - Fin */
        }
    }
};

$(function() {
    baseURL = "/inps/";
    $.ajaxSetup({
        cache: false,
        data: {
            wtf: $("#idSesion").val()
        }
    });
    validarCampo();
    anularEnter();
    anularCopyPasteInput();
    notify();
    $.datepicker.regional['es'] = {
        closeText: 'Cerrar',
        prevText: '&#x3c;Ant',
        nextText: 'Sig&#x3e;',
        currentText: 'Hoy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
            'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre',
            'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul',
            'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Mi&eacute;rcoles',
            'Jueves', 'Viernes', 'S&aacute;bado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mi&eacute;', 'Juv', 'Vie',
            'S&aacute;b'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'S&aacute;'],
        weekHeader: 'Sm',
        dateFormat: 'dd/mm/yy',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''
    };

    $.datepicker.setDefaults($.datepicker.regional['es']);

    validateFormActive();

    //cierra dialogs, que tengas boton atributo css "btnCloseDialog"
    boton.closeDialog();
});

/*********FUNCIONES**********/
/* OSINE791 - RSIS 21 - Inicio */
/*
* Funciones para Mostrar links de cambio de Estados
*/
var fxLinkGrilla = {
    habilitarEstados : function(estado, estadoSgte, rol){ //habilita estado
        var link ='';
         if(estado==constant.identificadorEstado.osSupervisado){
             if(estadoSgte==constant.identificadorEstado.osAprobado)
                    link = 'linkAprobarExpeEval';
             else
                     link = 'linkRevisarExpeEval';
         }else if(estado==constant.identificadorEstado.osRevisado){
             if(rol==constant.rol.supervisorRegional || rol==constant.rol.especialista){
                     link =''
             }else if(rol==constant.rol.jefe){
                 link ='linkAprobarExpeEval';
             }
         }else if(estado==constant.identificadorEstado.osAprobado){
             link = 'linkNotificarExpeEval';
         }

         return link;
    },
    mostrarOcultarMenu:function(contextMenu){
            var nli=$('#'+contextMenu+' li').length;
            var contOcultos=0;
            $('#'+contextMenu+' li').map(function(){
                    if($(this).children('a').css('display')=='none'){
                    contOcultos++;
                    }
            });
            if(nli==contOcultos){
                    $('#'+contextMenu).parent().css('opacity','0');
            }
    },
    /* OSINE791 RSIS28 - Inicio */
    habilitarNotificado: function(identificadorEstado, flagTramiteFinalizado, nroOrdenesServicio,caso) {
        switch (caso) {
            case constant.flujoLink.dsrCri:
                //no habilita links
            break;
            default:
                if(identificadorEstado==constant.identificadorEstado.osOficiado){
                    $('#linkConcluirOS').css('display','');
                    $('#linkEnviarMensajeriaOS').css('display','');
                }else if(identificadorEstado==constant.identificadorEstado.osConcluido
                        && flagTramiteFinalizado==constant.estado.inactivo && nroOrdenesServicio < 2 ){
                    $('#linkConfirmarDescargoOS').css('display','');
                }
            break;
        }
    }
    }
    /* OSINE791 RSIS28 - Fin */   
/* OSINE791 - RSIS 21 - Fin */
/*
* funciones de complemento para grillas.
*/
var fxGrilla = {
    divPto: '<div class="ptoGrilla">...</div>',
    setPtosSuspensivos: function(idGrilla, campo) {
        if ($('td[aria-describedby="' + idGrilla + '_' + campo + '"]').eq(0).length > 0) {
            var heightI = parseInt($('td[aria-describedby="' + idGrilla + '_' + campo + '"]').eq(0).css('height').replace('px', ''));
            $('td[aria-describedby="' + idGrilla + '_' + campo + '"]').css('white-space', 'normal');
            $('td[aria-describedby="' + idGrilla + '_' + campo + '"]').map(function() {
                var html = fxGrilla.limpiaPtos($(this).html());
                if (parseInt($(this).css('height').replace('px', '')) > heightI) {
                    $(this).css('position', 'relative');
                    html = html + fxGrilla.divPto;
                }
                $(this).html(html);
            });
            $('td[aria-describedby="' + idGrilla + '_' + campo + '"]').css('white-space', 'pre');
        }
    },
    limpiaPtos: function(valor) {
        return valor.replace(fxGrilla.divPto, '');
    },
    ocultarCampos: function(idGrilla, campos) {
        var camposArr = campos.split(",");
        for (var i = 0; i < camposArr.length; i++) {
            $('#' + idGrilla).hideCol(camposArr[i]);
        }
    },
    mostrarCampos: function(idGrilla, campos) {
        var camposArr = campos.split(",");
        for (var i = 0; i < camposArr.length; i++) {
            $('#' + idGrilla).showCol(camposArr[i]);
        }
    },
    obtenerPage: function(idGrilla) {
        return $("#" + idGrilla).getGridParam("page");
    },
    recargarGrillaPagina: function(idGrilla, pagina) {
        $("#" + idGrilla).trigger("reloadGrid", [{page: pagina}]);
    }

};
/*
* funciones para llenado de datos
*/
var fill = {
    /*
     * llena combo 
     */
    combo: function(data, id, desc, tagDestino) {
        var html = '<option value="">--Seleccione--</option>';
        if (data != null) {
            $.each(data, function(k, v) {
                html += '<option value="' + v[id] + '">' + v[desc] + '</option>';
            });
        }
        $(tagDestino).html(html);
    },
    /*
     *llena combo y selecciona el valor con el texto de valorText 
     */
    comboValorTxt: function(data, id, desc, tagDestino, valorText) {
        var html = '<option value="">--Seleccione--</option>';
        if (data != null) {
            $.each(data, function(k, v) {
                html += '<option value="' + v[id] + '" ';
                if (v[desc] == valorText) {
                    html += ' selected  ';
                }
                html += '>' + v[desc] + '</option>';
            });
        }
        $(tagDestino).html(html);
    },
    /*
     *llena combo y selecciona el valor con el texto de valorId 
     */
    comboValorId: function(data, id, desc, tagDestino, valorId) {
        var html = '<option value="">--Seleccione--</option>';
        if (data != null) {
            $.each(data, function(k, v) {
                html += '<option value="' + v[id] + '">' + v[desc] + '</option>';
            });
        }
        $(tagDestino).html(html);
        $(tagDestino).val(valorId);
    },
    clean: function(tagDestino) {
        var html = '<option value="">--Seleccione--</option>';
        $(tagDestino).html(html);
    }
};

/*
* funciones para botones con usos comun
*/
var boton = {
    closeDialog: function() {
       $('.btnCloseDialog').unbind( "click" );
        $('.btnCloseDialog').click(function() {
            var idP = $(this).parent().parent().attr('id');
            $('#' + idP).dialog('close');
        });
    }
};

/*
* funciones para popups de confirmacion.
*/
var confirmer = {
    start: function() {
        if ($('#dialogComunConf').html() == null || $('#dialogComunConf').html() == undefined) {
            var html = '<div id="dialogComunConf" class="dialogComunConf" style="display:none;" title="Confirmaci&oacute;n">' +
                    '<div class="cuerpo">' +
                    '<div class="ilb icon"><span class="ui-icon ui-icon-alert" style="float: left;margin: 0 7px 20px 0;"></span></div>' +
                    '<div class="ilb text"><span id="textDialogComunConf"></span></div>' +
                    '</div>' +
                    '<div class="botones">' +
                    '<button id="btnConfDialogComunConf">Aceptar</button><button onclick="confirmer.close()">Cancelar</button></div></div>';
            $(document.body).append(html);
            $("#dialogComunConf").dialog({resizable: false, autoOpen: false, height: "auto", width: "auto", dialogClass: 'dialog', modal: true, closeText: "Cerrar"});
        }
    },
    /* OSINE_SFS-791 - RSIS 33 - Inicio */
    startConfirmer: function() {
        if ($('#dialogComunConfirmer').html() == null || $('#dialogComunConfirmer').html() == undefined) {
            var html = '<div id="dialogComunConfirmer" class="dialogComunConf" style="display:none;" title="Confirmaci&oacute;n">' +
                    '<div class="cuerpo">' +
                    '<div class="ilb icon"><span class="ui-icon ui-icon-alert" style="float: left;margin: 0 7px 20px 0;"></span></div>' +
                    '<div class="ilb text"><span id="textDialogComunConfirmer"></span></div>' +
                    '</div>' +
                    '<div class="botones">' +
                    '<button id="btnSiConfDialogComunConf">Si Confirmar</button>' +
                    '<button id="btnNoConfDialogComunConf">No Confirmar</button>' +
                    '<button onclick="confirmer.close()">Cancelar</button></div></div>';
            $(document.body).append(html);
            $("#dialogComunConfirmer").dialog({resizable: false, autoOpen: false, height: "auto", width: "auto", dialogClass: 'dialog', modal: true, closeText: "Cerrar"});
        }
    },
    /* OSINE_SFS-791 - RSIS 33 - Fin */
    open: function(msj, evtClick, objText) {
        var textAceptar = "Aceptar";
        var textCancelar = "Cancelar";
        var title = "Confirmación";
        //inicializo botones
        $('#dialogComunConf .botones button').css('display', '');
        //Nuevos Texto Botones
        if (objText != undefined && objText != '') {
            textAceptar = (objText.textAceptar != undefined) ? objText.textAceptar : textAceptar;
            textCancelar = (objText.textCancelar != undefined) ? objText.textCancelar : textCancelar;
            title = (objText.title != undefined) ? objText.title : title;
        }
        //oculto si texto de Botones viene vacio
        if (textAceptar == '') {
            $('#dialogComunConf .botones').find('button:eq(0)').css('display', 'none');
        }
        if (textCancelar == '') {
            $('#dialogComunConf .botones').find('button:eq(1)').css('display', 'none');
        }
        //Texto Botones
        $('#dialogComunConf .botones').find('button:eq(0)').html(textAceptar);
        $('#dialogComunConf .botones').find('button:eq(1)').html(textCancelar);
        //title
        $('#dialogComunConf').dialog('option', 'title', title);
        //abro popup
        $('#dialogComunConf #textDialogComunConf').html(msj);
        $('#dialogComunConf #btnConfDialogComunConf').attr('onclick', "confirmer.close(); " + evtClick);
        $('#dialogComunConf').dialog('open');
    },
    close: function() {
        //$('#dialogComunConf').remove();
        $("#dialogComunConf").dialog("close");
        $('#dialogComunConf #textDialogComunConf').html('');
        $('#dialogComunConf #btnConfDialogComunConf').attr('onclick', '');
    }
};
/* funcion llenado de arbol actividades */
var fxTree = {
    build: function(objData) {
        //ordenar json de ajax
        var cont = 0;
        var data = [];
        $.each(objData, function(k, v) {
            data[cont] = {'key': v.idActividad, 'title': v.nombre, 'idActividadPadre': v.idActividadPadre, 'children': [], 'folder': false};
            cont++;
        });
        //declaro auxiliares
        var dataF = [];//data a retornar
        var dataT = [];//data q almacena restantes, mientras se va armando el arbol
        //armo 1er nivel
        var x = fxTree.orderChild(data, null);
        dataF = x.resultado;
        dataT = x.restante;
        //armo 2do nivel
        $.each(dataF, function(k, v) {
            var idPadre = v.key;
            var x = fxTree.orderChild(dataT, idPadre);
            dataF[k]['children'] = x.resultado;
            dataT = x.restante;
        });
        //armo 3do nivel
        $.each(dataF, function(k, v) {
            $.each(dataF[k]['children'], function(kk, vv) {
                var idPadre = vv.key;
                var x = fxTree.orderChild(dataT, idPadre);
                dataF[k]['children'][kk]['children'] = x.resultado;
                dataT = x.restante;
            });
        });
        return dataF;
    },
    orderChild: function(data, idPadre) {
        var dataA = [];
        var dataB = [];
        var contA = 0;
        var contB = 0;
        $.each(data, function(k, v) {
            if (v.idActividadPadre == idPadre) {
                v['folder'] = fxTree.isFather(v.key, data);
                dataA[contA] = v;
                contA++;
            } else {
                dataB[contB] = v;
                contB++;
            }
        });
        return {resultado: dataA, restante: dataB};
    },
    isFather: function(id, data) {
        var retorno = false;
        $.each(data, function(k, v) {
            if (id == v.idActividadPadre) {
                retorno = true;
            }
        });
        return retorno;
    }
};
/* funciones loading */
loading = {
    open: function() {
        $('#overlay_loading').css('display', '');
    },
    close: function() {
        $('#overlay_loading').css('display', 'none');
    }
}

function quitaSlashDir(dir) {
    var sep = dir.split("\\");
    dir = sep[sep.length - 1];
    return dir;
}

function errorAjax(jqXHR) {
    var msj1 = "El servicio no se encuentra disponible";
    var msj2 = "Intente mas tarde";
    if (jqXHR != undefined) {
        if (jqXHR.responseText.indexOf('@errorSesionExpired@') > 0) {
            msj1 = "Error sesion expirada";
            msj2 = "La sesion a expirado, por favor vuelva a autenticarse.";
        }
    }
    loading.close();
    mensajeGrowl('error', msj1, msj2);
}

/*
* @param {type} idDivMsje, id del div que mostrara el mensaje
* @param {type} Msje, texto a mostrar
* @param {type} ElementError, elementos del Formulario que generan error, Ej. #txtNombre
* @returns {undefined}*
*/
function muestraDivError(idDivMsje, Msje, ElementError) {
    $(idDivMsje).show();
    $(idDivMsje).html(Msje);
    $(ElementError).addClass("error");
}
function limpiaValidacionesMostradas(idDivMsje, idForm) {
    $('#' + idDivMsje).html("").hide();
    $('#' + idForm).find('input,select,textarea').removeClass('error');
}

function mensajeGrowl0(type, title, detail) {
    if (type == "success")
        type = "info"

    $('#default').puigrowl('show', [{severity: type, summary: title, detail: detail}]);
}
function notify() {
    $('#notifynormal').puinotify({
        easing: 'easeInOutCirc'
    });
    $("#notifynormal").click(function(e) {
        $('#notifynormal').puinotify('hide');
    });
}
function mensajeGrowl(type, title, detail, time) {
    if (title == undefined) {
        title = "";
    }
    if (detail == undefined) {
        detail = "";
    }
    if (time == undefined || isNaN(time)) {
        time = 8000;
    }
    if ($('#notifynormal').hasClass('error')) {
        $('#notifynormal').removeClass('error');
    } else if ($('#notifynormal').hasClass('warn')) {
        $('#notifynormal').removeClass('warn');
    } else if ($('#notifynormal').hasClass('success')) {
        $('#notifynormal').removeClass('success');
    }
    $('#notifynormal').addClass(type);
    $('#notifynormal').puinotify('show', '<h1>' + title + '</h1> <p>' + detail + '</p>');
    id = '#notifynormal';

    window.setTimeout(closeMessage, time);
}
function cerrarMessage() {
    if ($('#notify').is(":visible")) {
        $('#notify').puinotify('hide');
    }
}

function closeMessage() {
    if ($(id).is(":visible")) {
        $(id).puinotify('hide');
    }
}
window.history.forward();

function noBack() {
    window.history.forward();
}

function validarCampo() {
    (function($) {
        $.fn.validCampoNumero = function(cadena) {
            $(this).on({
                keypress: function(e) {
                    var key = e.which,
                            keye = e.keyCode,
                            tecla = String.fromCharCode(key).toLowerCase(),
                            letras = cadena;
                    if (letras.indexOf(tecla) == -1 && keye != 9 && (key == 37 || keye != 37) && (keye != 39 || key == 39) && keye != 8 && (keye != 46 || key == 46) || key == 161) {
                        e.preventDefault();
                    }
                }
            });
        };
    })(jQuery);
}

function validateFormActive() {
    var html = "<a  data-title='?' class='tooltip'><img src='/sglss/images/error_small.png'  style='float: left;'/></a>";

    (function($) {
        $.fn.validateAllForm = function(div) {

            $(div).hide();
            var validar = true;
            var form = $(this);
            var id = "#" + form.attr("id");
            var errorTotal = "";
            $(id + ' input, ' + id + ' select, ' + id + ' textarea').each(
                    function(index) {
                        var texto = "";
                        var input = $(this);
                        var error = validateInput(input, html)
                        if (error != "") {
                            validar = false;

                            if (error != "[O]") {
                                var label = $("label[for='" + $(this).attr('id') + "']");
                                if (label.length != 0)
                                    texto = label.html().replace('(*)', '');//+"<br/>";
                                if (errorTotal != "")
                                    errorTotal += "<//>" + texto + error;
                                else {
                                    errorTotal += texto + error;
                                }
                            } else {
                                if ($(this).attr('class').indexOf('hasDatepicker') == -1 && this.type != "select-one") {//TODO evitar se abra datepicker
                                    //$(this).focus();  //jpiro, focus cambia estilo css y no se ve rojo, confunde usuario
                                }
                            }
                        }

                        $(this).on({
                            blur: function(e) {
                                var input = $(this);
                                validateInput(input, html);

                            }});

                    }
            );
            //JPIRO valida checks - inicio
            var errorCheck = "";
            if (validar) {
                var cont = 0;
                $(id).find('[validate|="[CHECK]"]').map(function() {
                    $(this).find('input:checked').map(function() {
                        cont++;
                    });
                    if (cont == 0) {
                        validar = false;
                        var texto = "";
                        var error = " Debe Seleccionar uno al menos.";
                        var label = $("label[for='" + $(this).attr('id') + "']");
                        if (label.length != 0)
                            texto = label.html().replace('(*)', '');
                        errorCheck += texto + error;
                    }
                });
            }

            //JPIRO valida checks - fin

            if (errorTotal != "") {

                var array = errorTotal.split('<//>');
                $(div).show();
                $(div).focus();
                $(div).html(array[0]);
            } else if (errorCheck != "") {
                $(div).show();
                $(div).focus();
                $(div).html(errorCheck);
            }
            //JPIRO, caso inputs dentro de tabs, mostrar tab con campo con class=error
            $('[aria-controls|="' + $(form).find('.error').eq(0).attr('validatetab') + '"] a').trigger('click').effect("pulsate", "slow");

            return validar;
        };
    })(jQuery);

    (function($) {
        $.fn.validarForm = function(data) {
            var form = $(this);
            var id = "#" + form.attr("id");
            $(id + ' input,' + id + ' textarea').each(
                    function(index) {
                        var cadena = "";
                        var input = $(this);

                        var validacion = input.attr('validate');
                        if (validacion !== null && typeof validacion !== "undefined")
                            if (validacion.indexOf("[SL]") !== -1 || validacion.indexOf("[SN]") !== -1 || validacion.indexOf("[SNP]") !== -1 || validacion.indexOf("[MONEDA]") !== -1) {//TODO para otros casos

                                if (validacion.indexOf("[SL]") !== -1) {
                                    cadena = caracteres;
                                } else if (validacion.indexOf("[SN]") !== -1) {
                                    cadena = numeros;
                                } else if (validacion.indexOf("[SNP]") !== -1) {
                                    cadena = numeros;
                                } else if (validacion.indexOf("[MONEDA]") !== -1) {
                                    cadena = moneda;
                                }

                                $(this).on({
                                    keypress: function(e) {
                                        var key = e.which,
                                                keye = e.keyCode,
                                                tecla = String.fromCharCode(key).toLowerCase(),
                                                letras = cadena;
                                        if (letras.indexOf(tecla) == -1 && keye != 9 && (key == 37 || keye != 37) && (keye != 39 || key == 39) && keye != 8 && (keye != 46 || key == 46) || key == 161) {
                                            e.preventDefault();
                                        }
                                    }
                                });
                            }
                    }
            );

        };
    })(jQuery);
}
function validateInput(input, html) {
    var validaciones = input.attr('validate');
    var error = "";
    if (validaciones !== null && typeof validaciones !== "undefined")
        error = validaCampos(input.val(), validaciones);
    if (error !== "") {
        input.addClass("error");
    } else {
        input.removeClass("error")
    }
    return error;
}
function validaCampos(value, tipo) {
    var mensaje = "";
    var error;
    if (tipo.indexOf("[O]") !== -1) { //para los obligatorios
        if ($.trim(value) === "") {
            error = true;
            mensaje = "[O]";
            return mensaje;
        }
    }
    if (tipo.indexOf("[SL]") !== -1) {
        var expreg = new RegExp("^$|^([á-úÁ-Ú]|[a-zA-Z ])*$");
        if (!expreg.test(value)) {
            mensaje = " Formato de solo letras.";
            return mensaje;
        }
    }
    if (tipo.indexOf("[SN]") !== -1) {
        var expreg = new RegExp("^$|[0-9][0-9]*");
        if (!expreg.test(value) || isNaN(value)) {
            mensaje = " Formato de solo Números.";
            return mensaje;
        }
    }
    if (tipo.indexOf("[SNP]") !== -1) {//sono numero entero
        var expreg = new RegExp("^$|[1-9][0-9]*");
        if (!expreg.test(value) || isNaN(value) || value < 0) {
            mensaje = " Formato de Números, no válido.";
            return mensaje;
        }
    }

    if (tipo.indexOf("[CORREO]") !== -1) {
        var expreg = new RegExp("(^$|^.*@.*\..*$)");//("/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/");  
        if (!expreg.test(value)) {
            mensaje = " Ingrese un formato valido de correo.";
            return mensaje;
        }
    }

    if (tipo.indexOf("[MONEDA]") !== -1) {
        var expreg = new RegExp("^$|[0-9]+(\.[0-9]+)?$");
        if (!expreg.test(value)) {
            mensaje = " Ingrese un formato valido de moneda.";
            return mensaje;
        }
    }
    return mensaje;
}


function anularEnter() {
    $("input[type=text]").keypress(function(e) {
        if (e.which == 13 || e.keyCode == 13) {
            return false;
        }
    });
}

function anularEspaciadora(id) {
    $(id).keypress(function(e) {
        if (e.which == 32 || e.keyCode == 32) {
            return false;
        }
    });
}
function anularEnterTextArea(id) {
    $(id).keypress(function(e) {
        if (e.which == 13 || e.keyCode == 13) {
            return false;
        }
    });
}

function anularCopyPasteInput() {
    $('input').bind('copy paste', function(e) {

        e.preventDefault();
    });
}
function permiteCopyPasteInput(ids) {
    $(ids).unbind('copy paste');
}

function validaEnteros(obj, nDec) {
    var expreg = /^\d*$/;
    if (!isNaN(nDec) && (Number(nDec) > 0))
        expreg = new RegExp("^\\d*(\\.\\d{1," + nDec + "})?$");
    if (expreg.test(obj))
        return true;
    else
        return false;
}
function comparingDates(fromDate, toDate, fromDateName, toDateName) {

    if (fromDate != '' && toDate != '') {

        var datefrom = new Date();
        var dateto = new Date();

        try {
            var arrayfrom = fromDate.split('/');
            datefrom.setFullYear(arrayfrom[2]);
            datefrom.setMonth(parseFloat(arrayfrom[1]) - 1);
            datefrom.setDate(parseFloat(arrayfrom[0]));
            datefrom.setHours('0');
            datefrom.setMinutes('0');
            datefrom.setSeconds('0');

        } catch (ex) {
            alert('El campo' + fromDateName + ' es una fecha invalida.');
            return false;
        }

        try {
            var arrayto = toDate.split('/');
            dateto.setFullYear(arrayto[2]);
            dateto.setMonth(parseFloat(arrayto[1]) - 1);
            dateto.setDate(parseFloat(arrayto[0]));
            dateto.setHours('0');
            dateto.setMinutes('0');
            dateto.setSeconds('0');

        } catch (ex) {
            alert('El campo' + toDateName + ' es una fecha invalida.');
            return false;
        }

        if (datefrom > dateto) {
            return false;
        }
    }
    return true;
}

function dias_entre(date1, date2) {
    var resultado = comparaFecha(date1, date2);
    if (isNaN(resultado))
        return resultado;

    var f1 = date1.split("/");
    var f2 = date2.split("/");
    // The number of milliseconds in one day
    var ONE_DAY = 1000 * 60 * 60 * 24;

    // Convert both dates to milliseconds
    var date1_ms = new Date(f1[2], f1[1], f1[0]).getTime();
    var date2_ms = new Date(f2[2], f2[1], f2[0]).getTime();

    // Calculate the difference in milliseconds
    var difference_ms = date2_ms - date1_ms;
    // Convert back to days and return
    return Math.round(difference_ms / ONE_DAY);

}
Date.daysBetween = function(date1, date2) {
    //Get 1 day in milliseconds
    var one_day = 1000 * 60 * 60 * 24;

    // Convert both dates to milliseconds
    var date1_ms = date1.getTime();
    var date2_ms = date2.getTime();

    // Calculate the difference in milliseconds
    var difference_ms = date2_ms - date1_ms;

    // Convert back to days and return
    return Math.round(difference_ms / one_day);
}

//devuelve 1 si fecha1>fecha2, -1 si fecha1<fecha2 y 0 si fecha1=fecha2
function comparaFecha(fecha1, fecha2)
{
    if (!esFecha(fecha1)) {
        return fecha1 + " no es una fecha correcta";
    }
    if (!esFecha(fecha2)) {
        return fecha2 + " no es una fecha correcta";
    }
    if (fecha1 == fecha2)
        return 0;
    else {
        var f1 = fecha1.split("/");
        var f2 = fecha2.split("/");
        if ((for1 = f1[2] + f1[1] + f1[0]) > (for2 = f2[2] + f2[1] + f2[0]))
            return 1;
        else if (for1 < for2)
            return -1;
        else
            return 0;
    }
}

function parseDate(fecha) {
    var curr_date = fecha.getDate();
    var curr_month = fecha.getMonth();
    curr_month++;   // need to add 1 – as it’s zero based !
    var curr_year = fecha.getFullYear();
    var formattedDate = (('' + curr_date).length < 2 ? '0' : '') + curr_date + "/" + (('' + curr_month).length < 2 ? '0' : '') + curr_month + "/" + curr_year;
    return formattedDate;
}
function esFecha(fecha)
{
    //Se verifica que la fecha sólo tenga caracteres numéricos y el caracter "/"
    for (var i = 0; i < fecha.length; i++) {
        //var carac = fecha.substring(i,i+1);
        var carac = fecha.charAt(i);
        if ((carac < "0" || carac > "9") && carac != "/") {
            return false;
        }
    }

    //Obtenemos el dia de la fecha
    var pos1 = fecha.indexOf("/");
    aux = fecha.substring(0, pos1);
    if (aux.length != 2)
        return false;  //verificamos que la parte del dia tenga dos caracteres
    if (aux.charAt(0) == "0") {
        aux = aux.substr(1, 1);
    }
    var dia = parseInt(aux);

    //Obtenemos el mes de la fecha
    var pos2 = fecha.indexOf("/", pos1 + 1);
    var aux = fecha.substring(pos1 + 1, pos2);
    if (aux.length != 2)
        return false;  //verificamos que la parte del mes tenga dos caracteres
    if (aux.charAt(0) == "0") {
        aux = aux.substr(1, 1);
    }
    var mes = parseInt(aux);

    //Obtenemos el año de la fecha
    aux = fecha.substring(pos2 + 1, fecha.length);
    if (aux.length != 4)
        return false;  //verificamos que la parte del año tenga cuatro caracteres
    var anno = parseInt(aux);


    if (mes < 1 || mes > 12)
        return false;  //el mes debe estar entre 1 y 12
    if (dia < 1 || dia > 31)
        return false;  //el día debe estar entre 1 y 31
    if (anno < 1754 || anno > 9999)
        return false;  //el año debe estar entre 1754 y 9999

    if (mes == 2 && dia > 29)
        return false;  //valida Febrero: el día debe estar entre 1 y 29

    if ((anno % 4) != 0 && dia == 29 && (mes == 2))
        return false; //Año Bisiesto: se verifica que febrero tenga 29 días

    if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) & dia > 30)
        return false;  //Meses de 30 dias.

    return true;
}

function esHora(lahora) {
    if (lahora.length == 0) {
        return false;
    }
    var eraseAMPM = lahora.toString().split(" ");
    if (eraseAMPM.length != 2) {
        return false;
    }
    eraseAMPM[1] = eraseAMPM[1].toUpperCase();
    var nuevaHora = eraseAMPM[0];
    var arrHora = nuevaHora.toString().split(":");
    if (arrHora.length != 2) {
        return false;
    }
    if (parseInt(arrHora[0]) < 0 || parseInt(arrHora[0]) > 12 || arrHora[0].length != 2) {
        return false;
    }
    if (parseInt(arrHora[1]) < 0 || parseInt(arrHora[1]) > 59 || arrHora[1].length != 2) {
        return false;
    }
    if (eraseAMPM[1].toString() != "AM" && eraseAMPM[1].toString() != "PM") {
        return false;
    }
    return true;
}
function validatecla(event, tipo, textbox) {

    var tecla;
    if (navigator.appName.indexOf("Netscape") != -1) {
        tecla = event.which;
    } else {
        tecla = event.keyCode;
    }

    var key = String.fromCharCode(tecla);

    /*---Mayuscula---*/
    key = key.toUpperCase();

    var telefonos = "-/#";
    var numeros = "0123456789";
    var especiales = "'#ï¿½()_;:ï¿½[]{}!ï¿½/?ï¿½``ï¿½ï¿½+ï¿½=&%$*";
    var letras = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnÃ‘n??OoPpQqRrSsTtUuVvWwXxYyZz??????????";

    if (tecla == 8)
        return true;
    if (tecla == 127)
        return true;
    if (tecla == 0)
        return true;

    if (tipo == 'letras') {
        window.event.keyCode = window.event.keyCode - 32;
        if (tecla == 32)
            return true;
        if (letras.indexOf(key) != -1)
            return true;
        return false;
    }

    if (tipo == 'enteros') {

        if (numeros.indexOf(key) != -1)
            return true;
        return false;
    }

    if (tipo == 'ruc') {
        if (numeros.indexOf(key) != -1) {
            return true;
        }

        return false;
    }

    if (tipo == 'decimales') {
        if (numeros.indexOf(key) != -1)
            return true;
        var cadena = textbox.value;
        if (cadena.indexOf('.') != -1)
            return false;
        if (tecla == 46)
            return true;
        return false;
    }

    if (tipo == 'decimales2') {

        if (numeros.indexOf(key) != -1) {
            if (textbox.value.length == 5) {
                if (textbox.value.indexOf('.') == -1) {
                    textbox.value = textbox.value + '.';
                }
            }

            return true;
        }

        var cadena = textbox.value;
        if (cadena.indexOf('.') != -1)
            return false;
        if (tecla == 46)
            return true;

        return false;
    }

    if (tipo == 'decimales3') {

        if (numeros.indexOf(key) != -1) {
            if (textbox.value.length == 6) {
                if (textbox.value.indexOf('.') == -1) {
                    textbox.value = textbox.value + '.';
                }
            }

            return true;
        }

        var cadena = textbox.value;
        if (cadena.indexOf('.') != -1)
            return false;
        if (tecla == 46)
            return true;

        return false;
    }

    if (tipo == 'NoNumeros') {
        if (tecla == 32)
            return true;
        if (numeros.indexOf(key) != -1 || especiales.indexOf(key) != -1)
            return false;
        return true;
    }

    if (tipo == 'especiales') {
        if (tecla == 32)
            return true;
        if (especiales.indexOf(key) != -1)
            return false;
        return true;
    }

    if (tipo == 'num_letras') {
        if (tecla == 32)
            return true;
        if (numeros.indexOf(key) != -1 || letras.indexOf(key) != -1)
            return true;
        return false;
    }

    if (tipo == 'telefonos') {
        if (tecla == 32)
            return true;
        if (numeros.indexOf(key) != -1 || telefonos.indexOf(key) != -1)
            return true;
        return false;
    }

    if (tipo == 'todo') {
        if (tecla == 32)
            return true;
        if (numeros.indexOf(key) != -1 || especiales.indexOf(key) != -1
                || letras.indexOf(key) != -1)
            return false;
        return true;
    }

}

function redir(url) {
    //IE 7 detectado
    if (document.all && (!document.documentMode || (document.documentMode && document.documentMode < 8))) {
        window.location.replace(url);
    } else {
        window.location = url;
    }
}

function formateafecha(fecha)
{
    var long = fecha.length;
    var dia;
    var mes;
    var ano;

    if ((long >= 2) && (primerslap == false)) {
        dia = fecha.substr(0, 2);
        if ((IsNumeric(dia) == true) && (dia <= 31) && (dia != "00")) {
            fecha = fecha.substr(0, 2) + "/" + fecha.substr(3, 7);
            primerslap = true;
        }
        else {
            fecha = "";
            primerslap = false;
        }
    }
    else
    {
        dia = fecha.substr(0, 1);
        if (IsNumeric(dia) == false)
        {
            fecha = "";
        }
        if ((long <= 2) && (primerslap = true)) {
            fecha = fecha.substr(0, 1);
            primerslap = false;
        }
    }
    if ((long >= 5) && (segundoslap == false))
    {
        mes = fecha.substr(3, 2);
        if ((IsNumeric(mes) == true) && (mes <= 12) && (mes != "00")) {
            fecha = fecha.substr(0, 5) + "/" + fecha.substr(6, 4);
            segundoslap = true;
        }
        else {
            fecha = fecha.substr(0, 3);
            ;
            segundoslap = false;
        }
    }
    else {
        if ((long <= 5) && (segundoslap = true)) {
            fecha = fecha.substr(0, 4);
            segundoslap = false;
        }
    }
    if (long >= 7)
    {
        ano = fecha.substr(6, 4);
        if (IsNumeric(ano) == false) {
            fecha = fecha.substr(0, 6);
        }
        else {
            if (long == 10) {
                if ((ano == 0) || (ano < 1900) || (ano > 2100)) {
                    fecha = fecha.substr(0, 6);
                }
            }
        }
    }

    if (long >= 10)
    {
        fecha = fecha.substr(0, 10);
        dia = fecha.substr(0, 2);
        mes = fecha.substr(3, 2);
        ano = fecha.substr(6, 4);
// Año no viciesto y es febrero y el dia es mayor a 28 
        if ((ano % 4 != 0) && (mes == 02) && (dia > 28)) {
            fecha = fecha.substr(0, 2) + "/";
        }
        if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
            if (dia == 31)
                fecha = fecha.substr(0, 2) + "/";
        }
    }
    return (fecha);
}

function valida_numero(xinput, tipval)
{
    var xkey = event.keyCode;
    if (tipval == "int")
        if ((xkey < 48) || (xkey > 57))
            event.returnValue = false;
    if (tipval == "dec")
    {
        if (xkey < 46 || xkey > 57 || xkey == 47)
            event.returnValue = false;
    }
    if (tipval == "stn") {
        if (((xkey != 46) && (xkey < 48)) || ((xkey > 57) && (xkey < 65)) || ((xkey > 90) && (xkey != 95) && (xkey < 97)) || (xkey > 122))
            event.returnValue = false;
    }
    if (tipval == "str") {
        if (((xkey != 32) && (xkey < 65)) || ((xkey > 90) && (xkey < 97)))
            event.returnValue = false;
    }
    if (tipval == "stn2") {
        if (((xkey != 32) && (xkey < 46)) || ((xkey > 57) && (xkey < 65)) || ((xkey > 90) && (xkey != 95) && (xkey < 97)) || (xkey > 122))
            event.returnValue = false;
    }
    if (tipval == "tlf")
        if (((xkey != 32) && (xkey < 45)) || (xkey > 57))
            event.returnValue = false;

    if (tipval == "afn")
    {
        if (((xkey != 32) && (xkey < 45)) || (xkey > 57) ||
                ((xkey < 48) || (xkey > 57)))
            event.returnValue = false;
    }

}
function validarTextarea() {
    $('textarea').bind('keyup change', function() {
        var texto = $(this).val();
        var longitud = $(this).val().length;
        var maximo = parseInt($(this).attr("maxlength"));
        if (longitud > maximo) {
            $(this).val(texto.substr(0, maximo));
        }
    });
    $('textarea').bind('copy paste', function(e) {

        e.preventDefault();
    });
    $("textarea[maxlength]").keypress(function(event) {
        var key = event.which;

        //all keys including return.
        if (key >= 33 || key == 13 || key == 32) {
            var maxLength = $(this).attr("maxlength");
            var length = this.value.length;
            if (length >= maxLength) {
                event.preventDefault();
            }
        }
    });

}
function validateDouble(objeto, cantEnt, cantDec) {
    var encontrado = '0';
    var valor = objeto.value;
    if (valor != '') {
        ind = valor.indexOf('.');
        if (ind != -1)
            encontrado = 1;
        if (encontrado == '1') {
            if (valor.charAt(valor.length - 1) == '.' && (valor.length - 1) > ind)
                valor = valor.substring(0, valor.length - 1);
            dec = valor.substring(ind + 1, valor.length);
            if (dec.length > cantDec)
                valor = valor.substring(0, ind + cantDec + 1);
        } else {
            if (valor.length > cantEnt)
                valor = valor.substring(0, cantEnt);
        }
    }
    objeto.value = valor;
}

/*EOG*/
function redir(url) {
    //IE 7 detectado
    if (document.all && (!document.documentMode || (document.documentMode && document.documentMode < 8))) {
        window.location.replace(url);
    } else {
        window.location = url;
    }
}
/**
* 
 * @param {type} data
* @returns {String}
*/
function fecha(data) {
    if (data == null) {
        return "&nbsp;";
    } else {
        if (data == '-62135751600000') {
            return '01/01/0001';
        } else {
            if (typeof data == "string") {
                return data;
            } else {
                return $.datepicker.formatDate('dd/mm/yy', new Date(data));
            }
        }
    }
}
function fecha_hora(data) {
    if (data == null) {
        return "&nbsp;";
    } else {
        if (data == '-62135751600000') {
            return '01/01/0001';
        } else {
            if (typeof data == "string") {
                return data;
            } else {
                var date = new Date(data);
                var fecha = $.datepicker.formatDate('dd/mm/yy', new Date(data));
                var hh = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
                var min = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
                var ss = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
                return fecha + " " + hh + ":" + min + ":" + ss;
            }
        }
    }
}

function cambioColorTrGrid(grid) {
    var row = grid.jqGrid('getRowData');
    for (var i = 0; i < row.length; i++) {
        origen = row[i].flagOrigen;
        idExpediente = row[i].idExpediente;
        if (origen == 1) {
            grid.find("tr").eq(i + 1).addClass("tr-inps");
//           $("#gridAsignacion").find("tr").eq(i+1).css("background", "#D5E6F7");
        }
    }
}

function comparaHora(horaInicio, horaFin) {
//    if (!esHora(horaInicio)) {
//        return horaInicio + " no es una Hora correcta";
//    }
//    if (!esHora(horaFin)) {
//        return horaFin + " no es una Hora correcta";
//    }
    if (horaInicio.getHours() < horaFin.getHours()) {
        return -1;
    } else if (horaInicio.getHours() > horaFin.getHours()) {
        return 1;
    } else { // las horas son iguales
        if (horaInicio.getMinutes() < horaFin.getMinutes()) {
            return -1;
        } else if (horaInicio.getMinutes() > horaFin.getMinutes()) {
            return 1;
        } else {
            return 0;
        }
    }
}
function changeTipoDocu(origen, destino, form, tipo) {
    var tipoDocu = $(origen).find(':checked').html();
    var obli = $(destino).attr('validate').indexOf("[O]") !== -1 ? "[O]" : "";
    switch (tipoDocu) {
        case "DNI":
            $(destino).attr("maxlength", "8");
            $(destino).attr("minlength", "8");
            $(destino).attr("validate", obli + "[SN]");
            break;
        case "CE":
            $(destino).attr("maxlength", "12");
            $(destino).attr("minlength", "8");
            $(destino).attr("validate", obli + "[SLN]");
            break;
        case "PASAPORTE":
            $(destino).attr("maxlength", "12");
            $(destino).attr("minlength", "12");
            $(destino).attr("validate", obli + "[SLN]");
            break;
        case "CED. DIPLOMATICA DE IDENTIDAD":
            $(destino).attr("maxlength", "15");
            $(destino).attr("minlength", "15");
            $(destino).attr("validate", obli + "[SN]");
            break;
        case "CÉD. DIPLÓMATICA DE IDENTIDAD":
            $(destino).attr("maxlength", "15");
            $(destino).attr("minlength", "15");
            $(destino).attr("validate", obli + "[SN]");
            break;
        default:
            $(destino).attr("maxlength", "10");
            $(destino).removeAttr("minlength");
            $(destino).attr("validate", obli + "[SLN]");
    }
    $(destino).unbind("keypress");
    $(form).validarForm();
    if (tipo != "carga") {
        $(destino).val("");
    }
}

function obtenerFechaFormateada(date) {
    var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
    month = month.length > 1 ? month : '0' + month;
    var day = date.getDate().toString();
    day = day.length > 1 ? day : '0' + day;
    return day + '/' + month + '/' + year;
}

