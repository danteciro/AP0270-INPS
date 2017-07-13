/**
 * Resumen		
 * Objeto				: actaInspeccion.js
 * Descripción			: JavaScript donde se maneja las acciones de la búsqueda de Empresas Zona.
 * Fecha de Creación	: 15/09/216.
 * PR de Creación		: OSINE_SFS-1063.
 * Autor				: Hernan Torres Saenz.
 * ===============================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |     Descripción
 * ===============================================================================================
 *
 */

var especialistaActaInspeccion={
	cargarAnios:function(idSlctDest) {
		$.ajax({
            url:baseURL + "pages/actaInspeccion/cargarAnios",
            type:'get',
            dataType:'json',
            async:false,
            data:{},
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                fill.comboValorId(data,"codigo","descripcion",idSlctDest,"-1");
            },
            error:function(jqXHR){errorAjax(jqXHR);}
        });
		$("#slctAnioActa option[value='']").remove();
		especialistaActaInspeccion.cargarZonas($('#slctAnioActa').val());
    },
    cargarTipoEmpresas:function(idSlctDest) {
		$.ajax({
            url:baseURL + "pages/actaInspeccion/cargarTipoEmpresas",
            type:'get',
            dataType:'json',
            async:false,
            data:{},
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                fill.comboValorId(data,"codigo","descripcion",idSlctDest,"-1");
            },
            error:function(jqXHR){errorAjax(jqXHR);}
        });
    },
    procesarGridEmpresasZona:function(nombreGrid, accion) {
        var nombres = ['idSupervisionRechazoCarga','idSupeCampRechCarga','idTipo','Tipo Empresa','R.U.C.','rucHidden','idEmpresa','Empresa','descripcionEmpresaHidden','Nro. Expediente',
                       'numeroExpedienteHidden','idOficio','nombreOficio','Oficio','idZona','Zona','idActa','nombreActa','Acta Inspecci&oacute;n','Imp Plantilla',
                       'Reg. Acta','Imprimir Acta','Adj. Acta','Reabrir Acta','anio', 'flagCerrado'];
        var columnas = [
                        
            {name: "idSupervisionRechazoCarga", hidden:true},
            {name: "idSupeCampRechCarga", hidden:true},
            {name: "idTipo", hidden:true, width: 130, sortable: false},
            {name: "tipo", hidden:false, width: 90, sortable: false},
            {name: "ruc", width: 65, hidden:false},
            {name: "rucHidden", width: 65, hidden:true},
            {name: "idEmpresa", hidden:true},
            {name: "descripcionEmpresa", width: 180, sortable: false, align: "left"},
            {name: "descripcionEmpresaHidden", width: 180, sortable: false, align: "left", hidden:true},
            {name: "numeroExpediente", width: 90, sortable: false, align: "left"},
            {name: "numeroExpedienteHidden", width: 90, sortable: false, align: "left", hidden:true},
            {name: "idOficio", hidden:true},
            {name: "nombreOficio", hidden:true},
            {name: "", width: 70, sortable: false, align: "center",formatter: "descargarFileOficio"},
            {name: "idZona", width: 130, sortable: false, align: "left",hidden:true},
            {name: "descripcionZona", width: 70, sortable: false,hidden:false, align: "left"},
            {name: "idActa", hidden:true},
            {name: "nombreActa", hidden:true},
            {name: "", width: 90, sortable: false, align: "center",formatter: "descargarFileActa"},
            {name: "", width: 70, sortable: false, align: "center",formatter: "imprimirPlantilla"},
            {name: "", width: 70, sortable: false, align: "center",formatter: "registrarActa"},
            {name: "", width: 80, sortable: false, align: "center",formatter: "imprimirActa"},
            {name: "", width: 70, sortable: false, align: "center",formatter: "adjuntar"},
            {name: "", width: 70, sortable: false, align: "center",formatter:"reabrirActa"},
            {name: "anio", width: 130, sortable: false, align: "left",hidden:true},
            {name: "flagCerrado", hidden:true},
            
            ];
        $("#gridContenedor"+nombreGrid).html("");
        var grid = $("<table>", {
            "id": "grid"+nombreGrid
        });
        var pager = $("<div>", {
            "id": "paginacion"+nombreGrid
        });
        $("#gridContenedor"+nombreGrid).append(grid).append(pager);
        var anio;
        var tipoEmpresa;
        var tipoZona;
        if($('#slctAnioActa').val()==null){
        	anio='';
        }else{
        	anio=$('#slctAnioActa').val();
        }
        if($('#slctTipoEmpresaActa').val()==null){
        	tipoEmpresa='';
        }else{
        	tipoEmpresa=$('#slctTipoEmpresaActa').val();
        }
        if($('#slctZona').val()==null){
        	tipoZona='';
        }else{
        	tipoZona=$('#slctZona').val();
        }
        $('#idEmpresaActa').val($.trim($('#idEmpresaActa').val()));
        grid.jqGrid({
            url: baseURL + "pages/actaInspeccion/findEmpresasZona",
            datatype: "json",
            mtype : "POST",
            postData: {
            	anio : anio,
            	idTipo: tipoEmpresa,
            	idZona: tipoZona,
            	ruc: $('#idRUCActa').val(),
            	descripcionEmpresa: $('#idEmpresaActa').val(),
            	numeroExpediente: $('#idNroExpedienteActa').val()
            },
            hidegrid: false,
            rowNum: constantBandeja.rowNumPrinc,
            pager: "#paginacion"+nombreGrid,
            emptyrecords: "No se encontraron registros para los filtros ingresados.",
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
                id: "idSupervisionRechazoCarga"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            loadComplete: function(data) {
            	
            	if(data.error!=null){
            		mensajeGrowl("error", data.error, "");
            	}
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });

    },
    cargarZonas:function(anio) {
    	$.ajax({
            url:baseURL + "pages/actaInspeccion/listarZonas",
            type:'get',
            dataType:'json',
            async:false,
            data:{
            	anio: anio
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                if(data.resultado==0){
                	fill.combo(data.listaZonas,"idZona","descripcion",slctZona);
                }else{
                    mensajeGrowl("error", data.mensaje, "");
                }
                
            },
            error:function(jqXHR){errorAjax(jqXHR);}
        });
    }
};

jQuery.extend($.fn.fmatter, {
	descargarFileOficio: function(cellvalue, options, rowdata) {
        var html = '';
        var nombreOficio=rowdata.nombreOficio;
        var idOficio=rowdata.idOficio;
        if(rowdata.numeroExpediente!=null && rowdata.numeroExpediente!=''){
	        if (nombreOficio != null && nombreOficio != '' && idOficio!='' && idOficio!=null){
	            html = '<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idOficio+'&nombreArchivo='+nombreOficio+'">'+
	              '<img class="vam" width="17" height="18" title="Descargar" src="'+baseURL+'images/stickers.png">'+
	            '</a>';
	        }
        }
        return html;
    }
});

jQuery.extend($.fn.fmatter, {
	descargarFileActa: function(cellvalue, options, rowdata) {
        var html = '';
        var nombreActa=rowdata.nombreActa;
        var idActa=rowdata.idActa;
        
	        if (nombreActa != null && nombreActa != '' && idActa!='' && idActa!=null){
	            html = '<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idActa+'&nombreArchivo='+nombreActa+'">'+
	              '<img class="vam" width="17" height="18" title="Descargar" src="'+baseURL+'images/stickers.png">'+
	            '</a>';
	        }
		
        return html;
    }
});



/**MODAL IMPRIMIR
jQuery.extend($.fn.fmatter, {
	imprimirPlantilla: function(cellvalue, options, rowdata) {
		var anio=rowdata.anio;
		var subEstacion=0;
		var idEmpresa=rowdata.idEmpresa;
		var esplantilla=true;
        var html = '';
        var nombre='0';
        var idSupeCampRechCarga=rowdata.idSupeCampRechCarga;
        var idArchivo='0'; 
        if (nombre != null && nombre != '' && idArchivo!='' && idArchivo!=null){
            html = '<a class="link" href="'+baseURL + 'pages/actaInspeccion/imprimirPlantilla?subEstacion='+subEstacion+'&idEmpresa='+idEmpresa+'&anio='+anio+'&idSupeCampRechCarga='+idSupeCampRechCarga+'&esplantilla='+esplantilla+'">'+
              '<img class="vam" width="26" height="26" title="Imprimir" src="'+baseURL+'images/imprimir.png">'+
            '</a>';
        }
        
        return html;
    }
});
jQuery.extend($.fn.fmatter, {
	imprimirPlantilla : function(cellvalue, options, rowdata) {
		
		var anio=rowdata.anio;
		var subEstacion=0;
		var idEmpresa=rowdata.idEmpresa;
		var empresa=rowdata.descripcionEmpresa;
		var esPlantilla=true;
		var idZona=rowdata.idZona;
		var descripcionZona=rowdata.descripcionZona;
		var idSupeCampRechCarga=rowdata.idSupeCampRechCarga;
        var html = ''; 
						html = '<a href="#" onClick="modalImprimir.abrirModalGenerarActaInspeccion(\''+ subEstacion + '\', \'' + idEmpresa + '\', \'' + empresa + '\', \'' + anio + '\', \'' + idSupeCampRechCarga+ '\', \'' +idZona+ '\', \'' + esPlantilla + '\', \'' + descripcionZona + '\' );" >'
								+ '<img id="btImprimirPlantilla" class="vam" width="26" height="26" title="Imprimir" src="'
								+ baseURL + 'images/imprimir.png">' + '</a>';
		 
		return html;
		
 
	}
});

**/

jQuery.extend($.fn.fmatter, {
	imprimirPlantilla : function(cellvalue, options, rowdata) {
		
		var anio=rowdata.anio;
		var subEstacion=0;
		var idEmpresa=rowdata.idEmpresa;
		var empresa=rowdata.descripcionEmpresaHidden;
		var esPlantilla=true;
		var idZona=rowdata.idZona;
		var descripcionZona=rowdata.descripcionZona;
		var idSupeCampRechCarga=rowdata.idSupeCampRechCarga;
        var html = ''; 
						html = '<a href="#" onClick="modalImprimir.validarCargarSubEstaciones(\''+ subEstacion + '\', \'' + idEmpresa + '\', \'' + empresa + '\', \'' + anio + '\', \'' + idSupeCampRechCarga+ '\', \'' +idZona+ '\', \'' + esPlantilla + '\', \'' + descripcionZona + '\' );" >'
								+ '<img id="btImprimirPlantilla" class="vam" width="26" height="26" title="Imprimir" src="'
								+ baseURL + 'images/imprimir.png">' + '</a>';
		 
		return html;
		
 
	}
});
$("#btnGenerarOficio").click(function() {
	modalImprimir.abrirModalGenerarActaInspeccion();
 
});

var modalImprimir = {
		validarCargarSubEstaciones:function(subEstacion,idEmpresa,empresa,anio,idSupeCampRechCarga,idZona,esPlantilla,descripcionZona){
			$.ajax({
	            url:baseURL + "pages/actaInspeccion/cargarSubEstaciones",
	            type:'post',
	            dataType:'json',
	            async:false,
	            data:{
	            	idEmpresa: idEmpresa, 
	            	anio: anio,
	            	idZona: idZona
	            },
	            beforeSend:loading.open,
	            success:function(data){
	                loading.close();
	                if(data.listaSubEstaciones != null){
	                	modalImprimir.abrirModalGenerarActaInspeccion(subEstacion,idEmpresa,empresa,anio,idSupeCampRechCarga,idZona,esPlantilla,descripcionZona);
	                }else{
	                    mensajeGrowl("warn", "En la "+descripcionZona+" de la Empresa "+empresa+" no se tiene Sub Estaciones.", "");
	                }
	                
	            },
	            error:function(jqXHR){errorAjax(jqXHR);}
	        });
		},
		abrirModalGenerarActaInspeccion : function(subEstacion,idEmpresa,empresa,anio,idSupeCampRechCarga,idZona,esPlantilla,descripcionZona) {
		var title = 'Configurar Etapas'; 
		$.ajax({
			url : baseURL + "pages/actaInspeccion/abrirGenerarActaInspeccion",
			type : 'POST',
			async : false,
			data : {
				subEstacion : subEstacion,
				idEmpresa :idEmpresa,
				empresa:empresa,
				anio :anio,
				idSupeCampRechCarga :idSupeCampRechCarga,
				idZona: idZona,
				esPlantilla :esPlantilla,
				descripcionZona:descripcionZona
			},
			beforeSend : loading.open,
			success : function(data) {
				loading.close();
				$("#dialogGenerarActaInspeccion").html(data);
				$("#dialogGenerarActaInspeccion").dialog({
					resizable : false,
					draggable : true,
					autoOpen : true,
					height : "auto",
					width : "auto",
					modal : true,
					dialogClass : 'dialog',
					title : title,
					closeText : "Cerrar"
				});
				modalGenerarActa.cargarSubEstaciones("#slctSubEstacion",$('#idEmpresa').val(), $('#anio').val(), $('#idZona').val());
				$('#slctSubEstacion').val($('#comboEstacion').val());
			},
			error : errorAjax
		});
	
	}
};



/**MODAL IMPRIMIR ***/


jQuery.extend($.fn.fmatter, {
	adjuntar: function(cellvalue, options, rowdata) {
        var html = '';
        var idActaSupe=rowdata.idActa;
        var idSupeCampRech=rowdata.idSupeCampRechCarga;
        var flagCerrado=rowdata.flagCerrado;
        
        if ((idSupeCampRech != null && idSupeCampRech != '') &&
        		(idActaSupe == null || idActaSupe == '') && (flagCerrado == '0')){
        	var parametros="'"+idSupeCampRech+"','"+rowdata.numeroExpedienteHidden+"','"+rowdata.descripcionEmpresaHidden+"','"+rowdata.anio+"','"+rowdata.rucHidden+"'";
        	html = '<a href="#" onClick="modalActaInspeccion.abrirAdjuntarActa('+parametros+');" >'+
            '<img  class="vam" width="30" height="20" src="'+baseURL+'images/adjuntar.png">'+
            '</a>';
        }
        
        return html;
    }
});

jQuery.extend($.fn.fmatter, {
	registrarActa: function(cellvalue, options, rowdata) {
        var html = '';
        var idSupeCamp='0';
        var flagCerrado=rowdata.flagCerrado;
        
        if(rowdata.idSupeCampRechCarga==null || rowdata.idSupeCampRechCarga==''){
        	idSupeCamp='';
        }else{
        	idSupeCamp=rowdata.idSupeCampRechCarga;
        }
        
        if(flagCerrado != '1'){
	    	var empresaAnioZonaIdSupe="'"+rowdata.idEmpresa+"','"+rowdata.anio+"','"+rowdata.idZona+"','"+rowdata.descripcionZona+"','"+rowdata.idSupervisionRechazoCarga+"','"+idSupeCamp+"'";
	    	html = '<a href="#" onClick="modalActaInspeccion.validarAbrirRegistroActa('+empresaAnioZonaIdSupe+');" >'+
	        '<img  class="vam" width="20" height="20" src="'+baseURL+'images/cuaderno.png">'+
	        '</a>';
        }
        return html;
    }
});

jQuery.extend($.fn.fmatter, {
	imprimirActa: function(cellvalue, options, rowdata) {
		var anio=rowdata.anio;
		var subEstacion=0;
		var idEmpresa=rowdata.idEmpresa;
		var esplantilla=false;
        var html = '';
        var nombre='0';
        var idSupeCampRechCarga=rowdata.idSupeCampRechCarga;
        var idArchivo='0'; 
        var idZona = rowdata.idZona;
        var descripcionEmpresa= rowdata.descripcionEmpresaHidden;
        if (idSupeCampRechCarga != null && idSupeCampRechCarga != '' ){
            html = '<a class="link" href="'+baseURL + 'pages/actaInspeccion/imprimirActaInspeccion?subEstacion='+subEstacion+'&idEmpresa='+idEmpresa+'&anio='+anio+'&idSupeCampRechCarga='+idSupeCampRechCarga+'&esplantilla='+esplantilla+'&idZona='+idZona+'&descripcionEmpresa='+descripcionEmpresa+'">'+
              '<img class="vam" width="26" height="26" title="Imprimir" src="'+baseURL+'images/imprimir.png">'+
            '</a>';
        }
        
        return html;
    }
});

jQuery.extend($.fn.fmatter, {
	reabrirActa: function(cellvalue, options, rowdata) {
        var html = '';
        var flag=rowdata.flagCerrado;
        if (flag == '1'){
        	html = '<a href="#" onClick="modalActaInspeccion.validarReabrirRegistroActa('+rowdata.idSupeCampRechCarga+');" >'+
	        '<img  class="vam" width="55" height="16" src="'+baseURL+'images/reabrir.png">'+
	        '</a>';
        }
        return html;
    }
});

var modalActaInspeccion={
	validarAbrirRegistroActa:function(idEmpresa,anio,idZona,descripcionZona,idSupervision,idSupeCamp){
		$.ajax({
            url:baseURL + "pages/actaInspeccion/validarAbrirRegistroActa",
    		type:'GET',
    		dataType:'json',
    		async:false,
    		data:{
    			idEmpresa : idEmpresa,
    			anio : anio,
    			idZona: idZona,
    			idSupeCamp: idSupeCamp
    			},
    		beforeSend:loading.open,
    		success:function(data){
    			loading.close();
    						
				if(data.resultado==0){
						modalActaInspeccion.abrirRegistroActa(idEmpresa,anio,idZona,descripcionZona,idSupervision,idSupeCamp);
				}else{
    				mensajeGrowl("warn", data.mensaje, "");
    			}
    		       
    		},
    		error:function(jqXHR){errorAjax(jqXHR);}
    	});
    },	
	abrirRegistroActa:function(idEmpresa,anio,idZona,descripcionZona,idSupervision,idSupeCamp){
		tabSeleccionado="";
    	var title="Registrar Acta de Inspección";
    	$.ajax({
            url:baseURL + "pages/actaInspeccion/abrirRegistroActa",
    		type:'POST',
    		async:false,
    		data:{
    			idEmpresa : idEmpresa,
    			anio : anio,
    			idZona :  idZona,
    			nombreZona: descripcionZona,
    			idSupervisionRechCarga:  idSupervision,
    			idSupeCampRechCarga: idSupeCamp
    			},
    		beforeSend:loading.open,
    		success:function(data){
    			loading.close();
				$("#dialogRegistrarActa").html(data);
				$("#dialogRegistrarActa").dialog({
					resizable: false,
					draggable: true,
					autoOpen: true,
					height:"auto",
					width: "auto",
					modal: true,
					dialogClass: 'dialog',
					title: title,
					position: 'top',
					closeText: "Cerrar"
				});				
				$('#idListaReles').html(releVacio);
				modalRegistro.cargarDepartamento('#slctDepartamentoRA');
				$('#slctDepartamentoRA').val($('#comboDepartamento').val());
		        modalRegistro.cargarProvincia("#slctProvinciaRA",$('#slctDepartamentoRA').val());
		        $('#slctProvinciaRA').val($('#comboProvincia').val());
		        modalRegistro.cargarDistrito("#slctDistritoRA",$('#slctDepartamentoRA').val(), $('#slctProvinciaRA').val());
		        $('#slctDistritoRA').val($('#comboDistrito').val());
		        modalRegistro.cargarSubEstaciones("#slctSubEstacionRA",$('#idEmpresaRA').val(), $('#idAnioRA').val(), $('#idZonaRA').val());
		        if(idSupeCamp==''){
		        	especialistaActaInspeccion.procesarGridEmpresasZona('EmpresasZona','');
		        }
    		       
    		},
    		error:errorAjax
    	});
        
    },
    
    abrirAdjuntarActa:function(idSupeCamp, numeroExpediente, descripcionEmpresa, anio, ruc){
    	
    	var title="Adjuntar Acta de Inspección";
        $.ajax({
            url:baseURL + "pages/actaInspeccion/abrirAdjuntarActa",
    		type:'POST',
    		async:false,
    		data:{
    			idSupeCampRechCarga: idSupeCamp,
    			numeroExpediente: numeroExpediente,
    			descripcionEmpresa: descripcionEmpresa,
    			anio: anio,
    			ruc: ruc
    			},
    		beforeSend:loading.open,
    		success:function(data){
    			loading.close();
    				$("#dialogAdjuntarActa").html(data);
    				$("#dialogAdjuntarActa").dialog({
    					resizable: false,
    					draggable: true,
    					autoOpen: true,
    					height:"auto",
    					width: "auto",
    					modal: true,
    					dialogClass: 'dialog',
    					title: title,
    					closeText: "Cerrar"
    				});
    		},
    		error:errorAjax
    	});
    },
    
    validarReabrirRegistroActa:function(idsupe){
		confirmer.open("¿Ud. Está seguro de Reabrir el registro de Acta?","modalActaInspeccion.reabrirRegistroActa('"+idsupe+"')");
    },
    
    reabrirRegistroActa:function(idSupe) {
    	$.ajax({
            url:baseURL + "pages/actaInspeccion/actualizarFlagRegistroActa",
            type:'post',
            dataType:'json',
            async:false,
            data:{
            	idSupeCampRechCarga: idSupe,
            	estadoFlagRegistro: 'reabrirRegistroActa'
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                if (data.resultado == '0') {
                	mensajeGrowl("success", data.mensaje, "");
	                especialistaActaInspeccion.procesarGridEmpresasZona('EmpresasZona','');
                }else{
                	mensajeGrowl("error", data.mensaje, "");
                }
                
            },
            error:function(jqXHR){errorAjax(jqXHR);}
        });
    }
};


$(function() {
	
	especialistaActaInspeccion.cargarAnios('#slctAnioActa');
	especialistaActaInspeccion.cargarTipoEmpresas('#slctTipoEmpresaActa');
	$('#slctAnioActa').change(function(){
        fill.clean("#slctZona");
        especialistaActaInspeccion.cargarZonas($('#slctAnioActa').val());
    });
	
	$('#btnReabrirActa').click(function() {
        validarReabrirActa();
    });
	$('#btnBuscarRegistroActaInspeccion').click(function() {
		especialistaActaInspeccion.procesarGridEmpresasZona('EmpresasZona','buscar');
	});
	
	especialistaActaInspeccion.procesarGridEmpresasZona('EmpresasZona','');
	
	$('#idRUCActa').alphanum(constant.valida.alphanum.soloNumeros);
	$('#idNroExpedienteActa').alphanum(constant.valida.alphanum.soloNumeros);
	
	boton.closeDialog();
	
	$('#btnLimpiarActaInspeccion').click(function(){
		$("#idRUCActa").val('');
		$("#idEmpresaActa").val('');
		$("#idNroExpedienteActa").val('');
		$('#slctTipoEmpresaActa').val('');
		$('#slctZona').val('');
		
		especialistaActaInspeccion.procesarGridEmpresasZona('EmpresasZona','');
	});
	
});