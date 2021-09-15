<!DOCTYPE html>
<html>
<body>
<div style="width: 50%; margin: 0 auto; text-align: center">
    <h2>Update task</h2>
    <form action="/updateTask" method="post">
        <div style="padding-bottom: 5px">
            <#if taskSuccessfullyUpdated??>
                <p style="color: green; font-size: 9px">${taskSuccessfullyUpdated}</p>
            </#if>
            <input type="hidden" name="id" value="${task.id}">
            <#if descriptionError??>
                <p style="color: red; font-size: 9px">${descriptionError}</p>
            </#if>
            <div>
                Description: <textarea name="description" placeholder="Description">${task.description}</textarea>
            </div>
        </div>
        <br>
        <input type="submit" value="OK"/>
        <input type="button" value="To list" onclick="window.location.href = '/tasksList/' + ${id}">
    </form>
</div>
</body>
</html>