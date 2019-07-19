<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <link href="<c:url value="/resources/css/form.css" />" rel="stylesheet">
</head>
<body>

<table>
    <tr>
        <th><spring:message code="form.column.name"/></th>
        <th><spring:message code="form.column.job"/></th>
        <th><spring:message code="form.column.date"/></th>
        <th><spring:message code="form.column.time"/></th>
        <th><spring:message code="form.column.case"/></th>
    </tr>
    <c:if test="${not empty absenceHistory}">
        <c:forEach var="item" items="${absenceHistory}">
            <tr>
                <td>${item.employee.name}</td>
                <td>${item.job.position}</td>
                <td>${item.date}</td>
                <td>${item.startTime}-${item.endTime}</td>
                <td>${item.cause}</td>
            </tr>
        </c:forEach>
    </c:if>
</table>

<br/>

<form:form action="getEmployeeAbsences" modelAttribute="searchAbsence">
    <table>
        <tr>
            <th colspan="3"><spring:message code="form.search.title"/></th>
        </tr>
        <tr>
            <td><spring:message code="form.search.column.employee"/></td>
            <td/>
            <td>
                <form:select path="employee">
                    <form:option value="-1">
                        <spring:message code="option.select-employee"/>
                    </form:option>

                    <form:options items="${employees}" itemValue="id" itemLabel="name"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td><spring:message code="form.search.column.job"/></td>
            <td/>
            <td>
                <form:select path="job">
                    <form:option value="-1">
                        <spring:message code="option.select-job"/>
                    </form:option>

                    <form:options items="${jobs}" itemValue="id" itemLabel="position"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <spring:bind path="dateStr">
                <td><spring:message code="form.search.column.date"/></td>
                <td>
                    <form:select path="dateOp">
                        <form:options items="${dateSearchOps}" itemValue="name" itemLabel="name"/>
                    </form:select>
                </td>
                <td>
                    <form:input type="text" path="dateStr" class="date" name="dateStr"/>
                <br/>
                    <form:errors path="date" cssClass="error"/>
                </td>
            </spring:bind>
        </tr>
        <tr>
            <spring:bind path="startTimeStr">
                <td><spring:message code="form.search.column.startTime"/></td>
                <td>
                <form:select path="startTimeOp">
                    <form:options items="${dateSearchOps}" itemValue="name" itemLabel="name"/>
                </form:select>
                </td>
                <td>
                    <form:input type="text" path="startTimeStr" class="time" name="startTimeStr"/>
                    <br/>
                    <form:errors path="startTime" cssClass="error"/>
                </td>
            </spring:bind>
        </tr>
        <tr>
            <spring:bind path="endTimeStr">
                <td><spring:message code="form.search.column.endTime"/></td>
                <td>
                    <form:select path="endTimeOp">
                        <form:options items="${dateSearchOps}" itemValue="name" itemLabel="name"/>
                    </form:select>
                </td>
                <td>
                    <form:input type="text" path="endTimeStr" class="time" name="endTimeStr"/>
                    <br/>
                    <form:errors path="endTime" cssClass="error"/>
                </td>
            </spring:bind>
        </tr>
        <tr>
            <td><spring:message code="form.search.column.cause"/></td>
            <td/>
            <td><form:input type="text" path="cause" class="text" name="cause"/></td>
        </tr>
        <tr>
            <td colspan="3"><button type="submit"><spring:message code="button.search"/></button></td>
        </tr>
    </table>
</form:form>

<br/>

<form:form action="addEmployeeAbsence" modelAttribute="newAbsence">
    <table>
        <tr><th colspan="2"><spring:message code="form.add.title"/></th></tr>
        <tr>
            <spring:bind path="employee">
                <td><spring:message code="form.add.column.employee"/></td>
                <td>
                    <form:select path="employee">
                        <form:option value="-1">
                            <spring:message code="option.select-employee"/>
                        </form:option>
                        <form:options items="${employees}" itemValue="id" itemLabel="name"/>
                    </form:select>
                    <br/>
                    <form:errors path="employee" cssClass="error"/>
                </td>
            </spring:bind>
        </tr>
        <tr>
            <spring:bind path="job">
                <td><spring:message code="form.add.column.job"/></td>
                <td>
                    <form:select path="job">
                        <form:option value="-1">
                            <spring:message code="option.select-job"/>
                        </form:option>
                        <form:options items="${jobs}" itemValue="id" itemLabel="position"/>
                    </form:select>
                    <br/>
                    <form:errors path="job" cssClass="error"/>
                </td>
            </spring:bind>
        </tr>
        <tr>
            <spring:bind path="date">
                <td><spring:message code="form.add.column.date"/></td>
                <td>
                    <form:input path="dateStr" class="date" type="text" name="dateStr"/>
                    <br/>
                    <form:errors path="dateStr" cssClass="error"/>
                </td>
            </spring:bind>
        </tr>
        <tr>
            <spring:bind path="startTime">
                <td><spring:message code="form.add.column.startTime"/></td>
                <td>
                    <form:input path="startTimeStr" class="time" type="text" name="startTimeStr"/>
                    <br/>
                    <form:errors path="startTimeStr" cssClass="error"/>
                </td>
            </spring:bind>
        </tr>
        <tr>
            <spring:bind path="endTime">
                <td><spring:message code="form.add.column.endTime"/></td>
                <td>
                    <form:input path="endTimeStr" class="time" type="text" name="endTimeStr"/>
                    <br/>
                    <form:errors path="endTimeStr" cssClass="error"/>
                </td>
            </spring:bind>
        </tr>
        <tr>
            <spring:bind path="cause">
                <td><spring:message code="form.add.column.cause"/></td>
                <td>
                    <form:input path="cause" class="text" type="text" name="cause"/>
                    <br/>
                    <form:errors path="cause" cssClass="error"/>
                </td>
            </spring:bind>
        </tr>
        <tr>
            <td colspan="2"><button type="submit"><spring:message code="button.add"/></button></td>
        </tr>
    </table>
</form:form>
</body>
</html>