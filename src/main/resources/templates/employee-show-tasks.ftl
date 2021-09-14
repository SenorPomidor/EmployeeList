<!DOCTYPE html>
<html>
<body>
<div style="width: 640px; margin: 0 auto; text-align: center">
    <h2>Your tasks</h2>
    <#if noTasks??>
        <p style="color: red; font-size: 16px; font-weight: bold;">${noTasks}</p>
    </#if>
    <#list allTasks>
        <table style="border-collapse: collapse">
            <tr>
                <th style="width: 17%; padding: 2px 10px; background-color: #ccddff">Description</th>
                <th style="width: 17%; padding: 2px 10px; background-color: #ccddff">Complete</th>
                <th style="width: 17%; padding: 2px 10px; background-color: #ccddff">Operations</th>
            </tr>
            <#items as task>
                <tr class="${task?item_parity}Row">
                    <td style="background-color: #e6eeff">${task.description}</td>
                    <td style="background-color: #e6eeff">${task.complete?c}</td>
                    <td style="background-color: #e6eeff">
                        <input type="button" value="Complete" onclick="window.location.href = '/soon'"/>
                    </td>
                </tr>
            </#items>
        </table>
        <br>
    </#list>
    <input type="button" value="To main" onclick="window.location.href = '/'">
</div>
</body>
</html>
