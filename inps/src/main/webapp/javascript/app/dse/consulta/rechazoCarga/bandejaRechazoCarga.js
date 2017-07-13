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
	 
var consultaRechazoCarga={
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
    	var nombres = [ 'idEmpresa', 'idTipo', 'anio','idInformeSupeRechCarga','idInformeDoc', 'nombreInformeDoc','tipo', 'R.U.C.',
    					'EMPRESA', 'NRO. EXPEDIENTE', 'OFICIO', 'ACTA INSPECCIÓN',
    					'INFORME SUPERVISIÓN', 'Ver Otros Docum','flagObservado','idInformeSupeRechCarga','nombreInformeDoc','idInformeDoc' ];
    			var columnas = [
    		         {name: "idEmpresa", hidden:true},
    	            {name: "idTipo", hidden:true, width: 130, sortable: false},
    	            {name: "anio", width: 130, sortable: false, align: "left",hidden:true},
    	            {name: "idInformeSupeRechCarga", hidden:true},
    	            {name: "idOficioDoc", hidden: true },
    	            {name: "nombreOficioDoc", hidden: true },
    	            {name: "tipoEmpresa", width: 90, sortable: false, align: "left"},
    	            {name: "ruc", width: 90, align: "left"},
    	            {name: "empresa", width: 300, sortable: false, align: "left"},
    	            {name: "numeroExpediente", width: 90, sortable: false, align: "left"},
    	            {name: "",width: 60,sortable: false, align: "center",formatter: "descargarFileExpedienteRechazo"},
    	            {name: "actaPresentacion", width: 130, sortable: false, align: "center", formatter:"actaInspeccionRechazo"},
    	            {name: "informeSupervision", width: 130, sortable: false, align: "center", formatter:"informeSupervisionRechazo"},
    	            {name: "",width: 60,sortable: false, align: "center",formatter: "verOtrosDocumentosRechazo"},
    	            {name: "flagObservado", hidden:true},
    	            {name: "idInformeSupeRechCarga", hidden:true},
    	            {name: "nombreInformeDoc", hidden:true},
    	            {name: "idInformeDoc", hidden: true}
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
        if($('#slctAnio').val()==null){
        	anio='';
        }else{
        	anio=$('#slctAnio').val();
        }
        if($('#slctTipoEmpresa').val()==null){
        	tipoEmpresa='';
        }else{
        	tipoEmpresa=$('#slctTipoEmpresa').val();
        }
        
        
        var valorRadioSiSupervision =$('#radioSiSupervision').is(':checked');
        var valorObservado;
        if(valorRadioSiSupervision==true){
        	valorObservado="= 1";
        } else{
        	valorObservado=" is null";
        }
 
        $('#idEmpresa').val($.trim($('#idEmpresa').val()));
        
        grid.jqGrid({
        	url : baseURL + "pages/empresasVw/findEmpresaVw",
            datatype: "json",
            mtype : "POST",
            postData: {
            	anio : anio,
            	idTipo: tipoEmpresa,
            	ruc: $('#idRUC').val(),
            	empresa: $('#idEmpresa').val(),
            	numeroExpediente: $('#idNroExpediente').val() ,
            	flagObservado: valorObservado
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
	descargarFileExpedienteRechazo : function(cellvalue, options, rowdata) {
		var html = '';
		var numeroExpedienteDF = rowdata.numeroExpediente;
		 var nombreDF=rowdata.nombreOficioDoc;
		 var idArchivoDF=rowdata.idOficioDoc;
		if (numeroExpedienteDF != null) {
			 if (nombreDF != null && nombreDF != '' && idArchivoDF!='' && idArchivoDF!=null && nombreDF != undefined){
		            html = '<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idArchivoDF+'&nombreArchivo='+nombreDF+'">'+
		              '<img class="vam" width="17" height="18" title="Descargar" src="'+baseURL+'images/stickers.png">'+
		            '</a>';
		        }
		}
		return html;
	}
});


 

jQuery.extend($.fn.fmatter, {
	actaInspeccionRechazo : function(cellvalue, options, rowdata) {
	
		var numeroExpedienteDF = rowdata.numeroExpediente;
		 var nombreDF='0';
	     var idOficioDocDF='0';
		var html = '';
		if (numeroExpedienteDF != null) {
			 if (nombreDF != null && nombreDF != '' && idOficioDocDF!='' && idOficioDocDF!=null){       
		            html = '<a class="link" style="color:#0073ea;" onClick="modalActasExpedientesRechazo.actasExpedientesConsulta(\''+numeroExpedienteDF +'\');" >'+
		            ' Ver '+
		            '</a>';
		        }
		}
		return html;
	}
});

jQuery.extend($.fn.fmatter, {
	informeSupervisionRechazo : function(cellvalue, options, rowdata) {
		var flag= rowdata.flagObservado;
		var idArchivo= rowdata.idInformeDoc;
		var nombre=rowdata.nombreInformeDoc;

		var html = '';
			if(nombre != null && nombre != '' && idArchivo!='' && idArchivo!=null){
				if(flag!=null && flag==1){     
				    html = '<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idArchivo+'&nombreArchivo='+nombre+'">'+
		              '<img class="vam" width="17" height="18" title="Descargar" src="'+baseURL+'images/stickers2.png">'+
		            '</a>';
		        }else{
					    html = '<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idArchivo+'&nombreArchivo='+nombre+'">'+
			              '<img class="vam" width="17" height="18" title="Descargar" src="'+baseURL+'images/stickers.png">'+
			            '</a>';
			    }
			}
		return html;
	}
});

 
jQuery.extend($.fn.fmatter, {
	verOtrosDocumentosRechazo : function(cellvalue, options, rowdata) {
		  var html = '';
		  html = '<a  class="link" onClick="modalActasExpedientesRechazo.abrirVerDocumentoBandeja('+ rowdata.numeroExpediente+');" style="color:#0073ea;" href="#">Ver</a>';
		  return html;
		
	}
});

$("#btnGenerarOficio").click(function() {
	modalOficio.abrirGeneraOficio();
});

var modalOficio = {
	abrirGeneraOficio : function(ruc,idEmpresa,anio,empresa) {
		var title = '';
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
				$("#dialogGenerarOficio").html(data);
				$("#dialogGenerarOficio").dialog({
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


var modalActasExpedientesRechazo = {
		actasExpedientesConsulta : function(numeroExpediente) {
			var title = '';
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
					$("#dialogActaExpedientesZona").html(data);
					$("#dialogActaExpedientesZona").dialog({
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
		},
		abrirVerDocumentoBandeja : function(numeroExpediente) {
			$.ajax({
	             url: baseURL + "pages/informeSupervision/abrirVerDocumento",
	              type:'POST',
	              async:false,
	              data:{
	            	  numeroExpediente  : numeroExpediente
	                     },
	              beforeSend:loading.open,
	              success:function(data){
	                     loading.close();
	                     boton.closeDialog();
	                            $("#dialogVerDocumento").html(data);
	                            $("#dialogVerDocumento").dialog({
	                                   resizable: false,
	                                   draggable: true,
	                                   //position: 'top',
	                                   autoOpen: true,
	                                   height:"auto",
	                                   width: "auto",
	                                   modal: true,
	                                   dialogClass: 'dialog',
	                                   title: "Ver Otros Documentos",
	                                   closeText: "Cerrar"
	                            });
	                            //$('#nroExpedienteDoc').val(numeroExpediente);
	              },
	              error:errorAjax
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
	$("#idRUC").alphanum(constant.valida.alphanum.soloNumeros);
	$("#idEmpresa").alphanum(constant.valida.alphanum.descrip);
	$("#idNroExpediente").alphanum(constant.valida.alphanum.soloNumeros);

	consultaRechazoCarga.cargarAnios('#slctAnio');
	consultaRechazoCarga.cargarTipoEmpresas('#slctTipoEmpresa');
	consultaRechazoCarga.procesarGridEmpresas('Empresas','');
	$('#btnBuscarRegistroRechazoCarga').click(function() {
		consultaRechazoCarga.procesarGridEmpresas('Empresas','buscar');
	});
	
	$('#btnLimpiarRechazoCarga').click(function(){
		$("#idRUC").val('');
		$("#idEmpresa").val('');
		$("#idNroExpediente").val('');
		$('#slctTipoEmpresa').val('');
		$('#radioSiSupervision').attr('checked',false);
		
		consultaRechazoCarga.procesarGridEmpresas('Empresas','buscar');
	});
	
});