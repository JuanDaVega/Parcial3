<h2>Procesos Electorales</h2>
<c:forEach var="proceso" items="${procesos}">
    <p>
        ${proceso.nombre}
        <a href="/votante/procesos/${proceso.id}/cargos">Ver Cargos</a>
    </p>
</c:forEach>
