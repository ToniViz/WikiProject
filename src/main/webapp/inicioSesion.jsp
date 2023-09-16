<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="recursos/normalize.css" rel="stylesheet" type="text/css">
        <link href="recursos/style.css" type="text/css" rel="stylesheet" >


        <title>Wikis</title>
    </head>
    <body>


        <jsp:include page="/WEB-INF/paginas/comunes/cabecero.jsp"></jsp:include>
            <main class="contenedor sombra"> 

                <h2>Inicio de Sesión</h2>
                <section class="contenedor-fondo degradado-fondo">

                    

                    <form class="formulario" action="${pageContext.request.contextPath}/Controlador?accion=inicios" method="POST">
                    <fieldset>
                        <div>
                            <div class="campo">
                                <label>Nombre</label>
                                <input class="input-text" type="text" placeholder="Nombre de usuario" name="nombre" required>
                            </div>
                            <div class="campo">
                                <label>Contraseña</label>
                                <input class="input-text" type="text" placeholder="Password" name="pass" required>
                            </div>
                        </div>
                        <div class="centrar">
                            <input class="boton" type="submit" id="ocultar-modal" value="Enviar" />
                        </div>
                    </fieldset>


                </form>
            </section>



        </main>



    </body>
</html>



