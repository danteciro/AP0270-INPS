/**
 * Resumen		
 * Objeto		: ordenServicioEnvioMensajeria.js
 * Descripción		: JavaScript donde se centraliza las acciones del envio mensajería
 * Fecha de Creación	: 26/05/2016
 * PR de Creación	: OSINE_SFS-480
 * Autor			: Mario Dioses Fernandez
 * =====================================================================================================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                         |     Descripción
 * =====================================================================================================================================================================
 * OSINE_SFS-480  |  26/05/2016   |   Mario Dioses Fernandez         |     Construir formulario de envio a Mensajeria, consumiendo WS
 *
 *    
 */

// common/ordenServicio/OrdenServicioEnvioMensajeria.js
var coOrSeOrSeEnMe = {
    comportamiento: function() {
        $("#dlgCliente").dialog({
            resizable: false,
            draggable: true,
            autoOpen: false,
            height: "auto",
            width: "auto",
            modal: true,
            dialogClass: 'dialog',
            closeText: "Cerrar"
        });

        $('#slctDepartamentoOS').change(function() {
            fill.clean("#slctProvinciaOS");
            fill.clean("#slctDistritoOS");
            if ($('#slctDepartamentoOS').val() != '') {
                coOrSeOrSeEnMe.cargarProvincia("#slctProvinciaOS", $('#slctDepartamentoOS').val());
                coOrSeOrSeEnMe.cargarDistrito("#slctDistritoOS", $('#slctDepartamentoOS').val(), $('#slctProvinciaOS').val());
            }
        });

        $('#slctProvinciaOS').change(function() {
            fill.clean("#slctDistritoOS");
            if ($('#slctProvinciaOS').val() != '') {
                coOrSeOrSeEnMe.cargarDistrito("#slctDistritoOS", $('#slctDepartamentoOS').val(), $('#slctProvinciaOS').val());
            }
        });

        $('#slctDepartamentoTestOS').change(function() {
            fill.clean("#slctProvinciaTestOS");
            fill.clean("#slctDistritoTestOS");
            if ($('#slctDepartamentoTestOS').val() != '') {
                coOrSeOrSeEnMe.cargarProvincia("#slctProvinciaTestOS", $('#slctDepartamentoTestOS').val());
                coOrSeOrSeEnMe.cargarDistrito("#slctDistritoTestOS", $('#slctDepartamentoTestOS').val(), $('#slctProvinciaTestOS').val());
            }
        });

        $('#slctProvinciaTestOS').change(function() {
            fill.clean("#slctDistritoTestOS");
            if ($('#slctProvinciaTestOS').val() != '') {
                coOrSeOrSeEnMe.cargarDistrito("#slctDistritoTestOS", $(
                        '#slctDepartamentoTestOS').val(), $(
                        '#slctProvinciaTestOS').val());
            }
        });

        $("#btnAceptarCliente").on('click', function() {
            if ($('#txtDestinatariosIdOS').val() != '') {
                var IdDestinatario = $('#txtDestinatariosIdOS').val();
                var nroIdentificacion = $('#txtNroDocumentoDestinatariosOS').val();
                var razonSocial = $('#txtRazonSocial').val();
                var direccion = $('#txtDireccion').val();
                var departamento = $('#slctDepartamentoOS').val();
                var provincia = $('#slctProvinciaOS').val();
                var distrito = $('#slctDistritoOS').val();
                var ubigeo = departamento + provincia + distrito;
                var referencia = $('#txtReferencia').val();
                var mydata = {
                    idDestinatario: IdDestinatario,
                    nroIdentificacion: nroIdentificacion,
                    razonSocial: razonSocial,
                    direccion: direccion,
                    departamento: ubigeo,
                    provincia: ubigeo,
                    distrito: ubigeo,
                    referencia: referencia,
                    sel: ''
                };

                var existe = false;
                var ids = jQuery("#gridDestinatario").jqGrid('getDataIDs');
                for (var i = 0; i < ids.length; i++) {
                    var rowId = ids[i];
                    var rowData = jQuery('#gridDestinatario').jqGrid('getRowData', rowId);
                    var idDestinatario = rowData['idDestinatario'];
                    if (idDestinatario == IdDestinatario) {
                        existe = true;
                        break;
                    }
                }
                if (!existe) {
                    $("#txtDestinatariosOS").val('');
                    jQuery("#gridDestinatario").jqGrid('addRowData', IdDestinatario, mydata);
                    $('#dlgCliente').dialog('close');
                } else {
                    mensajeGrowl("error", constant.confirmMensajeria.duplicado);
                    $("#txtDestinatariosOS").val('');
                    $('#dlgCliente').dialog('close');
                }
            }
        });

        $("#btnAgregarDestinatario").on('click', function() {
            $('#txtRazonSocial').attr('validate', 'validate="[O]"');
            coOrSeOrSeEnMe.abrirDetalleCliente();
        });

        $("#btnEnviarMensajeria").on('click', function() {
            $('#txtRazonSocial').removeAttr('validate');
            validar = $('#frmOSMensajeria').validateAllForm('#divMensajeValidaFrmOS');
            if (validar)
                confirmer.open('¿Confirma que desea realizar el envio a mensajer&iacutea?', 'coOrSeOrSeEnMe.envioMensajeria()');
        });

        $("#txtDestinatariosOS").change(function() {
            if (($("#txtDestinatariosOS").val().trim()).length == 0) {
                $('#txtDestinatariosIdOS').val('');
                $('#txtNroDocumentoDestinatariosOS').val('');
                $('#txtRazonSocial').val('');
                $('#txtDireccion').val('');
                $('#slctDepartamentoOS').val('-1');
                $('#slctProvinciaOS').val('-1');
                $('#slctDistritoOS').val('-1');
                $('#txtReferencia').val('');
            }
        });
    },
    envioMensajeria: function() {
        var idsDocumentosNOSeleccionados = '';
        var idDocPrincipalExpediente = '';
        var iDsArchivosNOSeleccionados = '';
        var iDsArchivosSeleccionados = '';
        var idTipoenvio = $('#slctTipoEnvioOS').val();
        var idUsuarioSiged = $('#IdPersonalSigedOS').val();
        var oficinaRegional = $('#slctOficinaOS').val();
        var flagNotificarOS = $('#flagNotificarOS').val();
        var lstClienteCustom = coOrSeOrSeEnMe.getClientesSeleccion();
        var documentosSel = coOrSeOrSeEnMe.getDocumentosSeleccionados();
        $.each(documentosSel, function(i, item) {
            if (item.principal) {
                idDocPrincipalExpediente = item.iddocumento;
                iDsArchivosSeleccionados += item.idarchivos;
            } else {
                idsDocumentosNOSeleccionados += item.iddocumento + ',';
                iDsArchivosNOSeleccionados += item.idarchivos + ',';
            }
        });
        if (idsDocumentosNOSeleccionados.length != 0)
            idsDocumentosNOSeleccionados = idsDocumentosNOSeleccionados
                    .substring(0, idsDocumentosNOSeleccionados.length - 1);
        if (iDsArchivosNOSeleccionados.length != 0)
            iDsArchivosNOSeleccionados = iDsArchivosNOSeleccionados.substring(
                    0, iDsArchivosNOSeleccionados.length - 1);
        if (idDocPrincipalExpediente == '') {
            mensajeGrowl("error", constant.confirmMensajeria.selDocumento);
            return;
        }
        if (lstClienteCustom == '') {
            mensajeGrowl("error", constant.confirmMensajeria.selClientes);
            return;
        }

        $.ajax({
            url: baseURL + "pages/archivo/registrarMensajeriaSIGED",
            type: 'get',
            dataType: 'json',
            async: false,
            data: {
                idtipoenvio: idTipoenvio,
                idUsuarioSiged: idUsuarioSiged,
                lstClienteCustom: lstClienteCustom,
                idsDocumentosNOSeleccionados: idsDocumentosNOSeleccionados,
                idDocPrincipalExpediente: idDocPrincipalExpediente,
                oficinaRegional: oficinaRegional,
                IDsArchivosSeleccionados: iDsArchivosSeleccionados,
                IDsArchivosNOSeleccionados: iDsArchivosNOSeleccionados
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                if (data != null) {
                    if (data.resultado == '1') {
                        mensajeGrowl("success", constant.confirmMensajeria.save);
                        if (flagNotificarOS == '1')
                            coOrSeOrSeNot.fxNotificar.procesaNotificarOrdenServicio()
                        $('#dialogEnviarMensajeria').dialog('close');
                    } else {
                        mensajeGrowl("error", data.mensaje);
                    }
                }
            },
            error: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    getClientesSeleccion: function() {
        var lstClienteCustom = '';
        $('#gridDestinatario tr').each(function() {
            var idCliente = $(this).find('td').eq(0).text();
            if (idCliente != '') {
                if ($('#idDest' + idCliente).is(':checked')) {
                    lstClienteCustom += idCliente + ',';
                }
            }
        });
        if (lstClienteCustom.length != 0)
            lstClienteCustom = lstClienteCustom.substring(0, lstClienteCustom.length - 1);
        return lstClienteCustom;
    },
    obtenerSeleccion: function(documentopadre) {
        var elementsDeLaLista = $(documentopadre).children();
        var documento;
        var archivos = new Array();
        $.each(elementsDeLaLista, function(key, value) {
            if (value.type == 'checkbox' || value.type == 'radio') {
                documento = value;
            } else {
                if ($(value).children()[0]) {
                    archivos.push($(value).children()[0]);
                }
            }
        });
        var count = 0;
        $.each(archivos, function(key, value) {
            if ($(value).is(':checked')) {
                count++;
            }
        });
        var sinArchivosMarcados = true;
        if (count > 0) {
            sinArchivosMarcados = false;
        }
        return {
            documento: documento,
            archivos: archivos,
            sinArchivosMarcados: sinArchivosMarcados
        };
    },
    getDocumentosSeleccionados: function() {
        var seleccionados = $('.documentosEnvio');
        var data = new Array();
        $.each(seleccionados, function(i, value) {
            var documentoPadre = $(value).children();
            var datos = coOrSeOrSeEnMe.obtenerSeleccion(documentoPadre);
            var documento = datos.documento;
            var archivos = datos.archivos;
            var codArchivos = '';
            $.each(archivos, function(key, value) {
                codArchivos += $(value).attr('value') + ',';
            });
            if (codArchivos.length != 0)
                codArchivos = codArchivos.substring(0, codArchivos.length - 1);
            data.push({
                iddocumento: $(documento).attr('value'),
                principal: $(documento).is(':checked'),
                idarchivos: codArchivos
            })
        });
        return data;
    },
    cargarDepartamento: function(idSlctDest) {
        $.ajax({
            url: baseURL + "pages/bandeja/cargarDepartamento",
            type: 'get',
            dataType: 'json',
            async: false,
            data: {},
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                fill.comboValorId(data, "idDepartamento", "nombre", idSlctDest, "-1");
            },
            error: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    cargarProvincia: function(combo, idDepartamento) {
        $.ajax({
            url: baseURL + "pages/ubigeo/buscarProvincias",
            type: 'post',
            dataType: 'json',
            async: false,
            data: {
                idDepartamento: idDepartamento
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                fill.combo(data, "idProvincia", "nombre", combo);
            },
            error: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    cargarDistrito: function(combo, idDepartamento, idProvincia) {
        $.ajax({
            url: baseURL + "pages/ubigeo/buscarDistritos",
            type: 'post',
            dataType: 'json',
            async: false,
            data: {
                idDepartamento: idDepartamento,
                idProvincia: idProvincia
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                fill.combo(data, "idDistrito", "nombre", combo);
            },
            error: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    procesarDocumentosExpedientes: function() {
        $.ajax({
            url: baseURL
                    + "pages/archivo/findArchivoExpedienteMensajeriaOS",
            type: 'get',
            dataType: 'json',
            async: false,
            data: {
                numeroExpediente: $("#nroExpedienteOS").val(),
                idOrdenServicio: $("#IdExpedienteOS").val()
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                var html = '';
                var archivo = data.ArchivoExpediente;
                var extension = data.tiposArhivos;
                $.each(data.ArchivoExpediente,
                        function(i, item) {
                            if (item.idDocumento != null && item.nroDocumento != null && item.enumerado == 'S') {
                                html += '<div class="rowContent" style="width: 97.3%;" id="row_d_' + item.idDocumento + '">';
                                html += '<ul class="documento">';
                                html += '<input type="checkbox"';
                                html += ' onClick="coOrSeOrSeEnMe.marcarTodosLosArchivos(this);" id="d_' + item.idDocumento + '" ';
                                html += ' class="css-checkbox" lang="' + item.idTipoDocumento.descripcion + '" value="' + item.idDocumento + '" /> ';
                                html += ' <label for="d_' + item.idDocumento + '" name="checkbox2_lbl" ';
                                html += ' class="css-label lite-blue-check">' + item.idTipoDocumento.descripcion + '</label> ';
                                html += '';
                                $.each(archivo, function(j, archivo) {
                                    var nombreArchivo = archivo.nombreArchivo;
                                    var formato = (nombreArchivo.substring(nombreArchivo.lastIndexOf("."))).toUpperCase();
                                    if ((item.idDocumento == archivo.idDocumento) && (archivo.idArchivo != null) && (archivo.nombreArchivo != null) && (extension.indexOf(formato) > -1)) {
                                        html += ' <li class="archivo" style="overflow:hidden;"><input ';
                                        html += ' onClick="coOrSeOrSeEnMe.seleccionarArchivo(this);" type="checkbox" ';
                                        html += ' class="css-checkbox" id="a_' + archivo.idArchivo + '" value="' + archivo.idArchivo + '" lang="' + archivo.nombreArchivo + '" /> <label for="a_' + archivo.idArchivo + '" ';
                                        html += ' class="css-label lite-gray-check">' + archivo.nombreArchivo + '</label> ';
                                        html += ' </li> ';
                                    }
                                });
                            }
                            html += '</ul>';
                            html += '</div>';
                        });
                if (data.length == 0) {
                    html = 'No se encontraron documentos para enviar a mensajeria.';
                }
                $('#panelDocExpediente').empty();
                $('#panelDocExpediente').html(html);
                //limpia documentos sin archivos
                $('#panelDocExpediente').children('div').map(function() {
                    if ($(this).find('li').length < 1) {
                        $(this).remove();
                    }
                });
            },
            error: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    seleccionarArchivo: function(archivo) {
        var row = $(archivo).parent()[0];
        var documentopadre = $(row).parent()[0];
        var content = $(documentopadre).parent();
        var objSeleccionados = this.obtenerSeleccion(documentopadre);
        var documento = objSeleccionados.documento;
        var archivos = objSeleccionados.archivos;
        var sinArchivosMarcados = objSeleccionados.sinArchivosMarcados;
        if (archivo.checked) {
            $(documento).attr('checked', true);
            content.addClass("rowSelected");
        } else {
            if (sinArchivosMarcados) {
                $(documento).attr('checked', false);
                content.removeClass("rowSelected");
            }
        }
    },
    marcarTodosLosArchivos: function(chkDocumento) {
        var documentopadre = $(chkDocumento).parent()[0];
        var content = $(documentopadre).parent();
        var objSeleccionados = this.obtenerSeleccion(documentopadre);
        var archivos = objSeleccionados.archivos;
        var checked = false;
        if ($(chkDocumento).is(':checked')) {
            checked = true;
            content.removeClass("rowSelected");
            content.addClass("rowSelected");
        } else {
            content.removeClass("rowSelected");
        }
        $.each(archivos, function(key, value) {
            $(value).attr('checked', checked);
        });
    },
    obtenerSeleccion : function(documentopadre) {
        var elementsDeLaLista = $(documentopadre).children();
        var documento;
        var archivos = new Array();
        $.each(elementsDeLaLista, function(key, value) {
            if (value.type == 'checkbox' || value.type == 'radio') {
                documento = value;
            } else {
                if ($(value).children()[0]) {
                    archivos.push($(value).children()[0]);
                }
            }
        });
        var count = 0;
        $.each(archivos, function(key, value) {
            if ($(value).is(':checked')) {
                count++;
            }
        });
        var sinArchivosMarcados = true;
        if (count > 0) {
            sinArchivosMarcados = false;
        }
        return {
            documento: documento,
            archivos: archivos,
            sinArchivosMarcados: sinArchivosMarcados
        };
    },
            moverAlaDerecha: function() {
        var me = this;
        var contentDerecha = $('.rowSelected');
        var c = 0;
        var count_errors = 0;
        $.each(contentDerecha, function(key, value) {
            var documentoPadre = $(value).children();
            var objSeleccionados = me.obtenerSeleccion(documentoPadre);
            var documento = objSeleccionados.documento;
            var archivos = objSeleccionados.archivos;
            me.generarHtmlSeleccionDerecha(objSeleccionados, value);
        });
    },
    moverAlaIzquierda: function() {
        var me = this;
        var contentIzquierda = $('.rowSelectedSendData');
        if ($('.rowSelectedSendData').length) {
            var idRadio = $('.rowSelectedSendData').attr("id").replace('row_', '');
            $.each(contentIzquierda, function(key, value) {
                var documento = $(value);
                var arr = $(documento).attr('id').split('_');
                if ($('#' + idRadio).is(':checked'))
                    fill.clean('#slctTipoEnvioOS');
                $('#row_d_' + arr[2]).show();
                $(value).remove();
            });
        }
    },
    generarHtmlSeleccionDerecha: function(objSeleccionados, seleccionado) {
        var archivos = objSeleccionados.archivos;
        var documento = objSeleccionados.documento;
        var checked = '';
        $(documento).attr('checked', false);
        $('#row_d_' + $(documento).attr('value')).removeClass("rowSelected");
        var html = '<div class="rowDataSelected documentosEnvio" lang="0" onClick="coOrSeOrSeEnMe.selectData(this);" style="width: 97.3%;" id="row_i_'
                + $(documento).attr('value') + '">';
        html += '<ul class="documento">';
        html += '<input onClick="coOrSeOrSeEnMe.seleccionarPrincipal(this.value);"  id="i_'
                + $(documento).attr('value')
                + '" '
                + checked
                + ' class="custom-radio" name="principal" value="'
                + $(documento).attr('value')
                + '" type="radio" lang="'
                + $(documento).attr('lang') + '" />';
        html += '<label for="i_' + $(documento).attr('value') + '">'
                + $(documento).attr('lang') + '</label>';

        $.each(
                archivos,
                function(key, value) {
                    if ($(value).is(':checked')) {
                        $(value).attr('checked', false);
                        html += '<li class="archivo" style="overflow:hidden;"><input type="text" style="display:none" value="'
                                + $(value).attr('value')
                                + '" lang="'
                                + $(value).attr('lang')
                                + '" />'
                                + $(value).attr('lang') + '</li>';
                    }
                });

        $('#panelDocEnvia').append();
        html += '</ul>';
        html += '</div>';
        $(seleccionado).hide();
        $('#panelDocEnvia').append(html);
    },
    selectData: function(row) {
        if ($(row).attr('class').indexOf('rowDataSelected') >= 0) {
            $(row).removeClass("rowDataSelected");
            $(row).addClass("rowSelectedSendData");
        } else {
            $(row).removeClass("rowSelectedSendData");
            $(row).addClass("rowDataSelected");
        }
    },
    seleccionarPrincipal: function(iIdDoc) {
        var esTipo = '1'; // Establecido por SIGED
        $.ajax({
            url: baseURL + "pages/archivo/validarDocumentoSIGED",
            type: 'get',
            dataType: 'json',
            async: false,
            data: {
                esTipo: esTipo,
                iIdDoc: iIdDoc
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                if (data != null) {
                    if (data.resultado == '1') {
                        coOrSeOrSeEnMe.cargarTiposEnvio(data.listaTipoEnvio);
                    } else {
                        $('#i_' + iIdDoc + '').attr('checked', false);
                        mensajeGrowl("error", data.mensaje);
                    }
                }
            },
            error: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    cargarTiposEnvio: function(data) {
        if (data != null) {
            fill.comboValorId(data, "id", "descripcion", '#slctTipoEnvioOS', "-1");
        }
    },
    cargarOficinaRegional: function() {
        $.ajax({
            url: baseURL + "pages/archivo/listarOficinaRegionalSIGED",
            type: 'get',
            dataType: 'json',
            async: false,
            data: {
                IdPersonalSIGED: $('#IdPersonalSigedOS').val()
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                var PorDefecto = '-1';
                if (data != null) {
                    if (data.resultado == '1') {
                        if (data.listaOficinaRegional.length != 0) {
                            PorDefecto = data.listaOficinaRegional[0].porDefecto;
                        }
                        fill.comboValorId(data.listaOficinaRegional, "idUnidad", "nombre", '#slctOficinaOS', PorDefecto.toString());
                    }
                }
            },
            error: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    cargarClienteSIGED: function() {
        $.ajax({
            url: baseURL + "pages/archivo/findExpedienteSIGED",
            type: 'get',
            dataType: 'json',
            async: false,
            data: {
                nroExpediente: $('#nroExpedienteOS').val()
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                if (data.expediente != null && data.expediente.clientes != null && data.expediente.clientes.cliente != null) {
                    if (data.expediente.clientes.cliente.length != 0) {
                        $('#IdClienteSIGEDOS').val(data.expediente.clientes.cliente[0].codigoCliente);
                        $('#nroIdentClienteSIGEDOS').val(data.expediente.clientes.cliente[0].numeroIdentificacion);
                    }
                }
            },
            error: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    procesarGridDestinatario: function() {
        var nombreGrid = "Destinatario";
        var estadoFlujo = false;
        nombres = ['IdDestinatario', 'NRO IDENTIFICACI&Oacute;N', 'DESTINATARIO', 'DIRECCI&Oacute;N', 'DEPARTAMENTO', 'PROVINCIA', 'DISTRITO', 'REFERENCIA', ''];
        columnas = [{name: "idDestinatario", width: 100, hidden: true, sortable: false, align: "left"},
            {name: "nroIdentificacion", width: 80, sortable: false, align: "center"},
            {name: "razonSocial", width: 150, sortable: false, align: "left"},
            {name: "direccion", width: 150, sortable: false, align: "left"},
            {name: "departamento", width: 120, sortable: false, align: "center", formatter: "setDepartamento"},
            {name: "provincia", width: 120, sortable: false, align: "center", formatter: "setProvincia"},
            {name: "distrito", width: 120, sortable: false, align: "center", formatter: "setDistrito"},
            {name: "referencia", width: 120, sortable: false, align: "left"},
            {name: "sel", width: 25, align: "center", valign: "top", hidden: false, formatter: "checkboxSelecDestinatario"}
        ];

        $("#gridContenedor" + nombreGrid).html("");
        var grid = $("<table>", {
            "id": "grid" + nombreGrid
        });
        var pager = $("<div>", {
            "id": "paginacion" + nombreGrid
        });
        $("#gridContenedor" + nombreGrid).append(grid).append(pager);
        grid.jqGrid({
            url: baseURL + "pages/archivo/GridDestalleDestinatarioSIGED",
            datatype: "json",
            postData: {
                idDestinatario: $('#IdClienteSIGEDOS').val(),
                nroIdentificacion: $('#nroIdentClienteSIGEDOS').val(),
                estadoFlujo: estadoFlujo,
                nroExpediente: $('#nroExpedienteOS').val()
            },
            hidegrid: false,
            rowNum: constant.rowNumPrinc,
            pager: "#paginacion" + nombreGrid,
            emptyrecords: "No se encontraron resultados",
            recordtext: "{0} - {1}",
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
                id: "idDestinatario"
            },
            loadComplete: function(data) {
                var html = '<input id="chkSeleAllVisibles" type="checkbox" name="" value="" onclick="coOrSeOrSeEnMe.selecAllOrdenServicioVisibles(this);"><label for="chkSeleAllVisibles" class="checkbox"></label>';
                $('#gridDestinatario_sel').html(html);
                $('#gridDestinatario_sel').css('height', '20px');
                $('#gridDestinatario_sel').removeClass(
                        'ui-jqgrid-sortable');
                $('#ordDestinatarioSeleccionados').find('div.filaForm').map(function() {
                    $('#idDestinatario' + $(this).attr('id').replace('idDestinatarioSele', '')).attr('checked', true);
                });
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            }
        });
    },
    selecAllOrdenServicioVisibles: function(obj) {
        if ($(obj).attr('checked')) {
            $('#gridDestinatario').find('input').attr('checked', true);
        } else {
            $('#gridDestinatario').find('input').attr('checked', false);
        }
        $('#gridDestinatario').find('input').map(function() {
            coOrSeOrSeEnMe.selecDestinatario(this);
        });
    },
    autoComplete: function() {
        $('#txtDestinatariosOS').autocomplete({
            source: function(request, response) {
                if ($('#txtDestinatariosOS').val().length > 3) {
                    $.ajax({
                        type: "GET",
                        url: baseURL + "pages/archivo/listarDestinatarioSIGED",
                        data: {
                            filtro: request.term
                        },
                        dataType: "json",
                        context: this,
                        jsonReader: {
                            root: "listaDestinatario"
                        },
                        success: function(data) {
                            if (data.listaDestinatario != null) {
                                response($.map(data.listaDestinatario,
                                        function(
                                                item) {
                                            return {
                                                label: item.descripcionIdentificacion + ' - '
                                                        + item.nroidentificacion
                                                        + ' - '
                                                        + item.razonSocial,
                                                value: item.idCliente
                                            }
                                        }));
                            }
                        }
                    });
                }
            },
            focus: function(event, ui) {
                $("#txtDestinatariosOS").val(ui.item.label);
                return false;
            },
            select: function(event, ui) {
                var label = ui.item.label;
                var ArrayDestinatario = label.split('-');
                $("#txtDestinatariosOS").val(ui.item.label);
                $("#txtDestinatariosIdOS").val(ui.item.value);
                if (ArrayDestinatario.length != 0) {
                    $("#txtNroDocumentoDestinatariosOS").val(ArrayDestinatario[1].trim());
                }
                coOrSeOrSeEnMe.abrirDetalleCliente();
                return false;
            },
            change: function(ev, ui) {
                if (!ui.item)
                    $(this).val('');
            }
        })
    },
    abrirDetalleCliente: function() {
        if ($("#txtNroDocumentoDestinatariosOS").val() != '') {
            $("#dlgCliente").dialog('option', 'title', 'DATOS PERSONALIZADOS DEL CLIENTE');
            $('#dlgCliente').dialog('open');
            coOrSeOrSeEnMe.cargarDepartamento('#slctDepartamentoOS');
            coOrSeOrSeEnMe.cargarDetalleCliente($('#txtNroDocumentoDestinatariosOS').val());
        }
    },
    cargarDetalleCliente: function(nroIdentificacion) {
        var estadoFlujo = false;
        $.ajax({
            url: baseURL + "pages/archivo/listarDestalleDestinatarioSIGED",
            type: 'get',
            dataType: 'json',
            async: false,
            data: {
                nroIdentificacion: nroIdentificacion,
                estadoFlujo: estadoFlujo,
                nroExpediente: $('#nroExpedienteOS').val()
            },
            beforeSend: loading.open,
            success: function(data) {
                loading.close();
                if (data != null) {
                    var code = data.clienteDetalle.distrito;
                    code = ('00000' + code).slice(-6);
                    var IdDep = code.substring(0, 2);
                    var IdProv = code.substring(2, 4);
                    var IdDist = code.substring(4, 6);
                    if (data.resultado == '1') {
                        $('#txtRazonSocial').val(
                                data.clienteDetalle.razonSocial);
                        $('#txtDireccion').val(
                                data.clienteDetalle.direccion);
                        $('#slctDepartamentoOS').val(IdDep);
                        coOrSeOrSeEnMe.cargarProvincia(
                                "#slctProvinciaOS", $(
                                '#slctDepartamentoOS').val());
                        $('#slctProvinciaOS').val(IdProv);
                        coOrSeOrSeEnMe.cargarDistrito(
                                "#slctDistritoOS", $(
                                '#slctDepartamentoOS').val(),
                                $('#slctProvinciaOS').val());
                        $('#slctDistritoOS').val(IdDist);
                        $('#txtReferencia').val(
                                data.clienteDetalle.referencia);
                    }
                }
            },
            error: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    selecDestinatario: function(obj) {
        if ($(obj).attr('checked')) {
            idDestSele = $(obj).attr('id').replace('idDest', '');
            if ($('#idDestinatarioSele' + idDestSele).length == '0') {
                var row = $('#gridDestinatario').jqGrid("getRowData", idDestSele);
                var html = '<div id="idDestinatarioSele' + idDestSele + '">'
                        + '<input type="text" name="" value="'
                        + row['idDestinatario'] + '" />' + '</div>';
                $('#ordServicioSeleccionados').append(html);
            }
        } else {
            $('#idDestinatarioSele' + $(obj).attr('id').replace('idDest', ''))
                    .remove();
        }
    }
};

jQuery.extend($.fn.fmatter, {
    checkboxSelecDestinatario: function(cellvalue, options, rowdata) {
        var id = rowdata.idDestinatario;
        var html = '';
        if (id != null && id != '' && id != undefined) {
            html = '<input id="idDest' + id + '" type="checkbox" name="" value="" onclick="coOrSeOrSeEnMe.selecDestinatario(this)">' + '<label for="idDest' + id + '" class="checkbox"></label>';
        }
        return html;
    },
    setDepartamento: function(cellvalue, options, rowdata) {
        var code = rowdata.distrito;
        code = ('00000' + code).slice(-6);
        var IdDep = code.substring(0, 2);
        coOrSeOrSeEnMe.cargarDepartamento('#slctDepartamentoTestOS');
        $('#slctDepartamentoTestOS').val(IdDep);
        var nomDep = $('#slctDepartamentoTestOS option:selected').text();
        if (nomDep.indexOf('--Seleccione--')>0)
            return '';
        else
            return nomDep;
    },
    setProvincia: function(cellvalue, options, rowdata) {
        var code = rowdata.distrito;
        code = ('00000' + code).slice(-6);
        var IdProv = code.substring(2, 4);
        coOrSeOrSeEnMe.cargarProvincia("#slctProvinciaTestOS", $('#slctDepartamentoTestOS').val());
        $('#slctProvinciaTestOS').val(IdProv);
        var nomProv = $('#slctProvinciaTestOS option:selected').text();
        if (nomProv.indexOf('--Seleccione--')>0)
            return '';
        else
            return nomProv;
    },
    setDistrito: function(cellvalue, options, rowdata) {
        var code = rowdata.distrito;
        code = ('00000' + code).slice(-6);
        var IdDist = code.substring(4, 6);
        coOrSeOrSeEnMe.cargarDistrito("#slctDistritoTestOS", $('#slctDepartamentoTestOS').val(), $('#slctProvinciaTestOS').val());
        $('#slctDistritoTestOS').val(IdDist);
        var nomDist = $('#slctDistritoTestOS option:selected').text();
        if (nomDist.indexOf('--Seleccione--')>0)
            return '';
        else
            return nomDist;
    }
});

$(function() {
    coOrSeOrSeEnMe.comportamiento();
    coOrSeOrSeEnMe.cargarClienteSIGED();
    coOrSeOrSeEnMe.procesarDocumentosExpedientes();
    coOrSeOrSeEnMe.cargarOficinaRegional();
    coOrSeOrSeEnMe.procesarGridDestinatario();
    boton.closeDialog();
});