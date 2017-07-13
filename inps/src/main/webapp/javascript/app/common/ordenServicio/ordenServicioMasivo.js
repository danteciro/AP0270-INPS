/**
 * Resumen		
 * Objeto		: ordenServicioMasivo.js
 * Descripción		: ordenServicioMasivo
 * Fecha de Creación	: 
 * PR de Creación	: OSINE_SFS-480
 * Autor			: GMD
 * =====================================================================================================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                         |     Descripción
 * =====================================================================================================================================================================
 * OSINE_SFS-480  |  19/05/2016   |   Hernán Torres Saenz            |     Adecuar  interfaz para la nueva forma de generación de órdenes de servicio (masivo).
 * OSINE_SFS-480  |  23/05/2016   |   Giancarlo Villanueva Andrade   |     Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
 * OSINE_SFS-480  |  23/05/2016   |   Luis García Reyna              |     Implementar la funcionalidad de devolución de asignaciones de acuerdo a especificaciones.
 * OSINE_MANT_DSHL_003 |27/06/2017|   Claudio Cchauca Umana						|    	Detallar mensaje s� no se encuentra la Unidad Operativa ingresada				 
 *    
 */

//common/OrdenServicio/OrdenServicio.js
/* OSINE_SFS-480 - RSIS 26 - Inicio */
var coOrSeOrSeMa = {
    armaValidateFormOS: function() {
        $('#frmOSMAS').find('input, select, textarea').removeAttr('validate');
        switch ($('#tipoAccionOSMAS').val()) {
            case constant.accionOS.generar:
                $(
                        '#slctIdFlujoSigedOSMAS,#txtAsuntoSigedOSMAS,#cmbTipoAsigOSMAS,#slctTipoEmprSupeOSMAS,#cmbTipoSupervisionOSMAS,#slctEmprSupeOSMAS,#nroDocumentoOS,#slctIdProcesoOSMAS')
                        .attr('validate', '[O]');
                break;
            default:
        }
    },
    fxGenerar: {
        validaGenerarOS: function() {
            if ($('#frmOSMAS').validateAllForm("#divMensajeValidaFrmOSMAS")) {
                if ($('#unidOperativaSeleccionadaMAS').html() != "") {
                    confirmer.open(
                            "¿Confirma que desea generar una Orden de Servicio?",
                            "coOrSeOrSeMa.fxGenerar.procesaGenerarOrdenServicio()");
                } else {
                    mensajeGrowl("warn", "Debe seleccionar Unidad Operativa", "");
                }
            }
        },
        procesaGenerarOrdenServicio: function() {
            coOrSeOrSeMa.armaFormOrdenServicioMasiva();
            $.ajax({
                url: baseURL + "pages/expediente/generarExpedienteOrdenServicioMasivo",
                type: 'post',
                async: false,
                dataType: 'json',
                data: $('#frmOSMAS').serialize(),
                beforeSend: loading.open(),
                success: function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                        $("#dialogOrdenServicioMasivo").dialog('close');
                        confirmer.open(""
                                /* OSINE_SFS-480 - RSIS 27 - Inicio */
                                //confirmer.open("Se generó satisfactoriamente la  Orden de Servicio:<br> "
                                //+ data.expediente.ordenServicio.numeroOrdenServicio
                                /* OSINE_SFS-480 - RSIS 27 - Fin */
                                /* OSINE_SFS-480 - RSIS 27 - Inicio */
                                + data.msgOrdSerCreada
                                + data.ordSerCreadaConcatenada
                                + data.msgOrdSerNoCreada
                                + data.msgNoGenExpSiged
                                + data.ordSerNoGenExpSigedConcatenada
                                + data.msgNoReeSiged
                                + data.ordSerNoReeSigedConcatenada
                                + data.msgNoIdPerSiged
                                + data.ordSerNoIdPerSigedConcatenada
                                /* OSINE_SFS-480 - RSIS 27 - Fin */
                                + "", "", {textAceptar: '', textCancelar: 'Cerrar', title: 'Orden de Servicio'});
                        // recargar grillas involucradas
                        espeAsigAsig.procesarGridAsignacion();
                        espeNotiArchNotiArch.procesarGridArchivadoNotificado();// TEMPORAL// pq// el// expediente// desparece// al// confirmar// descargo,// pero// luego// reaparece// al// asignar// os
                    } else {
                        mensajeGrowl("error", data.mensaje, "");
                    }
                },
                error: errorAjax
            });

        },
        /* OSINE_SFS-480 - RSIS 27 - Inicio */
        validarQuitarUO: function(rowid, nombreGrid) {
            var row = $('#grid' + nombreGrid).getRowData(rowid);
            confirmer.open("¿Ud. Está seguro de quitar la Unidad Operativa?", "coOrSeOrSeMa.fxGenerar.quitarUO('" + rowid + "','" + nombreGrid + "')");
        },
        quitarUO: function(rowid, nombreGrid) {
            var row = $('#grid' + nombreGrid).getRowData(rowid);
            $.ajax({
                url: baseURL + "pages/unidadSupervisada/quitarUO",
                type: 'post',
                async: false,
                data: {
                    idUnidadSupervisada: rowid,
                },
                beforeSend: loading.open,
                success: function(data) {
                    loading.close();
                    coOrSeOrSeMa.fxGenerar.buscarUnidadOperativa('0');
                    $('#idUnidSupSele' + rowid).remove();
                    mensajeGrowl("success", data.mensaje, "");
                    //$('#divAnularArchivo').css('display','inline-block');
                },
                error: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
        },
        buscarUnidadOperativa: function(flagBusqueda) {
            var postDataEnvio = {
                registroHidrocarburos: $('#txtNumeroRHUnidad').val() == null || $('#txtNumeroRHUnidad').val().trim() == '' ? '' : $('#txtNumeroRHUnidad').val(),
                cadCodigoOsinerg: $('#txtCadCodigoOsiBusqUnidad').val(),
                razonSocial: $('#txtRazoSociBusqUnidad').val(),
                idDepartamento: $('#slctDepartamentoUnidad').val() == null || $('#slctDepartamentoUnidad').val().trim() == '' ? '' : $('#slctDepartamentoUnidad').val(),
                idProvincia: $('#slctProvinciaUnidad').val() == null || $('#slctProvinciaUnidad').val().trim() == '' ? '' : $('#slctProvinciaUnidad').val(),
                idDistrito: $('#slctDistritoUnidad').val() == null || $('#slctDistritoUnidad').val().trim() == '' ? '' : $('#slctDistritoUnidad').val(),
                flagBusqueda: flagBusqueda,
                flagEsUnidadMuestral: $("#flagEsUnidadMuestral").val(),
                idObligacionSubTipo: $('#cmbSubTipoSupervisionOSMAS').val()
            };

            var nombres = ['idUnidadSupervisada', 'C&Oacute;DIGO OSINERGMIN', 'NRO. REGISTRO HIDROCARBURO', 'RUBRO',
                '','DIRECCION', 'DEPARTAMENTO', 'PROVINCIA', 'DISTRITO', 'ID ACTIVIDAD', 'TIPO - NUMERO DE DOCUMENTO','ruc', 'RAZ&Oacute;N SOCIAL'
                        , 'TIPO SELECCION', '', ''];
            var columnas = [
                {name: "idUnidadSupervisada", hidden: true},
                {name: "codigoOsinergmin", width: 70, hidden: false},
                {name: "nroRegistroHidrocarburo", hidden: false},
                {name: "nombreActividad", width: 150},
//                {name: "direccion", width: 160},
                {name: "direccionUnidadsupervisada.idDirccionUnidadSuprvisada", hidden: true},
                {name: "direccionUnidadsupervisada.direccionCompleta", width: 150},
//                {name: "empresaSup.idEmpresaSupervisada", hidden: true},
                {name: "direccionUnidadsupervisada.departamento.idDepartamento", hidden: true},
                {name: "direccionUnidadsupervisada.provincia.idProvincia", hidden: true},
                {name: "direccionUnidadsupervisada.distrito.idDistrito", hidden: true},
                {name: "idActividad", hidden: true},
                {name: "nroDocumentoCompleto", width: 100},
                {name: "ruc", hidden:true},
                {name: "nombreUnidad", width: 210, sortable: false, align: "left", hidden: false},
//                {name: "empresaSup.razonSocial", width: 210, sortable: false, align: "left"},
                {name: "tipoSeleccion", width: 50, sortable: false, align: "center"},
                /* OSINE_SFS-480 - RSIS 40 - Inicio */
                {name: 'check', width: 21, sortable: false, align: "center", formatter: "checkboxSelecUnidadSupervisada"},
                {name: 'flagMuestral', hidden: true}
                /* OSINE_SFS-480 - RSIS 40 - Inicio */
            ];
            $("#gridContenedorUnidadOperativaMAS").html("");
            var grid = $("<table>", {
                "id": "gridUnidadesOperativas"
            });
            var pager = $("<div>", {
                "id": "paginacionUnidadesOperativas"
            });
            $("#gridContenedorUnidadOperativaMAS").append(grid).append(pager);

            grid.jqGrid({
                url: baseURL + "pages/unidadSupervisada/cargaDataUnidadOperativaMasivaGrid",
                datatype: "json",
                mtype: "POST",
                postData: postDataEnvio,
                hidegrid: false,
                rowNum: constant.rowNumPrinc,
                pager: "#paginacionUnidadesOperativas",
                emptyrecords: "No se encontraron resultados",
                loadtext: "Cargando",
                colNames: nombres,
                colModel: columnas,
                height: "auto",
                viewrecords: true,
                caption: "",
                jsonReader: {
                    root: "filas",
                    page: "pagina",
                    total: "total",
                    records: "registros",
                    repeatitems: false,
                    id: "idUnidadSupervisada"
                },
                onSelectRow: function(rowid, status) {
                    grid.resetSelection();
                },
                onRightClickRow: function(rowid) {
                    var row = $('#gridUnidadesOperativas').getRowData(rowid);
                    $('#contextMenuUnidadOperativa').parent().css('opacity', '1');
                    $('#linkQuitar').css('display', '')
                    $('#linkQuitar').attr('onClick', 'coOrSeOrSeMa.fxGenerar.validarQuitarUO("' + rowid + '","gridUnidadesOperativas")');
                },
                loadComplete: function(data) {
                    //agregar check seleccionar todos
                    var html = '<input id="chkSeleAllVisibles" type="checkbox" name="" value="" onclick="coOrSeOrSeMa.selecAllOrdenServicioVisibles(this);"><label for="chkSeleAllVisibles" class="checkbox"></label>';
                    $('#gridUnidadesOperativas_check').html(html);
                    $('#gridUnidadesOperativas_check').css('height', '20px');
                    $('#gridUnidadesOperativas_check').removeClass('ui-jqgrid-sortable');

                    //para marcar los ya seleccionados, casos de navegar entre paginas de grilla
                    $('#unidOperativaSeleccionadaMAS').find('div').map(function() {
                        $('#idUnidSup' + $(this).attr('id').replace('idUnidSupSele', '')).attr('checked', true);
                    });
                    // Para la opción de Quitar UO de la lista de Sesión.
                    $('#contextMenuUnidadOperativa').parent().remove();
                    $('#divContextMenuUnidadOperativaMAS').html("<ul id='contextMenuUnidadOperativa'>"
                            + "<li> <a id='linkQuitar' data-icon='ui-icon-trash' title='Quitar'>Quitar</a></li>"
                            + "</ul>");
                    $('#contextMenuUnidadOperativa').puicontextmenu({
                        target: $('#gridUnidadesOperativas')
                    });
                },
                loadError: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
        },
        /* OSINE_SFS-480 - RSIS 27 - Fin */
        procBuscarUnidadSupervisadaOS: function() {
            $('#txtCadCodigoOsinergOSMAS').removeClass("error");
            if ($('#txtCadCodigoOsinergOSMAS').val() == '') {
                $('#txtCadCodigoOsinergOSMAS').addClass("error");
                return false;
            } else {
                $.ajax({
                    url: baseURL + "pages/unidadSupervisada/cargaDataUnidadOperativaMasivaInd",
                    type: 'post',
                    async: false,
                    dataType: 'json',
                    data: {
                        cadCodigoOsinerg: $('#txtCadCodigoOsinergOSMAS').val(),
                        flagEsUnidadMuestral: '0'
                    },
                    beforeSend: loading.open,
                    success: function(data) {
                        loading.close();
                        if (data.resultado == "0") {
                            coOrSeOrSeMa.armaValidateFiltros();
                            coOrSeOrSeMa.fxGenerar.buscarUnidadOperativa('0');
                        } else {
                        	//OSINE_MANT_DSHL_003 - inicio
                            mensajeGrowl(
                                    "error",
                                    "No se encuentra la Unidad Operativa ingresada, no posee un estado RHO Vigente o la Actividad no corresponde al usuario.",
                                    "");
                          //OSINE_MANT_DSHL_003 - fin
                        }
                    },
                    error: errorAjax
                });
            }

        },
        procBuscarUnidadSupervisadaMasivaOS: function() {
            $.ajax({
                url: baseURL + "pages/unidadSupervisada/cargaDataUnidadOperativaMasivaMul",
                type: 'post',
                async: false,
                dataType: 'json',
                data: {
                    idObligacionSubTipo: $('#cmbSubTipoSupervisionOSMAS').val(),
                    flagEsUnidadMuestral: '1'
                },
                beforeSend: loading.open,
                success: function(data) {
                    loading.close();
                    if (data.resultado == "0") {
                        coOrSeOrSeMa.armaValidateFiltros();
                        //coOrSeOrSeMa.activarSlctTipoEmprSupeOS(); 
                        coOrSeOrSeMa.fxGenerar.buscarUnidadOperativa('0');
                    } else {
                        mensajeGrowl(
                                "error",
                                "No se encuentra la Unidad Operativa ingresada.",
                                "");
                    }
                },
                error: errorAjax
            });

        }
    },
    activarSlctTipoEmprSupeOS: function(difiere, difiereAct, validaMensaje) {
        if (difiere || $('#slctIdProcesoOSMAS').val() == '' || $('#cmbTipoSupervisionOSMAS').val() == '' || $('#txtIdActividadOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere) {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
        if (!validaMensaje) {
            if (difiere && !difiereAct) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
            }
            if (difiereAct && !difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
            }
            if (difiereAct && difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro, ni al mismo Ubigeo.", "");
            }
        }
    },
    removeSesionLista: function() {
        $.ajax({
            url: baseURL + "pages/unidadSupervisada/removeSesionLista",
            type: 'get',
            async: false,
            dataType: 'json',
            success: function(data) {
                $('#unidOperativaSeleccionadaMAS').html('');
                $('#txtIdActividadOSMAS').val('');
                $("#txtIdDepartamentoOSMAS").val('');
                $("#txtIdProvinciaOSMAS").val('');
                $("#txtIdDistritoOSMAS").val('');
                coOrSeOrSeMa.armaValidateFiltros();//coOrSeOrSeMa.activarSlctTipoEmprSupeOS();
                loading.close;
            },
            error: errorAjax
        });
    },
    /* OSINE_SFS-480 - RSIS 26 - Inicio*/
    obtenerObligacionSubTipo: function(idTipoSupervision) {
        $.getJSON(baseURL + "pages/serie/listarObligacionSubTipo", {
            idObligacionTipo: idTipoSupervision,
            ajax: 'true',
            async: true
        }, function(data) {
            var html = '<option value="">--Seleccione--</option>';
            var len = data.length;
            for (var i = 0; i < len; i++) {
                html += '<option value="' + data[i].idObligacionSubTipo + '"  codigo="' + data[i].identificadorSeleccion + '" >'
                        + data[i].nombre + '</option>';
            }
            $('#cmbSubTipoSupervisionOSMAS').html(html);
        });
    },
    selecUnidSupervisada: function(obj) {
        difiere = false;
        difiereAct = false;
        validaMensaje = false;
        var countDep = 0;
        var countProv = 0;
        var countDist = 0;
        var countAct = 0;
        var depActual = 0;
        var depAnterior = 0;
        var provActual = 0;
        var provAnterior = 0;
        var distActual = 0;
        var distAnterior = 0;
        var actAnterior = 0;
        var actActual = 0;
        if ($(obj).attr('checked')) {
            idUniOpeSele = $(obj).attr('id').replace('idUnidSup', '');
            if ($('#idUnidSupSele' + idUniOpeSele).length == '0') {
                var row = $('#gridUnidadesOperativas').jqGrid("getRowData", idUniOpeSele);
                var html = '<div id="idUnidSupSele' + idUniOpeSele + '">' +
                        '<input type="text" style="width:50px;" name="" value="' + row['idUnidadSupervisada'] + '" />' +
//                        '<input type="text" style="width:50px;" name="" value="' + row['empresaSup.idEmpresaSupervisada'] + '" />' +
                        '<input type="text" style="width:50px;" codigo="iddirec" name="" value="' + row['direccionUnidadsupervisada.idDirccionUnidadSuprvisada'] + '" />' +
                        '<input type="text" style="width:50px;" codigo="actividad" name="" value="' + row['idActividad'] + '" />' +
                        '<input type="text" style="width:50px;" codigo="departamento" name="" value="' + row['direccionUnidadsupervisada.departamento.idDepartamento'] + '" />' +
                        '<input type="text" style="width:50px;" codigo="provincia" name="" value="' + row['direccionUnidadsupervisada.provincia.idProvincia'] + '" />' +
                        '<input type="text" style="width:50px;" codigo="distrito" name="" value="' + row['direccionUnidadsupervisada.distrito.idDistrito'] + '" />' +
                        '<input type="text" style="width:50px;" codigo="codosi" name="" value="' + row['codigoOsinergmin'] + '" />' +
                        '<input type="text" style="width:50px;" codigo="flagMuestral" name="" value="' + row['flagMuestral'] + '" />' +
                        '<input type="text" style="width:50px;" codigo="ruc" name="" value="' + row['ruc'] + '" />' +
                        '</div>';
                $('#unidOperativaSeleccionadaMAS').append(html);
            }
        } else {
            $('#idUnidSupSele' + $(obj).attr('id').replace('idUnidSup', '')).remove();
        }

        var dataDep = $('#unidOperativaSeleccionadaMAS').find('div input[codigo="departamento"]');
        dataDep.map(function() {
            depAnterior = depActual;
            depActual = $(this).val();
            if (countDep == 0) {
                depAnterior = depActual;
            }
            if (depAnterior != depActual) {
                difiere = true;
            }
            countDep++;
        });
        var dataPro = $('#unidOperativaSeleccionadaMAS').find('div input[codigo="provincia"]');
        dataPro.map(function() {
            provAnterior = provActual;
            provActual = $(this).val();
            if (countProv == 0) {
                provAnterior = provActual;
            }
            if (provAnterior != provActual) {
                difiere = true;
            }
            countProv++;
        });
        var dataDis = $('#unidOperativaSeleccionadaMAS').find('div input[codigo="distrito"]');
        dataDis.map(function() {
            distAnterior = distActual;
            distActual = $(this).val();
            if (countDist == 0) {
                distAnterior = distActual;
            }
            if (distAnterior != distActual) {
                difiere = true;
            }
            countDist++;
        });
        var dataAct = $('#unidOperativaSeleccionadaMAS').find('div input[codigo="actividad"]');
        dataAct.map(function() {
            actAnterior = actActual;
            actActual = $(this).val();
            if (countAct == 0) {
                actAnterior = actActual;
            }
            if (actAnterior != actActual) {
                difiereAct = true;
            }
            countAct++;
        });

        codosi = $('#idUnidSupSele' + $(obj).attr('id').replace('idUnidSup', '')).find('input[codigo="codosi"]').val();
        if (codosi == '') {
            $(obj).removeAttr('checked');
            $('#idUnidSupSele' + $(obj).attr('id').replace('idUnidSup', '')).remove();
            mensajeGrowl("warn", "La Unidad Operativa seleccionada no tiene Código Osinergmin.", "");
        }
        
        var iddirec = $('#idUnidSupSele' + $(obj).attr('id').replace('idUnidSup', '')).find('input[codigo="iddirec"]').val();
        if (iddirec == '') {
            $(obj).removeAttr('checked');
            $('#idUnidSupSele' + $(obj).attr('id').replace('idUnidSup', '')).remove();
            mensajeGrowl("warn", "La Unidad Operativa no tiene Dirección.", "");
        }
        var ruc = $('#idUnidSupSele' + $(obj).attr('id').replace('idUnidSup', '')).find('input[codigo="ruc"]').val();
        if (ruc == '') {
            $(obj).removeAttr('checked');
            $('#idUnidSupSele' + $(obj).attr('id').replace('idUnidSup', '')).remove();
            mensajeGrowl("warn", "La Unidad Operativa no tiene RUC.", "");
        }
        
        
        if ($(obj).attr('checked')) {
        } else {
            validaMensaje = true;
        }
        if (!difiere) {
            $("#txtIdDepartamentoOSMAS").val($('#unidOperativaSeleccionadaMAS div').find('input').eq(3).val());
            $("#txtIdProvinciaOSMAS").val($('#unidOperativaSeleccionadaMAS div').find('input').eq(4).val());
            $("#txtIdDistritoOSMAS").val($('#unidOperativaSeleccionadaMAS div').find('input').eq(5).val());
        } else {
            $("#txtIdDepartamentoOSMAS").val('');
            $("#txtIdProvinciaOSMAS").val('');
            $("#txtIdDistritoOSMAS").val('');
        }
        if (!difiereAct && $('#unidOperativaSeleccionadaMAS').html() != "") {
            $("#txtIdActividadOSMAS").val($('#unidOperativaSeleccionadaMAS div').find('input').eq(2).val());
        } else {
            $("#txtIdActividadOSMAS").val('');
        }

        coOrSeOrSeMa.armaValidateFiltros(difiere, difiereAct, validaMensaje);//coOrSeOrSeMa.activarSlctTipoEmprSupeOS(difiere);


    },
    selecAllOrdenServicioVisibles: function(obj) {
        if ($(obj).attr('checked')) {
            $('#gridUnidadesOperativas').find('input').attr('checked', true);
        } else {
            $('#gridUnidadesOperativas').find('input').attr('checked', false);
        }
        $('#gridUnidadesOperativas').find('input').map(function() {
            coOrSeOrSeMa.selecUnidSupervisada(this);
        });
    },
    armaFormOrdenServicioMasiva: function() {
        cont = 0;
        $('#unidOperativaSeleccionadaMAS div').map(function() {
            $(this).find('input').eq(0).attr('name', 'unidadesSupervisadas[' + cont + '].idUnidadSupervisada');
//            $(this).find('input').eq(1).attr('name', 'unidadesSupervisadas[' + cont + '].empresaSupervisada.idEmpresaSupervisada');
            $(this).find('input').eq(1).attr('name', 'unidadesSupervisadas[' + cont + '].listaDirecciones[0].idDirccionUnidadSuprvisada');
            $(this).find('input').eq(2).attr('name', 'unidadesSupervisadas[' + cont + '].actividad.idActividad');
            $(this).find('input').eq(3).attr('name', 'unidadesSupervisadas[' + cont + '].listaDirecciones[0].departamento.idDepartamento');
            $(this).find('input').eq(4).attr('name', 'unidadesSupervisadas[' + cont + '].listaDirecciones[0].provincia.idProvincia');
            $(this).find('input').eq(5).attr('name', 'unidadesSupervisadas[' + cont + '].listaDirecciones[0].distrito.idDistrito');
            $(this).find('input').eq(6).attr('name', 'unidadesSupervisadas[' + cont + '].codigoOsinergmin');
            $(this).find('input').eq(7).attr('name', 'unidadesSupervisadas[' + cont + '].flagMuestral');
            $(this).find('input').eq(8).attr('name', 'unidadesSupervisadas[' + cont + '].ruc');
            cont++;
        });
    },
    armaValidateFiltros: function(difiere, difiereAct, validaMensaje) {
        str = $('#txtFiltrosMAS').val();
        array = str.split(',');
        contadorXfiltro = 0;
        supervision = 0;
        rubro = 0;
        ubigeo = 0;
        proceso = 0;
        for (i = 0; i < array.length; i++) {
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
        coOrSeOrSeMa.armaCondicionFiltros(contadorXfiltro, supervision, rubro, ubigeo, proceso, difiere, difiereAct, validaMensaje);
    },
    armaCondicionFiltros: function(contadorXfiltro, supervision, rubro, ubigeo, proceso, difiere, difiereAct, validaMensaje) {
        switch (contadorXfiltro) {
            case 0:
                coOrSeOrSeMa.armaCondicionCeroFiltros();
                //console.info("La Unidad Orgánica no contiene ningún filtro");
                break;
            case 1:
                //console.info('valida ['+contadorXfiltro+']condiciones');
                if (supervision == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxUnoTipoSupervision();
                } else if (rubro == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxUnoRubro(difiereAct, validaMensaje);
                } else if (ubigeo == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxUnoUbigeo(difiere, validaMensaje);
                } else if (proceso == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxUnoProceso();
                }
                break;
            case 2:
                //console.info('valida ['+contadorXfiltro+']condiciones');
                if (supervision == 1 && rubro == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxDosSR(difiereAct, validaMensaje);
                } else if (supervision == 1 && ubigeo == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxDosSU(difiere);
                } else if (supervision == 1 && proceso == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxDosSP();
                } else if (rubro == 1 && ubigeo == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxDosRU(difiere, difiereAct, validaMensaje);
                } else if (rubro == 1 && proceso == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxDosRP(difiereAct, validaMensaje);
                } else if (ubigeo == 1 && proceso == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxDosUP(difiere, validaMensaje);
                }
                break;
            case 3:
                if (supervision == 1 && rubro == 1 && ubigeo == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxTresSRU(difiere, difiereAct, validaMensaje);
                } else if (rubro == 1 && ubigeo == 1 && proceso == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxTresRUP(difiere, difiereAct, validaMensaje);
                } else if (supervision == 1 && rubro == 1 && proceso == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxTresSRP(difiereAct, validaMensaje);
                } else if (supervision == 1 && ubigeo == 1 && proceso == 1) {
                    coOrSeOrSeMa.armaCondicionFiltrosxTresSUP(difiere);
                }
                break;
            case 4:
                //console.info('valida ['+contadorXfiltro+']condiciones');
                coOrSeOrSeMa.activarSlctTipoEmprSupeOS(difiere, difiereAct, validaMensaje);
                break;
        }

    },
    armaCondicionCeroFiltros: function() {
        coOrSeOrSeMa.habilitarEmpresa();
    },
    armaCondicionFiltrosxUnoProceso: function() {
        if ($('#slctIdProcesoOSMAS').val() == '') {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
    },
    armaCondicionFiltrosxUnoTipoSupervision: function() {
        if ($('#cmbTipoSupervisionOSMAS').val() == '') {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
    },
    armaCondicionFiltrosxUnoRubro: function(difiereAct, validaMensaje) {
        if ($('#txtIdActividadOSMAS').val() == '' || difiereAct) {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
        if (!validaMensaje && difiereAct) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
        }
    },
    armaCondicionFiltrosxUnoUbigeo: function(difiere, validaMensaje) {
        if ($('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere) {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
        if (!validaMensaje && difiere) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
        }
    },
    armaCondicionFiltrosxDosSR: function(difiereAct, validaMensaje) {
        if ($('#cmbTipoSupervisionOSMAS').val() == '' || $('#txtIdActividadOSMAS').val() == '' || difiereAct) {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
        if (!validaMensaje && difiereAct) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
        }
    },
    armaCondicionFiltrosxDosSU: function(difiere, validaMensaje) {
        if ($('#cmbTipoSupervisionOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere) {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
        if (!validaMensaje && difiere) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
        }
    },
    armaCondicionFiltrosxDosSP: function() {
        if ($('#cmbTipoSupervisionOSMAS').val() == '' || $('#slctIdProcesoOSMAS').val() == '') {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
    },
    armaCondicionFiltrosxDosRU: function(difiere, difiereAct, validaMensaje) {
        if ($('#txtIdActividadOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere || difiereAct) {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
        if (!validaMensaje) {
            if (difiere && !difiereAct) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
            }
            if (difiereAct && !difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
            }
            if (difiereAct && difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro, ni al mismo Ubigeo.", "");
            }
        }
    },
    armaCondicionFiltrosxDosRP: function(difiereAct, validaMensaje) {
        if ($('#txtIdActividadOSMAS').val() == '' || $('#slctIdProcesoOSMAS').val() == '' || difiereAct) {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
        if (!validaMensaje && difiereAct) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
        }
    },
    armaCondicionFiltrosxDosUP: function(difiere, validaMensaje) {
        if ($('#slctIdProcesoOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere) {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
        if (!validaMensaje && difiere) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
        }
    },
    armaCondicionFiltrosxTresSRU: function(difiere, difiereAct, validaMensaje) {
        if (difiereAct || $('#cmbTipoSupervisionOSMAS').val() == '' || $('#txtIdActividadOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere) {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
        if (!validaMensaje) {
            if (difiere && !difiereAct) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
            }
            if (difiereAct && !difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
            }
            if (difiereAct && difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro, ni al mismo Ubigeo.", "");
            }
        }
    },
    armaCondicionFiltrosxTresRUP: function(difiere, difiereAct, validaMensaje) {
        if (difiereAct || $('#txtIdActividadOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere || $('#slctIdProcesoOSMAS').val() == '') {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
        if (!validaMensaje) {
            if (difiere && !difiereAct) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
            }
            if (difiereAct && !difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
            }
            if (difiereAct && difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro, ni al mismo Ubigeo.", "");
            }
        }
    },
    armaCondicionFiltrosxTresSRP: function(difiereAct, validaMensaje) {
        if ($('#cmbTipoSupervisionOSMAS').val() == '' || $('#txtIdActividadOSMAS').val() == '' || $('#slctIdProcesoOSMAS').val() == '' || difiereAct) {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
        if (!validaMensaje && difiereAct) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
        }
    },
    armaCondicionFiltrosxTresSUP: function(difiere, validaMensaje) {
        if ($('#cmbTipoSupervisionOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere || $('#slctIdProcesoOSMAS').val() == '') {
            coOrSeOrSeMa.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMa.habilitarEmpresa();
        }
        if (!validaMensaje && difiere) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
        }
    },
    habilitarEmpresa: function() {
        $("#slctTipoEmprSupeOSMAS").val('');
        $("#slctTipoEmprSupeOSMAS").removeAttr('disabled');
        $("#slctEmprSupeOSMAS").removeAttr('disabled');
    },
    deshabilitarEmpresa: function() {
        $("#slctTipoEmprSupeOSMAS").val('');
        $("#slctEmprSupeOSMAS").val('');
        $("#slctTipoEmprSupeOSMAS").attr('disabled', 'disabled');
        $("#slctEmprSupeOSMAS").attr('disabled', 'disabled');
    }
    /* OSINE_SFS-480 - RSIS 26 - Fin */
};
/* OSINE_SFS-480 - RSIS 26 - Inicio */
jQuery.extend($.fn.fmatter, {
    checkboxSelecUnidadSupervisada: function(cellvalue, options, rowdata) {
        var id = rowdata.idUnidadSupervisada;
        var html = '';
        if (id != null && id != '' && id != undefined) {
            html = '<input id="idUnidSup' + id + '" type="checkbox" name="" value="" onclick="coOrSeOrSeMa.selecUnidSupervisada(this)">' +
                    '<label for="idUnidSup' + id + '" class="checkbox"></label>';
        }
        return html;
    }
});
/* OSINE_SFS-480 - RSIS 26 - Fin */
$(function() {
    coOrSeOrSeMa.removeSesionLista();
    $('#slctIdFlujoSigedOSMAS').change(function() {
        $('#slctCodigoFlujoSigedOSMAS').val($('#slctIdFlujoSigedOSMAS').find(':checked').attr('codigoSiged'));
        $('#slctNombreFlujoSigedOSMAS').val($('#slctIdFlujoSigedOSMAS').find(':checked').text());
    });
    $('#btnCerrarRecepcion').click(function() {
        $("#divOficioFechaFin").dialog("close");
    });
    $('#slctIdProcesoOSMAS').change(function() {
        fill.clean("#slctIdEtapaOS,#slctIdTramiteOS");
        common.serie.cargarEtapa("#slctIdProcesoOSMAS", "#slctIdEtapaOS");
    });
    // change empresa supervisora
    $('#slctEmprSupeOSMAS').change(function() {
        if ($('#slctTipoEmprSupeOSMAS').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
            $('#slctLocadorEmprSupeOSMAS').val($('#slctEmprSupeOSMAS').val());
            $('#slctSupervisoraEmprSupeOSMAS').val('');
        } else if ($('#slctTipoEmprSupeOSMAS').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
            $('#slctSupervisoraEmprSupeOSMAS').val($('#slctEmprSupeOSMAS').val());
            $('#slctLocadorEmprSupeOSMAS').val('');
        }
    });
    $('#slctTipoEmprSupeOSMAS')
            .change(
            function() {
                var idProceso = $("#slctIdProcesoOSMAS").val();
                var idObligacionTipo = $("#cmbTipoSupervisionOSMAS").val();
                var idRubro = $("#txtIdActividadOSMAS").val();
                var idDepartamento = $("#txtIdDepartamentoOSMAS").val();
                var idProvincia = $("#txtIdProvinciaOSMAS").val();
                var idDistrito = $("#txtIdDistritoOSMAS").val();
                fill.clean("#slctEmprSupeOSMAS");
                if ($('#slctTipoEmprSupeOSMAS').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
                    common.empresaSupervisora.cargarLocador('#slctEmprSupeOSMAS', idObligacionTipo, idProceso, idRubro, idDepartamento, idProvincia, idDistrito);
                } else if ($('#slctTipoEmprSupeOSMAS').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
                    common.empresaSupervisora.cargarSupervisoraEmpresa('#slctEmprSupeOSMAS', idObligacionTipo, idProceso, idRubro, idDepartamento, idProvincia, idDistrito);
                }
                $('#slctCodigoTipoEmprSupeOSMAS').val($('#slctTipoEmprSupeOSMAS').find(':checked').attr('codigo'));
            });

    $('#slctIdProcesoOSMAS').change(function() {
        coOrSeOrSeMa.armaValidateFiltros();//coOrSeOrSeMa.activarSlctTipoEmprSupeOS();
    });
    $('#cmbTipoSupervisionOSMAS').change(function() {
        coOrSeOrSeMa.armaValidateFiltros();//coOrSeOrSeMa.activarSlctTipoEmprSupeOS();
        $('#flagEsUnidadMuestral').val(constant.estado.inactivo);
    });
    // generar
    $('#btnGenerarOSMAS').click(function() {
        coOrSeOrSeMa.fxGenerar.validaGenerarOS();
    });
    // buscar UnidadOperativa por CodigoOsinergmin
    $('#btnBuscarClienteOSMasivo').click(coOrSeOrSeMa.fxGenerar.procBuscarUnidadSupervisadaOS);
    // arma validaciones de campos obligatorios en el formulario
    coOrSeOrSeMa.armaValidateFormOS();
    // cargar files del expediente
    if ($('#tipoAccionOSMAS').val() != constant.accionOS.generar) {
        common.procesarGridFilesExpediente($('#txtNumeroExpedienteOSMAS').val(),
                $('#txtIdOrdenServicioOSMAS').val(), "FilesOrdenServicio", $(
                '#tipoAccionOSMAS').val());
    }
    // validaciones alphanum
    $('#txtAsuntoSigedOSMAS').alphanum(constant.valida.alphanum.descrip);
    boton.closeDialog();
    /* OSINE_SFS-480 - RSIS 26 - Inicio */
    $('#cmbTipoSupervisionOSMAS').change(function() {
        if ($("#cmbTipoSupervisionOSMAS").find(':checked').attr('codigo') > 0) {
            $('#subTipo').css('display', 'inline-block');
            coOrSeOrSeMa.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSMAS').val());
        } else {
            $('#subTipo').css('display', 'none');
            coOrSeOrSeMa.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSMAS').val());
        }
        coOrSeOrSeMa.removeSesionLista();
        coOrSeOrSeMa.fxGenerar.buscarUnidadOperativa('0');
    });
    $('#cmbSubTipoSupervisionOSMAS').change(function() {
        if ($("#cmbSubTipoSupervisionOSMAS").find(':checked').attr('codigo') == 'S') {
            $('#flagEsUnidadMuestral').val(constant.estado.activo);
            coOrSeOrSeMa.fxGenerar.procBuscarUnidadSupervisadaMasivaOS();
        } else {
            $('#flagEsUnidadMuestral').val(constant.estado.inactivo);
            coOrSeOrSeMa.removeSesionLista();
            coOrSeOrSeMa.fxGenerar.buscarUnidadOperativa('0');
        }

    });
    $('#txtCadCodigoOsinergOSMAS').alphanum(constant.valida.alphanum.codigoOsinergmin);
    /* OSINE_SFS-480 - RSIS 26 - Fin */
    $('#txtObligatorioUnidadOrganicaMAS').html(($('#descFiltroConfiguradoMAS').val() != '') ? 'Datos requeridos: ' + $('#descFiltroConfiguradoMAS').val() : '');
});