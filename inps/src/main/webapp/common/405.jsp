<%-- 
    Document   : 405
    Created on : 16/06/2015, 10:00:16 AM
    Author     : jpiro
--%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template pageTitle="Bienvenido" scrollPanelTitle="">
    
    <jsp:attribute name="headArea">
        <script type="text/javascript" charset="utf-8">
            
        </script>
        
        <style>
            .iconoMenu{
                    float: left;
                    overflow: hidden;
                    width: 200px;
                    margin: 5px 10px;
                    text-align: center;				
            }
            .iconoMenu img{
                    width: 100px;
                    height: 100px;
                    border-width:0px;
            }
            .iconoMenu span{
                    display: block;
                    text-transform:capitalize;
                    text-align: center;		
                    font-weight:bold;
                    font-size: 1em;					
            }
            .iconoMenu span a{
                    text-decoration: none;						
            }
            .iconoMenu:hover{
                    background-color:#CBE3F7;							
            }
            #contenedorIconos {	
                    margin: 20px auto;
                    max-width: 800px;
                    overflow: hidden;	
                    display: table;
            }
        </style> 
    </jsp:attribute>
    
    <jsp:attribute name="bodyArea">
        <div class="tac" style="margin-top:40px;">
            <div class="tac titua">ERROR 505</div>
            <div>No puede acceder directamente a esta pagina. <br>
                Por favor utilice los vinculos del Sistema para ingresar a los modulos deseados.<br>
                Haga click <a onclick="window.location.href = '/inps/';" style="color: blue;font-weight: bold;cursor: pointer;">aqui</a> para volver a la pagina de inicio.
            </div>            
        </div>
    </jsp:attribute>
    
</t:template>