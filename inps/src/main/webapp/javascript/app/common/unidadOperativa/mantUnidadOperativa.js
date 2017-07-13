//common/unidadOperativa/MantUnidadOperativa.js
var coUnOpMaUnOp = {
	procesarGridDireEmprSupe : function(flg_load, nombreGrid,idEmpresaSupervisada) {
		if (flg_load === undefined) {
			flg_load = 1;
		}

		var nombres = ['ID', 'TIPO DIRECCION', 'DIRECCION', 'DEPARTAMENTO',
				'PROVINCIA', 'DISTRITO' ];
		var columnas = [ {
			name : "idDireccionEmpresaSup",
			width : 90,
			sortable : false,
			align : "left",
			hidden : true
		},  {
			name : "tipoDireccion",
			width : 90,
			sortable : false,
			align : "left"
		}, {
			name : "direccionCompleta",
			width : 450,
			sortable : false,
			align : "left"
		}, {
			name : "departamento",
			width : 100,
			sortable : false,
			align : "left"
		}, {
			name : "provincia",
			width : 70,
			sortable : false,
			align : "left"
		}, {
			name : "distrito",
			width : 70,
			sortable : false,
			align : "left"
		} ];
		$("#gridContenedor" + nombreGrid).html("");
		var grid = $("<table>", {
			"id" : "grid" + nombreGrid
		});
		var pager = $("<div>", {
			"id" : "paginacion" + nombreGrid
		});
		$("#gridContenedor" + nombreGrid).append(grid).append(pager);

		grid.jqGrid({
			 url: baseURL + "pages/empresaSupervisada/listarDireccionEmpresaSupervisada",
			datatype : "json",
			// datatype: "local",
			postData : {
				idEmpresaSupervisada : idEmpresaSupervisada
			},
			hidegrid : false,
			rowNum : constant.rowNum,
			pager : "#paginacion" + nombreGrid,
			emptyrecords : "No se encontraron resultados",
			recordtext : "{0} - {1}",
			loadtext : "Cargando",
			colNames : nombres,
			colModel : columnas,
			height : "auto",
			viewrecords : true,
			caption : "Listado Direcciones",
			jsonReader : {
				root : "filas",
				page : "pagina",
				total : "total",
				records : "registros",
				repeatitems : false,
				id : "idDirccionEmpSuprvisada"
			},
			onSelectRow : function(rowid, status) {
				grid.resetSelection();
				var row=$('#gridDireEmprSupe').jqGrid("getRowData",rowid);
                $('#linkEditarDirEmpSup').attr('onClick', 'coUnOpMaUnOp.obtenerDireccionEmpresaSupervisada("'+rowid+'")');
                $('#linkEliminarDirEmpSup').attr('onClick', 'coUnOpMaUnOp.eliminarDireccionEmpresaSupervisada("'+rowid+'")');
			},
			loadComplete : function(data) {
				$('#contextMenuDireEmprSupe').parent().remove();
                $('#divContextMenuDireEmprSupe').html("<ul id='contextMenuDireEmprSupe'>"
                        + "<li> <a id='linkEditarDirEmpSup' data-icon='ui-icon-pencil'>Editar</a></li>"
                        + "<li> <a id='linkEliminarDirEmpSup' data-icon='ui-icon-trash'>ELiminar</a></li>"
                        + "</ul>");
                $('#contextMenuDireEmprSupe').puicontextmenu({
                    target: $('#gridDireEmprSupe')
                });
			}
		});
	},	
	obtenerDireccionEmpresaSupervisada : function(idDireccionEmpresaSupervisada){
		$.ajax({
			url : baseURL + "pages/empresaSupervisada/obtenerDireccionEmpresaSupervisada",
			type : 'post',
			async : false,
			data : {
				idDireccionEmpresaSupervisada : idDireccionEmpresaSupervisada
			},
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					$('#idDireccionEmpresaSupervisadaUO').val(data.direccion.idDirccionEmpSuprvisada);
					$('#tipoDireccionES').val(data.direccion.idTipoDireccion);					
					$('#tipoViaES').val(data.direccion.idTipoVia);
					$('#descTipoViaES').val(data.direccion.descripcionVia);
					$('#numeroDireccionES').val(data.direccion.numeroVia);
					$('#interiorDireccionES').val(data.direccion.interior);
					$('#manzanaDireccionES').val(data.direccion.manzana);
					$('#loteDireccionES').val(data.direccion.lote);
					$('#kilometroDireccionES').val(data.direccion.kilometro);
					$('#blockDireccionES').val(data.direccion.blockChallet);
					$('#etapaDireccionES').val(data.direccion.etapa);
					$('#numeroDepartamentoDireccionES').val(data.direccion.numeroDepartamento);
					$('#urbanizacionDireccionES').val(data.direccion.urbanizacion);
					$('#direccionCompletaES').val(data.direccion.direccionCompleta);
					$('#referenciaDireccionES').val(data.direccion.referencia);
					coUnOpMaUnOp.cargarSerie.cargarDepartamento(data);
					$('#btnEditarDireccionES').css('display','inline-block');
					$('#btnGuardarDireccionES').css('display','none');
//					mensajeGrowl('success', data.mensaje);
					loading.close();
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	eliminarDireccionEmpresaSupervisada : function(idDireccionEmpresaSupervisada){
		confirmer.open('¿Confirma que desea eliminar la dirección de la Empresa Supervisada?','coUnOpMaUnOp.confirmaEliminarDireccionEmpresaSupervisada('+idDireccionEmpresaSupervisada+');');		
	},
	confirmaEliminarDireccionEmpresaSupervisada : function(idDireccionEmpresaSupervisada){
		$.ajax({
			url : baseURL+ "pages/empresaSupervisada/eliminarDireccionEmpresaSupervisada",
			type : 'post',
			async : false,
			data : {
				idDireccionEmpresaSupervisada : idDireccionEmpresaSupervisada
			},
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					mensajeGrowl('success', constant.confirm.remove);
					coUnOpMaUnOp.procesarGridDireEmprSupe(0, "DireEmprSupe",$('#idEmpresaSupervisadaUO').val());
					coUnOpMaUnOp.limpiarDireccionEmpresaSupervisada();
					loading.close();
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	guardarDireccionEmpresaSupervisada:function(){
		$.ajax({
			url : baseURL + "pages/empresaSupervisada/guardarDireccionEmpresaSupervisada",
			type : 'post',
			async : false,
			data : $('#formDireccionEmpresaSupervisada').serialize(),
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					mensajeGrowl('success', constant.confirm.save);
					loading.close();
					coUnOpMaUnOp.procesarGridDireEmprSupe(0, "DireEmprSupe",$('#idEmpresaSupervisadaUO').val());
					coUnOpMaUnOp.limpiarDireccionEmpresaSupervisada();
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	actualizarDireccionEmpresaSupervisada:function(){
		$.ajax({
			url : baseURL + "pages/empresaSupervisada/actualizarDireccionEmpresaSupervisada",
			type : 'post',
			async : false,
			data : $('#formDireccionEmpresaSupervisada').serialize(),
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					mensajeGrowl('success', constant.confirm.edit);
					loading.close();
					coUnOpMaUnOp.procesarGridDireEmprSupe(0, "DireEmprSupe",$('#idEmpresaSupervisadaUO').val());
					coUnOpMaUnOp.limpiarDireccionEmpresaSupervisada();
					$('#btnEditarDireccionES').css('display','none');
					$('#btnGuardarDireccionES').css('display','inline-block');
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	procesarGridReprEmprSupe : function(flg_load, nombreGrid,idEmpresaSupervisada) {
		if (flg_load === undefined) {
			flg_load = 1;
		}

		var nombres = ['ID', 'DOCUMENTO IDENTIDAD', 'NOMBRE COMPLETO', 'CORREO','','TELEFONO' ];
		var columnas = [ {
			name : "idEmpresaContacto",
			width : 150,
			sortable : false,
			align : "left",
			hidden: true
		}, {
			name : "numeroDocIdentidad",
			width : 150,
			sortable : false,
			align : "left"
		}, {
			name : "nombreCompleto",
			width : 280,
			sortable : false,
			align : "left"
		}, {
			name : "correoElectronico",
			width : 230,
			sortable : false,
			align : "left"
		}, {
			name : "telefono",
			width : 120,
			sortable : false,
			align : "left",
			hidden: true
		}, {
			name : "telefonoPersonal",
			width : 120,
			sortable : false,
			align : "left"
		} ];
		$("#gridContenedor" + nombreGrid).html("");
		var grid = $("<table>", {
			"id" : "grid" + nombreGrid
		});
		var pager = $("<div>", {
			"id" : "paginacion" + nombreGrid
		});
		$("#gridContenedor" + nombreGrid).append(grid).append(pager);

		grid.jqGrid({
			url: baseURL + "pages/empresaSupervisada/listarEmpresaContactoEmpresaSupervisada",
			datatype: "json",
			postData : {
				idEmpresaSupervisada : idEmpresaSupervisada
			},
			hidegrid : false,
			rowNum : constant.rowNum,
			pager : "#paginacion" + nombreGrid,
			emptyrecords : "No se encontraron resultados",
			recordtext : "{0} - {1}",
			loadtext : "Cargando",
			colNames : nombres,
			colModel : columnas,
			height : "auto",
			viewrecords : true,
			caption : "Listado Representantes",
			jsonReader : {
				root : "filas",
				page : "pagina",
				total : "total",
				records : "registros",
				repeatitems : false,
				id : "idEmpresaContacto"
			},
			onSelectRow : function(rowid, status) {
				grid.resetSelection();
                $('#linkEditarEmpContactoEmpSup').attr('onClick', 'coUnOpMaUnOp.obtenerEmpContactoEmpSup("'+rowid+'")');
                $('#linkEliminarEmpContactoEmpSup').attr('onClick', 'coUnOpMaUnOp.eliminarEmpContactoEmpSup("'+rowid+'")');
			},
			loadComplete : function(data) {
				$('#contextMenuReprEmprSupe').parent().remove();
                $('#divContextMenuReprEmprSupe').html("<ul id='contextMenuReprEmprSupe'>"
                        + "<li> <a id='linkEditarEmpContactoEmpSup' data-icon='ui-icon-pencil'>Editar</a></li>"
                        + "<li> <a id='linkEliminarEmpContactoEmpSup' data-icon='ui-icon-trash'>Eliminar</a></li>"
                        + "</ul>");
                $('#contextMenuReprEmprSupe').puicontextmenu({
                    target: $('#gridContenedorReprEmprSupe')
                });
			}
		});
	},
	obtenerEmpContactoEmpSup:function(idEmpresaContacto){
		$.ajax({
			url : baseURL + "pages/empresaSupervisada/obtenerEmpresaContactoEmpresaSupervisada",
			type : 'post',
			async : false,
			data : {
				idEmpresaContacto : idEmpresaContacto
			},
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					$('#idEmpresaContactoUO').val(data.empresaContacto.idEmpresaContacto);
					$('#tipoDocumentoES').val(data.empresaContacto.tipoDocumentoIdentidad.idMaestroColumna);
					$('#numeroDocumentoRepresentanteES').val(data.empresaContacto.numeroDocIdentidad);
					$('#cargoRepresentanteES').val(data.empresaContacto.cargo);
					$('#apellidoPaternoRepresentanteES').val(data.empresaContacto.apellidoPaterno);
					$('#apellidoMaternoRepresentanteES').val(data.empresaContacto.apellidoMaterno);
					$('#nombreRepresentanteES').val(data.empresaContacto.nombre);
					$('#correoRepresentanteES').val(data.empresaContacto.correoElectronico);
					$('#telefonoRepresentanteES').val(data.empresaContacto.telefonoPersonal);
					$('#btnEditarRepresentanteES').css('display','inline-block');
					$('#btnGuardarRepresentanteES').css('display','none');
//					mensajeGrowl('success', data.mensaje);
					loading.close();
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	actualizarEmpresaContactoEmpresaSupervisada:function(){
		$.ajax({
			url : baseURL + "pages/empresaSupervisada/actualizarEmpresaContactoEmpresaSupervisada",
			type : 'post',
			async : false,
			data : $('#formEmpresaContactoEmpresaSupervisada').serialize(),
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					mensajeGrowl('success', constant.confirm.edit);
					loading.close();
					coUnOpMaUnOp.procesarGridReprEmprSupe(0, "ReprEmprSupe",$('#idEmpresaSupervisadaUO').val());
					coUnOpMaUnOp.limpiarEmpresaContactoEmpresaSupervisada();
					$('#btnEditarRepresentanteES').css('display','none');
					$('#btnGuardarRepresentanteES').css('display','inline-block');
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	eliminarEmpContactoEmpSup : function(idEmpresaContacto){
		confirmer.open('¿Confirma que desea eliminar el representante de la Empresa Supervisada?','coUnOpMaUnOp.confirmaEliminarEmpContactoEmpSup('+idEmpresaContacto+');');
	},
	confirmaEliminarEmpContactoEmpSup : function(idEmpresaContacto){
		$.ajax({
			url : baseURL+ "pages/empresaSupervisada/eliminarEmpresaContactoEmpresaSupervisada",
			type : 'post',
			async : false,
			data : {
				idEmpresaContacto : idEmpresaContacto
			},
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					mensajeGrowl('success', constant.confirm.remove);
					coUnOpMaUnOp.procesarGridReprEmprSupe(0, "ReprEmprSupe",$('#idEmpresaSupervisadaUO').val());
					coUnOpMaUnOp.limpiarEmpresaContactoEmpresaSupervisada();
					loading.close();
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	guardarEmpresaContactoEmpresaSupervisada:function(){
		$.ajax({
			url : baseURL + "pages/empresaSupervisada/guardarEmpresaContactoEmpresaSupervisada",
			type : 'post',
			async : false,
			data : $('#formEmpresaContactoEmpresaSupervisada').serialize(),
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					mensajeGrowl('success', constant.confirm.save);
					loading.close();
					coUnOpMaUnOp.procesarGridReprEmprSupe(0, "ReprEmprSupe",$('#idEmpresaSupervisadaUO').val());
					coUnOpMaUnOp.limpiarEmpresaContactoEmpresaSupervisada();
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	procesarGridUnidadOperativa : function(flg_load, nombreGrid,idEmpresaSupervisada) {
		if (flg_load === undefined) {
			flg_load = 1;
		}
		var nombres = ['ID', 'CODIGO OSINERGMIN', 'NOMBRE', 'RUBRO' ];
		var columnas = [{
			name : "idUnidadSupervisada",
			width : 120,
			sortable : false,
			align : "left",
			hidden: true
		}, {
			name : "codigoOsinergmin",
			width : 120,
			sortable : false,
			align : "left"
		}, {
			name : "nombreUnidad",
			width : 300,
			sortable : false,
			align : "left"
		}, {
			name : "actividad.nombre",
			width : 370,
			sortable : false,
			align : "left"
		}, ];
		$("#gridContenedor" + nombreGrid).html("");
		var grid = $("<table>", {
			"id" : "grid" + nombreGrid
		});
		var pager = $("<div>", {
			"id" : "paginacion" + nombreGrid
		});
		$("#gridContenedor" + nombreGrid).append(grid).append(pager);

		grid.jqGrid({
			url: baseURL + "pages/unidadSupervisada/listarUnidadesSupervisadas",
			datatype: "json",
//			datatype : "local",
			postData : {
				idEmpresaSupervisada : idEmpresaSupervisada
			},
			hidegrid : false,
			rowNum : constant.rowNum,
			pager : "#paginacion" + nombreGrid,
			emptyrecords : "No se encontraron resultados",
			recordtext : "{0} - {1}",
			loadtext : "Cargando",
			colNames : nombres,
			colModel : columnas,
			height : "auto",
			viewrecords : true,
			caption : "Listado Unidades Operativas",
			jsonReader : {
				root : "filas",
				page : "pagina",
				total : "total",
				records : "registros",
				repeatitems : false,
				id : "idUnidadSupervisada"
			},
			onSelectRow : function(rowid, status) {
				grid.resetSelection();
				var row=$('#gridUnidOper').jqGrid("getRowData",rowid);
                $('#linkEditarUniOper').attr('onClick', 'coUnOpMaUnOp.obtenerUnidadSupervisada("'+rowid+'")');
//                $('#linkEliminarUniOper').attr('onClick', 'coUnOpMaUnOp.eliminarUnidadSupervisada("'+rowid+'")');
                $('#linkVerDireccionesUniOper').attr('onClick', 'coUnOpMaUnOp.listarDireccionesUnidadSupervisada("'+rowid+'")');
			},
			loadComplete : function(data) {
				$('#contextMenuUnidOper').parent().remove();
                $('#divContextMenuUnidOper').html("<ul id='contextMenuUnidOper'>"
                        + "<li> <a id='linkEditarUniOper' data-icon='ui-icon-pencil'>Editar</a></li>"                        
                        + "<li> <a id='linkVerDireccionesUniOper' data-icon='ui-icon-trash'>Ver Direcciones</a></li>"
//                        + "<li> <a id='linkEliminarUniOper' data-icon='ui-icon-trash'>Eliminar</a></li>"
                        + "</ul>");
                $('#contextMenuUnidOper').puicontextmenu({
                    target: $('#gridUnidOper')
                });
			}
		});
	},
	obtenerUnidadSupervisada : function(idUnidadSupervisada){
		$.ajax({
			url : baseURL + "pages/unidadSupervisada/obtenerUnidadSupervisada",
			type : 'post',
			async : false,
			data : {
				idUnidadSupervisada : idUnidadSupervisada
			},
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {					
					$('#txtIdUnidadSupervisadaUS').val(data.unidad.idUnidadSupervisada);
					$('#txtCodigoOsinergminUS').val(data.unidad.codigoOsinergmin);
					$('#txtIdRHUS').val(data.unidad.idRegistroHidrocarburo);
					$('#txtCodigoRHUS').val(data.unidad.nroRegistroHidrocarburo);
					$('#txtNombreUS').val(data.unidad.nombreUnidad);
					$('#txtIdActivSel').val(data.unidad.idActividad);
					$('#txtActivSel').val(data.unidad.nombreActividad);
					$('#txaObservacionUS').html(data.unidad.observacion);
					$('#txaObservacionUS').val(data.unidad.observacion);
					$('#idUnidadSupervisadaDireccionUS').val(data.unidad.idUnidadSupervisada);
					$('#btnEditarUnidadSupervisada').css('display','inline-block');
					$('#btnGuardarUnidadSupervisada').css('display','none');
//					mensajeGrowl('success', data.mensaje);
					loading.close();
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	eliminarUnidadSupervisada : function(idUnidadSupervisada){
		$.ajax({
			url : baseURL+ "pages/unidadSupervisada/eliminarUnidadSupervisada",
			type : 'post',
			async : false,
			data : {
				idUnidadSupervisada : idUnidadSupervisada
			},
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					mensajeGrowl('success', constant.confirm.remove);
					coUnOpMaUnOp.procesarGridUnidadOperativa(0, "UnidOper",$('#idEmpresaSupervisadaUS').val());
					loading.close();
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	validaGuardarUnidOper : function() {
		$("#dialogUnidadOperativa").dialog('close');
	},
	actualizarEmpresaSupervisada : function() {
		$.ajax({
					url : baseURL + "pages/empresaSupervisada/actualizarEmpresaSupervisada",
					type : 'post',
					async : false,
					data : {
						idEmpresaSupervisada : $('#idEmpresaSupervisadaUO')
								.val(),
						razonSocial : $('#razonSocialEmpresaSupervisada').val(),
						ruc : $('#rucEmpresaSupervisada').val(),
						telefono : $('#telefonoEmpresaSupervisada').val(),
						nombreComercial : $(
								'#nombreComercialEmpresaSupervisada').val(),
						ciu : $('#ciuEmpresaSupervisada').val()
					},
					beforeSend : loading.open,
					success : function(data) {
						if (data.resultado == 0) {
							mensajeGrowl('success', constant.confirm.edit);
							loading.close();
						} else if (data.resultado == 2) {
							mensajeGrowl('warn', data.mensaje);
							loading.close();
						} else {
							mensajeGrowl('error', data.mensaje);
							loading.close();
						}
					},
					error : errorAjax
				});
	},
	cargarProvincia : function() {
		$data = {
			idDepartamento : $('#departamentoES').val()
		};
		var url = "pages/ubigeo/buscarProvincias";
		$.post(url, $data, function(data) {
			var html = '<option value="">--Seleccione--</option>';
			var len = data.length;
			for ( var i = 0; i < len; i++) {
				html += '<option value="' + data[i].idProvincia + '">'
						+ data[i].nombre + '</option>';
			}
			html += '</option>';
			$("#provinciaES").html(html);
			html = '<option value="">--Seleccione--</option>';
			$("#distritoES").html(html);
		});
	},

	cargarDistrito : function() {
		$data = {
			idDepartamento : $('#departamentoES').val(),
			idProvincia : $('#provinciaES').val()
		};
		var url = "pages/ubigeo/buscarDistritos";
		$.post(url, $data, function(data) {
			var html = '<option value="">--Seleccione--</option>';
			var len = data.length;
			for ( var i = 0; i < len; i++) {
				html += '<option value="' + data[i].idDistrito + '">'
						+ data[i].nombre + '</option>';
			}
			html += '</option>';
			$("#distritoES").html(html);
		});
	},
	cargarProvinciaUS : function() {
		$data = {
			idDepartamento : $('#departamentoUS').val()
		};
		var url = "pages/ubigeo/buscarProvincias";
		$.post(url, $data, function(data) {
			var html = '<option value="">--Seleccione--</option>';
			var len = data.length;
			for ( var i = 0; i < len; i++) {
				html += '<option value="' + data[i].idProvincia + '">'
						+ data[i].nombre + '</option>';
			}
			html += '</option>';
			$("#provinciaUS").html(html);
			html = '<option value="">--Seleccione--</option>';
			$("#distritoUS").html(html);
		});
	},

	cargarDistritoUS : function() {
		$data = {
			idDepartamento : $('#departamentoUS').val(),
			idProvincia : $('#provinciaUS').val()
		};
		var url = "pages/ubigeo/buscarDistritos";
		$.post(url, $data, function(data) {
			var html = '<option value="">--Seleccione--</option>';
			var len = data.length;
			for ( var i = 0; i < len; i++) {
				html += '<option value="' + data[i].idDistrito + '">'
						+ data[i].nombre + '</option>';
			}
			html += '</option>';
			$("#distritoUS").html(html);
		});
	},
	cargarSerie:{
		cargarDepartamento : function(data){
			if(data.direccion.idDepartamento<10){
				idDepartamentoArmado = "0"+data.direccion.idDepartamento;
				$('#departamentoES').val(idDepartamentoArmado);
				coUnOpMaUnOp.cargarSerie.cargarProvincia(data);				
			}else{
				$('#departamentoES').val(data.direccion.idDepartamento);
				coUnOpMaUnOp.cargarSerie.cargarProvincia(data);
			}
		},
		cargarProvincia : function(datav) {
			$data = {
				idDepartamento : $('#departamentoES').val()
			};
			var url = "pages/ubigeo/buscarProvincias";
			$.post(url, $data, function(data) {				
				var html = '<option value="">--Seleccione--</option>';
				var len = data.length;
				for ( var i = 0; i < len; i++) {
					html += '<option value="' + data[i].idProvincia + '">'
							+ data[i].nombre + '</option>';
				}
				html += '</option>';
				$("#provinciaES").html(html);
				html = '<option value="">--Seleccione--</option>';
				$("#distritoES").html(html);

				if(datav.direccion.idProvincia<10){					
					idProvincia = "0"+datav.direccion.idProvincia;
					$('#provinciaES').val(idProvincia);
					coUnOpMaUnOp.cargarSerie.cargarDistrito(datav);
					
				}else{
					$('#provinciaES').val(datav.direccion.idProvincia);
					coUnOpMaUnOp.cargarSerie.cargarDistrito(datav);
				}
				
				
			});
		},

		cargarDistrito : function(datav) {
			$data = {
				idDepartamento : $('#departamentoES').val(),
				idProvincia : $('#provinciaES').val()
			};
			var url = "pages/ubigeo/buscarDistritos";
			$.post(url, $data, function(data) {
				var html = '<option value="">--Seleccione--</option>';
				var len = data.length;
				for ( var i = 0; i < len; i++) {
					html += '<option value="' + data[i].idDistrito + '">'
							+ data[i].nombre + '</option>';
				}
				html += '</option>';
				$("#distritoES").html(html);
				
				if(datav.direccion.idDistrito<10){
					idDistrito = "0"+datav.direccion.idDistrito;
					$('#distritoES').val(idDistrito);
					
				}else{
					$('#distritoES').val(datav.direccion.idDistrito);
				}
			});
		}
		
	},
	
	procesarGridDireUnidSupe : function(flg_load, nombreGrid,idUnidadSupervisada) {
		if (flg_load === undefined) {
			flg_load = 1;
		}

		var nombres = ['ID', 'TIPO DIRECCION', 'DIRECCION', 'DEPARTAMENTO',
				'PROVINCIA', 'DISTRITO' ];
		var columnas = [ {
			name : "idDirccionUnidadSuprvisada",
			width : 90,
			sortable : false,
			align : "left",
			hidden : true
		},  {
			name : "tipoDireccion",
			width : 90,
			sortable : false,
			align : "left"
		}, {
			name : "direccionCompleta",
			width : 450,
			sortable : false,
			align : "left"
		}, {
			name : "departamento",
			width : 100,
			sortable : false,
			align : "left"
		}, {
			name : "provincia",
			width : 70,
			sortable : false,
			align : "left"
		}, {
			name : "distrito",
			width : 70,
			sortable : false,
			align : "left"
		} ];
		$("#gridContenedor" + nombreGrid).html("");
		var grid = $("<table>", {
			"id" : "grid" + nombreGrid
		});
		var pager = $("<div>", {
			"id" : "paginacion" + nombreGrid
		});
		$("#gridContenedor" + nombreGrid).append(grid).append(pager);

		grid.jqGrid({
			 url: baseURL + "pages/unidadSupervisada/listarDireccionUnidadSupervisada",
			datatype : "json",
			// datatype: "local",
			postData : {
				idUnidadSupervisada : idUnidadSupervisada
			},
			hidegrid : false,
			rowNum : constant.rowNum,
			pager : "#paginacion" + nombreGrid,
			emptyrecords : "No se encontraron resultados",
			recordtext : "{0} - {1}",
			loadtext : "Cargando",
			colNames : nombres,
			colModel : columnas,
			height : "auto",
			viewrecords : true,
			caption : "Listado Direcciones",
			jsonReader : {
				root : "filas",
				page : "pagina",
				total : "total",
				records : "registros",
				repeatitems : false,
				id : "idDirccionUnidadSuprvisada"
			},
			onSelectRow : function(rowid, status) {
				grid.resetSelection();
                $('#linkEditarDirUniSup').attr('onClick', 'coUnOpMaUnOp.obtDireccionUnidadSupervisada("'+rowid+'")');
                $('#linkEliminarDirUniSup').attr('onClick', 'coUnOpMaUnOp.eliminarDireccionUnidadSupervisada("'+rowid+'")');
			},
			loadComplete : function(data) {
				$('#contextMenuDireUnidOper').parent().remove();
                $('#divContextMenuDireUnidOper').html("<ul id='contextMenuDireUnidOper'>"
                        + "<li> <a id='linkEditarDirUniSup' data-icon='ui-icon-pencil'>Editar</a></li>"
                        + "<li> <a id='linkEliminarDirUniSup' data-icon='ui-icon-trash'>Eliminar</a></li>"
                        + "</ul>");
                $('#contextMenuDireUnidOper').puicontextmenu({
                    target: $('#gridDireUnidSupe')
                });
			}
		});
	},
	obtDireccionUnidadSupervisada : function(idDirccionUnidadSuprvisada){
		$.ajax({
			url : baseURL + "pages/unidadSupervisada/obtenerDireccionUnidadSupervisada",
			type : 'post',
			async : false,
			data : {
				idDirccionUnidadSuprvisada : idDirccionUnidadSuprvisada
			},
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					$('#idDireccionUnidadSupervisadaUS').val(data.direccion.idDirccionUnidadSuprvisada);
					$('#tipoDireccionUS').val(data.direccion.idTipoDireccion);
//					$('#departamentoUS').val(data.direccion.idDepartamento);
					$('#tipoViaUS').val(data.direccion.idTipoVia);
					$('#descripcionViaUS').val(data.direccion.descripcionVia);
					$('#numeroViaUS').val(data.direccion.numeroVia);
					$('#interiorUS').val(data.direccion.interior);
					$('#manzanaUS').val(data.direccion.manzana);
					$('#loteUS').val(data.direccion.lote);
					$('#kilometroUS').val(data.direccion.kilometro);
					$('#blockUS').val(data.direccion.blockChallet);
					$('#etapaUS').val(data.direccion.etapa);
					$('#numeroDepartamentoUS').val(data.direccion.numeroDepartamento);
					$('#urbanizacionUS').val(data.direccion.urbanizacion);
					$('#direccionCompletaUS').val(data.direccion.direccionCompleta);
					$('#referenciaUS').val(data.direccion.referencia);
					$('#btnEditarDireccionUS').css('display','inline-block');
					$('#btnGuardarDireccionUS').css('display','none');
					coUnOpMaUnOp.cargarSerieUnidad.cargarDepartamento(data);
//					mensajeGrowl('success', data.mensaje);
					loading.close();
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	eliminarDireccionUnidadSupervisada : function(idDireccionUnidadSupervisada){
		confirmer.open('¿Confirma que desea eliminar la dirección de la Unidad Operativa?','coUnOpMaUnOp.confirmaEliminarDireccionUnidadSupervisada('+idDireccionUnidadSupervisada+');');		
	},
	confirmaEliminarDireccionUnidadSupervisada : function(idDireccionUnidadSupervisada){
		$.ajax({
			url : baseURL+ "pages/unidadSupervisada/eliminarDireccionUnidadSupervisada",
			type : 'post',
			async : false,
			data : {
				idDireccionUnidadSupervisada : idDireccionUnidadSupervisada
			},
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					mensajeGrowl('success', constant.confirm.remove);
					coUnOpMaUnOp.procesarGridDireUnidSupe(0, "DireUnidSupe",$('#txtIdUnidadSupervisadaUS').val());
					coUnOpMaUnOp.limpiarDireccionUnidadSupervisada();
					loading.close();
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	guardarDireccionUnidadSupervisada:function(){
		$.ajax({
			url : baseURL + "pages/unidadSupervisada/guardarDireccionUnidadSupervisada",
			type : 'post',
			async : false,
			data : $('#formDireccionUnidadSupervisada').serialize(),
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					mensajeGrowl('success', constant.confirm.save);
					loading.close();
					coUnOpMaUnOp.procesarGridDireUnidSupe(0, "DireUnidSupe",$('#txtIdUnidadSupervisadaUS').val());
					coUnOpMaUnOp.limpiarDireccionUnidadSupervisada();
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	actualizarDireccionUnidadSupervisada:function(){
		$.ajax({
			url : baseURL + "pages/unidadSupervisada/actualizarDireccionUnidadSupervisada",
			type : 'post',
			async : false,
			data : $('#formDireccionUnidadSupervisada').serialize(),
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					mensajeGrowl('success', constant.confirm.edit);
					loading.close();
					coUnOpMaUnOp.procesarGridDireUnidSupe(0, "DireUnidSupe",$('#txtIdUnidadSupervisadaUS').val());
					coUnOpMaUnOp.limpiarDireccionUnidadSupervisada();
					$('#btnEditarDireccionUS').css('display','none');
					$('#btnGuardarDireccionUS').css('display','inline-block');
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	cargarSerieUnidad:{
		cargarDepartamento : function(data){			
				$('#departamentoUS').val(data.direccion.idDepartamento);
				coUnOpMaUnOp.cargarSerieUnidad.cargarProvincia(data);
		},
		cargarProvincia : function(datav) {
			$data = {
				idDepartamento : $('#departamentoUS').val()
			};
			var url = "pages/ubigeo/buscarProvincias";
			$.post(url, $data, function(data) {				
				var html = '<option value="">--Seleccione--</option>';
				var len = data.length;
				for ( var i = 0; i < len; i++) {
					html += '<option value="' + data[i].idProvincia + '">'
							+ data[i].nombre + '</option>';
				}
				html += '</option>';
				$("#provinciaUS").html(html);
				html = '<option value="">--Seleccione--</option>';
				$("#distritoUS").html(html);

				$('#provinciaUS').val(datav.direccion.idProvincia);
				coUnOpMaUnOp.cargarSerieUnidad.cargarDistrito(datav);				
				
			});
		},

		cargarDistrito : function(datav) {
			$data = {
				idDepartamento : $('#departamentoUS').val(),
				idProvincia : $('#provinciaUS').val()
			};
			var url = "pages/ubigeo/buscarDistritos";
			$.post(url, $data, function(data) {
				var html = '<option value="">--Seleccione--</option>';
				var len = data.length;
				for ( var i = 0; i < len; i++) {
					html += '<option value="' + data[i].idDistrito + '">'
							+ data[i].nombre + '</option>';
				}
				html += '</option>';
				$("#distritoUS").html(html);
				$('#distritoUS').val(datav.direccion.idDistrito);
			});
		}
		
	},
	limpiarDireccionEmpresaSupervisada:function(){
		$('#idDireccionEmpresaSupervisadaUO').val('');
		$('#tipoDireccionES').val('');
		$('#departamentoES').val('');
		$('#tipoViaES').val('');
		$('#descTipoViaES').val('');
		$('#numeroDireccionES').val('');
		$('#interiorDireccionES').val('');
		$('#manzanaDireccionES').val('');
		$('#loteDireccionES').val('');
		$('#kilometroDireccionES').val('');
		$('#blockDireccionES').val('');
		$('#etapaDireccionES').val('');
		$('#numeroDepartamentoDireccionES').val('');
		$('#urbanizacionDireccionES').val('');
		$('#direccionCompletaES').val('');
		$('#referenciaDireccionES').val('');
		$('#provinciaES').val('');
		$('#distritoES').val('');
	},
	limpiarEmpresaContactoEmpresaSupervisada:function(){
		$('#idEmpresaContactoUO').val('');
		$('#tipoDocumentoES').val('');
		$('#numeroDocumentoRepresentanteES').val('');
		$('#cargoRepresentanteES').val('');
		$('#apellidoPaternoRepresentanteES').val('');
		$('#apellidoMaternoRepresentanteES').val('');
		$('#nombreRepresentanteES').val('');
		$('#correoRepresentanteES').val('');
		$('#telefonoRepresentanteES').val('');
	},
	limpiarUnidadSupervisada:function(){
		$('#txtCodigoOsinergminUS').val('');
		$('#txtCodigoRHUS').val('');
		$('#txtNombreUS').val('');
		$('#txtActivSel').val('');
		$('#txtIdActivSel').val('');
		$('#txaObservacionUS').val('');
	},
	limpiarDireccionUnidadSupervisada:function(){
		$('#tipoDireccionUS').val('');
		$('#departamentoUS').val('');
		$('#tipoViaUS').val('');
		$('#descripcionViaUS').val('');
		$('#numeroViaUS').val('');
		$('#interiorUS').val('');
		$('#manzanaUS').val('');
		$('#loteUS').val('');
		$('#kilometroUS').val('');
		$('#blockUS').val('');
		$('#etapaUS').val('');
		$('#numeroDepartamentoUS').val('');
		$('#urbanizacionUS').val('');
		$('#direccionCompletaUS').val('');
		$('#referenciaUS').val('');
		$('#provinciaUS').val('');
		$('#distritoUS').val('');
		$('#idDireccionUnidadSupervisadaUS').val('');
	},
	listarDireccionesUnidadSupervisada:function(idUnidadSupervisada){
		$('#detalleDireccionUnidadSupervisada').css('display','block');
		$('#idUnidadSupervisadaDireccionUS').val(idUnidadSupervisada);
		$('#txtIdUnidadSupervisadaUS').val(idUnidadSupervisada);
		coUnOpMaUnOp.procesarGridDireUnidSupe(0, "DireUnidSupe",idUnidadSupervisada);
	},
	ocultaListarDireccionesUnidadSupervisada:function(){
		$('#detalleDireccionUnidadSupervisada').css('display','none');
	},
	guardarUnidadSupervisada:function(){
		$.ajax({
			url : baseURL + "pages/unidadSupervisada/guardarUnidadSupervisada",
			type : 'post',
			async : false,
			data : $('#formUnidadSupervisada').serialize(),
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					mensajeGrowl('success', constant.confirm.save);
					loading.close();
					coUnOpMaUnOp.procesarGridUnidadOperativa(0, "UnidOper",$('#idEmpresaSupervisadaUS').val());
					$('#txtCodigoOsinergminUS').val(data.unidadSupervisada.codigoOsinergmin);
					coUnOpMaUnOp.limpiarUnidadSupervisada();					
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
	},
	actualizarUnidadSupervisada:function() {
		$.ajax({
			url : baseURL + "pages/unidadSupervisada/actualizarUnidadSupervisada",
			type : 'post',
			async : false,
			data : {
				'empresaSupervisada.idEmpresaSupervisada' : $('#idEmpresaSupervisadaUS').val(),
				idUnidadSupervisada : $('#txtIdUnidadSupervisadaUS').val(),
				codigoOsinergmin : $('#txtCodigoOsinergminUS').val(),
				nombreUnidad : $('#txtNombreUS').val(),
				'actividad.idActividad' : $('#txtIdActivSel').val(),
				observacion : $('#txaObservacionUS').val(),
				nroRegistroHidrocarburo:$('#txtCodigoRHUS').val(),
				idRegistroHidrocarburo:$('#txtIdRHUS').val()
			},
			beforeSend : loading.open,
			success : function(data) {
				if (data.resultado == 0) {
					mensajeGrowl('success', constant.confirm.edit);
					coUnOpMaUnOp.procesarGridUnidadOperativa(0, "UnidOper",$('#idEmpresaSupervisadaUS').val());
					coUnOpMaUnOp.limpiarUnidadSupervisada();
					$('#btnEditarUnidadSupervisada').css('display','none');
					$('#btnGuardarUnidadSupervisada').css('display','inline-block');
					loading.close();
				} else if (data.resultado == 2) {
					mensajeGrowl('warn', data.mensaje);
					loading.close();
				} else {
					mensajeGrowl('error', data.mensaje);
					loading.close();
				}
			},
			error : errorAjax
		});
}
};

$(function() {
	$('#txtCodigoRHUS').alphanum(constant.valida.alphanum.telefono);
	$('#txtNombreUS').alphanum(constant.valida.alphanum.descrip);
	$('#txaObservacionUS').alphanum(constant.valida.alphanum.descrip);
	$('#kilometroDireccionES').alphanum(constant.valida.alphanum.numerico);
	$('#descTipoViaES').alphanum(constant.valida.alphanum.descrip);
	$('#nombreComercialEmpresaSupervisada').alphanum(constant.valida.alphanum.descrip);
	$('#telefonoEmpresaSupervisada').alphanum(constant.valida.alphanum.telefono);
	$('#telefonoRepresentanteES').alphanum(constant.valida.alphanum.telefono);
        
        $('#descripcionViaUS,#interiorUS,#manzanaUS,#loteUS,#kilometroUS,#direccionCompletaUS,#referenciaUS,#numeroDepartamentoUS,#urbanizacionUS').alphanum(constant.valida.alphanum.descrip);
	        
	boton.closeDialog();
	$('#btnGuardarEmpresaSupervisada').click(function() {
		valida = $('#formEmpresaSupervisadaInps').validateAllForm('divMensajeValidaEmpresaSupervisada');
		var mensajeValidacion = "";
		validaTelefono=false;	
		if(valida){
			telefono = $('#telefonoEmpresaSupervisada').val();
			if(telefono.length<7){
				validaTelefono=false;
				mensajeValidacion="Telefono(*): La cantidad de caracteres no puede ser menor a 7";
			}
			var divValidacion = $('#divMensajeValidaEmpresaSupervisada');            	
	        if (mensajeValidacion != "") {
	        	divValidacion.show();
	            divValidacion.focus();
	            divValidacion.html(mensajeValidacion);
	            validaTelefono = false;
	        } else {
	            divValidacion.hide();
	            divValidacion.html("");
	            validaTelefono = true;
	        }
			
			if(validaTelefono){			
				confirmer.start();
				confirmer.open('¿Confirma que desea actualizar la informaci&oacute;n de la Empresa Supervisada?','coUnOpMaUnOp.actualizarEmpresaSupervisada();');	
			}
		}
		
	});
	$('#btnGuardarUnidOper').click(function() {
		coUnOpMaUnOp.validaGuardarUnidOper();
	});
	$('#tabsUnidadOperativa').tabs();
	$('#txtActivSel').click(function() {
		common.abrirPopupSeleccActividad('individual', 'Sel');
	});

	$('#fldstDireccionES').puifieldset({
		toggleable : true,
		collapsed : true
	});
	$('#fldstRepresentanteES').puifieldset({
		toggleable : true,
		collapsed : true
	});
	
	coUnOpMaUnOp.procesarGridDireEmprSupe(0, "DireEmprSupe",$('#idEmpresaSupervisadaUO').val());
	coUnOpMaUnOp.procesarGridReprEmprSupe(0, "ReprEmprSupe",$('#idEmpresaSupervisadaUO').val());
	$('#fldstDireccionUO').puifieldset({
		toggleable : true,
		collapsed : true
	});

	coUnOpMaUnOp.procesarGridUnidadOperativa(0, "UnidOper",$('#idEmpresaSupervisadaUS').val());
	$('#departamentoES').change(function() {
		coUnOpMaUnOp.cargarProvincia();
	});
	$('#provinciaES').change(function() {
		coUnOpMaUnOp.cargarDistrito();
	});
	$('#departamentoUS').change(function() {
		coUnOpMaUnOp.cargarProvinciaUS();
	});
	$('#provinciaUS').change(function() {
		coUnOpMaUnOp.cargarDistritoUS();
	});
	$('#btnGuardarDireccionES').click(function(){
		validar = $('#formDireccionEmpresaSupervisada').validateAllForm('divMensajeValidacion');	
		if(validar){
			confirmer.open('¿Confirma que desea registrar la direcci&oacute;n de la Empresa Supervisada?','coUnOpMaUnOp.guardarDireccionEmpresaSupervisada();');	
		}
		
	});
	$('#btnGuardarDireccionUS').click(function(){
		validar = $('#formDireccionUnidadSupervisada').validateAllForm('divMensajeValidacion');
		if(validar){
			confirmer.open('¿Confirma que desea registrar la dirección de la Unidad Operativa?','coUnOpMaUnOp.guardarDireccionUnidadSupervisada();');			
		}
		
	});
	$('#btnEditarDireccionES').click(function(){
		validar = $('#formDireccionEmpresaSupervisada').validateAllForm('divMensajeValidacion');
		if(validar){
			confirmer.open('¿Confirma que desea actualizar la información de la dirección de la Empresa Supervisada?','coUnOpMaUnOp.actualizarDireccionEmpresaSupervisada();');	
		}
	});
	$('#btnEditarDireccionUS').click(function(){
		validar = $('#formDireccionUnidadSupervisada').validateAllForm('divMensajeValidacion');
		if(validar){
			confirmer.open('¿Confirma que desea actualizar la información de la dirección de la Unidad Operativa?','coUnOpMaUnOp.actualizarDireccionUnidadSupervisada();');	
		}
	});
	$('#btnEditarRepresentanteES').click(function(){
		validar = $('#formEmpresaContactoEmpresaSupervisada').validateAllForm('divMensajeValidacion');
		if(validar){
			confirmer.open('¿Confirma que desea actualizar la información del representante de la Empresa Supervisada?','coUnOpMaUnOp.actualizarEmpresaContactoEmpresaSupervisada();');						
		}
	});
	$('#btnGuardarRepresentanteES').click(function(){
		validar = $('#formEmpresaContactoEmpresaSupervisada').validateAllForm('divMensajeValidacion');
		var mensajeValidacion = "";
		validaTelefono=false;	
		if(validar){
			telefono = $('#telefonoRepresentanteES').val();
			if(telefono.length<7){
				validaTelefono=false;
				mensajeValidacion="REPRESENTANTES: Telefono(*) La cantidad de caracteres no puede ser menor a 7";
				$('#telefonoRepresentanteES').addClass("error");
			}else{
				$('#telefonoRepresentanteES').removeClass("error");
			}
			var divValidacion = $('#divMensajeValidaEmpresaSupervisada');            	
	        if (mensajeValidacion != "") {
	        	divValidacion.show();
	            divValidacion.focus();
	            divValidacion.html(mensajeValidacion);
	            validaTelefono = false;
	        } else {
	            divValidacion.hide();
	            divValidacion.html("");
	            validaTelefono = true;
	        }
			
			if(validaTelefono){	
				confirmer.open('¿Confirma que desea registrar el representante de la Empresa Supervisada?','coUnOpMaUnOp.guardarEmpresaContactoEmpresaSupervisada();');	
			}
		}
	});
	$('#tipoDocumentoES').change(function(){
		changeTipoDocu('#tipoDocumentoES','#numeroDocumentoRepresentanteES','#formEmpresaContactoEmpresaSupervisada');
	});
	$('#btnGuardarUnidadSupervisada').click(function(){
		validar = $('#formUnidadSupervisada').validateAllForm('divMensajeValidaEmpresaSupervisada');
		if(validar){
			coUnOpMaUnOp.guardarUnidadSupervisada();
		}
		
	});
	$('#btnEditarUnidadSupervisada').click(function(){
		validar = $('#formUnidadSupervisada').validateAllForm('divMensajeValidaEmpresaSupervisada');
		if(validar){
			confirmer.open('¿Confirma que desea actualizar la informaci&oacute;n de la Unidad Supervisada?','coUnOpMaUnOp.actualizarUnidadSupervisada();');
		}		
	});
});