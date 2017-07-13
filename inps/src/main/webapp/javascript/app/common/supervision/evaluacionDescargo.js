//variables globales
var tipoED = "";
var tipoSancion = "";
var NivelHallazgo = false; // true >> Solo hallazgo   mdiosesf -RSIS6
var ArrayDEvaluacion = []; //mdiosesf -RSIS6
var coEvalDesc=(function() {
	function constructor() {
		tipoED = $('#tipoED').val();
		tipoSancion = $('#tipoSancion').val();		
		comportamiento();
		grillaArchivosMediosProbatorio();
		if(tipoED==constant.modoSupervision.consulta){activarModoConsulta();}
		if(tipoSancion==constant.tipoSancion.nosancion){ procesarGridTipificacion(); }//mdiosesf - RSIS6
		else{ NivelHallazgo = true; procesarGridTipificacion();  $('#divDescripcionHallazgoED').css('display','inline');$('#divTipificacionGrillaED').css('display','none');}//mdiosesf - RSIS6
	}
	function comportamiento(){
		var ventanaMedio=true;
		//botones
		$("#btnAceptarDescripcionED").click(function() {			
			var idTipificacionCriterio = $('#idTipificacionCriterioED').val();
			var tieneCriterio = $('#tieneCriterio_' + idTipificacionCriterio).val();
			var descripcion = $('#txtDescripcionED').val();			
			if(tieneCriterio==null){ tieneCriterio = 1;}
			if(descripcion.trim()!="") {
				if(tieneCriterio==0){ 
					$('#comentario_'+idTipificacionCriterio).val(descripcion);
					var ArrayDE=[{idCriterio:null,idTipificacion:idTipificacionCriterio,idDetalleCriterio:null,idDetalleSupervision:null, descripcionResultado:descripcion, idDetalleEvaluacion:null,tieneCriterio:tieneCriterio,flagRegistrado:null}];				
										
				}
				else { 					 
					$('#comentarioCriterio_'+idTipificacionCriterio).val(descripcion);
					var ArrayDE=[{idCriterio:idTipificacionCriterio,idTipificacion:null,idDetalleCriterio:null,idDetalleSupervision:null, descripcionResultado:descripcion, idDetalleEvaluacion:null,tieneCriterio:null,flagRegistrado:null}];					
				}  
				verificarArrayDEvaluacion(ArrayDE);
				$("#divComentarioHallazgoED").dialog("close");
			}
		});
		
		$("#btnGuardarEvalDescargo").click(function() {
			guardarEvalDescargo();
		});
		$('#btnAgregarMedioProbatorioED').click(function() {
			validarAgregarMedioProbatorioED();
        });
		
		$("#fileArchivoSuperED").change(function(){      
	        $("#file_nameSuperED").val(quitaSlashDir($("#fileArchivoSuperED").val())); 
	    });
		//Formularios
		$("#formMedioAprobatorioED").ajaxForm({
			beforeSubmit: function () {loading.open();},
            dataType: 'json',
            resetForm: true,
            success: function(data) {
            	loading.close();
                if (data != null && data.error != null) {enviarDatosArchivoBL(data);}
            },
            error: errorAjax
        });
		if(tipoED==constant.modoSupervision.consulta){ventanaMedio = false;}
		//acordeon
		$('#fldstMediosDescargo').puifieldset({toggleable: true,collapsed:ventanaMedio});
		$('#fldstDescargo').puifieldset({toggleable: true,collapsed:false});
	}
	function grillaArchivosMediosProbatorio(){
		var nombres = ['idDocumentoAdjunto','DESCRIPCI&Oacute;N','ARCHIVO','DESCARGAR'];
        var columnas = [
            {name: "idDocumentoAdjunto",hidden: true},
            {name: "descripcionDocumento", hidden: false, width: 200, sortable: false, align: "center"},
            {name: "nombreArchivo", hidden: false, width: 200, sortable: false, align: "center"},
            {name: "idDocumentoAdjunto",width: 60,sortable: false,align: "center",formatter: descargarMedioProbatorio}
            ];
        $("#gridContenedorArchivosMPED").html("");
        var grid = $("<table>", {
            "id": "gridArchivosMedioProbatorioED"
        });
        var pager = $("<div>", {
            "id": "paginacionArchivosMedioProbatorioED"
        });
        $("#gridContenedorArchivosMPED").append(grid).append(pager);
        if($('#tipoSancion').val()==$('#flagResultadoED').val()){
        	grid.jqGrid({
                url: baseURL + "pages/archivo/findPghDocumentoAdjunto",
                datatype: "json",
                postData: {
                	idDetalleSupervision:$('#idDetalleSupervisionED').val()
                },
                hidegrid: false,
                rowNum: constant.rowNum,
                pager: "#paginacionArchivosMedioProbatorioED",
                emptyrecords: "No se encontraron resultados",
                loadtext: "Cargando",
                colNames: nombres,
                colModel: columnas,
                height: "auto",
                width: "auto",
                viewrecords: true,
                caption: "",
                jsonReader: {root: "filas",page: "pagina",total: "total",records: "registros",repeatitems: false,id: "idDocumentoAdjunto"},
                onSelectRow: function(rowid, status) {
                	grid.resetSelection();
                },
                onRightClickRow: function(rowid) {
                    $('#linkEliminarArchivosMPED').attr('onClick', 'coEvalDesc.eliminarMedioProbatorioConf('+rowid+')');
                    },
                loadComplete: function(data) {
                	if(tipoED==constant.modoSupervision.registro){
    	                $('#contextMenuArchivosMPED').parent().remove();
    	                $('#divContextArchivosMPED').html("<ul id='contextMenuArchivosMPED'>"
    	                        + "<li> <a id='linkEliminarArchivosMPED' data-icon='ui-icon-trash' title='Eliminar'>Eliminar</a></li>"
    	                        + "</ul>");
    	                $('#contextMenuArchivosMPED').puicontextmenu({
    	                    target: $('#gridArchivosMedioProbatorioED')
    	                });
    	                $('#contextMenuArchivosMPED').parent().css('width','65px');
    	                cambioColorTrGrid($('#gridArchivosMedioProbatorioED'));
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
                pager: "#paginacionArchivosMedioProbatorioED",
                emptyrecords: "No se encontraron resultados",
                loadtext: "Cargando",
                colNames: nombres,
        		colModel: columnas,
                height: 'auto',
                width: 'auto',
                viewrecords: true,
                jsonReader: { root: "filas",page: "pagina",total: "total",records: "registros",repeatitems: false,id:"idDocumentoAdjunto"},
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
	
	function procesarGridTipificacion() { //mdiosesf-RSIS6	 	
		var nombres = ['idDetalleEvaluacion','idDetalleSupervision','idDetalleCriterio','idTipificacion','tieneCriterio','flagRegistrado','C&Oacute;D','DESCRIPCI&Oacute;N TIPIFICACI&Oacute;N','SANCI&Oacute;N','OTRAS SANCIONES','COMENTARIO DEL HALLAZGO',''];
        var columnas = [
            {name: "idDetalleEvaluacion", hidden:true},
            {name: "detalleSupervision.idDetalleSupervision", hidden:true},
            {name: "detalleCriterio.idDetalleCriterio", hidden:true},
            {name: "tipificacion.idTipificacion", hidden:true},
            {name: "tipificacion.tieneCriterio", hidden:true},
            {name: "flagRegistrado", hidden:true},
            {name: "tipificacion.codTipificacion", width: 50, sortable: false, align: "center"},
            {name: "tipificacion.descripcion", width: 260, sortable: false, align: "left"},
            {name: "tipificacion.sancionMonetaria", width: 60, sortable: false, align: "left",formatter:formatoSancionTipificacion},
            {name: "tipificacion.otrasSanciones", width: 80, sortable: false, align: "center"},
            {name: "", width: 270, sortable: false, align: "center", formatter: comentarioTipificacion},
            {name: "seleccion", width: 30, sortable: false, align: "center", formatter:seleccionTipificacion}
            ];
               
        var nombresSubGrid 	= ['idCriterio','idTipificacion','DESCRIPCI&Oacute;N CRITERIO ESPEC&Iacute;FICO','SANCI&Oacute;N ESPEC&Iacute;FICA','COMENTARIO DEL HALLAZGO',''];
        var columnasSubGrid = [{name: "idCriterio",hidden:true},
                               {name: "idTipificacion",hidden:true},
                               {name: "descripcion", width: 380, sortable: false, align: "left"},
                               {name: "sancionMonetaria", width: 80, sortable: false, align: "center",formatter:formatoSancionCriterio},
                               {name: "descripcionResultado", width: 270, sortable: false, align: "center", formatter:comentarioCriterio},
                               {name: "", width: 30, sortable: false, align: "center", valign: "top", formatter:seleccionCriterio}
                               ];
        
        $("#gridContenedorTipificacionesED").html("");
        var grid = $("<table>", {
            "id": "gridTipificacionesED"
        });
        var pager = $("<div>", {
            "id": "paginacionTipificaciones"
        });
        $("#gridContenedorTipificacionesED").append(grid).append(pager);
    	grid.jqGrid({
    		url: baseURL + "pages/supervision/findTipificacionMultiple", 
    		datatype: "json",
    		postData: {
    			tipoDH:$('#tipoED').val(),
    			tipo:$('#tipoSancion').val(), 
    			idDetalleSupervision:$('#idDetalleSupervisionED').val(), 
    			idObligacion:$('#idObligacionED').val(),
    			idTipificacion:$('#idTipificacionED').val(), 
    			idCriterio:$('#idCriterioED').val()		
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
    				idDetalleSupervision = $('#idDetalleSupervisionED').val();  
    			}   		
    			if(NivelHallazgo){  // Nivel de Hallazgo				      				
					NivelHallazgo = true;
					var ArrayDE=[{idCriterio:null,idTipificacion:null,idDetalleCriterio:null,idDetalleSupervision:idDetalleSupervision, descripcionResultado:descripcionResultado, idDetalleEvaluacion:null,tieneCriterio:-1,flagRegistrado:flagRegistrado}];
					verificarArrayDEvaluacion(ArrayDE);	        				
    				$('#txtDescripcionHallazgoED').val(descripcionResultado);
					$('#formEvalDescargo').css('display','inline');$('#divTipificacionGrillaED').css('display','none');$('#divDescripcionHallazgoED').css('display','inline');
    			} else {
	    			if(data.total==0){   // Nivel de Hallazgo
	    				NivelHallazgo = true; 
	    				$('#formEvalDescargo').css('display','inline'); $('#divTipificacionGrillaED').css('display','none'); $('#divDescripcionHallazgoED').css('display','inline');
	    			}
	    			else{    
	    				if(data.filas.length == 1){	 
	    					if(flagResultado==constant.tipoSancion.sancion || (flagResultado==constant.tipoSancion.nosancion && idTipificacion==null)){  // Nivel de Hallazgo
	    						NivelHallazgo = true;
	    						var ArrayDE=[{idCriterio:null,idTipificacion:null,idDetalleCriterio:null,idDetalleSupervision:idDetalleSupervision, descripcionResultado:descripcionResultado, idDetalleEvaluacion:null,tieneCriterio:-1,flagRegistrado:flagRegistrado}];
	    						verificarArrayDEvaluacion(ArrayDE);	        				
		        				$('#txtDescripcionHallazgoED').val(descripcionResultado);
		    					$('#formEvalDescargo').css('display','inline');$('#divTipificacionGrillaED').css('display','none');$('#divDescripcionHallazgoED').css('display','inline');
	    					}
	    					else {  // Nivel de Tipificacion Multiple	 
	    						NivelHallazgo = false;
	        					$('#txtDescripcionHallazgoED').removeAttr('validate');
	    	    				$('#formEvalDescargo').css('display','inline');$('#divTipificacionGrillaED').css('display','inline');$('#divDescripcionHallazgoED').css('display','none');
	    					}
	    				} else { // Nivel de Tipificacion Multiple
	    					NivelHallazgo = false;
	    					$('#txtDescripcionHallazgoED').removeAttr('validate');
		    				$('#formEvalDescargo').css('display','inline');$('#divTipificacionGrillaED').css('display','inline');$('#divDescripcionHallazgoED').css('display','none');
	    				}
	    			}
	    			$('#gridTipificacionesED').find('td[aria-describedby="gridTipificacionesED_seleccion"]').css('white-space','nowrap');
	    			var fila = $('#gridTipificacionesED').jqGrid('getRowData');
	                for (var i = 0; i < fila.length; i++) {
	                	$('#gridTipificacionesED').find("td[aria-describedby='gridTipificacionesED_codTipificacion']").css("font-weight","bold");
	                	$('#gridTipificacionesED').find("td[aria-describedby='gridTipificacionesED_descripcion']").css("font-weight","bold");
	                	$('#gridTipificacionesED').find("td[aria-describedby='gridTipificacionesED_sancionMonetaria']").css("font-weight","bold");
	                	$('#gridTipificacionesED').find("td[aria-describedby='gridTipificacionesED_otrasSanciones']").css("font-weight","bold");
	                	$('#gridTipificacionesED').find("td[aria-describedby='gridTipificacionesED_comentario']").css("font-weight","bold");
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
                    	tipoDH:$('#tipoED').val(),
                    	idDetalleSupervision: rowData["detalleSupervision.idDetalleSupervision"],
                    	idTipificacion: rowData["tipificacion.idTipificacion"],
                    	idCriterio: idCriterio,
                    	idObligacion:$('#idObligacionED').val()
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
	
	$("#divComentarioHallazgoED").dialog({ 
        resizable: false,
        draggable: true,
        autoOpen: false,
        height:"auto",
        width: "auto",
        modal: true,
        dialogClass: 'dialog',
        closeText: "Cerrar" 
    });	 //mdiosesf - RSIS6
	
	
	function validarDescEvalDescargo(){
		var mensajeValidacion = "";
		var ids = jQuery("#gridTipificacionesED").jqGrid('getDataIDs');
		var nroSubGrid = 1;
		var nroGrid = 0; 
		if(!NivelHallazgo){
			for(var i = 0; i<ids.length; i++){
			    var rowId = ids[i];
			    var rowData = jQuery('#gridTipificacionesED').jqGrid ('getRowData', rowId);
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
			    	var idsSubGrid = jQuery('#gridTipificacionesED_'+nroSubGrid+'_t').jqGrid('getDataIDs');
			    	for(var x = 0; x<idsSubGrid.length; x++){
					    var rowIdSubGrid = idsSubGrid[x];
					    var rowDataSubGrid = jQuery('#gridTipificacionesED_'+nroSubGrid+'_t').jqGrid ('getRowData', rowIdSubGrid);				    
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
			var descripcionResultado = $('#txtDescripcionHallazgoED').val();
			if(descripcionResultado.trim()!='' && descripcionResultado.trim().length<8){
				$('#txtDescripcionHallazgoED').addClass('error');
	    		mensajeValidacion += "El comentario de hallazgo debe tener una longitud mayor o igual a 8 caracteres, corregir. <br>";
			}			
		}		
		setArrayDEvaluacion();		
//		for(var x = 0; x<ArrayDEvaluacion.length; x++){			 
//			console.info("idCriterio: " + ArrayDEvaluacion[x].idCriterio + " | idTipificacion: " + ArrayDEvaluacion[x].idTipificacion + " | idDetalleCriterio: " + ArrayDEvaluacion[x].idDetalleCriterio + " | idDetalleSupervision: " + ArrayDEvaluacion[x].idDetalleSupervision + " | descripcionResultado: " + ArrayDEvaluacion[x].descripcionResultado + " - idDetalleEvaluacion: " + ArrayDEvaluacion[x].idDetalleEvaluacion + " | tieneCriterio: " + ArrayDEvaluacion[x].tieneCriterio + " | flagRegistrado: " + ArrayDEvaluacion[x].flagRegistrado) 
//		}
		return mensajeValidacion;
	}
	
	function setArrayDEvaluacion(){ //mdiosesf - RSIS6
		var ids = jQuery("#gridTipificacionesED").jqGrid('getDataIDs');
		var nroSubGrid = 1;	
		var nroGrid = 0; 
		if(!NivelHallazgo){
			for(var i = 0; i<ids.length; i++){
			    var rowId = ids[i];
			    var rowData = jQuery('#gridTipificacionesED').jqGrid ('getRowData', rowId);
			    var tieneCriterio = rowData['tipificacion.tieneCriterio'];
			    var idTipificacion = rowData['tipificacion.idTipificacion'];		   
		    	var idDetalleSupervision = rowData['detalleSupervision.idDetalleSupervision']; 
		    	var idDetalleEvaluacion = rowData['idDetalleEvaluacion'];
		    	if(idDetalleEvaluacion==''){idDetalleEvaluacion=null;}
		    	if(tieneCriterio == 0){nroGrid++;}
		    	else if(tieneCriterio == 1){
		    		if(nroGrid>0){nroSubGrid++;}
			    	var idsSubGrid = jQuery('#gridTipificacionesED_'+nroSubGrid+'_t').jqGrid('getDataIDs');
			    	for(var x = 0; x<idsSubGrid.length; x++){
					    var rowIdSubGrid = idsSubGrid[x];
					    var rowDataSubGrid = jQuery('#gridTipificacionesED_'+nroSubGrid+'_t').jqGrid ('getRowData', rowIdSubGrid);				    
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
			}
		}
		else {
			var descripcionResultado = $('#txtDescripcionHallazgoED').val();
			var idDetalleSupervision = $('#idDetalleSupervisionED').val();
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
	
	function enviarDatosArchivoBL(data) {
        if (data.error) {mensajeGrowl("warn", "Advertencia:", data.mensaje);}
        else {
        	mensajeGrowl("success", constant.confirm.save);
        	$('#flagResultadoED').val( $('#tipoSancion').val());
        	grillaArchivosMediosProbatorio();
        }
    }
	function guardarEvalDescargo(){
		var mensajeValidacion = '';
		if(validarFormulario()){
			mensajeValidacion = validarDescEvalDescargo(); //mdiosesf - RSIS6
			if(mensajeValidacion==''){confirmer.open('¿Confirma que desea registrar los datos del descargo?', 'coEvalDesc.registrarEvalDescargo()');}
			else{mensajeGrowl('warn', mensajeValidacion);}
		}
	}
	function eliminarMedioProbatorioConf(idDocumentoAdjunto){
		confirmer.open('¿Confirma que desea eliminar el medio probatorio?', 'coEvalDesc.suprimirMedioProbatorio("'+idDocumentoAdjunto+'")');
	}
	//CRUD
	function registrarEvalDescargo(){ //mdiosesf - RSIS6
		loading.open(); 		
        var i = 0;
        //generamos los parametros para post
        var parameters = "_=p";
        var idDetalleSupervision = $('#idDetalleSupervisionED').val();
        parameters += "&tipoCumple="+$('#tipoSancion').val(); 
        if(idDetalleSupervision!=null) parameters += "&idDetalleSupervision="+idDetalleSupervision; else parameters += "&idDetalleSupervision="-1;
        parameters += "&idObligacion="+$('#idObligacionED').val(); 
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
                	$('#divMediosProbatorioED').css("display","inline");
                } else {
                    mensajeGrowl("error", data.mensaje);
                }
            },
            error: errorAjax
        });
	}
	function registrarDocumentoAdjunto(){
		$("#formMedioAprobatorioED").submit();
	}
	function suprimirMedioProbatorio(idDocumentoAdjunto){
		loading.open();
		var url = baseURL + "pages/archivo/eliminarPghDocAdjDetalleSuper";
		$.post(url, {
			idDocumentoAdjunto : idDocumentoAdjunto,
			'detalleSupervision.idDetalleSupervision':$('#idDetalleSupervisionED').val()
		}, function(data) {
			loading.close();
			if (data.resultado == '0') {grillaArchivosMediosProbatorio();mensajeGrowl("success", constant.confirm.remove);}
			else if (data.resultado == '1') {mensajeGrowl('error', data.mensaje);}
		});
	}
	//Validaciones
	function validarFormulario(){
		var validarEvaluacionDescargo = true;
		validarEvaluacionDescargo = $('#formEvalDescargo').validateAllForm('#divMensajeValidaFrmED');
		return validarEvaluacionDescargo;
	}
	function validarEvalDescargo(){
		$('#txtDescargo').removeClass('error');
		var mensajeValidacion = "";
		if($('#txtDescargo').val().trim()!='' && $('#txtDescargo').val().trim().length<8){
			$('#txtDescargo').addClass('error');
			mensajeValidacion += "El descargo debe tener una longitud mayor o igual a 8 caracteres,corregir <br>";
		}
		return mensajeValidacion;
	}
	function validarAgregarMedioProbatorioED(){
		$('#txtDescripcionMPED,#file_nameSuperED').removeClass('error');
		var mensajeValidacion = '';
		if(validarFormMedioProbatorio()){
			mensajeValidacion = validarRegistrado();
			if(mensajeValidacion==''){
				mensajeValidacion = validarArchivoPermitido($('#file_nameSuperED').val());
				if(mensajeValidacion==''){
					mensajeValidacion = validarMedioProbatorio($('#txtDescripcionMPED').val(),$('#file_nameSuperED').val());
					if(mensajeValidacion==''){confirmer.open('¿Confirma que desea agregar el archivo?', 'coEvalDesc.registrarDocumentoAdjunto()');}
				}
			}
			if(mensajeValidacion!=''){mensajeGrowl('warn', mensajeValidacion);}
		}
	}
	function validarFormMedioProbatorio(){
		var validarDocumentoAdjunto = true;
		validarDocumentoAdjunto = $('#formMedioAprobatorioED').validateAllForm('#divMensajeValidaFrmED');
		return validarDocumentoAdjunto;
	}
	function validarRegistrado(){
		var mensajeValidacion='';
		$.ajax({
            url: baseURL + "pages/supervision/buscarDetalleSupervision",
            type: 'post',
            async: false,
            data: {idDetalleSupervision:$('#idDetalleSupervisionED').val(),flagResultado:$('#tipoSancion').val(),flagRegistrado:constant.estado.activo},
            success: function(data) {
            	if(data.resultado=='0'){
	        		if(data.listaDetalleSupervision.length==undefined || data.listaDetalleSupervision.length==0){
	        			return mensajeValidacion = "Debes de registrar Descargo <br>";
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
            	idDetalleSupervision : $('#idDetalleSupervisionED').val(),
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
	    editar = "<img src=\"" + baseURL + "/../images/stickers.png\" width='17' height='18' onclick=\"coEvalDesc.descargaDocumentosAdjunto('" + id + "')\" style=\"cursor: pointer;\" alt=\"Descargar Medio Probatorio\"/>";
        return editar;
	}
	function eliminarMedioProbatorio(cellvalue, options, rowdata) {
		var editar = "";
        editar = "<img src=\"" + baseURL + "/../images/delete_16.png\" style=\"cursor: pointer;\" alt=\"Eliminar Medio Probatorio\" onclick=\"coEvalDesc.eliminarMedioProbatorioConf('"+rowdata.idDocumentoAdjunto+"');\" />";
        return editar;
	}
	function descargaDocumentosAdjunto(idDocumentoResultante){
        document.location.href = baseURL + 'pages/archivo/descargaPghArchivoBD?idDocumentoAdjunto=' + idDocumentoResultante;
	}
	function activarModoConsulta(){
		$('#divAdjuntoED').css('display', 'none');
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
		if(tipo==0) { descripcionResultado = $("#comentario_"+idTipificacionCriterio).val(); }
		else { descripcionResultado = $("#comentarioCriterio_"+idTipificacionCriterio).val(); }
		$('#txtDescripcionED').val(descripcionResultado);
		$('#idTipificacionCriterioED').val(idTipificacionCriterio);		
		$("#divComentarioHallazgoED" ).dialog('option', 'title', 'INGRESO DE TEXTO');
		$('#divComentarioHallazgoED').dialog('open');
    }
	
	function comentarioTipificacion(cellvalue, options, rowdata) {  //mdiosesf - RSIS6 	
    	var html =""; 	
    	var idCriterio=rowdata.criterio.idCriterio;    	    	
    	var idDetalleCriterio=rowdata.detalleCriterio.idDetalleCriterio; 
    	var idDetalleSupervision=rowdata.detalleSupervision.idDetalleSupervision;
    	var idDetalleEvaluacion=rowdata.idDetalleEvaluacion;
    	var idTipificacion=rowdata.tipificacion.idTipificacion;   	
    	var descripcionResultado=rowdata.descripcionResultado;
    	var tieneCriterio=rowdata.tipificacion.tieneCriterio; 
    	var flagRegistrado=rowdata.flagRegistrado;
    	$('#idDetalleSupervisionED').val(idDetalleSupervision);
    	var tipoDH = $('#tipoED').val();    
    	if(!NivelHallazgo) {
    		var ArrayDE=[{idCriterio:idCriterio,idTipificacion:idTipificacion,idDetalleCriterio:idDetalleCriterio,idDetalleSupervision:idDetalleSupervision, descripcionResultado:descripcionResultado, idDetalleEvaluacion:idDetalleEvaluacion,tieneCriterio:tieneCriterio,flagRegistrado:flagRegistrado}];
    		verificarArrayDEvaluacion(ArrayDE);
    	}
    	if (tipoDH==constant.modoSupervision.consulta){if(tieneCriterio==0){html="<p style='text-align:left; font-size:11px;'>"+descripcionResultado+"</p>";}}
    	else{    		
    		if(tieneCriterio==0){  
    			html = "<input id='comentario_"+idTipificacion+"' type='text' class='ipt-grid-inps ipt-medium-small' disabled readonly";
    			html+=" name='comentario_"+idTipificacion+"' value='"+descripcionResultado+"' onclick='coEvalDesc.ComentarioHallazgoCriterio(this,\""+idTipificacion+"\",0)' />";   		
    			html+="<input type='hidden' id='id_"+idTipificacion+"' value='"+idTipificacion+"'/>";
    			html+="<input type='hidden' id='tieneCriterio_"+idTipificacion+"' value='"+tieneCriterio+"'/>";
    		}
    	}    	
        return html;
    }
	
	function seleccionTipificacion (cellvalue, options, rowdata){
    	var html ="";
    	var idTipificacion=rowdata.tipificacion.idTipificacion;
    	var idCriterio=rowdata.criterio.idCriterio;
    	var descripcionResultado=rowdata.descripcionResultado;
    	var tieneCriterio=rowdata.tipificacion.tieneCriterio;
    	var tipoDH = $('#tipoED').val(); 
    	if(tieneCriterio==0){
	    	html = "<center><input id='seleccion_"+idTipificacion+"' type='checkbox' onclick='coEvalDesc.habilitaComentarioTipi(this,\""+idTipificacion+"\")' name='seleccion_"+idTipificacion+"'";
	    	if (tipoDH==constant.modoSupervision.consulta){
	    		html+=" disabled ";
	    		if(tieneCriterio==0){html+=" checked ";}
	    	}
	    	else{if(tieneCriterio>0){html+=" disabled ";}}
	    	html+=" /> <label for='seleccion_"+idTipificacion+"' class='checkbox'></label></center>";
    	}
        return html; 
    }
	
    function formatoSancionTipificacion (cellvalue, options, rowdata){    	
    	var sancionMonetaria=$.trim(rowdata.tipificacion.sancionMonetaria);
    	var html = '';
    	if (sancionMonetaria != null && sancionMonetaria != '' && sancionMonetaria != undefined){html="Hasta "+sancionMonetaria+" UIT";}
    	return html;
    }
    
    function comentarioCriterio (cellvalue, options, rowdata) {
    	var html ="";
    	var idCriterio=rowdata.idCriterio;
    	var descripcionResultado=rowdata.descripcionResultado;
    	var flagRegistrado = 0;
    	if(descripcionResultado!=''){flagRegistrado=1;}
    	var tipoDH = $('#tipoED').val(); 
    	if(!NivelHallazgo) {
    		var ArrayDE=[{idCriterio:idCriterio,idTipificacion:null,idDetalleCriterio:null,idDetalleSupervision:null, descripcionResultado:descripcionResultado, idDetalleEvaluacion:null,tieneCriterio:null,flagRegistrado:flagRegistrado}];    	
    		verificarArrayDEvaluacion(ArrayDE);
    	}
    	if (tipoDH==constant.modoSupervision.consulta){html="<p style='text-align:left; font-size:11px;'>"+descripcionResultado+"</p>";}
    	else{   		
    		html = "<input id='comentarioCriterio_"+idCriterio+"' type='text' class='ipt-grid-inps ipt-medium-small' disabled readonly name='comentarioCriterio_"+idCriterio+"' value='"+descripcionResultado+"' onclick='coEvalDesc.ComentarioHallazgoCriterio(this,\""+idCriterio+"\",1)' />";
    		html+="<input type='hidden' id='id_"+idCriterio+"' value='"+idCriterio+"'/>";
    	}    	
        return html;
    }
    
    function seleccionCriterio (cellvalue, options, rowdata){
    	var idCriterio=rowdata.idCriterio;
    	var tipoDH = $('#tipoED').val();
    	var html = "<input id='seleccionCriterio_"+idCriterio+"' type='checkbox' onclick='coEvalDesc.habilitaComentarioCriti(this,\""+idCriterio+"\")' name='seleccionCriterio_"+idCriterio+"'";
    	if (tipoDH==constant.modoSupervision.consulta){html+=" disabled checked ";}
    	html+=" /><label for='seleccionCriterio_"+idCriterio+"' class='checkbox'></label>";
        return html;        
    }
    
    function formatoSancionCriterio (cellvalue, options, rowdata){
    	var sancionMonetaria=$.trim(rowdata.sancionMonetaria);
    	var html = '';
    	if (sancionMonetaria != null && sancionMonetaria != '' && sancionMonetaria != undefined){html=sancionMonetaria+" UIT";}
    	return html;
    }	
	return{
		constructor:constructor,
		registrarEvalDescargo:registrarEvalDescargo,
		registrarDocumentoAdjunto:registrarDocumentoAdjunto,
		eliminarMedioProbatorioConf:eliminarMedioProbatorioConf,
		suprimirMedioProbatorio:suprimirMedioProbatorio,
		descargaDocumentosAdjunto:descargaDocumentosAdjunto,
		ComentarioHallazgoCriterio:ComentarioHallazgoCriterio, //mdiosesf - RSIS6
		habilitaComentarioCriti:habilitaComentarioCriti, //mdiosesf - RSIS6
		habilitaComentarioTipi:habilitaComentarioTipi//mdiosesf - RSIS6
	};
})();


$(function() {
	boton.closeDialog();
	coEvalDesc.constructor();
});
	