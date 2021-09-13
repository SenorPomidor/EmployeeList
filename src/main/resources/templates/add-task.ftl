<!DOCTYPE html>
<html>
<body>
<div style="width: 50%; margin: 0 auto; text-align: center">
    <h2>Add task</h2>
    <form action="/saveNewTask" method="post">
        <div style="padding-bottom: 5px">
            <input type="hidden" name="id" value="${emp.id}">
            <#if descriptionError??>
                <p style="color: red; font-size: 9px">${descriptionError}</p>
            </#if>
            <div>
                Description: <input type="text" name="description" placeholder="Description"/>
            </div>
        </div>
        <input type="submit" value="OK"/>
        <input type="button" value="To list" onclick="window.location.href = '/tasksList/' + ${emp.id}">
    </form>
</div>
</body>
</html>