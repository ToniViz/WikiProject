<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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

        <jsp:include page="/WEB-INF/paginas/coordinador/coordinadorMenu.jsp"></jsp:include>

            <!-- Creación de artículo -->
            <main class="contenedor sombra">
                <section>
                    <h3>Añadir Contenido a ${articulo.tituloArticulo}</h3>
                <form class="formulario" action="${pageContext.request.contextPath}/Controlador?accion=addContent"
                      method="POST">
                    <fieldset>
                        <legend>Completa los campos</legend>
                        <div>
                            <div class="campo">
                                <label for="Subtitulo">Subtitulo</label>
                                <input class="input-text" type="text" cols="30" rows="2" name="Subtitulo" ></textarea>
                            </div>
                             <div class="campo">
                                 <label for="Contenido">Contenido</label>
                                 <textarea class="input-text" type="text" cols="30" rows="10" name="Contenido" ></textarea>
                            </div>
                        </div>
                        <div>
                            <input class="boton" type="submit" value="Enviar">
                        </div>
                    </fieldset>
                </form>
            </section>
                          

        </main>

        <!-- Pie de pagina -->
        <jsp:include page="/WEB-INF/paginas/comunes/piedePagina.jsp"></jsp:include>




    </body>
</html>