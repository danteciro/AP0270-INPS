$(function(){
	boton.closeDialog();
	$("#idRUCInf").alphanum(constant.valida.alphanum.soloNumeros);
	$("#idEmpresaInf").alphanum(constant.valida.alphanum.descrip);
	$("#idNroExpedienteInf").alphanum(constant.valida.alphanum.soloNumeros);
	
	mantenimientoInformeSupervision.cargarAnios('#comboAnioInf');
	mantenimientoInformeSupervision.cargarTipoEmpresas('#comboTipoEmpresaInf');
	mantenimientoInformeSupervision.procesarGridInformeSupervision('InformeSupervision');
	$("#btnBuscarInforme").click(function(){
		mantenimientoInformeSupervision.procesarGridInformeSupervision('InformeSupervision');
	});
	
	$('#btnLimpiarInforme').click(function(){
		$("#idRUCInf").val('');
		$("#idEmpresaInf").val('');
		$("#idNroExpedienteInf").val('');
		$('#comboTipoEmpresaInf').val('');
		
		mantenimientoInformeSupervision.procesarGridInformeSupervision('InformeSupervision');
	});
	
});

var idEmpresa;

var mantenimientoInformeSupervision = {
		
	cargarAnios:function(idSlctDest) {
			$.ajax({
	            url:baseURL + "pages/informeSupervision/cargarAnios",
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
			$("#comboAnioInf option[value='']").remove();
	    },
	cargarTipoEmpresas:function(idSlctDest) {
			$.ajax({
	            url:baseURL + "pages/informeSupervision/cargarTipoEmpresas",
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
		
	procesarGridInformeSupervision : function(nombreGrid) {
		var nombres = ['TIPO EMPRESA','R.U.C.','EMPRESA','NRO. EXPEDIENTE', 'OFICIO','ACTA INSPECCIÓN',
		               'INFORME SUPERVISIÓN','VER OTROS DOCUMENTOS','a','b','nombreInformeDoc','idInformeDoc','e','idOficioDoc','nombreOficioDoc','anio'];
        var columnas = [
            
            {name: "tipoEmpresa", width: 100, sortable: false, align: "left"},
            {name: "ruc", width: 95, sortable: false, align: "center"},
            {name: "empresa", width: 300, sortable: false, align: "left"},
            {name: "numeroExpediente", width: 130, sortable: false, align: "left"},
            {name: "", width: 60, sortable: false, align: "center",formatter:"formatoOficio" },
            {name: "", width: 130, sortable: false, align: "center", formatter:"formatoActaInspeccion"},
            {name: "", width: 130, sortable: false, align: "center", formatter:"formatoInforme"},
            {name: "", width: 130, sortable: false, align: "center", formatter:"formatoVerDocumento" },
            {name: "idSupervisionRechCarga", hidden: true },
            {name: "idInformeSupeRechCarga", hidden: true },
            {name: "nombreInformeDoc", hidden: true },
            {name: "idInformeDoc", hidden: true },
            {name: "idEmpresa", hidden:true},
            {name: "idOficioDoc", hidden:true},
            {name: "nombreOficioDoc", hidden:true},
            {name: "anio", hidden:true}
            
            ];
        var url = baseURL + "pages/informeSupervision/buscarEmpresas" ;
		var nombreGrid = "InformeSupervision";
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
        if($('#comboAnioInf').val()==null){
        	anio='';
        }else{
        	anio=$('#comboAnioInf').val();
        }
        if($('#comboTipoEmpresaInf').val()==null){
        	tipoEmpresa='';
        }else{
        	tipoEmpresa=$('#comboTipoEmpresaInf').val();
        }
        $('#idEmpresaInf').val($.trim($('#idEmpresaInf').val()));
        
        grid.jqGrid({
			    url: url,
	            datatype: "json",
	            mtype : "POST",
	            postData: { 
	            	anio : anio,
	            	tipoEmpresa: tipoEmpresa,
	            	ruc: $('#idRUCInf').val(),
	            	empresa: $('#idEmpresaInf').val(),
	            	numeroExpediente: $('#idNroExpedienteInf').val()
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
	                id: "idSupervisionRechCarga"
	            },
	            onSelectRow: function(rowid, status) {
	                grid.resetSelection();
	            },
	            loadComplete: function(data){
	             },
	            loadError: function(jqXHR) {
	                errorAjax(jqXHR);
	            }
	    });
        
	},

	abrirVerDocumento : function(numeroExpediente) {
		
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
	},
	
	abrirAdjuntarInforme : function(numeroExpediente,ruc,idSupervisionRechCarga,nombreEmpresa,anio) {
		$.ajax({
             url: baseURL + "pages/informeSupervision/abrirAdjuntarInforme",
              type:'POST',
              async:false,
              data:{
            	 
            	  },
              beforeSend:loading.open,
              success:function(data){
                     loading.close();
                     boton.closeDialog();
                            $("#dialogAdjuntarInforme").html(data);
                            $("#dialogAdjuntarInforme").dialog({
                                   resizable: false,
                                   draggable: true,
                                   //position: 'top',
                                   autoOpen: true,
                                   height:"auto",
                                   width: "auto",
                                   modal: true,
                                   dialogClass: 'dialog',
                                   title: "Adjuntar Informe",
                                   closeText: "Cerrar"
                            });
                            $("#nroExpediente").val(numeroExpediente);
                    	    $("#nroRuc").val(ruc);
                    	    $("#idSupervisionRechCargaInforme").val(idSupervisionRechCarga);
                    	    $("#hdnNombreEmpresaInf").val(nombreEmpresa);
                    	    $("#hdnAnioInf").val(anio);
              },
              
		});
	},

	abrirActaInspeccion : function(rowid) {
		$.ajax({
            url: baseURL + "pages/informeSupervision/abrirActaInspeccion",
             type:'POST',
             async:false,
             data:{
                    },
             beforeSend:loading.open,
             success:function(data){
                    loading.close();
                          $("#dialogActaInspeccion").html(data);
                           $("#dialogActaInspeccion").dialog({
                                  resizable: false,
                                  draggable: true,
                                  //position: 'top',
                                  autoOpen: true,
                                  height:"auto",
                                  width: "auto",
                                  modal: true,
                                  dialogClass: 'dialog',
                                  title: "Acta de Inspeccion por Sede",
                                  closeText: "Cerrar"
                           });
                           boton.closeDialog();
                           
             },
    
		});
	}
};

var modalActasExpedientesInforme = {
		actasExpedientes : function(numeroExpediente) {
			//alert("Expediente:::"+nroExpediente);
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
					//$("#dialogActaExpedientesZonaInfSup").html(data);
					//$("#dialogActaExpedientesZonaInfSup").dialog({
					$("#dialogActaExpedientesZonaEspecialista").html(data);			
					$("#dialogActaExpedientesZonaEspecialista").dialog({
						resizable : false,
						draggable : true,
						autoOpen : true,
						height : "auto",
						width : "auto",
						modal : true,
						dialogClass : 'dialog',
						title : title,
						closeText : "Cerrar",
						close:function(){
	                           //$("#dialogActaExpedientesZonaInfSup").dialog("destroy");
	                           //$(this).remove();
//							$(this).dialog("destroy");
//							$(this).remove();	
	                    }
					});
				},
				 error : errorListado
			});
			 
		}
	};

jQuery.extend($.fn.fmatter, { 
	 formatoVerDoc: function(cellvalue, options, rowdata) {
		    var html = '';
		    html = '<a href="#" onClick="mantenimientoInformeSupervision.abrirVerDocumento();" >'+
		    '<img id="btnVerDocumento" class="vam" width="30" height="20" src="'+baseURL+'images/adjuntar.png">'+
		    '</a>';
		    return html;

	    },
	
	 formatoInforme : function(cellvalue, options, rowdata) {
			  var html = '';
			  var idInformedoc = rowdata.idInformeDoc;
			  var anio=rowdata.anio;
			  anio=anio.substring(0,4);
			  
			  if (idInformedoc == null || idInformedoc == 0){
			    html = '<a href="#" onClick="mantenimientoInformeSupervision.abrirAdjuntarInforme('+rowdata.numeroExpediente+","+ rowdata.ruc+","+ rowdata.idSupervisionRechCarga +",'"+ rowdata.empresa + "'," + anio+');">'+
			    '<img id="btnVerFormato" class="vam" width="30" height="20" title="Adjuntar" src="'+baseURL+'images/adjuntar.png">'+
			    '</a>';
			  }
			  else
				  {
				  html ='<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idInformedoc+'&nombreArchivo='+rowdata.nombreInformeDoc+'">'+
	              '<img class="vam" width="17" height="18" title="Descargar" src="'+baseURL+'images/stickers.png">'+
		            '</a>'
					  ;
				  }
			    return html;
		  },
	 /*formatoActaInspeccion : function(cellvalue, options, rowdata){
			  var html = '';
			  html = '<a  class="link" onClick="mantenimientoInformeSupervision.abrirActaInspeccion();" style="color:#0073ea;" href="#">Ver</a>';
			  return html;
	  }*/
		  //jQuery.extend($.fn.fmatter, {
		  formatoActaInspeccion : function(cellvalue, options, rowdata) {
				
					var numeroExpediente = rowdata.numeroExpediente;
					 var nombre='0';
				     var idOficioDoc='0';
					var html = '';
					if (numeroExpediente != null) {
						 if (nombre != null && nombre != '' && idOficioDoc!='' && idOficioDoc!=null){       
					            html = '<a class="link" style="color:#0073ea;" onClick="modalActasExpedientesInforme.actasExpedientes(\''+numeroExpediente +'\');" >'+
					            ' Ver '+
					            '</a>';
					        }
					}
					return html;
				}
			//});
		  ,
	 formatoOficio : function(cellvalue, options, rowdata){
			  var html = '';
			  var idOficioDoc=rowdata.idOficioDoc;
			  var nombreOficioDoc=rowdata.nombreOficioDoc;
			  
			  if(idOficioDoc != null && idOficioDoc !=0){
				  //if (nombreOficioDoc != null && nombreOficioDoc != '' && nombreOficioDoc != undefined){
					  html = '<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idOficioDoc+'&nombreArchivo='+nombreOficioDoc+'">'+
					  		 '<img class="vam" width="17" height="18" title="Descargar" src="'+baseURL+'images/stickers.png">'+
					  		 '</a>';
				  //}
			  }
			  return html;
		  },
	 formatoVerDocumento : function(cellvalue, options, rowdata){
			  var html = '';
			  html = '<a  class="link" onClick="mantenimientoInformeSupervision.abrirVerDocumento('+ rowdata.numeroExpediente+');" style="color:#0073ea;" href="#">Ver</a>';
			  return html;
		  }
		  
		
});
