<h1>

    <#list users as user>
        ${user.name1!'null'}   ${user.id}<br>
    </#list>

    ${.now?string("yyyy-MM-dd HH:mm:ss.sss")}

    <form action="login" method="post" >
        账号: <input type="text" name="username" value="lisi"><br>
        密码: <input type="text" name="password" value="123"><br>
        <button>登录</button>
    </form>


</h1>
