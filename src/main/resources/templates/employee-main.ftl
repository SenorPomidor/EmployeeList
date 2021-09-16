<!DOCTYPE>
<html>
    <body>
        <div style="width: 500px; margin: 0 auto; text-align: center">
            <h2>Wellcome, ${name} ${surname}!</h2>
            <img style="width: 100%" src="https://miro.medium.com/max/1400/0*oyD7ekV-hMU91h4J.png" />
            <br><br>
            <input type="button" value="To tasks" onclick="window.location.href = 'tasksList/' + ${id}">
            <input type="button" value="Sign Out" onclick="window.location.href = 'logout'">
        </div>
    </body>
</html>