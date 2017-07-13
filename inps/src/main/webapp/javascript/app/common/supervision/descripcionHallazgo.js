//variables globales
var tipoDH = "";
var tipoCumple = "";
var NivelHallazgo = false; // true >> Solo hallazgo   mdiosesf -RSIS6
var ArrayDEvaluacion = []; //mdiosesf -RSIS6
var coSupeHalla=(function() {
	function constructor() {		
		tipoDH = $('#tipoDH').val(); // Consulta - Registro
		tipoCumple = $('#tipoCumple').val(); // cumple - incumple
		grillaArchivosMediosProbatorio();
		if(tipoCumple==constant.tipoCumple.incumple){
				cargarTipificaciones(tipoDH);
				cargarCriterios(tipoDH);
				cargarSancionesEspecificas();				
		}
		comportamiento();		
		if (tipoDH==constant.modoSupervision.consulta){activarModoConsulta();}	
		if(tipoCumple==constant.tipoCumple.incumple){ procesarGridTipificacion(); }//mdiosesf - RSIS6
		else{ NivelHallazgo = true; procesarGridTipificacion(); $('#divDescripcionHallazgo').css('display','inline');$('#divTipificacionGrilla').css('display','none'); }//mdiosesf - RSIS6
		$('#txtDescripcion').alphanum(constant.valida.alphanum.descrip);
		$('#txtDescripcionHallazgo').alphanum(constant.valida.alphanum.comentario);
	}
		
	function cargarTipificaciones(tipoDH) {
		$('#lblComentarioHallazgo').empty();
		$('#lblComentarioHallazgo').append("Comentario del hallazgo(*):");
		$("#divCriterio,#divSancionEspe").css("display","none");
		var valTipifica=$('#idTipificacionDH').val();
		if (tipoDH==constant.modoSupervision.registro) {cargaTipificacionRadio(generadorRadio,'#tipificacionCmbRadio');}
		if (tipoDH==constant.modoSupervision.consulta) {
			if (valTipifica>0) {
				cargaTipificacionRadio(generadorRadio,'#tipificacionCmbRadio', $('#idTipificacionDH').val());
				$('input:radio[name=radioTipificacion]:checked').attr('disabled','disabled');
				$('#cmbTipificacion').css('height','45px');
			}
		}
		if (valTipifica>0) {$('#chkT_radioTipificacion'+valTipifica).attr('checked','checked');}
		if ($('input:radio[name=radioTipificacion]:checked').val()>0) {
			cargarSanciones('input:radio[name=radioTipificacion]:checked');
		}else{$('#divSancion').css("display","none");}
	}
	
	function cargarCriterios(tipoDH){
		$("#divCriterio,#divSancionEspe").css("display","none");
		var valCriterio=$('#idCriterioDH').val();
		if (tipoDH==constant.modoSupervision.registro) {
			var radioTipifica = $('input:radio[name=radioTipificacion]:checked').val();
			if(radioTipifica>0){
				cargarCriterioRad(generadorRadio,'input:radio[name=radioTipificacion]:checked','#criterioCmbRadio');
			}
		}else if (tipoDH==constant.modoSupervision.consulta) {
			if(valCriterio>0){
				cargarCriterioRad(generadorRadio,'input:radio[name=radioTipificacion]:checked','#criterioCmbRadio',valCriterio);
				$('#cmbTipificacion').css('height','45px');
				$('#cmbCriterio').css('height','45px');
				$('input:radio[name=radioTipificacion]:checked').attr('disabled','disabled');
				$('input:radio[name=radioCriterio]:checked').attr('disabled','disabled');
			}
		}
		if(valCriterio>0){
			$('#chkT_radioCriterio'+valCriterio).attr('checked','checked');
		}
	}
	
	function cargarSancionesEspecificas(){
		var valCriterio = $('input:radio[name=radioCriterio]:checked').val();
		if (valCriterio>0) {
			cargarSancionEspecifica(valCriterio);
		}
	}
	
	function comportamiento(){
		var ventanaMedio=true;
		$("#btnAceptarDescripcion").click(function() {			
			var idTipificacionCriterio = $('#idTipificacionCriterio').val();
			var tieneCriterio = $('#tieneCriterio_' + idTipificacionCriterio).val();
			var descripcion = $('#txtDescripcion').val();			
			if(tieneCriterio==null){ tieneCriterio = 1;}
			if(tieneCriterio==0){ 
				$('#comentario_'+idTipificacionCriterio).val(descripcion);
				var ArrayDE=[{idCriterio:null,idTipificacion:idTipificacionCriterio,idDetalleCriterio:null,idDetalleSupervision:null, descripcionResultado:descripcion, idDetalleEvaluacion:null,tieneCriterio:tieneCriterio,flagRegistrado:null}];								
			}
			else { 
				$('#comentarioCriterio_'+idTipificacionCriterio).val(descripcion);
				var ArrayDE=[{idCriterio:idTipificacionCriterio,idTipificacion:null,idDetalleCriterio:null,idDetalleSupervision:null, descripcionResultado:descripcion, idDetalleEvaluacion:null,tieneCriterio:null,flagRegistrado:null}];					
			}  
			verificarArrayDEvaluacion(ArrayDE);
			$("#divComentarioHallazgo").dialog("close");
		});
		
		$("#btnGuardarDescripcionHallazgo").click(function() {
			guardarDescHallazgo();
		});
		$('#btnAgregarMedioProbatorio').click(function() {
			validarAgregarMedioProbatorio();
        });
		$('#btnCerrarDescripcionHallazgo').click(function() {
			var pagina=fxGrilla.obtenerPage('gridObligaciones');
        	fxGrilla.recargarGrillaPagina('gridObligaciones',pagina);
        });
		
		$("#fileArchivoSuper").change(function(){      
	        $("#file_nameSuper").val(quitaSlashDir($("#fileArchivoSuper").val())); 
	    });
		//Formularios
		$("#formMedioAprobatorio").ajaxForm({
			beforeSubmit: function () {loading.open();},
            dataType: 'json',
            resetForm: true,
            success: function(data) {
            	loading.close();
                if (data != null && data.error != null) {enviarDatosArchivoBL(data);}
            },
            error: errorAjax
        });
		
		//radios
	    $("input[name=radioTipificacion]").click(function () {
	    	$("#divSancionEspe").css("display","none");
			cargarSanciones('input:radio[name=radioTipificacion]:checked');
		    cargarCriterioRad(generadorRadio,'input:radio[name=radioTipificacion]:checked','#criterioCmbRadio');
		    cargarSancionesEspecificas();
		});
	    $(document).on('click', 'input:radio[name=radioCriterio]', function (event) {
	    	cargarSancionEspecifica($('input:radio[name=radioCriterio]:checked').val());
	    });
	    if (tipoDH==constant.modoSupervision.consulta){ventanaMedio = false;}
	    //acordion
		$('#fldstHallazgo').puifieldset({toggleable: true,collapsed:false});
		$('#fldstMedios').puifieldset({toggleable: true,collapsed:ventanaMedio});
	}
		
	function procesarGridTipificacion() { //mdiosesf-RSIS6	 	
		var nombres = ['idDetalleEvaluacion','idDetalleSupervision','idDetalleCriterio','idTipificacion','tieneCriterio','flagRegistrado', 'flagResultado','C&Oacute;D','DESCRIPCI&Oacute;N TIPIFICACI&Oacute;N','SANCI&Oacute;N','OTRAS SANCIONES','COMENTARIO DEL HALLAZGO',''];
        var columnas = [
            {name: "idDetalleEvaluacion", hidden:true},
            {name: "detalleSupervision.idDetalleSupervision", hidden:true},
            {name: "detalleCriterio.idDetalleCriterio", hidden:true},
            {name: "tipificacion.idTipificacion", hidden:true},
            {name: "tipificacion.tieneCriterio", hidden:true},
            {name: "flagRegistrado", hidden:true},
            {name: "flagResultado", hidden:true},
            {name: "tipificacion.codTipificacion", width: 50, sortable: false, align: "center"},
            {name: "tipificacion.descripcion", width: 260, sortable: false, align: "left"},
            {name: "tipificacion.sancionMonetaria", width: 60, sortable: false, align: "left",formatter:"formatoSancionTipificacion"},
            {name: "tipificacion.otrasSanciones", width: 80, sortable: false, align: "center"},
            {name: "", width: 270, sortable: false, align: "center", formatter:"comentarioTipificacion"},
            {name: "seleccion", width: 30, sortable: false, align: "center", formatter:"seleccionTipificacion"}
            ];
               
        var nombresSubGrid 	= ['idCriterio','idTipificacion','DESCRIPCI&Oacute;N CRITERIO ESPEC&Iacute;FICO','SANCI&Oacute;N ESPEC&Iacute;FICA','COMENTARIO DEL HALLAZGO',''];
        var columnasSubGrid = [{name: "idCriterio",hidden:true},
                               {name: "idTipificacion",hidden:true},
                               {name: "descripcion", width: 380, sortable: false, align: "left"},
                               {name: "sancionMonetaria", width: 80, sortable: false, align: "center",formatter:"formatoSancionCriterio"},
                               {name: "descripcionResultado", width: 270, sortable: false, align: "center", formatter:"comentarioCriterio"},
                               {name: "", width: 30, sortable: false, align: "center", valign: "top", formatter:"seleccionCriterio"}
                               ];
        
        $("#gridContenedorTipificaciones").html("");
        var grid = $("<table>", {
            "id": "gridTipificaciones"
        });
        var pager = $("<div>", {
            "id": "paginacionTipificaciones"
        });
        $("#gridContenedorTipificaciones").append(grid).append(pager);
    	grid.jqGrid({
    		url: baseURL + "pages/supervision/findTipificacionMultiple", 
    		datatype: "json",
    		postData: {
    			tipoDH:$('#tipoDH').val(), 
    			tipo:$('#tipoCumple').val(), 
    			idDetalleSupervision:$('#idDetalleSupervisionDH').val(), 
    			idObligacion:$('#idObligacionDH').val(),
    			idTipificacion:$('#idTipificacionDH').val(), 
    			idCriterio:$('#idCriterioDH').val()		
    		},
    		hidegrid: false,
    		rowNum: constant.rowNumPrinc,
    		pager: "#paginacionTipificaciones",
    		emptyrecords: "No se encontraron resultados",
    		loadtext: "Cargando",
    		colNames: nombres,
    		colModel: columnas,
    		height: "auto",
    		width: "auto",
    		viewrecords: true,
    		caption: "",
    		jsonReader: {root: "filas",page: "pagina",total: "total",records: "registros",repeatitems: false,id: "idTipificacion"},
    		onSelectRow: function(rowid, status) {
    			grid.resetSelection();
    		},
    		onRightClickRow: function(rowid) {
            },
    		loadComplete: function(data) {     			
    			var idTipificacion = null;
				var descripcionResultado = "";
				var idDetalleEvaluacion = null; 
				var idDetalleSupervision = null; 
				var flagResultado = "";	
				var flagRegistrado = "0";
    			if(data.total!=0){   
    				idTipificacion = data.filas[0].tipificacion.idTipificacion;
    				descripcionResultado = data.filas[0].descripcionResultado;
    				idDetalleEvaluacion = data.filas[0].idDetalleEvaluacion; 
    				flagResultado = data.filas[0].flagResultado;
    				flagRegistrado = data.filas[0].flagRegistrado;
    				idDetalleSupervision = $('#idDetalleSupervisionDH').val();  
    			} 
    			if(NivelHallazgo){  // Nivel de Hallazgo		
					NivelHallazgo = true;
					var ArrayDE=[{idCriterio:null,idTipificacion:null,idDetalleCriterio:null,idDetalleSupervision:idDetalleSupervision, descripcionResultado:descripcionResultado, idDetalleEvaluacion:null,tieneCriterio:-1,flagRegistrado:flagRegistrado}];
					verificarArrayDEvaluacion(ArrayDE);	        				
    				$('#txtDescripcionHallazgo').val(descripcionResultado);
					$('#formDescHallazgo').css('display','inline');$('#divTipificacionGrilla').css('display','none');$('#divDescripcionHallazgo').css('display','inline');
    			} else {     	
	    			if(data.total==0){   // Nivel de Hallazgo
	    				NivelHallazgo = true; 
	    				$('#formDescHallazgo').css('display','inline'); $('#divTipificacionGrilla').css('display','none'); $('#divDescripcionHallazgo').css('display','inline');
	    			}
	    			else{	    
	    				if(data.filas.length == 1){ 
	    					if(flagResultado==constant.tipoCumple.cumple || (flagResultado==constant.tipoCumple.incumple && idTipificacion==null)){  // Nivel de Hallazgo
	    						NivelHallazgo = true;
	    						var ArrayDE=[{idCriterio:null,idTipificacion:null,idDetalleCriterio:null,idDetalleSupervision:idDetalleSupervision, descripcionResultado:descripcionResultado, idDetalleEvaluacion:null,tieneCriterio:-1,flagRegistrado:flagRegistrado}];
	    						verificarArrayDEvaluacion(ArrayDE);	        				
		        				$('#txtDescripcionHallazgo').val(descripcionResultado);
		    					$('#formDescHallazgo').css('display','inline');$('#divTipificacionGrilla').css('display','none');$('#divDescripcionHallazgo').css('display','inline');
	    					}
	    					else {  // Nivel de Tipificacion Multiple
	    						NivelHallazgo = false;
	        					$('#txtDescripcionHallazgo').removeAttr('validate');
	    	    				$('#formDescHallazgo').css('display','inline');$('#divTipificacionGrilla').css('display','inline');$('#divDescripcionHallazgo').css('display','none');
	    					}
	    				} else { // Nivel de Tipificacion Multiple
	    					NivelHallazgo = false;
	    					$('#txtDescripcionHallazgo').removeAttr('validate');
		    				$('#formDescHallazgo').css('display','inline');$('#divTipificacionGrilla').css('display','inline');$('#divDescripcionHallazgo').css('display','none');
	    				}
	    			}
	    			$('#gridTipificaciones').find('td[aria-describedby="gridTipificaciones_seleccion"]').css('white-space','nowrap');
	    			var fila = $('#gridTipificaciones').jqGrid('getRowData');	
	                for (var i = 0; i < fila.length; i++) {
	                	$('#gridTipificaciones').find("td[aria-describedby='gridTipificaciones_codTipificacion']").css("font-weight","bold");
	                	$('#gridTipificaciones').find("td[aria-describedby='gridTipificaciones_descripcion']").css("font-weight","bold");
	                	$('#gridTipificaciones').find("td[aria-describedby='gridTipificaciones_sancionMonetaria']").css("font-weight","bold");
	                	$('#gridTipificaciones').find("td[aria-describedby='gridTipificaciones_otrasSanciones']").css("font-weight","bold");
	                	$('#gridTipificaciones').find("td[aria-describedby='gridTipificaciones_comentario']").css("font-weight","bold");
	                 }
    			}
            },
            subGrid: true,
            afterInsertRow: function(rowid, aData, rowelem) {
            	var rowData = grid.getRowData(rowid);
                if (rowData["tipificacion.tieneCriterio"] == 0) {
                    $('tr#' + rowid, grid).children("td.sgcollapsed").html("").removeClass('ui-sgcollapsed sgcollapsed');
                }
            },
            subGridOptions: {
                "plusicon": "ui-icon-circle-plus",
                "minusicon": "ui-icon-circle-minus",
                "openicon": "ui-icon-arrowreturn-1-e",
                "reloadOnExpand": false,
                "selectOnExpand": false
            },                          
            subGridRowExpanded: function(subgrid_id, row_id) { 
                var subgrid_table_id, pager_id; 
            	var rowData = grid.getRowData(row_id);
            	var idCriterio = rowData["criterio.idCriterio"];            	
            	if(idCriterio==null) { idCriterio = -1; }
                subgrid_table_id = subgrid_id+"_t";                
                pager_id = "p_"+subgrid_table_id; 
                $("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
                jQuery("#"+subgrid_table_id).jqGrid({ 
                    url: baseURL + "pages/supervision/findCriterio",
                    datatype: "json", 
                    postData: {   
                    	tipoDH: $('#tipoDH').val(),
                    	idDetalleSupervision: rowData["detalleSupervision.idDetalleSupervision"],
                    	idTipificacion: rowData["tipificacion.idTipificacion"],
                    	idCriterio: idCriterio,
                    	idObligacion:$('#idObligacionDH').val()
                    },
                    colNames: nombresSubGrid,
                    colModel: columnasSubGrid,
                    rowNum:constant.rowNum, 
                    pager: pager_id, 
                    sortorder: "asc", 
                    height: '100%',
                    autowidth: true,
                    jsonReader: { root: "filas", page: "pagina", total: "total", records: "registros", repeatitems: false, id: "idCriterio"},
                    loadComplete: function(data) {
                    	jQuery("#" + subgrid_table_id).find('td[aria-describedby="'+subgrid_table_id+'_seleccionCriterio"]').css('white-space','nowrap');
                    }
                }); 
            },
    		loadError: function(jqXHR) {
    			errorAjax(jqXHR);
    		}
    	});
	}
	
	$("#divComentarioHallazgo").dialog({ 
        resizable: false,
        draggable: true,
        autoOpen: false,
        height:"auto",
        width: "auto",
        modal: true,
        dialogClass: 'dialog',
        closeText: "Cerrar" 
    });	 //mdiosesf - RSIS6
	
	function grillaArchivosMediosProbatorio(){
		var nombres = ['idDocumentoAdjunto','DESCRIPCI&Oacute;N','ARCHIVO','DESCARGAR'];
        var columnas = [
            {name: "idDocumentoAdjunto",hidden: true},
            {name: "descripcionDocumento", hidden: false, width: 200, sortable: false, align: "center"},
            {name: "nombreArchivo", hidden: false, width: 200, sortable: false, align: "center"},
            {name: "idDocumentoAdjunto",width: 60,sortable: false,align: "center",formatter: descargarMedioProbatorio}
            ];
        $("#gridContenedorArchivosMP").html("");
        var grid = $("<table>", {
            "id": "gridArchivosMedioProbatorio"
        });
        var pager = $("<div>", {
            "id": "paginacionArchivosMedioProbatorio"
        });
        $("#gridContenedorArchivosMP").append(grid).append(pager);
        if(tipoCumple==$('#flagResultado').val()){
        	grid.jqGrid({
        		url: baseURL + "pages/archivo/findPghDocumentoAdjunto",
        		datatype: "json",
        		postData: {
        			idDetalleSupervision:$('#idDetalleSupervisionDH').val()
        		},
        		hidegrid: false,
        		rowNum: constant.rowNum,
        		pager: "#paginacionArchivosMedioProbatorio",
        		emptyrecords: "No se encontraron resultados",
        		loadtext: "Cargando",
        		colNames: nombres,
        		colModel: columnas,
        		height: "auto",
        		width: "auto",
        		viewrecords: true,
        		caption: "",
        		jsonReader: {
        			root: "filas",
        			page: "pagina",
        			total: "total",
        			records: "registros",
        			repeatitems: false,
        			id: "idDocumentoAdjunto"
        		},
        		onSelectRow: function(rowid, status) {
        			grid.resetSelection();
        		},
        		onRightClickRow: function(rowid) {
                    $('#linkEliminarArchivosMP').attr('onClick', 'coSupeHalla.eliminarMedioProbatorioConf('+rowid+')');
                    },
        		loadComplete: function(data) {
                	if(tipoDH==constant.modoSupervision.registro){
    	                $('#contextMenuArchivosMP').parent().remove();
    	                $('#divContextArchivosMP').html("<ul id='contextMenuArchivosMP'>"
    	                        + "<li> <a id='linkEliminarArchivosMP' data-icon='ui-icon-trash' title='Eliminar'>Eliminar</a></li>"
    	                        + "</ul>");
    	                $('#contextMenuArchivosMP').puicontextmenu({
    	                    target: $('#gridArchivosMedioProbatorio')
    	                });
    	                $('#contextMenuArchivosMP').parent().css('width','65px');
    	                cambioColorTrGrid($('#gridArchivosMedioProbatorio'));
                	}
                },
        		loadError: function(jqXHR) {
        			errorAjax(jqXHR);
        		}
        	});
        }else{
        	grid.jqGrid({
                datatype: "local",
                hidegrid: false,
                rowNum: constant.rowNum,
                pager: "#paginacionArchivosMedioProbatorio",
                emptyrecords: "No se encontraron resultados",
                loadtext: "Cargando",
                colNames: nombres,
        		colModel: columnas,
                height: 'auto',
                width: 'auto',
                viewrecords: true,
                jsonReader: { root: "filas", page: "pagina", total: "total",  records: "registros",repeatitems: false, id:"idDocumentoAdjunto"},
                onSelectRow: function(rowid, status) {
                },
                onRightClickRow: function(rowid, iRow, iCol, e) {
                },
                loadComplete: function(data) {
                },
                loadError: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
        }
	}
	
	function enviarDatosArchivoBL(data) {
        if (data.error) {mensajeGrowl("warn", "Advertencia:", data.mensaje);}
        else {
        	mensajeGrowl("success", constant.confirm.save);
        	$('#flagResultado').val( $('#tipoCumple').val());
        	grillaArchivosMediosProbatorio();
        }
    }
	
	function cargarSanciones(idSlctOrig){
		var sancionDescripcion='';
		if($(idSlctOrig).val()!= ''){
			$.ajax({
				url:baseURL + "pages/supervision/cargarTipoSancion",
				type:'get',
				dataType:'json',
				async:false,
				data:{idTipificacion:$(idSlctOrig).val()},
				beforeSend:loading.open,
				success:function(data){
					loading.close();
					if(data!=undefined){
						if(data.sancionMonetaria!=null && data.sancionMonetaria.trim()!=''){
							sancionDescripcion+= "Hasta "+data.sancionMonetaria+" UIT ";
						}
						if(data.otrasSanciones!=null && data.otrasSanciones.trim()!=''){
							sancionDescripcion+= "/ Otras sanciones: "+data.otrasSanciones;
						}
						if(sancionDescripcion.trim()!=''){
							$('#txtSancion').val(sancionDescripcion);
							$('#divSancion').css('display', 'inline');
						}else{$('#divSancion').css('display', 'none');}
					}else{$('#divSancion').css('display', 'none');}
				},
				error:function(jqXHR){errorAjax(jqXHR);}
			});
		}else{$('#divSancion').css('display', 'none');}
	}
	
	function guardarDescHallazgo(){
		var mensajeValidacion = '';		
		if(validarFormulario()){			
			mensajeValidacion = validarDescripcionHallazgo();
			if(mensajeValidacion==''){
				confirmer.open('¿Confirma que desea registrar los datos del hallazgo?', 'coSupeHalla.registrarDescHallazgo()');
			}
			else{mensajeGrowl('warn', mensajeValidacion);}
		}
	}
	
	function eliminarMedioProbatorioConf(idDocumentoAdjunto){
		confirmer.open('¿Confirma que desea eliminar el medio probatorio?', 'coSupeHalla.suprimirMedioProbatorio("'+idDocumentoAdjunto+'")');
	}
	
	//CRUD
	function registrarDescHallazgo(){ //mdiosesf-RSIS6
		loading.open(); 		
        var i = 0;
        //generamos los parametros para post
        var parameters = "_=p";
        var idDetalleSupervision = $('#idDetalleSupervisionDH').val();
        parameters += "&tipoCumple="+$('#tipoCumple').val(); 
        if(idDetalleSupervision!=null) parameters += "&idDetalleSupervision="+idDetalleSupervision; else parameters += "&idDetalleSupervision="-1;
        parameters += "&idObligacion="+$('#idObligacionDH').val(); 
        for(var x = 0; x<ArrayDEvaluacion.length; x++){	
	        if(ArrayDEvaluacion[x].descripcionResultado!=null && ArrayDEvaluacion[x].descripcionResultado.trim()!=''){
	        	if(ArrayDEvaluacion[x].idCriterio!=null) parameters += "&listaDEvaluacion["+x+"].criterio.idCriterio="+ArrayDEvaluacion[x].idCriterio;                          
	        	if(ArrayDEvaluacion[x].idTipificacion!=null) parameters += "&listaDEvaluacion["+x+"].tipificacion.idTipificacion="+ArrayDEvaluacion[x].idTipificacion;
	        	if(ArrayDEvaluacion[x].idDetalleCriterio!=null) parameters += "&listaDEvaluacion["+x+"].detalleCriterio.idDetalleCriterio="+ArrayDEvaluacion[x].idDetalleCriterio;
	        	if(ArrayDEvaluacion[x].idDetalleSupervision!=null) parameters += "&listaDEvaluacion["+x+"].detalleSupervision.idDetalleSupervision="+ArrayDEvaluacion[x].idDetalleSupervision;
	        	if(ArrayDEvaluacion[x].descripcionResultado!=null) parameters += "&listaDEvaluacion["+x+"].descripcionResultado="+ArrayDEvaluacion[x].descripcionResultado;
	        	if(ArrayDEvaluacion[x].idDetalleEvaluacion!=null) parameters += "&listaDEvaluacion["+x+"].idDetalleEvaluacion="+ArrayDEvaluacion[x].idDetalleEvaluacion;
	        	if(ArrayDEvaluacion[x].tieneCriterio!=null) parameters += "&listaDEvaluacion["+x+"].tipificacion.tieneCriterio="+ArrayDEvaluacion[x].tieneCriterio;
	        	if(ArrayDEvaluacion[x].flagRegistrado!=null) parameters += "&listaDEvaluacion["+x+"].flagRegistrado="+ArrayDEvaluacion[x].flagRegistrado;
        	} 
    	}       
		$.ajax({
            url: baseURL + "pages/supervision/guardarDetalleEvaluacion",
            type: 'post',
            async: false,
            data: parameters,
            success: function(data) {
            	loading.close();           	
            	if(data.resultado=='0'){
            		mensajeGrowl("success", constant.confirm.save);
            		$('#divMediosProbatorio').css("display","inline");
	            } else {
	            	mensajeGrowl("error", data.mensaje);
	            }
            },
            error: errorAjax
        });
	}
	
	function registrarDocumentoAdjunto(){
		$("#formMedioAprobatorio").submit();
	}
	
	function suprimirMedioProbatorio(idDocumentoAdjunto){
		loading.open();
		var url = baseURL + "pages/archivo/eliminarPghDocAdjDetalleSuper";
		$.post(url, {
			idDocumentoAdjunto : idDocumentoAdjunto,
			'detalleSupervision.idDetalleSupervision':$('#idDetalleSupervisionDH').val()
		}, function(data) {
			loading.close();
			if (data.resultado == '0') {grillaArchivosMediosProbatorio();mensajeGrowl("success", constant.confirm.remove);}
			else if (data.resultado == '1') {mensajeGrowl('error', data.mensaje);}
		});
	}
	
	//Validaciones
	function validarFormulario(){
		var validarDescripcionHallazgo = true;
		var validarRadio=true;
		validarRadio=validarRadios();
		validarDescripcionHallazgo = $('#formDescHallazgo').validateAllForm('#divMensajeValidaFrmDH');
		if (validarRadio==validarDescripcionHallazgo && validarDescripcionHallazgo==false) {
			validarDescripcionHallazgo=false;
		}
		if (validarRadio!=validarDescripcionHallazgo) {
			validarDescripcionHallazgo=false;
		}
		
		return validarDescripcionHallazgo;
	}
	
	function validarDescripcionHallazgo(){		
		var mensajeValidacion = "";
		var ids = jQuery("#gridTipificaciones").jqGrid('getDataIDs');
		var nroSubGrid = 1;
		var nroGrid = 0; 
		if(!NivelHallazgo){
			for(var i = 0; i<ids.length; i++){
			    var rowId = ids[i];
			    var rowData = jQuery('#gridTipificaciones').jqGrid ('getRowData', rowId);
			    var tieneCriterio = rowData['tipificacion.tieneCriterio'];
			    var idTipificacion = rowData['tipificacion.idTipificacion'];
			    if(tieneCriterio == 0){			    	
			    	var descripcionResultado = $('#comentario_'+idTipificacion).val();
			    	nroGrid++;
			    	if(descripcionResultado.trim()!='' && descripcionResultado.trim().length<8){
			    		$('#comentario_'+idTipificacion).addClass('error');
			    		mensajeValidacion += "El comentario de hallazgo debe tener una longitud mayor o igual a 8 caracteres, corregir. <br>";
			    		break;
			    	}
			    } else {
			    	if(nroGrid>0){nroSubGrid++;}
			    	var idsSubGrid = jQuery('#gridTipificaciones_'+nroSubGrid+'_t').jqGrid('getDataIDs');	
			    	for(var x = 0; x<idsSubGrid.length; x++){
					    var rowIdSubGrid = idsSubGrid[x];
					    var rowDataSubGrid = jQuery('#gridTipificaciones_'+nroSubGrid+'_t').jqGrid ('getRowData', rowIdSubGrid);	
					    var idCriterio = rowDataSubGrid['idCriterio'];
					    rowDataSubGrid['idTipificacion'] = idTipificacion;
					    var descripcionResultado = $('#comentarioCriterio_'+idCriterio).val();
				    	if(descripcionResultado.trim()!='' && descripcionResultado.trim().length<8){
				    		$('#comentarioCriterio_'+idCriterio).addClass('error');
				    		mensajeValidacion += "El comentario de hallazgo debe tener una longitud mayor o igual a 8 caracteres, corregir. <br>";
				    		break;
				    	}
			    	}
			    	if(mensajeValidacion!=''){ break; }
			    }	    	
			}
		}
		else { 
			var descripcionResultado = $('#txtDescripcionHallazgo').val();
			if(descripcionResultado.trim()!='' && descripcionResultado.trim().length<8){
				$('#txtDescripcionHallazgo').addClass('error');
	    		mensajeValidacion += "El comentario de hallazgo debe tener una longitud mayor o igual a 8 caracteres, corregir. <br>";
			}			
		}		
		setArrayDEvaluacion();		
//		for(var x = 0; x<ArrayDEvaluacion.length; x++){			 
//                    console.info("idCriterio: " + ArrayDEvaluacion[x].idCriterio + " | idTipificacion: " + ArrayDEvaluacion[x].idTipificacion + " | idDetalleCriterio: " + ArrayDEvaluacion[x].idDetalleCriterio + " | idDetalleSupervision: " + ArrayDEvaluacion[x].idDetalleSupervision + " | descripcionResultado: " + ArrayDEvaluacion[x].descripcionResultado + " - idDetalleEvaluacion: " + ArrayDEvaluacion[x].idDetalleEvaluacion + " | tieneCriterio: " + ArrayDEvaluacion[x].tieneCriterio + " | flagRegistrado: " + ArrayDEvaluacion[x].flagRegistrado) 
//		}
		return mensajeValidacion;
	}
	
	function setArrayDEvaluacion(){ //mdiosesf - RSIS6
		var ids = jQuery("#gridTipificaciones").jqGrid('getDataIDs');
		var nroSubGrid = 1;	
		var nroGrid = 0; 
		var numeroGrid = 1;
		if(!NivelHallazgo){
			for(var i = 0; i<ids.length; i++){
			    var rowId = ids[i];
			    var rowData = jQuery('#gridTipificaciones').jqGrid ('getRowData', rowId);
			    var tieneCriterio = rowData['tipificacion.tieneCriterio'];
			    var idTipificacion = rowData['tipificacion.idTipificacion'];		   
		    	var idDetalleSupervision = rowData['detalleSupervision.idDetalleSupervision']; 
		    	var idDetalleEvaluacion = rowData['idDetalleEvaluacion'];
		    	if(idDetalleEvaluacion==''){idDetalleEvaluacion=null;}
		    	if(tieneCriterio == 0){nroGrid++;}
		    	else if(tieneCriterio == 1){
		    		if(nroGrid>0){nroSubGrid++;}
			    	var idsSubGrid = jQuery('#gridTipificaciones_'+numeroGrid+'_t').jqGrid('getDataIDs');
			    	for(var x = 0; x<idsSubGrid.length; x++){
					    var rowIdSubGrid = idsSubGrid[x];
					    var rowDataSubGrid = jQuery('#gridTipificaciones_'+numeroGrid+'_t').jqGrid ('getRowData', rowIdSubGrid);				    
					    var idCriterio = rowDataSubGrid['idCriterio'];	
					    rowDataSubGrid['idTipificacion'] = idTipificacion;
					    for(var e = 0; e<ArrayDEvaluacion.length; e++){		
				    		if(ArrayDEvaluacion[e].idCriterio==idCriterio){
				    			ArrayDEvaluacion[e].idTipificacion = idTipificacion;
				    			ArrayDEvaluacion[e].idDetalleSupervision = idDetalleSupervision;
				    			ArrayDEvaluacion[e].idDetalleEvaluacion = idDetalleEvaluacion;
				    			ArrayDEvaluacion[e].tieneCriterio = -1;
				    		}
				    	}
			    	}
			    }	
		    	numeroGrid++;
			}
		}
		else {
			var descripcionResultado = $('#txtDescripcionHallazgo').val();
			var idDetalleSupervision = $('#idDetalleSupervisionDH').val();
			var ArrayDE=[{idCriterio:null,idTipificacion:null,idDetalleCriterio:null,idDetalleSupervision:idDetalleSupervision, descripcionResultado:descripcionResultado, idDetalleEvaluacion:null,tieneCriterio:-1,flagRegistrado:null}];
			verificarArrayDEvaluacion(ArrayDE);
		}
	}
	
	function verificarArrayDEvaluacion(grid){ //mdiosesf - RSIS6
		var idCriterio=grid[0].idCriterio;
		var idTipificacion=grid[0].idTipificacion;
		var idDetalleCriterio=grid[0].idDetalleCriterio;
		var idDetalleSupervision=grid[0].idDetalleSupervision;
		var descripcionResultado=grid[0].descripcionResultado;
		var idDetalleEvaluacion=grid[0].idDetalleEvaluacion;
		var tieneCriterio=grid[0].tieneCriterio;
		var flagRegistrado=grid[0].flagRegistrado;		
		var existe=false;	
		for(var i = 0; i<ArrayDEvaluacion.length; i++){
			if(grid[0].idTipificacion==null && ArrayDEvaluacion[i].idCriterio==null){
				if(ArrayDEvaluacion[i].idDetalleSupervision==grid[0].idDetalleSupervision){
					ArrayDEvaluacion[i].descripcionResultado=descripcionResultado;	
					existe=true;
					break;
				}
			}
			else if(grid[0].idTipificacion==null && ArrayDEvaluacion[i].idCriterio!=null){
				if(ArrayDEvaluacion[i].idCriterio==grid[0].idCriterio){
					ArrayDEvaluacion[i].descripcionResultado=descripcionResultado;
					existe=true;
					break;
				}
			} else {
				if(ArrayDEvaluacion[i].idTipificacion==grid[0].idTipificacion){
					ArrayDEvaluacion[i].descripcionResultado=descripcionResultado;	
					existe=true;
					break;
				}
			}						
		}
		if(!existe){ ArrayDEvaluacion.push({idCriterio:idCriterio,idTipificacion:idTipificacion,idDetalleCriterio:idDetalleCriterio,idDetalleSupervision:idDetalleSupervision, descripcionResultado:descripcionResultado, idDetalleEvaluacion:idDetalleEvaluacion,tieneCriterio:tieneCriterio,flagRegistrado:flagRegistrado}); }
	}
	
	function validarAgregarMedioProbatorio(){
		$('#txtDescripcionMP,#file_nameSuper').removeClass('error');
		var mensajeValidacion = '';
		if(validarFormMedioProbatorio()){
			if(tipoCumple==constant.tipoCumple.incumple){
				mensajeValidacion = validarRegistrado();
			}
			if(mensajeValidacion==''){
				mensajeValidacion = validarArchivoPermitido($('#file_nameSuper').val());
				if(mensajeValidacion==''){
					if($("#gridArchivosMedioProbatorio").jqGrid('getDataIDs').length>0){
						mensajeValidacion = validarMedioProbatorio($('#txtDescripcionMP').val(),$('#file_nameSuper').val());
					}
					if(mensajeValidacion==''){confirmer.open('¿Confirma que desea agregar el archivo?', 'coSupeHalla.registrarDocumentoAdjunto()');}
				}
			}
			if(mensajeValidacion!=''){mensajeGrowl('warn', mensajeValidacion);}
		}
	}
	
	function validarFormMedioProbatorio(){
		var validarDocumentoAdjunto = true;
		validarDocumentoAdjunto = $('#formMedioAprobatorio').validateAllForm('#divMensajeValidaFrmDH');
		return validarDocumentoAdjunto;
	}
	
	function validarRegistrado(){
		var mensajeValidacion='';		
		$.ajax({
            url: baseURL + "pages/supervision/buscarDetalleSupervision",
            type: 'post',
            async: false,
            data: {idDetalleSupervision:$('#idDetalleSupervisionDH').val(),flagResultado:$('#tipoCumple').val()},
            success: function(data) {
            	if(data.resultado=='0'){
	        		if(data.listaDetalleSupervision.length==undefined || data.listaDetalleSupervision.length==0){
	        			return mensajeValidacion = "Debes de registrar Hallazgo <br>";
	        		}
	            }else{
	            	mensajeValidacion = data.mensaje;
	            	return mensajeValidacion;
	            }
            },
            error: errorAjax
        });
		return mensajeValidacion;
	}
	
	function validarMedioProbatorio(descripcion,nombreArchivo){
		var mensajeValidacion = "";
		loading.open();
		var url = baseURL + "pages/archivo/listaPghDocumentoAdjunto";
		$.ajax({
            url: url,
            type: 'post',
            async: false,
            data: {
            	idDetalleSupervision : $('#idDetalleSupervisionDH').val(),
    			descripcionDocumento : descripcion,
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
    }
	
	function validarTipoArchivo(extension,nombreArchivo){
		var mensajeValidacion = "";
		var extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf("."),nombreArchivo.length);
		if(extension.toUpperCase()!=extensionArchivo.toUpperCase()){mensajeValidacion='El archivo seleccionado no corresponde con el tipo de archivo, por favor corregir <br>';}
		return mensajeValidacion;
	}
	
	function validarArchivoPermitido(nombreArchivo){
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
	}
	
	//Formater
	function descargarMedioProbatorio(id,cellvalue, options, rowdata) {
		var editar = "";
	    editar = "<img src=\"" + baseURL + "/../images/stickers.png\" width='17' height='18' onclick=\"coSupeHalla.descargaDocumentosAdjunto('" + id + "')\" style=\"cursor: pointer;\" alt=\"Descargar Medio Probatorio\"/>";
        return editar;
	}
	
	function eliminarMedioProbatorio(cellvalue, options, rowdata) {
		var editar = "";
        editar = "<img src=\"" + baseURL + "/../images/delete_16.png\" style=\"cursor: pointer;\" alt=\"Eliminar Medio Probatorio\" onclick=\"coSupeHalla.eliminarMedioProbatorioConf('"+rowdata.idDocumentoAdjunto+"');\" />";
        return editar;
	}
	
	function descargaDocumentosAdjunto(idDocumentoResultante){
        document.location.href = baseURL + 'pages/archivo/descargaPghArchivoBD?idDocumentoAdjunto=' + idDocumentoResultante;
	}
	
	function activarModoConsulta(){
		$('#divAdjuntoDH').css('display', 'none');
	}
	
	//carga de radio combos
	function cargaTipificacionRadio(callback,tagDestino,idTipificacion){
        var postData = {idObligacion:$("#idObligacionDH").val(),
        				idTipificacion:idTipificacion
        		};
	    if(postData.idObligacion!=""){
	        $.ajax({
	            url: baseURL + 'pages/supervision/findTipifica',
	            type: "get",
	            async: false,
	            datatype: "json",
	        	data:postData,
	            success: function(data) {
	            	if(data.length>0){
		                callback(data,'idTipificacion','descripcion','codTipificacion',tagDestino,"radioTipificacion");
						$("#divTipificacion").css("display","inline");
				        $("#tipiRadioSizeHD").val(data.length);
				        $("#divSancionEspe").css("display","none");
					} else {
						$("#divTipificacion").css("display","none");
						$("#divSancion").css("display","none");
						$("#divCriterio").css("display","none");
						$("#divSancionEspe").css("display","none");
				        $("#tipiRadioSizeHD").val(data.length);
					}
	            },
	            error:errorAjax
	        });
	    }else{
	        callback([],'idTipificacion','descripcion','codTipificacion',tagDestino,'radioTipificacion');
	        $("#divCriterio").css("display","none");
			$("#divSancionEspe").css("display","none");
	    }
	}
	
	function cargarCriterioRad(callback,CmbOrigen,tagDestino,idCriterio){
		$('#cmbCriterio').html('');
        var postData = {idTipificacion:$(CmbOrigen).val(),
        				idCriterio:idCriterio,
        				idObligacion:$("#idObligacionDH").val()
        		};
	    if(postData.idObligacion!=""){
	        $.ajax({
	            url: baseURL + 'pages/supervision/cargarCriterio',
	            type: "get",
	            async: false,
	            datatype: "json",
	        	data:postData,
	            success: function(data) {
	            	if(data.length>0){
		                callback(data,'idCriterio','descripcion','ninguno',tagDestino,'radioCriterio');
		                $("#divCriterio").css("display","inline");
						$("#divSancionEspe").css("display","none");
				        $("#criterioRadioSizeHD").val(data.length);
					} else {
						$("#divCriterio").css("display","none");
						$("#divSancionEspe").css("display","none");
				        $("#criterioRadioSizeHD").val(data.length);
					}
	            },
	            error:errorAjax
	        });
	    }else{
			callback([],'idTipificacion','descripcion','idCriterio',tagDestino,'radioTipificacion');
			$("#divCriterio").css("display","none");
			$("#divSancionEspe").css("display","none");
	    }
	}
	
	function generadorRadio(data,id,desc,codigo,tagDestino,nombre){
	    $(tagDestino).hide();
	    var html="";
	    if(data.length>0){
	        var cont=0;
	        $.each(data,function(k,v){
	        	if (codigo=="ninguno") {
		            html+='<div class="lblh contChkA bgA" style="position:relative; width: 99%;white-space: normal;" title="'+v[desc]+'">';
		            html+='<div class="ilb vam txtFilaUnic" style="width:88%;white-space: normal;height:auto; ">'+v[desc]+'</div>';
				}else{
		            html+='<div class="lblh contChkA bgA" style="position:relative; width: 99%;white-space: normal;" title="'+v[codigo]+" - "+v[desc]+'">';
		            html+='<div class="ilb vam txtFilaUnic" style="width:88%;white-space: normal;height:auto; ">'+v[codigo]+" - "+v[desc]+'</div>';
				}
	            html+='<div class="fr">';
	            if (data.length==1) {
		            html+='<input type="radio" checked="checked" name="'+nombre+'" value="'+v[id]+'"  id="chkT_'+nombre+v[id]+'" >';
				}else {
		            html+='<input type="radio" name="'+nombre+'" value="'+v[id]+'"  id="chkT_'+nombre+v[id]+'" >';
				}
	            html+='<label for="chkT_'+nombre+v[id]+'" class="radio"></label>';
	            html+='</div>';
	            html+='</div>';
	            cont++;

	        });
	        $(tagDestino).show();
	    }
	    $(tagDestino).children('div').eq(1).html(html);
	}
	
	function validarRadios(){
		var errorRadio=true;
		if ($("#tipiRadioSizeHD").val()>0 && $('input:radio[name=radioTipificacion]:checked').val()==undefined) {
            $('#cmbTipificacion').addClass('error');
            errorRadio=false;
		}else{$('#cmbTipificacion').removeClass('error');}
        
		if ($("#criterioRadioSizeHD").val()>0 && $('input:radio[name=radioCriterio]:checked').val()==undefined) {
            $('#cmbCriterio').addClass('error');
            errorRadio=false;
		}else{$('#cmbCriterio').removeClass('error');}
		return errorRadio;
	}

	function cargarSancionEspecifica(idCriterio){
		$('#txtSancionEspecifica').val('');
		var postData = {
				idTipificacion:-1,
				idCriterio:idCriterio,
				idObligacion:-1
		};
		$.ajax({
            url: baseURL + 'pages/supervision/cargarCriterio',
            type: "get",
            async: false,
            datatype: "json",
        	data:postData,
            success: function(data) {
            	if(data.length>0){
            		if(data[0].sancionMonetaria!=null && data[0].sancionMonetaria.trim()!=''){
            			$('#txtSancionEspecifica').val(data[0].sancionMonetaria+" UIT");
            			$("#divSancionEspe").css("display","inline");
            		}else{$("#divSancionEspe").css("display","none");}
            	}else {$("#divSancionEspe").css("display","none");}
            },
            error:errorAjax
        });
	}
	
	function habilitaComentarioCriti(obj,idCriterio){ //mdiosesf - RSIS6
    	if(obj.checked){$('#comentarioCriterio_'+idCriterio).removeAttr('disabled');}
    	else{$('#comentarioCriterio_'+idCriterio).attr('disabled','disabled');}
    }
	
	function habilitaComentarioTipi(obj,idTipificacion){ //mdiosesf - RSIS6
    	if(obj.checked){$('#comentario_'+idTipificacion).removeAttr('disabled');}
    	else{$('#comentario_'+idTipificacion).attr('disabled','disabled');}
    }
	
	function ComentarioHallazgoCriterio(obj,idTipificacionCriterio,tipo){ //mdiosesf - RSIS6
		var descripcionResultado = "";
		if(tipo==0) { descripcionResultado = $("#comentarioHidden_"+idTipificacionCriterio).val(); }
		else { descripcionResultado = $("#comentarioCriterioHidden_"+idTipificacionCriterio).val(); }
		$('#txtDescripcion').val(descripcionResultado);
		$('#idTipificacionCriterio').val(idTipificacionCriterio);		
		$("#divComentarioHallazgo" ).dialog('option', 'title', 'INGRESO DE TEXTO');
		$('#divComentarioHallazgo').dialog('open');
    }
	
	return{
		constructor:constructor,
		descargaDocumentosAdjunto:descargaDocumentosAdjunto,
		registrarDescHallazgo:registrarDescHallazgo,
		eliminarMedioProbatorioConf:eliminarMedioProbatorioConf,
		registrarDocumentoAdjunto:registrarDocumentoAdjunto,
		suprimirMedioProbatorio:suprimirMedioProbatorio,
		cargarSancionesEspecificas:cargarSancionesEspecificas,
		cargarCriterios:cargarCriterios,
		cargarTipificaciones:cargarTipificaciones,
		habilitaComentarioCriti:habilitaComentarioCriti, //mdiosesf - RSIS6
		habilitaComentarioTipi:habilitaComentarioTipi, //mdiosesf - RSIS6
		ComentarioHallazgoCriterio:ComentarioHallazgoCriterio, //mdiosesf - RSIS6
		verificarArrayDEvaluacion:verificarArrayDEvaluacion //mdiosesf - RSIS6
	};	
})();

jQuery.extend($.fn.fmatter, { //mdiosesf - RSIS6
    comentarioTipificacion: function(cellvalue, options, rowdata) {    	
    	var html ="";  	
    	var idCriterio=rowdata.criterio.idCriterio;    	    	
    	var idDetalleCriterio=rowdata.detalleCriterio.idDetalleCriterio; 
    	var idDetalleSupervision=rowdata.detalleSupervision.idDetalleSupervision;
    	var idDetalleEvaluacion=rowdata.idDetalleEvaluacion;
    	var idTipificacion=rowdata.tipificacion.idTipificacion;   	
    	var descripcionResultado=rowdata.descripcionResultado;
    	var tieneCriterio=rowdata.tipificacion.tieneCriterio; 
    	var flagRegistrado=rowdata.flagRegistrado;
    	$('#idDetalleSupervisionDH').val(idDetalleSupervision);
    	var tipoDH = $('#tipoDH').val();   
    	if(!NivelHallazgo) {
    		var ArrayDE=[{idCriterio:idCriterio,idTipificacion:idTipificacion,idDetalleCriterio:idDetalleCriterio,idDetalleSupervision:idDetalleSupervision, descripcionResultado:descripcionResultado, idDetalleEvaluacion:idDetalleEvaluacion,tieneCriterio:tieneCriterio,flagRegistrado:flagRegistrado}];    	
    		coSupeHalla.verificarArrayDEvaluacion(ArrayDE);
    	}
    	if (tipoDH==constant.modoSupervision.consulta){if(tieneCriterio==0){html="<p style='text-align:left; font-size:11px;'>"+descripcionResultado+"</p>";}}
    	else{    		
    		if(tieneCriterio==0){  
    			html = "<input id='comentario_"+idTipificacion+"' type='text' style='text-transform: none !important;' class='ipt-grid-inps ipt-medium-small' disabled readonly";
    			html+=" name='comentario_"+idTipificacion+"' value='"+descripcionResultado+"' onclick='coSupeHalla.ComentarioHallazgoCriterio(this,\""+idTipificacion+"\",0)' />";   		
    			html+="<input type='hidden' id='id_"+idTipificacion+"' value='"+idTipificacion+"'/>";
    			html+="<input type='hidden' id='tieneCriterio_"+idTipificacion+"' value='"+tieneCriterio+"'/>";
    			html+="<textarea style='display:none;' rows='20' maxlength='4000' style='text-transform: none !important;' id='comentarioHidden_"+idTipificacion+"'>"+ descripcionResultado + "</textarea>";
    		}
    	}    	
        return html;
    },
    seleccionTipificacion: function(cellvalue, options, rowdata){
    	var html ="";
    	var idTipificacion=rowdata.tipificacion.idTipificacion;
    	var idCriterio=rowdata.criterio.idCriterio;
    	var descripcionResultado=rowdata.descripcionResultado;
    	var tieneCriterio=rowdata.tipificacion.tieneCriterio;
    	var tipoDH = $('#tipoDH').val(); 
    	if(tieneCriterio==0){
	    	html = "<center><input id='seleccion_"+idTipificacion+"' type='checkbox' onclick='coSupeHalla.habilitaComentarioTipi(this,\""+idTipificacion+"\")' name='seleccion_"+idTipificacion+"'";
	    	if (tipoDH==constant.modoSupervision.consulta){
	    		html+=" disabled ";
	    		if(tieneCriterio==0){html+=" checked ";}
	    	}
	    	else{if(tieneCriterio>0){html+=" disabled ";}}
	    	html+=" /> <label for='seleccion_"+idTipificacion+"' class='checkbox'></label></center>";
    	}
        return html; 
    },    
    formatoSancionTipificacion: function(cellvalue, options, rowdata){
    	var sancionMonetaria=$.trim(rowdata.tipificacion.sancionMonetaria);
    	var html = '';
    	if (sancionMonetaria != null && sancionMonetaria != '' && sancionMonetaria != undefined){html="Hasta "+sancionMonetaria+" UIT";}
    	return html;
    },
    comentarioCriterio: function(cellvalue, options, rowdata) {
    	var html ="";
    	var idCriterio=rowdata.idCriterio;
    	var descripcionResultado=rowdata.descripcionResultado;
    	var flagRegistrado = 0;
    	if(descripcionResultado!=''){flagRegistrado=1;}
    	var tipoDH = $('#tipoDH').val();  
    	if(!NivelHallazgo) {
    		var ArrayDE=[{idCriterio:idCriterio,idTipificacion:null,idDetalleCriterio:null,idDetalleSupervision:null, descripcionResultado:descripcionResultado, idDetalleEvaluacion:null,tieneCriterio:null,flagRegistrado:flagRegistrado}];    	
    		coSupeHalla.verificarArrayDEvaluacion(ArrayDE);
    	}
    	if (tipoDH==constant.modoSupervision.consulta){html="<p style='text-align:left; font-size:11px;'>"+descripcionResultado+"</p>";}
    	else{   		
    		
    		html = "<input id='comentarioCriterio_"+idCriterio+"' type='text' style='text-transform: none !important;' class='ipt-grid-inps ipt-medium-small' disabled readonly name='comentarioCriterio_"+idCriterio+"' value='"+descripcionResultado+"' onclick='coSupeHalla.ComentarioHallazgoCriterio(this,\""+idCriterio+"\",1)' />";
    		html+="<input type='hidden' id='id_"+idCriterio+"' value='"+idCriterio+"'/>";
    		html+="<textarea style='display:none;' rows='20' maxlength='4000' style='text-transform: none !important;' id='comentarioCriterioHidden_"+idCriterio+"'>"+ descripcionResultado + "</textarea>";
    	}    	
        return html;
    },
    seleccionCriterio: function(cellvalue, options, rowdata){
    	var idCriterio=rowdata.idCriterio;
    	var tipoDH = $('#tipoDH').val();
    	var html = "<input id='seleccionCriterio_"+idCriterio+"' type='checkbox' onclick='coSupeHalla.habilitaComentarioCriti(this,\""+idCriterio+"\")' name='seleccionCriterio_"+idCriterio+"'";
    	if (tipoDH==constant.modoSupervision.consulta){html+=" disabled checked ";}
    	html+=" /><label for='seleccionCriterio_"+idCriterio+"' class='checkbox'></label>";
        return html;        
    },
    formatoSancionCriterio: function(cellvalue, options, rowdata){
    	var sancionMonetaria=$.trim(rowdata.sancionMonetaria);
    	var html = '';
    	if (sancionMonetaria != null && sancionMonetaria != '' && sancionMonetaria != undefined){html=sancionMonetaria+" UIT";}
    	return html;
    },
    descargarMedioProbatorio: function(id,cellvalue, options, rowdata) {
		var editar = "";
	    editar = "<img src=\"" + baseURL + "/../images/stickers.png\" width='17' height='18' style=\"cursor: pointer;\" alt=\"Descargar Medio Probatorio\"/>";
        return editar;
    }
}); //mdiosesf - RSIS6

$(function() {
	boton.closeDialog();
	coSupeHalla.constructor();
});