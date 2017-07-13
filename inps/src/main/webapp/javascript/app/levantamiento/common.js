
/** 
/**
* Resumen		
* Objeto			: common.js
* Descripción		: js common.js
* Fecha de Creación	: 25/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad de bandeja de expedientes con infracciones de supervisión DSR-CRITICIDAD para uso del Agente
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente registrar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente consultar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
*/ 
 
var navSlide = function(){};

navSlide.prototype = {
  selectores : function(){
	  	this.$html = $('html'),
        this.$wrap = $('.view-wrap'),
        this.$btnAjx = $('.nav_ajx');
        this.eventAjx();
    },	
    eventAjx : function(){
    	var that = this;
    	this.$html.on('click','.nav_ajx',function(event){    		
    		event.preventDefault();
    		var url = $(this).attr('href');    		
    		
            $el = $(this);
    		if(!url || url == '' || url == '#'){
    			alert("Ingrese Ruta")
    		} 
    		else{
    			/* OSINE_SFS-791 - RSIS 29-30-31 - Inicio */
        		var li='';    		
        		if(($(this).attr('class')).indexOf('item-infraccion')!=-1){
        			li=$(this).parent();
        			$(".listado-infraccion li").removeClass("active-infraccion");
        			li.addClass("active-infraccion");
        		} else if(($(this).attr('class')).indexOf('btn-levantamiento')!=-1 && ($(this).attr('class')).indexOf('btn-zoom')==-1){
        			li=$(this).parent().parent();
        			$(".listado-infraccion li").removeClass("active-infraccion");
        			li.addClass("active-infraccion");
        		} 
        		/* OSINE_SFS-791 - RSIS 29-30-31 - Fin */        		
    			if(!$el.hasClass('btn_inner')){
    				that.$btnAjx.removeClass('active');
    			}          
    			$el.addClass('active');
    			that.navAjx(url);
    		}
    	});
    },
    /* OSINE_SFS-791 - RSIS 29-30-31 - Inicio */ 
    parameters : function(url){
    	var parameters = "_=p";
    	parameters += "&idSupervision=" + ($('.active-infraccion .idSupervision').val() != undefined ? $('.active-infraccion .idSupervision').val() : 0);
    	parameters += "&numeroExpediente=" + ($('.active-infraccion .numeroExpediente').val() != undefined ? $('.active-infraccion .numeroExpediente').html() : ""); 
    	parameters += "&idExpediente=" + ($('.active-infraccion .idExpediente').val() != undefined ? $('.active-infraccion .idExpediente').val() : 0);
    	if(url.indexOf('?')!=-1 && url.split('?').length!=1){
    		var arrayUrl=url.split('?');
    		parameters += "&navegacion="+arrayUrl[0];    		
    		parameters += "&idDetalleSupervision="+arrayUrl[1];
    		parameters += "&modoSupervision="+arrayUrl[2];
    		$('#codOsinergminSeccion').val($('.active-infraccion .codOsinergmin').html());
    		$('#urlDetSeccion').val(url);
    	} else {
    		$('#urlSeccion').val(url);
    		$('#codOsinergminSeccion').val($('.active-infraccion .codOsinergmin').html());    			
    		parameters += "&navegacion="+url;
    	}
    	return parameters;
    },
    confirmer : function(tituloModal, mensajeModal, evtClick){
    	$('.tituloModal').html(tituloModal);
		$('.mensajeModal').html(mensajeModal);
		if(evtClick!=null && evtClick!=''){
			$("#confirmacion .btnCancelarConfirmer").css('display', 'inline');
			$('#confirmacion .btnAceptarConfirmer').attr('onclick', evtClick);			
		} else {
			$("#confirmacion .btnCancelarConfirmer").css('display', 'none');
			$('#confirmacion .btnAceptarConfirmer').removeAttr("onclick");
            $('.modal-backdrop').remove();
		}		
		$('#confirmacion').modal('show');
    },
    loadingOpen: function() {
        $('.loading').css('display', '');
    },
    loadingClose: function() {
        $('.loading').css('display', 'none');
    },
    /* OSINE_SFS-791 - RSIS 29-30-31 - Fin */ 
    navAjx : function(url){
    	/* OSINE_SFS-791 - RSIS 29-30-31 - Inicio */    	    	
    	var that = this; 
        $.ajax({
			url: "/inps/pages/modLevantamientos/mostrarInfraccion",
	        type:'get',
	        async:false,
	        data: this.parameters(url),
	        beforeSend:navSlide.loadingOpen(),
	        success:function(data){
	        	navSlide.loadingClose();
	        	that.$wrap.empty();
	            that.$wrap.html(data);
	        }
	    });
        /* OSINE_SFS-791 - RSIS 29-30-31 - Fin */ 
    },
    init : function(){
    	this.selectores();  
    }
}

var navSlide = new navSlide();
navSlide.init();
navSlide.navAjx('modLevantamientos/bandejaSupervisionInfraccion');

/*--------------------------------------------------------------------------------------------------------- 
GENERALES
**//*---------------------------------------------------------------------------------------------------- */



/*--------------------------------------------------------------------------------------------------------- 
RESPONSIVE PANEL SMARTPHONE
**//*---------------------------------------------------------------------------------------------------- */

var correoDrop = function () {
    //var speed = 600;
    $('#dropCorreo').on('click', function () {
        $el = $(this),
        $box = $('.correo-hide');

        if (!$el.hasClass('active')) {
            $box.show();
            $el.addClass('active');
        } 
        else {
            $box.hide();
            $el.removeClass('active');
        }
    });

    $('.remove-drop-correo').on('click', function(){
        $('#dropCorreo').trigger('click');
    });
};

var tabLeftPanel = function(){

    $("#content-pvo div.tab-hide").hide(); // Initially hide all content
    $("#tab-pvo li:first").attr("id","current"); // Activate first tab
    $("#content-pvo div.tab-hide:first").fadeIn(); // Show first tab content
    $('#tab-pvo li a').click(function(e) {
        e.preventDefault();
        if ($(this).attr("id") == "current"){ //detection for current tab
         return       
        }
        else{             
        $("#content-pvo div.tab-hide").hide(); //Hide all content
        $("#tab-pvo li").attr("id",""); //Reset id's
        $(this).parent().attr("id","current"); // Activate this
        $( $(this).attr('href')).fadeIn(); // Show content for current tab
        }
    });
    
};

var controlGrupoSiguo = function(){

    $("#content-pvo-unidad div.tab-hide-unidad").hide(); // Initially hide all content
    $("#tab-pvo-unidad li:first").attr("id","current"); // Activate first tab
    $("#content-pvo-unidad div.tab-hide-unidad:first").fadeIn(); // Show first tab content
    $('#tab-pvo-unidad li a').click(function(e) {
        e.preventDefault();
        if ($(this).attr("id") == "current"){ //detection for current tab
         return       
        }
        else{             
        $("#content-pvo-unidad div.tab-hide-unidad").hide(); //Hide all content
        $("#tab-pvo-unidad li").attr("id",""); //Reset id's
        $(this).parent().attr("id","current"); // Activate this
        $( $(this).attr('href')).fadeIn(); // Show content for current tab
        }
    });
    
};
 

var heightMenuOpen = function(){
     $(window).resize(function () {
        $('.scroll-tab').css("height", $(window).innerHeight() - 170);
    });
    $(window).trigger('resize');
};


/*--------------------------------------------------------------------------------------------------------- 
RESIZE WINDOW SCROLL COLUMN
**//*---------------------------------------------------------------------------------------------------- */

var columResize = function(){
     $(window).resize(function () {
        $('.colum-bandeja').css("height", $(window).innerHeight() - 110);
    });
    $(window).trigger('resize');
};

var columResizeMax = function(){
     $(window).resize(function () {
        $('.colum-bandeja-max').css("height", $(window).innerHeight() - 170);
    });
    $(window).trigger('resize');
};


/*--------------------------------------------------------------------------------------------------------- 
RESIZE WINDOW SCROLL CORREO BANDEJA
**//*---------------------------------------------------------------------------------------------------- */

var columResizeInbox = function(){
     $(window).resize(function () {
        $('.inbox').css("height", $(window).innerHeight() - 170);
    });
    $(window).trigger('resize');
};