/*
/**
* Resumen		
* Objeto			: bandejaIngresoLevantamiento.js
* Descripción		: js bandejaIngresoLevantamiento
* Fecha de Creación	: 31/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     31/10/2016      Mario Dioses Fernandez      Crear una funcionalidad de bandeja de expedientes con infracciones de supervisión DSR-CRITICIDAD para uso del Agente
* OSINE_SFS-791     31/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente registrar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
* OSINE_SFS-791     31/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente consultar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
*/ 

var modBanIngLev = {		
	inicioModBanIngLevantamiento : function(){
		$("#idEnviarInfoModLev").click(function() {
			var noTieneDescrip=false;
			$('#gridObliInfraccionModLev .flagDescLevantamiento').each(function() {
			    var flagDescLevantamiento = $(this).html();
			    if(flagDescLevantamiento!=null && flagDescLevantamiento=='0'){
			    	noTieneDescrip=true;
			    }			  
			});
			if(!noTieneDescrip) {
				modBanIngLev.enviarLevantamiento();
			} else {
				navSlide.confirmer('Advertencia', "Aun no se complet&oacute; el registro de levantamiento(s), verificar.");
			}
		 });
		
	},
	enviarLevantamiento : function(){
		$.ajax({
			url: "/inps/pages/modLevantamientos/enviarLevantamiento",
	        type:'post',
	        async:false,
	        data:{
	        	idExpediente: $('#idExpedienteBanIngLev').val(), 
	        	idSupervision: $('#idSupervisionBanIngLev').val()
	        },
	        beforeSend:navSlide.loadingOpen(),
	        success:function(data){
	        	navSlide.loadingClose();
	        	if (data.resultado == '0') {
	        		var html='<center><img src="/inps/stylesheets/contentLev/images/system/modal_ok.png" class="img-rounded"  width="140" />';
	        		html+='<p style="margin-top:10px;">'+data.mensaje+'</p></center>';
	        		navSlide.confirmer('Confirmaci&oacute;n', html);
            		modBanIngLev.listarSupSinSubSanar();
            		navSlide.navAjx('modLevantamientos/bandejaSupervisionInfraccion');
            	} else {
            		navSlide.confirmer('Error', data.mensaje);
            	}     
	        }
	    }); 		    
	},
	listarSupSinSubSanar : function(){
		$.ajax({
			url: "/inps/pages/modLevantamientos/listarSupSinSubSanar",
	        type:'post',
	        async:false,
	        data:{
	        	codigoOsinergmin: $('#codOsinergminSeccion').val()
	        },
	        beforeSend:navSlide.loadingOpen(),
	        success:function(data){
	        	navSlide.loadingClose();
	        	var html = '';
	        	$(data.supervisionList).each(function(i,obj){
	        		if(i==0){
	        			html+="<li class='active-infraccion li'"+this.idSupervision +"'>";		        			
	        		} else {
	        			html+="<li class='li'"+this.idSupervision +"'>";	
	        		}	        		            		
	        			html+="<a href='modLevantamientos/bandejaSupervisionInfraccion' class='item-infraccion nav_ajx'>";
	        				html+="<span class='top table-c'>";
	        					html+="<input type='hidden' class='idSupervision' value='"+this.idSupervision+"'>";
	        					html+="<span class='cell left'>Expediente:</span>";
	        					html+="<span class='cell numeroExpediente'>"+this.ordenServicioDTO.expediente.numeroExpediente+"</span>";
	        				html+="</span>";
	        				html+="<span class='middle table-c'>";
	        					html+="<span class='label-01 left'>C&oacute;d. Osinergmin:</span>";
	        					html+="<span class='label-02 codOsinergmin'>"+this.ordenServicioDTO.expediente.unidadSupervisada.codigoOsinergmin+"</span>";
	        				html+="</span>";	
	        				html+="<span class='middle table-c'>";
	        					html+="<span class='label-01 left'>Reg.Hidrocarburos:</span>";
	        					if(this.ordenServicioDTO.expediente.unidadSupervisada.nroRegistroHidrocarburo!=null){
	        						html+="<span class='label-02'>"+this.ordenServicioDTO.expediente.unidadSupervisada.nroRegistroHidrocarburo+"</span>";
	        					} else {
	        						html+="<span class='label-02'>--</span>";
	        					}	        					
	        				html+="</span>";
	        				html+="<span class='cell'>";
	        					html+="<span class='estado'>"+this.ordenServicioDTO.expediente.estadoLevantamiento.descripcion+"</span>";
	        				html+="</span>";											
	        			html+="</a>"; 
	        		if(this.ordenServicioDTO.expediente.estadoLevantamiento.codigo != $('#codigoEstadoLevEvaluar').val()) {
	        			html+="<span class='accion'>";
	        				html+="<a href='modLevantamientos/bandejaIngresoLevantamientos' class='btn-levantamiento nav_ajx'>Levantamiento</a>";
	        			html+="</span>";
	        		}
	        		html+="</li>";            
	        	});      	
	        	if(html!=''){	        		
	        		$('.listado-infraccion').html(html);
	        	}
	        }
	    }); 		
	}
}
$(function(){	
	modBanIngLev.inicioModBanIngLevantamiento();
});