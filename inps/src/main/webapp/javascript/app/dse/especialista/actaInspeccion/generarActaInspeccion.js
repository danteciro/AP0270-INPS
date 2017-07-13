var listaEtapas = [];
var listaEtapasConfiguradas = [];
var lista={};
var modalGenerarActa={
			cargarSubEstaciones:function(idSlctDest, idEmpresa, anio, idZona){
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
		                fill.combo(data.listaSubEstaciones,"idSubEstacion","descripcion",idSlctDest);
		            },
		            error:function(jqXHR){errorAjax(jqXHR);}
		        });
			},
			// Obtener Etapas x Sub Estacion
			obtenerEtapaBySubEstacion: function(idSlctDest, idEmpresa, anio, idSubEstacion) {
		        var URL = baseURL + "pages/actaInspeccion/cargarEtapas";		        
		        if($('#slctSubEstacion').val()!=""){
					$.ajax({
			            url:URL,
			            type:'post',
			            dataType:'json',
			            async:false,
			            data:{	idEmpresa: idEmpresa,anio: anio,idSubEstacion: idSubEstacion},
			            beforeSend:loading.open,
			            success:function(data){
			            	loading.close();
			            	var html='';
			            	console.info('cantidad de registros --> '+data.length);
				            for(var x = 0; x < data.length;x++){
				            	var idEtapa = data[x].idEtapa;
				                var descripcionEtapa = data[x].descripcion;   
				                html += '<div style="margin-right:10px;margin-top:3px;width:130px;" validateK="[CHECK]" >';
				                html += '<input  id="' + idEtapa + '" type="checkbox" name="" value="' + idEtapa + '" class="checkbox chkEtapa"  onclick="slcEtapa(this)" />';
				                html += '<label for="' + idEtapa + '" class="checkbox">' + descripcionEtapa + '</label>';
				                html += '</div>';
				            }
				            $('#divTipSan').html(html);
				            $("#divTipSan").css('display','inline-block');		                
			                fill.combo(data,"idEtapa","descripcion",idSlctDest);
			            },
			            error:function(jqXHR){errorAjax(jqXHR);}
			        });
				}	
			},
			// Test
			//obtenerEtapaBySubEstacionConfigurada: function(idEmpresa,anio,idSubEstacion){
			obtenerEtapaBySubEstacionConfigurada: function(idEmpresa,anio,idSubEstacion, idZona){
			// Test
				var URL = baseURL + "pages/actaInspeccion/cargarEtapasConfiguradas";
				if($('#slctSubEstacion').val()!=""){
					$.ajax({
			            url:URL,
			            type:'post',
			            dataType:'json',
			            async:false,
			            data:{	
			            	idEmpresa: idEmpresa,
			            	anio: anio,
			            	idSubEstacion: idSubEstacion
			            	// Test
			            	,idZona: idZona
			            	// Test
			            	},
			            beforeSend:loading.open,			            
			            success:function(data){
			            	loading.close();
			            	if(data!=null){
			            		console.info('cantidad de registros --> '+data.length);			            		
			            		lista = data;
			            		console.info(lista);
					            for(var x = 0; x < data.length;x++){
					            	listaEtapasConfiguradas.push(data[x].idEtapa);
					            }
			            	}
			            	
			            },
			            error:function(jqXHR){errorAjax(jqXHR);}
			        });
				}else{
					
				}				
			}
		
};
var coExSuDo={
		guardarConfiguracionReles: function(listaEtapas) {
		    	 $.ajax({
		    	 url: baseURL + "pages/actaInspeccion/guardarConfiguracionReles",
		         type: 'post',
		         async: false,
		         dataType: 'json',
		         data: {
		        	 anio:$('#anio').val(),
		        	 idEmpresa:$('#idEmpresa').val(),
		        	 empresa:$('#empresa').val(),
		        	 idZona:$('#idZona').val(),
		        	 idSubestacion:$('#slctSubEstacion').val(),
		        	 idEtapas :listaEtapas.toString().trim(),
		        	 idSupeCampR : $('#idSupeCampRechCarga').val()
		         },
		         beforeSend: loading.open,
		         success: function(data) {
		             loading.close();
		             if (data.resultado == '0') {	
		            	 console.info(data.configuraciones +" tamaño: "+data.configuraciones.length);
		                if(data.configuraciones!=null && data.configuraciones.length>0){
		            		console.info('cantidad de registros --> '+data.configuraciones.length);			            		
		            		lista = data.configuraciones;
		            		console.info(lista);
		            		listaEtapasConfiguradas = [];
				            for(var x = 0; x < lista.length;x++){				            	
				            	listaEtapasConfiguradas.push(lista[x].idEtapa);
				            	console.info(listaEtapasConfiguradas);
				            }
		            	}else{
		            		if(data.recargarGrillla==1){
		            			if($('#rolSesion').val()=='ESPECIALISTA'){
		                    		especialistaActaInspeccion.procesarGridEmpresasZona('EmpresasZona','');
		                    	}else if($('#rolSesion').val()=='SUPERVISOR'){
		                    		supervisorActaInspeccion.procesarGridEmpresasZona('EmpresasZonaSupervisor','');
		                    	}
		            		}
		            		listaEtapasConfiguradas=[];
		            	}
		                mensajeGrowl("success", constant.confirm.save, ""); 
		                 
		             }else if(data.resultado == '2'){
		            	 mensajeGrowl("warn", data.mensaje, ""); 
		             }else{
		            	 
		            	 mensajeGrowl("error", data.mensaje, "");
		             }
		        },
		                error: function(jqXHR) {
		                    errorAjax(jqXHR);
		                }
		            });
		        },
imprimirActaInspeccion: function() { 
	idSubestacion = $('#slctSubEstacion').val();
	idEmpresa = $('#idEmpresa').val();
	anio = $('#anio').val();
	idSupeCampRechCarga = $('#idSupeCampRechCarga').val();
	idZona = $('#idZona').val(); 
	esPlantillas=true;
	var descripcionEmpresa = $('#empresa').val();
		if(idSubestacion!=null && idEmpresa != null && anio != null){
			//validar reporte pas
			data="idSubestacion="+idSubestacion+"&idEmpresa="+idEmpresa+"&anio="+anio+"&esPlantillas="+esPlantillas+"&idZona="+idZona+"&descripcionEmpresa="+descripcionEmpresa;
			URLdownload=baseURL + "pages/actaInspeccion/imprimirPlantilla";
			$.download(URLdownload, data, "get");
		}else{
			showMsj(3, "No se pudo mostrar el reporte");		
		}	
   }
};
function slcEtapa(obj){
	if($(obj).is(':checked')){
		listaEtapas.push($(obj).val());
		console.info('Etapas: -> '+listaEtapas);
	}else{
		for(var x=0;x<listaEtapas.length;x++){
			if(listaEtapas[x] == $(obj).val()){
				listaEtapas.splice(x, 1);
			}			
		}
	}
}
function validacion() {
	
    var validar = false;       
    var divValidacion = $('#divMensajeValidaFormPlantillaActa');
    var mensajeValidacion = "";
    var cont = 0;
    $('#divTipSan').find('[validateK|="[CHECK]"]').map(function() {
            $(this).find('input:checked').map(function() {
                cont++;
            });
            if (cont == 0) {
            	mensajeValidacion = "* Debe Seleccionar una Etapa.";
            }else{
            	mensajeValidacion = "";
            }
     });

    if(mensajeValidacion != ""){
        divValidacion.show();
        divValidacion.focus();
        divValidacion.html(mensajeValidacion);
        validar = false;
    }else{
        divValidacion.hide();
        divValidacion.html("");
        validar = true;
    }
    return validar;
}

function marcaCheck(idSubEstacion){
	var c=0;
	for(var x = 0; x < lista.length;x++){
    	if(lista[x].idSubEstacion==idSubEstacion){
    		c++;
    		console.info('eq - ' + c);
    		$('#divTipSan').find('input').map(function(){
    			var id = $(this).attr('id');
        		if(lista[x].idEtapa == id){
        			$('#'+id).attr('checked',true);    
        			listaEtapas.push(lista[x].idEtapa);
        		}
        	});    		
    	}
    }
	
}

function validaImprimirActaInspeccion(){
	$.ajax({
   	 url: baseURL + "pages/actaInspeccion/validaImprimirActa",
        type: 'post',
        async: false,
        dataType: 'json',
        data: {
       	 anio:$('#anio').val(),
       	 idEmpresa:$('#idEmpresa').val(),
       	 empresa:$('#empresa').val(),
       	 idSubestacion:$('#slctSubEstacion').val(),
       	idZona : $('#idZona').val()
       	},
        beforeSend: loading.open,
        success: function(data) {
            loading.close();
            if (data.resultado == '0') {
            	coExSuDo.imprimirActaInspeccion(); 
                } else if(data.resultado == '2')  {
                       mensajeGrowl("warn", data.mensaje, "");

                   }

               },
               error: function(jqXHR) {
                   errorAjax(jqXHR);
               }
           });
}

$(function() {
	$('.etapaActa').css('display','none');
	$('#slctSubEstacion').change(function() {
		listaEtapas = [];
		if($('#slctSubEstacion').val()!=''){	
			//Listar Checks Dinamicos
    		modalGenerarActa.obtenerEtapaBySubEstacion("#slctEtapa",$('#idEmpresa').val(),$('#anio').val(),$('#slctSubEstacion').val());
    		$('#btnImprimirActa').css('display','inline-block');
    		$('.etapaActa').css('display','inline-block');
    		//Listar Configuracion Registrada
    		// Test
    		//modalGenerarActa.obtenerEtapaBySubEstacionConfigurada($('#idEmpresa').val(),$('#anio').val(),$('#slctSubEstacion').val());
    		modalGenerarActa.obtenerEtapaBySubEstacionConfigurada($('#idEmpresa').val(),$('#anio').val(),$('#slctSubEstacion').val(),$('#idZona').val());
    		// Test
    		marcaCheck($('#slctSubEstacion').val());
    	}else{
    		$('.etapaActa').css('display','none');
    		$('#divTipSan').html('');
    		$('#btnImprimirActa').css('display','none');
    	}
	});

    $('#btnGuardarConfReles').click(function(){
    	var valida = $('#formGenerarActaInspeccion').validateAllForm('#divMensajeValidaFormPlantillaActa');
    	if(valida){
    		var validacionCheck;
    		if(listaEtapasConfiguradas.length == 0){
    			validacionCheck=validacion();
    			if(validacionCheck){
        			coExSuDo.guardarConfiguracionReles(listaEtapas); 
        		} 
    		}else{
    			coExSuDo.guardarConfiguracionReles(listaEtapas); 
    		}    		
    		   		 
    	}
    	
    	
      });

    $('#btnImprimirActa').click(function(){
    	validaImprimirActaInspeccion();	
       	  
      });
    
	//listasDinamicas.procesarEtapas();
	boton.closeDialog();
});
