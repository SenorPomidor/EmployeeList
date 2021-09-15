<!DOCTYPE html>
<html>
<body>
<div style="width: 50%; margin: 0 auto; text-align: center">
    <h2>Add task</h2>
    <form action="/saveTask" method="post">
        <div style="padding-bottom: 5px">
            <#if taskSuccessfullyAdded??>
                <p style="color: green; font-size: 9px">${taskSuccessfullyAdded}</p>
            </#if>
            <input type="hidden" name="id" value="${employee.id}">
            <#if descriptionError??>
                <p style="color: red; font-size: 9px">${descriptionError}</p>
            </#if>
            <div>
                Description: <textarea name="description" placeholder="Description"></textarea>
            </div>
        </div>
        <br>
        <input type="submit" value="OK"/>
        <input type="button" value="To list" onclick="window.location.href = '/tasksList/' + ${employee.id}">
    </form>
</div>
</body>
</html>