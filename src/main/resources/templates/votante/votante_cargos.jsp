<h2>Cargos y Candidatos</h2>
<c:forEach var="cargo" items="${cargos}">
    <h3>${cargo.nombre}</h3>
    <c:forEach var="candidato" items="${cargo.candidatos}">
        <c:if test="${candidato.estado == 'APROBADO'}">
            <p>${candidato.nombre}</p>
            <form method="post" action="/votante/votar">
                <input type="hidden" name="candidatoId" value="${candidato.id}" />
                <input type="hidden" name="votanteId" value="<!-- ID DEL VOTANTE -->" />
                <button type="submit">Votar</button>
            </form>
        </c:if>
    </c:forEach>
</c:forEach>
