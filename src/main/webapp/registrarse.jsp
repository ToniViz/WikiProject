 
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="recursos/normalize.css" rel="stylesheet" type="text/css">
        <link href="recursos/style.css" type="text/css" rel="stylesheet" >
       


        <title>Registrarse</title>
    </head>
    <body>


        <jsp:include page="/WEB-INF/paginas/comunes/cabecero.jsp"></jsp:include>
        <main class="contenedor sombra">
            
            <h2>Formulario de Registro</h2>

            <section class="contenedor-fondo degradado-fondo">            
                <form id="formulario" class="formulario" action="${pageContext.request.contextPath}/Controlador?accion=registro" method="POST">
                    <fieldset>
                        <div>
                            <div class="campo">
                                <label>Nombre</label>
                                <input class="input-text" type="text" placeholder="Nombre" name="nombre" required>
                            </div>
                            <div class="campo">
                                <label>Primer Apellido</label>
                                <input class="input-text" type="text" placeholder="Apellido" name="apellido" required>
                            </div>
                            <div class="campo">
                                <label>Segundo Apellido)</label>
                                <input class="input-text" type="text" placeholder="Apellido" name="apellidoDos" required>
                            </div>
                            <div class="campo">
                                <label>Dirección</label>
                                <input id="direccion" class="input-text" type="text" placeholder="Ciudad, Calle/Avenida, Nº" name="direccion" required>
                            </div>
                            <div class="campo">
                                <label>Teléfono</label>
                                <input id="telefono" class="input-text" type="text" placeholder="Telefono" name="telefono" required>
                            </div>
                        </div>
                        <div class="centrar">
                            <input id="boton" class="boton" type="submit" value="Enviar" />
                        </div>
                    </fieldset>


                </form>
            </section>

        </main>



    </body>
</html>



