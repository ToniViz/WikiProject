<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <link href="recursos/normalize.css" rel="stylesheet" type="text/css">
        <link href="recursos/style.css" type="text/css" rel="stylesheet" >

       
        <title>Cambio de Rol Supervisor</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/paginas/comunes/cabecero.jsp"></jsp:include>
        
        <jsp:include page="/WEB-INF/paginas/supervisor/supervisorMenu.jsp"></jsp:include>

            <!-- Cambio de Rol -->
            <main class="contenedor sombra">
            <jsp:include page="/WEB-INF/paginas/comunes/cambioRol.jsp"></jsp:include>
            </main>

            <!-- Pie de pagina -->
        <jsp:include page="/WEB-INF/paginas/comunes/piedePagina.jsp"></jsp:include>




    </body>
</html>