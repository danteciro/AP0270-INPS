/**
 * Resumen		
 * Objeto		: ordenServicio.js
 * Descripción		: ordenServicio
 * Fecha de Creación	: 17/06/2015
 * PR de Creación	: OSINE_SFS-480
 * Autor			: Julio Piro Gonzales
 * =====================================================================================================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                         |     Descripción
 * =====================================================================================================================================================================
 * OSINE_SFS-480  |  05/05/2016   |   Mario Dioses Fernandez         |     Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
 * OSINE_SFS-480  |  09/05/2016   |   Luis García Reyna              |     Modificar Mensaje de Confirmacion de "Revisar Expediente"
 * OSINE_SFS-480  |  10/05/2016   |   Mario Dioses Fernandez         |     Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica)
 * OSINE_SFS-480  |  20/05/2016   |   Hernán Torres Saenz            |     Crear la interfaz "Anular orden de servicio" de acuerdo a especificaciones
 * OSINE_SFS-480  |  23/05/2016   |   Giancarlo Villanueva Andrade   |     Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
 * OSINE_SFS-480  |  01/06/2016   |   Luis García Reyna              |     Implementar la funcionalidad de editar asignaciones de acuerdo a especificaciones
 * OSINE_SFS-480  |  06/06/2016   |   Mario Dioses Fernandez         |     Envio de Datos de Mensajeria a SIGED mediante WS
 * OSINE_791-RSIS34| 10/10/2016   |   Cristopher Paucar Torre        |     Editar Orden de Servicio "Cambiar tipo de Asignación".
 *    
 */

//common/OrdenServicio/OrdenServicio.js
var changeProceso = false;
var changeTipoSupervision = false;
var changeRubro = false;
var changeUbigeo = false;
var coOrSeOrSe = {            
    armaValidateFormOS: function() {
        $('#frmOS').find('input, select, textarea').removeAttr('validate');
        switch ($('#tipoAccionOS').val()) {
            case constant.accionOS.asignar:
                $('#slctIdProcesoOS,#slctIdEtapaOS,#slctIdTramiteOS,#cmbTipoAsigOS,#cmbTipoSupervisionOS,#slctUnidSupeOS,#slctTipoEmprSupeOS,#slctEmprSupeOS')
                        .attr('validate', '[O]');
                break;
            case constant.accionOS.generar:
                $('#slctIdFlujoSigedOS,#txtAsuntoSigedOS,#slctIdProcesoOS,#cmbTipoAsigOS,#cmbTipoSupervisionOS,#codigoOsinergOS,#txtIdUnidadSupervisadaOS,#slctTipoEmprSupeOS,#slctEmprSupeOS,#nroDocumentoOS')
                        .attr('validate', '[O]');
                break;
            case constant.accionOS.editar:
                $('#slctIdFlujoSigedOS,#txtAsuntoSigedOS,#slctIdProcesoOS,#cmbTipoAsigOS,#cmbTipoSupervisionOS,#codigoOsinergOS,#txtIdUnidadSupervisadaOS,#txtRucUnidadSupervisadaOS,#slctTipoEmprSupeOS,#slctEmprSupeOS,#nroDocumentoOS')
                        .attr('validate', '[O]');                                                                                                                                                                                   
                break;
            default:
        }
    },
    armaConsultarFormOS: function() {
        if ($('#txtflagOrigenOS').val() == constant.flgOrigenExpediente.siged) {
//                    $('#slctIdProcesoOS').trigger('change');
            fill.clean("#slctIdEtapaOS,#slctIdTramiteOS");
            common.serie.cargarEtapa("#slctIdProcesoOS", "#slctIdEtapaOS");
            $('#slctIdEtapaOS').val($('#txtIdEtapaTrOS').val());
            $('#slctIdEtapaOS').trigger('change');
            $('#slctIdTramiteOS').val($('#txtIdTramiteOS').val());
        }
        // para datos de empresa supervisora
        if ($('#txtIdLocadorOS').val() != '' && $('#txtIdLocadorOS').val() != undefined) {
            $('#slctTipoEmprSupeOS').val($('#slctTipoEmprSupeOS').find('option[codigo="' + constant.empresaSupervisora.personaNatural + '"]').val())
            $('#slctTipoEmprSupeOS').trigger("change");
            $('#slctEmprSupeOS').val($('#txtIdLocadorOS').val());
        } else if ($('#txtIdSupervisoraEmpresaOS').val() != '' && $('#txtIdSupervisoraEmpresaOS').val() != undefined) {
            $('#slctTipoEmprSupeOS').val($('#slctTipoEmprSupeOS').find('option[codigo="' + constant.empresaSupervisora.personaJuridica + '"]').val())
            $('#slctTipoEmprSupeOS').trigger("change");
            $('#slctEmprSupeOS').val($('#txtIdSupervisoraEmpresaOS').val());
        }
    },
    fxAsignar: {
        validaAsignarOS: function() {
            if ($('#frmOS').validateAllForm("#divMensajeValidaFrmOS")) {
                confirmer.open(
                        "¿Confirma que desea Generar una Orden de Servicio?",
                        "coOrSeOrSe.fxAsignar.procesaAsignarOrdenServicio()");
            }
        },
        procesaAsignarOrdenServicio: function() {
            loading.open();
            $.getJSON(
                    baseURL + "pages/expediente/asignarOrdenServicio",
                    {
                        idExpediente: $('#txtIdExpedienteOS').val(),
                        'numeroExpediente': $('#txtNumeroExpedienteOS').val(),
                        'flujoSiged.nombreFlujoSiged': $('#slctIdFlujoSigedOS').find(':checked').text(),
                        asuntoSiged: $('#txtAsuntoSigedOS').val(),
                        'personal.idPersonal': $('#idPersonalSesion').val(),
                        'tramite.idTramite': $('#slctIdTramiteOS').val(),
                        'proceso.idProceso': $('#slctIdProcesoOS').val(),
                        'ordenServicio.idTipoAsignacion': $('#cmbTipoAsigOS').val(),
                        'obligacionTipo.idObligacionTipo': $('#cmbTipoSupervisionOS').val(),                        
                        'obligacionSubTipo.idObligacionSubTipo': $('#cmbSubTipoSupervisionOS').val(),
                        'unidadSupervisada.idUnidadSupervisada': $('#slctUnidSupeOS').val(),
                        codigoTipoSupervisor: $('#slctTipoEmprSupeOS').find(':checked').attr('codigo'),
                        'ordenServicio.locador.idLocador': $('#slctEmprSupeOS').val(),
                        'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa': $('#slctEmprSupeOS').val()
                    }
            ).done(function(data) {
                loading.close();
                if (data.resultado == '0') {
                    $("#dialogOrdenServicio").dialog('close');
                    confirmer.open("Se generó satisfactoriamente la  Orden de Servicio: <b>" + data.expediente.ordenServicio.numeroOrdenServicio + "</b>", "", {textAceptar: '', textCancelar: 'Cerrar', title: 'Orden de Servicio'});
                    // recargar grillas involucradas
                    espeDeriDeri.procesarGridDerivado();
                    espeAsigAsig.procesarGridAsignacion();
                    espeNotiArchNotiArch.procesarGridArchivadoNotificado();
                } else {
                    mensajeGrowl("error", data.mensaje, "");
                }
            }).fail(errorAjax);
        }
    },
    fxAtender: {
        validaAtenderOS: function() {
            var idSupervision = '';
            var mensajeRespuesta = "";
            if ($('#txtFlagSupervision').val() == constant.supervision.realiza) {
                idSupervision = coOrSeOrSe.fxAtender.validaExisteSupervision();
                if (idSupervision != null) {
                    if (idSupervision != '') {
                        datos = coOrSeOrSe.fxAtender
                                .validaRegistroSupervision(idSupervision);
                        if (datos != null) {
                            mensajeRespuesta = datos.mensaje;
                            if (datos.validoSupervision) {
                                mensajeRespuesta = mensajeRespuesta == '' ? mensajeRespuesta
                                        : mensajeRespuesta + "<br>";
                                confirmer
                                        .open(
                                        mensajeRespuesta
                                        + "¿Confirma que desea cerrar la Orden de Servicio?",
                                        "coOrSeOrSe.fxAtender.procesaAtenderOrdenServicio()");
                            } else {
                                confirmer.open(mensajeRespuesta + "<br>", "", {
                                    textAceptar: '',
                                    textCancelar: 'Cerrar',
                                    title: 'Confirmación'
                                });
                            }
                        }
                    }
                }
            } else {
                confirmer.open(
                        "¿Confirma que desea cerrar la Orden de Servicio?",
                        "coOrSeOrSe.fxAtender.procesaAtenderOrdenServicio()");
            }
        },
        procesaAtenderOrdenServicio: function() {
            loading.open();
            $.getJSON(
                    baseURL + "pages/ordenServicio/atender",
                    {
                        idExpediente: $('#txtIdExpedienteOS').val(),
                        numeroExpediente: $('#txtNumeroExpedienteOS').val(),
                        'ordenServicio.idOrdenServicio': $('#txtIdOrdenServicioOS').val(),
                        idPersonalOri: $('#idPersonalSesion').val(),
                        flagSupervision: $('#txtFlagSupervision').val(),
                        'empresaSupervisada.idEmpresaSupervisada': $('#txtIdEmpresaSupervisadaOS').val(),
                        'flujoSiged.codigoSiged': $('#slctIdFlujoSigedOS').find(':checked').attr('codigoSiged'),
                        asuntoSiged: $('#txtAsuntoSigedOS').val(),
                        /* OSINE_SFS-Ajustes - mdiosesf - Inico */
                        'ordenServicio.numeroOrdenServicio': $('#lblNumeroOrdenServicioAte').text()
                        /* OSINE_SFS-Ajustes - mdiosesf - Fin */
                    }).done(function(data) {
                loading.close();               
                if (data.resultado == '0') {
                    $("#dialogOrdenServicio").dialog('close');
                    mensajeGrowl("success", constant.confirm.save, "");
                    // recargar grillas involucradas
                    supeAsigAsig.procesarGridAsignacion();
                } else {
                    mensajeGrowl("error", data.mensaje, "");
                }
            }).fail(errorAjax);
        },
        validaExisteSupervision: function() {
            var idSupervision = '';
            $.ajax({
                url: baseURL + "pages/supervision/findSupervision",
                type: 'post',
                async: false,
                dataType: 'json',
                data: {
                    idOrdenServicio: $('#txtIdOrdenServicioOS').val()
                },
                beforeSend: function() {
                    loading.open();
                },
                success: function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                        if (data.supervision != null) {
                            idSupervision = data.supervision.idSupervision;
                        }
                    } else {
                        mensajeGrowl("error", data.mensaje, "");
                        idSupervision = null;
                    }
                },
                error: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
            return idSupervision;
        },
        validaRegistroSupervision: function(idSupervision) {
            var datos = null;
            loading.open();
            $.ajax({
                url: baseURL + "pages/supervision/validarRegistroSupervision",
                type: 'post',
                async: false,
                dataType: 'json',
                data: {
                    enPantalla: '0',
                    idSupervision: idSupervision,
                    'ordenServicioDTO.expediente.obligacionTipo.idObligacionTipo':$('#txtIdObligacionTipo').val(),
                    'ordenServicioDTO.expediente.unidadSupervisada.actividad.idActividad':$('#txtIdActividad').val(),
                    'ordenServicioDTO.expediente.proceso.idProceso':$('#txtIdProceso').val()
                },
                success: function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                        datos = data;
                    } else {
                        mensajeGrowl("error", data.mensaje, "");
                    }
                },
                error: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
            return datos;
        }
    },
    fxRevisar: {
        validaRevisarOS: function() {
            /* OSINE_SFS-480 - RSIS 14 - Inicio */
            confirmer.open("¿Desea dar conformidad a la Orden de Servicio?",
                    /* OSINE_SFS-480 - RSIS 14 - Fin */
                    "coOrSeOrSe.fxRevisar.procesaRevisarOrdenServicio()");
            // $("#dialogOrdenServicio").dialog('close');
        },
        procesaRevisarOrdenServicio: function() {
            loading.open();
            $.getJSON(baseURL + "pages/ordenServicio/revisar", {
                idExpediente: $('#txtIdExpedienteOS').val(),
                idOrdenServicio: $('#txtIdOrdenServicioOS').val(),
                idPersonalOri: $('#idPersonalSesion').val()
                        // htorress - RSIS 18 - Inicio
                        ,
                numeroExpediente: $('#txtNumeroExpedienteOS').val(),
                asuntoSiged: $('#txtAsuntoSigedOS').val()
                        // htorress - RSIS 18 - Fin
            }).done(function(data) {
                loading.close();
                if (data.resultado == '0') {
                    $("#dialogOrdenServicio").dialog('close');
                    mensajeGrowl("success", constant.confirm.save, "");
                    // recargar grillas involucradas
                    espeEvalEval.procesarGridEvaluacion();
                } else {
                    mensajeGrowl("error", data.mensaje, "");
                }
            }).fail(errorAjax);
        }
    },
    fxAprobar: {
        validaAprobarOS: function() {
            confirmer.open("¿Confirma que desea Aprobar la Orden de Servicio?",
                    "coOrSeOrSe.fxAprobar.procesaAprobarOrdenServicio()");
        },
        procesaAprobarOrdenServicio: function() {
            loading.open();
            $.getJSON(baseURL + "pages/ordenServicio/aprobar", {
                idExpediente: $('#txtIdExpedienteOS').val(),
                idOrdenServicio: $('#txtIdOrdenServicioOS').val(),
                idPersonalOri: $('#idPersonalSesion').val()
                        // htorress - RSIS 18 - Inicio
                        ,
                numeroExpediente: $('#txtNumeroExpedienteOS').val(),
                asuntoSiged: $('#txtAsuntoSigedOS').val()
                        // htorress - RSIS 18 - Fin
            }).done(function(data) {
                loading.close();
                if (data.resultado == '0') {
                    $("#dialogOrdenServicio").dialog('close');
                    mensajeGrowl("success", constant.confirm.save, "");
                    // recargar grillas involucradas
                    espeEvalEval.procesarGridEvaluacion();
                } else {
                    mensajeGrowl("error", data.mensaje, "");
                }
            }).fail(errorAjax);
        }
    },
    fxNotificar: {
        validaNotificarOS: function() {
            /* OSINE_SFS-480 - RSIS 04 - Inicio */
            //confirmer.open("¿Confirma que desea Notificar la Orden de Servicio?","coOrSeOrSe.fxNotificar.procesaNotificarOrdenServicio()");
            $.ajax({
                url: baseURL + "pages/bandeja/abrirEnviarMensajeria",
                type: 'get',
                async: false,
                data: {
                    idExpediente: $('#txtIdExpedienteOS').val(),
                    numeroExpediente: $('#txtNumeroExpedienteOS').val(),
                    'ordenServicio.idOrdenServicio': $('#txtIdOrdenServicioOS').val(),
                    flagNotificar: 1
                },
                beforeSend: loading.open,
                success: function(data) {
                    loading.close();
                    $("#dialogEnviarMensajeria").html(data);
                    $("#dialogEnviarMensajeria").dialog({
                        resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height: "auto",
                        width: "auto",
                        modal: true,
                        dialogClass: 'dialog',
                        title: "ENVÍO DE MENSAJERIA",
                        close: function() {
                            $("#dlgCliente").dialog("destroy");
                        }
                    });
                    coOrSeOrSeEnMe.autoComplete();
                },
                error: errorAjax
            });
            /* OSINE_SFS-480 - RSIS 04 - Fin */
        },
        procesaNotificarOrdenServicio: function() {
            loading.open();
            $.getJSON(baseURL + "pages/ordenServicio/notificar", {
                idExpediente: $('#txtIdExpedienteOS').val(),
                idOrdenServicio: $('#txtIdOrdenServicioOS').val(),
                idPersonalOri: $('#idPersonalSesion').val()
            }).done(function(data) {
                loading.close();
                if (data.resultado == '0') {
                    $("#dialogOrdenServicio").dialog('close');
                    /* OSINE_SFS-480 - RSIS 04 - Inicio */
                    //mensajeGrowl("success", constant.confirm.save, "");
                    /* OSINE_SFS-480 - RSIS04 - Fin */
                    // recargar grillas involucradas
                    espeEvalEval.procesarGridEvaluacion();
                    espeNotiArchNotiArch.procesarGridArchivadoNotificado();
                } else {
                    mensajeGrowl("error", data.mensaje, "");
                }
            }).fail(errorAjax);
        }
    },
    fxConcluir: {
        validaConcluirOS: function() {
            confirmer.open(
                    "¿Confirma que desea Concluir la Orden de Servicio?",
                    "coOrSeOrSe.fxConcluir.procesaConcluirOrdenServicio()");
        },
        procesaConcluirOrdenServicio: function() {
            loading.open();
            $.getJSON(baseURL + "pages/ordenServicio/concluir", {
                idExpediente: $('#txtIdExpedienteOS').val(),
                idOrdenServicio: $('#txtIdOrdenServicioOS').val(),
                idPersonalOri: $('#idPersonalSesion').val(),
                numeroExpediente: $('#txtNumeroExpedienteOS').val(), // htorress - RSIS 18 - Inicio
                idArchivo: $('#slctOficio').val(),
                /* OSINE_SFS-480 - RSIS 17 - Inicio */
                fechaInicioPlazoDescargo: $('#fechaRecepcion').val()
                        /* OSINE_SFS-480 - RSIS 17 - Fin */
            }).done(function(data) {
                loading.close();
                if (data.resultado == '0') {
                    $("#dialogOrdenServicio").dialog('close');
                    mensajeGrowl("success", constant.confirm.save, "");
                    // recargar grillas involucradas
                    espeNotiArchNotiArch.procesarGridArchivadoNotificado();
                    espeAsigAsig.procesarGridAsignacion();
                } else {
                    mensajeGrowl("error", data.mensaje, "");
                }
            }).fail(errorAjax);
        },
        /* OSINE_SFS-480 - RSIS 17 - Inicio */
        cargarDocumentosOrdenServicio: function(idSlctDest) {
            $
                    .ajax({
                url: baseURL + "pages/archivo/findArchivoExpedienteOS",
                type: 'get',
                dataType: 'json',
                async: false,
                data: {
                    numeroExpediente: $('#txtNumeroExpedienteOS')
                            .val(),
                    idOrdenServicio: $('#txtIdOrdenServicioOS').val()
                },
                beforeSend: loading.open,
                success: function(data) {
                    loading.close();
                    var existe = false;
                    for (i = 0; i < data.ArchivoExpediente.length; i++) {
                        existe = false;
                        for (x = 0; x < data.MaestroPlazo.length; x++) {
                            if (data.ArchivoExpediente[i].idTipoDocumento.codigo == data.MaestroPlazo[x].codigo) {
                                existe = true;
                                break;
                            }
                        }
                        if (!existe) {
                            data.ArchivoExpediente.splice(i, 1);
                            i--;
                        }
                    }
                    fill.comboValorId(data.ArchivoExpediente, "idArchivo", "nombreArchivo", idSlctDest, "-1");
                    $("#divOficioFechaFin").dialog({
                        resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height: "auto",
                        width: "auto",
                        modal: true,
                        dialogClass: 'dialog',
                        closeText: "Cerrar",
                        title: 'FECHA RECEPCION USUARIO - MENSAJERIA',
                        close: function() {
                            $("#divOficioFechaFin").dialog("destroy");
                        }
                    });
                    $("#fechaRecepcion").val("");
                },
                error: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
        }
        /* OSINE_SFS-480 - RSIS17 - Fin */
    },
    fxObservar: {
        observarOS: function() {
            $('#divFormOS').hide("blind");
            $('#btnObservarOS,#btnsAccPrinc').hide();
            $('#formObserva,#btnConfirmaObservarOS').show();
        },
        validaObservarOS: function() {
            if ($('#frmObservarOS').validateAllForm("#divMensajeValidaObserva")) {
                confirmer
                        .open(
                        "¿Confirma que desea Observar/Devolver la Orden de Servicio?",
                        "coOrSeOrSe.fxObservar.procesaObservarOrdenServicio()");
            }
        },
        procesaObservarOrdenServicio: function() {
            $.ajax({
                url: baseURL + "pages/ordenServicio/observar",
                type: 'post',
                async: false,
                dataType: 'json',
                data: {
                    idExpediente: $('#txtIdExpedienteOS').val(),
                    idOrdenServicio: $('#txtIdOrdenServicioOS').val(),
                    idPersonalOri: $('#idPersonalSesion').val(),
                    motivoReasignacion: $('#txtMotivoObserOS').val()
                            // htorress - RSIS 18 - Inicio
                            ,
                    asuntoSiged: $('#txtAsuntoSigedOS').val(),
                    numeroExpediente: $('#txtNumeroExpedienteOS').val()
                            // htorress - RSIS 18 - Fin
                },
                beforeSend: loading.open,
                success: function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                        $("#dialogOrdenServicio").dialog('close');
                        mensajeGrowl("success", constant.confirm.save, "");
                        // recargar grillas involucradas
                        espeEvalEval.procesarGridEvaluacion();
                    } else {
                        mensajeGrowl("error", data.mensaje, "");
                    }
                },
                error: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
        }
    },
    fxObservarAprobar: {
        observarOS: function() {
            $('#divFormOS').hide("blind");
            $('#btnObservarOSAprobar,#btnsAccPrinc').hide();
            $('#formObserva,#btnConfirmaObservarOSAprobar').show();
        },
        validaObservarOS: function() {
            if ($('#frmObservarOS').validateAllForm("#divMensajeValidaObserva")) {
                confirmer
                        .open(
                        "¿Confirma que desea Observar/Devolver la Orden de Servicio?",
                        "coOrSeOrSe.fxObservarAprobar.procesaObservarOrdenServicio()");
            }
        },
        procesaObservarOrdenServicio: function() {
            $.ajax({
                url: baseURL + "pages/ordenServicio/observarAprobar",
                type: 'post',
                async: false,
                dataType: 'json',
                data: {
                    'expediente.idExpediente' : $('#txtIdExpedienteOS').val(),
                    idOrdenServicio : $('#txtIdOrdenServicioOS').val(),
                    idPersonalOri : $('#idPersonalSesion').val(),
                    motivoReasignacion : $('#txtMotivoObserOS').val(),
                    // htorress - RSIS 18 - Inicio
                    'expediente.asuntoSiged' : $('#txtAsuntoSigedOS').val(),
                    'expediente.numeroExpediente' : $('#txtNumeroExpedienteOS').val(),
                    // htorress - RSIS 18 - Fin
                    'ordenServicioDTO.expediente.obligacionTipo.idObligacionTipo':$('#txtIdObligacionTipo').val(),
                    'ordenServicioDTO.expediente.unidadSupervisada.actividad.idActividad':$('#txtIdActividad').val(),
                    'ordenServicioDTO.expediente.proceso.idProceso':$('#txtIdProceso').val()
                },
                beforeSend: loading.open,
                success: function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                        $("#dialogOrdenServicio").dialog('close');
                        mensajeGrowl("success", constant.confirm.save, "");
                        // recargar grillas involucradas
                        espeEvalEval.procesarGridEvaluacion();
                    } else {
                        mensajeGrowl("error", data.mensaje, "");
                    }
                },
                error: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
        }
    },
    fxGenerar: {
        validaGenerarOS: function() {
            if ($('#frmOS').validateAllForm("#divMensajeValidaFrmOS")) {
                confirmer.open(
                        "¿Confirma que desea generar una Orden de Servicio?",
                        "coOrSeOrSe.fxGenerar.procesaGenerarOrdenServicio()");
            }
        },
        procesaGenerarOrdenServicio: function() {
            loading.open();
            $
                    .getJSON(
                    baseURL
                    + "pages/expediente/generarExpedienteOrdenServicio",
                    {
                        'flujoSiged.idFlujoSiged': $(
                                '#slctIdFlujoSigedOS').val(),
                        'flujoSiged.codigoSiged': $(
                                '#slctIdFlujoSigedOS').find(':checked')
                                .attr('codigoSiged'),
                        asuntoSiged: $('#txtAsuntoSigedOS').val(),
                        'proceso.idProceso': $('#slctIdProcesoOS')
                                .val(),
                        'ordenServicio.idTipoAsignacion': $(
                                '#cmbTipoAsigOS').val(),
                        'obligacionTipo.idObligacionTipo': $(
                                '#cmbTipoSupervisionOS').val(),
                        'empresaSupervisada.idEmpresaSupervisada': $(
                                '#txtIdEmpresaSupervisadaOS').val(),
                        'unidadSupervisada.idUnidadSupervisada': $(
                                '#txtIdUnidadSupervisadaOS').val(),
                        'personal.idPersonal': $('#idPersonalSesion')
                                .val(),
                        codigoTipoSupervisor: $('#slctTipoEmprSupeOS')
                                .find(':checked').attr('codigo'),
                        'ordenServicio.locador.idLocador': $(
                                '#slctEmprSupeOS').val(),
                        'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa': $(
                                '#slctEmprSupeOS').val()
                    })
                    .done(
                    function(data) {
                        loading.close();
                        if (data.resultado == '0') {
                            $("#dialogOrdenServicio").dialog('close');
                            confirmer
                                    .open(
                                    "Se generó satisfactoriamente la  Orden de Servicio: <b>"
                                    + data.expediente.ordenServicio.numeroOrdenServicio
                                    + "</b>",
                                    "",
                                    {
                                        textAceptar: '',
                                        textCancelar: 'Cerrar',
                                        title: 'Orden de Servicio'
                                    });
                            // recargar grillas involucradas
                            espeAsigAsig.procesarGridAsignacion();
                            espeNotiArchNotiArch
                                    .procesarGridArchivadoNotificado();// TEMPORAL
                            // pq
                            // el
                            // expediente
                            // desparece
                            // al
                            // confirmar
                            // descargo,
                            // pero
                            // luego
                            // reaparece
                            // al
                            // asignar
                            // os
                        } else {
                            mensajeGrowl("error", data.mensaje, "");
                        }
                    }).fail(errorAjax);
        },
        procBuscarClienteOS: function() {
            $('#codigoOsinergOS').removeClass("error");
            if ($('#codigoOsinergOS').val() == '') {
                $('#codigoOsinergOS').addClass("error");
                return false;
            } else {
                $('#txtIdActividad,#txtIdUnidadSupervisadaOS,#txtRucUnidadSupervisadaOS,#txtRubroOS,#txtDireOperOS,#nroDocumentoOS,#razonSocialOS,#txtIdDepartamentoOS,#txtIdProvinciaOS,#txtIdDistritoOS').val('');
                $.ajax({
                    url: baseURL + "pages/unidadSupervisada/cargaDataUnidadOperativa",
                    type: 'get',
                    async: false,
                    dataType: 'json',
                    data: {
                        cadCodigoOsinerg: $('#codigoOsinergOS').val(),
                    },
                    beforeSend: loading.open,
                    success: function(data) {
                        loading.close();
                        if (data.resultado == "0") {
                            $('#txtIdEmpresaSupervisadaOS').val(data.registro.empresaSup.idEmpresaSupervisada);
                            $('#txtIdUnidadSupervisadaOS').val(data.registro.idUnidadSupervisada);
                            $('#txtRucUnidadSupervisadaOS').val(data.registro.ruc);
                            /* OSINE_SFS-480 - RSIS 11 - Inicio */
                            $('#txtIdActividad').val(data.registro.idActividad);
                            /* OSINE_SFS-480 - RSIS 11 - Fin */
                            $('#txtRubroOS').val(data.registro.actividad);
                            /* OSINE_SFS-480 - RSIS 11 - Inicio */
                            if (data.registro.direccionUnidadsupervisada.direccionCompleta != null && data.registro.direccionUnidadsupervisada.direccionCompleta != undefined) {
                                $('#txtDireOperOS').val(data.registro.direccionUnidadsupervisada.direccionCompleta);
                                $('#txtIdDepartamentoOS').val(data.registro.direccionUnidadsupervisada.departamento.idDepartamento);
                                $('#txtIdProvinciaOS').val(data.registro.direccionUnidadsupervisada.provincia.idProvincia);
                                $('#txtIdDistritoOS').val(data.registro.direccionUnidadsupervisada.distrito.idDistrito);
                            }
                            /* OSINE_SFS-480 - RSIS 11 - Fin */
                            $('#nroDocumentoOS').val(data.registro.ruc != '' ? "RUC - " + data.registro.ruc : data.registro.empresaSup.tipoDocumentoIdentidad.descripcion + " - " + data.registro.empresaSup.nroIdentificacion);
                            $('#razonSocialOS').val(data.registro.razonSocial);
                            /* OSINE_SFS-480 - RSIS 26 - Inicio */
                            changeProceso = false;
                            changeTipoSupervision = false;
                            changeRubro = true;
                            changeUbigeo = true;
                            coOrSeOrSe.armaValidateFiltros(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);
                            /* OSINE_SFS-480 - RSIS 11 - Inicio */
                            //coOrSeOrSe.activarSlctTipoEmprSupeOS(); 
                            /* OSINE_SFS-480 - RSIS 11 - Fin */
                            /* OSINE_SFS-480 - RSIS 26 - Fin */
                        } else {
                            mensajeGrowl("error", "No se encuentra la Unidad Operativa ingresada.", "");
                        }
                    },
                    error: function(jqXHR) {
                        errorAjax(jqXHR);
                    }
                });
            }
        },
    },
    fxConfirmarDescargo: {
        validaConfirmarDescargoOS: function() {
            confirmer
                    .open(
                    "¿Confirma que se realizaron los Descargos respectivos?",
                    "coOrSeOrSe.fxConfirmarDescargo.procesaConfirmarDescargoOrdenServicio()");
        },
        procesaConfirmarDescargoOrdenServicio: function() {
            loading.open();
            $.getJSON(baseURL + "pages/ordenServicio/confirmarDescargo", {
                idExpediente: $('#txtIdExpedienteOS').val(),
                idOrdenServicio: $('#txtIdOrdenServicioOS').val(),
                idPersonalOri: $('#idPersonalSesion').val(),
                numeroExpediente: $('#txtNumeroExpedienteOS').val()
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
    /* OSINE_SFS-480 - RSIS 42 - Inicio */
    fxAnular: {
        validaAnularOS: function() {
            confirmer.open("¿Confirma que desea Anular la Orden de Servicio?", "coOrSeOrSe.fxAnular.procesaAnularOrdenServicio()");
        },
        procesaAnularOrdenServicio: function() {
            loading.open();
            $.getJSON(baseURL + "pages/ordenServicio/anular",
                    {
                        idExpediente: $('#txtIdExpedienteOS').val(),
                        numeroExpediente: $('#txtNumeroExpedienteOS').val(),
                        'ordenServicio.idOrdenServicio': $('#txtIdOrdenServicioOS').val(),
                        idPersonalOri: $('#idPersonalSesion').val(),
                        flagSupervision: $('#txtFlagSupervision').val(),
                        'empresaSupervisada.idEmpresaSupervisada': $('#txtIdEmpresaSupervisadaOS').val(),
                        'flujoSiged.codigoSiged': $('#slctIdFlujoSigedOS').find(':checked').attr('codigoSiged'),
                        asuntoSiged: $('#txtAsuntoSigedOS').val(),
                        'ordenServicio.comentarioDevolucion': $('#txtComentarioAnulacion').val(),
                    })
                    .done(function(data) {
                loading.close();
                if (data.resultado == '0') {
                    $("#dialogOrdenServicio").dialog('close');
                    mensajeGrowl("success", data.mensaje, "");
                    // Recargar grillas involucradas
                    espeAsigAsig.procesarGridAsignacion();
                    espeDeriDeri.procesarGridDerivado();
                } else {
                    mensajeGrowl("error", data.mensaje, "");
                }
            })
                    .fail(errorAjax);
        },
    },
    /* OSINE_SFS-480 - RSIS 42 - Fin */
    cargaDataUnidadOperativa: function(fill) {
        $.ajax({
            url: baseURL + "pages/unidadSupervisada/cargaDataUnidadOperativa",
            type: 'get',
            async: false,
            dataType: 'json',
            data: {
                cadIdUnidadSupervisada: $('#slctUnidSupeOS').val()
            },
            before: function() {
                loading.open();
            },
            success: function(data) {
                loading.close();
                fill(data);
            },           
            error: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },  
    fillDataUnidadOperativa: function(data) {
        $('#txtRubroOS,#txtDireOperOS').val("");
        /* OSINE_SFS-480 - RSIS 11 - Inicio */
        if (data != null && data.registro != null) {
            $('#txtIdActividad').val(data.registro.idActividad);
            $('#txtRubroOS').val(data.registro.actividad);
            $('#txtIdUnidadSupervisadaOS').val(data.registro.idUnidadSupervisada);
            $('#txtRucUnidadSupervisadaOS').val(data.registro.ruc);
            
            if (data.registro.direccionUnidadsupervisada.direccionCompleta != null && data.registro.direccionUnidadsupervisada.direccionCompleta != undefined) {
                $('#txtDireOperOS').val(data.registro.direccionUnidadsupervisada.direccionCompleta);
                $('#txtIdDepartamentoOS').val(data.registro.direccionUnidadsupervisada.departamento.idDepartamento);
                $('#txtIdProvinciaOS').val(data.registro.direccionUnidadsupervisada.provincia.idProvincia);
                $('#txtIdDistritoOS').val(data.registro.direccionUnidadsupervisada.distrito.idDistrito);
            }

            /* OSINE_SFS-480 - RSIS 26 - Inicio */
            if ($('#tipoAccionOS').val() == constant.accionOS.asignar ||
                    $('#tipoAccionOS').val() == constant.accionOS.generar ||
                    $('#tipoAccionOS').val() == constant.accionOS.editar) {
                changeProceso = false;
                changeTipoSupervision = false;
                changeRubro = false;
                changeUbigeo = false;
                coOrSeOrSe.armaValidateFiltros(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);
            }
            /* OSINE_SFS-480 - RSIS 26 - Fin */
        }
        /* OSINE_SFS-480 - RSIS 11 - Fin */
    },
    activarSlctTipoEmprSupeOS: function(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo) {
        /* OSINE_SFS-480 - RSIS 26 - Inicio */
        if ($('#slctIdProcesoOS').val() == '' || $('#cmbTipoSupervisionOS').val() == '' || $('#txtIdActividad').val() == '' || $('#txtIdDepartamentoOS').val() == '' || $('#txtIdProvinciaOS').val() == '' || $('#txtIdDistritoOS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeTipoSupervision || changeRubro || changeUbigeo) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
        /* OSINE_SFS-480 - RSIS 26 - Fin */
    },
    /* OSINE_SFS-480 - RSIS 47 - Inicio */
    fxEditar: {
        validaEditarOS: function() {
            if ($('#frmOS').validateAllForm("#divMensajeValidaFrmOS")) {
                confirmer.open("¿Confirma que desea Editar la Orden de Servicio?", "coOrSeOrSe.fxEditar.procesaEditarOrdenServicio()");
            }
        },
        procesaEditarOrdenServicio: function() {
            loading.open();
            $.getJSON(baseURL + "pages/expediente/editarExpedienteOrdenServicio",
                    {
                        'idExpediente': $('#txtIdExpedienteOS').val(),
                        'ordenServicio.idOrdenServicio': $('#txtIdOrdenServicioOS').val(),
                        'ordenServicio.numeroOrdenServicio': $('#lblNumeroOrdenServicio').text(),
                        'flujoSiged.idFlujoSiged': $('#slctIdFlujoSigedOS').val(),
                        asuntoSiged: $('#txtAsuntoSigedOS').val(),
                        'flujoSiged.codigoSiged': $('#slctIdFlujoSigedOS').find(':checked').attr('codigoSiged'),
                        'flujoSiged.nombreFlujoSiged': $('#slctIdFlujoSigedOS').find(':checked').text(),
                        'proceso.idProceso': $('#slctIdProcesoOS').val(),
                        'ordenServicio.idTipoAsignacion': $('#cmbTipoAsigOS').val(),
                        'obligacionTipo.idObligacionTipo': $('#cmbTipoSupervisionOS').val(),
                        'obligacionSubTipo.idObligacionSubTipo': $('#cmbSubTipoSupervisionOS').val(),
//                        'empresaSupervisada.idEmpresaSupervisada': $('#txtIdEmpresaSupervisadaOS').val(),
                        'unidadSupervisada.idUnidadSupervisada': $('#txtIdUnidadSupervisadaOS').val(),
                        'unidadSupervisada.ruc': $('#txtRucUnidadSupervisadaOS').val(),
                        'personal.idPersonal': $('#idPersonalSesion').val(),
                        codigoTipoSupervisor: $('#slctTipoEmprSupeOS').find(':checked').attr('codigo'),
                        'ordenServicio.locador.idLocador': $('#slctEmprSupeOS').val(),
                        'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa': $('#slctEmprSupeOS').val(),
                        'numeroExpediente': $('#txtNumeroExpedienteOS').val(),
//                        'empresaSupervisada.ruc': $('#txtRUCSupervisoraEmpresaOS').val(),
                        'unidadSupervisada.actividad.idActividad':$('#txtIdActividad').val(),
                        flagCambioEmpresaSupervisora: ($('#txtCodigoTipoSupervisor').val() != $('#slctTipoEmprSupeOS').find(':checked').attr('codigo')) ||
                                ($('#txtCodigoTipoSupervisor').val() == $('#slctTipoEmprSupeOS').find(':checked').attr('codigo') &&
                                        $('#slctEmprSupeOS').val() != $('#txtIdLocadorOS').val() + $('#txtIdSupervisoraEmpresaOS').val()) ? 1 : 0,
                        flagEvaluaTipoAsignacion:$('#flagEvaluaTipoAsignacion').val()
                        
            })
                    .done(function(data) {
                loading.close();

                if (data.resultado == '0') {
                    $("#dialogOrdenServicio").dialog('close');
                    mensajeGrowl("success", constant.confirm.edit, "");
                    //recargar grillas involucradas
                    espeAsigAsig.procesarGridAsignacion();
                } else {
                    mensajeGrowl("error", data.mensaje, "");
                }
            })
                    .fail(errorAjax);
        },            
        procBuscarClienteOSEditar: function() {
            $('#codigoOsinergOS').removeClass("error");
            if ($('#codigoOsinergOS').val() == '') {
                $('#codigoOsinergOS').addClass("error");
                return false;
            } else {
                $('#txtIdActividad,#txtIdUnidadSupervisadaOS,#txtRucUnidadSupervisadaOS,#txtRubroOS,#txtDireOperOS,#nroDocumentoOS,#razonSocialOS,#txtIdDepartamentoOS,#txtIdProvinciaOS,#txtIdDistritoOS').val('');
                $.ajax({
                    url: baseURL + "pages/unidadSupervisada/cargaDataUnidadOperativa",
                    type: 'get',
                    async: false,
                    dataType: 'json',
                    data: {
                        cadCodigoOsinerg: $('#codigoOsinergOS').val(),
                    },
                    beforeSend: loading.open,
                    success: function(data) {
                        loading.close();
                        if (data.resultado == "0") {
//                            $('#txtIdEmpresaSupervisadaOS').val(data.registro.empresaSup.idEmpresaSupervisada);
                            $('#txtIdUnidadSupervisadaOS').val(data.registro.idUnidadSupervisada);
                            $('#txtRucUnidadSupervisadaOS').val(data.registro.ruc);
                            $('#txtIdActividad').val(data.registro.idActividad);
                            $('#txtRubroOS').val(data.registro.actividad);
                            if (data.registro.direccionUnidadsupervisada.direccionCompleta != null && data.registro.direccionUnidadsupervisada.direccionCompleta != undefined) {
                                $('#txtDireOperOS').val(data.registro.direccionUnidadsupervisada.direccionCompleta);
                                $('#txtIdDepartamentoOS').val(data.registro.direccionUnidadsupervisada.departamento.idDepartamento);
                                $('#txtIdProvinciaOS').val(data.registro.direccionUnidadsupervisada.provincia.idProvincia);
                                $('#txtIdDistritoOS').val(data.registro.direccionUnidadsupervisada.distrito.idDistrito);
                            }
                            $('#nroDocumentoOS').val(data.registro.ruc != '' ? "RUC - " + data.registro.ruc : data.registro.empresaSup.tipoDocumentoIdentidad.descripcion + " - " + data.registro.empresaSup.nroIdentificacion);
                            $('#razonSocialOS').val(data.registro.empresaSup.razonSocial);
                            /* OSINE_SFS-480 - RSIS 26 - Inicio */
                            changeProceso = false;
                            changeTipoSupervision = false;
                            changeRubro = true;
                            changeUbigeo = true;
                            coOrSeOrSe.armaValidateFiltros(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);//coOrSeOrSe.activarSlctTipoEmprSupeOS();
                            /* OSINE_SFS-480 - RSIS 26 - Fin */
                        } else {
                            mensajeGrowl("error", "No se encuentra la Unidad Operativa ingresada.", "");
                        }
                    },
                    error: function(jqXHR) {
                        errorAjax(jqXHR);
                    }
                });
            }
        }
    },  
    /* OSINE_SFS-480 - RSIS 26 - Inicio */
    armaValidateFiltros: function(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo) {
        str = $('#txtFiltrosOSI').val();
        array = str.split(',');
        contadorXfiltro = 0;
        supervision = 0;
        rubro = 0;
        ubigeo = 0;
        proceso = 0;
        for (var i = 0; i < array.length; i++) {
            if (array[i] == "OBLI") {
                contadorXfiltro++;
                supervision = 1;
            }
            if (array[i] == "RUBR") {
                contadorXfiltro++;
                rubro = 1;
            }
            if (array[i] == "UBIG") {
                contadorXfiltro++;
                ubigeo = 1;
            }
            if (array[i] == "PROC") {
                contadorXfiltro++;
                proceso = 1;
            }
        }
        coOrSeOrSe.armaCondicionFiltros(contadorXfiltro, supervision, rubro, ubigeo, proceso, changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);

    },
    armaCondicionFiltros: function(contadorXfiltro, supervision, rubro, ubigeo, proceso, changeProceso, changeTipoSupervision, changeRubro, changeUbigeo) {
        switch (contadorXfiltro) {
            case 0:
                coOrSeOrSe.armaCondicionCeroFiltros();
                break;
            case 1:
                if (supervision == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxUnoTipoSupervision(changeTipoSupervision);
                } else if (rubro == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxUnoRubro(changeRubro);
                } else if (ubigeo == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxUnoUbigeo(changeUbigeo);
                } else if (proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxUnoProceso(changeProceso);
                }
                break;
            case 2:
                if (supervision == 1 && rubro == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxDosSR(changeTipoSupervision, changeRubro);
                } else if (supervision == 1 && ubigeo == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxDosSU(changeTipoSupervision, changeUbigeo);
                } else if (supervision == 1 && proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxDosSP(changeProceso, changeTipoSupervision);
                } else if (rubro == 1 && ubigeo == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxDosRU(changeRubro, changeUbigeo);
                } else if (rubro == 1 && proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxDosRP(changeProceso, changeRubro);
                } else if (ubigeo == 1 && proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxDosUP(changeProceso, changeUbigeo);
                }
                break;
            case 3:
                if (supervision == 1 && rubro == 1 && ubigeo == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxTresSRU(changeTipoSupervision, changeRubro, changeUbigeo);
                } else if (rubro == 1 && ubigeo == 1 && proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxTresRUP(changeProceso, changeRubro, changeUbigeo);
                } else if (supervision == 1 && rubro == 1 && proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxTresSRP(changeProceso, changeTipoSupervision, changeRubro);
                } else if (supervision == 1 && ubigeo == 1 && proceso == 1) {
                    coOrSeOrSe.armaCondicionFiltrosxTresSUP(changeProceso, changeTipoSupervision, changeUbigeo);
                }
                break;
            case 4:
                coOrSeOrSe.activarSlctTipoEmprSupeOS(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);
                break;
        }

    },
    armaCondicionCeroFiltros: function() {
        coOrSeOrSe.habilitarEmpresa();
    },
    armaCondicionFiltrosxUnoProceso: function(changeProceso) {
        if ($('#slctIdProcesoOS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxUnoTipoSupervision: function(changeTipoSupervision) {
        if ($('#cmbTipoSupervisionOS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeTipoSupervision) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxUnoRubro: function(changeRubro) {
        if ($('#txtIdActividad').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeRubro) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxUnoUbigeo: function(changeUbigeo) {
        if ($('#txtIdDepartamentoOS').val() == '' || $('#txtIdProvinciaOS').val() == '' || $('#txtIdDistritoOS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeUbigeo) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxDosSR: function(changeTipoSupervision, changeRubro) {
        if ($('#cmbTipoSupervisionOS').val() == '' || $('#txtIdActividad').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeTipoSupervision || changeRubro) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxDosSU: function(changeTipoSupervision, changeUbigeo) {
        if ($('#cmbTipoSupervisionOS').val() == '' || $('#txtIdDepartamentoOS').val() == '' || $('#txtIdProvinciaOS').val() == '' || $('#txtIdDistritoOS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeTipoSupervision || changeUbigeo) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxDosSP: function(changeProceso, changeTipoSupervision) {
        if ($('#cmbTipoSupervisionOS').val() == '' || $('#slctIdProcesoOS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeTipoSupervision) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxDosRU: function(changeRubro, changeUbigeo) {
        if ($('#txtIdActividadOS').val() == '' || $('#txtIdDepartamentoOS').val() == '' || $('#txtIdProvinciaOS').val() == '' || $('#txtIdDistritoOS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeRubro || changeUbigeo) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxDosRP: function(changeProceso, changeRubro) {
        if ($('#txtIdActividadOS').val() == '' || $('#slctIdProcesoOS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeRubro) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxDosUP: function(changeProceso, changeUbigeo) {
        if ($('#slctIdProcesoOS').val() == '' || $('#txtIdDepartamentoOS').val() == '' || $('#txtIdProvinciaOS').val() == '' || $('#txtIdDistritoOS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeUbigeo) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxTresSRU: function(changeTipoSupervision, changeRubro, changeUbigeo) {
        if ($('#cmbTipoSupervisionOS').val() == '' || $('#txtIdActividadOS').val() == '' || $('#txtIdDepartamentoOS').val() == '' || $('#txtIdProvinciaOS').val() == '' || $('#txtIdDistritoOS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeTipoSupervision || changeRubro || changeUbigeo) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxTresRUP: function(changeProceso, changeRubro, changeUbigeo) {
        if ($('#txtIdActividadOS').val() == '' || $('#txtIdDepartamentoOS').val() == '' || $('#txtIdProvinciaOS').val() == '' || $('#txtIdDistritoOS').val() == '' || $('#slctIdProcesoOS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeRubro || changeUbigeo) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxTresSRP: function(changeProceso, changeTipoSupervision, changeRubro) {
        if ($('#cmbTipoSupervisionOS').val() == '' || $('#txtIdActividadOS').val() == '' || $('#slctIdProcesoOS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeTipoSupervision || changeRubro) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    armaCondicionFiltrosxTresSUP: function(changeProceso, changeTipoSupervision, changeUbigeo) {
        if ($('#cmbTipoSupervisionOS').val() == '' || $('#txtIdDepartamentoOS').val() == '' || $('#txtIdProvinciaOS').val() == '' || $('#txtIdDistritoOS').val() == '' || $('#slctIdProcesoOSMAS').val() == '') {
            coOrSeOrSe.deshabilitarEmpresa();
        } else {
            coOrSeOrSe.habilitarEmpresa();
        }
        if (changeProceso || changeTipoSupervision || changeUbigeo) {
            $('#slctTipoEmprSupeOS,#slctEmprSupeOS').val('');
        }
    },
    habilitarEmpresa: function() {
        $("#slctTipoEmprSupeOS").removeAttr('disabled');
        $("#slctEmprSupeOS").removeAttr('disabled');
    },
    deshabilitarEmpresa: function() {
        $("#slctTipoEmprSupeOS").attr('disabled', 'disabled');
        $("#slctEmprSupeOS").attr('disabled', 'disabled');
    },
//    obtenerObligacionSubTipo : function(idTipoSupervision) {
//        $.ajax({
//                url : baseURL + "pages/serie/listarObligacionSubTipo",
//                type : 'get',
//                async : false,
//                dataType:'json',
//                data : {
//                    idObligacionTipo: idTipoSupervision
//                },
//                beforeSend:function(){loading.open();},
//                success : function(data) {
//                    loading.close();
//                    var html = '<option value="">--Seleccione--</option>';
//                    var len = data.length;
//                    for (var i = 0; i < len; i++) {
//                            html += '<option value="' + data[i].idObligacionSubTipo + '"  codigo="'+data[i].identificadorSeleccion+'" >'
//                            + data[i].nombre + '</option>';
//                    }
//                    $('#cmbSubTipoSupervisionOS').html(html);
//                },
//                error : function(jqXHR) {
//                        errorAjax(jqXHR);
//                }
//        });
//    },
    /* OSINE_SFS-480 - RSIS 26 - Fin */
    /* OSINE_SFS-480 - RSIS 47 - Fin */
    inicializaObjetosEventos: function() {
        // Fecha
        $('#fechaRecepcion').datepicker();
        /* OSINE_SFS-480 - RSIS 17 - Inicio */
        $("#fechaRecepcion").datepicker("option", "maxDate", new Date());
        $('#fechaRecepcion').change(
                function() {
                    if ($('#fechaRecepcion').val() != '') {
                        var fecInicio = $(this).datepicker('getDate');
                        fecInicioFormat = new Date(fecInicio.getTime());
                        fecInicioFormat.setDate(fecInicioFormat.getDate());
                        $('#fechaRecepcion').val($('#fechaRecepcion').val());
                    } else {
                        $('#fechaRecepcion').val('');
                        $("#fechaRecepcion").datepicker("option", "minDate",
                                new Date(0, 0, 0, 0, 0, 0));
                    }
                });

        $('#btnCerrarRecepcion').click(function() {
            $("#divOficioFechaFin").dialog("close");
        });
        /* OSINE_SFS-480 - RSIS 17 - Fin */

        // change UnidadOperativa
        $('#slctUnidSupeOS').change(function() {
            $('#txtIdActividad,#txtIdUnidadSupervisadaOS,#txtRucUnidadSupervisadaOS,#txtRubroOS,#txtDireOperOS,#txtIdDepartamentoOS,#txtIdProvinciaOS,#txtIdDistritoOS').val('');
            if ($('#slctUnidSupeOS').val() != "") {
                coOrSeOrSe.cargaDataUnidadOperativa(coOrSeOrSe.fillDataUnidadOperativa);
            }
            /* OSINE_SFS-480 - RSIS 26 - Inicio */
            if ($('#tipoAccionOS').val() == constant.accionOS.asignar ||
                    $('#tipoAccionOS').val() == constant.accionOS.generar ||
                    $('#tipoAccionOS').val() == constant.accionOS.editar) {
                changeProceso = false;
                changeTipoSupervision = false;
                changeRubro = true;
                changeUbigeo = true;
                coOrSeOrSe.armaValidateFiltros(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);
            }
            /* OSINE_SFS-480 - RSIS 26 - Fin */    
        });
        // change Serie (proceso,etapa,tramite)
        $('#slctIdProcesoOS').change(function() {
            fill.clean("#slctIdEtapaOS,#slctIdTramiteOS");
            common.serie.cargarEtapa("#slctIdProcesoOS", "#slctIdEtapaOS");
        });
        $('#slctIdEtapaOS').change(function() {
            fill.clean("#slctIdTramiteOS");
            common.serie.cargarTramite("#slctIdEtapaOS", "#slctIdTramiteOS");
        });
        // change empresa supervisora
        $('#slctTipoEmprSupeOS')
                .change(
                function() {
                    var idProceso = $("#slctIdProcesoOS").val();
                    var idObligacionTipo = $("#cmbTipoSupervisionOS").val();
                    var idRubro = $("#txtIdActividad").val();
                    var idDepartamento = $("#txtIdDepartamentoOS").val();
                    var idProvincia = $("#txtIdProvinciaOS").val();
                    var idDistrito = $("#txtIdDistritoOS").val();
                    fill.clean("#slctEmprSupeOS");
                    if ($('#slctTipoEmprSupeOS').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
                        common.empresaSupervisora.cargarLocador('#slctEmprSupeOS', idObligacionTipo, idProceso, idRubro, idDepartamento, idProvincia, idDistrito);
                    } else if ($('#slctTipoEmprSupeOS').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
                        common.empresaSupervisora.cargarSupervisoraEmpresa('#slctEmprSupeOS', idObligacionTipo, idProceso, idRubro, idDepartamento, idProvincia, idDistrito);
                    }
                });
        /* OSINE_SFS-480 - RSIS 42 - Inicio */
        //anular
        $('#btnAnularOrdenServicio').click(function() {
            coOrSeOrSe.fxAnular.validaAnularOS();
        });
        /* OSINE_SFS-480 - RSIS 42 - Fin */
        /* OSINE_SFS-480 - RSIS 47 - Inicio */
        //editar
        $('#btnEditarOrdenServicio').click(function() {
            coOrSeOrSe.fxEditar.validaEditarOS();
        });
        /* OSINE_SFS-480 - RSIS 47 - Fin */
        $('#slctIdProcesoOS').change(function() {
            /* OSINE_SFS-480 - RSIS 26 - Inicio */
            changeRubro = false;
            changeUbigeo = false;
            changeProceso = true;
            changeTipoSupervision = false;
            coOrSeOrSe.armaValidateFiltros(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);//coOrSeOrSe.activarSlctTipoEmprSupeOS();
            /* OSINE_SFS-480 - RSIS 26 - Fin */
        });
        $('#cmbTipoSupervisionOS').change(function() {
            /* OSINE_SFS-480 - RSIS 26 - Inicio */
            changeRubro = false;
            changeUbigeo = false;
            changeProceso = false;
            changeTipoSupervision = true;
            coOrSeOrSe.armaValidateFiltros(changeProceso, changeTipoSupervision, changeRubro, changeUbigeo);//coOrSeOrSe.activarSlctTipoEmprSupeOS();
            /* OSINE_SFS-480 - RSIS 26 - Fin */
        });

        // atender
        $('#btnAtenderOrdenServicio').click(function() {
            coOrSeOrSe.fxAtender.validaAtenderOS();
        });
        // revisar
        $('#btnRevisarOrdenServicio').click(function() {
            coOrSeOrSe.fxRevisar.validaRevisarOS();
        });
        // aprobar
        $('#btnAprobarOrdenServicio').click(function() {
            coOrSeOrSe.fxAprobar.validaAprobarOS();
        });
        // notificar
        $('#btnNotificarOrdenServicio').click(function() {
            coOrSeOrSe.fxNotificar.validaNotificarOS();
        });
        // concluir
        $('#btnConcluirOrdenServicio').click(function() {
            coOrSeOrSe.fxConcluir.cargarDocumentosOrdenServicio('#slctOficio');
        });
        /* OSINE_SFS-480 - RSIS 17 - Inicio */
        // concluir
        $('#btnAceptarRecepcion').click(function() {
            if ($('#frmConcluirOS').validateAllForm("#divMensajeValidaFrmConcluirOS")) {
                $("#divOficioFechaFin").dialog("close");
                coOrSeOrSe.fxConcluir.validaConcluirOS();
            }
        });
        /* OSINE_SFS-480 - RSIS 17 - Fin */
        // observar
        $('#btnObservarOS').click(function() {
            coOrSeOrSe.fxObservar.observarOS();
        });
        $('#btnConfirmaObservarOS').click(function() {
            coOrSeOrSe.fxObservar.validaObservarOS();
        });
        // observarJefe
        $('#btnObservarOSAprobar').click(function() {
            coOrSeOrSe.fxObservarAprobar.observarOS();
        });
        $('#btnConfirmaObservarOSAprobar').click(function() {
            coOrSeOrSe.fxObservarAprobar.validaObservarOS();
        });
        // asignar
        $('#btnAsignarOS').click(function() {
            coOrSeOrSe.fxAsignar.validaAsignarOS();
        });
        // generar
        $('#btnGenerarOS').click(function() {
            coOrSeOrSe.fxGenerar.validaGenerarOS();
        });
        // confirmardescargo
        $('#btnConfirmarDescargoOS').click(function() {
            coOrSeOrSe.fxConfirmarDescargo.validaConfirmarDescargoOS();
        });

        // mant UnidadOperativa
        // <!-- QUITAR EMPRESA SUPERVISORA - Inicio -->
//        $('#btnCrearUnidOperOrdeServ').click(
//                function() {
//                    common.abrirMantUnidadOperativa("new", $('#nroDocumentoOS')
//                            .val().replace('RUC - ', ''),
//                            $('#razonSocialOS').val(), $(
//                            '#txtIdEmpresaSupervisadaOS').val());
//                });
//      <!-- QUITAR EMPRESA SUPERVISORA - Fin -->
        // htorress - RSIS 8 - Inicio
        // $('#btnSubirDocumento').click(function(){common.abrirSubirDocumento($('#txtNumeroExpedienteOS').val(),$('#txtIdOrdenServicioOS').val());});
        $('#btnSubirDocumento').click(
                function() {
                    common.abrirSubirDocumento($('#txtNumeroExpedienteOS').val(),
                            $('#txtIdOrdenServicioOS').val(), $(
                            '#txtIdEmpresaSupervisadaOS').val(), $(
                            '#txtIdExpedienteOS').val(), $(
                            '#txtAsuntoSigedOS').val(), $(
                            '#slctIdFlujoSigedOS').find(':checked').attr(
                            'codigoSiged'));
                });
        // htorress - RSIS 8 - Fin
        $('#btnRegistroSupervision').click(function() {
            common.abrirSupervision(
                    $('#tipoAccionOS').val() == constant.accionOS.atender ? constant.modoSupervision.registro : constant.modoSupervision.consulta,
                    $('#txtIdOrdenServicioOS').val(),
                    $('#rolSesion').val(),
                    $('#slctTipoEmprSupeOS option:selected').text(),
                    $('#slctEmprSupeOS option:selected').text(),
                    constant.estado.activo,
                    $('#txtIdExpedienteOS').val(),
                    $('#txtIdObligacionTipo').val(),
                    $('#txtIdProceso').val(),
                    $('#txtIdActividad').val(),
                    /* OSINE791 - RSIS2 - Inicio */
                    $('#txtIteracionOS').val()
                    /* OSINE791 - RSIS2 - Fin */
                    );
        });
        // buscar UnidadOperativa por CodigoOsinergmin
        $('#btnBuscarClienteOS').click(coOrSeOrSe.fxGenerar.procBuscarClienteOS);
        $('#btnBuscarClienteOSEditar').click(coOrSeOrSe.fxEditar.procBuscarClienteOSEditar);
        // validaciones alphanum
        $('#txtAsuntoSigedOS').alphanum(constant.valida.alphanum.descrip);
        // htorress - RSIS 18 - Inicio
        $('#txtMotivoObserOS').alphanum(constant.valida.alphanum.comentario);
        // htorress - RSIS 18 - Fin
        /* OSINE_SFS-480 - RSIS 26 - Inicio */
        $('#txtObligatorioUnidadOrganicaOS').html(($('#descFiltroConfiguradoOSI').val() != '') ? 'Datos requeridos: ' + $('#descFiltroConfiguradoOSI').val() : '');
        /* OSINE_SFS-480 - RSIS 26 - Inicio */
        $('#cmbTipoSupervisionOS').change(function() {
            if ($("#cmbTipoSupervisionOS").find(':checked').attr('codigo') > 0) {
                $('#subTipoOS').css('display', 'inline-block');
                //coOrSeOrSe.obtenerObligacionSubTipo($('#cmbTipoSupervisionOS').val());
                common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOS').val(), '#cmbSubTipoSupervisionOS');
            } else {
                $('#subTipoOS').css('display', 'none');
                fill.clean('#cmbSubTipoSupervisionOS');
            }
        });
    }
};

$(function() {
    coOrSeOrSe.inicializaObjetosEventos();

    $('#slctUnidSupeOS').trigger('change');

    //Registrar Supervision
    /* OSINE_SFS-480 - RSIS 13 - Inicio */
//    if ($('#tipoAccionOS').val() == constant.accionOS.atender 
//            && $('#txtFlagSupervision').val() == constant.estado.activo) {
//        idSupervisionValida = coOrSeOrSe.fxAtender.validaExisteSupervision();
//        if (idSupervisionValida == '') {
//            common.abrirSupervision(
//                $('#tipoAccionOS').val() == constant.accionOS.atender ? constant.modoSupervision.registro : constant.modoSupervision.consulta, 
//                $('#txtIdOrdenServicioOS').val(), 
//                $('#rolSesion').val(), 
//                $('#slctTipoEmprSupeOS option:selected').text(), 
//                $('#slctEmprSupeOS option:selected').text(),
//                constant.estado.inactivo
//            );
//        }
//    }
    /* OSINE_SFS-480 - RSIS 13 - Fin */

    // arma validaciones de campos obligatorios en el formulario
    coOrSeOrSe.armaValidateFormOS();
    // cargar files del expediente
    if ($('#tipoAccionOS').val() != constant.accionOS.generar) {
        common.procesarGridFilesExpediente($('#txtNumeroExpedienteOS').val(),
                $('#txtIdOrdenServicioOS').val(), "FilesOrdenServicio",
                $('#tipoAccionOS').val());
    }

    // arma visualizacion de valores para casos "consultar" y diferentes a generar
    coOrSeOrSe.armaConsultarFormOS();
    // arma subtipo supervision, casos tipo consulta y editar
    if ($('#txtIdObligacionSubTipoOS').val() != '') {
        //coOrSeOrSe.obtenerObligacionSubTipo($('#cmbTipoSupervisionOS').val());
        common.obligacionTipo.obtenerObligacionSubTipo($('#cmbTipoSupervisionOS').val(), '#cmbSubTipoSupervisionOS');
        $('#subTipoOS').css('display', 'inline-block');
        $('#cmbSubTipoSupervisionOS').val($('#txtIdObligacionSubTipoOS').val());
    } 
    /* OSINE_SFS-791 - RSIS 34 - Inicio */
    if($('#tipoAccionOS').val()== constant.accionOS.editar && $('#flagEvaluaTipoAsignacion').val()==constant.estado.activo &&
       $('#slctMotivoEditar').find(':checked').attr('codigo')==constant.codMotivoPeticionEditar.cambiar_tipo_asignacion){
         $('#slctIdFlujoSigedOS').attr('disabled', 'disabled');
         $('#txtAsuntoSigedOS').attr('disabled', 'disabled');
         $('#slctIdProcesoOS').attr('disabled', 'disabled');
         $('#cmbTipoSupervisionOS').attr('disabled', 'disabled');
         $('#codigoOsinergOS').attr('disabled', 'disabled');
         $('#btnBuscarClienteOSEditar').css('display', 'none');
         $('#txtRubroOS').attr('disabled', 'disabled');
         $('#txtDireOperOS').attr('disabled', 'disabled');
         $('#nroDocumentoOS').attr('disabled', 'disabled');
         $('#razonSocialOS').attr('disabled', 'disabled');
         $('#slctMotivoEditar').attr('disabled', 'disabled');
         $('#txtComentarioAnular').attr('disabled', 'disabled');
         $('#slctTipoEmprSupeOS,#slctEmprSupeOS').attr('disabled',true);                
    }
    /* OSINE_SFS-791 - RSIS 34 - Fin */
    boton.closeDialog();
});