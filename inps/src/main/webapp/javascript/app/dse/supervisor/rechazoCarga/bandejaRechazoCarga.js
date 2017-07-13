/**
 * Resumen		
 * Objeto				: bandejaRechazoCarga
 * Descripción			: JavaScript donde se maneja las acciones de la búsqueda de Empresas.
 * Fecha de Creación	: 15/09/216.
 * PR de Creación		: OSINE_SFS-1063.
 * Autor				: Victoria Ubaldo Gamarra
 * ===============================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |     Descripción
 * ===============================================================================================
 *
 */
	 
var especialistaRechazoCarga={
		cargarAnios:function(idSlctDest) {
			$.ajax({
	            url:baseURL + "pages/empresasVw/cargarAnios",
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
			$("#slctAnio option[value='']").remove();
	    },
	    cargarTipoEmpresas:function(idSlctDest) {
			$.ajax({
	            url:baseURL + "pages/empresasVw/cargarTipoEmpresas",
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
    procesarGridEmpresas:function(nombreGrid, accion) {
    	var nombres = [ 'idOficioDoc','nombreOficioDoc',
    	                'idEmpresa', 'idTipo', 'anio','idInformeSupeRechCarga','idInformeDoc', 'nombreInformeDoc','TIPO EMPRESA', 'R.U.C.',
    					'EMPRESA', 'NRO. EXPEDIENTE', 'OFICIO', 'ACTA INSPECCIÓN',
    					'INFORME SUPERVISIóN', 'GENERAR OFICIO' ];
     			var columnas = [
    			     {name: "idOficioDoc", hidden:true},
     		        {name: "nombreOficioDoc",hidden:true},
    		        {name: "idEmpresa", hidden:true},
    	            {name: "idTipo", hidden:true},
    	            {name: "anio", hidden:true},
    	            {name: "idInformeSupeRechCarga", hidden:true},
    	            {name: "nombreInformeDoc", hidden: true },
    	            {name: "idInformeDoc", hidden: true },
    	            {name: "tipoEmpresa", width: 90, sortable: false, align: "left"},
    	            {name: "ruc", width: 90, align: "center"},
    	            {name: "empresa", width: 300, sortable: false, align: "left"},
    	            {name: "numeroExpediente", width: 90, sortable: false, align: "left"},
    	            {name: "",width: 60,sortable: false, align: "center",formatter: "descargarFileExpediente"},
    	            {name: "actaPresentacion", width: 130, sortable: false, align: "center", formatter:"actaInspeccion"},
    	            {name: "informeSupervision", width: 130, sortable: false, align: "center", formatter:"informeSupervision"},
    	            {name: "",width: 60,sortable: false, align: "center",formatter: "generarOficio"},
    	            ];
        $("#gridContenedor"+nombreGrid).html("");
        var grid = $("<table>", {
            "id": "grid"+nombreGrid
        });
        var pager = $("<div>", {
            "id": "paginacion"+nombreGrid
        });
        $("#gridContenedor"+nombreGrid).append(grid).append(pager);
        var ruc= $('#txtRUC').val();
        $('#txtEmpresa').val($.trim($('#txtEmpresa').val()));
        var numeroExpediente = $('#txtNumExpediente').val();
       
        var anio;
        var idTipo;
        if($('#slctAnio').val()==null){
        	anio='';
        }else{
        	anio=$('#slctAnio').val();
        }
        if($('#slctTipoEmpresa').val()==null){
        	idTipo='';
        }else{
        	idTipo=$('#slctTipoEmpresa').val();
        }
       
        grid.jqGrid({
        	url : baseURL + "pages/empresasVw/findEmpresaVw",
            datatype: "json",
            mtype : "POST",
            postData: {
            	anio : anio,
            	idTipo: idTipo,
            	ruc: ruc,
            	empresa: $('#txtEmpresa').val(),
            	numeroExpediente:  numeroExpediente
            },
            hidegrid: false,
            rowNum: constantBandeja.rowNumPrinc,
            pager: "#paginacion"+nombreGrid,
            emptyrecords: "No se encontraron registros para los filtros ingresados",
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
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });

    }  
};
 
 
jQuery.extend($.fn.fmatter, {
	descargarFileExpediente : function(cellvalue, options, rowdata) {
		var html = '';
		var nroExpediente = rowdata.numeroExpediente;
		 var nombre=rowdata.nombreOficioDoc;
		 var idArchivo=rowdata.idOficioDoc;
		if (nroExpediente != null) {
			 if (nombre != null && nombre != '' && idArchivo!='' && idArchivo!=null && nombre != undefined){
		            html = '<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idArchivo+'&nombreArchivo='+nombre+'">'+
		              '<img class="vam" width="17" height="18" title="Descargar" src="'+baseURL+'images/stickers.png">'+
		            '</a>';
		        }
		}
		return html;
	}
});

 

jQuery.extend($.fn.fmatter, {
	actaInspeccion : function(cellvalue, options, rowdata) {
	
		var nroExpediente = rowdata.numeroExpediente;
		 var nombre='0';
	     var idOficioDoc='0';
		var html = '';
		if (nroExpediente != null) {
			 if (nombre != null && nombre != '' && idOficioDoc!='' && idOficioDoc!=null){       
		            html = '<a href="#" class="link" style="color:#0073ea;" onClick="modalActasExpedientes.actasExpedientes(\''+nroExpediente +'\');" >'+
		            ' Ver '+
		            '</a>';
		        }
		}
		return html;
	}
});

jQuery.extend($.fn.fmatter, {
	informeSupervision : function(cellvalue, options, rowdata) {
		var html = '';
		var idInformeSupeRechCarga = rowdata.idInformeSupeRechCarga;
		 var nombre=rowdata.nombreInformeDoc;
	     var idInformedoc = rowdata.idInformeDoc;

		if (idInformeSupeRechCarga != null) {

			 if (nombre != null && nombre != '' && idInformeSupeRechCarga!='' && idInformeSupeRechCarga!=null){       
				 html ='<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idInformedoc+'&nombreArchivo='+rowdata.nombreInformeDoc+'">'+
	              '<img class="vam" width="17" height="18" title="Descargar" src="'+baseURL+'images/stickers.png">'+
		            '</a>';
			 }
		}
		
		return html;
	}
});

 
jQuery.extend($.fn.fmatter, {
	generarOficio : function(cellvalue, options, rowdata) {
		
		var numeroExpediente = rowdata.numeroExpediente;
		var ruc = rowdata.ruc;
		var idEmpresa=rowdata.idEmpresa;
		var anio = rowdata.anio;
		var empresa = rowdata.empresa;
		var html = '';
		if (numeroExpediente == null) {
						html = '<a href="#" onClick="modalOficio.abrirGeneraOficio(\''+ ruc + '\', \'' + idEmpresa + '\', \'' + anio + '\', \'' + empresa+ '\');" >'
								+ '<img id="btnGenerarOficio" class="vam" width="30" height="20" src="'
								+ baseURL + 'images/adjuntar.png">' + '</a>';
		}
		return html;
	}
});

$("#btnGenerarOficio").click(function() {
	modalOficio.abrirGeneraOficio();
 
});

var modalOficio = {
	abrirGeneraOficio : function(ruc,idEmpresa,anio,empresa) {
		var title = 'Adjuntar Oficio';
		$.ajax({
			url : baseURL + "pages/empresasVw/abrirGeneraOficio",
			type : 'POST',
			async : false,
			data : {
				ruc:ruc,
				idEmpresa: idEmpresa,
				anio:anio,
				empresa:empresa
			},
			beforeSend : loading.open,
			success : function(data) {
				loading.close();
				$("#dialogGenerarOficioEspecialista").html(data);
				$("#dialogGenerarOficioEspecialista").dialog({
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
			},
			error : errorAjax
		});
	
	}
};


var modalActasExpedientes = {
		actasExpedientes : function(numeroExpediente) {
			var title = 'Actas por Zona';
			$.ajax({
				url : baseURL + "pages/empresasVw/abrirActaExpedientesZona",
				type : 'POST',
				async : false,
				data : {
					numeroExpediente:numeroExpediente
				},
				beforeSend : loading.open,
				success : function(data) {
					loading.close();
					$("#dialogActaExpedientesZonaSupervisor").html(data);			
					$("#dialogActaExpedientesZonaSupervisor").dialog({
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
				},
				 error : errorListado
			});
			 
		}
	};


 
function errorListado(jqXHR) {
    var msj1 = "Aún no se ha realizado el Acta de Inspección por Zona.";
    
    loading.close();
    mensajeGrowl('warn', msj1);
}

$(function() {
	
	boton.closeDialog();
	$("#txtRUC").alphanum(constant.valida.alphanum.soloNumeros);
	$("#txtEmpresa").alphanum(constant.valida.alphanum.descrip);
	$("#txtNumExpediente").alphanum(constant.valida.alphanum.soloNumeros);

	especialistaRechazoCarga.cargarAnios('#slctAnio');
	especialistaRechazoCarga.cargarTipoEmpresas('#slctTipoEmpresa');
	especialistaRechazoCarga.procesarGridEmpresas('Empresas','');
	$('#btnBuscarRegistroRechazoCargaSupervisor').click(function() {
		especialistaRechazoCarga.procesarGridEmpresas('Empresas','buscar');
	});
	
	$('#btnLimpiarRechazoCarga').click(function(){
		$("#txtRUC").val('');
		$("#txtEmpresa").val('');
		$("#txtNumExpediente").val('');
		$('#slctTipoEmpresa').val('');
		
		especialistaRechazoCarga.procesarGridEmpresas('Empresas','buscar');
	});
	
});